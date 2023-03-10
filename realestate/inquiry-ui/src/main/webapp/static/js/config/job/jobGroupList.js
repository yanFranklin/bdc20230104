layui.use(['table', 'laytpl', 'laydate', 'layer', 'form', 'upload'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        upload = layui.upload,
        form = layui.form;
    var BASE_URL = '/realestate-inquiry-ui';
    var url = BASE_URL + "/job/group/page";

    //表格
    var unitTableTitle = [
        {type: 'checkbox', fixed: 'left'},
        {type: 'numbers', fixed:'left', title: '序号', width: 60 },
        {type: 'id', fixed:'left', title: 'id', width: 60, hide: true },
        {field: 'appname', title: 'appname'},
        {field: 'title', sort: true, title: '执行器名称', align: 'center', width: 350, style: 'text-align:left'},
        {field: 'addresslist', title: 'OnLine 机器地址', align: 'center', style: 'text-align:left', minWidth: 250},
        {field: 'addresstype', title: '注册方式', align: 'center', width: 100,
            templet: function (d) {
                return formatZcfs(d.addresstype);
            }
        },
        // {field: 'pzr', sort: true, title: '配置人', align: 'center', width: 120},
        {
            field: 'updatetime', sort: true, title: '配置日期', align: 'center', width: 200,
            templet: function (d) {
                return format(d.updatetime);
            }
        },
        {title: '操作', fixed: 'right', toolbar: '#barDemo', width: 130}
    ]

    function formatZcfs(addresstype){
        if(isNullOrEmpty(addresstype)){
            return "";
        } else if(addresstype == 0) {
            return '<span class="" style="color:blue;">自动注册</span>';
        } else if(addresstype == 1) {
            return '<span class="" style="color:red;">手动录入</span>';
        }else {
            return yxj;
        }
    }

    var tableConfig = {
        id: 'id',
        toolbar: "#toolbar",
        defaultToolbar: ["filter"],
        cols: [unitTableTitle]
    }
    //加载表格
    loadDataTablbeByUrl('#jobGroupTable', tableConfig);
    //表格初始化
    table.init('jobGroupTable', tableConfig)
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
    table.on('toolbar(jobGroupTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addJobGroup();
                break;
            case 'edit':
                editJobGroup(checkStatus.data);
                break;
            case 'delete':
                deleteJobGroup(checkStatus.data);
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
    table.on('rowDouble(jobGroupTable)', function (obj) {
        var array = new Array();
        array.push(obj.data);

        editJobGroup(array);
    });


    //监听工具条
    table.on('tool(barDemo)', function (obj) {
        var data = obj.data;
        if (isNullOrEmpty(data.gzid)) {
            warnMsg(" 没有信息，无法查看！");
            return;
        }
        if (obj.event === 'editJobGroup') {
            // window.open("zhgz.html?gzid="+data.gzid);
            editJobGroup(data);
        }
        if (obj.event === 'deleteJobGroup') {
            deleteJobGroup(data);
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
    function addJobGroup() {
        layer.open({
            type: 2,
            title: '新增执行器',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '400px'],
            offset: 'auto',
            content: ["jobGroupEdit.html" , 'yes'],
            end: function () {
                tableReload('id', null, url);
            }
        });
    }

    /**
     * 编辑执行器
     * @param data
     */
    function editJobGroup(data){
        if(!data || data.length != 1){
            layer.alert("<div style='text-align: center'>请选择需要编辑的某一条记录！</div>", {title: '提示'});
            return;
        }
        layer.open({
            type: 2,
            shade: false,
            shadeClose: true,
            isOutAnim: false,
            content: "../job/jobGroupEdit.html?jobGroupId=" + data[0].id,
            area: ['660px', '400px'],
            title: '修改执行器配置',
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
    function deleteJobGroup(data){
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
                // 保存日志
                var jobGroupIds = new Array();
                $.each(data, function (key, value) {
                    jobGroupIds.push(value.id);
                });
                // saveLogs(zgzIds, "jobGroupSC", "jobGroup配置-删除");

                $.ajax({
                    url: "/realestate-inquiry-ui/job/group/remove?id=" +  jobGroupIds[0],
                    type: "POST",
                    // data: JSON.stringify(jobGroupIds),
                    // contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 200) {
                            layer.open({
                                title: '系统提示' ,
                                btn: [ '确定' ],
                                content: '删除执行器成功',
                                icon: '1',
                                end: function () {
                                    tableReload('id', null, url);
                                }
                            });
                        } else {
                            layer.open({
                                title: '系统提示',
                                btn: [ '确定' ],
                                content: (data.msg || "删除执行器成功"),
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