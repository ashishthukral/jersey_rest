package dao;

import java.util.Map;

import org.apache.log4j.Logger;

import common.CommonUtil;

import domain.User;

public enum UserDao {
	USER_DAO_INSTANCE;

	private Map<Integer, User> _dbUserMap = CommonUtil.INSTANCE.getDbUserMap();
	private final static Logger LOG = Logger.getLogger(UserDao.class);
	private static Integer NEXT_USER_ID = 4;

	public Integer createUser(User iUser) {
		LOG.info("createUser");
		iUser.setId(NEXT_USER_ID);
		_dbUserMap.put(NEXT_USER_ID, iUser);
		NEXT_USER_ID++;
		System.out.println(_dbUserMap);
		return iUser.getId();
	}

	public User getUserById(Integer iUserId) {
		LOG.info("getUserById");
		User anUser = _dbUserMap.get(iUserId);
		System.out.println(_dbUserMap);
		return anUser;
	}

	public void userUpdate(User iUser) {
		LOG.info("userUpdate");
		_dbUserMap.put(iUser.getId(), iUser);
		System.out.println(_dbUserMap);
	}

	public void deleteUserById(Integer iUserId) {
		LOG.info("deleteUserById");
		_dbUserMap.remove(iUserId);
		System.out.println(_dbUserMap);
	}

}
