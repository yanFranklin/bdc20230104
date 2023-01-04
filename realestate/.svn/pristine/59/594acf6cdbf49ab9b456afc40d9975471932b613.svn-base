/**
 卫健委-死亡医学证明
 不用自动查询，输入条件手动查询
 */
// 查询参数
var gzlslid = $.getUrlParam("gzlslid");
var qlrlx = '1';
var searchData = {};
var key = "";
var jkname = $.getUrlParam("jkname");
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var laytpl = layui.laytpl;
    form.render();

    $(function () {
        $('#search').on('click', function () {
            // 获取查询条件
            var sfzh = $(this).parents('.layui-form-item').find('#sfzh').val();
            var xm = $(this).parents('.layui-form-item').find('#xm').val();
            // 判空
            if (!isNotBlank(sfzh) || (!isNotBlank(xm))) {
                warnMsg('请输入必要查询条件');
                return false;
            }
            // 组织查询条件
            searchData = {
                "cxywcs": []
            };
            searchData.cxywcs.push({
                "sfzh": sfzh,
                "xm": xm
            });

            // 展示查询条件，加载表格内容
            generateTable(searchData, false);

            // 查询条件不为空进行查询
            if (searchData && searchData.cxywcs && searchData.cxywcs.length > 0) {
                search();
            }
        });

        //查询
        function search() {
            var resultData = [];
            addModel();
            if (searchData.cxywcs && searchData.cxywcs.length > 0) {
                for (var i = 0; i < searchData.cxywcs.length; i++) {
                    // 获取查询参数
                    var param = {
                        sfzh: searchData.cxywcs[i].sfzh,
                        xm: searchData.cxywcs[i].xm,
                    };
                    // 重新请求
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/gx/shijgxCx/wjwsmyxzm",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async: false,
                        success: function (data) {
                            if (data.code == 200) {
                                if(!isNullOrEmpty(data.message)){
                                    successMsg(data.message);
                                }
                                if (data.data && data.data.length > 0) {
                                    for (var i = 0; i < data.data.length; i++) {
                                        data.data[i].sfzh = param.sfzh;
                                        data.data[i].xm = param.xm;
                                        data.data[i].mzdm = formatMzdb(data.data[i].mzdm);
                                        data.data[i].xbdm = formatMb(data.data[i].xbdm);
                                        resultData.push(data.data[i]);
                                    }
                                }
                                dealCxjgxx("success", jkname);
                            } else {
                                if(!isNullOrEmpty(data.message)){
                                    warnMsg(data.message);
                                }else {
                                    warnMsg("查询错误");
                                }
                            }

                            removeModal();
                        },
                        error: function (xhr, status, error) {
                            dealCxjgxx("fail", jkname);
                            removeModal();
                            delAjaxErrorMsg(xhr)
                        }
                    });
                }

                if (resultData.length > 0) {
                    //处理缓存数据
                    key = saveJkDataToRedis(resultData);
                    generateTable(resultData, true);
                }
            }
        }

        function generateTable(data, isSearch) {
            if (isSearch) {
                var getTpl = tableTpl.innerHTML;
            } else {
                var getTpl = cxywcsTpl.innerHTML;
            }

            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
            $(document).find('input').each(function () {
                $(this).attr('title', $(this).val());
            });
            form.render();
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid, "wjwsmyxzm", "卫健委-死亡医学证明", key, "市级共享交换平台");
        });

        function formatMzdb(mzdm) {
            switch (mzdm) {
                case "01":
                    return "汉族";
                case "02":
                    return "蒙古族";
                case "03":
                    return "回族";
                case "04":
                    return "藏族";
                case "05":
                    return "维吾尔族";
                case "06":
                    return "苗族";
                case "07":
                    return "彝族";
                case "08":
                    return "壮族";
                case "09":
                    return "布依族";
                case "10":
                    return "朝鲜族";
                case "11":
                    return "满族";
                case "12":
                    return "侗族";
                case "13":
                    return "瑶族";
                case "14":
                    return "白族";
                case "15":
                    return "土家族";
                case "16":
                    return "哈尼族";
                case "17":
                    return "哈萨克族";
                case "18":
                    return "傣族";
                case "19":
                    return "黎族";
                case "20":
                    return "傈僳族";
                case "21":
                    return "佤族";
                case "22":
                    return "畲族";
                case "23":
                    return "高山族";
                case "24":
                    return "拉祜族";
                case "25":
                    return "水族";
                case "26":
                    return "东乡族";
                case "27":
                    return "纳西族";
                case "28":
                    return "景颇族";
                case "29":
                    return "柯尔克孜族";
                case "30":
                    return "土族";
                case "31":
                    return "达斡尔族";
                case "32":
                    return "仫佬族";
                case "33":
                    return "羌族";
                case "34":
                    return "布朗族";
                case "35":
                    return "撒拉族";
                case "36":
                    return "毛难族";
                case "37":
                    return "仡佬族";
                case "38":
                    return "锡伯族";
                case "39":
                    return "阿昌族";
                case "40":
                    return "普米族";
                case "41":
                    return "塔吉克族";
                case "42":
                    return "怒族";
                case "43":
                    return "乌孜别克族";
                case "44":
                    return "俄罗斯族";
                case "45":
                    return "鄂温克族";
                case "46":
                    return "崩龙族";
                case "47":
                    return "保安族";
                case "48":
                    return "裕固族";
                case "49":
                    return "京族";
                case "50":
                    return "塔塔尔族";
                case "51":
                    return "独龙族";
                case "52":
                    return "鄂伦春族";
                case "53":
                    return "赫哲族";
                case "54":
                    return "门巴族";
                case "55":
                    return "珞巴族";
                case "56":
                    return "基诺族";
                case "57":
                    return "外国血统中国籍人士";
                case "97":
                    return "其他";
                case "98":
                    return "不详";
            }
        };

        function formatMb(xbdm) {
            switch (xbdm) {
                case "1":
                    return "男性";
                case "2":
                    return "女性";
            }
        };
    });


});



