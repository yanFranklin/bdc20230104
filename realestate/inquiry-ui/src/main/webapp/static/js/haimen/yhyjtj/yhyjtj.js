layui.config({
    base: '../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});
var reverseList = ['zl'];
var searchData, searchFilterData, needsearch, needFilter;
// 当前页数据
var currentPageData = new Array();

layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        formSelects = layui.formSelects;

    // 日期控件
    lay('.test-item').each(function(){
        //初始化日期控件
        var startDate = laydate.render({
            elem: '#kssj',
            trigger: 'click',
            /*type: 'datetime',*/
            format: 'yyyy-MM-dd',
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
            elem: '#jssj',
            trigger: 'click',
            /*type: 'datetime',*/
            format: 'yyyy-MM-dd',
        });

    });

    // 下拉选择添加删除按钮
    renderSelectClose(form);

    $(function () {

        $.ajax({
            url: getContextPath() + "/rest/v1.0/yhyjtj/queryXtjg",
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                console.info(data);
                if(data){
                    var yhData = [];
                    data.forEach(function (v) {
                        yhData.push({"name": v.jgmc, "value": v.jgmc});
                    });
                    formSelects.data('selectDyqr', 'local', {
                        arr: yhData
                    });
                }
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr);
            }
        });

        // 加载Table
        table.render({
            elem: '#pageTable',
            toolbar: '#toolbarDemo',
            title: '银行月结统计列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                /*{type: 'checkbox', fixed: 'left'},*/
                {field: 'xh', title: '序号', width: 50, type: 'numbers'},
                {field: 'slbh', title: '受理编号', minWidth: 150},
                {field: 'gzldymc', title: '工作流定义名称', minWidth: 200},
                {field: 'bdcqzh', title: '不动产权证号', minWidth: 250},
                {field: 'zl', title: '坐落', minWidth: 300},
                {field: 'dyqr', title: '抵押权人', width: 250},
                {field: 'dyr', title: '抵押人', width: 200},
                {field: 'bjsj', width: 180, title: '办结时间'},
                {field: 'zdzhmj', minWidth: 150, title: '宗地宗海面积'},
                {field: 'dzwmj', minWidth: 150, title: '定着物面积'},
                {field: 'zdzhyt', width: 100, title: '宗地宗海用途'},
                {field: 'dzwyt', width: 100, title: '定着物用途'},
                {field: 'hj', width: 100, title: '合计'},
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                // 设置选中行
                if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.xmid) {
                                v.LAY_CHECKED = true;
                            }
                        })
                    });
                }

                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                setHeight();
                reverseTableCell(reverseList);
            }
        });

        // 监听表格操作栏按钮
        table.on('toolbar(pageTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel(checkStatus.data, obj.config.cols[0]);
                    break;
            }
        });

        // 导出Excel
        // 通过隐藏form提交方式，避免ajax方式不支持下载文件
        function exportExcel(d, cols){
            var obj = getSearchData();
            if (!isNotBlank(obj.kssj) && !isNotBlank(obj.jssj) && !isNotBlank(obj.dyqr)  && !isNotBlank(obj.dyrlx)) {
                warnMsg(" 请添加搜索条件后，在进行导出。 ");
                return;
            }
            addModel();
            $.ajax({
                url: getContextPath()+"/rest/v1.0/yhyjtj/getExportData",
                type: 'POST',
                dataType: "json",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();
                    exportData(d, cols, data);
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                }
            });
        }

        function exportData(d, cols, exportData) {
            addModel();
            // 标题
            var showColsTitle = new Array();
            // 列内容
            var showColsField = new Array();
            // 列宽
            var showColsWidth = new Array();
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

            var kssj = $("#kssj").val();
            var jssj = $("#jssj").val();

            if(kssj && jssj && $("#dyrlx").val()){
                var dyrlx = $("#dyrlx").find("option:selected").text();
                var name = Format(kssj, "yyyy年MM月dd日") + "~" + Format(jssj, "yyyy年MM月dd日") + dyrlx + '抵押登记'
                $("#sheetName").val(name);
                $("#fileName").val(name);
            }else{
                $("#fileName").val( '银行月结抵押登记');
                $("#sheetName").val('银行月结抵押登记');
            }

            // 设置Excel基本信息 2020年4月单位非住宅抵押登记
            $("#cellTitle").val(showColsTitle);
            $("#cellWidth").val(showColsWidth);
            $("#cellKey").val(showColsField);


            // 导出增加序号字段
            var zj = 0;
            for (var i = 0; i < exportData.length; i++) {
                exportData[i].xh = i + 1;
                if(parseFloat(exportData[i].hj).toString() != "NaN"){
                    zj += exportData[i].hj;
                }
            }

            var zjStr = "总计：" + zj;
            exportData.push({
                xh : zjStr, slbh : zjStr, gzldymc : zjStr, bdcqzh : zjStr, zl : zjStr, dyqr : zjStr, dyr : zjStr, bjsj : zjStr,
                zdzhmj : zjStr, dzwmj : zjStr, zdzhyt : zjStr, dzwyt : zjStr, hj : zjStr
            });

            removeModal();
            $("#data").val(JSON.stringify(exportData));
            $("#mergeSameCell").val(true);
            $("#mergeColumnCellKeyList").val(JSON.stringify(new Array("xh,slbh,gzldymc,bdcqzh,zl,dyqr,dyr,bjsj,zdzhmj,dzwmj,zdzhyt,dzwyt,hj")));
            $("#addBorder").val(true);
            $("#form").submit();
        }

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(pageTable)', function (obj) {
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;

            $.each(data, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedData', {
                        key: v.xmid, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedData', {
                        key: v.xmid, remove: true
                    });
                }
            });
        });

        // 查询处理逻辑
        function search() {
            // 每次查询清除下导出缓存数据
            layui.sessionData("checkedData", null);

            // 获取查询参数
            var obj = getSearchData();
            if (!isNotBlank(obj.kssj) && !isNotBlank(obj.jssj) && !isNotBlank(obj.dyqr)  && !isNotBlank(obj.dyrlx)) {
                warnMsg(" 请添加搜索条件后，在进行导出。 ");
                return;
            }

            addModel();
            // 重新请求
            table.reload("pageTable", {
                url: getContextPath()+ "/rest/v1.0/yhyjtj/page",
                where: obj,
                page: {
                    curr: 1,  //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                },
                done: function (res, curr, count) {
                    removeModal();
                    if(res.code ==0){
                        currentPageData = res.data;
                        reverseTableCell(reverseList);
                        setHeight();
                    }else{
                        $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + res.msg);
                    }
                }
            });
        }


        // 获取页面中查询参数
        function getSearchData(){
            var searchData = $(".search").serializeArray();
            var useData = {};
            searchData.forEach(function (v) {
                useData[v.name] = v.value;
            });
            useData['dyqr'] = formSelects.value('selectDyqr', 'name').join(",");
            return useData;
        }

        //监听滚动事件
        var scrollTop = 0,
            scrollLeft = 0;
        var tableTop = 0, tableLeft = 0;
        var $nowBtnMore = '';
        //点击表格中的更多
        $('.bdc-table-box').on('click','.bdc-more-btn',function (event) {
            tableTop = $(this).offset().top;
            tableLeft = $(this).offset().left;
            event.stopPropagation();
            $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
            var $btnMore = $(this).siblings('.bdc-table-btn-more');
            if($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight){
                // $btnMore.css({top: $(this).offset().top + 26 -scrollTop,right: 20});
                $btnMore.css({top: tableTop + 26 -scrollTop,left:tableLeft - 30});
            }else {
                // $btnMore.css({top: $(this).offset().top -scrollTop - $btnMore.height(),right: 20});
                $btnMore.css({top: tableTop -scrollTop - $btnMore.height(),left: tableLeft - 30});
            }
            $('.bdc-table-btn-more').hide();
            $btnMore.show();
        });

        //点击更多内的任一内容，隐藏
        $('.bdc-table-btn-more a').on('click',function () {
            $(this).parent().hide();
        });
        //点击页面任一空白位置，消失
        $('body').on('click',function () {
            $('.bdc-table-btn-more').hide();
            $('.bdc-table-top-more-show').hide();
        });

    });
});