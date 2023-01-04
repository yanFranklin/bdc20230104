/**
 * @author  <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * @version 1.0, 2020/01/21
 * @description 子规则校验处理JS
 */
layui.use(['jquery', 'table', 'laytpl', 'layer', 'form',], function () {
    var form = layui.form;
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;

    var zgzData = layui.sessionData('zgzData');
    var bdcGzZgzDTO = JSON.parse(zgzData.data);

    // 初始化
    (function () {
        var csArray = new Array();
        var order = 1;

        for (var i in bdcGzZgzDTO.bdcGzSjlDTOList) {
            var bdcGzSjlDTO = bdcGzZgzDTO.bdcGzSjlDTOList[i];
            for (var j in bdcGzSjlDTO.bdcGzSjlCsDOList) {
                var repeat = false;
                var bdcGzSjlCsDO = bdcGzSjlDTO.bdcGzSjlCsDOList[j];

                // 重复参数只显示一个
                for(var p in csArray){
                    if(csArray[p].sjlcszl == getParamType(bdcGzSjlCsDO.sjlcszl) && csArray[p].sjlcsmc == bdcGzSjlCsDO.sjlcsmc){
                        repeat = true;
                        break;
                    }
                }

                if(!repeat && ( 1 == bdcGzSjlCsDO.sjlcszl || 2 == bdcGzSjlCsDO.sjlcszl || 4 == bdcGzSjlCsDO.sjlcszl)){
                    csArray.push({
                        "xh": order++,
                        "sjlcszl": getParamType(bdcGzSjlCsDO.sjlcszl),
                        "sjlcsmc": bdcGzSjlCsDO.sjlcsmc,
                        "sjlcsz": bdcGzSjlCsDO.sjlcsz
                    });
                }
            }
        }

        table.render({
            elem: '#sjlcs',
            cols: [[
                {field: 'sjlcsmc', title: '参数名称', width: 100, align: 'center'},
                {field: 'sjlcszl', title: '参数类型', width: 100, align: 'center'},
                {field: 'sjlcsz', title: '参数值', minWidth: 250, align: 'center', edit: 'text'},
            ]],
            data: csArray,
            page: false,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            }
        });
    })();

    //编辑完成更新数据
    table.on('edit(sjlcs)', function (obj) {
        var sjlcs = table.cache.sjlcs;

        if(!$.isEmptyObject(bdcGzZgzDTO.bdcGzSjlDTOList)){
            $.each(bdcGzZgzDTO.bdcGzSjlDTOList, function (key, sjlDTO) {
                $.each(sjlDTO.bdcGzSjlCsDOList, function (key, sjlCsDTO) {
                    $.each(sjlcs, function (key, cs) {
                        if(sjlCsDTO.sjlcsmc == cs.sjlcsmc){
                            sjlCsDTO.sjlcsz = cs.sjlcsz;
                        }
                    });
                });
            });
        }
    });

    /**
     * 执行验证
     */
    $("#check").click(function () {
        addModel();

        // 验证属于临时测试，因为有些属性正式保存时候才生成，所以复制对象，避免因为设置ID等导致正式验证问题
        var newBdcGzZgzDTO = JSON.parse(JSON.stringify(bdcGzZgzDTO));
        newBdcGzZgzDTO.gzid = createUUID();

        if(newBdcGzZgzDTO.bdcGzBdsTsxxDOList){
            $.each(newBdcGzZgzDTO.bdcGzBdsTsxxDOList, function (key, bdsTsxxDO) {
                bdsTsxxDO.gzid = newBdcGzZgzDTO.gzid;
                bdsTsxxDO.bdsid = createUUID();
            });
        }

        $.each(newBdcGzZgzDTO.bdcGzSjlDTOList, function (key, sjlDTO) {
            sjlDTO.gzid = newBdcGzZgzDTO.gzid;
            //sql加密
            if(!isNullOrEmpty(sjlDTO.sjnr)){
                sjlDTO.sjnr = Base64.encode(sjlDTO.sjnr);
            }
        });

        $.ajax({
            url: "/realestate-inquiry-ui/zgz/check",
            type: "POST",
            data: JSON.stringify(newBdcGzZgzDTO),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if(data && data.length > 0) {
                    $("#jyjg").val(formatJson(JSON.stringify(data[0])));
                } else {
                    $("#jyjg").val('[ 当前子规则验证结果为空 ]');
                }
            },
            error:function($xhr,textStatus,errorThrown){
                $("#jyjg").val(formatJson($xhr.responseText));
            },
            complete: function () {
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