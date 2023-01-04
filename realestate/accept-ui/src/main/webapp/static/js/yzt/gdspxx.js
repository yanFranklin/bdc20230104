
var reverseList = ['zl'];
// 分页重置查询参数
var searchParam =[];

var processInsId = getQueryString("processInsId");

layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;

    // 当前页数据
    var currentPageData = [];
    $(function () {
        // 加载Table
        table.render({
            elem: '#gdspxxTable',
            toolbar: '#toolbarDemo',
            title: '供地审批信息',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[

                {type: 'checkbox', fixed: 'left'},
                {field: 'xh',      title: '序号', type: 'numbers'},
                {field: 'gyId',   title: '共用id', hide: true},

                {field: 'proid',   title: '编号', hide: true},

                {field: 'sqyddw',  minWidth: 230, title: '申请用地单位'},
                {field: 'ydwz',    minWidth: 280, title: '用地位置'},
                {field: 'sqydmj',      minWidth: 80,  title: '申请用地面积'},
                {field: 'tdyt',    minWidth: 100, title: '规划用途'}
            ]],
            data: [],
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
                setHeight();
                reverseTableCell(reverseList);
            }
        });

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
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });

            if (0 == count) {
                layer.alert("<div style='text-align: center'>请输入必要查询条件！</div>", {title: '提示'});
                return false;
            }

            // 获取查询参数
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });

            searchParam = obj;
            addModel();
            // 重新请求
            table.reload("gdspxxTable", {
                url:  "/realestate-accept-ui/yzt/gdspxx/list"
                , where: obj
                , page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();
                }
            });
        }

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

    });

    // 监听表格操作栏按钮
    table.on('toolbar(gdspxxTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;
        switch (obj.event) {
            case 'drcl':
                drcl(data);
                break;
        }
    });

});

/**
 * 导入材料
 * @param data
 */
function drcl(data){
    if(data){
        if(data.length > 1){
            layer.alert("<div style='text-align: center'>一次只能操作一条数据</div>", {title: '提示'});
            return;
        }
        var proid = data[0].proid;
        console.log("导入材料的proid和processInsId分别为：");
        console.log(proid + ";" + processInsId);
        // 打开附件操作弹窗

        layer.open({
            title: '附件信息',
            type: 2,
            area: ['1150px', '600px'],
            content: "/realestate-accept-ui/view/yzt/yztfj.html?autoTzFj=false&processInsId="+processInsId+"&proid="+proid
            ,cancel: function(){
            }
            ,success: function () {
            }
            ,end: function (index, layero) {

            }
        });


    }
}

