/**
 * describe: 批量发证
 */

var reverseList = ['zl'];
var taskName = "发证";
var plfzTableId = "#plfzTable";
var plfzCurrentId = "plfzTable";
var zdList = getMulZdList("zjzl");
var signStreamData = "";
var lcPageEdition = "changzhou";
layui.use(['jquery', 'form', 'table', 'laydate','workflow'], function () {
    //获取字典
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        workflow = layui.workflow,
        formSelects = layui.formSelects;
    // 当前页数据
    var currentPageData = new Array();

    layui.sessionData('checkedPlfzDataLc', null);

    $(function () {
        //1.工作流定义名称
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/task/process/all",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            async: false,
            success: function (data) {
                $('#selectLcmc').append(new Option("请选择", ""));
                $.each(data, function (index, item) {
                    $('#selectLcmc').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                });
                layui.form.render("select");
            }, error: function (e) {
                response.fail(e,'');
            }
        });

        //2.渲染领证人证件种类
        if(zdList.zjzl){
            $('#lzrzjzl').append(new Option("请选择", ""));
            $.each(zdList.zjzl, function (index, item){
                $('#lzrzjzl').append(new Option(item.MC, item.DM));
            });
            layui.form.render("select");
        }

        //3.定义table的cols，显示默认表格
        renderTable();

        //4.查询 按钮
        $('#search').on('click', function () {
            search();
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
        table.on('toolbar(plfzFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'send':
                    send(checkStatus);
                    break;
                case 'editLzr':
                    editLzr(checkStatus);
                    break;
            }
        });

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(plfzFilter)', function (obj) {
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;

            $.each(data, function (i, v) {
                var keyT = v.processInstanceId;
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedPlfzDataLc', {
                        key: keyT, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedPlfzDataLc', {
                        key: keyT, remove: true
                    });
                }
            });
        });

        // 监听行事件
        table.on('tool(plfzFilter)', function (obj) { //bdcdyhList为table的lay-filter对应的值
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

        // 受理编号弹出层
        $('.slbh').on('click', function () {
            //大弹出层
            var index = layer.open({
                title: '请输入受理编号',
                type: 1,
                area: ['960px', '360px'],
                btn: ['确定', '取消'],
                content: $('#bdc-popup-slbh'),
                yes: function (index, layero) {
                    //提交 的回调
                    $("#slbh").val($("#slbhs").val());
                    layer.close(index);
                },
                btn2: function (index, layero) {
                    //取消 的回调
                },
                success: function () {
                    $("#slbhs").focus();
                }
            });
        });
    });

    // 加载表格
    function renderTable() {
        var cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'jfzt', minWidth: 90, templet: '#jfztTpl', title: '收费状态'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'procStartUserName', minWidth: 100, title: '受理人'},
            {field: 'processDefName', templet: '#lcmcTpl', title: '流程名称', minWidth: 160},
            {field: 'djyy', minWidth: 200, title: '登记原因'},
            {field: 'taskName', title: '节点名称', width: 90},
            {field: 'startTime', title: '开始时间', width: 100, sort: true},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'category', title: '业务类型', width: 90, hide: true},
            {field: 'claimStatusName', title: '认领状态', width: 90, hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
        ];
        var pageUrl = getContextPath() + "/rest/v1.0/task/todo";
        var whereObj = {
            taskName : taskName,
        };
        table.render({
            elem: '#plfzTable',
            title: '批量发证列表',
            url: pageUrl,
            id: "plfzTable",
            method: 'post',
            even: true,
            limits: [10, 30, 50, 70, 90, 110, 130, 150],
            defaultToolbar: ['filter'],
            toolbar: '#toolbarDemo',
            where: whereObj,
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            cols: [cols],
            page: true,
            parseData: function (res) {
                // 设置选中行
                if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedPlfzDataLc');
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
                changeTableHeight();
                reverseTableCell(reverseList);
                table.resize('plfzTable');
            }
        });
    }

    // 重新渲染表格数据
    function reloadTable(whereObj) {
        addModel();
        layui.use(['table'], function () {
            var table = layui.table;
            table.reload('plfzTable', {
                where: whereObj,
                page: {
                    curr: 1,  //重新从第 1 页开始
                    limits: [10, 50, 100]
                },
                done: function (res, curr, count) {
                    removeModal();
                    changeTableHeight();
                    reverseTableCell(reverseList);
                    table.resize('plfzTable');
                }
            });
        });
    }

    // 查询方法
    function search() {
        reloadTable({
            taskName : taskName,
            processDefName : $("#selectLcmc").val(),
            slbh : $("#slbh").val(),
        });
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

    //表格高度自适应
    function changeTableHeight() {
        if ($('.bdc-list-tab .layui-tab-content .layui-show .layui-table-main>.layui-table').height() == 0) {
            $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
        } else {
            $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height());
            $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-fixed .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height() - 17);
        }
    }

    // 办结按钮方法
    function send(checkStatus){
        var data = checkStatus.data;
        if(data.length == 0){
            warnMsg("至少选择一条数据进行办结！");
            return;
        }
        workflow.forwardPlProcess(checkStatus, getContextPath() + "/rest/v1.0/task/todo", plfzTableId, plfzCurrentId);
    }
    // 修改领证人
    function editLzr(checkStatus){
        if(checkStatus.data.length == 0){
            warnMsg("至少选择一条数据进行修改！");
            return;
        }
        var gzlslidList = [];
        $.each(checkStatus.data, function(index, value){
            gzlslidList.push(value.processInstanceId);
        });

        getQlrxx(gzlslidList[0]).done(function(qlrxx){
            layer.open({
                title: '修改领证人信息',
                type: 1,
                area: ['960px', '400px'],
                btn: ['确定', '取消'],
                content: $('#bdc-popup-large'),
                yes: function (index, layero) {
                    //提交 的回调
                    var lzrxx = getFormLzrxx();
                    if(isNotBlank(lzrxx)){
                        // 批量保存领证人签字内容
                        plSaveQzxx(gzlslidList);

                        // // 批量保存领证人信息
                        lzrxx.gzlslidList = gzlslidList;
                        plModifyLzr(lzrxx).done(function(){
                            layer.close(index);
                            //保存成功后，执行批量办结功能
                            workflow.forwardPlProcess(checkStatus, getContextPath() + "/rest/v1.0/task/todo", plfzTableId, plfzCurrentId);
                        });
                    }
                },
                btn2: function (index, layero) {},
                success:function(){
                    if(isNotBlank(qlrxx)){
                        $("#lzr").val(qlrxx.qlrmc);
                        $("#lzrzjzl").val(qlrxx.zjzl);
                        $("#lzrzjh").val(qlrxx.zjh);
                        $("#lzrdh").val(qlrxx.dh);
                        layui.form.render("select");
                    }
                }
            });
        });
    }

    // 获取权利人信息
    function getQlrxx(gzlslid){
        var deferred = $.Deferred();
        $.ajax({
            url: getContextPath()+"/rest/v1.0/yw/fzjl/getlzrxx",
            type: "GET",
            data: {gzlslid: gzlslid},
            dataType: "json",
            success: function (data) {
                deferred.resolve(data);
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr);
                deferred.reject();
            }
        });
        return deferred;
    }

    // 获取页面领证人信息
    function getFormLzrxx(){
        var obj = {
            lzr: $("#lzr").val(),
            lzrzjh: $("#lzrzjh").val(),
            lzrdh: $("#lzrdh").val(),
            lzrzjzl: $("#lzrzjzl").val()
        };
        if(isNullOrEmpty(obj.lzr)||isNullOrEmpty(obj.lzrzjh)){
            warnMsg("请输入领证人、领证人证件号");
            return;
        }
        if(isNotBlank(obj.lzrzjh) && obj.lzrzjzl == 1){
            var yzxx = verifyIdNumber(obj.lzrzjh);
            if(yzxx.isSubmit == false){
                warnMsg(yzxx.verifyMsg);
                return;
            }
        }
        if(isNotBlank(obj.lzrdh)){
            if(!validatePhone($.trim(obj.lzrdh))){
                warnMsg("请输入正确的电话号码");
                return;
            }
        }
        return obj;
    }

    // 批量修改领证人信息
    function plModifyLzr(lzrxx){
        addModel();
        var deferred = $.Deferred();
        $.ajax({
            url: getContextPath()+"/rest/v1.0/yw/fzjl/pl/lzrxx",
            type: "POST",
            async: false,
            data: JSON.stringify(lzrxx),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                removeModal();
                successMsg("修改成功")
                deferred.resolve();
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
                deferred.reject();
            }
        });
        return deferred;
    }

    // 批量保存签名信息
    function plSaveQzxx(gzlslidList){
        if(isNotBlank(signStreamData)){
            var bdcFzjlLzrQzxxDTO = {
                signStreamData: signStreamData,
                gzlslidList: gzlslidList,
            }
            $.ajax({
                url: getContextPath()+"/rest/v1.0/yw/fzjl/pl/qzxx",
                type: "POST",
                async: false,
                data: JSON.stringify(bdcFzjlLzrQzxxDTO),
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);
                }
            });
        }
    }

});

// 领证人签字
function lzrqz(){
    layer.open({
        title: '领证人签字',
        type: 2,
        area: ['700px','430px'],
        content: "/realestate-register-ui/nantong/fzjl/qzb.html",
        end: function (index, layero) {
            var signStream = layui.data('signStream');
            if(signStream && signStream.data) {
                signStreamData = signStream.data;
                $(".lzrqz").attr("src","data:image/png;base64," + signStream.data);
                $(".lzrqz").show();
            }
        }
    });
}