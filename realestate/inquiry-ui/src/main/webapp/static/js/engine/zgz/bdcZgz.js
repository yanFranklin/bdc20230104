/**
 * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
 * @description 子规则js
 */
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form', 'upload'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        upload = layui.upload,
        form = layui.form;
    var BASE_URL = '/realestate-inquiry-ui';
    var url = BASE_URL + "/zgz/page";

    //子规则表格
    var unitTableTitle = [
        {type: 'checkbox', fixed: 'left'},
        {type: 'numbers', fixed:'left', title: '序号', width: 60 },
        {field: 'gzid', title: '规则id', hide: true},
        {field: 'gzmc', sort: true, title: '规则名称', align: 'center', width: 350, style: 'text-align:left'},
        {field: 'ytsm', title: '用途说明', align: 'center', style: 'text-align:left', minWidth: 250},
        {field: 'yxj', title: '验证级别', align: 'center', width: 100,
            templet: function (d) {
                return formatYxj(d.yxj);
            }
        },
        {field: 'pzr', sort: true, title: '配置人', align: 'center', width: 120},
        {
            field: 'pzrq', sort: true, title: '配置日期', align: 'center', width: 200,
            templet: function (d) {
                return format(d.pzrq);
            }
        },
        {title: '查看', fixed: 'right', toolbar: '#barDemo', width: 130}
    ]

    function formatYxj(yxj){
        if(isNullOrEmpty(yxj)){
            return "";
        } else if(yxj == 1) {
            return '<span class="" style="color:blue;">忽略</span>';
        } else if(yxj == 3) {
            return '<span class="" style="color:red;">警告</span>';
        } else if(yxj == 4) {
            return '<span class="" style="color:limegreen;">例外</span>';
        }else {
            return yxj;
        }
    }

    var tableConfig = {
        id: 'gzid',
        toolbar: "#toolbar",
        defaultToolbar: ["filter"],
        cols: [unitTableTitle]
    }
    //加载表格
    loadDataTablbeByUrl('#zgzTable', tableConfig);
    //表格初始化
    table.init('zgzTable', tableConfig)
    tableReload('gzid', null, url);
    // 加载上传子规则事件绑定
    importZgz();

    //查询表单信息
    form.on("submit(search)", function (data) {
        tableReload('gzid', data.field, url);

        // 加载上传子规则事件绑定
        importZgz();
        return false;
    });

    /**
     * 重置
     */
    $('#reset').on('click', function () {
        tableReload('gzid', null, url);
        // 加载上传子规则事件绑定
        importZgz();
    });

    //头工具栏事件
    table.on('toolbar(zgzTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addBdcZgz();
                break;
            case 'edit':
                editBdcZgz(checkStatus.data);
                break;
            case 'delete':
                deleteBdcZgz(checkStatus.data);
                break;
            case 'copy':
                copyBdcZgz(checkStatus.data);
                break;
            case 'export':
                exportBdcZgz(checkStatus.data);
                break;
        }
        ;
    });

    /**
     * 行双击编辑
     */
    table.on('rowDouble(zgzTable)', function (obj) {
        var array = new Array();
        array.push(obj.data);

        editBdcZgz(array);
    });


    //监听工具条
    table.on('tool(zgzTable)', function (obj) {
        var data = obj.data;
        if (isNullOrEmpty(data.gzid)) {
            warnMsg(" 没有组合规则信息，无法查看！");
            return;
        }
        if (obj.event === 'glzhgz') {
            window.open("zhgz.html?gzid="+data.gzid);
        }
    });

    //回车事件
    $("body").keydown(function () {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $('#search').click();
        }
    });

    /**
     * 新增
     */
    function addBdcZgz(){
        window.open("addBdcZgz.html");
    }

    /**
     * 编辑
     * @param data
     */
    function editBdcZgz(data){
        if(!data || data.length != 1){
            layer.alert("<div style='text-align: center'>请选择需要编辑的某一条记录！</div>", {title: '提示'});
            return;
        }
        window.open("editBdcZgz.html?gzid=" + data[0].gzid);
    }

    /**
     * 删除
     * @param data
     */
    function deleteBdcZgz(data){
        if(!data || data.length == 0){
            layer.alert("<div style='text-align: center'>请选择需要删除的记录！</div>", {title: '提示'});
            return;
        }

        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            content: '确定要删除所选规则？',
            btn: ['确定','取消'],
            skin: 'bdc-small-tips',
            btnAlign: 'c',
            yes: function(){
                // 保存日志
                var zgzIds = new Array();
                $.each(data, function (key, value) {
                    zgzIds.push(value.gzid);
                });
                saveLogs(zgzIds, "ZGZSC", "子规则配置-删除");

                $.ajax({
                    url: "/realestate-inquiry-ui/zgz",
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">删除成功');
                        tableReload('gzid', null, url);
                        // 加载上传子规则事件绑定
                        importZgz();
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
     * 子规则复制
     * @param data
     */
    function copyBdcZgz(data){
        if(!data || data.length == 0 || data.length > 1){
            layer.alert("<div style='text-align: center'>请选择需要复制的一条记录！</div>", {title: '提示'});
            return;
        }
        //获取data[0].gzid,发送ajax请求，进行后台数据拷贝。
        $.ajax({
            url: BASE_URL + "/zgz/copyBdcGzZgz/"+data[0].gzid,
            type: 'POST',
            contentType: 'application/json',
            dataType: "text",
            success: function (result) {
                if(!isNullOrEmpty(result)){
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">复制成功');
                    tableReload('gzid', null, url);
                    // 加载上传子规则事件绑定
                    importZgz();
                } else {
                    layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">复制失败，请重试');
                }
            },
            error: function ($xhr, textStatus, errorThrown) {
                layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">复制失败，请重试');
            }
        });

        saveLog(data[0].gzid, "ZGZFZ", "子规则配置-复制");
    }

    /**
     * 子规则导出
     * @param data
     */
    function exportBdcZgz(data){
        if(!data || data.length == 0){
            layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要导出的记录');
            return;
        }

        $("#filedata").val(JSON.stringify(data));
        $("#zgzfile").submit();

        // 保存日志
        var zgzIds = new Array();
        $.each(data, function (key, value) {
            zgzIds.push(value.gzid);
        });
        saveLogs(zgzIds, "ZGZDC", "子规则配置-导出");
    }

    // 导入子规则文件
    function importZgz(){
        upload.render({
            elem: '#import' //绑定元素
            ,url: BASE_URL + '/zgz/import' //上传接口
            ,accept: 'file'
            ,acceptMime: 'file/txt'
            ,exts: 'txt'
            ,before: function(obj){
                addModel();
            }
            ,done: function(res){
                removeModal();
                //上传完毕回调
                if(res.code == 0){
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">导入成功，即将刷新页面');
                    setTimeout(function () {
                        location.reload();
                    }, 500);

                    // 保存日志
                    saveLogs(res.content, "ZGZDR", "子规则配置-导入");
                } else {
                    layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">导入失败，请选择正确文件！');
                }
            }
            ,error: function(){
                removeModal();
                layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">导入失败，请选择正确文件！');
            }
        });
    }

    /**
     * 保存操作日志
     * @param zgzid 子规则ID
     * @param key 日志类型标识
     * @param content 日志类型描述
     */
    function saveLog(zgzid, key, content) {
        $.ajax({
            url: "/realestate-inquiry-ui/zgz/queryBdcGzZgzDTOByGzid",
            type: 'GET',
            contentType: 'application/json',
            data: {"gzid": zgzid},
            dataType: "json",
            success: function (data) {
                if (data) {
                    saveDetailLog(key, content, {"GZNR": JSON.stringify(data)});
                }
            }
        });
    }

    /**
     * 保存多个子规则操作日志
     * @param zgzids 子规则ID集合
     * @param key 日志类型标识
     * @param content 日志类型描述
     */
    function saveLogs(zgzids, key, content) {
        var zgzArray = new Array();

        $.each(zgzids, function (key, zgzid) {
            var zgzDTO = getZgzDTO(zgzid);
            if(null != zgzDTO){
                zgzArray.push(zgzDTO);
            }
        });

        saveDetailLog(key, content, {"GZNR": JSON.stringify(zgzArray)});
    }

    /**
     * 根据子规则ID获取规则信息
     * @param zgzid 子规则ID
     */
    function getZgzDTO(zgzid) {
        var zgzDTO = null;
        $.ajax({
            url: "/realestate-inquiry-ui/zgz/queryBdcGzZgzDTOByGzid",
            type: 'GET',
            contentType: 'application/json',
            data: {"gzid": zgzid},
            async: false,
            dataType: "json",
            success: function (data) {
                if (data) {
                    zgzDTO = data;
                }
            }
        });
        return zgzDTO;
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