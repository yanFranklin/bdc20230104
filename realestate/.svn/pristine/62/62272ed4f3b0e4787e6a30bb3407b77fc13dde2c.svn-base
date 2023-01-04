/**
 * Created by ysy on 2020/7/20.
 * 配置页面js
 */
layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
var zddata = [];
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table,
        form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;
    var BASE_URL = getContextPath() + "/rest/v1.0/bdczdpz";


    $(function () {
        getZdList();
        $('#search').on('click', function () {
            var getSearch = form.val("searchFilter");
            getZdList(getSearch.tablename,getSearch.zdname)
            // 重新请求
            table.reload("dzTableId", {
                data: zddata
            });
        });

        var isSearch = true;
        //监听回车事件
        $(document).keydown(function (event) {
            var tab = $('.layui-this').val();
            if (event.keyCode == 13 && isSearch) { //绑定回车
                $(".bdc-search-box #search").click();
            }
        });
        //判断回车操作
        $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });


        table.render({
            elem: '#zdTable',
            id: 'dzTableId'
            , page: true //开启分页
            , defaultToolbar: ['filter']
            , toolbar: "#toolbarDemo"
            , data: zddata
            , even: true
            , cols: [[ //表头
                {type: 'checkbox',width:50,  fixed: 'left'}
                ,{field: 'name', title: '字典名称', width: 150}
                ,{field: 'mc', title: '字典名称描述', minWidth: 200}
                ,{field: 'tableName', title: '表名', width: 200}
                ,{title: '操作', width:120, fixed: 'right', toolbar: '#barDemo'}
            ]]
            , request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            }
            , done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');


                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png"  alt="">无数据');
                } else {
                    $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 131);
                }
            }
        });


        //监听工具条
        table.on('tool(zdTable)', function (obj) {
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

            if (layEvent === 'edit') {
                modifyBdcZdDz(data);
            }
            if (layEvent === 'refresh') {
                refreshBdcZdDz(data);
            }

        });

        //头工具栏事件
        table.on('toolbar(zdTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'refresh':
                    var checkData = checkStatus.data;
                    if(checkData && checkData.length >0){
                        for(var i=0;i<checkData.length;i++){
                            refreshBdcZdDz(checkData[i]);
                        }
                    }else{
                        layer.alert("请先勾选需要刷新的字典");
                        return false
                    }
                    break;
            }
        });

        //3. 输入框删除图标
        if (!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.")) {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
                $(this).siblings('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
            }).on('blur', function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                }, 150)
            });
        }


        //修改配置
        function modifyBdcZdDz(data) {
            var index = layer.open({
                type: 2,
                title: "修改第三方字典对照",
                area: ['1150px', '600px'],
                fixed: false, //不固定
                maxmin: true, //开启最大化最小化按钮
                content: ["../zd/editZdDz.html?zdmc=" + data.name, 'yes'],
                cancel: function (index, layero) {
                    refreshBdcZdDz(data);
                    layer.close(index);
                }
            });
            layer.full(index);
        }

        //刷新配置
        function refreshBdcZdDz(data) {
            addModel();
            $.ajax({
                type: 'GET',
                url: BASE_URL + "/refresh",
                data: {
                    'tablename': data.tableName
                },
                dataType: "json",
                success: function (data) {
                    removeModal();
                },
                error: function (err) {
                    removeModal();
                    delAjaxErrorMsg(err);
                }
            });
        }

        //获得展示列表
        function getZdList(tablename,zdname) {
            addModel();
            $.ajax({
                type: 'GET',
                url: BASE_URL + "/list",
                data: {
                    'tablename': tablename,
                    'zdname':zdname
                },
                async: false,
                dataType: "json",
                success: function (data) {
                    zddata = data;
                    table.resize('zdTable');
                    removeModal();

                },
                error: function (err) {
                    removeModal();
                    delAjaxErrorMsg(err);
                }
            });
        }
    });

});