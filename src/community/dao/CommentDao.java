package community.dao;

import static community.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import community.beans.Comment;
import community.exception.NoRowsUpdatedRuntimeException;
import community.exception.SQLRuntimeException;

public class CommentDao {

	public void insert(Connection connection, Comment comments) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO  Comments ( ");
			sql.append("message_id");
			sql.append(", text");
			sql.append(", branch_id");
			sql.append(", job_id");
			sql.append(", user_id");
			sql.append(", created_at");
			sql.append(", updated_at");
			sql.append(") VALUES (");
			sql.append("?"); // message_id
			sql.append(", ?"); // text
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // job_id
			sql.append(", ?"); // user_id
			sql.append(", CURRENT_TIMESTAMP"); // created_at
			sql.append(", CURRENT_TIMESTAMP"); // updated_at
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt   (1, comments.getMessageId());
			ps.setString(2, comments.getText());
			ps.setInt	(3, comments.getBranchId());
			ps.setInt	(4, comments.getJobId());
			ps.setInt	(5, comments.getUserId());

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
			String sql = "delete  FROM comments WHERE id = ?";

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
