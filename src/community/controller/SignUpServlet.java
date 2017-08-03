package community.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import community.beans.User;
import community.service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();

		User users = new User();
		users.setLogin_id(request.getParameter("login_id"));
		users.setPassword(request.getParameter("password"));
		users.setName(request.getParameter("name"));
		users.setBranch_id(Integer.parseInt(request.getParameter("branch_id")));
		users.setJob_id(Integer.parseInt(request.getParameter("job_id")));
		users.setIs_stopped(0);

		if (isValid(request, messages) == true) {


			new UserService().register(users);

			response.sendRedirect("");
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("user", users);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");

		if (StringUtils.isEmpty(login_id) == true) {
			messages.add("IDを入力してください");
		}
		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		}
		// TODO アカウントが既に利用されていないか、メールアドレスが既に登録されていないかなどの確認も必要
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
