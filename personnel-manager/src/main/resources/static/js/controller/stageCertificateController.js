 //控制层 
app.controller('stageCertificateController' ,function($scope, $location, userService, stageCertificateService){
    $scope.stageCertificate = {
        uid:0,
        name:"",
        honorImage:"",
        postEvaluateImage:"",
        applyImage:"",
        stageEvaluateImage:"",
        workSummaryImage:"",
        synthesizeEvaluteImage:"",
        assessImage:"",
        tarnsferApproverImage:"",
        changeApproverImage:"",
        appointImage:"",
        leaveReportImage:"",
        trainApplyImage:"",
        trainServiceImage:"",
        yearInterviewImage:"",
        awardDispositionImage:""
    }

    //根据自身id进行查询
    $scope.findOne=function(){
        userService.findOne().success(
            function(response){
                $scope.user = response.data;
                $scope.stageCertificate.uid = $scope.user.uid;
                $scope.stageCertificate.name = $scope.user.name;
                $scope.showImg($scope.stageCertificate.uid);
                $scope.initImage();
            }
        );
    }
    //根据传过来的id查询
    $scope.findByUid = function (uid) {
        userService.findByUid(uid).success(
            function (response) {
                $scope.user = response.data;
                $scope.stageCertificate.uid = $scope.user.uid;
                $scope.stageCertificate.name = $scope.user.name;
                $scope.showImg($scope.stageCertificate.uid);
                $scope.initImage();
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
        stageCertificateService.showStageCertificate(uid).success(
            function (response) {
                //获取图片地址，属于哪个图片类型
                $scope.packageStageCertificate(response.data);
            }
        )
    }

    //封装入职表单证书属性，用户图片展示
    $scope.packageStageCertificate = function(data){
        let honor = data.honorImage;
        if($scope.StringNotBlank(honor)){
            if(honor.search(",")){
                //遍历所有关于employ的图片地址
                let honors = honor.split(",");
                for (let i = honors.length - 1; i >= 0; i--) {
                    $scope.setStageCertificate("honor",honors[i]);
                }
            }else{
                $scope.setStageCertificate("honor",honor);
            }
        }

        let postEvaluate = data.postEvaluateImage;
        if($scope.StringNotBlank(postEvaluate)){
            if(postEvaluate.search(",")){
                //遍历所有关于employ的图片地址
                let postEvaluates = postEvaluate.split(",");
                for (let i = postEvaluates.length - 1; i >= 0; i--) {
                    $scope.setStageCertificate("postEvaluate",postEvaluates[i]);
                }
            }else{
                $scope.setStageCertificate("postEvaluate",postEvaluate);
            }
        }

        let apply = data.applyImage;
        if($scope.StringNotBlank(apply)){
            if(apply.search(",")){
                //遍历所有关于employ的图片地址
                let applys = apply.split(",");
                for (let i = applys.length - 1; i >= 0; i--) {
                    $scope.setStageCertificate("apply",applys[i]);
                }
            }else{
                $scope.setStageCertificate("apply",apply);
            }
        }

        let stageEvaluate = data.stageEvaluateImage;
        if($scope.StringNotBlank(stageEvaluate)){
            if(stageEvaluate.search(",")){
                //遍历所有关于employ的图片地址
                let stageEvaluates = stageEvaluate.split(",");
                for (let i = stageEvaluates.length - 1; i >= 0; i--) {
                    $scope.setStageCertificate("stageEvaluate",stageEvaluates[i]);
                }
            }else{
                $scope.setStageCertificate("stageEvaluate",stageEvaluate);
            }
        }

        let workSummary = data.workSummaryImage;
        if($scope.StringNotBlank(workSummary)){
            if(workSummary.search(",")){
                //遍历所有关于employ的图片地址
                let workSummarys = workSummary.split(",");
                for (let i = workSummarys.length - 1; i >= 0; i--) {
                    $scope.setStageCertificate("workSummary",workSummarys[i]);
                }
            }else{
                $scope.setStageCertificate("workSummary",workSummary);
            }
        }
        let synthesizeEvalute = data.synthesizeEvaluteImage;
        if($scope.StringNotBlank(synthesizeEvalute)){
            if(synthesizeEvalute.search(",")){
                //遍历所有关于employ的图片地址
                let synthesizeEvalutes = synthesizeEvalute.split(",");
                for (let i = synthesizeEvalutes.length - 1; i >= 0; i--) {
                    $scope.setStageCertificate("synthesizeEvalute",synthesizeEvalutes[i]);
                }
            }else{
                $scope.setStageCertificate("synthesizeEvalute",synthesizeEvalute);
            }
        }
        let assess = data.assessImage;
        if($scope.StringNotBlank(assess)){
            if(assess.search(",")){
                //遍历所有关于employ的图片地址
                let assesss = assess.split(",");
                for (let i = assesss.length - 1; i >= 0; i--) {
                    $scope.setStageCertificate("assess",assesss[i]);
                }
            }else{
                $scope.setStageCertificate("assess",assess);
            }
        }
        let tarnsferApprover = data.tarnsferApproverImage;
        if($scope.StringNotBlank(tarnsferApprover)){
            if(tarnsferApprover.search(",")){
                //遍历所有关于employ的图片地址
                let tarnsferApprovers = tarnsferApprover.split(",");
                for (let i = tarnsferApprovers.length - 1; i >= 0; i--) {
                    $scope.setStageCertificate("tarnsferApprover",tarnsferApprovers[i]);
                }
            }else{
                $scope.setStageCertificate("tarnsferApprover",tarnsferApprover);
            }
        }
        let changeApprover = data.changeApproverImage;
        if($scope.StringNotBlank(changeApprover)){
            if(changeApprover.search(",")){
                //遍历所有关于employ的图片地址
                let changeApprovers = changeApprover.split(",");
                for (let i = changeApprovers.length - 1; i >= 0; i--) {
                    $scope.setStageCertificate("changeApprover",changeApprovers[i]);
                }
            }else{
                $scope.setStageCertificate("changeApprover",changeApprover);
            }
        }
        let appoint = data.appointImage;
        if($scope.StringNotBlank(appoint)){
            if(appoint.search(",")){
                //遍历所有关于employ的图片地址
                let appoints = appoint.split(",");
                for (let i = appoints.length - 1; i >= 0; i--) {
                    $scope.setStageCertificate("appoint",appoints[i]);
                }
            }else{
                $scope.setStageCertificate("appoint",appoint);
            }
        }
        let leaveReport = data.leaveReportImage;
        if($scope.StringNotBlank(leaveReport)){
            if(leaveReport.search(",")){
                //遍历所有关于employ的图片地址
                let leaveReports = leaveReport.split(",");
                for (let i = leaveReports.length - 1; i >= 0; i--) {
                    $scope.setStageCertificate("leaveReport",leaveReports[i]);
                }
            }else{
                $scope.setStageCertificate("leaveReport",leaveReport);
            }
        }
        let trainApply = data.trainApplyImage;
        if($scope.StringNotBlank(trainApply)){
            if(trainApply.search(",")){
                //遍历所有关于employ的图片地址
                let trainApplys = trainApply.split(",");
                for (let i = trainApplys.length - 1; i >= 0; i--) {
                    $scope.setStageCertificate("trainApply",trainApplys[i]);
                }
            }else{
                $scope.setStageCertificate("trainApply",trainApply);
            }
        }let trainService = data.trainServiceImage;
        if($scope.StringNotBlank(trainService)){
            if(trainService.search(",")){
                //遍历所有关于employ的图片地址
                let trainServices = trainService.split(",");
                for (let i = trainServices.length - 1; i >= 0; i--) {
                    $scope.setStageCertificate("trainService",trainServices[i]);
                }
            }else{
                $scope.setStageCertificate("trainService",trainService);
            }
        }
        let yearInterview = data.yearInterviewImage;
        if($scope.StringNotBlank(yearInterview)){
            if(yearInterview.search(",")){
                //遍历所有关于employ的图片地址
                let yearInterviews = yearInterview.split(",");
                for (let i = yearInterviews.length - 1; i >= 0; i--) {
                    $scope.setStageCertificate("yearInterview",yearInterviews[i]);
                }
            }else{
                $scope.setStageCertificate("yearInterview",yearInterview);
            }
        }

        let awardDisposition = data.awardDispositionImage;
        if($scope.StringNotBlank(awardDisposition)){
            if(awardDisposition.search(",")){
                //遍历所有关于employ的图片地址
                let awardDispositions = awardDisposition.split(",");
                for (let i = awardDispositions.length - 1; i >= 0; i--) {
                    $scope.setStageCertificate("awardDisposition",awardDispositions[i]);
                }
            }else{
                $scope.setStageCertificate("awardDisposition",awardDisposition);
            }
        }

    }
    //抽取公共部分，封装属性
    $scope.setStageCertificate = function(k,v){
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
            //获取名称
            let values = $(this).parents('.diyUploadHover').find("input:hidden").val();
            let index = values.lastIndexOf("/");
            let fileName = values.substring(index + 1);
            //删除服务器上的图片
            $.ajax({
                url:"/common/delete/upload/"+$scope.stageCertificate.uid+"/"+fileName,
                type:'DELETE',
                data:{}
            });
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
        let type = ['honor','postEvaluate','apply','stageEvaluate','workSummary','synthesizeEvalute','assess',
                   'tarnsferApprover','changeApprover','appoint','leaveReport','trainApply','trainService',
                  'yearInterview','awardDisposition'];
        //获取每一个图片类型的id，进行遍历赋值，封装对象
        for (var i = 0; i < type.length; i++) {
            //获取每个类型的所有input下的value值
            var $types = $("#" + type[i]).find('input:hidden');
            //遍历每一个value值，对其进行拼接封装
            for(var j = 0; j < $types.length; j++){
                if(j == 0){
                    $scope.stageCertificate[type[i]+"Image"] = $types.eq(j).attr("value");
                }else{
                    $scope.stageCertificate[type[i]+"Image"] += "," + $types.eq(j).attr("value");
                }
            }
        }
        stageCertificateService.uploadStageCertificate($scope.stageCertificate).success(
            function (response) {
                //提交成功
                if(response.flag){
                    alert("提交成功");
                }
            }
        )
    }

    $scope.download = function () {
        window.location = 'http://localhost:8085/download/upload/'+$scope.stageCertificate.uid;
    }


    $scope.initImage = function () {
        for(let i = 1; i <= 15; i++){
            let $tgaUpload = $('#goodsUpload' + i).diyUpload({
                url:'http://localhost:8085/common/upload/'+$scope.stageCertificate.uid, //三个证书文件通用的上传文件地址
                uid:$scope.stageCertificate.uid,
                success:function(data) {
                    $("#" + data.file_id).find('input').attr("value",data.data);
                    if(data.flag){
                        alert("上传成功");
                    }
                },
                error:function(err) {
                    alert(err.data);
                },
                buttonText : '',
                accept: {
                    title: "Images",
                    extensions: 'gif,jpg,jpeg,bmp,png'
                },
                thumb:{
                    width:120,
                    height:90,
                    quality:100,
                    allowMagnify:true,
                    crop:true,
                    type:"image/jpeg"
                }
            });
        }
    }
});	
