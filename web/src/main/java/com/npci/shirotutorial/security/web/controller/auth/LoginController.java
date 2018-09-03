package com.npci.shirotutorial.security.web.controller.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class LoginController implements Serializable {
    private static final long serialVersionUID = -9111779500857948131L;

    private String username;
    private String password;
    private boolean rememberMe;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void login() {

        // Yani user login taze darad login mikonad
        // agar login bashad va be safheye login biayad in meghdar false ast
        boolean justLogged = !SecurityUtils.getSubject().isAuthenticated();

        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, rememberMe));
        } catch (Exception e) {
            throw new IncorrectCredentialsException("Unknown user, please try again");
        }
    }
}
