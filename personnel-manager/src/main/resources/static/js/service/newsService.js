//服务层
app.service('newsService',function($http){

    this.addNews = function (news) {
		return $http.post('/news/addNews',news);
    }
    this.deleteNews = function (id) {
        return $http.delete('/news/deleteNews'+ id);
    }
    this.updateNews = function (news) {
        return $http.put('/news/updateNews',news);
    }
    this.findAllNews = function (page,size) {
        return $http.get('/news/findAllNews/'+page+'/'+size);
    }
    this.findById = function (id) {
        return $http.get('/news/find/'+id);
    }
});
