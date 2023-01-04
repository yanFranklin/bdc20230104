//收费项目列表数据
var getSfxmList;
var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/fph';
var BASE_CZ_URL = '/realestate-inquiry-ui/rest/v1.0/changzhou/fph';
var sfxxid;
var qlrlb;
var sfzt;
var fplb;
// 存储打印配置的sessionKey
// 当前页的打印类型
var dylxArr = ["fssr_fp", "jspz_fp"];
var sessionKey = "fp";
setDypzSession(dylxArr, sessionKey);
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form', 'laytpl'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        laytpl = layui.laytpl,
        layer = layui.layer;
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == "13") {
            $("#query").click();
            return false;
        }
    });
    var data;
    var allData = [],
        effectiveData = [];
    //程序计算数量
    var bdcSlSfxmSlDTO = {};

    form.render();

    var cols = [
        {type: 'radio', width: 50, fixed: 'left'},
        {field: 'sfxxid', title: 'ID', width: 50, fixed: 'left', hide: true},
        {field: 'qlrlb', title: '权利人类别', width: 50, fixed: 'left', hide: true}
        , {
            field: 'fph', title: '发票号', templet: function (d) {
                if (fplb = "1") {
                    return isNullOrEmpty(d.fssrfph) ? "" : d.fssrfph;
                } else if (fplb = "9") {
                    return isNullOrEmpty(d.jspzfph) ? "" : d.jspzfph;
                } else {
                    return "";
                }
            }
        }
        , {field: 'slbh', title: '受理编号'}
        , {field: 'qlrmc', title: '权利人'}
        , {field: 'gzldymc', title: '流程名称'}
        , {field: 'hj', title: '合计'}
        , {title: '操作', width: 260, fixed: 'right', templet: '#barDemo'}
    ];
    table.render({
        elem: '#fphTable'
        , id: "fphTableId"
        , even: true
        , cols: [cols]
        , toolbar: false
        , data: [],
        done: function () {
            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
            }
        }
    });

    $('#query').click(function () {
        var slbh = $('#slbh').val();
        if (isNullOrEmpty(slbh)) {
            warnMsg("受理编号不能为空！")
            return false;
        }
        var checkData = checkState(slbh);
        if (checkData.state == true) {
            warnMsg("项目已办结，不能再次收费!");
            return false;
        } else if (checkData.sfzt == true) {
            layer.confirm('已收费，是否需要重新收费？', function (index) {
                querySfxx(slbh);
                layer.close(index);
                return false;
            });
        } else {
            querySfxx(slbh);
        }
    });

    $('#search').click(function () {
        var slbh = $('#slbh').val();
        if (isNullOrEmpty(slbh)) {
            warnMsg("受理编号不能为空！")
            return false;
        }
        fplb = $('#fplb').val();
        $.ajax({
            url: BASE_CZ_URL + "/query?slbh=" + slbh + "&fplb=" + fplb,
            type: "GET",
            success: function (data) {
                effectiveData = [];
                $.each(data, function (key, value) {
                    value['slbh'] = slbh;
                    effectiveData.push(value);
                });
                sfxxCache = data;
                allData = data;
                toolbarId = "#barDemo";
                table.reload('fphTableId', {
                    data: effectiveData,
                });
                $("#secondTableView").html('');
            }, error: function (e) {
                showErrorInfo(e, "获取信息失败");
            }, complete: function (XMLHttpRequest, textStatus) {
                // 关闭loading
                removeModal();
            }
        });
    });
    var sfxxCache = data;

    function querySfxx(slbh) {
        fplb = $('#fplb').val();
        addModel();
        $.ajax({
            url: BASE_CZ_URL + "/query?slbh=" + slbh + "&fplb=" + fplb,
            type: "GET",
            success: function (data) {
                effectiveData = [];
                $.each(data, function (key, value) {
                    value['slbh'] = slbh;
                    effectiveData.push(value);
                });
                sfxxCache = data;
                allData = data;
                table.reload('fphTableId', {
                    data: effectiveData,
                    toolbar: false
                });
                $("#secondTableView").html('');
            },
            error: function (e) {
                showErrorInfo(e, "生成发票号失败");
            },
            complete: function (XMLHttpRequest, textStatus) {
                // 关闭loading
                removeModal();
            }
        });
    }

    function checkState(slbh) {
        var result = {};
        result.state = false;
        $.ajax({
            url: BASE_URL + "/check?slbh=" + slbh,
            type: "GET",
            async: false,
            success: function (data) {
                result.state = data.state;
                result.taskData = data.taskData;
                result.sfzt = data.sfzt;
            }, complete: function (XMLHttpRequest, textStatus) {

            }
        });
        return result;
    }

    function checkFph(data) {
        if (fplb == "1") {
            return isNullOrEmpty(data.fssrfph) ? "" : data.fssrfph;
        }
        if (fplb == "9") {
            return isNullOrEmpty(data.jspzfph) ? "" : data.jspzfph;
        }
    }

    //一级 表格 操作按钮监听事件
    table.on('tool(fphTableFilter)', function (obj) {
        var layEvent = obj.event; //获得 lay-event 对应的值
        var data = obj.data;
        console.log(data);
        if (layEvent === "getfph") { // 获取发票号
            var selectFph = checkFph(data);
            if (!isNullOrEmpty(selectFph)) {
                warnMsg("该收费信息已经获取发票号！")
                return;
            }
            var fph = getFph(data.sfxxid);
            if (fplb == "1") {
                obj.update({
                    fssrfph: fph,
                    fph: fph
                });
            } else {
                obj.update({
                    jspzfph: fph,
                    fph: fph
                });
            }
        }
        if (layEvent === "dy") { //打印
            if (isNotBlank(checkFph(data))) {
                dy(data.sfxxid);
            } else {
                warnMsg("请先获取发票号，再打印！");
            }
        }
    });

    //监听表格1单选框选择
    table.on('radio(fphTableFilter)', function (obj) {
        console.log(obj.data); //选中行的相关数据
        sfxxid = obj.data.sfxxid;
        fph = obj.data.fph;
        qlrlb = obj.data.qlrlb;
        sfzt = obj.data.sfzt;
        renderSecondTable(obj.data.sfxxid, obj.data.xmid, obj.data.gzlslid);
    });

    function renderSecondTable(sfxxid, xmid, gzlslid) {
        fplb = $('#fplb').val();
        $.ajax({
            url: BASE_CZ_URL + "/sf/xm/new",
            type: 'GET',
            dataType: 'json',
            data: {sfxxid: sfxxid, fplb: fplb},
            success: function (data) {
                renderSfxmList(xmid, gzlslid);
                data.forEach(function (v) {
                    v['sfxmList'] = getSfxmList;
                    //登记费和工本费初始化加载需要程序计算数量
                    if ((isNotBlank(v.sfxmmc) && v.sfxmmc.indexOf("登记费") > -1 || v.sfxmmc.indexOf("工本费") > -1 || v.sfxmmc.indexOf("土地使用权交易服务费") > -1) && v.sl === null) {
                        if (v.sfxmmc.indexOf("工本费") > -1) {
                            v['sl'] = bdcSlSfxmSlDTO.gbfsl;
                        } else if (isNotBlank(v.sfxmbz) && v.sfxmbz.indexOf("非住宅") > -1) {
                            v['sl'] = bdcSlSfxmSlDTO.fzzdjfsl;
                        } else if (isNotBlank(v.sfxmbz) && v.sfxmbz.indexOf("住宅") > -1) {
                            v['sl'] = bdcSlSfxmSlDTO.zzdjfsl;

                        } else if (v.sfxmmc.indexOf("土地使用权交易服务费") > -1) {
                            if (v.jsff === "1") {
                                //取住宅面积和
                                v['sl'] = bdcSlSfxmSlDTO.zzmjsl;
                            } else if (v.jsff === "2") {
                                //取非住宅面积和
                                v['sl'] = bdcSlSfxmSlDTO.fzzmjsl;

                            } else if (v.jsff === "3") {
                                //取总权利面积和
                                v['sl'] = bdcSlSfxmSlDTO.tdsyjsl;

                            } else {
                                v['sl'] = 0;
                            }
                        }

                    }
                    getSfxmList.forEach(function (value) {
                        if (value.sfxmdm == v.sfxmdm) {
                            value.bdcSlSfxmSfbzList.forEach(function (item) {
                                if (item.sfxmbz == v.sfxmbz) {
                                    v['sfxmdj'] = item.sfxmdj;
                                }
                            });
                        }
                    });
                    //计算应收金额,实收金额
                    var getShje = calculateFloat(parseFloat(v.sfxmdj) * v.sl);

                    //应收金额
                    v['ysje'] = getShje;
                    //实收金额
                    v['ssje'] = calculateFloat(getShje - v.jmje);

                });

                var getTpl = secondTableTpl.innerHTML;
                laytpl(getTpl).render(filterNotNull(data), function (html) {
                    var $secondTableView = $("#secondTableView");
                    $secondTableView.html(html);
                    computeTotal($secondTableView);
                    if (sfzt == '2') {
                        var $secondSelect = $secondTableView.find("select");
                        $secondSelect.attr("disabled", true);
                        $secondSelect.parents('.bdc-td-box').addClass('bdc-one-icon').append('<img src="../../static/lib/bdcui/images/lock.png" alt="">');

                        var $secondInput = $secondTableView.find("input");
                        $secondInput.attr("disabled", true);
                        $secondInput.parents('.bdc-td-box').addClass('bdc-one-icon').append('<img src="../../static/lib/bdcui/images/lock.png" alt="">');

                        var $secondA = $secondTableView.find("a");
                        $secondA.css("pointer-events", "none").addClass('bdc-prohibit-btn');
                    }
                    form.render();
                });

            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });

    }

    //获取收费项目列表
    function renderSfxmList(xmid, gzlslid) {
        $.ajax({
            url: BASE_URL + "/sf/xm/pz/all",
            type: 'GET',
            dataType: 'json',
            data: {processInsId: gzlslid, xmid: xmid},
            async: false,
            success: function (data) {
                getSfxmList = data;
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }

    //打印
    function dy(sfxxid) {
        if (isNullOrEmpty(sfxxid)) {
            warnMsg("收费信息ID不能为空！")
            return false;
        }
        var dylx = fplb == '1' ? 'fssr_fp' : 'jspz_fp'
        var dataUrl = getIP() + BASE_CZ_URL + "/dy/xml/" + sfxxid + "?fplb=" + fplb;
        printChoice(dylx, "realestate-inquiry-ui", dataUrl, null, false, sessionKey)
        var sfxxidList = [];
        sfxxidList.push(sfxxid);
        modifyKpzt(sfxxidList);
    }

    //获取发票号
    function getFph(sfxxid) {
        addModel();
        var slbh = $('#slbh').val();
        if (isNullOrEmpty(slbh)) {
            removeModal();
            warnMsg("受理编号不能为空！")
            return false;
        }
        if (isNullOrEmpty(sfxxid)) {
            removeModal()
            warnMsg("收费信息ID不能为空！")
            return false;
        }
        var fph = '';
        $.ajax({
            url: BASE_CZ_URL + "?sfxxid=" + sfxxid + "&slbh=" + slbh + "&fplb=" + fplb,
            type: "GET",
            async: false,
            success: function (data) {
                fph = data[0].fph;
            }, complete: function (XMLHttpRequest, textStatus) {
                removeModal();
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
        return fph;
    }
});

// 页面展示时过滤收费项目数据，对数量为0的数据不进行展现。
function filterNotNull(data) {
    var notNullData = [];
    $.each(data, function (index, value) {
        if (!isNullOrEmpty(value.sl) && value.sl !== 0) {
            notNullData.push(value);
        }
    });
    return notNullData;
}

/**
 * 处理小数计算问题，四舍五入保留两位小数
 * 需要引用math.js
 * @param equation  计算公式
 */
function calculateFloat(equation) {
    var result = math.format(math.evaluate(equation), 14);
    return formatFloat(parseFloat(result));
}

//处理小数问题
function formatFloat(f) {
    if ((f + '').indexOf('.') != -1) {
        var d, carryD, i,
            ds = f.toString().split('.'),
            len = ds[1].length,
            b0 = '', k = 0;

        if (len > 2) {
            while (k < 2 && ds[1].substring(0, ++k) == '0') {
                b0 += '0';
            }
            d = Number(ds[1].substring(0, 2));
            // 判断保留位数后一位是否需要进1
            carryD = Math.round(Number('0.' + ds[1].substring(2, len)));
            len = (d + carryD).toString().length;
            if (len > 2) {
                return Number(ds[0]) + 1;
            } else if (len == 2) {
                return Number(ds[0] + '.' + (d + carryD));
            }
            return Number(ds[0] + '.' + b0 + (d + carryD));
        }
    }
    return f;
}

//获取总计
function computeTotal($table) {
    var hj = 0;
    var $ssjeList = $table.find('.bdc-ssje');
    $ssjeList.each(function (i) {
        hj += formatFloat(parseFloat($($ssjeList[i]).val()));
    });
    $table.find('.bdc-hj').val(formatFloat(hj));
}

// 更新收费信息是否开票信息
function modifyKpzt(sfxxidList) {
    $.ajax({
        url: BASE_URL + "/sf/kpzt",
        type: "POST",
        data: JSON.stringify(sfxxidList),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
        },
        error: function (e) {
            delAjaxErrorMsg(xhr);
        }
    });
}