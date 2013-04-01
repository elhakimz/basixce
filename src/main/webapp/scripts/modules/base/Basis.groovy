import com.vaadin.data.util.BeanItemContainer
import com.vaadin.ui.Button
import com.vaadin.ui.Table
import org.basix.common.BaseController
import org.vaadin.teemu.clara.binder.annotation.UiField
import org.vaadin.teemu.clara.binder.annotation.UiHandler

parameters = [:]

def controller = new BasisController("modules/base/BaseUI.xml");
output = controller.view;

class BasisController extends BaseController{

    @UiField("button")
    private Button button

    @UiField("table")
    private Table table
    List<Foo> foos = new ArrayList<Foo>();

    BasisController(String xmlView) {
        super(xmlView)
        BeanItemContainer<Foo> bic = new BeanItemContainer<Foo>(Foo.class)
        Foo foo = new Foo()
        foo.id="1";foo.name="John Doe";foo.birthPlace="jakarta";foo.birthDay=new Date()
        bic.addItem(foo)
        foo.id="2";foo.name="John Does";foo.birthPlace="bandung";foo.birthDay=new Date()
        bic.addItem(foo)
        table.selectable = true
        table.containerDataSource = bic
        table.visibleColumns = ["id","name","birthDay","birthPlace"]
    }

    @UiHandler("button")
    void onClick(Button.ClickEvent event){
       view.application.mainWindow.showNotification("click")
    }

    class Foo{
        String id
        String name
        Date birthDay
        String birthPlace
    }
}
