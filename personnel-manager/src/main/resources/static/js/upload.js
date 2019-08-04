$(function(){
    //定义图片所属的类型,顺序一致
    var type = ['employ','identity','degree','education','post','background'];
    //上传图片
    for(var i = 1; i <= 6; i++){
        var $tgaUpload = $('#goodsUpload' + i).diyUpload({
            url:'http://localhost:8085/common/upload/',
            success:function(data) {
                alert(data.data);
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