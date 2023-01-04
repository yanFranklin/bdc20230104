layui.use(['form','jquery','element','layer','table','laytpl'],function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        element = layui.element,
        table = layui.table,
        laytpl = layui.laytpl;


    // 查询方式,true为通过中间库根据合同编号查询,false为通过接口根据证件号查询
    var queryFlag = false;

    /*if(queryFlag){
        $(".jkcx").addClass('layui-hide');
        $(".zjkcx").removeClass('layui-hide');
    }else{
        $(".zjkcx").addClass('layui-hide');
        $(".jkcx").removeClass('layui-hide');
    }*/
     // 当前页数据
    $(function () {
        generateSpfHtxx({});
        generateClfHtxx({});

        //监听tab切换
        element.on('tab(tabFilter)', function(data){
            /*if(queryFlag){
                $(".jkcx").addClass('layui-hide');
                $(".zjkcx").removeClass('layui-hide');
            }else{
                $(".zjkcx").addClass('layui-hide');
                $(".jkcx").removeClass('layui-hide');
            }*/
            switch(data.index){
                case 1:
                    break;
                case 0:
                    // console.log('000');
                    break;
            }
        });

        $('.cxhtxx').on('click', function () {
            search();
        });
        // 回车查询
        $('.required').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });
    });

    function search() {
        var tabid = $('.layui-show').attr("id");
        if(queryFlag){
            var htbh = $('.layui-show').find('#htbh').val();
            if(isNotBlank(htbh)){
                // 判断存量房或商品房查询
                if(tabid == 'clf'){
                    cxClfHtxxByHtbh(htbh);
                }else {
                    cxSpfHtxxByHtbh(htbh);
                }
            }else {
                layer.msg("合同编号不能为空", {icon: 5, time: 3000});
            }
        }else{
            var htbh = $('.layui-show').find('#htbh').val();
            if(isNotBlank(htbh)){
                // 判断存量房或商品房查询
                if(tabid == 'clf'){
                    cxClfHtxx(htbh);
                }else {
                    cxSpfHtxx(htbh);
                }
            }else {
                layer.msg("合同编号不能为空", {icon: 5, time: 3000});
            }
        }

    }

    function generateSpfHtxx(data){
        var tpl = spfTpl.innerHTML, view = document.getElementById('spfHtxx');
        //渲染数据
        laytpl(tpl).render(data, function (html) {
            view.innerHTML = html;
        });
        form.render();
    }
    function generateClfHtxx(data){
        var tpl = clfTpl.innerHTML, view = document.getElementById('clfHtxx');
        //渲染数据
        laytpl(tpl).render(data, function (html) {
            view.innerHTML = html;
        });
        form.render();
    }

    /**
     * 根据htbh查询中间库获取商品房信息
     * @param htbh
     */
    function cxSpfHtxxByHtbh(htbh) {
        addModel();
        $.ajax({
            // url: "/realestate-etl/rest/v1.0/bengbu/fcjy/spfhtxx?contractNo=" + htbh,
            url: "/realestate-inquiry-ui/rest/v1.0/bengbu/htxx/spfHtxx?contractNo=" + htbh,
            type: 'GET',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                removeModal();
                if (isNotBlank(data) && null != data.htbh) {
                    generateSpfHtxx(data);
                } else {
                    layer.msg("未查询到合同信息", {icon: 5, time: 3000});
                }
            }
            ,
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }

    /**
     * 根据htbh查询中间库获取存量房信息
     * @param htbh
     */
    function cxClfHtxxByHtbh(htbh) {
        addModel();
        $.ajax({
            // url: "/realestate-etl/rest/v1.0/bengbu/fcjy/clfhtxx?contractNo=" + htbh,
            url: "/realestate-inquiry-ui//rest/v1.0/bengbu/htxx/clfHtxx?contractNo=" + htbh,
            type: 'GET',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                removeModal();
                if (isNotBlank(data) && null != data.htbh) {
                    generateClfHtxx(data);
                } else {
                    layer.msg("未查询到合同信息", {icon: 5, time: 3000});
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }

    /**
     * 通过接口查询
     * 根据合同编号查询商品房合同信息
     * @param htbh
     */
    function cxSpfHtxx(htbh) {
        addModel();
        $.ajax({
            url: getContextPath()+"/rest/v1.0/queryByExchange/bengbu/fcjyhtxx",
            type: 'GET',
            dataType: 'json',
            data: {
                htbh : htbh,
                beanName : "fcjySpfBaxx_bbhtxx",
            },
            success: function (data) {
                removeModal();
                if(isNotBlank(data)){
                    generateSpfHtxx(data);
                }else{
                    layer.msg("未查询到合同信息", {icon: 5, time: 3000});
                }
            }
            ,
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }

    /**
     * 通过接口查询
     * 根据合同编号查询存量房合同信息
     * @param htbh
     */
    function cxClfHtxx(htbh) {
        addModel();
        $.ajax({
            url: getContextPath()+"/rest/v1.0/queryByExchange/bengbu/fcjyhtxx",
            type: 'GET',
            dataType: 'json',
            data: {
                htbh : htbh,
                beanName : "fcjyClfHtxx_bbhtxx",
            },
            success: function (data) {
                removeModal();
                if(isNotBlank(data)){
                    generateClfHtxx(data);
                }else {
                    layer.msg("未查询到合同信息", {icon: 5, time: 3000});
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }

})
