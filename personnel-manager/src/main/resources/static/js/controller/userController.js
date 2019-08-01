 //控制层 
app.controller('userController' ,function($scope, $controller, userService){

    $controller('finalController',{$scope:$scope});
    //加载初始化
	$scope.init = function(){
        $scope.degreeSelect();
        $scope.titleSelect();
        $scope.educationSelect();
        $scope.jobStatusSelect();
        $scope.levelSelect();
        $scope.findCollegeList();
        $scope.findOne();
	}
	//查询实体 
	$scope.findOne=function(){
        userService.findOne().success(
			function(response){
				$scope.user = response.data;
			}
		);				
	}
	//最高学位查询
	$scope.degreeSelect = function(){
        userService.select($scope.type.degree).success(
        	function(response){
				$scope.degreeList = response.data;
			}
		)
	}
    //最高职称查询
    $scope.titleSelect = function(){
        userService.select($scope.type.title).success(
            function(response){
                $scope.titleList = response.data;
            }
        )
    }
    //最高学历查询
    $scope.educationSelect = function(){
        userService.select($scope.type.education).success(
            function(response){
                $scope.educationList = response.data;
            }
        )
    }
    //入职状态查询
    $scope.jobStatusSelect = function(){
        userService.select($scope.type.jobStatus).success(
            function(response){
                $scope.jobStatusList = response.data;
            }
        )
    }
    //人员类型查询
    $scope.levelSelect = function(){
        userService.select($scope.type.level).success(
            function(response){
                $scope.levelList = response.data;
            }
        )
    }
    //查询全部部门
    $scope.findCollegeList = function(){
        userService.findCollegeList().success(
            function(response){
                $scope.collegeList = response.data;
            }
        )
    }
    //保存按钮
	$scope.save = function() {
		//获取3个无法双向绑定的时间
		var birthday = $('#form_birthday').val();
        var startTime = $('#form_startTime').val();
		var graduateTime = $('#form_graduateTime').val();
        //console.log(birthday + " - " + startTime + " - " + graduateTime);
        //确定提交
        /*if(confirm('确定要提交并数据吗?') == true){
            $.myToast('保存成功');
        	console.log(birthday + " - " + startTime + " - " + graduateTime);
		}else{
        	return;
		}*/
    }
});	
