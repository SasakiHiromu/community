package community.dao;

import static community.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import community.beans.Message;
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
			ps.setInt	(4, messages.getBranch_id());
			ps.setInt	(5, messages.getJob_id());
			ps.setInt	(6, messages.getUser_id());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}
