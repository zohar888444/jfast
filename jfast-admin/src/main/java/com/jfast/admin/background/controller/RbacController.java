package com.jfast.admin.background.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jfast.background.param.AddBackgroundAccountParam;
import com.jfast.background.param.AssignMenuParam;
import com.jfast.background.param.AssignRoleParam;
import com.jfast.background.param.BackgroundAccountEditParam;
import com.jfast.background.param.BackgroundAccountQueryCondParam;
import com.jfast.background.param.MenuParam;
import com.jfast.background.param.RoleParam;
import com.jfast.background.service.RbacService;
import com.jfast.background.vo.BackgroundAccountVO;
import com.jfast.background.vo.MenuVO;
import com.jfast.background.vo.RoleVO;
import com.jfast.common.operlog.OperLog;
import com.jfast.common.vo.PageResult;
import com.jfast.common.vo.Result;
import com.jfast.constants.Constant;

import cn.dev33.satoken.stp.StpUtil;

@Controller
@RequestMapping("/rbac")
public class RbacController {
	
	public static final String 模块_后台账号 = "后台账号";

	@Autowired
	private RbacService rbacService;

	@PostMapping("/assignRole")
	@ResponseBody
	public Result<String> assignRole(@RequestBody AssignRoleParam param) {
		rbacService.assignRole(param);
		return Result.success();
	}

	@GetMapping("/findRoleByAccountId")
	@ResponseBody
	public Result<List<RoleVO>> findRoleByAccountId(String accountId) {
		return Result.success(rbacService.findRoleByAccountId(accountId));
	}

	@PostMapping("/assignMenu")
	@ResponseBody
	public Result<String> assignMenu(@RequestBody AssignMenuParam param) {
		rbacService.assignMenu(param);
		return Result.success();
	}

	@GetMapping("/findMenuByRoleId")
	@ResponseBody
	public Result<List<MenuVO>> findMenuByRoleId(String roleId) {
		return Result.success(rbacService.findMenuByRoleId(roleId));
	}

	@GetMapping("/findAllRole")
	@ResponseBody
	public Result<List<RoleVO>> findAllRole() {
		return Result.success(rbacService.findAllRole());
	}

	@PostMapping("/addOrUpdateRole")
	@ResponseBody
	public Result<String> addOrUpdateRole(RoleParam param) {
		rbacService.addOrUpdateRole(param);
		return Result.success();
	}

	@GetMapping("/delRole")
	@ResponseBody
	public Result<String> delRole(String id) {
		rbacService.delRole(id);
		return Result.success();
	}

	@GetMapping("/findRoleById")
	@ResponseBody
	public Result<RoleVO> findRoleById(String id) {
		return Result.success(rbacService.findRoleById(id));
	}

	@GetMapping("/findMenuById")
	@ResponseBody
	public Result<MenuVO> findMenuById(String id) {
		return Result.success(rbacService.findMenuById(id));
	}

	@GetMapping("/delMenu")
	@ResponseBody
	public Result<String> delMenu(String id) {
		rbacService.delMenu(id);
		return Result.success();
	}

	@PostMapping("/addOrUpdateMenu")
	@ResponseBody
	public Result<String> addOrUpdateMenu(MenuParam param) {
		rbacService.addOrUpdateMenu(param);
		return Result.success();
	}

	@GetMapping("/findMenuTree")
	@ResponseBody
	public Result<List<MenuVO>> findMenuTree() {
		return Result.success(rbacService.findMenuTree());
	}

	@GetMapping("/findBackgroundAccountByPage")
	@ResponseBody
	public Result<PageResult<BackgroundAccountVO>> findBackgroundAccountByPage(BackgroundAccountQueryCondParam param) {
		return Result.success(rbacService.findBackgroundAccountByPage(param));
	}

	@OperLog(subSystem = Constant.子系统_后台管理, module = 模块_后台账号, operate = "修改登录密码")
	@PostMapping("/modifyLoginPwd")
	@ResponseBody
	public Result<String> modifyLoginPwd(String id, String newPwd) {
		rbacService.modifyLoginPwd(id, newPwd);
		return Result.success();
	}

	@OperLog(subSystem = Constant.子系统_后台管理, module = 模块_后台账号, operate = "编辑账号")
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

	@OperLog(subSystem = Constant.子系统_后台管理, module = 模块_后台账号, operate = "删除账号")
	@GetMapping("/delBackgroundAccount")
	@ResponseBody
	public Result<String> delBackgroundAccount(String id) {
		rbacService.delBackgroundAccount(id);
		return Result.success();
	}

	@OperLog(subSystem = Constant.子系统_后台管理, module = 模块_后台账号, operate = "新增账号")
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
