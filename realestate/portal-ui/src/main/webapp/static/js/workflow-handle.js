layui.define(['jquery', 'element', 'form', 'response', 'layer', 'laytpl'], function (exports) {
    var $ = layui.jquery,
        element = layui.element,
        response = layui.response,
        layer = layui.layer,
        laytpl = layui.laytpl,
        form = layui.form;
    // 1:转发 2:删除 3:挂起 4: 激活 5:退回 6：认领 7：取回
    var forbiddenActive = [4];
    var forbiddenHangUp = [1, 2, 3, 5, 6, 7];
    var forbiddenClock = [4, 7];

    var workflow = {
        startUpProcess: function (data) {
            return startUpProcess(data);
        }, startUpFdjywProcess: function (data) {
            return startUpFdjywProcess(data);
        }, startUpTslcProcess: function (processDefKey, url) {
            return startUpTslcProcess(processDefKey, url);
        }, forwardProcess: function (checkData, url, tableId, currentId, isIndex, processInstanceId, endType) {
            return forwardProcess(checkData, url, tableId, currentId, isIndex, processInstanceId, endType);
        }, backProcess: function (checkData, url, tableId, currentId, isIndex) {
            return backProcess(checkData, url, tableId, currentId, isIndex);
        }, backPlProcess: function (checkStatus, url, tableId, currentId, isIndex) {
            return backPlProcess(checkStatus, url, tableId, currentId, isIndex);
        }, claimProcess: function (checkStatus, url, tableId, currentId, openpage) {
            return claimProcess(checkStatus, url, tableId, currentId, openpage)
        }, allowBack: function (taskId) {
            return allowBack(taskId);
        }, allowFetchBack: function (taskId) {
            return allowFetchBack(taskId);
        }, fetchProcess: function (checkData, doneUrl, doneTableId, doneCurrentId, isIndex) {
            return fetchProcess(checkData, doneUrl, doneTableId, doneCurrentId, isIndex);
        }, hangUpProcess: function (checkStatus, url, tableId, currentId) {
            return hangUpProcess(checkStatus, url, tableId, currentId);
        }, deleteProcess: function (checkStatus, url, tableId, currentId, isIndex, type) {
            return deleteProcess(checkStatus, url, tableId, currentId, isIndex, type);
        }, stopProcess: function (checkStatus, url, tableId, currentId, isIndex) {
            return stopProcess(checkStatus, url, tableId, currentId, isIndex);
        }, forwardauto: function (checkData, url, tableId, currentId, isIndex, processInstanceId, endType) {
            return forwardauto(checkData, url, tableId, currentId, isIndex, processInstanceId, endType);
        }, forwardPlProcess: function (checkStatus, url, tableId, currentId, bdzf) {
            return forwardPlProcess(checkStatus, url, tableId, currentId, bdzf);
        }, activeProcess: function (checkStatus, url, tableId, currentId) {
            return activeProcess(checkStatus, url, tableId, currentId);
        }, isEnd: function (taskId, endType) {
            return isEnd(taskId, endType);
        }, endProcess: function (checkData, url, tableId, currentId, isIndex) {
            return endProcess(checkData, url, tableId, currentId, isIndex);
        }, checkStaus: function (checkData, staus, type) {
            return checkStaus(checkData, staus, type);
        }, turnCheck: function (checkData) {
            return turnCheck(checkData, "zf");
        }, checkBtx: function (formViewKey, gzlslid, taskId) {
            return checkBtx(formViewKey, gzlslid, taskId);
        }, showHangReson: function (checkData) {
            return showHangReson(checkData);
        }, queryOpinion: function (processInstanceId, taskId, type) {
            return queryOpinion(processInstanceId, taskId, type);
        }, queryUserType: function (type) {
            return queryUserType(type);
        }, lockProcess: function (checkData) {
            lockProcess(checkData);
        }, cancelClaimProcess: function (checkData, doneUrl, doneTableId, doneCurrentId, isIndex) {
            //取消认领
            cancelClaimProcess(checkData, doneUrl, doneTableId, doneCurrentId, isIndex)
        }, cancelClaimPlProcess: function (checkData, doneUrl, doneTableId, doneCurrentId, isIndex) {
            //取消批量认领
            cancelClaimPlProcess(checkData, doneUrl, doneTableId, doneCurrentId, isIndex)
        }, fetchPlProcess: function (checkData, doneUrl, doneTableId, doneCurrentId, isIndex) {
            return fetchPlProcess(checkData, doneUrl, doneTableId, doneCurrentId, isIndex);
        }, abandonProcess: function (checkData, doneUrl, doneTableId, doneCurrentId, isIndex) {
            return abandonProcess(checkData, doneUrl, doneTableId, doneCurrentId, isIndex);
        }, setPriority: function (checkStatus, doneUrl, doneTableId, doneCurrentId, isxm) {
            return setPriority(checkStatus, doneUrl, doneTableId, doneCurrentId, isxm);
        }, canclePriority: function (checkStatus, doneUrl, doneTableId, doneCurrentId, isxm) {
            return canclePriority(checkStatus, doneUrl, doneTableId, doneCurrentId, isxm);
        }, applyDelete : function(checkData, url, tableId, currentId, isIndex, processInstanceId, endType){
            return applyDelete(checkData, url, tableId, currentId, isIndex, processInstanceId, endType);
        }, allowDelete : function (checkData, url, tableId, currentId, isIndex, processInstanceId){
            return allowDelete(checkData, url, tableId, currentId, isIndex, processInstanceId);
        }
    };

    // 创建任务
    function startUpProcess(data) {
        if (data) {
            //判断流程创建是否超出限制
            var result = judgeProcessOutCount(data.processDefKey);
            if (!result) {
                layer.msg("流程创建超出限制次数，本月不允许再次创建")
                return false;
            }
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/workflow/process-instances/process/param",
                cache: false,    // 设置为 false 将不缓存此页面
                async: false,
                success: function (param) {
                    if (!isNullOrEmpty(param.slbh) && !isNullOrEmpty(param.jbxxid)) {
                        var slbh = param.slbh;
                        var jbxxid = param.jbxxid;
                        if(param.xztzCover =="true"){
                            window.open('/realestate-accept-ui/view/query/selectBdcdyh.html?processDefKey=' + data.processDefKey + '&jbxxid=' + jbxxid + '&slbh=' + slbh,"流程创建");
                        }else {
                            window.open('/realestate-accept-ui/view/query/selectBdcdyh.html?processDefKey=' + data.processDefKey + '&jbxxid=' + jbxxid + '&slbh=' + slbh);
                        }
                    } else {
                        layer.msg("获取 jbxxid 和 slbh 失败！");
                    }
                }, error: function (e) {
                    delAjaxErrorMsg(e);
                }
            });

        } else {
            layer.msg('创建失败！');
        }
    }

    function judgeProcessOutCount(gzldyid) {
        var res = true;
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/process/overcount",
            cache: false,    // 设置为 false 将不缓存此页面
            async: false,
            data: {gzldyid: gzldyid},
            success: function (result) {
                if (isNotBlank(result) && result != null && result <= 0) {
                    res = false;
                }
            }, error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
        return res;
    }

    // 创建非登记业务流程
    function startUpFdjywProcess(data) {
        if (data) {
            $.ajax({
                type: "PUT",
                url: getContextPath() + "/rest/v1.0/workflow/process-instances/fdjyw",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: JSON.stringify(data),
                success: function (param) {
                    window.open('/realestate-portal-ui/view/new-page.html?taskId=' + param.taskId);
                }, error: function (e) {
                    delAjaxErrorMsg(e);;
                }
            });
        } else {
            layer.msg('创建失败！');
        }
    }


    // 创建特殊流程
    function startUpTslcProcess(processDefKey, url) {
        if (isNullOrEmpty(processDefKey) || isNullOrEmpty(url)) {
            layer.msg('特殊流程的流程定义 key 和 url 均不能为空！');
        } else {
            window.open(url + '?processDefKey=' + processDefKey);
        }
    }

//转发操作
    function forwardProcess(checkData, url, tableId, currentId, isIndex, processInstanceId, endType) {
        var checkResult = turnCheck(checkData, "zf");
        if (checkResult == false) {
            removeModal();
            return false;
        } else if (checkResult == true) {
            // addModel();
            removeModal();
            forward(checkData, url, tableId, currentId, isIndex, processInstanceId, endType);
        } else {
            removeModal();
            var tsxxHtml = loadTsxx(checkResult);
            rightShowWarn(tsxxHtml,checkData, url, tableId, currentId, isIndex,processInstanceId);
        }
    }

    function forward(checkData, url, tableId, currentId, isIndex, processInstanceId, endType) {
        if (!checkStaus(checkData, 1, '转发') || !checkBtx(checkData.formKey, checkData.processInstanceId, checkData.taskId)) {
            removeModal();
            return false;
        }
        loadForwardPage(checkData, url, tableId, currentId, isIndex, processInstanceId, endType);
    }

    /**
     * 加载 转发/办结 页面
     */
    function loadForwardPage(checkData, url, tableId, currentId, isIndex, processInstanceId, endType) {
        var zfUrl;
        if (lcPageEdition == 'nt') {
            zfUrl = "../nantong/send.html";
        } else if(lcPageEdition == 'changzhou'){
            zfUrl = "../changzhou/send.html";
        } else{
            zfUrl = "../view/send.html";
        }
        var end = isEnd(checkData.taskId, endType);
        removeModal();
        if (end) {
            layer.confirm('您确定要办结吗？', {
                title: '提示',
                btn: ['确定', '取消'] //按钮
            }, function (index) {
                layer.close(index);
                endProcess(checkData, url, tableId, currentId, isIndex);
            });
        } else {
            layer.open({
                type: 2,
                skin: 'layui-layer-lan',
                title: '任务转发',
                area: ['960px', '490px'],
                content: zfUrl + '?currentId=' + currentId + '&isIndex=' + isIndex + '&name=' + checkData.taskId + "&isEnd=" + end + "&processInstanceId=" + processInstanceId+"&processKey="+checkData.processKey
            });
        }
    }

    function forwardauto(checkStatus, url, tableId, currentId, isIndex, processInstanceId, endType) {
        var checkData = checkStatus.data[0];
        // 前端先验证
        if (!checkStaus(checkData, 1, '转发')) {
            removeModal();
            return false;
        }
        var workFlowDTO = {};
        workFlowDTO['taskId'] = checkData.taskId;
        workFlowDTO['slbh'] = checkData.slbh;
        workFlowDTO['taskName'] = checkData.taskName;
        workFlowDTO['processDefKey'] = checkData.processKey;
        workFlowDTO['processInstanceId'] = checkData.processInstanceId;
        workFlowDTO['formKey'] = checkData.formKey;
        workFlowDTO['checkMb'] = "zf";

        $.ajax({
            type: "POST",
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/zfyz",
            async: false,
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data: JSON.stringify(workFlowDTO),
            success: function (data) {
                removeModal();
                console.info(data);
                var checkData = checkStatus.data[0];
                if (data.bdcGzyzVOS != null) {
                    var tsxxHtml = loadTsxx(data.bdcGzyzVOS);
                    rightShowWarn(tsxxHtml, checkData, url, tableId, currentId, isIndex, processInstanceId, endType);
                    return;
                }
                if (data.bdcBtxyzVOS != null) {
                    showBtxResult(data.bdcBtxyzVOS);
                }
                if (data.bdcBtxyzVOS == null && data.bdcGzyzVOS == null) {
                    loadForwardPage(checkData, url, tableId, currentId, isIndex, processInstanceId, endType);
                }
            }, error: function (e) {
                removeModal();
                delAjaxErrorMsg(e);;
            }
        });
    }

    // 判断是走流程删除，还是直接删除
    function checkLcSqsc(taskId){
        var deferred = $.Deferred();
        var params = [{"key":"SFSQSC","value":"1"}];
        $.ajax({
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/forward/nextnode/flowexp/"+taskId,
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(params),
            success: function (data) {
                if(isNotBlank(data)){
                    // 走流程删除
                    deferred.resolve(true);
                }else{
                    warnMsg("未获取到下一节点同组织的角色人员，请联系管理员。");
                    // 直接删除
                    deferred.reject();
                }
            },
            error: function (err) {
                delAjaxErrorMsg(err);
                deferred.reject();
            }
        });
        return deferred;
    }

    // 申请删除功能
    function applyDelete(checkStatus, url, tableId, currentId, isIndex, processInstanceId, endType) {
        var checkData = checkStatus.data[0];
        checkLcSqsc(checkData.taskId).done(function (data) {
            if(data){
                // 前端先验证
                if (!checkStaus(checkData, 1, '申请删除')) {
                    removeModal();
                    return false;
                }
                var checkResult = turnCheck(checkData, "sqsc");
                var gzResults = [];
                if (checkResult == true) {
                    var workflow = {};
                    workflow.processInstanceId = checkData.processInstanceId;
                    if (isNullOrEmpty(checkData.processKey)) {
                        workflow.processDefKey = checkData.procDefKey;
                    } else {
                        workflow.processDefKey = checkData.processKey;
                    }
                    workflow.slbh = checkData.slbh;
                    // 打开申请删除页面
                    loadApplyDeletePage(checkData, url, tableId, currentId, isIndex, processInstanceId, endType);
                }  else {
                    removeModal();
                    console.info(checkResult);
                    $.each(checkResult, function (key, item) {
                        gzResults.push(item);
                    });
                }

                if (gzResults.length > 0) {
                    // 渲染规则页面
                    var tsxxHtml = loadTsxx(gzResults);
                    rightShowWarnCallBack(tsxxHtml, checkStatus.data[0], url, tableId, currentId, isIndex, processInstanceId,
                        function (){
                            loadApplyDeletePage(checkData, url, tableId, currentId, isIndex, processInstanceId, endType);
                        });
                }
            }else{
                // 直接删除流程
                deleteProcess(checkStatus, url, tableId, currentId, isIndex, endType);
            }
        });
    }

    function loadApplyDeletePage(checkData, url, tableId, currentId, isIndex, processInstanceId, endType){
        removeModal();
        console.info(checkData);
        // 设置转发所需参数值
        var zfcs = {"SFSQSC": "1"};
        $.ajax({
            url: getContextPath() + '/rest/v1.0/workflow/process-instances/forward/addZfcs/' + checkData.taskId,
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(zfcs),
            success: function () {
                var zfUrl = "../view/apply-delete.html";
                var end = isEnd(checkData.taskId, endType);
                layer.open({
                    type: 2,
                    skin: 'layui-layer-lan',
                    title: '申请删除',
                    area: ['960px', '490px'],
                    content: zfUrl + '?currentId=' + currentId + '&isIndex=' + isIndex + '&name=' + checkData.taskId + "&isEnd=" + end + "&processInstanceId=" + processInstanceId
                });
            },
            error: function (e) {
                delAjaxErrorMsg(e, "申请删除时添加转发参数失败");;
            }
        });
    }

    function allowDelete(checkStatus, url, tableId, currentId, isIndex, processInstanceId){
        layer.confirm('您确定要删除吗？', {
            title: '提示',
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            addModel('删除中');
            layer.close(index);
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/workflow/process-instances/lcsc/withCzrz/" + processInstanceId,
                dataType: "json",
                success: function (data) {
                    if (isIndex) {
                        layer.msg('删除成功');
                        renderTable(url, tableId, currentId);
                    } else {
                        layer.msg('删除成功，即将关闭当前页。');
                        setTimeout(function(){ window.close(); }, 1000);
                    }
                }, error: function (e) {
                    delAjaxErrorMsg(e);
                }, complete: function () {
                    removeModal();
                }
            });
        });
    }

    //退回操作
    function backProcess(checkData, url, tableId, currentId, isIndex) {
        var checkResult = turnCheck(checkData,"th");
        if (checkResult == false) {
            removeModal();
            return false;
        } else if (checkResult == true) {
            removeModal();
            back(checkData, url, tableId, currentId, isIndex);
        } else {
            removeModal();
            var tsxxHtml = loadTsxx(checkResult);
            rightShowWarnCallBack(tsxxHtml, checkData, url, tableId, currentId, isIndex, "",
                function (){
                    back(checkData, url, tableId, currentId, isIndex);
                });
        }

    }
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 退回
     */
    function back(checkData, url, tableId, currentId, isIndex) {
        removeModal();
        addModel();
        if (!checkStaus(checkData, 5, '退回')) {
            removeModal();
            return false;
        }
        if (allowBack(checkData.taskId)) {
            layer.open({
                title: '退回',
                type: 2,
                skin: 'layui-layer-lan',
                area: ['430px', '430px'],
                content: './back-info.html?currentId=' + currentId + '&isIndex=' + isIndex + '&taskId=' + checkData.taskId
            });
        }
        removeModal();
    }

    function claimProcess(checkStatus, url, tableId, currentId, openpage) {
        if (typeof rlGz !== "undefined") {
            addModel();
            var checkData = checkStatus.data[0];
            var checkResult = turnCheck(checkData, "rl");
            if (checkResult == false) {
                removeModal();
                return false;
            } else if (checkResult == true) {
                removeModal();
                claimProcessDetail(checkStatus, url, tableId, currentId, openpage);
            } else {
                removeModal();
                var tsxxHtml = loadTsxx(checkResult);
                rightRlShowWarn(tsxxHtml, checkStatus, url, tableId, currentId, true, checkData.processInstanceId);
            }
        } else {
            claimProcessDetail(checkStatus, url, tableId, currentId, openpage);
        }
    }

    function claimProcessDetail(checkStatus, url, tableId, currentId, openpage) {
        var checkResult = true;
        var taskIds = [];
        var gzlslidIds = [];
        var workFlowDTOList =[];
        checkStatus.data.forEach(function (v) {
            checkResult = checkStaus(v, 6, '认领');
            if (!checkResult) {
                return false;
            }
            var workFlowDTO ={};
            taskIds.push(v.taskId);
            workFlowDTO.taskId =v.taskId;
            if (gzlslidIds.indexOf(v.processInstanceId) == -1) {
                gzlslidIds.push(v.processInstanceId);
                workFlowDTO.processInstanceId =v.processInstanceId;
            }
            workFlowDTOList.push(workFlowDTO);

        });
        if (taskIds.length == 0) {
            return;
        }
        addModel('认领中');
        $.ajax({
            type: "post",
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/task/claim",
            traditional: true,
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data: JSON.stringify(workFlowDTOList),
            success: function (data) {
                removeModal();
                layer.msg('认领成功');
                // renderTable(url, tableId, currentId);
                if (lcPageEdition == 'nt') {
                    if(tableId == '#wwsqTable'){
                        $('#wwsqTab').click();
                    }else{
                        $('#rlTab').click();
                    }
                    $.ajax({
                        type: "post",
                        url: getContextPath() + "/rest/v1.0/task/todo",
                        traditional: true,
                        success: function (data) {
                            if (data.totalElements > 99) {
                                $('.bdc-list-num-tips').html('99+');
                            } else {
                                $('.bdc-list-num-tips').html(data.totalElements);
                            }
                        }
                    });
                }else {
                    $('#todoTab').click();
                    var rlUrl = getContextPath() + "/rest/v1.0/task/claim/list";
                    //获取互联网+的处理
                    if($('.bdc-rl-num-word').length>0 && $('.bdc-rl-num-word').html().indexOf("互联网")!=-1){
                        $.ajax({
                            type: "POST",
                            url: rlUrl,
                            data: {sply: "3"},
                            success: function (data) {
                                if(data && data.hasOwnProperty("totalElements")){
                                    $('.bdc-rl-num-js').html(data.totalElements)
                                }
                            }
                        });
                    }else{
                        $.ajax({
                            type: "POST",
                            url: rlUrl,
                            success: function (data) {
                                if(data && data.hasOwnProperty("totalElements")){
                                    if (data.totalElements > 99) {
                                        $('.bdc-rl-num-js').html('99+');
                                    } else {
                                        $('.bdc-rl-num-js').html(data.totalElements);
                                    }
                                }
                            }
                        });
                    }
                }
                if(openpage){
                    openPage(checkStatus.data[0]);
                }
            }, error: function (e) {
                delAjaxErrorMsg(e);
                // response.fail(e,"");
            }, complete: function () {
                removeModal();
            }
        });
    }
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 取消认领
     */
    function cancelClaimProcess(checkData, url, tableId, currentId, isIndex) {
        var logoutIndex = layer.confirm('您确定要取消认领吗？', {
            title: '提示',
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            addModel('取消中');
            layer.close(index);
            var sljd =false;
            var gzlslid =checkData.processInstanceId;
            if(checkData.taskName=='受理'){
                sljd =true;
            }
            if(checkData.claimStatus != 2){
                layer.alert('非认领任务，无法取消认领！', {title: '提示'});
                removeModal();
                return false;
            }
            $.ajax({
                type: "post",
                url: getContextPath() + "/rest/v1.0/workflow/process-instances/task/claim/cancel",
                traditional: true,
                data: {taskId: checkData.taskId,gzlslid:gzlslid,sljd:sljd},
                success: function (data) {
                    layer.msg('取消成功');
                    renderTable(url, tableId, currentId);
                    $.ajax({
                        type: "post",
                        url: getContextPath() + "/rest/v1.0/task/todo",
                        traditional: true,
                        success: function (data) {
                            if (data.totalElements > 99) {
                                $('.bdc-list-num-tips').html('99+');
                            } else {
                                $('.bdc-list-num-tips').html(data.totalElements);
                            }
                        }
                    });
                }, error: function (e) {
                    delAjaxErrorMsg(e);
                }, complete: function () {
                    removeModal();
                    window.renderTable('','',currentId);
                    window.parent.close();
                }
            });
        });
    }

    /**
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 取消认领
     */
    function cancelClaimPlProcess(checkData, url, tableId, currentId, isIndex) {
        var logoutIndex = layer.confirm('您确定要批量取消认领吗？', {
            title: '提示',
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            addModel('取消中');
            layer.close(index);
            var workflowList = [];
            var frlTask = "";
            $.each(checkData, function (key, item) {
                var workflow = {};
                workflow.taskId = item.taskId;
                workflow.slbh = item.slbh;
                workflow.processInstanceId = item.processInstanceId;
                workflow.sljd =true;
                if(item.taskName !='受理'){
                    workflow.sljd =false;

                }
                if(item.claimStatus!=2){
                    frlTask+=item.slbh+',';
                }
                workflowList.push(workflow);
            });
            if(frlTask.length!=0){
                layer.alert('受理编号:'+frlTask.substring(0,frlTask.length-1)+'非认领任务，无法取消认领！', {title: '提示'});
                removeModal();
                return false;
            }

            $.ajax({
                type: "post",
                url: getContextPath() + "/rest/v1.0/workflow/process-instances/task/claim/cancel/pl",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: JSON.stringify(workflowList),
                success: function (data) {
                    var failMsg = "";
                    var successMsg = "取消成功" + data.success + "条";
                    if (!isNullOrEmpty(data.fail) && data.fail > 0) {
                        failMsg = "取消失败" + data.fail + "条";
                        if (data.success == 0) {
                            successMsg = "";
                        }
                    }
                    var otherMsg = "";
                    $.each(data.messages, function (key, item) {
                        otherMsg += item +"</br>";
                    });
                    layforwardMsg(successMsg, failMsg, otherMsg);
                    renderTable(url, tableId, currentId);
                    $.ajax({
                        type: "post",
                        url: getContextPath() + "/rest/v1.0/task/todo",
                        traditional: true,
                        success: function (data) {
                            if (data.totalElements > 99) {
                                $('.bdc-list-num-tips').html('99+');
                            } else {
                                $('.bdc-list-num-tips').html(data.totalElements);
                            }
                        }
                    });
                }, error: function (e) {
                    delAjaxErrorMsg(e);
                }, complete: function () {
                    removeModal();
                    window.renderTable('','',currentId);
                    window.parent.close();
                }
            });
        });
    }
    //打开新页面
    function openPage(obj) {
        if (!showHangReson(obj)) {
            return false;
        }
        //锁定任务
        lockProcess(obj);
        window.open("./new-page.html?name=" + obj.taskId + "&type=rl", obj.slbh);
    }

    //判断是否可以退回
    function allowBack(taskId) {
        var isAllow = false;
        $.ajax({
            type: "get",
            async: false,
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/back/isAllow",
            data: {
                taskId: taskId
            },
            success: function (data) {
                data.code == 0 ? isAllow = true : layer.msg(data.msg);
            }, error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
        return isAllow;
    }

//判断是否可以取回
    function allowFetchBack(taskId) {
        var isAllowFetchBack = false;
        $.ajax({
            type: "get",
            async: false,
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/task/isAllowFetchBack",
            data: {
                taskId: taskId
            },
            success: function (data) {
                data.code == 0 ? isAllowFetchBack = true : layer.msg(data.msg);
            }, error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
        return isAllowFetchBack;
    }

    function fetchProcess(checkData, doneUrl, doneTableId, doneCurrentId, isIndex) {
        if (!allowFetchBack(checkData.taskId)) {
            return false;
        }
        var logoutIndex = layer.confirm('您确定要取回吗？', {
            title: '提示',
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            layer.close(index);
            addModel('取回中');
            $.ajax({
                type: "get",
                url: getContextPath() + "/rest/v1.0/workflow/process-instances/task/fetchBack",
                traditional: true,
                data: {taskId: checkData.taskId},
                success: function (data) {
                    if (isIndex) {
                        layer.msg('取回成功');
                        renderTable(doneUrl, doneTableId, doneCurrentId);
                    } else {
                        layer.msg('取回成功，即将关闭当前页。');
                        setTimeout(function(){ window.close(); }, 1000);
                    }
                }, error: function (e) {
                    delAjaxErrorMsg(e);
                }, complete: function () {
                    removeModal();
                }
            });
        });
    }
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 批量取回
     */
    function fetchPlProcess(checkData, doneUrl, doneTableId, doneCurrentId, isIndex) {
        var taskIdList=[];
        $.each(checkData.data, function (key, val) {
            taskIdList.push(val.taskId);
        });
        if (taskIdList.length==0) {
            return false;
        }
        var logoutIndex = layer.confirm('您确定要取回吗？', {
            title: '提示',
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            layer.close(index);
            addModel('取回中');
            $.ajax({
                type: "get",
                url: getContextPath() + "/rest/v1.0/workflow/process-instances/task/plFetchBack",
                traditional: true,
                data: {taskIdList: taskIdList},
                success: function (data) {
                    if (isIndex) {
                        layer.msg(data,{time: 5000});
                        renderTable(doneUrl, doneTableId, doneCurrentId);
                    } else {
                        layer.msg(data+'即将关闭当前页。');
                        setTimeout(function(){ window.close(); }, 1000);
                    }
                }, error: function (e) {
                    delAjaxErrorMsg(e);
                }, complete: function () {
                    removeModal();
                }
            });
        });
    }

    function abandonProcess(checkData, url, tableId, currentId, isIndex) {
        var workflowList = [];
        var checkResult = true;
        if (!checkStaus(checkData, 2, '撤销')) {
            checkResult = false;
            return checkResult;
        }

        var workflow = {};
        workflow.processInstanceId = checkData.processInstanceId;
        if (isNullOrEmpty(checkData.processKey)) {
            workflow.processDefKey = checkData.procDefKey;
        } else {
            workflow.processDefKey = checkData.processKey;
        }
        workflowList.push(workflow);

        addModel();
        var checkResult = tsGzYz('cxlcgzyz', workflowList[0].processInstanceId);
        if (checkResult == false) {
            removeModal();
            return false;
        } else if (checkResult == true) {
            removeModal();
            // 清空撤消原因的内容
            $("#abandonreason").val('');
            var logoutIndex = layer.open({
                title: '撤销',
                type: 1,
                area: ['430px'],
                btn: ['确定', '取消'],
                content: $('#abandon-reason')
                ,yes: function(index, layero) {
                    var reason = $("#abandonreason").val();
                    var msg = "撤销"
                    if (lcPageEdition == 'changzhou') {
                        msg = "退回"
                    }
                    if (isNullOrEmpty(reason) && isNullOrEmpty(reason.replace(/\s+/g, ""))) {
                        layer.msg('请输入' + msg + '原因!');
                        return false;
                    }
                    //提交 的回调
                    addModel(msg + '中');
                    layer.close(index);
                    for (var index in workflowList) {
                        workflowList[index].reason = reason;
                    }
                    $.ajax({
                        type: "POST",
                        url: getContextPath() + "/rest/v1.0/workflow/process-instances/abandon",
                        contentType: "application/json;charset=utf-8",
                        dataType: "json",
                        data: JSON.stringify(workflowList[0]),
                        success: function (data) {
                            if (isIndex) {
                                layer.msg(msg + '成功');
                                renderTable(url, tableId, currentId);
                            } else {
                                layer.msg(msg + '成功，即将关闭当前页。');
                                setTimeout(function () {
                                    window.close();
                                }, 1000);
                            }
                        }, error: function (e) {
                            delAjaxErrorMsg(e);
                        }, complete: function () {
                            removeModal();
                        }
                    });
                }
                ,btn2: function(index, layero){
                    //取消 的回调
                    layer.close(index);
                }
            });
        } else {
            removeModal();
            var tsxxHtml = loadTsxx(checkResult);
            rightShowWarn(tsxxHtml, checkData, url, tableId, currentId, isIndex);
        }
    }

    function setPriority(checkStatus, url, tableId, currentId, isxm) {
        var workflow = {};
        var requestUrl = "";
        // 项目列表修改的是流程的优先级 added by lixin 2020.06.23
        if (isxm == true || isxm == "true") {
            workflow.processInstanceId = checkStatus.data[0].processInstanceId;
            requestUrl = getContextPath() + "/rest/v1.0/workflow/process-instances/priority";
        } else {
            workflow.taskId = checkStatus.data[0].taskId;
            requestUrl = getContextPath() + "/rest/v1.0/workflow/process-instances/task/priority"
        }
        $("#priorityreason").val('');
        var logoutIndex = layer.open({
            title: '加急',
            type: 1,
            area: ['430px'],
            btn: ['确定', '取消'],
            content: $('#priority-reason')
            , yes: function (index, layero) {
                var reason = $("#priorityreason").val();
                workflow.reason = reason;
                addModel();
                layer.close(index);
                $.ajax({
                    type: "POST",
                    url: requestUrl,
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify(workflow),
                    success: function (data) {
                        layer.msg('加急成功');
                        renderTable(url, tableId, currentId);
                    }, error: function (e) {
                        delAjaxErrorMsg(e);
                    }, complete: function () {
                        removeModal();
                    }
                });
            }
            , btn2: function (index, layero) {
                //取消 的回调
                layer.close(index);
            }
        });

    }

    function canclePriority(checkStatus, doneUrl, doneTableId, doneCurrentId, isxm){
        var workflow = {};
        var requestUrl = "";
        // 项目列表修改的是流程的优先级 added by lixin 2020.06.23
        if (isxm == true || isxm == "true") {
            workflow.processInstanceId = checkStatus.data[0].processInstanceId;
            requestUrl = getContextPath() + "/rest/v1.0/workflow/process-instances/canclePriority";
        } else {
            workflow.taskId = checkStatus.data[0].taskId;
            requestUrl = getContextPath() + "/rest/v1.0/workflow/process-instances/task/canclePriority"
        }
        addModel();
        $.ajax({
            type: "POST",
            url: requestUrl,
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data: JSON.stringify(workflow),
            success: function (data) {
                layer.msg('取消加急成功');
                renderTable(doneUrl, doneTableId, doneCurrentId);
            }, error: function (e) {
                delAjaxErrorMsg(e);
            }, complete: function () {
                removeModal();
            }
        });
    }

    //挂起任务
    function hangUpProcess(checkStatus, url, tableId, currentId) {
        var processInstanceIds = [];
        var checkResult = true;
        $.each(checkStatus.data, function (key, item) {
            if (!checkStaus(item, 3, '挂起')) {
                checkResult = false;
                return checkResult;
            }
            processInstanceIds.push(item.processInstanceId);
        });
        if (!checkResult) {
            return checkResult;
        }
        //小弹出层
        layer.open({
            title: '任务挂起',
            type: 1,
            area: ['430px'],
            btn: ['挂起', '取消'],
            content: $('#bdc-popup-textarea')
            , yes: function (index, layero) {
                var reason = $("#gqjgyy").val();
                if (isNullOrEmpty(reason)) {
                    layer.msg('请输入挂起原因');
                } else {
                    addModel('挂起中');
                    // 挂起当前任务
                    $.ajax({
                        type: "post",
                        url: getContextPath() + "/rest/v1.0/workflow/process-instances/hang",
                        traditional:true,
                        data: {
                            processInstanceIds: processInstanceIds,
                            reason: reason
                        },
                        success: function (data) {
                            layer.close(index);
                            $("#gqjgyy").val("");
                            layer.msg(data);
                            renderTable(url, tableId, currentId);
                        }, error: function (e) {
                            delAjaxErrorMsg(e);
                        }, complete: function () {
                            removeModal();
                        }
                    });
                }
            }, btn2: function (index, layero) {
                $("#gqjgyy").val("");
            }, cancel: function () {
                $("#gqjgyy").val("");
            }, success: function () {
                $("#gqjgyyLab").html('<span class="required-span"><sub>*</sub></span>挂起原因');
            }
        });
    }


    //批量退回操作
    //可批量转发列表
    var workflowBackList = [];
    var plBackIgnoreList = [];
    function backPlProcess(checkStatus, url, tableId, currentId, isIndex) {
        // 清空退回原因
        $("#backreason").val("");
        var statusResults = batchCheckStatus(workflowBackList, checkStatus, "转发");
        if (workflowBackList!= null && workflowBackList.length > 0) {
            layer.open({
                title: '退回原因',
                type: 1,
                area: ['430px'],
                btn: ['确认', '取消'],
                content: $('#back-reason')
                ,yes: function(index, layero){
                    // 判断是否需要填写退回原因
                    var thyjCheck = "";
                    $.ajax({
                        url: getContextPath() + "/rest/v1.0/workflow/process-instances/getThyjCheck",
                        dataType: "json",
                        async: false,
                        success: function (data) {
                            thyjCheck = data;
                        }, error: function (e) {
                            delAjaxErrorMsg(e);
                        }
                    });
                    var opinion = $('#backreason').val();
                    if (thyjCheck == '1'){
                        if(isNullOrEmpty(opinion)){
                            layer.msg('请输入退回意见');
                            return;
                        }
                    }
                    layer.close(index);
                    addModel('退回中');
                    var bdcPlBackDTO = {};
                    bdcPlBackDTO.workFlowDTOList = workflowBackList;
                    bdcPlBackDTO.opinion = opinion;
                    $.ajax({
                        type: "POST",
                        contentType: "application/json;charset=utf-8",
                        url: getContextPath() + "/rest/v1.0/workflow/process-instances/back/pl",
                        data: JSON.stringify(bdcPlBackDTO),
                        success: function (data) {
                            if (data.gzyzMsg == false) {
                                showPlThWarnMsg(data, url, tableId, currentId);
                            } else {
                                workflowBackList = [];
                                plBackIgnoreList = [];
                            }
                            if (isNullOrEmpty(data.message)) {
                                data.message = "";
                            }
                            layforwardMsg(data.successMsg, data.failMsg, data.message+ statusResults);
                            renderTable(url, tableId, currentId);
                        }, error: function (e) {
                            response.fail(e,'');
                        },complete:function(){
                            removeModal();
                        }
                    });

                }
                ,btn2: function(index, layero){
                    layer.close(index);
                }
            });
        } else {
            removeModal();
            layer.msg(statusResults);
        }
    }

    //批量转发任务
    //可批量转发列表
    var workflowPlList = [];
    //可忽略全部
    var plIgnoreList = [];

    function forwardPlProcess(checkStatus, url, tableId, currentId, bdzf) {
        if (bdzf === undefined) {
            bdzf = false;
        }
        // 拆分相同流程相同节点的流程
        var sameNodes = batchCheckNodes(checkStatus);
        if (sameNodes) {
            addModel("批量转发中");
            setTimeout(function() {
                preformPlForward(checkStatus, url, tableId, currentId, sameNodes, bdzf);
            }, 500);
        } else {
            if (lcPageEdition === "yancheng") {
                warnMsg("所选数据存在不同流程或者不同节点！")
            } else {
                var logoutIndex = layer.confirm('您确定要批量转发吗？', {
                    title: '提示',
                    btn: ['确定', '取消'] //按钮
                }, function (index) {
                    addModel('批量转发中');
                    layer.close(index);
                    //延迟调用接口，使弹窗能在点击确定后立即关闭 避免用户重复点击
                    setTimeout(function () {
                        preformPlForward(checkStatus, url, tableId, currentId, bdzf);
                    }, 10)

                });
            }
        }
    }

    // 调用批量转发方法
    function plForward(url, tableId, currentId, statusResults) {
        $.ajax({
            type: "POST",
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/task-handel/batch-forward/complete",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(workflowPlList),
            async: false,
            success: function (data) {
                if (data.gzyzMsg == false) {
                    showPlWarnMsg(data, url, tableId, currentId);
                } else {
                    workflowPlList = [];
                    plIgnoreList = [];
                }
                var btxMsg = initBtxMsg(data.forwardYzDTOS);
                layforwardMsg(data.successMsg, data.failMsg, btxMsg + statusResults);
                renderTable(url, tableId, currentId);
            }, error: function (e) {
                delAjaxErrorMsg(e);
            }, complete: function () {
                removeModal();
            }
        });
    }

    function preformPlForward(checkStatus, url, tableId, currentId, sameNodes, bdzf) {
        // 对于转发项目的状态进行判断
        var statusResults = batchCheckStatus(workflowPlList, checkStatus, "转发");
        if (workflowPlList != null && workflowPlList.length > 0) {
            if (sameNodes) {
                var workflow = workflowPlList[0];
                var end = isEnd(workflow.taskId);
                // 办结就不需要让用户选择了，直接走默认转发逻辑
                if (end) {
                    plForward(url, tableId, currentId, statusResults);
                } else {
                    removeModal();
                    var plzfkey = new Date().getTime();
                    sessionStorage.setItem(plzfkey, JSON.stringify(workflowPlList));
                    var area = ['960px', '490px'];
                    if (bdzf === true) {
                        area = ['980px', '530px'];
                    }
                    var send = layer.open({
                        type: 2,
                        skin: 'layui-layer-lan',
                        title: '任务转发',
                        area: area,
                        content: '../view/send.html?currentId=' + currentId + '&name=' + workflow.taskId + "&isEnd=" + end + "&processInstanceId=" + workflow.processInstanceId + "&sameNodes=" + sameNodes + "&plzfkey=" + plzfkey + "&bdzf=" + bdzf + "&slbh=" + checkStatus.data[0].slbh,
                        end: function () {
                            var item = sessionStorage.getItem(plzfkey);
                            if (isNullOrEmpty(item)) {
                                workflowPlList = [];
                                plIgnoreList = [];
                            } else {
                                workflowPlList = JSON.parse(item);
                                plForward(url, tableId, currentId, statusResults);
                                sessionStorage.removeItem(plzfkey);
                            }
                        }
                    });

                }
            } else {
                addModel('批量转发中');
                plForward(url, tableId, currentId, statusResults);
            }
        } else {
            removeModal();
            layer.msg(statusResults);
        }
    }

    /**
     * 批量转发判断是否可以选择节点转发
     * <p>
     * 分别判断 流程 和 节点是否相同
     * 相同则返回 字符串 sameNodes
     * 否则默认按照批量转发的逻辑处理
     * </p>
     *
     * @param checkStatus 选择数据
     * @return sameNodes 是否是相同流程相同节点
     */
    function batchCheckNodes(checkStatus) {
        var taskName = "",
            processDefKey = "";
        var sameNodes = true;
        // 判断是否是相同流程相同节点，statusResults 返回 sameNodes
        $.each(checkStatus.data, function (key, item) {
            // 分别判断 流程 和 节点是否相同
            if (item.processDefKey) {
                if (processDefKey == "") {
                    processDefKey = item.processDefKey;
                } else if (processDefKey != item.processDefKey) {
                    sameNodes = false;
                }
            }
            if (item.processKey) {
                if (processDefKey == "") {
                    processDefKey = item.processKey;
                } else if (processDefKey != item.processKey) {
                    sameNodes = false;
                }
            }

            if (taskName == "") {
                taskName = item.taskName;
            } else if (taskName != item.taskName) {
                sameNodes = false;
            }
        });
        return sameNodes;
    }

    /**
     * 批量流程判断状态
     * @param checkStatus 选择数据
     * @param statusResults 提示信息
     * @param type 类型 「转发，退回」
     */
    function batchCheckStatus(list, checkStatus, type) {
        var statusResults = "";
        $.each(checkStatus.data, function (key, item) {
            if ((item.state == 1 || item.state == 4 || isNullOrEmpty(item.state)) && (forbiddenActive.indexOf(1) != -1)) {
                statusResults += '<p class="bdc-zf-tips-p"><img src="../static/lib/bdcui/images/info-small.png" alt="">流程：' + item.slbh + '已激活,不能进行' + type + '!</p>';
            } else if (item.state == 2 && (forbiddenHangUp.indexOf(1) != -1)) {
                statusResults += '<p class="bdc-zf-tips-p"><img src="../static/lib/bdcui/images/info-small.png" alt="">流程：' + item.slbh + '已挂起,不能进行' + type + '!</p>';
            } else if (item.state == 3 && (forbiddenClock.indexOf(1) != -1)) {
                statusResults += '<p class="bdc-zf-tips-p"><img src="../static/lib/bdcui/images/info-small.png" alt="">流程：' + item.slbh + '已办理,不能进行' + type + '!</p>';
            } else if (isNullOrEmpty(item.state) && item.procStatus == 3) {//项目列表的特殊处理
                statusResults += '<p class="bdc-zf-tips-p"><img src="../static/lib/bdcui/images/info-small.png" alt="">流程：' + item.slbh + '已挂起,不能进行' + type + '!</p>';
            } else {
                // 处理数据
                item.processDefKey = item.processKey;
                list.push(item);
            }
        });
        return statusResults;
    }

    // 初始化必填项消息
    function initBtxMsg(data) {
        var btxMsg = '';
        if (!isNullOrEmpty(data)) {
            data.forEach(function (v) {
                if (!isNullOrEmpty(v.errorMsg)) {
                    btxMsg += '<p class="bdc-zf-tips-p"><img src="../static/lib/bdcui/images/info-small.png" alt="">' + v.errorMsg + '</p>';
                }
            });
        }
        return btxMsg;
    }

//    批量转发右侧显示异常信息
    function showPlWarnMsg(data, url, tableId, currentId) {
        var warnLayer = layer.open({
            type: 1,
            title: '提示信息',
            anim: -1,
            skin: 'bdc-tips-right bdc-error-layer',
            shade: [0],
            area: ['390px'],
            offset: 'r',
            content: loadPlTsxx(data.forwardYzDTOS),
            success: function() {
                //点击忽略全部
                $('#ignoreAll').click(function(){
                    //点击忽略时增加日志记录，同步处理
                    var data = this.nextElementSibling.innerText.replace(/忽略/g, "").replace(/查看/g, "");
                    addRemoveLog(data, "");
                    $("#ignoreAll").css("pointer-events", "none");
                    $("#ignoreAll").css("cursor", "default");
                    $("#ignoreAll").css("color", "gray");
                    if (plIgnoreList.length != 0) {
                        addModel();
                        $.ajax({
                            type: "POST",
                            url: getContextPath() + "/rest/v1.0/workflow/process-instances/task-handel/batch-forward/complete",
                            contentType: "application/json;charset=utf-8",
                            data: JSON.stringify(plIgnoreList),
                            success: function (data) {
                                layer.close(warnLayer);
                                //右上角关闭 清空「转发和忽略」的信息
                                workflowPlList = [];
                                plIgnoreList = [];
                                var btxMsg = initBtxMsg(data.forwardYzDTOS);
                                layforwardMsg(data.successMsg, data.failMsg, btxMsg);
                            }, error: function (e) {
                                delAjaxErrorMsg(e);
                            }, complete: function () {
                                removeModal();
                            }
                        });
                        renderTable(url, tableId, currentId);
                    } else {
                        warnMsg("没有可以转发的流程！");
                        removeModal();
                    }
                });
                setTimeout(function() {
                    $('.bdc-error-layer').css('opacity', 1)
                }, 500);
            },
            cancel: function(){
                //右上角关闭 清空「转发和忽略」的信息
                workflowPlList = [];
                plIgnoreList = [];
            }
        });
    }

    //    批量退回右侧显示异常信息
    function showPlThWarnMsg(data, url, tableId, currentId) {
        var opinion = $('#backreason').val();
        var warnLayer = layer.open({
            type: 1,
            title: '提示信息',
            anim: -1,
            skin: 'bdc-tips-right bdc-error-layer',
            shade: [0],
            area: ['390px'],
            offset: 'r',
            content: loadPlTsxx(data.forwardYzDTOS),
            success: function() {
                //点击忽略全部
                $('#ignoreAll').click(function(){
                    addModel();
                    var bdcPlBackDTO = {};
                    bdcPlBackDTO.workFlowDTOList = plBackIgnoreList;
                    bdcPlBackDTO.opinion = opinion;
                    $.ajax({
                        type: "POST",
                        url: getContextPath() + "/rest/v1.0/workflow/process-instances/back/pl",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(bdcPlBackDTO),
                        success: function (data) {
                            layer.close(warnLayer);
                            //右上角关闭 清空「退回和忽略」的信息
                            workflowBackList = [];
                            plBackIgnoreList = [];
                            layforwardMsg(data.successMsg, data.failMsg,"");
                        }, error: function (e) {
                            delAjaxErrorMsg(e);
                        }, complete: function () {
                            removeModal();
                        }
                    });
                    renderTable(url, tableId, currentId);
                });
                setTimeout(function() {
                    $('.bdc-error-layer').css('opacity', 1)
                }, 500);
            },
            cancel: function(){
                //右上角关闭 清空「转发和忽略」的信息
                workflowBackList = [];
                plBackIgnoreList = [];
            }
        });
    }

    /**
     * 批量转发信息提示
     * @param successMsg 成功信息
     * @param failMsg 失败信息
     * @param other 其他信息
     */
    function layforwardMsg(successMsg, failMsg, other){
        var forwardmsg = '';
        if (!isNullOrEmpty(failMsg)) {
            forwardmsg += '<p class="bdc-zf-tips-p"><img src="../static/lib/bdcui/images/error-small.png" alt="">'+ failMsg +'</p>'
        }
        if (!isNullOrEmpty(successMsg)) {
            forwardmsg += '<p class="bdc-zf-tips-p"><img src="../static/lib/bdcui/images/success-small.png" alt="">' + successMsg + '</p>'
        }
        if (isNullOrEmpty(other)) {
            other = "";
        }
        layer.msg(forwardmsg + other);
    }

    // 认领退回逻辑，执行删除，页面显示内容为退回 「盐城」
    function preformBack(workflowList, isIndex, url, tableId, currentId) {
        $("#wsywdeletereason").val('');
        var logoutIndex = layer.open({
            title: '退回',
            type: 1,
            area: ['430px'],
            btn: ['确定', '取消'],
            content: $('#wsyw_delete-reason')
            , yes: function (index, layero) {
                var reason = $("#wsywdeletereason").val();
                if (isNullOrEmpty(reason)) {
                    layer.msg('请输入退回原因!');
                    return false;
                }
                //提交 的回调
                addModel('退回中');
                layer.close(index);
                for (var index in workflowList) {
                    workflowList[index].reason = reason;
                }
                $.ajax({
                    type: "DELETE",
                    url: getContextPath() + "/rest/v1.0/workflow/process-instances/rl",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify(workflowList),
                    success: function (data) {
                        layer.msg('退回成功');
                        renderTable(url, tableId, currentId);
                    }, error: function (e) {
                        delAjaxErrorMsg(e);
                    }, complete: function () {
                        removeModal();
                    }
                });
            }
            , btn2: function (index, layero) {
                //取消 的回调
                layer.close(index);
            }
        });
    }

    function preformDelte(workflowList, isIndex, url, tableId, currentId) {
        $("#deletereason").val('');
        /**
         * 判断是否需要填写删除原因
         */
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/getDeleteReason",
            success: function (data) {
                if (data == '1') {
                    var logoutIndex = layer.open({
                        title: '删除',
                        type: 1,
                        area: ['430px'],
                        btn: ['确定', '取消'],
                        content: $('#delete-reason')
                        ,yes: function(index, layero){
                            var reason = $("#deletereason").val();
                            if (isNullOrEmpty(reason)) {
                                layer.msg('请输入删除原因!');
                                return false;
                            }
                            //提交 的回调
                            addModel('删除中');
                            layer.close(index);
                            for (var index in workflowList) {
                                workflowList[index].reason = reason;
                            }
                            $.ajax({
                                type: "DELETE",
                                url: getContextPath() + "/rest/v1.0/workflow/process-instances",
                                contentType: "application/json;charset=utf-8",
                                dataType: "json",
                                data: JSON.stringify(workflowList),
                                success: function (data) {
                                    if (isIndex) {
                                        layer.msg('删除成功');
                                        renderTable(url, tableId, currentId);
                                    } else {
                                        layer.msg('删除成功，即将关闭当前页。');
                                        setTimeout(function(){ window.close(); }, 1000);
                                    }
                                }, error: function (e) {
                                    delAjaxErrorMsg(e);
                                }, complete: function () {
                                    removeModal();
                                }
                            });
                        }
                        ,btn2: function(index, layero){
                            //取消 的回调
                            layer.close(index);
                        }
                    });
                } else {
                    var logoutIndex = layer.confirm('您确定要删除吗？', {
                        title: '提示',
                        btn: ['确定', '取消'] //按钮
                    }, function (index) {
                        addModel('删除中');
                        layer.close(index);
                        $.ajax({
                            type: "DELETE",
                            url: getContextPath() + "/rest/v1.0/workflow/process-instances",
                            contentType: "application/json;charset=utf-8",
                            dataType: "json",
                            data: JSON.stringify(workflowList),
                            success: function (data) {
                                if (isIndex) {
                                    layer.msg('删除成功');
                                    renderTable(url, tableId, currentId);
                                } else {
                                    layer.msg('删除成功，即将关闭当前页。');
                                    setTimeout(function(){ window.close(); }, 1000);
                                }
                            }, error: function (e) {
                                delAjaxErrorMsg(e);
                            }, complete: function () {
                                removeModal();
                            }
                        });
                    });
                }
            }, error: function (e) {
                delAjaxErrorMsg(e);
            }, complete: function () {
                removeModal();
            }
        });
    }

//删除任务
    function deleteProcess(checkStatus, url, tableId, currentId, isIndex, type) {
        var workflowList = [];
        var checkResult = true;
        var gzResults = [];
        $.each(checkStatus.data, function (key, item) {
            if (!checkStaus(item, 2, '删除')) {
                checkResult = false;
                return checkResult;
            }
            checkResult = turnCheck(item, "sc");
            if (checkResult == true) {
                var workflow = {};
                workflow.processInstanceId = item.processInstanceId;
                if (isNullOrEmpty(item.processKey)) {
                    workflow.processDefKey = item.procDefKey;
                } else {
                    workflow.processDefKey = item.processKey;
                }
                workflow.slbh = item.slbh;
                workflowList.push(workflow);
            } else if (checkResult == false) {
                removeModal();
                return false;
            } else {
                removeModal();
                console.info(checkResult);
                $.each(checkResult, function (key, item) {
                    gzResults.push(item);
                });
            }
        });
        if (workflowList.length > 0) {
            if (type == 'back') {
                preformBack(workflowList, isIndex, url, tableId, currentId);
            } else {
                // 执行删除
                preformDelte(workflowList, isIndex, url, tableId, currentId);
            }
        }
        if (gzResults.length > 0) {
            // 渲染规则页面
            var tsxxHtml = loadTsxx(gzResults);
            rightScShowWarn(tsxxHtml,checkStatus.data, url, tableId, currentId, isIndex,checkStatus.data.processInstanceId);
        }
    }

    // 终止任务
    function stopProcess(checkStatus, url, tableId, currentId, isIndex){
        var workflowList = [];
        var checkResult = true;
        var gzResults = [];
        $.each(checkStatus.data, function (key, item) {
            if (!checkStaus(item, 2, '终止')) {
                checkResult = false;
                return checkResult;
            }
            checkResult = turnCheck(item,"sc");
            if (checkResult == true) {
                var workflow = {};
                workflow.processInstanceId = item.processInstanceId;
                if (isNullOrEmpty(item.processKey)) {
                    workflow.processDefKey = item.procDefKey;
                } else {
                    workflow.processDefKey = item.processKey;
                }
                workflow.slbh = item.slbh;
                workflowList.push(workflow);
            } else if (checkResult == false) {
                removeModal();
                return false;
            } else {
                removeModal();
                console.info(checkResult);
                $.each(checkResult, function (key, item) {
                    gzResults.push(item);
                });
            }
        });
        if (workflowList.length > 0) {
            // 执行删除
            preformStop(workflowList, isIndex, url, tableId, currentId);
        }
        if (gzResults.length > 0) {
            // 渲染规则页面
            var tsxxHtml = loadTsxx(gzResults);
            rightScShowWarn(tsxxHtml,checkStatus.data, url, tableId, currentId, isIndex,checkStatus.data.processInstanceId);
        }
    }
    // 任务终止前置操作
    function preformStop(workflowList, isIndex, url, tableId, currentId){
        $("#stopreason").val('');
        layer.open({
            title: '终止',
            type: 1,
            area: ['430px'],
            btn: ['确定', '取消'],
            content: $('#stop-reason'),
            yes: function(index, layero){
                var reason = $("#stopreason").val();
                if (isNullOrEmpty(reason)) {
                    layer.msg('请输入终止原因!');
                    return false;
                }
                layer.close(index);
                for (var i in workflowList) {
                    workflowList[i].reason = reason;
                }
                addModel('终止中');
                // 调用后台接口终止流程
                $.ajax({
                    type: "POST",
                    url: getContextPath() + "/rest/v1.0/workflow/process-instances/stopTask",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify(workflowList),
                    success: function (data) {
                        removeModal();
                        if (isIndex) {
                            layer.msg('终止成功');
                            renderTable(url, tableId, currentId);
                        } else {
                            layer.msg('终止成功，即将关闭当前页。');
                            setTimeout(function(){ window.close(); }, 1000);
                        }
                    }, error: function (e) {
                        removeModal();
                        delAjaxErrorMsg(e);;
                    }
                });
            },
            btn2: function(index, layero){
                layer.close(index);
            }
        });
    }


//激活流程
    function activeProcess(checkStatus, url, tableId, currentId) {
        var processInstanceIds = [];
        var checkResult = true;
        $.each(checkStatus.data, function (key, item) {
            if (!checkStaus(item, 4, '激活')) {
                checkResult = false;
                return checkResult;
            }
            processInstanceIds.push(item.processInstanceId);
        });
        if (!checkResult) {
            return checkResult;
        }
        //小弹出层
        layer.open({
            title: '任务激活',
            type: 1,
            area: ['430px'],
            btn: ['激活', '取消'],
            content: $('#bdc-popup-textarea')
            , yes: function (index, layero) {
                var reason = $("#gqjgyy").val();
                if (isNullOrEmpty(reason)) {
                    layer.msg('请输入激活原因');
                } else {
                    addModel('激活中');
                    // 激活当前任务
                    $.ajax({
                        type: "POST",
                        url: getContextPath() + "/rest/v1.0/workflow/process-instances/activate",
                        traditional:true,
                        data: {
                            processInstanceIds: processInstanceIds,
                            reason: reason
                        },
                        success: function (data) {
                            layer.close(index);
                            $("#gqjgyy").val("");
                            layer.msg(data);
                            renderTable(url, tableId, currentId);
                        }, error: function (e) {
                            delAjaxErrorMsg(e);
                        }, complete: function () {
                            removeModal();
                        }
                    });
                }
            }, btn2: function (index, layero) {
                $("#gqjgyy").val("");
            }, cancel: function () {
                $("#gqjgyy").val("");
            }, success: function () {
                $("#gqjgyyLab").html('<span class="required-span"><sub>*</sub></span>激活原因');
            }
        });
    }


//判断是否可以办结
    function isEnd(taskId, endType) {
        var isEnd = false;
        //获取转发按钮还是办结按钮
        $.ajax({
            type: "GET",
            async: false,
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/forward/isEnd",
            data: {
                taskId: taskId
            },
            success: function (data) {
                /**
                 * modified by lixin 2020/06/02
                 * 流程详情页面的办结直接走 endprocess 方法，其他判断是否可以办结根据 isNodeEnd 判断
                 */
                if (!isNullOrEmpty(endType) && (endType === "true" || endType === true)) {
                    isEnd = data.end || data.forceEnd;
                } else {
                    isEnd = data.end;
                }
            }, error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
        return isEnd;
    }

//办结
    function endProcess(checkData, url, tableId, currentId, isIndex) {
        var end = false;
        addModel('办结中');
        // 清空删除原因的内容
        $("#jsreason").val('');
        /**
         * 判断是否需要填写办结原因
         */
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/blmodify?gzlslid=" + checkData.processInstanceId,
            success: function (data) {
                if(data.bllc == true){
                    if(data.zssd == true){
                        var logoutIndex = layer.open({
                            title: '解锁证书',
                            type: 1,
                            area: ['430px'],
                            btn: ['确定', '取消'],
                            content: $('#js-reason')
                            , yes: function (index, layero) {
                                var reason = $("#jsreason").val();
                                if (isNullOrEmpty(reason)) {
                                    layer.msg('请输入解锁原因!');
                                    return false;
                                }
                                //提交 的回调
                                addModel('解锁中');
                                layer.close(index);
                                endBlModify(checkData, reason, url, tableId, currentId, isIndex);
                            }, btn2: function (index, layero) {
                                //取消 的回调
                                layer.close(index);
                            }
                        });
                    }else{
                        endBlModify(checkData, "数据修改办结", url, tableId, currentId, isIndex);
                    }
                }else{
                    endCommonProcess(checkData, url, tableId, currentId, isIndex);
                }
            },
            error: function (e) {
                delAjaxErrorMsg(e);
            },
            complete: function () {
                removeModal();
            }
        });
        return end;
    }

    function endCommonProcess(checkData, url, tableId, currentId, isIndex) {
        $.ajax({
            type: "POST",
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/end",
            data: {taskId: checkData.taskId},
            async: false,
            success: function () {
                if (isIndex == 'true') {
                    layer.msg("办结成功！");
                    renderTable(url, tableId, currentId);
                } else {
                    layer.msg('办结成功，即将关闭当前页。');
                    setTimeout(function () {
                        window.close();
                    }, 1000);
                    renderTable(url, tableId, currentId);
                }
            }, error: function (e) {
                delAjaxErrorMsg(e);
            }, complete: function () {
                removeModal();
            }
        });
    }

    function endBlModify(checkData, reason, url, tableId, currentId, isIndex) {
        $.ajax({
            type: "POST",
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/end/blmodify",
            data: {
                gzlslid: checkData.processInstanceId,
                taskId: checkData.taskId,
                jsyy: reason
            },
            async: false,
            success: function () {
                if (isIndex == 'true') {
                    layer.msg("办结成功！");
                    renderTable(url, tableId, currentId);
                } else {
                    layer.msg('办结成功，即将关闭当前页。');
                    setTimeout(function(){ window.close(); }, 1000);
                }
            }, error: function (e) {
                delAjaxErrorMsg(e);
            }, complete: function () {
                removeModal();
            }
        });
    }


    function checkStaus(checkData, staus, type) {
        var result = false;
        if ((checkData.state == 1 || checkData.state == 4 || isNullOrEmpty(checkData.state)) && (forbiddenActive.indexOf(staus) != -1)) {
            layer.msg('受理编号为' + checkData.slbh + '的流程已激活,不能进行' + type + '!');
        } else if (checkData.state == 2 && (forbiddenHangUp.indexOf(staus) != -1)) {
            layer.msg('受理编号为' + checkData.slbh + '的流程已挂起,不能进行' + type + '!');
        } else if (checkData.state == 3 && (forbiddenClock.indexOf(staus) != -1)) {
            layer.msg('受理编号为' + checkData.slbh + '的流程已办理,不能进行' + type + '!');
        } else if (isNullOrEmpty(checkData.state) && checkData.procStatus == 3) {//项目列表的特殊处理
            layer.msg('受理编号为' + checkData.slbh + '的流程已挂起,不能进行' + type + '!');
        } else {
            result = true;
        }
        return result;
    }

    /**
     * 规则验证
     * @param checkData
     * @returns {boolean}
     */
    function turnCheck(checkData,mbtype) {
        var check = false;
        // 项目列表参数处理
        if (isNullOrEmpty(checkData.processKey) && !isNullOrEmpty(checkData.procDefKey)) {
            checkData['processKey'] = checkData.procDefKey;
        }
        if (isNullOrEmpty(checkData.taskId)) {
            checkData['taskId'] = "";
        }
        if (isNullOrEmpty(checkData.processInstanceId)) {
            checkData['processInstanceId'] = checkData.procInsId;
        }
        // 参数校验
        if (isNullOrEmpty(checkData.processKey) && isNullOrEmpty(checkData.processInstanceId)) {
            warnMsg("验证规则，未获取到项目流程定义 key 和工作流实例 id");
            return false;
        }
        $.ajax({
            url: getContextPath() + '/rest/v1.0/check/gzyz/' + checkData.processKey + '/' + checkData.processInstanceId,
            type: 'POST',
            data: {mbtype: mbtype, taskid: checkData.taskId},
            async: false,
            success: function (data) {
                check = data.length == 0 ? true : data;
            }, error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
        return check;
    }

    /**
     * 显示必填项验证结果
     * @param data
     */
    function showBtxResult(data) {
        var msg = '';
        var bdmc = [];
        $.each(data, function (key, val) {
            msg += '<p>' + val.yzxx + '</p>';
            bdmc.push(val.bdmc);
        });
        //新增 点击 转发 弹出层
        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '错误提示',
            area: ['320px'],
            content: '<h3 style="line-height: 36px;">以下表单有为空项，请填写完整</h3><p>' + bdmc.join("、") + '</p>',
            btn: ['确定'],
            btnAlign: 'c',
            yes: function () {
                //确定操作
                layer.close(deleteIndex);
            }
        });
        //点击 转发 不为空内容提示
        $('.bdc-null-tips-box').removeClass('bdc-hide');
        //循环新增内容
        $('.bdc-null-tips-box .bdc-close').on('click', function () {
            $('.bdc-null-tips-box').addClass('bdc-hide');
        });
        $('#nullTips').html(msg);
    }

    /**
     * 验证必填项
     */
    function checkBtx(formViewKey, gzlslid, taskId) {
        var check = false;
        $.ajax({
            url: getContextPath() + '/rest/v1.0/check/btxyz/' + formViewKey + '/' + gzlslid,
            type: 'POST',
            data: {taskId: taskId},
            async: false,
            success: function (data) {
                if (data == '') {
                    check = true;
                } else {
                    showBtxResult(data);
                }
            }, error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
        return check;
    }

    function showHangReson(checkData) {
        var result = true;
        if (checkData.state == 2 || checkData.procStatus == 3) {
            $.ajax({
                url: getContextPath() + '/rest/v1.0/workflow/process-instances/opinion',
                type: 'POST',
                data: {
                    processInstanceId: checkData.processInstanceId,
                    type: 'suspend_opinion'
                },
                async: false,
                success: function (data) {
                    var name = data.userAlisa;
                    var opinion = data.opinion;
                    var msg = '流程已挂起';
                    if (name != '' && name != undefined) {
                        msg = msg + '，挂起人：' + name;
                    }
                    if (opinion != '' && opinion != undefined) {
                        msg = msg + '，挂起原因：' + opinion;
                    }
                    layer.msg(msg, {area: ['auto', 'auto']});
                }, error: function (e) {
                    delAjaxErrorMsg(e);
                }, complete: function () {
                    result = false;
                }
            });
        }
        return result;
    }

    function queryOpinion(processInstanceId, taskId, type) {
        var result = '';
        var user = queryUserType(type);
        $.ajax({
            url: getContextPath() + '/rest/v1.0/workflow/process-instances/opinion',
            type: 'POST',
            data: {taskId: taskId, processInstanceId: processInstanceId, type: type},
            async: false,
            success: function (data) {
                var opinion = data.opinion;
                if (opinion != '' && opinion != undefined) {
                    result += user.opinionType + '：' + opinion;
                }
            }, error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
        return result;
    }

    function lockProcess(checkData) {
        if (checkData.state != 3) {
            // 锁定当前任务
            $.ajax({
                type: "post",
                url: getContextPath() + "/rest/v1.0/workflow/process-instances/lock",
                data: {
                    taskId: checkData.taskId
                }, success: function (data) {
                },
                error: function (e) {
                }
            });
        }
    }

    function queryUserType(type) {
        var user = {};
        if (type == 'suspend_opinion') {
            user.userType = '挂起人';
            user.opinionType = '挂起原因';
        } else if (type == 'back_opinion') {
            user.userType = '退回人';
            user.opinionType = '退回原因';
        } else {
            user.userType = '';
            user.opinionType = '';
        }
        return user;
    }

    function loadTsxx(data) {
        var confirmInfo = '';
        var alertInfo = '';
        var showIgnoreAll=true;
        $.each(data, function (index, item) {
            if (item.yzlx === 1) {
                if (isNotBlank(item.tsxx) && item.tsxx.indexOf("SFLZ") > -1) {
                    var msg = item.tsxx.replace("SFLZ", "");
                    var rl_gzlslid = JSON.parse(item.requestParam).gzlslid;
                    // 添加 a 标签，防止 tsxx 为空时，出现压盖现象
                    confirmInfo += '<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + msg +
                        '<a href="javascrit:;" onclick="showMatchPage(this, ' + rl_gzlslid + ');return false" class="sureRemove">是</a>' +
                        '<a href="javascript:;" class="confirmRemove">忽略</a></p>';
                }else if(isNotBlank(item.tsxx) && item.tsxx.indexOf("WWSQTH") > -1){
                    var msg = item.tsxx.replace("WWSQTH", "");
                    // 添加 a 标签，防止 tsxx 为空时，出现压盖现象
                    confirmInfo += '<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + msg +
                        '<a href="javascript:;"  class="sureRemove lcsc">是</a>' +
                        '<a href="javascript:;" class="confirmRemove">否</a></p>';
                } else {
                    confirmInfo += '<p><img src="../static/lib/bdcui/images/warn.png" alt="">' + item.tsxx + '<a href="javascript:;" class="confirmRemove">忽略</a></p>';
                }
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
        tsxxHtml += '<div class="bdc-right-tips-box bdc-zfyz-tips" id="bottomTips">';
        tsxxHtml += alertInfo;
        tsxxHtml += confirmInfo;
        tsxxHtml += '</div>';
        return tsxxHtml;
    }

    //批量转发提示忽略信息
    function loadPlTsxx(data) {
        var tsxxHtml = '<a class="layui-btn layui-btn-sm bdc-ignore-btn" id="ignoreAll">忽略全部</a>' +
            '<div class="bdc-right-tips-box" >';
        data.forEach(function (v) {
            if(v.bdcGzyzVOS != null){
                var confirmInfo = '';
                var alertInfo = '';
                var isIgnore = true;
                v.bdcGzyzVOS.forEach(function (item) {
                    if (item.yzlx === 1) {
                        confirmInfo += '<p><img src="../static/lib/bdcui/images/warn.png" alt="">' + item.tsxx + '</p>';
                    } else if (item.yzlx === 3 || item.yzlx === 4 ) {
                        isIgnore = false;
                        // 添加 a 标签，防止 tsxx 为空时，出现压盖现象
                        alertInfo += '<p><img src="../static/lib/bdcui/images/error.png" alt="">' + item.tsxx + '<a href="javascript:;" ></a></p>';
                    }
                });
                if(isIgnore){
                    workflowPlList.forEach(function (value) {
                        if (value.slbh == v.slbh) {
                            value.ignore = true;
                            plIgnoreList.push(value);
                        }
                    });
                    tsxxHtml +=  '<div class="title-area bdc-ignore-content"><p class="tsxx-title">受理编号</p><span>'+ v.slbh +'</span>';
                }else {
                    tsxxHtml +=  '<div class="title-area"><p class="tsxx-title">受理编号</p><span>'+ v.slbh +'</span>';
                }
                tsxxHtml += alertInfo;
                tsxxHtml += confirmInfo;
                tsxxHtml += '</div>'
            }
        });

        tsxxHtml += '</div>';
        return tsxxHtml;
    }

    var warnLayer;

    function rightShowWarn(tsxxHtml, checkData, url, tableId, currentId, isIndex, processInstanceId, endType) {
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
                //id bottomTips与消息弹框id重复,导致pSize计算错误,换成class:bdc-zfyz-tips
                var pSize=$(".bdc-zfyz-tips > p").size();
                var confirmSize=$(".confirmRemove").size();
                var alertSize=pSize-confirmSize;
                $('.bdc-tips-right .bdc-right-tips-box .layui-icon-close').on('click', function () {
                    layer.close(warnLayer);
                });
                $('.confirmRemove').click(function () {
                    $(this).parent().remove();
                    //在没有警告提示下创建
                    if($(".bdc-zfyz-tips > p").size() == 0){
                        //点击忽略时增加日志记录，同步处理
                        var data = this.parentElement.innerText.replace(/忽略/g, "").replace(/查看/g, "");
                        addRemoveLog(data,processInstanceId);
                        addModel();
                        forward(checkData, url, tableId, currentId, isIndex, processInstanceId, endType);
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
                    forward(checkData, url, tableId, currentId, isIndex, processInstanceId, endType);
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
                    if($(".bdc-zfyz-tips > p").size() == 1){
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

    // 规则验证右侧提示窗， callbackFn 为忽略全部和无警告提示时，回调的函数方法
    function rightShowWarnCallBack(tsxxHtml, checkData, url, tableId, currentId, isIndex, processInstanceId, callbackFn){
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
                //id bottomTips与消息弹框id重复,导致pSize计算错误,换成class:bdc-zfyz-tips
                var pSize=$(".bdc-zfyz-tips > p").size();
                var confirmSize=$(".confirmRemove").size();
                var alertSize=pSize-confirmSize;
                $('.bdc-tips-right .bdc-right-tips-box .layui-icon-close').on('click', function () {
                    layer.close(warnLayer);
                });
                $('.confirmRemove').click(function () {
                    $(this).parent().remove();
                    //在没有警告提示下创建
                    if (alertSize == 0 && $(".confirmRemove").size() == 0) {
                        addModel();
                        callbackFn();
                        // 避免转发页面不展示将关闭操作放到后面执行
                        layer.close(warnLayer);
                    }
                });

                $('.sureRemove').click(function () {
                    if ($(".bdc-zfyz-tips > p").size() == 1) {
                        $(this).parent().remove();
                        if($(this).hasClass("lcsc")){
                            var data = [];
                            data.push(checkData);
                            var allData = {'data': data};
                            //流程删除
                            deleteProcess(allData,url,tableId,currentId,isIndex,"");
                        }
                        layer.close(warnLayer);
                    } else {
                        $(this).hide();
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
                    callbackFn();
                    // 避免转发页面不展示将关闭操作放到后面执行
                    layer.close(warnLayer);
                });

                setTimeout(function () {
                    $('.bdc-error-layer').css('opacity', 1)
                }, 500);
            }
        });
    }

    function rightRlShowWarn(tsxxHtml, checkStatus, url, tableId, currentId, isIndex, processInstanceId) {
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
                //id bottomTips与消息弹框id重复,导致pSize计算错误,换成class:bdc-zfyz-tips
                var pSize = $(".bdc-zfyz-tips > p").size();
                var confirmSize = $(".confirmRemove").size();
                var alertSize = pSize - confirmSize;
                $('.bdc-tips-right .bdc-right-tips-box .layui-icon-close').on('click', function () {
                    layer.close(warnLayer);
                });
                $('.confirmRemove').click(function () {
                    $(this).parent().remove();
                    //在没有警告提示下创建
                    if ($(".bdc-zfyz-tips > p").size() == 0) {
                        //点击忽略时增加日志记录，同步处理
                        var data = this.parentElement.innerText.replace(/忽略/g, "").replace(/查看/g, "");
                        addRemoveLog(data, processInstanceId);
                        addModel();
                        claimProcessDetail(checkStatus, url, tableId, currentId, isIndex);
                        // 避免转发页面不展示将关闭操作放到后面执行
                        layer.close(warnLayer);
                    }
                });

                $('.sureRemove').click(function () {
                    if ($(".bdc-zfyz-tips > p").size() == 1) {
                        $(this).parent().remove();
                        layer.close(warnLayer);
                    } else {
                        $(this).hide();
                    }
                });

                setTimeout(function () {
                    $('.bdc-error-layer').css('opacity', 1)
                }, 500);
            }
        });
    }

    /**
     * 右侧规则验证删除提示框
     */
    function rightScShowWarn(tsxxHtml, checkData, url, tableId, currentId, isIndex, processInstanceId) {
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
                //id bottomTips与消息弹框id重复,导致pSize计算错误,换成class:bdc-zfyz-tips
                var pSize=$(".bdc-zfyz-tips > p").size();
                var confirmSize=$(".confirmRemove").size();
                var alertSize=pSize-confirmSize;
                $('.bdc-tips-right .bdc-right-tips-box .layui-icon-close').on('click', function () {
                    layer.close(warnLayer);
                });
                $('.confirmRemove').click(function () {
                    $(this).parent().remove();
                    //在没有警告提示下创建
                    if (alertSize == 0 && $(".confirmRemove").size() == 0) {
                        addModel();
                        preformDelte(checkData, url, tableId, currentId, isIndex, processInstanceId);
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
                    preformDelte(checkData, url, tableId, currentId, isIndex, processInstanceId);
                    // 避免转发页面不展示将关闭操作放到后面执行
                    layer.close(warnLayer);
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

    exports("workflow", workflow);

    /**
     * 特殊规则验证
     * @param checkMb
     * @param gzlslid
     * @returns {boolean}
     */
    function tsGzYz(checkMb, gzlslid) {
        var check = false;
        $.ajax({
            url: getContextPath() + '/rest/v1.0/check/tsgzyz/'  + checkMb + '/' + gzlslid,
            type: 'GET',
            async: false,
            success: function (data) {
                check = data.length == 0 ? true : data;
            }, error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
        return check;
    }
});

//匹配界面
function showMatchPage(elem, gzlslid) {
    console.info(gzlslid);
    getReturnData("/rest/v1.0/task/xm", {gzlslid: gzlslid}, "GET", function (data) {
        var index = layer.open({
            type: 2,
            title: "数据匹配",
            area: ['1150px', '80%'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: 'http://' + document.location.host + '/realestate-accept-ui/view/query/matchData.html?lx=check',
            success: function (layero, index) {
                $(elem).parent().remove();
                console.info(data);
                sessionStorage.setItem('matchData', JSON.stringify(data));
            },
            cancel: function (layero, index) {
            }
        });
        layer.full(index);
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}


