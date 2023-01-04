/**
 * 综合查询：盐城高级查询条件页面JS
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

var jtcyCount = 0, zmcxmd, dybdclxChild, jtcyDataList, zhcxpz;
// 获取家庭成员与户主关系字典(表名：BDC_ZD_YHZGX)
var yhzgxZdList = getMulZdList("yhzgx");
// 获取抵押不动产登记类型字典(表名：BDC_ZD_DYBDCLX)
var dybdclxZdList = getMulZdList("dybdclx");

layui.use(['form', 'jquery', 'element', 'formSelects', 'layer', 'laytpl'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var laytpl = layui.laytpl;
    var formSelects = layui.formSelects;

    $(function () {
        //使用js渲染下拉框
        //$($('select[disabled="disabled"]')[0]).after('<img src="../../static/lib/bdcui/images/lock.png" alt="">');
        formSelects.data('select01', 'local', {
            arr: [
                {"name": "精确", "value": '0'}
            ]
        });
        // 下拉选择添加删除按钮
        renderSelectClose(form);
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');
        if (moduleCode){
            setElementAttrByModuleAuthority(moduleCode);
        }
        //监听点击新增权利人
        $('.bdc-add-qlr').on('click',function () {
            var getTpl = qlrTpl.innerHTML;
            laytpl(getTpl).render([], function(html){
                jtcyCount += 1;
                html = html.replace(/jtcymc/g, "jtcymc" + jtcyCount);
                html = html.replace(/jtcyzjh/g, "jtcyzjh" + jtcyCount);
                html = html.replace(/yhzgx/g, "yhzgx" + jtcyCount);
                html = html.replace(/jtcycym/g, "jtcycym" + jtcyCount);

                $('.bdc-add-box').append(html);
                if(nameClickTimes != 1){
                    $("select[name='qlrmcmhlx']").val(nameSelect);
                }
                if(zjhClickTimes != 1){
                    $("select[name='qlrzjhmhlx']").val(zjhSelect);
                }

                $.each(yhzgxZdList.yhzgx, function (index, item) {
                    $('#yhzgx' + jtcyCount).append('<option value="' + item.MC + '">' + item.MC + '</option>');
                });
                form.render('select');
            });

            form.render('select');
        });

        //监听权利人名称选择
        var nameClickTimes = 1;
        var nameSelect;
        form.on('select(qlrNameFilter)', function(data){
            nameClickTimes++;
            nameSelect = data.value;
            $("select[name='qlrmcmhlx']").val(nameSelect);
            $("select[name='jtcycymmhlx']").val(nameSelect);
            form.render('select');
        });
        //监听义务人名称选择
        form.on('select(ywrNameFilter)', function(data){
            var ywrnameSelect = data.value;
            $("select[name='ywrmcmhlx']").val(ywrnameSelect);
            form.render('select');
        });

        //监听权利人证件号选择
        var zjhClickTimes = 1;
        var zjhSelect;
        form.on('select(qlrZjhFilter)', function(data){
            zjhClickTimes++;
            zjhSelect = data.value;
            $("select[name='qlrzjhmhlx']").val(zjhSelect);
            form.render('select');
        });

        //点击删除权利人按钮
        $('#popupTwoRows').on('click','.bdc-delete-qlr',function () {
            $(this).parent().remove();
            jtcyCount -= 1;
        });
        //3. 输入框删除图标
        if (!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.")) {
            //监听input点击
            $('.layui-form-item').on('click', '.layui-input-inline .layui-icon-close', function () {
                $(this).siblings('.layui-input').val('');
            });

            $('.layui-form-item').on('focus', '.layui-input-inline .layui-input', function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                $(this).parents('.layui-input-inline').find('.layui-icon-close').removeClass('bdc-hide');
                $(this).siblings('.layui-edge').addClass('bdc-hide');
            }).on('blur', '.layui-input-inline .layui-input', function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                    $this.parents('.layui-input-inline').find('.layui-icon-close').addClass('bdc-hide');
                    $this.siblings('.layui-edge').removeClass('bdc-hide');
                }, 150)
            });
        }

        // 获取配置项
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/bzb/zhcx/pz",
            type: "GET",
            dataType: 'json',
            async: false,
            success: function (res) {
                if(res){
                    zhcxpz = res;
                    if(res.cxmd) {
                        $.each(res.cxmd.split(","), function (index, item) {
                            $('#cxmd').append('<option value="' + item + '">' + item + '</option>');
                        });
                        if(!isNullOrEmpty(zmcxmd)) {
                            $('#cxmd').val(zmcxmd);
                        }
                        form.render('select');
                    }
                }else{
                    failMsg("发现页面未配置正确，请联系管理员解决！");
                }
            },
            error: function () {
                failMsg("发现页面未配置正确，请联系管理员解决！");
            }
        });

        if (dybdclxZdList) {
            $.each(dybdclxZdList.dybdclx, function (index, item) {
                $('#dybdclx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            if(!isNullOrEmpty(dybdclxChild)) {
                $('#dybdclx').val(dybdclxChild);
            }
            form.render('select');
        }


        form.on('submit(submitBtn)', function(data) {
            var val = data.field;

            // 验证必要查询条件
            var count = 0;
            $(".required").each(function(i){
                var value= $(this).val();
                if(!isNullOrEmpty(value)){
                    count += 1;
                }
            });
            if(0 == count){
                warnMsg(" 请输入必要查询条件，例如权利人名称");
                return false;
            }

            // 如果只输入权利人名称，则通过配置控制证件号是否必填
            if(1 == zhcxpz.cxzjhbt){
                // 获取其它非权利人信息字段
                var otherCount = 0;
                $(".other").each(function(i){
                    var value= $(this).val();
                    if(!isNullOrEmpty(value)){
                        otherCount += 1;
                    }
                });

                // 权利人名称或证件号有值，其它字段无值情况下
                if(count > 0 && otherCount == 0){
                    var isZjhNull = false;
                    // 核查权利人证号及家庭成员证号
                    $(".zjh").each(function(){
                        var zjh = $(this).val();
                        if(isNullOrEmpty(zjh)){
                            isZjhNull = true;
                        }
                    });

                    if(isZjhNull){
                        warnMsg(" 请输入权利人证件号");
                        return false;
                    }
                }
            }

            // 获取家庭成员信息
            jtcy();
            // 设置查询目的
            parent.setCxmd($("#cxmd").val());
            // 设置抵押不动产类型
            parent.setDybdclx($("#dybdclx").val());
            //获取父页面中复选框权利类型的值
            val.qllx = parent.getQllxCheckedVal();
            val.qszt3 = parent.getQsztCheckedVal();

            //权利人曾用名有值,证件号自动带入权利人证件号
            if(!isNullOrEmpty(val.qlrcym)){
                val.qlrcymzjh =val.qlrzjh;
            }
            //义务人曾用名有值,证件号自动带入义务人证件号
            if(!isNullOrEmpty(val.ywrcym)){
                val.ywrcymzjh =val.ywrzjh;
            }

            parent.setSearchData(val, true);
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });

        // 获取家庭成员数据，缓存到父页面bdcZszm.html
        function jtcy() {
            var jtcyArray = new Array();

            for(var i = 1; i <= jtcyCount; i++) {
                var jtcy = {};

                var jtcymc = $("#jtcymc"+ i).val();
                if(!isNullOrEmpty(jtcymc)) {
                    jtcy.jtcymc = jtcymc;
                }

                var jtcyzjh = $("#jtcyzjh"+ i).val();
                if(!isNullOrEmpty(jtcyzjh)) {
                    jtcy.jtcyzjh = jtcyzjh;
                }

                var yhzgx = $("#yhzgx"+ i).val();
                if(!isNullOrEmpty(yhzgx)) {
                    jtcy.yhzgx = yhzgx;
                }

                var jtcycym = $("#jtcycym"+ i).val();
                if(!isNullOrEmpty(jtcycym)) {
                    jtcy.jtcycym = jtcycym;
                }

                jtcyArray.push(jtcy);
            }

            jtcyDataList = jtcyArray;
            parent.setJtcyData(jtcyArray);
        }

        //身份证读卡器设置
        window.onReadIdCard = function (qlrlb) {
            try {
                var cardInfo = readIdCard();
                if (cardInfo.ReadCard()) {
                    var name = cardInfo.Name;
                    var number = cardInfo.ID;

                    if (1 == qlrlb) {
                        $('input[name="qlrmc"]').val(trimStr(name));
                        $('input[name="qlrzjh"]').val(trimStr(number));
                    } else {
                        $('input[name="ywrmc"]').val(trimStr(name));
                        $('input[name="ywrzjh"]').val(trimStr(number));
                    }
                } else {
                    warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
                }
            } catch (objError) {
                warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
            }
        }

        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#submitBtn").click();
            }
        });

        $('.bdc-frame-close').on('click',function () {
            parent.setSearchData(null, false);
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
    });
});

/**
 * 传递查询参数
 */
window.setSearchData = function (data, jtcyData, cxmd, dybdclx) {
    jtcyDataList = jtcyData;
    zmcxmd = cxmd;
    dybdclxChild = dybdclx;
    layui.use(['form', 'jquery', 'laytpl'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laytpl = layui.laytpl;

        // 获取外层查询参数
        form.val('searchform', JSON.parse(JSON.stringify(data)));

        // 设置默认的字段模糊类型
        var mhlx = {"bdcdyhmhlx":3, "bdcqzhmhlx":3, "qlrmcmhlx":0, "qlrzjhmhlx":0, "ywrmcmhlx":0,
            "ywrzjhmhlx":0, "zlmhlx":3, "ycqzhmhlx":0, "zhlshmhlx":0, "fjhmhlx":0, "slbhmhlx":0,
            "fwbhmhlx":0, "zslshmhlx":0, "ybdcdyhmhlx":0, "yxtcqzhmhlx":0, "cfwhmhlx":3};
        form.val('searchform', JSON.parse(JSON.stringify(mhlx)));

        // 查询目的
        if(zhcxpz && zhcxpz.cxmd && !isNullOrEmpty(zmcxmd)) {
            $('#cxmd').val(zmcxmd);
            form.render('select');
        }

        if(!isNullOrEmpty(dybdclxChild)) {
            $('#dybdclx').val(dybdclxChild);
            form.render('select');
        }

        if(jtcyData && jtcyData.length > 0) {
            jtcyCount = jtcyData.length;

            for(var i = 1; i <= jtcyData.length; i++) {
                var getTpl = qlrTpl.innerHTML;
                laytpl(getTpl).render([], function(html){
                    html = html.replace(/jtcymc/g, "jtcymc" + i);
                    html = html.replace(/jtcyzjh/g, "jtcyzjh" + i);
                    html = html.replace(/yhzgx/g, "yhzgx" + i);
                    html = html.replace(/jtcycym/g, "jtcycym" + i);
                    $('.bdc-add-box').append(html);
                });
            }
            form.render('select');

            if(!yhzgxZdList || yhzgxZdList.length == 0) {
                yhzgxZdList = getMulZdList("yhzgx");
            }

            for(var i = 0; i < jtcyData.length; i++) {
                if(!isNullOrEmpty(jtcyData[i].jtcymc)) {
                    $("#jtcymc"+ (i+1)).val(jtcyData[i].jtcymc);
                }

                if(!isNullOrEmpty(jtcyData[i].jtcyzjh)) {
                    $("#jtcyzjh"+ (i+1)).val(jtcyData[i].jtcyzjh);
                }

                if(!isNullOrEmpty(jtcyData[i].jtcycym)) {
                    $("#jtcycym"+ (i+1)).val(jtcyData[i].jtcycym);
                }

                $.each(yhzgxZdList.yhzgx, function (index, item) {
                    $('#yhzgx' + (i + 1)).append('<option value="' + item.MC + '">' + item.MC + '</option>');
                });
                if (!isNullOrEmpty(jtcyData[i].yhzgx)) {
                    $("#yhzgx" + (i + 1)).val(jtcyData[i].yhzgx);
                }
            }
            form.render('select');
        }
    });
}

//身份证读卡器设置
window.onReadIdCard2 = function (obj) {
    try {
        if(obj && obj.id) {
            var nameId = "jtcymc1", zjhId = "jtcyzjh1";
            if (obj.id.indexOf("jtcymc") > -1) {
                nameId = obj.id;
                zjhId = "jtcyzjh" + obj.id.substring(obj.id.length - 1, obj.id.length);
            } else if (obj.id.indexOf("jtcyzjh") > -1) {
                zjhId = obj.id;
                nameId = "jtcymc" + obj.id.substring(obj.id.length - 1, obj.id.length);
            }

            console.log("#" + nameId);
            console.log("#" + zjhId);

            var cardInfo = readIdCard();
            if (cardInfo.ReadCard()) {
                var name = cardInfo.Name;
                var number = cardInfo.ID;
                $("#" + nameId).val(name);
                $("#" + zjhId).val(number);
            } else {
                warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
            }
        } else {
            warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
        }
    } catch (objError) {
        warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
    }
}