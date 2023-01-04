/*获取字典数据*/

var table;
var form;
var laydate;
var baid = getQueryString("baid");
var fwdcbindex = getQueryString("fwdcbindex");
/*table数据加载*/
$(function () {

    layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
        table = layui.table;
        form = layui.form;
        laydate = layui.laydate;
        //不动产单元号的表头
        var unitTableTitle = [
            {type: 'checkbox', fixed: 'left'},
            {field: 'FWID', title: '', hide: true},
            {field: 'FW_DCB_INDEX', title: '', hide: true},
            {field: 'ZL', title: '坐落', width: "30%"},
            {field: 'FJH', title: '房间号', width: "20%"},
            {field: 'SCJZMJ', title: '建筑面积', width: "10%"},
            {field: 'BDCDYH', title: '不动产单元号', width: "30%"},
            {field: 'cz', title: '操作', width: "7.5%", templet: '#hsczTpl', fixed: "right"}
        ];
        var url = getContextPath() + '/rest/v1.0/lpb/hs';
        var bdcSlBdcdyhQO = form.val("searchform");
        bdcSlBdcdyhQO.fwdcbindex = fwdcbindex;
       /* var tableConfig = {
            id: 'fwid',
            url: url,
            //where: {bdcSlBdcdyhQO: JSON.stringify(bdcSlBdcdyhQO)},
            defaultToolbar: ['filter'],
            page: true,
            toolbar: '#toolbarDemo',
            cols: [unitTableTitle],
            done: function (res, curr, count) {
                $(".bdc-line-search-container").css("padding-top", '61px');
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() === 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 131);
                }
                var reverseList = ['zldz'];
                reverseTableCell(reverseList, "fwid");
            }
        };*/


        table.render({
            id:"fwid",
            elem: '#hsTable',
            toolbar: '#toolbarDemo',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size'
            },
            even: true,
            cols: [unitTableTitle],
            data: [],
            page: true,
            limits: [10, 20, 50, 100, 200, 500],
            parseData: function (res) {
                // 设置选中行
                if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.fwid) {
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
                $(".bdc-line-search-container").css("padding-top", '61px');
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() === 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 131);
                }
                var reverseList = ['zldz'];
                reverseTableCell(reverseList, "fwid");
            }
        });



        //加载表格
       // loadDataTablbeByUrl('#hsTable', tableConfig);
        //监听行工具事件，表格操作栏内部按钮
        table.on('tool(hsTable)', function (obj) {
            var hsxx = obj.data;
            switch (obj.event) {
                case 'showlpb':
                    showlpb(hsxx.FW_DCB_INDEX);
                    break;
            }
        });

        // 监听表格头部按钮
        table.on('toolbar(hsTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'link':
                    if (checkStatus.data.length > 0) {
                        // var fwidList = [];
                        var fsssDTOList = [];
                        checkStatus.data.forEach(function (element, sameElement, set) {
                            var fsssDTO = {};
                            fsssDTO.fwid = element.FWID;
                            fsssDTO.bdcdyh = element.BDCDYH;
                            fsssDTOList.push(fsssDTO);
                        });
                        glhsxx(fsssDTOList);
                    } else {
                        ityzl_SHOW_INFO_LAYER("请先勾选数据再操作");
                    }
                    break;
            }
        });

        //提交表单
        form.on("submit(queryhs)", function (data) {
            data.field.fwDcbIndex = fwdcbindex;
            tableReloadNew('fwid', data.field, url);
            return false;
        });

        $("#queryhs").click();

    });

});

function showlpb(fwdcbindex) {
    var url = '/building-ui/lpb/view?code=htba&fwDcbIndex=' + fwdcbindex;
    var index = layer.open({
        type: 2,
        title: "",
        area: ['1300px', '600px'],
        fixed: false, //不固定
        // maxmin: true, //开启最大化最小化按钮
        content: url,
        cancel: function () {
        }

    });
    layer.full(index);
}

function glhsxx(fsssDTOList) {
    getReturnData("/rest/v1.0/htbaxx/fsss?baid=" + baid,JSON.stringify(fsssDTOList),"POST",function (data) {
        if(data && data.length > 0) {
            var msg = "";
            for(var  i=0;i<data.length;i++) {
                msg += data[i].msg+'</br>';
            }
            ityzl_SHOW_WARN_LAYER(msg);
        } else {
            ityzl_SHOW_SUCCESS_LAYER('关联成功');
        }
    },function (xhr) {
        ityzl_SHOW_WARN_LAYER('关联失败');
        delAjaxErrorMsg(xhr);
    })
}