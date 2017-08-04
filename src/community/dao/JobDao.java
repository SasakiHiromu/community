package community.dao;

import static community.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import community.beans.Job;
import community.exception.SQLRuntimeException;

public class JobDao {
	public List<Job> getAllJob(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM jobs ");

			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			List<Job> ret = toJobList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	private List<Job> toJobList(ResultSet rs) throws SQLException {

		List<Job> ret = new ArrayList<Job>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Job jobs = new Job();
				jobs.setId(id);
				jobs.setName(name);

				ret.add(jobs);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
