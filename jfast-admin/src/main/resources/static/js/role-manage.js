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
			
			showAssignMenuDialogFlag: false,
			menuTrees: [],
			selectedMenuIds: [],
			selectedAllMenu: false,
		}
	},
	mounted: function () {
		this.refreshTable();
		this.findMenuTree();
	},
	methods: {
		
		toggleSelectedAllMenu: function () {
			if (this.selectedAllMenu) {
				var selectedMenuIds = [];
				for (var i = 0; i < this.menuTrees.length; i++) {
					var menuTree = this.menuTrees[i];
					for (var j = 0; j < menuTree.subMenus.length; j++) {
						var subMenu = menuTree.subMenus[j];
						selectedMenuIds.push(subMenu.id);
					}
					selectedMenuIds.push(menuTree.id);
				}
				this.selectedMenuIds = selectedMenuIds;
			} else {
				this.selectedMenuIds = [];
			}
		},

		assignMenu: function () {
			var that = this;
			axios.post('/rbac/assignMenu', {
				roleId: that.selectedRoleId,
				menuIds: that.selectedMenuIds
			}, {
				headers: {
				}
			}).then(function (response) {
				that.$notify('success', {
					title: '提示',
					content: '操作成功'
				});
				that.showAssignMenuDialogFlag = false;
				that.refreshTable();
			});
		},

		findMenuTree: function () {
			var that = this;
			axios.get('/rbac/findMenuTree', {
				params: {
				}
			}).then(function (response) {
				that.menuTrees = response.data.data;
			});
		},

		showAssignMenuDialog: function (id) {
			var that = this;
			that.showAssignMenuDialogFlag = true;
			that.selectedAllMenu = false;
			that.selectedRoleId = id;
			axios.get('/rbac/findMenuByRoleId', {
				params: {
					roleId: id
				}
			}).then(function (response) {
				var selectedMenus = response.data.data;
				var selectedMenuIds = [];
				for (var i = 0; i < selectedMenus.length; i++) {
					var selectedMenu = selectedMenus[i];
					for (var j = 0; j < selectedMenu.subMenus.length; j++) {
						var subMenu = selectedMenu.subMenus[j];
						selectedMenuIds.push(subMenu.id);
					}
					selectedMenuIds.push(selectedMenu.id);
				}
				that.selectedMenuIds = selectedMenuIds;
			});
		},

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