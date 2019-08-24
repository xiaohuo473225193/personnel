 //控制层 
app.controller('indexController' ,function($scope, indexService){
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

    $scope.openclose = function(e){
        let ul = $('#sidebar > ul');
        let sidebar = $('#sidebar');
        if(sidebar.hasClass('open'))
        {
            sidebar.removeClass('open');
            ul.slideUp(250);
        } else
        {
            sidebar.addClass('open');
            ul.slideDown(250);
        }
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
});	
