import com.AppFacade
import com.BasixApp
import com.vaadin.Application
import com.vaadin.ui.Component
import com.vaadin.ui.HorizontalSplitPanel
import com.vaadin.ui.TabSheet
import com.vaadin.ui.themes.Reindeer
import org.basix.common.BaseController
import org.basix.common.pattern.ICommand
import org.basix.ui.comp.AceGroovyEditor
import org.basix.ui.comp.AceTextEditor
import org.basix.ui.comp.AceXmlEditor
import org.basix.ui.comp.DirectoryTree
import org.vaadin.aceeditor.AceEditor
import org.vaadin.teemu.clara.binder.annotation.UiField

parameters = [:]

def controller = new EditorController("modules/webtools/EditorUI.xml", null );
output = controller.view;


class EditorController extends BaseController {

    private DirectoryTree tree

    @UiField("split")
    private HorizontalSplitPanel split;

    private String currentFile

    private TabSheet tabSheet = new TabSheet()

    EditorController(String xmlView, Application app) {
        super(xmlView)
        tree = new DirectoryTree(AppFacade.instance.baseDirectory+"/scripts")
        initEditor()

    }

    void initEditor() {
        split.firstComponent = tree
        split.secondComponent = tabSheet
        tabSheet.setSizeFull()
        tabSheet.setImmediate(true)
        tabSheet.addStyleName(Reindeer.TABSHEET_SMALL)
        tree.setSizeFull()
        split.setSplitPosition(20)
        tree.onSelectItem = new ICommand() {

            @Override
            void execute(Map executeParameter) {
                loadNewDoc(executeParameter["value"])
            }
        }

        tabSheet.closeHandler = new TabSheet.CloseHandler() {
            @Override
            public void onTabClose(TabSheet tabsheet, Component tabContent) {
                onTabCloseHandler(tabContent);
            }
        };


        tabSheet.addListener(new TabSheet.SelectedTabChangeListener() {
            @Override
            public void selectedTabChange(TabSheet.SelectedTabChangeEvent event) {
                TabSheet tabsheet = event.getTabSheet();
                Component cmp=tabsheet.selectedTab
                if (cmp != null) {
                    currentFile =  ((AceEditor)cmp).data
                }
            }
        });

    }

    /**
     * load new document
     */
    private void loadNewDoc(String sDocFile) {

        currentFile = sDocFile;

        Iterator<Component> iter = tabSheet.componentIterator
        while (iter.hasNext()) {
            AceEditor ed = iter.next() as AceEditor
            println ed.data
            if (ed.data == sDocFile ){
                tabSheet.selectedTab = ed
                return
            }

        }


        final AceEditor editor
        if (currentFile.endsWith(".groovy")){
            editor=new AceGroovyEditor()
        }else if (currentFile.endsWith(".xml")){
            editor=new AceXmlEditor()
        }else{
            editor=new AceTextEditor()
        }

        int n = sDocFile.lastIndexOf("\\") + 1;

        if (n < 1) {
            n = sDocFile.lastIndexOf("/") + 1;
        }

        String title = sDocFile.substring(n);

        String value = new File(currentFile).text
        editor.data = currentFile
        editor.setValue(value);
        editor.setSizeFull();

        TabSheet.Tab tab = tabSheet.addTab(editor, title);
        tab.setClosable(true)
        tab.description=currentFile
        tabSheet.setSelectedTab(tab)

    }



    private void onTabCloseHandler(final Component tabContent){
        println "closing tabContent "
        try {
            TabSheet.Tab tab = tabSheet.getTab(tabContent)
            tabSheet.removeTab(tab)
        } catch (e) {

        }

    }


}