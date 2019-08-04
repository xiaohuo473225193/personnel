//控制层
app.controller('newsController' ,function($scope, newsService) {
    //查询实体
    $scope.addNews = function () {
        newsService.addNews().success(
            function (response) {
                $scope.news = response.data;
            }
        );
    }
})