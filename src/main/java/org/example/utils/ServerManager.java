package org.example.utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.util.HashMap;

public class ServerManager {

    // create a thread local object server
    // this service is for AppiumDriverLocalService below
    //
    private static ThreadLocal<AppiumDriverLocalService> server = new ThreadLocal<>();

    // this for logging purpose
    TestUtils utils = new TestUtils();

    /**
     * create method to get which local server we are running
     * Mac or Windows
     * using a getter method
     *
     */
    public AppiumDriverLocalService getServer(){
        return server.get();
    }

    /**
     * print starting appium server
     * select which machine mac or windows (MacGetAppiumService or WinddowsGetAppiumService) and
     * start server
     * check if server is null then stop else
     *
     * print appium server is started
     */
    public void startServer(){

        utils.log().info("starting appium server");

        AppiumDriverLocalService server = MacGetAppiumService();

        server.start();

        if(server == null || !server.isRunning()){
            utils.log().fatal("Appium server not started. ABORT!!!");
            throw new AppiumServerHasNotBeenStartedLocallyException("Appium server not started. ABORT!!!");
        }

        server.clearOutPutStreams(); // -> Comment this if you want to see server logs in the console

        // this.server is server class object declared above
        // setting value and then we can access it by using
        // getServer() method above
        this.server.set(server);

        utils.log().info("Appium server started");
    }

    public AppiumDriverLocalService getAppiumServerDefault() {
        return AppiumDriverLocalService.buildDefaultService();
    }

    public AppiumDriverLocalService WindowsGetAppiumService() {
        GlobalParams params = new GlobalParams();
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withLogFile(new File(params.getPlatformName() + "_"
                        + params.getDeviceName() + File.separator + "Server.log")));
    }

    public AppiumDriverLocalService MacGetAppiumService() {
        GlobalParams params = new GlobalParams();

        // need to understand this code
        HashMap<String, String> environment = new HashMap<String, String>();
        environment.put("PATH", "/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home:/Users/abdieid/Library/Android/sdk" + System.getenv("PATH"));
        environment.put("ANDROID_HOME", "/Users/abdieid/Library/Android/sdk");
        environment.put("JAVA_HOME", "/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home");
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File("/usr/local/bin/node"))
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withEnvironment(environment)
                .withLogFile(new File(params.getPlatformName() + "_"
                        + params.getDeviceName() + File.separator + "Server.log"))); // here we can view server log
    }

}
