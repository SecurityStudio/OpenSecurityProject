package com.npci.shirotutorial.security.web.security;

import com.npci.shirotutorial.security.model.entity.User;
import com.npci.shirotutorial.security.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class SecurityRealm extends AuthorizingRealm {

    @Inject
    Logger logger;

    @Inject
    UserService userService;

    public SecurityRealm() {
        super();
        setAuthenticationCachingEnabled(Boolean.TRUE);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // identify account to log to
        UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
        String username = userPassToken.getUsername();

        if (username == null) {
            logger.warn("Username is null.");
            return null;
        }

        // read password hash and salt from db
        User user = this.userService.findByUsername(username);

        if (user == null) {
            logger.warn("No account found for user [" + username + "]");
            throw new IncorrectCredentialsException();
        }

        return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        String username = (String) getAvailablePrincipal(principals);

        Set<String> roleNames = new HashSet<>();
        roleNames.add(this.userService.findByUsername(username).getRoles().iterator().next().getName());

        AuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        /**
         * If you want to do Permission Based authorization, you can grab the Permissions List associated to your user:
         * For example:
         * Set<String> permissions = new HashSet<>();
         * permissions.add(this.userService.findByUsername(username).getRole().getPermissions());
         * ((SimpleAuthorizationInfo)info).setStringPermissions(permissions);
         */
        return info;
    }
}
