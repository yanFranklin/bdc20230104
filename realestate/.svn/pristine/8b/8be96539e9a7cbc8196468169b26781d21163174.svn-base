var proid = getQueryString("proid");
var processInsId = getQueryString("processInsId");
var autoTzFj = getQueryString("autoTzFj")?getQueryString("autoTzFj"):"true";

// 当前流程拖曳的附件
var currentFj = [];
// 供地审批信息的附件
var gdspxxFj = [];
//供地审批信息拖曳附件
var dragFj = [];
layui.use(['element', 'form', 'jquery', 'tree', 'layer'], function () {
    var form = layui.form,
        $ = layui.jquery,
        layer = layui.layer,
        tree = layui.tree;

    form.render();

    // ztree初始化设置
    var treeLeft="treeLeft";
    var treeRight="treeRight";
    var zTreeLeft,zTreeRight;
    var settingLeft = {
        edit: {
            enable: true,
            showRemoveBtn: false,
            showRenameBtn: false
        },
        view: {
            dblClickExpand: true,
            showLine: false,
        },
        check: {enable: false},
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeDrag: beforeDrag,
            beforeDrop: beforeDrop,
            onDrop: zTreeonDrop,

        }/*,
	callback: {
		onClick: ztreeLeftOnClick,
		onRightClick: ztreeLeftOnRightClick,
		onAsyncSuccess: zTreeLeftOnAsyncSuccess
	}*/
    };
    var settingRight = {
        edit: {
            enable: true,
            showRemoveBtn: showRemoveBtn,
            // showRemoveBtn: true,
            // showRenameBtn: showRenameBtn,
            showRenameBtn: false
        },
        view: {
            dblClickExpand: true,
            showLine: false,
        },
        check: {enable: false},
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    $(function () {

        addModel();
        // 加载基本信息
        setTimeout(function () {
            try {
                $.when(getGlywFj(),getDqlcFj()).done(function () {
                    $('.bdc-menu-aside').css("height",$('body').height()-20);
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e,"加载失败");
                return
            }
        }, 50);

        // 监听 收起、展开、自动挂接按钮
        $(document).on('click','#showAll',function () {
            zTreeLeft.expandAll(true);
        });

        $(document).on('click','#hideAll',function () {
            zTreeLeft.expandAll(false);
        });

        $(document).on('click','#zdgj',function () {
            for(var j = 0;j<zTreeLeft.getNodes().length;j++){
                var zdgjData = zTreeLeft.getNodes()[j].children;
                if(zdgjData !=[] && zdgjData !=null){
                    var newNodes = [];
                    for(var i=0; i <zdgjData.length; i++){
                        if(zdgjData[i].type=='0'){
                            newNodes.push(zdgjData[i]);
                        }
                    }
                    zTreeRight.addNodes(null, newNodes);
                }
            }

        });

        form.on("submit(save)", function (data) {
            saveFj();
        });

    });
    /**
     * 查询当前流程的附件
     */
    function getDqlcFj(){
        $.ajax({
            url: "/realestate-accept-ui/yzt/fj/current/list/"+processInsId+"/"+autoTzFj,
            type: 'GET',
            success: function (data) {
                console.log("当前流程的附件材料为：");
                console.log(data);
                if(data){
                    // currentFj = data;
                    getFolder(data,currentFj);
                    eachAddIcon(currentFj);
                    eachAddDrag(currentFj,false);
                    loadDqlcFj();
                }
            }
        });
    }

    /**
     * 查询供地审批信息的附件
     */
    function getGlywFj(){
        $.ajax({
            url: "/realestate-accept-ui/yzt/fj/getGdspxxFj/list/"+processInsId+"/"+proid+"/"+autoTzFj,
            type: 'GET',
            async: false,
            success: function (data) {
                console.log("供地审批信息的附件材料为：");
                console.log(data);
                gdspxxFj=data;
                dragFj = gdspxxFj;

                eachAddIcon(gdspxxFj);
                eachAddDrag(gdspxxFj,true);
                loadGdspFj();
            }
        });
    }

    function saveFj(){
        addModel();
        var saveData = zTreeRight.getNodes();
        $.ajax({
            url: "/realestate-accept-ui/yzt/fj/save/"+processInsId,
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(saveData),
            success: function (result) {
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER("保存成功");
            },
            error: function (e) {
                delAjaxErrorMsg(e);
                removeModal();
            }
        });
    }

    // 加载供地审批附件
    function loadGdspFj() {
        $.fn.zTree.init($("#"+treeLeft), settingLeft, gdspxxFj);
        zTreeLeft = $.fn.zTree.getZTreeObj(treeLeft);
        zTreeLeft.expandAll(true);
        zTreeLeft.setting.edit.drag.isCopy = true;
        zTreeLeft.setting.edit.drag.isMove = false;
    }
    // 加载当前流程附件
    function loadDqlcFj() {
        $.fn.zTree.init($("#"+treeRight), settingRight, currentFj);
        zTreeRight = $.fn.zTree.getZTreeObj(treeRight);
        zTreeRight.expandAll(true);
        zTreeRight.setting.edit.drag.isCopy = false;
        zTreeRight.setting.edit.drag.isMove = false;
    }

    // 1目录数据处理

    // 1.1当前流程附件只保留文件夹
    function getFolder(arr,list) {
        //遍历目录数据
        for (let i = 0; i < arr.length; i++) {

            // 文件类型为文件夹
            if(arr[i].type=='0'){
                let childList=JSON.parse(JSON.stringify(arr[i]));

                // 判断文件夹下是否有子目录
                if (arr[i].children !=null && arr[i].children.length > 0 ) {
                    // 清空筛选子文件夹
                    //childList.children=[];
                    getFolder(arr[i].children ,childList);// 递归遍历
                }
                // 子目录筛选完毕放入父目录
                if(list.children != undefined){
                    list.children.push(childList);
                }else {
                    list.push(childList);
                }

            }
        }
    }

    // 1.2自定义目录图标
    function eachAddIcon(dataArray) {
        for (let i = 0; i < dataArray.length; i++) { //遍历源数据
            let arr;
            if(dataArray[i].type=='0'){
                dataArray[i].icon = "../../static/image/file.png"; //给当前点击的树形结构数据增加文件夹图标
                if (dataArray[i].children != [] > 0  && dataArray[i].children !=null) { //进入到这里表示当前层树形结构数据中存在children，则继续遍历
                    arr = dataArray[i].children;//将当前的层的数据中的children数组用来遍历
                    eachAddIcon(arr);// 递归遍历
                }
            }else {
                dataArray[i].icon = "../../static/image/new-page/log.png"; //给当前点击的树形结构数据增加文件图标
            }
        }
    }

    /**
     * 1.3左侧目录增加拖动属性
     * @param dataArray
     * @param drag {boolean}
     */
    function eachAddDrag(dataArray,drag) {
        for (let i = 0; i < dataArray.length; i++) { //遍历源数据
            let arr;
            dataArray[i].drag = drag; //给当前点击的树形结构数据增加drag
            if (dataArray[i].children != [] > 0  && dataArray[i].children !=null) { //进入到这里表示当前层树形结构数据中存在children，则继续遍历
                arr = dataArray[i].children;//将当前的层的数据中的children数组用来遍历
                eachAddDrag(arr,drag);// 递归遍历
            }
        }
    }


    // 2 拖曳功能方法
    /**
     * 2.1 拖动之前，判断左侧树节点是否符合拖拽条件
     * @param treeId
     * @param treeNodes
     * @returns {boolean}
     */
    function beforeDrag(treeId, treeNodes) {
        for (var i=0,l=treeNodes.length; i<l; i++) {
            if (treeNodes[i].drag === false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 2.2 拖拽之后，判断目标树节点是否符合接收条件
     * @param treeId
     * @param treeNodes
     * @param targetNode
     * @param moveType
     * @returns {boolean}
     */
    function beforeDrop(treeId, treeNodes, targetNode, moveType) {
        if(treeId=='treeRight'){
            // 判断目标节点是否为根目录
            if(targetNode){
                if(targetNode.type=='0' && moveType == "inner"){
                    dragFj = treeNodes;
                    return true;
                }
            }else if(targetNode==null && moveType =='inner'){
                // dragFj = treeNodes;
                if(treeNodes[0].type =='0'){
                    return true;
                }else {
                    ityzl_SHOW_WARN_LAYER('附件请拖动至文件夹下')
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 2.3 允许左侧拖曳过来的节点进行删除操作
     * @param treeId
     * @param treeNodes
     * @param targetNode
     * @param moveType
     * @returns {boolean}
     */
    function zTreeonDrop(event,treeId, treeNodes, targetNode, moveType) {
        eachAddDrag(treeNodes,true);
    }

    function showRemoveBtn(treeId, treeNode) {
        //只有从左侧拖过来的节点，才允许进行删除操作
        return treeNode.drag || treeNode.owner == "auto";
    }

});

