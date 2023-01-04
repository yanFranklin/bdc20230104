/**
 * Created by Ypp on 2020/4/2.
 * 对比信息页面js
 */
var processInsId;
var type;
var xmid;
layui.use(['layer', 'table','laytpl'], function(){
    var layer = layui.layer
        ,table = layui.table
        ,$ = layui.jquery
        ,laytpl = layui.laytpl;

    $(function() {
        processInsId = $.getUrlParam('processInsId');
        type = $.getUrlParam("type");
        xmid = $.getUrlParam("xmid");

        addModel();
        var url = "/realestate-register-ui/rest/v1.0/blxx/log/xxdb?processInsId=" + processInsId;
        if(!isNullOrEmpty(type)){
            url ="/realestate-register-ui/rest/v1.0/blxx/log/getSjdb?processInsId=" + processInsId +"&xmid="+xmid;
        }
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            async: false,
            success: function (data) {
                removeModel();
                var tableData = data;
                var getTpl = tableTpl.innerHTML
                    , view = document.getElementById('tableView');
                laytpl(getTpl).render(tableData, function (html) {
                    view.innerHTML = html;
                });
            }, error: function (xhr, status, error) {
                removeModel();
                delAjaxErrorMsg(xhr);
            }
        });

        // 行为0时列表无数据，默认隐藏处理
        $(function () {
            var $basic = $(document).find('.basic-info');
            $basic.each(function () {
                if($(this).find('.layui-table tr').hasClass('bdc-table-none')){
                    $btn =$(this).find('.bdc-title-sign-btn .bdc-operate-btn');
                    if($btn.html() == '收起'){
                        $btn.html('展开');
                        $(this).find('.bdc-jt-table').toggleClass('bdc-hide');
                    }
                }
            });
        });
        //单击收起、展开按钮
        $('.bdc-form-div').on('click', '.bdc-title-sign-btn', function () {
            var $this = $(this).find('.bdc-operate-btn');
            if ($this.html() == '展开') {
                $this.html('收起');
            } else {
                $this.html('展开');
            }
            $this.parents('.basic-info').find('.bdc-jt-table').toggleClass('bdc-hide');
        });

        //测试当前页面作为弹出层的效果，实际使用时注释或者删掉
        // $('.content-title>p').on('click',function(){
        //     var dbLayer = layer.open({
        //         type: 2,
        //         title: '对比信息',
        //         anim: -1,
        //         skin: 'bdc-tips-right bdc-error-layer',
        //         shade: [0],
        //         area: ['900px','100%'],
        //         offset: 'r',
        //         content: './dbym.html',
        //         success: function () {
        //             setTimeout(function () {
        //                 $('.bdc-error-layer').css('opacity',1)
        //             },200);
        //             $(".bdc-error-layer iframe").contents().find(".bdc-content-fix").remove();
        //             $(".bdc-error-layer iframe").contents().find(".bdc-form-div").css('padding-top','10px');
        //         }
        //     });
        // })
    })
});