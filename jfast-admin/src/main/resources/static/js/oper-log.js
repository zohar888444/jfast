Vue.use(TDesign);
var appVM = new Vue({
	el: '#q-app',
	data: function () {
		return {
			formData: {
				operName: '',
				ipAddr: '',
			},

			tableData: [],
			isLoading: false,
			columns: [{
				colKey: 'operName',
				title: '操作账号'
			}, {
				colKey: 'ipAddr',
				title: 'ip地址'
			}, {
				colKey: 'subSystem',
				title: '子系统'
			}, {
				colKey: 'module',
				title: '模块'
			}, {
				colKey: 'operate',
				title: '操作'
			}, {
				colKey: 'operTime',
				title: '操作时间',
			}],
			pagination: {
				current: 1,
				pageSize: 10
			},
		}
	},
	mounted: function () {
		this.loadTableData();
	},
	methods: {

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
			var operTimeRange = that.$refs.operTimeRangePicker.inputValue;
			if (operTimeRange.length > 0) {
				that.formData.timeStart = operTimeRange[0] + ' 00:00:00';
				that.formData.timeEnd = operTimeRange[1] + ' 23:59:59';
			}
			return that.formData;
		},

		loadTableData: function () {
			var that = this;
			that.isLoading = true;
			var queryParam = that.getQueryParam();
			queryParam.pageSize = that.pagination.pageSize;
			queryParam.pageNum = that.pagination.current;
			axios.get('/operLog/findOperLogByPage', {
				params: queryParam
			}).then(function (response) {
				var data = response.data.data;
				that.pagination.total = data.total;
				that.tableData = data.content;
				that.isLoading = false;
			});
		}

	},
});