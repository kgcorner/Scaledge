package com.kgcorner.scaledge.security.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CORSFilter extends AbstractFilter {
	private final Logger log = Logger.getLogger(CORSFilter.class);

	public CORSFilter() {
		log.info("SimpleCORSFilter init");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		log.info("Adding cors");
		String path = ((HttpServletRequest) req).getServletPath();
		log.info("********************");
		log.info(path);
		log.info("********************");
		HttpServletResponse response = (HttpServletResponse) res;

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me"
				+ ", X-ACCESS-TOKEN, X-DATE-TOKEN, X-USER-ID, Authorization, X-SOCIAL-TOKEN, X-SYNC-TOKEN, X-SYNC-TOKEN-PHRASE,X-LOGIN-PROVIDER");
		String method = ((HttpServletRequest) req).getMethod();
		if(method.equalsIgnoreCase("options")) {
			return;
		}
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}
}
