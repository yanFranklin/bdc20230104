var chooseBdcXm = false;
// 获取字典
function loadZdData(){
    var zdList = [];
    $.ajax({
        url: getContextPath()+"/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        },error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return zdList;
}

// 获取交易信息按钮事件
function getJyxxBtn(fwfclx) {
    SpfClfJyxx.openPage(fwfclx);
}

//获取扣款信息
function getKkxx() {
    getReturnData("/slym/sw/wsxx", {gzlslid: processInsId, htbh: getHtbhParam()}, "GET", function (data) {
        ityzl_SHOW_SUCCESS_LAYER("获取成功");
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

//获取一窗受理税票
function getYcslSp() {
    getReturnData("/slym/sw/ycslsp", {gzlslid: processInsId}, "GET", function (data) {
        ityzl_SHOW_SUCCESS_LAYER("获取成功");
        //重新加载附件材料列表
        loadSjcl();
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

function getJyxxBtnPl(fwfclx) {
    chooseBdcXm = true;
    SpfClfJyxx.openPage(fwfclx);
}

//交易信息合同编号字段绑定事件
layui.use(['jquery', 'table'], function () {
    table = layui.table;
    table.on('tool(bdcHtbhTable)', function (obj) {
        console.info(obj.data);
        if (obj.event === 'glClfJyxx') {
           SpfClfJyxx.linkCheck(obj.data.fcjyBaxxDTO,"clf");
        }else if(obj.event ==="glSpfJyxx"){
            SpfClfJyxx.linkCheck(obj.data.fcjyBaxxDTO,"spf");

        }
    });

    table.on('tool(chooseXmTable)', function (obj) {
        console.info(obj.data);
        if (obj.event === 'chooseXm') {
            importXmHtxx(obj.data);
        }
    });
});

// 一窗房产交易信息(商品房,存量房)
var SpfClfJyxx = function(){

    function openPage(fwfclx){
        var beanName = fwfclx ==="clf"?"ntyth_clfwqxx_zh":"nt_spfjyxx";

        var qlrxx= {};
        if(bdcSlSqrListCache != null){
            $.each(bdcSlSqrListCache,function(index,value){
                var sqr = value.bdcSlSqrDO;
                if(sqr.sqrlb == 1){
                    qlrxx = sqr;
                    return false;
                }
            });
        }
        layer.open({
            title: '获取交易信息',
            type: 1,
            skin: 'bdc-spf-layer',
            area: ['960px','500px'],
            content: $('#bdc-popup-large'),
            success: function (layero,index) {
                //清空已有值
                $('#contractNo').val('');
                $('#mfzjh').val('');
                $('#contractNo').focus(); //自动获取焦点
                if(isNotBlank(qlrxx)){
                    $('#mfzjh').val(qlrxx.zjh);
                }
                //增加onclick事件
                $("#querySpfClfJyxx").attr("onclick","SpfClfJyxx.queryJyxx('"+fwfclx+"')");
                var param ={};
                if(fwfclx ==="clf"){
                    var htbh =getHtbhParam();
                    if(isNotBlank(htbh)) {
                        $('#contractNo').val(htbh);
                    }
                    param = {
                        beanName: beanName,
                        zjh: $('#mfzjh').val(),
                        htbh: $('#contractNo').val()
                    };
                    if(isNotBlank(param.zjh) ||isNotBlank(param.htbh)){
                        getJyxxData(param,fwfclx);
                    }else{
                        tableLoad([],fwfclx);
                    }
                }else {
                    param ={
                        beanName: beanName,
                        fwbm : getFwbmParam()
                    };
                    if(isNotBlank(param.fwbm)){
                        getJyxxData(param,fwfclx);
                    }else{
                        tableLoad([],fwfclx);
                    }
                }

            }
        });
    }

    function getFwbmParam(){
        var fwbm = "";
        getReturnData("/ycsl/fwxx", {xmid: xmid}, "GET", function (data) {
            if(isNotBlank(data)){
                fwbm = data.fwbm;
            }
        }, function (error) {
            console.info(error);
        }, false);
        return fwbm;
    }



    /**
     * 调用接口，获取交易信息
     * @param beanName 接口标识
     * @returns void
     */
    function queryJyxx(fwfclx){
        if(checkQueryParam()){
            return;
        }
        var beanName = fwfclx ==="clf"?"ntyth_clfwqxx_zh":"nt_spfjyxx";
        var param = {
            htbh: $('#contractNo').val(),
            zjh: $('#mfzjh').val(),
            beanName: beanName
        };
        getJyxxData(param,fwfclx);
    }
    /**
     * 查询时校验获取房产交易信息页面查询参数是否为空
     * @returns {boolean} true 为空  false 不为空
     */
    function checkQueryParam(){
        var mfzjh = $('#mfzjh').val();
        var contractNo = $('#contractNo').val();
        var mfxm = $('#mfxm').val();
        if(!isNotBlank(mfzjh) && !isNotBlank(contractNo) && !isNotBlank(mfxm)){
            showAlertDialog("请先输入姓名、证件号、合同编号信息。");
            return true;
        }
        return false;
    }
    // 调用后台请求获取交易信息数据
    function getJyxxData(param,fwfclx){
        var url =fwfclx ==="clf"?getContextPath() + "/ycsl/jyxx/queryFcjyxx":getContextPath() + "/ycsl/jyxx/querySpfJyxx";
        addModel();
        $.ajax({
            url: url,
            type: 'GET',
            data: param,
            dataType: 'json',
            success: function (data) {
                removeModal();
                if (!isNotBlank(data)) {
                    data = [];
                }
                // 调用数据过滤回调方法
                tableLoad(formatJyxx(data),fwfclx); //重新加载table
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr);
            }
        });
    }

    // 将接口返回数据封装为layuiTable所需格式数据格式
    function formatJyxx(data){
        var fcjyxx =[];
        $.each(data,function(index,value){
            var zrfArray = [];
            var zcfArray = [];
            $.each(value.bdcSlSqr, function(index,sqr){
                if(sqr.sqrlb == "1"){
                    zrfArray.push(sqr.sqrmc)
                }else if(sqr.sqrlb =="2"){
                    zcfArray.push(sqr.sqrmc);
                }
            });
            fcjyxx.push({
                zrf : zrfArray.join(","),
                zcf : zcfArray.join(","),
                zl : value.bdcSlXmDO != null ?value.bdcSlXmDO.zl:"",
                htbh : value.bdcSlJyxx.htbh,
                htdjsj : value.bdcSlJyxx.htdjsj,
                fcjyBaxxDTO : value
            });
        });
        return fcjyxx;
    }

    /**
     * 加载房产交易合同信息表格数据
     * @param data 表格数据
     */
    function tableLoad(data,fwfclx){
        var cols = [];
        //存量房数据列
        if(fwfclx ==="clf"){
            cols =[[
                {field:'htbh', title:'合同编号', width:180, templet: '#htbhTpl', event: 'glClfJyxx'},
                {field:'zrf', title:'买方', width:150},
                {field:'zcf', title:'卖方', width:150},
                {field:'zl', title:'坐落'}
            ]];

        }else{
            //商品房数据列
            cols =[[
                {field:'htbh', title:'合同编号', width:180, templet: '#htbhTpl', event: 'glSpfJyxx'},
                {field:'zrf', title:'买方', width:150},
                {field:'zcf', title:'开发商', width:150},
                {field:'htdjsj', title:'合同签订时间', width:150,templet: function (d) {
                        return Format(formatChinaTime(new Date(d.htdjsj)), "yyyy-MM-dd");
                    }},
                {field:'zl', title:'坐落'}
            ]];

        }
        table.render({
            elem: '#pageTable',
            title: '交易信息',
            even: true,
            limit: 3000,
            cols : cols,
            data: data,
            page: false,
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                if($('.layui-table-body>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        });
    }

    /**
     * 对用户选择交易信息关联校验，当接口反馈的买房信息与权利人信息是否一致
     * @param data 选中行的数据
     */
    function linkCheck(data,fwfclx){
        if(null == bdcSlSqrListCache){
            showAlertDialog("未获取到权利人信息。");
            return false;
        }
        // 处理接口返回数据，多个权利人数据值返回一条，权利人名称按“ ”空格分隔，证件号按“;”分号分隔。
        var msrArray = [];
        var fcjyxxQO={
            version:"nantong"
        };
        data.fcjyxxQO=fcjyxxQO;
        data.gzlslid =processInsId;
        $.each(data.bdcSlSqr,function (index,msrxx) {
            if("1" == msrxx.sqrlb){
                var msrmcArray = msrxx.sqrmc.split(" ");
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
            $.each(bdcSlSqrListCache, function(i,value){
                var sqr = value.bdcSlSqrDO;
                if(sqr.sqrlb == 1 && sqr.sqrmc == msrxx.sqrmc && sqr.zjh == msrxx.zjh){
                    checkFlag = true;
                }
            });
            if(!checkFlag){
                errorMsgArray.push("交易买方为"+msrxx.qlrmc+"，证件号为"+msrxx.zjh);
            }
        });

        // 选择的交易信息的权利人与表单权利人信息不一致时，给予提示。
        if(errorMsgArray.length == 0){
            checkHtbhLink(data,fwfclx);
        }else{
            var errorMsg =errorMsgArray.join("。")+"与填写信息不一致，是否导入？";
            layer.confirm(errorMsg,function(index){
                layer.close(index);
                checkHtbhLink(data,fwfclx);
            });
        }
    }

    // 验证当前合同备案号是否已经被关联
    function checkHtbhLink(fcjyBaxx,fwfclx){
        if(chooseBdcXm){
            // 导入多个合同信息时，触发选择项目信息
            chooseBdcSlXm(fcjyBaxx, fwfclx);
        }else{
            var fwyt =fcjyBaxx.bdcSlFwxx != null ?fcjyBaxx.bdcSlFwxx.fwyt:"";
            getReturnData("/ycsl/jyxx/checkHtbhLinked",{htbh : fcjyBaxx.bdcSlJyxx.htbh,fwyt:fwyt}, "GET", function(data){
                removeModal();
                if(data){
                    layer.confirm('当前备案号已经被关联，是否重新关联', {
                        btn: ['是', '否']
                    }, function(index, layero){
                        proxyDealDjxx(fcjyBaxx,fwfclx);
                    }, function(index){
                        layer.close(index);
                    });
                }else{
                    proxyDealDjxx(fcjyBaxx,fwfclx);
                }
            });
        }
    }
    function proxyDealDjxx(data,fwfclx,xmid){
        if(fwfclx ==="clf"){
            dealSlJyxx(data, xmid);
            dealSqrxx(data, xmid);
            //统一处理附件信息
            dealFjxx(data);
        }else {
            dealSpfJyxx(data, xmid);
            dealSqrxx(data, xmid);
        }
    }

    function chooseBdcSlXm(fcjyBaxx, fwlclx){
        getReturnData("/ycsl/list/bdcslxm", {gzlslid: processInsId}, 'GET', function (data) {
            removeModal();
            if(data){
                $.each(data, function (idx, item){
                    item.fcjyBaxx = fcjyBaxx;
                    item.fwlclx = fwlclx;
                });
                var reverseList = ['zl'];
                layer.open({
                    title: '选择关联的项目信息',
                    type: 1,
                    skin: 'bdc-spf-layer',
                    area: ['960px','500px'],
                    content: $('#bdc-choose-bdcxm'),
                    success: function (layero, index) {
                        table.render({
                            elem: '#chooseXmTable',
                            title: '选择关联的项目',
                            defaultToolbar: [],
                            even: true,
                            cols: [[
                                {field:'ybdcqz', templet: '#ybdcqzTpl', title:'原不动产权证号', event: 'chooseXm'},
                                {field:'zl', title:'坐落'},
                            ]],
                            data: data,
                            page: false,
                            done: function () {
                                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                                reverseTableCell(reverseList);
                                if($('.layui-table-body>.layui-table').height() == 0){
                                    $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
                                }
                            }
                        });
                    }
                });
            }else{
                ityzl_SHOW_WARN_LAYER("未获取到项目信息。");
            }
        }, function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }, false);
    }

    return {
        openPage : openPage,
        queryJyxx : queryJyxx,
        linkCheck : linkCheck,
        chooseBdcSlXm: chooseBdcSlXm,
    };
}();

function importXmHtxx(htxx){
    addModel();
    getReturnData("/ycsl/jyxx/checkHtbhImport",{htbh : htxx.fcjyBaxx.bdcSlJyxx.htbh, gzlslid: processInsId}, "GET", function(data){
        removeModal();
        if (data) {
            layer.close(layer.index);
            ityzl_SHOW_WARN_LAYER("同一个合同编号在同一笔业务中只导入一次。");
        } else {
            dealSlJyxx(htxx.fcjyBaxx, htxx.xmid);
            dealSqrxx(htxx.fcjyBaxx, htxx.xmid);
            layer.close(layer.index);
        }
    });
}

//获取合同编号
function getHtbhParam() {
    var htbh = "";
    getReturnData("/ycsl/jyxx", {xmid: xmid}, "GET", function (data) {
        if (isNotBlank(data)) {
            htbh = data.htbh;
        }
    }, function (error) {
        console.info(error);
    }, false);
    return htbh;
}

// 标红触发后移除红框
layui.use(['jquery'], function () {
    var $ = layui.jquery;
    $(function () {
        $('.bdc-form-div').on('focus', '.layui-input', function () {
            if ($(this).hasClass('layui-form-danger')) {
                $(this).removeClass('layui-form-danger');
            }
            if ($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')) {
                $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
            }
        });
        $('.bdc-form-div').on('click','.xm-input',function () {
            if($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')){
                $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
            }
        });
    });
});

// 设置按钮下拉选项事件
function titleShowUi(){
    $(".print-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }

        $("#print").show();
        $("#qzxx").hide();
        setUiWidth($(this), $("#print"));
    });

    $(".print-btn li").click(function () {
        $("#print").hide();
    });

    $(".qzxx-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }

        $("#qzxx").show();
        $("#print").hide();
        setUiWidth($(this), $("#qzxx"));
        // 调整下拉框位置
        var Y = $(this).offset().left;
        $("#qzxx").offset({left: Y - 100});
    });

    $(".qzxx-btn li").click(function () {
        $("#qzxx").hide();
    });

    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && (elem.id == 'print' ||elem.id =='qzxx')) {
                return;
            }
            elem = elem.parentNode;
        }
        $("#print").hide();
        $("#qzxx").hide();
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