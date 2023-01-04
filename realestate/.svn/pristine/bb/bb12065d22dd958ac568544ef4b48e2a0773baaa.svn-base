var isOneWebSource = $.getUrlParam('isOneWebSource');
layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'laytpl'], function() {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laytpl = layui.laytpl;
    $('.bdc-search-content').hide();

    $('.bdc-percentage-container').css({"padding-top": "10px"});
    var taskId = $.getUrlParam('taskId');
    var processInsId = $.getUrlParam('processInsId');

    // potal首页点击的哪个列表todo:待办，list:项目列表，done:已办
    var processInstanceTypeDone = "done";
    var processInstanceTypelist = "list";
    var jsonParam = {};
    if(parent.sessionStorage && (parent.sessionStorage.getItem("lcArray"+taskId)
            || parent.sessionStorage.getItem("listArray"+processInsId))){
        jsonParam = JSON.parse(parent.sessionStorage.getItem("lcArray"+taskId));
        if(jsonParam == null){
            jsonParam = JSON.parse(parent.sessionStorage.getItem("listArray"+processInsId));
        }
    }
    // 如果是已办和项目列表 则不能点击推送过户按钮
    if (jsonParam && (jsonParam.processInstanceType == processInstanceTypeDone
            || jsonParam.processInstanceType == processInstanceTypelist)) {
        $(".layui-table-box").find('button').attr({"disabled":"disabled"})
    }
});

/**
 * 推送过户
 * @param obj
 * @param data
 */
function tsgh(obj, data){
   if(data != null){
       console.log(data);
       if(data.BLZT == 3){
           layer.alert('该状态不能推送！', {title: '提示'});
           return false;
       }
       var url = "";
       var blyw = data.BLYW;
       var blywlx = "";
       if("自来水" == blyw){
           blywlx = "1";
       }else if("电" == blyw){
           blywlx = "2";
       }else{
           blywlx = "3";
       }
       url = '/realestate-inquiry-ui/sdqgh/tsGh/'+data.GZLSLID+"/"+blywlx+"/"+isOneWebSource;
       addModel();
       $.ajax({
           url: url,
           type: 'get',
           success: function (data) {
               console.log("过户返回值："+data);
               removeModal();
               if(data !=null && data == true){
                   layer.alert('推送业务成功！', {title: '提示'});
               }else{
                   layer.alert('推送业务失败，请检查日志！', {title: '提示'});
               }
               $('#search').click();
           },
           error: function (e) {
               removeModal();
               layer.alert('推送业务失败，系统异常！', {title: '提示'});
           }
       });
   }else{
       layer.alert('当前数据异常！', {title: '提示'});
   }
}


/**
 * 通过查找当前项目下所有的权利人
 * @param gzlslid
 */
function queryQlrByGzlslid(gzlslid){
    var list = [];
    $.ajax({
        url: getContextPath()+"/slym/sdqgh/getQlrlist/"+gzlslid,
        type: 'GET',
        async: false,
        success: function (data) {
            list = data;
        }
    });
    if(list.length == 0){
        ityzl_SHOW_INFO_LAYER("该流程还未保存权利人，请前往受理页面添加权利人！")
    }
    xrQlrlist(list);
    return list;
}


/**
 * 状态查询
 * @param obj
 * @param data
 */
function ztcx(obj, data){
    if(data != null){
        console.log(data);
        var blyw = data.BLYW;
        if("电" != blyw){
            layer.alert('非"电"业务,不能查询！', {title: '提示'});
            return;
        }
        url = '/realestate-inquiry-ui/sdqgh/ztcx/'+data.CONSNO+'/'+data.GZLSLID;
        addModel();
        $.ajax({
            url: url,
            type: 'get',
            success: function (data) {
                console.log("状态查询返回值："+data);
                if(data ){
                    layer.alert('业务办理成功，请刷新页面！', {title: '提示'});
                }else{
                    layer.alert('业务还在办理中，请等候！', {title: '提示'});
                }
                removeModal();
            },
            error: function (e) {
                removeModal();
                layer.alert('状态查询业务失败，系统异常！', {title: '提示'});
            }
        });
    }else{
        layer.alert('当前数据异常！', {title: '提示'});
    }
}

