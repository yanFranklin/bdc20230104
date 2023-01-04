/**
 * 常州：挂接上下手业务
 */

function gjsxs(obj, data) {
    if(!data || !data.XMID) {
        layer.msg('为获取项目ID信息，无法进行挂接。');
        return;
    }
    checkCurrentXmid(data.XMID).done(function(res){
        // 判断当前项目是否存在上一手产权信息
        if(isNotBlank(res.parentXm) && res.parentXm.length > 0){
            layer.confirm('当前项目存在上下手项目信息，是否继续关联', function (index) {
                layer.close(index);
                gjxm(data.XMID);
            });
        }else{
            gjxm(data.XMID);
        }
    });
}

function gjxm(xmid){
    var url = "/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=lscqxx";
    var index = layer.open({
        type: 2,
        title: "挂接历史产权信息",
        area: ['960px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: url,
        btn:['确定','取消'],
        yes:function(index, layero){
            var iframeWin = window[layero.find('iframe')[0]['name']];
            var lsCheckedData= iframeWin.layui.table.checkStatus('pageTable');
            console.info(lsCheckedData);
            if(isNullOrEmpty(lsCheckedData) || lsCheckedData.data.length ==  0 ||lsCheckedData.data.length > 1){
                warnMsg("请选择一条进行关联");
                return false;
            }
            var yxmid = lsCheckedData.data[0].XMID;

            // 验证当前项目是否存在下一手产权信息
            checkCurrentXmid(yxmid).done(function(res){
                if(isNotBlank(res.sonXm) && res.sonXm.length > 0){
                    layer.confirm('当前项目存在上下手项目信息，是否继续关联', function (index) {
                        layer.close(index);
                        addBdcXmLsgx(xmid, yxmid);
                    });
                }else{
                    addBdcXmLsgx(xmid, yxmid);
                }
                layer.close(index);
            });
            layer.close(index);
        }
    });
    layer.full(index);
}

// 校验当前产权是否关联上下手关系
function checkCurrentXmid(xmid){
    var deferred = $.Deferred();
    addModel();
    $.ajax({
        url: getContextPath() + "/history/xmlsgx",
        data: {"xmid": xmid},
        type: "GET",
        dataType: "json",
        success: function (data) {
            removeModal();
            deferred.resolve(data);
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
            deferred.reject();
        }
    });
    return deferred;
}

// 添加项目历史关系
function addBdcXmLsgx(xmid, yxmid){
    addModel();
    $.ajax({
        url: getContextPath() + "/history/sc/lsgx",
        data: {"xmid": xmid, "yxmid": yxmid},
        type: "GET",
        dataType: "json",
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("挂接成功");
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}