var gzlslid = getQueryString('gzlslid');
var qlrlx= '1';
var jkname= $.getUrlParam("jkname");
var key ="";
var mlmc= getQueryString("mlmc");
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        table = layui.table;
    var form = layui.form;
    $(function () {
        init();
        function init(){
            table.render({
                elem: '#pageTable',
                toolbar: '#toolbarDemo',
                title: '卫计委死亡证明查询接口查询',
                defaultToolbar: ['filter', 'print', 'exports'],
                even: true,
                cols: [[
                    {type: 'numbers', width: 50, fixed: 'left'},
                    {field: 'message',  title: '状态',        width: 150},
                    {field: 'errorMsg', title: '错误信息',     width: 150},
                    {field: 'gmsfhm',   title: '公民身份证号', width: 200},
                    {field: 'xm',       title: '姓名',        width: 200},
                    {field: 'xbdm',     title: '性别',        width: 100,
                        templet: function (d) {
                            if (isNullOrEmpty(d.xbdm)) {
                                return "";
                            } else if("1" == d.xbdm.toString()){
                                return "男性";
                            } else if("2" == d.xbdm.toString()){
                                return "女性";
                            }
                        }
                    },
                    {field: 'mzdm',     title: '民族代码',    width: 200 ,
                        templet: function (d) {
                            if (isNullOrEmpty(d.mzdm)) {
                                return "";
                            } else {
                                return formatMzdb(d.mzdm.toString());
                            }
                        }
                    },
                    {field: 'swyy',     title: '死亡原因',    width: 150}
                ]],
                data: [],
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

        var qlrDataList =[];

        if (!isNullOrEmpty(gzlslid)) {
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

        // 点击查询
        $('#qtqlr').on('click', function () {
            qtqlr();
        });

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        //渲染
        form.on('select(qlrmc)', function (data) {
            if(isNotBlank(data.value)) {
                for (var i = 0; i < qlrDataList.length; i++) {
                    if (data.value == qlrDataList[i].qlrmc) {
                        $("#zjh").val(qlrDataList[i].zjh);
                    }
                }
            }
            // form.render('select');
        });


        function search() {
            var slbh = $("#slbh").val();
            var qlrmc = $("#qlrmc").val();
            var zjh = $("#zjh").val();

            if(isNullOrEmpty(qlrmc) || isNullOrEmpty(zjh) ){
                warnMsg("请输入查询姓名和证件号！");
                return;
            }

            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/bjjk/swzm",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({"slbh":slbh, "qlrmc": qlrmc, "zjh": zjh}),
                success: function (data) {
                    if(data){
                        var code = data.resultinfo[0].success;
                        var result = [];
                        if("1" == code){
                             result = [{"message": data.resultinfo[0].message, "errorMsg": data.body[0].errorMsg}];
                        } else {

                            var body = data.body[0];
                            $.each(body.data,function(index ,item){
                                result.push({"message": data.resultinfo[0].message, "errorMsg": item.errorMsg,
                                    "gmsfhm": item.gmsfhm, "mzdm": item.mzdm,
                                    "swyy": item.swyy, "xm": item.xm,
                                    "xbdm": item.xbdm});
                            });
                        }
                        dealCxjgxx("success",jkname);
                        key = saveJkDataToRedis(result);
                        table.render({
                            elem: '#pageTable',
                            toolbar: '#toolbarDemo',
                            title: '死亡证明查询',
                            defaultToolbar: ['filter', 'print', 'exports'],
                            even: true,
                            cols: [[
                                {type: 'numbers', width: 50, fixed: 'left'},
                                {field: 'message',  title: '状态',        width: 150},
                                {field: 'errorMsg', title: '错误信息',     width: 150},
                                {field: 'gmsfhm',   title: '公民身份证号', width: 200},
                                {field: 'xm',       title: '姓名',        width: 200},
                                {field: 'xbdm',     title: '性别',        width: 100,
                                    templet: function (d) {
                                        if (isNullOrEmpty(d.xbdm)) {
                                            return "";
                                        } else if("1" == d.xbdm.toString()){
                                            return "男性";
                                        } else if("2" == d.xbdm.toString()){
                                            return "女性";
                                        }
                                    }
                                },
                                {field: 'mzdm',     title: '民族代码',    width: 200 ,
                                    templet: function (d) {
                                        if (isNullOrEmpty(d.mzdm)) {
                                            return "";
                                        } else {
                                            return formatMzdb(d.mzdm.toString());
                                        }
                                    }
                                },
                                {field: 'swyy',     title: '死亡原因',    minWidth: 200}
                            ]],
                            data: result,
                            page: true,
                            done: function () {
                                setHeight();
                            }
                        });
                    } else {
                        warnMsg("未查询到死亡证明结果！");
                    }
                },error:function(){
                    init();
                    dealCxjgxx("fail",jkname);
                    warnMsg("死亡证明查询失败，请重试！");
                }, complete:function () {
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


function formatMzdb(mzdm){
    switch (mzdm) {
        case "01": return "汉族";
        case "02": return "蒙古族";
        case "03": return "回族";
        case "04": return "藏族";
        case "05": return "维吾尔族";
        case "06": return "苗族";
        case "07": return "彝族";
        case "08": return "壮族";
        case "09": return "布依族";
        case "10": return "朝鲜族";
        case "11": return "满族";
        case "12": return "侗族";
        case "13": return "瑶族";
        case "14": return "白族";
        case "15": return "土家族";
        case "16": return "哈尼族";
        case "17": return "哈萨克族";
        case "18": return "傣族";
        case "19": return "黎族";
        case "20": return "傈僳族";
        case "21": return "佤族";
        case "22": return "畲族";
        case "23": return "高山族";
        case "24": return "拉祜族";
        case "25": return "水族";
        case "26": return "东乡族";
        case "27": return "纳西族";
        case "28": return "景颇族";
        case "29": return "柯尔克孜族";
        case "30": return "土族";
        case "31": return "达斡尔族";
        case "32": return "仫佬族";
        case "33": return "羌族";
        case "34": return "布朗族";
        case "35": return "撒拉族";
        case "36": return "毛难族";
        case "37": return "仡佬族";
        case "38": return "锡伯族";
        case "39": return "阿昌族";
        case "40": return "普米族";
        case "41": return "塔吉克族";
        case "42": return "怒族";
        case "43": return "乌孜别克族";
        case "44": return "俄罗斯族";
        case "45": return "鄂温克族";
        case "46": return "崩龙族";
        case "47": return "保安族";
        case "48": return "裕固族";
        case "49": return "京族";
        case "50": return "塔塔尔族";
        case "51": return "独龙族";
        case "52": return "鄂伦春族";
        case "53": return "赫哲族";
        case "54": return "门巴族";
        case "55": return "珞巴族";
        case "56": return "基诺族";
        case "57": return "外国血统中国籍人士";
        case "97": return "其他";
        case "98": return "不详";
    }
}
