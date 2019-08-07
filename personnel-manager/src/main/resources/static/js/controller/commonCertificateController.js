 //控制层 
app.controller('commonCertificateController' ,function($scope,commonCertificateService){
    $scope.commonCertificate = {
        uid:"2",
        employImage:"",
        identityImage:"",
        degreeImage:"",
        educationImage:"",
        postImage:"",
        backgroundImage:""
    }
    //在初始化的时候回显图片信息
    $scope.initShow = function(){
        commonCertificateService.showCommonCertificate($scope.commonCertificate.uid).success(
            function (response) {
                //获取图片地址，属于哪个图片类型
                $scope.packageCommonCertificate(response.data);
            }
        )
    }
    //封装常规证书属性，用户图片展示
    $scope.packageCommonCertificate = function(data){
        let employ = data.employImage;
        if($scope.StringNotBlank(employ)){
            if(employ.search("[,]")){
                //遍历所有关于employ的图片地址
                let employs = employ.split("[,]");
                for (let i = employs.length - 1; i >= 0; i--) {
                    $scope.setCommonCertificate("employ",employs[i]);
                }
            }else{
                $scope.setCommonCertificate("employ",employ);
            }
        }

        let identity = data.identityImage;
        if($scope.StringNotBlank(identity)){
            if(identity.search("[,]")){
                //遍历所有关于employ的图片地址
                let identitys = identity.split("[,]");
                for (let i = identitys.length - 1; i >= 0; i--) {
                    $scope.setCommonCertificate("identity",identitys[i]);
                }
            }else{
                $scope.setCommonCertificate("identity",identity);
            }
        }

        let degree = data.degreeImage;
        if($scope.StringNotBlank(degree)){
            if(degree.search("[,]")){
                //遍历所有关于employ的图片地址
                let degrees = degree.split("[,]");
                for (let i = degrees.length - 1; i >= 0; i--) {
                    $scope.setCommonCertificate("degree",degrees[i]);
                }
            }else{
                $scope.setCommonCertificate("degree",degree);
            }
        }

        let education = data.educationImage;
        if($scope.StringNotBlank(education)){
            if(education.search("[,]")){
                //遍历所有关于employ的图片地址
                let educations = education.split("[,]");
                for (let i = educations.length - 1; i >= 0; i--) {
                    $scope.setCommonCertificate("education",educations[i]);
                }
            }else{
                $scope.setCommonCertificate("education",education);
            }
        }

        let post = data.postImage;
        if($scope.StringNotBlank(post)){
            if(post.search("[,]")){
                //遍历所有关于employ的图片地址
                let posts = post.split("[,]");
                for (let i = posts.length - 1; i >= 0; i--) {
                    $scope.setCommonCertificate("post",posts[i]);
                }
            }else{
                $scope.setCommonCertificate("post",post);
            }
        }

        let background = data.backgroundImage;
        if($scope.StringNotBlank(background)){
            if(background.search("[,]")){
                //遍历所有关于employ的图片地址
                let backgrounds = background.split("[,]");
                for (let i = backgrounds.length - 1; i >= 0; i--) {
                    $scope.setCommonCertificate("background",backgrounds[i]);
                }
            }else{
                $scope.setCommonCertificate("background",background);
            }
        }
    }
    //抽取公共部分，封装属性
    $scope.setCommonCertificate = function(k,v){
        let liId = $scope.getUUID();
        //并绑定一个唯一的id号
        let $lis = '<li class="diyUploadHover" id='+liId+'> \
                    <div class="diysViewThumb">\
                        <input type="hidden" value='+v+'>\
                        <div class="diysBar"> \
                            <div class="diysProgress">0%</div> \
                        </div> \
                        <p class="diysControl"><span class="diysLeft"><i></i></span><span class="diysCancel"><i></i></span><span class="diysRight"><i></i></span></p>\
                        <img src='+v+'> \
                    </div> \
                </li>';
        $("#"+k).find(".upload-ul").prepend($lis);

        let $fileBox = $('#'+liId);
        //绑定取消事件;
        $fileBox.find('.diysCancel').on('click',function(){
            $(this).parents('.diyUploadHover').remove();
        });

        //绑定左移事件;
        $fileBox.find('.diysLeft').on('click',function(){
            $(this).parents('.diyUploadHover').insertBefore($(this).parents('.diyUploadHover').prev());
        });

        //绑定右移事件;
        $fileBox.find('.diysRight').on('click',function(){
            $(this).parents('.diyUploadHover').insertAfter($(this).parents('.diyUploadHover').next());
        });

    }
    $scope.getUUID = function(){
        return Number(Math.random().toString().substr(3,10) + Date.now()).toString(36);
    }
    //判断字符串不能为空
    $scope.StringNotBlank = function(str){
        if(str != "" && str != null && str != undefined){
            return true;
        }else{
            return false;
        }
    }
    //更新保存图片信息
    $scope.save = function () {
        var type = ['employ','identity','degree','education','post','background'];
        //获取每一个图片类型的id，进行遍历赋值，封装对象
        for (var i = 0; i < type.length; i++) {
            //获取每个类型的所有input下的value值
            var $types = $("#" + type[i]).find('input:hidden');
            //遍历每一个value值，对其进行拼接封装
            for(var j = 0; j < $types.length; j++){
                if(j == 0){
                    $scope.commonCertificate[type[i]+"Image"] = $types.eq(j).attr("value");
                }else{
                    $scope.commonCertificate[type[i]+"Image"] += "[,]" + $types.eq(j).attr("value");
                }
            }
        }
        commonCertificateService.uploadCommonCertificate($scope.commonCertificate).success(
            function (response) {
                //提交成功
                if(response.flag){
                    alert("提交成功");
                }
            }
        )
    }
    $scope.download = function () {
        window.location = 'http://localhost:8085/download/upload/'+$scope.commonCertificate.uid;
        /*commonCertificateService.download($scope.commonCertificate.uid).success(
            function (response) {
                alert(response.data)
            }
        )*/
    }
});	
