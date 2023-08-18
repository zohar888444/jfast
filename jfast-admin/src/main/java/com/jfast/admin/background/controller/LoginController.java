package com.jfast.admin.background.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jfast.background.service.RbacService;
import com.jfast.background.vo.AccountAuthInfoVO;
import com.jfast.common.exception.BizException;
import com.jfast.common.vo.Result;
import com.jfast.common.vo.TokenInfo;
import com.jfast.constants.Constant;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;

@Controller
public class LoginController {

	@Autowired
	private RbacService rbacService;

	@PostMapping("/login")
	@ResponseBody
	public Result<TokenInfo> login(String userName, String loginPwd, String googleVerCode, HttpServletRequest request) {
		AccountAuthInfoVO vo = rbacService.getAccountAuthInfo(userName);
		if (vo == null) {
			throw new BizException("账号或密码不正确");
		}
		if (!SaSecureUtil.sha256(loginPwd).equals(vo.getLoginPwd())) {
			throw new BizException("账号或密码不正确");
		}
		if (Constant.功能状态_禁用.equals(vo.getState())) {
			throw new BizException("账号已被禁用");
		}
		StpUtil.login(vo.getId(), false);
		StpUtil.getSession().set("userName", vo.getUserName());
		StpUtil.getSession().set("subSystem", Constant.子系统_后台管理);

		rbacService.updateLatelyLoginTime(vo.getId());

		return Result.success(TokenInfo.build());
	}

	@PostMapping("/logout")
	@ResponseBody
	public Result<String> login() {
		StpUtil.logout();
		return Result.success();
	}

}
