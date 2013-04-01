package org.basix.common;

import com.vaadin.ui.Component;
import org.vaadin.teemu.clara.Clara;

/**
 * User: abilhakim
 * Date: 3/31/13
 * Time: 11:43 AM
 */
public class BaseController extends AbstractController {

    private String xmlView;
    protected Component view;

    public BaseController(final String xmlView){
      this.xmlView = xmlView;
      this.view =Clara.create(xmlView,this);

    }

    public Component getView() {
        return view;
    }
}
