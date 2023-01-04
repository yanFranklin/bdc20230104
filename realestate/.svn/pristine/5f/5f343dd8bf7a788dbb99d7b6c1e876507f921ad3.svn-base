layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({formSelects:'lib/form-select/formSelects-v4'});
layui.use(['table','form','layer','formSelects'], function() {
    var table = layui.table,
        formSelects = layui.formSelects,
        form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;
    var BASE_URL = getContextPath() +"/rest/v1.0/jspz";

    formSelects.config('selectQxdm', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('selectEditQxdm', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    $(function(){

        /**
         * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
         * @description 渲染 select
         */
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/lcpz/zdxx",
            type: "POST",
            data:'qx',
            contentType: 'application/json',
            dataType: "json",
            async:false,
            success: function (data) {
                if(data){
                    formSelects.data('selectQxdm','local',{arr:data.qx});
                    formSelects.data('selectEditQxdm','local',{arr:data.qx});
                }
            },
            error: function (e) {
                delAjaxErrorMsg(e);
            }
        });

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
            elem: '#qxdmpzTable',
            id: 'dzTableId'
            ,page: true //开启分页
            ,defaultToolbar: ['filter']
            ,toolbar: "#toolbarDemo"
            ,url: url
            ,even: true
            ,cols: [[ //表头
                {type: 'checkbox', fixed: 'left',width:50}
                ,{field: 'rolecode', title: '角色编码',width:'44%'}
                ,{field: 'qxdm', title: '区县',width:'44%'}
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
        table.on('toolbar(qxdmpzTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var data = checkStatus.data;
            switch (obj.event) {
                case 'add':
                    addPz();
                    break;
                case 'delete':
                    deleteJsQxdmPz(data);
                    break;
            }
        });

        //监听工具条
        table.on('tool(qxdmpzTable)', function(obj){
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            console.log(data);

            if(layEvent === 'edit'){
                modifyJsQxdmPz(data);
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

        /**
         * 新增
         */
        function addPz() {

            addModel();
            //小弹出层
            layer.open({
                title: '新增角色区县代码配置',
                type: 1,
                area: ['430px','350px'],
                btn: ['提交', '取消'],
                content: $('#bdc-popup-small'),
                yes: function(index, layero){
                    saveJsQxdmPz(index);
                },
                btn2: function(index, layero){
                    //取消 的回调
                    removeModal();
                },
                cancel: function(index){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                    removeModal();
                },
                success: function () {
                    formSelects.value('selectEditQxdm',[]);
                    $("#edit-rolecode").val("");
                    $("#gxid").val("");
                }
            });
        }


        function saveJsQxdmPz(index) {
            var qxdm =formSelects.value('selectEditQxdm')
            var jsqxdmPz={
                "rolecode": $("#edit-rolecode").val(),
                "qxdm": formSelects.value('selectEditQxdm','val').join(","),
                "gxid": $("#gxid").val()
            };
            if(isNullOrEmpty(jsqxdmPz.qxdm) ||isNullOrEmpty(jsqxdmPz.rolecode)){
                layer.alert("区县或角色编码不能为空");
                return false;
            }
            //提交 的回调
            $.ajax({
                url: BASE_URL + '/save',
                type: "PATCH",
                dataType: "json",
                contentType: 'application/json',
                data:JSON.stringify(jsqxdmPz),
                success: function (data) {
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功',{
                        time: 1000}
                    )
                },error: function (e) {
                    delAjaxErrorMsg(e);
                },complete:function () {
                    $('#rolecode').val('');
                    formSelects.value('selectEditQxdm',[]);
                    $('#gxid').val('');
                    layer.close(index);
                    table.reload('dzTableId');
                    removeModal();
                }
            });
        }

        //修改配置
        function modifyJsQxdmPz(data){
            $('#gxid').val(data.gxid);
            formSelects.value('selectEditQxdm', data.qxdm.split(','));
            $('#edit-rolecode').val(data.rolecode);
            formSelects
            var index = layer.open({
                title: '修改角色区县代码配置',
                type: 1,
                area: ['430px','350px'],
                btn: ['提交', '取消'],
                content: $('#bdc-popup-small'),
                yes: function(index, layero){
                    saveJsQxdmPz(index);
                },
                btn2: function(index, layero){
                    //取消 的回调
                    removeModal();
                },
                cancel: function(index){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                    removeModal();
                },
                success: function () {
                }
            });
        }
    });

    function deleteJsQxdmPz(data){
        if(!data || data.length == 0){
            errorsMsg("请选择需要删除角色区县代码配置数据");
            return;
        }
        var bdcJsQxdmPz  = [];
        $.each(data,function(index, item){
            bdcJsQxdmPz.push({
                rolecode : item.rolecode,
                qxdm : item.qxdm
            });
        });
        addModel();
        $.ajax({
            type: 'DELETE',
            url: BASE_URL,
            contentType: 'application/json',
            dataType: "json",
            data: JSON.stringify(bdcJsQxdmPz),
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