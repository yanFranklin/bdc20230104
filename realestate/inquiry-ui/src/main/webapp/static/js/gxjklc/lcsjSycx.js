/**
 * 收养信息查询
 */
// 查询参数
var gzlslid = $.getUrlParam("gzlslid");
var qlrlx = '1';
var formStateId = $.getUrlParam("formStateId");
var excludeQlrlx = '1';
var key = "";
var searchData = {};
var jkname = $.getUrlParam("jkname");
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var laytpl = layui.laytpl;
    form.render();

    $(function () {

        getSearch();

        getStateById(false, formStateId, 'lcsjDqjg');

        $('#search').on('click', function () {
            // 获取查询条件
            var qlrmc = $(this).parents('.layui-form-item').find('#qlrmc').val();
            var zjh = $(this).parents('.layui-form-item').find('#zjh').val();

            // 判空
            if (!isNotBlank(qlrmc) || !isNotBlank(zjh)) {
                warnMsg('请输入必要查询条件');
                return false;
            }

            // 组织查询条件
            var getData = {
                "cxywcs": [],
            };
            getData.cxywcs.push({"qlrmc": qlrmc, "zjh": zjh});
            searchData = getData;

            // 展示查询条件，加载表格内容
            generateTable(searchData, false);

            // 查询条件不为空进行查询
            if (searchData && searchData.cxywcs && searchData.cxywcs.length > 0) {
                search();
            }
        });

        //获取查询条件
        var qlrDataList = [];

        function getSearch() {
            if (isNullOrEmpty(gzlslid)) {
                return false;
            }
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/xmxx",
                type: "POST",
                data: JSON.stringify({"gzlslid": gzlslid}),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();

                    if (data && data.length > 0) {
                        $("#slbh").val(data[0].slbh);
                        $.ajax({
                            url: "/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/qlr",
                            type: "POST",
                            data: JSON.stringify({"gzlslid": gzlslid}),
                            contentType: 'application/json',
                            success: function (data) {
                                removeModal();
                                if (data && data.length > 0) {
                                    var optionList = '';
                                    for (var i = 0; i < data.length; i++) {
                                        qlrDataList.push({qlrmc: data[i].qlrmc, zjh: data[i].zjh});
                                        optionList += '<option value="' + data[i].qlrmc + '">' + data[i].qlrmc + '</option>';
                                    }
                                    $('#qlrmc').append(optionList);
                                    if (qlrDataList.length > 0) {
                                        $("#qlrmc").val(qlrDataList[0].qlrmc);
                                        $("#zjh").val(qlrDataList[0].zjh);
                                    }
                                    form.render("select");
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
            if (isNotBlank(data.value)) {
                for (var i = 0; i < qlrDataList.length; i++) {
                    if (data.value == qlrDataList[i].qlrmc) {
                        $("#zjh").val(qlrDataList[i].zjh);
                    }
                }
            }
        });
    // 点击查询
        $('#qtqlr').on('click', function () {
            qtqlr();
        });
        //其他权利人
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

                    qlrDataList.push({qlrmc: newqlrmc, zjh: newzjh});
                    var optionList = '<option value="' + newqlrmc + '">' + newqlrmc + '</option>';


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

        //查询
        function search() {
            addModel();
            //循环调用
            if (searchData.cxywcs.length > 0) {
                var resultData = [];
                for (var paramlength = 0; paramlength < searchData.cxywcs.length; paramlength++) {
                    var param = {
                        "BSYRXM": searchData.cxywcs[paramlength].qlrmc,
                        "BSYRSFZJH": searchData.cxywcs[paramlength].zjh
                    };
                    // 重新请求
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/gx/shucheng/bjjk/sycx",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async: false,
                        success: function (data) {
                            // 结果数据不为空，重新加载表格
                            if (data && data.length > 0) {
                                for (let i = 0; i < data.length; i++) {
                                    // 将需要展示的查询条件加入结果数据
                                    data[i].qlrmc = param.BSYRXM;
                                    data[i].zjh = param.BSYRSFZJH;
                                    resultData.push(data[i]);
                                }
                            }
                            dealCxjgxx("success", jkname);
                        },
                        error: function (xhr, status, error) {
                            dealCxjgxx("fail", jkname);
                            removeModal();
                            delAjaxErrorMsg(xhr)
                        }
                    });

                }

                key = saveJkDataToRedis(resultData);
                removeModal();
                if (resultData.length > 0) {
                    generateTable(resultData, true);
                }

            }

        }


        function generateTable(data, isSearch) {
            $('#lcsbj').removeClass('layui-hide');
            var getTpl = tableTpl.innerHTML;

            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            //uploadPdf(gzlslid, "sycx", "收养信息查询", key, "省级数据共享交换平台");
        });

    });

});