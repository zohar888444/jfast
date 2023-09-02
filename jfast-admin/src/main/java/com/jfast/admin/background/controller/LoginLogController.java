package com.jfast.admin.background.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jfast.common.vo.PageResult;
import com.jfast.common.vo.Result;
import com.jfast.log.param.LoginLogQueryCondParam;
import com.jfast.log.service.LoginLogService;
import com.jfast.log.vo.LoginLogVO;

@Controller
@RequestMapping("/loginLog")
public class LoginLogController {

	@Autowired
	private LoginLogService loginLogService;

	@GetMapping("/findLoginLogByPage")
	@ResponseBody
	public Result<PageResult<LoginLogVO>> findLoginLogByPage(LoginLogQueryCondParam param) {
		return Result.success(loginLogService.findLoginLogByPage(param));
	}

}
