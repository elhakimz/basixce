package org.basix.ui.shell;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.basix.common.model.User;
import org.basix.common.pattern.ICommand;

import java.util.HashMap;
import java.util.Map;

/**
 * User: abilhakim
 * Date: 4/4/13
 * Time: 10:51 AM
 */
public class LoginWindow extends Window {

    private User user=new User();
    private BeanItem<User> biUser=new BeanItem<User>(user);
    private Form form = new Form();
    private HorizontalLayout horizontalLayout=new HorizontalLayout();
    private Button btnProceed=new Button("Proceed");

    private ICommand onLogin;

    public LoginWindow(){
        setModal(true);
        center();
        setClosable(false);
        setResizable(false);
        setWidth("250px");
        setHeight("200px");

        setCaption("Login Administrator");
        form.setItemDataSource(biUser);
        form.setVisibleItemProperties(new String[]{"userName","password"});
        ((TextField)form.getField("password")).setSecret(true);

        VerticalLayout vl = (VerticalLayout) getContent();
        vl.setSpacing(true);
        vl.addComponent(form);
        vl.addComponent(horizontalLayout);
        horizontalLayout.addComponent(btnProceed);
        horizontalLayout.setWidth("100%");
        horizontalLayout.setComponentAlignment(btnProceed,Alignment.MIDDLE_CENTER);

        btnProceed.setStyleName("primary");
        btnProceed.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                doLogin();
            }
        });


    }

    private void doLogin(){

        form.commit();

        Map map = new HashMap();

        map.put("userName",user.getUserName());
        map.put("password",user.getPassword());

        onLogin.execute(map);

    }

    public void setOnLogin(ICommand onLogin) {
        this.onLogin = onLogin;
    }
}
