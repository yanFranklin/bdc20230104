var searchData, searchFilterData, needsearch, needFilter;
// 当前页数据
var currentPageData = new Array();
var fjdz;
layui.use(['form', 'jquery', 'laydate', 'element', 'table', 'laytpl'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laytpl = layui.laytpl,
        laydate = layui.laydate;
    $(function () {
        // 加载Table
        table.render({
            elem: '#pageTable',
            toolbar: '#toolbarDemo',
            // url: "/realestate-inquiry-ui/rest/v1.0/chcg/bengbu/chcgcx",
            title: '测绘成果',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'cgflname',  title: '附件名称'},
                {
                    field: 'attachpath', hide:true, title: '附件地址',
                    templet: function (d) {
                        return showFj(d);
                    }
                },
                {field: 'cz', title: '操作', templet: '#barDemo', align: "center",fixed:"right"}
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                // 设置选中行
                if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.xmid) {
                                v.LAY_CHECKED = true;
                            }
                        })
                    });
                }

                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                setHeight();
            }
        });

        //头工具栏事件
        table.on('toolbar(pageTable)', function (obj) {
            switch (obj.event) {
                case 'loadFj':
                    loadFj();
                    break;
            }
        });

        //监听工具条
        table.on('tool(pageTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'fjck') {
                previewFj(data);
            }
        });

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        function search() {
            // 获取查询内容
            var bdcdyh = $('#bdcdyh').val();
            if (isNullOrEmpty(bdcdyh)){
                warnMsg("请输入必要查询条件！");
                return;
            }
            var obj = {'bdcdyh':bdcdyh,'beanname':'queryDchyCgfl'};
            addModel();
            // 重新请求
            table.reload("pageTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/chcg/bengbu/chcgcx",
                where: obj,
                page: {
                    curr: 1,  //重新从第 1 页开始
                    limit: [10, 50, 100, 200, 500]
                },
                done: function (res, curr, count) {
                    if(res.code == 0){
                        currentPageData = res.data;
                        removeModal();
                        setHeight();
                    }else{
                        $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + res.msg);
                    }
                },
                error: function (res) {
                    removeModal();
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + res.msg);
                }
            });
        }

        /**
         * 预览附件
         */
        function previewFj(data){
            if (isNullOrEmpty(data.attachpath)){
                warnMsg("无附件地址，无法预览");
                return;
            }
            window.open(data.attachpath);
        }

        function showFj(data) {
            if (!isNullOrEmpty(data.attachpath)){
                return '<a href="javascript:viewFj(&quot;' + data.attachpath + '&quot;)" class="layui-table-link">' + data.attachpath + '</a>';
            }
        }

        window.viewFj = function (attachpath) {
            window.open(attachpath);
        }

        function loadFj() {
            var bdcdyh = $('#bdcdyh').val();
            var pdfUrl = document.location.protocol + "//" + document.location.host +
                "/realestate-inquiry-ui/rest/v1.0/chcg/fjxzzip?bdcdyh=" + encodeURIComponent(bdcdyh);
            window.open(pdfUrl, "FJDOWNLOAD"+ bdcdyh);
        }

    });
});



