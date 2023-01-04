//获取收件材料信息,渲染到页面
var sjclNumber = 0;
//用于存放所有的收件材料id
var sjclidLists = [];
var sjclids = new Set();
//权利信息项目信息对象
var qlxx =[];
var layer, laytpl, form, formSelects,table,$;
var processInsId = $.getUrlParam('processInsId');
//是否只读
var readOnly = $.getUrlParam('readOnly');
//表单ID
var formStateId = $.getUrlParam('formStateId');
//证书模板标志
var zsmodel = getQueryString("zsmodel");
//是否注销流程
var zxlc = $.getUrlParam('zxlc');
//工作流实例ID
var gzlslid = $.getUrlParam('processInsId');
var zdList =[];
layui.use(['form','jquery','laydate','element','layer','laytpl'],function () {
        $ = layui.jquery;
        var element = layui.element;
        form = layui.form;
        layer = layui.layer;
        var laydate = layui.laydate;
        laytpl = layui.laytpl;
        form.on("submit(saveJtcy)", function (data) {
            saveJtcy();
            return false;
        });
    $(function () {
        /**
         * 字典
         */
         getCommonZd(function (data) {
             zdList =data;
         });
        loadInfo();

    });
});


//页面渲染
function renderJyqForm() {
    //表单渲染
    form.render();
    //渲染日期
    renderDate(form,"");
    //权限控制
    getStateById(readOnly, formStateId, "slymjtcy");
    //重新渲染select
    form.render("select");
    //不可编辑加锁
    disabledAddFa();
}




//提交前验证处理数据,返回验证信息
function checkCommitData(){
    var checkMsg="";
    //家庭成员名称不为空,验证家庭成员证件号
    for (var i = 0; i < $(".jtcy-table tbody tr").length; i++) {
        var $jtcyTr =$($(".jtcy-table tbody tr")[i]);
        var jtcymc = $jtcyTr.find("input[name=jtcymc]").val();
        if(isNotBlank(jtcymc)) {
            var $jtcyzjh =$jtcyTr.find("input[name=jtcyzjh]");
            var jtcyzjh = $jtcyzjh.val();
            var msg = checkSfzZjh(jtcyzjh);
            if (isNotBlank(msg)) {
                checkMsg ="家庭成员"+msg;
                $jtcyzjh.addClass('layui-form-danger');
                break;
            }
        }
    }
    return checkMsg;

}
var jtcyList = [];
/**
 * 加载信息
 */
function loadInfo(){
    //获取原项目
    getReturnData("/slym/qlr/list",{processInsId:processInsId,xmid:'',qlrlb:1},"GET",function (data) {
        if(!isNotBlank(data)){
            ityzl_SHOW_WARN_LAYER("未查询到权利人信息");
            return false;
        }
        if(data.length >0){
            var jtcyxx = {};
            jtcyxx.jtcyList = [];
            for (var qlrlength = 0; qlrlength < data.length; qlrlength++) {
                var qlrId = data[qlrlength].qlrid;
                var qlrmc = data[qlrlength].qlrmc;
                jtcyxx.qlrId = qlrId;
                jtcyxx.qlrmc = qlrmc;
                getReturnData("/rest/v1.0/jtcy/qlrid/"+data[qlrlength].qlrid,{},"GET",function (jtcydata) {
                    jtcyxx.jtcyList= jtcyxx.jtcyList.concat(jtcydata);

                },function (error) {
                    delAjaxErrorMsg(error);
                },false);


            }
            jtcyList.push(jtcyxx);
        }
        generateJtcyxx(jtcyList);

    },function (error) {
        delAjaxErrorMsg(error);
    });
}


function generateJtcyxx(jtcyList){
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json;
        json = {
            jtcyList: jtcyList,
            zd: zdList
        };
        var view;
        var tpl = jtcyxxTpl.innerHTML;
        view = document.getElementById('jtcyxx');
        //渲染数据
        if (isNotBlank(view)) {
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
        }
        form.render();
        form.render('select');
        renderSelect();
        removeModal();
    })
}
//删除家庭成员
function delJtcy(jtcyid, obj) {
    //防止删除之前新增了很多数据没保存，删除后会刷新table
    var url = getContextPath() + "/rest/v1.0/jtcy?jtcyid=" + jtcyid+"&gzlslid="+processInsId;
    //var jtcyid =$tr.find("input[name=jtcyid]").val();
    //saveJtcy();
    if (isNotBlank(jtcyid)) {

        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '是否确认删除？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                addModel();
                //确定操作
                if (isNotBlank(jtcyid)) {
                    $.ajax({
                        url: url,
                        type: 'DELETE',
                        success: function (data) {
                            removeModal();
                            //ityzl_SHOW_SUCCESS_LAYER("删除成功");
                            //$tr.remove();
                            $(obj).parent().parent().remove();
                        }, error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr)
                        }
                    });
                } else {
                    removeModal();
                    //ityzl_SHOW_SUCCESS_LAYER("删除成功");
                    $(obj).parent().parent().remove();
                    //$tr.remove();
                }
                layer.close(deleteIndex);
            },
            btn2: function () {
                //取消
                layer.close(deleteIndex);
            }
        });
    } else {
        $(obj).parent().parent().remove();
    }
}
function addBdcJtcy(elem, qlrId){
    var appendEl = $("#addSjclTable");
    if (isNotBlank(elem) && isNotBlank(qlrId)) {
        appendEl = $(elem).parents('.addjtcy').find("table[name='addjtcy" + qlrId + "']")
    }
    var trELArray = $(appendEl).find("tr");
    if (trELArray.length > 1) {
        $(appendEl).find("tr[class='bdc-table-none']").remove();
        //移除后长度减一
        trELArray = $(appendEl).find("tr");
    }
    sjclNumber = trELArray.length;
    var sjclpzList = [];
    var addSjclxx = {
        gzlslid: processInsId,
        qlrId: qlrId,
        sjclpzList: sjclpzList,
        zd: zdList
    };
    var getTpl = addJtcyTpl.innerHTML;
    laytpl(getTpl).render(addSjclxx, function (html) {
        appendEl.append(html);
        form.render(null, qlrId);
    });
    //控制权限
    resetSelectDisabledCss();
    $(appendEl.find('tr:last-child td input')[0]).focus();
}


function saveJtcy(){
    var qlrAndJtcyList =[];
    $(".addjtcy").each(function (index,item) {
        var cbfAndJtcy ={};
        var $formadd =$(this);
        var qlrId = $formadd.find(".qlrid").val();
        if(isNotBlank(qlrId)) {
            var jtcyList = [];
            var qlrIds = [];
            $formadd.find(".jtcy-table tr").each(function () {
                var jtcy = {};
                var jtcyArray = $(this).find(".jtcy");
                jtcyArray.serializeArray().forEach(function (item, index) {
                    jtcy[item.name] = item.value;
                });
                var jtcymc = $(this).find("input[name=jtcymc]").val();
                if (isNotBlank(jtcymc)) {
                    var getJtcyid = $(this).find("#jtcyid").val();
                    var hkbbm = $(this).find("#hkbbm").val();
                    jtcy.jtcyid = getJtcyid;
                    jtcy.hkbbm = hkbbm;
                    jtcyList.push(jtcy);
                    qlrIds.push(qlrId)
                }

            });
            cbfAndJtcy.qlrid = qlrIds;
            cbfAndJtcy.jtcyList = jtcyList;
            qlrAndJtcyList.push(cbfAndJtcy);
        }
    });

    if(qlrAndJtcyList.length >0){
        console.log(qlrAndJtcyList);
        getReturnData("/rest/v1.0/jtcy/updateJtcyxx/"+processInsId,JSON.stringify(qlrAndJtcyList),"POST",function (data) {
            ityzl_SHOW_SUCCESS_LAYER("保存成功！");
            setTimeout(function () {
                location.reload();
            }, 500);
        },function (error) {
            delAjaxErrorMsg(error);
        },false)
    }
}