package org.basix.ui.shell;

import com.vaadin.Application;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import org.basix.common.BaseController;
import org.basix.metadata.module.FunctionTag;
import org.basix.metadata.module.ModuleMetaProcessor;
import org.basix.metadata.module.ModuleTag;
import org.basix.scripting.ScriptRunner;
import org.vaadin.teemu.clara.binder.annotation.UiField;

import java.util.Iterator;
import java.util.List;

/**
 * User: abilhakim
 * Date: 3/31/13
 * Time: 1:35 PM
 */
public class ShellController extends BaseController implements Button.ClickListener {


    @UiField("splitPanel")
    private HorizontalSplitPanel splitPanel;

    @UiField("toolbar")
    private CssLayout toolbar;

    @UiField("right")
    private CssLayout right;

    @UiField("left")
    private CssLayout left;


    @UiField("menu")
    private CssLayout menu;

    @UiField("tabSheet")
    private TabSheet tabSheet;


    private Label title;


    private String fileLocation;

    private ModuleMetaProcessor moduleMetaProcessor ;

    List<ModuleTag> moduleTags;

    Application app;
    public ShellController(Application app){

        this("MainShellUI.xml");
        this.app =app;
        fileLocation = app.getContext().getBaseDirectory().getAbsolutePath()+"/scripts/Modules.xml";
        moduleMetaProcessor=new ModuleMetaProcessor(fileLocation);
        initLayout();
    }

    public ShellController(String xmlView) {
        super(xmlView);
    }

    public void initLayout(){

        toolbar.setWidth("100%");
        toolbar.addStyleName("toolbar-invert");

        right.setSizeUndefined();
        right.addStyleName("right");

        left.setSizeUndefined();
        left.addStyleName("left");

        Label title = new Label("BASIX (A1)");
        title.addStyleName("h1");
        left.addComponent(title);



        final Button.ClickListener moduleClick = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
               populateMenu((String) event.getButton().getData());
            }
        };

        moduleTags = moduleMetaProcessor.getModuleTags();
        for(ModuleTag tag:moduleTags){
            Button b = new NativeButton(tag.getCaption());
            b.setData(tag.getPack());
            b.addListener(moduleClick);
            left.addComponent(b);
        }



        Button b = new Button("Action");
        b.setIcon(new ThemeResource("../runo/icons/16/document.png"));
        b.addStyleName("borderless");
        right.addComponent(b);

        b = new Button("Settings..");

        b.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
               app.getMainWindow().addWindow(new ConfigWindow());
            }
        });

        b.setIcon(new ThemeResource("../runo/icons/16/document.png"));
        b.addStyleName("borderless");
        right.addComponent(b);

        Label text = new Label("Informational text");
        right.addComponent(text);

        splitPanel.addStyleName("small blue white");
        splitPanel.setSplitPosition(20);

        populateMenu(null);



    }


    /**
     * Called when a {@link com.vaadin.ui.Button} has been clicked. A reference to the
     * button is given by {@link com.vaadin.ui.Button.ClickEvent#getButton()}.
     *
     * @param event An event containing information about the click.
     */
    @Override
    public void buttonClick(Button.ClickEvent event) {

    }

    public void setViewController(String caption,String viewController){

        Iterator<Component> iterator = tabSheet.getComponentIterator();
        while (iterator.hasNext()) {
            Component next = iterator.next();
            try {
                if(((VerticalLayout)next).getData().equals(viewController)){
                    tabSheet.setSelectedTab(next);
                    return;
                }
            } catch (NullPointerException e) {

            }
        }

        ScriptRunner runner = ScriptRunner.getInstance();
        String scriptName = viewController.replace(".", "/");
        scriptName="modules/"+scriptName+".groovy";
        System.out.println("scriptname ="+scriptName);
        runner.setScriptFile(scriptName);
        runner.run();

        VerticalLayout vl = (VerticalLayout) runner.getOutput();
        vl.setCaption(caption);
        vl.setData(viewController);
        vl.setSizeFull();


        tabSheet.addTab(vl).setClosable(true);
        tabSheet.setSelectedTab(vl);
    }


    public void populateMenu(String packageName){

        menu.removeAllComponents();

        ModuleTag moduleTag=null;

        if(packageName==null){
             moduleTag = moduleTags.get(0);
        }else {
            for(ModuleTag tag:moduleTags){
                if(tag.getPack().equals(packageName)){
                    moduleTag = tag;
                    break;
                }
            }
        }

        if(moduleTag==null) return;



        final Button.ClickListener menuClick = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ComponentContainer p = (ComponentContainer) event.getButton()
                        .getParent();
                for (Iterator iterator = p.getComponentIterator(); iterator
                        .hasNext();) {
                    ((AbstractComponent) iterator.next())
                            .removeStyleName("selected");
                }
                event.getButton().addStyleName("selected");
                setViewController(event.getButton().getCaption(),(String) event.getButton().getData());
            }
        };



        for(FunctionTag tag:moduleTag.getFunctionTags()){

            if(tag.isSeparator()){

                Label l = new Label(tag.getCaption());
                l.addStyleName("section");
                menu.addComponent(l);

            }else{

                NativeButton nativeButton = new NativeButton(tag.getCaption(),menuClick);
                nativeButton.setData(tag.getController());
                menu.addComponent(nativeButton);


            }

        }


    }

}
