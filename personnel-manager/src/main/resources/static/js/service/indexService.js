//服务层
app.service('indexService',function($http){
	//读取列表数据绑定到表单中
	this.getUser = function(){
		return $http.get('/user/get');
	}
	this.getMenuByAuthor = function (uid) {
        return $http.get('/menu/findMenu/'+uid);
    }
    this.updatePassword = function (oldPassword, newPassword) {
        return $http.put('/update/'+oldPassword+'/'+newPassword);
    }
});
