package edu.eci.ieti.takeiteasysk.model;

public class Role{

    private  String id;

    private ERole role;

    public Role() {

    }
    public String getAuthority() {
        return this.role.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

}
