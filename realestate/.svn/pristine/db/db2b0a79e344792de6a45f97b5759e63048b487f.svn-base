var slbh;
var $, laytpl, form;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    $ = layui.jquery;
    var element = layui.element;
    laytpl = layui.laytpl;
    var layer = layui.layer;

    // 监听页面上申请人证件种类修改
    form.on('select(zjzl)', function (data) {
        var $zjh = $(data.elem).parents("td").next().find("input");
        addSfzYz(data.value, $zjh);
        form.render();
    });
});

//新增申请人
function addQlr(xmid) {
    var qllx = $('.layui-show').data('qllx');
    var djxl = $('.layui-show').data('djxl');
    var url = getContextPath() + "/view/slym/sqr.html?processInsId=" + processInsId + "&formStateId="
        + formStateId + "&xmid=" + xmid + "&qllx=" + qllx + "&djxl=" + djxl;
    layer.open({
        type: 2,
        area: ['960px', '575px'],
        fixed: false, //不固定
        title: "新增申请人",
        content: url,
        btnAlign: "c"
    });
    form.render();
    resetSelectDisabledCss();
}

//权利人详情展示
function showQlr(sqrid, xmid, qlrlb, elem) {
    addModel();
    var qllx = $('.layui-show').data('qllx');
    var djxl = $('.layui-show').data('djxl');
    var url = getContextPath() + "/nantong/ycsl/sqr.html?processInsId=" + processInsId + "&sqrid=" + sqrid + "&xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&qllx=" + qllx + "&djxl=" + djxl;
    ;
    layer.open({
        type: 2,
        area: ['960px', '575px'],
        fixed: false, //不固定
        title: "申请人详细信息",
        content: url,
        btnAlign: "c"
    });
    removeModal();
    form.render();
    renderDate(form, "");
    disabledAddFa();
}

//删除权利人
function deleteSqr(sqrid, sxh, qlrlb) {
    var xmid = "";
    var tabXmid = $('.layui-show').data('xmid');
    var tabQllx = $('.layui-show').data('qllx');
    var tabDjxl = $('.layui-show').data('djxl');
    var url = "/slym/sqr/sqrxx/pl?gzlslid=" + processInsId + "&sqrid=" + sqrid + "&qllx=" + tabQllx + "&djxl=" + tabDjxl;
    if (!isNotBlank(tabXmid)) {
        tabXmid = xmid;
    }
    var deleteIndex = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '确认删除',
        area: ['320px'],
        content: '是否确认删除？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            addModel();
            //确定操作
            getReturnData(url, {}, 'DELETE', function (data) {
                removeModal();
                loadYcslxx(tabXmid, $('.layui-show'));
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
            }, function (err) {
                delAjaxErrorMsg(err)
            });
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });
}

//权利人顺序号修改
function changeSqrSxh(sqrid, czlx) {
    var tabXmid = $('.layui-show').data('xmid');
    if (!isNotBlank(tabXmid)) {
        tabXmid = xmid;
    }
    getReturnData("/slym/sqr/sxh", {
        sqrid: sqrid,
        czlx: czlx,
        gzlslid: processInsId
    }, 'GET', function (data) {
        if (data > 0) {
            ityzl_SHOW_SUCCESS_LAYER("调整成功");
            loadYcslxx(tabXmid, $('.layui-show'));
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

//关闭panel
function cancelEdit() {
    layer.closeAll();
}

// 加载权力信息与不动产单元信息统一入口
function generateQlxxAndBdcdyxx(data, $nowTab, bdcSlXm, bdcqllxSlXm) {
    // 加载权利信息
    generateQlxx(data, bdcSlXm, $nowTab, bdcqllxSlXm);

    // 加载不动产单元信息
    var djxl = bdcSlXm.djxl;
    var xmxx = bdcqllxSlXm[djxl];
    if (xmxx && xmxx.length < 2) {
        generateBdcdyxx({
            bdcSlXm: bdcSlXm,
            zd: zdList
        }, $nowTab);
    } else {
        pllc = true;
        generateBdcdyxx({
            bdcSlXm: xmxx,
            zd: zdList
        }, $nowTab);
    }

}

// 加载不动产单元信息
function generateBdcdyxx(data, $nowTab) {
    if ($nowTab.find('#bdcdyxx').length > 0) {
        if (data.bdcSlXm.length > 1) {
            var tpl = bdcdyxxPlTpl.innerHTML;
            var view = $nowTab.find('#bdcdyxx')[0];
            laytpl(tpl).render(data, function (html) {
                view.innerHTML = html;
            });
            form.render();
            $("#qlxx").remove();
            loadBdcdyh();
        } else {
            var tpl = bdcdyxxTpl.innerHTML;
            var view = $nowTab.find('#bdcdyxx')[0];
            laytpl(tpl).render(data, function (html) {
                view.innerHTML = html;
            });
            form.render();
        }
    }
}

function loadBdcdyh() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var table = layui.table;
        var qllx = $('.layui-show').data('qllx');
        var isSearch = true;
        $(document).keydown(function (event) {
            if (event.keyCode == 13 && isSearch) { //绑定回车
                $("#searchBdcdy").click();
            }
        });
        // //判断回车操作
        $('.bdc-tab-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });

        $('body').on('focus', 'textarea', function () {
            isSearch = false;
        }).on('blur', 'textarea', function () {
            isSearch = true;
        });

        //获取权利信息表头
        var unitTableTitle = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'fjh', title: '房间号', minWidth: 100},
            {field: 'ycqzh', title: '原产权证号', minWidth: 280},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {
                field: 'qllx', title: '权利类型', minWidth: 100, templet: function (d) {
                    return changeDmToMc(d.qllx, '', zdList.qllx);
                }
            },
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, '', zdList.qlxz);
                }
            },
            {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {
                field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: function (d) {
                    return changeDmToMc(d.zdzhyt, '', zdList.tdyt);
                }
            },
            {field: 'zh', title: '幢号', minWidth: 100},
            {
                field: 'tdsyqssj', title: '土地使用开始期限', minWidth: 150,
                templet: function (d) {
                    if (d.tdsyqssj) {
                        return Format(format(d.tdsyqssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'tdsyjssj', title: '土地使用结束期限', minWidth: 150,
                templet: function (d) {
                    if (d.tdsyjssj) {
                        return Format(format(d.tdsyjssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'fwlx', title: '房屋类型', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.fwlx, '', zdList.fwlx);
                }
            },
            {
                field: 'fwxz', title: '房屋性质', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.fwxz, '', zdList.fwxz);
                }
            },
            {field: 'zcs', title: '总层数', minWidth: 100},
            {field: 'szc', title: '所在物理层', minWidth: 100},
            {field: 'szmyc', title: '所在名义层', minWidth: 100},
            {
                field: 'fwjg', title: '房屋结构', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.fwjg, '', zdList.fwjg);
                }
            },
            {
                field: 'ghyt', title: '规划用途', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.ghyt, '', zdList.fwyt);
                }
            },
            {field: 'jyjg', title: '房地产交易价格', minWidth: 180},
            {
                field: 'jgsj', title: '竣工时间', minWidth: 150
            },
            {field: 'jzmj', title: '建筑面积', minWidth: 100},
            {field: 'ftjzmj', title: '分摊建筑面积', minWidth: 120},
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style: 'cursor: pointer;'}
        ];
        var url = getContextPath() + '/slym/xm/listSlFwxxByPage';

        // 查询参数
        var data = {
            sort: "xmid,ASC"
        };
        data["qllx"] = qllx;

        // 根据当前查询参数，获取所有的单元信息，用于数据导出
        data["gzlslid"] = processInsId;
        //提交表单
        $("#searchBdcdy").click(function () {
            var bdcdyArray = $(".bdcdyForm").serializeArray();
            bdcdyArray.forEach(function (item) {
                data[item.name] = item.value;
            });
            table.reload('xmid', {
                url: url,
                where: data,
                // data:[11],
                page: {
                    //重新从第 1 页开始
                    curr: 1,
                    limits: [10, 50, 100, 200, 500]
                },
                done: function () {
                    removeModal();
                    $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                    reverseTableCell(reverseList, 'xmid');
                }
            })
            return false;
        });
        //重置操作
        $("#reset").on('click', function () {
            $('.bdc-form-search input').val('');
            $('.bdc-form-search select').val('');
            form.render('select');
        });

        table.render({
            id: 'xmid',
            url: url,
            where: data,
            cols: [unitTableTitle],
            elem: '#bdcdyTable',
            toolbar: "#toolbarBdcdyh",
            limits: [10, 50, 100, 200, 500],
            limit: 10,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            title: '不动产单元信息',
            defaultToolbar: ['filter'],
            page: true,
            autoSort: false,
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content//解析数据列表
                }
            },
            done: function (res) {
                var reverseList = ['zl'];
                reverseTableCell(reverseList, 'xmid');
            }
        });
        renderSearchInput();

        //头工具栏事件
        table.on('toolbar(bdcdyTable)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === "plxg") { //批量修改
                var checkStatus = table.checkStatus(obj.config.id);
                var checkData = checkStatus.data;
                var xmids = [];
                if (checkData.length === 0) {
                    showConfirmDialog("提示", "未选择数据，默认修改全部数据?", "openPlxg", "''", "", "");
                } else {
                    for (var i = 0; i < checkData.length; i++) {
                        var xmid = checkData[i].xmid;
                        xmids.push(xmid);
                    }
                    openPlxg(xmids);
                }

            }
            return false;
        });

        //监听滚动事件
        var scrollTop = 0,
            scrollLeft = 0;
        var tableTop = 0, tableLeft = 0;
        var $nowBtnMore = '';
        $(window).on('scroll', function () {
            scrollTop = $(this).scrollTop();
            scrollLeft = $(this).scrollLeft();

            if ($nowBtnMore != '') {
                if (tableTop + 26 + $nowBtnMore.height() < document.body.offsetHeight) {
                    $nowBtnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
                } else {
                    $nowBtnMore.css({top: tableTop - scrollTop - $nowBtnMore.height(), left: tableLeft - 30});
                }
            }
        });
    })

}

//打开批量修改页面
function openPlxg(xmids) {
    //打开前先清除缓存数据
    sessionStorage.removeItem('plxg_xmids');
    //数据过多,存入缓存
    sessionStorage.setItem('plxg_xmids', xmids);
    var index = layer.open({
        type: 2,
        title: "批量修改",
        area: ['1300px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: getContextPath() + "/view/slym/zdjbdb_plxg.html?processInsId=" + processInsId + "&xmid=" + xmid + "&formStateId=" + formStateId + "&zxlc=" + zxlc,
        cancel: function () {
            $.when(loadBdcdyh()).done(function () {
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        clearInterval(a);
                    }
                }, 50);
            });
        }
    });
    layer.full(index);
}
/**
 * 初始化加载权利信息模块数据信息
 * @param ycslYmxxDTO 一窗受理页面信息
 * @param bdcSlXm 不动产受理项目信息
 * @param $nowTab 当前点击tab页
 */
function generateQlxx(ycslYmxxDTO, bdcSlXm, $nowTab, bdcqllxSlXm) {
    if (isNotBlank(ycslYmxxDTO)) {
        // 根据登记小类获取登记原因
        var djyyList = tabModel.loadDjyy(bdcSlXm.djxl);
        if (isNotBlank(djyyList)) {
            var tpl = {};
            // 渲染权利信息
            if (bdcSlXm.qllx == parseInt(commonDyaq_qllx)) {
                includeDyaq = true;
                var bdcSlQl = {};
                getReturnData("/rest/v1.0/ycym/qlxx/dyaq/" + bdcSlXm.xmid, {}, "GET", function (data) {
                    bdcSlQl = data;
                }, function (error) {
                    delAjaxErrorMsg(error);

                }, false);
                var data = {
                    zd: zdList,
                    bdcSlJyxx: ycslYmxxDTO.bdcSlJyxxDO,
                    bdcSlQl: bdcSlQl,
                    bdcSlXm: bdcSlXm,
                    djyyList: djyyList,
                    dkfs: dkfs
                };
                tpl = bdc_dyaqTpl.innerHTML;
            } else {
                // 从zrf中解析qlr
                var zrflist = ycslYmxxDTO.bdcSlZrfDTOList;
                var tdsyqr = "";
                if (zrflist) {
                    for (var i = 0; i < zrflist.length; i++) {
                        var item = zrflist[i];
                        var sqr = item.bdcSlSqrDO.sqrmc;
                        tdsyqr += sqr + ",";
                    }
                    if (tdsyqr.length > 0) {
                        tdsyqr = tdsyqr.substr(0, tdsyqr.length - 1);
                    }
                }
                var data = {
                    zd: zdList,
                    bdcSlJyxx: ycslYmxxDTO.bdcSlJyxxDO,
                    bdcSlFwxx: ycslYmxxDTO.bdcSlFwxxDO,
                    bdcSlXm: bdcSlXm,
                    djyyList: djyyList,
                    tdsyqr: tdsyqr
                };
                if (bdcqllxSlXm[bdcSlXm.djxl].length > 1) {
                    tpl = plqlxxTpl.innerHTML;
                } else {
                    tpl = qlxxTpl.innerHTML;
                }
            }
            if ($nowTab.find('#qlxx').length > 0) {
                var view = $nowTab.find('#qlxx')[0];
                if (bdcqllxSlXm[bdcSlXm.djxl].length > 1) {
                    $("#plqlxx").show();
                    view = $nowTab.find('#plqlxx')[0];
                }
                laytpl(tpl).render(data, function (html) {
                    view.innerHTML = html;
                });
                // 添加权利信息页面表单监听事件，同步权利信息至交易与税收页面
                qlxxModel.syncFwxxData();
            }
        }
    }
}

// 保存不动产受理房屋信息
function saveBdcSlFwxx(className, tabType) {
    var fwxxData = {};
    var fwxxTab;
    if (tabType === "jyssTab") {
        fwxxTab = $(".swxx " + className);
    } else {
        fwxxTab = $("#qlxxTab " + className);
    }
    if (fwxxTab.length != 0) {
        fwxxData = fwxxTab.serializeObject();
        //fwxxid
        fwxxData.fwxxid = $("input[name=fwxxid]").val();
        //修改土地性质为划拨或租赁时，权利的结束时间不可填写
        if ($('.jssj').length > 0) {
            checkJssjGz2($("#qlxz").val(), $('.jssj').val(), false);
            checkJssjGz4($("#qlxz").val(), $('.jssj').val());

        }
        if ($('.jssj').length > 0 && $('.qssj').length > 0) {
            checkJssjGz3($("#qlxz").val(), $('.jssj').val(), $('.qssj').val())
        }
        if (qlxxModel.checkSzcAndZcs(fwxxData.szc, fwxxData.zcs)) {
            $.ajax({
                url: getContextPath() + "/ycsl/fwxx",
                type: 'PATCH',
                dataType: 'json',
                async: false,
                contentType: "application/json",
                data: JSON.stringify(fwxxData),
                success: function (data) {
                    // ityzl_SHOW_SUCCESS_LAYER("提交成功");
                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }
    }
}

function saveBdcSlXm(className) {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var $nowTab = $('.layui-tab-item:nth-child(' + i + ')');
        var tabXmid = $nowTab.data('xmid');
        if (!isNotBlank(tabXmid)) {
            continue;
        }
        saveBdcSlXmTab($nowTab, className);
    }

}

// 保存不动产受理项目信息
function saveBdcSlXmTab($nowTab, className) {
    var bdcSlXmSingleData = {},
        bdcSlXm = [];
    if ($nowTab.find(className).length != 0) {
        var exincludeByBdcdyxx = [];
        //权利信息模块的受理项目信息
        var $ql_xmxx = $nowTab.find("#qlxx " + className);
        if ($ql_xmxx.length > 0) {
            exincludeByBdcdyxx = $ql_xmxx.serializeArray();
        }
        var $bdcdy_xmxx = $nowTab.find("#bdcdyxx " + className);
        if ($bdcdy_xmxx.length > 0) {
            bdcSlXm = $bdcdy_xmxx.serializeArray();
        }
        var sqfbcz = $nowTab.find("input[type='radio']:checked").val();

        if (bdcSlXm.length > 10) {
            for (var j = 0; j < bdcSlXm.length; j++) {
                var name = bdcSlXm[j].name;
                bdcSlXmSingleData[name] = bdcSlXm[j].value;
                if ((j + 1 < bdcSlXm.length && bdcSlXm[j + 1].name === 'xmid') || j + 1 == bdcSlXm.length) {
                    for (var m = 0; m < exincludeByBdcdyxx.length; m++) {
                        var name1 = exincludeByBdcdyxx[m].name;
                        bdcSlXmSingleData[name1] = exincludeByBdcdyxx[m].value;
                    }
                    bdcSlXmSingleData.sqfbcz = sqfbcz;
                    $.ajax({
                        url: getContextPath() + "/ycsl/xmxx",
                        type: 'PATCH',
                        dataType: 'json',
                        async: false,
                        contentType: "application/json",
                        data: JSON.stringify(bdcSlXmSingleData),
                        success: function (data) {
                            // ityzl_SHOW_SUCCESS_LAYER("提交成功");
                        }, error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr)
                        }
                    });
                }
            }
        } else {
            // 单个对象数据直接保存
            var bdcSlXmData = $nowTab.find(className).serializeObject();
            bdcSlXmData.sqfbcz = sqfbcz;
            $.ajax({
                url: getContextPath() + "/ycsl/xmxx",
                type: 'PATCH',
                dataType: 'json',
                async: false,
                contentType: "application/json",
                data: JSON.stringify(bdcSlXmData),
                success: function (data) {
                    // ityzl_SHOW_SUCCESS_LAYER("提交成功");
                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }


    }
}

function saveBdcSlJyxx(className, tabType) {
    var isFirst = true;
    for (var i = 0; i <= $('.layui-tab-item').length; i++) {
        var $nowTab = $('.layui-tab-item:nth-child(' + i + ')');
        var tabXmid = $nowTab.data('xmid');
        if (!isNotBlank(tabXmid)) {
            continue;
        }
        saveBdcSlJyxxTab($nowTab, className, isFirst, tabType);
        if (tabType === "jyssTab" && isFirst) {
            break;
        }

        isFirst = false;
    }

}

//交易信息保存(批量页面）
function saveJyxxPl() {
    var jyxxData = {};
    var jyxxArray = $(".jyxx");
    if (jyxxArray !== null && jyxxArray.length > 0) {
        jyxxArray.serializeArray().forEach(function (item, index) {
            jyxxData[item.name] = item.value;
        });
        if ($("input[name=jyhth]").length != 0) {
            jyxxData["htbh"] = $("input[name=jyhth]").val();
        }
        getReturnData("/ycsl/jyxx/list?processInsId=" + processInsId, JSON.stringify(jyxxData), 'PATCH', function (data) {
            loadJbxx();

        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);
    }

}

// 保存不动产受理交易信息
function saveBdcSlJyxxTab($nowTab, className, isFirst, tabType) {
    var bdcSlJyxxData = {};
    if ($nowTab.find(className).length != 0 || (isFirst && $(".swxx .jyxx").length != 0)) {
        if (tabType === "slTab") {
            bdcSlJyxxData = $nowTab.find(className).serializeObject();
        }
        // 添加jyxxid
        eval("bdcSlJyxxData." + "jyxxid" + "='" + $nowTab.find('#jyxxid').val() + "'");
        if (tabType === "jyssTab" && isFirst) {
            var jyssData = $(".swxx .jyxx").serializeObject();
            for (var key in jyssData) {
                bdcSlJyxxData[key] = jyssData[key];
            }
        }
        $.ajax({
            url: getContextPath() + "/ycsl/htxx",
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(bdcSlJyxxData),
            success: function (data) {
                // ityzl_SHOW_SUCCESS_LAYER("提交成功");
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }
}

// 保存不动产受理抵押权信息
function saveBdcSlDyaq(className) {
    if ($(className).length !== 0) {
        for (var i = 1; i <= $('.layui-tab-item').length; i++) {
            var dyaqData = $('.layui-tab-item:nth-child(' + i + ') ' + className).serializeObject();
            if (isNotBlank(dyaqData)) {
                $.ajax({
                    url: getContextPath() + "/rest/v1.0/ycym/qlxx/dyaq",
                    type: 'PUT',
                    dataType: 'json',
                    async: false,
                    contentType: "application/json",
                    data: JSON.stringify(dyaqData),
                    success: function (data) {
                    }, error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });
            }
        }
    }
}


var qlxxModel = {
    // 初始化附记和权利其他状况按钮事件
    initEvent: (function () {
        return function () {
            // 领证人证件类型添加初始化事件
            form.on('select(lzrzjhFilter)', function (data) {
                var $zjh = $(data.elem).parents(".layui-inline").next().find("input");
                addSfzYz(data.value, $zjh);
            });
        }
    })(),
    // 所在层与总层数验证
    checkSzcAndZcs: function (szc, zcs) {
        if (szc === "0") {
            throw error = new Error("所在层不能为0");
            return false;
        }
        if (isNotBlank(szc) && isNotBlank(zcs) && parseInt(szc) > parseInt(zcs)) {
            throw error = new Error("所在层不能大于总层数");
            return false;
        }
        return true;
    },
    // 同步权利信息模块相关数据至交易与税收模块中
    syncFwxxData: (function () {
        // 计算土地权利面积
        var sumTdqlMj = function ($fttdmj, $dytdmj) {
            var fttdmj = $fttdmj.val();
            var dytdmj = $dytdmj.val();
            var fttdmjX = 0;
            var dytdmjX = 0;
            if (fttdmj != "") {
                fttdmjX = parseFloat(fttdmj) * 100;
            }
            if (dytdmj != "") {
                dytdmjX = parseFloat(dytdmj) * 100;
            }
            var $syqmj = $("input[name='tdsyqmj']");
            if ($syqmj.length > 0) {
                $syqmj.val((fttdmjX + dytdmjX) / 100);
            }
        }
        return function () {
            // 监听房屋类型，同步更新交易与税收页面的房屋类型
            form.on('select(fwlx)', function (data) {
                $(".swxx #fwxx").find("select[name='fwlx']").val(data.value);
                form.render("select");
                disabledAddFa();
                resetSelectDisabledCss();
            });
            // 监听房屋性质，同步更新交易与税收页面的房屋性质
            form.on('select(fwxz)', function (data) {
                $(".swxx #fwxx").find("select[name='fwxz']").val(data.value);
                form.render("select");
                disabledAddFa();
                resetSelectDisabledCss();
            });
            // 分摊土地面积和独用土地面积监听
            var $fttdmj = $("input[name='fttdmj']");
            var $dytdmj = $("input[name='dytdmj']");
            if ($fttdmj.length > 0 && $dytdmj.length > 0) {
                $fttdmj.on('change', function () {
                    sumTdqlMj($fttdmj, $dytdmj);
                });
                $dytdmj.on('change', function () {
                    sumTdqlMj($fttdmj, $dytdmj);
                });
            }
        }
    })()
}

// Tab模块，通用方法信息
var tabModel = {
    // 通过不动产登记小类获取当前登记小类相关的登记原因信息
    loadDjyy: function (djxl) {
        if (isNotBlank(djxl)) {
            var djxlList;
            getReturnData("/slym/xm/queryDjxlDjyy", {djxl: djxl}, 'GET', function (data) {
                if (isNotBlank(data)) {
                    djxlList = data;
                }
            }, function (err) {
                console.log(err);
            }, false);
            return djxlList;
        }
    }
}

function saveSqr() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        if (!isNotBlank(tabXmid)) {
            continue;
        }
        var $nowTab = $('.layui-tab-item:nth-child(' + i + ')');
        saveSqrTab($nowTab, tabXmid, tabDjxl);
    }

}


// 页面上方保存按钮 保存权利人
function saveSqrTab($nowTab, tabXmid, tabdjxl) {
    var bdcSlSqrData = [];
    var sqrlist = [];
    var qlrnum = 0;
    var ywrnum = 0;
    var gyfs = "";
    var ywrgyfs = "";
    var tabQllx = $nowTab.data('qllx');
    if ($nowTab.find(".sqr").length != 0) {
        bdcSlSqrData = $nowTab.find(".sqr").serializeArray();
        var bdcSlSqrSingleData = {};
        for (var j = 0; j < bdcSlSqrData.length; j++) {
            var name = bdcSlSqrData[j].name;
            bdcSlSqrSingleData[name] = bdcSlSqrData[j].value;
            // 以qlrid为每一组权利人的起始数据，作为分割依据
            if ((j + 1 < bdcSlSqrData.length && bdcSlSqrData[j + 1].name === 'sqrid') || j + 1 == bdcSlSqrData.length) {
                if (Object.keys(bdcSlSqrSingleData).length === 1) {
                    continue;

                }
                if (bdcSlSqrSingleData.sqrlb === "1") {
                    qlrnum++;
                    gyfs += bdcSlSqrSingleData.gyfs + ",";
                }
                if (bdcSlSqrSingleData.sqrlb == '2') {
                    ywrnum++;
                    ywrgyfs += bdcSlSqrSingleData.gyfs + ",";
                }
                bdcSlSqrSingleData.xmid = tabXmid;
                toUpperClass(bdcSlSqrSingleData, ["zjh", "dlrzjh", "frzjh", "lzrzjh"]);
                sqrlist.push(bdcSlSqrSingleData);

                bdcSlSqrSingleData = {};
            }
        }
    } else {
        // 没有申请人 认为是成功的
        return true;
    }
    //验证权利人共有方式
    if (!checkGyfs(gyfs, qlrnum)) {
        throw err = new Error('权利人共有方式不正确，请检查！');
        return false;
    }

    //验证义务人共有方式
    if (!checkGyfs(ywrgyfs, ywrnum)) {
        throw err = new Error('义务人共有方式不正确，请检查！');
        return false;
    }

    // 验证共有比例
    if (!checkAddGybl(sqrlist)) {
        throw err = new Error('共有比例不正确，请检查！');
    }


    console.log(sqrlist);
    var count = batchsaveAllSqrs(sqrlist, tabQllx, tabdjxl);
    // 更新的数量 == 页面的数量 则说明更新成功
    if (bdcSlSqrData.length == count) {
        return true;
    } else {
        return false;
    }

}

/**
 * 上方保存按钮 需要保存所有申请人
 * @param sqrList
 */
function saveAllSqrs(sqrList) {
    var count = 0;
    for (var i = 0; i < sqrList.length; i++) {
        var bdcQlrData = sqrList[i];
        //申请人名称单独处理
        var BdcSlSqrDTO = {};
        var bdcSlSqrDTOList = [];
        BdcSlSqrDTO['BdcSlSqrDO'] = bdcQlrData;
        BdcSlSqrDTO['bdcSlJtcyDOList'] = [];
        bdcSlSqrDTOList.push(BdcSlSqrDTO);
        var url = getContextPath() + "/slym/sqr/sqrxx?gzlslid=" + processInsId;
        $.ajax({
            url: url,
            type: 'PATCH',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bdcSlSqrDTOList),
            async: false,
            success: function (data) {
                if (isNotBlank(data)) {
                    count++;
                }
            },
            error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
    }
    return count;
}

function batchsaveAllSqrs(sqrList, tabqllx, tabdjxl) {
    var count = 0;
    var url = getContextPath() + "/slym/sqr/sqrxx/save?gzlslid=" + processInsId + "&qllx=" + tabqllx + "&djxl=" + tabdjxl;
    $.ajax({
        url: url,
        type: 'PATCH',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(sqrList),
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                count++;
            }
        },
        error: function (e) {
            delAjaxErrorMsg(e);
        }
    });
    return count;
}

/**
 * gyfs 共有方式集合，多个用英文逗号隔开
 * qlrnum 权利人个数
 * xmid 用于判断当前登记小类是否默认共同共有
 * 验证共有方式是否正确：1.一个权利人为单独所有 2.两个权利人共有方式不为单独所有且保持一致 3.三个及三个以上共有方式不为单独所有
 * 返回结果：是否验证通过
 */
function checkGyfs(gyfs, qlrnum, xmid) {
    if (qlrnum === 1) {
        // 如果 传递过XMID 则判断当前项目 的登记小类 是否为 默认共有方式为 GTGY
        if (xmid) {
            var checkflag = false;
            getReturnData("/slym/qlr/checkdefaultgyfs", {gyfs: gyfs, xmid: xmid}, "GET", function (data) {
                checkflag = data;
            }, function () {
            }, false);
            if (checkflag) {
                return true;
            }
        }
        //一个权利人时共有方式必须为单独所有
        if (!dgGyfsYz) {
            return true;
        }
        if (gyfs.indexOf("1") < 0 && gyfs.indexOf("2") < 0 && gyfs.indexOf("3") < 0) {
            return true;
        }
    } else if (qlrnum === 2) {
        //两个权利人时共有方式不能存在单独所有，且两个共有方式保持一致
        var gyfsArr = gyfs.split(",");
        if (gyfsArr.length > 1 && gyfsArr[0] === gyfsArr[1] && gyfsArr[0] !== "0") {
            return true;
        }
    } else {
        //多个权利人时共有方式不能存在单独所有
        if (gyfs.indexOf("0") < 0) {
            return true;
        }
    }
    return false;
}

/**
 * qlrList 权利人集合
 * 验证共有比例之和为1：支持百分数，小数，分数（多个共有比例为统一类型）
 * 返回结果：是否验证通过
 */
function checkAddGybl(qlrList) {
    var sumgybl = 0;
    var gyblList = [];
    var qlrs = [];
    //分母相乘
    var fenmumultiply = 1;
    //分子之和
    var fenzicount = 0;
    for (var k in qlrList) {
        if (qlrList[k].qlrlb === "1" || (qlrList[k].sqrlb === "1")) {
            qlrs.push(qlrList[k]);
        }
    }
    if (isNotBlank(qlrs)) {
        for (var k in qlrs) {
            if (qlrs[k].gyfs === "2") {
                if (isNotBlank(qlrs[k].qlbl)) {
                    var qlbl = qlrs[k].qlbl;
                    if (isNaN(qlbl)) {
                        if (qlbl.indexOf("%") > -1) {
                            gyblList.push(parseFloat(qlbl.substr(0, qlbl.length - 1)));
                        } else if (qlbl.indexOf("/") > -1) {
                            var fenmu = parseInt(qlbl.split("/")[1]);
                            fenmumultiply = fenmumultiply * fenmu;
                        }
                    } else {
                        gyblList.push(parseFloat(qlrs[k].qlbl) * 100);

                    }
                } else {
                    //按份共有共有比例必须有值
                    return false;
                }
            } else if (isNotBlank(qlrs[k].qlbl)) {
                throw err = new Error('非按份共有请勿填写共有比例！');
            }

        }
    } else {
        //无权利人直接通过
        return true;
    }
    if (gyblList.length > 0) {
        for (var i in gyblList) {
            sumgybl += formatFloat(gyblList[i]);
        }
        if (formatFloat(sumgybl) === 100) {
            return true;

        }
    } else if (fenmumultiply > 1) {
        for (var k in qlrList) {
            if (qlrList[k].qlrlb === "1" && isNotBlank(qlrList[k].qlbl)) {
                var qlbl = qlrList[k].qlbl;
                if (qlbl.indexOf("/") > -1) {
                    var fenmu = parseInt(qlbl.split("/")[1]);
                    var fenzi = parseInt(qlbl.split("/")[0]);
                    fenzicount += fenmumultiply / fenmu * fenzi;
                }
            }
        }
        if (fenmumultiply === fenzicount) {
            return true;

        }
    } else {
        return true;

    }
    return false;
}


//计算使用期限（单位：月）
function countSyqxByMonth() {
    var $layuishow = $('.layui-show');
    var dyaqssj = $layuishow.find("#dyaq-zwlxqssj").val();
    var syqx = parseInt($layuishow.find(".zwlxqx").val());
    if (syqx > 0) {
        //计算结束时间后的日期 日 要减一天
        if (isNotBlank(dyaqssj)) {
            var qssj = new Date(dyaqssj);
            qssj.setMonth(qssj.getMonth() + syqx);
            var time = qssj.getTime() - 1000 * 60 * 60 * 24;
            console.log(Format(formatChinaTime(qssj), "yyyy-MM-dd"));
            $layuishow.find("#dyaq-zwlxjssj").val(Format(formatChinaTime(new Date(time)), "yyyy-MM-dd"));
        }
    }
}

