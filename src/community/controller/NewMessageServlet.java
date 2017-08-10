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

import community.beans.Message;
import community.beans.User;
import community.service.MessageService;

@WebServlet(urlPatterns = { "/newMessage" })
public class NewMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {


		request.getRequestDispatcher("message.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<String>();

		User users = (User) session.getAttribute("loginUser");

		Message message = new Message();
		message.setTitle(request.getParameter("title"));

		if (StringUtils.isBlank(request.getParameter("category")) == true) {
			message.setCategory(request.getParameter("newCategory"));
		}

		message.setText(request.getParameter("text"));
		message.setBranchId(users.getBranchId());
		message.setJobId(users.getJobId());
		message.setUserId(users.getId());

		if (isValid(request, messages) == true) {
			if (StringUtils.isBlank(request.getParameter("newCategory")) == true) {
				message.setCategory(request.getParameter("category"));
			}

			new MessageService().register(message);
			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String text = request.getParameter("text");
		String title = request.getParameter("title");
		String category = request.getParameter("category");
		String newCategory = request.getParameter("newCategory");

		if (StringUtils.isBlank(title) == true) {
			messages.add("件名を入力してください");
		}

		if (StringUtils.isBlank(category) == true && StringUtils.isBlank(newCategory) == true) {
			messages.add("既存のカテゴリーを選択するか、新規カテゴリーを作成してください");
		}

		if (StringUtils.isBlank(category) == false && StringUtils.isBlank(newCategory) == false) {
			messages.add("既存のカテゴリーか新規カテゴリーどちらかにしてください");
		}

		if (StringUtils.isBlank(text) == true) {
			messages.add("本文を入力してください");
		}

		if (1000L < text.length()) {
			messages.add("1000文字以下で入力してください");
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
