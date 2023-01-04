function loadBdcdyhTable(gzldyid){
    $(".xzbdcdyh").show();

    var gzldyid = gzldyid;
    layui.use(['jquery', 'table', 'form', 'layer'], function () {
        //console.log(gzldyid);
        var $ = layui.jquery,
            table = layui.table,
            form = layui.form,
            layer = layui.layer;


        // 获取参数（工作流定义 id）
        //var gzldyid = gzldyid;
        // yxmid 获取到说明是复制功能打开页面
        yxmid = $.getUrlParam('yxmid');
        // 被复制的 bdcdyh 和 qllx
        var ybdcdyh = $.getUrlParam('ybdcdyh');
        var yqllx = $.getUrlParam('qllx');

        // 0. 获取字典项和选择台账配置内容
        if (isNullOrEmpty(gzldyid) && !isNullOrEmpty(yxmid)) {


        }else {
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
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth:400,templet: function (obj) {
                    if (!isNullOrEmpty(obj.bdcdyh)) {
                        var qjgldm =!isNullOrEmpty(obj.qjgldm)?obj.qjgldm:"";
                        var qsxz =!isNullOrEmpty(obj.qsxz)?obj.qsxz:"";
                        var zl =!isNullOrEmpty(obj.zl)?obj.zl:"";
                        var qlr =!isNullOrEmpty(obj.qlr)?obj.qlr:"";
                        return '<span style="color:#1d87d1; cursor:pointer" title="' + obj.bdcdyh + '" onclick="selectBdcdyh(' + "'" + obj.bdcdyh + "'" + ','+"'"+qjgldm+"','"+qsxz+"'"+',' +"'"+zl+"'" +',' +"'"+qlr+"'"  +')">' + formatBdcdyh(obj.bdcdyh) + '</span>'
                    } else {
                        return '<span></span>';
                    }
                }, align: "center"
            },
            {field: 'zl', title: '坐落', minwidth: 400,align: "center"},
            {field: 'qlr', title: '权利人', minwidth: 200, align: "center"},
            {field: 'zt', title: '状态',  width: 140,templet: '#bdcdyzt', align: "center"}
        ];

        var tableConfig = {
            id: 'bdcdyhTable',
            toolbar: "#toolbar",
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
                reverseTableCell(reverseList);
            }
        };
        $(".bdc-tip").hide()
        $(".xzcf").hide();
        $(".xzcq").hide();
        // 表格初始化
        table.init('bdcdyhTable', tableConfig);
        // 点击查询提交表单
        form.on("submit(search)", function (data) {
            var url = getContextPath() + "/rest/v1.0/blxx/blxz/bdcdyh";
            //var cxObj = data.field;
            // 获取查询参数
            var cxObj = {};
            $(".bdcdyhcxtj").each(function (i) {
                var name = $(this).attr('name');
                cxObj[name] = $(this).val();
            });

            cxObj.zl = deleteWhitespace(cxObj.zl, "edge");
            if (!isNullOrEmpty(cxObj.zl)) {
                cxObj.zl = cxObj.zl.replace(/,/g, '%').replace(/ /g, "%").replace(/，/g, "%");
            }
            cxObj.qllx = bdcslxztzpz.qllx;
            cxObj.zdtzm = bdcslxztzpz.zdtzm;
            cxObj.dzwtzm = bdcslxztzpz.dzwtzm;
            cxObj.dyhcxlx = bdcslxztzpz.dyhcxlx;
            cxObj.bdcdyh = deleteWhitespace(cxObj.bdcdyh, "all");
            tableReload('bdcdyhTable', cxObj, url);
            // 不进行页面跳转
            return false;
        });

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(bdcdyhTable)', function (obj) {
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


        table.on('toolbar(bdcdyhTable)', function (obj) {
            //获得 lay-event 对应的值
            var layEvent = obj.event;
            if (layEvent === "xzbdcdyh") {
            var checkStatus = table.checkStatus(obj.config.id);
            var selectData = checkStatus.data;
            if (isNullOrEmpty(selectData)){
                warnMsg("请选择数据！");
                return;
            }
            var bdcdyhList = [];
            var paramList = [];
            for (var i = 0; i < selectData.length; i++) {
                var map = {};
                map.bdcdyh = selectData[i].bdcdyh;
                map.yqllx = !isNullOrEmpty(selectData[i].qsxz)?selectData[i].qsxz:"";
                map.qjgldm = !isNullOrEmpty(selectData[i].qjgldm)?selectData[i].qjgldm:"";

                map.ptgzldyid = processDefKey;
                map.gzldyid =  $("#lcxl_select").val();
                map.lcmc = $("#lcxl_select option:selected " ).text();
                if (choosebdcdyh.indexOf($("#lcdl_select option:selected " ).text()) != -1) {
                    map.qlsjly = "1";
                }
                map.blshlx = -1;
                map.zl = selectData[i].zl||"";
                map.qlr = selectData[i].qlr||"";
                paramList.push(map);
                bdcdyhList.push(selectData[i].bdcdyh);
            }
                //addModel();
                //console.log(paramList);
                $("#bdcdyh").val(bdcdyhList.join(","));
                plXxblParamsList = paramList;
                addDyhGzyz();
            }
            if(layEvent ==="chooseBdcdyh"){
                var url = getContextPath() + "/view/xxbl/choose_detail.html?type=chooseBdcdyh&sessionKey="+sessionKey;
                layer.open({
                    type: 2,
                    area: ['1150px', '600px'],
                    fixed: false, //不固定
                    title: "已选信息",
                    content: url,
                    btnAlign: "c"
                });
            }
            if(layEvent ==="cleanBdcdyh"){
                sessionStorage.setItem(sessionKey,null);
                successMsg("清空成功");
            }
        })



    })
}

function selectBdcdyh(bdcdyh,qjgldm,yqllx,zl,qlr) {
        // 产权类选择 bdcdyh 需要判断在登记库中有无现势产权
        if (cqxzbdcdyh === 'cq') {
            cqxzBdcdyhYz(bdcdyh,qjgldm,yqllx,zl,qlr);
        } else {
            xzBdcdyhReload(bdcdyh,qjgldm,yqllx,zl,qlr);
        }
}

/**
 * 产权选择不动产单元号验证
 * @param bdcdyh
 */
function cqxzBdcdyhYz(bdcdyh,qjgldm,yqllx,zl,qlr) {
    addModel();
    var selectDataList = [];
    for(var i = 0; i<plXxblParamsList.length;i++){
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = plXxblParamsList[i].bdcdyh;
        bdcGzYzsjDTO.qjgldm = plXxblParamsList[i].qjgldm;
        selectDataList.push(bdcGzYzsjDTO);
    }
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = "blcqxzbdcdyh";
    bdcGzYzQO.paramList = selectDataList;
    $.ajax({
        url: getContextPath() + '/rest/v1.0/blxx/bdcGzyz',
        type: 'POST',
        async: false,
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcGzYzQO),
        success: function (data) {
            removeModel();
            if (data.length > 0) {
                warnMsg(data[0].msg);
            } else {
                xzBdcdyhReload(bdcdyh,qjgldm,yqllx,zl,qlr);

            }
        }, error: function (xhr, status, error) {
            removeModel();
            delAjaxErrorMsg(xhr);
        }
    });
}


function xzBdcdyhReload(bdcdyh,qjgldm,yqllx,zl,qlr){
    //页面输入框赋值
    $("#bdcdyh").val(bdcdyh);
    $("#qjgldm").val(qjgldm);
    $("#yqllx").val(yqllx);

    var bdcBlxxDTO = {};
    bdcBlxxDTO["bdcdyh"] = bdcdyh;
    bdcBlxxDTO["yqllx"] = yqllx;
    bdcBlxxDTO["qjgldm"] = qjgldm;

    bdcBlxxDTO["ptgzldyid"] = processDefKey;
    bdcBlxxDTO["gzldyid"] =$("#lcxl_select" ).val();
    bdcBlxxDTO["lcmc"] = $("#lcxl_select option:selected " ).text();
    if (choosebdcdyh.indexOf($("#lcdl_select option:selected " ).text()) != -1) {
        bdcBlxxDTO["qlsjly"] = "1";
    }
    bdcBlxxDTO["blshlx"] = -1;
    bdcBlxxDTO["yqllx"] = yqllx;
    bdcBlxxDTO["zl"] = zl;
    bdcBlxxDTO["qlr"] = qlr;

    plXxblParamsList = [];
    plXxblParamsList.push(bdcBlxxDTO);
    addDyhGzyz();
}