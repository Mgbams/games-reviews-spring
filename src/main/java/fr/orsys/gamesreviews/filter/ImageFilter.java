package fr.orsys.gamesreviews.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class ImageFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (((HttpServletRequest) request).getRequestURI().endsWith("jpg")) {
			((HttpServletResponse) response).setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			((HttpServletResponse) response).setHeader("Pragma", "no-cache");
			((HttpServletResponse) response).setDateHeader("Expires", 0);
			((HttpServletResponse) response).setHeader("Cache-Control", "max-age=0");
		};
		chain.doFilter(request, response);
	}
	

}
