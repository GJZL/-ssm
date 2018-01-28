package cn.hzxy.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.dao.JedisClient;
import cn.hzxy.mapper.TbUserMapper;
import cn.hzxy.pojo.TbUser;
import cn.hzxy.pojo.TbUserExample;
import cn.hzxy.pojo.TbUserExample.Criteria;
import cn.hzxy.sso.service.UserService;
import cn.hzxy.utils.JsonUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	@Value("${SSO_SESSION_EXPIRE}")
	private int SSO_SESSION_EXPIRE;

	@Override
	public EgoResult checkData(String content, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if (type == 1) {
			criteria.andUsernameEqualTo(content);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(content);
		} else {
			criteria.andEmailEqualTo(content);
		}
		List<TbUser> userList = userMapper.selectByExample(example);
		if (userList.size() == 0 || userList == null) {
			return EgoResult.ok(true);
		}
		return EgoResult.ok(false);
	}

	@Override
	public EgoResult createrUser(TbUser user) {
		Date date = new Date();
		user.setCreated(date);
		user.setUpdated(date);
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		return EgoResult.ok();
	}

	@Override
	public EgoResult loginUser(String username, String password) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> userList = userMapper.selectByExample(example);
		if (userList == null || userList.size() > 0) {
			return EgoResult.build(400, "用户名或密码错误！");
		}
		TbUser user = userList.get(0);
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return EgoResult.build(400, "用户名或密码错误！");
		}
		String token = UUID.randomUUID().toString();
		user.setPassword(null);
		jedisClient.set(REDIS_USER_SESSION_KEY + token, JsonUtils.objectToJson(user));
		jedisClient.expire(REDIS_USER_SESSION_KEY + token, SSO_SESSION_EXPIRE);
		return EgoResult.ok(token);
	}

	@Override
	public EgoResult getUserByToken(String token) {
		String tokenFlag = jedisClient.get(REDIS_USER_SESSION_KEY + token);
		if (StringUtils.isEmpty(tokenFlag)) {
			return EgoResult.build(400, "请重新登录");
		}
		jedisClient.expire(REDIS_USER_SESSION_KEY + token, SSO_SESSION_EXPIRE);
		return EgoResult.ok(JsonUtils.jsonToPojo(tokenFlag, TbUser.class));
	}

	@Override
	public EgoResult logoutUser(String token) {
		String tokenFlag = jedisClient.get(REDIS_USER_SESSION_KEY + token);
		if (StringUtils.isEmpty(tokenFlag)) {
			return EgoResult.build(400, "操作失败，请重试！");
		}
		jedisClient.del(REDIS_USER_SESSION_KEY+token);
		return EgoResult.ok();
	}
}