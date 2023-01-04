/**
 * Created by ysy on 2020/3/9.
 * 新增配置js
 */
layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response',formSelects:'lib/form-select/formSelects-v4'});

var action = $.getUrlParam("action");
var pzid = $.getUrlParam("pzid");
layui.use(['form','layer','table','laytpl','formSelects'], function() {
    var form = layui.form,
        $ = layui.jquery,
        layer = layui.layer,
        response = layui.response,
        formSelects = layui.formSelects,
        laytpl = layui.laytpl;

    formSelects.config('selectPzZxt', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    //字典
    queryZd();
    function queryZd(){
        $.ajax({
            url: getContextPath()+"/rest/v1.0/lcpz/zdxx",
            type: "POST",
            data:'tsywpzlx,tsywpzmk,tsywpzzxt,sf',
            contentType: 'application/json',
            dataType: "json",
            async:false,
            success: function (data) {
                if(data){
                    if(data.tsywpzlx){
                        appendSelect(data.tsywpzlx, '#pzlx');
                    }
                    if(data.tsywpzmk){
                        appendSelect(data.tsywpzmk, '#gnmk');
                    }
                    if(data.tsywpzzxt){
                        formSelects.data('selectPzZxt','local',{arr: data.tsywpzzxt});
                    }
                    if(data.sf){
                        appendSelect(data.sf, '#sfxycq');
                        appendSelect(data.sf, '#sfsx');
                    }
                    form.render('select');
                }
                function appendSelect(data, element){
                    $.each(data, function (index, item) {
                        $(element).append('<option value="' + item.DM + '">' + item.MC + '</option>');
                    });
                }
            },
            error: function (e) {
                response.fail(e, '获取字典项失败！');
            }
        });
    }

    // 加载自定义字典项表格
    loadZdxTable();
    function loadZdxTable() {
        var tpl = addpzTableTpl.innerHTML, view = document.getElementById('addpzTableForm');
        //渲染数据
        laytpl(tpl).render({}, function (html) {
            view.innerHTML = html;
        });
        form.render();
    }

    //监听配置类型
    form.on('select(pzlx)', function (data) {
        //添加必填
        var $pzzzdxbs =$("#pzzzdxbs");
        if(data.value ==="3" ||data.value ==="4" ||data.value ==="5"||data.value ==="6"){
            $pzzzdxbs.attr("lay-verify", "required");
            //添加必填背景色
            $pzzzdxbs.parents(".layui-inline").addClass("bdc-not-null");

        }else{
            $pzzzdxbs.attr("lay-verify", "");
            //去除必填背景色
            $pzzzdxbs.parents(".layui-inline").removeClass("bdc-not-null");
        }
        form.render('select');
        // 当配置类型为5或6时，增加字典项表格
        if(data.value ==="5" ||data.value ==="6"){
            $(".form-margin-area").find("#addpzTable").removeClass("bdc-hide");
        }else {
            //其他情况字典项表格隐藏
            $(".form-margin-area").find("#addpzTable").addClass("bdc-hide");
            $("#pzzzdxbs").val("");
        }
        form.render();
    });

    var BASE_URL = getContextPath() +"/rest/v1.0/tsywpz";
    //点击提交时不为空的全部标红
    var isSubmit = true;
    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            if (value == '' || value == null || value == undefined) {//判断是否为空
                $(item).addClass('layui-form-danger');
                isSubmit = false;
            }
        }
    });


    // 字典项表格行增删
    $(document).on('click','.zdx-add', function() {
        var tpl = addpzTrTpl.innerHTML;
        laytpl(tpl).render({}, function (html) {
            $(".form-margin-area").find('#addpzTable tbody').append(html);
        });
    });

    $(document).on('click','.bdc-delete-btn', function(){
        $(this).parents('tr').remove();
    });

    //提交保存数据
    form.on('submit(submitBtn)', function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {
            addModel();
            var bdcTsywPzDTO = data.field;
            var bdcTsywZdyzdxDOList =[];
            //组织自定义字典项
            $("#addpzTableForm tbody tr").each(function () {
                var $tr =$(this);
                var zdxsjz =$tr.find('#zdxsjz').val();
                var zdxxsz =$tr.find('#zdxxsz').val();
                if(isNotBlank(zdxsjz) &&isNotBlank(zdxxsz)) {
                    var bdcTsywZdyzdxDO ={};
                    bdcTsywZdyzdxDO.zdyzdxbs =bdcTsywPzDTO.pzzzdxbs;
                    bdcTsywZdyzdxDO.zdxsjz =zdxsjz;
                    bdcTsywZdyzdxDO.zdxxsz =zdxxsz;
                    bdcTsywZdyzdxDOList.push(bdcTsywZdyzdxDO);
                }

            });
            bdcTsywPzDTO.bdcTsywZdyzdxDOList=bdcTsywZdyzdxDOList;

            $.ajax({
                url: BASE_URL + "/saveBdcTsywPz",
                type: "POST",
                data: JSON.stringify(bdcTsywPzDTO),
                contentType: 'application/json',
                success: function (data) {
                    if (data) {
                        saveSuccessMsg();
                        removeModal();
                    }
                },
                 error: function (xhr, status, error) {
                     delAjaxErrorMsg(xhr);
                     removeModal();
                 }
            });
            // 禁止提交后刷新
            return false;
        }
    });
    
    $('.bdc-form-div').on('focus','.layui-input',function () {
        if($(this).hasClass('layui-form-danger')){
            $(this).removeClass('layui-form-danger');
        }
        if($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')){
            $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
        }
    });
    $('.bdc-form-div').on('click','.xm-input',function () {
        if($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')){
            $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
        }
    });

    loadBdcTsywpz();
    function loadBdcTsywpz(){
        if(isNotBlank(pzid)){
            addModel();
            $.ajax({
                type: 'GET',
                url: getContextPath() +"/rest/v1.0/tsywpz",
                contentType: 'application/json',
                dataType: "json",
                data: {pzid : pzid},
                success: function (data) {
                    removeModal();
                    form.val("addPzForm",{
                        "pzid": data.pzid,
                        "pzmc": data.pzmc,
                        "pzlx": data.pzlx,
                        "pzzzdxbs": data.pzzzdxbs,
                        "pzcjr" : data.pzcjr,
                        "pzz" : data.pzz,
                        "pzqsz": data.pzqsz,
                        "pzyzzhbs": data.pzyzzhbs,
                        "pzdfbb" : data.pzdfbb,
                        "pzzxt" : data.pzzxt,
                        "gnmk" : data.gnmk,
                        "pzsm" : data.pzsm,
                        "pzzt":data.pzzt,
                        "sfsx":data.sfsx,
                        "sfxycq":data.sfxycq
                    });
                    // 隐藏配置值
                    $("#pzz").parents(".layui-inline").hide();
                    // 加载配置子系统多选框
                    if(isNotBlank(data.pzzxt)){
                        formSelects.value('selectPzZxt', data.pzzxt.split(","));
                    }
                    //加载自定义字典项内容
                    if((data.pzlx =="5" || data.pzlx =="6") && isNotBlank(data.pzzzdxbs)){
                        loadZdyZdxTable(data.pzzzdxbs, data.pzmc);
                    }
                    if(data.pzlx == "9"){
                        $(".default-element").hide();
                        var tpl = addXsdPzzTpl.innerHTML;
                        //渲染数据
                        laytpl(tpl).render({}, function (html) {
                            $("#pzsm").parents(".layui-form-item").after(html);
                        });
                        form.render();
                        if(isNotBlank(data.pzz)){
                            $("textarea[name='pzz']").val(formatXml(data.pzz));
                        }
                    }
                },
                error: function(err){
                    removeModal();
                    delAjaxErrorMsg(err);
                }
            });
        }else{
            // 加载配置创建人信息
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zszm/userinfo",
                type: 'GET',
                dataType: 'json',
                success: function (result) {
                    $("#pzcjr").val(result.alias);
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);
                }
            });
        }
        disabledAddFa();
    }

    // 配置类型下拉选择
    form.on('select(pzlx)', function(data){
        if(data.value == "9"){ // xsd
            $(".default-element").hide();
            $(".element-xsd").remove();
            var tpl = addXsdPzzTpl.innerHTML;
            //渲染数据
            laytpl(tpl).render({}, function (html) {
                $("#pzsm").parents(".layui-form-item").after(html);
            });
            form.render();
        }else{
            $(".default-element").show();
            $(".element-xsd").remove();
        }
    });

});

// 加载自定义字典项
function loadZdyZdxTable(bs, pzmc){
    $.ajax({
        type: 'GET',
        url: getContextPath() +"/rest/v1.0/tsywpz/zdyzdx/check",
        dataType: "json",
        data: {
            zdyzdxbs : bs,
            pzmc : pzmc
        },
        success: function (data) {
            console.log(data);
            layui.use(['form', 'laytpl'], function() {
                var form = layui.form,  $ = layui.jquery, laytpl = layui.laytpl;
                var tpl;
                var view = document.getElementById('addpzTableForm');
                if(data["readOnly"]){
                    tpl = zdxpzTableTpl.innerHTML; //只读模板
                }else{
                    tpl = addpzTableTpl.innerHTML;
                }
                //渲染数据
                laytpl(tpl).render(data[bs], function (html) {
                    view.innerHTML = html;
                });
                $(".form-margin-area").find("#addpzTable").removeClass("bdc-hide");
                form.render();
            });
        },
        error: function(err){
            delAjaxErrorMsg(err);
        }
    });
}

function getXsdExample(){
    var resultXml = formatXml(xsdMb);
    $("textarea[name='pzz']").val(resultXml);
}

function xsdFormat(){
    var xml = $("textarea[name='pzz']").val();
    if(isNotBlank(xml)){
        var resultXml = formatXml(xml);
        $("textarea[name='pzz']").val(resultXml);
    }
}

var xsdMb = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">"
    + "<!--业务报文ID--><xs:element name=\"BizMsgID\"><xs:simpleType><xs:restriction base=\"xs:string\"><xs:pattern value=\"[\\d]{18}\"/></xs:restriction></xs:simpleType></xs:element>"
    + "<!--权利类型--><xs:element name=\"RightType\"><xs:simpleType><xs:restriction base=\"xs:string\"><xs:enumeration value=\"1\"/></xs:restriction></xs:simpleType></xs:element>"
    + "<!--业务流水号--><xs:element name=\"RecFlowID\"><xs:simpleType><xs:restriction base=\"xs:string\"><xs:minLength value=\"1\"/><xs:maxLength value=\"50\"/></xs:restriction></xs:simpleType></xs:element>"
    + "<!--不动产单元号--><xs:element name=\"EstateNum\"><xs:simpleType><xs:restriction base=\"xs:string\"><xs:pattern value=\"[^\\\\s]{6,28}\"/></xs:restriction></xs:simpleType></xs:element>"
    +"<!--容积率--><xs:attribute name=\"RJL\"><xs:simpleType><xs:restriction base=\"xs:decimal\"><xs:totalDigits value=\"4\"/><xs:fractionDigits value=\"2\"/></xs:restriction></xs:simpleType></xs:attribute>"
    +"</xs:schema>";
