var processInsId = $.getUrlParam("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var addBtnShow = $.getUrlParam("addBtnShow");
var storageUrl = "";

layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'laytpl'], function() {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laytpl = layui.laytpl;


    /**
     * 查询按钮
     */
    $('.layui-tab').on('click','.search', function(item) {
        // 获取户号
        var consNo = $(this).parents("tr").find('.consno').val();
        if(consNo == ""){
            warnMsg("请输入户号！");
            return false;
        }
        var url  = "";
        var ywlx;
        if($(this).parents(".basic-info").attr("id") == "shuigh"){
            ywlx = 1;
            url = "/realestate-inquiry-ui/sdqgh/shui/check/shucheng"+"?gzlslid="+processInsId+"&userId="+consNo;
        }else if($(this).parents(".basic-info").attr("id") == "diangh"){
            ywlx = 2;
            url = "/realestate-inquiry-ui/sdqgh/dian/zt/"+processInsId +"/"+consNo;
        }else if($(this).parents(".basic-info").attr("id") == "qigh"){
            ywlx = 3;
            url = "/realestate-inquiry-ui/sdqgh/qi"
        }
        addModel();
        queryConsNoZt(url,$(this),ywlx,consNo);
        return false;
    })

    /**
     * 过户按钮
     */
    $('.layui-tab').on('click', '.gh', function(item) {
        addModel();
        var sdqxx =[];
        var $this =$(this);
        $('.basic-info tr').each(function (index, item) {
            if(!$(this).hasClass("gray-tr")){
                var sdq ={};
                $($(this).find("input,select")).each(function (index, item) {
                    sdq[item.name] =item.value;
                })
                sdq["rqfwbsm"] = $(this).parents(".basic-info").find('.gh').attr("rqlx");
                if(isNotBlank(sdq.ywlx) &&isNotBlank(sdq.hzmc) &&isNotBlank(sdq.xhzmc) &&isNotBlank(sdq.consno)){
                    sdqxx.push(sdq);
                }
            }
        });
        if(sdqxx.length ===0){
            removeModal();
            layer.alert("请检查必要字段是否填写:户号，户主名称，新户主名称");
            return false;
        }
        getReturnData("/sdqgh/saveSdq/"+processInsId,JSON.stringify(sdqxx),"POST",function (){
            removeModal();
            $this.parents('.basic-info').find('.consno').attr({"disabled":"off"});
            $this.parents('.basic-info').find('.hzmc').attr({"disabled":"off"});
            $this.parents('.basic-info').find('.xhzmc').attr({"disabled":"off"});
            $this.parents('.basic-info').find('.hzzl').attr({"disabled":"off"});
            $this.hide();

            ityzl_SHOW_SUCCESS_LAYER("已记录办理信息");

        },function (error){
            delAjaxErrorMsg(error);
        });
        return false;
    })

    /**
     * 推送按钮
     */
    // $('.layui-tab').on('click', '.ts', function(item) {
    //     addModel();
    //     // 获取户号
    //     var consNo = $(this).parents("tr").find('.consno').val();
    //     if(consNo == ""){
    //         warnMsg("请输入户号！");
    //         return false;
    //     }
    //     // 水过户
    //     var url = "/realestate-inquiry-ui/sdqgh/shui/tsgh/shucheng"+"?gzlslid="+processInsId+"&userId="+consNo;
    //     tsShuiGh(url);
    //     return false;
    // })

    //打印申请表
    $("#print").on('click',function(item){
        var modelUrl = getIP() + "/realestate-inquiry-ui/static/printModel/sdqsqb.fr3";
        var dataUrl=  getIP() + "/realestate-inquiry-ui/sdqgh/print/" + processInsId +"/"+isOneWebSource;
        print(modelUrl, dataUrl, false);
    })

    // 查询大云文件中心的地址
    querySlymUrl();
    //加载水电气信息
    querySdqyw(processInsId);

    /**
     * 加载收件材料
     */
    loadSjcl();

    if(addBtnShow == "false"){
        layui.use(['form'], function() {
            $('.consno').attr({"readonly":"readonly"})
            $('.xhzmc').attr({"disabled":"off"})
            form.render()
        })
        $('.sjcl').hide();
    }

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
 * 查询当前户号是否满足办理水电气过户的条件
 */
function queryConsNoZt(url,item,ywlx,consNo){
    if(!isNotBlank(url)){
        warnMsg("当前业务暂不支持");
        return false;
    }
    // 水
    if (ywlx == 1){
        queryShuiZt(url, item);
    }
    // 电
    if (ywlx == 2){
        queryDianZt(url, item);
    }
    if (ywlx == 3){
        queryQiZt(ywlx,consNo, item);
    }
}

function queryDianZt(url, item){
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if(data.success){
                ityzl_SHOW_SUCCESS_LAYER("查询成功,查询结果："+data.data.msg);
                $(item).parents(".basic-info").find('.result').val(data.data.msg);
                // $(item).next().show();
            }else{
                warnMsg(data.fail != null &&isNotBlank(data.fail.message) ?data.fail.message:"查询失败");
            }
            removeModal();
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
        }
    });
}

function queryShuiZt(url,item){
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if(data.data){
                ityzl_SHOW_SUCCESS_LAYER("查询成功，允许过户");
                $(item).parents(".basic-info").find('.result').val('允许过户');
                // $(item).next().show();
            }else{
                warnMsg("欠费不允许过户");
                $(item).parents(".basic-info").find('.result').val('欠费不允许过户');
            }
            removeModal();
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
        }
    });
}
/**
 * 3-气
 */
function queryQiZt(type,consNo,item){
    var url= "";
    if(type == "3"){
        url = "/realestate-inquiry-ui/sdqgh/qi"
    }
    var rqlx = $(item).attr("rqlx")
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        data: {consNo: consNo,processInsId:processInsId,rqlx:rqlx},
        //async: false,
        success: function (data) {
            if (data !== null) {
                if(type == "3"){
                    if($(item).attr("rqlx") == "HFRQ") {
                        $(item).parents(".basic-info").find('.hzzl').val(data.address == null ? "" : data.address);
                        // $(item).parents(".basic-info").find('.hzmc').val(data.username == null ? "" : data.username);
                        //记录当前查询的接口
                        $(item).parents(".basic-info").find('.gh').attr("rqlx","HFRQ");
                        if (data.result == "true") {
                            $(item).parents(".basic-info").find('.gh').show();
                            $(item).parents(".gray-tr").removeClass('.gray-tr');
                            $(item).parents(".basic-info").find('.result').val("可以正常受理");
                        } else {
                            $(item).parents(".basic-info").find('.gh').hide();
                            //$(item).parents(".gray-tr").removeClass('.gray-tr');
                            $(item).parents(".basic-info").find('.result').val(data.resultText);
                            // 但账户余额有50需要交接|不可过户，
                            // 有欠费|不可过户，有代扣关系|不可过户，XXXXX。
                            //展示data.resultText
                        }
                    } else if($(item).attr("rqlx") == "WNRQ"){
                        $(item).parents(".basic-info").find('.hzzl').val(data.address == null ? "" : data.address);
                        //$(item).parents(".basic-info").find('.hzmc').val(data.username == null ? "" : data.username);
                        //记录当前查询的接口
                        $(item).parents(".basic-info").find('.gh').attr("rqlx","WNRQ");
                        if (data.result == true) {
                            $(item).next().show();
                            $(item).parents(".gray-tr").removeClass('.gray-tr');
                            $(item).parents(".basic-info").find('.result').val(data.resultText);
                        } else {
                            $(item).next().hide();
                            $(item).parents(".basic-info").find('.result').val(data.resultText );
                        }
                    }
                }
            }
            removeModal();
        },
        error: function (xhr, status, error) {
            removeModal();
            //delAjaxErrorMsg(xhr)
        }
    });
}

// 推送水过户
function tsShuiGh(url){
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            ityzl_SHOW_SUCCESS_LAYER("推送过户成功");
            removeModal();
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
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
            $(item).find('.result').val("可以正常受理");
            $(item).find(".gh").hide();
        }else if (data.blzt ===3){
            // 办理成功
            $(item).find('.consno').attr({"disabled":"off"});
            $(item).find('.hzmc').attr({"disabled":"off"});
            $(item).find('.xhzmc').attr({"disabled":"off"});
            $(item).find('.hzzl').attr({"disabled":"off"});
            $(item).find('.result').val("办理成功");
            $(item).find(".gh").hide();
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

/**
 * 加载收件材料
 */
function loadSjcl(){
    $.ajax({
        url: "/realestate-inquiry-ui/sdqgh/sjcl",
        type: 'GET',
        data:{processInsId: processInsId},
        dataType:"json",
        success: function (data) {
            generateSjcl(data);
        }
    });
}

/**
 * 加载收件材料
 * @param data
 */
function generateSjcl(data) {
    var sjclidLists = [];
    if(data !== null && data.length >0) {
        for(var i=0;i<data.length;i++) {
            sjclidLists.push(data[i].sjclid);
        }
    }
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {
            bdcSlSjclDOList: data,
        };
        var tpl = sjclTpl.innerHTML, view = document.getElementById("sjcl");
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
    })

}

//附件上传（单个上传）
function scfj(element) {
    var clmcEl = $(element).parent().parent().find("input[name='clmc']")[0];
    var clmc = $(clmcEl).val();
    uploadSjcl(clmc);

}

/**
 * @param clmc 材料名称
 *  收件材料上传
 */
function uploadSjcl(clmc) {
    var sjclArrayPllc = $(".sjcl").serializeArray();
    updateAllSjcl(sjclArrayPllc,clmc);

}

/**
 *
 * @param sjclArray
 * @param clmc
 * @returns {boolean}
 */
function updateAllSjcl(sjclArray,clmc) {
    var flag = 1;
    var sjclList = [];
    var sjcl = {};
    if (sjclArray.length === 0) {
        layer.alert("未添加材料,无法上传!");
        return false;
    }
    for (var i = 0; i < sjclArray.length; i++) {
        var name = sjclArray[i].name;
        sjcl[name] = sjclArray[i].value;
        // 以xmid为每一组收件材料的起始数据，作为分割依据
        if ((i + 1 < sjclArray.length && sjclArray[i + 1].name === 'xmid') || i + 1 == sjclArray.length) {
            if (sjcl.name === "") {
                flag = 0;
                return;
            }
            sjclList.push(sjcl);
            sjcl = {};
        }
    }
    if (flag === 1) {

        $.ajax({
            url: "/realestate-inquiry-ui/sdqgh/sjcl/list",
            type: 'POST',
            contentType: 'application/json;charset=utf-8',
            data:JSON.stringify(sjclList),
            async:false,
            success: function (data) {
                if (data > 0) {
                    var bdcSlWjscDTO = queryBdcSlWjscDTO(clmc);
                    var spaceId = processInsId;
                    if (isNotBlank(clmc)) {
                        spaceId = encodeURI(clmc);
                    }
                    bdcSlWjscDTO.spaceId = spaceId;
                    bdcSlWjscDTO.storageUrl = storageUrl;
                    var url = "/realestate-inquiry-ui/view/sdqgh/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
                    openSjcl(url, "附件上传");

                    loadSjcl();
                }
            }
        });
    } else {
        layer.alert("材料名称为空,无法上传!");
    }
}

//查询收件材料所需参数（打开附件上传使用）
function queryBdcSlWjscDTO(clmc) {
    var bdcSlWjscDTO = {};
    $.ajax({
        url: '/realestate-inquiry-ui/sdqgh/bdcSlWjscDTO',
        type: 'GET',
        async: false,
        contentType: 'application/json;charset=utf-8',
        data: {processInsId: processInsId,clmc: clmc},
        success: function (data) {
            if (isNotBlank(data)) {
                bdcSlWjscDTO = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return bdcSlWjscDTO;
}

//获取配置URL地址
function querySlymUrl() {
    $.ajax({
        url: '/realestate-inquiry-ui/sdqgh/url',
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                storageUrl = data.storageUrl;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

//打开附件上传
function openSjcl(url, title, xmid) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true,
        end: function () {
            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/sdqgh/ys",
                type: 'PATCH',
                dataType: 'json',
                data: {gzlslid: processInsId, xmid: xmid},
                success: function (data) {
                    if (data > 0) {
                        loadSjcl();
                    }
                    removeModal();
                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }
    });
    layer.full(index);
}
