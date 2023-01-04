
function loadCqTable(gzldyid){
    $(".xzcq").show();
    $(".xzbdcdyh").hide();
    $(".xzcf").hide();
    $(".bdc-tip").hide()
    var gzldyid = gzldyid;
    layui.use(['jquery', 'table', 'form', 'layer'], function () {
        var $ = layui.jquery,
            table = layui.table,
            form = layui.form,
            layer = layui.layer;
        $(function () {
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
                {type: 'checkbox', width: 50, fixed: 'left'},
                {
                    field: 'bdcqzh', title: '不动产权证号', align: "center", templet: function (obj) {
                        if (!isNullOrEmpty(obj.bdcqzh)) {
                            var qjgldm =!isNullOrEmpty(obj.qjgldm)?obj.qjgldm:"";
                            var zl =!isNullOrEmpty(obj.zl)?obj.zl:"";
                            return '<span style="color:#1d87d1; cursor:pointer" title="' + obj.bdcqzh + '" onclick="selectDgBdcqzh(' + "'" + obj.bdcqzh + "','" + obj.bdcdyh + "','" +qjgldm+"'"+',' +"'"+zl+"'" + ')">' + obj.bdcqzh + '</span>'
                        } else {
                            return '<span></span>';
                        }
                    }
                },
                {
                    field: 'bdcdyh', title: '不动产单元号', align: "center", width: 400, templet: function (obj) {
                        return '<span>' + formatBdcdyh(obj.bdcdyh) + '</span>'
                    }
                },
                {field: 'qllx', title: '权利类型', align: "center", width: 500, hide: 'true'},
                {field: 'zl', title: '坐落', align: "center", width: 400},
                {field: 'qlrmc', title: '权利人',width: 200, align: "center"}
            ];

            var tableConfig = {
                id: 'cqTable',
                toolbar: "#cqtoolbar",
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
            table.init('cqTable', tableConfig);

            // 点击查询提交表单
            form.on("submit(search)", function (data) {
                var url = getContextPath() + "/rest/v1.0/blxx/blxz/bdcqz";
                var cxObj = {};
                $(".cqcxtj").each(function (i) {
                    var name = $(this).attr('name');
                    cxObj[name] = $(this).val();
                });
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
                tableReload('cqTable', cxObj, url);
                // 不进行页面跳转
                return false;
            });

            // 监听复选框事件，缓存选中的行数据
            table.on('checkbox(cqTable)', function (obj) {
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

            table.on('toolbar(cqTable)', function (obj) {

                //获得 lay-event 对应的值
                var layEvent = obj.event;
                if (layEvent === "xzcq") {
                var checkStatus = table.checkStatus(obj.config.id);
                var selectData = checkStatus.data;
                if (isNullOrEmpty(selectData)){
                    warnMsg("请选择数据！");
                    return;
                }
                var bdcdyhList = [];
                var ycqzhList = [];
                var paramList = [];
                for (var i = 0; i < selectData.length; i++) {
                    var map = {};
                    map.bdcdyh = selectData[i].bdcdyh;
                    map.ycqzh = !isNullOrEmpty(selectData[i].bdcqzh)?selectData[i].bdcqzh:"";
                    map.qjgldm = !isNullOrEmpty(selectData[i].qjgldm)?selectData[i].qjgldm:"";

                    map.ptgzldyid = processDefKey;
                    map.gzldyid = $("#lcxl_select").val();
                    map.lcmc = $("#lcxl_select option:selected " ).text()
                    if (choosebdcdyh.indexOf($("#lcdl_select option:selected " ).text()) != -1) {
                        map.qlsjly = "1";
                    }
                    map.blshlx = -1;
                    map.zl = selectData[i].zl||"";
                    paramList.push(map);

                    bdcdyhList.push(selectData[i].bdcdyh);
                    if(!isNullOrEmpty(selectData[i].bdcqzh)){
                        ycqzhList.push(selectData[i].bdcqzh);
                    }
                }

                    //选择产权，传递三个参数，ycqzh，bdcdyh，qjgldm
                    //addModel();
                    //console.log(paramList);
                    $("#bdcdyh").val(bdcdyhList.join(","));
                    $("#ycqzh").val(ycqzhList.join(","));
                    plXxblParamsList = paramList;
                    addDyhGzyz();
                }
                if(layEvent ==="chooseCq"){
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
                if(layEvent ==="cleanCq"){
                    sessionStorage.setItem(sessionKey,null);
                    successMsg("清空成功");
                }
            })

        });

    });

}

function selectDgBdcqzh(bdcqzh, bdcdyh,qjgldm,zl) {
    //页面输入框赋值
    $("#bdcdyh").val(bdcdyh);
    //$("#ycqzh").val(bdcqzh);
    $("#qjgldm").val(qjgldm);
    if(!isNullOrEmpty(bdcqzh)){
        $("#ycqzh").val(bdcqzh);
    }
    var bdcBlxxDTO = {};

    bdcBlxxDTO["bdcdyh"] = bdcdyh;
    bdcBlxxDTO["qjgldm"] = qjgldm;
    if(!isNullOrEmpty(ycqzh)){
        bdcBlxxDTO["ycqzh"] = bdcqzh;
    }

    bdcBlxxDTO["ptgzldyid"] = processDefKey;
    bdcBlxxDTO["gzldyid"] = $("#lcxl_select").val();
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
