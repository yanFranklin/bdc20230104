
var reverseList = ['zl'];
// 分页重置查询参数
var searchParam =[];
var moduleCode = getQueryString("moduleCode");
var table;
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    table = layui.table;
    // 当前页数据
    var currentPageData = new Array();
    $(function () {
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');
        if(moduleCode){
            setElementAttrByModuleAuthority(moduleCode);
        }
        // 加载Table
        table.render({
            elem: '#djjfglTable',
            toolbar: '#toolbarDemo',
            title: '登记缴费管理',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'xmid', title: '项目编号',  hide: true, width: 150},
                {field: 'xmly', title: '项目来源',  hide: true, width: 150},

                {field: 'gzlslid', title: '工作流实例id',  align: 'center', hide: true, width: 150},
                {field: 'sfxxid', title: '收费信息id',  align: 'center', hide: true, width: 150},


                {field: 'slbh', title: '受理编号', width: 150},
                {field: 'qlr', title: '权利人', minWidth: 80},
                {field: 'djxl', title: '业务类型', minWidth: 100},
                {field: 'zl', title: '坐落', minWidth: 250},
                {field: 'hj', title: '缴费金额(元)', minWidth: 120},
                {field: 'sfsj', title: '缴费时间',
                    templet:function(d){
                        if(d.sfsj != "" && d.sfsj != null){
                            return Format(format(d.sfsj), 'yyyy-MM-dd hh:mm:ss');
                        }else{
                            return "";
                        }
                    },
                    width: 150
                },
                {field: 'sfrxm', title: '收费人员', minWidth:100},
                {field: 'sfztmc', title: '缴费状态',fixed: 'right', minWidth: 100},
                {fixed: 'right', title: '流程图', templet: '#lcTpl', width: 75},
                {fixed: 'right', title:'操作', templet:"#barAction2", width:250}
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                setHeight();
                reverseTableCell(reverseList,"djjfglTable");
                if(moduleCode){
                    setElementAttrByModuleAuthority(moduleCode);
                }
            }
        });

        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            search();
        });
        // 回车查询
        $('.required').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        $('.bdc-table-box').on('mouseenter', 'td', function () {
            if ($(this).text() && reverseList.indexOf($(this).attr("data-field")) !== -1) {
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
            }
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                }, 20);
            });
        });


        //查询
        function search() {
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });

            if (0 == count) {
                warnMsg("请输入必要查询条件（受理编号、缴费人）");
                return false;
            }

            // 获取查询参数
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });

            searchParam = obj;
            addModel();
            // 重新请求
            table.reload("djjfglTable", {
                url:  "/realestate-accept-ui/sf/xm/djjfgl"
                , where: obj
                , page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    reverseTableCell(reverseList,"djjfglTable");
                    removeModal();
                    setHeight();
                    if(moduleCode){
                        setElementAttrByModuleAuthority(moduleCode);
                    }
                }
            });
        }


        //监听工具条
        table.on('tool(djjfglTable)', function (obj) {
            var data = obj.data;

            if (obj.event === 'jfxxxg') {
                jfxxxg(data);
            }
            if (obj.event === 'xxck') {
                xxck(data);
            }

            if(obj.event === 'xgdzph'){
                xgdzph(data);
            }
        });

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

        if(!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i)=="9.")) {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click','.layui-icon-close',function () {
                $(this).siblings('.layui-input').val('');
                $(this).siblings().find('.layui-input').val('');
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

    });

    // 监听表格操作栏按钮
    table.on('toolbar(djjfglTable)', function (obj) {
        var hasJfsbm;
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;

        console.log(obj);

        data.forEach(function(element, index) {
            if (!isNullOrEmpty(element.jfsbm)) {
                hasJfsbm = true;
                $('[lay-id="djjfglTable"]').find('.layui-table-tool').find('button').attr('disabled', 'disabled');
            }
        });

        if (!hasJfsbm) {
            switch (obj.event) {
                case 'jf':
                    jf(data);
                    break;
                case 'qxjf':
                    qxjf(data);
                    break;
                case 'plxg':
                    plxg(data);
                    break;
                case 'plxgfph':
                    plxgfph(data);
                    break;
            }
        } else {
            var actionText = $('#' + obj.event).text();
            ityzl_SHOW_WARN_LAYER("缴款码不为空，不可" + actionText);
        }

        $('[lay-id="djjfglTable"]').find('.layui-table-tool').find('button').removeAttr('disabled');

    });

});

/**
 * 刷新表格
 */
function reloadTable(){
    table.reload("djjfglTable", {
        url:  "/realestate-accept-ui/sf/xm/djjfgl"
        , where: searchParam
        , page: {
            curr: 1, //重新从第 1 页开始
            limits: [10, 50, 100, 200, 500]
        }
        , done: function (res, curr, count) {
            currentPageData = res.data;
            reverseTableCell(reverseList,"djjfglTable");
            removeModal();
            setHeight();
        }
    });
}

/**
 * 查看流程图
 */
function viewLct(gzlslid) {
    layer.open({
        title: '流程详细页面',
        type: 2,
        skin: 'layui-layer-lan',
        area: ['960px', '600px'],
        content: ['/bpm-ui/process/processDetail/' + gzlslid]
    });
}

/**
 * 信息查看
 * @param data
 */
function xxck(data){
    var gzlslid = data.gzlslid;
    var xmly = data.xmly;
    var xmid = data.xmid;
    // 登记的数据查看详情
    if(xmly == "1"){
        $.ajax({
            url: "/realestate-accept-ui/sf/xm/xxck/"+gzlslid,
            type: 'GET',
            success: function (result) {
                if(result){
                    window.open("/realestate-portal-ui/view/new-page.html?name=" + result +"&type=done");
                }else{
                    layer.alert("<div style='text-align: center'>未查询相关的登记业务</div>", {title: '提示'});
                }
            }
        });
    }else{
        // 非登记的数据查看详情
        window.open('/realestate-register-ui/view/lsgx/new-page.html?version=yancheng&xmid=' + xmid + '&processInsId=' + gzlslid);
    }
}

/**
 * 缴费信息修改
 * @param data
 */
function jfxxxg(data){
    if(data.sfzt === 2){
        ityzl_SHOW_WARN_LAYER("已缴费状态不能修改");
        return;
    }

    if(!isNullOrEmpty(data.jfsbm)){
        ityzl_SHOW_WARN_LAYER("缴款码不为空，不可修改");
        return;
    }
    var gzlslid = data.gzlslid;
    var xmid = data.xmid;
    var bdcGzYzQO ={};
    bdcGzYzQO.zhbs = "DJJFGL_JFXX";
    var gzyzParamMap={};
    gzyzParamMap.gzlslid = gzlslid;
    bdcGzYzQO.paramMap = gzyzParamMap;
    gzyzCommon(2,bdcGzYzQO,function (result) {
        console.log("验证通过");
        //单选框弹出层
        layer.open({
            title: '缴费金额修改',
            type: 1,
            area: ['430px','240px'],
            btn: ['确定', '取消'],
            content: $('#bdc-popup-radio')
            ,yes: function(index, layero){
                // 需要记录修改缴费日志 要把修改缴费原因带过去
                var xgjfyy = $('#bdc-popup-radio #xgjfyy').val();

                window.open('./sfxx.html?xgjfyy='+ encodeURI(xgjfyy)+"&processInsId="+gzlslid+"&slbh="+data.slbh+"&xmid="+xmid);
                layer.close(index);
            }
            ,btn2: function(index, layero){
                layer.close(index);

            }
            ,cancel: function(){
                layer.closeAll();
            }
            ,success: function () {

            }
        });

    });
}

/**
 * 缴费
 * @param data
 */
function jf(data){
    if(data && data.length >0){
        var jfList = [];
        for(var i=0;i<data.length;i++){
            var obj = {};
            var sfzt = data[i].sfzt;
            if(sfzt === 2){
                continue;
            }
            jfList.push(data[i]);
        }

        var selectDataList = [];
        for (var i = 0; i < jfList.length; i++) {
            var selectData = jfList[i];
            var bdcGzYzsjDTO = {};
            bdcGzYzsjDTO.gzlslid = selectData.gzlslid;
            selectDataList.push(bdcGzYzsjDTO);
        }
        var bdcGzYzQO ={};
        bdcGzYzQO.zhbs = "DJJFGL_JFXX";
        bdcGzYzQO.paramList = selectDataList;
        console.log("缴费验证参数：");
        console.log(bdcGzYzQO);

        if(jfList.length > 0){
            gzyzCommon(1,bdcGzYzQO,function (result) {
                addModel();
                $.ajax({
                    url: "/realestate-accept-ui/sf/jfcz?type=jf",
                    type: 'POST',
                    data: JSON.stringify(jfList),
                    contentType: 'application/json',
                    success: function (result) {
                        removeModal();
                        ityzl_SHOW_SUCCESS_LAYER("操作成功，即将刷新页面")
                        window.setTimeout(function(){$('#search').click()},2000)
                    },
                    error: function () {
                        removeModal();
                        ityzl_SHOW_WARN_LAYER("操作失败，请检查日志")

                    }
                });
            })

        }else{
            ityzl_SHOW_WARN_LAYER("请先选择未交费的数据");
        }
    }else{
        ityzl_SHOW_WARN_LAYER("请先选择数据");
    }
}

/**
 * 取消缴费
 * @param data
 */
function qxjf(data){
    if(data && data.length >0){
        var realData = [];
        var count = 0;
        for(var i=0;i<data.length;i++){
            var sfzt = data[i].sfzt;
            if(sfzt === 1){
                continue;
            }
            count++;
            realData.push(data[i]);
        }
        if(count == 0){
            ityzl_SHOW_WARN_LAYER("请先选择已缴费的数据");
            return;
        }

        var selectDataList = [];
        for (var i = 0; i < realData.length; i++) {
            var selectData = realData[i];
            var bdcGzYzsjDTO = {};
            bdcGzYzsjDTO.gzlslid = selectData.gzlslid;
            selectDataList.push(bdcGzYzsjDTO);
        }
        var bdcGzYzQO ={};
        bdcGzYzQO.zhbs = "DJJFGL_QXJF";
        bdcGzYzQO.paramList = selectDataList;
        console.log("取消缴费验证参数：");
        console.log(bdcGzYzQO);

        gzyzCommon(1,bdcGzYzQO,function (result) {
            //单选框弹出层
            layer.open({
                title: '取消缴费原因',
                type: 1,
                area: ['430px','210px'],
                btn: ['确定', '取消'],
                content: $('#bdc-popup-textarea')
                ,yes: function(index, layero){
                    // 需要记录修改缴费日志 要把修改缴费原因带过去
                    var qxjfyy = $('#bdc-popup-textarea #qxjfyy').val();
                    if(qxjfyy){
                        var jfList = [];
                        for(var i=0;i<realData.length;i++){
                            realData[i]['qxjfyy'] = qxjfyy;
                            jfList.push(realData[i]);
                        }
                        if(jfList.length > 0){
                            addModel();
                            $.ajax({
                                url: "/realestate-accept-ui/sf/jfcz?type=qxjf",
                                type: 'POST',
                                data: JSON.stringify(jfList),
                                contentType: 'application/json',
                                success: function (result) {
                                    removeModal();
                                    ityzl_SHOW_SUCCESS_LAYER("操作成功，即将刷新页面");
                                    $('#bdc-popup-textarea #qxjfyy').val('');
                                    window.setTimeout(function(){$('#search').click()},2000);
                                },
                                error: function () {
                                    $('#bdc-popup-textarea #qxjfyy').val('');
                                    removeModal();
                                    ityzl_SHOW_WARN_LAYER("操作失败，请检查日志")

                                }
                            });
                        }

                        layer.close(index);
                    }else{
                        ityzl_SHOW_WARN_LAYER("请填写取消缴费原因");
                    }

                }
                ,btn2: function(index, layero){
                    layer.close(index);

                }
                ,cancel: function(){
                    layer.closeAll();
                }
                ,success: function () {

                }
            });
        });

    }else{
        ityzl_SHOW_WARN_LAYER("请先选择数据");
    }
}


/**
 * 修改发票号
 */
function xgdzph(data){
    if(isNotBlank(data.fph)){
        $("#fph").val(data.fph);
    }else{
        $("#fph").val("");
    }
    layer.open({
        title: '补录发票号',
        type: 1,
        area: ['430px'],
        btn: ['确定', '取消'],
        content: $('#bdc-popup-small'),
        yes: function (index, layero) {
            var fph = $("#fph").val();
            if (isNullOrEmpty(fph)) {
                layer.msg('请输入发票号!');
                return false;
            }
            $.ajax({
                url: getContextPath() + "/sf/xx",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify({sfxxid: data.sfxxid, fph:fph}) ,
                success: function (result) {
                    var slbh = data.slbh;
                    var HLNR = "修改前发票号：" + data.fph + "; 修改后发票号: " + fph;
                    if(typeof(data.fph) == "undefined") {
                        HLNR = "修改前无发票号"  + "; 修改后发票号: " + fph;
                    }
                    saveDetailLog("XGFPH", "修改发票号",slbh,{"HLNR": JSON.stringify(HLNR),"QLR":data.qlr,"YWR":data.ywr,"ZL":data.zl,"BDCDYH":data.bdcdyh});
                    ityzl_SHOW_SUCCESS_LAYER("修改成功");
                    reloadTable();

                },
                error: function (xhr, status, error) {
                    var slbh = data.slbh;
                    var HLNR = "修改发票号失败！";
                    saveDetailLog("XGFPH", "修改发票号",slbh,{"HLNR": JSON.stringify(HLNR),"QLR":data.qlr,"YWR":data.ywr,"ZL":data.zl,"BDCDYH":data.bdcdyh});
                    delAjaxErrorMsg(xhr);
                }
            });
            layer.close(index);
        }, btn2: function (index, layero) {
            layer.close(index);
        }
    });
}

/**
 * 批量修改发票号
 */
function plxgfph(data){
    if(data.length == 0){
        ityzl_SHOW_WARN_LAYER("请选择需要修改的记录");
        return;
    }
    var sfxxidList = [];
    $.each(data, function(index, val){
        sfxxidList.push({sfxxid: val.sfxxid,oldFph: val.fph, slbh: val.slbh});
    });

    $("#fph").val("");
    layer.open({
        title: '批量补录发票号',
        type: 1,
        area: ['430px'],
        btn: ['确定', '取消'],
        content: $('#bdc-popup-small'),
        yes: function (index, layero) {
            var fph = $("#fph").val();
            if (isNullOrEmpty(fph)) {
                layer.msg('请输入发票号!');
                return false;
            }
            var sfxxList = [];
            $.each(sfxxidList, function(index, value){
                sfxxList.push({sfxxid: value.sfxxid, fph: fph, oldFph: value.oldFph, slbh: value.slbh});
            });
            var plxgxx = [];//修改信息
            $.each(sfxxidList, function(index, value){
                plxgxx.push({slbh: value.slbh, oldFph: value.oldFph});
            });
            var slbhList = [];
            $.each(data, function(index, value){
                slbhList.push(value.slbh);
            });
            var qlrList = [];
            $.each(data, function(index, value){
                qlrList.push(value.qlr);
            });
            var ywrList = [];
            $.each(data, function(index, value){
                ywrList.push(value.ywr);
            });
            var zlList = [];
            $.each(data, function(index, value){
                zlList.push(value.zl);
            });
            var bdcdyhList = [];
            $.each(data, function(index, value){
                bdcdyhList.push(value.bdcdyh);
            });
            $.ajax({
                url: getContextPath() + "/sf/pl/xx",
                type: 'PUT',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(sfxxList),
                success: function (result) {
                    var HLNR = operationContent(plxgxx,fph);
                    saveDetailLog("PLXGFPH", "批量修改发票号",slbhList.toString(),{"HLNR": JSON.stringify(HLNR),"QLR":qlrList.toString(),"YWR":ywrList.toString(),"ZL":zlList.toString(),"BDCDYH":bdcdyhList.toString()});
                    ityzl_SHOW_SUCCESS_LAYER("修改成功");
                    reloadTable();
                },
                error: function (xhr, status, error) {
                    var HLNR = "批量修改失败！"
                    saveDetailLog("PLXGFPH", "批量修改发票号",slbhList.toString(),{"HLNR": JSON.stringify(HLNR),"QLR":qlrList.toString(),"YWR":ywrList.toString(),"ZL":zlList.toString(),"BDCDYH":bdcdyhList.toString()});
                    delAjaxErrorMsg(xhr);
                }
            });
            layer.close(index);
        }, btn2: function (index, layero) {
            layer.close(index);
        }
    });
}

/**
 *根据模块管理中的元素配置设置权限
 * @param moduleCode 资源名
 */
function setElementAttrByModuleAuthority(moduleCode) {
    $.ajax({
        url: "/realestate-accept-ui/formCenter/moduleAuthority/" + moduleCode,
        type: 'GET',
        async: false,
        success: function (data) {
            console.log(data);
            if(data){
                for(var key in data) {
                    if(data[key] === "hidden") {
                        if($("."+ key)) {
                            $("."+ key).hide();
                        }
                        if($("#"+ key)){
                            $("#"+ key).hide();
                        }
                    }
                }
            }
            // 这里对input框进行样式修改，select框需要在各页面渲染处修改
            resetInputDisabledCss();
        }, error: function (xhr, status, error) {
            console.log("页面权限请求设置失败，请联系管理员！")
        }
    });
}


function plxg(data){
    if(data.length == 0){
        ityzl_SHOW_WARN_LAYER("请选择需要修改的记录");
        return;
    }
    var hasYsf = false;
    var ysfMsgArray= [];
    var gzlslidList = [];
    $.each(data, function(index, val){
        if(val.sfzt && val.sfzt == 2 ){
            hasYsf = true;
            ysfMsgArray.push("受理编号：" + val.slbh);
        }else{
            if(gzlslidList.indexOf(val.gzlslid) < 0){
                gzlslidList.push(val.gzlslid);
            }
        }
    });
    if(hasYsf){
        ityzl_SHOW_WARN_LAYER("选择的数据中存在已缴费。"+ ysfMsgArray.join("，"));
        return;
    }

    var sfxxDataList = [];
    $.each(gzlslidList, function(index, value){
        sfxxDataList.push({
            gzlslid : value,
        });
    });

    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = "DJJFGL_JFXX";
    bdcGzYzQO.paramList = sfxxDataList;
    gzyzCommon(1, bdcGzYzQO,function (result) {
        console.log("验证通过");
        //单选框弹出层
        var index = layer.open({
            type: 2,
            title: "批量缴费金额修改",
            area: ['1150px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: "./plxgSfxm.html?gzlslids="+gzlslidList.join(","),
            cancel: function () {
                reloadTable();
                layer.close();
            }
        });
        layer.full(index);
    });

}

// 保存记录操作信息
function saveDetailLog(logType, viewTypeName,slbh,data){
    var json = JSON.parse(JSON.stringify(data));
    json.logType = logType;
    json.viewTypeName = viewTypeName;
    json.viewType = "accept-ui";
    json.slbh = slbh;
    saveLogInfo(json);
}

// 批量修改操作内容拼接
function operationContent(plxgxx, newFph){

    var result ="";
    for (i = 1,len = plxgxx.length; i <= len; i++){
        // var str = i + "、受理编号：" + plxgxx[i-1].slbh + "；发票号：" + plxgxx[i-1].oldFph + "; "
        var str = i + "、受理编号：" + plxgxx[i-1].slbh + "；发票号："
        if(typeof(plxgxx[i-1].oldFph) == "undefined") {
            str =str + "无发票号;";
        } else {
            str =str + plxgxx[i-1].oldFph + "; ";
        }
        result = result + str;
    }
    result = "修改前：" + result + "<br>" + "修改后：";
    for (i = 1,len = plxgxx.length; i <= len; i++){
        var str1 = i + "、受理编号：" + plxgxx[i-1].slbh + "；发票号：" + newFph + "; "
        result = result + str1;
    }
    return result;
}
//
// // 批量修改操作内容拼接
// function jointContent(anylist) {
//     var result ="";
//     for (i = 1,len = anylist.length; i <= len; i++){
//         var str = i + "、" + anylist[i-1] + "; "+"<br>"
//         result = result + str;
//     }
//     return result;
// }