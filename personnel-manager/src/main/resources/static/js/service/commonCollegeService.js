//服务层
app.service('commonCollegeService',function($http){

    this.findUserOfCollegeByCidInit = function (cid,page,size) {
		return $http.get('/college/findCollegeUserListByCid/'+cid+'/'+page+'/'+size);
    }

});
