//控制层
//所有部门通用的控制层，对所有部门功能的提取
app.controller('searchController' ,function($scope, $location, $controller, userService) {

    $controller('finalController',{$scope:$scope});

    $controller('pageController',{$scope:$scope});

    //定义查询结果
    $scope.keywords = {};
    $scope.searchPage = function (page,size) {
        let keyword = $location.search()['keyword']; // 获取路径的参数
        let author = $location.search()['author']; // 获取路径的参数
        console.log(author +"," + keyword);
        if(!$scope.StringNotBlank(keyword) || !$scope.StringNotBlank(author)){
            return;
        }
        $scope.keywords.keyword = keyword;
        $scope.keywords.author = author;
        userService.searchKeyword($scope.keywords,page,size).success(
            function (response) {
                if(response.flag){
                    $scope.collegeUsers = response.data.list;
                    $scope.paginationConf.totalItems = response.data.total;
                }else{
                    alert(response.data);
                }
            }
        )
    }

    //判断字符串不能为空
    $scope.StringNotBlank = function(str){
        if(str != "" && str != null && str != undefined){
            return true;
        }else{
            return false;
        }
    }
})