//控制层
//所有部门通用的控制层，对所有部门功能的提取
app.controller('commonCollegeController' ,function($scope, $controller, $location, commonCollegeService) {

    $controller('finalController',{$scope:$scope});

    $controller('pageController',{$scope:$scope});

    $scope.user = {};
    $scope.collegeCid = 0;
    $scope.selectOption = {
        jobNumber:"",
        name:"",
        identityCard:""
    }

    //查询实体
    $scope.searchPage = function (page,size) {
        commonCollegeService.findUserOfCollegeByCidInit($scope.collegeCid,$scope.selectOption,page,size).success(
            function (response) {
                $scope.collegeUsers = response.list;
                $scope.paginationConf.totalItems = response.total;
            }
        );
    }
    $scope.initAttr = function (cid) {
        $scope.collegeCid = cid;
    }
    $scope.initCid = function(){
        let collegeId = $location.search()['collegeId'];
        $scope.collegeCid = collegeId;
    }
    $scope.deleteUser = function () {
        commonCollegeService.deleteUser($scope.selectIds).success(
            function (response) {
                if(response.flag){
                    alert("删除成功");
                }
                $scope.reloadList();
        })
    }
    $scope.updateUser = function () {
        if($scope.selectIds.length == 0){
            alert("请勾选对应的文本框，最多勾选一个");
        }
        if($scope.selectIds.length == 1){
            console.log($scope.selectIds[0]);
            window.location = "../upload-person.html#?selectIds=" + $scope.selectIds[0];
        }
        if($scope.selectIds.length > 1){
            alert("最多只能勾选一个");
        }
    }

    //客户端用@RequestParam接收
    $scope.export = function () {
        commonCollegeService.findOne().success(
            function(response){
                $scope.user = response.data;
                $scope.download('/download/export/'+$scope.collegeCid+'/'+$scope.user.author,$scope.selectOption);
            }
        );
    }
    $scope.download = function(url,data){
        let inputs = '';
        $.each(data, function(name, value) {
            inputs += '<input type="hidden" name="'+ name +'" value="'+ value +'" />';
        });
        console.log(inputs);
        $('<form action="'+ url +'" method="post">'+inputs+'</form>')
            .appendTo('body').submit().remove();
    }


})