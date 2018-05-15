package org.sterling.intranet.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.sterling.intranet.security.SecurityUtils;

/**
 *
 * @author Var Arenz G. Villarino
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler
{

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException
    {
        SecurityUtils.sendError(response, exception, HttpServletResponse.SC_FORBIDDEN, "Not authorized resources");
    }
}