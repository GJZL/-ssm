package cn.hzxy.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.TbUser;
import cn.hzxy.sso.service.UserService;
import cn.hzxy.utils.ExceptionUtil;
import cn.hzxy.utils.JsonUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 校验用户是否可用
	 * @param param
	 * @param type
	 * @param callback
	 * @return Object
	 */
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {
		EgoResult egoResult = null;
		if (StringUtils.isEmpty(param)) {
			egoResult = EgoResult.build(400, "校验内容不能为空");
		}
		if (type == null) {
			egoResult = EgoResult.build(400, "校验类型不能为空");
		}
		if (type != 1 && type != 2 && type != 3) {
			egoResult = EgoResult.build(400, "校验类型不正确");
		}
		// 当传参错误时判断是否是callback
		if (null != egoResult) {
			if (callback != null) {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(egoResult);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			} else {
				return egoResult;
			}
		}
		EgoResult result = userService.checkData(param, type);
		// 传参正确后再次判断是不是callback
		if (null != callback) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		} else {
			return result;
		}
	}

	/**
	 * 创建用户
	 * @param user
	 * @return EgoResult
	 */
	@RequestMapping("/register")
	public EgoResult createrUser(TbUser user) {
		EgoResult result = userService.createrUser(user);
		return result;
	}

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return EgoResult
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public EgoResult loginUser(String username, String password) {
		EgoResult egoResult = userService.loginUser(username, password);
		return egoResult;
	}

	/**
	 * 查询用户是否登录
	 * @param token
	 * @param callback
	 * @return Object
	 */
	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		EgoResult result = null;
		try {
			result = userService.getUserByToken(token);
		} catch (Exception e) {
			result = EgoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		if (StringUtils.isEmpty(callback)) {
			return result;
		} else {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
	}
	/**
	 * 查询用户是否登录
	 * @param token
	 * @param callback
	 * @return Object
	 */
	@RequestMapping("/logout/{token}")
	@ResponseBody
	public Object logoutUser(@PathVariable String token, String callback) {
		EgoResult result = null;
		try {
			result = userService.logoutUser(token);
		} catch (Exception e) {
			result = EgoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		if (StringUtils.isEmpty(callback)) {
			return result;
		} else {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
	}
}