/**
 * author: <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
 * version 1.0.0  2022/06/29
 * describe: 周期性工作量统计
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var reverseList = ['bmmc', 'rymc'];
var tableData = [];
var listAllLcData = [];
var listLcData = [];
layui.use(['form', 'jquery', 'laydate', 'element', 'table', 'laytpl', 'formSelects'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        laydate = layui.laydate,
        form = layui.form;
    var formSelects = layui.formSelects;

    lay('.test-item').each(function(){
        //初始化日期控件
        var startDate = laydate.render({
            elem: "#kssj",
            trigger: 'click',
            type: 'datetime',
            done: function(value,date){
                //选择的结束时间大
                if($("#jzsj").val() != '' && !completeDate($("#jzsj").val() ,value)){
                    $("#jzsj").val('');
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
            elem: "#jzsj",
            trigger: 'click',
            type: 'datetime',
        });

    });

    laydate.render({
        elem: '#yzrq',
        trigger: 'click',
        format: 'yyyy-MM-dd',
        type: 'date'
    });
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });


    var bmcols = [
        {field: 'bmmc', title: '部门名称', minWidth:'200',},

        {field: 'sjl', title: '收件量',minWidth:'120', templet: function (d) {
                return formatSl(d.sjl);
            }
        },
        {field: 'zdlcgzl', title: '重点流程工作量',minWidth:'130', templet: function (d) {
                return formatSl(d.zdlcgzl);
            }
        },
        {field: 'yfwfzmsl', title: '打印有房无房证明量',minWidth:'120', templet: function (d) {
                return formatSl(d.yfwfzmsl);
            }
        },
        {field: 'qszmsl', title: '打印权属证明量', minWidth:'120', templet: function (d) {
                return formatSl(d.qszmsl);
            }
        },
        {field: 'zhcxsl', title: '综合查询量', minWidth:'120', templet: function (d) {
                return formatSl(d.zhcxsl);
            }
        },
        {field: 'szzsl', title: '缮证证书量', minWidth:'120', templet: function (d) {
                return formatSl(d.szzsl);
            }
        },
        {field: 'szzml', title: '缮证证明量', minWidth:'120', templet: function (d) {
                return formatSl(d.szzml);
            }
        },
        {field: 'zzzsl', title: '纸质证书量', minWidth:'120', templet: function (d) {
                return formatSl(d.zzzsl);
            }
        },
        {field: 'zzzml', title: '纸质证明量', minWidth:'120', templet: function (d) {
                return formatSl(d.zzzml);
            }
        },
        {field: 'dzzsl', title: '电子证书量', minWidth:'120', templet: function (d) {
                return formatSl(d.dzzsl);
            }
        },
        {field: 'dzzml', title: '电子证明量', minWidth:'120', templet: function (d) {
                return formatSl(d.dzzml);
            }
        },
        // {field: 'zzydzzsl', title: '纸质与电子证书量', minWidth:'120', style:"display:none", templet: function (d) {
        //         return formatSl(d.zzydzzsl);
        //     }
        // },
        // {field: 'zzydzzml', title: '纸质与电子证明量', minWidth:'120', style:"display:none", templet: function (d) {
        //         return formatSl(d.zzydzzml);
        //     }
        // },
        {field: 'scdj', title: '首次登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '100');
            }
        },
        {field: 'zydj', title: '转移登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '200');
            }
        },
        {field: 'bgdj', title: '变更登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '300');
            }
        },
        {field: 'zxdj', title: '注销登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '400');
            }
        },
        {field: 'gzdj', title: '更正登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '500');
            }
        },
        {field: 'yydj', title: '异议登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '600');
            }
        },
        {field: 'ygdj', title: '预告登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '700');
            }
        },
        {field: 'cjfdj', title: '查解封登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '800');
            }
        },
        {field: 'dyaqdj', title: '抵押权登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '37');
            }
        },
        {field: 'hblcSjl', title: '合并登记', minWidth:'120', templet: function (d) {
                return formatSl(d.hblcSjl);
            }
        },
        {field: 'jzqdj', title: '居住权登记',  minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '92');
            }
        },
        {field: 'fzl', title: '发证量',  minWidth:'120', templet: function (d) {
                return formatSl(d.fzl);
            }
        },
        {field: 'fsl', title: '复审量',  minWidth:'120', templet: function (d) {
                return formatSl(d.fsl);
            }
        },
    ];
    var rycols = [
        {field: 'rymc', title: '人员名称', minWidth: '200'},
        {field: 'sjl', title: '收件量', minWidth:'120', templet: function (d) {
                return formatSl(d.sjl);
            }
        },
        {field: 'zdlcgzl', title: '重点流程工作量',minWidth:'130', templet: function (d) {
                return formatSl(d.zdlcgzl);
            }
        },
        {field: 'yfwfzmsl', title: '打印有房无房证明量', minWidth:'120', templet: function (d) {
                return formatSl(d.yfwfzmsl);
            }
        },
        {field: 'qszmsl', title: '打印权属证明量', minWidth:'120', templet: function (d) {
                return formatSl(d.qszmsl);
            }
        },
        {field: 'zhcxsl', title: '综合查询量', minWidth:'120', templet: function (d) {
                return formatSl(d.zhcxsl);
            }
        },
        {field: 'szzsl', title: '缮证证书量', minWidth:'120', templet: function (d) {
                return formatSl(d.szzsl);
            }
        },
        {field: 'szzml', title: '缮证证明量', minWidth:'120', templet: function (d) {
                return formatSl(d.szzml);
            }
        },
        {field: 'zzzsl', title: '纸质证书量', minWidth:'120', templet: function (d) {
                return formatSl(d.zzzsl);
            }
        },
        {field: 'zzzml', title: '纸质证明量', minWidth:'120', templet: function (d) {
                return formatSl(d.zzzml);
            }
        },
        {field: 'dzzsl', title: '电子证书量', minWidth:'120', templet: function (d) {
                return formatSl(d.dzzsl);
            }
        },
        {field: 'dzzml', title: '电子证明量', minWidth:'120', templet: function (d) {
                return formatSl(d.dzzml);
            }
        },
        // {field: 'zzydzzsl', title: '纸质与电子证书量', minWidth:'120',style:"display:none", templet: function (d) {
        //         return formatSl(d.zzydzzsl);
        //     }
        // },
        // {field: 'zzydzzml', title: '纸质与电子证明量', minWidth:'120',style:"display:none", templet: function (d) {
        //         return formatSl(d.zzydzzml);
        //     }
        // },
        {field: 'scdj', title: '首次登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '100');
            }
        },
        {field: 'zydj', title: '转移登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '200');
            }
        },
        {field: 'bgdj', title: '变更登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '300');
            }
        },
        {field: 'zxdj', title: '注销登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '400');
            }
        },
        {field: 'gzdj', title: '更正登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '500');
            }
        },
        {field: 'yydj', title: '异议登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '600');
            }
        },
        {field: 'ygdj', title: '预告登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '700');
            }
        },
        {field: 'cjfdj', title: '查解封登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '800');
            }
        },
        {field: 'dyaqdj', title: '抵押权登记', minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '37');
            }
        },
        {field: 'hblcSjl', title: '合并登记', minWidth:'120', templet: function (d) {
                return formatSl(d.hblcSjl);
            }
        },
        {field: 'jzqdj', title: '居住权登记',  minWidth:'120', templet: function (d) {
                return getMapVal(d.djlxslMap, '92');
            }
        },
        {field: 'fzl', title: '发证量',  minWidth:'120', templet: function (d) {
                return formatSl(d.fzl);
            }
        },
        {field: 'fsl', title: '复审量',  minWidth:'120', templet: function (d) {
                return formatSl(d.fsl);
            }
        },
    ];
    cols = bmcols;

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#zqxgzltjListTable',
        toolbar: '#toolbar',
        title: '周期性工作量统计表',
        defaultToolbar: ['filter'],
        method : 'POST',
        data: [],
        even: true,
        cols: [cols],
        page: false,
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
               // count: res.data.length, //解析数据长度
                data: res.data //解析数据列表
            }
        },
        done: function (res, curr, count) {
            tableData = res.data;
            removeModal();
            setHeight();
            if (res.code === 1) {
                var msg = isNotBlank(res.msg)? res.msg : "未查询到工作量信息异常";
                layer.confirm(msg, {btn: '确认', title: '信息', area: '650px'}, function (index) {
                    layer.close(index);
                });
            }
        }
    });

    $('.bdc-table-box').on('mouseenter','td',function () {
        if($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1){
            $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
        }
        $('.layui-table-grid-down').on('click',function () {
            setTimeout(function () {
                $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
            },20);
        });
    });

    $('#reset').on('click',function(item){
        //改用树结构
        var zTreeObj = $.fn.zTree.getZTreeObj("select_tree");//ztree对象
        zTreeObj.checkAllNodes(false);//设置全部为false,不选中节点
        $('.org_select_show').text('选择');
        $('.org_select_tree').css('display', 'none');
        $("input[name='bmmc']").val("");//清空input值
        $("input[name='bmid']").val("");//清空input值
        $('.reseticon').hide();
    });

    //头工具栏事件
    table.on('toolbar(zqxgzltjListTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'exportExcel':
                exportExcel(checkStatus.data);
                break;
        }
    });

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改备注
     */
    function exportExcel(){
        if ($.isEmptyObject(tableData)) {
            warnMsg("未获取到数据，请先查询后在进行导出。");
            return;
        }
        // 标题
        var showColsTitle = ["序号"];
        // 列内容
        var showColsField = ["xh"];
        // 列宽
        var showColsWidth = [10];

        for(var index in cols){
            if(cols[index].type != 'checkbox' && !cols[index].toolbar){
                showColsTitle.push(cols[index].title);
                showColsField.push(cols[index].field);
                if(cols[index].width > 0){
                    showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                }else if(cols[index].minWidth > 0){
                    showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
                }else{
                    showColsWidth.push(300 / 100 * 5);
                }
            }
        }

        var data = tableData;
        for(var i = 0; i < data.length; i++){
            data[i].xh  = i + 1;
            for(var key in data[i]){
                if(key == "djlxslMap"){
                    handlerDjlxData(data[i], data[i][key]);
                }else{
                    data[i][key] = formatSl(data[i][key]);
                }
            }
        }

        // 设置Excel基本信息
        $("#fileName").val('周期性工作量统计');
        $("#sheetName").val('周期性工作量统计表');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        $("#data").val(JSON.stringify(data));
        $("#form").submit();
    }


    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        // 获取查询内容
        var obj = {};
        $(".search").each(function (i) {
            var name;
            var value;
            if ($(this).attr("id") == "selectZdlc") {
                name = "process";
                value = formSelects.value('selectZdlc', 'valStr');
            } else{
                value = $(this).val();
                name = $(this).attr('name');
            }

            if(!isNullOrEmpty(value)){
                obj[name] = value;
            }
        });
        if(!isNotBlank(obj) || isNullOrEmpty(obj.dimension ) || isNullOrEmpty(obj.tjlx) || isNullOrEmpty(obj.kssj)){
            warnMsg("请输入必要查询条件,如统计维度、统计类型、开始时间");
            return false;
        }
        if(isNotBlank(obj.kssj)){
            obj.kssj = strFormatDate(obj.kssj).Format('yyyyMMddhhmmss');
        }
        if(isNotBlank(obj.jzsj)){
            obj.jzsj = strFormatDate(obj.jzsj).Format('yyyyMMddhhmmss');
        }

        if(obj.dimension == "wd-ry"){
            cols = rycols;
        }else if(obj.dimension == "wd-bm"){
            cols = bmcols;
        }
        // 重新请求
        addModel();
        table.reload("zqxgzltjListTable", {
            url: getContextPath() + '/rest/v1.0/gzltj/bdcxx/list/zqxgzlxx',
            cols : [cols],
            where: obj,
            method: 'post',
            page: false,
            done: function (res, curr, count) {
                this.where = {};
                tableData = res.data;
                removeModal();
                setHeight();
                if (res.code === 1) {
                    var msg = isNotBlank(res.msg)? res.msg : "未查询到工作量信息异常";
                    layer.confirm(msg, {btn: '确认', title: '信息', area: '650px'}, function (index) {
                        layer.close(index);
                    });
                }
            }
        });
    });

    loadProcessCategoryName();

    /**
     * 渲染流程分类下拉框
     */
    function loadProcessCategoryName() {
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/process/listAllCategoryProcess",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            async: false,
            success: function (data) {
                var listData = new Array();
                listAllLcData = data.content;
                // 给登记类型下拉框赋值
                for (var key in listAllLcData) {
                    var item = {"value": listAllLcData[key].id, "name": listAllLcData[key].name}
                    listData.push(item);
                }
                formSelects.data('selectDjlx', 'local', {
                    arr: listData
                });

                // 给登记类型下拉框赋值，当不选登记类型时候，默认展示全部
                $.each(listAllLcData, function (index, item) {
                    $.each(item.processList, function (index1, item1) {
                        var lc = {"value": item1.processDefKey, "name": item1.name}
                        listLcData.push(lc);
                    });
                });
                formSelects.data('selectZdlc', 'local', {
                    arr: listLcData
                });

            }, error: function (e) {
                response.fail(e,'');
            }
        });
    }

    formSelects.on('selectDjlx', function(id, vals, val, isAdd, isDisabled){
        //id:           点击select的id
        //vals:         当前select已选中的值
        //val:          当前select点击的值
        //isAdd:        当前操作选中or取消
        //isDisabled:   当前选项是否是disabled
        var processList;
        $.each(listAllLcData, function (index, item) {
            if(item.name == val.name){
                processList = item.processList
            }
        });
        if(isAdd){
            // 如果开始是空，勾选第一个，需要将重点流程设置为空
            if(isNullOrEmpty(vals)){
                listLcData = [];
            }
            for (var key in processList) {
                var item = {"value": processList[key].processDefKey, "name": processList[key].name}
                listLcData.push(item);
            }
            formSelects.data('selectZdlc', 'local', {
                arr: listLcData
            });
        } else{
            for (var key in processList) {
                listLcData = listLcData.filter(function(item) {item.value != processList[key].processDefKey})
            }
            if(isNullOrEmpty(listLcData)){
                // 给登记类型下拉框赋值，当不选登记类型时候，默认展示全部
                $.each(listAllLcData, function (index, item) {
                    $.each(item.processList, function (index1, item1) {
                        var lc = {"value": item1.processDefKey, "name": item1.name}
                        listLcData.push(lc);
                    });
                });
            }
            formSelects.data('selectZdlc', 'local', {
                arr: listLcData
            });
        }
    });

});

function formatSl(data){
    if(isNotBlank(data)){
        return data;
    }else{
        return "";
    }
}

function getMapVal(data, key){
    if(isNotBlank(data) && isNotBlank(key)){
        var val = data[key];
        if(isNotBlank(val)){
            return val;
        }
    }
    return "";
}

function handlerDjlxData(data, djlxMap){
    if(isNotBlank(djlxMap)){
        for(var key in djlxMap){
            if(key == 100){
                data["scdj"] = formatSl(djlxMap[key]);
            }
            if(key == 200){
                data["zydj"] = formatSl(djlxMap[key]);
            }
            if(key == 300){
                data["bgdj"] = formatSl(djlxMap[key]);
            }
            if(key == 400){
                data["zxdj"] = formatSl(djlxMap[key]);
            }
            if(key == 500){
                data["gzdj"] = formatSl(djlxMap[key]);
            }
            if(key == 600){
                data["yydj"] = formatSl(djlxMap[key]);
            }
            if(key == 700){
                data["ygdj"] = formatSl(djlxMap[key]);
            }
            if(key == 800){
                data["cjfdj"] = formatSl(djlxMap[key]);
            }
            if(key == 37){
                data["dyaqdj"] = formatSl(djlxMap[key]);
            }
            if(key == 92){
                data["jzqdj"] = formatSl(djlxMap[key]);
            }
        }
    }
}


//时间比对
function completeDate(date1, date2) {
    var oDate1 = strFormatDate(date1);
    var oDate2 = strFormatDate(date2);
    if (oDate1.getTime() > oDate2.getTime()) {
        return true;
    } else {
        return false;
    }
}

