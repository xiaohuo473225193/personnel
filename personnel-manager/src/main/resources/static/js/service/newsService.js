//服务层
app.service('newsService',function($http){
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

    this.save = function (user) {
        return $http.put('/user/save',user);
    }



    this.addNews = function (news) {
		return $http.post('/news/addNews',news);
    }
    this.deleteNews = function (id) {
        return $http.delete('/news/deleteNews'+id);
    }
    this.updateNews = function (news) {
        return $http.put('/news/updateNews',news);
    }
    this.findAllNews = function () {
        return $http.get('/news/findAllNews');
    }
});
