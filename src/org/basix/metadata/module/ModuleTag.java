package org.basix.metadata.module;

import java.util.ArrayList;
import java.util.List;

/**
 * User: abilhakim
 * Date: 3/31/13
 * Time: 2:40 PM
 */
public class ModuleTag {

    private String caption;
    private String pack;
    private String role;

    private List<FunctionTag> functionTags = new ArrayList<FunctionTag>();

    public ModuleTag(String caption, String pack) {
        this.caption = caption;
        this.pack = pack;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public List<FunctionTag> getFunctionTags() {
        return functionTags;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
