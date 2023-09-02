Vue.use(TDesign);
var appVM = new Vue({
	el : '#q-app',
	data : function() {
		return {
			formData : {
				userName : '',
				ipAddr : '',
				state : ''
			},
			loginStateDictItems : [],

			tableData : [],
			isLoading : false,
			columns : [ {
				colKey : 'userName',
				title : '登录账号'
			}, {
				colKey : 'ipAddr',
				title : 'ip地址'
			}, {
				colKey : 'subSystem',
				title : '子系统'
			}, {
				colKey : 'browser',
				title : '浏览器'
			}, {
				colKey : 'os',
				title : '操作系统'
			}, {
				colKey : 'login_state',
				title : '登录状态',
			}, {
				colKey : 'loginTime',
				title : '登录时间',
			} ],
			pagination : {
				current : 1,
				pageSize : 10
			},
		}
	},
	mounted : function() {
		this.findLoginStateDictItem();
		this.loadTableData();
	},
	methods : {

		findLoginStateDictItem : function() {
			var that = this;
			axios.get('/dictconfig/findDictItemInCache', {
				params : {
					dictTypeCode : 'loginState'
				}
			}).then(function(response) {
				that.loginStateDictItems = response.data.data;
			});
		},

		refreshTable : function() {
			this.pagination.current = 1;
			this.loadTableData();
		},

		onPageChange : function(pageInfo) {
			this.pagination.current = pageInfo.current;
			this.pagination.pageSize = pageInfo.pageSize;
			this.loadTableData();
		},

		getQueryParam : function() {
			var that = this;
			var loginTimeRange = that.$refs.loginTimeRangePicker.inputValue;
			if (loginTimeRange.length > 0) {
				that.formData.timeStart = loginTimeRange[0] + ' 00:00:00';
				that.formData.timeEnd = loginTimeRange[1] + ' 23:59:59';
			}
			return that.formData;
		},

		loadTableData : function(param) {
			var that = this;
			that.isLoading = true;
			var queryParam = that.getQueryParam();
			queryParam.pageSize = that.pagination.pageSize;
			queryParam.pageNum = that.pagination.current;
			axios.get('/loginLog/findLoginLogByPage', {
				params : queryParam
			}).then(function(response) {
				var data = response.data.data;
				that.pagination.total = data.total;
				that.tableData = data.content;
				that.isLoading = false;
			});
		}

	},
});