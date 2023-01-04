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
       var type = "POST";
       var blyw = data.BLYW;
       if("自来水" == blyw){
           url="/realestate-inquiry-ui/sdqgh/shui/tsgh/shucheng"+"?gzlslid="+data.GZLSLID+"&userId="+data.CONSNO;
           type = "GET";
       }else if("电" == blyw){
           url = '/realestate-inquiry-ui/sdqgh/dian/tsgh/bc/'+data.GZLSLID;
       }else{
           url="";
       }
      if(!isNotBlank(url)){
          warnMsg("暂不支持当前业务");
          return false;
      }
       addModel();
       $.ajax({
           url: url,
           type: type,
           success: function (data) {
               removeModal();
               if(data.success){
                   ityzl_SHOW_SUCCESS_LAYER("推送成功");
               }else{
                   warnMsg(data.fail != null &&isNotBlank(data.fail.message) ?data.fail.message:"推送失败");
               }
               $('#search').click();
           },
           error: function (e) {
               removeModal();
               delAjaxErrorMsg(e);
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
        var url = '/realestate-inquiry-ui/sdqgh/dian/ghblzt/'+data.GZLSLID+'/'+data.CONSNO;
        addModel();
        $.ajax({
            url: url,
            type: 'get',
            success: function (data) {
               removeModal();
                if(data.success){
                    ityzl_SHOW_SUCCESS_LAYER("查询成功,查询结果"+data.data.msg);
                }else{
                    warnMsg(data.fail != null &&isNotBlank(data.fail.message) ?data.fail.message:"查询失败");
                }
            },
            error: function (e) {
                removeModal();
                delAjaxErrorMsg(e);
            }
        });
    }else{
        layer.alert('当前数据异常！', {title: '提示'});
    }
}


