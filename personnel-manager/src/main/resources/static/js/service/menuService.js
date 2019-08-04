//服务层
app.service('menuService',function($http){

    this.addMenu = function (menu) {
		return $http.post('/menu/addMenu',menu);
    }
    this.deleteMenu = function (id) {
        return $http.delete('/menu/deleteMenu'+id);
    }
    this.updateMenu = function (menu) {
        return $http.put('/menu/updateMenu',menu);
    }
    this.findAllMenu = function () {
        return $http.get('/menu/findAllMenu');
    }
});
