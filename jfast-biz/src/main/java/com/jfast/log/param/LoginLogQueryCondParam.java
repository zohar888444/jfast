package com.jfast.log.param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import com.jfast.common.param.PageParam;
import com.jfast.log.domain.LoginLog;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginLogQueryCondParam extends PageParam {

	private String ipAddr;

	private String userName;

	private String subSystem;

	private String state;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date timeStart;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date timeEnd;

	public Specification<LoginLog> buildSpecification() {
		LoginLogQueryCondParam param = this;
		Specification<LoginLog> spec = new Specification<LoginLog>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Predicate toPredicate(Root<LoginLog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StrUtil.isNotEmpty(param.getIpAddr())) {
					predicates.add(builder.equal(root.get("ipAddr"), param.getIpAddr()));
				}
				if (StrUtil.isNotEmpty(param.getUserName())) {
					predicates.add(builder.equal(root.get("userName"), param.getUserName()));
				}
				if (StrUtil.isNotEmpty(param.getSubSystem())) {
					predicates.add(builder.equal(root.get("subSystem"), param.getSubSystem()));
				}
				if (StrUtil.isNotEmpty(param.getState())) {
					predicates.add(builder.equal(root.get("state"), param.getState()));
				}
				if (param.getTimeStart() != null) {
					predicates.add(
							builder.greaterThanOrEqualTo(root.get("loginTime").as(Date.class), param.getTimeStart()));
				}
				if (param.getTimeEnd() != null) {
					predicates.add(builder.lessThanOrEqualTo(root.get("loginTime").as(Date.class), param.getTimeEnd()));
				}
				return predicates.size() > 0 ? builder.and(predicates.toArray(new Predicate[predicates.size()])) : null;
			}
		};
		return spec;
	}

}
