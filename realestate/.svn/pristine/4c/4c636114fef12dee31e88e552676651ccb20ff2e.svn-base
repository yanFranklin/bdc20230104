/** 收费信息id */
var sfxxid = $.getUrlParam("sfxxid");
var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/fph/edit';
var zdList = [];
var form,laytpl,laydate, layer;
var originData;
var fields = {"fph":"发票号","sfsj":"收费时间","hj":"合计","kprxm":"开票人姓名","sfzt":"缴费状态", "jfsbm":"缴费书号", "sfxsjf":"是否线上缴费"};
layui.use(['laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        form = layui.form;

    generateSfxx();

    // 加载收费信息
    function generateSfxx(){
        var data = layui.sessionData("bdcFpSfxxData");
        console.info(data);
        if(!isNotBlank(data)){
            warnMsg("未获取到收费信息");
            return;
        }
        originData = data.sfxx;
        //判断收费状态，为已缴费时，所有数据都不可修改；
        if (originData.sfzt == 2 ) {
            warnMsg("已完成收费，无法进行修改！");
        }
        data.sfxx.sfsj = formatDate(data.sfxx.sfsj);
        var json = {
            sfxx: data.sfxx,
        };
        var tpl, view;
        tpl = sfxxTpl.innerHTML;
        view = document.getElementById('fpxx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        laydate.render({
            elem: '#sfsj',
            format: 'yyyy-MM-dd HH:mm:ss',
            type: 'datetime'
        });
        if (originData.sfzt == 2 ) {
            $('#fph').attr("readonly",true);
            $('#hj').attr("readonly",true);
            $('#jfsbm').attr("readonly",true);

            $('#sfsj').attr("disabled",true);
            $('#sfzt').attr("disabled",true);
            $('#sfxsjf').attr("disabled",true);

            $('.org_select_show2').hide();
            $('#kprxm').attr("disabled",true);

            form.render();//必须；
        }
    }

    /**
     * 点击保存
     */
    $('#saveData').on('click', function () {
        // addModel();
        var fph = $("#fph").val();
        if(isNotBlank(fph) && originData.fph != fph){
            // 验证发票号是否可用
            $.ajax({
                url: BASE_URL + "/check?sfxxid=" + sfxxid + "&fph=" + fph,
                type: "GET",
                async: false,
                success: function (data) {
                    if (data.isKy == false || data.syzt == '2') {
                        warnMsg("该发票号不可用！")
                        return false;
                    } else if (data.syzt == '3') {
                        layer.confirm('该发票号已使用，是否修改？', function (index) {
                            updateSfxx();
                            layer.close(index);
                            return false;
                        });
                    } else {
                        updateSfxx();
                    }
                },
                error: function (e) {
                    showErrorInfo(e, "验证服务异常");
                    removeModal();
                },complete: function (XMLHttpRequest, textStatus) {
                    removeModal();
                }
            });
        }else{
            updateSfxx();
        }
        return false;
    });

    $('#cancel').on('click', function () {
        var index=parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });

    function updateSfxx(){
        var sfxx = {
            sfxxid: sfxxid,
            fph :  $("#fph").val(),
            sfsj : $("#sfsj").val(),
            sfztczsj: $("#sfsj").val(),
            hj: $("#hj").val(),
            kprxm: $("#kprxm").val(),
            sfzt: $("#sfzt").val(),
            sfxsjf: $("#sfxsjf").val(),
            jfsbm: $("#jfsbm").val()
        };
        console.info(sfxx);
        $.ajax({
            url: BASE_URL + "/save",
            type: "GET",
            data: sfxx,
            success: function (data) {
                addFpxxXgjl(sfxx);
                layer.msg("更新成功");
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                window.parent.$("#search").click();
            },
            error: function (e) {
                showErrorInfo(e, "更新服务异常");
                removeModal();
            }, complete: function (XMLHttpRequest, textStatus) {
                // 关闭loading
                removeModal();
            }
        });
    }

    function addFpxxXgjl(data){
        var xgqzd = [], xghzd=[];
        // 比对修改前与修改后数据
        if(isNotBlank(data) && isNotBlank(originData)){
            for(var key in data){
                var newValue =  fmtValue(data[key]);
                var oldValue = fmtValue(originData[key]);
                if(newValue != oldValue){
                    xgqzd.push({
                        "key" : key,
                        "value" : oldValue,
                        "name" : fields[key],
                    });
                    xghzd.push({
                        "key" : key,
                        "value" : newValue,
                        "name" : fields[key],
                    });
                }
            }
        }
        if(xghzd.length > 0 && xgqzd.length > 0){
            data.slbh = originData.slbh;
            data.zl = originData.zl;
            data.xmid = originData.xmid;
            data.jkrxm = originData.jfrxm;
            data.xgqzd = JSON.stringify(xgqzd);
            data.xghzd = JSON.stringify(xghzd);
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/fpxgjl/addFpXgjl",
                type: "GET",
                data: data,
                success: function (res) {
                },
                error: function (e) {
                    showErrorInfo(e, "更新服务异常");
                    removeModal();
                }
            });
        }
    }

    function fmtValue(value){
        if(value == 0 ){
            return value;
        }
        if(!isNotBlank(value)){
            return "";
        }
        return value;
    }
});