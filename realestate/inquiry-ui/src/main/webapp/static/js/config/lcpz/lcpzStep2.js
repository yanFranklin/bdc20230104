/**
 * Created by Administrator on 2019/5/29.
 */
layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response', formSelects: 'lib/form-select/formSelects-v4'});
var djxlpzid = $.getUrlParam('djxlpzid');
var djxlbdclx = $.getUrlParam('bdclx');
layui.use(['jquery', 'formSelects', 'form', 'response'], function () {
    var $ = layui.jquery,
        formSelects = layui.formSelects,
        response = layui.response,
        form = layui.form;
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    $(function () {
        //循环遍历所有的必填星号
        var elemList = $('.required-span');
        for (var i = 0; i < elemList.length; i++) {
            //判断必填星号大于18px且左边文字大于5时调整样式
            console.log($(elemList[i]).parent().text().length)
            console.log($(elemList[i]).css('font-size')!="18px")
            if($(elemList[i]).css('font-size')!="18px" &&  $(elemList[i]).parent().text().length>6){
                $(elemList[i]).parent().css({"line-height":"11px"})
            }
        }
    });
    formSelects.config('selectPzGzldymc', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'key'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectQllx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectBdclx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('selectZdtzm', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('selectDzwtzm', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectBdcdyfwlx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectYgdjzl', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectDyhCxtj', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectXmCxtj', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectCfCxtj', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectLjzCxtj', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectWlzsCxtj', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectCdCxtj', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    //选择从台账类型字典表
    formSelects.config('selectXztzlx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    //项目来源字典表
    formSelects.config('selectXmly', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    /*
    * 定着物用途字典表
    * */
    formSelects.config('selectDzwyt', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    var bdcdyfwlxArr = [{"DM": "hs", "MC": "户室"}, {"DM": "ychs", "MC": "预测户室"}, {
        "DM": "ljz",
        "MC": "逻辑幢"
    }, {"DM": "xmxx", "MC": "项目内多幢"}];


    var gzldyid = $.getUrlParam('gzldyid');
    var qllx = $.getUrlParam('qllx');
    var djxl = $.getUrlParam('djxl');

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description h获取工作流定义名称
     */
    $.ajax({
        url: BASE_URL + '/mryj/gzldymcs',
        type: "GET",
        dataType: "json",
        async:false,
        success: function (data) {
            if(data){
                formSelects.data('selectPzGzldymc','local',{arr:data});
                formSelects.value('selectPzGzldymc', [gzldyid]);
                formSelects.disabled('selectPzGzldymc');
            }
        }
    });

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 渲染 select
     */
    $.ajax({
        url: BASE_URL + "/lcpz/zdxx",
        type: "POST",
        data: 'qllx,zdzhtzm,bdclx,ygdjzl,xztzcxtj,xztzlx,xmly,fwyt',
        contentType: 'application/json',
        async: false,
        dataType: "json",
        success: function (data) {
            if (data) {
                formSelects.data('selectQllx', 'local', {arr: data.qllx});
                formSelects.data('selectBdcdyfwlx', 'local', {arr: bdcdyfwlxArr});
                formSelects.data('selectZdtzm', 'local', {arr: data.zdzhtzm});
                formSelects.data('selectBdclx', 'local', {arr: data.bdclx});
                formSelects.data('selectYgdjzl', 'local', {arr: data.ygdjzl});
                formSelects.data('selectXztzlx','local',{arr:data.xztzlx});
                formSelects.data('selectXmly', 'local', {arr: data.xmly});
                formSelects.data('selectDzwyt', 'local', {arr: data.fwyt});
                var dyhCxtjList =[];
                var xmCxtjList =[];
                var cfCxtjList =[];
                var ljzCxtjList =[];
                var wlzsCxtjList =[];
                var cdCxtjList = [];
                for (var i = 0; i < data.xztzcxtj.length; i++) {
                    if (data.xztzcxtj[i].TZLX ==="1") {
                        //选择单元号
                        dyhCxtjList.push(data.xztzcxtj[i]);
                    }else if (data.xztzcxtj[i].TZLX ==="2") {
                        //选择产权
                        xmCxtjList.push(data.xztzcxtj[i]);
                    }else if (data.xztzcxtj[i].TZLX ==="3") {
                        //选择查封
                        cfCxtjList.push(data.xztzcxtj[i]);
                    }else if (data.xztzcxtj[i].TZLX ==="4") {
                        //选择逻辑幢
                        ljzCxtjList.push(data.xztzcxtj[i]);
                    }else if (data.xztzcxtj[i].TZLX ==="5") {
                        //选择外联证书
                        wlzsCxtjList.push(data.xztzcxtj[i]);
                    }else if (data.xztzcxtj[i].TZLX ==="8"){
                        //选择查档产权证
                        cdCxtjList.push(data.xztzcxtj[i]);
                    }

                }
                formSelects.data('selectDyhCxtj','local',{arr:dyhCxtjList});
                formSelects.data('selectXmCxtj','local',{arr:xmCxtjList});
                formSelects.data('selectCfCxtj','local',{arr:cfCxtjList});
                formSelects.data('selectLjzCxtj','local',{arr:ljzCxtjList});
                formSelects.data('selectWlzsCxtj','local',{arr:wlzsCxtjList});
                formSelects.data('selectCdCxtj','local',{arr:cdCxtjList});
            }
        },
        error: function (e) {
            response.fail(e, '');
        }
    });

    if( sessionStorage.getItem('bdcSlXztzPzId')){
        enableNextBtn();
    }
    if(gzldyid!=undefined&&gzldyid!=null){
        queryBdcSlxztz(gzldyid);
    }



    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 提交保存数据
     */
    form.on('submit(submitBtn)', function (data) {
        var cxtj={};
        if(isNotBlank(data.field.dyhcxtj)) {
            cxtj.dyhCxtjList = data.field.dyhcxtj.split(',');
        }
        if(isNotBlank(data.field.xmcxtj)) {
            cxtj.xmCxtjList = data.field.xmcxtj.split(',');
        }
        if(isNotBlank(data.field.cfcxtj)) {
            cxtj.cfCxtjList = data.field.cfcxtj.split(',');
        }
        if(isNotBlank(data.field.ljzcxtj)) {
            cxtj.ljzCxtjList = data.field.ljzcxtj.split(',');
        }
        if(isNotBlank(data.field.wlzscxtj)) {
            cxtj.wlzsCxtjList = data.field.wlzscxtj.split(',');
        }
        if(isNotBlank(data.field.cdcxtj)){
            cxtj.cdCxtjList = data.field.cdcxtj.split(',');
        }
        data.field.cxtj =JSON.stringify(cxtj);
        // 验证单元号查询类型是否必填
        if(DyhcxlxRequired()){
            $.ajax({
                url: BASE_URL + "/lcpz/insert/step2",
                type: "PUT",
                data: {param:JSON.stringify(data.field),gzldyid:gzldyid},
                success: function (data) {
                    // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功', {
                            time: 1000
                        }
                    )
                    sessionStorage.setItem('bdcSlXztzPzId', data.pzid);
                    enableNextBtn();
                },
                error: function (e) {
                    delAjaxErrorMsg(e);
                }
            });

            //检查接入配置是否有数据，没有数据默认新增
            var bdcdyfwlxSet = new Set();
            var bdcdyfwlxs = data.field.bdcdyfwlx.split(',');
            for (var i = 0; i < bdcdyfwlxs.length; i++) {
                if (bdcdyfwlxs[i] === "hs" || bdcdyfwlxs[i] === "ychs") {
                    bdcdyfwlxSet.add("4")
                } else if (bdcdyfwlxs[i] === "ljz") {
                    bdcdyfwlxSet.add("2");
                } else if (bdcdyfwlxs[i] === "xmxx") {
                    bdcdyfwlxSet.add("1");
                }
            }
            //防止删除之前新增了很多数据没保存，删除后会刷新table丢失
            var bdcdyfwlxArray = [];
            bdcdyfwlxSet.forEach(function (element, sameElement, set) {
                bdcdyfwlxArray.push(sameElement)
            });
            $.ajax({
                url: BASE_URL + "/lcpz/yzjryw",
                type: "GET",
                data: {djxl: djxl, qllx: qllx, bdclx: djxlbdclx, bdcdyfwlx: bdcdyfwlxArray.join(","), pzid: djxlpzid},
                success: function (data) {

                },
                error: function (e) {
                    delAjaxErrorMsg(e);
                }
            });
            // 禁止提交后刷新
            return false;
        }
    });

    form.verify({
        required: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value == '' || value == null) {
                return '必填项不能为空！';
            }
        }
    });

    $('.nextstep').on('click',function () {
        if(this.attributes.disabled){
            return false;
        }
        window.location.href='step3.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    });

    $('.beforestep').on('click',function () {
        window.location.href='step1.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    });


    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取不动产受理限制台账配置
     */
    function queryBdcSlxztz(gzldyid) {
        if(gzldyid==undefined||gzldyid==null){
            return false;
        }
        $.ajax({
            url: BASE_URL + '/lcpz/bdcSlxztz',
            type: "GET",
            data:{gzldyid:gzldyid},
            dataType: "json",
            success: function (data) {
                if(data){
                    if(data.xztzlx!=null){
                        formSelects.value('selectXztzlx', data.xztzlx.split(','));
                    }
                    if(data.zdtzm!=null){
                        formSelects.value('selectZdtzm', data.zdtzm.split(','));
                    }
                    if(data.dzwtzm!=null){
                        formSelects.value('selectDzwtzm', data.dzwtzm.split(','));
                    }
                    if(data.bdcdyfwlx!=null){
                        formSelects.value('selectBdcdyfwlx', data.bdcdyfwlx.split(','));
                    }
                    if(data.dyhcxlx!=null){
                        formSelects.value('selectDyhcxlx', [data.dyhcxlx]);
                    }
                    if(data.qllx!=null){
                        formSelects.value('selectQllx', data.qllx.split(','));
                    }else{
                        formSelects.value('selectQllx', [qllx]);
                    }
                    if(data.pzid!=undefined && data.pzid!=null){
                        $('#pzid').val(data.pzid);
                        enableNextBtn();
                    }
                    if(data.bdclx!=null){
                        formSelects.value('selectBdclx', data.bdclx.split(','));
                    }
                    if (data.ygdjzl != null) {
                        formSelects.value('selectYgdjzl', data.ygdjzl.split(','));
                    }
                    if (data.xmsjly != null) {
                        formSelects.value('selectXmsjly', [data.xmsjly]);
                    }
                    if (data.xmly != null) {
                        formSelects.value('selectXmly', data.xmly.split(','));
                    }
                    if (data.dzwyt != null) {
                        formSelects.value('selectDzwyt', data.dzwyt.split(','));
                    }
                    if (data.cxtj != null) {
                        var cxtjObj = {
                            "dyhCxtjList": [],
                            "xmCxtjList": [],
                            "cfCxtjList": [],
                            "ljzCxtjList": [],
                            "wlzsCxtjList": []
                        };
                        //选择单元号
                        try {
                            cxtjObj = JSON.parse(data.cxtj);
                        } catch (e) {
                            ityzl_SHOW_WARN_LAYER("选择台账自定义查询条件配置异常,请检查配置");
                            cxtjObj = {
                                "dyhCxtjList": [],
                                "xmCxtjList": [],
                                "cfCxtjList": [],
                                "ljzCxtjList": [],
                                "wlzsCxtjList": []
                            };
                        }
                        formSelects.value('selectDyhCxtj', cxtjObj.dyhCxtjList);
                        formSelects.value('selectXmCxtj', cxtjObj.xmCxtjList);
                        formSelects.value('selectCfCxtj', cxtjObj.cfCxtjList);
                        formSelects.value('selectLjzCxtj', cxtjObj.ljzCxtjList);
                        formSelects.value('selectWlzsCxtj', cxtjObj.wlzsCxtjList);
                        formSelects.value('selectCdCxtj', cxtjObj.cdCxtjList);
                    }
                }
            }
        });
    }

    // 根据选择台账类型判断单元号查询类型是否必填
    function DyhcxlxRequired() {
        var xztzlx = layui.formSelects.value('selectXztzlx','val'),
            dyhcxlx = layui.formSelects.value('selectDyhcxlx','val');
        var required = false;
        if (xztzlx != null) {
            // 判断选择台账类型是否有选中不动产单元信息
            for (var i = 0; i < xztzlx.length; i++) {
                if (xztzlx[i] == 1) {
                    required = true;
                }
            }
            if (required && ( dyhcxlx == null || dyhcxlx == '')) {
                var alertIndex = layer.open({
                    type: 1,
                    skin: 'bdc-small-tips',
                    title: '信息',
                    area: ['320px'],
                    content: '选择台账类型选中不动产单元信息时，单元号查询类型不能为空',
                    btn: ['确定'],
                    btnAlign: 'c',
                    yes: function () {
                        layer.close(alertIndex);
                    }
                });

                return false;
            }else {
                return true;
            }
        }
    }
});