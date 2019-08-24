 //控制层 
app.controller('entryCertificateController' ,function($scope, $location, userService, entryCertificateService){
    $scope.entryCertificate = {
        uid:0,
        name:"",
        entryNoticeImage:"",
        entryAgreeImage:"",
        secrecyImage:"",
        hireImage:""
    }


    //根据自身id进行查询
    $scope.findOne=function(){
        userService.findOne().success(
            function(response){
                $scope.user = response.data;
                $scope.entryCertificate.uid = $scope.user.uid;
                $scope.entryCertificate.name = $scope.user.name;
                $scope.showImg($scope.entryCertificate.uid);
            }
        );
    }
    //根据传过来的id查询
    $scope.findByUid = function (uid) {
        userService.findByUid(uid).success(
            function (response) {
                $scope.user = response.data;
                $scope.entryCertificate.uid = $scope.user.uid;
                $scope.entryCertificate.name = $scope.user.name;
                $scope.showImg($scope.entryCertificate.uid);
            }
        )
    }
    //在初始化的时候回显图片信息
    $scope.initShow = function(){
        let uid = $location.search()['uid']; // 获取路径的参数
        if(uid != null && uid != "" && uid != undefined){
            $scope.findByUid(uid);
        }else{
            $scope.findOne();//为空，就是查看自身
        }
    }

    $scope.showImg = function(uid){
        entryCertificateService.showEntryCertificate(uid).success(
            function (response) {
                //获取图片地址，属于哪个图片类型
                $scope.packageEntryCertificate(response.data);
            }
        )
    }

    //封装入职表单证书属性，用户图片展示
    $scope.packageEntryCertificate = function(data){
        let entryNotice = data.entryNoticeImage;
        if($scope.StringNotBlank(entryNotice)){
            if(entryNotice.search("[,]")){
                //遍历所有关于employ的图片地址
                let entryNotices = entryNotice.split("[,]");
                for (let i = entryNotices.length - 1; i >= 0; i--) {
                    $scope.setEntryCertificate("entryNotice",entryNotices[i]);
                }
            }else{
                $scope.setEntryCertificate("entryNotice",entryNotice);
            }
        }

        let entryAgree = data.entryAgreeImage;
        if($scope.StringNotBlank(entryAgree)){
            if(entryAgree.search("[,]")){
                //遍历所有关于employ的图片地址
                let entryAgrees = entryAgree.split("[,]");
                for (let i = entryAgrees.length - 1; i >= 0; i--) {
                    $scope.setEntryCertificate("entryAgree",entryAgrees[i]);
                }
            }else{
                $scope.setEntryCertificate("entryAgree",entryAgree);
            }
        }

        let secrecy = data.secrecyImage;
        if($scope.StringNotBlank(secrecy)){
            if(secrecy.search("[,]")){
                //遍历所有关于employ的图片地址
                let secrecys = secrecy.split("[,]");
                for (let i = secrecys.length - 1; i >= 0; i--) {
                    $scope.setEntryCertificate("secrecy",secrecys[i]);
                }
            }else{
                $scope.setEntryCertificate("secrecy",secrecy);
            }
        }

        let hire = data.hireImage;
        if($scope.StringNotBlank(hire)){
            if(hire.search("[,]")){
                //遍历所有关于employ的图片地址
                let hires = hire.split("[,]");
                for (let i = hires.length - 1; i >= 0; i--) {
                    $scope.setEntryCertificate("hire",hires[i]);
                }
            }else{
                $scope.setEntryCertificate("hire",hire);
            }
        }

    }
    //抽取公共部分，封装属性
    $scope.setEntryCertificate = function(k,v){
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
        let type = ['entryNotice','entryAgree','secrecy','hire'];
        //获取每一个图片类型的id，进行遍历赋值，封装对象
        for (var i = 0; i < type.length; i++) {
            //获取每个类型的所有input下的value值
            var $types = $("#" + type[i]).find('input:hidden');
            //遍历每一个value值，对其进行拼接封装
            for(var j = 0; j < $types.length; j++){
                if(j == 0){
                    $scope.entryCertificate[type[i]+"Image"] = $types.eq(j).attr("value");
                }else{
                    $scope.entryCertificate[type[i]+"Image"] += "[,]" + $types.eq(j).attr("value");
                }
            }
        }
        entryCertificateService.uploadEntryCertificate($scope.entryCertificate).success(
            function (response) {
                //提交成功
                if(response.flag){
                    alert("提交成功");
                }
            }
        )
    }
});	
