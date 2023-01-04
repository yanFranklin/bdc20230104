// 注销申请人
var dyzxsqr;
// 注销申请人证件号
var dyzxsqrzjh;
layui.use(['jquery', 'table', 'laypage', 'form'], function () {
    var $ = layui.$;
    var table = layui.table;
    var laypage = layui.laypage;
    var form = layui.form;

    // 获取参数
    var processInsId = $.getUrlParam('processInsId');
    // 表单ID
    var formStateId = $.getUrlParam('formStateId');
    // 获取表单权限参数readOnly
    var readOnly = $.getUrlParam('readOnly');

    $(function () {

        $.ajax({
            url: BASE_URL + '/bdcdy/pz/lkdw',
            type: "GET",
            success: function (data) {
                if (isNotBlank(data)) {
                    $("#lkdw").html(data);
                }
            }
        });

        //滚动时头部固定
        var $contentTitle = $('.bdc-content-fix:visible');
        if ($contentTitle.length != 0) {
            defaultStyle();
        }

        function defaultStyle() {
            if ($(window).scrollTop() > 10) {
                $contentTitle.css('top', '0');
            } else if ($(window).scrollTop() <= 10) {
                $contentTitle.css('top', '10px');
            }
        }

        $(window).resize(function () {
            if ($contentTitle.length != 0) {
                defaultStyle();
            }
        });
        $(window).on('scroll', function () {
            if ($contentTitle.length != 0) {
                if ($(this).scrollTop() > 10) {
                    $contentTitle.css('top', '0');
                } else if ($(this).scrollTop() <= 10) {
                    $contentTitle.css('top', '10px');
                }
            }
        });

        var _colsParam = [
            {field: "zl", title: "坐落", align: "center"},
            {field: "ywr", title: "产权人", align: "center"},
            // 字段未知
            {field: "qlr", title: "预告权利人", align: "center"},
            // 字段未知
            {field: "bdcqzh", title: "预告证明号", align: "center"}
        ];
        // 查询的结果
        var data = [];
        $.ajax({
            url: BASE_URL + '/bdcdy/dyazx/' + processInsId,
            type: "GET",
            dataType: "json",
            async: false,
            timeout: 10000,
            success: function (result) {
                data = result.content;
            }
        });
        // 查询注销信息和受理业务信息
        $.ajax({
            url: BASE_URL + '/bdcdy/ydyaxx/' + processInsId,
            type: "GET",
            dataType: "json",
            // async: false,
            timeout: 10000,
            success: function (result) {
                var slbh = "业务宗号：";
                var zxdjsj;
                if (result) {
                    // 当前注销流程的受理编号
                    slbh = slbh + result.slbh;
                    // 注销登记时间
                    if (result.zxdydjsj) {
                        zxdjsj = Format(result.zxdydjsj, 'yyyy年MM月dd日');
                    }
                    // 抵押证明注销申请人信息
                    if (result.dyzxsqr) {
                        dyzxsqr = result.dyzxsqr;
                        $("#dyzxsqr").val(dyzxsqr);
                    }
                    if (result.dyzxsqrzjh) {
                        dyzxsqrzjh = result.dyzxsqrzjh;
                        $("#dyzxsqrzjh").val(dyzxsqrzjh);
                    }
                }
                $("#slbh").html(slbh);
                $(".zxdjsj").html(zxdjsj);
            }
        });

        table.render({
            elem: '#pageTable',
            title: '抵押注销证明',
            even: true,
            cols: [_colsParam],
            data: data,
            page: true,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');


                if ($('.layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
                }

                //设置权限控制样式
                if (readOnly || !isNullOrEmpty(formStateId)) {
                    getStateByIdQlxx(readOnly, formStateId, "dyzxzm");
                }
            }
        });
        /**
         * 保存
         */
        form.on('submit(saveBtn)', function (data) {
            var dyzxsqr = $("#dyzxsqr").val();
            var dyzxsqrzjh = $("#dyzxsqrzjh").val();
            var param = {};
            param["dyzxsqr"] = dyzxsqr;
            param["dyzxsqrzjh"] = dyzxsqrzjh;
            $.ajax({
                url: BASE_URL + '/bdcdy/ydyaxx/' + processInsId,
                type: "PUT",
                data: JSON.stringify(param),
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    if (data && data.code && data.code == 1) {
                        warnMsg(data.msg);
                    } else if (data && data > 0) {
                        successMsg("保存成功！");
                    } else {
                        warnMsg("提交失败，请重试！");
                    }
                }
            });
            // 禁止提交后刷新
            return false;
        });

        /**
         * 打印抵押注销证明
         * @returns {boolean}
         */
        form.on('submit(printBtn)', function (data) {
            var modelUrl = ygdjzxzmModelUrl;
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/bdcdy/" + processInsId + "/ygdjzxzm/xml";
            print(modelUrl, dataUrl, false);

            // 禁止提交后刷新
            return false;
        });
    });
});

/**
 * 身份证读卡器设置
 * @param element
 */
function zxsqrReadIdCard(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            var pName = objTest.Name;
            var pCardNo = objTest.ID;

            if (isNullOrEmpty(dyzxsqrzjh)) {
                dyzxsqrzjh = pCardNo;
                dyzxsqr = pName;
            } else if (dyzxsqrzjh.indexOf(pCardNo) <= -1) {
                dyzxsqrzjh = dyzxsqrzjh + "," + pCardNo;
                dyzxsqr = dyzxsqr + "," + pCardNo;
            }

            $("#dyzxsqr").val(trimStr(dyzxsqr));
            $("#dyzxsqrzjh").val(trimStr(dyzxsqrzjh));

        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}