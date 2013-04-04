package org.basix.ui.shell;

import com.vaadin.Application;
import org.basix.common.BaseController;

/**
 * User: abilhakim
 * Date: 4/3/13
 * Time: 5:52 PM
 */
public class AppShellController extends BaseController {

    Application app;
    public AppShellController(){
        super("AppShellUI.xml");

    }

    public AppShellController(String xmlView) {
        super(xmlView);
    }
}
