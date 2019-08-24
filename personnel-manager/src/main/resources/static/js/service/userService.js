//服务层
app.service('userService',function($http){
	//读取列表数据绑定到表单中
	this.findOne = function(){
		return $http.get('/user/get');
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

    this.addUser=function(author,user){
		return $http.post('/user/addUser/'+author,user);
	}


    this.updeteByUser=function(user){
        return $http.put('/user/updateByUser',user);
    }

    this.findByUid = function (uid) {
        return $http.get('/user/findByUid/' + uid)
    }

    this.findByExample = function (rows,size,selectOptionData) {
		return $http.get("/user/findByExample"+ rows + size ,selectOptionData)
    }
});
