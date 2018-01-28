package cn.hzxy.sso.service;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.TbUser;

public interface UserService {
	 /**
	  * 检验用户是否存在
	 * @param content
	 * @param type
	 * @return EgoResult
	 */
	EgoResult checkData(String content, Integer type);

	/**
	 * 创建用户
	 * @param user
	 * @return EgoResult
	 */
	EgoResult createrUser(TbUser user);

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return EgoResult
	 */
	EgoResult loginUser(String username, String password);

	/**
	 * 查询用户是否登录
	 * @param token
	 * @return EgoResult
	 */
	EgoResult getUserByToken(String token);

	/**
	 * 用户退出登录
	 * @param token
	 * @return EgoResult
	 */
	EgoResult logoutUser(String token);
}
