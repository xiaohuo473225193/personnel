 //控制层 
app.controller('indexController' ,function($scope, $compile, indexService, menuService){
    $scope.user = {};
    //获取当前的用户信息
    $scope.getUser = function () {
        indexService.getUser().success(
            function (response) {
                $scope.user = response.data;
                $scope.getMenuByAuthor($scope.user.uid);

            }
        )
    }
    //获取当前的可操作的权限
    $scope.getMenuByAuthor = function (uid) {
        indexService.getMenuByAuthor(uid).success(
            function (response) {
                $scope.menuList = response.data;
            }
        )
    }
    //修改密码
    $scope.updatePassword = function(){
        //判断确认密码和新密码是否一致
        if($scope.entity.confirmPassword != $scope.entity.newPassword){
            alert("密码不一致");
            return;
        }
        indexService.updatePassword($scope.entity.oldPassword, $scope.entity.newPassword).success(
            function (response) {
                alert(response.data);
                $scope.entity = {};
                //location.href="/logout";
            }
        )
    }

    $scope.openAndClose = function (e) {
        let submenu = $(e.target).siblings('ul');//ul
        let li = $(e.target).parents('li'); //li
        let submenus = $('#sidebar li.submenu ul');//ul
        let submenus_parents = $('#sidebar li.submenu');//li
        if(li.hasClass('open'))
        {
            console.log("open -> " + $(window).width());
            if(($(window).width() > 768) || ($(window).width() < 479)) {
                submenu.slideUp();//向下滑动
            } else {
                submenu.fadeOut(250);//淡出
            }
            li.removeClass('open');
        } else
        {
            console.log("no open -> " + $(window).width());
            if(($(window).width() > 768) || ($(window).width() < 479)) {
                submenus.slideUp();//向上滑动
                submenu.slideDown();//向下滑动
            } else {
                submenus.fadeOut(250);//淡出
                submenu.fadeIn(250);//淡入
            }
            submenus_parents.removeClass('open');
            li.addClass('open');
        }
    }

    //全局搜索功能
    $scope.search = function () {
       let author = $scope.user.author;
       let keyword = $scope.keyword;

       console.log(author +"," + keyword);

       if(author == 1){
           alert("权限不足");
           return;
       }
       if(!$scope.StringNotBlank(keyword)){
           alert("不能为空");
           return;
       }
       //window.location = "../search.html#?author=" + author +"&keyword=" + keyword;
       //随机时间，解决只跳转一次问题
       window.open("../search.html?_t="+new Date().getTime()+"#?author=" + author +"&keyword=" + keyword, "iframe");

       $scope.keyword = "";
    }

    //判断字符串不能为空
    $scope.StringNotBlank = function(str){
        if(str != "" && str != null && str != undefined){
            return true;
        }else{
            return false;
        }
    }
    $scope.loadMenuNode = function (id,isParent) {
        if(isParent == 0){//这个节点父节点
            return;
        }
        menuService.loadMenuById(id).success(
            function (response) {
                //封装页面
                $scope.menus = response.data;
                if($scope.menus != null && $scope.menus.length > 0){
                    console.log($scope.menus);
                    let $lis_prefix = "<ul>";
                    let $lis_content = "";
                    let $lis_suffix = "</ul>";
                    for(let i = 0; i < $scope.menus.length; i++){
                        let id = $scope.menus[i].id;
                        let isParentId = ($scope.menus.isParent == "true" ? "1" : "0");
                        let name = $scope.menus[i].name;
                        let url = ($scope.menus[i].isParent == 'true' ? 'javascript:void(0);' : $scope.menus[i].url);
                        console.log(id + ", " + name + ", " + url);
                        $lis_content += '<li id="menu_' + id + '" ng-mouseenter="loadMenuNode(' + id + ',' + isParentId + ')"> \
                                     <a href="' + url + '" target="iframe">' + name + '</a> \
                                     </li>';
                    }
                    let menuContent = $lis_prefix + $lis_content + $lis_suffix;
                    let compiles = $compile(menuContent)($scope);
                    $('#menu_'+id).append(compiles);
                }
           }
        )
    }
});	
