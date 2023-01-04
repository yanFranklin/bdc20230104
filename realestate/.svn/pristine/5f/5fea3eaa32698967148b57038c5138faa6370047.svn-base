var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';

/**
 * 点击明细 查看项目信息
 * @param obj
 * @param data
 */
function mx(obj, data) {
    window.open("/realestate-portal-ui/view/new-page.html?processinsid=" + data.GZLSLID + "&type=lsgx", data.SLBH);
}

/**
 * 新增按钮 打开创建质检项目页面
 */
function create() {
    var div = " <div id=\"popupTwoRows\"><form class=\"layui-form\" action=\"\">" +
        "            <div class=\"layui-form-item pf-form-item\">" +
        "                <div class=\"layui-inline\" style=\"width: 100%\">" +
        "                    <label class=\"layui-form-label\">开始时间</label>" +
        "                    <div class=\"layui-input-inline bdc-end-time-box\">" +
        "                        <input name=\"kssj\" id='kssj' placeholder=\"请输入内容\" class=\"layui-input\"></input>" +
        "                    </div>" +
        "                </div>" +
        "                <div class=\"layui-inline\" style=\"width: 100%\">" +
        "                    <label class=\"layui-form-label\">结束时间</label>" +
        "                    <div class=\"layui-input-inline bdc-end-time-box\">" +
        "                        <input name=\"jssj\" id='jssj' placeholder=\"请输入内容\" class=\"layui-input\"></input>" +
        "                    </div>" +
        "                </div>" +
        "            </div>" +
        "        </form>" +
        "    </div>";

    var index = layer.open({
        type: 1,
        title: "新增质检",
        area: ['600px', '240px'],
        fixed: false, //不固定bdcFzjl
        maxmin: true, //开启最大化最小化按钮
        content: div,
        btn: ['保存', '取消'],
        success: function (layero, index) {
            layui.use('laydate', function () {
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#kssj'
                });
                laydate.render({
                    elem: '#jssj'
                });
            })
        },
        yes: function (index, layero) {
            zjcj(index);
        }
        , btn2: function (index, layero) {
            layer.close(index);
        }
        , cancel: function (index) {
            layer.close(index);
        }
    });
}

/**
 * 质检创建
 * @param index
 */
function zjcj(index) {
    var kssj = $("#kssj").val();
    var jssj = $("#jssj").val();
    var zjzt = queryZjzt(kssj, jssj);
    if (isNotBlank(zjzt)) {
        layer.confirm("当前设置日期范围内存在已抽查数据，是否继续抽查？", {
            title: "提示",
            btn: ["确认", "取消"],
            btn1: function (index2) {
                layer.close(index2);
                cjzjxx(kssj, jssj, index);
            },
            btn2: function () {
                layer.close(index);
            }
        })
    } else {
        cjzjxx(kssj, jssj, index);
    }
}

/**
 * 通过开始时间和结束时间判断是否进行过抽查
 * @param kssj
 * @param jssj
 */
function queryZjzt(kssj, jssj) {
    var result;
    $.ajax({
        url: BASE_URL + '/zjcx/queryZjzt',
        type: "GET",
        data: {kssj: kssj, jssj: jssj},
        async: false,
        success: function (data) {
            result = data;
        },
        error: function (e) {
            response.fail(e)
        }, complete: function () {

        }
    });
    return result;
}

//查询遮罩
function addDivModel() {
    var modalHtml = '<div id="waitModalLayer">'+
        '<p class="bdc-wait-content">'+
        '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>'+
        '<span>请稍等</span>'+
        '</p>'+
        '</div>';
    $('#popupTwoRows').append(modalHtml);
}

/**
 * 创建质检信息
 * @param kssj
 * @param jssj
 */
function cjzjxx(kssj, jssj, index) {
    addDivModel();
    $.ajax({
        url: BASE_URL + '/zjcx/cjzjxx',
        type: "GET",
        data: {kssj: kssj, jssj: jssj},
        success: function (data) {
            if (data > 0) {
                saveDetailLog("CJZJXX", "创建质检信息", {"KSSJ": kssj, "JSSJ": jssj});
                successMsg("抽选完成");
                layer.close(index);
            } else {
                warnMsg("未能抽选数据，请确认是否存在可抽选数据！");
            }
            removeModal();
        },
        error: function (e) {
            response.fail(e)
        }, complete: function () {
            $("#zl").attr("value",' ');
            $("#search").click();
            $("#zl").attr("value",'');
        }
    });
}

/**
 * 质检通过
 */
function zjtg() {
    if (isNullOrEmpty(checkeddata)){
        warnMsg("请选择数据！");
        return;
    }

    var checkedArray = new Array();
    for (var i = 0; i < checkeddata.length; i++) {
        var checked = new Object();
        checked.zjid = checkeddata[i].ZJID;
        checkedArray.push(checked);
    }

    $.ajax({
        url: BASE_URL + '/zjcx/zjtg',
        type: "POST",
        data: JSON.stringify(checkedArray),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            saveDetailLog("ZJTG", "质检通过", {"ZJNR": checkeddata});
            saveSuccessMsg();
        },
        error: function (e) {
            saveFailMsg();
        }, complete: function () {
            layui.table.reload('pageTable', {page: {curr: 1}});
        }
    });
}

/**
 * 质检未通过
 */
function zjwtg() {
    var div = " <div id=\"popupTwoRows\"><form class=\"layui-form\" action=\"\">" +
        "            <div class=\"layui-form-item pf-form-item\">" +
        "                <div class=\"layui-inline\" style=\"width: 100%\">" +
        "                    <label class=\"layui-form-label\">审批意见</label>" +
        "                    <div class=\"layui-input-inline bdc-end-time-box\">" +
        "                        <textarea class='layui-textarea' name=\"spyj\" id='spyj' placeholder=\"请输入内容\"></textarea>" +
        "                    </div>" +
        "                </div>" +
        "            </div>" +
        "        </form>" +
        "    </div>";
    //小弹出层
    layer.open({
        title: '审批意见',
        type: 1,
        area: ['600px', '300px'],
        btn: ['提交', '取消'],
        content: div
        , yes: function (index, layero) {
            //提交 的回调
            saveZjwtg(index)
        }
        , btn2: function (index, layero) {
            //取消 的回调
            layer.close(index)
        }
        , cancel: function (index) {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
            layer.close(index)
        }
        , success: function () {

        }
    });
}

function saveZjwtg(index) {
    var spyj = $("#spyj").val();
    if (isNullOrEmpty(spyj)) {
        warnMsg("请输入审批意见！");
        return;
    }
    if (isNullOrEmpty(checkeddata)){
        warnMsg("请选择数据！");
        return;
    }

    var checkedArray = new Array();
    for (var i = 0; i < checkeddata.length; i++) {
        var checked = new Object();
        checked.zjid = checkeddata[i].ZJID;
        checkedArray.push(checked);
    }

    $.ajax({
        url: BASE_URL + '/zjcx/zjwtg?spyj=' + spyj,
        type: "POST",
        data: JSON.stringify(checkedArray),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            saveDetailLog("ZJWTG", "质检未通过", {"ZJNR": checkeddata, "SPYJ": spyj});
            saveSuccessMsg();
        },
        error: function (e) {
            saveFailMsg();
        }, complete: function () {
            $('#spyj').val('');
            layui.table.reload('pageTable', {page: {curr: 1}});
            layer.close(index);
        }
    });
}

function loadComplete() {
    var getTd = $('.layui-table-view .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        if ($(getTd[i]).attr('data-field') == "ZJZT") {
            var getTdCell = $(getTd[i]).find('.layui-table-cell');
            if (getTdCell[0].innerText == '0') {
                getTdCell[0].innerText = '未质检';
            }
            if (getTdCell[0].innerText == '1') {
                getTdCell[0].innerText = '质检通过';
            }
            if (getTdCell[0].innerText == '2') {
                getTdCell[0].innerText = '质检未通过';
            }
        }
    }
}