//控制层
app.controller('collegeController' ,function($scope, collegeService) {
    //查询实体
    $scope.addCollege = function () {
        collegeService.addCollege($scope.college).success(
            function (response) {
                $scope.college = response.data;
            }
        );
    }

    $scope.deleteCollege = function () {
        collegeService.deleteCollege($scope.id).success(
            function (response) {
                $scope.College = response.data;
            }
        );
    }

    $scope.updeteCollege = function () {
        collegeService.updeteCollege($scope.college).success(
            function (response) {
                $scope.college = response.data;
            }
        );
    }

    $scope.findCollege = function () {
        collegeService.findCollege().success(
            function (response) {
                $scope.college = response.data
            }
        )
    }
})