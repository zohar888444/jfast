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

import com.jfast.background.domain.OperLog;
import com.jfast.common.param.PageParam;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OperLogQueryCondParam extends PageParam {

	private String ipAddr;

	private String operName;

	private String operAccountId;

	private String subSystem;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date timeStart;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date timeEnd;

	public Specification<OperLog> buildSpecification() {
		OperLogQueryCondParam param = this;
		Specification<OperLog> spec = new Specification<OperLog>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Predicate toPredicate(Root<OperLog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StrUtil.isNotEmpty(param.getIpAddr())) {
					predicates.add(builder.equal(root.get("ipAddr"), param.getIpAddr()));
				}
				if (StrUtil.isNotEmpty(param.getOperName())) {
					predicates.add(builder.equal(root.get("operName"), param.getOperName()));
				}
				if (StrUtil.isNotEmpty(param.getOperAccountId())) {
					predicates.add(builder.equal(root.get("operAccountId"), param.getOperAccountId()));
				}
				if (StrUtil.isNotEmpty(param.getSubSystem())) {
					predicates.add(builder.equal(root.get("subSystem"), param.getSubSystem()));
				}
				if (param.getTimeStart() != null) {
					predicates.add(
							builder.greaterThanOrEqualTo(root.get("operTime").as(Date.class), param.getTimeStart()));
				}
				if (param.getTimeEnd() != null) {
					predicates.add(builder.lessThanOrEqualTo(root.get("operTime").as(Date.class), param.getTimeEnd()));
				}
				return predicates.size() > 0 ? builder.and(predicates.toArray(new Predicate[predicates.size()])) : null;
			}
		};
		return spec;
	}

}
