layui.config({
    base: '../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
// 户室图图片数组
var hstList = [];
var base64 = "";
$('#fcfhthefei').click(function () {
    layui.use(["tree", "jquery", "response"], function () {
        var tree = layui.tree,
            layer = layui.layer,
            response = layui.response,
            $ = layui.jquery;
        $(function () {
            //回到查询页面
            $('.bdc-major-btn').on('click', function () {
                window.close();
            });
            var $paramArr = getIpHz();
            var slbh = $paramArr['slbh'];
            var bdcqzh = $paramArr['bdcqzh'];
            var type = $paramArr['type'];
            var bdcdyh = $paramArr['bdcdyh'];
            if(isNullOrEmpty(slbh) && isNullOrEmpty(bdcqzh)){
                layer.msg('查询参数缺失,需查看受理编号和证号是否有值!');
                return;
            }
            addModel();
            var daid;
            //单击上一页
            var nowPage = 1;
            var totalPage = 36;
            //图片缩放
            var image = new Image();
            var viewer;

            $.ajax({
                type: "GET",
                url: "/building-ui/djdcb/hefei/dajbxx",
                data: {slbh: slbh, bdcqzh: bdcqzh, type: type},
                success: function (data) {
                    if (data) {
                        daid = data.IMGID;
                        $('.bdc-total-page').html(data.PAGES);
                        totalPage = data.PAGES;
                    }
                    if (isNullOrEmpty(daid)) {
                        //去数据库获取
                        $.ajax({
                            type: "GET",
                            url: "/building-ui/djdcb/hefei/gethst",
                            data: {bdcdyh: bdcdyh},
                            success: function (data) {
                                if (data.length > 0) {
                                    totalPage = data.length;
                                    $('.bdc-total-page').html(totalPage);
                                    $('.bdc-current-page').html(nowPage);
                                    hstList = data;
                                    base64 = hstList[0];
                                    $("#prev1").removeClass("layui-hide");
                                    $("#next1").removeClass("layui-hide");
                                    $("#img").removeClass("layui-hide");
                                    $("#img").attr("src","data:image/jpeg;base64," + hstList[0]);
                                }else{
                                    removeModel();
                                    layer.msg('没获取到档案信息!');
                                    return;
                                }
                                removeModel();
                            }, error: function (e) {
                                removeModel();
                                response.fail(e,'');
                            }
                        });
                    } else{
                        //获取档案目录信息
                        $.ajax({
                            type: "GET",
                            url: "/building-ui/djdcb/hefei/damlxx",
                            data: {archid: daid, type: type},
                            success: function (data) {
                                if (data.length > 0) {
                                    data.forEach(function (v) {
                                        v.title = v.RNAME + "-----" + v.FROMPAGE;
                                    });
                                    var treeData = [{
                                        title: '扫描档案目录'
                                        , id: 1
                                        , checked: true
                                        , spread: true
                                        , children: data
                                    }];
                                    //渲染
                                    var inst1 = tree.render({
                                        elem: '#treeList'  //绑定元素
                                        ,data: treeData
                                        ,click: function(obj){
                                            getFjxx(daid,obj.data.FROMPAGE);
                                        }
                                    });
                                    getFjxx(daid, data[0].FROMPAGE);
                                    $("#prev").removeClass("layui-hide");
                                    $("#next").removeClass("layui-hide");
                                    $("#back").removeClass("layui-hide");
                                }else{
                                    removeModel();
                                }
                            }, error: function (e) {
                                removeModel();
                                response.fail(e,'');
                            }
                        });
                    }
                }, error: function (e) {
                    response.fail(e,'');
                    removeModel();
                }
            });


            $('.bdc-prev').on('click',function () {
                if(nowPage > 1){
                    nowPage--;
                    $('.bdc-current-page').html(nowPage);
                    getFjxx(daid,nowPage);
                }
            });
            //单击下一页
            $('.bdc-next').on('click',function () {
                if(nowPage < totalPage){
                    nowPage++;
                    $('.bdc-current-page').html(nowPage);
                    getFjxx(daid,nowPage);
                }
            });

            $('.bdc-prev1').on('click',function () {
                if(nowPage > 1){
                    nowPage--;
                    $('.bdc-current-page').html(nowPage);
                    $("#img").attr("src","data:image/jpeg;base64," + hstList[nowPage-1]);
                    base64 = hstList[nowPage-1];
                }
            });
            //单击下一页
            $('.bdc-next1').on('click',function () {
                if(nowPage < totalPage){
                    nowPage++;
                    $('.bdc-current-page').html(nowPage);
                    $("#img").attr("src","data:image/jpeg;base64," + hstList[nowPage-1]);
                    base64 = hstList[nowPage-1];
                }
            });


            //监听按键事件
            $(document).keydown(function(event){
                switch (event.keyCode){
                    case 37:
                        //左箭头，上一页
                        $('.bdc-prev').click();
                        break;
                    case 39:
                        //右箭头，下一页
                        $('.bdc-next').click();
                        break;
                }
            });

            //渲染指定图片
            function renderImg(src) {
                image.src = src;
                viewer = new Viewer(image, {
                    hidden: function () {
                        viewer.destroy();
                    }
                });
                viewer.show();
            }

            function getFjxx(daid,page) {
                $.ajax({
                    type: "GET",
                    url: "/building-ui/djdcb/hefei/fcfht",
                    data: {archid: daid, currentpage: page, type: type},
                    success: function (data) {
                        if (data) {
                            $('.bdc-current-page').html(page);
                            nowPage = page;
                            //换图片显示
                            if (viewer) {
                                viewer.destroy();
                            }
                            base64 = data.data;
                            renderImg("data:image/jpeg;base64," + data.data);
                        }
                        removeModel();
                    }, error: function (e) {
                        removeModel();
                        response.fail(e,'');
                    }
                });
            }

            //打印图片
            $('#print').click(function(){
                printDaxxImage(daid,nowPage);
            });

            $('#download').click(function (){
                download(nowPage)
            })

            function printDaxxImage(daid,page) {
                var user=queryCurrentUser();
                if(user==undefined || user==null){
                    warnMsg("用户信息不能为空！")
                    return false;
                }
                var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/hefei/image/xml/"+user.username+"/"+daid+"/"+page;
                print(daxxImageModelUrl, dataUrl, false);
            }
        });


    });
});


function download(nowPage){
    var obj = {base64: base64, bdcdyh: bdcdyh, nowPage: nowPage};
    $.ajax({
        type: "POST",
        data : JSON.stringify(obj),
        contentType:"application/json;charset=UTF-8",
        url: "/building-ui/djdcb/fcfht/download",
        success: function (data) {
            removeModel();
        }, error: function (e) {
            removeModel();
        }
    });
}
