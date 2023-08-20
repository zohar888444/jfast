Vue.use(TDesign);
var appVM = new Vue({
	el : '#q-app',
	data : function() {
		return {
			formData: {
				userName: ''
			},

			tableData: [],
			isLoading: false,
			columns: [{
				colKey: 'userName',
				title: '账号'
			}, {
				colKey: 'state',
				title: '状态'
			}, {
				colKey: 'security_setting',
				title: '安全设置',
			}, {
				colKey: 'registeredTime',
				title: '注册时间',
			}, {
				colKey: 'latelyLoginTime',
				title: '最近登录时间',
			}, {
				colKey: 'action',
				title: '操作',
			}],
			pagination: {
				current: 1,
				pageSize: 10
			},

			showAddDialogFlag : false,
			selectedAccount : '',
			selectedAccountId : '',
			functionStateDictItems : [{
				dictItemCode : '1',
				dictItemName : '启用'
			},{
				dictItemCode : '0',
				dictItemName : '禁用'
			}],
			showEditDialogFlag : false,
			showAssignRoleDialogFlag : false,
			roleDictItems : [],
			selectedRoleIds : [],

			showUpdateLoginPwdDialogFlag : false,
			pwd : '',
		}
	},
	mounted : function() {
		this.loadTableData();
	},
	methods : {

		showUpdateLoginPwdDialog: function (id) {
			this.selectedAccountId = id;
			this.pwd = '';
			this.showUpdateLoginPwdDialogFlag = true;
		},

		updateLoginPwd : function(id) {
			var that = this;
			var that = this;
			if (that.pwd === null || that.pwd === '') {
				this.$message('warning', '请输入密码');
				return;
			}
			axios.post('/rbac/modifyLoginPwd', {
				id: that.selectedAccountId,
				newPwd: that.pwd
			}, {
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			}).then(function (response) {
				that.$notify('success', {
					title: '提示',
					content: '操作成功'
				});
				that.showUpdateLoginPwdDialogFlag = false;
				that.refreshTable();
			});
		},

		delAccount : function(id) {
			var that = this;
			var confirmDia = that.$dialog.confirm({
				header: '提示',
				body: '确定要删除吗',
				confirmBtn: '确定',
				cancelBtn: '取消',
				onConfirm: function () {
					axios.get('/rbac/delBackgroundAccount', {
						params: {
							id: id
						}
					}).then(function (response) {
						that.$notify('success', {
							title: '提示',
							content: '操作成功'
						});
						that.refreshTable();
						confirmDia.destroy();
					});
				},
				onClose: function () {
					confirmDia.destroy();
				},
			});
		},

		updateAccount : function() {
			var that = this;
			var selectedAccount = that.selectedAccount
			if (selectedAccount.userName === null || selectedAccount.userName === '') {
				this.$message('warning', '请输入账号');
				return;
			}
			if (selectedAccount.state === null || selectedAccount.state === '') {
				this.$message('warning', '请选择状态');
				return;
			}
			axios.post('/rbac/updateBackgroundAccount', selectedAccount, {
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			}).then(function (response) {
				that.$notify('success', {
					title: '提示',
					content: '操作成功'
				});
				that.showEditDialogFlag = false;
				that.refreshTable();
			});
		},

		showEditDialog : function(id) {
			var that = this;
			axios.get('/rbac/findBackgroundAccountById', {
				params: {
					id: id
				}
			}).then(function (response) {
				that.selectedAccount = response.data.data;
				that.showEditDialogFlag = true;
			});
		},

		addAccount : function() {
			var that = this;
			var selectedAccount = that.selectedAccount;
			if (selectedAccount.userName === null || selectedAccount.userName === '') {
				this.$message('warning', '请输入账号');
				return;
			}
			if (selectedAccount.loginPwd === null || selectedAccount.loginPwd === '') {
				this.$message('warning', '请输入登录密码');
				return;
			}
			axios.post('/rbac/addBackgroundAccount', selectedAccount, {
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			}).then(function (response) {
				that.$notify('success', {
					title: '提示',
					content: '操作成功'
				});
				that.showAddDialogFlag = false;
				that.refreshTable();
			});
		},

		showAddDialog : function() {
			this.showAddDialogFlag = true;
			this.selectedAccount = {
				userName : '',
				loginPwd : ''
			};
		},

		refreshTable: function () {
			this.pagination.current = 1;
			this.loadTableData();
		},

		onPageChange: function (pageInfo) {
			this.pagination.current = pageInfo.current;
			this.pagination.pageSize = pageInfo.pageSize;
			this.loadTableData();
		},

		getQueryParam: function () {
			var that = this;
			return that.formData;
		},

		loadTableData: function () {
			var that = this;
			that.isLoading = true;
			var queryParam = that.getQueryParam();
			queryParam.pageSize = that.pagination.pageSize;
			queryParam.pageNum = that.pagination.current;
			axios.get('/rbac/findBackgroundAccountByPage', {
				params: queryParam
			}).then(function (response) {
				var data = response.data.data;
				that.pagination.total = data.total;
				that.tableData = data.content;
				that.isLoading = false;
			});
		},

	},
});