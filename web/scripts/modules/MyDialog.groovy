import com.vaadin.ui.Button
import com.vaadin.ui.Label
import org.basix.common.BaseController
import org.vaadin.teemu.clara.binder.annotation.UiField
import org.vaadin.teemu.clara.binder.annotation.UiHandler

/**
 * User: abilhakim
 * Date: 3/31/13
 * Time: 3:43 AM
 */

parameters = [:]

def controller = new MyDialogController("MyDialogUI.xml");
output = controller.view;

/**
 * Controller class
 */
class MyDialogController extends BaseController {

    /**
     * UI fields declared by ID
     */
    @UiField("label")
    Label label;

    @UiField("button")
    Button button;

    /**
     * constructor
     * @param xmlView
     */
    MyDialogController(String xmlView) {
       super(xmlView)
    }


    /**
     * event handler
     * @param event
     */
    @UiHandler("button")
    onButtonClick(Button.ClickEvent event){
      modules.base.Hello hello = new Hello()
      view.application.mainWindow.showNotification(hello.helloString);
    }

}