var searchData, searchFilterData, needsearch, needFilter;
// 当前页数据
var currentPageData = new Array();

layui.use(['form', 'jquery', 'laydate', 'element', 'table', 'laytpl'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laytpl = layui.laytpl,
        laydate = layui.laydate;
    $(function () {

        // 加载Table
        table.render({
            elem: '#pageTable',
            toolbar: '#toolbarDemo',
            url: "/realestate-inquiry-ui/rest/v1.0/dwgz/list",
            title: '单位公章列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'dwmc', width: 250, title: '单位名称'},
                {
                    field: 'basj', width: 180, title: '备案时间',
                    templet: function (d) {
                        return formatDate(d.basj);
                    }
                },
                {field: 'qksm',minWidth: 250, title: '情况说明'},
                {field: 'cz', title: '操作', width: 150, templet: '#barDemo', align: "center",fixed:"right"}
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                // 设置选中行
                if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.xmid) {
                                v.LAY_CHECKED = true;
                            }
                        })
                    });
                }

                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                setHeight();
            }
        });

        //头工具栏事件
        table.on('toolbar(pageTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'add':
                    addDwgz();
                    break;
                case 'edit':
                    editDwgz(checkStatus.data);
                    break;
            }
        });

        //监听工具条
        table.on('tool(pageTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'fjck') {
                uploadFj(data);
            }
        });

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        function search() {
            // 获取查询内容
            var dwmc = $('#dwmc').val();
            var obj = {'dwmc':dwmc};
            addModel();
            // 重新请求
            table.reload("pageTable", {
                where: obj,
                page: {
                    curr: 1,  //重新从第 1 页开始
                },
                done: function (res, curr, count) {
                    removeModal();
                    if(res.code ==0){
                        currentPageData = res.data;
                        setHeight();
                    }else{
                        $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + res.msg);
                    }
                }
            });
        }

        /**
         * 新增
         */
        function addDwgz() {
            addModel('');
            layer.open({
                title: '新增单位公章',
                type: 2,
                area: ['440px', '500px'],
                content: ["addOrEditDwgz.html?action=add", 'yes']
                , end: function () {
                    removeModal();
                    table.reload('pageTable');
                }
            });
        }

        /**
         * 编辑
         * @param data
         */
        function editDwgz(data) {
            if (!data || data.length != 1) {
                layer.alert("请选择需要编辑的某一条记录！", {title: '提示'});
                return;
            }
            addModel('');
            layer.open({
                type: 2,
                title: '编辑单位公章',
                anim: -1,
                shadeClose: true,
                maxmin: true,
                shade: false,
                area: ['440px', '500px'],
                offset: 'auto',
                content: ["addOrEditDwgz.html?action=edit&dwmc=" + encodeURI(data[0].dwmc) + "&id=" + data[0].id, 'yes'],
                success: function (layero, index) {
                    // var iframe = window['layui-layer-iframe' + index];
                    // iframe.setData(data[0]);
                },
                end: function () {
                    removeModal();
                    table.reload('pageTable');
                }
            });
        }

    });
});


/**
 *  附件上传
 */
function uploadFj(data){
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/dwgz/folder',
        type: 'GET',
        async: false,
        contentType: 'application/json;charset=utf-8',
        data: {spaceId: data.id, wjjmc: data.dwmc},
        success: function (data) {
            if (isNullOrEmpty(data)) {
                layer.msg("文件夹生成失败");
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

    var bdcSlWjscDTO = queryBdcSlWjscDTO(data);
    bdcSlWjscDTO.spaceId = data.id;
    bdcSlWjscDTO.storageUrl =document.location.protocol + "//" + document.location.host + "/storage";
    //bdcSlWjscDTO.nodeId = data.fjcl;
    var url = "/realestate-inquiry-ui/bengbu/dwgzcx/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
    openSjcl(url, "附件上传");
}

//查询收件材料所需参数（打开附件上传使用）
function queryBdcSlWjscDTO(data) {
    var bdcSlWjscDTO = {};
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/dwgz/getFileCs',
        type: 'GET',
        async: false,
        contentType: 'application/json;charset=utf-8',
        data: {processInsId: data.id},
        success: function (data) {
            if (isNotBlank(data)) {
                bdcSlWjscDTO = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return bdcSlWjscDTO;
}

//打开附件上传
function openSjcl(url, title, xmid) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true
    });
    layer.full(index);
}

