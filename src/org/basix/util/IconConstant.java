package org.basix.util;

import com.vaadin.terminal.ThemeResource;

import java.io.IOException;
import java.util.Properties;

/**
 * User: abilhakim
 * Date: 4/3/13
 * Time: 12:00 PM
 */
public class IconConstant {

    private final Properties properties = new Properties();

    private static IconConstant __instance;

    private IconConstant(){
        try {

            properties.load(this.getClass().getClassLoader().getResourceAsStream("Icons.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static IconConstant getInstance(){
        if(__instance==null){
            __instance = new IconConstant();
        }
        return __instance;
    }

    String getProperty(String prop){
        return properties.getProperty(prop);
    }

    public static String getProp(String prop){
        return IconConstant.getInstance().getProperty(prop);
    }

    public ThemeResource getIcon(String prop){
        String s = getProperty("icon.directory");
        String s2 = getProperty(prop);
        try {
            ThemeResource tr = new ThemeResource(s+s2);
            return tr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
