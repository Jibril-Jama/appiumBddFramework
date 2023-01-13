package org.example.utils;

import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.java.hu.De;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class CapabilitiesManager {

    TestUtils utils = new TestUtils();

    // return the desired capabilities based on platform (IOS or Android)

    public DesiredCapabilities getCaps() throws IOException {

        GlobalParams params = new GlobalParams();

        // load properties

        Properties pros = new PropertyManager().getProps();

        try{
            utils.log().info("getting capabilities");
            DesiredCapabilities caps = new DesiredCapabilities();
            // values coming from global params
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, params.getPlatformName());
            caps.setCapability(MobileCapabilityType.UDID, params.getUDID());
            caps.setCapability(MobileCapabilityType.DEVICE_NAME, params.getDeviceName());

            switch(params.getPlatformName()){
                case "Android":

                    // values coming from config property file
                    caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, pros.getProperty("androidAutomationName"));
                    caps.setCapability("appPackage", pros.getProperty("androidAppPackage"));
                    caps.setCapability("appActivity", pros.getProperty("androidAppActivity"));

                    // values coming from global params
                    caps.setCapability("systemPort", params.getSystemPort());
                    caps.setCapability("chromeDriverPort", params.getChromeDriverPort());
                    //String androidAppUrl = getClass().getResource(props.getProperty("androidAppLocation")).getFile();

                    // defining app location
                    String androidAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                            + File.separator + "resources" + File.separator + "apps" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.2.1.apk";

                    // adding logs
                    utils.log().info("appUrl is" + androidAppUrl);
                    caps.setCapability("app", androidAppUrl);
                    break;

                case "iOS":
                    caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, pros.getProperty("iOSAutomationName"));
                    //String iOSAppUrl = getClass().getResource(props.getProperty("iOSAppLocation")).getFile();
                    String iOSAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                            + File.separator + "resources" + File.separator + "apps" + File.separator + "SwagLabsMobileApp.app";
                    utils.log().info("appUrl is" + iOSAppUrl);
                    caps.setCapability("bundleId", pros.getProperty("iOSBundleId"));
                    caps.setCapability("wdaLocalPort", params.getWdaLocalPort());
                    caps.setCapability("webkitDebugProxyPort", params.getWebkitDebugProxyPort());
                    caps.setCapability("app", iOSAppUrl);
                    break;
            }
            return caps;
        } catch(Exception e){
            e.printStackTrace();
            utils.log().fatal("Failed to load capabilities. ABORT!!" + e.toString());
            throw e;
        }

    }

}
