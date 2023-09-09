package com.jfast.admin.background.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jfast.common.vo.PageResult;
import com.jfast.common.vo.Result;
import com.jfast.log.param.OperLogQueryCondParam;
import com.jfast.log.service.OperLogService;
import com.jfast.log.vo.OperLogVO;

@Controller
@RequestMapping("/operLog")
public class OperLogController {

	@Autowired
	private OperLogService operLogService;

	@GetMapping("/findOperLogByPage")
	@ResponseBody
	public Result<PageResult<OperLogVO>> findOperLogByPage(OperLogQueryCondParam param) {
		return Result.success(operLogService.findOperLogByPage(param));
	}

}
