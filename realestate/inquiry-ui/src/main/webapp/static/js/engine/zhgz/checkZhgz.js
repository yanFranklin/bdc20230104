/**
 * @author  <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * @version 1.0, 2020/02/12
 * @description 组合规则校验处理JS
 */
layui.use(['jquery', 'table', 'laytpl', 'layer', 'form',], function () {
    var form = layui.form;
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;

    var zhid = $.getUrlParam("zhid");
    var zhbs = $.getUrlParam("zhbs");
    var bdcGzSjlCsDOList ;

    // 初始化
    (function () {
        var csArray = new Array();
        addModel();

        $.ajax({
            url: "/realestate-inquiry-ui/bdcZhGz/zgz/cs?zhid=" + zhid,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if(data && data.length > 0) {
                    bdcGzSjlCsDOList = data;

                    var order = 1;
                    $.each(data, function (key, bdcGzSjlCsDO) {
                        var repeat = false;
                        // 重复参数只显示一个
                        for (var p in csArray) {
                            if (csArray[p].sjlcszl == getParamType(bdcGzSjlCsDO.sjlcszl) && csArray[p].sjlcsmc == bdcGzSjlCsDO.sjlcsmc) {
                                repeat = true;
                                break;
                            }
                        }

                        if (!repeat && (1 == bdcGzSjlCsDO.sjlcszl || 2 == bdcGzSjlCsDO.sjlcszl || 4 == bdcGzSjlCsDO.sjlcszl)) {
                            csArray.push({
                                "xh": order++,
                                "sjlcszl": getParamType(bdcGzSjlCsDO.sjlcszl),
                                "sjlcsmc": bdcGzSjlCsDO.sjlcsmc,
                                "sjlcsz": bdcGzSjlCsDO.sjlcsz
                            });
                        }
                    });

                    table.render({
                        elem: '#sjlcs',
                        cols: [[
                            {field: 'sjlcsmc', title: '参数名称', width: 100, align: 'center'},
                            {field: 'sjlcszl', title: '参数类型', width: 100, align: 'center'},
                            {field: 'sjlcsz', title: '参数值', minWidth: 100, align: 'center', edit: 'text'},
                        ]],
                        data: csArray,
                        page: false,
                        done: function () {
                            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                        }
                    });
                } else {
                    removeModal();
                    layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">未查询到关联的子规则参数信息');
                }
            },
            error:function($xhr,textStatus,errorThrown){
                failMsg("获取组合规则参数信息失败，请重试！");
            },
            complete: function () {
                removeModal();
            }
        });
    })();

    //编辑完成更新数据
    table.on('edit(sjlcs)', function (obj) {
        var sjlcs = table.cache.sjlcs;

        if(!$.isEmptyObject(bdcGzSjlCsDOList)){
            $.each(bdcGzSjlCsDOList, function (key, sjlCsDTO) {
                $.each(sjlcs, function (key, cs) {
                    if(sjlCsDTO.sjlcsmc == cs.sjlcsmc){
                        sjlCsDTO.sjlcsz = cs.sjlcsz;
                    }
                });
            });
        }
    });

    /**
     * 执行验证
     */
    $("#check").click(function () {
        addModel();

        var startTime = (new Date()).getTime();
        $.ajax({
            url: "/realestate-inquiry-ui/bdcZhGz/check",
            type: "POST",
            data: JSON.stringify({"zhbs": zhbs, "bdcGzSjlCsDOList": bdcGzSjlCsDOList}),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if(data && data.zgzTsxxDTOList) {
                    $("#jyjg").val(formatJson(JSON.stringify(data)));

                    var tsxx = "", index = 1;
                    $.each(data.zgzTsxxDTOList, function (key, zgzTsxxDTO) {
                        tsxx += index + "、规则名称：【" + zgzTsxxDTO.gzmc + "】；提示信息：【" + zgzTsxxDTO.tsxx + "】\n\n";
                        index += 1;
                    });
                    $("#tsxx").val(tsxx);
                } else {
                    $("#jyjg").val('[ 当前子规则验证结果为空 ]');
                }
            },
            error:function($xhr,textStatus,errorThrown){
                $("#jyjg").val(formatJson($xhr.responseText));
                var error = JSON.parse($xhr.responseText);
                $("#ycxx").val(error.msg);
            },
            complete: function () {
                var endTime = (new Date()).getTime();
                $("#zxsj").val(((endTime - startTime) / 1000) + ' 秒');
                removeModal();
            }
        });
    });
});

/**
 * 关闭弹出页面
 */
window.closeWin = function(){
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

window.closeParentWindow = function(){
    var index = parent.parent.layer.getFrameIndex(window.name);
    parent.parent.layer.close(index);
};