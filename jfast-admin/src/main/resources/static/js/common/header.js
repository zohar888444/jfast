var headerVM = new Vue({
	el : '#header',
	data : function() {
		return {
			userName : '',
			activedMenu : '',
			currentPathName : '',
			menus : [],
		}
	},
	mounted : function() {
		var that = this;
		that.activedMenu = window.location.pathname;
		that.findLoginAccountMenuTree();
		that.getLoginAccountInfo();
	},
	methods : {

		logout : function() {
			var that = this;
			axios.post('/logout', {}, {
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				}
			}).then(function(response) {
				that.$notify('success', {
					title : '提示',
					content : '退出成功',
					duration : 1500,
					onDurationEnd : function() {
						window.localStorage.removeItem('tokenName');
						window.location.href = '/page/login';
					}
				});
			});
		},

		getLoginAccountInfo : function() {
			var that = this;
			axios.get('/rbac/getAccountInfo', {
				params : {}
			}).then(function(response) {
				that.userName = response.data.data.userName;
			});
		},

		refreshCache : function() {
			var that = this;
			axios.post('/setting/refreshCache', {}, {
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				}
			}).then(function(response) {
				that.$notify('success', {
					title : '提示',
					content : '操作成功',
					duration : 1000,
					onDurationEnd : function() {
						window.location.reload();
					}
				});
			});
		},

		findLoginAccountMenuTree : function() {
			var that = this;
			axios.get('/rbac/findMyMenuTree', {
				params : {}
			}).then(function(response) {
				that.menus = response.data.data;
			});
		},

		hasSubMenu : function(menu) {
			return menu.subMenus.length != 0;
		},

		navTo : function(url) {
			window.location.href = url;
		}
	},
});