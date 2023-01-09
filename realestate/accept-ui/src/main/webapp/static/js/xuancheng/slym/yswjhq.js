layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var gzlslid = $.getUrlParam('gzlslid');
//页面入口
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    $(function () {
    laydate.render({
        elem: '#startTime',
        type: 'datetime',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#endTime').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/image/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#endTime',
        type: 'datetime',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#startTime').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/image/error-small.png" alt="">结束时间不能小于开始时间');
            }

        }
    });
    //点击高级查询--4的倍数
    $('#seniorSearch').on('click',function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        $(this).parent().toggleClass('bdc-button-box-four');
        $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);
        if($(this).html() == '高级查询'){
            $(this).html('收起');
        }else {
            $(this).html('高级查询');
        }
    });

        $(function () {
            getZdList();
            $('#selectedGrCategoryName').append(new Option("请选择", ""));
            var wjlxSelect = zdList["ggpt"];
            $.each(wjlxSelect, function (index, item) {
                $('#selectedWjlx').append(new Option(item.MC, item.DM));
                form.render('select');
            });
        });




    table.render({
        elem: '#pageTable',
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter'],
        even: true,
        data:[],
        cols: [[

            {field: 'xmmc', title: '项目名称', width: 140},
            {field: 'xmwz', title: '项目位置', width: 200},
            {field: 'fjmc', title: '附件名称', width: 160},
            {field: 'dwmc', title: '单位名称', width: 300},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 100, sort: true},
            {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 180}
        ]],
        autoSort: false,
        page: true,
        parseData: function (res) {
            var result;
            if (isNullOrEmpty(res)) {
                return {
                    code: 0, //解析接口状态
                    msg: '<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据',
                    count: 0, //解析数据长度
                    data: [] //解析数据列表
                }
            } else {
                if(this.page.curr){
                    //若为第二页 则curr为2 页面显示的数据就是从res.data集合数组里的 （2-1）*limit（10）=10 到 limit（10）*2=20的数据
                    result = res.content.slice(this.limit*(this.page.curr-1),this.limit*this.page.curr);
                }
                else{
                    // 一开始就是第一页 则就是 显示的数据就是从res.data集合数组里的0到limit（10）中
                    result=res.content.slice(0,this.limit);
                }
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: result //解析数据列表
                }
            }
        },
        done: function () {
            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');


            if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
            } else {
                $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 131);
            }
        }
    });
        form.render();
        $('#search').on('click', function () {
            search();
        });
        // 回车查询
        $('.required').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });
        // 下拉选择添加删除按钮
        renderSelectClose(form);
        //监听行工具事件
        table.on('tool(pageTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'savefile'){
                $.ajax({
                    type: "POST",
                    url: getContextPath() + "/ggpt/fjxz?gzlslid=" + gzlslid,
                    traditional: true,
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        ityzl_SHOW_SUCCESS_LAYER("保存成功");
                    }, error: function (e) {
                        ityzl_SHOW_WARN_LAYER("失败");
                    }
                });
            }
        });

        function search(){
            if(isNullOrEmpty($("#selectedWjlx").val())){
                ityzl_SHOW_INFO_LAYER("请选择文件类型");
            }else{
                // 获取查询参数
                var obj = {};
                $(".cxtj").each(function (i) {
                    var name = $(this).attr('name');
                    obj[name] = $(this).val();
                });
                addModel();
                table.reload("pageTable", {
                    url: getContextPath() + "/ggpt/ggxx",
                    contentType: 'application/json',
                    dataType: "json",
                    where: {obj: obj},
                    method: 'post',
                    page: {
                        curr: 1  //重新从第 1 页开始
                    },
                    done: function (data) {
                        removeModal();
                        currentPageData = data.data;
                    }
                });
            }

        }


    });
});
//加载字典
function getZdList() {
    getReturnData("/bdczd", {}, "POST", function (data) {
        zdList = data;
    }, function () {
    }, false);
    return zdList;
}