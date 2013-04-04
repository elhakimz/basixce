package com;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Window;
import org.basix.common.pattern.ICommand;
import org.basix.ui.shell.LoginWindow;
import org.basix.ui.shell.ShellController;

import java.util.Map;

/**
 * User: abilhakim
 * Date: 4/3/13
 * Time: 6:16 PM
 */
public class BasixAdminApp  extends Application implements ApplicationContext.TransactionListener {
    private static ThreadLocal<BasixAdminApp> currentApp = new ThreadLocal<BasixAdminApp>();
    private Window mainWindow;


    private ShellController shellController;
    private boolean initialized=false;
    private boolean  isLogin;

    private LoginWindow loginWindow = new LoginWindow();

    /**
     * <p>
     * Main initializer of the application. The <code>init</code> method is
     * called by the framework when the application is started, and it should
     * perform whatever initialization operations the application needs, such as
     * creating windows and adding components to them.
     * </p>
     */
    @Override
    public void init() {
        setTheme("reindeermods");
        shellController = new ShellController();
        Component c = shellController.getView();
        c.setSizeFull();

        mainWindow = new Window("Vaadin Application", (ComponentContainer) c);
        mainWindow.setSizeFull();
        setMainWindow(mainWindow);

        AppFacade.getInstance().setApplication(this);
        shellController.initModules();
        shellController.initLayout();


        loginWindow.setOnLogin(new ICommand() {
            @Override
            public void execute(Map executeParameter) {

              mainWindow.removeWindow(loginWindow);

            }
        });

        mainWindow.addWindow(loginWindow);


    }



    private void doInitialize(){
       if(initialized) return;

       initialized=true;
    }



    /**
     * Invoked at the beginning of every transaction.
     * <p/>
     * The transaction is linked to the context, not the application so if
     * you have multiple applications running in the same context you need
     * to check that the request is associated with the application you are
     * interested in. This can be done looking at the application parameter.
     *
     * @param application     the Application object.
     * @param transactionData the Data identifying the transaction.
     */
    @Override
    public void transactionStart(Application application, Object transactionData) {
        if ( application == BasixAdminApp.this )
        {
            currentApp.set ( this );
            doInitialize();

        }
    }

    /**
     * Invoked at the end of every transaction.
     * <p/>
     * The transaction is linked to the context, not the application so if
     * you have multiple applications running in the same context you need
     * to check that the request is associated with the application you are
     * interested in. This can be done looking at the application parameter.
     *
     * @param application      the Application object.
     * @param transactionData the Data identifying the transaction.
     */
    @Override
    public void transactionEnd(Application application, Object transactionData) {

        if ( application == BasixAdminApp.this )
        {
            currentApp.set ( null );
            currentApp.remove ();
        }
    }

    public static BasixAdminApp getInstance()
    {
        return currentApp.get();
    }
}
