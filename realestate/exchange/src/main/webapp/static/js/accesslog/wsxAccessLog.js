var regioncode = "admin";
getCurrentUserRegionCode();
function getCurrentUserRegionCode(){
    $.ajax({
        url: "../accessLog/queryregioncode",
        dataType: "json",
        cache: false,
        async: false,
        success: function (data) {
            regioncode = data.code;
            if(regioncode == 'admin' || regioncode == 'hefei'){
                $("#qxdmSelectDiv").removeClass("layui-hide");
                initQxdmSelect(data.qxList);
            }
        },
        error: function (xhr, status, error) {
            layer.alert("获取用户行政区划异常！");
        }
    });
}

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param 初始化区县列表
 * @return
 * @description 
 */
function initQxdmSelect(qxList){
    var optionHtml = "";
    if(qxList && qxList.length > 0) {
        for (var i = 0; i < qxList.length; i++) {
            var qx = qxList[i];
            optionHtml += "<option value='"+ qx.qxdm +"'>"+ qx.qxmc + "</option>";
            if(i == 0){
                regioncode = qx.qxdm;
            }
        }
    }
    $("#qxdmSelect").html(optionHtml);
}

layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl','laydate'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;
    var reverseList = ['bdcdyh','zl','bdcqzh'];
    $(function () {
        //初始化日期控件
        laydate.render({
            elem: '#kssj'
            ,type: 'datetime'
        });
        laydate.render({
            elem: '#jssj'
            ,type: 'datetime'
        });

        //点击高级查询--4的倍数
        $('#seniorSearch').on('click',function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            $(this).parent().toggleClass('bdc-button-box-four');
            $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);

            $('.layui-table-body').height($('.bdc-table-box').height() - 129);
            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 129 - 17);
        });

        //提交表单
        form.on("submit(query)", function (data) {
            tableReload('accessTable', data.field);
            return false;
        });
        var tableConfig = {
            toolbar: "#toolbarSqxx"
            , url: '../accessLog/querywsxaccesslog?regionCode=' + regioncode
            , cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'ywh', title: '业务号', width: 150},
                {field: 'bdcdyh', title: '不动产单元号', width: 250},
                {field: 'qlr', title: '权利人', width: 250},
                {field: 'zl', title: '坐落', width: 250},
                {field: 'bdcqzh', title: '不动产权证号', width: 250},
                {field: 'bjsj', title: '办结时间', width: 150},
                {field: 'ywbwid', title: '业务报文ID', width: 200},
                {
                    field: 'cgbs', title: '接入状态', width: 100
                    , templet: function (d) {
                        return jrzt(d.cgbs);
                    }
                },
                {field: 'xyzt', title: '获取响应报文状态', width: 200
                    , templet: function (d) {
                        return xyzt(d);
                    }},
                {
                    title: '操作',
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#logListToolBarTmpl',
                    width: 250
                }
            ]],
            done: function () {
                reverseTableCell(reverseList);
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body').height('56px');
                    $('.layui-table-fixed .layui-table-body').height('56px');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        };

        $('.bdc-table-box').on('mouseenter','td',function () {
            if($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1){
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
            }
            $('.layui-table-grid-down').on('click',function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                },20);
            });
        });

        loadDataTablbeByUrl("#accessTable", tableConfig);
        //头工具栏事件
        table.on('toolbar(dataTable)', function (obj) {
            if(obj.event != "LAYTABLE_COLS"){
                var checkStatus = table.checkStatus(obj.config.id);
                var data = checkStatus.data;
                if (data && data.length > 0) {
                    if (obj.event === "access") {
                        plsb(data);
                    }
                    if (obj.event === "getBw") {
                        plGetResponse(data)
                    }
                } else {
                    layer.alert("请选择一条数据进行操作")
                }
            }
        });
        table.on('tool(dataTable)', function (obj) {
            var data = obj.data;
            if (data) {
                if (obj.event === "ywbw") {
                    if (data.jrbw) {
                        layer.open({
                            title: "业务报文",
                            area: ['960px', '450px'],
                            content: '<xmp style="white-space: pre-wrap">' + data.jrbw + '</xmp>'
                        });
                    } else {
                        layer.msg("无业务报文")
                    }
                }
                if (obj.event === "xybw") {
                    layer.open({
                        title: "响应报文",
                        area: ['960px', '450px'],
                        content: '<xmp style="white-space: pre-wrap">' + data.xybw+ '</xmp>'
                    });
                }
                if (obj.event === "sjjy") {
                    var qxdm = $("#qxdmSelect").val();
                    if (data.ywh) {
                        $.ajax({
                            url: "../accessLog/checkwsxaccessdata",
                            dataType: "json",
                            data: {
                                qxdm: qxdm,
                                ywh: data.ywh,
                                bdcdyh:data.bdcdyh
                            },
                            success: function (data) {
                                if (data) {
                                    layer.msg("校验成功");
                                } else {
                                    layer.msg("校验失败");
                                }
                            }
                        });
                    } else {
                        layer.msg("项目主键为空")
                    }
                }
            } else {
                layer.alert("当前数据缺失，请检查数据");
                return false
            }
        });
        //接入状态
        function jrzt(cgbs) {
            if (cgbs != null) {
                if (cgbs === 1) {
                    return "成功";
                } else {
                    return "失败";
                }
            } else {
                return "未接入";
            }
        }

        function xyzt(data){
            if(data.xyxx == '响应成功'){
                return "响应成功";
            }else if(data.xybm != null){
                return "响应失败";
            }
            return '未获取';
        }

        function plsb(data) {
            var qxdm = $("#qxdmSelect").val();
            var ywList = [];
            for (var i = 0; i < data.length; i++) {
                ywList.push({ywh:data[i].ywh,bdcdyh:data[i].bdcdyh});
            }
            var reqData = {requestList: ywList,qxdm:qxdm};
            addModel();
            $.ajax({
                url: "../accessLog/wsx/plnationalaccess",
                dataType: "json",
                type: "post",
                data: {accessvo:JSON.stringify(reqData)},
                success: function (data) {
                    layer.msg("上报结束");
                    removeModal();
                },
                error: function (xhr, status, error) {
                    layer.msg("上报失败!");
                    removeModal()
                }
            });
        }

        function plGetResponse(data) {
            var xmidList = [];
            for (var i = 0; i < data.length; i++) {
                xmidList.push(data[i].ywh);
            }
            addModel();
            $.ajax({
                url: "../accessLog/getAccessResponse",
                data: "logType=province&xmidList=" + xmidList,
                success: function (data) {
                    layer.msg(data);
                    removeModal()
                },
                error: function (xhr, status, error) {
                    layer.msg("获取失败!");
                    removeModal()
                }
            });
        }
    });

});