layui.use(['table', 'laytpl', 'laydate', 'layer', 'form', 'upload'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        upload = layui.upload,
        form = layui.form;
    var BASE_URL = '/realestate-inquiry-ui';
    var url = BASE_URL + "/job/info/page";

    $.ajax({
        url: '/realestate-inquiry-ui/job/group/all',
        type: "GET",
        dataType: "json",
        timeout : 10000,
        success: function(data) {
            if(data && data.length > 0){
                // 渲染字典项
                $.each(data, function(index,item) {
                    $('#jobGroup').append('<option value="'+item.id +'">'+item.title + '</option>');
                });
            }

            form.render('select');
            // 下拉选择添加删除按钮
            renderSelectClose(form);
        }
    });


    //表格
    var unitTableTitle = [
        {type: 'checkbox', fixed: 'left'},
        // {type: 'numbers', fixed:'left', title: '序号', width: 60 },
        {field: 'id',  fixed:'left', title: '任务id', width: 200  },
        {field: 'jobDesc', title: '任务描述'},
        //
        {field: 'scheduleType', sort: true, title: '调度类型', align: 'center', width: 200, style: 'text-align:left'},
        {field: 'scheduleConf', title: '调度配置', align: 'center', style: 'text-align:left', minWidth: 360},

        {field: 'glueType', title: '运行模式', align: 'center', style: 'text-align:left', minWidth: 250},
        {field: 'executorHandler', title: '运行配置', align: 'center', style: 'text-align:left', minWidth: 250},
        {field: 'executorParam', title: '任务参数', align: 'center', style: 'text-align:left', minWidth: 250},

        {field: 'author', title: '负责人', align: 'center', style: 'text-align:left', minWidth: 250},
        {field: 'triggerStatus', title: '运行状态', align: 'center', width: 100,
            templet: function (d) {
                return formatYxzt(d.triggerStatus);
            }
        },
        {
            field: 'updatetime', sort: true, title: '配置日期', align: 'center', width: 200,
            templet: function (d) {
                return format(d.updatetime);
            }
        },
        {title: '操作', fixed: 'right', toolbar: '#barDemo', width: 360}
    ]

    function formatYxzt(triggerStatus){

        if(triggerStatus == 0) {
            return '<span class="" style="color:blue;">stopped</span>';
        } else if(triggerStatus == 1) {
            return '<span class="" style="color:red;">running</span>';
        }else if(isNullOrEmpty(triggerStatus)){
            return "";
        }else {
            return triggerStatus;
        }
    }

    var tableConfig = {
        id: 'id',
        toolbar: "#toolbar",
        defaultToolbar: ["filter"],
        cols: [unitTableTitle]
    }
    //加载表格
    loadDataTablbeByUrl('#jobInfoTable', tableConfig);
    //表格初始化
    table.init('jobInfoTable', tableConfig)
    tableReload('id', null, url);
    //查询表单信息
    form.on("submit(search)", function (data) {
        tableReload('id', data.field, url);
        return false;
    });

    /**
     * 重置
     */
    $('#reset').on('click', function () {
        tableReload('gzid', null, url);
        // 加载上传子规则事件绑定
        // importZgz();
    });

    //头工具栏事件
    table.on('toolbar(jobInfoTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addJobInfo();
                break;
            case 'edit':
                editJobInfo(checkStatus.data);
                break;
            case 'delete':
                deleteJobInfo(checkStatus.data);
                break;
            case 'copy':
                copyJobGroup(checkStatus.data);
                break;
            case 'export':
                exportJobGroup(checkStatus.data);
                break;
        }
        ;
    });

    /**
     * 行双击编辑
     */
    table.on('rowDouble(jobInfoTable)', function (obj) {
        var array = new Array();
        array.push(obj.data);
        startJobInfo(array);

        // editJobInfo(array);
    });


    //监听工具条
    table.on('tool(barDemo)', function (obj) {
        var data = obj.data;
        if (isNullOrEmpty(data.gzid)) {
            warnMsg(" 没有信息，无法查看！");
            return;
        }
        if (obj.event === 'startJobInfo') {
            // window.open("zhgz.html?gzid="+data.gzid);
            startJobInfo(data);
        }
        if (obj.event === 'triggerJobInfo') {
            triggerJobInfo(data);
        }
    });

    //回车事件
    $("body").keydown(function () {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $('#search').click();
        }
    });


    /**
     * 新增执行器
     */
    function addJobInfo() {
        layer.open({
            type: 2,
            title: '新增任务',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '400px'],
            offset: 'auto',
            content: ["jobInfoEdit.html" , 'yes'],
            end: function () {
                tableReload('id', null, url);
            }
        });
    }

    /**
     * 编辑执行器
     * @param data
     */
    function editJobInfo(data){
        if(!data || data.length != 1){
            layer.alert("<div style='text-align: center'>请选择需要编辑的某一条记录！</div>", {title: '提示'});
            return;
        }
        layer.open({
            type: 2,
            shade: false,
            shadeClose: true,
            isOutAnim: false,
            content: "../job/jobInfoEdit.html?jobInfoId=" + data[0].id,
            // content: ["jobInfoEdit.html", 'yes'],
            area: ['660px', '400px'],
            title: '修改任务配置',
            // success: function (layero, index) {
            //     var iframe = window['layui-layer-iframe' + index];
            //     iframe.setData(data[0]);
            // },
            end: function () {
                tableReload('id', null, url);
            }
        });

        // window.open("jobGroupEdit.html?id=" + data[0].id);
    }


    /**
     * 删除 执行器
     * @param data
     */
    function deleteJobInfo(data){
        if(!data || data.length == 0){
            layer.alert("<div style='text-align: center'>请选择需要删除的记录！</div>", {title: '提示'});
            return;
        }

        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            content: '确定要删除？',
            btn: ['确定','取消'],
            skin: 'bdc-small-tips',
            btnAlign: 'c',
            yes: function(){
                var jobGroupIds = new Array();
                $.each(data, function (key, value) {
                    jobGroupIds.push(value.id);
                });

                $.ajax({
                    url: "/realestate-inquiry-ui/job/info/remove?id=" +  jobGroupIds[0],
                    type: "POST",
                    // data: JSON.stringify(jobGroupIds),
                    // contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 200) {
                            layer.open({
                                title: '系统提示' ,
                                btn: [ '确定' ],
                                content: '删除任务v成功',
                                icon: '1',
                                end: function () {
                                    tableReload('id', null, url);
                                }
                            });
                        } else {
                            layer.open({
                                title: '系统提示',
                                btn: [ '确定' ],
                                content: (data.msg || "删除任务成功"),
                                icon: '2'
                            });
                        }
                        // layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">删除成功');
                        // tableReload('gzid', null, url);
                        // 加载上传子规则事件绑定
                        // importZgz();
                    },
                    error:function($xhr,textStatus,errorThrown){
                        fail();
                    }
                });

                layer.close(deleteIndex);
            },
            btn2: function(){
                //取消
            }
        });
    }

    function startJobInfo(data) {
        var obj = data;
        $.ajax({
            url: "/realestate-inquiry-ui/job/info/start",
            type: 'GET',
            dataType: 'json',
            async: false,
            data: {id: data[0].id},
            success: function (data) {
                // if (moduleCode){
                //     setElementAttrByModuleAuthority(moduleCode);
                // }

                // form.val("searchform", data.content);
                // jobGroupId = data.content.jobGroup;
                // form.render();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        })


    }
    /**
     * 提交成功提示
     */
    window.success = function(){
        layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功');
    }

    /**
     * 失败提示
     * @param data
     */
    window.fail = function(data){
        layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">删除失败，请重试');
    }

    window.closeParentWindow = function () {
        var index = parent.parent.layer.getFrameIndex(window.name);
        parent.parent.layer.close(index);
    };

});