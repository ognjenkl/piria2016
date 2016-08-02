package filter;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.LoginBean;

/**
 * @author ognjen
 *
 */
@WebFilter("/*")
public class AccessControlFilter implements Filter{

	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse resp = (HttpServletResponse) arg1;
		System.out.println("Access controler: ");
		HttpSession session = req.getSession(false);
		
//		System.out.println("uri: " + req.getRequestURI());
//		System.out.println("servlet path: " + req.getServletPath());
//		System.out.println("context path: " + req.getContextPath());
		LoginBean loginBean = (session != null) ? (LoginBean)session.getAttribute("login") : null;
		String homeURL = req.getContextPath() + "/guest.xhtml";
		
		boolean loggedIn = loginBean != null && loginBean.isLoggedIn();
		boolean guestRequest = req.getRequestURI().equals(homeURL);
		boolean resourceRequest = req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER);
		
		if(loggedIn || guestRequest || resourceRequest){
			if(!resourceRequest){
				resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
				resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
				resp.setDateHeader("Expires", 0); // Proxies.
		       
			}			
			//System.out.println("doFileter: " + req.getRequestURI());
			if(req.getServletPath().startsWith("/admin.xhtml") && loginBean.getUser().getPrivilege() > 1)
//				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				resp.sendRedirect(homeURL);
			else if(req.getServletPath().startsWith("/superuser.xhtml") && loginBean.getUser().getPrivilege() > 2)
				resp.sendRedirect(homeURL);
			else if(req.getServletPath().startsWith("/user.xhtml") && loginBean.getUser().getPrivilege() > 3)
				resp.sendRedirect(homeURL);

			arg2.doFilter(req, resp);
		}else{
			System.out.println("redirect");
			//prereacunava naknadno putanju za resp.sendRedirect("guest.xhtml");
			resp.sendRedirect(homeURL);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
