/**
 * Created by 前端组 on 2019/8/13.
 * 扫描档案js
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
layui.use(['tree','jquery','response'], function(){
    var tree = layui.tree,
        layer = layui.layer,
        response = layui.response,
        $ = layui.jquery;

    $(function () {
        //回到查询页面
        $('.bdc-major-btn').on('click',function () {
            window.close();
        });
        var $paramArr = getIpHz();
        var slbh = $paramArr['slbh'];
        var bdcqzh = $paramArr['bdcqzh'];
        var type = $paramArr['type'];
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
            url: getContextPath() + "/rest/v1.0/daxx/hefei/dajbxx",
            data: {slbh: slbh, bdcqzh: bdcqzh, type: type},
            success: function (data) {
                if (data) {
                    daid = data.IMGID;
                    $('.bdc-total-page').html(data.PAGES);
                    totalPage = data.PAGES;
                }
                if (isNullOrEmpty(daid)) {
                    removeModel();
                    layer.msg('没获取到档案信息!');
                    return;
                }
                //获取档案目录信息
                $.ajax({
                    type: "GET",
                    url: getContextPath() + "/rest/v1.0/daxx/hefei/damlxx",
                    data: {archid: daid, type: type},
                    success: function (data) {
                        if (data) {
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
                            getFjxx(daid,1);
                        }else{
                            removeModel();
                        }
                    }, error: function (e) {
                        removeModel();
                        response.fail(e,'');
                    }
                });
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
                url: getContextPath() + "/rest/v1.0/daxx/hefei/dafjxx",
                data: {archid: daid, currentpage: page, type: type},
                success: function (data) {
                    if (data) {
                        $('.bdc-current-page').html(page);
                        nowPage = page;
                        //换图片显示
                        if (viewer) {
                            viewer.destroy();
                        }
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