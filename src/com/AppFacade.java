package com;

import com.vaadin.Application;
import org.basix.common.pattern.IFacade;

/**
 * User: abilhakim
 * Date: 4/4/13
 * Time: 3:33 AM
 */
public class AppFacade implements IFacade {

     private static AppFacade instance;
     private Application application;

     private AppFacade(){

     }

     public static AppFacade getInstance(){
       if(instance==null) instance = new AppFacade();
       return instance;
     }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public  String getBaseDirectory() {
        return this.application.getContext().getBaseDirectory().getAbsolutePath();
    }
}
