
layui.config({
    base: '../static/' //静态资源所在路径
}).extend({
    response:'lib/bdcui/js/response'
});
var zdList;
var form;
/**
 * Created by Ypp on 2020/1/3.
 * 检查日志js
 */
layui.use(['form','jquery','table','laydate','layer','response'],function () {
    var $ = layui.jquery,
        table = layui.table,
        response = layui.response,
        laydate = layui.laydate,
        layer = layui.layer;
    form = layui.form,

    $(function(){
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');
        if(moduleCode){
            setElementAttrByModuleAuthority(moduleCode);
        }

        var nowDate = new Date(new Date().toLocaleDateString());
        var nextDate = new Date(nowDate.getTime() + 24*60*60*1000);
        var fxkssj = nowDate.Format('yyyy-MM-dd hh:mm:ss');
        var fxjssj = nextDate.Format('yyyy-MM-dd hh:mm:ss');
        //精确到时分秒日期控件
        laydate.render({
            elem: '#fxsjq',
            type: 'datetime'
            ,trigger: 'click',
            value:nowDate,
            done: function(value,date){
                //选择的结束时间大
                if($('#fxsjz').val() != '' && !completeDate($('#fxsjz').val(),value)){
                    $('#fxsjz').val('');
                    $('.laydate-disabled.layui-this').removeClass('layui-this');
                }
                endFxDate.config.min ={
                    year:date.year,
                    month:date.month-1,
                    date: date.date,
                    hours: date.hours,
                    minutes: date.minutes,
                    seconds: date.seconds
                }
            }
        });
        //初始化日期控件
        var endFxDate =laydate.render({
            elem: '#fxsjz',
            type: 'datetime'
            ,trigger: 'click',
            value:nextDate
        });



        var startGxDate = laydate.render({
            elem: '#gxsjq',
            type: 'datetime'
            ,trigger: 'click',
            done: function(value,date){
                //选择的结束时间大
                if($('#gxsjz').val() != '' && !completeDate($('#gxsjz').val(),value)){
                    $('#gxsjz').val('');
                    $('.laydate-disabled.layui-this').removeClass('layui-this');
                }
                endGxDate.config.min ={
                    year:date.year,
                    month:date.month-1,
                    date: date.date,
                    minutes: date.minutes,
                    seconds: date.seconds
                }
            }
        });
        var endGxDate = laydate.render({
            elem: '#gxsjz',
            type: 'datetime'
            ,trigger: 'click'
        });

        var startSdjcDate = laydate.render({
            elem: '#qsrq',
            type: 'datetime'
            ,trigger: 'click',
            done: function(value,date){
                //选择的结束时间大
                if($('#jsrq').val() != '' && !completeDate($('#jsrq').val(),value)){
                    $('#jsrq').val('');
                    $('.laydate-disabled.layui-this').removeClass('layui-this');
                }
                endSdjcDate.config.min ={
                    year:date.year,
                    month:date.month-1,
                    date: date.date,
                    minutes: date.minutes,
                    seconds: date.seconds
                }
            }
        });
        var endSdjcDate = laydate.render({
            elem: '#jsrq',
            type: 'datetime'
            ,trigger: 'click'
        });

        var startGzDate = laydate.render({
            elem: '#gz_qsrq',
            type: 'datetime'
            ,trigger: 'click',
            done: function(value,date){
                //选择的结束时间大
                if($('#gz_jsrq').val() != '' && !completeDate($('#gz_jsrq').val(),value)){
                    $('#gz_jsrq').val('');
                    $('.laydate-disabled.layui-this').removeClass('layui-this');
                }
                endGzDate.config.min ={
                    year:date.year,
                    month:date.month-1,
                    date: date.date,
                    minutes: date.minutes,
                    seconds: date.seconds
                }
            }
        });
        var endGzDate = laydate.render({
            elem: '#gz_jsrq',
            type: 'datetime'
            ,trigger: 'click'
        });
        var startPlanDate = laydate.render({
            elem: '#plan_qsrq',
            type: 'datetime'
            ,trigger: 'click',
            done: function(value,date){
                //选择的结束时间大
                if($('#plan_jsrq').val() != '' && !completeDate($('#plan_jsrq').val(),value)){
                    $('#plan_jsrq').val('');
                    $('.laydate-disabled.layui-this').removeClass('layui-this');
                }
                endGzDate.config.min ={
                    year:date.year,
                    month:date.month-1,
                    date: date.date,
                    minutes: date.minutes,
                    seconds: date.seconds
                }
            }
        });
        var endGzDate = laydate.render({
            elem: '#plan_jsrq',
            type: 'datetime'
            ,trigger: 'click'
        });
        function completeDate(date1,date2){
            var oDate1 = new Date(date1);
            var oDate2 = new Date(date2);
            if(oDate1.getTime() > oDate2.getTime()){
                return true;
            } else {
                return false;
            }
        }
        //输入框删除图标
        if(!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i)=="9.")) {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click','.layui-icon-close',function () {
                $(this).siblings('.layui-input').val('');
                $(this).siblings().find('.layui-input').val('');
                $(this).siblings('select').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus',function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                $(this).parents('.layui-input-inline').find('.layui-icon-close').removeClass('bdc-hide');
                $(this).siblings('.layui-edge').addClass('bdc-hide');
            }).on('blur',function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                    $this.parents('.layui-input-inline').find('.layui-icon-close').addClass('bdc-hide');
                    $this.siblings('.layui-edge').removeClass('bdc-hide');
                },150)
            });
        }

        //点击高级查询--4的倍数
        $('#seniorSearch').on('click',function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            $(this).parent().toggleClass('bdc-button-box-four');
            $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);

            if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../image/table-none.png" alt="">无数据');
            }else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        });

        table.render({
            elem: '#pageTable',
            id:'pageTableId',
            toolbar: '#toolbarDemo',
            title: '日志表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            even: true,
            cols: [[
                {type: 'checkbox', width:50, fixed: 'left'},
                {field:'LOGID', title:'id',hide:true},
                {field:'JCXX', title:'检查信息'},
                {field:'GZMS', title:'规则描述'},
                {field:'SLBH', title:'受理编号'},
                {field:'XMID', title:'项目ID'},
                {field:'BDCDYH', title:'不动产单元号',width:260},
                {field:'XZWH', title:'限制文号'},
                {field:'FXSJ', title:'发现时间'},
                {field:'GXSJ', title:'更新时间'},
                {field:'JCZT', title:'检查状态',width:90, templet: '#jcztTpl'},
                {field:'JJZT', title:'解决状态',width:90, templet: '#jjztTpl'},
                {field:'JJFS', title:'解决方式',width:90},
                {field:'JCDJ', title:'检查等级',width:90, templet: '#jcdjTpl'},
                {field:'SFGQ', title:'是否挂起',width:90, templet: '#sfgqTpl'},
                {field:'GQYY', title:'挂起原因'},
                {field:'QXDM', title:'行政区划',templet: function (d) {
                        return convertZdDmToMc("qx", d.QXDM);
                    }}

            ]],
            data: [],
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            page: true,
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');


                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }else {
                    $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 131);
                }
            }
        });


        //获取字典表
        $.ajax({
            type: "get",
            url: getContextPath() + "/rest/v1.0/queryGzxx/lcList",
            async:false,
            dataType:"json",
            success: function (data) {
                $('#lcs').append(new Option("请选择", ""));
                $.each(data, function (index, item) {
                    $('#lcs').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                });
                form.render("select");
            }, error: function (e) {
                response.fail(e, '')
            }
        });

        //获取字典表
        $.ajax({
            type: "get",
            url: getContextPath() + "/rest/v1.0/queryGzxx/gzxxList",
            async:false,
            dataType:"json",
            success: function (data) {
                $('#jcgz').append(new Option("请选择", ""));
                $('#jcxx').append(new Option("请选择", ""));
                $.each(data, function (index, item) {
                    //忽略规则不列入检查规则下拉中
                    if(item.sfhl ==0) {
                        $('#jcgz').append(new Option(item.gzms, item.gzid));
                    }
                    $('#jcxx').append(new Option(item.gzms, item.gzid));
                });
                form.render("select");
            }, error: function (e) {
                response.fail(e, '')
            }
        });

        // 获取字典信息
        getZdList();
        loadXzqh();


        //查询
        $("#search").click(function(){
            var obj = {};
            $(".search").each(function (i) {
                var value = $(this).val();
                var name = $(this).attr('name');
                obj[name] = value;
            });
            // 重新请求
            table.reload("pageTableId", {
                url:getContextPath()+"/rest/v1.0/queryCgsjCheck/getCgsjList",
                where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
            });
        })
        table.reload("pageTableId", {
            url:getContextPath()+"/rest/v1.0/queryCgsjCheck/getCgsjList",
            where:{
                "fxkssj":fxkssj,
                "fxjssj":fxjssj
            },page: {
                curr: 1  //重新从第 1 页开始
            }
        });
        //显示表格
        //$("#search").click();

        //头工具栏事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'xxSolve':
                    var selectedNum = checkStatus.data.length;
                    if (selectedNum == 0) {
                        layer.msg('请选择数据进行操作！');
                        return false;
                    }
                    layer.open({
                        title: '解决信息',
                        type: 1,
                        area: ['960px'],
                        btn: ['提交', '取消'],
                        content: $('#bdc-popup-jjxx')
                        ,yes: function(index, layero){
                            var jjfs=$("#jjfs").val();
                            if(isNullOrEmpty(jjfs)){
                                layer.msg('必填项不能为空', {icon: 5});
                                return false;
                            }
                            var logids=[];
                            checkStatus.data.forEach( function(item){
                                logids.push(item.LOGID);
                            })
                            addModel();
                            $.ajax({
                                type: "POST",
                                url: getContextPath() + "/rest/v1.0/queryCgsjCheck/xxSolve",
                                traditional:true,
                                data: {logid: logids, jjfs: jjfs},
                                success:function (data) {
                                    layer.msg(data);
                                    renderTable("pageTableId");
                                    formReset("jjxxForm");
                                    layer.close(index);
                                },error:function (e) {
                                    response.fail(e, '');
                                }, complete: function () {
                                    removeModal();
                                }
                            });
                        }
                        ,btn2: function(){
                            formReset("jjxxForm");
                        }
                        ,cancel: function(){
                            formReset("jjxxForm");
                        }
                    });
                    break;
                case 'cancelSolve':
                    var selectedNum = checkStatus.data.length;
                    if (selectedNum == 0) {
                        layer.msg('请选择数据进行操作！');
                        return false;
                    }
                    // 取消解决
                    var logoutIndex = layer.confirm('您确定要取消吗？', {
                        title: '提示',
                        btn: ['确定', '取消'] //按钮
                    }, function (index) {
                        var logids=[];
                        checkStatus.data.forEach( function(item){
                            logids.push(item.LOGID);
                        })
                        addModel();
                        $.ajax({
                            type: "POST",
                            url: getContextPath() + "/rest/v1.0/queryCgsjCheck/cancelSolve",
                            traditional:true,
                            data: {logid: logids},
                            success:function (data) {
                                layer.msg(data);
                                renderTable("pageTableId");
                            },error:function (e) {
                                response.fail(e, '');
                            }, complete: function () {
                                removeModal();
                            }
                        });
                    });
                    break;
                case 'xxGuaQi':
                    var selectedNum = checkStatus.data.length;
                    if (selectedNum == 0) {
                        layer.msg('请选择数据进行操作！');
                        return false;
                    }
                    layer.open({
                        title: '挂起信息',
                        type: 1,
                        area: ['960px'],
                        btn: ['提交', '取消'],
                        content: $('#bdc-popup-gq')
                        ,yes: function(index, layero){
                            var gqyy=$("#gqyy").val();
                            if(isNullOrEmpty(gqyy)){
                                layer.msg('必填项不能为空', {icon: 5});
                                return false;
                            }
                            var logids=[];
                            checkStatus.data.forEach( function(item){
                                logids.push(item.LOGID);
                            })
                            addModel();
                            $.ajax({
                                type: "POST",
                                url: getContextPath() + "/rest/v1.0/queryCgsjCheck/xxGuaQi",
                                traditional:true,
                                data: {logid: logids, gqyy: gqyy},
                                success:function (data) {
                                    layer.msg(data);
                                    renderTable("pageTableId");
                                    formReset("gqForm");
                                    layer.close(index);
                                },error:function (e) {
                                    response.fail(e, '');
                                }, complete: function () {
                                    removeModal();
                                }
                            });
                        }
                        ,btn2: function(){
                            formReset("gqForm");
                        }
                        ,cancel: function(){
                            formReset("gqForm");
                        }
                    });
                    break;
                case 'xxQxGuaQi':
                    var selectedNum = checkStatus.data.length;
                    if (selectedNum == 0) {
                        layer.msg('请选择数据进行操作！');
                        return false;
                    }
                    // 取消挂起
                    var logoutIndex = layer.confirm('您确定要取消吗？', {
                        title: '提示',
                        btn: ['确定', '取消'] //按钮
                    }, function (index) {
                        var logids=[];
                        checkStatus.data.forEach( function(item){
                            logids.push(item.LOGID);
                        })
                        addModel();
                        $.ajax({
                            type: "POST",
                            url: getContextPath() + "/rest/v1.0/queryCgsjCheck/xxGuaQi",
                            traditional:true,
                            data: {logid: logids,sfqx:0},
                            success:function (data) {
                                layer.msg(data);
                                renderTable("pageTableId");
                            },error:function (e) {
                                response.fail(e, '');
                            }, complete: function () {
                                removeModal();
                            }
                        });
                    });
                    break;
                case 'retest':
                    var selectedNum = checkStatus.data.length;
                    if (selectedNum == 0) {
                        layer.msg('请选择重检数据！');
                        return false;
                    }
                    var xmids=[];
                    checkStatus.data.forEach( function(item){
                        xmids.push(item.XMID);
                    })
                    addModel();
                    $.ajax({
                        type: "POST",
                        url: getContextPath() + "/rest/v1.0/queryCgsjCheck/retest",
                        traditional:true,
                        data: {xmids: xmids},
                        success:function (data) {
                            layer.msg(data);
                            renderTable("pageTableId");
                        },error:function (e) {
                            response.fail(e, '');
                        }, complete: function () {
                            removeModal();
                        }
                    });
                    break;
                case 'retestTimes':
                    layer.open({
                        title: '检测条件',
                        type: 1,
                        area: ['640px'],
                        btn: ['提交', '取消'],
                        content: $('#bdc-popup-sdjc')
                        ,yes: function(index, layero){
                            var qsrq=$("#qsrq").val();
                            var jsrq=$("#jsrq").val();
                            if(isNullOrEmpty(qsrq) && isNullOrEmpty(jsrq)){
                                layer.msg('请填写检测时段', {icon: 5});
                                return false;
                            }
                            addModel();
                            $.ajax({
                                type: "POST",
                                url: getContextPath() + "/rest/v1.0/queryCgsjCheck/retest",
                                data: form.val('sdjcFilter'),
                                success:function (data) {
                                    layer.msg(data);
                                    renderTable("pageTableId");
                                    formReset("sdjcForm");
                                    layer.close(index);
                                },error:function (e) {
                                    response.fail(e, '');
                                }, complete: function () {
                                    removeModal();
                                }
                            });
                        }
                        ,btn2: function(){
                            formReset("sdjcForm");
                        }
                        ,cancel: function(){
                            formReset("sdjcForm");
                        }
                    });
                    break;
                case 'retestRule':
                    layer.open({
                        title: '检测条件',
                        type: 1,
                        area: ['960px','450px'],
                        btn: ['提交', '取消'],
                        content: $('#bdc-popup-gzjc')
                        ,yes: function(index, layero){
                            var jcgz=$("#jcgz").val();
                            if(isNullOrEmpty(jcgz)){
                                layer.msg('必填项不能为空', {icon: 5});
                                return false;
                            }
                            addModel();
                            $.ajax({
                                type: "POST",
                                url: getContextPath() + "/rest/v1.0/queryCgsjCheck/retest",
                                data: form.val('gzFilter'),
                                success:function (data) {
                                    layer.msg(data);
                                    renderTable("pageTableId");
                                    formReset("gzForm");
                                    layer.close(index);
                                },error:function (e) {
                                    response.fail(e, '');
                                }, complete: function () {
                                    removeModal();
                                }
                            });
                        }
                        ,btn2: function(){
                            formReset("gzForm");
                        }
                        ,cancel: function(){
                            formReset("gzForm");
                        }
                    });
                    break;
                case 'export':
                    var selectedNum = checkStatus.data.length;
                    $("#exportForm").attr("action",getContextPath()+"/rest/v1.0/queryCgsjCheck/exportCgsjList")
                    if (selectedNum == 0) {
                        // 取消挂起
                        var logoutIndex = layer.confirm('未勾选数据,将导出全部数据？', {
                            title: '提示',
                            btn: ['确定', '取消'] //按钮
                        }, function (index) {
                            var data=form.val('jcrzForm');
                            if(data){
                                for(var key in data){
                                   $("#export"+key).val(data[key]);
                                }
                            }
                            $("#exportForm")[0].submit();
                        });
                    }else{
                        var logids=[];
                        checkStatus.data.forEach( function(item){
                            logids.push(item.LOGID);
                        })
                        $("#exportlogids").val(logids);
                        $("#exportForm")[0].submit();
                    }
                    break;
                case 'jcqk':
                    addModel();
                    $.ajax({
                        type: "POST",
                        url: getContextPath() + "/rest/v1.0/queryCgsjCheck/jcqk",
                        success:function (data) {
                            layer.msg(data);
                        },error:function (e) {
                            response.fail(e, '');
                        }, complete: function () {
                            removeModal();
                        }
                    });
                    break;
                case 'createCheckplan':
                    layer.open({
                        title: '检查计划',
                        type: 1,
                        area: ['960px','350px'],
                        btn: ['生成计划','保存计划', '取消'],
                        content: $('#bdc-popup-plan')
                        ,yes: function(index, layero){
                            var qsrq=$("#plan_qsrq").val();
                            var jsrq=$("#plan_jsrq").val();
                            var dcjcts=$("#dcjcts").val();
                            if(isNullOrEmpty(qsrq) && isNullOrEmpty(jsrq)){
                                layer.msg('请填写检测时段', {icon: 5});
                                return false;
                            }
                            if(isNullOrEmpty(dcjcts)){
                                layer.msg('请填写单次检测条数', {icon: 5});
                                return false;
                            }
                            addModel('bdc-popup-plan');
                            $.ajax({
                                type: "POST",
                                url: getContextPath() + "/rest/v1.0/checkPlan/showPlan",
                                data: form.val('planFilter'),
                                success:function (data) {
                                    if(data.success){
                                        if(data.zsjl == null){
                                            layer.msg('该时段内不存在需要检测的项目');
                                        }else{
                                            $("#zsjl").val(data.zsjl);
                                            $("#jccs").val(data.jccs);
                                            $("#version").val(data.version);
                                            $("#planDiv").removeClass("bdc-hide");
                                        }
                                    }
                                },error:function (e) {
                                    response.fail(e, '');
                                }, complete: function () {
                                    removeModal();
                                }
                            });
                        } ,btn2: function(index, layero){
                            var qsrq=$("#plan_qsrq").val();
                            var jsrq=$("#plan_jsrq").val();
                            var dcjcts=$("#dcjcts").val();
                            var zsjl=$("#zsjl").val();
                            var jccs=$("#jccs").val();
                            if(isNullOrEmpty(qsrq) && isNullOrEmpty(jsrq)){
                                layer.msg('请填写检测时段', {icon: 5});
                                return false;
                            }
                            if(isNullOrEmpty(dcjcts)){
                                layer.msg('请填写单次检测条数', {icon: 5});
                                return false;
                            }
                            if(isNullOrEmpty(zsjl) && isNullOrEmpty(jccs)){
                                layer.msg('总数据量和检查次数不能为空', {icon: 5});
                                return false;
                            }
                            addModel();
                            $.ajax({
                                type: "POST",
                                url: getContextPath() + "/rest/v1.0/checkPlan/savePlan",
                                data: form.val('planFilter'),
                                success:function (data) {
                                   if(data){
                                       layer.msg("保存检查计划成功");
                                       planFormReset("planForm");
                                       layer.close(index);
                                   }else{
                                       layer.msg("保存检查计划失败");
                                   }
                                },error:function (e) {
                                    response.fail(e, '');
                                }, complete: function () {
                                    removeModal();
                                }
                            });
                            return false;
                        }
                        ,btn3: function(){
                            planFormReset("planForm");
                        }
                    });
                    break;
                case 'viewCheckplan':
                    layer.open({
                        title: '检查计划列表',
                        type: 1,
                        area: ['1200px','420px'],
                        content: $('#planLayer')
                        ,success: function () {
                            table.render({
                                id: 'planTableId',
                                elem: '#planTable',
                                title: '检查计划表格',
                                defaultToolbar: ['filter'],
                                request: {
                                    limitName: 'size', //每页数据量的参数名，默认：limit
                                    loadTotal: true,
                                    loadTotalBtn: false
                                },
                                even: true,
                                cols: [[
                                    {field: 'ID', title: 'ID',hide:true},
                                    {field: 'VERSION', title: '版本'},
                                    {field: 'QSRQ',  width:145,templet:'<div>{{ layui.util.toDateString(d.QSRQ, "yyyy-MM-dd HH:mm:ss") }}</div>' ,title: '起始日期'},
                                    {field: 'JSRQ', width:145,templet:'<div>{{ layui.util.toDateString(d.JSRQ, "yyyy-MM-dd HH:mm:ss") }}</div>' ,title: '结束日期'},
                                    {field: 'ZSJL', title: '总数据量'},
                                    {field: 'DCJCTS', title: '单次检查条数'},
                                    {field: 'JCCS', title: '检查次数'},
                                    {field: 'DQJCCS', title: '当前检查次数',edit: 'text'},
                                    {field: 'CREATEUSER', title: '创建人'},
                                    {field: 'CREATETIME', width:145,templet:'<div>{{ layui.util.toDateString(d.CREATETIME, "yyyy-MM-dd HH:mm:ss") }}</div>' ,title: '创建时间',sort: true}
                                ]],
                                url: getContextPath() + "/rest/v1.0/checkPlan/queryPlan",
                                parseData: function (res) { //res 即为原始返回的数据
                                    return {
                                        "code": res.code, //解析接口状态
                                        "msg": res.message, //解析提示文本
                                        "count": res.totalElements, //解析数据长度
                                        "data": res.content //解析数据列表
                                    };
                                },
                                page: true,
                                done: function () {
                                    if ($('.layui-layer .bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                                        $('.layui-layer .layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
                                    }
                                }
                            });
                        }
                    });
                    break;
            }
        });
        table.on('edit(planFilter)', function(obj){
            var value = obj.value //得到修改后的值
                ,data = obj.data //得到所在行所有键
            $.ajax({
                type: "POST",
                url: getContextPath() + "/rest/v1.0/checkPlan/savePlan",
                data: {'id':data.ID ,'dqjccs':value},
                success:function (data) {
                    if(data){
                        layer.msg("修改成功");
                    }else{
                        layer.msg("修改失败");
                    }
                },error:function (e) {
                    response.fail(e, '');
                }
            });
        });
    })
    //检查计划表单清空
    function planFormReset(id) {
        $("#planDiv").addClass("bdc-hide");
        formReset(id);
    }

    //表单清空
    function formReset(id) {
        $("#"+id)[0].reset();
    }
});

function getZdList() {
    getReturnData("/rest/v1.0/queryCgsjCheck/bdczd",{},"POST",function (data) {
        zdList=data;
    },function () {
    },false);
    return zdList;
}
function loadXzqh(){
    $('#qx').append(new Option("请选择", ""));
    $.each(zdList.qx, function (index, item) {
        $('#qx').append(new Option(item.MC, item.DM));
    });
    form.render("select");
}