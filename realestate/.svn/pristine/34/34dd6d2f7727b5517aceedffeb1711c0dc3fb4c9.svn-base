/**
 * Created by YuanPengpeng on 2019/6/21.
 */
layui.use(['jquery', 'laytpl', 'carousel'], function () {
    var $ = layui.$,
        carousel = layui.carousel,
        laytpl = layui.laytpl;

    //轮播切换时间
    var qhsj =3000;


    $(function () {
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zhdp",
            dataType: "json",
            cache: false,
            async: false,
            success: function (data) {
                if(data){
                    if(data.qhsj){
                        qhsj =data.qhsj;
                    }
                    if(data.zhdpDataList) {
                        zhdpdata = data.zhdpDataList;
                    }
                }

            }
        });

        var getTpl = zhdpmb.innerHTML
            , view = document.getElementById('zhdp');
        laytpl(getTpl).render(zhdpdata, function (html) {
            view.innerHTML = html;
        });

        // 实时显示时间
        showTime();
        setInterval(showTime, 50000);
        // 排队人数轮播
        carousel.render({
            elem: '#contentCarousel'
            , width: '100%'
            , height: '100%'
            , anim: 'updown'
            , arrow: 'none'
            , indicator: 'none'
            //自动切换的时间间隔
            , interval: qhsj
        });

        var zhdp = $("#zhdp");
        $.each(zhdp.children().children('table'), function (i, zhdptable) {
            var rowcount = zhdptable.rows.length;
            var obj1 = null;
            var obj2 = null;
            // 合并行
            obj1 = zhdptable.rows[1].cells[0];
            for (var i = 2; i < rowcount; i++) {
                obj2 = zhdptable.rows[i].cells[0];
                if (obj1.innerText == obj2.innerText) {
                    obj1.rowSpan++;
                    obj2.parentNode.removeChild(obj2);
                } else {
                    obj1 = zhdptable.rows[i].cells[0];
                }
            }
        });

        setTimeout(function () {
            location.reload();
        }, 60000);
    });

});