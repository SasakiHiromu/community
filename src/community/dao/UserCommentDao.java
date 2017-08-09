package community.dao;

import static community.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import community.beans.UserComment;
import community.exception.SQLRuntimeException;

public class UserCommentDao {

	public List<UserComment> getUserComments(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM talk_board.comment ");
			sql.append("ORDER BY created_at DESC");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserComment> ret = toUserCommentList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserComment> toUserCommentList(ResultSet rs)
			throws SQLException {

		List<UserComment> ret = new ArrayList<UserComment>();
		try {
			while (rs.next()) {
				String name = rs.getString("name");
				int id = rs.getInt("id");
				int messageId = rs.getInt("message_id");
				String text = rs.getString("text");
				int branchId = rs.getInt("branch_id");
				int jobId = rs.getInt("job_id");
				int userId = rs.getInt("user_id");
				Timestamp createdAt = rs.getTimestamp("created_at");

				UserComment comment = new UserComment();
				comment.setName(name);
				comment.setId(id);
				comment.setMessageId(messageId);
				comment.setText(text);
				comment.setBranchId(branchId);
				comment.setJobId(jobId);
				comment.setUserId(userId);
				comment.setCreatedAt(createdAt);
				System.out.println();

				ret.add(comment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
