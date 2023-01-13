package org.example.utils;

public class GlobalParams {

    // common desired capabilities for Android and IOS
    // Thread local is used to support parallel execution
    //
    private static ThreadLocal<String> platformName = new ThreadLocal<String>();
    private static ThreadLocal<String> udid = new ThreadLocal<String>();
    private static ThreadLocal<String> deviceName = new ThreadLocal<String>();
    private static String abbName;

   // Android only
    private static ThreadLocal<String> systemPort = new ThreadLocal<String>();
    private static ThreadLocal<String> chromeDriverPort = new ThreadLocal<String>();

    //IOS only
    private static ThreadLocal<String> wdaLocalPort = new ThreadLocal<String>();
    private static ThreadLocal<String> webkitDebugProxyPort = new ThreadLocal<String>();

    private static String a;

/**
 * create setter and getter methods for each of the above
 * They are "Creating" and "Retaining" the "thread local object" of these variables
 * Hence, this ensures in a multithreading environment, each thread will have its own value for these variables
 *
 * The methods are called from the initialze global param method and string values are passed in as well
 */


    public String setPlatformName11(String platformName1){

        return a = "Android";
}

    public void setPlatformName(String platformName1){
    platformName.set(platformName1);
}

    public String getPlatformName(){
        return platformName.get();
    }

    public String getUDID() {
        return udid.get();
    }

    public void setUDID(String udid2) {
        udid.set(udid2);
    }

    public String getDeviceName() {
        return deviceName.get();
    }

    public void setDeviceName(String deviceName2) {
        deviceName.set(deviceName2);
    }

    public String getSystemPort() {
        return systemPort.get();
    }

    public void setSystemPort(String systemPort2) {
        systemPort.set(systemPort2);
    }

    public String getChromeDriverPort() {
        return chromeDriverPort.get();
    }

    public void setChromeDriverPort(String chromeDriverPort2) {
        chromeDriverPort.set(chromeDriverPort2);
    }

    public String getWdaLocalPort() {
        return wdaLocalPort.get();
    }

    public void setWdaLocalPort(String wdaLocalPort2) {
        wdaLocalPort.set(wdaLocalPort2);
    }

    public String getWebkitDebugProxyPort() {
        return webkitDebugProxyPort.get();
    }

    public void setWebkitDebugProxyPort(String webkitDebugProxyPort2) {
        webkitDebugProxyPort.set(webkitDebugProxyPort2);
    }

    /**
     * create a new method to initialize Global params
     */

    public void initializeGlobalParams(){
        GlobalParams params = new GlobalParams();

        /**
         * setting "default values" for the above variables
         * using system.getProperty to set the values as key value pair
         * if we run the test using runner and not maven command
         * it will use these values and if i want to change these values
         * I can do it here. This is for runner but not for maven.
         * if we use command line we can send these system properties values
         *
         * Calling the above methods and passing in the string value
         */


        params.setPlatformName(System.getProperty("platformName", "Android"));
        params.setUDID(System.getProperty("udid", "emulator-5554"));
        params.setDeviceName(System.getProperty("deviceName", "Pixel_5"));


        /**
         *the above was common for both IOS and Android but the rest of the variables they are platform specific
         * we use switch and case from there we set the values for the variables for each platform
         */

        switch(params.getPlatformName()){
            case "Android":
                params.setSystemPort(System.getProperty("systemPort", "10000"));
                params.setChromeDriverPort(System.getProperty("chromeDriverPort", "11000"));
                break;
            case "iOS":
                params.setWdaLocalPort(System.getProperty("wdaLocalPort", "10001"));
                params.setWebkitDebugProxyPort(System.getProperty("webkitDebugProxyPort", "11001"));
                break;
            default:
                throw new IllegalStateException("Invalid Platform Name!");
        }
    }







}
