/**
 * 输入框下拉树处理JS
 */
layui.extend({
    custom_tree: '../../../../realestate-inquiry-ui/static/ztree/custom_tree'
});
// 组织树多选值
var OrgTreeCheck = new Map();
var custom_tree;
layui.use(['layer', 'custom_tree'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        custom_tree = layui.custom_tree;

    /**
     * 初始化组织机构下拉控件
     */
    $('.org_select_show').click(function () {
        if ($('.org_select_show').text() == '选择') {
            $('.org_select_tree').css('display', 'block');
            $('.org_select_show').text('隐藏')
        } else {
            $('.org_select_show').text('选择');
            $('.org_select_tree').css('display', 'none');
        }
    });
    //下拉面板宽高位置
    (function () {
        $('.org_select_tree').css({'width': '260px', 'height': '200px', 'left': 0, 'top': 40});
    })();
    //加载树
    custom_tree.load({
        treeId: "select_tree",
        commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs',
        checkboxEnabled: true,
        onCheck:onCheck,
    });

    /**
     * ztree新增方法
     * 全选
     */
    $('#all').click(function () {
        var zTreeObj = $.fn.zTree.getZTreeObj("select_tree");//得到tree对象
        var node = zTreeObj.getNodes();//得到全部节点
        var nodes = zTreeObj.transformToArray(node);//全部节点转换成数组array
        var checkNode = zTreeObj.getCheckedNodes().length;//得到被选中的节点数量
        if (checkNode < nodes.length) {//已选中的节点数小于总数 - 全选
            $('#all').prop('checked', true)
            zTreeObj.checkAllNodes(true);//给全部节点设置为true
        } else {
            zTreeObj.checkAllNodes(false);//给全部节点设置为false
            OrgTreeCheck=new Map();
        }
        var checked = zTreeObj.getCheckedNodes(true);//得到选中的节点
        var checkeds = zTreeObj.transformToArray(checked); //转变为数组
        //判断点击前是否已有选项
        if(checkeds.length>0){
            for( var i=0;i<checkeds.length;i++){
                OrgTreeCheck.set(checkeds[i].id, {id: checkeds[i].id, code: checkeds[i].code, name: checkeds[i].name});
            }

        }
        concat(OrgTreeCheck, "select_tree")
    });
    /**
     * ztree新增方法
     * 反选
     */
    $("#unall").click(function (e) {
        var zTreeObj = $.fn.zTree.getZTreeObj("select_tree"); //ztree对象

        var checked = zTreeObj.getCheckedNodes(true);//得到选中的节点
        var checkeds = zTreeObj.transformToArray(checked); //转变为数组
        var checkNode = zTreeObj.getCheckedNodes(true).length;//选中的节点数量

        var node = zTreeObj.getNodes();//全部节点
        var nodes = zTreeObj.transformToArray(node);//全部节点数量

        if (checkNode < nodes.length) {//已选中的节点数小于总数 - 全选
            //判断点击前是否已有选项
            if(checkeds.length>0){
                for( var i=0;i<checkeds.length;i++){
                    OrgTreeCheck.delete(checkeds[i].id, {id: checkeds[i].id, code: checkeds[i].code, name: checkeds[i].name});
                }

            }
            zTreeObj.checkAllNodes(true);//给所有的都设置为true
            $.each(checkeds, function (index, node) {//之前选中的节点为false
                zTreeObj.checkNode(node, false, false);
            });

        } else {
            zTreeObj.checkAllNodes(false);//否则所有的都设置为false
            OrgTreeCheck=new Map();
        }
        var changechecked = zTreeObj.getCheckedNodes(true);//得到选中的节点
        var newcheckeds = zTreeObj.transformToArray(changechecked); //转变为数组
        //判断点击后
        if(newcheckeds.length>0){
            for( var i=0;i<newcheckeds.length;i++){
                OrgTreeCheck.set(newcheckeds[i].id, {id: newcheckeds[i].id, code: newcheckeds[i].code, name: newcheckeds[i].name});
            }

        }
        concat(OrgTreeCheck, "select_tree")
    });

    /**
     * 清空
     */
    $('#empty').on('click', function (e) {
        var zTreeObj = $.fn.zTree.getZTreeObj("select_tree");//ztree对象
        zTreeObj.checkAllNodes(false);//设置全部为false,不选中节点
        OrgTreeCheck=new Map();
        $("input[name='bmmc']").val("");//清空input值
        $("input[name='djbmdm']").val("");//清空input值
    });
    /**
     * 搜索
     * @param selName 输入框输入值
     */
    $("#deptsel").bind("keyup blur",function(){
        if(this.value!=""){
            ztreeFuzzySearch(this.value)
        }else{
            $('.menuContent .xm-select-empty').css('display', 'none');
            layui.custom_tree.load({
                treeId: "select_tree",
                commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs',
                checkboxEnabled: true,
                onCheck:onCheck,
            });

        }



    });

});
/**
 * 初始化加载多选组织树方法
 * @param treeId 树ID
 * @param onCheckFn 多选框选中事件方法
 */
function loadMultiOrgTree(treeId, onCheckFn) {
    layui.custom_tree.load({
        treeId: treeId,
        commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs',
        checkboxEnabled: true,
        onCheck: onCheckFn
    });
}
/**
 * 初始化加载单选组织树方法
 * @param treeId 树ID
 * @param onclickFn 单选选中事件方法
 */
function loadSingleOrgTree(treeId, onclickFn) {
    layui.custom_tree.load({
        treeId: treeId,
        commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs',
        checkboxEnabled: false,
        onClick: onclickFn
    });
}
function bindClik(ele) {
    var select_show = $(ele).parents(".layui-inline").find(".org_select_show");
    var select_tree = $(ele).parents(".org_select_tree");
    $(select_show).click(function () {
        if ($(select_show).text() == '选择') {
            $(select_tree).css('display', 'block');
            $(select_show).text('隐藏')
        } else {
            $(select_show).text('选择');
            $(select_tree).css('display', 'none');
        }
    });
}
/**
 * ztree新增搜索方法
 *  @param name 输入值
 */
function ztreeFuzzySearch(name){
    var zTreeObj = layui.custom_tree.getZTreeObj();
    var nameKey = zTreeObj.setting.data.key.name;

    ztreeFilter(zTreeObj, name);

    // ztree 树节点过滤
    function ztreeFilter(zTreeObj,_keywords) {
        function filterFunc(node) {
            if(!node.zAsync){
                zTreeObj.showNode(node);
                zTreeObj.expandNode(node, true);
                zTreeObj.reAsyncChildNodes(node, "refresh");
            }
            if (_keywords.length == 0) {
                zTreeObj.showNode(node);
                return true;
            }
            if (node[nameKey] && node[nameKey].toLowerCase().indexOf(_keywords.toLowerCase())!=-1) {
                zTreeObj.showNode(node);
                return true;
            }
            zTreeObj.hideNode(node);
            return false;
        }

        var nodesShow = zTreeObj.getNodesByFilter(filterFunc);
        processShowNodes(nodesShow, _keywords);
    }

    // 节点展开
    function processShowNodes(nodesShow,_keywords){
        if(nodesShow && nodesShow.length>0){
            $('.menuContent .xm-select-empty').css('display', 'none');
            if(_keywords.length>0){
                $.each(nodesShow, function(n,obj){
                    var pathOfOne = obj.getPath();
                    if(pathOfOne && pathOfOne.length>0){
                        for(var i=0;i<pathOfOne.length-1;i++){
                            zTreeObj.showNode(pathOfOne[i]);
                            zTreeObj.expandNode(pathOfOne[i],true);
                        }
                    }
                });
            }else{
                $('.menuContent .xm-select-empty').css('display', 'block');
                var rootNodes = zTreeObj.getNodesByParam('level','0');
                $.each(rootNodes,function(n,obj){
                    zTreeObj.expandNode(obj, true);
                });
            }
        }else{
            $('.menuContent .xm-select-empty').show();
        }
    }
}


/**
 * onCheck 用于捕获 checkbox 被勾选 或 取消勾选的事件回调函数
 * @param e 标准的js event对象
 * @param treeId 对应 zTree 的 treeId，便于用户操控
 * @param treeNode 被勾选 或 取消勾选的节点 JSON 数据对象
 */
function onCheck(e, treeId, treeNode) {

    if(treeNode.checked){
        OrgTreeCheck.set(treeNode.id, {id: treeNode.id, code: treeNode.code, name: treeNode.name});
    }else{
        OrgTreeCheck.delete(treeNode.id);
    }
    concat(OrgTreeCheck, treeId)
}
/**
 * concat用于拼接获取的id和name值获参
 * @param OrgTreeCheck 为选中的节点
 *  @param treeId 对应 zTree 的 treeId
 */
function concat(OrgTreeCheck, treeId) {
    var treeDiv = $("#" + treeId).parents(".layui-inline");
    var checkedOrgName = [], checkedOrgid = [];
    if (OrgTreeCheck.size > 0) {
        OrgTreeCheck.forEach(function (value, key) {
            checkedOrgName.push(value.name);
            checkedOrgid.push(value.id);
        });
        $(treeDiv).find("input[name=bmmc]").val(checkedOrgName.join(","));
        $(treeDiv).find("input[name=djbmdm]").val(checkedOrgid.join(","));
        //获取当前部门下的人员
        getBmryList(checkedOrgid.join(","),"click");
    } else {
        $(treeDiv).find("input[name=bmmc]").val("");
        $(treeDiv).find("input[name=djbmdm]").val("");
    }

}

