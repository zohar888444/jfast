package com.jfast.admin.background.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jfast.background.param.AddBackgroundAccountParam;
import com.jfast.background.param.BackgroundAccountEditParam;
import com.jfast.background.param.BackgroundAccountQueryCondParam;
import com.jfast.background.service.RbacService;
import com.jfast.background.vo.BackgroundAccountVO;
import com.jfast.background.vo.MenuVO;
import com.jfast.common.vo.PageResult;
import com.jfast.common.vo.Result;

import cn.dev33.satoken.stp.StpUtil;

@Controller
@RequestMapping("/rbac")
public class RbacController {
	
	@Autowired
	private RbacService rbacService;

	@GetMapping("/findBackgroundAccountByPage")
	@ResponseBody
	public Result<PageResult<BackgroundAccountVO>> findBackgroundAccountByPage(BackgroundAccountQueryCondParam param) {
		return Result.success(rbacService.findBackgroundAccountByPage(param));
	}

	@PostMapping("/modifyLoginPwd")
	@ResponseBody
	public Result<String> modifyLoginPwd(String id, String newPwd) {
		rbacService.modifyLoginPwd(id, newPwd);
		return Result.success();
	}

	@PostMapping("/updateBackgroundAccount")
	@ResponseBody
	public Result<String> updateBackgroundAccount(BackgroundAccountEditParam param) {
		rbacService.updateBackgroundAccount(param);
		return Result.success();
	}

	@GetMapping("/findBackgroundAccountById")
	@ResponseBody
	public Result<BackgroundAccountVO> findBackgroundAccountById(String id) {
		BackgroundAccountVO accountInfo = rbacService.findBackgroundAccountById(id);
		return Result.success(accountInfo);
	}

	@GetMapping("/getAccountInfo")
	@ResponseBody
	public Result<BackgroundAccountVO> getAccountInfo() {
		BackgroundAccountVO accountInfo = rbacService.findBackgroundAccountById(StpUtil.getLoginIdAsString());
		return Result.success(accountInfo);
	}

	@GetMapping("/delBackgroundAccount")
	@ResponseBody
	public Result<String> delBackgroundAccount(String id) {
		rbacService.delBackgroundAccount(id);
		return Result.success();
	}

	@PostMapping("/addBackgroundAccount")
	@ResponseBody
	public Result<String> addBackgroundAccount(AddBackgroundAccountParam param) {
		rbacService.addBackgroundAccount(param);
		return Result.success();
	}

	@GetMapping("/findMyMenuTree")
	@ResponseBody
	public Result<List<MenuVO>> findMyMenuTree() {
		return Result.success(rbacService.findMenuTreeByAccountId(StpUtil.getLoginIdAsString()));
	}


}
