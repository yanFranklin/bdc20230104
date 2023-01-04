/**
 * author: <a href="mailto:caolu@gtmap.cn>caolu</a>
 * version 1.0.0  2022/07/18
 * describe: 发证页面自动获取ems工作人员，弹窗
 */
var bz = getQueryString("bz");
var fzyj = getQueryString("fzyj");
var xmid = getQueryString("xmid");
var sfhb = getQueryString("sfhb");
var zsidList = getQueryString("zsidList");
var gzlslid = getQueryString("gzlslid");

layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;

    // 获取参数


    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#emsryTable',
        title: 'EMS工作人员信息',
        defaultToolbar: ['filter'],
        url: "/realestate-register-ui/rest/v1.0/fzjl/queryEmsRyxx",
        where: {"jglb": 7},
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        even: true,
        limits: commonLimits,
        cols: [[
            {width: 50, sort: false, type: 'numbers', title: '序号', unresize: true},
            {width: 150, sort: false, field: 'jgmc', title: '机构名称'},
            {width: 100, sort: false, field: 'dlrmc', title: '姓名'},
            {width: 150, sort: false, field: 'dlrzjzlmc', title: '证件类型'},
            {width: 200, sort: false, field: 'dlrzjh', title: '证件号码'},
            {minWidth: 100, sort: false, field: 'dlrdh', title: '电话'},
            {field: 'cz', title: '操作', width: 80, templet: '#emsxz', align: "center", fixed: "right"}
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            var searchHeight = 131;
            setTableHeight(searchHeight);

        }
    });

    // 行工具事件,lay-filter="emsryTable"
    table.on('tool(emsryTable)', function (obj) {
        if (obj.event === 'xzems') {
            //获取选中的数据
            var selectData = [];
            selectData.push(obj.data);
            console.log(selectData);
            if (selectData.length == 1) {
                // 更新

                var bdcLzr = {
                    lzr: selectData[0].dlrmc,
                    lzrzjh: selectData[0].dlrzjh,
                    lzrdh: selectData[0].dlrdh,
                    lzrzjzl: selectData[0].dlrzjzl,
                    zsidList: zsidList.split(',')
                };
                console.log(bdcLzr);
                var bdcLzrList = [];
                bdcLzrList.push(bdcLzr);
                var bdcFzjlDTO = {
                    bz: bz,
                    fzyj: fzyj,
                    bdcFzjlZsDTOList: bdcLzrList
                };
                console.log(bdcFzjlDTO);
                var bdcFzjlEmsDTO = {
                    jgid: selectData[0].jgid,
                    xmid: xmid,
                    gzlslid: gzlslid,
                    sfhb: sfhb,
                    bdcFzjlDTO: bdcFzjlDTO
                }
                console.log(bdcFzjlEmsDTO);

                $.ajax({
                    url: "/realestate-register-ui/rest/v1.0/fzjl/getEmsFjxx",
                    type: "POST",
                    data: JSON.stringify(bdcFzjlEmsDTO),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        successMsg("更新成功，EMS附件上传成功！");
                        saveDetailLog("UPDATE", "自动获取Ems人员更新领证人信息", bdcFzjlEmsDTO);
                        var index = parent.layer.getFrameIndex(window.name);
                        //刷新父页面
                        parent.location.reload();
                        //关闭当前页
                        parent.layer.close(index);
                    }, error: function () {
                        // 关闭加载
                        warnMsg("更新失败，EMS附件上传失败");
                    }
                });
            }

        }

    });


});

//关闭当前页面(父页面关闭当前弹框)
function closeWin() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}




