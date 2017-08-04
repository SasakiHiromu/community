package community.service;

import static community.utils.CloseableUtil.*;
import static community.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import community.beans.Job;
import community.dao.JobDao;

public class JobService {

	public List<Job> getAllJob() {

		Connection connection = null;
		try {
			connection = getConnection();

			JobDao jobDao = new JobDao();
			List<Job> jobs = jobDao.getAllJob(connection);

			commit(connection);

			return jobs;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
}