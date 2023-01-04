/**
 * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @description 自定义查询的解封编辑按钮事件
 */

// 解封项目ids
var jfxmids= "";
var jfbdcdyhs = "";
layui.use(['form', 'jquery', 'element', 'table', 'laydate'], function () {
    var laydate = layui.laydate;
    var form = layui.form;
    $(function(){
        lay('.test-item').each(function() {
            laydate.render({
                elem: '#jfsj',
                type: 'datetime',
                format: 'yyyy-MM-dd HH:mm:ss'
            });

            laydate.render({
                elem: '#beginTime',
                type: 'datetime',
                format: 'yyyy-MM-dd HH:mm:ss',
                // value: preDate,
                done: function (value, date, endDate) {
                    var startDate = new Date(value).getTime();
                    var endTime = new Date($('#endTime').val()).getTime();
                    if (endTime < startDate) {
                        layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                    }
                }
            });
            laydate.render({
                elem: '#endTime',
                type: 'datetime',
                format: 'yyyy-MM-dd HH:mm:ss',
                // value: preDate,
                done: function (value, date, endDate) {
                    var endTime = new Date(value).getTime();
                    var startDate = new Date($('#beginTime').val()).getTime();
                    if (endTime < startDate) {
                        layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                    }
                }
            });
        })
    })

    /**
     * 表单数据提交,编辑
     */
    form.on('submit(submitBtnEdit)', function(data) {
        var obj = {};
        if($("input[name='beginTime']").val() == ""){
            layer.alert('请填写查封起始时间！', {title: '提示'});
            return false;
        }
        if($("input[name='endTime']").val() ==  ""){
            layer.alert('请填写查封结束时间！', {title: '提示'});
            return false;
        }
        obj['cfqssj'] = data.field.beginTime;
        obj['cfjssj'] = data.field.endTime;
        obj['fj'] = data.field.fj;
        obj['xmid'] = data.field.xmid;

        $.ajax({
            url: "/realestate-inquiry-ui/cfxx/edit",
            data: obj,
            dataType: "json",
            success: function (data) {
                layer.alert('更新成功，请刷新列表！', {title: '提示'});
                setTimeout(function(){parent.layer.closeAll()},2000);
            }
        });
        return false;
    });
    /**
     * 表单数据提交，解封表单
     */
    form.on('submit(submitBtn)', function(data) {
        var obj = {};
        if($("input[name='jfsj']").val() == ""){
            layer.alert('请填写失效时间！', {title: '提示'});
            return false;
        }
        if($("input[name='jfyy']").val() == ""){
            layer.alert('请填写失效原因！', {title: '提示'});
            return false;
        }
        obj['jfyy'] = data.field.jfyy;
        obj['jfsj'] = data.field.jfsj;
        obj['xmid'] = parent.jfxmids;
        obj['bdcdyh'] = parent.jfbdcdyhs;
        $.ajax({
            url: "/realestate-inquiry-ui/cfxx/jf",
            data: obj,
            dataType: "json",
            success: function (data) {
                parent.layer.alert('失效成功，刷新列表！', {title: '提示'});
                setTimeout(function(){
                    parent.layer.closeAll();
                    parent.tableReload('pageTable', {data: JSON.stringify(parent.queryObject)}, parent.dataUrl);
                },1000);
            }
        });
        return false;
    });
})


function opendialog(obj,data){
    var xmids = "";
    var bdcdyhs = "";
    if(obj && data){//这两个参数有值 说明是单个解封

        var checkData = data;

        if(checkData.SFCQ == "0"){// 未超期
            layer.alert('还未到解封时间，不能失效！', {title: '提示'});
            return;
        }
        if(checkData.QSZT != "1"){// 不是现势的
            layer.alert('非现势的状态，不能失效！', {title: '提示'});
            return;
        }
        xmids = checkData.XMID;
        bdcdyhs = checkData.BDCDYH;
    }else{// 参数没有值，说明是勾选的批量解封
        if(checkeddata.length == 0){
            layer.alert('请勾选需要失效的数据！', {title: '提示'});
            return;
        }
        // 判断批量解封的查封文号是否一致，是否都到期，是否都是现势状态
        var cfwhObj = {};
        for(var i=0;i<checkeddata.length;i++){
            if(cfwhObj.cfwh!= checkeddata[i].CFWH && i!= 0){
                layer.alert('勾选的数据查封文号不一致，请检查！', {title: '提示'});
                return;
            }
            if(checkeddata[i].SFCQ == '0'){
                layer.alert('勾选的数据含有未到解封时间的，请检查！', {title: '提示'});
                return;
            }
            if(checkeddata[i].QSZT != '1'){
                layer.alert('勾选的数据含有非现势状态的，请检查！', {title: '提示'});
                return;
            }
            cfwhObj['cfwh'] = checkeddata[i].CFWH;
            xmids+=checkeddata[i].XMID+",";
            bdcdyhs+=checkeddata[i].BDCDYH+",";
        }
        xmids = xmids.substring(0,xmids.length-1);
        bdcdyhs = bdcdyhs.substring(0,bdcdyhs.length-1);
    }
    jfxmids = xmids;
    jfbdcdyhs = bdcdyhs;
    console.log (bdcdyhs);
    var count = queryWdqXfByBdcyhs(bdcdyhs,xmids);
    if(count > 0){
        layer.alert('还未到解封时间，不能失效！', {title: '提示'});
        return;
    }

    layer.open({
        type: 2,
        title: '失效信息',
        anim: -1,
        shadeClose: true,
        maxmin: true,
        shade: false,
        area: ['430px', '400px'],
        offset: 'auto',
        content: [ "jfInfo.html", 'yes'],
        end:function(){
        }
    });
}

function closeWin(){
    parent.layer.closeAll();
}

function edit(obj,data){
    var checkData = data;
    if(checkData.QSZT != "1"){// 不是现势的
        layer.alert('非现势的状态，不能编辑！', {title: '提示'});
        return;
    }

    var cfqqsj = checkData.CFQSSJ;
    var cfjssj = checkData.CFJSSJ;
    var fj = checkData.FJ;
    var cflx = checkData.CFLX;

    layer.open({
        type: 2,
        title: '编辑查封信息',
        anim: -1,
        shadeClose: true,
        maxmin: true,
        shade: false,
        area: ['430px', '440px'],
        offset: 'auto',
        content: [ "editInfo.html", 'yes'],
        success: function (layero, index) {
            if(cfqqsj !="" && cfqqsj !="null" && cfqqsj!=null){
                frames[0].$("#beginTime").val(cfqqsj+" 00:00:00");
            }
            if(cfjssj !="" && cfjssj !="null" && cfjssj!=null){
                frames[0].$("#endTime").val(cfjssj+" 00:00:00");
            }
            frames[0].$("#fj").val(fj);
            frames[0].$("#xmid").val(checkData.XMID);
            if(cflx != "2"){// 不是轮候查封的，只允许编辑附记
                frames[0].$("#beginTime").attr("disabled","disabled");
                frames[0].$("#endTime").attr("disabled","disabled");
            }
        },
        end:function(){
        }
    });

}

/**
 * 通过bdcdyh查询未到期的续封信息
 * @param bdcdyhs
 */
function queryWdqXfByBdcyhs(bdcdyhs,xmids){
    var count = 0;
    $.ajax({
        url: '/realestate-inquiry-ui/cfxx/queryWdqXfByBdcyhs',
        data: {"bdcdyhs":bdcdyhs,xmids:xmids},
        dataType: "json",
        async: false,
        success: function (data) {
            count = parseInt(data);
        },
        error: function (e) {
           return 0;
        }
    });
    return count;
}

/*
function jf(obj,data,xmids){
    if(!xmids){
        var checkData = data;
        if(checkData.SFCQ == "0"){// 未超期
            layer.alert('还未到解封时间，不能解封！', {title: '提示'});
            return;
        }
        if(checkData.QSZT != "1"){// 不是现势的
            layer.alert('非现势的状态，不能解封！', {title: '提示'});
            return;
        }
    }

    xmids = xmids?xmids:checkData.XMID;
    addModel();
    $.ajax({
        url: '/realestate-inquiry-ui/cfxx/jf',
        data: {"xmid":xmids},
        dataType: "json",
        success: function (data) {
            removeModal();
            layer.alert('解封成功，请刷新列表！', {title: '提示'});
        },
        error: function (e) {
            removeModal();
            layer.alert('解封失败，请新建解封登记流程进行解封！', {title: '提示'});
        }
    });
}

function batchJf(){
    if(checkeddata.length == 0){
        layer.alert('请勾选需要解封的数据！', {title: '提示'});
        return;
    }
    var xmids = '';
    var cfwhObj = {};
    for(var i=0;i<checkeddata.length;i++){
        if(cfwhObj.cfwh!= checkeddata[i].CFWH && i!= 0){
            layer.alert('勾选的数据查封文号不一致，请检查！', {title: '提示'});
            return;
        }
        if(checkeddata[i].SFCQ == '0'){
            layer.alert('勾选的数据含有未到解封时间的，请检查！', {title: '提示'});
            return;
        }
        if(checkeddata[i].QSZT != '1'){
            layer.alert('勾选的数据含有非现势状态的，请检查！', {title: '提示'});
            return;
        }
        cfwhObj['cfwh'] = checkeddata[i].CFWH;
        xmids+=checkeddata[i].XMID+",";
    }
    xmids = xmids.substring(0,xmids.length-1);

    jf(null,null,xmids);
}*/