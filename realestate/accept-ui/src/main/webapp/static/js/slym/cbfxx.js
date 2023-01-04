layui.use(['form','jquery','laydate','element','layer','laytpl'],function () {
    $ = layui.jquery;
    var element = layui.element,
        form = layui.form,
        layer = layui.layer,
        laytpl = layui.laytpl;

    //获取参数
    //项目ID
    var xmid = $.getUrlParam('xmid');

    //是否只读
    var readOnly = $.getUrlParam('readOnly');
    //表单ID
    var formStateId = $.getUrlParam('formStateId');

    var zdList =[];

    /**
     * 字典
     */
    getCommonZd(function (data) {
        zdList =data;
    });


    //加载承包方信息
    generateCbfxx();

    //加载承包方信息
    function generateCbfxx() {
        var cbfxx={};
        //获取承包方以及家庭成员信息
        getReturnData("/slym/qlr/listBdcQlr", JSON.stringify({xmid: xmid, qlrlb: 1}), 'POST', function (data) {
            var cbfAndJtcyxxList =[];
            if(data.length >0){
                for (var qlrlength = 0; qlrlength < data.length; qlrlength++) {
                    var cbfAndJtcy ={};
                    cbfAndJtcy.cbfxx =data[qlrlength];
                    getReturnData("/rest/v1.0/jtcy/qlrid/"+data[qlrlength].qlrid,{},"GET",function (jtcydata) {
                        cbfAndJtcy.jtcyList =jtcydata;

                    },function (error) {
                        delAjaxErrorMsg(error);
                    },false);
                    cbfAndJtcyxxList.push(cbfAndJtcy);

                }
            }else{
                var cbfAndJtcyxx ={};
                cbfAndJtcyxx.cbfxx={};
                cbfAndJtcyxxList.push(cbfAndJtcyxx);
            }
            cbfxx.cbfAndJtcyxxList =cbfAndJtcyxxList;
            cbfxx.zd=zdList;
        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);


        var cbfTpl = cbfxxTpL.innerHTML, cbfView =document.getElementById('bdc-popup-large');
        //渲染数据
        laytpl(cbfTpl).render(cbfxx, function (html) {
            cbfView.innerHTML = html;
        });

        form.render();
        $('.layui-inline input').attr("disabled", "off");
        $('.layui-inline select').attr("disabled", "off");
        $('td input').attr("disabled", "off");
        $('td select').attr("disabled", "off");

        //重新渲染select
        form.render("select");

        //不可编辑加锁
        disabledAddFa();

    }
});
