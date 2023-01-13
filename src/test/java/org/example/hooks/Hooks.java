package org.example.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.ThreadContext;
import org.example.utils.DriverManager;
import org.example.utils.GlobalParams;
import org.example.utils.ServerManager;
import org.openqa.selenium.OutputType;

public class Hooks {

    /**
     * before any test begins we do the following:
        * initialize global params class and use its initializeGlobalParams method
        * default platform is Android if using runner class and we can change this in global params setplafromName metheod
        * start server
     */
    @Before
    public void initialize() throws Exception {

        GlobalParams params = new GlobalParams();
        params.initializeGlobalParams();

        //log4j stuff see TDD section to understand
        // use getplatformName and getDeviceName methods from global params so
        // log4j can create a unique folder for each of the devices we can test

        ThreadContext.put("ROUTINGKEY", params.getPlatformName() + "_"
                + params.getDeviceName());

        // start server

        new ServerManager().startServer();

        new DriverManager().initializeDriver();


    }

    /**
     * after tests runs we do the following
     * quit appium server
     */
    @After
    public void quit(Scenario scenario){

        if(scenario.isFailed()){
            byte[] screenshot = new DriverManager().getDriver().getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }

        DriverManager driverManager = new DriverManager();
        if(driverManager.getDriver() != null){
            driverManager.getDriver().quit();
            driverManager.setDriver(null);
        }

        // stop or quit appium server
        ServerManager serverManager = new ServerManager();
        if(serverManager.getServer() != null){
            serverManager.getServer().stop();
        }




    }







}


