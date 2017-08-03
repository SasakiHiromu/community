package community.service;

import static community.utils.CloseableUtil.*;
import static community.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import community.beans.Message;
import community.beans.UserMessage;
import community.dao.MessageDao;
import community.dao.UserMessageDao;

public class MessageService {

	public void register(Message messages) {

		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			messageDao.insert(connection, messages);

			commit(connection);
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


	public List<UserMessage> getMessage() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao messageDao = new UserMessageDao();
			List<UserMessage> ret = messageDao.getUserMessages(connection);

			commit(connection);

			return ret;
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
