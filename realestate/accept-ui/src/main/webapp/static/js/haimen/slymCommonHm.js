/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 海门受理页面操作通用js
 * @date : 2020/6/17 14:48
 */

layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response', formSelects: 'lib/form-select/formSelects-v4'});
var formSelects;
var gzldyid = getQueryString("processDefKey");
layui.use(['jquery', 'table', 'layer', 'formSelects'], function () {
    table = layui.table;
    layer = layui.layer;
    formSelects = layui.formSelects;
    //交易信息合同编号字段绑定事件
    table.on('tool(bdcHtbhTable)', function (obj) {
        if (obj.event === 'openpage') {
            console.info(obj.data);
            JyxxModule.linkClick(obj.data);
        }
    });

    //获取抵押合同信息
    $(document).on('click','#queryDyhtxx',function () {
        console.log("获取抵押合同信息");
        queryDyhtxx();
    });

    $(document).on('click','#ckgdxx',function () {
        var ckgdxx = layer.open({
            type: 1,
            title: '信息',
            skin: 'bdc-ckgdxx-layer',
            area: ['430px'],
            content:
                '<div id="bdc-popup-small ckgdxx-layer">'+
                '<form class="layui-form" action="">'+
                '<div class="layui-form-item pf-form-item">'+
                '<div class="layui-inline" style="width: 100%;">'+
                ' <label class="layui-form-label bdc-label-newline">合同编号</label>'+
                ' <div class="layui-input-inline">'+
                '<input type="text" name="htbh" autocomplete="off" placeholder="请输入" class="layui-input">'+
                '</div>'+
                '</div>'+
                '</div>'+
                '</form>'+
                '</div>',
            btn: ['确定'],
            btnAlign: 'c',
            yes: function(index,layero){
                //确定操作
                var htbh = layero.find("input[name='htbh']").val();
                if(!isNotBlank(htbh)){
                    ityzl_SHOW_WARN_LAYER("请输入合同编号。");
                    return;
                }
                addModel();
                getReturnData("/rest/v1.0/slym/getYzthtUrl", {htbh:htbh},"GET",function (data) {
                    removeModal();
                    console.info(data);
                    window.open(data);
                },function(xhr){
                    removeModal();
                    delAjaxErrorMsg(xhr);
                })
                layer.close(index);
            }
        });
    });
});

// 获取商品房交易信息、同步交易信息、房屋信息至受理数据
var getSpfJyxx = function(){
    // 设置商品房交易信息接口参数、请求地址、回调接口等信息
    var beanName = "nt_spfjyxx";
    JyxxModule.setConfig({
        beanName :  beanName,  // 接口标识名称
        url : getContextPath() + "/ycsl/jyxx/querySpfJyxx",
        // 设置弹出框列表头
        cols : [[
            {field:'htbh', title:'合同编号', width:180, templet: '#htbhTpl', event: 'openpage'},
            {field:'zrf', title:'买方', width:150},
            {field:'zcf', title:'开发商', width:150},
            {field:'htdjsj', title:'合同签订时间', width:150,templet: function (d) {
                    return Format(formatChinaTime(new Date(d.htdjsj)), "yyyy-MM-dd");
                }},
            {field:'zl', title:'坐落'}
        ]],
        // 对调用后台交易备案接口数据进行过滤，返回易处理的格式
        dataFormatFn : function(data){
            var fcjyxx =[];
            $.each(data,function(index,value){
                var zrfArray = [];
                var zcfArray = [];
                $.each(value.bdcSlSqr, function(index,sqr){
                    if(sqr.sqrlb == "1"){
                        zrfArray.push(sqr.sqrmc)
                    }else{
                        zcfArray.push(sqr.sqrmc);
                    }
                });
                fcjyxx.push({
                    zrf : zrfArray.join(","),
                    zcf : zcfArray.join(","),
                    zl : value.bdcSlXmDO.zl,
                    htbh : value.bdcSlJyxx.htbh,
                    htdjsj : value.bdcSlJyxx.htdjsj,
                    fcjyBaxxDTO : value
                });
            });
            return fcjyxx;
        },
        // 列表中，点击交易信息合同编号字段事件
        linkClickCallBack : function(data){
            var fcjyBaxx = data.fcjyBaxxDTO;
            if(qlrCache == null){
                showAlertDialog("未获取到输入权利人信息。");
                return false;
            }
            // 处理接口返回数据，多个权利人数据值返回一条，权利人名称按“ ”空格分隔，证件号按“;”分号分隔。
            var msrArray = [];
            $.each(fcjyBaxx.bdcQlr,function (index,msrxx) {
                if("1" == msrxx.qlrlb){
                    var msrmcArray = msrxx.qlrmc.split(" ");
                    var msrzjhArray = msrxx.zjh.split(";");
                    for(var i = 0; i< msrmcArray.length; i++){
                        msrArray.push({
                            qlrmc : msrmcArray[i],
                            zjh : msrzjhArray[i]
                        });
                    }
                }
            });
            // 比对接口返回的权利人信息与表单中的权利人信息是否一致
            var errorMsgArray = [];
            $.each(msrArray,function (index,msrxx) {
                var checkFlag = false;
                $.each(qlrCache, function(i,value){
                    if(value.qlrlb == 1 && value.qlrmc == msrxx.qlrmc && value.zjh == msrxx.zjh){
                        checkFlag = true;
                    }
                });
                if(!checkFlag){
                    errorMsgArray.push("交易买方为"+msrxx.qlrmc+"，证件号为"+msrxx.zjh);
                }
            });
            // 选择的交易信息的权利人与表单权利人信息不一致时，给予提示。
            if(errorMsgArray.length == 0){
                checkHtbhLink(fcjyBaxx);
            }else{
                var errorMsg =errorMsgArray.join("。")+"与填写信息不一致，是否导入？";
                layer.confirm(errorMsg,function(index){
                    addModel();
                    checkHtbhLink(fcjyBaxx);
                });
            }
            // 查询当前备案号是否已被关联，并给予提示。 选择是：重新选择交易信息进行关联。 否:则关联当前备案号。
            function checkHtbhLink(fcjyBaxx){
                getReturnData("/ycsl/jyxx/checkHtbhLinked",{htbh : fcjyBaxx.bdcSlJyxx.htbh}, "GET", function(data){
                    removeModal();
                    console.info(data);
                    if(data){
                        layer.confirm('当前备案号已经被关联，是否重新关联', {
                            btn: ['是', '否']
                        }, function(index, layero){
                            dealSlJyxx(fcjyBaxx);
                        }, function(index){
                            layer.close(index);
                        });
                    }else{
                        dealSlJyxx(fcjyBaxx);
                    }
                });
            }
            // 将交易信息接口数据同步更新值受理库
            function dealSlJyxx(data){
                getReturnData("/ycsl/jyxx/dealSlJyxx?xmid=" + xmid +"&processInsId="+ processInsId,
                    JSON.stringify(data), "POST", function () {
                        removeModal();
                        refreshQlxx();
                        ityzl_SHOW_SUCCESS_LAYER("导入成功。");
                    }, function (error) {
                        console.info(error);
                        removeModal();
                        ityzl_SHOW_WARN_LAYER("导入失败。");
                    }, false);
            }
        }
    });
    // 打开获取房产交易信息页面
    JyxxModule.openPage(function(){
        // 设置打开房产交易信息弹出框后，执行数据查询操作。
        // 南通商品房流程特殊要求： 1、打开弹出框使用fwbm作为参数调用交易信息接口
        //  2、用户点击弹出窗的查询时，使用htbh和zjh作为参数调用交易信息接口
        var fwbm = "";
        if("undefined" != typeof bdcXm){
            fwbm = bdcXm.bdcdywybh;
            if(isNullOrEmpty(fwbm)){
                getReturnData("/slym/xm/xx", {xmid: xmid}, "GET", function (data) {
                    fwbm = data.bdcdywybh;
                }, function (error) {
                    console.info(error);
                }, false);
            }
        }else{
            getReturnData("/slym/xm", {processInsId:processInsId}, "GET", function (data) {
                if(isNotBlank(data)){
                    fwbm = data.bdcdywybh;
                }
            }, function (error) {
                console.info(error);
            }, false);
        }
        JyxxModule.getJyxxData({
            fwbm : fwbm ,
            beanName : beanName
        });
    });
}

// 获取存量房交易信息、同步交易信息、房屋信息至登记、受理数据
var getClfJyxx = function(){
    JyxxModule.setConfig({
        beanName :  "nt_clfhtxx",  // 接口标识名称
        url : getContextPath() + "/ycsl/jyxx/queryFcjyxx",
        cols : [[
            {field:'htbh', title:'合同编号', width:180, templet: '#htbhTpl', event: 'openpage'},
            {field:'msrmc', title:'买方', width:150},
            {field:'cmrmc', title:'卖方', width:150},
            {field:'zl', title:'坐落'}
        ]],
        dataFormatFn : function(data){
            var fcjyxx =[];
            $.each(data.wqxx,function(index,value){
                var msrmcArray = [];
                $.each(value.msr,function(i,msrxx){
                    msrmcArray.push(msrxx.msrmc);
                });
                var cmrmcArray = [];
                $.each(value.cmr,function(j,cmrxx){
                    cmrmcArray.push(cmrxx.cmrmc);
                });
                var fwzl = value.fw[0].zl + (value.fw.length>1?"等":"");
                fcjyxx.push({
                    msrmc : msrmcArray.join(","),
                    cmrmc : cmrmcArray.join(","),
                    zl : fwzl,
                    htbh : value.htbh,
                    wqxx : value
                });
            });
            return fcjyxx;
        },
        linkClickCallBack : function(data){
            var data  = data.wqxx;
            if(qlrCache == null){
                showAlertDialog("未获取到输入权利人信息。");
                return false;
            }
            var errorMsgArray = [];
            $.each(data.msr,function (index,msrxx) {
                var checkFlag = false;
                $.each(qlrCache,function(i,value){
                    if(value.qlrlb == 1 && value.qlrmc == msrxx.msrmc && value.zjh == msrxx.msrzjh){
                        checkFlag = true;
                    }
                });
                if(!checkFlag){
                    errorMsgArray.push("交易买方为"+msrxx.msrmc+"，证件号为"+msrxx.msrzjh);
                }
            });

            if(errorMsgArray.length == 0){
                proxyDealDjxx(data);
            }else{
                var errorMsg =errorMsgArray.join("。")+"与填写信息不一致，是否导入？";
                layer.confirm(errorMsg,function(index){
                    addModel();
                    proxyDealDjxx(data);
                });
            }
            // 同步更新交易信息、房屋信息等至登记与受理数据
            function proxyDealDjxx(data){
                if(data.fw || data.fw.length > 0){
                    if(isNotBlank(data.sqsj)){
                        data.sqsj = format(new Date(data.sqsj));
                    }
                    dealDjxx(data);
                }else{
                    showAlertDialog("未获取到房产合同交易信息中的房屋交易价格信息。");
                }
            }
        }
    });
    JyxxModule.openPage(function(){
        JyxxModule.queryJyxx();
    });
}

// 获取商品房交易信息、同步合同编号、交易价格，上传pdf
var getNtSpfBaJyxx = function(){
    // 设置商品房交易信息接口参数、请求地址、回调接口等信息
    var beanName = "zjj_spfbaxx";
    JyxxModule.setConfig({
        beanName :  beanName,  // 接口标识名称
        url : getContextPath() + "/ycsl/jyxx/queryNtSpfBaJyxx",
        cols : [[
            {field:'htbh', title:'合同编号', width:180, templet: '#htbhTpl', event: 'openpage'},
            {field:'msrmc', title:'买方', width:150},
            {field:'cmrmc', title:'卖方', width:150},
            {field:'zl', title:'坐落'}
        ]],
        dataFormatFn : function(data){
            if (!isNotBlank(data)){
                showAlertDialog("未获取到房产合同交易信息。");
            }
            var fcjyxx =[];

            $.each(data,function(index,value){
                fcjyxx.push({
                    msrmc : value.gmfmc,
                    cmrmc : value.agentname,
                    zl : value.fwdz,
                    htbh : value.htbh
                });
            });
            return fcjyxx;
        },
        linkClickCallBack: function (data){
            // 设置合同编号、合同价格到流程中并上传附件
            JyxxModule.queryNtJyxx(data.htbh,false);
        }
    });
    JyxxModule.openPage(function(){
        JyxxModule.queryNtJyxx("",true);
    });
}

var JyxxModule = function(){
    // 默认参数
    var defaultConfig = {
        beanName : null,
        cols : null,
        url : null,
        dataFormatFn : function(data){
            return data;
        },
        setQueryParam : null,
        linkClickCallBack : null
    };
    /**
     * 设置基本参数信息
     * @param config
     *  beanName（接口标识符） 、 cols（展示的数据列名）展示的数据列名 、url（数据表格异步请求地址）数据表格异步请求地址、
     *  dataFormatFn（数据过滤回调接口）、setQueryParam（设置表格查询参数回调方法）
     */
    function setConfig(config){
        defaultConfig = $.extend({}, defaultConfig, config);
    }
    /**
     * 打开接口返回的数据页面
     */
    function openPage(callback){
        var qlrxx= {};
        if(qlrCache != null){
            $.each(qlrCache,function(index,value){
                if(value.qlrlb == 1){
                    qlrxx = value;
                    return false;
                }
            });
        }
        if(!isNotBlank(qlrxx)){
            showAlertDialog("请先输入权利人信息。");
            return false;
        }
        layui.use(['jquery', 'table'], function() {
            layer = layui.layer;
            layer.open({
                title: '获取房产交易合同信息',
                type: 1,
                skin: 'bdc-spf-layer',
                area: ['960px','500px'],
                content: $("#bdc-popup-large"),
                success: function (layero,index) {
                    //清空已有值
                    $('#contractNo').val('');
                    $('#mfzjh').val('');
                    //自动获取焦点
                    $('#contractNo').focus();
                    if(isNotBlank(qlrxx)){
                        $('#mfzjh').val(qlrxx.zjh);
                        $('#mfxm').val(qlrxx.qlrmc);
                    }
                    callback();
                }
            });
        });

    };
    // 加载页面数据表格
    function loadTable(data){
        table.render({
            elem: '#pageTable',
            title: '房产交易合同信息',
            even: true,
            limit: 3000,
            cols: defaultConfig.cols,
            data: data,
            page: false,
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                if($('.layui-table-body>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
                }
            }
        });
    };
    // 校验查询参数
    function checkQueryParam(){ //校验查询参数是否为空
        var mfzjh = $('#mfzjh').val();
        var contractNo = $('#contractNo').val();
        if(!isNotBlank(mfzjh) && !isNotBlank(contractNo)){
            showAlertDialog("请先输入证件号或合同编号信息。");
            return true;
        }
        return false;
    };
    // 按钮点击查询事件
    function queryJyxx(){
        if(checkQueryParam()){
            return;
        }
        addModel();
        var param = {
            zjh : $('#mfzjh').val(),
            htbh : $('#contractNo').val(),
            beanName : defaultConfig.beanName
        };
        if(typeof defaultConfig.setQueryParam === "function"){
            param = $.extend({}, param, defaultConfig.setQueryParam());
        }
        getJyxxData(param);
    };

    function queryNtJyxx(htbh,onlyQuery){
        if(checkQueryParam()){
            return;
        }
        addModel();
        if(isNullOrEmpty(htbh)){
            htbh = $('#jyhth').val();
        }
        var param = {
            zjh : $('#mfzjh').val(),
            htbh : htbh,
            gzlslid: processInsId,
            xmid: xmid,
            beanName : defaultConfig.beanName,
            onlyQuery: onlyQuery
        };
        if(typeof defaultConfig.setQueryParam === "function"){
            param = $.extend({}, param, defaultConfig.setQueryParam());
        }
        if(onlyQuery === false){
            getJyxxData(param);
        } else {
            getJyxxData(param,true);
        }
    };

    // 点击合同编号回调事件
    function linkClick(data){
        if(typeof defaultConfig.linkClickCallBack === "function"){
            defaultConfig.linkClickCallBack(data);
        }
    };
    // 调用后台请求获取交易信息数据
    function getJyxxData(param,noshow){
        $.ajax({
            url: defaultConfig.url,
            type: 'GET',
            data: param,
            dataType: 'json',
            success: function (data) {
                removeModal();
                if(isNullOrEmpty(noshow)) {
                    layer.alert('获取成功！', {title: '提示'});
                    //非查询的情况下重新加载权利信息
                    commonReloadQlxx();
                }
                if (!isNotBlank(data)) {
                    data = [];
                }
                // 调用数据过滤回调方法
                if(typeof defaultConfig.dataFormatFn === "function"){
                    loadTable(defaultConfig.dataFormatFn(data));
                }else{
                    loadTable(data);
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr);
            }
        });
    }

    return {
        openPage : openPage,
        setConfig : setConfig,
        queryJyxx : queryJyxx,
        queryNtJyxx : queryNtJyxx,
        linkClick : linkClick,
        getJyxxData : getJyxxData
    };
}();

function layerOpen(title, url) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true
    });
    layer.full(index);
}

//领证信息  新增
function addLzxxTr(elem){
    var $this = $(elem);
    var $tbody = $this.parents('.basic-info').find('.bdc-table-box-lzr tbody');
    var trLength = $this.parents('.basic-info').find('.bdc-table-box-lzr tbody .bdc-lzxx-tr').length;
    if(trLength == 0){
        $tbody.find('.bdc-table-none').remove();
    }
    var lzrxx = {};
    var addXmid = "";
    var xmidArray = $(".layui-this").find("input[name='xmid']");
    var xmidInput = xmidArray[0];
    addXmid = $(xmidInput).val();
    lzrxx.xmid = addXmid;
    lzrxx.zd = zdList;
    var getLzxxTpl = addLzxxTpl.innerHTML;
    laytpl(getLzxxTpl).render(lzrxx, function(html){
        $tbody.append(html);
    });
    form.render();
    resetSelectDisabledCss();
}
//领证信息操作列 删除
function deleteLzxxTr(lzrid,elem,djxl){
    if(isNotBlank(lzrid)) {
        showConfirmDialog("提示","是否确认删除","deleteLzr","'"  + lzrid + "','" + djxl + "'","","",true);
    } else {
        var $this = $(elem);
        $this.parents('.bdc-lzxx-tr').remove();
    }
}

function deleteLzr(lzrid,djxl) {
    if(isNotBlank(lzrid)) {
        var url = "";
        if(lclx === "zhlc") {
            url = "/slym/lzr/nt/lzrdelte?lzrid=" + lzrid + "&gzlslid=" + processInsId;
        }
        if(lclx === "pllc") {
            url = "/slym/lzr/nt/lzrdelte/pllc?gzlslid=" + processInsId + "&lzrid=" + lzrid;
        }
        if(lclx === "plzh") {
            url = "/slym/lzr/nt/lzrdelte/plzh?gzlslid=" + processInsId + "&lzrid=" + lzrid + "&djxl=" + djxl;
        }
        getReturnData(url, '',"DELETE",function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("删除成功");
            if(lclx ==="zhlc") {
                refreshQlxx();
            } else if(lclx === "pllc") {
                loadLzrxx();
            } else if(lclx === "plzh") {
                loadLzr();
            }

        },function(xhr){
            removeModal();
            delAjaxErrorMsg(xhr);
        })
    }
}

// 档案调用
function dady(processInsId) {
    // 判断是否是多个不动单元
    getReturnData("/slym/xm/dady", {
        processInsId: processInsId
    }, 'GET', function (data) {
        if (data) {
            if (data === "empty") {
                layer.msg("未查询到当前 bdcdyh 上一手的产权")
            } else {
                var dadyInfo = tellTdFromBdc(data.xmid);

                if(!dadyInfo || isNullOrEmpty(dadyInfo.hmDadyUrl)) {
                    ityzl_SHOW_WARN_LAYER("未配置档案调用地址，请联系管理员!");
                    return;
                }
                window.open(dadyInfo.hmDadyUrl + data.slbh);
            }
        } else {
            layerOpen("档案调用列表", "/realestate-accept-ui/haimen/query/dadyList.html?processInsId=" + processInsId);
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}


//受理页面顶部按钮位置设置和按钮事件
function titleShowUi() {
    $(".jyxx-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#sqs").hide();
    });
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取商品房交易信息
     */
    $('#querySpfJyxx').click(function (e) {
        console.log("获取商品房交易信息");
        getSpfJyxx();
    });
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取存量房交易信息
     */
    $('#queryClfbaJyxx').click(function (e) {
        console.log("获取存量房交易信息");
        getClfJyxx();
    });

    /**
     * @author <a href ="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 获取商品房房备案信息(南通)
     */
    $('#queryNtSpfbaJyxx').click(function (e) {
        console.log("获取商品房房备案信息(南通)");
        getNtSpfBaJyxx();
    });

    //获取税票
    $('#querySwspFj').click(function (e) {
        console.log("获取税票");
        querySwspFj();
    });

    $(".sqs-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#jyxx").hide();
        if (includeDyaq) {
            printData('dyasqs');
            console.log("打印抵押申请书")
        } else {
            var qlrlx = getQlrlx();
            console.log("权利人类型：" + qlrlx);
            if (qlrlx == '1') {
                printDifModelData('grsqs',lclx);
                console.log("打印个人申请书")
            } else {
                printDifModelData('dwsqs',lclx);
                console.log("打印单位申请书")
            }
        }
    });

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取权利人类型
     */
    function getQlrlx() {
        var result = '1';
        $.ajax({
            url: getContextPath() + "/slym/qlr/list/xm",
            type: 'GET',
            dataType: 'json',
            data: {xmid: xmid, qlrlb: "1", lclx: lclx},
            async: false,
            success: function (data) {
                if (data && data.length > 0) {
                    result = data[0].qlrlx;
                }
            }
        });
        return result;
    }

    $(".jyxx-btn li").click(function () {
        $("#jyxx").hide();
    });
    $(".sqs-btn li").click(function () {
        $("#sqs").hide();
    });

    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && elem.id == 'print') {
                return;
            }
            elem = elem.parentNode;
        }
        $("#print").hide();
    });
    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && elem.id == 'jyxx') {
                return;
            }
            elem = elem.parentNode;
        }
        $("#jyxx").hide();
    });
    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && elem.id == 'sqs') {
                return;
            }
            elem = elem.parentNode;
        }
        $("#sqs").hide();
    });
}

function setUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    uiElement.offset({top: X + 40, left: Y - 0});
    var uiWidth = 90;
    var liArray = uiElement.find("li");
    if (liArray.length > 0) {
        var textNumber = 0;
        for (var i = 0; i < liArray.length; i++) {
            var liEl = liArray[i];
            var liText = $(liEl).text();
            if (textNumber < liText.length) {
                textNumber = liText.length;
            }
        }
        var countWidth = (textNumber + 3) * 13;
        if (uiWidth < countWidth) {
            uiWidth = countWidth;
        }
    }
    uiElement.width(uiWidth);
}

//水电气信息
function loadSqdghxx(sdqxxs) {
    var dwpzxx;
    // 加载水气单位信息,读取配置
    $.ajax({
        url: "/realestate-accept-ui/slym/sdq/sdqdwxx/" + processInsId,
        type: "GET",
        async: false,
        success: function (data) {
            dwpzxx = data;
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    var json = {
        bdcQlrDOList: qlrCache
    };
    if(!isNullOrEmpty(dwpzxx.gqdw)) {
        json.gqdw = dwpzxx.gqdw;
    }
    if(!isNullOrEmpty(dwpzxx.gsdw)) {
        json.gsdw = dwpzxx.gsdw;
    }
    if(!isNullOrEmpty(sdqxxs)){
        sdqxxs.forEach(function (data,index){
            // 供水
            if(data.ywlx == 1){
                json.s_sdqxx = data;
                if (isNotBlank(data.rqfwbsm)){
                    loadSdqXhzAndHy(1,data.rqfwbsm);
                }
            }
            // 供电
            if(data.ywlx == 2){
                json.d_sdqxx = data;
            }
            // 供气
            if(data.ywlx == 3){
                json.q_sdqxx = data;
                if (isNotBlank(data.rqfwbsm)){
                    loadSdqXhzAndHy(3,data.rqfwbsm);
                }
            }
        })
    }
    if(isNullOrEmpty(json.d_sdqxx)){
        json.d_sdqxx = {};
    }
    if(isNullOrEmpty(json.q_sdqxx)){
        json.q_sdqxx = {};
    }
    if(isNullOrEmpty(json.s_sdqxx)){
        json.s_sdqxx = {};
    }
    var tpl;
    var view;
    tpl = sdqghxxTpl.innerHTML;
    view = $(".sdqghxx")[0];
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
}

// 选择单位名称，根据配置，水电气过户信息加载新户主名称选择框，核验按钮
function loadSdqXhzAndHy(ywlx,dwdm){
    var url =  getContextPath() + "/slym/sdq/sdqdwxx/" + processInsId + "?ywlx=" + ywlx +"&dwdm=" + dwdm;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            console.info(data);
            // 水
            if (ywlx == 1){
                var gsdw = data.gsdw;
                var pushStyle = gsdw.pushStyle;
                var sfhy = gsdw.sfhy;
                if (pushStyle == 1 || pushStyle==3){
                    $('#gsXhzmc').hide();
                }else{
                    $('#gsXhzmc').show();
                }
                if (!sfhy){
                    $('#gsHy').hide();
                }else{
                    $('#gsHy').show();
                }
            }
            // 气
            if (ywlx == 3){
                var gqdw = data.gqdw;
                var pushStyle = gqdw.pushStyle;
                var sfhy = gqdw.sfhy;
                if (pushStyle == 1 || pushStyle == 3) {
                    $('#gqXhzmc').hide();
                } else {
                    $('#gqXhzmc').show();
                }
                if (!sfhy) {
                    $('#gqHy').hide();
                } else {
                    $('#gqHy').show();
                }
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
        }
    });
}

// 核验用电户号
function checkYdhh(ele){
    var consno = $(ele).parents("#sdqghDiv").find("input[name='consno-d']").val();
    if(!isNotBlank(consno)){
        ityzl_SHOW_WARN_LAYER("请输入用电户号后，在进行核验。");
        return;
    }
    getReturnData("/slym/sdq/hy/ydghxx", {
        gzlslid: processInsId,
        ydhh: consno
    }, "GET", function (data) {
        removeModal();
        if(data.hyjg){
            ityzl_SHOW_SUCCESS_LAYER("核验成功");
        } else {
            ityzl_SHOW_WARN_LAYER("户号不在" + data.ywrmc + "名下，请核实");
        }
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });

}

// 核验用水户号,先保存数据到数据库，再核验
function checkYshh(ele){
    var consno = $(ele).parents("#sdqghDiv").find("input[name='consno-s']").val();
    if(!isNotBlank(consno)){
        ityzl_SHOW_WARN_LAYER("请输入用水户号后，在进行核验。");
        return;
    }
    var rqfwbsm = $('#sdqgh-s-lx').val();
    if(!isNotBlank(rqfwbsm)){
        ityzl_SHOW_WARN_LAYER("请选择供水单位后，在进行核验。");
        return;
    }
    // 保存水过户信息
    saveShuighxx();
    getReturnData("/slym/sdq/check/water/consno", {
        gzlslid: processInsId,
        consno: consno
    }, "GET", function (data) {
        removeModal();
        if(data.data == 0){
            $('#shui-hyjg').text('核验成功');
            $('#shui-hyjg').css('color','green')
            ityzl_SHOW_SUCCESS_LAYER("核验成功");
        } else {
            $('#shui-hyjg').text('核验失败');
            $('#shui-hyjg').css('color','red')
            ityzl_SHOW_WARN_LAYER("核验失败");
        }
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}

// 核验用气户号
function checkYqhh(ele){
    var consno = $(ele).parents("#sdqghDiv").find("input[name='consno-q']").val();
    if(!isNotBlank(consno)){
        ityzl_SHOW_WARN_LAYER("请输入用气户号后，在进行核验。");
        return;
    }
    var rqfwbsm = $('#sdqgh-q-lx').val();
    if(!isNotBlank(rqfwbsm)){
        ityzl_SHOW_WARN_LAYER("请选择供气单位后，在进行核验。");
        return;
    }
    saveQighxx();
    getReturnData("/slym/sdq/check/gas/consno", {
        gzlslid: processInsId,
        consno: consno
    }, "GET", function (data) {
        removeModal();
        if(data.data == 0){
            $('#qi-hyjg').text('核验成功');
            $('#qi-hyjg').css('color','green');
            ityzl_SHOW_SUCCESS_LAYER("核验成功");
        } else {
            $('#qi-hyjg').text('核验失败');
            $('#qi-hyjg').css('color','red');
            ityzl_SHOW_WARN_LAYER("核验失败");
        }
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}

function saveSdqGhxx() {
    if (!document.getElementById("sdqghDiv")) {
        //如果页面不存在该元素，则不保存
        return;
    }
    var sdqghArray = [];
    //电过户
    if(!isNullOrEmpty($('#sdqgh-d').val())){
        var sdqgh = {};
        sdqgh.gzlslid = processInsId;
        sdqgh.slbh = $('#sjbh').val();
        sdqgh.consno = $('#sdqgh-d').val();
        sdqgh.sffgyd = $("input[name='d-sffgyd']:checked").val();
        sdqgh.ywlx = 2;
        sdqgh.sfbl = 0;
        sdqghArray.push(sdqgh)
    }

    //气过户
    if(!isNullOrEmpty($('#sdqgh-q').val())){
        var sdqQigh = {};
        sdqQigh.gzlslid = processInsId;
        sdqQigh.slbh = $('#sjbh').val();
        sdqQigh.consno = $('#sdqgh-q').val();
        sdqQigh.rqfwbsm = $('#sdqgh-q-lx').val();
        sdqQigh.xhzmc = $('#qihz').val();
        sdqQigh.ywlx = 3;
        sdqQigh.sfbl = 0;
        sdqghArray.push(sdqQigh)
    }

    if(!isNullOrEmpty($('#sdqgh-s').val())) {
        //水过户
        var sdqShuigh = {};
        sdqShuigh.gzlslid = processInsId;
        sdqShuigh.slbh = $('#sjbh').val();
        sdqShuigh.consno = $('#sdqgh-s').val();
        sdqShuigh.rqfwbsm = $('#sdqgh-s-lx').val();
        sdqShuigh.xhzmc = $('#shuihz').val();
        sdqShuigh.ywlx = 1;
        sdqShuigh.sfbl = 0;
        sdqghArray.push(sdqShuigh)
    }
    if (sdqghArray.length > 0){
        getReturnData("/slym/sdq/sdqxx", JSON.stringify(sdqghArray), "POST", function (data) {
            console.log('更新水电气信息成功');
        }, function (xhr) {
            delAjaxErrorMsg(xhr)
        })
    }
}

// 新增或更新供水信息
function saveShuighxx() {
    if (!document.getElementById("sdqghDiv")) {
        //如果页面不存在该元素，则不保存水电气过户信息
        return;
    }
    var sdqghArray = [];

    if(!isNullOrEmpty($('#sdqgh-s').val())) {
        //水过户
        var sdqShuigh = {};
        sdqShuigh.gzlslid = processInsId;
        sdqShuigh.slbh = $('#sjbh').val();
        sdqShuigh.consno = $('#sdqgh-s').val();
        sdqShuigh.rqfwbsm = $('#sdqgh-s-lx').val();
        sdqShuigh.xhzmc = $('#shuihz').val();
        sdqShuigh.ywlx = 1;
        sdqghArray.push(sdqShuigh)
    }
    getReturnData("/slym/sdq/sdqxx", JSON.stringify(sdqghArray), "POST", function (data) {
        console.log('更新水过户信息成功');
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    })
}

// 新增或更新供气信息
function saveQighxx() {
    if (!document.getElementById("sdqghDiv")) {
        //如果页面不存在该元素，则不保存水电气过户信息
        return;
    }
    var sdqghArray = [];

    if(!isNullOrEmpty($('#sdqgh-s').val())) {
        //气过户
        if(!isNullOrEmpty($('#sdqgh-q').val())){
            var sdqQigh = {};
            sdqQigh.gzlslid = processInsId;
            sdqQigh.slbh = $('#sjbh').val();
            sdqQigh.consno = $('#sdqgh-q').val();
            sdqQigh.rqfwbsm = $('#sdqgh-q-lx').val();
            sdqQigh.xhzmc = $('#qihz').val();
            sdqQigh.ywlx = 3;
            sdqghArray.push(sdqQigh);
        }
    }
    getReturnData("/slym/sdq/sdqxx", JSON.stringify(sdqghArray), "POST", function (data) {
        console.log('更新气过户信息成功');
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    })
}

function queryDyhtxx() {
    var gzlslid = processInsId;
    var dyhtxx = layer.open({
        type: 1,
        title: '信息',
        skin: 'bdc-ckgdxx-layer',
        area: ['450px','150px'],
        content:
            '<div id="bdc-popup-small ckgdxx-layer">'+
            '<form class="layui-form" action="">'+
            '<div class="layui-form-item pf-form-item">'+
            '<div class="layui-inline" style="width: 100%;">'+
            ' <label class="layui-form-label bdc-label-newline">合同编号</label>'+
            ' <div class="layui-input-inline">'+
            '<input type="text" name="htbh" autocomplete="off" placeholder="请输入" class="layui-input">'+
            '</div>'+
            '</div>'+
            '</div>'+
            '</form>'+
            '</div>',
        btn: ['确定'],
        btnAlign: 'c',
        yes: function(index,layero){
            //确定操作
            var htbh = layero.find("input[name='htbh']").val();
            if(!isNotBlank(htbh)){
                ityzl_SHOW_WARN_LAYER("请输入合同编号。");
                return;
            }
            addModel();
            getReturnData("/slym/sjcl/queryDyhtxx", {htbh:htbh,processInsId:gzlslid},"GET",function (data) {
                removeModal();
                if(data &&data.length >0){
                    ityzl_SHOW_SUCCESS_LAYER("获取抵押合同号信息成功");
                }else{
                    ityzl_SHOW_WARN_LAYER("获取抵押合同号信息失败！");
                }
            }, function (xhr) {
                removeModal();
                delAjaxErrorMsg(xhr);
            })
            layer.close(index);
        }
    });

}

//审核不通过按钮触发事件
function shbtg() {
    // 审核不通过
    layer.open({
        title: '审核',
        type: 1,
        area: ['430px'],
        btn: ['确定', '取消'],
        content: $('#check-back-reason')
        , yes: function (index, layero) {
            var reason = $("#reason").val();
            if (isNullOrEmpty(reason)) {
                layer.msg('请输入审核不通过原因!');
                return false;
            }
            //提交 的回调
            addModel();
            layer.close(index);

            $.ajax({
                type: "get",
                url: getContextPath() + "/ycsl/shbtg",
                data: {gzlslid: processInsId, reason: reason},
                success: function () {
                    removeModal();
                    layer.msg('操作成功，即将关闭页面');
                    setTimeout(function () {
                        window.close();
                    }, 1000);
                }, error: function (e) {
                    removeModal();
                    delAjaxErrorMsg(e, "");
                }, complete: function () {
                    removeModal();
                }
            });
        }
        , btn2: function (index, layero) {
            //取消 的回调
            layer.close(index);
        }
    });


}


//权利人义务人获取证照数据
function queryZzxx(zjh) {
    //1.打开弹框选择证照类型
    var index = layer.open({
        title: '选择证照类型',
        type: 1,
        skin: 'bdc-spf-layer',
        area: ['450px', '500px'],
        content: $("#chooseZzlx"),
        btn: ['查询', '取消'],
        yes: function (index, layero) {
            //提交 的回调
            addModel();
            var csjzzxx = {};
            csjzzxx.zjh = zjh;
            csjzzxx.zzlx = formSelects.value('zzlx', 'val').join(",");
            csjzzxx.gzlslid = processInsId;
            csjzzxx.gzldyid = gzldyid;
            getReturnData("/slym/qlr/zzxx", JSON.stringify(csjzzxx), "POST", function (data) {
                removeModal();
                if (data == "200") {
                    ityzl_SHOW_SUCCESS_LAYER("获取成功");
                } else {
                    ityzl_SHOW_WARN_LAYER("未获取到信息");
                }
                layer.close(index);
                loadSjcl();
            }, function (xhr) {
                delAjaxErrorMsg(xhr);
            })
        }, btn2: function (index, layero) {
            //取消 的回调
            layer.close(index);
        }, cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
            layer.close(index);
        }, success: function (layero, index) {
            var zzlx = zdList.csjzzlx;
            formSelects.config('zzlx', {
                keyName: 'MC',            //自定义返回数据中name的key, 默认 name
                keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
            }, true);
            formSelects.data('zzlx', 'local', {arr: zzlx});
            getReturnData("/slym/qlr/zzlx", {gzldyid: gzldyid}, "GET", function (data) {
                if (data && data.length > 0) {
                    formSelects.value('zzlx', data[0].zzlx.split(","));
                }
            }, function (xhr) {
                delAjaxErrorMsg(xhr);
            })
            $("#sfmdiv").hide();
        }
    })
}

//扫描苏服码查询
function scanSfm(lclx, xmid, djxl) {
    //输入框并获取焦点信息.打开弹框，并设置获取隐藏输入框的焦点
    //1.打开弹框选择证照类型
    var index = layer.open({
        title: '选择证照类型',
        type: 1,
        skin: 'bdc-spf-layer',
        area: ['450px', '500px'],
        content: $("#chooseZzlx"),
        btn: ['查询', '取消'],
        yes: function (index, layero) {
            //提交 的回调
            //先判断是否获取到苏服码内容
            var sfmxx = $("#sfmxx").val();
            if (isNotBlank(sfmxx)) {
                addModel();
                var csjzzxx = {};
                csjzzxx.zzlx = formSelects.value('zzlx', 'val').join(",");
                csjzzxx.gzlslid = processInsId;
                csjzzxx.gzldyid = gzldyid;
                csjzzxx.sfmnr = sfmxx;
                csjzzxx.lclx = lclx;
                csjzzxx.xmid = xmid;
                //组合流程新增权利人需要
                csjzzxx.djxl = djxl;
                getReturnData("/slym/qlr/sfm", JSON.stringify(csjzzxx), "POST", function (data) {
                    removeModal();
                    if (data == "200") {
                        ityzl_SHOW_SUCCESS_LAYER("获取成功");
                    } else {
                        ityzl_SHOW_WARN_LAYER("未获取到信息");
                    }
                    layer.close(index);
                    loadQlr();
                    loadSjcl();
                }, function (xhr) {
                    delAjaxErrorMsg(xhr);
                })
            } else {
                ityzl_SHOW_WARN_LAYER("请先扫描苏服码再查询");
                $("#sfmxx").focus();
            }

        }, btn2: function (index, layero) {
            //取消 的回调
            layer.close(index);
        }, cancel: function () {
            //右上角关闭回调
            layer.close(index);
        }, success: function (layero, index) {
            var zzlx = zdList.csjzzlx;
            formSelects.config('zzlx', {
                keyName: 'MC',            //自定义返回数据中name的key, 默认 name
                keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
            }, true);
            formSelects.data('zzlx', 'local', {arr: zzlx});
            getReturnData("/slym/qlr/zzlx", {gzldyid: gzldyid}, "GET", function (data) {
                if (data && data.length > 0) {
                    formSelects.value('zzlx', data[0].zzlx.split(","));
                }
            }, function (xhr) {
                delAjaxErrorMsg(xhr);
            })
            //设置焦点，选中输入框，这样扫码枪扫描信息会直接赋值
            $("#sfmdiv").show();
            $("#sfmxx").val('');
            $("#sfmxx").focus();
        }
    })
}