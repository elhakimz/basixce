package org.basix.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Abiel
 *         Date: 4/4/13
 *         Time: 2:21 PM
 */
public class AppConstant {


    public static final String PLATFORM=getProp("app.platform");
    public static final String DB_DRIVER=getProp("db.driver");
    public static final String DB_URL=getProp("db.url");
    public static final String DB_USER=getProp("db.user");
    public static final String DB_PASSWORD=getProp("db.password");



    private final Properties properties = new Properties();

    private static AppConstant __instance;


    private AppConstant(){
        try {

            properties.load(this.getClass().getClassLoader().getResourceAsStream("Application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AppConstant getInstance(){
        if(__instance==null){
            __instance = new AppConstant();
        }
        return __instance;
    }

    String getProperty(String prop){
        return properties.getProperty(prop);
    }

    public static String getProp(String prop){
        return AppConstant.getInstance().getProperty(prop);
    }



}
