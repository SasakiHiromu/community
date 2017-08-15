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

import community.beans.Branch;
import community.beans.Job;
import community.beans.User;
import community.service.BranchService;
import community.service.JobService;
import community.service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {


		List<Branch> getAllBranch = new BranchService().getAllBranch();
		request.setAttribute("allbranches", getAllBranch);

		List<Job> getAllJob = new JobService().getAllJob();
		request.setAttribute("alljobs", getAllJob);

		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();

		User users = new User();
		users.setLoginId(request.getParameter("loginId"));
		users.setPassword(request.getParameter("password"));
		users.setName(request.getParameter("name"));
		users.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		users.setJobId(Integer.parseInt(request.getParameter("jobId")));
		users.setIsStopped(0);

		if (isValid(request, messages) == true) {


			new UserService().register(users);

			response.sendRedirect("status");
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("user", users);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String login_id = request.getParameter("loginId");
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");
		String name = request.getParameter("name");
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int jobId = Integer.parseInt(request.getParameter("jobId"));


		if (StringUtils.isBlank(login_id) == true) {
			messages.add("IDを入力してください");
		}
		if (StringUtils.isBlank(password) == true) {
			messages.add("パスワードを入力してください");
		}

		if (password.matches("^[0-9a-zA-Z]+$") && 6 <= password.length() && password.length() <= 20) {
			messages.add("パスワードを入力してください");
		}

		if (newPassword.matches("^[0-9a-zA-Z]+$") && 6 <= newPassword.length() && newPassword.length() <= 20) {
			messages.add("パスワードを入力してください");
		}

		if (StringUtils.isBlank(password) != true && StringUtils.isBlank(newPassword) != true) {
			if (!(password.contentEquals(newPassword))) {
				messages.add("パスワードが一致しません");
			}
		}

		if (StringUtils.isBlank(name) == true) {
			messages.add("名前を入力してください");
		}

		if (10 <= name.length()) {
			messages.add("名前を10文字以内で入力してください");
		}

		if ( branchId == 0) {
			messages.add("所属を選択してください");
		}

		if (jobId == 0) {
			messages.add("役職を選択してください");
		}

		// TODO アカウントが既に利用されていないか、メールアドレスが既に登録されていないかなどの確認も必要
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
