 //控制层 
app.controller('userController' ,function($scope, $location,$controller, userService){

    $controller('finalController',{$scope:$scope});

    $scope.entity = {}; //
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
	$scope.findOne = function(){
        userService.findOne().success(
			function(response){
				$scope.user = response.data;
				//对时间进行格式化，方便用于双向绑定
                $scope.user.birthday = $scope.formatDate($scope.user.birthday);
                $scope.user.startTime = $scope.formatDate($scope.user.startTime);
			}
		);				
	}
    $scope.formatDate = function (val) {
        if (val != null) {
            let date = new Date(val);
            let month = date.getMonth() + 1;
            if(month < 10){
                month = "0" + date.getMonth();
            }
            let time = date.getDate();
            if(time < 10){
                time = "0" + date.getDate();
            }
            return date.getFullYear() + '-' + month + '-' + time;
        }
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
        //确定提交
        $('body').dailog({
            type:'success',
            maskBg:'rgba(88,11,22,0.5)',
            title:'保存',
            discription:'是否确定信息填写正确，并提交?'
        },function(ret) {
            if(ret.index===0) {
                userService.save($scope.user);
                $.myToast('保存成功');
            };
        });
    }

    $scope.addUser = function () {
	    let object = null;
	    if($scope.entity.uid != null){
            object = userService.updeteByUser($scope.entity);
        }else{
            object = userService.addUser($scope.entity.author,$scope.entity);
        }
        object.success(
            function (response) {
              if($scope.entity.uid != null){
                  if(response.flag){
                      alert("更新成功");
                  }else{
                      alert("更新失败");
                  }
              }else{
                  $scope.entity = {};
                  if(response.flag){
                      alert("添加成功");
                  }else{
                      alert("添加失败");
                  }
              }
        })
    }

    $scope.updeteByUser = function () {
        userService.updeteByUser($scope.user).success(
            function (response) {
                $scope.user = response.data
            })
    }

    $scope.findByUid = function () {
        let selectId = $location.search()['selectIds']; // 获取路径的参数
        userService.findByUid(selectId).success(
            function (response) {
               $scope.entity = response.data;
                //对时间进行格式化，方便用于双向绑定
                $scope.entity.birthday = $scope.formatDate($scope.entity.birthday);
                $scope.entity.startTime = $scope.formatDate($scope.entity.startTime);
                if($scope.entity.endTime != "" && $scope.entity.endTime != null && $scope.entity.endTime != undefined){
                    $scope.entity.endTime = $scope.formatDate($scope.entity.endTime);
                }
            }
        )
    }

    $scope.findByExample = function () {
        userService.findByExample($scope.rows,$scope.size,$scope.selectOptionData).success(
            function (response) {
                $scope.userList = response.data
            }
        )
    }
    //返回列表
    $scope.returnList = function(){
        //$scope.selectIds = [];
        window.location.replace(document.referrer);//返回上一页并刷新
    }

});	
