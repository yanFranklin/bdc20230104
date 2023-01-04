var gzlslid = $.getUrlParam("gzlslid");
var qlrlx = '1';
var jkname= $.getUrlParam("jkname");
var key ="";
var mlmc= getQueryString("mlmc");
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form;
    // 中编办信息
    var zbbData = [];
    $(function () {
        // 初始化
        init();
        getSearch();

        // 点击查询
        $('#qtqlr').on('click', function () {
            qtqlr();
        });

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        // 表格信息
        function init() {
            table.render({
                elem: '#pageTable',
                toolbar: '#toolbarDemo',
                title: '中编办查询',
                defaultToolbar: ['filter', 'print', 'exports'],
                even: true,
                cols: [[
                    {type: 'numbers', width: 50, fixed: 'left'},
                    {field: 'djgljg', title: '登记管理机关', width: 150},
                    {field: 'jgdz', title: '机构地址', width: 150},
                    {field: 'sjrq', title: '数据日期', width: 150},
                    {field: 'qtmc', title: '其他名称', width: 200},
                    {field: 'zzhywfw', title: '宗旨和业务范围', width: 200},
                    {field: 'bfrq', title: '颁发日期', width: 200},
                    {field: 'tyshxydm', title: '统一社会信用代码', width: 150},
                    {field: 'jgxz', title: '机构性质', width: 150},
                    {field: 'onemc', title: '第一名称', width: 150},
                    {field: 'twomc', title: '第二名称', width: 150},
                    {field: 'threemc', title: '第三名称', width: 150},
                    {field: 'fmjg', title: '赋码机关', width: 150},
                    {field: 'qlbs', title: '权力标识', width: 150},
                    {field: 'kbzj', title: '开办资金', width: 150},
                    {
                        field: 'ywlx', title: '业务类型', width: 150,
                        templet: function (d) {
                            if (d && d.ywlx) {
                                if (0 == d.ywlx || '0' == d.ywlx) {
                                    return '<p>设立</p>';
                                } else if (1 == d.ywlx || '1' == d.ywlx) {
                                    return '<p>变更</p>';
                                } else if (3 == d.ywlx || '3' == d.ywlx) {
                                    return '<p>注销</p>';
                                } else if (4 == d.ywlx || '4' == d.ywlx) {
                                    return '<p>证书补领</p>';
                                } else if (5 == d.ywlx || '5' == d.ywlx) {
                                    return '<p>重新申领证书</p>';
                                }
                            }
                            return '';
                        }
                    },
                    {
                        field: 'zt', title: '状态', width: 150,
                        templet: function (d) {
                            if (d) {
                                if (1 == d.zt || '1' == d.zt) {
                                    return '<p style="color:#f54743">已撤销（证书废止）</p>';
                                } else if (2 == d.zt || '2' == d.zt) {
                                    return '<p style="color:#f54743">已吊销</p>';
                                } else if (3 == d.zt || '3' == d.zt) {
                                    return '<p style="color:#f54743">冻结</p>';
                                } else if (9 == d.zt || '9' == d.zt) {
                                    return '<p style="color:#f54743">注销</p>';
                                } else {
                                    return '<p style="color:#10a54a">正常</p>';
                                }
                            }
                        }
                    },
                    {field: 'fzr', title: '负责人（法定代表人）', width: 150},
                    {field: 'yxqx_s', title: '有效期（始）', width: 150},
                    {field: 'yxqx_z', title: '有效期（至）', width: 150},
                    {field: 'zs', title: '住所', width: 150},
                    {field: 'jbdw', title: '举办单位', width: 150}
                ]],
                data: zbbData,
                page: true,
                done: function () {
                    setHeight();
                }
            });

            if(isNullOrEmpty(gzlslid)){
                $(".upload-pdf").hide();
            }

            //头工具栏事件
            table.on('toolbar(pageTable)', function (obj) {
                switch (obj.event) {
                    case 'upload-pdf':
                        uploadPdf(gzlslid,jkname,mlmc,key,"省级数据共享交换平台");
                        break;
                }

            });
        }

        // 查询事假
        function search() {
            var slbh = $("#slbh").val();
            var qlrmc = $("#qlrmc").val();
            var zjh = $("#zjh").val();

            if (isNullOrEmpty(slbh) || isNullOrEmpty(qlrmc) || isNullOrEmpty(zjh)) {
                warnMsg("请输入查询接口完整条件！");
                return;
            }
            addModel();
            var searchData = {"slbh": slbh, "qlrmc": qlrmc, "zjh": zjh, "type": "10"};

            // 查询婚姻登记信息
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/bjjk/zbb",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(searchData),
                success: function (data) {
                    if (data) {
                        zbbData = data;
                        dealCxjgxx("success",jkname);
                        key = saveJkDataToRedis(zbbData);
                    }else {
                        warnMsg("查询失败，请重试！");
                    }
                }, error: function () {
                    init();
                    dealCxjgxx("fail",jkname);
                    warnMsg("查询失败，请重试！");
                }, complete: function () {
                    init();
                    removeModal();
                }
            });
        }
        var qlrDataList = [];
        function getSearch() {
            if(isNullOrEmpty(gzlslid)){
                return false;
            }
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/xmxx",
                type: "POST",
                data: JSON.stringify({"gzlslid":gzlslid}),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();

                    if(data && data.length>0){
                        $("#slbh").val(data[0].slbh);
                        $.ajax({
                            url:"/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/qlr",
                            type: "POST",
                            data: JSON.stringify({"gzlslid":gzlslid}),
                            contentType: 'application/json',
                            success: function (data) {
                                removeModal();

                                if(data && data.length>0){
                                    var optionList = '';

                                    for (var i = 0; i < data.length; i++) {
                                        if(data[i].qlrlx != qlrlx) {
                                            qlrDataList.push({qlrmc: data[i].qlrmc, zjh: data[i].zjh});
                                            optionList += '<option value="' + data[i].qlrmc + '">' + data[i].qlrmc + '</option>';
                                        }
                                    }
                                    $('#qlrmc').append(optionList);
                                    if(qlrDataList.length > 0) {
                                        $("#qlrmc").val(qlrDataList[0].qlrmc);
                                        $("#zjh").val(qlrDataList[0].zjh);
                                    }
                                    form.render("select");
                                    $('#search').click();


                                }

                            },
                            error: function (xhr, status, error) {
                                removeModal();
                                delAjaxErrorMsg(xhr)
                            }
                        });
                    }

                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }
        //渲染
        form.on('select(qlrmc)', function (data) {
            if(isNotBlank(data.value)) {
                for (var i = 0; i < qlrDataList.length; i++) {
                    if (data.value == qlrDataList[i].qlrmc) {
                        $("#zjh").val(qlrDataList[i].zjh);
                    }
                }
            }
        });

        //扫描下载
        function qtqlr() {
            layer.open({
                type: 1,
                title: '查询其他权利人',
                skin: 'bdc-ckgdxx-layer',
                area: ['430px'],
                content:
                    '<div id="bdc-popup-small ckgdxx-layer">' +
                    '<form class="layui-form" action="">' +
                    '<div class="layui-form-item pf-form-item">' +
                    '<div class="layui-inline" style="width: 100%;">' +
                    ' <label class="layui-form-label">权利人名称:</label>' +
                    ' <div class="layui-input-inline">' +
                    '<input type="text" autofocus="true" name="newqlrmc" id="newqlrmc" autocomplete="off" placeholder="请输入" class="layui-input">' +
                    '</div>' +
                    '</div>' +
                    '<div class="layui-inline" style="width: 100%;">' +
                    ' <label class="layui-form-label">证件号:</label>' +
                    ' <div class="layui-input-inline">' +
                    '<input type="text" autofocus="true" name="newzjh" id="newzjh" autocomplete="off" placeholder="请输入" class="layui-input">' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</form>' +
                    '</div>',
                btn: ['查询'],
                btnAlign: 'c',
                yes: function (index, layero) {
                    //确定操作
                    var newqlrmc = layero.find("input[name='newqlrmc']").val();
                    var newzjh = layero.find("input[name='newzjh']").val();

                    qlrDataList.push({ qlrmc: newqlrmc, zjh: newzjh });
                    var optionList = '<option value="'+ newqlrmc + '">' + newqlrmc + '</option>';


                    $('#qlrmc').append(optionList);
                    $("#qlrmc").val(newqlrmc);
                    $("#zjh").val(newzjh);
                    form.render("select");
                    $('#search').click();

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
});