var thyjData = [];
layui.use(['form', 'laytpl', 'jquery','table'], function () {
    var $ = layui.jquery,
        table = layui.table;
    var processInstanceId = $.getUrlParam('processInstanceId');
    var taskId = $.getUrlParam('taskId');
    $(function () {
        $.ajax({
            type: "POST",
            url: getContextPath() + "/rest/v1.0/workflow/process-instances/range/opinions",
            data: {
                processInsId: processInstanceId,
                taskId: taskId,
                types: "back_opinion,next_back_opinion",
                rangeType: 1,
            },
            sync: false,
            success: function (data) {
                thyjData = data;
                thyjData.forEach(function (value){
                    if(value.type==='back_opinion'){
                        value.type = '下个节点退回';
                    }else{
                        value.type = '本节点退回';
                    }
                })
                table.render({
                    elem: '#thyjTable'
                    ,id: 'thyjTable'
                    ,page: true //开启分页
                    ,data: thyjData
                    ,type: 'POST'
                    ,even: true
                    ,autoSort: true
                    ,initSort: {
                        field:'time,type'
                        ,type:'desc'
                    }
                    ,cols: [[ //表头
                        {field: 'opinion', title: '退回意见',width:'40%'}
                        ,{field: 'type', title: '退回类型',width:'20%'}
                        ,{field: 'time', title: '退回时间',width:'25%'}
                        ,{field: 'userAlisa', title: '退回人',width:'15%'}
                    ]]
                    ,request: {
                        limitName: 'size' //每页数据量的参数名，默认：limit
                    }
                    ,done: function () {
                    }
                });
                //头工具栏事件
                table.on('toolbar(thyjTable)', function (obj) {
                    var checkStatus = table.checkStatus(obj.config.id);
                    var data = checkStatus.data;
                    switch (obj.event) {
                        case 'choose':
                            addPz();
                            break;
                    }
                });
            }, error: function (e) {
                response.fail(e, '');
            }
        });
    })
});