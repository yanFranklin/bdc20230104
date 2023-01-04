var reverseList = ['zl'];
// 分页重置查询参数
var searchParam = [];
var moduleCode = "";
var table;
layui.config({
    base: '../static/layui/lay/layui_exts/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    excel: 'excel'
});
layui.use(['jquery', 'element', 'form','excel','table', 'laydate'], function () {
    var $ = layui.jquery,
        table = layui.table,
        element = layui.element,
        laytpl = layui.laytpl,
        layer = layui.layer,
        laydate = layui.laydate,
        carousel = layui.carousel,
        response = layui.response,
        workflow = layui.workflow,
        moduleAuthority = layui.moduleAuthority,
        excel = layui.excel,
        formSelects = layui.formSelects;
    // 当前页数据
    var currentPageData = new Array();
    $(function () {
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');
        if (moduleCode) {
            setElementAttrByModuleAuthority(moduleCode);
        }

        loadProcessDefName();

        loadDate();

        /**
         * 初始化日期控件
         */
        function loadDate() {
            lay('.test-item').each(function () {
                var kssjdy = laydate.render({
                    elem: '#kssjdy',
                    type: 'datetime',
                    trigger: 'click',
                    done: function (value, date) {
                        //选择的结束时间大
                        if ($('#kssjxy').val() != '' && !completeDate($('#kssjxy').val(), value)) {
                            $('#kssjxy').val('');
                            $('.laydate-disabled.layui-this').removeClass('layui-this');
                        }
                        kssjxy.config.min = {
                            year: date.year,
                            month: date.month - 1,
                            date: date.date,
                            hours: date.hours,
                            minutes: date.minutes,
                            seconds: date.seconds
                        }
                    }
                });
                var kssjxy = laydate.render({
                    elem: '#kssjxy',
                    type: 'datetime',
                    trigger: 'click'
                });
            });
        }


        // 加载Table
        table.render({
            elem: '#lrqTable',
            url: getContextPath() + "/rest/v1.0/task/lqr",
            method: 'post',
            toolbar: '#toolbarDemo',
            title: '领取人台账',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'slbh', title: '项目编号', width: 150},
                {field: 'zl', title: '坐落', width: 150},
                {field: 'qlr', title: '申请人', align: 'center', width: 150},
                {field: 'torsionUserName', title: '认领人', align: 'center', width: 150},
                {field: 'taskName', title: '认领节点', align: 'center', width: 150},
                {field: 'delegationDate', title: '持有时间'},
                {field: 'claimStatusName', title: '备注'},
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                reverseTableCell(reverseList, "lrqTable");
                if (moduleCode) {
                    setElementAttrByModuleAuthority(moduleCode);
                }
            }
        });

        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            search();
        });
        // 回车查询
        $('.required').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        //查询
        function search() {

            // 获取查询参数
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });

            searchParam = obj;
            // 重新请求
            addModel();
            table.reload("lrqTable", {
                where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
            });
            removeModal();
        }

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });


        $('.bdc-table-box').on('mouseenter', 'td', function () {
            if ($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1) {
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
            }
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                }, 20);
            });
        });
        if (!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.")) {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
                $(this).siblings('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
            }).on('blur', function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                }, 150)
            });
        }
        //点击高级查询--4的倍数(超出两行的查询条件，如果不一致，需要再单独分装js)
        $('#seniorSearch').on('click', function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            $(this).parent().toggleClass('bdc-button-box-four');
            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }

            $('.bdc-percentage-container').css('padding-top', $('.bdc-search-content').height() + 10);

            if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
            } else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        });
    });

    // 监听表格操作栏按钮
    table.on('toolbar(lrqTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;

        console.log(obj);
        switch (obj.event) {
            case 'xz':
                var datas = checkStatus.data;
                if (datas.length>0) {
                    var exportFileData = exportFileData(datas,"lrqTable");
                    excel.exportExcel(exportFileData, '领取人任务.xlsx', 'xlsx')
                    return;
                } else {
                    layer.tips('选择要下载的数据',
                        this,
                        {
                            tips: 3
                        });
                }
                break;
        }
        $('[lay-id="lrqTable"]').find('.layui-table-tool').find('button').removeAttr('disabled');
        function exportFileData(datas,id) {
            //根据传入tableID获取表头
            var headers = $("div[lay-id=" + id + "] .layui-table-box table").get(0);
            var htrs = unique(headers.querySelectorAll('tr'));

            var titles = {};
            for (var j = 0; j < htrs.length; j++) {
                var hths = unique(htrs[j].querySelectorAll("th"));
                for (var i = 0; i < hths.length; i++) {
                    var clazz = hths[i].getAttributeNode('class').value;
                    var data_field = hths[i].getAttributeNode('data-field').value;
                    if (clazz != ' layui-table-col-special' && clazz != 'layui-hide') {
                        //排除居左、具有、隐藏字段
                        //修改:默认字段data-field+i,兼容部分数据表格中不存在data-field值的问题
                        titles[data_field] = hths[i].innerText;
                    }
                }
            }
            //根据传入tableID获取table内容
            // var bodys = $("div[lay-id=" + id + "] .layui-table-box table").get(1);
            // var btrs = unique(bodys.querySelectorAll("tr"))
            var bodysArr = new Array();
            for (var j = 0; j < datas.length; j++) {
                var contents = {};
                var btds = datas[j];
                for (var val in btds) {
                    for (var key in titles) {
                        //修改:默认字段data-field+i,兼容部分数据表格中不存在data-field值的问题
                        if (val === key) {
                            //根据表头字段获取table内容字段
                            contents[val] = btds[val];
                        }
                    }
                }
                if(isNullOrEmpty(contents['xh'])){
                    contents['xh'] = j + 1;
                }
                bodysArr.push(contents)
            }
            //将标题行置顶添加到数组
            bodysArr.unshift(titles);
            return bodysArr;
        }
        function unique(arr) {
            for (var i = 0; i < arr.length; i++) {
                for (var j = i + 1; j < arr.length; j++) {
                    if (arr[i] == arr[j]) {         //第一个等同于第二个，splice方法删除第二个
                        arr.splice(j, 1);
                        j--;
                    }
                }
            }
            return arr;
        }
    });

    function xz() {
        var checkStatus = table.checkStatus('lrqTable')
            ,data = checkStatus.data;
      //获取选中数据
        var dataAll = new Array();
        $.each(data, function(key, value){
            dataAll.push(value);
        })

        if (dataAll.length === 0) {
            layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要导出Excel的数据行!');
        } else {
            var data = JSON.stringify(dataAll);

            var tempForm = $("<form>");
            tempForm.attr("id", "tempForm1");
            tempForm.attr("style", "display:none");
            tempForm.attr("target", "export");
            tempForm.attr("method", "post");
            tempForm.attr("action", 'http://192.168.2.99:8030/realestate-inquiry-ui/dtcx/export/excel');

            // var dataInput = $("<input>");
            // dataInput.attr("type", "hidden");
            // dataInput.attr("name", "dataString");
            // dataInput.attr("value", dataString);
            // tempForm.append(dataInput);

            var dataInputSelect = $("<input>");
            dataInputSelect.attr("type", "hidden");
            dataInputSelect.attr("name", "data");
            dataInputSelect.attr("value", data);
            tempForm.append(dataInputSelect);

            tempForm.on("submit", function () {
            });
            tempForm.trigger("submit");
            $("body").append(tempForm);
            tempForm.submit();
            $("tempForm1").remove();
        }
    }

    /**
     *根据模块管理中的元素配置设置权限
     * @param moduleCode 资源名
     */
    function setElementAttrByModuleAuthority(moduleCode) {
        $.ajax({
            url: "/realestate-accept-ui/formCenter/moduleAuthority/" + moduleCode,
            type: 'GET',
            async: false,
            success: function (data) {
                console.log(data);
                if (data) {
                    for (var key in data) {
                        if (data[key] === "hidden") {
                            if ($("." + key)) {
                                $("." + key).hide();
                            }
                            if ($("#" + key)) {
                                $("#" + key).hide();
                            }
                        }
                    }
                }
                // 这里对input框进行样式修改，select框需要在各页面渲染处修改
                resetInputDisabledCss();
            }, error: function (xhr, status, error) {
                console.log("页面权限请求设置失败，请联系管理员！")
            }
        });
    }


    /**
     * 渲染流程名称下拉框
     */
    function loadProcessDefName() {
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/task/process/all",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            async: false,
            success: function (data) {
                $('#selectedDefName').append(new Option("请选择", ""));
                $.each(data, function (index, item) {
                    $('#selectedDefName').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                });
                layui.form.render("select");
                if (isNotBlank(formSelects)) {
                    formSelects.render();
                }
            }, error: function (e) {
                response.fail(e, '');
            }
        });
    }
});