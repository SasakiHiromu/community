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
		User editUser = userservice.getUser(Integer.parseInt(request.getParameter("id")));
		session.setAttribute("editUser", editUser);

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

		if (isValid(request, messages) == true) {

			try {
				new UserService().update(editUser);
			} catch (NoRowsUpdatedRuntimeException e) {
				session.removeAttribute("editUser");
				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("settings");
			}

			session.removeAttribute("editUser");

			response.sendRedirect("status");
		} else {
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("settings");
		}
	}

	private User getEditUser(HttpServletRequest request)
			throws IOException, ServletException {


		UserService userservice = new UserService();
		User editUser = userservice.getUser(Integer.parseInt(request.getParameter("id")));

		editUser.setLogin_id(request.getParameter("login_id"));
		editUser.setPassword(request.getParameter("password"));
		editUser.setName(request.getParameter("name"));
		editUser.setBranch_id(Integer.parseInt(request.getParameter("branch_id")));
		editUser.setJob_id(Integer.parseInt(request.getParameter("job_id")));
		return editUser;
	}


	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");

		if (StringUtils.isEmpty(login_id) == true) {
			messages.add("アカウント名を入力してください");
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

