package community.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import community.beans.Branch;
import community.beans.Job;
import community.beans.UserComment;
import community.beans.UserMessage;
import community.service.BranchService;
import community.service.CommentService;
import community.service.JobService;
import community.service.MessageService;

/**
 * Servlet implementation class TopServlet
 */
@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException,ServletException {


		List<Branch> getAllBranch = new BranchService().getAllBranch();
		request.setAttribute("allbranches", getAllBranch);

		List<Job> getAllJob = new JobService().getAllJob();
		request.setAttribute("alljobs", getAllJob);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startDate = "2017/7/31 00:00:00";
		String endDate = sdf.format(date).toString();

		if (request.getParameter("startDate") != null) {
			startDate = request.getParameter("startDate") + " 00:00:00";
		}

		if (request.getParameter("endDate") != null) {
			endDate = request.getParameter("endDate") + " 23:59:00";
		}

		List<UserMessage> messageList = new MessageService().getMessage(startDate,endDate);
		request.setAttribute("messages", messageList);


		List<UserComment> commentList = new CommentService().getComment();
		request.setAttribute("comments", commentList);




		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}

}