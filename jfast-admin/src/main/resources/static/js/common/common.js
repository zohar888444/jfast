axios.interceptors.request.use(function(config) {
	var tokenName = window.localStorage.getItem('tokenName');
	var tokenValue = window.localStorage.getItem('tokenValue');
	if (tokenName != null && tokenName != '') {
		config.headers[tokenName] = tokenValue;
	}
	return config;
}, function(error) {
	return Promise.reject(error);
});
axios.interceptors.response.use(function(response) {
	if (response.data.code != null && response.data.code != 200) {
		if (response.data.code == 999) {
			appVM.$notify('error', {
				title : '提示',
				content : '系统检测到你已离线,请重新登录!',
				duration : 1500,
				onDurationEnd : function() {
					window.localStorage.removeItem('tokenName');
					window.location.href = '/page/login';
				}
			});
		} else {
			appVM.$message('warning', response.data.msg);
			return Promise.reject(response.data);
		}
	}
	return response;
}, function(error) {
	return Promise.reject(error);
});

