package org.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {

    private static Properties props  = new Properties();

    // logging
    TestUtils utils = new TestUtils();

    // returns properties object
    // to load properties we use input stream
    // pr
    public Properties getProps() throws IOException {
        InputStream is = null;
        String propsFileName = "config.properties";
        //check if we loaded propsFile because we don't need to load it again and again
        if(props.isEmpty()){
            try{

                utils.log().info("loading config properties");
                // since the file is in the class path we just provide propsFileName
                // if we did'nt have it in classpath we will need to provide the full path
                is = getClass().getClassLoader().getResourceAsStream(propsFileName);
                // load props
                props.load(is);
            } catch (IOException e){
                e.printStackTrace();
                utils.log().fatal("Failed to load config properties. ABORT" + e.toString());
                throw e;
            } finally {
                if(is !=null){
                    is.close();
                }
            }
        }
      return props;
    }





}
