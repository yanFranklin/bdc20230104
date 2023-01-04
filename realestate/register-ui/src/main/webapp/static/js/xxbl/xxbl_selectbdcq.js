// 选择台账配置表
var bdcslxztzpz;
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
        var gzldyid = $.getUrlParam('gzldyid');

        // 0. 获取字典项和选择台账配置内容
        loadXztzpz(gzldyid);

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
                field: 'bdcqzh', title: '不动产权证号', width: "25%", templet: function (obj) {
                    if (!isNullOrEmpty(obj.bdcqzh)) {
                        var qjgldm =!isNullOrEmpty(obj.qjgldm)?obj.qjgldm:"";
                        return '<span style="color:#1d87d1; cursor:pointer" title="' + obj.bdcqzh + '" onclick="selectBdcqzh(' + "'" + obj.bdcqzh + "','" + obj.bdcdyh + "','" +qjgldm+"'"+ ')">' + obj.bdcqzh + '</span>'
                    } else {
                        return '<span></span>';
                    }
                }
            },
            {
                field: 'bdcdyh', title: '不动产单元号', width: "25%", templet: function (obj) {
                    return '<span>' + formatBdcdyh(obj.bdcdyh) + '</span>'
                }
            },
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
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
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
            cxObj.qllx = bdcslxztzpz.qllx;
            cxObj.zdtzm = bdcslxztzpz.zdtzm;
            cxObj.dzwtzm = bdcslxztzpz.dzwtzm;
            // 如果页面中没有获取到 房屋类型
            if (isNullOrEmpty(cxObj.bdcdyfwlx)) {
                cxObj.bdcdyfwlx = bdcslxztzpz.bdcdyfwlx;
            }
            cxObj.dyhcxlx = bdcslxztzpz.dyhcxlx;
            tableReload('blTable', cxObj, url);
            // 不进行页面跳转
            return false;
        });

    });


    /**
     * 加载选择台账配置，渲染下拉框
     * @param gzldyid 工作流定义 id
     */
    function loadXztzpz(gzldyid) {
        $.ajax({
            url: getContextPath() + "/rest/v1.0/blxx/blxz/xztzpz",
            type: 'GET',
            async: false,
            data: {gzldyid: gzldyid},
            success: function (data) {
                if (isNullOrEmpty(data)) {
                    warnMsg("选择台账未配置，请检查！");
                } else {
                    var xztzlxList = [];
                    if (!isNullOrEmpty(data.xztzlx)) {
                        xztzlxList = data.xztzlx.split(",");
                    }
                    if (!isNullOrEmpty(data.bdclx)) {
                        bdclxList = data.bdclx.split(",");
                    }
                    if (!isNullOrEmpty(data.bdcdyfwlx)) {
                        bdcdyfwlxList = data.bdcdyfwlx.split(",");
                    }
                    data.xztzlxList = xztzlxList;
                    bdcslxztzpz = data;
                    renderSelect();
                }
            }, error: function (xhr, status, error) {
                warnMsg("加载选择台账配置失败");
            }
        });
    }

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
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
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
function selectBdcqzh(bdcqzh, bdcdyh,qjgldm) {
    window.parent.document.getElementsByName("ycqzh")[0].value = bdcqzh;
    window.parent.document.getElementsByName("bdcdyh")[0].value = bdcdyh;
    if(window.parent.document.getElementsByName("qjgldm").length >0) {
        window.parent.document.getElementsByName("qjgldm")[0].value = qjgldm;
    }
    window.parent.cancelEdit();
}

