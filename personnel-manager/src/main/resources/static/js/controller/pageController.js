app.controller('pageController',function($scope){
	//分页控件配置
	$scope.paginationConf = {
			currentPage : 1,//当前页
			totalItems : 10,//总记录数
			itemsPerPage : 10,//每页记录数
			perPageOptions : [10,20,30,40,50],//分页选项
			onChange : function(){
				//当页码变更后自动触发方法
				$scope.reloadList();
			}
	}
	$scope.reloadList = function(){
		$scope.findUserOfCollegeByCidInit($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
	}

	//用户勾选的ID集合
	$scope.selectIds = [];
	$scope.updateSelection = function($event,id){
		if($event.target.checked){
			//向集合添加元素
			$scope.selectIds.push(id);
		}else{
			var index = $scope.selectIds.indexOf(id);//查询元素在集合中的位置
			$scope.selectIds.splice(index,1);//从集合中删除元素，需要元素位置和从位置开始的删除个数
		}
	}
	$scope.jsonToString=function(jsonString,key){
		//转换成json对象
		var json = JSON.parse(jsonString);
		var value = "";
		for(var i=0;i<json.length;i++){
			//key值确定，就用.key，如果不确定就是用a[key]
			value += "," + json[i][key];
		}
		value = value.substring(1);
		return value;
	}
});