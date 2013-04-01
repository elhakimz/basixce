import com.vaadin.ui.HorizontalSplitPanel
import com.vaadin.ui.Tree
import com.vaadin.ui.VerticalLayout
import org.basix.common.BaseController
import org.vaadin.aceeditor.AceSuggestibleEditor
import org.vaadin.aceeditor.gwt.ace.AceMode
import org.vaadin.aceeditor.gwt.ace.AceTheme
import org.vaadin.teemu.clara.binder.annotation.UiField


parameters = [:]

def controller = new EditorController("modules/webtools/EditorUI.xml");
output = controller.view;


class EditorController extends BaseController{




    private Tree tree=new Tree()

    @UiField("split")
    private HorizontalSplitPanel split;

    private AceSuggestibleEditor editor=new AceSuggestibleEditor()

    EditorController(String xmlView) {
        super(xmlView)

        initEditor()

    }

    void initEditor(){
     split.firstComponent = tree
     split.secondComponent = editor
     editor.setSizeFull()
     tree.setSizeFull()
     split.setSplitPosition(20)
     editor.setTheme(AceTheme.eclipse)
     editor.setMode(AceMode.groovy)
    }
}