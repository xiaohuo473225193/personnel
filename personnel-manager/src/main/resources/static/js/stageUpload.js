$(function(){
    //定义图片所属的类型,顺序一致
    //var type = ['honor','postEvaluate','apply','stageEvaluate','workSummary','synthesizeEvalute','assess',
    //           'tarnsferApprover','changeApprover','appoint','leaveReport','trainApply','trainService',
    //           'yearInterview','awardDisposition'];
    //上传图片
    for(var i = 1; i <= 15; i++){
        var $tgaUpload = $('#goodsUpload' + i).diyUpload({
            url:'http://localhost:8085/common/upload', //三个证书文件通用的上传文件地址
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
});