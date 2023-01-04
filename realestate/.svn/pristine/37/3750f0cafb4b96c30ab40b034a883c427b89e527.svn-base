/**
 * Created by ypp on 2020/3/2.
 * 配置页面js
 */
var zdx = {
    pzlxZd : [],
    pzzxtZd : [],
    gnmkZd : []
};
layui.use(['table','form','layer'], function(){
    var table = layui.table,
        form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;



    $(function(){
        var BASE_URL = getContextPath() +"/rest/v1.0/gxjkpz";
        $('#search').on('click',function(){
            var getSearch = form.val("searchFilter");
            // 重新请求
            table.reload("pzTableId", {
                where: getSearch
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
            });
        });

        var isSearch = true;
        //监听回车事件
        $(document).keydown(function (event) {
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

        /**
         * 加载Table数据列表
         */
        table.render({
            elem: '#pzTable'
            ,id: 'pzTableId'
            ,page: true //开启分页
            ,defaultToolbar: ['filter']
            ,toolbar: "#toolbarDemo"
            ,url: BASE_URL+"/page"
            ,even: true
            ,cols: [[ //表头
                {type: 'checkbox', fixed: 'left',width:50}
                ,{field: 'fmldm', title: '父目录代码'}
                ,{field: 'fmlmc', title: '父目录名称'}
                ,{field: 'zmldm', title: '子目录代码'}
                ,{field: 'zmlmc', title: '子目录名称'}
                ,{field: 'ymdz', title: '页面地址'}
                ,{title: '操作', width: 100,fixed: 'right',toolbar: '#barDemo'}
            ]]
            ,request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            }
            ,autoSort: false
            ,parseData: function (res) {
                    return {
                        code: res.code, //解析接口状态
                        msg: res.msg, //解析提示文本
                        count: res.totalElements, //解析数据长度
                        data: res.content //解析数据列表
                    }
                }
            ,done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');


                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png"  alt="">无数据');
                }else {
                    $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);
                    console.info($('.bdc-table-box').height());
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        });

        //头工具栏事件
        table.on('toolbar(pzFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'add':
                    addPz();
                    break;
                case 'delete':
                    deleteGxjkzd(checkStatus.data);
                    break;
                case 'lcpz':
                    lcpz();
                    break;
            }

        });

        //监听工具条
        table.on('tool(pzFilter)', function(obj){
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if(layEvent ==="modify"){
                var index = layer.open({
                    type: 2,
                    title: "修改配置",
                    area: ['1150px', '600px'],
                    fixed: false, //不固定
                    maxmin: true, //开启最大化最小化按钮
                    content:["../gxjkpz/addOrEditPz.html?action=edit&id="+data.id, 'yes'],
                    success: function (layero, index) {
                    },
                    end:function(){
                        table.reload('pzTableId');
                    }

                });
                layer.full(index);
            }

        });




        //3. 输入框删除图标
        if(!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i)=="9."))
        {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click','.layui-icon-close',function () {
                $(this).siblings('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus',function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
            }).on('blur',function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                },150)
            });
        }


        //新增配置
        function addPz() {
            var index = layer.open({
                type: 2,
                title: "新增配置",
                area: ['1150px', '600px'],
                fixed: false, //不固定
                maxmin: true, //开启最大化最小化按钮
                content:["../gxjkpz/addOrEditPz.html?action=add", 'yes'],
                success: function (layero, index) {
                },
                end:function(){
                    table.reload('pzTableId');
                }

            });
            layer.full(index);
            
        }

        /**
         * 配置内容删除
         */
        function deleteGxjkzd(data){
            if(!data || data.length == 0){
                layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要删除配置的记录');
                return;
            }
            var deleteIndex = layer.open({
                type: 1,
                skin: 'bdc-small-tips',
                title: '确认删除',
                area: ['320px'],
                content: '是否确认删除？',
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function () {

                    addModel();
                    $.ajax({
                        type: 'DELETE',
                        url: BASE_URL,
                        contentType: 'application/json',
                        dataType: "json",
                        data: JSON.stringify(data),
                        success: function () {
                            removeModal();
                            layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">删除成功');
                            setTimeout(function () {
                                table.reload('pzTableId');
                            }, 500);
                        },
                        error: function(err){
                            removeModal();
                            delAjaxErrorMsg(err);
                        }
                    });
                    layer.close(deleteIndex);
                },
                btn2: function () {
                    //取消
                    layer.close(deleteIndex);
                }
            });


        }

        function lcpz(){
            window.open("/realestate-inquiry-ui/view/config/gxjkpz/gxjklcpz.html");
        }

    });
});
