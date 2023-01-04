/**
 * Created by 前端组 on 2019/8/13.
 * 扫描档案js
 */
layui.use(['tree','jquery'], function(){
    var tree = layui.tree,
        $ = layui.jquery;

    $(function () {
        //渲染
        var treeData = [{
            title: '扫描档案目录'
            ,id: 1
            ,checked: true
            ,spread: true
            ,children: [{
                title: '房屋所有权权属登记申请（审核）书---1'
                ,id: 3
                ,href: 'https://www.layui.com/'
            },{
                title: '委托书、公证书、具结书-------6'
                ,id: 4
            },{
                title: '商品房买卖合同------------7'
                ,id: 20
            },{
                title: '商品房网上备案确认单------------27'
                ,id: 20
            },{
                title: '测绘报告------------28'
                ,id: 20
            },{
                title: '身份证件类------------29'
                ,id: 20
            },{
                title: '各类收费发票------------31'
                ,id: 20
            },{
                title: '其他有关材料------------34'
                ,id: 20
            },{
                title: '房屋分户图------------36'
                ,id: 20
            }]
        }];
        var inst1 = tree.render({
            elem: '#treeList'  //绑定元素
            ,data: treeData
            ,click: function(obj){
                console.log(obj.data); //得到当前点击的节点数据
                console.log(obj.state); //得到当前节点的展开状态：open、close、normal
                console.log(obj.elem); //得到当前节点元素

                console.log(obj.data.children); //当前节点下是否有子节点
            }
        });

        //单击上一页
        var nowPage = 1;
        var totalPage = 36;
        $('.bdc-prev').on('click',function () {
            if(nowPage > 1){
                nowPage--;
                $('.bdc-current-page').html(nowPage);

                //换图片显示
                viewer.destroy();
                var src = '/lib/bdcui/images/code.png';
                renderImg(src);
            }
        });
        //单击下一页
        $('.bdc-next').on('click',function () {
            if(nowPage < totalPage){
                nowPage++;
                $('.bdc-current-page').html(nowPage);

                //换图片显示
                viewer.destroy();
                var src = '/lib/bdcui/images/test/01.jpg';
                renderImg(src);
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

        //默认展示第一张图片
        var image = new Image();
        image.src = '/lib/bdcui/images/test/01.jpg';
        var viewer = new Viewer(image, {
            hidden: function () {
                // viewer.destroy();
            }
        });
        viewer.show();

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

    });
});