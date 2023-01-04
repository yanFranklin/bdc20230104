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
    var url = BASE_URL + "/gdzgz";

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
        }
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

    //查询表单信息
    form.on("submit(search)", function (data) {
        tableReload('gzid', data.field, url);

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
        window.open("addBdcGdZgz.html");
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

                $.ajax({
                    url: "/realestate-inquiry-ui/gdzgz",
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">删除成功');
                        tableReload('gzid', null, url);
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

    function loadDataTablbeByUrl(_domId, _tableConfig) {
        layui.use(['table', 'jquery'], function () {
            if (_domId) {
                var table = layui.table;
                var $ = layui.jquery;
                var tableDefaultConfig = {
                    elem: _domId,
                    toolbar: "#toolbarDemo",
                    cellMinWidth: 80,
                    page: false,  //开启分页
                    limit: 10000,
                    even: true,
                    done: function () {
                        $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

                        if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                            $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
                        }else {
                            $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                        }
                    }
                }
                if (!_tableConfig.cols || !_tableConfig.cols[0] || _tableConfig.cols[0].length < 1) {
                    _tableConfig.cols = [[]]
                }
                var tableRenderConfig = $.extend({}, tableDefaultConfig, _tableConfig)
                table.render(tableRenderConfig);
            }
        });
    }

    function tableReload(tableid, postData,url) {
        layui.use(['table'], function () {
            var table = layui.table;
            table.reload(tableid, {
                url:url,
                where: postData,
                page: false
            });
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