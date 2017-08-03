package community.service;

import static community.utils.CloseableUtil.*;
import static community.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import community.beans.Branch;
import community.dao.BranchDao;

public class BranchService {

	public List<Branch> getAllBranch() {

		Connection connection = null;
		try {
			connection = getConnection();

			BranchDao branchDao = new BranchDao();
			List<Branch> branches = branchDao.getAllBranch(connection);

			commit(connection);

			return branches;
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