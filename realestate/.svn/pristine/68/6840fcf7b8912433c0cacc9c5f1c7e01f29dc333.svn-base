/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/06/12
 * describe: 证书证明关联不动产单元台账
 */
layui.use(['form','jquery','laydate','element','table'],function () {
    var $ = layui.jquery,
        table = layui.table,
        laydate = layui.laydate;

    var zsid = $.getUrlParam("zsid");

    $(function () {
        // 加载Table
        table.render({
            elem: '#pageTable',
            title: '证书证明关联不动产单元列表',
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field:'xh',     width: 70, title: '序号', type: 'numbers'},
                {field:'slbh',   width:150, title:'受理编号'},
                {field:'bdcdyh', width:280, title:'不动产单元号',
                    templet: function(d){
                        return formatBdcdyh(d.bdcdyh);
                    }
                },
                {field:'zl',  minWidth:250, title:'坐落'},
                {field:'qlrmc',  width:300, title:'权利人名称'},
                {field:'qlrzjh', width:250, title:'权利人证件号'},
                {field:'ywrmc',  width:300, title:'义务人名称'},
                {field:'ywrzjh', width:250, title:'义务人证件号'},
                {title:'查看', fixed:'right', toolbar: '#barDemo', width:100}
            ]],
            page: true,
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                setHeightWithNoToolbar();
                var reverseList = ['zl'];
                reverseTableCell(reverseList);
            }
        });

        //监听工具条
        table.on('tool(pageTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'djls'){
                viewDjlsgx(data);
            }
        });

        // 查看历史关系
        function viewDjlsgx(data){
            window.open("/realestate-register-ui/view/lsgx/bdcdyDjLsgxNew.html?bdcdyh=" + data.bdcdyh+"&tzym=bdcZszmBdcdy.html");
        }


        // 点击查询
        $('#search').on('click',function () {
            search();
        });

        // 回车查询
        $('.search').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        // 默认初始查询
        search();

        // 查询处理逻辑
        function search(){
            // 获取查询参数
            var obj = {};
            $(".search").each(function(i){
                var name = $(this).attr('name');
                var value = $(this).val();
                if(value){
                    value = value.replace(/\s*/g,"");
                }
                obj[name] = value;
            });
            obj.zsid = zsid;

            addModel();
            // 重新请求
            table.reload("pageTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/zszm/xmxx"
                ,where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
                ,done: function (res, curr, count) {
                    var reverseList = ['zl'];
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeightWithNoToolbar();
                }
            });
        }

        // 重置清空查询条件
        $('#reset').on('click',function () {
            $("input[name='bdcdyh']").val(null);
            $("input[name='zl']").val(null);
            $("input[name='qlrmc']").val(null);
            $("input[name='qlrzjh']").val(null);
            search();
        });
    });

    //身份证读卡器设置
    window.onReadIdCard = function(qlrlb){
        try {
            var cardInfo = readIdCard();
            if (cardInfo.ReadCard()) {
                var name = cardInfo.Name;
                var number = cardInfo.ID;

                if(1 == qlrlb){
                    $('input[name="qlrmc"]').val(name);
                    $('input[name="qlrzjh"]').val(number);
                } else {
                    $('input[name="ywrmc"]').val(name);
                    $('input[name="ywrzjh"]').val(number);
                }
            } else {
                alertMsg("请检查读卡器是否安装成功并重新放置身份证！");
            }
        } catch (objError) {
            alertMsg("请检查读卡器是否安装成功并重新放置身份证！");
        }
    }
});