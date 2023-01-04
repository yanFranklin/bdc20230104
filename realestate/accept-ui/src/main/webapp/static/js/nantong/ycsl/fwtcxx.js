//实际房屋套次
var fwtc =0;
var sqrid = getQueryString("sqrid");
layui.use(['jquery', 'layer', 'form', 'element','laytpl'],function () {
    $ = layui.jquery;
    layer = layui.layer;
    form = layui.form;
    element = layui.element;
    laytpl = layui.laytpl;


    var sbfwtc = getQueryString("sbfwtc");



    var param = {
        sqrid:sqrid,
        sbfwtc: sbfwtc
    };
    fwtcxxCompareResult(param);



});

function fwtcxxCompareResult(param){
    addModel();
    $.ajax({
        url: getContextPath() + "/ycsl/nantong/compareFwtcxx",
        type: 'POST',
        async: false,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(param),
        success: function (data) {
            removeModal();
            if(data){
                generateFwtcxx(data);
                if(data.resultDataDTOList.length >0 && data.resultDataDTOList[0].filterYtFwtcxx){
                    var count = data.resultDataDTOList[0].filterYtFwtcxx.length;
                    // 义务人， 页面申报套次为首套（字典值为0），查询到房屋为1套时，此时导入对应sbfwtc应该做-1处理
                    if(data.resultDataDTOList[0].sqrDTO && data.resultDataDTOList[0].sqrDTO.sqrlb == "2"){
                        --count;
                    }
                    if(count > 1){
                        //默认其他套
                        fwtc = 9;
                    }else{
                        fwtc = count;
                    }
                }
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });

}

// 组织房屋套次信息到页面
function generateFwtcxx(data) {
    var json ={
        resultDataDTOList: data.resultDataDTOList,
        zd: parent.zdList
    };

    var getTpl = fwtcTpl.innerHTML;
    laytpl(getTpl).render(json, function (html) {
        $('.form-margin-area').html(html);
    });
    form.render('select');
    disabledAddFa();
}

//导入婚姻比对信息
function daoruFwtcbdxx(){
    var fwtcbdxx ={};
    fwtcbdxx.sbfwtc=fwtc;
    fwtcbdxx.sqrid=sqrid;
    getReturnData("/ycsl/import/fwtcbdxx",JSON.stringify(fwtcbdxx),"POST",function () {
        parent.loadYcslxx(parent.$('.layui-tab-item:nth-child(' + 2 + ')').data("xmid"),parent.$('.layui-tab-item:nth-child(' + 2 + ')'));
        ityzl_SHOW_SUCCESS_LAYER("导入成功");
    },function (error) {
        delAjaxErrorMsg(error);
    })
}


