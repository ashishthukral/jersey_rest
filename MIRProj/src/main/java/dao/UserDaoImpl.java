package dao;

import java.util.Map;

import org.apache.log4j.Logger;

import common.CommonUtil;

import domain.User;

public enum UserDaoImpl {
	USER_DAO_INSTANCE;

	private Map<Integer, User> _dbUserMap = CommonUtil.INSTANCE.getDbUserMap();
	private final static Logger LOG = Logger.getLogger(UserDaoImpl.class);
	private static Integer NEXT_USER_ID = 4;

	public Integer createUser(User iUser) {
		LOG.info("createUser");
		iUser.setId(NEXT_USER_ID);
		_dbUserMap.put(NEXT_USER_ID, iUser);
		NEXT_USER_ID++;
		LOG.info(iUser);
		LOG.info(_dbUserMap);
		return iUser.getId();
	}

	public User getUserById(Integer iUserId) {
		LOG.info("getUserById");
		LOG.info("iUserId=" + iUserId);
		User anUser = _dbUserMap.get(iUserId);
		LOG.info(anUser);
		LOG.info(_dbUserMap);
		return anUser;
	}

	public void userUpdate(User iUser) {
		LOG.info("userUpdate");
		LOG.info("Old User info=" + _dbUserMap.get(iUser.getId()));
		LOG.info("New User info=" + iUser);
		_dbUserMap.put(iUser.getId(), iUser);
		LOG.info(_dbUserMap);
	}

	public void deleteUserById(Integer iUserId) {
		LOG.info("deleteUserById");
		LOG.info("iUserId=" + iUserId);
		_dbUserMap.remove(iUserId);
		LOG.info(_dbUserMap);
	}

}
