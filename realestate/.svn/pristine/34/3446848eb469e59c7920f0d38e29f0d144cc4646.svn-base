/**
 * Created by Administrator on 2019/5/29.
 */

layui.config({
    base: '../../../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response',formSelects:'lib/form-select/formSelects-v4'});
layui.use(['jquery','formSelects','form','response'],function () {
    var sfbzMap = []
    var $ = layui.jquery,
        formSelects = layui.formSelects,
        response = layui.response,
        form = layui.form;
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    $(function () {
        $('.bdc-content').css('min-height',$('body').height()-56);
    });
    formSelects.config('selectSfxmbz', {
        keyName: 'SFXMBZ',            //自定义返回数据中name的key, 默认 name
        keyVal: 'SFXMBZ'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectQlrlb', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectJedw', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectJsff', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('selectddjb', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    var gzldyid = $.getUrlParam('gzldyid');
    var qllx = $.getUrlParam('qllx');
    var djxl = $.getUrlParam('djxl');
    var action = $.getUrlParam('action');
    if (djxl != undefined && djxl != null) {
        $('#djxl').val(djxl);
    }

    //新增时，自动获取当前最大序号，序号赋值加1
    if(action ==="add"){
        loadXh();
    }

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 渲染 select
     */
    $.ajax({
        url: BASE_URL + "/lcpz/zdxx",
        type: "POST",
        data: 'jedw,qlrlb,sfbz,sfjsff,ddjb',
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            if (data) {
                sfbzMap = data.sfbz;
                formSelects.data('selectSfxmbz', 'local', {arr: data.sfbz});
                formSelects.data('selectJedw', 'local', {arr: data.jedw});
                formSelects.data('selectQlrlb', 'local', {arr: data.qlrlb});
                formSelects.data('selectJsff', 'local', {arr: data.sfjsff});
                formSelects.data('selectddjb', 'local', {arr: data.ddjb});
            }
        },
        error: function (e) {
            response.fail(e, '');
        }
    });

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 提交保存数据
     */
    form.on('submit(submitBtn)', function (data) {
        var obj=data.field;
        obj.sfxmmc=replaceBracket(obj.sfxmmc);
        if (obj.sfxmbz) {
            $.each(sfbzMap, function (index, item) {
                if (obj.sfxmbz == item.SFXMBZ) {
                    obj['sfxmdm'] = item.SFXMDM;
                    return false;
                }
            });
        }
        $.ajax({
            url: BASE_URL + "/lcpz/sfxmpz",
            type: "PUT",
            data: JSON.stringify(obj),
            async:false,
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                layer.msg('<img src="../../../../static/lib/bdcui/images/success-small.png" alt="">提交成功',{
                    time: 1000}
                );
                if(action ==="edit"){
                    //编辑完自动关闭弹出层
                    closeWin();
                }else {
                    form.val("sfxmpzForm", {
                        "sl": "1"
                        , "sfxmbz": ""
                        , "ysje": ""
                        , "sfxmmc": ""

                    });
                    formSelects.value('selectSfxmbz', []);
                    formSelects.value('selectJedw', []);
                    formSelects.value('selectQlrlb', []);
                    formSelects.value('selectJsff', []);
                    formSelects.value('selectddjb', []);
                    loadXh();
                }
            },
            error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
        // 禁止提交后刷新
        return false;
    });

    form.verify({
        required: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value == '' || value == null) {
                return '必填项不能为空！';
            }
        }
    });

    formSelects.on('selectSfxmbz', function(id, vals, val, isAdd, isDisabled) {
        //id:           点击select的id
        //vals:         当前select已选中的值
        //val:          当前select点击的值
        //isAdd:        当前操作选中or取消
        //isDisabled:   当前选项是否是disabled
        if (isAdd) {
            $('#sfxmdj').val(val.SFXMDJ);
            $('#ysje').val(val.SFXMDJ);
        } else {
            $('#sfxmdj').val("");
            $('#ysje').val("");
        }
    });
    $('#sl').change(function () {
        var sl=this.value;
        var bz=$('#sfxmdj').val();
        $('#ysje').val(Number(sl)*Number(bz));

    });
    window.setData = function (data) {
        // 这里直接用data赋值无效（原因未知），需要重新转JSON
        form.val('sfxmpzForm', JSON.parse(JSON.stringify(data)));
        formSelects.value('selectSfxmbz', [data.sfxmbz]);
        formSelects.value('selectJedw', [data.jedw]);
        formSelects.value('selectQlrlb', [data.qlrlb]);
        formSelects.value('selectJsff', [data.jsff]);
        formSelects.value('selectddjb', data.ddjb.split(','));
        //赋值sfxmdj单价
        var sfxmbz =data.sfxmbz;
        var sfxmdj =$("#sfxmdj").val();
        if(isNotBlank(sfxmbz) &&!isNotBlank(sfxmdj)) {
            $.each(sfbzMap, function (index, item) {
                if (sfxmbz == item.SFXMBZ) {
                    $("#sfxmdj").val(item.SFXMDJ);
                    return false;
                }
            });
        }

    };
    /**
     * 关闭弹出页面
     */
    window.closeWin = function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    };

    window.closeParentWindow = function () {
        var index = parent.parent.layer.getFrameIndex(window.name);
        parent.parent.layer.close(index);
    };

    // 自动获取当前最大序号，序号赋值加1
    function loadXh() {
        var obj ={};
        obj.djxl =djxl;
        getReturnData("/rest/v1.0/lcpz/querySfxmPzMaxSxh",JSON.stringify(obj),"POST",function (data) {
            if(data ||data ===0){
                $("#xh").val(parseInt(data) +1);
            }

        },function (error) {
            response.fail(error, '');

        })
    }
});