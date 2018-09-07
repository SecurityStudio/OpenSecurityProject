package com.npci.shirotutorial.security.web.controller.auth;

import com.npci.shirotutorial.security.util.ResourceMgr;
import com.npci.shirotutorial.security.web.security.WebPages;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
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
        Subject currentUser = SecurityUtils.getSubject();
        boolean justLogged = !currentUser.isAuthenticated();

        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(rememberMe);

            currentUser.login(token);
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + WebPages.HOME_URL);
            // return "/index?faces-redirect=true";

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
            // throw new IncorrectCredentialsException("Unknown user, please try again");
        }

        // return null;
    }

    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            SecurityUtils.getSubject().logout();
            String logoutMessage = ResourceMgr.getMessageBundle().getString("logout.message");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, logoutMessage, ""));
        }
    }

    public Subject getCurrentUser() {
        return SecurityUtils.getSubject();
    }

    public boolean isAuthenticated() {
        return getCurrentUser().isAuthenticated();
    }
}
