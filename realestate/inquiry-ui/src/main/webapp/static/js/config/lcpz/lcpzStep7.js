/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 默认意见js
 */
layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response'});
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form','response'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        response = layui.response,
        form = layui.form;

    var gzldyid = $.getUrlParam('gzldyid');
    var qllx = $.getUrlParam('qllx');
    var djxl = $.getUrlParam('djxl');


    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    var tableData;


    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#djyyTable',
        toolbar: '#toolbar',
        title: '登记原因配置列表',
        defaultToolbar: ['filter'],
        url: BASE_URL+'/lcpz/djyy/page',
        where: {djxl: djxl,gzldyid:gzldyid},
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'xh', type: 'numbers', width: 60, title: '序号'},
            {field: 'id', title: 'id', hide: true},
            {field: 'djyy', title: '登记原因'},
            {field: 'djxl', title: '登记小类', hide: true},
            {field: 'sfmr', title: '是否默认', templet: '#sfTpl'},
            {title: '排序', fixed: 'right', width: 200, templet: '#barAction',}
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        parseData: function (res) {
            tableData = res.content;
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

            if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
            } else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 135);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 135 - 17);
            }
            $('.layui-table-main').css('overflow', 'hidden');
        }
    });

    //头工具栏事件
    table.on('toolbar(djyyTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addBdcdjyy(djxl);
                break;
            case 'edit':
                editBdcdjyy(checkStatus.data);
                break;
            case 'delete':
                deleteBdcdjyy(checkStatus.data);
                break;
        }
    });

    /**
     * 监听排序事件
     */
    table.on('sort(djyyTable)', function (obj) {
        table.reload('djyyTable', {
            initSort: obj
            , where: {
                field: obj.field //排序字段
                , order: obj.type //排序方式
            }
        });
    });



    /**
     * 重新加载数据
     */
    window.reload = function () {
        var obj = {'djxl':$('#djxl').val()};
        table.reload("djyyTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };

    /**
     * 新增
     */
    function addBdcdjyy(djxl) {
        addModel('');
        //小弹出层
        layer.open({
            title: '登记原因修改',
            type: 1,
            area: ['430px'],
            btn: ['提交', '取消'],
            content: $('#bdc-popup-small'),
            yes: function(index, layero){
                saveDjyy(index);
            },
            btn2: function(index, layero){
                //取消 的回调
                removeModal();
            },
            cancel: function(){
                //右上角关闭回调
                //return false 开启该代码可禁止点击该按钮关闭
                removeModal();
            },
            success: function () {
                //清空 gxid
                $("#gxid").val("");
                $("#sxh").val("");
                getReturnData("/rest/v1.0/lcpz/djyy/max/"+ djxl,{},"GET",function (data) {
                    $("#sxh").val(data+1);
                },function (error) {
                });
            }
        });
    }

    function saveDjyy(index) {
        var sfgllc =$('input[name="sfgllc"]:checked').val();
        var editgzldyid ="";
        if (sfgllc == 1) {
            editgzldyid = gzldyid;
        }
        var djyy={
            "djxl": djxl,
            "djyy": $('#djyy').val(),
            "sfmr": $('input[name="sfmr"]:checked').val(),
            "id": $('#gxid').val(),
             gzldyid: editgzldyid,
            "sxh": $("#sxh").val(),
        };
        var djyySearch={"djxl":djxl,"gzldyid":gzldyid};
        //提交 的回调
        $.ajax({
            url: BASE_URL + '/lcpz/djyy',
            type: "PUT",
            dataType: "json",
            contentType: 'application/json',
            data:JSON.stringify(djyy),
            success: function (data) {
                layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功',{
                    time: 1000}
                )
            },error: function (e) {
                delAjaxErrorMsg(e);
            },complete:function () {
                $('#djyy').val('');
                $('#gxid').val('');
                layer.close(index);
                removeModal();
                table.reload("djyyTable", {
                    where: djyySearch
                    , page: {
                        curr: 1  //重新从第 1 页开始
                    }
                });
            }
        });
    }
    /**
     * 编辑
     * @param data
     */
    function editBdcdjyy(data) {
        if (!data || data.length != 1) {
            layer.alert("请选择需要编辑的某一条记录！", {title: '提示'});
            return;
        }
        addModel('');
        var djyy = data[0];
        $('#gxid').val(djyy.id);
        $('#djyy').val(djyy.djyy);
        $('#sxh').val(djyy.sxh);

        // 渲染是否默认
        $('[name="sfmr"]').each(function (i, item) {
            if ($(item).val() == djyy.sfmr) {
                $(item).attr('checked', true);
            } else {
                $(item).removeAttr('checked');
            }
            form.render();
        });

        // 渲染是否关联流程
        if (!isNullOrEmpty(djyy.gzldyid)) {
            $('[name="sfgllc"][value="1"]').attr('checked', true);
            $('[name="sfgllc"][value="0"]').removeAttr('checked');
            form.render();
        } else {
            $('[name="sfgllc"][value="1"]').removeAttr('checked');
            $('[name="sfgllc"][value="0"]').attr('checked', true);
            form.render();
        }

        //小弹出层
        layer.open({
            title: '登记原因修改',
            type: 1,
            area: ['430px'],
            btn: ['提交', '取消'],
            content: $('#bdc-popup-small')
            , yes: function (index, layero) {

                //提交 的回调
                saveDjyy(index);
            }
            , btn2: function (index, layero) {
                //取消 的回调
                removeModal();
            }
            , cancel: function () {
                //右上角关闭回调
                //return false 开启该代码可禁止点击该按钮关闭
                removeModal();
            }
            , success: function () {

            }
        });
    }

    /**
     * 删除
     * @param data
     */
    function deleteBdcdjyy(data) {
        if (!data || data.length == 0) {
            layer.alert("请选择需要删除的记录！", {title: '提示'});
            return;
        }
        var djyySearch={"djxl":djxl,"gzldyid":gzldyid};
        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            skin: 'bdc-small-tips',
            content: '确定要删除此登记原因配置？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                $.ajax({
                    url: BASE_URL+"/lcpz/djyy",
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if (data && $.isNumeric(data) && data > 0) {
                            layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">删除成功',{
                                time: 1000}
                            );
                        } else {
                            layer.alert("删除失败，请重试！", {title: '提示'});
                        }
                    },
                    error: function () {
                        layer.alert("删除失败，请重试！", {title: '提示'});
                    },complete:function () {
                        table.reload("djyyTable", {
                            where: djyySearch
                            , page: {
                                curr: 1  //重新从第 1 页开始
                            }
                        });
                        layer.close(deleteIndex);
                    }
                });

            },
            btn2: function () {
                //取消
            }
        });

    }

    /**
     * 行双击编辑
     */
    table.on('rowDouble(djyyTable)', function (obj) {
        var array = new Array();
        array.push(obj.data);
        editBdcdjyy(array);
    });
    $('.nextstep').on('click',function () {
        window.location.href='step8.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })

    $('.beforestep').on('click',function () {
        window.location.href='step6.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })

    // 详细信息查看
    table.on('tool(djyyTable)', function (obj) {
        switch(obj.event){
            case "up":
                up(obj.data);
                break;
            case "down":
                down(obj.data);
                break;
        }
    });

    /**
     * 顺序向前
     * 获取前一条数据的顺序号,与当前数据顺序号进行交换
     */
    function up(data){
        var currentDataSxh = data.sxh;
        var djyyList = [];
        if(currentDataSxh == 1){
            return;
        }
        // 分页 数据第11条数据,向前排序时,需要获取到第10条数据,然后进行两条数据顺序交换
        if(currentDataSxh%10 == 1){
            queryDjyy(data.djxl, currentDataSxh-1).done(function(node){
                data.sxh = node.sxh;
                node.sxh = currentDataSxh;
                djyyList.push(data);
                djyyList.push(node);
                changeSxh(djyyList);
            }).fail(function(){
                data.sxh = currentDataSxh -1;
                djyyList.push(data);
                changeSxh(djyyList);
            });
        }else{
            // 获取前一条数据顺序,进行顺序号交换
            var preData = tableData[currentDataSxh-2];
            data.sxh = preData.sxh;
            preData.sxh = currentDataSxh;
            djyyList.push(preData);
            djyyList.push(data);
            changeSxh(djyyList);
        }
    }

    /**
     * 顺序向后
     * 获取后一条数据的顺序号,与当前数据顺序号进行交换
     */
    function down(data){
        var currentDataSxh = data.sxh;
        var nextData = tableData[currentDataSxh];
        var djyyList = [];
        if(isNotBlank(nextData)){
            data.sxh = nextData.sxh;
            nextData.sxh = currentDataSxh;
            djyyList.push(nextData);
            djyyList.push(data);
            changeSxh(djyyList);
        }else{
            queryDjyy(data.djxl, currentDataSxh+1).done(function(node){
                data.sxh = node.sxh;
                node.sxh = currentDataSxh;
                djyyList.push(data);
                djyyList.push(node);
                changeSxh(djyyList);
            }).fail(function(){
                data.sxh = currentDataSxh + 1;
                djyyList.push(data);
                changeSxh(djyyList);
            })
        }
    }

    // 查询登记原因配置
    function queryDjyy(djxl, sxh){
        var deferred = $.Deferred();
        $.ajax({
            url: BASE_URL + '/lcpz/djyy',
            async: true,
            type: "GET",
            dataType: "json",
            data: {djxl: djxl, sxh: sxh},
            success: function (data) {
                if(isNotBlank(data)){
                    deferred.resolve(data[0]);
                }else{
                    deferred.reject();
                }
            },
            error: function (){
                deferred.reject();
            }
        });
        return deferred;
    }

    // 更改顺序号
    function changeSxh(djyyList){
        addModel();
        $.ajax({
            url: BASE_URL + '/lcpz/djyy/pl',
            type: "PUT",
            dataType: "json",
            contentType: 'application/json',
            data:JSON.stringify(djyyList),
            success: function (data) {
                removeModal();
                table.reload("djyyTable", {
                    where: {"djxl":djxl, "gzldyid":gzldyid}
                    , page: {
                        curr: 1  //重新从第 1 页开始
                    }
                });
            },
            error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
    }
});