package org.wordscaster;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by A on 27.06.2016.
 */
public class Config {
    final static Logger logger = Logger.getLogger(Config.class);
    public Config() {
    }
    public static String readProperty(String propertyName ) throws IOException {
        Properties prop = new Properties();
        String result = null;

        File catalinaBase = new File(System.getProperty( "catalina.base" )).getAbsoluteFile();
        File propertyFile = new File(catalinaBase, "conf\\wordscaster.properties");
        FileInputStream input = new FileInputStream(propertyFile);

//        FileInputStream input = new FileInputStream("wordscaster.properties");

        try
        {
            prop.load(input);
            result = prop.getProperty(propertyName);
        }
        catch(IOException e)
        {
            logger.error("Error message:" + e.getMessage());
            System.out.println("###");
            //throw e;
        }
        return result;
    }

    public static String dbUser() throws IOException
    {
        return readProperty("dbUser");
    }

    public static String dbPassword() throws IOException
    {
        return readProperty("dbPassword");
    }

    public static String dbUrl() throws IOException
    {
        return readProperty("dbUrl");
    }

    public static String jdbcDriver() throws IOException
    {
        return readProperty("jdbcDriver");
    }
}
