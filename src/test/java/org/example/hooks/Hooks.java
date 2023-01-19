package org.example.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.ThreadContext;
import org.example.pages.BasePage;
import org.example.utils.DriverManager;
import org.example.utils.GlobalParams;
import org.example.utils.ServerManager;
import org.example.utils.VideoManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class Hooks {

    /**
     * before any test begins we do the following:
        * initialize global params class and use its initializeGlobalParams method
        * default platform is Android if using runner class and we can change this in global params setplafromName metheod
        * start server
     */
    @Before
    public void initialize() throws Exception {

        // see TDD on what these methods do and base class
        BasePage basePage = new BasePage();
        basePage.closeApp();
        basePage.launchApp();

        /*GlobalParams params = new GlobalParams();
        params.initializeGlobalParams();*/

        //log4j stuff see TDD section to understand
        // use getplatformName and getDeviceName methods from global params so
        // log4j can create a unique folder for each of the devices we can test

        /*ThreadContext.put("ROUTINGKEY", params.getPlatformName() + "_"
                + params.getDeviceName());*/

        // start server
        /**
         * This approach of using before hook has the following results
         *   an app will be installed for every scenario we have
         *   Driver instance will be instantiated fro each scenario
         *   This will result in extra time to execute the automation test
         *   1:30 to 1:35
         */

       /* new ServerManager().startServer();
        new DriverManager().initializeDriver();*/
        new VideoManager().startRecording();


    }

    /**
     * after tests runs we do the following
     * quit appium server
     */
    @After
    public void quit(Scenario scenario) throws IOException {

        try {

            if (scenario.isFailed()) {

                /*File src = new DriverManager().getDriver().getScreenshotAs(OutputType.FILE);
                byte[] fileContent = FileUtils.readFileToByteArray(src);
                scenario.attach(fileContent, "image/png", scenario.getName());
                scenario.log("ScreenShot attached");*/

                byte[] screenshot = new DriverManager().getDriver().getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
                scenario.log("ScreenShot attached");

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }




        new VideoManager().stopRecording(scenario.getName());

       /* DriverManager driverManager = new DriverManager();
        if(driverManager.getDriver() != null){
            driverManager.getDriver().quit();
            driverManager.setDriver(null);
        }

        // stop or quit appium server
        ServerManager serverManager = new ServerManager();
        if(serverManager.getServer() != null){
            serverManager.getServer().stop();
        }*/




    }







}


