package com.hiwoo.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(urlPatterns = {"/*"}, filterName = "JsonpFilter")
public class JsonpFilter implements Filter {
	private ServletContext servletContext;

	/**
	 * Default constructor.
	 */
	public JsonpFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hReq = (HttpServletRequest) request;
		HttpServletResponse hResp = (HttpServletResponse) response;
		hResp.setHeader("Access-Control-Allow-Origin", "*");
		hResp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		hResp.setHeader("Access-Control-Max-Age","3600");  
		hResp.setHeader("Access-Control-Allow-Headers","x-requested-with,Content-Type,Authorization,Accept,Token,WechatAuth,AuthGroupId");


		chain.doFilter(hReq, hResp);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		servletContext = fConfig.getServletContext();
	}

}
