window.TRSApp = {};

(function(TRSApp) {

	function ViewModel(options) {
		var self = this;

		// 标题、数据集、弹出对话框和内容（HTML）
		self.title = ko.observable(options.title);
	
		
		self.recordSet = ko.observableArray();
		self.dialogContent = ko.observable();

		self.d = ko.observable();
		
        self.dataUpdataSet = options.dataUpdataSet;
        
		// 排序
		// orderBy，defaultOrderBy & isAsc: 当前排序字段名，默认排序字段名和方向（升序/降序）
		// totalPages， pageNumbers & pageIndex：总页数，页码列表和当前页
		self.orderBy = ko.observable();
		self.isAsc = ko.observable();
		self.defaultOrderBy = options.defaultOrderBy;
		
		// 分页
		// totalPages， pageNumbers & pageIndex：总页数，页码列表和当前页
		self.totalPages = ko.observable();
		self.pageNumbers = ko.observableArray();
		self.pageIndex =  ko.observable();
         self.pageSize = ko.observable(1);
		// 查询条件：标签和输入值
		self.searchCriteria = ko.observableArray(options.searchCriteria);

		// 作为显示数据的表格的头部：显示文字和对应的字段名（辅助排序）
		self.headers = ko.observableArray(options.headers);

		// CRUD均通过Ajax调用实现，这里提供用于获取Ajax请求地址的方法
		self.dataQueryUrlAccessor = options.dataQueryUrlAccessor;
		self.dataAddUrlAccessor = options.dataAddUrlAccessor;
		self.dataUpdateAccessor = options.dataUpdateAccessor;
		self.dataDeleteAccessor = options.dataDeleteAccessor;

		// removeData：删除操作完成后将数据从recordSet中移除
		// replaceData：修改操作后更新recordSet中相应记录
		self.removeData = options.removeData;
		self.replaceData = options.replaceData;
  
		// Search按钮
		self.search = function() {
			self.orderBy(self.defaultOrderBy);
			self.isAsc(true);
			self.pageIndex(1);

			  $.ajax({
				url : self.dataQueryUrlAccessor(self),
				type : "post",
				success : function(result) {
					self.recordSet(result.pageBean.resultList);
					self.totalPages(result.pageBean.pageTotal);
					self.resetPageNumbders();
				}
			});

//			self.recordSet(userList);
//			self.totalPages(20);
//			self.resetPageNumbders();

		
		};

		// Reset按钮
		self.reset = function() {
			for (var i = 0; i < self.searchCriteria().length; i++) {
				self.searchCriteria()[i].value("");
			}
		};

		// 获取数据之后根据记录数重置页码
		self.resetPageNumbders = function() {
			self.pageNumbers.removeAll();
			var begin = Math.round(Math.max(1, self.pageIndex() - 7 / 2));//循环显示分页
			var end = Math.round(Math.min(begin + (7 - 1), self.totalPages()));//循环显示分页
			for(var i = begin ;i <= end; i++ ){
				self.pageNumbers.push(i);
			}
		
		};

		// 点击表格头部进行排序
		self.sort = function(header) {
			if (self.orderBy() == header.value) {
				self.isAsc(!self.isAsc());
			}
			self.orderBy(header.value);
			self.pageIndex(1);
			$.ajax({
				url : self.dataQueryUrlAccessor(self),
				type : "GET",
				success : function(result) {
					self.recordSet(result.Data);
				}
			});
		};

		// 点击页码获取当前页数据
		self.turnPage = function(pageIndex) {
			self.pageIndex(pageIndex);
			self.resetPageNumbders();
			$.ajax({
				url : self.dataQueryUrlAccessor(self),

				type : "POST",
				success : function(result) {
					self.recordSet(result.pageBean.resultList);
					self.totalPages(result.pageBean.pageTotal);
					self.resetPageNumbders();
				}
			});
		};

		
		
	}
	TRSApp.ViewModel = ViewModel;
}(window.TRSApp));
