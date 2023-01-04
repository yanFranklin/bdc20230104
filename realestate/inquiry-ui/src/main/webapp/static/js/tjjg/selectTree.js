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
        $('.org_select_tree').css({'width': '260px', 'height': '200px', 'left': 40, 'top': 40});
    })();
    //加载树
    custom_tree.load({
        treeId: "select_tree",
        commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs',
        checkboxEnabled: false,
        onClick: function(event, treeId, treeNode) {
            if ("select_tree" == treeId) {
                console.log(treeNode.code);
                $("input[name=bmid]").val(treeNode.id);
                $("input[name=bmmc]").val(treeNode.name);
            }
        }
    });

});


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
 * 搜索
 * @param selName 输入框输入值
 */
$("#deptsel").bind("keyup blur",function(){
    if(this.value!=""){
        ztreeFuzzySearch(this.value)
    }else{
        $('.menuContent .xm-select-empty').css('display', 'none');
        //初始化树
        layui.custom_tree.load({
            treeId: "select_tree",
            commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs',
            checkboxEnabled: false,
            onClick: function(event, treeId, treeNode) {
                if ("select_tree" == treeId) {
                    $("input[name=bmid]").val(treeNode.id);
                    $("input[name=bmmc]").val(treeNode.name);
                }
            }
        });

    }
});

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


