layui.use(['jquery','table','laytpl','layer','element','form'],function () {
    var $ = layui.jquery;
    var form = layui.form;

    var sjlmc = $.getUrlParamWithDecode("sjlmc");
    var sjlid = $.getUrlParamWithDecode("sjlid");

    //同步数据流名称
    //在这个JS里面，其实就是把数据push好
    if(!isNullOrEmpty(sjlmc)){
        $("input[name='sjlmc']").val(sjlmc);
    }

    if(!isNullOrEmpty(sjlid)){
        $("input[name='sjlid']").val(sjlid);
    }

    // 赋值数据流页面数据
    var bdcGzSjlDTOList = window.parent.bdcGzZgzDTO.bdcGzSjlDTOList;
    var bdcGzSjlCsDOList = {};
    for(var i in bdcGzSjlDTOList){
        if(bdcGzSjlDTOList[i].sjlid == sjlid){
            form.val('sjlForm', JSON.parse(JSON.stringify(bdcGzSjlDTOList[i])));
            bdcGzSjlCsDOList = bdcGzSjlDTOList[i].bdcGzSjlCsDOList;

            // 根据数据来源方式设置请求应用和HTTP方法显示与否
            if(bdcGzSjlDTOList[i]){
                if("1" == bdcGzSjlDTOList[i].sjly){
                    $("#div_qqyy").hide();
                    $("#div_fwff").hide();
                } else if("2" == bdcGzSjlDTOList[i].sjly){
                    $("#div_qqyy").show();
                    $("#div_fwff").show();
                }
            }
            break;
        }
    }

    // 赋值参数列表
    if(!$.isEmptyObject(bdcGzSjlCsDOList)){
        for(var i = 0; i < bdcGzSjlCsDOList.length; i++){
            var sjlcszVal = '';
            if(!isNullOrEmpty(bdcGzSjlCsDOList[i].sjlcsz)){
                sjlcszVal = 'value="'+ bdcGzSjlCsDOList[i].sjlcsz +'"';
            }

            $('.bdc-table-sjl tbody').append(
                '<tr>'+
                    '<td>'+
                        '<input type="text" lay-verify="required" class="layui-table-edit set-center" name="sjlcsxh" value="'+ bdcGzSjlCsDOList[i].sjlcsxh +'">'+
                    '</td>'+
                    '<td>'+
                        '<select name="sjlcszl" lay-verify="" value="'+ bdcGzSjlCsDOList[i].sjlcszl +'" id="sjlcszl'+ i +'"><option value="">请选择</option><option value="1">常量</option><option value="2">入参</option><option value="3">动参</option><option value="4">实体</option></select>' +
                    '</td>'+
                    '<td>'+
                        '<input type="text" lay-verify="required" class="layui-table-edit set-center" name="sjlcsmc" value="'+ bdcGzSjlCsDOList[i].sjlcsmc +'">'+
                    '</td>'+
                    '<td>'+
                        '<input type="text" class="layui-table-edit set-center" name="sjlcsz" ' + sjlcszVal + ' >'+
                    '</td>'+
                    '<td class="set-center">'+
                        '<a class="layui-btn layui-btn-xs bdc-major-btn bdc-add-tr">新增</a>'+
                        '<a class="layui-btn layui-btn-danger layui-btn-xs bdc-delete-btn" lay-event="del">删除</a>'+
                    '</td>'+
                '</tr>'
            );

            $("#firstdelbutton").parent().parent().remove();
            form.render('select');
            $("#sjlcszl"+i).val(bdcGzSjlCsDOList[i].sjlcszl);
            form.render('select');
        }
    }

    //新增一行参数
    $('.bdc-table-sjl').on('click','.bdc-add-tr',function () {
        var trIndex = $('.bdc-table-sjl tbody tr').length + 1;
        $('.bdc-table-sjl tbody').append(
            '<tr>'+
                '<td>'+
                    '<input type="text" lay-verify="required" class="layui-table-edit set-center" name="sjlcsxh" value="'+ trIndex +'">'+
                '</td>'+
                '<td>'+
                    '<select name="sjlcszl" lay-verify=""><option value="">请选择</option><option value="1">常量</option><option value="2">入参</option><option value="3">动参</option><option value="4">实体</option></select>' +
                '</td>'+
                '<td>'+
                    '<input type="text" lay-verify="required" class="layui-table-edit set-center" name="sjlcsmc">'+
                '</td>'+
                '<td>'+
                    '<input type="text"  class="layui-table-edit set-center" name="sjlcsz">'+
                '</td>'+
                '<td class="set-center">'+
                    '<a class="layui-btn layui-btn-xs bdc-major-btn bdc-add-tr">新增</a>'+
                    '<a class="layui-btn layui-btn-danger layui-btn-xs bdc-delete-btn" lay-event="del">删除</a>'+
                '</td>'+
            '</tr>');
        //重新渲染select
        form.render('select')
    })

    //删除行
    $('.bdc-table-sjl').on('click','.bdc-delete-btn',function () {
        $(this).parent().parent().remove();
    })

    form.verify({
        qqyy: function(value, item){
            if("2" == $("#sjly").val() && isNullOrEmpty($("#qqyy").val())){
                return "请输入请求应用！";
            }
        },
        fwff: function(value, item){
            if("2" == $("#sjly").val() && isNullOrEmpty($("#fwff").val())){
                return "请选择HTTP方法！";
            }
        }
    });

    // 保存数据流页面
    form.on('submit(submitBtn)', function(data) {
        var bdcGzSjlDTO = data.field;

        // 如果是SQL形式不保存请求应用和HTTP方法字段
        if("1" == $("#sjly").val()){
            bdcGzSjlDTO.qqyy = null;
            bdcGzSjlDTO.fwff = null;
        }

        bdcGzSjlDTO["bdcGzSjlCsDOList"] = getSjlCs();
        delete bdcGzSjlDTO.sjlcsxh;
        delete bdcGzSjlDTO.sjlcsmc;
        delete bdcGzSjlDTO.sjlcsz;
        delete bdcGzSjlDTO.sjlcszl;

        // 校验数据流内容
        if(!bdcGzZgzSjlCheck(bdcGzSjlDTO)){
            window.parent.$("#checkflag").val(false);
            return false;
        }

        var bdcGzSjlDTOList = window.parent.bdcGzZgzDTO.bdcGzSjlDTOList;
        var isExist = false;
        for(var i in bdcGzSjlDTOList){
            if(bdcGzSjlDTOList[i].sjlid == sjlid){
                isExist = true;
                // 更新
                window.parent.bdcGzZgzDTO.bdcGzSjlDTOList[i] = bdcGzSjlDTO;
                break;
            }
        }
        // 新增
        if(false == isExist){
            let deleteSjlIds = sessionStorage.getItem("deleteSjlId");
            console.log(null != deleteSjlIds && deleteSjlIds.indexOf(bdcGzSjlDTO.sjlid) > -1);
            if(!(null != deleteSjlIds && deleteSjlIds.indexOf(bdcGzSjlDTO.sjlid) > -1)){
                window.parent.bdcGzZgzDTO.bdcGzSjlDTOList.push(bdcGzSjlDTO);
            }
        }

        if (window.parent.$("#checkflag").val() == "true") {
            window.parent.$("#checkflag").val("");
        } else {
            successMsg("数据流保存成功");
        }
        // 禁止提交后刷新
        return false;
    });

    function getSjlCs(){
        var sjlcsxhdata=$("input[name='sjlcsxh']").map(function () {
            return $(this).val();
        }).get();
        var sjlcszldata=$("select[name='sjlcszl']").map(function () {
            return $(this).val();
        }).get();
        var sjlcsmcdata=$("input[name='sjlcsmc']").map(function () {
            return $(this).val();
        }).get();
        var sjlcszdata=$("input[name='sjlcsz']").map(function () {
            return $(this).val();
        }).get();

        var sjlcs = new Array();
        for(var j = 0; j < sjlcsxhdata.length; j++){
            var sjlCsDO = {};
            sjlCsDO["sjlcsxh"] = sjlcsxhdata[j];
            sjlCsDO["sjlcszl"] = sjlcszldata[j];
            sjlCsDO["sjlcsmc"] = sjlcsmcdata[j];
            sjlCsDO["sjlcsz"] = sjlcszdata[j];
            sjlcs.push(sjlCsDO);
        }
        return sjlcs;
    }

    /**
     * 需要根据数据来源方式设置请求应用和HTTP方法显示与否
     */
    form.on('select(sjly)', function(data){
        if("1" == data.value){
            $("#div_qqyy").hide();
            $("#div_fwff").hide();
        } else if("2" == data.value){
            $("#div_qqyy").show();
            $("#div_fwff").show();
        }
    });

});