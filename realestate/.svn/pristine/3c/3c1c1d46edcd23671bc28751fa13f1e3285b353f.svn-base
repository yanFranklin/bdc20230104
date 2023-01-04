/**
 * Created by ysy on 2020/7/20.
 * 详细配置js
 */
var zdbs = $.getUrlParam("zdbs");
var dsfxtbs = $.getUrlParam("dsfxtbs");
var BASE_URL = getContextPath() +"/rest/v1.0/dsfzdpz";
layui.use(['form','layer','table','laytpl'], function(){
    var form = layui.form,
        $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        laytpl = layui.laytpl;



    $(function(){

        //单击 新增
        $('.bdc-add-tr-btn').on('click',function(){
            var getTrTpl = addTrTpl.innerHTML;
            laytpl(getTrTpl).render([], function(html){
                if($('.bdc-form-table tbody .bdc-table-none').length > 0){
                    $('.bdc-form-table tbody .bdc-table-none').remove();
                }
                $('.bdc-form-table tbody').append(html);
            });
        });

        //单击表格内删除
        $('.bdc-table-box').on('click','.bdc-delete-btn',function(){
            var $this = $(this);
            if($this.parents('tbody').find('tr').length == 1){
                $this.parents('tbody').append('<tr class="bdc-table-none">' +
                    ' <td colspan="4"> ' +
                    '<div class="layui-none">' +
                    '<img src="../../../static/lib/bdcui/images/table-none.png" alt="">无数据 ' +
                    '</div> ' +
                    '</td> ' +
                    '</tr>');
            }
            $this.parents('tr').remove();
        });

        //点击提交时不为空的全部标红
        var isSubmit = true;
        var verifyMsg = "必填项不能为空";
        form.verify({
            required: function (value, item) {
                if (value == '' || value == null || value == undefined) {//判断是否为空
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                }
            },
            tdRequired : function(value, item){
                if (value == '' || value == null || value == undefined) {//判断是否为空
                    $(item).focus();
                    isSubmit = false;
                    verifyMsg = "字典值不能为空";
                }
            }
        });

        //提交保存数据
        form.on('submit(submitBtn)', function (data) {
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                return false;
            } else {
                console.info(data.field);
                if($(".bdc-table-none").length == 1){
                    alertMsg("未配置对照值内容");
                    return false;
                }
                var formData = getFormData();
                addModel();
                $.ajax({
                    type: 'POST',
                    url: BASE_URL +"/save",
                    contentType: 'application/json',
                    dataType: "json",
                    data: JSON.stringify(formData),
                    success: function (data) {
                        removeModal();
                        successMsg("保存成功");
                    },
                    error: function(err){
                        removeModal();
                        delAjaxErrorMsg(err);
                    }
                });
                return false;
            }
        });

    });

    // 初始化加载第三方字典项对照值
    initTable();
    function initTable(){
        var tableData = new Array();

        if(isNotBlank(zdbs) && isNotBlank(dsfxtbs)){
            addModel();
            tableData = getZdxData();
            $("#zdbs").val(zdbs);
            $("#dsfzdbs").val(dsfxtbs);
        }
        var getTrTpl = tableTpl.innerHTML;
        laytpl(getTrTpl).render(tableData, function(html){
            if($('.bdc-form-table tbody .bdc-table-none').length > 0){
                $('.bdc-form-table tbody .bdc-table-none').remove();
            }
            $('.bdc-form-table tbody').append(html);
        });
    }

});

function getFormData(){
    var zdxArray = new Array();
    $(".bdc-form-table").find("tr").each(function(index, element){
        if($(element).find("input").length>0){
            var zdx = {
                dsfzdz: $(element).find("input[name='dsfzdz']").val(),
                bdczdz: $(element).find("input[name='bdczdz']").val(),
            };
            zdxArray.push(zdx);
        }
    });
    return {
        zdbs : $("#zdbs").val(),
        dsfxtbs: $("#dsfzdbs").val(),
        bdcZdDsfzdgxDOList: zdxArray,
    };
}

function getZdxData(){
    var data= new Array();
    $.ajax({
        type: 'POST',
        url: BASE_URL +"/getDsfZdxBybs",
        contentType: 'application/json',
        dataType: "json",
        async: false,
        data: JSON.stringify({zdbs: zdbs,dsfxtbs: dsfxtbs}),
        success: function (data) {
            removeModal();
            tableData = data;
        },
        error: function(err){
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
    return tableData;
}