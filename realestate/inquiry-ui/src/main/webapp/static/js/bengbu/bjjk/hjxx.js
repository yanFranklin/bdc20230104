var $,form, laytpl;
layui.use(['jquery', 'layer', 'element', 'form', 'laytpl'], function () {
    var layer = layui.layer,
        element = layui.element;
    laytpl = layui.laytpl;
    form = layui.form;

    $(document).on('click','#search',function () {
      var zjh = $('#cxzjh').val();

      // 身份证号验证
        if(isNotBlank(zjh)){
            var msg = checkSfzZjh(zjh);
            if(isNotBlank(msg)){
                layer.msg(msg, {icon: 5, time: 3000});
            }else {
                generateHjxx(zjh);
            }
        }else {
            layer.msg("身份证号不能为空", {icon: 5, time: 3000});
        }
    })


});

function generateHjxx(zjh) {
    addModel();
    $.ajax({
        url: getContextPath()+"/rest/v1.0/queryByExchange/bengbu/hygaxx",
        type: 'GET',
        dataType: 'json',
        data: {
            qlrzjh : zjh,
            beanName : "acceptJtcyxx"
        },
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                var tpl = hjxxTpl.innerHTML, view = document.getElementById('hjxxTable');
                //渲染数据
                laytpl(tpl).render(data, function (html) {
                    view.innerHTML = html;
                });
                form.render();
            }else{
                layer.msg("未查询到户籍信息", {icon: 5, time: 3000});
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

function queryHyxx(zjh){
    if(!isNotBlank(zjh)){
        alertMsg("未获取到证件号信息！");
        return;
    }
    var url = getContextPath()+"/bengbu/bjjk/hyxx.html?zjh=" + zjh ;
    layer.open({
        type: 2,
        area: ['960px', '575px'],
        fixed: false, //不固定
        title: "婚姻信息",
        content: url,
        btnAlign: "c"
    });
}
