package com;

import com.vaadin.Application;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;
import org.basix.scripting.ScriptRunner;
import org.basix.ui.shell.ShellController;

/**
 * User: abilhakim
 * Date: 3/30/13
 * Time: 4:12 AM
 */
public class BasixApp extends Application {

    //@UiField("contactList")
    private Table contactList;

    //@UiField("newDialog")
    private Button newDialog;

    //@UiField("form")
    private Form form;

    private Window mainWindow;


    private static final String FNAME = "First Name";
    private static final String LNAME = "Last Name";
    private static final String COMPANY = "Company";
    private static final String[] fieldNames = new String[] { FNAME, LNAME,
            COMPANY, "Mobile Phone", "Work Phone", "Home Phone", "Work Email",
            "Home Email", "Street", "City", "Zip", "State", "Country" };

    private static String baseDirectory;

    private ShellController shellController;

    @Override
    public void init() {
        baseDirectory = this.getContext().getBaseDirectory().getAbsolutePath();
        setTheme("reindeermods");
        shellController=new ShellController(this);
        Component c = shellController.getView();
        c.setSizeFull();

        mainWindow = new Window("Vaadin Application", (ComponentContainer) c);
        mainWindow.setSizeFull();
        setMainWindow(mainWindow);
//        initContactList();
//        initEditor();
    }


    private void initContactList() {
        contactList.setVisibleColumns(new String[] { FNAME, LNAME, COMPANY });
    }


    private void initEditor() {
        /* User interface can be created dynamically to reflect underlying data. */


        /*
         * Data can be buffered in the user interface. When doing so, commit()
         * writes the changes to the data source. Here we choose to write the
         * changes automatically without calling commit().
         */

    }

    public static String getBaseDirectory() {
        return baseDirectory;
    }

    //@UiHandler("newDialog")
    public void openWindow(Button.ClickEvent event){

        ScriptRunner runner = ScriptRunner.getInstance();
        runner.setScriptFile("MyDialog.groovy");
        runner.run();
        Component c= (Component) runner.getOutput();

        Window w = new Window("Hello");
        w.getContent().addComponent(c);
        mainWindow.addWindow(w);

       //mainWindow.showNotification("Hello");
    }

    //@UiHandler("contactList")
    public void onTableSelect(Property.ValueChangeEvent event){

        Item item = contactList.getItem(contactList.getValue());
        Contact contact = new Contact();
        contact.setFirstName((String) item.getItemProperty(FNAME).getValue());
        contact.setLastName((String) item.getItemProperty(LNAME).getValue());
        contact.setCompany((String) item.getItemProperty(COMPANY).getValue());
        BeanItem bi = new BeanItem(contact);
        System.out.println("Hello");
        form.setItemDataSource(bi);
        form.setVisibleItemProperties(new String[]{"firstName","lastName","company"});
    }

    /*
     * Generate some in-memory example data to play with. In a real application
     * we could be using SQLContainer, JPAContainer or some other to persist the
     * data.
     */
    //@UiDataSource("contactList")
    public IndexedContainer createDummyDatasource() {
        IndexedContainer ic = new IndexedContainer();

        for (String p : fieldNames) {
            ic.addContainerProperty(p, String.class, "");
        }

        /* Create dummy data by randomly combining first and last names */
        String[] fnames = { "Peter", "Alice", "Joshua", "Mike", "Olivia",
                "Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene",
                "Lisa", "Marge" };
        String[] lnames = { "Smith", "Gordon", "Simpson", "Brown", "Clavel",
                "Simons", "Verne", "Scott", "Allison", "Gates", "Rowling",
                "Barks", "Ross", "Schneider", "Tate" };
        for (int i = 0; i < 1000; i++) {
            Object id = ic.addItem();
            ic.getContainerProperty(id, FNAME).setValue(
                    fnames[(int) (fnames.length * Math.random())]);
            ic.getContainerProperty(id, LNAME).setValue(
                    lnames[(int) (lnames.length * Math.random())]);
        }

        return ic;
    }

    /*
     * A custom filter for searching names and companies in the
     * contactContainer.
     */
    private static class ContactFilter implements Container.Filter {
        private String needle;

        public ContactFilter(String needle) {
            this.needle = needle.toLowerCase();
        }

        public boolean passesFilter(Object itemId, Item item) {
            String haystack = ("" + item.getItemProperty(FNAME).getValue()
                    + item.getItemProperty(LNAME).getValue() + item
                    .getItemProperty(COMPANY).getValue()).toLowerCase();
            return haystack.contains(needle);
        }

        public boolean appliesToProperty(Object id) {
            return true;
        }
    }

    public class Contact{
        private String firstName;
        private String lastName;
        private String company;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }
    }
}
