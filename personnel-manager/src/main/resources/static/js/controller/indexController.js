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
            if(($(window).width() > 768) || ($(window).width() < 479)) {
                submenu.slideDown();
            } else {
                submenu.fadeOut(250);
            }
            li.removeClass('open');
        } else
        {
            if(($(window).width() > 768) || ($(window).width() < 479)) {
                submenus.slideUp();
                submenu.slideDown();
            } else {
                submenus.fadeOut(250);
                submenu.fadeIn(250);
            }
            submenus_parents.removeClass('open');
            li.addClass('open');
        }
        /*$('.submenu > a').click(function(e)
        {
            console.log("1生效了？")
            e.preventDefault();
            var submenu = $(this).siblings('ul');
            var li = $(this).parents('li');
            var submenus = $('#sidebar li.submenu ul');
            var submenus_parents = $('#sidebar li.submenu');
            if(li.hasClass('open'))
            {
                if(($(window).width() > 768) || ($(window).width() < 479)) {
                    submenu.slideUp();
                } else {
                    submenu.fadeOut(250);
                }
                li.removeClass('open');
            } else
            {
                if(($(window).width() > 768) || ($(window).width() < 479)) {
                    submenus.slideUp();
                    submenu.slideDown();
                } else {
                    submenus.fadeOut(250);
                    submenu.fadeIn(250);
                }
                submenus_parents.removeClass('open');
                li.addClass('open');
            }
        });
        var ul = $('#sidebar > ul');
        $('#sidebar > a').click(function(e)
        {
            console.log("2生效了？")
            e.preventDefault();
            var sidebar = $('#sidebar');
            if(sidebar.hasClass('open'))
            {
                sidebar.removeClass('open');
                ul.slideUp(250);
            } else
            {
                sidebar.addClass('open');
                ul.slideDown(250);
            }
        });*/
    }
});	
