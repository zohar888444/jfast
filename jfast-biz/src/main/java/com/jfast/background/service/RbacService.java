package com.jfast.background.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.jfast.background.domain.BackgroundAccount;
import com.jfast.background.param.AddBackgroundAccountParam;
import com.jfast.background.param.BackgroundAccountEditParam;
import com.jfast.background.param.BackgroundAccountQueryCondParam;
import com.jfast.background.repo.BackgroundAccountRepo;
import com.jfast.background.vo.BackgroundAccountVO;
import com.jfast.common.exception.BizException;
import com.jfast.common.vo.PageResult;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.StrUtil;

@Validated
@Service
public class RbacService {

	@Autowired
	private BackgroundAccountRepo backgroundAccountRepo;

	@Transactional
	public void updateBackgroundAccount(@Valid BackgroundAccountEditParam param) {
		BackgroundAccount existAccount = backgroundAccountRepo.findByUserNameAndDeletedFlagIsFalse(param.getUserName());
		if (existAccount != null && !existAccount.getId().equals(param.getId())) {
			throw new BizException("账号已存在");
		}
		if (existAccount != null && existAccount.getSuperAdminFlag()) {
			throw new BizException("无权操作超级管理员");
		}
		BackgroundAccount userAccount = backgroundAccountRepo.getOne(param.getId());
		BeanUtils.copyProperties(param, userAccount);
		backgroundAccountRepo.save(userAccount);
	}

	@Transactional(readOnly = true)
	public PageResult<BackgroundAccountVO> findBackgroundAccountByPage(@Valid BackgroundAccountQueryCondParam param) {
		Specification<BackgroundAccount> spec = new Specification<BackgroundAccount>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Predicate toPredicate(Root<BackgroundAccount> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(builder.equal(root.get("deletedFlag"), false));
				predicates.add(builder.equal(root.get("superAdminFlag"), false));
				if (StrUtil.isNotEmpty(param.getUserName())) {
					predicates.add(builder.like(root.get("userName"), "%" + param.getUserName() + "%"));
				}
				return predicates.size() > 0 ? builder.and(predicates.toArray(new Predicate[predicates.size()])) : null;
			}
		};
		Page<BackgroundAccount> result = backgroundAccountRepo.findAll(spec, PageRequest.of(param.getPageNum() - 1,
				param.getPageSize(), Sort.by(Sort.Order.desc("registeredTime"))));
		PageResult<BackgroundAccountVO> pageResult = new PageResult<>(
				BackgroundAccountVO.convertFor(result.getContent()), param.getPageNum(), param.getPageSize(),
				result.getTotalElements());
		return pageResult;
	}

	@Transactional
	public void delBackgroundAccount(@NotBlank String accountId) {
		BackgroundAccount account = backgroundAccountRepo.getOne(accountId);
		if (account.getSuperAdminFlag()) {
			throw new BizException("无权操作超级管理员");
		}
		account.deleted();
		backgroundAccountRepo.save(account);
	}

	@Transactional
	public void addBackgroundAccount(@Valid AddBackgroundAccountParam param) {
		BackgroundAccount userAccount = backgroundAccountRepo.findByUserNameAndDeletedFlagIsFalse(param.getUserName());
		if (userAccount != null) {
			throw new BizException("账号已存在");
		}
		param.setLoginPwd(SaSecureUtil.sha256(param.getLoginPwd()));
		BackgroundAccount newUserAccount = param.convertToPo();
		backgroundAccountRepo.save(newUserAccount);
	}

}
