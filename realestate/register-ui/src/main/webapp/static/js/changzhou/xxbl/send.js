layui.use(['jquery', 'table', 'element', 'form', 'laytpl'], function () {
    var $ = layui.jquery,
        table = layui.table,
        laytpl = layui.laytpl,
        form = layui.form;

    $(function () {
        //获取地址栏参数
        var $paramArr = getIpHz();
        var blshid;
        var version;

        if ($paramArr['blshid']) {
            blshid = $paramArr['blshid'];
        }
        if ($paramArr['version']) {
            version = $paramArr['version'];
        }

        //获取转发类型、内容和角色
        var col = [[]],
            // 优先加载表格的边框
            col = [[
                {title: '参与角色', templet: '#roleTpl'},
                {title: '参与人', templet: '#personTpl'}
            ]];

        addModel();
        // 获取转发角色
        $.ajax({
            type: "GET",
            url: "/realestate-register-ui/rest/v1.0/blxx/blsh/forward/roles",
            success: function (data) {
                var tableData = [{roleDtoList: data}];
                if (data.length != 0 && !isNullOrEmpty(data[0]['id'])) {
                    $.ajax({
                        type: "GET",
                        async: false,
                        url: "/realestate-register-ui/rest/v1.0/blxx/blsh/forward/users",
                        data: {
                            roleid: data[0]['id']
                        },
                        success: function (data) {
                            tableData[0].personList = data;
                            renderZfTable(tableData, col);
                        }, error: function (e) {
                            response.fail(e, '');
                        }
                    });
                } else {
                    layer.msg('转发节点未配置角色，即将关闭转发窗口。', {
                        time: 2000
                    }, function () {
                        window.closeWin();
                    })
                }

            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr);
            }, complete: function () {
                removeModel();
            }
        });

        // 渲染表格
        function renderZfTable(data, col) {
            table.render({
                elem: '#checkboxTable',
                id: 'checkBoxTableId',
                cols: col,
                data: data,
                limit: 1000,
                done: function () {
                    form.render('select');
                }
            });
        }


        //监听角色选择
        form.on('select(roleFilter)', function (data) {
            renderPerson(data.value, data.othis.parents('td').next().find('select'));
        });

        //默认渲染第一个角色的，选择角色，重新渲染
        function renderPerson(roleId, $select) {
            $.ajax({
                type: "GET",
                url: "/realestate-register-ui/rest/v1.0/blxx/blsh/forward/users",
                data: {
                    roleid: roleId
                },
                success: function (data) {
                    $select.html('<option value="">请选择</option>');
                    // $select.append('<option value="allUser">全部</option>');
                    data.forEach(function (v) {
                        $select.append('<option value="' + v.id + '">' + v.alias + '</option>');
                    });
                    form.render('select');
                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);
                }
            });
        }

        //点击转发
        $('.bdc-send-btn').on('click', function () {
            var $roleSelect = $('.bdc-zf-table .layui-table-main tr:first-child td:first-child .layui-this');
            var roleName = $roleSelect.html();
            var roleId = $roleSelect.attr('lay-value');
            var $userSelect = $('.bdc-zf-table .layui-table-main tr:first-child td:last-child .layui-this');
            if ($userSelect.length == 0) {
                layer.msg('请选择参与人');
            } else {
                var userName = $userSelect.html();
                var userid = $userSelect.attr('lay-value');
                // 组织数据
                var requestData;
                // 蚌埠批量转发时会带version=bengbu
                if(isNotBlank(version) && version === 'bengbu'){
                    var blshIds = sessionStorage.getItem("blshIds");
                    var blxxIdArr = blshIds.split(",");
                    var blshList = [];
                    $.each(blxxIdArr, function (index,item){
                        var blsh = {
                            blshid : item,
                            shryxm : userName,
                            shryid : userid
                        }
                        blshList.push(blsh);
                    });
                    requestData = blshList;
                    // 设置请求地址
                    url = '/realestate-register-ui/rest/v1.0/blxx/blsh/forward/pl';
                }else{
                    var blxx = {};
                    blxx.blshid = blshid;
                    blxx.shryxm = userName;
                    blxx.shryid = userid;

                    requestData = blxx;
                    //设置请求地址
                    url = '/realestate-register-ui/rest/v1.0/blxx/blsh/forward';
                }

                addModel('转发中');
                $.ajax({
                    type: "POST",
                    traditional: true,
                    url: url,
                    data: JSON.stringify(requestData),
                    contentType: 'application/json',
                    success: function (data) {
                        layer.msg('转发成功，即将关闭当前页。', {
                            time: 1000
                        }, function () {
                            window.parent.location.reload();
                        });
                    }, error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr);
                    }, complete: function () {
                        removeModel();
                    }
                });
            }
        });

        //点击取消
        $('.bdc-cancel-btn').on('click', function () {
            closeWin();
        });
    });
});