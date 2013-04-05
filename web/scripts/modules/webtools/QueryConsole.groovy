import com.vaadin.data.Container
import com.vaadin.data.Item
import com.vaadin.data.util.IndexedContainer
import com.vaadin.ui.*
import com.vaadin.ui.themes.Reindeer
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.basix.common.BaseController
import org.basix.scripting.GSql
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
    private Button btnRun

    private AceSqlEditor editor=new AceSqlEditor()
    private TabSheet tabSheet=new TabSheet()
    private Tree tree = new Tree();
    HorizontalLayout hl = new HorizontalLayout()

    Sql sql



    QueryConsoleController(String xmlView) {
        super(xmlView)
        try {
            sql =GSql.sql
        } catch (e) {
            view.window.showNotification(e.message, Window.Notification.TYPE_ERROR_MESSAGE)
        }
        initLayout()

    }

    void initLayout(){

        tabSheet.addStyleName(Reindeer.TABSHEET_SMALL)
        tabSheet.setSizeFull()


        editor.setSizeFull()
        editor.setValue(" SELECT * FROM DUAL")
        hl.addComponent(editor)

        tree.setSizeFull()

        hl.addComponent(tree)
        tree.setWidth("200px")
        hl.setSizeFull()
        hl.setExpandRatio(editor,0.8)

        split.firstComponent=hl
        split.secondComponent=tabSheet
        populateMetadata()
    }

   @UiHandler("btnRun")
    void runQuery(Button.ClickEvent event){


        Container result = processQuery(editor.value)

        Table table = new Table()

        if (result!=null){

            table.setContainerDataSource(result)
            table.caption=" Result "
            table.setSizeFull()
            tabSheet.addTab(table).closable =true
            tabSheet.selectedTab=table
        }
    }


    def processQuery(String query){
     Container container = new IndexedContainer();

      if (query.toLowerCase().trim().startsWith("select")){

           boolean defined=false

           try {
               sql.eachRow(query){

                   if(!defined){
                       ((GroovyRowResult)it.toRowResult()).keySet().each {k ->
                          container.addContainerProperty(k,String.class,null)
                       }
                   defined = true
                   }

                   Item newItem = container.getItem(container.addItem());
                   ((GroovyRowResult)it.toRowResult()).keySet().each {k ->
                      newItem.getItemProperty(k).setValue(((GroovyRowResult)it.toRowResult())[k])
                   }
               }
           } catch (e) {
               view.window.showNotification("Error "+e.message,Window.Notification.TYPE_WARNING_MESSAGE)
               return null;
           }
      }
        return container
    }

    def populateMetadata(){

        def tables = []

        String[] types= ["TABLE"]
        def resultSet = sql.connection.metaData.getTables (null, null, "FND%", types)

        println "resultSet.fetchSize = $resultSet.fetchSize"

        while(resultSet.next()) {
            String t = resultSet.getString ("TABLE_NAME")
            tree.addItem(t)
            println "t = $t"
        }

        tree.addListener(new Tree.ExpandListener() {
            @Override
            void nodeExpand(Tree.ExpandEvent event) {

            }
        })


    }

}