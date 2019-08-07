//控制层
//所有部门通用的控制层，对所有部门功能的提取
app.controller('commonCollegeController' ,function($scope, $controller,commonCollegeService) {

    $controller('finalController',{$scope:$scope});

    $controller('pageController',{$scope:$scope});

    $scope.collegeCid = 0;
    //查询实体
    $scope.findUserOfCollegeByCidInit = function (page,size) {
        commonCollegeService.findUserOfCollegeByCidInit($scope.collegeCid,page,size).success(
            function (response) {
                $scope.collegeUsers = response.list;
                $scope.paginationConf.totalItems = response.total;
            }
        );
    }
    $scope.initAttr = function (cid) {
        $scope.collegeCid = cid;
    }
})