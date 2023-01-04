/**
 * Created by Administrator on 2019/5/29.
 */
layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response',formSelects:'lib/form-select/formSelects-v4'});
layui.use(['jquery','formSelects','form','response'],function () {
    var $ = layui.jquery,
        formSelects = layui.formSelects,
        response = layui.response,
        form = layui.form;
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    $(function () {
        $('.bdc-content').css('min-height',$('body').height()-56);
    });
    formSelects.config('selectPzGzldymc', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'key'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectDjxl', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('selectBfzDjxl', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取工作流定义名称
     */
    $.ajax({
        url: BASE_URL + '/mryj/gzldymcs',
        type: "GET",
        dataType: "json",
        async:false,
        success: function (data) {
            if(data){
                formSelects.data('selectPzGzldymc','local',{arr:data})
            }
        }
    });

    $.ajax({
        url: BASE_URL+"/lcpz/zdxx",
        type: "POST",
        data:'djxl',
        contentType: 'application/json',
        dataType: "json",
        async:false,
        success: function (data) {
            if(data){
                formSelects.data('selectDjxl','local',{arr:data.djxl});
                formSelects.data('selectBfzDjxl','local',{arr:data.djxl});
            }
        },
        error: function (e) {
            response.fail(e, '');
        }
    });

    var gzldyid = $.getUrlParam('gzldyid');
    var djxl = $.getUrlParam('djxl');
    var bfzDjxl = $.getUrlParam('bfzDjxl');

    if(gzldyid!=undefined&&gzldyid!=null && bfzDjxl!=undefined && bfzDjxl!=null){
        formSelects.value('selectPzGzldymc',[gzldyid])
        formSelects.disabled('selectPzGzldymc');
        formSelects.value('selectBfzDjxl',[bfzDjxl])
        formSelects.disabled('selectBfzDjxl');
    }

    $($('select[disabled="disabled"]')[0]).after('<img src="../../../static/lib/bdcui/images/lock.png" alt="">');


    form.on('submit(submitBtn)', function (data) {
        var warnLayer = layer.open({
            type: 1,
            title: '提示信息',
            anim: -1,
            skin: 'bdc-tips-right bdc-error-layer',
            closeBtn: 1,
            shade: [0],
            area: ['430px','300px'],
            offset: 'r',
            content: '<div class="bdc-right-tips-box" id="bottomTips">' +
                // '<h3>提示信息<i class="layui-icon layui-icon-close"></i></h3>'+
                '<p id="fztsxx"></p>'+
                '</div>',
            success: function () {
                $('.bdc-tips-right .bdc-right-tips-box .layui-icon-close').on('click',function () {
                    layer.close(warnLayer);
                });
                setTimeout(function () {
                    $('.bdc-error-layer').css('opacity',1)
                },500);
                var djxl=formSelects.value('selectDjxl');
                var djxlPzData=fzDjxlpz(gzldyid,djxl,bfzDjxl);
                showtsxx(djxlPzData);
                var sfxmPzData=fzSfxmpz(gzldyid,djxl,bfzDjxl);
                showtsxx(sfxmPzData);
                var sjclPzData=fzSjclpz(gzldyid,djxl,bfzDjxl);
                showtsxx(sjclPzData);
                var djlxDjxl=fzBdcDjlxDjxl(gzldyid,djxl,bfzDjxl);
                showtsxx(djlxDjxl);
                var djxlQllx=fzBdcDjxlQllx(gzldyid,djxl,bfzDjxl);
                showtsxx(djxlQllx);
                var djxlDjyy=fzBdcDjxlDjyy(gzldyid,djxl,bfzDjxl);
                showtsxx(djxlDjyy);
                var cshFwkg=fzCshFwkg(gzldyid,djxl,bfzDjxl);
                showtsxx(cshFwkg);
                var qlqtzkfj=fzQlqtzkfjpz(gzldyid,djxl,bfzDjxl);
                showtsxx(qlqtzkfj);
            },end:function () {
                closeWin();
            }
        });
        // 禁止提交后刷新
        return false;
    });

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 复制 登记小类配置
     */
    function fzDjxlpz(gzldyid,djxl,bfzDjxl) {
        var returnData='';
        $.ajax({
            url: BASE_URL+"/lcpz/fz/djxlPz",
            type: "get",
            data:{gzldyid:gzldyid,newdjxl:djxl[0].DM,bfzDjxl:bfzDjxl},
            contentType: 'application/json',
            dataType: "json",
            async:false,
            success: function (data) {
                returnData=data;
            },
            error: function (e) {
                response.fail(e, '');
            }
        });
        return returnData;
    }
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description  复制收费项目配置
     */
    function fzSfxmpz(gzldyid,djxl,bfzDjxl){
        var returnData='';
        $.ajax({
            url: BASE_URL+"/lcpz/fz/sfxmpz",
            type: "get",
            data:{gzldyid:gzldyid,newdjxl:djxl[0].DM,bfzDjxl:bfzDjxl},
            contentType: 'application/json',
            dataType: "json",
            async:false,
            success: function (data) {
                returnData=data;
            },
            error: function (e) {
                response.fail(e, '');
            }
        });
        return returnData;
    }
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 复制收件材料配置
     */
    function fzSjclpz(gzldyid,djxl,bfzDjxl){
        var returnData='';
        $.ajax({
            url: BASE_URL+"/lcpz/fz/sjclpz",
            type: "get",
            data:{gzldyid:gzldyid,newdjxl:djxl[0].DM,bfzDjxl:bfzDjxl},
            contentType: 'application/json',
            dataType: "json",
            // async:false,
            success: function (data) {
                returnData=data;
            },
            error: function (e) {
                response.fail(e, '');
            }
        });
        return returnData;
    }

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 复制登记类型登记小类关系
     */
    function fzBdcDjlxDjxl(gzldyid,djxl,bfzDjxl){
        var returnData='';
        $.ajax({
            url: BASE_URL+"/lcpz/fz/djlxDjxl",
            type: "get",
            data:{gzldyid:gzldyid,djxl:djxl[0].DM,bfzDjxl:bfzDjxl},
            contentType: 'application/json',
            dataType: "json",
            async:false,
            success: function (data) {
                returnData=data;
            },
            error: function (e) {
                response.fail(e, '');
            }
        });
        return returnData;
    }
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 登记小类权利类型
     */
    function fzBdcDjxlQllx(gzldyid,djxl,bfzDjxl){
        var returnData='';
        $.ajax({
            url: BASE_URL+"/lcpz/fz/djxlQllx",
            type: "get",
            data:{gzldyid:gzldyid,djxl:djxl[0].DM,bfzDjxl:bfzDjxl},
            contentType: 'application/json',
            dataType: "json",
            async:false,
            success: function (data) {
                returnData=data;
            },
            error: function (e) {
                response.fail(e, '');
            }
        });
        return returnData;
    }
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 复制登记小类登记原因
     */
    function fzBdcDjxlDjyy(gzldyid,djxl,bfzDjxl){
        var returnData='';
        $.ajax({
            url: BASE_URL+"/lcpz/fz/djxlDjyy",
            type: "get",
            data:{gzldyid:gzldyid,djxl:djxl[0].DM,bfzDjxl:bfzDjxl},
            contentType: 'application/json',
            dataType: "json",
            async:false,
            success: function (data) {
                returnData=data;
            },
            error: function (e) {
                response.fail(e, '');
            }
        });
        return returnData;
    }
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description  复制初始化服务开关
     */
    function fzCshFwkg(gzldyid,djxl,bfzDjxl){
        var returnData='';
        $.ajax({
            url: BASE_URL+"/lcpz/fz/cshfwkg",
            type: "get",
            data:{gzldyid:gzldyid,djxl:djxl[0].DM,bfzDjxl:bfzDjxl},
            contentType: 'application/json',
            dataType: "json",
            async:false,
            success: function (data) {
                returnData=data;
            },
            error: function (e) {
                response.fail(e, '');
            }
        });
        return returnData;
    }
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 复制权利其他状况附记配置
     */
    function fzQlqtzkfjpz(gzldyid,djxl,bfzDjxl){
        var returnData='';
        $.ajax({
            url: BASE_URL+"/lcpz/fz/qlqtzkfjpz",
            type: "get",
            data:{gzldyid:gzldyid,djxl:djxl[0].DM,bfzDjxl:bfzDjxl},
            contentType: 'application/json',
            dataType: "json",
            async:false,
            success: function (data) {
                returnData=data;
            },
            error: function (e) {
                response.fail(e, '');
            }
        });
        return returnData;
    }


    function showtsxx(data){
        if(data!=undefined&&data.fzsl!=null&&data.fznr!=null){
            var imgInfo=data.fzsl>0?'<img src="../../../static/lib/bdcui/images/success-small.png" alt="">':'<img src="../../../static/lib/bdcui/images/warn.png" alt="">';
            var info='<p>'+imgInfo +'成功复制'+data.fzsl+'条数据，'+data.fznr+ '</p>';
            $('#fztsxx').before(info);
        }
    }


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