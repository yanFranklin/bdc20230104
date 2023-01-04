// 选择台账配置表
var bdcslxztzpz = {};
// 不动产类型
var bdclxList = [];
// 字典项
var zdList = [];
// 字典不动产类型
var zdBdclxList = [];
// 房屋类型字典项
var zdbdcdyfwlxList = [{"DM": "xmxx", "MC": "项目内多幢"}, {"DM": "ljz", "MC": "逻辑幢"}, {
    "DM": "hs",
    "MC": "实测户室"
}, {"DM": "ychs", "MC": "预测户室"}];
// 不动产房屋类型
var bdcdyfwlxList = [];
// 复制功能传递的参数
var yxmid;
// 操作类型
var actionType = '';
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
        actionType = $.getUrlParam('actionType');

        // 0. 获取字典项和选择台账配置内容
        if (isNullOrEmpty(gzldyid) && !isNullOrEmpty(yxmid)) {
            bdcslxztzpz.qllx = yqllx;

            // 解析 ybdcdyh
            bdcslxztzpz.zdtzm = ybdcdyh.substring(12, 14);
            bdcslxztzpz.dzwtzm = ybdcdyh.substring(19, 20);
            // 定着物特征码为 W 则不动产房屋类型默认为 td
            if (bdcslxztzpz.dzwtzm !== 'W') {
                $('#bdcdyfwlx').append(new Option("请选择", ""));// 下拉菜单里添加元素
                $.each(zdbdcdyfwlxList, function (index, item) {
                    if (bdcdyfwlxList.length === 0 || bdcdyfwlxList.indexOf(item.DM + "") > -1) {
                        $('#bdcdyfwlx').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
                    }
                });
            }
            bdcslxztzpz.bdcdyfwlx = zdbdcdyfwlxList;
            // 单元号查询类型默认为 1，不考虑海域等情况
            bdcslxztzpz.dyhcxlx = 1;

            // 渲染页面下拉框
            zdList = getZdList();
            zdBdclxList = zdList.bdclx;

            //渲染 bdclx 下拉框，bdclx 在选择台账配置表中未配置就默认显示 zd 项中的内容
            $('#selectedBdclx').append(new Option("请选择", ""));

            $.each(zdBdclxList, function (index, item) {
                if (bdclxList.length === 0 || bdclxList.indexOf(item.DM + "") > -1) {
                    $('#selectedBdclx').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
                }
            });

            layui.form.render("select");
        } else {
            loadXztzpz(gzldyid);
        }

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
                field: 'bdcdyh', title: '不动产单元号', width: "35%", templet: function (obj) {
                    if (!isNullOrEmpty(obj.bdcdyh)) {
                        var qsxz =!isNullOrEmpty(obj.qsxz)?obj.qsxz:"";
                        return '<span style="color:#1d87d1; cursor:pointer" title="' + obj.bdcdyh + '" onclick="selectBdcdyh(' + "'" + obj.bdcdyh + "','"+qsxz+"'" + ')">' + formatBdcdyh(obj.bdcdyh) + '</span>'
                    } else {
                        return '<span></span>';
                    }
                }
            },
            {field: 'zl', title: '坐落', width: "35%"},
            {field: 'qlr', title: '权利人', width: "15%"},
            {field: 'zt', title: '状态', width: "15%", templet: '#bdcdyzt', align: "center"}
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
            var url = getContextPath() + "/rest/v1.0/blxx/blxz/bdcdyh";
            var cxObj = data.field;
            cxObj.zl = deleteWhitespace(cxObj.zl, "edge");
            if (!isNullOrEmpty(cxObj.zl)) {
                cxObj.zl = cxObj.zl.replace(/,/g, '%').replace(/ /g, "%").replace(/，/g, "%");
            }
            cxObj.qllx = bdcslxztzpz.qllx;
            cxObj.zdtzm = bdcslxztzpz.zdtzm;
            cxObj.dzwtzm = bdcslxztzpz.dzwtzm;
            cxObj.dyhcxlx = bdcslxztzpz.dyhcxlx;
            cxObj.bdcqzh = replaceBracket(cxObj.bdcqzh);
            cxObj.bdcdyh = deleteWhitespace(cxObj.bdcdyh, "all");
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
 * 获取到 不动产单元的状态
 * @param bdcdyh
 * @return {string}
 */
function getBdcdyZt(bdcdyh) {
    var bdcdyZt = '<p class="bdc-table-state bdc-ql-state">';
    $.ajax({
        url: getContextPath() + '/rest/v1.0/blxx/bdcdyzt?bdcdyh=' + bdcdyh,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data !== null) {
                if (data.yg) {
                    bdcdyZt += '<span class="bdc-yg">已预告</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.yg) {
                    bdcdyZt += '<span class="bdc-yg">宗地预告</span>';
                }
                if (data.ycf) {
                    bdcdyZt += '<span class="bdc-ycf">预查封</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.ycf) {
                    bdcdyZt += '<span class="bdc-ycf">宗地预查封</span>';
                }
                if (data.ydya) {
                    bdcdyZt += '<span class="bdc-ydy">预抵押</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.ydya) {
                    bdcdyZt += '<span class="bdc-ydy">宗地预抵押</span>';
                }
                if (data.cf) {
                    bdcdyZt += '<span class="bdc-cf">查封</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.cf) {
                    bdcdyZt += '<span class="bdc-cf">宗地查封</span>';
                }
                if (data.dya) {
                    bdcdyZt += '<span class="bdc-dya">抵押</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.dya) {
                    bdcdyZt += '<span class="bdc-dya">宗地抵押</span>';
                }
                if (data.yy) {
                    bdcdyZt += '<span class="bdc-yy">异议</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.yy) {
                    bdcdyZt += '<span class="bdc-yy">宗地异议</span>';
                }
                if (data.sd) {
                    bdcdyZt += '<span class="bdc-sd">锁定</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.sd) {
                    bdcdyZt += '<span class="bdc-sd">宗地锁定</span>';
                }
                if (data.dyi) {
                    bdcdyZt += '<span class="bdc-dy">地役</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.dyi) {
                    bdcdyZt += '<span class="bdc-dy">宗地地役</span>';
                }
                if (data.zjgcdy) {
                    bdcdyZt += '<span class="bdc-zjgcdy">在建工程抵押</span>';
                }
                if (data.ks) {
                    bdcdyZt += '<span class="bdc-ks">可售</span>';
                }
                if (data.ys) {
                    bdcdyZt += '<span class="bdc-ys">已售</span>';
                }
                if (data.xjspfks) {
                    bdcdyZt += '<span class="bdc-xjspfks">新建商品房可售</span>';
                }
                if (data.xjspfys) {
                    bdcdyZt += '<span class="bdc-xjspfys">新建商品房已售</span>';
                }
                if (data.clfks) {
                    bdcdyZt += '<span class="bdc-clfks">存量房可售</span>';
                }
                if (data.clfys) {
                    bdcdyZt += '<span class="bdc-clfys">存量房已售</span>';
                }
                if (bdcdyZt.indexOf("span") < 0) {
                    bdcdyZt += '<span class="bdc-normal">正常</span>';
                }

                bdcdyZt += '</p>';
            }
        }, error: function (xhr, status, error) {
            // delAjaxErrorMsg(xhr)
        }
    });
    return bdcdyZt
}

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
 * @param bdcdyh 选择的不动产单元号
 * @param yqllx 选择数据对应的权利类型
 */
function selectBdcdyh(bdcdyh,yqllx) {
    addModel();
    // yxmid 值存在，属于复制功能
    if (!isNullOrEmpty(yxmid) && !isNullOrEmpty(actionType)) {
        var url= '';
        if (actionType == 'copy') {
            url = getContextPath() + '/rest/v1.0/blxx/copy?yxmid=' + yxmid + '&bdcdyh=' + bdcdyh;
        } else {
            if (bdcdyh.indexOf('F') < 0) {
                warnMsg("仅支持房屋的挂接！");
                removeModel();
                return;
            }
            url = getContextPath() + '/rest/v1.0/blxx/gjxx?yxmid=' + yxmid + '&bdcdyh=' + bdcdyh;
        }
        $.ajax({
            url: url,
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
        window.parent.document.getElementsByName("bdcdyh")[0].value = bdcdyh;
        if(window.parent.document.getElementsByName("yqllx").length >0) {
            window.parent.document.getElementsByName("yqllx")[0].value = yqllx;
        }
        window.parent.cancelEdit();
    }
}

