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
function getJyxxBtn(){
    SpfJyxx.openPage();
}

//交易信息合同编号字段绑定事件
layui.use(['jquery', 'table'], function() {
    table = layui.table;
    table.on('tool(bdcHtbhTable)', function (obj) {
        if (obj.event === 'openpage') {
            console.info(obj.data);
           SpfJyxx.linkCheck(obj.data.fcjyBaxxDTO);
        }
    });
});

// 一窗房产交易信息
var SpfJyxx = function(){
    var beanName = "nt_spfjyxx";

    function openPage(){
        var qlrxx= new Object();
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
            title: '获取商品房信息',
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
                var param ={
                    beanName: beanName,
                    fwbm : getFwbmParam()
                }
                if(isNotBlank(param.fwbm)){
                    getJyxxData(param);
                }else{
                    tableLoad(new Array());
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
    function queryJyxx(){
        var deferred = $.Deferred();
        if(checkQueryParam()){
            return;
        }
        var param = {
            htbh: $('#contractNo').val(),
            zjh: $('#mfzjh').val(),
            // xm : $('#mfxm').val(),
            beanName: beanName
        }
        getJyxxData(param);
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
    function getJyxxData(param){
        addModel();
        $.ajax({
            url: getContextPath() + "/ycsl/jyxx/querySpfJyxx",
            type: 'GET',
            data: param,
            dataType: 'json',
            success: function (data) {
                removeModal();
                if (!isNotBlank(data)) {
                    data = new Array();
                }
                // 调用数据过滤回调方法
                tableLoad(formatJyxx(data)); //重新加载table
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr);
            }
        });
    };

    // 将接口返回数据封装为layuiTable所需格式数据格式
    function formatJyxx(data){
        var fcjyxx =new Array();
        $.each(data,function(index,value){
            var zrfArray = new Array();
            var zcfArray = new Array();
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
    }

    /**
     * 加载房产交易合同信息表格数据
     * @param data 表格数据
     */
    function tableLoad(data){
        table.render({
            elem: '#pageTable',
            title: '商品房备案信息',
            even: true,
            limit: 3000,
            cols : [[
                {field:'htbh', title:'合同编号', width:180, templet: '#htbhTpl', event: 'openpage'},
                {field:'zrf', title:'买方', width:150},
                {field:'zcf', title:'开发商', width:150},
                {field:'htdjsj', title:'合同签订时间', width:150,templet: function (d) {
                        return Format(formatChinaTime(new Date(d.htdjsj)), "yyyy-MM-dd");
                    }},
                {field:'zl', title:'坐落'}
            ]],
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
    function linkCheck(data){
        if(null == bdcSlSqrListCache){
            showAlertDialog("未获取到权利人信息。");
            return false;
        }
        // 处理接口返回数据，多个权利人数据值返回一条，权利人名称按“ ”空格分隔，证件号按“;”分号分隔。
        var msrArray = new Array();
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
        var errorMsgArray = new Array();
        $.each(msrArray,function (index,msrxx) {
            var checkFlag = false;
            $.each(bdcSlSqrListCache, function(i,value){
                var sqr = value.bdcSlSqrDO
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
            checkHtbhLink(data);
        }else{
            var errorMsg =errorMsgArray.join("。")+"与填写信息不一致，是否导入？";
            layer.confirm(errorMsg,function(index){
                addModel();
                checkHtbhLink(data);
            });
        }
    }

    // 验证当前合同备案号是否已经被关联
    function checkHtbhLink(fcjyBaxx){
        getReturnData("/ycsl/jyxx/checkHtbhLinked",{htbh : fcjyBaxx.bdcSlJyxx.htbh}, "GET", function(data){
            removeModal();
            if(data){
                layer.confirm('当前备案号已经被关联，是否重新关联', {
                    btn: ['是', '否']
                }, function(index, layero){
                    proxyDealDjxx(fcjyBaxx);
                }, function(index){
                    layer.close(index);
                });
            }else{
                proxyDealDjxx(fcjyBaxx);
            }
        });
    }
    function proxyDealDjxx(data){
        dealSpfJyxx(data);
    }

    return {
        openPage : openPage,
        queryJyxx : queryJyxx,
        linkCheck : linkCheck,
    };
}();

 // 标红触发后移除红框
layui.use(['jquery'],function () {
    var $ = layui.jquery;
    $(function () {
        $('.bdc-form-div').on('focus','.layui-input',function () {
            if($(this).hasClass('layui-form-danger')){
                $(this).removeClass('layui-form-danger');
            }
            if($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')){
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
        setUiWidth($(this), $("#print"));
    });

    $(".print-btn li").click(function () {
        $("#print").hide();
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