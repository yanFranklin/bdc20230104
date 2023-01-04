/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 发票号 列表
 */
layui.use(['table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;

    /**
     * 查询区县代码字典
     */
    var xzqhData;
    $.ajax({
        url: getContextPath() + '/rest/v1.0/zsbhmb/zd/qxdm',
        type: "GET",
        dataType: "json",
        async: false,
        timeout : 10000,
        success: function(data) {
            if(data && data.length > 0){
                xzqhData = data;
                //没渲染之前第一个选项已经有值（不是 请选择 选项）
                //说明是南通的html，则不作任务处理
                // 渲染字典项
                $.each(data, function (index, item) {
                    $('#qxdm').append('<option value="' + item.XZDM + '">' + item.XZMC + '（' + item.XZDM + '）' + '</option>');
                });
            }
            form.render('select');
        }
    });
    /**
     * 获取字典
     */
    var zdData;
    $.ajax({
        url: getContextPath() + '/rest/v1.0/zsyzh/zd',
        type: "GET",
        async: false,
        dataType: "json",
        timeout : 10000,
        success: function(data) {
            zdData = data;

            if(data){
                if(data.zslx){
                    $.each(data.zslx, function(index,item) {
                        $('#zslx').append('<option value="'+item.DM +'">' + item.MC + '</option>');
                    });
                }

                if(data.zssyqk){
                    $.each(data.zssyqk, function(index,item) {
                        $('#syqk').append('<option value="'+item.DM +'">' + item.MC + '</option>');
                    });
                }
                if(data.fplx) {
                    $.each(data.fplx, function(index,item) {
                        $('#fplx').append('<option value="'+item.DM +'">' + item.MC + '</option>');
                    });
                }
                form.render('select');

                // 下拉选择添加删除按钮
                renderSelectClose(form);
            }
        }
    });

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#fphTable',
        toolbar: '#toolbar',
        title: '发票号模板列表',
        defaultToolbar: ['filter'],
        url: getContextPath() + "/rest/v1.0/fph/page",
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {minWidth:80,  sort:true,  field:'nf',    title:'年份' },
            {minWidth:100, sort:true,  field:'qxdm',  title:'行政区划',
                templet: function(d){
                    if(xzqhData){
                        for(var index in xzqhData){
                            if(xzqhData[index].XZDM == d.qxdm){
                                return xzqhData[index].XZMC;
                            }
                        }
                    } else {
                        return d.qxdm;
                    }
                }
            },
            {minWidth:200, sort:true,  field:'fph', title:'发票号'},
            {minWidth:100, sort:false, field:'syqk',  title:'使用情况',
                templet: function(d){
                    if(zdData && zdData.zssyqk){
                        for(var index in zdData.zssyqk){
                            if(zdData.zssyqk[index].DM == d.syqk){
                                if(2 == d.syqk){
                                    return '<span style="color: red">' + zdData.zssyqk[index].MC + '</span>';
                                }
                                if(6 == d.syqk){
                                    return '<span style="color: green">' + zdData.zssyqk[index].MC + '</span>';
                                }
                                return zdData.zssyqk[index].MC;
                            }
                        }
                    } else {
                        return d.syqk;
                    }
                }
            },
            {minWidth:150, sort:false, field:'slbh',  title:'受理编号'},
            {minWidth:200, sort:true,  field:'lqbm',  title:'领取部门'},
            {minWidth:150, sort:true,  field:'lqr',   title:'领取人'},
            {minWidth:150, field:'syr', title:'使用人'},
            {minWidth:150, sort:false, field:'cjr',   title:'创建人'},
            {minWidth:150, sort:false, field:'zzjfbs',   title:'自助机可用',
                templet: function(d){
                    if(1 == d.zzjfbs){
                        return '是';
                    }else{
                        return '否';
                    }
                }
            },
            {minWidth:200, sort:false, field:'cjsj',  title:'创建时间',
                templet: function(d){
                    return format(d.cjsj);
                }
            },
            {minWidth:200, sort:false, field:'sysj',  title:'使用时间',
                templet: function(d){
                    return format(d.sysj);
                }
            },
            {minWidth:200, templet:'#barAction', title:'操作', fixed: 'right'}
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
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
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

            if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
            }else {
                $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });

    //头工具栏事件
    table.on('toolbar(fphTable)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'add':
                addFph();
                break;
            case 'edit':
                editFph(checkStatus.data);
                break;
            case 'delete':
                deleteFph(checkStatus.data);
                break;
            case 'example':
                showExample();
                break;
            case 'syqkedit':
                showSyqkEdit(checkStatus.data);
                break;
        };
    });

    //监听工具条
    table.on('tool(fphTable)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            delFph(data);
        }
        if(obj.event === 'symx') {
            viewFphSymx(data.fphid);
        }
    });

    /**
     * 行双击编辑
     */
    table.on('rowDouble(fphTable)', function(obj){
        var array = new Array();
        array.push(obj.data);
        editFph(array);
    });

    /**
     * 监听排序事件
     */
    table.on('sort(fphTable)', function(obj){
        table.reload('fphTable', {
            initSort: obj
            ,where: {
                field: obj.field //排序字段
                ,order: obj.type //排序方式
            }
        });

    });

    /**
     * 新增发票号
     */
    function addFph(){
        layer.open({
            type: 2,
            title: '新增发票号',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '400px'],
            offset: 'auto',
            content: [ "addFph.html", 'yes'],
            end:function(){
                table.reload('fphTable');
            }
        });
    }

    /**
     * 编辑发票号
     */
    function editFph(data){
        if(!data || data.length != 1){
            alertMsg("请选择需要编辑的某一条记录！");
            return;
        }

        layer.open({
            type: 2,
            title: '编辑发票号',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '400px'],
            offset: 'auto',
            content: [ "editFph.html", 'yes'],
            success: function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];
                iframe.setData(data[0]);
            },
            end:function(){
                table.reload('fphTable');
            }
        });
    }

    /**
     * 显示示例
     */
    function showExample(){
        layer.open({
            title: '发票号示例',
            type: 2,
            area: ['960px','500px'],
            content: 'fphsl.html'
        });
    }

    /**
     * 显示修改使用情况
     */
    function showSyqkEdit(data){
        if(!data || data.length < 1){
            alertMsg("请选择需要编辑的记录！");
            return;
        }
        var div = " <div id=\"bdc-popup-textarea\"><form class=\"layui-form\" action=\"\">" +
            "            <div class=\"layui-form-item pf-form-item\">" +
            "                <div class=\"layui-inline\">" +
            "                    <label class=\"layui-form-label\">使用情况</label>" +
            "                    <div class=\"layui-input-inline\">" +
            "                        <select name=\"syqkvalue\" id='syqkvalue'>" +
            "                           <option value=\"6\">已领用</option>" +
            "                           <option value=\"3\">已使用</option>" +
            "                           <option value=\"2\">作废</option>" +
            "                        </select>" +
            "                    </div>" +
            "                </div>" +
            "            </div>" +
            "        </form>" +
            "    </div>";
        layer.open({
            title: '使用状况修改',
            type: 1,
            area: ['430px', '300px'],
            btn: ['提交', '取消'],
            content: div
            , yes: function (index, layero) {
                //提交 的回调
                saveSyqk(index, data);
            }
            , btn2: function (index, layero) {
                //取消 的回调
                layer.close(index)
            }
            , cancel: function (index) {
                //右上角关闭回调
                layer.close(index)
            }
            , success: function () {
                form.render('select');
            }
        });
    }

    /*
     * 修改使用情况
     */
    function saveSyqk(index, data) {
        var syqkArr = new Array();
        $.each(data, function (index, item) {
            var syqkObj = new Object();
            syqkObj.fph = item["fph"];
            syqkObj.fphid = item["fphid"];
            syqkObj.syqk = $('#syqkvalue[name=syqkvalue]').val();//$('#syqkvalue').val();
            syqkArr.push(syqkObj);
        });

        $.ajax({
            url: getContextPath() + "/rest/v1.0/fph/syqkedit",
            type: "POST",
            data: JSON.stringify(syqkArr),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if(data == 0 || data != syqkArr.length){
                    warnMsg("发票号使用情况更新失败");
                }else{
                    saveSuccessMsg();
                }
            },
            error: function (e) {
                saveFailMsg();
            }, complete: function () {
                $('#syqk').val('');
                layer.close(index);
                parent.layui.table.reload('fphTable', {page: {curr: 1}});
                parent.layer.close(index);
            }
        });
    }

    /**
     * 删除发票号
     */
    function deleteFph(data){
        if(!data || data.length == 0){
            alertMsg("请选择需要删除的记录！");
            return;
        }

        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            skin: 'bdc-small-tips',
            content: '确定要删除所选发票号？',
            btn: ['确定','取消'],
            btnAlign: 'c',
            yes: function(){
                $.ajax({
                    url: getContextPath() + "/rest/v1.0/fph",
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if(data && $.isNumeric(data) && data > 0){
                            delSuccessMsg();
                            reload();
                        } else {
                            delFailMsg();
                        }
                    },
                    error:function(){
                        delFailMsg();
                    }
                });
                layer.close(deleteIndex);
            },
            btn2: function(){
                //取消
            }
        });
    }

    /**
     * 作废发票号
     */
    function delFph(data){
        if(!data){
            alertMsg("请选择需要作废的记录！");
            return;
        }

        layer.open({
            title: '作废印制号',
            type: 2,
            area: ['430px','220px'],
            content: 'zfFph.html',
            success: function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];
                iframe.setData(data);
            },
            end:function(){
                table.reload('fphTable');
            }
        });
    }

    /*
    * 查看发票号使用明细
    * */
    function viewFphSymx(fphid) {
        addModel();
        $.ajax({
            url: getContextPath() + "/rest/v1.0/fph/" + fphid + "/symx",
            type: "GET",
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data) {
                    layer.open({
                        type: 1,
                        title: "发票号使用明细",
                        content: $('#popup'),
                        area: ['985px', '500px'],
                        cancel: function () {
                            $("#popup").css('display', 'none');
                        },
                        success: function (layero, index) {
                            viewTableRender(data);
                        }
                    });
                }
            }, error: function () {
                alertMsg("查询异常！");
            }, complete: function () {
                removeModal();
            }
        });
    }

    /**
     * 发票号使用明细
     */
    function viewTableRender(symxData) {
        table.render({
            elem: '#viewTable',
            toolbar: '#toolbarDemo',
            id: 'test1',
            title: '发票号使用明细',
            defaultToolbar: [],
            cols: [[
                {type: 'xh', fixed: 'left', width: 60},
                {field: 'slbh', title: '受理编号', width: 112},
                {field: 'sybmmc', title: '使用部门名称', width: 130},
                {field: 'syr', title: '使用人', width: 110},
                {field: 'sysj', title: '使用时间', width: 200},
                {field: 'syyy', title: '使用原因', width: 250},
                {
                    field: 'syqk', title: '使用情况', width: 95,
                    templet: function (d) {
                        if (zdData && zdData.zssyqk) {
                            for (var index in zdData.zssyqk) {
                                if (zdData.zssyqk[index].DM == d.syqk) {
                                    if (2 == d.syqk) {
                                        return '<span style="color: red">' + zdData.zssyqk[index].MC + '</span>';
                                    } else {
                                        return zdData.zssyqk[index].MC;
                                    }
                                }
                            }
                        } else {
                            return d.syqk;
                        }
                    }
                }
            ]],
            data: symxData,
            page: false,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            }
        });
    }

    /**
     * 点击查询
     */
    $('#search').on('click',function () {
        //这里处理下因为下拉树框数据清除但是隐藏ID值未清理问题
        if(isNullOrEmpty($("input[name='lqbm']").val())){
            $("input[name='lqbmid']").val(null);
        }
        if(isNullOrEmpty($("input[name='lqr']").val())){
            $("input[name='lqrid']").val(null);
        }

        // 获取查询内容
        var obj = {};
        $(".search").each(function(i){
            var value= $(this).val();
            var name= $(this).attr('name');
            obj[name]=value;
        });
        // 重新请求
        table.reload("fphTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });

    //点击高级查询--4的倍数
    $('#seniorSearch').on('click',function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        $(this).parent().toggleClass('bdc-button-box-four');

        if ($(this).html() == '高级查询') {
            $(this).html('收起');
        } else {
            $(this).html('高级查询');
        }

        $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);
        $('.layui-table-body').height($('.bdc-table-box').height() - 129);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 129 - 17);
    });


    /**
     * 重新加载数据
     */
    window.reload = function(){
        // 获取查询内容
        var obj = {};
        $(".search").each(function(i){
            var value= $(this).val();
            var name= $(this).attr('name');
            obj[name]=value;
        });

        table.reload("fphTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };
});