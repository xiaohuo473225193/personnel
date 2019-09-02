//服务层
app.service('menuService',function($http){

    this.addMenu = function (menu) {
		return $http.post('/menu/addMenu',menu);
    }
    this.deleteMenu = function (id) {
        return $http.delete('/menu/deleteMenu'+ id);
    }
    this.updateMenu = function (menu) {
        return $http.put('/menu/updateMenu',menu);
    }
    this.findMenu = function (author) {
        return $http.get('/menu/findMenu' + author);
    }
    this.initZtree = function () {
        return $http.get('/menu/load/parent');
    }
    this.removeMenu = function (id) {
        return $http.delete('/menu/delete/'+id);
    }
    this.renameMenu = function (id, name) {
        return $http.put('/menu/rename/'+id+'/'+name);
    }
    this.dropToMenu = function(sourceId,targetId){
        return $http.put('/menu/drop/'+sourceId+'/'+targetId);
    }
    //添加一个新的具体部门
    this.addMenuNode = function (pid, id, name) {
        return $http.post('/menu/add/'+pid+'/'+id+'/'+name);
    }
    //添加一个新的目录
    this.addRootMenuNode = function(name){
        return $http.post('/menu/root/'+name);
    }
    //根据所给菜单id，查询下一级菜单
    this.loadMenuById = function (id) {
        return $http.get('/menu/loadMenu/'+id);
    }
});
