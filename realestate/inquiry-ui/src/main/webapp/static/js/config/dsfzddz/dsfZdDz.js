/**
 * Created by ysy on 2020/7/20.
 * 配置页面js
 */
layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response'});

layui.use(['table','form','layer'], function() {
    var table = layui.table,
        form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;
    var BASE_URL = getContextPath() +"/rest/v1.0/dsfzdpz";

    $(function(){

        $('#search').on('click',function(){
            var getSearch = form.val("searchFilter");
            console.log(getSearch);
            // 重新请求
            table.reload("dzTableId", {
                where: getSearch
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
            });
        });

        $('#refresh').on('click',function(){
            addModel();
            $.ajax({
                type: 'GET',
                url: BASE_URL +"/refresh",
                dataType: "json",
                success: function () {
                    removeModal();

                },
                error: function(err){
                    removeModal();
                    delAjaxErrorMsg(err);
                }
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


        var url = BASE_URL + "/page";
        table.render({
            elem: '#dsfZdTable',
            id: 'dzTableId'
            ,page: true //开启分页
            ,defaultToolbar: ['filter']
            ,toolbar: "#toolbarDemo"
            ,url: url
            ,even: true
            ,cols: [[ //表头
                {type: 'checkbox', fixed: 'left',width:50}
                ,{field: 'zdbs', title: '字典标识',width:'44%'}
                ,{field: 'dsfxtbs', title: '第三方系统标识',width:'44%'}
                ,{title: '操作', width: 150,fixed: 'right',toolbar: '#barDemo'}
            ]]
            ,request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            }
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
                    $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 131);
                }
            }
        });

        //头工具栏事件
        table.on('toolbar(dsfZdTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var data = checkStatus.data;
            switch (obj.event) {
                case 'add':
                    addPz();
                    break;
                case 'delete':
                    deleteDsfZdx(data);
                    break;
            }
        });

        //监听工具条
        table.on('tool(dsfZdTable)', function(obj){
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            console.log(data);

            if(layEvent === 'edit'){
                modifyDsfZdDz(data);
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
                title: "新增第三方字典对照",
                area: ['1150px', '600px'],
                fixed: false, //不固定
                maxmin: true, //开启最大化最小化按钮
                content:["addZdDz.html", 'yes'],
                cancel: function(index, layero){
                    table.reload('dzTableId');
                    layer.close(index);
                }
            });
            layer.full(index);
        }

        //修改配置
        function modifyDsfZdDz(data){
            var index = layer.open({
                type: 2,
                title: "修改第三方字典对照",
                area: ['1150px', '600px'],
                fixed: false, //不固定
                maxmin: true, //开启最大化最小化按钮
                content:["addZdDz.html?zdbs=" + data.zdbs + "&dsfxtbs=" + data.dsfxtbs, 'yes'],
                cancel: function(index, layero){
                    table.reload('dzTableId');
                    layer.close(index);
                }
            });
            layer.full(index);
        }
    });

    function deleteDsfZdx(data){
        if(!data || data.length == 0){
            errorsMsg("请选择需要删除第三方对照数据");
            return;
        }
        var bdcZdgxDTO = new Object();
        var bdcZdDsfzdgxDO  = new Array();
        $.each(data,function(index, item){
            bdcZdDsfzdgxDO.push({
                zdbs : item.zdbs,
                dsfxtbs : item.dsfxtbs
            });
        });
        addModel();
        $.ajax({
            type: 'DELETE',
            url: BASE_URL +"/bs",
            contentType: 'application/json',
            dataType: "json",
            data: JSON.stringify(bdcZdDsfzdgxDO),
            success: function () {
                removeModal();
                successMsg("删除成功");
                setTimeout(function () {
                    table.reload('dzTableId');
                }, 500);
            },
            error: function(err){
                removeModal();
                delAjaxErrorMsg(err);
            }
        });
    }
});