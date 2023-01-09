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
        var processInstanceId = $paramArr['processInstanceId'];
        var processKey = $paramArr['processKey'];
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
                        } else if (roleDtoList != null &&roleDtoList.length != 0 && !isNullOrEmpty(roleDtoList[0]['id'])) {
                            var queryurl;
                            if (!isNullOrEmpty(forwardTask[i].forwardTaskDto.selectUserNames)) {
                                defaultUser = forwardTask[i].forwardTaskDto.selectUserNames;
                                queryurl = getContextPath() + "/rest/v1.0/workflow/process-instances/forward/user?username=" + defaultUser;
                            } else {
                                var taskId = forwardTask[i].forwardTaskDto.taskId;
                                var activityId = forwardTask[i].forwardTaskDto.activityId;
                                queryurl = getContextPath() + "/rest/v1.0/workflow/process-instances/forward/users?roleId=" + forwardTask[i].roleDtoList[0]['id']+"&taskId="+taskId+"&activityId="+activityId;;
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
                            // 选择多个节点并行批量转发 modified by zhuyong
                            var forwardDatas = new Array();
                            for (var i = 0; i < checkStatus.data.length; i++) {
                                var forwardData = {"taskId": taskId, "opinion": opinion};
                                forwardData.activityId = checkStatus.data[i].forwardTaskDto.activityId;

                                var roleOption = $('.layui-table-main tr[data-index=' + i + '] td[data-field=2] .layui-form-select dd.layui-this');
                                if(roleOption && roleOption[0]) {
                                    forwardData.selectRoleIds = roleOption[0].attributes[0].value;
                                }

                                var userOption = $('.layui-table-main tr[data-index=' + i + '] td[data-field=3] .layui-form-select dd.layui-this');
                                if(userOption && userOption[0]) {
                                    forwardData.selectUserNames = userOption[0].attributes[0].value;
                                }

                                forwardDatas.push(forwardData);
                            }
                            addModel('转发中');
                            $.ajax({
                                type: "POST",
                                traditional: true,
                                url: getContextPath() + "/rest/v1.0/workflow/process-instances/forward/parallel",
                                contentType: 'application/json',
                                data: JSON.stringify(forwardDatas),
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
                                var zfhyzData =selectData[0];
                                zfhyzData.selectRoleIds =selectRoleIds;
                                zfhyzData.selectUserNames = selectUserNames;
                                zfhyzData.opinion = opinion;
                                zfhyz(zfhyzData,function () {
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

        /**
         * 转发后验证
         */
        function zfhyz(zfhyzData,zfcallback){
            addModel('加载中');
            $.ajax({
                type: "POST",
                traditional: true,
                url: getContextPath() + "/rest/v1.0/workflow/process-instances/forward/zfhyz",
                data: {
                    "activityId": zfhyzData.forwardTaskDto.activityId,
                    "activityName": zfhyzData.activityName,
                    "procDefId": processKey,
                    "roleIds": zfhyzData.roleIds,
                    "usernames": zfhyzData.usernames,
                    "taskId": taskId,
                    "selectRoleIds": zfhyzData.selectRoleIds,
                    "selectUserNames": zfhyzData.selectUserNames,
                    "opinion": zfhyzData.opinion,
                    "gzlslid":processInstanceId
                },
                success: function (data) {
                    console.info(data);
                    if (data.bdcGzyzVOS != null) {
                        $('.bdc-send-btn')[0].removeAttribute("disabled", "off");
                        $('.bdc-send-btn')[0].classList.remove("layui-btn-disabled");
                        var tsxxHtml = loadTsxx(data.bdcGzyzVOS);
                        rightShowZfhWarn(tsxxHtml,processInstanceId,zfcallback);
                        return;
                    }else {
                        zfcallback();
                    }

                }, error: function (e) {
                    response.fail(e, '');
                }, complete: function () {
                    removeModal();
                }
            });

        }

        function loadTsxx(data) {
            var confirmInfo = '';
            var alertInfo = '';
            var showIgnoreAll=true;
            $.each(data, function (index, item) {
                if (item.yzlx === 1) {
                    confirmInfo += '<p><img src="../static/lib/bdcui/images/warn.png" alt="">' + item.tsxx + '<a href="javascript:;" class="confirmRemove">忽略</a></p>';
                } else if (item.yzlx === 3 ) {
                    if(showIgnoreAll){
                        showIgnoreAll=false;
                    }
                    // 添加 a 标签，防止 tsxx 为空时，出现压盖现象
                    alertInfo += '<p><img src="../static/lib/bdcui/images/error.png" alt="">' + item.tsxx + '<a href="javascript:;" ></a></p>';
                }else if (item.yzlx ===4 ){ // 规则例外操作
                    if(showIgnoreAll){
                        showIgnoreAll=false;
                    }
                    var responseData = JSON.parse(item.responseData);
                    var xmidArray = [];

                    if(responseData){
                        // 兼容抵押和查封的 转发例外
                        for (var key in responseData){
                            var valueItem = responseData[key];
                            $.each(valueItem,function(index, value){
                                if(value.XMID && $.inArray(value.XMID,xmidArray) <0){
                                    xmidArray.push(value.XMID);
                                }
                            });
                        }

                        // 添加 a 标签，防止 tsxx 为空时，出现压盖现象
                        var requestParam =JSON.parse(item.requestParam);
                        var xmids = "";
                        if(xmidArray.length>0){
                            xmids = xmidArray.join(",");
                        }
                        alertInfo += '<p><img src="../static/lib/bdcui/images/error.png" alt="">' + item.tsxx
                            + '<a href="javascript:;"  data-gzid="'+item.gzid +'" data-gzmc="'+item.gzmc+'" data-bdcdyh="'+requestParam.bdcdyh+'"'
                            + ' data-xmid="'+xmids+'"  data-slbh="'+requestParam.slbh+'" class="lwRemove">例外</a></p>';
                    }
                }
            });
            var tsxxHtml = '';
            if(showIgnoreAll){
                tsxxHtml +='<a class="layui-btn layui-btn-sm bdc-ignore-btn" id="ignoreAll"">忽略全部</a>';
            }
            tsxxHtml += '<div class="bdc-right-tips-box bdc-zfhyz-tips" id="bottomTips">';
            tsxxHtml += alertInfo;
            tsxxHtml += confirmInfo;
            tsxxHtml += '</div>';
            return tsxxHtml;
        }

        var warnLayer;

        function rightShowZfhWarn(tsxxHtml,processInstanceId,zfcallback) {
            warnLayer = layer.open({
                type: 1,
                title: '提示信息',
                anim: -1,
                skin: 'bdc-tips-right bdc-error-layer',
                shade: [0],
                area: ['600px'],
                offset: 'r',
                content: tsxxHtml,
                time: 5000000, //2秒后自动关闭
                success: function () {

                    var pSize=$(".bdc-zfhyz-tips > p").size();
                    var confirmSize=$(".confirmRemove").size();
                    var alertSize=pSize-confirmSize;
                    $('.bdc-tips-right .bdc-right-tips-box .layui-icon-close').on('click', function () {
                        layer.close(warnLayer);
                    });
                    $('.confirmRemove').click(function () {
                        $(this).parent().remove();
                        //在没有警告提示下创建
                        if($(".bdc-zfhyz-tips > p").size() == 0){
                            //点击忽略时增加日志记录，同步处理
                            var data = this.parentElement.innerText.replace(/忽略/g, "").replace(/查看/g, "");
                            addRemoveLog(data,processInstanceId);
                            addModel();
                            zfcallback();
                            // 避免转发页面不展示将关闭操作放到后面执行
                            layer.close(warnLayer);
                        }
                    });

                    $('#ignoreAll').click(function(){
                        if(alertSize>0){
                            layer.msg("警告信息不能忽略！");
                            return false;
                        }
                        //点击忽略时增加日志记录，同步处理
                        var data = this.nextElementSibling.innerText.replace(/忽略/g, "").replace(/查看/g, "");
                        addRemoveLog(data,processInstanceId);
                        addModel();
                        zfcallback();
                        // 避免转发页面不展示将关闭操作放到后面执行
                        layer.close(warnLayer);
                    });

                    // 例外操作
                    $('.lwRemove').click(function () {
                        // 增加例外日志内容
                        addModel();
                        var logData = this.parentElement.innerText.replace(/例外/g, "").replace(/查看/g, "");
                        addLwLog(logData);
                        var xmids = ($(this).data("xmid")+"").split(",");
                        var data = { gzid : $(this).data("gzid") , gzmc :  $(this).data("gzmc"), slbh:  $(this).data("slbh"),
                            bdcdyh : $(this).data("bdcdyh")};
                        $.each(xmids,function(index, value){
                            data.xmid = value;
                            addShxx(data);
                        });
                        removeModal();
                        // 页面提示框中内容只有当前例外提示时，直接删除当前提示信息内容，并关闭提示窗体。
                        // 当存在其他提示信息时，隐藏例外按钮。
                        if($(".bdc-zfhyz-tips > p").size() == 1){
                            $(this).parent().remove();
                            layer.close(warnLayer);
                        }else{
                            $(this).hide();
                        }
                        layer.msg("已添加例外审核，请例外审核后转发。");
                    });

                    setTimeout(function () {
                        $('.bdc-error-layer').css('opacity', 1)
                    }, 500);
                }
            });
        }

        //忽略增加日志记录
        function addRemoveLog(data,gzlslid) {
            getReturnData("/rest/v1.0/check/addIgnoreLog", JSON.stringify({data: data,gzlslid:gzlslid}), "POST", function () {
            }, function () {
            })
        }

        //例外增加日志记录
        function addLwLog(logData) {
            getReturnData("/rest/v1.0/check/addLwLog", {data: logData}, "POST", function () {
            }, function () {
            });
        }
        // 添加例外验证审核信息
        function addShxx(data) {
            console.log(data);
            var param = {gzid: data.gzid, gzmc: data.gzmc, bdcdyh: data.bdcdyh};
            $.ajax({
                url: getContextPath() + '/rest/v1.0/check/addShxxData',
                type: 'POST',
                dataType: 'json',
                async: false,
                data: {data: JSON.stringify(param), slbh: data.slbh, xmid: data.xmid},
                success: function (data) {
                }
            });
        }
    });
});



/**
 * 设置下拉框不可编辑的样式
 */
function resetSelectDisabledCss() {
    //select框禁用修改下拉图标(动态渲染的要在渲染完成之后执行以下两行js代码)
    $('.layui-select-disabled i').addClass("bdc-hide");
    $('.layui-select-disabled .layui-disabled').attr('disabled', 'true');
}