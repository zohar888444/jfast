var appVM = new Vue({
	el : '#q-app',
	data : function() {
		return {
			treeCustomKey: {
				value: 'id',
				label: 'name',
				children: 'subMenus'
			},

			showAddDialogFlag : false,
			selectedMenu : '',
			selectedMenuId : '',
			showEditDialogFlag : false,
			menuTrees : [],
		}
	},
	mounted : function() {
		this.findMenuTree();
	},
	methods : {

		findMenuTree : function() {
			var that = this;
			that.menuTrees = [];
			axios.get('/rbac/findMenuTree', {
				params: {
				}
			}).then(function (response) {
				that.menuTrees = response.data.data;
			});
		},

		delMenu : function(id) {
			var that = this;
			var confirmDia = that.$dialog.confirm({
				header: '提示',
				body: '确定要删除吗',
				confirmBtn: '确定',
				cancelBtn: '取消',
				onConfirm: function () {
					axios.get('/rbac/delMenu', {
						params: {
							id: id
						}
					}).then(function (response) {
						that.$notify('success', {
							title: '提示',
							content: '操作成功'
						});
						that.findMenuTree();
						confirmDia.destroy();
					});
				},
				onClose: function () {
					confirmDia.destroy();
				},
			});
		},

		updateMenu : function() {
			var that = this;
			var selectedMenu = that.selectedMenu
			if (selectedMenu.name === null || selectedMenu.name === '') {
				this.$message('warning', '请输入菜单名');
				return;
			}
			if (selectedMenu.orderNo === null || selectedMenu.orderNo === '') {
				this.$message('warning', '请输入排序号');
				return;
			}
			axios.post('/rbac/addOrUpdateMenu', selectedMenu, {
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			}).then(function (response) {
				that.$notify('success', {
					title: '提示',
					content: '操作成功'
				});
				that.showEditDialogFlag = false;
				that.findMenuTree();
			});
		},

		showEditDialog : function(id) {
			var that = this;
			axios.get('/rbac/findMenuById', {
				params: {
					id : id
				}
			}).then(function (response) {
				that.selectedMenu = response.data.data;
				that.showEditDialogFlag = true;
			});
		},

		addMenu : function() {
			var that = this;
			var selectedMenu = that.selectedMenu;
			if (selectedMenu.name === null || selectedMenu.name === '') {
				this.$message('warning', '请输入菜单名');
				return;
			}
			if (selectedMenu.orderNo === null || selectedMenu.orderNo === '') {
				this.$message('warning', '请输入排序号');
				return;
			}
			axios.post('/rbac/addOrUpdateMenu', selectedMenu, {
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			}).then(function (response) {
				that.$notify('success', {
					title: '提示',
					content: '操作成功'
				});
				that.showAddDialogFlag = false;
				that.findMenuTree();
			});
		},

		showAddDialog : function(parentId, parentName) {
			this.showAddDialogFlag = true;
			this.selectedMenu = {
				name : '',
				url : '',
				orderNo : '',
				parentId : parentId,
				parentName : parentName,
				type : parentId === null ? 'menu_1' : 'menu_2'
			};
		}

	},
});