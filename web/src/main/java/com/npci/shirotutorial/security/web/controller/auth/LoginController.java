package com.npci.shirotutorial.security.web.controller.auth;

import com.npci.shirotutorial.security.util.ResourceMgr;
import com.npci.shirotutorial.security.web.HttpBasic;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.slf4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.MessageFormat;

@Named
@RequestScoped
public class LoginController extends HttpBasic implements Serializable {
    private static final long serialVersionUID = -9111779500857948131L;

    public static final String SAVED_REQUEST_KEY = "shiroSavedRequest";

    @Inject
    Logger logger;

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

    public String login() {

        // Yani user login taze darad login mikonad
        // agar login bashad va be safheye login biayad in meghdar false ast
        Subject currentUser = SecurityUtils.getSubject();
        boolean justLogged = !currentUser.isAuthenticated();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(rememberMe);

            logger.info("Login started");
            currentUser.login(token);

            MessageFormat message = new MessageFormat("User {0} logged in successfully");
            logger.info(message.format(new Object[]{token.getUsername()}));

            SavedRequest savedRequest = getSavedRequest();

            if (savedRequest != null) {
                logger.info("Redirecting to saved request (URI): " + savedRequest.getRequestURI());
                logger.info("Redirecting to saved request (URL): " + savedRequest.getRequestUrl());
                ec.redirect(savedRequest.getRequestURI());
            }

            // ec.redirect(ec.getRequestContextPath() + WebPages.HOME_URL);
            return "/index?faces-redirect=true";

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
            // throw new IncorrectCredentialsException("Unknown user, please try again");
        }

        return "/login?faces-redirect=true";
    }

    private SavedRequest getSavedRequest() {
        SavedRequest savedRequest = null;

        Subject currentUser = SecurityUtils.getSubject();

        Session session = currentUser.getSession(false);
        if (session != null) {
            savedRequest = (SavedRequest) session.getAttribute(SAVED_REQUEST_KEY);
        }

        if (savedRequest != null) {
            currentUser.getSession().removeAttribute(SAVED_REQUEST_KEY);
        }

        return savedRequest;
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
