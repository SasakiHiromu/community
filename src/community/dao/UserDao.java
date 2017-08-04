package community.dao;

import static community.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import community.beans.User;
import community.exception.NoRowsUpdatedRuntimeException;
import community.exception.SQLRuntimeException;


public class UserDao {

	public User getUser(Connection connection, String login_id, String password) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE (login_id = ?) AND password = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, login_id);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	private List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String loginId = rs.getString("login_id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				int	   branchId = rs.getInt("branch_id");
				int    jobId = rs.getInt("job_id");
				int    isStopped = rs.getInt("is_stopped");
				Timestamp createdAt = rs.getTimestamp("created_at");
				Timestamp updatedAt = rs.getTimestamp("updated_at");

				User users = new User();
				users.setId(id);
				users.setLoginId(loginId);
				users.setPassword(password);
				users.setName(name);
				users.setBranchId(branchId);
				users.setJobId(jobId);
				users.setIsStopped(isStopped);
				users.setCreatedAt(createdAt);
				users.setUpdateDate(updatedAt);

				ret.add(users);
			}
			return ret;
		} finally {
			close(rs);
		}
	}


	public void insert(Connection connection, User users) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users ( ");
			//sql.append("id");
			sql.append("login_id");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", job_id");
			sql.append(", is_stopped");
			sql.append(", created_at");
			sql.append(", updated_at");
			sql.append(") VALUES (");
			//sql.append("NEXT VALUE FOR my_seq "); // id
			sql.append("?"); // login_id
			sql.append(", ?"); // password
			sql.append(", ?"); // name
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // job_id
			sql.append(", ?"); // is_stopped
			sql.append(", CURRENT_TIMESTAMP"); // created_at
			sql.append(", CURRENT_TIMESTAMP"); // update_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, users.getLoginId());
			ps.setString(2, users.getPassword());
			ps.setString(3, users.getName());
			ps.setInt(4, users.getBranchId());
			ps.setInt(5, users.getJobId());
			ps.setInt(6, users.getIsStopped());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void update(Connection connection, User users) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append("  login_id = ?");
			sql.append(", password = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", job_id = ?");
			sql.append(", created_at = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" id = ?");
			sql.append(" AND");
			sql.append(" updated_at = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, users.getLoginId());
			ps.setString(2, users.getPassword());
			ps.setString(3, users.getName());
			ps.setInt(4, users.getBranchId());
			ps.setInt(5, users.getJobId());
			ps.setInt(6, users.getId());
			ps.setTimestamp(7,
					new Timestamp(users.getUpdateDate().getTime()));
			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	public User getUser(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<User> getAllUser(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users");

			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			List<User> ret = toUserList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void isStopped(Connection connection, int id, int isStopped) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" is_stopped = ?");
			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, isStopped);
			ps.setInt(2, id);

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
