package com.ip2lite.pkg;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(name = "IP2LocationServlet", urlPatterns = {"/IP2LocationServlet"})
public class IP2LocationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static Logger logger = Logger.getLogger(IP2LocationServlet.class);

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                                             throws ServletException, IOException {

		String ipAddress   = request.getHeader("X-FORWARDED-FOR");
		String userAgent   = null;
		String userReferer = null;
		String queryString = null;

		if(ipAddress == null) {    //Means client was not behind any proxy

		   ipAddress   = request.getRemoteAddr();
		   userAgent   = request.getHeader("user-agent");
		   userReferer = request.getHeader("referer");
		   queryString = request.getQueryString();
		}

		new AccessLogger(ipAddress, userAgent, userReferer, queryString);

		RequestDispatcher dispatcher = request.getRequestDispatcher("IP2Location.jsp");
        dispatcher.forward(request, response);
	}
}
