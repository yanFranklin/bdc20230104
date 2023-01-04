layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response',formSelects:'lib/form-select/formSelects-v4'});
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form','response','formSelects'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        response = layui.response,
        formSelects = layui.formSelects,
        form = layui.form;

    formSelects.config('selectGzldyid', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'key'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectJdmcAdd', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'name'            //自定义返回数据中value的key, 默认 value
    }, true);

    window.getJdmc=function (data) {
        $.ajax({
            url: BASE_URL + '/mryj/jdmcs?processDefKey=' + data,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if(data){
                    formSelects.data('selectJdmcAdd','local',{arr:data});
                    formSelects.value('selectJdmcAdd', [$('#jdmcData').val()]);
                }
            }
        });
    }
    var gzldyid = $.getUrlParam('gzldyid');
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    $.ajax({
        url: BASE_URL + '/mryj/gzldymcs',
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            if(data){
                formSelects.data('selectGzldyid','local',{arr:data})
                if(gzldyid!="" && gzldyid!="null"){
                    formSelects.value('selectGzldyid',[gzldyid]);
                    formSelects.disabled('selectGzldyid');
                    getJdmc(gzldyid);
                }
            }
        }
    });


    // 联动对应处理
    formSelects.on('selectGzldyid', function(id, vals, val, isAdd, isDisabled){
        //id:           点击select的id
        //vals:         当前select已选中的值
        //val:          当前select点击的值
        //isAdd:        当前操作选中or取消
        //isDisabled:   当前选项是否是disabled
        $('#gzldymc').val(val.name);
        getJdmc(val.key);
    });

    // 新增或者编辑标识
    var action = $.getUrlParam('action');
    // 编辑时传递的证书编号模板ID
    var mryjid = $.getUrlParam('mryjid');

    // 新增操作默认设值
    if ("add" == action) {
        // $("input[name='sfky']").prop('checked', 'checked');
        $("input[name='sfky']").val(0);
        formSelects.data('selectSjlx','local',{arr:[{"name":"字符串","value":"0"},{"name":"SQL","value":"1"}]})
        formSelects.data('selectYjlxAdd','local',{arr:[{"name":"默认意见","value":"0"},{"name":"可选意见","value":"1"}]})
    }


    //监听指定开关
    form.on('switch(sfky)', function () {
        $("input[name='sfky']").val(this.checked==true ? 1 : 0);
    });


    /**
     * 表单数据提交
     */
    form.on('submit(submitBtn)', function (data) {
        var mryjid=$('#mryjid').val();
        $.ajax({
            url: BASE_URL + "/mryj",
            type: (mryjid!=null&&mryjid!='')?"POST":"PUT",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                if (data && $.isNumeric(data) && data > 0) {
                    success();
                } else {
                    response.fail(data);
                }
            },
            error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
        // 禁止提交后刷新
        return false;
    });

    /**
     * 编辑时填充数据
     * @param data
     */
    window.setData = function (data) {
        // 这里直接用data赋值无效（原因未知），需要重新转JSON
        // formSelects.value('selectSjlx', [data.sjlx]);
        // formSelects.value('selectYjlxAdd', [data.yjlx]);
        formSelects.data('selectSjlx','local',{arr:[{"name":"字符串","value":"0","selected":data.sjlx==0},{"name":"SQL","value":"1","selected":data.sjlx==1}]})
        formSelects.data('selectYjlxAdd','local',{arr:[{"name":"默认意见","value":"0","selected":data.yjlx==0},{"name":"可选意见","value":"1","selected":data.yjlx==1}]})
        form.val('mryjForm', JSON.parse(JSON.stringify(data)));
        $("input[name='sfky']").val(data.sfky);
        $('#jdmcData').val(data.jdmc);
        $('#gzldyidData').val(data.gzldyid);
        if(data.gzldyid !='' && data.gzldyid != undefined){
            formSelects.value('selectGzldyid', [data.gzldyid]);
            getJdmc(data.gzldyid);
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

    /**
     * 提交成功提示
     */
    window.success = function () {
        layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功',{
            time: 1000},
            function(){
                closeParentWindow();
            }
        )};

    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function () {
        var msg = "提交失败，请检查填写内容!";
        layer.alert(msg, {title: '提示'});
    }
});


