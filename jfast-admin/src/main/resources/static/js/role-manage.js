var appVM = new Vue({
	el: '#q-app',
	data: function () {
		return {
			treeCustomKey: {
				value: 'id',
				label: 'name',
				children: 'subMenus'
			},
			roles: [],

			showAddDialogFlag: false,
			selectedRole: '',
			selectedRoleId: '',
			showEditDialogFlag: false,
		}
	},
	mounted: function () {
		this.refreshTable();
	},
	methods: {

		delRole: function (id) {
			var that = this;
			var confirmDia = that.$dialog.confirm({
				header: '提示',
				body: '确定要删除吗',
				confirmBtn: '确定',
				cancelBtn: '取消',
				onConfirm: function () {
					axios.get('/rbac/delRole', {
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

		updateRole: function () {
			var that = this;
			var selectedRole = that.selectedRole
			if (selectedRole.name === null || selectedRole.name === '') {
				this.$message('warning', '请输入角色名');
				return;
			}
			axios.post('/rbac/addOrUpdateRole', selectedRole, {
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

		showEditDialog: function (id) {
			var that = this;
			axios.get('/rbac/findRoleById', {
				params: {
					id: id
				}
			}).then(function (response) {
				that.selectedRole = response.data.data;
				that.showEditDialogFlag = true;
			});
		},

		addRole: function () {
			var that = this;
			var selectedRole = that.selectedRole;
			if (selectedRole.name === null || selectedRole.name === '') {
				this.$message('warning', '请输入角色名');
				return;
			}
			axios.post('/rbac/addOrUpdateRole', selectedRole, {
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

		showAddDialog: function () {
			this.showAddDialogFlag = true;
			this.selectedRole = {
				name: ''
			};
		},

		findAllRole: function () {
			var that = this;
			axios.get('/rbac/findAllRole', {
				params: {}
			}).then(function (response) {
				that.roles = response.data.data;
			});
		},

		refreshTable: function () {
			this.findAllRole();
		},

	},
});