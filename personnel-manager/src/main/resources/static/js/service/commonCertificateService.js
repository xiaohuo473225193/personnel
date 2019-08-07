//服务层
app.service('commonCertificateService',function($http){
	//更新常规证书图片信息
    this.uploadCommonCertificate = function(commonCertificate){
        return $http.put('/common/update/',commonCertificate);
    }
    this.showCommonCertificate = function(uid){
        return $http.get('/common/show/'+uid);
    }
    this.download = function (uid) {
        return $http({
                    method : "post",
                    url : '/download/upload/'+uid,
                    timeout : 50000,
                    responseType: "blob"   //注意此参数
                });
    }

    /*$http({
        method: 'POST',
        url: '/download/upload/'+uid,
        data: {fileName: name},
        responseType: 'arraybuffer'
    }).success(function (data, status, headers) {
        headers = headers();
        var contentType = headers['content-type'];
        var linkElement = document.createElement('a');
        try {
            var blob = new Blob([data], {type: contentType});
            var url = window.URL.createObjectURL(blob);
            linkElement.setAttribute('href', url);
            linkElement.setAttribute("download", name);
            var clickEvent = new MouseEvent("click", {
                "view": window,
                "bubbles": true,
                "cancelable": false
            });
            linkElement.dispatchEvent(clickEvent);
        } catch (ex) {
            console.log(ex);
        }
    }).error(function (data) {
        console.log(data);
    });*/
});
