/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/02/13
 * describe: 输入框下拉树处理JS
 */
layui.config({
    base: '/realestate-inquiry-ui/static/ztree/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v3',
    custom_tree: 'custom_tree'
});

var custom_tree;
layui.use(['layer', 'formSelects', 'custom_tree'],function () {
    var $ = layui.jquery,
        layer = layui.layer,
        formSelects = layui.formSelects;
        custom_tree = layui.custom_tree;

    /**
     * 初始化组织机构下拉控件
     */
    $('.org_select_show').click(function () {
        if($('.org_select_show').text() == '选择'){
            $('.org_select_tree').css('display','block');
            $('.org_select_show').text('隐藏')
        }else{
            $('.org_select_show').text('选择');
            $('.org_select_tree').css('display','none');
        }
    });
    $('.org_select_show2').click(function () {
        if($('.org_select_show2').text() == '选择'){
            $('.org_select_tree2').css('display','block');
            $('.org_select_show2').text('隐藏')
        }else{
            $('.org_select_show2').text('选择');
            $('.org_select_tree2').css('display','none');
        }
    });

    //下拉面板宽高位置
    (function(){
        var width = $("input[name='lqbm']").width + 40;
        $('.org_select_tree').css({'width': width, 'height':'200px', 'left':60, 'top':40});

        var width2 = $("input[name='lqr']").width + 40;
        $('.org_select_tree2').css({'width':width2, 'height':'200px', 'left':40, 'top':40});
    })();


    //加载树
    custom_tree.load({
        treeId: "select_tree",
        commonUrl: getContextPath() + '/rest/v1.0/ztree/orgs',
        checkboxEnabled: false
    });
    custom_tree.load({
        treeId: "select_tree2",
        commonUrl: getContextPath() + '/rest/v1.0/ztree/org/users',
        checkboxEnabled: false,
        onClick: function(event, treeId, treeNode){
            if("select_tree2" == treeId){
                if("user" != treeNode.type){
                    layer.msg("请选择人员节点！");
                    return;
                }
                $("input[name=kprxm]").val(treeNode.name.substr(4));
            }
        }
    });

});

function loadOrgTree(treeId, onclickFn){
    custom_tree.load({
        treeId: treeId,
        commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs',
        checkboxEnabled: false,
        onClick: onclickFn
    });
}

function bindClik(ele){
    var select_show = $(ele).parents(".layui-inline").find(".org_select_show");
    var select_tree =  $(ele).parents(".org_select_tree");
    $(select_show).click(function () {
        if($(select_show).text() == '选择'){
            $(select_tree).css('display','block');
            $(select_show).text('隐藏')
        }else{
            $(select_show).text('选择');
            $(select_tree).css('display','none');
        }
    });
}