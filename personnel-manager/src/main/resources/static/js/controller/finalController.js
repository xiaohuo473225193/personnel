//常量，所有固定的值存放的地方，要与数据库对应起来，否则会出现错误
//---------------------------------------
//存放常量的地方，包含的常量有:
//      男女选项
//      下拉框的固定类型值
app.controller('finalController',function($scope){
    // form-common.html 男女选项
    $scope.genders = [
        {id:"1", name:"男"},
        {id:"2", name:"女"}
    ];
    // form-common.html 下拉框的固定类型值
    $scope.type = {
        education:"002",
        title:"003",
        degree:"005",
        jobStatus:"006",
        level:"007"
    }
});