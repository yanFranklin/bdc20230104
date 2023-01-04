// 复制功能传递的参数
var yxmid;
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
        // yxmid 获取到说明是复制功能打开页面
        yxmid = $.getUrlParam('yxmid');
        // 被复制的 bdcdyh 和 qllx
        var ybdcdyh = $.getUrlParam('ybdcdyh');
        var yqllx = $.getUrlParam('qllx');
        var zdList = getZdList();

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
                field: 'bdcdyh', title: '不动产单元号', width: "18%", templet: function (obj) {
                    if (!isNullOrEmpty(obj.bdcdyh)) {
                        var qjgldm =!isNullOrEmpty(obj.qjgldm)?obj.qjgldm:"";
                        return '<span style="color:#1d87d1; cursor:pointer" title="' + obj.bdcdyh + '" onclick="selectCf(' + "'" + obj.bdcdyh + "','" + obj.cfwh +"','" + obj.xmid +"','"+qjgldm+"'"+ ')">' + formatBdcdyh(obj.bdcdyh) + '</span>'
                    } else {
                        return '<span></span>';
                    }
                }
            },
            {field: 'cfwh', title: '查封文号', width: "14%"},
            {field: 'cflx', title: '查封类型', width: "8%", templet: function (d) {
                    if (zdList && zdList.cflx && !isNullOrEmpty(d.cflx + '')) {
                        for (var index in zdList.cflx) {
                            if (zdList.cflx[index].DM == d.cflx) {
                                return zdList.cflx[index].MC;
                            }
                        }
                        return '';
                    } else {
                        return '';
                    }
                }
            },
            {field: 'cfjg', title: '查封机关', width: "8%"},
            {field: 'ywrmc', title: '被查封人', width: "9%"},
            {field: 'cfqssj', sort: true, title: '查封起始时间', minWidth: 130,
                templet: function (d) {
                    if (d.cfqssj) {
                        return Format(format(d.cfqssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }},
            {field: 'cfjssj', sort: true, title: '查封结束时间', minWidth: 130,
                templet: function (d) {
                    if (d.cfjssj) {
                        return Format(format(d.cfjssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }},
            {field: 'zl', title: '坐落', width: "18%"},
            {field: 'zt', title: '状态', width: "7.2%", templet: '#bdcdyzt', align: "center"}
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
            addModel();
            var url = getContextPath() + "/rest/v1.0/blxx/blxz/cf";
            var cxObj = data.field;
            cxObj.zl = deleteWhitespace(cxObj.zl, "edge");
            if (!isNullOrEmpty(cxObj.zl)) {
                cxObj.zl = cxObj.zl.replace(/,/g, '%').replace(/ /g, "%").replace(/，/g, "%");
            }
            cxObj.bdcdyh = deleteWhitespace(cxObj.bdcdyh, "all");
            cxObj.cfwh = deleteWhitespace(cxObj.cfwh, "edge");
            cxObj.ycqzh = deleteWhitespace(cxObj.ycqzh, "edge");
            tableReload('blTable', cxObj, url);
            // 不进行页面跳转
            return false;
        });

    });

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
                removeModel();
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
 * 选择不动产单元，关闭弹出层传递 bdcdyh 到父页面
 * @param obj 选择的数据
 */
function selectCf(bdcdyh, cfwh, xmid,qjgldm) {
    addModel();
    // yxmid 值存在，属于复制功能
    if (!isNullOrEmpty(yxmid)) {
        $.ajax({
            url: getContextPath() + '/rest/v1.0/blxx/copy?yxmid=' + yxmid + '&bdcdyh=' + bdcdyh,
            type: 'GET',
            success: function (data) {
                removeModel();
                window.parent.$('#bdcdyh').val(bdcdyh);
                window.parent.$('#search').click();
                window.parent.cancelEdit();
            }, error: function (xhr, status, error) {
                removeModel();
                delAjaxErrorMsg(xhr);
            }
        });
    } else {
        removeModel();
        window.parent.document.getElementsByName("bdcdyh")[0].value = bdcdyh;
        window.parent.document.getElementsByName("cfwh")[0].value = cfwh;
        window.parent.document.getElementsByName("yxmid")[0].value = xmid;
        if(window.parent.document.getElementsByName("qjgldm").length >0) {
            window.parent.document.getElementsByName("qjgldm")[0].value = qjgldm;
        }
        window.parent.cancelEdit();
    }
}
