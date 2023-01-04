/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description （常州）退回详情页面
 */
layui.use(['jquery', 'table', 'element', 'form', 'laytpl'], function () {
    var $ = layui.jquery,
        table = layui.table,
        laytpl = layui.laytpl,
        response = layui.response,
        form = layui.form;

    $(function () {
        //获取地址栏参数
        var $paramArr =  getIpHz();

        var getName = $paramArr['name'];
        //获取session数据
        var lcArray = '',
            taskId = $paramArr['taskId'];
        if (sessionStorage.getItem('lcArray' + getName)) {
            lcArray = JSON.parse(sessionStorage.getItem('lcArray' + getName));
        }
        var isIndex= $paramArr['isIndex'];
        var currentId= $paramArr['currentId'];

        //获取表格选择类型
        var col = [[
            {type: 'radio', width: 50, fixed: 'left'},
            {field: 'activityId', title: '', hide: true},
            {field: 'taskId', title: '', hide: true},
            {field: 'activityName', title: '退回节点'}
        ]]
        addModel();
        //获取转发内容
        $.ajax({
            type: "GET",
            async: false,
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/nantong/back/userTasks",
            data: {
                taskId: taskId
            },
            success: function (data) {
                if (data) {
                    if(data.length == 1){
                        data[0].LAY_CHECKED = true;
                    }
                    renderZfTable(data, col);
                } else {
                    $('#backContainer').addClass('bdc-hide');
                    $('#backOpinionContainer').removeClass('bdc-hide');
                }
            },
            error: function (e) {
                response.fail(e,'');
            },complete:function(){
                removeModal();
            }
        });

        function renderZfTable(data, col) {
            table.render({
                elem: '#checkboxTable',
                id: 'checkBoxTableId',
                cols: col,
                data: data
            });
        }

        //点击退回
        $('.bdc-back-btn').on('click', function () {
            var checkStatus = table.checkStatus('checkBoxTableId'); //idTest 即为基础参数 id 对应的值
            var backTaskDtos = checkStatus.data;
            if(!backTaskDtos || backTaskDtos.length == 0){
                warnMsg("请选择需要退回的节点!");
                return;
            }

            var success = '退回成功！';
            var opinion = $('.backopinion').val();
            // 所有流程的退回意见取消必填验证，此段代码保留
            var thyjCheck;
            $.ajax({
                url: getContextPath() + "/rest/v1.0/workflow/process-instances/getThyjCheck",
                dataType: "json",
                async: false,
                success: function (data) {
                    thyjCheck = data;
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });

            if (thyjCheck == '1'){
                if(isNullOrEmpty(opinion)){
                    layer.msg('请输入退回意见');
                    return;
                }
            }
            addModel('退回中');
            backTaskDtos.forEach(function (value) {
                value.taskId = taskId;
                value.opinion = opinion;
            });

            var backNodesDto = {};
            // lixin 判断选择的节点是第几个
            var index = $('.layui-form-radioed').parents('tr').index();
            var totalIndex = $('.layui-form-radioed').parents('table').find('tr').length;
            if (totalIndex > 1 && index == 0) {
                // 可以退回首节点，并且选择了首节点
                backNodesDto.firstNodeList = backTaskDtos;
            } else {
                backNodesDto.prevNodeList = backTaskDtos;
            }
            $.ajax({
                type: "POST",
                contentType: "application/json;charset=utf-8",
                url: getContextPath() + "/rest/v1.0/workflow/process-instances/back/general",
                data: JSON.stringify(backNodesDto),
                success: function () {
                    layer.msg(success);
                    setTimeout(function () {
                        //首页的
                        if(isIndex=='true'){
                            closeWin();
                            window.parent.renderTable('','',currentId);
                        }else{
                            window.parent.close();
                        } }, 1000);
                }, error: function (e) {
                    response.fail(e,'');
                },complete:function(){
                    removeModal();
                }
            });
        });

        /**
         * 关闭弹出页面
         */
        $('.bdc-back-cancel').on('click', function () {
            closeWin();
        })

    });
});