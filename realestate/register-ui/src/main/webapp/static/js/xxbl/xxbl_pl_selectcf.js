function loadCfTable(gzldyid){
    $(".bdc-tip").hide()
    $(".xzbdcdyh").hide();
    $(".xzcq").hide();
    $(".xzcf").show();
    var gzldyid = gzldyid;
    var yxmid;
    var reverseList = [];
    reverseList.push('zl');
    layui.use(['jquery', 'table', 'form', 'layer'], function () {
        var $ = layui.jquery,
            table = layui.table,
            form = layui.form,
            layer = layui.layer;

        $(function () {
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
                {type: 'checkbox', width: 50, fixed: 'left'},
                {
                    field: 'bdcdyh', title: '不动产单元号', align: "center",width: 300, templet: function (obj) {
                        if (!isNullOrEmpty(obj.bdcdyh)) {
                            var qjgldm =!isNullOrEmpty(obj.qjgldm)?obj.qjgldm:"";
                            var zl =!isNullOrEmpty(obj.zl)?obj.zl:"";
                            return '<span style="color:#1d87d1; cursor:pointer" title="' + obj.bdcdyh + '" onclick="selectCf(' + "'" + obj.bdcdyh + "','" + obj.cfwh +"','" + obj.xmid +"','"+qjgldm+"'"+',' +"'"+zl+"'" + ')">' + formatBdcdyh(obj.bdcdyh) + '</span>'
                        } else {
                            return '<span></span>';
                        }
                    }
                },
                {field: 'cfwh', title: '查封文号', align: "center", width: 200},
                {field: 'cflx', title: '查封类型', align: "center", width: 120, templet: function (d) {
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
                {field: 'cfjg', title: '查封机关', align: "center", width:200},
                {field: 'ywrmc', title: '被查封人', align: "center", width:120},
                {field: 'cfqssj', sort: true, title: '查封起始时间', align: "center", minWidth: 160,
                    templet: function (d) {
                        if (d.cfqssj) {
                            return Format(format(d.cfqssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }},
                {field: 'cfjssj', sort: true, title: '查封结束时间', align: "center", minWidth: 160,
                    templet: function (d) {
                        if (d.cfjssj) {
                            return Format(format(d.cfjssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }},
                {field: 'zl', title: '坐落', align: "center", width: 400},
                {field: 'zt', title: '状态', align: "center", width: 200, templet: '#bdcdyzt', align: "center"}
            ];

            var tableConfig = {
                id: 'cfTable',
                toolbar: "#cftoolbar",
                defaultToolbar: ['filter'],
                cols: [col],
                limit: 10,
                request: {
                    limitName: 'size' //每页数据量的参数名，默认：limit
                },
                parseData: function (res) { //res 即为原始返回的数据
                    // 设置选中行
                    if(res.content && res.content.length > 0){
                        var checkedData = layui.sessionData('checkedData');
                        res.content.forEach(function (v) {
                            $.each(checkedData, function(key, value){
                                if(key == v.bdcdyh){
                                    v.LAY_CHECKED = true;
                                }
                            })
                        });
                    }
                    return {
                        code: res.code, //解析接口状态
                        msg: res.msg, //解析提示文本
                        count: res.totalElements, //解析数据长度
                        data: res.content //解析数据列表
                    }
                },
                done: function (res, curr, count) {
                    //重新计算table高度
                    $('.bdc-table-box').height( $(".bdc-container").height()-230)
                    $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                    if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                        $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                    } else {
                        $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                    }
                }
            };

            // 表格初始化
            table.init('cfTable', tableConfig);

            // 点击查询提交表单
            form.on("submit(search)", function (data) {
                var url = getContextPath() + "/rest/v1.0/blxx/blxz/cf";

                var cxObj = {};
                $(".cfcxtj").each(function (i) {
                    var name = $(this).attr('name');
                    cxObj[name] = $(this).val();
                });
                cxObj.zl = deleteWhitespace(cxObj.zl, "edge");
                if (!isNullOrEmpty(cxObj.zl)) {
                    cxObj.zl = cxObj.zl.replace(/,/g, '%').replace(/ /g, "%").replace(/，/g, "%");
                }
                cxObj.bdcdyh = deleteWhitespace(cxObj.bdcdyh, "all");
                cxObj.cfwh = deleteWhitespace(cxObj.cfwh, "edge");
                cxObj.ycqzh = deleteWhitespace(cxObj.ycqzh, "edge");
                tableReload('cfTable', cxObj, url);
                // 不进行页面跳转
                return false;
            });
            // 监听复选框事件，缓存选中的行数据
            table.on('checkbox(cfTable)', function (obj) {
                // 获取选中或者取消的数据
                var data = obj.type == 'one' ? [obj.data] : currentPageData;
                $.each(data, function (i, v) {
                    var keyT = v.bdcdyh;
                    if (obj.checked) {
                        //.增加已选中项
                        layui.sessionData('checkedData', {
                            key: keyT, value: v
                        });
                    } else {
                        //.删除
                        layui.sessionData('checkedData', {
                            key: keyT, remove: true
                        });
                    }
                });
            });

            table.on('toolbar(cfTable)', function (obj) {
                //获得 lay-event 对应的值
                var layEvent = obj.event;
                if (layEvent === "xzcf") {
                var checkStatus = table.checkStatus(obj.config.id);
                var selectData = checkStatus.data;
                if (isNullOrEmpty(selectData)){
                    warnMsg("请选择数据！");
                    return;
                }
                // 选择查封传递bdcdyh，cfwh，xmid，qjgldm
                var bdcdyhList = [];
                var cfwhList = [];
                var paramList = [];
                for (var i = 0; i < selectData.length; i++) {
                    var map = {};
                    map.bdcdyh = selectData[i].bdcdyh;
                    map.cfwh = !isNullOrEmpty(selectData[i].cfwh)?selectData[i].cfwh:"";
                    map.yxmid = selectData[i].xmid;
                    map.qjgldm = !isNullOrEmpty(selectData[i].qjgldm)?selectData[i].qjgldm:"";
                    map.ptgzldyid = processDefKey;
                    map.gzldyid =$("#lcxl_select").val();
                    map.lcmc = $("#lcxl_select option:selected " ).text();
                    if (choosebdcdyh.indexOf($("#lcdl_select option:selected " ).text()) != -1) {
                        map.qlsjly = "1";
                    }
                    map.blshlx = -1;
                    map.zl = selectData[i].zl||"";
                    paramList.push(map);

                    bdcdyhList.push(selectData[i].bdcdyh);
                    if(!isNullOrEmpty(selectData[i].cfwh)){
                        cfwhList.push(selectData[i].cfwh);
                    }
                }
                    //addModel();
                    //console.log(paramList);
                    $("#bdcdyh").val(bdcdyhList.join(","));
                    $("#cfwh").val(cfwhList.join(","));
                    plXxblParamsList = paramList;
                    addDyhGzyz();
                }
                if(layEvent ==="chooseCf"){
                    var url = getContextPath() + "/view/xxbl/choose_detail.html?type=chooseCq&sessionKey="+sessionKey;
                    layer.open({
                        type: 2,
                        area: ['1150px', '600px'],
                        fixed: false, //不固定
                        title: "已选信息",
                        content: url,
                        btnAlign: "c"
                    });
                }
                if(layEvent ==="cleanCf"){
                    sessionStorage.setItem(sessionKey,null);
                    successMsg("清空成功");
                }
            })

        });

    });

}

/**
 * 选择不动产单元，关闭弹出层传递 bdcdyh 到父页面
 * @param obj 选择的数据
 */
function selectCf(bdcdyh, cfwh, xmid,qjgldm,zl) {
    //页面输入框赋值
    $("#bdcdyh").val(bdcdyh);
    $("#qjgldm").val(qjgldm);
    $("#yxmid").val(xmid);
    if(!isNullOrEmpty(cfwh)){
        $("#cfwh").val(cfwh);
    }


    var bdcBlxxDTO = {};
    bdcBlxxDTO["bdcdyh"] = bdcdyh;
    bdcBlxxDTO["yqllx"] = yqllx;
    bdcBlxxDTO["yxmid"] = xmid;
    if(!isNullOrEmpty(cfwh)){
        bdcBlxxDTO["cfwh"] = cfwh;
    }
    bdcBlxxDTO["ptgzldyid"] = processDefKey;
    bdcBlxxDTO["gzldyid"] =$("#lcxl_select" ).val();
    bdcBlxxDTO["lcmc"] = $("#lcxl_select option:selected " ).text();
    if (choosebdcdyh.indexOf($("#lcdl_select option:selected " ).text()) != -1) {
        bdcBlxxDTO["qlsjly"] = "1";
    }
    bdcBlxxDTO["blshlx"] = -1;
    bdcBlxxDTO["zl"] = zl;
    plXxblParamsList = [];
    plXxblParamsList.push(bdcBlxxDTO);
    addDyhGzyz();
}
