/**
 * @author "<a href="mailto:yaoyi@gtmap.cn>yaoyi</a>"
 * @version 1.0, 2019/11/5
 * @description 受理表单操作公共JS
 */

//获取存量房网签协议信息的相关方法
var Fcjyxx = {
    beanName : "nt_clfhtxx",
    openPage : function(beanName){
        //1、获取页面中的权利人的证件号信息 2、多个权利人时，默认获取第一个。3、未输入证件号时，给与提示
        var qlrxx= new Object();
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
        layer.open({
            title: '获取房产交易合同信息',
            type: 1,
            skin: 'bdc-spf-layer',
            area: ['960px','500px'],
            content: $('#bdc-popup-large'),
            success: function (layero,index) {
                //清空已有值
                $('#contractNo').val('');
                $('#mfzjh').val('');
                //自动获取焦点
                $('#contractNo').focus();
                if(isNotBlank(qlrxx)){
                    $('#mfzjh').val(qlrxx.zjh);
                    Fcjyxx.queryFcjyxx(Fcjyxx.beanName).done(function(data){
                        Fcjyxx.tableLoad(data);
                    });
                }
            }
        });
    },
    /**
     * 获取房产交易合同信息页面查询按钮，单击触发表格重新加载方法。
     */
    query:function(){
        Fcjyxx.queryFcjyxx(Fcjyxx.beanName).done(function(data){
            Fcjyxx.tableLoad(data);
        });
    },
    /**
     * 加载房产交易合同信息表格数据
     * @param data 表格数据
     */
    tableLoad : function(data){
        table.render({
            elem: '#pageTable',
            title: '房产交易合同信息',
            even: true,
            limit: 3000,
            cols: [[
                {field:'htbh', title:'合同编号', width:180, templet: '#htbhTpl', event: 'openpage'},
                {field:'msrmc', title:'买方', width:150},
                {field:'cmrmc', title:'卖方', width:150},
                {field:'zl', title:'坐落'}
            ]],
            data: data,
            page: false,
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                if($('.layui-table-body>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
                }
            }
        });
    },
    /**
     * 查询时校验获取房产交易信息页面查询参数是否为空
     * @returns {boolean} true 为空  false 不为空
     */
    checkQueryParamNull : function(){ //校验查询参数是否为空
        var mfzjh = $('#mfzjh').val();
        var contractNo = $('#contractNo').val();
        if(!isNotBlank(mfzjh) && !isNotBlank(contractNo)){
            showAlertDialog("请先输入权利人信息或合同编号信息。");
            return true;
        }
        return false;
    },
    /**
     * 调用存量房网签协议信息查询接口，获取交易信息
     * @param beanName 接口标识
     * @returns deferred jQueryDeferred对象
     */
    queryFcjyxx: function(beanName){
        var deferred = $.Deferred();
        if(Fcjyxx.checkQueryParamNull()){
            return deferred.reject();
        }
        addModel();
        $.ajax({
            url: getContextPath() + "/ycsl/jyxx/queryFcjyxx",
            type: 'GET',
            data: {
                htbh: $('#contractNo').val(),
                msrzjh: $('#mfzjh').val(),
                beanName: beanName
            },
            dataType: 'json',
            success: function (data) {
                removeModal();
                if (isNotBlank(data)) {
                    console.log(data.wqxx);
                    deferred.resolve(Fcjyxx.formatJyxx(data.wqxx));
                } else {
                    deferred.resolve(new Array());
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr);
                deferred.reject();
            }
        });
        return deferred;
    },
    /**
     * 格式化存量房交易信息接口返回值
     * @param data
     * @returns data 格式化后的数据
     */
    formatJyxx : function(data){ //格式化存量房交易信息接口返回值
        var fcjyxx =new Array();
        $.each(data,function(index,value){
            var msrmcArray = new Array();
            $.each(value.msr,function(i,msrxx){
                msrmcArray.push(msrxx.msrmc);
            });
            var cmrmcArray = new Array();
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
    /**
     * 对用户选择交易信息关联校验，当接口反馈的买房信息与权利人信息是否一致
     * @param data 选中行的数据
     */
    linkCheck : function(data){
        console.info(data);
        if(qlrCache == null){
            showAlertDialog("未获取到输入权利人信息。");
            return false;
        }
        var errorMsgArray = new Array();
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
            Fcjyxx.proxyDealDjxx(data);
        }else{
            var errorMsg =errorMsgArray.join("。")+"与填写信息不一致，是否导入？";
            layer.confirm(errorMsg,function(index){
                addModel();
                Fcjyxx.proxyDealDjxx(data);
            });
        }
    },
    proxyDealDjxx : function (data) {
        if(data.fw || data.fw.length > 0){
            dealDjxx(data);
        }else{
            showAlertDialog("未获取到房产合同交易信息中的房屋交易价格信息。");
        }

    }
}

layui.use(['jquery'],function () {
    var $ = layui.jquery;
    $(function () {
        //滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-form-div');
        function defaultStyle() {
            if($cnotentTitle.length == 1){
                $('.bdc-form-div').css('padding-top','68px');
            }
            if($(window).scrollTop() > 68){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','63px');
            }else if($(window).scrollTop() <= 68){
                $cnotentTitle.css('top','10px');
                $navContainer.css('top','68px');
            }
        }

        $(window).resize(function(){
            defaultStyle();
        });
        $(window).on('scroll',function () {
            if($(this).scrollTop() > 68){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','63px');
            }else if($(this).scrollTop() <= 68){
                $cnotentTitle.css('top','10px');
                $navContainer.css('top','68px');
            }
        });
        $('.layui-input').on('focus',function () {
            if($(this).hasClass('layui-form-danger')){
                $(this).removeClass('layui-form-danger');
            }
            if($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')){
                $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
            }
        });
        $('.xm-input').on('click',function () {
            if($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')){
                $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
            }
        });
    });
});