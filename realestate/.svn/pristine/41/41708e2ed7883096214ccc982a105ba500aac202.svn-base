layui.use(['jquery', 'table', 'element', 'form', 'laytpl'], function () {
    var $ = layui.jquery,
        table = layui.table,
        laytpl = layui.laytpl,
        form = layui.form;

    $(function () {
        var checkedData = layui.sessionData('checkedData');
        if ($.isEmptyObject(checkedData)) {
            warnMsg(" 未获取到需要转发的交接单！");
            return;
        }

        var jjdArray = new Array();
        $.each(checkedData, function (key, value) {
            jjdArray.push(value);
        });

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
            url: "/realestate-register-ui/rest/v1.0/jjd/forward/roles",
            success: function (data) {
                var tableData = [{roleDtoList: data}];
                if (data.length != 0 && !isNullOrEmpty(data[0]['id'])) {
                    $.ajax({
                        type: "GET",
                        async: false,
                        url: "/realestate-register-ui/rest/v1.0/jjd/forward/users",
                        data: {
                            roleid: 'allRoles'
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
                url: "/realestate-register-ui/rest/v1.0/jjd/forward/users",
                data: {
                    roleid: roleId
                },
                success: function (data) {
                    $select.html('<option value="">请选择</option>');
                    $select.append('<option value="allUser">全部</option>');
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

                var data = new Array();
                for (var i = 0; i < jjdArray.length; i++) {
                    var jjd = {};
                    jjd.jjdid = jjdArray[i].jjdid;
                    if (roleName == "全部"){
                        roleName = "";
                    }
                    jjd.jsks = roleName;
                    if (userid != "allUser") {
                        jjd.jsr = userName;
                        jjd.jsrid = userid;
                    }
                    jjd.jjdzt = jjdArray[i].jjdzt;
                    data.push(jjd);
                }

                addModel('转发中');
                $.ajax({
                    type: "POST",
                    traditional: true,
                    url: "/realestate-register-ui/rest/v1.0/jjd/forward/all",
                    data: JSON.stringify(data),
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