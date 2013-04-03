import com.vaadin.ui.*
import org.basix.common.BaseController
import org.basix.ui.comp.AceSqlEditor
import org.vaadin.teemu.clara.binder.annotation.UiField
import org.vaadin.teemu.clara.binder.annotation.UiHandler

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

    @UiField("btnRun")
    private NativeButton btnRun

    private AceSqlEditor editor=new AceSqlEditor()
    private TabSheet tabSheet=new TabSheet()

    QueryConsoleController(String xmlView) {
        super(xmlView)
        initLayout()
    }

    void initLayout(){
        editor.setSizeFull()

        editor.setValue("/* Enter Query Here */\r\n SELECT * FROM DUAL")
        split.firstComponent=editor
        split.setSecondComponent(tabSheet)

    }

   @UiHandler("btnRun")
    void runQuery(Button.ClickEvent event){
        Table table = new Table()
        table.caption="Result"
        table.setSizeFull()
        tabSheet.addTab(table).closable =true
    }


    void processSql(String sql){

    }



}