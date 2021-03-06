package community.dao;

import static community.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import community.beans.Message;
import community.exception.NoRowsUpdatedRuntimeException;
import community.exception.SQLRuntimeException;

public class MessageDao {

	public void insert(Connection connection, Message messages) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO messages ( ");
			sql.append("title");
			sql.append(", text");
			sql.append(", category");
			sql.append(", branch_id");
			sql.append(", job_id");
			sql.append(", user_id");
			sql.append(", created_at");
			sql.append(", updated_at");
			sql.append(") VALUES (");
			sql.append("?"); // title
			sql.append(", ?"); // text
			sql.append(", ?"); // category
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // job_id
			sql.append(", ?"); // user_id
			sql.append(", CURRENT_TIMESTAMP"); // created_at
			sql.append(", CURRENT_TIMESTAMP"); // updated_at
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, messages.getTitle());
			ps.setString(2, messages.getText());
			ps.setString(3, messages.getCategory());
			ps.setInt	(4, messages.getBranchId());
			ps.setInt	(5, messages.getJobId());
			ps.setInt	(6, messages.getUserId());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void delete(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "delete  FROM messages WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			int count = ps.executeUpdate();
			if(count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}
