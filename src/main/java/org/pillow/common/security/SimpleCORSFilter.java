package org.pillow.common.security;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 支持跨域资源访问（CORS）
 *
 * @author peilu.wang
 */
@Component
public class SimpleCORSFilter implements Filter {
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        //允许跨域访问
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        res.setHeader("Access-Control-Expose-Headers", "Content-Disposition, Authorization-info, Stale");
        //防止Clickjack攻击
        res.addHeader("X-FRAME-OPTIONS", "SAMEORIGIN"); 
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
