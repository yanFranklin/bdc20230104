layui.config({
    base: '../../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['table','form','layer','formSelects'], function() {
    var table = layui.table,
        form = layui.form,
        $ = layui.jquery,
        layer = layui.layer,
        formSelects = layui.formSelects;

    var consumer = getConsumer("gxbmbz");
    formSelects.data('consumer', 'local', {
        arr: consumer
    });

    var BASE_URL = getContextPath() +"/rest/v1.0/apiManagement";

    $(function(){

        $('#search').on('click',function(){
            var getSearch = form.val("searchFilter");
            console.log(getSearch);
            // 重新请求
            table.reload("contrastRelationTable", {
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


        var url = BASE_URL + "/contrastRelation/page/search";
        table.render({
            elem: '#contrastRelationTable',
            id: 'contrastRelationTable'
            ,page: true //开启分页
            ,defaultToolbar: ['filter']
            ,toolbar: "#toolbarDemo"
            ,url: url
            ,even: true
            ,cols: [[ //表头
                {type: 'checkbox', fixed: 'left',width:50,},
                {field: 'consumer', title: '接口消费方(共享部门)',width:'44%',
                    templet:function(d){
                        if (d.consumer == null)
                        {
                            return "";
                        }
                        return convertConsumerDictionary(d.consumer,consumer)}},
                {field: 'principal', title: '应用id/用户名',width:'44%'},
                {title: '操作', width: 150,fixed: 'right',toolbar: '#barDemo'}
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
        table.on('toolbar(contrastRelationTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var data = checkStatus.data;
            switch (obj.event) {
                case 'add':
                    addContrastRelation();
                    break;
                case 'delete':
                    deleteContrastRelation(data);
                    break;
            }
        });

        //监听工具条
        table.on('tool(contrastRelationTable)', function(obj){
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            console.log(data);

            if(layEvent === 'edit'){
                updateContrastRelation(data);
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

        //新增对照
        function addContrastRelation() {
            renderSelect();
            $("#popup-consumer").val(''),
            $("#popup-principal").val(''),
            form.render('select');
            layer.open({
                title: '新增接口消费方对照',
                type: 1,
                area: ['430px','460px'],
                btn: ['保存'],
                content: $('#popup')
                , yes: function (index, layero) {
                    //提交 的回调
                    var url = getContextPath() +"/rest/v1.0/apiManagement" + "/contrastRelation/save";
                    edit(url,"")
                    layer.close(index);
                }
                , btn2: function (index, layero) {

                }
                , cancel: function () {

                }
                , success: function () {

                }
            });
        }

        //修改配置
        function updateContrastRelation(data){
            renderSelect();
            $("#popup-consumer").each(function() {
                $(this).children("option").each(function() {
                    if (this.value == data.consumer) {
                        // 进行回显
                        console.log(this);
                        $(this).attr("selected","selected");
                    }
                });
            })
            $("#popup-principal").val(data.principal);
            form.render('select');
            layer.open({
                title: '修改接口消费方对照',
                type: 1,
                area: ['430px','460px'],
                btn: ['保存'],
                content: $('#popup')
                , yes: function (index, layero) {
                    //提交 的回调
                    var url = getContextPath() +"/rest/v1.0/apiManagement" + "/contrastRelation/update";
                    edit(url,data.id)
                    layer.close(index);
                }
                , btn2: function (index, layero) {

                }
                , cancel: function () {

                }
                , success: function () {

                }
            });
        }
    });


    function edit(url,id){
        //点击提交时不为空的全部标红
        var isSubmit = true;
        var verifyMsg = "必填项不能为空";
        form.verify({
            required: function (value, item) {
                if (value == '' || value == null || value == undefined) {//判断是否为空
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                }
            },
        });

        //提交保存数据
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {
            var param = {
                "id": id,
                "consumer": $("#popup-consumer").val(),
                "principal": $("#popup-principal").val(),
            }
            console.info(param);
            console.info(url);
            addModel();
            $.ajax({
                type: 'POST',
                url: url,
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify(param),
                success: function (data) {
                    removeModal();
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">保存成功');
                    setTimeout(function () {
                        table.reload('contrastRelationTable');
                    }, 500);
                },
                error: function(err){
                    removeModal();
                    delAjaxErrorMsg(err);
                }
            });
            return false;
        }

    }
    function deleteContrastRelation(data){
        if(!data || data.length == 0){
            layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要删除的对照数据');
            return;
        }
        var ids = [];
        $.each(data,function(index, item){
            ids.push(item.id);
        });
        var param = {
            "ids":ids
        };
        addModel();
        $.ajax({
            type: 'POST',
            url: BASE_URL +"/contrastRelation/delete",
            contentType: 'application/json',
            dataType: "json",
            data: JSON.stringify(param),
            success: function () {
                removeModal();
                layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">删除成功');
                setTimeout(function () {
                    table.reload('contrastRelationTable');
                }, 500);
            },
            error: function(err){
                removeModal();
                delAjaxErrorMsg(err);
            }
        });
    }
});

function renderSelect() {
    // 消费方(共享部门)下拉框
    var consumer = getConsumer("gxbmbz");
    var consumerOption = "<option value=''>" + '请选择' + "</option>";
    if (consumer.length > 0) {
        consumer.forEach((v, i) => {
            consumerOption += "<option value='" + v.value + "'>" + v.name + "</option>";
        });
    }
    $("#popup-consumer").append(consumerOption);
}

/**
 * 获取消费方字典项
 * @param dictionary
 */
function getConsumer(dictionary) {
    var consumer = [];
    $.ajax({
        url: getContextPath() + "/rest/v1.0/apiManagement/zdMul",
        dataType: "json",
        data: {
            zdNames: dictionary
        },
        async: false,
        success: function (data) {
            var gxbmbz = data.gxbmbz;
            if(typeof gxbmbz == "undefined" || gxbmbz == null){
                return consumer;
            }
            for(var i = 0 ; i < gxbmbz.length ; i++){
                var data = {
                    "name":gxbmbz[i].MC,
                    "value":gxbmbz[i].DM
                };
                consumer.push(data);
            }
        },
        error: function (e) {}
    });
    return consumer;
}
/**
 * 转换字典值
 * @param key
 * @param dictionaryList
 * @returns {string|string}
 */
function convertConsumerDictionary(key,dictionaryList) {
    if (key) {
        for(var i = 0; i < dictionaryList.length; i++){
            var dictionary = dictionaryList[i];
            if (key === dictionary.value) {
                return dictionary.name;
            }
        }
    }
    return key;
}