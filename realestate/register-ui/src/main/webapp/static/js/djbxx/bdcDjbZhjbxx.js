/**
 * 登记簿宗地基本信息JS代码
 */
layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer'], function () {
    var element = layui.element;
    var form = layui.form;
    var $ = layui.jquery;
    var laytpl = layui.laytpl;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var util = layui.util;
    // 查询参数
    var bdcdyh = $.getUrlParam("bdcdyh");


    // loading加载
    addModel();


    var screenH = document.documentElement.clientHeight;
    $(".content-main").css({'min-height': screenH - 70})


    // 字典
    var zdMap;
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/qlxx/zd',
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            if (data) {
                zdMap = data;
            }
        }
    });
    //获取数据
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/djbxx/" + bdcdyh + "/bdcdjbZhjbxx",
        contentType: "application/json;charset=utf-8",
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data) {
                // 设置不动产类型
                if (data && data.bdclx) {
                    $.each($("[name='bdclx']"), function (index, item) {
                        item.checked = false;
                        if (item.value == data.bdclx) {
                            item.checked = true;
                        }
                    });
                }


                // 字典转换
                convertEntityToMc(data);

                // 时间格式转换（IE不支持）
                if (data.djsj) {
                    data.djsj = Format(data.djsj, 'yyyy年MM月dd日');
                }

                // 为基本信息赋值
                delete data.bdclx;
                form.val('form', data);

                //为宗海变化情况赋值
                if (data.bdcBdcdjbZhjbxxZhbhqkDOList) {
                    $.each(data.bdcBdcdjbZhjbxxZhbhqkDOList, function (index, item) {
                        // 格式化时间
                        item.djsj = Format(item.djsj, 'yyyy年MM月dd日');
                    });
                    var zhbhqkTpl = zhbhqk.innerHTML;
                    laytpl(zhbhqkTpl).render(data.bdcBdcdjbZhjbxxZhbhqkDOList, function (html) {
                        $("#dbxx").after(html)
                    });
                }

                // 为用海状况赋值
                if (data.bdcBdcdjbZhjbxxYhzkDOList) {
                    var yhzkTpl = yhzk.innerHTML;
                    laytpl(yhzkTpl).render(data.bdcBdcdjbZhjbxxYhzkDOList, function (html) {
                        $("#wjmhdqk").before(html)
                    });
                }

                // 为用海用岛坐标赋值
                if (data.bdcBdcdjbZhjbxxYhydzbDOList) {
                    var listLength = data.bdcBdcdjbZhjbxxYhydzbDOList.length;
                    var remainder;
                    if (listLength % 2 > 0) {
                        remainder = 1;
                    } else {
                        remainder = 0;
                    }
                    //循环总次数
                    var count = parseInt(listLength / 2) + remainder;
                    var zbData = {};
                    zbData["count"] = count;
                    zbData["zblist"] = data.bdcBdcdjbZhjbxxYhydzbDOList;
                    var yhydzbTpl = yhydzb.innerHTML;
                    laytpl(yhydzbTpl).render(zbData, function (html) {
                        $("#dbxx").before(html)
                    });

                    //金额单位赋值
                    $("#dw").text("单位：公顷、"+commonJedw);
                }

                //重新渲染
                form.render();
            }
        }, complete: function (XMLHttpRequest, textStatus) {
            // 关闭loading
            removeModel();
        }
    });

    function convertEntityToMc(data) {
        // 用海方式转换
        for (var j = 0; j < data.bdcBdcdjbZhjbxxYhzkDOList.length; j++) {
            if (data.bdcBdcdjbZhjbxxYhzkDOList[j].yhfs && zdMap.yhfs) {
                for (var i = 0; i < zdMap.yhfs.length; i++) {
                    if (zdMap.yhfs[i].DM == data.bdcBdcdjbZhjbxxYhzkDOList[j].yhfs) {
                        data.bdcBdcdjbZhjbxxYhzkDOList[j].yhfs = zdMap.yhfs[i].MC;
                        break;
                    }
                }
            }
        }
        // 海岛用途
        if (data.hdyt && zdMap.wjmhdyt) {
            for (var i = 0; i < zdMap.wjmhdyt.length; i++) {
                if (zdMap.wjmhdyt[i].DM == data.hdyt) {
                    data.hdyt = zdMap.wjmhdyt[i].MC;
                    break;
                }
            }
        }
        // 用海类型B
        if (data.yhlxb && zdMap.yhlxb) {
            for (var i = 0; i < zdMap.hysylxb.length; i++) {
                if (zdMap.hysylxb[i].DM == data.yhlxb) {
                    data.yhlxb = zdMap.hysylxb[i].MC;
                    break;
                }
            }
        }
        // 用海类型A
        if (data.yhlxa && zdMap.yhlxa) {
            for (var i = 0; i < zdMap.hysylxa.length; i++) {
                if (zdMap.hysylxa[i].DM == data.yhlxa) {
                    data.yhlxa = zdMap.hysylxa[i].MC;
                    break;
                }
            }
        }
        // 海域等别
        if (data.db && zdMap.hydb) {
            for (var i = 0; i < zdMap.hydb.length; i++) {
                if (zdMap.hydb[i].DM == data.db) {
                    data.db = zdMap.hydb[i].MC;
                    break;
                }
            }
        }
        //项目性质
        if (data.xmxz && zdMap.xmxz) {
            for (var i = 0; i < zdMap.xmxz.length; i++) {
                if (zdMap.xmxz[i].DM == data.xmxz) {
                    data.xmxz = zdMap.xmxz[i].MC;
                    break;
                }
            }
        }
    }
})