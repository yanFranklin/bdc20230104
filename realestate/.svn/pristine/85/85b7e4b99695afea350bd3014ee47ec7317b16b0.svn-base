var gzlslids;
layui.use(['jquery', 'table', 'element', 'form', 'laytpl', 'laydate', 'layer','response'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        laydate = layui.laydate,
        response = layui.response;
    $(function () {

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

        function completeDate(date1, date2) {
            var oDate1 = new Date(date1);
            var oDate2 = new Date(date2);
            if (oDate1.getTime() > oDate2.getTime()) {
                return true;
            } else {
                return false;
            }
        }

        //监听台账查询 input 点击
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

        // 渲染表格
        var waitUrl = getContextPath() + "/rest/v1.0/task/todo";
        var waitTableId = '#waitTable';
        var waitCurrentId = 'dbTable';
        var waitToolbar = '#toolbarDemo';
        renderWaitTable(waitUrl, waitTableId, waitCurrentId, waitToolbar);


        // 待办任务头工具栏事件
        table.on('toolbar(waitTableFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            // 过滤了右侧的筛选事件
            if (obj.event != "LAYTABLE_COLS") {
                var selectedNum = checkStatus.data.length;
                if ( selectedNum != 1 ){
                    warnMsg("请选择一条数据进行操作！");
                    return false;
                }
            }

            var checkData = checkStatus.data[0];
            if (obj.event === 'match') {
                // 判断是否可以匹配
                checkMatch(checkData);
            }
        });
        
        //查询
        $('#dbSearch').on('click', function () {
            addModel();
            // 获取查询内容
            var obj = {};
            $(".dbSearch").each(function () {
                var value = $(this).val();
                var name = $(this).attr('name');
                obj[name] = value;
                if(name==='processDefName'){
                    var processDefName = $("#selectedDefName").find("option:selected").text();
                    obj[name] = processDefName == "请选择" ? "" : processDefName;
                }
            });
            obj["sfxn"] = 1;
            // 重新请求
            table.reload("dbTable", {
                where: obj,
                page: {
                    curr: 1  //重新从第 1 页开始
                },
                done: function(){
                    tableDone();
                }
            });
        });

        //点击高级查询--4的倍数
        $('#seniorSearch').on('click', function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            //$(this).parent().toggleClass('bdc-button-box-four');
            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }
        });

        loadProcessDefName();

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
                }, error: function (e) {
                    response.fail(e,'');
                }
            });
        }

        /**
         * 复制反转的 坐落
         */
        $('.bdc-table-box').on('mouseenter', 'td', function () {
            if ($(this).text() && $(this).attr("data-field") == "zl") {
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>')
            }
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    if (!isNullOrEmpty($('.layui-table-tips-main .bdc-table-date').html())) {
                        $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                    }
                }, 20);
            });
        });

    });

    function checkMatch(checkData) {
        var gzlslid = checkData.processInstanceId;
        var isPp = $("."+ gzlslid).text();
        if (!isNullOrEmpty(isPp) && isPp === '可匹配') {
            match(gzlslid);
        } else if (isPp === '加载中') {
            warnMsg("数据加载中！");
        } else {
            warnMsg("此数据不可以匹配！");
        }
    }

    /**
     * 自动匹配虚拟不动产单元号
     * @param gzlslid
     */
    function match(gzlslid) {
        addModel("自动匹配中");
        $.ajax({
            url: getContextPath() + "/rest/v1.0/task/matchData?gzlslid=" + gzlslid,
            type: 'GET',
            dataType: 'json',
            async: false,
            success: function () {
                removeModal();
                layer.msg("匹配成功！");
                // 模拟查询点击事件刷新页面
                $('#dbSearch').click();
            }, error: function (e) {
                removeModal();
                response.fail(e, '');
            }
        });
    }

    /**
     * 表格加载完成后渲染
     */
    function tableDone() {
        removeModal();
        gzlslids.forEach(function (gzlslid) {
            yzQj(gzlslid);
        });
        $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

        $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
        if ($('.layui-table-body>.layui-table').height() == 0) {
            $('.layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
        }
        var reverseList = ['zl'];
        reverseTableCell(reverseList);
    }

    //渲染表格
    function renderWaitTable(url, tableId, currentId, toolbar) {
        addModel();
        var obj = {};
        obj["sfxn"] = 1;
        table.render({
            elem: tableId,
            id: currentId,
            url: url,
            toolbar: toolbar,
            title: '虚拟单元号匹配台账',
            method: 'post',
            even: true,
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            where: obj,
            limits: [10,30,50,70,90,110,130,150],
            defaultToolbar: ['filter'],
            cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
                {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
                {field: 'qlr', minWidth: 100, title: '权利人'},
                {field: 'ywr', minWidth: 100, title: '义务人'},
                {field: 'zl', minWidth: 200, title: '坐落'},
                {field: 'isxn', minWidth: 75, title: '是否虚拟', hide: true},
                {minWidth: 75, title: '可匹配', templet: '#isPpTpl'},
                {field: 'procStartUserName', minWidth: 100, title: '受理人'},
                {field: 'processDefName', title: '流程名称', minWidth: 160},
                {field: 'taskName', title: '节点名称', width: 90},
                {field: 'newStartTime', title: '开始时间', width: 100, sort: true},
                {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
                {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
                {field: 'category', title: '业务类型', width: 90, hide: true},
                {field: 'claimStatusName', title: '认领状态', width: 90, hide: true},
                {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
            ]],
            parseData: function (res) { //res 即为原始返回的数据
                if (currentId == 'dbTable') {
                    if (res.totalElements > 99) {
                        $('.bdc-list-num-tips').html('99+');
                    } else {
                        $('.bdc-list-num-tips').html(res.totalElements);
                    }

                }
                gzlslids = [];
                res.content.forEach(function (v) {
                    if (v.startTime) {
                        var newStartTime = new Date(v.startTime);
                        v.newStartTime = newStartTime.toLocaleString();
                    }
                    gzlslids.push(v.processInstanceId);
                });
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            page: true,
            done: function () {
                tableDone();
            }
        });
    }
});

// 调用权籍服务，判断是否可以匹配
function yzQj(gzlslid) {
    $.ajax({
        url: getContextPath() + "/rest/v1.0/task/sflz?gzlslid=" + gzlslid,
        type: 'GET',
        dataType: 'json',
        async: true,
        success: function (data) {
            if (data !== true) {
                $("."+ gzlslid).html('<p class="bdc-table-state-th">不可匹配</p>');
            } else {
                $("."+ gzlslid).html('<p class="bdc-table-state-jh">可匹配</p>');
            }
        }
    });
}