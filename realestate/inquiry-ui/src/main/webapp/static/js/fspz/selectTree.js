/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/02/13
 * describe: 输入框下拉树处理JS
 */
layui.config({
    base: '../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4',
    custom_tree: '../../../../realestate-inquiry-ui/static/ztree/custom_tree'
});

var formSelects;

layui.use(['layer', 'formSelects', 'custom_tree'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        formSelects = layui.formSelects,
        custom_tree = layui.custom_tree;


    /**
     * 初始化组织机构下拉控件
     */
    $('.org_select_show1').click(function () {
        if ($('.org_select_show1').text() == '选择') {
            $('.org_select_tree1').css('display', 'block');
            $('.org_select_show1').text('隐藏')
        } else {
            $('.org_select_show1').text('选择');
            $('.org_select_tree1').css('display', 'none');
        }
    });
    $('.org_select_show2').click(function () {
        if ($('.org_select_show2').text() == '选择') {
            $('.org_select_tree2').css('display', 'block');
            $('.org_select_show2').text('隐藏')
        } else {
            $('.org_select_show2').text('选择');
            $('.org_select_tree2').css('display', 'none');
        }
    });

    //下拉面板宽高位置
    (function () {
        var width = $("input[name='bmmc']").width + 40;
        $('.org_select_tree1').css({'width': width, 'height': '200px', 'left': 60, 'top': 40});

        var width2 = $("input[name='lqr']").width + 40;
        $('.org_select_tree2').css({'width': 250, 'height': '200px', 'left': 40, 'top': 40});
    })();

    //加载树
    custom_tree.load({
        treeId: "select_tree1",
        commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs',
        checkboxEnabled: false,
        onClick: function (event, treeId, treeNode) {
            if ("select_tree1" == treeId) {
                $("input[name=bmid]").val(treeNode.id);
                $("input[name=bmmc]").val(treeNode.name);
                //获取当前部门下的人员
                var ryData = []
                $.ajax({
                    type: "GET",
                    url: getContextPath() + "/rest/v1.0/process/users/" + treeNode.id,
                    dataType: "json",
                    success: function (data) {
                        // 多选框里赋值
                        data.forEach(function (v) {
                            ryData.push({"name": v.alias, "value": v.id + "," + v.username});
                        });

                        formSelects.data('selectBjry', 'local', {
                            arr: ryData
                        });
                    }
                });
            }

            if ("select_tree2" == treeId) {
                if ("user" != treeNode.type) {
                    layer.msg("请选择人员节点！");
                    return;
                }

                $("input[name=lqrid]").val(treeNode.id);
                $("input[name=lqr]").val(treeNode.name.substr(4));
                $("input[name=lqrName]").val(treeNode.account);
            }
        }
    });
    // custom_tree.load({
    //     treeId: "select_tree2",
    //     commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/org/roles/users',
    //     checkboxEnabled: false,
    //     onClick: function(event, treeId, treeNode){
    //         if("select_tree1" == treeId){
    //             $("input[name=lqbmid]").val(treeNode.id);
    //             $("input[name=lqbm]").val(treeNode.name);
    //         }
    //
    //         if("select_tree2" == treeId){
    //             if("user" != treeNode.type){
    //                 layer.msg("请选择人员节点！");
    //                 return;
    //             }
    //
    //             $("input[name=lqrid]").val(treeNode.id);
    //             $("input[name=lqr]").val(treeNode.name.substr(4));
    //             $("input[name=lqrName]").val(treeNode.account);
    //         }
    //     }
    // });
});

/**
 * 搜索
 * @param selName 输入框输入值
 */
/**
 * 搜索
 * @param selName 输入框输入值
 */
$("#deptsel").bind("keyup blur", function () {
    if (this.value != "") {
        ztreeFuzzySearch(this.value)
    } else {
        $('.menuContent .xm-select-empty').css('display', 'none');
        //初始化树
        layui.custom_tree.load({
            treeId: "select_tree1",
            commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs',
            checkboxEnabled: false,
            onClick: function (event, treeId, treeNode) {
                if ("select_tree1" == treeId) {
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
function ztreeFuzzySearch(name) {
    var zTreeObj = $.fn.zTree.getZTreeObj("select_tree1");
    var nameKey = zTreeObj.setting.data.key.name;

    ztreeFilter(zTreeObj, name);

    // ztree 树节点过滤
    function ztreeFilter(zTreeObj, _keywords) {
        function filterFunc(node) {
            if (!node.zAsync) {
                zTreeObj.showNode(node);
                zTreeObj.expandNode(node, true);
                zTreeObj.reAsyncChildNodes(node, "refresh");
            }
            if (_keywords.length == 0) {
                zTreeObj.showNode(node);
                return true;
            }
            if (node[nameKey] && node[nameKey].toLowerCase().indexOf(_keywords.toLowerCase()) != -1) {
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
    function processShowNodes(nodesShow, _keywords) {
        if (nodesShow && nodesShow.length > 0) {
            $('.menuContent .xm-select-empty').css('display', 'none');
            if (_keywords.length > 0) {
                $.each(nodesShow, function (n, obj) {
                    var pathOfOne = obj.getPath();
                    if (pathOfOne && pathOfOne.length > 0) {
                        for (var i = 0; i < pathOfOne.length - 1; i++) {
                            zTreeObj.showNode(pathOfOne[i]);
                            zTreeObj.expandNode(pathOfOne[i], true);
                        }
                    }
                });
            } else {
                $('.menuContent .xm-select-empty').css('display', 'block');
                var rootNodes = zTreeObj.getNodesByParam('level', '0');
                $.each(rootNodes, function (n, obj) {
                    zTreeObj.expandNode(obj, true);
                });
            }
        } else {
            $('.menuContent .xm-select-empty').show();
        }
    }
}


//树结构下拉收起重置
$('#reset').on('click', function () {

    $('.org_select_show1').text('选择');
    $('.org_select_tree1').css('display', 'none');
    $('.org_select_show2').text('选择');
    $('.org_select_tree2').css('display', 'none');
});
