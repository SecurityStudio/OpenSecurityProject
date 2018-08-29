package com.npci.shirotutorial.security.web.security;

import com.npci.shirotutorial.security.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SecurityRealm extends AuthorizingRealm {

    Logger logger;

    UserService userService;

    public SecurityRealm() {
        super();
        this.logger = LoggerFactory.getLogger(SecurityRealm.class.getName());

        try {
            InitialContext ctx = new InitialContext();
            this.userService = (UserService) ctx.lookup("java:global/ShiroTutorial/UserService");
        } catch (NamingException e) {
            logger.warn("Cannot do the JNDI Lookup to instantiate the User service : ", e);
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
