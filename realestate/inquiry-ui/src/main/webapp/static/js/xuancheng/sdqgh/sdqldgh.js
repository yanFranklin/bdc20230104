var processInsId = $.getUrlParam("processInsId");
var addBtnShow = $.getUrlParam("addBtnShow");
// 如果资源挂在一窗流程下面，则需要传入 isOneWebSource=true,登记系统不传
var isOneWebSource = $.getUrlParam("isOneWebSource");
var qlrlist = [];
var storageUrl = "";

layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'laytpl'], function() {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laytpl = layui.laytpl;

    //表单行增删
    $('.layui-tab').on('click', '.bdc-item-add', function() {
        var getTpl = sdqaddTpl.innerHTML,
            view = $(this).parents('.basic-info').find('.layui-table tbody'),
            len = view.children('tr').length;

        laytpl(getTpl).render([], function(html) {
            view.append(html);
        });
        for(var i=0;i<qlrlist.length;i++){
            $(this).parents('.basic-info').find('.xhzmc').append("<option>"+qlrlist[i].qlrmc+"</option>")
        }
        form.render("select");
        //view.find('tr:eq(' + len + ') td:first-child').text(len + 1);
    });

    $('.layui-tab').on('click', '.bdc-delete-btn', function() {
        var view = $(this).parents('.layui-table'),
            len = view.find('tbody tr').length - 1,
            deletr = $(this).parents('tr');
        // 删除确认
        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '您确定要删除吗？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function() {
                //确定操作
                console.log('确定');
                layer.close(deleteIndex);

                deletr.remove();
                //序号重排
                // for (var i = 1; i <= len; i++) {
                //     view.find('tbody tr:eq(' + (i - 1) + ') td:first-child').text(i);
                // }
            },
            btn2: function() {
                //取消
            }
        });
    });


    /**
     * 查询按钮
     */
    $('.layui-tab').on('click','.search', function(item) {
        var type  = 1;
        if($(this).parents(".basic-info").attr("id") == "diangh"){
            type = 2;
        }else if($(this).parents(".basic-info").attr("id") == "qigh"){
            type = 3;
        }
        // 获取户号
        var consNo = $(this).parents("tr").find('.consno').val();
        if(consNo == ""){
            warnMsg("请输入户号！")
            return false;
        }
        var consNoOther = $(this).parents('.basic-info').find('.consno:eq(0)').val();
        if(consNo != consNoOther){
            warnMsg("输入的户号与当前户号不一致！")
            return false;
        }

        addModel();
        queryConsNoZt(type,consNo,$(this));
        return false;
    })

    /**
     * 过户按钮
     */
    $('.layui-tab').on('click', '.gh', function(item) {
        var type  = 1;
        if($(this).parents(".basic-info").attr("id") == "diangh"){
            type = 2;
        }else if($(this).parents(".basic-info").attr("id") == "qigh"){
            type = 3;
        }
        // 获取户号
        var consNo = $(this).parents("tr").find('.consno').val();
        // 获取户主名称
        var hzmc = $(this).parents("tr").find('.hzmc').val();
        // 获取新户主名称
        var xhzmc = $(this).parents("tr").find('.xhzmc').val();
        // 获取户主坐落
        var hzzl = $(this).parents("tr").find('.hzzl').val();
        //获取燃气类型--type为3时起作用
        var rqlx = $(this).parents(".basic-info").find('.gh').attr("rqlx");
        var dataObj = {};
        dataObj.consno = consNo;
        dataObj.hzmc = hzmc;
        dataObj.xhzmc = xhzmc;
        dataObj.hzzl = hzzl;
        dataObj.rqlx = rqlx;
        if(xhzmc == "" || xhzmc == null){
            alertMsg("请前往受理页面添加权利人后，选择新户主名称！")
            return false;
        }
        if(rqlx == "KXGS" && isNullOrEmpty(hzmc)){
            alertMsg("请填写户主名称！")
            return false;
        }
        saveData(dataObj,processInsId,type,$(this));
        return false;
    })

    //保存水电气信息
    // 保存
    $("#saveSdq").click(function () {
        addModel();
        var sdqxx = [];
        $('.basic-info tr').each(function (index, item) {
            if (!$(this).hasClass("gray-tr")) {
                var sdq = {};
                $($(this).find("input,select")).each(function (index, item) {
                    sdq[item.name] = item.value;
                })
                if (isNotBlank(sdq.ywlx)) {
                    sdqxx.push(sdq);
                }
            }
        });
        if (sdqxx.length === 0) {
            removeModal();
            layer.alert("没有需要保存的数据");
            return false;
        }
        getReturnData("/sdqgh/saveSdq/" + processInsId + "?needHz=false", JSON.stringify(sdqxx), "POST", function () {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("保存成功");

        }, function (error) {
            delAjaxErrorMsg(error);
        });

    });

    // 查询大云文件中心的地址
    querySlymUrl();
    // 查询当前流程下已经挂载的水电气业务
    querySdqyw(processInsId);
    // 查询当前流程下的权利人
    //if(addBtnShow != "false"){
    qlrlist = queryQlrByGzlslid(processInsId);
    //}

    /**
     * 加载收件材料
     */
    loadSjcl();

    if(addBtnShow == "false"){
        layui.use(['form'], function() {
            $(".bdc-item-add").hide();
            //$('.search').hide();
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
    url = "/realestate-inquiry-ui/sdqgh/querySdqyw/"+processInsId;
    $.ajax({
        url: url,
        type: 'GET',
        //async: false,
        success: function (data) {
            if(data!=null && data.length>0){
               for(var i = 0;i<data.length;i++){
                   if(data[i].ywlx == "1"){
                       controlBtnStatus($("#shuigh"),data[i]);
                   }
                   if(data[i].ywlx == "2"){
                       controlBtnStatus($("#diangh"),data[i]);
                   }
                   if(data[i].ywlx == "3"){
                       controlBtnStatus($("#qigh"),data[i]);
                   }
               }
            }
        },
        error: function (xhr, status, error) {
            //delAjaxErrorMsg(xhr);
            $(".bdc-item-add").hide();
        }
    });
}

/**
 * 查询当前户号是否满足办理水电气过户的条件
 * @param type
 * 1-水
 * 2-电
 * 3-气
 */
function queryConsNoZt(type,consNo,item){
    var url= "";
    if(type == "1"){
        url = "/realestate-inquiry-ui/sdqgh/shui"
    }else if(type == "2"){
        url = "/realestate-inquiry-ui/sdqgh/dian"
    } if(type == "3"){
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
                if(type == "1"){
                    $(item).parents(".basic-info").find('.gh').attr("rqlx",$(item).attr("rqlx"));
                    if(data.xym == "004"){
                        $(item).parents(".basic-info").find('.gh').show();
                        if(isNullOrEmpty(data.yhdz)){
                            //$(item).parents(".basic-info").find('.hzzl').removeAttr("readonly");
                            //如果没有返回地址则代入当前流程的坐落
                            var url = "/realestate-inquiry-ui/sdqgh/sdqxx/"+processInsId;
                            $.ajax({
                                url: url,
                                type: 'GET',
                                success: function (res) {
                                    $(item).parents(".basic-info").find('.hzzl').val(res.zl);
                                },
                                error: function (xhr, status, error) {

                                }
                            });
                        } else {
                            $(item).parents(".basic-info").find('.hzzl').val(data.yhdz==null?"":data.yhdz);
                        }
                        if(isNullOrEmpty(data.yhmc)){
                            //如果没有返回当前户主的名称则将当前流程的义务人代入，同时将输入框替换为下拉选项
                            $(item).parents(".basic-info")
                                .find('.hzmc')
                                .parents("td")
                                .html("")
                                .append('  <select name="hzmc" lay-search="" lay-filter="" id="hzmcselect" class="hzmc">\n' +
                                    '                                            <option value="">请选择</option>\n' +
                                    '      </select>')
                            ;
                            //添加select选项
                            var url = "/realestate-inquiry-ui/sdqgh/sdqxx/"+processInsId;
                            $.ajax({
                                url: url,
                                type: 'GET',
                                success: function (res) {
                                    for(var i=0;i<res.bdcYwrList.length;i++){
                                        $(item).parents(".basic-info")
                                            .find('.hzmc')
                                            .append("<option>"+res.bdcYwrList[i].qlrmc+"</option>")
                                    }
                                    var form = layui.form;
                                    form.render("select");
                                },
                                error: function (xhr, status, error) {

                                }
                            });
                        } else {
                            //如果返回当前户主的名称则将当前流程的义务人代入，同时将下拉选项替换为输入框
                            $(item).parents(".basic-info").find('.hzmc')
                                .parents("td")
                                .html("")
                                .append('<input type="text" name="hzmc"readonly="readonly"  class="layui-input hzmc">')
                               ;
                            $(item).parents(".basic-info").find('.hzmc').val(data.yhmc==null?"":data.yhmc);
                        }
                        $(item).parents(".basic-info").find('.result').val("可以正常受理");
                    }else if(data.xym == "000"){
                        $(item).parents(".basic-info").find('.gh').hide();
                        $(item).parents(".basic-info").find('.result').val("欠费，不能受理");
                    }else{
                        $(item).parents(".basic-info").find('.gh').hide();
                        $(item).parents(".basic-info").find('.result').val("不能受理,请咨询相关人员！");
                    }
                }else if(type == "2"){

                    if(data.resultCode == "0000"){
                        //展示出办理按钮
                        $(item).next().show();
                        $(item).parents(".basic-info").find('.result').val("可以正常受理");
                        $(item).parents(".basic-info").find('.hzzl').val(data.data.elecAddr==null?"":data.data.elecAddr);
                        $(item).parents(".basic-info").find('.hzmc').val(data.data.consName==null?"":data.data.consName);
                    }else if(data.resultCode == "0001"){
                        $(item).next().show();
                        $(item).parents(".basic-info").find('.result').val("存在银行代扣,可以受理");
                        $(item).parents(".basic-info").find('.hzzl').val(data.data.elecAddr==null?"":data.data.elecAddr);
                        $(item).parents(".basic-info").find('.hzmc').val(data.data.consName==null?"":data.data.consName);
                    }else if(data.resultCode == "0002"){
                        $(item).parents(".basic-info").find('.result').val("存在欠费，不能受理");
                        $(item).next().hide();
                    }else if(data.resultCode == "0003"){
                        $(item).parents(".basic-info").find('.result').val("系统异常，不能受理");
                        $(item).next().hide();
                    }else if(data.resultCode == "0004"){
                        $(item).parents(".basic-info").find('.result').val("户号不正确，不能受理");
                        $(item).next().hide();
                    }else{
                        $(item).parents(".basic-info").find('.result').val("不能受理");
                        $(item).next().hide();
                    }
                }else if(type == "3"){
                    if($(item).attr("rqlx") == "HFRQ") {
                        $(item).parents(".basic-info").find('.hzzl').val(data.address == null ? "" : data.address);
                        $(item).parents(".basic-info").find('.hzmc').val(data.username == null ? "" : data.username);
                        //记录当前查询的接口
                        $(item).parents(".basic-info").find('.gh').attr("rqlx","HFRQ");
                        if (data.result == "true") {
                            $(item).parents(".basic-info").find('.gh').show();
                            $(item).parents(".basic-info").find('.result').val("可以正常受理");
                        } else {
                            $(item).parents(".basic-info").find('.gh').hide();
                            $(item).parents(".basic-info").find('.result').val(data.resultText);
                            // 但账户余额有50需要交接|不可过户，
                            // 有欠费|不可过户，有代扣关系|不可过户，XXXXX。
                            //展示data.resultText
                        }
                    } else if($(item).attr("rqlx") == "WNRQ"){
                        $(item).parents(".basic-info").find('.hzzl').val(data.address == null ? "" : data.address);
                        $(item).parents(".basic-info").find('.hzmc').val(data.username == null ? "" : data.username);
                        //记录当前查询的接口
                        $(item).parents(".basic-info").find('.gh').attr("rqlx","WNRQ");
                        if (data.result == true) {
                            $(item).parents(".basic-info").find('.gh').show();
                            $(item).parents(".basic-info").find('.result').val(data.resultText);
                        } else {
                            $(item).parents(".basic-info").find('.gh').hide();
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

/**
 * 点击办理后，保存户号工作流实例id等信息
 * @param consNo
 * @param processInsId
 */
function saveData(dataObj, processInsId, type, item){
    addModel();
    dataObj.gzlslid = processInsId;
    dataObj.type = type;
    dataObj.ycsl = isOneWebSource;
    $.ajax({
        url: "/realestate-inquiry-ui/sdqgh/saveData",
        type: 'POST',
        contentType: 'application/json',
        dataType: "text",
        data: JSON.stringify(dataObj),
        async: false,
        success: function (data) {
            removeModal();
            successMsg('已记录办理信息');
            $(item).parents('td').find('a').hide();
            $(item).parents('.basic-info').find('.bdc-item-add') .hide();
            controlSelect($(item),"off");
        },
        error: function (xhr, status, error) {
            removeModal();
            //delAjaxErrorMsg(xhr);
            $(item).parents('tr').remove();
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
        $(item).find('.bdc-item-add') .click();
        $(item).find('.consno').val(data.consno);
        $(item).find('.hzmc').val(data.hzmc);
        $(item).find('.xhzmc').val(data.xhzmc);
        $(item).find('.hzzl').val(data.hzzl);
        $(item).find('.consno').attr({"sdqghid":data.id})
        $(item).find('.id').val(data.id);
        // 核验成功
        if (data.hyjg=="1"){
            $(item).find('.result').val("可以正常受理");
            $(item).find('.consno').attr({"readonly":"readonly"})
            $(item).find('.xhzmc').attr({"disabled":"off"})
            $(item).find('.bdc-item-add') .hide();
            $(item).find('.search').hide();
            $(item).find('.gh').hide();
            $(item).find('.del').hide();
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
 * 通过查找当前项目下所有的权利人
 * @param gzlslid
 */
function queryQlrByGzlslid(gzlslid){
    var list = [];
    $.ajax({
        url: "/realestate-inquiry-ui/sdqgh/getQlrlist/"+gzlslid+"/"+isOneWebSource,
        type: 'GET',
        async: false,
        success: function (data) {
            list = data;
        }
    });
    if(list.length == 0){
        alertMsg("该流程还未保存权利人，请前往受理页面添加权利人！")
    }
    xrQlrlist(list);
    return list;
}

/**
 * 渲染权利人
 * @param qlrlist
 */
function xrQlrlist(qlrlist){
    layui.use(['form'], function() {
        for(var i=0;i<qlrlist.length;i++){
            $('.xhzmc').append("<option>"+qlrlist[i].qlrmc+"</option>")
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
