//服务层
app.service('stageCertificateService',function($http){
	//更新常规证书图片信息
    this.uploadStageCertificate = function(stageCertificate){
        return $http.put('/stage/update/',stageCertificate);
    }
    this.showStageCertificate = function(uid){
        return $http.get('/stage/show/'+uid);
    }
});
