package org.example.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.apache.logging.log4j.ThreadContext;
import org.example.utils.DriverManager;
import org.example.utils.GlobalParams;
import org.example.utils.ServerManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;



@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
        ,features = {"src/test/resources"}
        ,glue = {"org.example.stepdef", "org.example.hooks"}
        ,snippets = CucumberOptions.SnippetType.CAMELCASE
        ,dryRun = false
        ,monochrome = true
        , publish = true
//       ,tags = "@test"
)

public class RunnerTest {

    @BeforeClass
    public static void initialize() throws Exception {

        GlobalParams params = new GlobalParams();
        params.initializeGlobalParams();

        //log4j stuff see TDD section to understand
        // use getplatformName and getDeviceName methods from global params so
        // log4j can create a unique folder for each of the devices we can test
        ThreadContext.put("ROUTINGKEY", params.getPlatformName() + "_"
                + params.getDeviceName());

        // start server and driver
        /**
         * This approach of using before hook has the following results
         *   an app will be installed for every scenario we have
         *   Driver instance will be instantiated fro each scenario
         *   This will result in extra time to execute the automation test
         *   1:30 to 1:35
         */
        new ServerManager().startServer();
        new DriverManager().initializeDriver();
    }

    @AfterClass
    public static void quit(){
        DriverManager driverManager = new DriverManager();
        if(driverManager.getDriver() != null){
            driverManager.getDriver().quit();
            driverManager.setDriver(null);
        }
        ServerManager serverManager = new ServerManager();
        if(serverManager.getServer() != null){
            serverManager.getServer().stop();
        }
    }


}
