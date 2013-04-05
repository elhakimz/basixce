package org.basix.common.model;

/**
 * User: abilhakim
 * Date: 4/4/13
 * Time: 11:01 AM
 */
public class User {

    private String userName="";
    private String password="";
    private String roleName="";


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
