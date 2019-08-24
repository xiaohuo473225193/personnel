//控制层
app.controller('newsController' ,function($scope, $controller, newsService) {

    $controller('pageController',{$scope:$scope});
    //查询实体
    $scope.addNews = function () {
        newsService.addNews($scope.news).success(
            function (response) {
                $scope.news = response.data;
            }
        );
    }

    $scope.deleteNews = function () {
        newsService.deleteNews($scope.id).success(
            function (response) {
                $scope.news = response.data;
            }
        );
    }

    $scope.updeteNews = function () {
        newsService.updeteNews($scope.news).success(
            function (response) {
                $scope.news = response.data;
            }
        );
    }

    $scope.searchPage = function (page,size) {
        newsService.findAllNews(page,size).success(
            function (response) {
                $scope.news = response.list;
            }
        )
    }
})