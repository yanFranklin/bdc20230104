var processInsId = $.getUrlParam("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");


layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'laytpl'], function() {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laytpl = layui.laytpl;

    //加载水电气信息
    querySdqyw(processInsId);



    //保存
    $("#saveSdq").click(function () {
        addModel();
        var sdqxx =[];
        $('.basic-info tr').each(function (index, item) {
            if(!$(this).hasClass("gray-tr")){
                var sdq ={};
                $($(this).find("input,select")).each(function (index, item) {
                    sdq[item.name] =item.value;
                })
                if(isNotBlank(sdq.ywlx) &&isNotBlank(sdq.hzmc) &&isNotBlank(sdq.xhzmc)){
                    sdqxx.push(sdq);
                }
            }
        });
        if(sdqxx.length ===0){
            removeModal();
            layer.alert("请检查必要字段是否填写:户主名称，新户主名称");
            return false;
        }
        getReturnData("/sdqgh/saveSdq/"+processInsId,JSON.stringify(sdqxx),"POST",function (){
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("保存成功");

        },function (error){
            delAjaxErrorMsg(error);
        });

    });

    //推送
    $(".gh").click(function (){
        //电
        var url="/sdqgh/dian/tsgh/"+processInsId;
        addModel();
        getReturnData(url,{},"POST",function (data){
            removeModal();
            if(data.success){
               ityzl_SHOW_SUCCESS_LAYER("推送成功");
            }else{
                warnMsg(data.fail != null &&isNotBlank(data.fail.message) ?data.fail.message:"推送失败");
            }

        },function (error){
            delAjaxErrorMsg(error);
        });
        return false;

    });

    //撤销
    $(".cancel").click(function (){
        var url="/sdqgh/dian/tsgh/cancel/"+processInsId;
        addModel();
        getReturnData(url,{},"POST",function (data){
            removeModal();
            if(data.success){
                ityzl_SHOW_SUCCESS_LAYER("撤销成功");
            }else{
                warnMsg(data.fail != null &&isNotBlank(data.fail.message) ?data.fail.message:"推送失败");
            }
        },function (error){
            delAjaxErrorMsg(error);
        });
        return false;
    });


});

/**
 * 查询当前流程下的已申请的水电气业务
 * @param processInsId
 */
function querySdqyw(processInsId){
    addModel();
    url = "/realestate-inquiry-ui/sdqgh/sdqxx/"+processInsId;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            removeModal();
            if(data.bdcQlrList ==null ||data.bdcQlrList.length ==0 ||data.bdcYwrList ==null ||data.bdcYwrList.length ==0){
                alertMsg("该流程还未保存权利人或义务人，请前往受理页面添加权利人或义务人！");
                return false;
            }
            xrQlrlist(data.bdcQlrList,'xhzmc');
            xrQlrlist(data.bdcYwrList,'hzmc');

            if(data.bdcSdqghDOList!=null && data.bdcSdqghDOList.length>0){
               for(var i = 0;i<data.bdcSdqghDOList.length;i++){
                   var sdqyw =data.bdcSdqghDOList[i];
                   if(sdqyw.ywlx == "1"){
                       controlBtnStatus($("#shuigh"),sdqyw);
                   }
                   if(sdqyw.ywlx == "2"){
                       controlBtnStatus($("#diangh"),sdqyw);
                   }
                   if(sdqyw.ywlx == "3"){
                       controlBtnStatus($("#qigh"),sdqyw);
                   }

               }
            }
            $(".hzzl").each(function (){
                if(isNullOrEmpty($(this).val())){
                    $(".hzzl").val(data.zl);
                }
            });
            getStateById(readOnly,formStateId,"sdqym");
        },
        error: function (xhr, status, error) {

        }
    });
}

/**
 * 控制按钮
 * @param item
 * @param consono
 * @param id
 */
function controlBtnStatus(item,data){
    layui.use(['form'], function() {
        var form = layui.form;
        $(item).find('.id').val(data.id);
        $(item).find('.consno').val(data.consno);
        $(item).find('.hzmc').val(data.hzmc);
        $(item).find('.xhzmc').val(data.xhzmc);
        $(item).find('.hzzl').val(data.hzzl);
        if(data.blzt ===2){
            //已申请
            $(item).find('.consno').attr({"disabled":"off"});
            $(item).find('.hzmc').attr({"disabled":"off"});
            $(item).find('.xhzmc').attr({"disabled":"off"});
            $(item).find('.hzzl').attr({"disabled":"off"});

        }
        form.render();
    })

}

/**
 * 过户后控制页面元素权限
 * @param item
 * @param status
 */
function controlSelect(item,status){
    layui.use(['form'], function() {
        var form = layui.form;
        $(item).parents('.basic-info').find('.xhzmc').attr({"disabled":status})
        $(item).parents('.basic-info').find('.consno').attr({"readonly":"readonly"})
        form.render("select");
    })
}


/**
 * 渲染权利人
 * @param qlrlist
 */
function xrQlrlist(qlrlist,className){
    layui.use(['form'], function() {
        for(var i=0;i<qlrlist.length;i++){
            $('.'+className).append("<option>"+qlrlist[i].qlrmc+"</option>")
        }
        var form = layui.form;
        form.render("select");
    })
}

