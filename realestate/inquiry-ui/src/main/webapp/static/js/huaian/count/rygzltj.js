/**
 * author: <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
 * version 1.0.0  2022/06/29
 * describe: 人员工作量统计
 */
var reverseList = ['zl'];
var tableData = [];
layui.use(['form', 'jquery', 'laydate', 'element', 'table', 'laytpl'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        laydate = layui.laydate,
        form = layui.form;

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


    var cols=[
        {field: 'rymc', title: '人员名称'},
        {field: 'jsmc', title: '角色名称'},
        {field: 'sjl', title: '收件量', templet: function (d) {
                return formatSl(d.sjl);
            }
        },
        {field: 'fsl', title: '复审量', templet: function (d) {
                return formatSl(d.fsl);
            }
        },
        {field: 'szl', title: '缮证量', templet: function (d) {
                return formatSl(d.szl);
            }
        },
        {field: 'fzl', title: '发证量', templet: function (d) {
                return formatSl(d.fzl);
            }
        },
        {field: 'yfwfzmsl', title: '打印信息利用', templet: function (d) {
                return formatSl(d.yfwfzmsl);
            }
        },
        {field: 'qszmsl', title: '打印权属证明', templet: function (d) {
                return formatSl(d.qszmsl);
            }
        },
        {field: 'djbsl', title: '打印登记簿量', templet: function (d) {
                return formatSl(d.djbsl);
            }
        },
        {field: 'zhcxl', title: '综合查询量', templet: function (d) {
                return formatSl(d.zhcxsl);
            }
        },
        {field: 'zs', title: '总数', templet: function (d) {
                return formatSl(d.zs);
            }
        },
    ];

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#rygzltjListTable',
        toolbar: '#toolbar',
        title: '人员工作量统计表',
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
    table.on('toolbar(rygzltjListTable)', function (obj) {
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
            if(cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar){
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
                data[i][key] = formatSl(data[i][key]);
            }
        }

        // 设置Excel基本信息
        $("#fileName").val('人员工作量统计');
        $("#sheetName").val('人员工作量统计表');
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
            var value = $(this).val();
            var name = $(this).attr('name');
            if(!isNullOrEmpty(value)){
                obj[name] = value;
            }
        });

        if(!isNotBlank(obj) || isNullOrEmpty(obj.tjlx) || isNullOrEmpty(obj.kssj)){
            warnMsg(" 请输入必要查询条件,如统计类型、开始时间");
            return false;
        }
        if(isNotBlank(obj.kssj)){
            obj.kssj = strFormatDate(obj.kssj).Format('yyyyMMddhhmmss');
        }
        if(isNotBlank(obj.jzsj)){
            obj.jzsj = strFormatDate(obj.jzsj).Format('yyyyMMddhhmmss');
        }
        // 重新请求
        addModel();
        console.info(obj);
        table.reload("rygzltjListTable", {
            url: getContextPath() + '/rest/v1.0/gzltj/bdcxx/list/rygzlxx',
            where: obj,
            method: 'post',
            page: false,
            initSort: {
                field: 'jsmc', type: 'asc'
            },
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

    function formatSl(data){
        if(isNotBlank(data)){
            return data;
        }else{
            return "";
        }
    }

    //对比时间
    function completeDate(date1, date2) {
        var oDate1 = strFormatDate(date1);
        var oDate2 = strFormatDate(date2);
        if (oDate1.getTime() > oDate2.getTime()) {
            return true;
        } else {
            return false;
        }
    }
});