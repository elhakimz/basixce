package org.basix.metadata.module;

/**
 * User: abilhakim
 * Date: 3/31/13
 * Time: 2:40 PM
 */
public class FunctionTag {

    private String caption;
    private String controller;
    private boolean isSeparator;

    public FunctionTag(String caption, String controller, boolean separator) {
        this.caption = caption;
        this.controller = controller;
        isSeparator = separator;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public boolean isSeparator() {
        return isSeparator;
    }

    public void setSeparator(boolean separator) {
        isSeparator = separator;
    }
}
