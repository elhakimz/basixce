package org.basix.ui.shell;

import com.AppFacade;
import com.vaadin.Application;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Runo;
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
 * Date: 4/3/13
 * Time: 5:52 PM
 */
public class AppShellController extends BaseController {

    @UiField("toolbar")
    private HorizontalLayout toolbar;

    @UiField("brand")
    private Label brand;

    @UiField("menu")
    private VerticalLayout menu;

    @UiField("tabSheet")
    private TabSheet tabSheet;

    Application app;

    private String fileLocation;
    private ModuleMetaProcessor moduleMetaProcessor ;
    List<ModuleTag> moduleTags;


    public AppShellController(){
        super("AppShellUI.xml");
    }

    public AppShellController(String xmlView) {
        super(xmlView);
    }

    public void initModules(){
        app= AppFacade.getInstance().getApplication();
        fileLocation = AppFacade.getInstance().getBaseDirectory()+"/scripts/Modules.xml";
        moduleMetaProcessor=new ModuleMetaProcessor(fileLocation);
    }


    public void initLayout(){
        brand.addStyleName(Runo.LABEL_H1);
        final Button.ClickListener moduleClick = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                populateMenu((String) event.getButton().getData());
            }
        };

        moduleTags = moduleMetaProcessor.getModuleTags();
        for(ModuleTag tag:moduleTags){
            String role=tag.getRole();

            if(role!=null && role.equals("ADMIN")){
                continue;
            }

            Button b = new NativeButton(tag.getCaption());
            b.setData(tag.getPack());
            b.addListener(moduleClick);
            toolbar.addComponent(b);
        }

    }


    public void populateMenu(String packageName){

        menu.removeAllComponents();

        ModuleTag moduleTag=null;


        for(ModuleTag tag:moduleTags){
            if(tag.getPack()!=null && tag.getPack().equals(packageName)){
                moduleTag=tag;
                break;
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
                e.printStackTrace();
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




}
