//服务层
app.service('entryCertificateService',function($http){
	//更新常规证书图片信息
    this.uploadEntryCertificate = function(entryCertificate){
        return $http.put('/entry/update/',entryCertificate);
    }
    this.showEntryCertificate = function(uid){
        return $http.get('/entry/show/'+uid);
    }
});
