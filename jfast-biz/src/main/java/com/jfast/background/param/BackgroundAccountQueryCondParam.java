package com.jfast.background.param;

import com.jfast.common.param.PageParam;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BackgroundAccountQueryCondParam extends PageParam {

	private String userName;

}
