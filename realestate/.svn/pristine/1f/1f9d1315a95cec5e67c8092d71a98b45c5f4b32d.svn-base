
var processInsId = getQueryString("processInsId");
// 审批信息的附件
var spxxFj = [];
layui.use(['element', 'form', 'jquery', 'tree', 'layer'], function () {
    var form = layui.form,
        $ = layui.jquery,
        layer = layui.layer,
        tree = layui.tree;

    form.render();

    // ztree初始化设置
    var treeLeft="treeLeft";
    var zTreeLeft,zTreeRight;
    var settingLeft = {
        edit: {
            enable: false,
            showRemoveBtn: false,
            showRenameBtn: false
        },
        view: {
            dblClickExpand: true,
            showLine: false,
        },
        check: {
            enable: true,
        },
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
                $.when(getGlywFj()).done(function () {
                    $('.bdc-menu-aside').css("height",$('body').height()-20);
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e,"加载失败");
                return
            }
        }, 50);


        $(document).on('click','#selected',function () {
            addModel();

            var checkedNodes = zTreeLeft.getCheckedNodes();
            console.log(checkedNodes);
            //暂时如此处理，后面调整
            var data = [];
            for(var i=0;i<checkedNodes.length;i++){
                var node ={};
                if(checkedNodes[i].children != null &&checkedNodes[i].children.length >0 &&!isNotBlank(checkedNodes[i].parentTId)){
                    node.name =checkedNodes[i].name;
                    var children =checkedNodes[i].children;
                    var childs =[];
                    for(var j=0;j<children.length;j++){
                        if(children[j].checked) {
                            var child = {};
                            child.name = children[j].name;
                            if (children[j].children != null && children[j].children.length > 0) {
                                var childchilds = [];
                                for (var k = 0; k < children[j].children.length; k++) {
                                    var childchild = {};
                                    childchild.name = children[j].children[k].name;
                                    if (children[j].children[k].children != null && children[j].children[k].children.length > 0) {

                                    } else {
                                        childchild.downUrl = children[j].children[k].url;
                                    }
                                    childchilds.push(childchild);
                                    child.children = childchilds;
                                }
                            } else {
                                child.downUrl = children[j].url;
                            }
                            childs.push(child);
                        }
                    }
                    node.children =childs;
                    data.push(node);

                }

            }
            if(data.length ===0){
                removeModal();
                layer.alert("未选择数据");
                return false;
            }
            $.ajax({
                url: "/realestate-accept-ui/rest/v1.0/ythsp/fj/copy?processInsId="+processInsId,
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(data),
                success: function (result) {
                    removeModal();
                    ityzl_SHOW_SUCCESS_LAYER("上传成功");
                },
                error: function (e) {
                    delAjaxErrorMsg(e);
                    removeModal();
                }
            });

        });



    });


    /**
     * 查询审批信息的附件
     */
    function getGlywFj(){
        $.ajax({
            url: "/realestate-accept-ui/rest/v1.0/ythsp/fj/"+processInsId,
            type: 'GET',
            async: false,
            success: function (data) {
                if(data.head.msg ="success"){
                    console.log(data);
                    spxxFj=data.data;
                    eachAddIcon(spxxFj);
                    loadYthspFj();
                }else{
                    layer.alert("获取附件信息失败"+data.head.msg);
                }
            },
            error: function (e) {
                removeModal();
                delAjaxErrorMsg(e);
            }
        });
    }



    // 加载供地审批附件
    function loadYthspFj() {
        $.fn.zTree.init($("#"+treeLeft), settingLeft, spxxFj);
        zTreeLeft = $.fn.zTree.getZTreeObj(treeLeft);
        zTreeLeft.expandAll(true);
        zTreeLeft.setting.edit.drag.isCopy = true;
        zTreeLeft.setting.edit.drag.isMove = false;
    }


    // 1目录数据处理

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

});