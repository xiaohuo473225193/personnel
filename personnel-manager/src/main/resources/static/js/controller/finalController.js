//常量，所有固定的值存放的地方，要与数据库对应起来，否则会出现错误
//---------------------------------------
//存放常量的地方，包含的常量有:
//      男女选项
//      下拉框的固定类型值
app.controller('finalController',function($scope){
    //所有服务器的地址 , 注意一个地方需要修改  import.html 有个固定的地址，当地址发送变化的时候注意修改
    $scope.server_url = "http://localhost:8085/";
    // form-common.html 男女选项
    $scope.genders = [
        {id:"1", name:"男"},
        {id:"2", name:"女"}
    ];
    $scope.authors = [
        {id:"1",name:"普通员工"},
        {id:"2",name:"部门负责人"},
        {id:"3",name:"系统管理员"}
    ]
    // form-common.html 下拉框的固定类型值
    $scope.type = {
        education:"002",
        title:"003",
        degree:"005",
        jobStatus:"006",
        level:"007"
    }
    // 部门的具体id ---> 要与数据库的college的cid对应起来
    //部门的结构组成
    $scope.college = {
        //教学单位下的部门
        teacherUnit:{
            secondary:63,//中专部
            build:15,//建筑工程学院
            ideology:24,//思想政治理论教学部
            information:2,//信息工程学院
            electromechanical:5,//机电工程学院
            sports:21,//公共体育教学部
            medicine:3,//医学院
            manager:4,//管理学院
            business:7,//商学院
            continuing:25,//继续教育学院
            international:22,//国际教育学院
            profession:26,//职业教育学院
            civil:6,//土木工程
            basics:23,//基础教学部
            foreign:19,//外国语学院
            art:17,//艺术设计学院
            chemistry:16,//药学与化学工程学院
            pharmacy:8,//药学院
            music:20//音乐学院
        },
        //党群机构下的部门
        partyOrganization:{
            policy:60,//党政办公室
            publicity:62,//宣传部
            organization:61//组织部
        },
        //行政机构下的部门
        administrative:{
            personnel:42,//人事处
            defend:52,//保卫处
            logistics:51,//后勤处
            league:48,//团委
            student:47,//学生处
            experiment:44,//实验设备处
            employment:49,//就业创业指导中心
            educational:43,//教务处
            teaching:45,//教学质量监测与评估中心
            recruit:55,//招生就业处
            school:50,//校地合作处
            finance:54,//财务处
            property:53,//资产处
            scientific:46//科研外事处
        },
        //教辅单位下的机构
        teachingAuxiliary:{
            library:57,//图书馆
            modern:58//现代教育技术中心
        },
        //教学评建办公室
        teachingEvaluation:64
    }
});