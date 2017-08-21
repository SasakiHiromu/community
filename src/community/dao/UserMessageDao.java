package community.dao;

import static community.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import community.beans.UserMessage;
import community.exception.SQLRuntimeException;

public class UserMessageDao {

	public List<UserMessage> getUserMessages(Connection connection,
			String startDate, String endDate, String category) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM talk_board.message");
			sql.append(" WHERE ");
			sql.append("created_at >= ?");
			sql.append(" AND ");
			sql.append("created_at <= ?");

			if (StringUtils.isBlank(category) == false) {
				sql.append(" AND ");
				sql.append("category = ?");

			}
			sql.append("ORDER BY created_at DESC");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, startDate + " 00:00:00");
			ps.setString(2, endDate + " 23:59:59"


					);

			if (StringUtils.isBlank(category) == false) {
				ps.setString(3, category);
			}

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserMessage> toUserMessageList(ResultSet rs)
			throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try {
			while (rs.next()) {
				String name = rs.getString("name");
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String category = rs.getString("category");
				String text = rs.getString("text");
				int branchId = rs.getInt("branch_id");
				int jobId = rs.getInt("job_id");
				int userId = rs.getInt("user_id");
				Timestamp createdAt = rs.getTimestamp("created_at");

				UserMessage message = new UserMessage();
				message.setName(name);
				message.setId(id);
				message.setTitle(title);
				message.setCategory(category);
				message.setText(text);
				message.setBranchId(branchId);
				message.setJobId(jobId);
				message.setUserId(userId);
				message.setCreatedAt(createdAt);

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public List<UserMessage> getUserCategory(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT category FROM message");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = getUserCategoryList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserMessage> getUserCategoryList(ResultSet rs)
			throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try {
			while (rs.next()) {
				String category = rs.getString("category");

				UserMessage categories = new UserMessage();
				categories.setCategory(category);
				ret.add(categories);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
