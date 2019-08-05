$(function(){
    //定义图片所属的类型,顺序一致
    //var type = ['entry_notice','entry_agree','secrecy','hire'];
    //上传图片
    for(var i = 1; i <= 4; i++){
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