//控制层
app.controller('menuController' ,function($scope, menuService) {
    //
    $scope.addMenu = function () {
        menuService.addMenu($scope.menu).success(
            function (response) {
                $scope.menu = response.data;
            }
        );
    }

    $scope.deleteMenu = function () {
        menuService.deleteMenu($scope.id).success(
            function (response) {
                $scope.menu = response.data;
            }
        );
    }

    $scope.updeteMenu = function () {
        menuService.updeteMenu($scope.menu).success(
            function (response) {
                $scope.menu = response.data;
            }
        );
    }

    $scope.findMenu = function () {
        menuService.findMenu($scope.author).success(
            function (response) {
                $scope.menu = response.data
            }
        )
    }
})