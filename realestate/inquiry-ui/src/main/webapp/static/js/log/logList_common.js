/**
 * author: <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
 * version 1.0.0  2022/09/08
 * describe: 日志查询-公用js
 */
var tToD = {};
var LogListCommon = {
    /**
     *  根据 eventName 读取当前日志类型需要展示的colsConfig配置，将cols中hide:true更改为false进行展示
     */
    showColsByConfig: function(cols, colsConfig){
        // 此处拷贝入参的cols , 防止修改影响原有的cols
        var tableCols = JSON.parse(JSON.stringify(cols));
        if(isNotBlank(colsConfig)){
            if(colsConfig.length > 0){
                $.each(tableCols, function (index, value){
                    if(isNotBlank(value.field)){
                        $.each(colsConfig, function(i, item){
                            if(value.field == item){
                                value.hide = false;
                            }
                        });
                    }
                });
            }
        }
        return tableCols;
    },

    /**
     * 处理查询参数，封装成Es查询参数
     */
    handlerQueryParam: function(param){
        if(param){
            var conditionQOList = [];
            // 组织es内容搜索参数
            if(isNotBlank(param["conditionKey"])&& isNotBlank(param["conditionValue"]) ){
                conditionQOList.push({
                    key : param["conditionKey"],
                    value: param["conditionValue"],
                    type: "like",
                });

            }
            $.each(param, function(key, value){
                if(["conditionKey", "conditionValue"].indexOf(key) > -1){
                    return true;
                }
                if(isNullOrEmpty(value)){
                    return true;
                }
                conditionQOList.push({
                   key : key,
                   value: value,
                   type: "like",
                });
            });
            if(conditionQOList.length > 0){
                param["condition"] =  Base64.encode(JSON.stringify(conditionQOList));
            }
        }
        return param;
    },

    initEventTypeToDesc: function(){
        var selectOps = $('#eventName').find('option');
        if(selectOps.length > 0 ){
            for(var i = 0; i<selectOps.length; i++){
                if($(selectOps[i]).val() != "" && $(selectOps[i]).text()!=""){
                    tToD[$(selectOps[i]).val()] = $(selectOps[i]).text();
                }
            }
        }
    },

    // 初始化页面元素：时间
    initSearchElement: function(){
        layui.use(['form', 'jquery','layer','laydate', 'laytpl'], function () {
            var layer = layui.layer;
            var laydate = layui.laydate;
            var form = layui.form;

            // 设置默认查询一周内的日志
            var nowDate = new Date();
            var preDate = new Date(nowDate.getTime() - 7*24*60*60*1000);
            lay('.test-item').each(function(){
                //初始化日期控件
                var startDate = laydate.render({
                    elem: "#begin",
                    trigger: 'click',
                    value: preDate,
                    type: 'datetime',
                    done: function(value,date){
                        //选择的结束时间大
                        if($("#end").val() != '' && !completeDate($("#end").val() ,value)){
                            $("#end").val('');
                            $('.laydate-disabled.layui-this').removeClass('layui-this');
                            layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                        }
                        endDate.config.min ={
                            year:date.year,
                            month:date.month-1,
                            date: date.date
                        }
                    }
                });
                var endDate = laydate.render({
                    elem: "#end",
                    trigger: 'click',
                    type: 'datetime',
                    value: nowDate,
                });

            });

            laydate.render({
                elem: '#yzrq',
                trigger: 'click',
                format: 'yyyy-MM-dd',
                type: 'date'
            });
        });
    },
    // 搜索框中 x的事件、重置事件
    initElement:function(){
        //x 的事件
        layui.form.on('select', function (data) {
            if($(this).text() == "请选择"){
                $(data.elem).parent().find('.reseticon').hide();
            }else{
                $(data.elem).parent().find('.reseticon').show()
            }
        });

        $('.reseticon').on('click',function(item){
            $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
            $(this).hide();
            layui.form.render("select");
        });

        $('#reset').on('click',function(item){
            $('#eventName').find("option").removeAttr("selected");
            $('.bdc-percentage-container').find('.layui-form')
                .find('select').find("option:eq(0)")
                .attr("selected","selected");
            $('.reseticon').hide();
        });
    }

}

// 格式化时间，入参为
function formatTime(time){
    if(typeof time == "string"){
        if(time.indexOf("/") > -1|| time.indexOf("-") > -1){
            return d.time;
        }else{
            return toLocalStringDate(new Date(parseInt(time)));
        }
    }else{
        return toLocalStringDate(new Date(parseInt(time)));
    }
}

// 重写方法，自定义格式化日期
function toLocalStringDate (date) {
    // 按自定义拼接格式返回
    return date.getFullYear() + "-" + addZero(date.getMonth() + 1) + "-" + addZero(date.getDate()) + " " +
        addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
}

// 补0   例如 2018/7/10 14:7:2  补完后为 2018/07/10 14:07:02
function addZero(num) {
    if(num<10)
        return "0" + num;
    return num;
}
// 原因可能存在url编码的情况，此处判断原因内容，并对原因信息进行解码处理
function fmtReson(reason){
    reason  = fmtData(reason);
    if(isNotBlank(reason)){
        return decodeURIComponent(reason);
    }
    return reason;
}

// 格式化数据，将unknown数据展示为空
function fmtData(data){
    if(isNullOrEmpty(data)){
        return "";
    }
    if(data == "unknown"){
        return "";
    }
    return data;
}
function completeDate(date1, date2) {
    var oDate1 = new Date(date1);
    var oDate2 = new Date(date2);
    if (oDate1.getTime() > oDate2.getTime()) {
        return true;
    } else {
        return false;
    }
}