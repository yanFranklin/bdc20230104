/**
 * Created by Administrator on 2019/5/29.
 */
layui.config({
    base: '../../../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response',formSelects:'lib/form-select/formSelects-v4'});
layui.use(['jquery','formSelects','form','response'],function () {
    var $ = layui.jquery,
        formSelects = layui.formSelects,
        response = layui.response,
        form = layui.form;
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    $(function () {
        $('.bdc-content').css('min-height', $('body').height() - 56);
    });

    formSelects.config('selectSjlxAdd', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('selectDjyyAdd', {
        keyName: 'djyy',            //自定义返回数据中name的key, 默认 name
        keyVal: 'djyy'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('selectSqbmAdd', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('selectSfbcAdd', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.render('selectSfbcAdd', {
        create: function (id, name) {
            console.log(id);    //多选id
            console.log(name);  //创建的标签名称
            return name;  //返回该标签对应的val
        }
    });

    var gzldyid = $.getUrlParam('gzldyid');
    var qllx = $.getUrlParam('qllx');
    var djxl = $.getUrlParam('djxl');
    var action = $.getUrlParam('action');
    if (action == 'add') {
        /**
         * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
         * @description 渲染 select
         */
        $.ajax({
            url: BASE_URL + "/lcpz/zdxx?djxl=" + djxl,
            type: "POST",
            data: 'sjlx,sqbm,djyy,sf',
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data) {
                    formSelects.data('selectSjlxAdd', 'local', {arr: data.sjlx});
                    formSelects.data('selectSqbmAdd', 'local', {arr: data.sqbm});
                    formSelects.data('selectDjyyAdd', 'local', {arr: data.djyy});
                    formSelects.data('selectSfbcAdd', 'local', {arr:data.sf});
                }
            },
            error: function (e) {
                response.fail(e, '');
            }
        });
        //新增时，自动获取当前最大序号，序号赋值加1
        loadXh();
       
    }

    if(djxl!=undefined && djxl!=null ){
        $('#djxl').val(djxl);
    }

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 提交保存数据
     */
    form.on('submit(submitBtn)', function (data) {
        var obj=data.field;
        obj.clmc = replaceBracket(obj.clmc).trim();
        $.ajax({
            url: BASE_URL + "/lcpz/djyysjclpz",
            type: "PUT",
            data: JSON.stringify(obj),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                layer.msg('<img src="../../../../static/lib/bdcui/images/success-small.png" alt="">提交成功', {
                        time: 1000
                    },
                    function(){
                    if(action ==="edit"){
                        //编辑完自动关闭弹出层
                        closeWin();
                    }else {
                        form.val("sjclpzForm", {
                            "clmc": ""
                            , "mrfs": "1"
                            , "djyy": ""
                        });
                        formSelects.value('selectSjlxAdd', []);
                        formSelects.value('selectSqbmAdd', []);
                        formSelects.value('selectDjyyAdd', []);
                        formSelects.value('selectSfbcAdd', []);
                        loadXh();
                    }
                    }
                );
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
    $('.nextstep').on('click',function () {
        window.location.href='step6.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })

    $('.beforestep').on('click',function () {
        window.location.href='step4.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })

    window.setData = function (data) {
        // 这里直接用data赋值无效（原因未知），需要重新转JSON
        var datas=JSON.parse(JSON.stringify(data));
        form.val('sjclpzForm', datas);
        /**
         * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
         * @description 渲染 select
         */
        $.ajax({
            url: BASE_URL + "/lcpz/zdxx?djxl=" + djxl,
            type: "POST",
            data: 'sjlx,sqbm,djyy,sf',
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data) {
                    formSelects.data('selectSjlxAdd', 'local', {arr: data.sjlx});
                    formSelects.value('selectSjlxAdd', [String(datas.sjlx)]);
                    formSelects.data('selectDjyyAdd', 'local', {arr: data.djyy});
                    formSelects.value('selectDjyyAdd', [String(datas.djyy)]);
                    formSelects.data('selectSqbmAdd', 'local', {arr: data.sqbm});
                    formSelects.value('selectSqbmAdd', [String(datas.sqbm)]);
                    formSelects.data('selectSfbcAdd','local',{arr:data.sf});
                    formSelects.value('selectSfbcAdd', [String(datas.sfbc)]);
                }
            },
            error: function (e) {
                response.fail(e, '');
            }
        });
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

    //自动获取当前最大序号，序号赋值加1
    function loadXh() {
        var obj ={};
        obj.djxl =djxl;
        getReturnData("/rest/v1.0/lcpz/queryDjyySjclPzMaxSxh",JSON.stringify(obj),"POST",function (data) {
            if(data ||data ===0){
                $("#xh").val(parseInt(data) +1);
            }
        },function (error) {
            response.fail(error, '');

        })
    }

});