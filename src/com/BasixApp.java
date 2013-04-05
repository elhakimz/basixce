package com;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Window;
import org.basix.common.BaseController;
import org.basix.ui.shell.AppShellController;

/**
 * User: abilhakim
 * Date: 3/30/13
 * Time: 4:12 AM
 */
public class BasixApp extends Application implements ApplicationContext.TransactionListener  {

    private static ThreadLocal<BasixApp> currentApp = new ThreadLocal<BasixApp>();

    private Window mainWindow;


    private static final String FNAME = "First Name";
    private static final String LNAME = "Last Name";
    private static final String COMPANY = "Company";
    private static final String[] fieldNames = new String[] { FNAME, LNAME,
            COMPANY, "Mobile Phone", "Work Phone", "Home Phone", "Work Email",
            "Home Email", "Street", "City", "Zip", "State", "Country" };



    private BaseController shellController;

    private BasixApp thisApp;

    private boolean initialized;

    @Override
    public void init() {

        thisApp=this;

        setTheme("runo");
        shellController = new AppShellController();
        Component c = shellController.getView();
        c.setSizeFull();

        mainWindow = new Window("Vaadin Application", (ComponentContainer) c);
        mainWindow.setSizeFull();

        setMainWindow(mainWindow);
        AppFacade.getInstance().setApplication(this);
        shellController.initModules();
        shellController.initLayout();

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
        if ( application == BasixApp.this )
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
        if ( application == BasixApp.this )
        {
            currentApp.set ( null );
            currentApp.remove ();
        }
    }

    public static BasixApp getInstance()
    {
        return currentApp.get();
    }
}
