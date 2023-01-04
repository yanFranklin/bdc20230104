/**
 * Created by Administrator on 2019/2/28.
 */
/**
 * Created by Administrator on 2019/1/21.
 */
layui.use(['jquery','table','element','form','layer'], function() {
    var $ = layui.jquery,
        table = layui.table,
        element = layui.element,
        layer = layui.layer;

    $(function () {
        //加入购物车时数字变化，加上这个动画，
        $('.bdc-car span').addClass('animateShow');
        setTimeout(function () {
            console.log('aaa');
            $('.bdc-car span').removeClass('animateShow');
        },5000);

        //鼠标移出购物车列表
        $('.bdc-gwc').on('mouseleave',function () {
            // $(this).animate({height:0},300);
            $(this).slideUp(300);
        });
        //购物车圈圈 操作事件
        // 第一次鼠标覆盖圈圈，重置表格尺寸
        var enterIndex=  0;
        $('.bdc-car').on('click',function (event) {
            event.stopPropagation();
            // $('.bdc-gwc').animate({height:0},300);
            $('.bdc-gwc').slideUp(300);
            layer.open({
                type: 2,
                content: 'https://www.iconfont.cn/search/index?q=car'
            });
        }).on('mouseenter',function () {
            // $('.bdc-gwc').animate({height:"495px"},300);
            enterIndex++;
            $('.bdc-gwc').slideDown(300);
            if(enterIndex == 1){
                table.resize('gwcTableId');
            }
        });

        var gwcData = [
            {id: 0, dyh: '3256596984444fdgfd',zl: '江苏省雨花台区'},
            {id: 1, dyh: '3256596984444gfdgdf',zl: '江苏省雨花台区'},
            {id: 2, dyh: '3256596984444dggfdg',zl: '江苏省雨花台区'},
            {id: 3, dyh: '3256596984444dgdggg',zl: '江苏省雨花台区'},
            {id: 4, dyh: '3256596984444dgdggg',zl: '江苏省雨花台区'},
            {id: 5, dyh: '3256596984444dgdggg',zl: '江苏省雨花台区'},
            {id: 6, dyh: '3256596984444dgdggg',zl: '江苏省雨花台区'},
            {id: 7, dyh: '3256596984444dgdggg',zl: '江苏省雨花台区'},
            {id: 8, dyh: '3256596984444dgdggg',zl: '江苏省雨花台区'},
            {id: 9, dyh: '3256596984444dgdggg',zl: '江苏省雨花台区'},
            {id: 10, dyh: '3256596984444dgdggg',zl: '江苏省雨花台区'},
            {id: 11, dyh: '3256596984444dgdggg',zl: '江苏省雨花台区'},
            {id: 12, dyh: '3256596984444dgdggg',zl: '江苏省雨花台区'}
        ];
        renderTable(gwcData);
        function renderTable(gwcData) {
            table.render({
                elem: '#gwcTable',
                page: false,
                id: 'gwcTableId',
                height: 380,
                limit: 30,
                cols: [[ //表头
                    {field: 'dyh', title: '不动产单元号',width: 200}
                    ,{field: 'zl', title: '坐落'},
                    {title: '', toolbar: '#stateTpl',width: 60}
                ]],
                data: gwcData
            });
        }
        //单击购物车表格中的去购物车
        $('.bdc-go-gwc').on('click',function (event) {
            event.stopPropagation();
            // $('.bdc-gwc').animate({height:0},300);
            $('.bdc-gwc').slideUp(300);
            layer.open({
                type: 2,
                content: 'https://www.iconfont.cn/search/index?q=car'
            });
        });
        //单击 购物车表格中 的 ×
        table.on('tool(gwcFilter)', function(obj){
            var getId = obj.data.id;
            if(obj.event === 'delete-msg'){
                gwcData.forEach(function (v,i) {
                    if(v.id == getId){
                        gwcData.splice(i, 1);
                        renderTable(gwcData);
                        return;
                    }
                });
            }
        });
    });
});