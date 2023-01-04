/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 初始化表格，工具栏事件
 */
$(function () {
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });
    // 获取参数
    var processInsId = $.getUrlParam('processInsId');
    // 表单ID
    var formStateId = $.getUrlParam('formStateId');
    // 任务id
    var taskId = $.getUrlParam('taskId');
    // 获取表单权限参数readOnly
    var readOnly = $.getUrlParam('readOnly');
    var processInstanceType = $.getUrlParam('processInstanceType');
    // potal首页点击的哪个列表todo:待办，list:项目列表，done:已办
    var processInstanceTypeTodo = "todo";
    var processInstanceTypeRl = "rl";
    // 控制办结按钮是否可见（默认不可见）
    var endBtnVisible = $.getUrlParam('endBtnVisible');

    /**
     * 获取字典
     */
    var zdData;
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/qlxx/zd',
        type: "GET",
        async: false,
        dataType: "json",
        timeout: 10000,
        success: function (data) {
            zdData = data;
        }
    });

    //点击高级查询
    $('#seniorSearch').on('click', function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        $(this).parent().toggleClass('bdc-button-box-four');
        $('.bdc-percentage-container').css('padding-top', $('.bdc-search-content').height() + 10);

        $('.layui-table-body').height($('.bdc-table-box').height() - 129);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 129 - 17);
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


    layui.config({
        base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
    }).extend({
        formSelects: 'formSelects-v4'
    });
    layui.use(['form', 'table', 'jquery', 'formSelects'], function () {
        var form = layui.form;
        var table = layui.table;
        var formSelects = layui.formSelects;

        // 页面加载更新领证人信息
        var allLzrUrl = "/realestate-register-ui/rest/v1.0/fzjl/lzr?gzlslid=" + processInsId;
        var lzrxxDTO = {};
        ajaxPostLzrxx(allLzrUrl, lzrxxDTO);

        // potal首页传过来的参数，取其中的processInstanceType
        // 如果是待办任务和认领列表的则执行自动更新fzr信息
        var jsonParam = {};
        if (parent.sessionStorage && parent.sessionStorage.getItem("lcArray" + taskId)) {
            jsonParam = JSON.parse(parent.sessionStorage.getItem("lcArray" + taskId));
        }
        if (jsonParam && (jsonParam.processInstanceType == processInstanceTypeTodo
            || jsonParam.processInstanceType == processInstanceTypeRl)) {
            // 自动更新发证人信息到zs表
            updateFzr();
        }
        // 设置字符过多，省略样式
        var reverseList = ['zl'];

        var zjzlArr = [];
        if (!isEmptyObject(zdData.zjzl)) {
            $.each(zdData.zjzl, function (index, item) {
                var zdParam = {};
                zdParam["name"] = item.MC;
                zdParam["value"] = item.DM;
                if (item.MC == "身份证") {
                    zdParam["selected"] = "selected";
                }
                zjzlArr.push(zdParam);
            })
        }

        //使用js渲染下拉框
        formSelects.data('select01', 'local', {
            arr: zjzlArr
        });


        /**
         * 加载Table数据列表
         */
        table.render({
            elem: '#fzjlTable',
            id: 'fzjlTable',
            toolbar: '#toolbarDemo',
            title: '发证记录',
            defaultToolbar: [],
            url: '/realestate-register-ui/rest/v1.0/zsxx/list/' + processInsId + '?resourceName=bdcFzjlList',
            // request: {
            //     limitName: 'size' //每页数据量的参数名，默认：limit
            // },
            cellMinWidth: 80,
            even: true,
            limits: commonLimits,// 每页数据量
            cols: [[
                {checkbox: true, fixed: true},
                {field: 'bdcqzh', title: '不动产权证号', minWidth: 270},
                {field: 'qzysxlh', title: '权证印刷序列号', minWidth: 130},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
                {field: 'zl', title: '坐落', minWidth: 250},
                {field: 'qlr', title: '权利人'},
                {field: 'mj', title: '面积'},
                {field: 'zslxmc', title: '证书类型', minWidth: 85},
                {field: 'zsid', title: '证书ID', hide: true},
                {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 65}
            ]],
            text: {
                none: '未查询到数据'
            },
            autoSort: false,
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
                var searchHeight = 131;
                setTableHeight(searchHeight);

                reverseTableCell(reverseList);

                // 获取表单控制权限
                if (readOnly || !isNullOrEmpty(formStateId)) {
                    getStateById(readOnly, formStateId, "bdcFzjlList");
                }
            }
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
        //根据选择的数据进行操作时，如果isSelectAll为true，操作包含本页的全部数据
        var isSelectAll = false;
        //监听全选复选鼠标覆盖
        $('.bdc-table-box').on('mouseenter', '.layui-table-header th .laytable-cell-checkbox', function () {
            $('.bdc-select-all-box').removeClass('bdc-hide');
        });
        $('.bdc-select-all-box p').on('click', function (e) {
            e.stopPropagation();
            $('.bdc-select-all-box').addClass('bdc-hide');
            $('.layui-table-header th .layui-form-checkbox').click();

            var checkStatus = table.checkStatus('bdcZsTable');
            if (checkStatus.isAll) {
                $('.layui-table-header th .layui-form-checkbox').addClass('layui-form-checked');
                if ($(this).hasClass('bdc-select-all')) {
                    isSelectAll = true;
                } else {
                    isSelectAll = false;
                }
            } else {
                if ($(this).hasClass('bdc-select-all')) {
                    isSelectAll = false;
                }
            }
        });
        $('.bdc-select-all-box').on('mouseleave', function () {
            $('.bdc-select-all-box').addClass('bdc-hide');
        });
        $('body').on('click', function () {
            $('.bdc-select-all-box').addClass('bdc-hide');
        });


        // 监听按钮事件
        form.on("submit(search)", function (data) {
            tableReload("fzjlTable", data.field);
            return false;
        });

        // 监听头工具栏事件
        //监听事件
        table.on('toolbar(dataTable)', function (obj) {
            var selectZsid = [];
            var checkStatus = table.checkStatus(obj.config.id);
            var checkData = checkStatus.data;

            if (isEmptyObject(checkData)) {
                warnMsg("当前没有选中发证记录，默认选择当前页！");
                // 默认全选当前页
                $('.layui-table-header th .layui-form-checkbox').click();
                $('.layui-table-header th .layui-form-checkbox').addClass('layui-form-checked');

                checkStatus = table.checkStatus('fzjlTable');
                checkData = checkStatus.data;
            }
            for (var i = 0; i < checkData.length; i++) {
                var zsid = checkData[i].zsid;
                selectZsid.push(zsid);
            }

            switch (obj.event) {
                case 'plPrint':
                    plPrint();
                    break;
                case 'plxg':
                    plxg(selectZsid, isSelectAll);
                    break;
            }
        });

        //监听行工具事件
        table.on('tool(dataTable)', function (obj) {
            var data = obj.data;
            layer.open({
                title: ['', 'font-size:16px;font-weight: 700;'],
                type: 2,
                area: ['100%', '100%'],
                resize: true,
                content: '/realestate-register-ui/view/fzjl/fzjl.html?zsid=' + data.zsid + '&taskId=' + taskId + '&processInsId=' + processInsId + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&processInstanceType=" + processInstanceType
            });

        });
    });

    // 存储打印配置的sessionKey
    // 当前页的打印类型
    var dylxArr = [fzjlList];
    var sessionKey = "fzjlList";
    setDypzSession(dylxArr, sessionKey);

    /**
     * 批量打印
     */
    function plPrint() {
        var modelUrl = fzjlModelUrl;
        // 默认一个xmid（批量打印不需要xmid的参数）
        var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/fzjl/xml?dylx=" + fzjlList + "&gzlslid=" + processInsId;
        printChoice(fzjlList, "realestate-register-ui", dataUrl, modelUrl, false, sessionKey);
        //print(modelUrl, dataUrl, false);
    }

    /**
     * 批量修改
     * @param obj
     */
    function plxg(selectZsid, isSelectAll) {
        //小弹出层
        layer.open({
            title: '领证人信息修改',
            type: 1,
            area: ['450px', '300px'],
            btn: ['保存', '重置'],
            content: $('#bdc-popup-small')
            , yes: function (index, layero) {
                //提交 的回调
                updateLzrxx(selectZsid, isSelectAll);
                // 更新发证人信息
                updateFzr();
            }
            , btn2: function (index, layero) {
                //取消 的回调
                $("#lzrForm")[0].reset();
                return false;
            }
            , cancel: function () {
                //右上角关闭回调
                $("#lzrForm")[0].reset();
                //return false 开启该代码可禁止点击该按钮关闭
            }
            , success: function () {

            }
        });
    }

    /**
     * 批量修改领证人信息
     */
    function updateLzrxx(selectZsid, isSelectAll) {
        var bdcFzjlZsDTO = {};
        var data = $('#lzrForm').serializeArray();
        var isSubmit = true;
        var msg = "必填项不能为空";
        var lzrzjzl;
        $.each(data, function (index, item) {
            if (item.name == "lzr") {
                if (isNullOrEmpty(item.value)) {
                    $("input[name='lzr']").addClass('layui-form-danger');
                    isSubmit = false;
                }
                bdcFzjlZsDTO["lzr"] = item.value;
            }
            if (item.name == "lzrzjh") {
                if (isNullOrEmpty(item.value)) {
                    $("input[name='lzrzjh']").addClass('layui-form-danger');
                    isSubmit = false;
                }
                if (lzrzjzl == '1') {
                    var yzxx = verifyIdNumber(item.value, item);
                    if (!isEmptyObject(yzxx)) {
                        isSubmit = false;
                        msg = yzxx.verifyMsg;
                    }
                }
                bdcFzjlZsDTO["lzrzjh"] = item.value;
            }
            if (item.name == "lzrdh") {
                if (isNullOrEmpty(item.value)) {
                    $("input[name='lzrdh']").addClass('layui-form-danger');
                    isSubmit = false;
                }
                if (!validatePhone(item.value)) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    msg = "联系电话格式不正确";
                }

                bdcFzjlZsDTO["lzrdh"] = item.value;
            }
            if (item.name == "lzrzjzl") {
                if (isNullOrEmpty(item.value)) {
                    $("select[name='lzrzjzl']").addClass('layui-form-danger');
                    isSubmit = false;
                }
                bdcFzjlZsDTO["lzrzjzl"] = item.value;
                lzrzjzl = item.value;
            }
        });
        if (!isSubmit) {
            warnMsg(msg);
            isSubmit = true;
            return false;
        }

        // 全选传gzlslid，部分数据选择传选择的zsid
        var url = "/realestate-register-ui/rest/v1.0/fzjl/lzr";
        if (isSelectAll) {
            url = "/realestate-register-ui/rest/v1.0/fzjl/lzr?gzlslid=" + processInsId;
        } else {
            bdcFzjlZsDTO["zsidList"] = selectZsid;
        }
        ajaxPostLzrxx(url, bdcFzjlZsDTO);
        // 禁止提交后刷新
        return false;
    }

    function ajaxPostLzrxx(url, bdcFzjlZsDTO) {
        // loading加载
        addModel();
        $.ajax({
            url: url,
            type: "POST",
            data: JSON.stringify(bdcFzjlZsDTO),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data && data.code && data.code == 1) {
                    warnMsg(data.msg);
                } else if (data && data > 0) {
                    successMsg("领证人信息保存成功！");
                } else if (data < 0) {
                    warnMsg("保存领证人信息失败，请重试！");
                }
            }
        });

        removeModel();
        // 禁止提交后刷新
        return false;
    }

    /**
     * 自动更新发证人
     */
    function updateFzr() {
        var url = "/realestate-register-ui/rest/v1.0/fzjl/fzr?gzlslid=" + processInsId;
        $.ajax({
            url: url,
            contentType: "application/json;charset=utf-8",
            type: "GET",
            async: false,
            dataType: "json",
            success: function (data) {
            }
        });
    }

});

/**
 * 身份证读卡器设置
 * @param element
 */
function lzrReadIdCard(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            var pName = objTest.Name;
            var pCardNo = objTest.ID;

            $("#lzr").val(trimStr(pName));
            $("#lzrzjh").val(trimStr(pCardNo));
        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}

