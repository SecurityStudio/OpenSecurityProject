package com.npci.shirotutorial.security.web.controller.user;

import com.npci.shirotutorial.security.model.entity.User;
import com.npci.shirotutorial.security.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;

@Named
@ViewScoped
public class UserController implements Serializable {

    private static final long serialVersionUID = -1197940889642999596L;

    private Long id;

    private User user;

    @EJB
    private UserService service;

    public UserController() {
        System.out.println("UserController -> ctor");
    }

    @PostConstruct
    private void init() {
        System.out.printf("UserController -> init");
        user = new User();
    }

    public void onLoad() {
        if (id != null) {
            user = service.find(id);
            if (user == null) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().dispatch("index");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // afterLoad();
    }

    public void save() {
        service.save(user);
        String message = "User {0} added successfully";
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(new MessageFormat(message).format(new Object[]{user.getId()}))
        );
    }

    public void edit() {
        user = service.edit(user);
        String message = "User {0} edited successfully to version {1,date,full} {1,time,full}";
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(new MessageFormat(message).format(new Object[]{user.getId(), user.getVersion()}))
        );
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
