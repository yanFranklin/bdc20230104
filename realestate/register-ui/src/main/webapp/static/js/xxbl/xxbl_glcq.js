// 从页面传递的工作流实例 id
var gzlslid;
// 从页面传递的xmid
var blxmid;
// 不动产类型
var bdclxList = [];
// 字典项
var zdList = [];
// 字典不动产类型
var zdBdclxList = [];
// 房屋类型字典项
var zdbdcdyfwlxList = [{"DM": "xmxx", "MC": "项目内多幢"}, {"DM": "ljz", "MC": "逻辑幢"}, {"DM": "hs", "MC": "实测户室"}, {"DM": "ychs", "MC": "预测户室"}];
// 不动产房屋类型
var bdcdyfwlxList = [];
var reverseList = [];
reverseList.push('zl');
layui.use(['jquery', 'table', 'form', 'layer'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        layer = layui.layer;

    $(function () {
        // 获取参数（工作流定义 id）
        gzlslid = $.getUrlParam('gzlslid');
        // 获取参数（xmid）
        blxmid = $.getUrlParam('xmid');

        /**
         * 监听台账查询 input 点击 显示 x 清空 input 中的内容
         */
        $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
            $(this).siblings('.layui-input').val('');
        });
        $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
            $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
        }).on('blur', function () {
            var $this = $(this);
            setTimeout(function () {
                $this.siblings('.layui-icon-close').addClass('bdc-hide');
            }, 150)
        });
        $('.bdc-content').css('min-height', $('body').height() - 112);

        var col = [
            {
                field: 'bdcdyh', title: '不动产单元号', width: "25%", templet: function (obj) {
                    return '<span style="color:#1d87d1; cursor:pointer" onclick="selectBdcqzh(' + "'" + obj.xmid + "','" + obj.bdcdyh + "'" + ')">' + formatBdcdyh(obj.bdcdyh) + '</span>'
                }
            },
            {field: 'bdcqzh', title: '不动产权证号', width: "25%"},
            {field: 'qllx', title: '权利类型', width: "15%", hide: 'true'},
            {field: 'zl', title: '坐落', width: "35%"},
            {field: 'qlrmc', title: '权利人', width: "15%"}
        ];

        var tableConfig = {
            id: 'blTable',
            toolbar: "#toolbar",
            defaultToolbar: ['filter'],
            cols: [col],
            limit: 10,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function (res, curr, count) {
                setTableHeight();
                reverseTableCell(reverseList);
            }
        };

        // 表格初始化
        table.init('blTable', tableConfig);

        // 点击查询提交表单
        form.on("submit(search)", function (data) {
            var url = getContextPath() + "/rest/v1.0/blxx/blxz/bdcqz";
            var cxObj = data.field;
            cxObj.zl = deleteWhitespace(cxObj.zl, "edge");
            if (!isNullOrEmpty(cxObj.zl)) {
                cxObj.zl = cxObj.zl.replace(/,/g, '%').replace(/ /g, "%").replace(/，/g, "%");
            }
            cxObj.bdcdyh = deleteWhitespace(cxObj.bdcdyh, "all");
            cxObj.bdcqzh = replaceBracket(cxObj.bdcqzh);
            tableReload('blTable', cxObj, url);
            // 不进行页面跳转
            return false;
        });

    });

    /**
     * 渲染页面的全部下拉框
     */
    function renderSelect() {
        zdList = getZdList();
        zdBdclxList = zdList.bdclx;
        //渲染 bdclx 下拉框，bdclx 在选择台账配置表中未配置就默认显示 zd 项中的内容
        if (bdclxList.length === 0) {
            $('#selectedBdclx').append(new Option("请选择", ""));
        }
        $.each(zdBdclxList, function (index, item) {
            if (bdclxList.length === 0 || bdclxList.indexOf(item.DM + "") > -1) {
                $('#selectedBdclx').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
            }
        });

        //渲染bdcdyfwlx下拉框
        if (bdcdyfwlxList.length !== 1) {
            $('#bdcdyfwlx').append(new Option("请选择", ""));
        }
        $.each(zdbdcdyfwlxList, function (index, item) {
            if (bdcdyfwlxList.length === 0 || bdcdyfwlxList.indexOf(item.DM + "") > -1) {
                $('#bdcdyfwlx').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
            }
        });
        layui.form.render("select");
    }

});


/**
 * 重新加载台账
 * @param table_id 表格 id
 * @param postData 查询参数
 * @param url   查询url
 */
function tableReload(table_id, postData, url) {
    layui.use(['table'], function () {
        var table = layui.table;
        table.reload(table_id, {
            url: url,
            where: postData,
            page: {
                //重新从第 1 页开始
                curr: 1,
                limits: [10, 50, 100, 200, 500]
            },
            done: function () {
                setTableHeight();
                reverseTableCell(reverseList);
            }
        });
    });
}

/**
 * 选择不动产权证号，关闭弹出层传递 bdcqzh 到父页面
 * @param bdcqzh 选择的 bdcqzh
 * @param bdcdyh 选择的 bdcdyh
 */
function selectBdcqzh(xmid) {
    layui.use(['jquery', 'table', 'form', 'layer'], function () {
        var $ = layui.jquery,
            layer = layui.layer;
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/blxx/glsjyz?gzlslid=" + gzlslid + "&yxmid=" + xmid,
            type: 'GET',
            dataType: 'text',
            success: function (data) {
                if (data === "bdcdyh") {
                    var index = layer.open({
                        type: 1,
                        skin: 'bdc-small-tips',
                        title: "提示",
                        area: ['320px'],
                        content: "关联所选项目不动产单元号不同，是否关联？",
                        btn: ['确定', '取消'],
                        btnAlign: 'c',
                        yes: function () {
                            glcq(xmid);
                            layer.close(index);
                        },
                        btn2: function () {
                            layer.close(index);
                        }
                    });
                } else if (data === "success") {
                    glcq(xmid);
                }
            },
            error: function (xhr, status, error) {
                removeModel();
                delAjaxErrorMsg(xhr)
            }
        });
    });
}

function glcq(yxmid) {
    layui.use(['jquery', 'table', 'form', 'layer'], function () {
        var $ = layui.jquery,
            layer = layui.layer;
        addModel();
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/blxx/glcq?gzlslid=" + gzlslid + "&xmid=" + yxmid+"&blxmid="+blxmid,
            type: "GET",
            dataType: "json",
            success: function () {
                successMsg("关联成功");
                var logInfo = {};
                logInfo['gzlslid'] = gzlslid;
                logInfo['yxmid'] = yxmid;
                saveDetailLog("XXBL_GL", "信息补录-关联", logInfo);
                removeModel();
                window.setTimeout(function () {
                  $("#search").click();
                }, 100);

            },
            error: function (xhr, status, error) {
                removeModel();
                delAjaxErrorMsg(xhr)
            }
        });
    });
}
