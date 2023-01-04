var gzlslid = $.getUrlParam("gzlslid");
var qlrlx = '1';
var jkname= $.getUrlParam("jkname");
var key ="";
var mlmc= getQueryString("mlmc");
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;
    // 婚姻登记信息
    var hydjxxData = [];
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

        //监听婚姻登记行工具事件
        table.on('tool(pageTable)', function (obj) {
            var data = obj.data;
            form.val('hydjxxForm', data);
            //小弹出层
            layer.open({
                title: '婚姻登记信息',
                type: 1,
                area: ['450px', '600px'],
                // btn: ['关闭'],
                content: $('#bdc-popup-small')
                , yes: function (index, layero) {
                }
                , cancel: function () {
                    //右上角关闭回调
                    $("#hydjxxForm")[0].reset();
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                , success: function () {

                }
            });

        });

        // 表格信息
        function init() {
            table.render({
                elem: '#pageTable',
                toolbar: '#toolbarDemo',
                title: '婚姻登记信息',
                defaultToolbar: ['filter', 'print', 'exports'],
                even: true,
                cols: [[
                    {type: 'numbers', width: 50, fixed: 'left'},
                    {field: 'op_type', title: '婚姻登记业务类型', minwidth: 80},
                    {field: 'dept_name', title: '登记机关名称', minwidth: 80},
                    {field: 'op_date', title: '登记日期', minwidth: 200},
                    {field: 'cert_no', title: '结婚证/离婚证字号', minwidth: 160},
                    {field: 'name_man', title: '男方姓名', minwidth: 80},
                    {field: 'name_woman', title: '女方姓名', minwidth: 80},
                    {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 80}
                ]],
                data: hydjxxData,
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

        // 获取查询参数
        var qlrDataList =[];

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
                            data: JSON.stringify({"gzlslid":gzlslid,"qlrlx":qlrlx}),
                            contentType: 'application/json',
                            success: function (data) {
                                removeModal();

                                if(data && data.length>0){
                                    var optionList = '';

                                    for (var i = 0; i < data.length; i++) {
                                        qlrDataList.push({ qlrmc: data[i].qlrmc, zjh: data[i].zjh });
                                        optionList += '<option value="'+ data[i].qlrmc + '">' + data[i].qlrmc + '</option>'
                                    }
                                    $('#qlrmc').append(optionList);
                                    if (qlrDataList.length > 0){
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
        // 查询事件
        function search() {
            var slbh = $("#slbh").val();
            var qlrmc = $("#qlrmc").val();
            var zjh = $("#zjh").val();

            if (isNullOrEmpty(slbh) || isNullOrEmpty(qlrmc) || isNullOrEmpty(zjh)) {
                warnMsg("请输入查询接口完整条件！");
                return;
            }
            addModel();
            var searchData = {"slbh": slbh, "qlrmc": qlrmc, "zjh": zjh};

            // 查询婚姻登记信息
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/bjjk/hydjxx",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(searchData),
                success: function (data) {
                    if (data) {
                        hydjxxData = data;
                        dealCxjgxx("success",jkname);
                        key = saveJkDataToRedis(hydjxxData);
                    }else {
                        warnMsg("身份核查失败，请重试！");
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