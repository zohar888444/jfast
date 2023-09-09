package com.jfast.log.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.jfast.background.domain.OperLog;
import com.jfast.background.repo.OperLogRepo;
import com.jfast.common.vo.PageResult;
import com.jfast.log.param.OperLogQueryCondParam;
import com.jfast.log.vo.OperLogVO;

@Validated
@Service
public class OperLogService {

	@Autowired
	private OperLogRepo operLogRepo;

	@Transactional(readOnly = true)
	public PageResult<OperLogVO> findOperLogByPage(@Valid OperLogQueryCondParam param) {
		Page<OperLog> result = operLogRepo.findAll(param.buildSpecification(),
				PageRequest.of(param.getPageNum() - 1, param.getPageSize(), Sort.by(Sort.Order.desc("operTime"))));
		PageResult<OperLogVO> pageResult = new PageResult<>(OperLogVO.convertFor(result.getContent()),
				param.getPageNum(), param.getPageSize(), result.getTotalElements());
		return pageResult;
	}

	@Transactional
	public void recordOperLog(OperLog operLog) {
		operLogRepo.save(operLog);
	}

}
