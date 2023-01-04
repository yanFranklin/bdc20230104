layui.use(['jquery', 'table', 'element', 'form', 'laytpl', 'response'], function () {
    var $ = layui.jquery,
        table = layui.table,
        laytpl = layui.laytpl,
        response = layui.response,
        form = layui.form;

    $(function () {
        $(document).on('click', '.layui-icon-close', function () {
            $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
            $(this).hide();
            form.render("select");
            resetSelectDisabledCss();
        });
        //监听select选择
        form.on('select', function (data) {
            if (isNotBlank(data.value) && $(this).parents(".xzq-sele").find("option:contains('请选择')").length > 0) {
                $(this).parents('.layui-form-select').find('.layui-icon-close').show();
            }
        });

        // 是否存在默认角色
        var defaultUser = "";
        //获取地址栏参数
        var $paramArr = getIpHz();
        var taskId;
        var isIndex;
        if ($paramArr['name']) {
            taskId = $paramArr['name'];
        } else if ($paramArr['taskId']) {
            taskId = $paramArr['taskId'];
        }
        var isEnd = $paramArr['isEnd'];
        var isIndex = $paramArr['isIndex'];
        var currentId = $paramArr['currentId'];
        var sameNodes = $paramArr['sameNodes'];
        var plzfkey = $paramArr['plzfkey'];
        if (!isNullOrEmpty(plzfkey)) {
            // 获取转发数据，清除 session
            var plzfList = JSON.parse(sessionStorage.getItem(plzfkey));
            sessionStorage.removeItem(plzfkey);
        }
        var isSelectAll = false;
        var forwardTask = [];
        // 优先加载表格的边框
        var col = [[
            {type: 'radio', width: 50, fixed: 'left'},
            {field: 'activityName', title: '转发活动'},
            {title: '参与角色', templet: '#roleTpl'},
            {title: '参与人', templet: '#personTpl'}
        ]];
        // 参与人无全部
        var defaultPersonCol = [[
            {type: 'radio', width: 50, fixed: 'left'},
            {field: 'activityName', title: '转发活动'},
            {title: '参与角色', templet: '#roleTpl'},
            {title: '参与人', templet: '#personDefaultTpl'}
        ]];
        addModel();
        // 获取转发数据
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/forward/taskAndRole",
            data: {
                taskId: taskId,
            },
            success: function (data) {
                // 判断结点类型是否并行
                if (data.nodeType == 'ParallelGateway') {
                    isSelectAll = true;
                    //全选
                    col = [[
                        {type: 'checkbox', width: 50, fixed: 'left', LAY_CHECKED: true},
                        {field: 'activityName', title: '转发活动'},
                        {title: '参与角色', templet: '#roleTpl'},
                        {title: '参与人', templet: '#personTpl'}
                    ]]
                } else {
                    isSelectAll = false;
                    col = [[
                        {type: 'radio', width: 50, fixed: 'left'},
                        {field: 'activityName', title: '转发活动'},
                        {title: '参与角色', templet: '#roleTpl'},
                        {title: '参与人', templet: '#personTpl'}
                    ]]
                }
                forwardTask = data.forWardTaskVOList;
                if (forwardTask.length != 0) {
                    for (var i = 0, len = forwardTask.length; i < len; i++) {
                        var forwardDto = forwardTask[i].forwardTaskDto;
                        forwardTask[i].activityName = forwardDto.activityName;
                        // 防止自动派件影响其他版本
                        forwardTask[i].isLock = false;
                        if (i == 0) {
                            forwardTask[i].LAY_CHECKED = true;
                            // 自动派件
                            if (forwardDto.dispatchEnable) {
                                // 判断当前用户是否是管理员
                                $.ajax({
                                    type: "GET",
                                    url: getContextPath() + "/rest/v1.0/user/info",
                                    data: {},
                                    async: false,
                                    success: function (data) {
                                        // 非管理员禁止修改
                                        if (data.admin != 1) {
                                            forwardTask[i].isLock = true;
                                            // 非管理员退回件转发按钮禁止处理
                                            if (forwardDto.defaultUserEnabled == 0 && forwardDto.backTask) {
                                                $('.bdc-send-btn').addClass('islock');
                                            }
                                        }
                                    }, error: function (e) {
                                        response.fail(e, '');
                                    }
                                });
                            }
                        }
                        var roleDtoList = forwardTask[i].roleDtoList;
                        if (forwardDto.dispatchEnable) {
                            renderZfTable(forwardTask, defaultPersonCol);
                        } else if (roleDtoList.length != 0 && !isNullOrEmpty(roleDtoList[0]['id'])) {
                            var queryurl;
                            if (!isNullOrEmpty(forwardTask[i].forwardTaskDto.selectUserNames)) {
                                defaultUser = forwardTask[i].forwardTaskDto.selectUserNames;
                                queryurl = getContextPath() + "/rest/v1.0/workflow/process-instances/forward/user?username=" + defaultUser;
                            } else {
                                var taskId = forwardTask[i].forwardTaskDto.taskId;
                                var activityId = forwardTask[i].forwardTaskDto.activityId;
                                queryurl = getContextPath() + "/rest/v1.0/workflow/process-instances/forward/users?roleId=" + forwardTask[i].roleDtoList[0]['id']+"&taskId="+taskId+"&activityId="+activityId;
                            }
                            $.ajax({
                                type: "GET",
                                async: false,
                                url: queryurl,
                                success: function (data) {
                                    forwardTask[i].personList = data;
                                    if (isNullOrEmpty(defaultUser)) {
                                        renderZfTable(forwardTask, col);
                                    } else {
                                        renderZfTable(forwardTask, defaultPersonCol);
                                    }
                                }, error: function (e) {
                                    response.fail(e, '');
                                }
                            });
                        } else {
                            layer.msg('转发节点未配置角色，即将关闭转发窗口。');
                            setTimeout(function () {
                                window.closeWin();
                            }, 1000);
                        }
                    }
                }
                else
                {
                    layer.msg('未配置转发节点，即将关闭转发窗口。');
                    setTimeout(function () {
                        window.closeWin();
                    }, 1000);
                }
            }
            ,
            error: function (e) {
                response.fail(e, '');
            }
            ,
            complete:function () {
                removeModal();
            }
        })
        ;

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
            if (isNullOrEmpty(defaultUser)) {
                renderPerson(data.value, data.othis.parents('td').next().find('select'));
            }
        });

//默认渲染第一个角色的，选择角色，重新渲染
        function renderPerson(roleId, $select) {
            var taskId = $select.parents("tr").find('input[id="taskId"]').val();
            var activityId = $select.parents("tr").find('input[id="activityId"]').val();
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/workflow/process-instances/forward/users",
                data: {
                    roleId: roleId,
                    taskId:taskId,
                    activityId:activityId
                },
                success: function (data) {
                    $select.html('<option value="">请选择</option>');
                    $select.append('<option value="allUser" selected>全部</option>');
                    data.forEach(function (v) {
                        $select.append('<option value="' + v.username + '">' + v.alias + '</option>');
                    });
                    form.render('select');
                }, error: function (e) {
                    response.fail(e, '');
                }
            });
        }

//点击转发
        $('.bdc-send-btn').on('click', function () {
            if ($(this).hasClass('islock')) {
                layer.msg('当前转发对象请假中，请联系管理员转发');
            } else {
                // 避免重复点击
                var sendBtn = $('.bdc-send-btn');
                sendBtn[0].setAttribute("disabled", "off");
                sendBtn[0].classList.add("layui-btn-disabled");
                $("a[name='bdc-sign-name']").click();
                var checkStatus = table.checkStatus('checkBoxTableId'); //idTest 即为基础参数 id 对应的值
                var selectData = checkStatus.data;
                var opinion = $('.bdc-opinion').val();
                if (isSelectAll) {
                    if (checkStatus.isAll) {
                        var $selectTr = $('.layui-table-main tr');
                        if ($selectTr.find('td:last-child .layui-anim-upbit .layui-this').length == checkStatus.data.length) {
                            var selectRoleIds, selectUserNames;
                            for (var i = 0; i < checkStatus.data.length; i++) {
                                var selectRoleId = $selectTr.find('td:nth-child(3) select option:selected').val();
                                var selectUserName = $selectTr.find('td:last-child select option:selected').val();
                                if (i = checkStatus.data.length - 1) {
                                    selectRoleIds = selectRoleId;
                                    selectUserNames = selectUserName;
                                } else {
                                    selectRoleIds = selectRoleId + ",";
                                    selectUserNames = selectUserName + ",";
                                }
                            }
                            addModel('转发中');
                            $.ajax({
                                type: "POST",
                                traditional: true,
                                url: getContextPath() + "/rest/v1.0/workflow/process-instances/forward",
                                data: {
                                    "taskId": taskId,
                                    "selectRoleIds": selectRoleIds,
                                    "selectUserNames": selectUserNames,
                                    "opinion": opinion
                                },
                                success: function (data) {
                                    layer.msg('转发成功，即将关闭当前页。');
                                    setTimeout(function () {
                                        if (isIndex == 'true') {
                                            closeWin();
                                            window.parent.renderTable('', '', currentId);
                                        } else {
                                            window.renderTable('', '', currentId);
                                            window.parent.close();
                                        }
                                    }, 1000);
                                }, error: function (e) {
                                    response.fail(e, '');
                                }, complete: function () {
                                    removeModal();
                                }
                            });
                        } else {
                            layer.msg('请选择参与人');
                        }
                    } else {
                        layer.msg('请选择全部数据');
                    }
                } else {
                    //单选
                    if (checkStatus.data.length == 1) {
                        var trIndex = $('.layui-form-radioed').parents('tr').data('index') + 1;
                        var $selectTr = $('.layui-table-main tr:nth-child(' + trIndex + ')');
                        var selectUserNames;
                        if ($selectTr.find('td:last-child select').length > 0) {
                            selectUserNames = $selectTr.find('td:last-child select option:selected').val();
                        } else {
                            selectUserNames = $selectTr.find('td:last-child input').val();
                        }
                        if (isNullOrEmpty(selectUserNames)) {
                            layer.msg('请选择参与人');
                        } else {
                            var selectRoleIds;
                            if ($selectTr.find('td:last-child select').length > 0) {
                                selectRoleIds = $selectTr.find('td:nth-child(3) select option:selected').val();
                            } else {
                                selectRoleIds = $selectTr.find('td:nth-child(3) input').val();
                            }
                            if (sameNodes) {
                                var workflowPlList = [];
                                $.each(plzfList, function (key, item) {
                                    item.activityId = selectData[0].forwardTaskDto.activityId;
                                    item.selectRoleIds = selectRoleIds;
                                    item.selectUserNames = selectUserNames;
                                    item.reason = opinion;
                                    workflowPlList.push(item);
                                });
                                sessionStorage.setItem(plzfkey, JSON.stringify(workflowPlList));
                                closeWin();
                            } else {
                                addModel('转发中');
                                $.ajax({
                                    type: "POST",
                                    traditional: true,
                                    url: getContextPath() + "/rest/v1.0/workflow/process-instances/forward",
                                    data: {
                                        "activityId": selectData[0].forwardTaskDto.activityId,
                                        "activityName": selectData[0].activityName,
                                        "procDefId": selectData[0].procDefId,
                                        "roleIds": selectData[0].roleIds,
                                        "usernames": selectData[0].usernames,
                                        "taskId": taskId,
                                        "selectRoleIds": selectRoleIds,
                                        "selectUserNames": selectUserNames,
                                        "opinion": opinion
                                    },
                                    success: function (data) {
                                        layer.msg('转发成功，即将关闭当前页。');
                                        setTimeout(function () {
                                            //首页的
                                            if (isIndex == 'true') {
                                                closeWin();
                                                window.parent.renderTable('', '', currentId);
                                            } else {
                                                window.parent.close();
                                            }
                                        }, 1000);
                                    }, error: function (e) {
                                        response.fail(e, '');
                                    }, complete: function () {
                                        removeModal();
                                    }
                                });
                            }
                        }
                    } else {
                        layer.msg('请选择一条数据');
                    }
                }
            }
        });

//点击取消
        $('.bdc-cancel-btn').on('click', function () {
            closeWin();
        });

        form.on('select(cycxyyFilter)', function (data) {
            console.info(data.value);
            var bdcOpinion = $(".bdc-opinion").val() + data.value;
            $(".bdc-opinion").val(bdcOpinion);
        });
    })
    ;
})
;

/**
 * 设置下拉框不可编辑的样式
 */
function resetSelectDisabledCss() {
    //select框禁用修改下拉图标(动态渲染的要在渲染完成之后执行以下两行js代码)
    $('.layui-select-disabled i').addClass("bdc-hide");
    $('.layui-select-disabled .layui-disabled').attr('disabled', 'true');
}