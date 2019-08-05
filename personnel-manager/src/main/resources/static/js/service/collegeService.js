//服务层
app.service('collegeService',function($http){

    this.addCollege = function (college) {
		return $http.post('/college/addCollege',college);
    }
    this.deleteCollege = function (id) {
        return $http.delete('/college/deleteCollege'+ id);
    }
    this.updateCollege = function (college) {
        return $http.put('/college/updateCollege',college);
    }
    this.findCollege = function () {
        return $http.get('/college/findCollege');
    }
});
