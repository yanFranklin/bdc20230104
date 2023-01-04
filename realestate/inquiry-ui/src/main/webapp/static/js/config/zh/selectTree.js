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

layui.use(['layer', 'formSelects', 'custom_tree'],function () {
    var $ = layui.jquery,
        layer = layui.layer,
        formSelects = layui.formSelects,
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

    //下拉面板宽高位置
    (function(){
        var width = $("input[name='lqbm']").width + 40;
        $('.org_select_tree').css({'width': width, 'height':'200px', 'left':120, 'top':40});
    })();

    //加载树
    custom_tree.load({
        treeId: "select_tree",
        commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs',
        checkboxEnabled: false,
        onClick: function(event, treeId, treeNode){
            if("select_tree" == treeId){
                $("input[name=djbmdm]").val(treeNode.code);
                $("input[name=djjg]").val(treeNode.name);
            }
        }
    });
});