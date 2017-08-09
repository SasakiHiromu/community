package community.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import community.beans.Branch;
import community.beans.Job;
import community.beans.User;
import community.beans.UserComment;
import community.beans.UserMessage;
import community.service.BranchService;
import community.service.CommentService;
import community.service.JobService;
import community.service.MessageService;
import community.service.UserService;

/**
 * Servlet implementation class TopServlet
 */
@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException,ServletException {

		String category = null;


		List<User> getAllUser = new UserService().getAllUser();
		request.setAttribute("allusers", getAllUser);

		List<Branch> getAllBranch = new BranchService().getAllBranch();
		request.setAttribute("allbranches", getAllBranch);

		List<Job> getAllJob = new JobService().getAllJob();
		request.setAttribute("alljobs", getAllJob);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = "2017/7/31 00:00:00";
		String endDate = sdf.format(date).toString();
		String diaryDate = df.format(date).toString();
		request.setAttribute("diaryDate", diaryDate);

		List<UserMessage> categoryList = new MessageService().getMessage(startDate,endDate,category);
		List<String> categories = new ArrayList<String>();
		for (int i = 0; i < categoryList.size(); i++) {
			 categories.add(categoryList.get(i).getCategory());
		}
		request.setAttribute("categories", categories);

		if (StringUtils.isBlank(request.getParameter("startDate")) == false) {
			startDate = request.getParameter("startDate");
		}

		if (StringUtils.isBlank(request.getParameter("endDate")) == false) {
			endDate = request.getParameter("endDate");
		}

		if (request.getParameter("categories") != null) {
			category = request.getParameter("categories");
		}

		List<UserMessage> messageList = new MessageService().getMessage(startDate,endDate,category);
		request.setAttribute("messages", messageList);


		List<UserComment> commentList = new CommentService().getComment();
		request.setAttribute("comments", commentList);




		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}

}