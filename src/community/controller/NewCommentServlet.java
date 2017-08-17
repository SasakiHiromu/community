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

import community.beans.Comment;
import community.beans.User;
import community.service.CommentService;

@WebServlet(urlPatterns = { "/newComment" })
public class NewCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> comments = new ArrayList<String>();

		if (isValid(request, comments) == true) {

			User users = (User) session.getAttribute("loginUser");

			Comment comment = new Comment();
			comment.setMessageId(Integer.parseInt(request.getParameter("message_id")));
			comment.setText(request.getParameter("text"));
			comment.setBranchId(users.getBranchId());
			comment.setJobId(users.getJobId());
			comment.setUserId(users.getId());

			new CommentService().register(comment);

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", comments);
			response.sendRedirect("./");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> comments) {

		String comment = request.getParameter("text");

		if (StringUtils.isBlank(comment) == true) {
			comments.add("本文を入力してください");
		}
		if (500L < comment.length()) {
			comments.add("500文字以下で入力してください");
		}
		if (comments.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}