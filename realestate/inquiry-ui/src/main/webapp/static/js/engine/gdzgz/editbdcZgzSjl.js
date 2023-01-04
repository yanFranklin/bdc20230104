layui.use(['jquery', 'table', 'laytpl', 'layer', 'element', 'form'], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;

    var sjlmc = $.getUrlParamWithDecode("sjlmc");
    var sjlid = $.getUrlParamWithDecode("sjlid");

    //同步数据流名称
    //在这个JS里面，其实就是吧数据push好
    if (!isNullOrEmpty(sjlmc)) {
        $("input[name='sjlmc']").val(sjlmc);
    }

    if (!isNullOrEmpty(sjlid)) {
        $("input[name='sjlid']").val(sjlid);
    }

    // 赋值数据流页面数据
    var bdcGzSjlDTOList = window.parent.bdcGzZgzDTO.bdcGzSjlDTOList;
    console.log(bdcGzSjlDTOList);
    var bdcGzSjlCsDOList = {};
    for (var i in bdcGzSjlDTOList) {
        if (bdcGzSjlDTOList[i].sjlid == sjlid) {
            form.val('sjlForm', JSON.parse(JSON.stringify(bdcGzSjlDTOList[i])));
            bdcGzSjlCsDOList = bdcGzSjlDTOList[i].bdcGzSjlCsDOList;
            break;
        }
    }

    // 赋值参数列表
    for (var i = 0; i < bdcGzSjlCsDOList.length; i++) {
        $('.bdc-table-sjl tbody').append(
            '<tr>' +
            '<td>' +
            '<input type="text" lay-verify="required" class="layui-table-edit set-center" name="sjlcsxh" value="' + bdcGzSjlCsDOList[i].sjlcsxh + '">' +
            '</td>' +
            '<td>' +
            '<select name="sjlcszl" lay-verify="" value="' + bdcGzSjlCsDOList[i].sjlcszl + '" id="sjlcszl' + i + '"><option value="">请选择</option><option value="1">常量</option><option value="2">入参</option><option value="3">动参</option><option value="4">实体</option></select>' +
            '</td>' +
            '<td>' +
            '<input type="text" lay-verify="required" class="layui-table-edit set-center" name="sjlcsmc" value="' + bdcGzSjlCsDOList[i].sjlcsmc + '">' +
            '</td>' +
            '<td>' +
            '<input type="text" class="layui-table-edit set-center" name="sjlcsz" value="' + bdcGzSjlCsDOList[i].sjlcsz + '">' +
            '</td>' +
            '<td class="set-center">' +
            '<a class="layui-btn layui-btn-xs bdc-major-btn bdc-add-tr">新增</a>' +
            '<a class="layui-btn layui-btn-danger layui-btn-xs bdc-delete-btn" lay-event="del">删除</a>' +
            '</td>' +
            '</tr>'
        );

        $("#firstdelbutton").parent().parent().remove();
        form.render('select');
        $("#sjlcszl" + i).val(bdcGzSjlCsDOList[i].sjlcszl);
        form.render('select');
    }


    //新增一行参数
    $('.bdc-table-sjl').on('click', '.bdc-add-tr', function () {
        var trIndex = $('.bdc-table-sjl tbody tr').length + 1;
        $('.bdc-table-sjl tbody').append(
            '<tr>' +
            '<td>' +
            '<input type="text" lay-verify="required" class="layui-table-edit set-center" name="sjlcsxh" value="' + trIndex + '">' +
            '</td>' +
            '<td>' +
            '<select name="sjlcszl" lay-verify=""><option value="">请选择</option><option value="1">常量</option><option value="2">入参</option><option value="3">动参</option><option value="4">实体</option></select>' +
            '</td>' +
            '<td>' +
            '<input type="text" lay-verify="required" class="layui-table-edit set-center" name="sjlcsmc">' +
            '</td>' +
            '<td>' +
            '<input type="text"  class="layui-table-edit set-center" name="sjlcsz">' +
            '</td>' +
            '<td class="set-center">' +
            '<a class="layui-btn layui-btn-xs bdc-major-btn bdc-add-tr">新增</a>' +
            '<a class="layui-btn layui-btn-danger layui-btn-xs bdc-delete-btn" lay-event="del">删除</a>' +
            '</td>' +
            '</tr>');
        //重新渲染select
        form.render('select')
    })

    //删除行
    $('.bdc-table-sjl').on('click', '.bdc-delete-btn', function () {
        $(this).parent().parent().remove();
    })

    // 保存数据流页面
    form.on('submit(submitBtn)', function (data) {
        var bdcGzSjlDTO = data.field;
        bdcGzSjlDTO["bdcGzSjlCsDOList"] = getSjlCs();
        delete bdcGzSjlDTO.sjlcsxh;
        delete bdcGzSjlDTO.sjlcsmc;
        delete bdcGzSjlDTO.sjlcsz;
        delete bdcGzSjlDTO.sjlcszl;

        var bdcGzSjlDTOList = window.parent.bdcGzZgzDTO.bdcGzSjlDTOList;
        var isExist = false;
        for (var i in bdcGzSjlDTOList) {
            if (bdcGzSjlDTOList[i].sjlid == sjlid) {
                isExist = true;
                // 更新
                window.parent.bdcGzZgzDTO.bdcGzSjlDTOList[i] = bdcGzSjlDTO;
                break;
            }
        }
        // 新增
        if (false == isExist) {
            window.parent.bdcGzZgzDTO.bdcGzSjlDTOList.push(bdcGzSjlDTO);
        }

        layer.msg("保存成功");
        // 禁止提交后刷新
        return false;
    });

    function getSjlCs() {
        var sjlcsxhdata = $("input[name='sjlcsxh']").map(function () {
            return $(this).val();
        }).get();
        var sjlcszldata = $("select[name='sjlcszl']").map(function () {
            return $(this).val();
        }).get();
        var sjlcsmcdata = $("input[name='sjlcsmc']").map(function () {
            return $(this).val();
        }).get();
        var sjlcszdata = $("input[name='sjlcsz']").map(function () {
            return $(this).val();
        }).get();

        var sjlcs = new Array();
        for (var j = 0; j < sjlcsxhdata.length; j++) {
            var sjlCsDO = {};
            sjlCsDO["sjlcsxh"] = sjlcsxhdata[j];
            sjlCsDO["sjlcszl"] = sjlcszldata[j];
            sjlCsDO["sjlcsmc"] = sjlcsmcdata[j];
            sjlCsDO["sjlcsz"] = sjlcszdata[j];
            sjlcs.push(sjlCsDO);
        }
        return sjlcs;
    }
});