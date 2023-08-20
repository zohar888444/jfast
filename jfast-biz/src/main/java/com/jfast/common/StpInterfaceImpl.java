package com.jfast.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;

@Component
public class StpInterfaceImpl implements StpInterface {

	@Override
	public List<String> getPermissionList(Object loginId, String loginType) {
		List<String> list = new ArrayList<String>();
		return list;
	}

	@Override
	public List<String> getRoleList(Object loginId, String loginType) {
		List<String> list = new ArrayList<String>();
		String subSystem = StpUtil.getSession().getString("subSystem");
		list.add(subSystem);
		return list;
	}

}
