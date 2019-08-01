//服务层
app.service('userService',function($http){
	//读取列表数据绑定到表单中
	this.findOne = function(){
		return $http.get('/user/findOne');
	}
	this.select = function(type){
		return $http.get('/base/list/' + type);
	}
	this.findCollegeList = function () {
        return $http.get('/college/list');
    }
});
