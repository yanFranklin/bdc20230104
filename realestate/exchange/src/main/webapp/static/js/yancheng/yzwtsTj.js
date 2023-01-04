layui.use(['jquery', 'layer', 'colorpicker', 'laydate'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        colorpicker = layui.colorpicker,
        laydate = layui.laydate;

    $(function () {
        //初始化日期控件
        laydate.render({
            elem: '#tskssj'
            , type: 'date'
        });
        laydate.render({
            elem: '#tsjssj'
            , type: 'date'
        });
        search();

        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            search();
        });
        // 回车查询
        $('.required').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        //查询
        function search() {
            //获取查询参数
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });

            $.ajax({
                url: "/realestate-exchange/yzw/tj",
                type: "GET",
                data: obj,
                success: function (data) {
                    if(data && data.length > 0){
                        var colorList = [];
                        var pzList=[];
                        data.forEach(function (v,i) {
                            var listName='';
                            if(v.TSZT=='0'){
                                colorList.push('#e75260');
                                listName ='失败';
                            } else if(v.TSZT=='1'){
                                colorList.push('#419192');
                                listName ='成功';
                            } else if(v.TSZT=='2'){
                                colorList.push('#f7bc25');
                                listName ='未推送';
                            }
                            pzList.push({"name":listName,"value":v.COUNT});
                        });
                        renderQqsl(pzList,colorList);
                    }else {
                        layer.msg('<img src="../static/lib/bdcui/images/info-small.png" alt="">无统计结果数据');
                    }

                },
                error: function (e) {
                    delAjaxErrorMsg(err);
                }
            });
        }

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

        //饼图
        function renderQqsl(legend,colorList){
            var qqslChart = echarts.init(document.getElementById('qqslYh'));
            var qqslOption = {
                legend: {
                    orient: 'horizontal',
                    left: 10,
                    data: legend
                },
                color: colorList,
                series: [{
                    name: '推送状态统计',
                    type: 'pie',
                    radius: '70%',
                    center: ['50%', '50%'],
                    label: {
                        show: false
                    },
                    emphasis: {
                        label: {
                            show: true,
                            formatter: '{b|{b}：}{c}   \n {per|{d}%}  ',
                            backgroundColor: '#f3f4f6',
                            borderColor: '#d0d5da',
                            borderWidth: 1,
                            borderRadius: 4,
                            padding: [0, 10],
                            rich: {
                                a: {
                                    color: '#999',
                                    lineHeight: 22,
                                    align: 'center'
                                },
                                b: {
                                    fontSize: 16,
                                    color: '#333',
                                    lineHeight: 30
                                },
                                c: {
                                    align: 'left',
                                    color: '#333!important',
                                    fontSize: 18
                                },
                                per: {
                                    color: '#1d87d1',
                                    align: 'left',
                                    lineHeight: 20
                                }
                            }
                        }
                    },
                    data: legend,
                }]
            };
            qqslChart.setOption(qqslOption,true);
        }
    });
});