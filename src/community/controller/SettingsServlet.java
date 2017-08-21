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
import community.exception.NoRowsUpdatedRuntimeException;
import community.service.BranchService;
import community.service.JobService;
import community.service.UserService;

@WebServlet(urlPatterns = { "/settings" })
public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		UserService userservice = new UserService();

		if(request.getParameter("id") == null ) {
			response.sendRedirect("status");
			return;
		}

		if(!request.getParameter("id").matches("^[0-9]+$")) {
			response.sendRedirect("status");
			return;
		}

		if(userservice.getUser(Integer.parseInt(request.getParameter("id"))) == null) {
			response.sendRedirect("status");
			return;
		}

		if(session.getAttribute("editUser") == null) {
			User editUser = userservice.getUser(Integer.parseInt(request.getParameter("id")));
			session.setAttribute("editUser", editUser);
		}

		List<Branch> getAllBranch = new BranchService().getAllBranch();
		request.setAttribute("allbranches", getAllBranch);

		List<Job> getAllJob = new JobService().getAllJob();
		request.setAttribute("alljobs", getAllJob);

		request.getRequestDispatcher("settings.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();


		User editUser = getEditUser(request);
		session.setAttribute("editUser", editUser);

		List<Branch> getAllBranch = new BranchService().getAllBranch();
		request.setAttribute("allbranches", getAllBranch);

		List<Job> getAllJob = new JobService().getAllJob();
		request.setAttribute("alljobs", getAllJob);

		if (isValid(request, messages) == true) {

			try {
				new UserService().update(editUser);
			} catch (NoRowsUpdatedRuntimeException e) {
				session.removeAttribute("editUser");
				messages.add("他の人によって更新されています。");
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("settings");
			}

			session.removeAttribute("editUser");

			response.sendRedirect("status");
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("editUser", editUser);
			request.setAttribute("newBranch", request.getParameter("allbranch.id"));
			request.setAttribute("newJob", request.getParameter("alljob.id"));
			//response.sendRedirect("settings");
			request.getRequestDispatcher("/settings.jsp").forward(request, response);
		}
	}

	private User getEditUser(HttpServletRequest request)
			throws IOException, ServletException {


		UserService userservice = new UserService();
		User editUser = userservice.getUser(Integer.parseInt(request.getParameter("id")));
		String password = request.getParameter("password");

		editUser.setLoginId(request.getParameter("loginId"));
		if (password.matches("^[0-9a-zA-Z]+$") && 6 <= password.length() && password.length() <= 20) {
			editUser.setPassword(request.getParameter("password"));
		}
		editUser.setName(request.getParameter("name"));
		editUser.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		editUser.setJobId(Integer.parseInt(request.getParameter("jobId")));
		return editUser;
	}


	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");
		String name = request.getParameter("name");
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int jobId = Integer.parseInt(request.getParameter("jobId"));


		if (StringUtils.isBlank(loginId) == true) {
			messages.add("IDを入力してください");
		}
//		if (StringUtils.isEmpty(password) == true) {
//			messages.add("パスワードを入力してください");
//		}
//		if (StringUtils.isEmpty(newPassword) == true) {
//			messages.add("確認用パスワードを入力してください");
//		}

		if (!(password.matches("^[0-9a-zA-Zｱ-ﾝ]+$")) && 6 <= password.length() && password.length() <= 20) {
			messages.add("パスワードを入力してください");
		}

		if (!(newPassword.matches("^[0-9a-zA-Zｱ-ﾝ]+$")) && 6 <= newPassword.length() && newPassword.length() <= 20) {
			messages.add("パスワードを入力してください3");
		}


		if (!(password.matches("^[ -~｡-ﾟ]+$")) && 6 <= password.length() && password.length() <= 20) {
			messages.add("パスワードを入力してください2");
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