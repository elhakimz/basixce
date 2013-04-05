package org.basix.ui.comp;

import com.vaadin.ui.*;
import org.basix.common.pattern.ICommand;
import org.basix.util.IconConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Abiel
 *         Date: 4/4/13
 *         Time: 8:51 PM
 */
public class NewFileDialog extends Window {

    private Label label = new Label("File Name");
    private TextField txtName = new TextField();
    private Select selType = new Select();
    private Button btnCreate=new Button("Create");
    private Button btnCancel=new Button("Cancel");

    private ICommand onCreateCommand;

    /**
     * constructor
     */
    public NewFileDialog(){


        VerticalLayout vl = (VerticalLayout) getContent();

        HorizontalLayout hl1 = new HorizontalLayout();
        hl1.setMargin(true);
        hl1.setSpacing(true);
        label.setWidth("100px");
        hl1.addComponent(label);
        txtName.setInputPrompt("File name with extension");
        hl1.addComponent(txtName);
        hl1.setWidth("100%");
        HorizontalLayout hl2 = new HorizontalLayout();

        btnCreate.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if(onCreateCommand!=null){
                    Map map=new HashMap();
                    map.put("value",txtName.getValue());
                    onCreateCommand.execute(map);
                }

                close();
            }
        });

        btnCancel.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        });


        hl2.addComponent(btnCreate);
        hl2.addComponent(btnCancel);
        hl2.setComponentAlignment(btnCancel,Alignment.MIDDLE_RIGHT);

        hl2.setMargin(true);
        hl2.setWidth("100%");
        vl.setMargin(true);
        vl.setSpacing(true);
        vl.addComponent(hl1);
        vl.addComponent(hl2);
        setModal(true);
        setWidth("320px");
        setHeight("200px");
        setResizable(false);
        setCaption("New File Dialog");
        center();

    }

    public void setOnCreateCommand(ICommand onCreateCommand) {
        this.onCreateCommand = onCreateCommand;
    }
}