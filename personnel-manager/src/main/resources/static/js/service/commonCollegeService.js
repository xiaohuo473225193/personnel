//服务层
app.service('commonCollegeService',function($http){

    this.findUserOfCollegeByCidInit = function (cid,data,page,size) {
		return $http.post('/college/findCollegeUserListByCid/'+cid+'/'+page+'/'+size,data);
    }
    //删除请求不可以携带对象
    this.deleteUser = function (uids) {
        return $http.delete('/user/deleteByUids/'+uids);
    }
    this.findOne = function(){
        return $http.get('/user/get');
    }
    this.loadCollegeName = function(id){
        return $http.get('/college/getName/'+id);
    }
});
