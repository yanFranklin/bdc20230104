/**
 * Created by Ypp on 2019/10/10.
 * 对比表格js
 */
layui.use(['table','laytpl','layer','form'],function () {
    var table = layui.table,
        $ = layui.jquery,
        layer = layui.layer,
        form =layui.form;

    $(function () {
        addModel();
        var currentData=[];
        $.ajax({
            url: getContextPath() + "/synlpb/dzxx",
            type: 'GET',
            dataType: 'json',
            data: {xmid:xmid},
            success: function (data) {
                if (data) {
                    currentData=data;
                    table.render({
                        id:'lpbdzTable',
                        elem: '#pageTable',
                        height: 'full-111',
                        title: '对比表格',
                        even: true,
                        limit: 3000,
                        cols: [[
                            {type: 'checkbox', width:50, fixed: 'left'},
                            {field:'fieldDesc', title:'字段名'},
                            {field:'field', title:'字段', width: 0,hide:true},
                            {field:'sfxd', title:'是否相等', width: 0,hide:true},
                            {field:'sourceVal', title:'登记数据'},
                            {field:'targetVal', title:'权籍数据'}
                        ]],
                        data: data,
                        done: function () {
                            removeModal();
                            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                            if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                                $('.layui-table-body .layui-none').html('<img src="../image/table-none.png" alt="">无数据');
                            }else if($('.layui-table-main.layui-table-body>table').height() < $('.layui-table-main.layui-table-body').height()) {
                                $('.layui-table-main.layui-table-body').height($('.layui-table-main.layui-table-body>table').height());
                                $('.layui-table-view').height($('.layui-table-main.layui-table-body>table').height() + 88);
                            }
                        }
                    });
                }
            },
            error: function (err) {
                delAjaxErrorMsg(err);
            }
        });
        //获取权限
        getStateById(readOnly, formStateId, 'contractLpb');
        //保存工具栏
        table.on('toolbar(rlTableFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var selectedNum = checkStatus.data.length;
            if (selectedNum == 0) {
                layer.msg('请选择一条数据进行操作！');
                return false;
            }
            switch (obj.event) {
                case 'claim-process':
                    workflow.claimProcess(checkStatus, rlUrl, rlTableId, rlCurrentId,false);
                    break;
            }
        });

        form.on('switch(switchXt)', function(data){
            var dealData=[];
            if(data.elem.checked){
                currentData.forEach(function (val,index) {
                    if(val.sfxd!=1){
                        dealData.push(val);
                    }
                })
            }else{
                dealData=currentData;
            }
            table.reload('lpbdzTable', {
                data: dealData
            });
        });


        $("#saveQjxx").click(function(){
            addModel();
            //合肥 规则验证，验证权籍数据是否可以同步到不动产
            tbGzyz();

        })

    });

    function saveQjxx() {
        var checkStatus = table.checkStatus('lpbdzTable');
        if(checkStatus.data.length==0){
            layer.msg('请选择要对照的数据!');
            removeModal();
        }else{
            var dealData=[];
            $.each(checkStatus.data, function (key, item) {
                dealData.push(item);
            });
            $.ajax({
                type: "POST",
                url: getContextPath() + "/synlpb/tbdzxx/"+xmid,
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: JSON.stringify(dealData),
                success: function (data) {
                    layer.msg('保存成功，即将关闭当前页。');
                    setTimeout(function(){ window.closeWin(); }, 1000);
                    removeModal();
                }, error: function (e) {
                    delAjaxErrorMsg(e);
                }
            });
        }
    }

    function tbGzyz(){
        var bdcdyh = getQueryString("bdcdyh");
        if (isNullOrEmpty(bdcdyh)) {
            saveQjxx();
            return;
        }
        var bdcdyhList = [];
        bdcdyhList.push(bdcdyh);
        var sftb = true;
        var paramMap = {};
        paramMap.bdcdyhList = bdcdyhList;
        var bdcGzYzQO = {};
        bdcGzYzQO.zhbs = "XYZQJTBSJ";
        bdcGzYzQO.paramMap = paramMap;

        gzyzCommon(2,bdcGzYzQO,function (data) {
            console.log("验证通过");
            saveQjxx();
        });
    }
    //合肥 规则验证，验证权籍数据是否可以同步到不动产
    function tbqjGzyz() {
        var bdcdyh = getQueryString("bdcdyh");
        if (isNullOrEmpty(bdcdyh)) {
            return true;
        }
        var bdcdyhList = [];
        bdcdyhList.push(bdcdyh);
        var sftb = true;
        var paramMap = {};
        paramMap.bdcdyhList = bdcdyhList;
        var bdcGzYzQO = {};
        bdcGzYzQO.zhbs = "XYZQJTBSJ";
        bdcGzYzQO.paramMap = paramMap;

        $.ajax({
            url: getContextPath() + '/bdcGzyz/qtyz',
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(bdcGzYzQO),
            async: false,
            success: function (tsdata) {
                if (tsdata.length > 0) {
                    sftb = false;
                }

            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr);
                removeModal();
            }
        });
        return sftb;
    }

});