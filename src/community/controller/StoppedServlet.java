package community.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import community.service.UserService;

@WebServlet(urlPatterns = {"/stopped"})
public class StoppedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		int id = Integer.parseInt(request.getParameter("id"));
		int isStopped = Integer.parseInt(request.getParameter("isStopped"));
		new UserService().isStopped(id,isStopped);

		response.sendRedirect("status");

	}
}
