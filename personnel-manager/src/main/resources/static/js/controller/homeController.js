 //控制层 
app.controller('homeController' ,function($scope, userService ,newsService){
    //获取最近的公告信息
    $scope.initNews = function () {
        newsService.findAllNews(1,5).success(
            function (response) {
                $scope.news = response.list;
            }
        )
    }
    $scope.getUser = function(){
        userService.findOne().success(
            function (response) {
                $scope.user = response.data;
            }
        )
    }

    $scope.getUserTotal = function () {
        userService.getUserTotal().success(
            function (response) {
                $scope.userTotal = response.data;
            }
        )
    }
});	
