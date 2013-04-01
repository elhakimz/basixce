import com.vaadin.ui.TabSheet
import com.vaadin.ui.VerticalSplitPanel
import org.basix.common.BaseController
import org.vaadin.aceeditor.AceSuggestibleEditor
import org.vaadin.aceeditor.gwt.ace.AceMode
import org.vaadin.teemu.clara.binder.annotation.UiField

/**
 * User: abilhakim
 * Date: 4/1/13
 * Time: 11:17 AM
 */


parameters = [:]

def controller = new QueryConsoleController("modules/webtools/QueryConsoleUI.xml");
output = controller.view;

class QueryConsoleController extends BaseController{


    @UiField("split")
    private VerticalSplitPanel split;

    private AceSuggestibleEditor editor=new AceSuggestibleEditor()
    private TabSheet tabSheet=new TabSheet()

    QueryConsoleController(String xmlView) {
        super(xmlView)
        initLayout()
    }

    void initLayout(){
        editor.setSizeFull()
        editor.setMode(AceMode.sql)
        editor.setValue("/* Enter Query Here */\r\n SELECT * FROM DUAL")
        split.firstComponent=editor
        split.setSecondComponent(tabSheet)

    }

    void run(){

    }

}