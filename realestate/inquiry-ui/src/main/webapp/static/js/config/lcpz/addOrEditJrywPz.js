/*
* 新增或者编辑
* */
layui.config({
    base: '../../../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response', formSelects: 'lib/form-select/formSelects-v4'});
var gzldyid = $.getUrlParam('gzldyid');
var qllx = $.getUrlParam('qllx');
var djxl = $.getUrlParam('djxl');
var bdclx = $.getUrlParam('bdclx');
var action = $.getUrlParam('action');
var djlx = "";
var jrfwFilter = [];
layui.use(['jquery', 'formSelects', 'form', 'response'], function () {
    var $ = layui.jquery,
        formSelects = layui.formSelects,
        response = layui.response,
        form = layui.form;
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    $(function () {
        $('.bdc-content').css('min-height', $('body').height() - 56);
    });
    formSelects.config('selectDjxl', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectQllx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectBdclx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectJryw', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('selectBdcdyfwlx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('selectYwfwdm', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'MC'            //自定义返回数据中value的key, 默认 value
    }, true);


    if (djxl != undefined && djxl != null) {
        $('#djxl').val(djxl);
    }

    /*
    * 先根据登记小类请求查询登记类型，根据登记类型确认
    * */
    $.ajax({
        url: BASE_URL + "/lcpz/djlxdjxlgx",
        type: "GET",
        data: {djxl: djxl},
        dataType: "json",
        async: false,
        success: function (data) {
            if (data) {
                djlx = data[0].djlx;
            }
        },
        error: function (e) {
            response.fail(e, '');
        }
    });

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 渲染 select
     */
    $.ajax({
        url: BASE_URL + "/lcpz/zdxx",
        type: "POST",
        data: 'djxl,qllx,djlx,bdclx,bdcdyfwlx,jryw,jrywfw',
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            if (data) {
                //根据登记类型确认接入业务的取值范围
                if (djlx) {
                    var jrywList = getJryw(djlx, data.jryw);
                    formSelects.data('selectJryw', 'local', {arr: jrywList});
                } else {
                    jrfwFilter = data.jryw;
                    formSelects.data('selectJryw', 'local', {arr: data.jryw});
                }
                formSelects.data('selectDjxl', 'local', {arr: data.djxl});
                formSelects.data('selectQllx', 'local', {arr: data.qllx});
                formSelects.data('selectBdclx', 'local', {arr: data.bdclx});
                formSelects.data('selectBdcdyfwlx', 'local', {arr: data.bdcdyfwlx});
                formSelects.data('selectYwfwdm', 'local', {arr: data.jrywfw});
            }
        },
        error: function (e) {
            response.fail(e, '');
        }
    });
    if (action === "add") {
        //新增的时候，djxl，bdclx，qllx是固定的，且不可编辑
        formSelects.value('selectDjxl', [djxl]);
        formSelects.disabled('selectDjxl');
        formSelects.value('selectQllx', [qllx]);
        formSelects.disabled('selectQllx');
        formSelects.value('selectBdclx', [bdclx]);
        formSelects.disabled('selectBdclx');
    }

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 提交保存数据
     */
    form.on('submit(submitBtn)', function (data) {
        var obj = data.field;
        obj.bdcqllxdm = formSelects.value('selectQllx', 'val')[0];
        obj.bdcqllxmc = formSelects.value('selectQllx', 'name')[0];
        obj.bdcdjlxdm = formSelects.value('selectDjxl', 'val')[0];
        obj.bdcdjlxmc = formSelects.value('selectDjxl', 'name')[0];
        obj.bdclx = formSelects.value('selectBdclx', 'val')[0];
        obj.ywdm = formSelects.value('selectJryw', 'val')[0];
        // obj.ywfwdm = formSelects.value('selectYwfwdm', 'name')[0];
        obj.sfdz = formSelects.value('selectBdcdyfwlx', 'val')[0];
        $.ajax({
            url: BASE_URL + "/lcpz/jrywpz",
            type: "POST",
            data: JSON.stringify(obj),
            async: false,
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                layer.msg('<img src="../../../../static/lib/bdcui/images/success-small.png" alt="">提交成功', {
                        time: 1000
                    }
                );
                //编辑完自动关闭弹出层
                closeWin();

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

    formSelects.on('selectJryw', function (id, vals, val, isAdd, isDisabled) {
        //id:           点击select的id
        //vals:         当前select已选中的值
        //val:          当前select点击的值
        //isAdd:        当前操作选中or取消
        //isDisabled:   当前选项是否是disabled
        //根据选择的值设置ywfwdm的值
        for (var i = 0; i < jrfwFilter.length; i++) {
            if (jrfwFilter[i].DM == val.DM) {
                $("#ywfwdm").val(jrfwFilter[i].JRYWFW);
                break;
            }
        }
    });
    window.setData = function (data) {
        // 这里直接用data赋值无效（原因未知），需要重新转JSON
        form.val('jrpzForm', JSON.parse(JSON.stringify(data)));
        formSelects.value('selectDjxl', [data.bdcdjlxdm]);
        formSelects.value('selectQllx', [data.bdcqllxdm]);
        formSelects.value('selectBdclx', [data.bdclx]);
        formSelects.value('selectJryw', [data.ywdm]);
        formSelects.value('selectBdcdyfwlx', [data.sfdz]);
        formSelects.value('selectYwfwdm', [data.ywfwdm]);
        formSelects.disabled('selectDjxl');
        formSelects.disabled('selectQllx');
        formSelects.disabled('selectBdclx');
        formSelects.disabled('selectBdcdyfwlx');
        formSelects.disabled('selectYwfwdm');
        $("#id").val(data.id);
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
});

function getJryw(djlx, jrywList) {
    if (jrywList) {
        for (var i = 0; i < jrywList.length; i++) {
            var dm = jrywList[i].DM;
            if (dm.substr(0, 3) == djlx) {
                jrfwFilter.push(jrywList[i]);
            }
        }
    }
    return jrfwFilter;
}


