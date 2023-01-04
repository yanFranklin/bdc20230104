/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 查询条件所需的日期初始化、复选框初始化及高级查询
 */
layui.config({
    base: '../../' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'form-select/formSelects-v4',
    selectN: 'select-extend/selectN'
});
layui.use(['form','jquery','laydate','element','formSelects','selectN'],function () {
    var $ = layui.jquery,
        form = layui.form,
        formSelects = layui.formSelects,
        laydate = layui.laydate,
        selectN = layui.selectN;
    $(function () {
        //1. 日期
        lay('.test-item').each(function(){
            //精确到时分秒日期控件
            laydate.render({
                elem: '#exact',
                trigger: 'click'
                ,range: '至',
                done: function(value, date, endDate){
                    console.log(value); //得到日期生成的值，如：2017-08-18
                    console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                    console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                }
            });
            laydate.render({
                elem: '#exact1',
                type: 'datetime'
                ,trigger: 'click'
            });
            //初始化日期控件
            laydate.render({
                elem: '#startTime1'
                ,trigger: 'click'
            });
            var startDate = laydate.render({
                elem: '#startTime'
                ,trigger: 'click',
                done: function(value,date){
                    //选择的结束时间大
                    if($('#endTime').val() != '' && !completeDate($('#endTime').val(),value)){
                        $('#endTime').val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                    }
                    endDate.config.min ={
                        year:date.year,
                        month:date.month-1,
                        date: date.date
                    }
                }
            });
            var endDate = laydate.render({
                elem: '#endTime'
                ,trigger: 'click'
            });
        });
        function completeDate(date1,date2){
            var oDate1 = new Date(date1);
            var oDate2 = new Date(date2);
            if(oDate1.getTime() > oDate2.getTime()){
                return true;
            } else {
                return false;
            }
        }

        //2. 下拉选择
        //2.1渲染单选框
        formSelects.data('select03', 'local', {
            arr: [
                {"name": "首次登记", "value": '100'},
                {"name": "转移登记", "value": '200'},
                {"name": "变更登记", "value": '300'},
                {"name": "注销登记", "value": '400'},
                {"name": "更正登记", "value": '500'},
                {"name": "异议登记", "value": '600'}
            ]
        });
        //2.2 js渲染复选框
        formSelects.data('selectJs', 'local', {
            arr: [
                {"name": "北京北京北京北京北京北京北京北京", "value": 1},
                {"name": "上海", "value": 2,"selected":"selected"},
                {"name": "广州", "value": 3},
                {"name": "深圳", "value": 0},
                {"name": "天津", "value": 5}
            ]
        });
        //默认选中value为0的，用引号包裹
        // formSelects.value('selectJs',['0',3]);
        //2.3 可创建目录单选下拉
        formSelects.on('select11', function(id, vals, val, isAdd, isDisabled){
            //id:           点击select的id
            //vals:         当前select已选中的值
            //val:          当前select点击的值
            //isAdd:        当前操作选中or取消
            //isDisabled:   当前选项是否是disabled
            console.log(val);//{name: "南京", value: "1557800098499"}
            console.log("选择了: " + val.name);

            //创建条目需要选择操作，监听选择可以把所有创建的保存到数据库
            // 可以根据选中的值，判断是不是数据库中的，不是则可以存入数据库
        });
        
        //获取选择的值
        console.log(formSelects.value('selectJs'));               // [{"name":"上海","val":"2"},{"name":"深圳","val":"4"}]
        console.log(formSelects.value('selectJs', 'val'));        // ["2","4"]
        console.log(formSelects.value('selectJs', 'name'));       // ["上海","深圳"]

        //重新渲染方式

        //监听选择
        formSelects.on('selectPage', function(id, vals, val, isAdd, isDisabled){
            //id:           点击select的id
            //vals:         当前select已选中的值
            //val:          当前select点击的值
            //isAdd:        当前操作选中or取消
            //isDisabled:   当前选项是否是disabled
            console.log("选择了: " + val.name);
            //如果return false, 那么将取消本次操作
            if(val.value == 5){
                return false;
            }
        });

        //3. 输入框删除图标
        if(!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i)=="9."))
        {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click','.layui-icon-close',function () {
                $(this).siblings('.layui-input').val('');
                $(this).siblings(':not(.bdc-screen-inline)').find('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus',function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                $(this).parents('.layui-input-inline').find('.layui-icon-close').removeClass('bdc-hide');
                $(this).siblings('.layui-edge').addClass('bdc-hide');
            }).on('blur',function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                    $this.parents('.layui-input-inline').find('.layui-icon-close').addClass('bdc-hide');
                    $this.siblings('.layui-edge').removeClass('bdc-hide');
                },150)
            });
        }

        //4.高级查询
        // 点击高级查询--3的倍数
        $('#seniorSearch').on('click',function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            $(this).parent().toggleClass('bdc-button-box-four');

            if($(this).html() == '高级查询'){
                $(this).html('收起');
            }else {
                $(this).html('高级查询');
            }
        });
        //点击高级查询--一般情况
        $('#seniorSearchNormal').on('click',function () {
            $('.pf-senior-show-normal').toggleClass('bdc-hide');

            if($(this).html() == '高级查询'){
                $(this).html('收起');
            }else {
                $(this).html('高级查询');
            }
        });

        // 多级
        var catData = [
            {"id":1,"name":"周边旅游","children":
                [
                    {"id":24,"name":"广东","status":0}
                ]
            },
            {"id":5,"name":"国内旅游","children":
                [
                    {"id":8,"name":"华北地区"}
                ]
            },
            {"id":6,"name":"出境旅游","children":[
                {"id":10,"name":"东南亚"}
            ]}
        ];
        var catData1 = [
            {"id":1,"name":"周边旅游","children":
                [
                    {"id":24,"name":"广东","status":0,"children":
                        [
                            {"id":7,"name":"广州"},
                            {"id":23,"name":"潮州"}
                        ]
                    }
                ]
            },
            {"id":5,"name":"国内旅游","children":
                [
                    {"id":8,"name":"华北地区","children":
                        [
                            {"id":9,"name":"北京"}
                        ]
                    }
                ]
            },
            {"id":6,"name":"出境旅游","children":
                [
                    {"id":10,"name":"东南亚","children":
                        [
                            {"id":11,"name":"马来西亚"}
                        ]
                    }
                ]
            }
        ];

        //无限级分类-基本配置--二级分类
        var catIns1 = selectN({
            //元素容器【必填】
            elem: '#cat_ids1'
            ,search:[false,true]
            ,showNumber: 2 //几级，默认3级
            //候选数据【必填】
            ,data: catData
        });

        //无限级分类-所有配置--三级级分类
        var catIns2 = selectN({
            //元素容器【必填】
            elem: '#cat_ids2'
            //候选数据【必填】
            ,data: catData1
            //设置了长度
            ,width:null
            //默认值
            ,selected: [6,10]

            //为真只取最后一个值
            ,last:true

            //空值项提示，可设置为数组['请选择省','请选择市','请选择县']
            ,tips: '请选择'

            //事件过滤器，lay-filter名 不设置与选择器相同(去#.)
            ,filter: ''

            //input的name 不设置与选择器相同(去#.)
            ,name: 'cat2'

            //数据分隔符
            ,delimiter: ','

            //数据的键名
            ,field:{idName:'id',titleName:'name',childName:'children'}

            //表单区分 form.render(type, filter); 为class="layui-form" 所在元素的 lay-filter="" 的值
            ,formFilter: null

        });
        $('#selectSearch').on('click',function () {
            console.log('catIns1 当前选中的值名：',catIns1.selected);
            console.log('catIns1 当前选中的值：',catIns1.values);
            console.log('catIns1 当前选中的名：',catIns1.names);
            console.log('catIns1 当前最后一个选中值：',catIns1.lastValue);
            console.log('catIns1 当前最后一个选中名：',catIns1.lastName);
            console.log('catIns1 当前最后一个是否已选：',catIns1.isSelected);

            console.log('catIns2 当前选中的值名：',catIns2.selected);
            console.log('catIns2 当前选中的值：',catIns2.values);
            console.log('catIns2 当前选中的名：',catIns2.names);
        });

    });
});