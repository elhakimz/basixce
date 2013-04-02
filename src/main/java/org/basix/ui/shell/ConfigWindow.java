package org.basix.ui.shell;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;

/**
 * User: abilhakim
 * Date: 4/2/13
 * Time: 7:09 AM
 */
public class ConfigWindow extends Window {

    TabSheet tabSheet = new TabSheet();
    Form dbForm = new Form();
    Form appForm = new Form();

    DbConfig dbConfig = new DbConfig();
    AppConfig appConfig = new AppConfig();

    BeanItem<DbConfig> biDb = new BeanItem<DbConfig>(dbConfig);
    BeanItem<AppConfig> biApp = new BeanItem<AppConfig>(appConfig);

    HorizontalLayout hl = new HorizontalLayout();
    Button saveButton = new Button("Save");
    Button cancelButton = new Button("Cancel");

    public ConfigWindow(){
        setCaption("Configuration");
        setModal(true);
        setWidth("400px");
        setHeight("400px");

        VerticalLayout vl = (VerticalLayout) getContent();
        vl.setSizeFull();
        vl.setSpacing(true);
        vl.addComponent(tabSheet);
        vl.addComponent(hl);
        vl.setExpandRatio(tabSheet, (float) 1.0);

        tabSheet.setSizeFull();
        tabSheet.addTab(createDbConfigForm());
        tabSheet.addTab(createAppConfigForm());

        hl.setWidth("100%");

        saveButton.addStyleName("primary");

        hl.addComponent(cancelButton);
        hl.addComponent(saveButton);

        hl.setComponentAlignment(cancelButton,Alignment.MIDDLE_LEFT);
        hl.setComponentAlignment(saveButton,Alignment.MIDDLE_RIGHT);


        center();
    }

    private VerticalLayout createDbConfigForm(){
      VerticalLayout verticalLayout=new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setCaption("Database");
        verticalLayout.addComponent(dbForm);
        dbForm.setItemDataSource(biDb);
        dbForm.setSizeFull();
        verticalLayout.addComponent(dbForm);
        return verticalLayout;
    }


    private VerticalLayout createAppConfigForm(){
        VerticalLayout verticalLayout=new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setCaption("Application");
        appForm.setItemDataSource(biApp);
        appForm.setSizeFull();
        verticalLayout.addComponent(appForm);
        return verticalLayout;
    }

    private void saveProperties(){

    }

    /**
     * DbConfig
     *
     */
    public class DbConfig{
        String jdbcDriver;
        String dbUrl;
        String userName;
        String password;

        public String getJdbcDriver() {
            return jdbcDriver;
        }

        public void setJdbcDriver(String jdbcDriver) {
            this.jdbcDriver = jdbcDriver;
        }

        public String getDbUrl() {
            return dbUrl;
        }

        public void setDbUrl(String dbUrl) {
            this.dbUrl = dbUrl;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    /**
     * App Config
     */
    public class AppConfig{

    }

}
