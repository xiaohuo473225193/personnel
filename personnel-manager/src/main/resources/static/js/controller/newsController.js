//控制层 $sce angularjs bind 带 html 格式的文本 否则报Error: [$sce:unsafe] http://errors.angularjs.org/1.2.28/$sce/unsafe
app.controller('newsController' ,function($scope, $controller, $location, $sce, newsService) {

    $controller('pageController',{$scope:$scope});

    $scope.entity = {};
    //显示公告具体内容
    $scope.showNews = function(){
        let id = $location.search()['id']; // 获取路径的参数
        newsService.findById(id).success(
            function (response) {
                $scope.news = response.data;
                //转换成html显示
                $scope.news.content = $sce.trustAsHtml($scope.news.content);
            }
        )
    }
    //查询实体
    $scope.addNews = function () {
        $scope.entity.content = editor.html();//获取内容
        newsService.addNews($scope.entity).success(
            function (response) {
                if(response.flag){
                    alert("发布成功");
                    editor.html("");//清空
                    $scope.entity = "";
                }else{
                    alert("发布失败");
                }
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