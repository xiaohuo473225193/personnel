//服务层
app.service('commonCertificateService',function($http){
	//更新常规证书图片信息
    this.uploadCommonCertificate = function(commonCertificate){
        return $http.put('/common/update/',commonCertificate);
    }
    this.showCommonCertificate = function(uid){
        return $http.get('/common/show/'+uid);
    }
});
