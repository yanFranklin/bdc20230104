/**
 * describe: 统计监管流程JS
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
layui.use(['jquery', 'form', 'table', 'laydate', 'formSelects'], function () {
    //获取字典
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        formSelects = layui.formSelects;
    // 当前页数据
    var currentPageData = new Array();

    layui.sessionData('checkedDataLc', null);

    $(function () {
        // 工作流定义名称
        getDataByAjax('/rest/v1.0/process/gzldymcs', '', 'GET', function (data) {
            var lcmcData = [];
            data.forEach(function (v) {
                lcmcData.push({"name": v.name, "value": v.processDefKey});
            });
            formSelects.data('selectLcmc', 'local', {
                arr: lcmcData
            });
        });

        //2.渲染起止时间，限制截止时间大于开始时间
        lay('.test-item').each(function () {
            //初始化日期控件
            var startDate = laydate.render({
                elem: '#kssj'
                , format: 'yyyy-MM-dd HH:mm:ss'
                , trigger: 'click',
                done: function (value, date) {
                    //选择的结束时间大
                    if ($('#jzsj').val() != '' && !completeDate($('#jzsj').val(), value)) {
                        $('#jzsj').val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                        layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                    }
                    endDate.config.min = {
                        year: date.year,
                        month: date.month - 1,
                        date: date.date
                    }
                }
            });
            var endDate = laydate.render({
                elem: '#jzsj'
                , format: 'yyyy-MM-dd HH:mm:ss'
                , trigger: 'click'
            });

        });

        //3.定义table的cols，显示默认表格
        renderTable();

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

        //6. 输入框删除图标
        if (!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.")) {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
                $(this).siblings('.layui-input').val('');
                $(this).siblings().find('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                $(this).parents('.layui-input-inline').find('.layui-icon-close').removeClass('bdc-hide');
                $(this).siblings('.layui-edge').addClass('bdc-hide');
            }).on('blur', function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                    $this.parents('.layui-input-inline').find('.layui-icon-close').addClass('bdc-hide');
                    $this.siblings('.layui-edge').removeClass('bdc-hide');
                }, 150)
            });
        }

        // 监听表格操作栏按钮
        table.on('toolbar(jglcFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel(checkStatus.data, obj.config.cols[0], "checked");
                    break;
            }
        });
        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(jglcFilter)', function (obj) {
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;

            $.each(data, function (i, v) {
                var keyT = v.processInstanceId;
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedDataLc', {
                        key: keyT, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedDataLc', {
                        key: keyT, remove: true
                    });
                }
            });
        });
        // 监听行事件
        table.on('tool(jglcFilter)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'openpage') {
                //得到当前行数据
                var listArray = {
                    processInstanceId: obj.data.processInstanceId,
                    processDefName: obj.data.processDefName,
                    claimStatus: obj.data.claimStatus,
                    claimStatusName: obj.data.claimStatusName,
                    taskName: obj.data.taskName,
                    processInstanceType: 'list',
                    taskId : obj.data.taskId
                };
                sessionStorage.setItem('listArray' + obj.data.processInstanceId, JSON.stringify(listArray));
                window.open("/realestate-portal-ui/view/new-page.html?name=" + obj.data.processInstanceId+"&type=list", obj.data.slbh);
            }
        });
    });

    //比较起止时间
    function completeDate(date1, date2) {
        var oDate1 = new Date(date1);
        var oDate2 = new Date(date2);
        if (oDate1.getTime() > oDate2.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    // 加载表格
    function renderTable() {
        var cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {
                field: 'procTimeoutCount', minWidth: 110, title: '超期状态', templet: function (d) {
                    var procTimeoutCount = d.procTimeoutCount;
                    procTimeoutCount = procTimeoutCount.toString().replace("-", "");
                    return '<span class="bdc-table-state-th">超期' + procTimeoutCount + '天</span>';
                }
            },
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'startUserName', minWidth: 100, title: '受理人'},
            {field: 'procDefName', minWidth: 160, title: '流程名称'},
        ];
        var pageUrl = '/realestate-inquiry-ui/rest/v1.0/tjjg/lc/page';
        table.render({
            elem: '#jglcTable',
            title: '统计监管流程',
            url: pageUrl,
            toolbar: '#toolbarDemo',
            defaultToolbar: ['filter'],
            where: {},
            method: 'GET',
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            even: true,
            cols: [cols],
            page: true,
            parseData: function (res) {
                // 设置选中行
                if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedDataLc');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.id) {
                                v.LAY_CHECKED = true;
                            }
                        })
                    });
                }
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            done: function () {
                setHeight();
                reverseTableCell(reverseList);
                table.resize('jglcTable');
            }
        });
    }

    // 重新渲染表格数据
    function reloadTable(whereObj) {
        addModel();
        layui.use(['table'], function () {
            var table = layui.table;
            table.reload('jglcTable', {
                where: whereObj,
                page: {
                    curr: 1,  //重新从第 1 页开始
                    limits: [10, 50, 100]
                },
                done: function (res, curr, count) {
                    removeModal();
                    setHeight();
                    reverseTableCell(reverseList);
                    table.resize('jglcTable');
                }
            });
        });
    }

    // 查询方法
    function search() {
        var requestConditions = getSearchParam();
        reloadTable({
            paramJson: JSON.stringify(requestConditions)
        });
    }

    function getSearchParam(){
        var lcmcKey = formSelects.value('selectLcmc', 'val')[0];
        var requestConditions = new Array();
        requestConditions.push(condition("processKey", "eq", lcmcKey));
        requestConditions.push(condition("procTimeoutCount", "eq" , $("#cqsj").val()));
        requestConditions.push(condition("startTime", "egt" , $("#kssj").val()));
        requestConditions.push(condition("startTime", "elt" , $("#jzsj").val()));
        return requestConditions;
    }

    function condition(key,judge,value){
        return {
            requestKey : key,
            requestJudge : judge,
            requestValue : value == undefined? "" : value,
        }
    }

    function exportExcel(d, cols, type) {
        if ($.isEmptyObject(d) && type == "checked") {
            // 未选择数据时，询问是否导出全部
            layer.confirm('未勾选数据,将导出全部数据？', {
                title: '提示',
                btn: ['确定', '取消'] //按钮
            }, function (index) {
                addModel();
                var requestConditions = getSearchParam();
                $.ajax({
                    url: '/realestate-inquiry-ui/rest/v1.0/tjjg/lc/page?loadTotal=true&page=1&size=12',
                    type: "GET",
                    async: true,
                    contentType: 'application/json;charset=utf-8',
                    data: {paramJson:JSON.stringify(requestConditions)},
                    success: function (data) {
                        removeModal();
                        layer.close(index);
                        if(data.content.length>0){
                            doExport(data.content, cols, type);
                        }else{
                            warnMsg("未获取到数据");
                        }
                    },
                    error: function (err) {
                        removeModal();
                        delAjaxErrorMsg(err);
                    }
                });
            });
        }else{
            doExport(d, cols, type);
        }
    }

    function doExport(d, cols, type){
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

        var data = d;

        for (var i = 0; i < data.length; i++) {
            data[i].xh = i + 1;
            data[i].procTimeoutCount = "超期"+data[i].procTimeoutCount+"天";
        }
        // 设置Excel基本信息
        $("#fileName").val('统计流程超期查询');
        $("#sheetName").val('统计表');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        $("#data").val(JSON.stringify(data));
        $("#form").submit();
    }

    //数据交互
    function getContextPath() {
        var pathName = document.location.pathname;
        var index = pathName.substr(1).indexOf("/");
        var result = pathName.substr(0, index + 1);
        return result;
    }

    function getDataByAjax(_path, _param, _type, _fn, async) {
        var _url = getContextPath() + _path;
        var getAsync = false;
        if (async) {
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