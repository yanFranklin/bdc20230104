/**
 * @param 电子证照简要材料信息
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description
 * @date : 2019/12/30 10:14
 */
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;

    var zdList;
    // 获取字典
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: "POST",
        async: false,
        dataType: "json",
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        }
    });

    // 获取参数
    var gzlslid = getQueryString('gzlslid');

    getReturnData('/dzzz/listCljyxxByPage', {"gzlslid": gzlslid}, 'GET', function (data) {
        var getTableData = {
            code: '',
            msg: '',
            count: 0,
            data: []
        };
        data.forEach(function (v, i) {
            v.forEach(function (value) {
                getTableData.data.push(value);
            });
        });
        getTableData.count = getTableData.data.length;
        renderTable(getTableData.data);
    });

    /**
     * 加载Table数据列表
     */
    function renderTable(data) {
        table.render({
            elem: '#jyclxxTable',
            title: '简要材料信息',
            defaultToolbar: ['filter'],
            even: true,
            limits: [10, 20, 50, 100, 200, 500],
            cols: [[
                {width:'15%',field:'bdcQlrDO.qlrmc',title:'申请人名称',templet: function (d) {
                    if(d && d.bdcQlrDO) {
                        return d.bdcQlrDO.qlrmc;
                    } else {
                        return '';
                    }
                    }},
                {width:'15%',field:'bdcQlrDO.zjh',title:'证件号',templet: function (d) {
                        if(d && d.bdcQlrDO && d.bdcQlrDO.zjh) {
                            return d.bdcQlrDO.zjh;
                        } else {
                            return '';
                        }
                    }},
                {width:'10%',field:'bdcQlrDO.qlrmc',title:'联系电话',templet: function (d) {
                        if(d && d.bdcQlrDO && d.bdcQlrDO.dh) {
                            return d.bdcQlrDO.dh;
                        } else {
                            return '';
                        }
                    }},
                {
                    width: '20%', field: 'typeCode', title: '材料名称', templet: function (d) {
                        if (d && d.typeCode) {
                            var dzzzmc = '';
                            getReturnData("/dzzz/clmc", {typecode: d.typeCode}, "GET", function (data) {
                                if (data) {
                                    dzzzmc = data.dzzzmc;
                                }
                            }, function (xhr) {
                            }, false);
                            return dzzzmc;
                        }
                    }
                },
                {
                    width: '10%', field: 'status', title: '是否有内容 ', templet: function (d) {
                        if (d && d.status) {
                            var sfzzlx = '';
                            if (d.status === "Y") {
                                sfzzlx = "是";
                            } else {
                                sfzzlx = "否";
                            }
                            return sfzzlx;
                        } else {
                            return '';
                        }
                    }
                },
                {
                    width: '10%', field: 'isMaterial', title: '是否是材料', templet: function (d) {
                        if (d && d.isMaterial) {
                            var sfzzlx = '';
                            if (d.isMaterial === "Y") {
                                sfzzlx = "是";
                            } else {
                                sfzzlx = "否";
                            }
                            return sfzzlx;
                        } else {
                            return '';
                        }
                    }
                },
                {
                    width: '10%', field: 'areaCode', title: '所属区划', templet: function (d) {
                        if (zdList && zdList.qx && d.areaCode) {
                            var xzqh = '';
                            for (var index in zdList.qx) {
                                if (zdList.qx[index].DM == d.areaCode) {
                                    xzqh = zdList.qx[index].MC;
                                }
                            }
                            return xzqh;
                        } else {
                            return '';
                        }
                    }
                },
                {width: '10%', sort: false, toolbar: '#barAction', title: '操作', fixed: 'right'}]
            ],
            text: {
                none: '未查询到数据'
            },
            autoSort: false,
            page: true,
            data: data,
            done: function () {
                var searchHeight = 79;
                setTableHeight(searchHeight);
            }
        });

    }

    // 详细信息查看
    table.on('tool(jyclxxTable)', function (obj) {
        var data = obj.data;
        var bdcDzzzVO = {
            typecode : data.typeCode,
            qlrmc :  data.bdcQlrDO.qlrmc,
            qlrzjh : data.bdcQlrDO.zjh,
            lxdh : data.bdcQlrDO.dh,
            gzlslid : gzlslid,
        };
        switch(obj.event){
            case "getSmClxx":
                //扫码下载
                scanWst(bdcDzzzVO);
                break;
            case "getclxx":
                //授权下载
                //现获取token认证，获取二维码和待认证token，二维码展示到评价器
                queryDzzzToken(bdcDzzzVO);
                break;
            case "getZsxx":
                getDzzzxx(bdcDzzzVO);
                break;
        }
    });


    function queryDzzzToken(bdcDzzzVO) {
        addModel();
        getReturnData("/dzzz/token", JSON.stringify(bdcDzzzVO), "POST", function (data) {
            //获取返回值将二维码推送给评价器
            if (data) {
                bdcDzzzVO.ewmnr = data.qrCode;
                bdcDzzzVO.token = data.token;
                queryDzzzPdf(bdcDzzzVO);
            }
        }, function (xhr) {
            removeModal();
            delAjaxErrorMsg(xhr);
        });
    }

    function queryDzzzPdf(bdcDzzzVO) {
        bdcDzzzVO.pdflx = "dzzzewm";
        getReturnData("/dzzz/ewmpdf", JSON.stringify(bdcDzzzVO), "POST", function (data) {
            if (data) {
                var result = window.parent.showPdf(data);
                if(result == 1 || result == 3) {
                    downloadDzzz(bdcDzzzVO);
                }
            }
            removeModal();
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        })
    }

    function downloadDzzz(bdcDzzzVO) {
        addModel();
        getReturnData("/dzzz/download", JSON.stringify(bdcDzzzVO), "POST", function (data) {
            removeModal();
            if (data && data > 0) {
                ityzl_SHOW_SUCCESS_LAYER("下载成功，请在文档中心查看");
            } else {
                ityzl_SHOW_INFO_LAYER("下载失败，未获取到对应材料信息");
            }
        }, function (xhr) {
            removeModal();
            delAjaxErrorMsg(xhr);
        })

    }

    function getDzzzxx(bdcDzzzVO){
        getReturnData("/dzzz/getDzzzxx", JSON.stringify(bdcDzzzVO), "POST", function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("下载成功，请在文档中心查看");
        }, function (xhr) {
            removeModal();
            delAjaxErrorMsg(xhr);
        })
    }

    //扫描下载
    function scanWst(bdcDzzzVO) {
        layer.open({
            type: 1,
            title: '请扫描皖事通通用授权码',
            skin: 'bdc-ckgdxx-layer',
            area: ['430px'],
            content:
                '<div id="bdc-popup-small ckgdxx-layer">' +
                '<form class="layui-form" action="">' +
                '<div class="layui-form-item pf-form-item">' +
                '<div class="layui-inline" style="width: 100%;">' +
                ' <label class="layui-form-label bdc-label-newline">通用授权码:</label>' +
                ' <div class="layui-input-inline">' +
                '<input type="text" autofocus="true" name="wstxx" id="wstxx" autocomplete="off" placeholder="请输入" class="layui-input">' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</form>' +
                '</div>',
            btn: ['下载'],
            btnAlign: 'c',
            yes: function (index, layero) {
                //确定操作
                var wstxx = layero.find("input[name='wstxx']").val();
                console.log("扫描到的通用授权码信息为："+wstxx);
                if (isNullOrEmpty(wstxx)) {
                    ityzl_SHOW_WARN_LAYER("请扫描皖事通通用授权码!");
                    return;
                }
                //内容中的后32为为token
                if(wstxx.length <= 32){
                    ityzl_SHOW_WARN_LAYER("授权码错误!");
                    return;
                }
                var token = wstxx.substr(wstxx.length - 32,wstxx.length);
                console.log("扫描到的通用授权码token信息为："+token);
                addModel();
                bdcDzzzVO.token = token;
                downloadDzzz(bdcDzzzVO);
                removeModal();
                layer.close(index);
            },
            cancle: function (index) {
                layer.close(index);
            },
            done: function (index) {

            }
        });
    }

});