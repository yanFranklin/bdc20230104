/**
 * Created by Administrator on 2019/3/2.
 */
layui.use(['jquery','table','element','form','laytpl','response'], function() {
    var $ = layui.jquery,
        table = layui.table,
        laytpl = layui.laytpl,
        response = layui.response,
        form = layui.form;

    $(function () {
        //获取地址栏参数
        var $paramArr =getIpHz();
        var taskId;
        var isIndex;
        if ($paramArr['name']) {
            taskId = $paramArr['name'];
        } else if ($paramArr['taskId']) {
            taskId = $paramArr['taskId'];
        }
        var isEnd = $paramArr['isEnd'];
        var isIndex= $paramArr['isIndex'];
        var currentId= $paramArr['currentId'];
        //获取转发类型、内容和角色
        var col = [[]],
            isSelectAll = false;
        var forwardTask = [];
        // 优先加载表格的边框
        col = [[
            {type: 'radio', width: 50, fixed: 'left'},
            {field: 'activityName', title: '转发活动'},
            {title: '参与角色', templet: '#roleTpl'},
            {title: '参与人', templet: '#personTpl'}
        ]];
        addModel();
        // 获取转发数据
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/forward/taskAndRole",
            data: {
                taskId: taskId
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
                    if (forwardTask.length == 1 && forwardTask[0].forwardTaskDto.activityId == 'endEvent') {
                        layer.confirm('您确定要办结吗？', {
                            title: '提示',
                            btn: ['确定', '取消'] //按钮
                        }, function (index) {
                            layer.close(index);
                            addModel();
                            $.ajax({
                                type: "POST",
                                traditional: true,
                                url: getContextPath() + "/rest/v1.0/workflow/process-instances/forward",
                                data: {
                                    "activityId": 'endEvent',
                                    "taskId": taskId,
                                },
                                async: false,
                                success: function () {
                                    removeModal();
                                    if (isIndex == 'true') {
                                        layer.msg("办结成功！");
                                        closeWin();
                                        window.parent.renderTable('', '', currentId);
                                    } else {
                                        layer.msg('办结成功，即将关闭当前页。');
                                        window.parent.close();
                                        renderTable('', '', currentId);
                                    }
                                }, error: function (e) {
                                    response.fail(e, '')
                                }, complete: function () {
                                    removeModal();
                                }
                            });
                        }, function () {
                            closeWin();
                        });
                    } else {
                        for (var i = 0, len = forwardTask.length; i < len; i++) {
                            var forwardDto = forwardTask[i].forwardTaskDto;
                            forwardTask[i].activityName = forwardDto.activityName;
                            if (i == 0) {
                                forwardTask[i].LAY_CHECKED = true;
                            }
                            var roleDtoList = forwardTask[i].roleDtoList;
                            if (roleDtoList.length != 0 && !isNullOrEmpty(roleDtoList[0]['id'])) {
                                renderZfTable(forwardTask, col);
                            } else {
                                layer.msg('转发节点未配置角色，即将关闭转发窗口。');
                                setTimeout(function () {
                                    window.closeWin();
                                }, 1000);
                            }
                        }
                    }
                } else {
                    layer.msg('未配置转发节点，即将关闭转发窗口。');
                    setTimeout(function(){ window.closeWin(); }, 1000);
                }
            },error: function(e){
                response.fail(e,'');
            },complete:function(){
                removeModal();
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
        form.on('select(roleFilter)', function(data){
            renderPerson(data.value,data.othis.parents('td').next().find('select'));
        });

        //默认渲染第一个角色的，选择角色，重新渲染
        function renderPerson(roleId,$select) {
            if (roleId == "allRole") {
                //选择的角色为全部，直接渲染
                $select.html('<option value="">请选择</option>');
                $select.append('<option value="allUser" selected>全部</option>');
                form.render('select');
            } else {
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
                            $select.append('<option value="'+ v.username +'">'+ v.alias +'</option>');
                        });
                        form.render('select');
                    },error: function(e){
                        response.fail(e,'');
                    }
                });
            }

        }

        //点击转发
        $('.bdc-send-btn').on('click',function () {
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
                                    if(isIndex=='true'){
                                        closeWin();
                                        window.parent.renderTable('','',currentId);
                                    }else{
                                        window.renderTable('','',currentId);
                                        window.parent.close();
                                    } }, 1000);
                            },error: function(e){
                                response.fail(e,'');
                            },complete:function(){
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
                    var selectUserNames = $selectTr.find('td:last-child select option:selected').val();
                    if(isNullOrEmpty(selectUserNames)){
                        layer.msg('请选择参与人');
                    }else{
                        var selectRoleIds = $selectTr.find('td:nth-child(3) select option:selected').val();
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
                                    if(isIndex=='true'){
                                        closeWin();
                                        window.parent.renderTable('','',currentId);
                                    }else{
                                        window.parent.close();
                                    } }, 1000);
                            },error: function(e){
                                response.fail(e,'');
                            },complete:function(){
                                removeModal();
                            }
                        });
                    }
                } else {
                    layer.msg('请选择一条数据');
                }
            }
        });

        //点击取消
        $('.bdc-cancel-btn').on('click', function () {
            closeWin();
        });
    });
});