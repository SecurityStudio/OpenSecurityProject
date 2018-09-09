package com.npci.shirotutorial.security.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class HttpBasic {
    protected Response getRedirectResponse(String requestUrl, HttpServletRequest request) {
        String appName = request.getContextPath();
        String baseUrl = request.getRequestURL().toString().split(request.getRequestURI())[0] + appName;
        try {
            if (requestUrl.split(appName).length > 1) {
                baseUrl += requestUrl.split(appName)[1];
            } else {
                baseUrl += requestUrl;
            }
            return Response.seeOther(new URI(baseUrl)).build();
        } catch (URISyntaxException e) {
            // e.printStackTrace();
            // logger.warning("URL redirection failed for request[" + request + "] : " + e);
            return Response.serverError()
                    .status(404)
                    .type(MediaType.TEXT_HTML)
                    .build();
        }
    }
}
