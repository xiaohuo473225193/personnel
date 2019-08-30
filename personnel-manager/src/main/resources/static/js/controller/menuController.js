//控制层
//在angularjs里面调用外部封装好的js文件里的函数的时候，要把该js文件放在angularjs文件的后面
//否则会报出找不到该方法
app.controller('menuController' ,function($scope, menuService) {
    //
    $scope.addMenu = function () {
        menuService.addMenu($scope.menu).success(
            function (response) {
                $scope.menu = response.data;
            }
        );
    }

    $scope.deleteMenu = function () {
        menuService.deleteMenu($scope.id).success(
            function (response) {
                $scope.menu = response.data;
            }
        );
    }

    $scope.updeteMenu = function () {
        menuService.updeteMenu($scope.menu).success(
            function (response) {
                $scope.menu = response.data;
            }
        );
    }

    $scope.findMenu = function () {
        menuService.findMenu($scope.author).success(
            function (response) {
                $scope.menu = response.data
            }
        )
    }
    /* 数据的结构形式
            [
            {id:1, pId:0, name:"[core] 基本功能 演示", open:false},
            {id:101, pId:1, name:"最简单的树 --  标准 JSON 数据"},
            {id:102, pId:1, name:"最简单的树 --  简单 JSON 数据"},
            {id:103, pId:1, name:"不显示 连接线"},
            {id:104, pId:1, name:"不显示 节点 图标"},
            {id:108, pId:1, name:"异步加载 节点数据"},
            {id:109, pId:1, name:"用 zTree 方法 异步加载 节点数据"},
            {id:110, pId:1, name:"用 zTree 方法 更新 节点数据"},
            {id:111, pId:1, name:"单击 节点 控制"},
            {id:112, pId:1, name:"展开 / 折叠 父节点 控制"},
            {id:113, pId:1, name:"根据 参数 查找 节点"},
            {id:114, pId:1, name:"其他 鼠标 事件监听"},

            {id:2, pId:0, name:"[excheck] 复/单选框功能 演示", open:false},
            {id:201, pId:2, name:"Checkbox 勾选操作"},
            {id:206, pId:2, name:"Checkbox nocheck 演示"},
            {id:207, pId:2, name:"Checkbox chkDisabled 演示"},
            {id:208, pId:2, name:"Checkbox halfCheck 演示"},
            {id:202, pId:2, name:"Checkbox 勾选统计"},
            {id:203, pId:2, name:"用 zTree 方法 勾选 Checkbox"},
            {id:204, pId:2, name:"Radio 勾选操作"},
            {id:209, pId:2, name:"Radio nocheck 演示"},
            {id:210, pId:2, name:"Radio chkDisabled 演示"},
            {id:211, pId:2, name:"Radio halfCheck 演示"},
            {id:205, pId:2, name:"用 zTree 方法 勾选 Radio"},

            {id:3, pId:0, name:"[exedit] 编辑功能 演示", open:false},
            {id:301, pId:3, name:"拖拽 节点 基本控制"},
            {id:302, pId:3, name:"拖拽 节点 高级控制"},
            {id:303, pId:3, name:"用 zTree 方法 移动 / 复制 节点"},
            {id:304, pId:3, name:"基本 增 / 删 / 改 节点"},
            {id:305, pId:3, name:"高级 增 / 删 / 改 节点"},
            {id:306, pId:3, name:"用 zTree 方法 增 / 删 / 改 节点"},
            {id:307, pId:3, name:"异步加载 & 编辑功能 共存"},
            {id:308, pId:3, name:"多棵树之间 的 数据交互"},

            {id:4, pId:0, name:"大数据量 演示", open:false},
            {id:401, pId:4, name:"一次性加载大数据量"},
            {id:402, pId:4, name:"分批异步加载大数据量"},
            {id:403, pId:4, name:"分批异步加载大数据量"},

            {id:5, pId:0, name:"组合功能 演示", open:false},
            {id:501, pId:5, name:"冻结根节点"},
            {id:502, pId:5, name:"单击展开/折叠节点"},
            {id:503, pId:5, name:"保持展开单一路径"},
            {id:504, pId:5, name:"添加 自定义控件"},
            {id:505, pId:5, name:"checkbox / radio 共存"},
            {id:506, pId:5, name:"左侧菜单"},
            {id:507, pId:5, name:"下拉菜单"},
            {id:509, pId:5, name:"带 checkbox 的多选下拉菜单"},
            {id:510, pId:5, name:"带 radio 的单选下拉菜单"},
            {id:508, pId:5, name:"右键菜单 的 实现"},
            {id:511, pId:5, name:"与其他 DOM 拖拽互动"},
            {id:512, pId:5, name:"异步加载模式下全部展开"},

            {id:6, pId:0, name:"其他扩展功能 演示", open:false},
            {id:601, pId:6, name:"隐藏普通节点"},
            {id:602, pId:6, name:"配合 checkbox 的隐藏"},
            {id:603, pId:6, name:"配合 radio 的隐藏"}
        ];
        */
    let setting = {};
    $scope.loadTreeMenu = function(){
        //对树结构的操作方式，进行设置
        //异步加载菜单
        setting = {
            view: {//视图操作
                addHoverDom: addHoverDom, //为addHoverDom事件绑定addHoverDom函数，名称任意(当鼠标移入，触发该函数)
                removeHoverDom: removeHoverDom, //为removeHoverDom事件绑定removeHoverDom函数，名称任意(当鼠标移出，触发该函数)
                selectedMulti: false //不支持多选
            },
            async: { //异步加载，为子节点进行提供的，点击子节点进行请求,节点展开操作
                enable: true,
                url:"/menu/load/node",
                autoParam:["id"],
                dataFilter: filter
            },
            callback:{
                beforeRemove: zTreeBeforeRemove,//节点删除前
                beforeRename: zTreeBeforeRename,//节点命名前
                onDrop: zTreeOnDrop//用于捕获节点拖拽操作结束的事件回调函数
            },
            check: {
                enable: false//是否显示复选框
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey:'id',
                    pIdKey:'pid',
                    rootPId: 0
                }
            },
            edit: {//编辑节点
                enable: true
            }
        };
        //加载到的数据 可以为异步加载
        $(document).ready(function(){
            $scope.initZtree();
        });
        function filter(treeId, parentNode, childNodes) {
            return childNodes.data;
        }
        let newCount = 1;
        //添加节点操作
        function addHoverDom(treeId, treeNode) {
            let sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
            let addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                + "' title='add node' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            let btn = $("#addBtn_"+treeNode.tId);
            if (btn) btn.bind("click", function(){//添加节点会监听到，调用该方法
                if(treeNode.level != 0){
                    alert("最多只能出现二级目录，不能存在三级目录");
                    return;
                }
                let zTree = $.fn.zTree.getZTreeObj("treeDemo");
                let newNodes = {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)};
                //添加节点,从数据库中取到唯一的id，再次进行渲染
                menuService.addMenuNode(treeNode.id, newNodes.id,newNodes.name).success(
                    function (response) {
                        if(response.flag){
                            newNodes.id = response.data.id;
                            newNodes.name = response.data.name;
                            zTree.addNodes(treeNode, newNodes);
                        }else{
                            return;
                        }
                    }
                );
                return false;
            });
        };
        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_"+treeNode.tId).unbind().remove();
        };
        function zTreeBeforeRemove(treeId, treeNode){//节点删除前
            //删除菜单
            menuService.removeMenu(treeNode.id).success(
                function (response) {
                    if(response.flag){
                        alert("删除成功");
                        $scope.initZtree();
                        return true;
                    }else{
                        alert("删除失败");
                        return false;
                    }
                }
            )
        }
        function zTreeBeforeRename(treeId, treeNode, newName){//节点命名前
            menuService.renameMenu(treeNode.id,newName).success(
                function (response) {
                    if(response.flag){
                        alert("修改成功");
                        $scope.initZtree();
                        return true;
                    }else{
                        alert("修改失败");
                        return false;
                    }
                }
            )
        }
        function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType){//用于捕获节点拖拽操作结束的事件回调函数
            if(treeNodes[0].level >= 2){
                alert("最多只能出现二级目录，不能存在三级目录");
                $scope.initZtree();
                return;
            }else{
                menuService.dropToMenu(treeNodes[0].id,targetNode.id).success(
                    function (response) {
                        if(response.flag){
                            alert("移动成功");
                            $scope.initZtree();
                        }else{
                            alert("移动失败");
                        }
                    }
                )
            }
        }
    }
    //初始化树
    $scope.initZtree = function(){
        menuService.initZtree().success(
            function (response) {
                console.log(response.data);
                let zTreeObj = $.fn.zTree.init($("#treeDemo"),setting, response.data);
                //让第一个父节点展开
                let rootNode_0 = zTreeObj.getNodeByParam('pid',0,null);
                zTreeObj.expandNode(rootNode_0, true, false, false, false);
            }
        );
    }
})