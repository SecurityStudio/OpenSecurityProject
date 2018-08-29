package com.npci.shirotutorial.security.web.filter;

import org.apache.shiro.web.servlet.ShiroFilter;

import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class ShiroFilterActivator extends ShiroFilter {
    private ShiroFilterActivator() {
    }
}
