/**
 * @description 证书预览页面js
 *
 */

layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer', 'carousel'], function () {
    // 轮播组件
    var carousel = layui.carousel;
    //建造组件实例
    carousel.render({
        elem: '#zsView'
        , width: '1349px' //设置容器宽度
        , height: '890px'
        , arrow: 'always' //始终显示箭头
        , autoplay: false
    });

    var form = layui.form;
    var $ = layui.jquery;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    // 查询参数
    var processInsId = $.getUrlParam("processInsId");
    var xmid = "";
    var pageData = {};
    var zdList = [];
    var add = false;
    $(function () {

        // loading加载
        var index = layer.load(2);

        var screenH = document.documentElement.clientHeight;
        $(".content-main").css({'min-height': screenH - 70});
        // 加载字典
        loadZdList();


        //获取xmid
        $.ajax({
            url: '/realestate-natural-ui/rest/v1.0/ywxx/' + processInsId,
            type: 'GET',
            dataType: 'json',
            async: false,
            success: function (data) {
                console.log('data:');
                console.log(data);
                if (data) {
                    pageData = data;
                    xmid = data.zrzyXm.xmid;
                    $("#djjg").html(data.zrzyXm.djjg);
                    $("#djjg_foot").html(data.zrzyXm.djjg);
                    $(".djdymc").html(data.zrzyDjdy.djdymc);
                    // 所有权人代码转为名称
                    var syqzt = data.zrzyDjdy.syqzt;
                    zdList.syqztlx.forEach(function(item, index){
                        if (item.DM == syqzt) {
                            data.zrzyDjdy.syqzt = item.MC;
                        }
                    });

                    data.zrzyDjdy.sz = "东至：" +  data.zrzyDjdy.dyszd
                        + "；</br>南至：" +  data.zrzyDjdy.dyszn
                        + "；</br>西至：" +  data.zrzyDjdy.dyszx+
                        "；</br>北至：" +  data.zrzyDjdy.dyszb + "。";
                    data.zrzyDjdy.zl = data.zrzyDjdy.zl;
                    //
                    var szy = {"zmj":0};
                    var sd = {"zmj":0};
                    var sl = {"zmj":0};
                    var cy = {"zmj":0};
                    var zmj = 0;
                    for (var i = 0; i < data.zrzyZrzkList.length; i++) {
                        zmj = zmj + data.zrzyZrzkList[i].gymj +
                            data.zrzyZrzkList[i].jtmj +
                            data.zrzyZrzkList[i].zyqmj;
                        if (data.zrzyZrzkList[i].zrzklx == 'SZY') {
                            szy = data.zrzyZrzkList[i];
                            szy.zmj = data.zrzyZrzkList[i].gymj +
                                data.zrzyZrzkList[i].jtmj +
                                data.zrzyZrzkList[i].zyqmj;
                        }
                        if (data.zrzyZrzkList[i].zrzklx == 'SD') {
                            sd = data.zrzyZrzkList[i];
                            sd.zmj = data.zrzyZrzkList[i].gymj +
                                data.zrzyZrzkList[i].jtmj +
                                data.zrzyZrzkList[i].zyqmj;
                        }
                        if (data.zrzyZrzkList[i].zrzklx == 'SL') {
                            sl = data.zrzyZrzkList[i];
                            sl.zmj = data.zrzyZrzkList[i].gymj +
                                data.zrzyZrzkList[i].jtmj +
                                data.zrzyZrzkList[i].zyqmj;
                        }
                        if (data.zrzyZrzkList[i].zrzklx == 'CY') {
                            cy = data.zrzyZrzkList[i];
                            cy.zmj = data.zrzyZrzkList[i].gymj +
                                data.zrzyZrzkList[i].jtmj +
                                data.zrzyZrzkList[i].zyqmj;
                        }
                    }
                    var qtmj = zmj - szy.zmj - sd.zmj - sl.zmj - cy.zmj;
                    data.zrzyDjdy.qtmj = qtmj.toFixed(2) + "公顷";
                    var zmjcontent = "";

                    if(isNullOrEmpty(sd)){
                        zmjcontent =   zmjcontent + "湿地资源：0 公顷，\n"
                    }else {
                        zmjcontent =   zmjcontent + "湿地资源："+ sd.zmj.toFixed(2) + "公顷，</br>"
                    }

                    if(isNullOrEmpty(sl)){
                        zmjcontent =   zmjcontent + "森林资源：0 公顷，\n"
                    }else {
                        zmjcontent =   zmjcontent + "森林资源："+ sl.zmj.toFixed(2) + "公顷，</br>"
                    }

                    if(isNullOrEmpty(cy)){
                        zmjcontent =   zmjcontent + "草原资源：0 公顷。\n"
                    }else {
                        zmjcontent =   zmjcontent + "草原资源："+ cy.zmj.toFixed(2) + "公顷。</br>"
                    }

                    if(isNullOrEmpty(szy)){
                        zmjcontent =   zmjcontent + "水流资源：0 公顷，\n"
                    }else {
                        zmjcontent =   zmjcontent + "水流资源："+ szy.zmj.toFixed(2) + "公顷，</br>"
                    }
                    data.zrzyDjdy.zmjcontent = zmjcontent;
                    generateTable(data);

                    var ftewm = "/realestate-natural-ui/rest/v1.0/ywxx/ewm?ewmnr=http%3A%2F%2Fzrzyqq.szbdcdjwx.com%3A8091%2Festateplat-wechat-page%2Fview%2Fhtml%2FshowPic.html%3Furl%3Dhttp%3A%2F%2Fzrzyqq.szbdcdjwx.com%3A8091%2Festateplat-olcommon%2Fapi%2Fv2%2FapplyModel%2FdownFile%3Ffjid%3D";
                    ftewm += data.zrzyXm.zrzydjdyh;
                    $("#ftewm").attr("src",ftewm)
                }
            },
            error: function (xhr, status, error) {
            }
        });

        //获取模板信息
        $.ajax({
            url: "/realestate-natural-ui/rest/v1.0/ywxx/ggxx/mb/" + processInsId,
            contentType: "application/json;charset=utf-8",
            type: "GET",
            dataType: "json",
            async: false,
            success: function (data) {
                if (!isNullOrEmpty(data)) {
                    // 为基本信息赋值
                    add = false;
                    form.val('info', data);
                }else {
                    add = true;
                }
                // 关闭加载
                layer.close(index);
            }
        });

        //
        //资源数据
        $.ajax({
            url: "/realestate-natural-ui/rest/v1.0/ywxx/ggxx/" + processInsId,
            contentType: "application/json;charset=utf-8",
            type: "GET",
            dataType: "json",
            async: false,
            success: function (data) {
                if (data && data.length) {
                    for (let i = 0; i < data.length; i++) {
                        // 所有权人代码转为名称
                        var syqzt = data[i].syqzt;
                        zdList.syqztlx.forEach(function (item, index) {
                            if (item.DM == syqzt) {
                                data[i].syqzt = item.MC;
                            }
                        });

                        // 权利行使方式代码转为名称
                        var qlxsfs = data[i].qlxsfs;
                        zdList.qlxsfs.forEach(function (item, index) {
                            if (item.DM == qlxsfs) {
                                data[i].qlxsfs = item.MC;
                            }
                        });

                        // 登记单元类型代码转为名称
                        var djdylx = data.djdylx;
                        zdList.djlx.forEach(function (item, index) {
                            if (item.DM == djdylx) {
                                data[i].djdylx = item.MC;
                            }
                        });
                    }
                    //generateTable(data);
                }
                // 关闭加载
                layer.close(index);
            }
        });



        //获取电话配置
        function getdh() {
            $.ajax({
                url: '/realestate-natural-ui/rest/v1.0/ywxx/djggdh',
                type: 'GET',
                async: false,
                success: function (data) {
                    if (!isNullOrEmpty(data)) {
                        console.log(data);
                        $("#dh").val(data)
                    }
                },
                error: function (xhr, status, error) {
                }
            });
        }
        getdh();


        $.ajax({
            url: '/realestate-natural-ui/rest/v1.0/ywxx/djggdz',
            type: 'GET',
            async: false,
            success: function (data) {
                if (!isNullOrEmpty(data)) {
                    console.log(data);
                    $("#dz").val(data)
                }
            },
            error: function (xhr, status, error) {
            }
        });
    });

    // $("input").blur(function () {
    //     if (xmid != "") {
    //         savembxx();
    //     }
    // });

    $("#save").click(function (e) {
        savembxx();
    });

    function savembxx() {
        var data = form.val('info');
        var url = "";
        if (add) {
            url = "/realestate-natural-ui/rest/v1.0/ywxx/ggxx/mb/add/" + xmid + "/" + JSON.stringify(data);
        } else {
            url = "/realestate-natural-ui/rest/v1.0/ywxx/ggxx/mb/update/" + xmid + "/" + JSON.stringify(data);
        }
        $.ajax({
            url: url,
            contentType: "application/json;charset=utf-8",
            type: "GET",
            dataType: "json",
            success: function (data) {
                add = false;
                layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">' + "保存成功");
            },
            error: function () {
                layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + "保存失败！");
            }
        });
    }

    function generateTable(data) {
        var getTpl = tableTpl.innerHTML;
        if(data.zrzyDjdy.djdyzmj){
            data.zrzyDjdy.djdyzmj = data.zrzyDjdy.djdyzmj.toFixed(2);
        }
        for (var i = 0; i < data.length; i++) {
            if(!isNullOrEmpty(data[i].mj)){
                data[i].mj = data[i].mj.toFixed(2);
            }
        }
        laytpl(getTpl).render(data, function (html) {
            $('tbody').html(html);
        });
    }

    function loadZdList() {
        $.ajax({
            url: '/realestate-natural-ui/rest/v1.0/ywxx/zd',
            type: 'GET',
            dataType: 'json',
            async: false,
            success: function (data) {
                console.log('zdList:');
                console.log(data);
                if (data) {
                    zdList = data;
                }
            },
            error: function (xhr, status, error) {
            }
        });
    }


    //下载
    $("#download").click(function (){
        $(".content-div").find("input").each(function () {
            var id = $(this).attr("id");
            var val = $("#"+id).val();
            $(this).replaceWith(val);
        });
        $(".title-btn-area").remove();
        var html = $(".content-div").html();
        // var config = $.extend(true, { method: "post" });
        var $iframe = $('<iframe id="down-file-iframe" />');
        var $form = $(
            '<form target="down-file-iframe" method="post" />'
        );
        $form.attr("action", "/realestate-natural-ui/rest/v1.0/ywxx/ggxx/mb/download");
        // var nr = $.makeArray($(".content-div").html());
        $form.append('<input type="hidden" name="nr" value="' + encodeURIComponent(html) + '" />');
        $iframe.append($form);
        $(document.body).append($iframe);
        $form[0].submit();
        $iframe.remove();
        //location.reload();
        //form.attr('action', "/realestate-natural-ui/rest/v1.0/ywxx/ggxx/mb/download");

        // $.ajax({
        //     url: "/realestate-natural-ui/rest/v1.0/ywxx/ggxx/mb/download",
        //     contentType: "application/json;charset=utf-8",
        //     type: "POST",
        //     dataType: "json",
        //     data:JSON.stringify({
        //         "nr":data
        //     }),
        //     success: function (data) {
        //         add = false;
        //     }
        // });
    });
});