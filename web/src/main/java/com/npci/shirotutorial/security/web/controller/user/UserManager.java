package com.npci.shirotutorial.security.web.controller.user;

import com.npci.shirotutorial.security.model.entity.User;
import com.npci.shirotutorial.security.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UserManager implements Serializable {
    private static final long serialVersionUID = -1061037211722294630L;

    private List<User> users;

    @EJB
    private UserService service;

    @PostConstruct
    private void init(){
        users = service.findAll();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
