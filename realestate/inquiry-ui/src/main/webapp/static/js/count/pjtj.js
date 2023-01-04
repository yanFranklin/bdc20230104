/**
 * describe: 评价统计页面
 */
var reverseList = ['zl'];
layui.config({
    base: '../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});
var colsList = {};
var bmmcList = {};
var bmyyyZd = {};
layui.use(['jquery','form','table','laydate','formSelects'], function () {
    //获取字典
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        formSelects = layui.formSelects;

    $(function () {
        // 获取不满意字典项
        getDataByAjax('/rest/v1.0/pjqcx/zd/bmyyy','','GET',function (data) {
            data.forEach(function(v){
                bmyyyZd[v.DM] = v.MC;
            });
        });
        //1.默认渲染部门名称，选中名称后，根据名称渲染办理人员
        getDataByAjax('/rest/v1.0/process/rootOrgs','','GET',function (data) {
            var bmData = [];
            data.forEach(function (v) {
                bmData.push({"name": v.name, "value": v.id});
                bmmcList[v.id] = v.code;
            });
            formSelects.data('selectBmmc', 'local', {
                arr: bmData
            });
        });
        formSelects.on('selectBmmc', function(id, vals, val){
            getDataByAjax('/rest/v1.0/process/users/'+val.value,'','GET',function (param) {
                var ryData = [];
                param.forEach(function (v) {
                    ryData.push({"name": v.alias, "value": v.alias});
                });
                formSelects.data('selectBlry', 'local', {
                    arr: ryData
                });
            });
        });
        getDataByAjax('/rest/v1.0/process/gzldymcs','','GET',function (data) {
            var ywmcData = [];
            data.forEach(function (v) {
                ywmcData.push({"name": v.name, "value": v.processDefKey});
            });
            formSelects.data('selectYwmc', 'local', {
                arr: ywmcData
            });
        });
        formSelects.on('selectYwmc', function(id, vals, val){
            getDataByAjax('/rest/v1.0/process/node/'+val.value,'','GET',function (param) {
                var nodeData = [];
                param.forEach(function (v) {
                    nodeData.push({"name": v.name, "value": v.name});
                });
                formSelects.data('selectJdmc', 'local', {
                    arr: nodeData
                });
            });
        });
        //2.渲染起止时间，限制截止时间大于开始时间
        lay('.test-item').each(function(){
            //初始化日期控件
            var startDate = laydate.render({
                elem: '#kssj'
                ,trigger: 'click',
                done: function(value,date){
                    //选择的结束时间大
                    if($('#jssj').val() != '' && !completeDate($('#jssj').val(),value)){
                        $('#jssj').val('');
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
                elem: '#jssj'
                ,trigger: 'click'
            });

        });

        //3.定义table的cols，显示默认表格
        colsList['default'] = [
            {field: 'number', type: 'numbers', width: 50, title: '序号'},
            {field: 'blry', title: '办理人员', align: 'center'},
            {field: 'ywbh', title: '业务编号', align: 'center'},
            {field: 'ywmc', title: '业务名称', align: 'center'},
            {field: 'myd', title: '满意度', align: 'center',templet: function(d){
                var value ="";
                if(isNotBlank(d.myd)){
                    switch (d.myd) {
                        case '1':
                            value = "好评";
                            break;
                        case '2':
                            value = "中评";
                            break;
                        case '3':
                            value = "差评";
                            break;
                        case '5':
                            value = "未评";
                            break;
                    }
                }
                return value;
                }},
            {field: 'jdmc', title: '节点名称', align: 'center'},
            {field: 'djbmmc',title: '部门名称', align: 'center'},
            {field: 'ywblsj', title: '业务办理时间', align: 'center',templet: function (d) {
                    return formatDate(d.ywblsj);
                }},
            {field: 'pjsj', title: '评价时间', align: 'center',templet: function (d) {
                    return formatDate(d.pjsj);
                }},
            {field: 'bmyyy', title: '不满意原因', align: 'center', templet: function(d){
                if(isNotBlank(d.bmyyy)){
                    return bmyyyZd[d.bmyyy];
                }else{
                    return "";
                }
            }}
        ];
        // 按部门过滤
        colsList['dept'] = [
            {field: 'pm', title: '排名', align: 'center'},
            {field: 'djbmmc', title: '部门名称', align: 'center'},
            {field: 'pjrc', title: '需评价业务量', align: 'center'},
            {field: 'hp', title: '好评', align: 'center'},
            {field: 'zp', title: '中评', align: 'center'},
            {field: 'cp', title: '差评', align: 'center'},
            {field: 'wp', title: '未评', align: 'center'},
            {field: 'pjf', title: '平均分', align: 'center'},
            {field: 'myl', title: '好评率', align: 'center'},
            {field: 'pjl', title: '评价率', align: 'center'},
        ];
        // 按人员过滤
        colsList['user'] = [
            {field: 'pm', title: '排名', align: 'center'},
            {field: 'blry', title: '办理人员', align: 'center'},
            {field: 'pjrc', title: '需评价业务量', align: 'center'},
            {field: 'hp', title: '好评', align: 'center'},
            {field: 'zp', title: '中评', align: 'center'},
            {field: 'cp', title: '差评', align: 'center'},
            {field: 'wp', title: '未评', align: 'center'},
            {field: 'pjf', title: '平均分', align: 'center'},
            {field: 'myl', title: '好评率', align: 'center'},
            {field: 'pjl', title: '评价率', align: 'center'},
        ];
        // 按业务名称
        colsList['process'] = [
            {field: 'pm', title: '排名', align: 'center'},
            {field: 'ywmc', title: '业务名称', align: 'center'},
            {field: 'pjrc', title: '需评价业务量', align: 'center'},
            {field: 'hp', title: '好评', align: 'center'},
            {field: 'zp', title: '中评', align: 'center'},
            {field: 'cp', title: '差评', align: 'center'},
            {field: 'wp', title: '未评', align: 'center'},
            {field: 'pjf', title: '平均分', align: 'center'},
            {field: 'myl', title: '好评率', align: 'center'},
            {field: 'pjl', title: '评价率', align: 'center'},
        ];
        // 按节点名称
        colsList['node'] = [
            {field: 'pm', title: '排名', align: 'center'},
            {field: 'jdmc', title: '节点名称', align: 'center'},
            {field: 'pjrc', title: '需评价业务量', align: 'center'},
            {field: 'hp', title: '好评', align: 'center'},
            {field: 'zp', title: '中评', align: 'center'},
            {field: 'cp', title: '差评', align: 'center'},
            {field: 'wp', title: '未评', align: 'center'},
            {field: 'pjf', title: '平均分', align: 'center'},
            {field: 'myl', title: '好评率', align: 'center'},
            {field: 'pjl', title: '评价率', align: 'center'},
        ];
        // 按人员+业务名称过滤
        colsList['userAndProcess'] = [
            // {field: 'number', type: 'numbers', width: 50, title: '序号'},
            {field: 'blry', title: '办理人员', align: 'center'},
            {field: 'ywmc', title: '业务名称', align: 'center'},
            {field: 'pjrc', title: '需评价业务量', align: 'center'},
            {field: 'hp', title: '好评', align: 'center'},
            {field: 'zp', title: '中评', align: 'center'},
            {field: 'cp', title: '差评', align: 'center'},
            {field: 'wp', title: '未评', align: 'center'},
            {field: 'pjf', title: '平均分', align: 'center'},
            {field: 'myl', title: '好评率', align: 'center'},
            {field: 'pjl', title: '评价率', align: 'center'},
        ];
        var defaultWhereObj = {
            tjwd: "default"
        };
        renderTable(colsList.default, defaultWhereObj);

        //4.查询 按钮
        $('#search').on('click', function () {
            search();
        });
        //回车查询
        $('.bdc-pjtj').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        //5. 监听表格上面 维度 复选框选择，重置表格
        form.on('select(wdFilter)', function(data){
            formSelects.undisabled();
            switch (data.value) {
                case 'dept':
                    //部门
                    formSelects.disabled('selectBmmc');
                    break;
                case 'user':
                    //人员
                    formSelects.disabled('selectBlry');
                    break;
                case 'process':
                    //流程
                    formSelects.disabled('selectYwmc');
                    break;
                case 'node':
                    //节点
                    formSelects.disabled('selectJdmc');
                    break;
                case 'userAndProcess':
                    formSelects.disabled('selectBlry');
                    formSelects.disabled('selectYwmc');
                    break;
            }
            search();
        });

        //6. 输入框删除图标
        if(!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i)=="9."))
        {
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

        // 监听表格操作栏按钮
        table.on('toolbar(pjtjFilter)', function (obj) {
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel(obj.config.cols[0]);
                    break;
            }
        });
    });

    //比较起止时间
    function completeDate(date1,date2){
        var oDate1 = new Date(date1);
        var oDate2 = new Date(date2);
        if(oDate1.getTime() > oDate2.getTime()){
            return true;
        } else {
            return false;
        }
    }

    // 加载表格
    function renderTable(cols,whereObj){
        var pageUrl = "";
        // 统计维度为空时，做评价信息普通查询。
        if("default" == whereObj.tjwd){
            pageUrl = '/realestate-inquiry-ui/rest/v1.0/pjqcx/page';
        }else{
            pageUrl = '/realestate-inquiry-ui/rest/v1.0/pjqcx/group/page';
        }
        table.render({
            elem: '#pjtjTable',
            title: '',
            toolbar: '#toolbarDemo',
            defaultToolbar: ['filter'],
            url: pageUrl,
            method: 'GET',
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            where: whereObj,
            even: true,
            cols: [cols],
            page: true,
            limits: [10, 50, 100, 200, 500],
            parseData: function (res) {
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            done: function (res) {
                setHeight();
                reverseTableCell(reverseList);
                table.resize('pjtjTable');
                // mergecols
                if(whereObj.tjwd != "default"){
                    $("#exportExcel").css("display","block");
                }
                if(whereObj.tjwd == "userAndProcess"){
                    merge(res);
                }
            }
        });
    }

    //查询
    function search(){
        var useData = getSearchParam();
        var tjwd = $("select[name='wd']").val();
        renderTable(colsList[tjwd],useData);
    }

    // 获取查询参数
    function getSearchParam(){
        var searchData = $(".bdc-pjtj").serializeArray();
        var useData = {};
        searchData.forEach(function (v) {
            useData[v.name] = v.value;
        });
        var djbmmc = bmmcList[formSelects.value('selectBmmc', 'val')[0]];
        useData['djbmdm'] = djbmmc == undefined? "" : djbmmc;
        var blry = formSelects.value('selectBlry', 'val')[0];
        useData['blry'] = blry == undefined? "" : blry;
        var ywmc = formSelects.value('selectYwmc', 'name')[0];
        useData['ywmc'] = ywmc == undefined? "" : ywmc;
        var jdmc = formSelects.value('selectJdmc', 'val')[0];
        useData['jdmc'] = jdmc == undefined? "" : jdmc;
        var tjwd = $("select[name='wd']").val();
        useData['tjwd'] = tjwd;
        return useData;
    }
    // 表格合并操作
    function merge(res) {
        var data = res.data;
        var mergeIndex = 0;//定位需要添加合并属性的行数
        var mark = 1; //这里涉及到简单的运算，mark是计算每次需要合并的格子数
        var columsName = "blry";//需要合并的列名称
        var columsIndex = 0;//需要合并的列索引值

        var trArr = $(".layui-table-body>.layui-table").find("tr");//所有行
        for (var i = 1; i < res.data.length; i++) { //这里循环表格当前的数据
            var tdCurArr = trArr.eq(i).find("td").eq(columsIndex);//获取当前行的当前列
            tdCurArr.css("background-color","#fff");//将当前表格所需合并列背景色统一
            var tdPreArr = trArr.eq(mergeIndex).find("td[data-field="+ columsName +"]");//获取相同列的第一列
            if (data[i][columsName] === data[i-1][columsName]) { //后一行的值与前一行的值做比较，相同就需要合并
                mark += 1;
                tdPreArr.each(function () {//相同列的第一列增加rowspan属性
                    $(this).attr("rowspan", mark);
                });
                tdCurArr.each(function () {//当前行隐藏
                    $(this).css("display", "none");
                });
            }else {
                mergeIndex = i;
                mark = 1;//一旦前后两行的值不一样了，那么需要合并的格子数mark就需要重新计算
            }
        }
        mergeIndex = 0;
        mark = 1;
    }

    // 导出Excel方法
    function exportExcel(cols) {
        var searchParam = getSearchParam();
        var exportAllData = new Array();
        getDataByAjax('/rest/v1.0/pjqcx/group/list',searchParam,'GET',
            function (data) {
                console.info(data);
                exportAllData = data;
            },false);

        // 标题
        var showColsTitle = [];
        // 列内容
        var showColsField = [];
        // 列宽
        var showColsWidth = [];
        for (var index in cols) {
            if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
                showColsTitle.push(cols[index].title);
                showColsField.push(cols[index].field);
                if (cols[index].width > 0) {
                    showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                } else if (cols[index].minWidth > 0) {
                    showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
                } else {
                    showColsWidth.push(200 / 100 * 15);
                }
            }
        }

        // 导出增加序号字段
        for (var i = 0; i < exportAllData.length; i++) {
            exportAllData[i].xh = i + 1;
        }
        // 设置Excel基本信息
        $("#fileName").val('不动产登记窗口业务评价情况统计表');
        $("#sheetName").val('不动产登记窗口业务评价情况统计表');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        if("userAndProcess" == $("select[name='wd']").val()){
            $("#mergeSameCell").val(true);
            $("#mergeCellKey").val(["blry"]);
        }
        $("#data").val(JSON.stringify(exportAllData));
        $("#form").submit();
    }

    //数据交互
    function getContextPath(){
        var pathName = document.location.pathname;
        var index = pathName.substr(1).indexOf("/");
        var result = pathName.substr(0, index + 1);
        return result;
    }
    function getDataByAjax(_path, _param,_type, _fn, async) {
        var _url = getContextPath() + _path;
        var getAsync = false;
        if(async){
            getAsync = async
        }
        $.ajax({
            url: _url,
            type: _type,
            async: getAsync,
            contentType: 'application/json;charset=utf-8',
            data: _param,
            success: function (data) {
                _fn.call(this, data, data);
            },
            error: function (err) {
                console.log(err);
            }
        });
    }
});