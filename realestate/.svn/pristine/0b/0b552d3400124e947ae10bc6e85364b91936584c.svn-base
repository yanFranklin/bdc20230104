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
    var zdzhh = $.getUrlParam("zdzhh");


    // loading加载
    addModel();


    // var screenH = document.documentElement.clientHeight;
    // $(".content-main").css({'min-height': screenH - 70})


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
        url: "/realestate-register-ui/rest/v1.0/djbxx/" + zdzhh + "/bdcdjbZdjbxx",
        contentType: "application/json;charset=utf-8",
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data) {
                // 时间格式转换（IE不支持）
                // data.djsj = util.toDateString(data.djsj, 'yyyy年MM月dd日');

              //土地承包所有面积换算成亩展示
              //   if(data.mjdw ===1 &&data.qllx ===9){
              //       data.mjdw =2;
              //       data.zdmj=changeMjDw(data.zdmj);
              //   }
                // 设置面积单位
                if (data && data.mjdw) {
                    $.each($("[name='mjdw']"), function (index, item) {
                        item.checked = false;
                        if (item.value == data.mjdw) {
                            item.checked = true;
                        }

                    });
                }
                // 设置不动产类型
                if (data && data.bdclx) {
                    $.each($("[name='bdclx']"), function (index, item) {
                        item.checked = false;
                        if (item.value == data.bdclx) {
                            item.checked = true;
                        }
                    });
                }
                if (data.djsj) {
                    data.djsj = Format(data.djsj, 'yyyy年MM月dd日');
                }
                // 字典转换
                convertEntityToMc(data);

                // 为基本信息赋值
                delete data.bdclx;
                delete data.mjdw;

                form.val('form', data);

                //为宗地变化情况赋值
                if (data.bdcBdcdjbZdjbxxZdbhqkDOList) {
                    $.each(data.bdcBdcdjbZdjbxxZdbhqkDOList, function (index, item) {
                        // 转换时间格式
                        item.djsj = Format(item.djsj, 'yyyy年MM月dd日');
                    });
                }
                if (data.bdcBdcdjbZdjbxxZdbhqkDOList) {
                    var getTpl = zdbhqk.innerHTML;
                    laytpl(getTpl).render(data.bdcBdcdjbZdjbxxZdbhqkDOList, function (html) {
                        $("#mainBody").after(html)
                    });
                }
                //金额单位赋值
                $("#jedw").text("、"+commonJedw);

                //重新渲染
                form.render();
            }
        }, complete: function (XMLHttpRequest, textStatus) {
            // 关闭loading
            removeModel();
        }
    });

    function convertEntityToMc(data) {
        // 权利类型
        if (data.qllx) {
            for (var i = 0; i < zdMap.qllx.length; i++) {
                if (zdMap.qllx[i].DM == data.qllx) {
                    data.qllx = zdMap.qllx[i].MC;
                    break;
                }
            }
        }
        // 权利性质
        if (data.qlxz) {
            for (var i = 0; i < zdMap.qlxz.length; i++) {
                if (zdMap.qlxz[i].DM == data.qlxz) {
                    data.qlxz = zdMap.qlxz[i].MC;
                    break;
                }
            }
        }
        // 土地用途
        if (data.yt) {
            var ytArr = data.yt.split(",");
            var ytMc = []
            for(var j = 0; j < ytArr.length; j++){
                for (var i = 0; i < zdMap.tdyt.length; i++) {
                    if (zdMap.tdyt[i].DM == ytArr[j]) {
                        if(zdMap.tdyt[i].MC){
                            ytMc.push(zdMap.tdyt[i].MC);
                        }
                        break;
                    }
                }
            }
            data.yt = ytMc.join("/");
        }
        // 土地等级
        if (data.dj) {
            for (var i = 0; i < zdMap.dj.length; i++) {
                if (zdMap.dj[i].DM == data.dj) {
                    data.dj = zdMap.dj[i].MC;
                    break;
                }
            }
        }
        //  权利设定方式
        if (data.qlsdfs) {
            for (var i = 0; i < zdMap.qlsdfs.length; i++) {
                if (zdMap.qlsdfs[i].DM == data.qlsdfs) {
                    data.qlsdfs = zdMap.qlsdfs[i].MC;
                    break;
                }
            }
        }
    }
})