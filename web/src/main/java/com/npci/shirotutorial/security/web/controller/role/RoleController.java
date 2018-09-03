package com.npci.shirotutorial.security.web.controller.role;

import com.npci.shirotutorial.security.model.entity.Role;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class RoleController implements Serializable {
    private static final long serialVersionUID = 458951882992131862L;

    private Role role;

    public RoleController() {
        role = new Role();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void save(){
        System.out.printf("Saving role" + role.getName());
    }

}
