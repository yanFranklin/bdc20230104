/**
 * 综合查询--高级查询条件处理
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

var code = $.getUrlParam('code');
var zdList = getMulZdList("qlxz,fwyt,hysylxa,hysylxb");
layui.use(['form', 'jquery', 'element', 'formSelects', 'layer', 'laytpl'], function () {
    form = layui.form;
    var $ = layui.jquery;
    var laytpl = layui.laytpl;
    var formSelects = layui.formSelects;

    if(zdList.qlxz != null) {
        $.each(zdList.qlxz, function (index, item) {
            $("#qlxz").append('<option value="' + item.DM + '">' + item.MC + '</option>');
        })
    }
    if(zdList.fwyt != null || zdList.hysylxa != null || zdList.hysylxb != null) {
        $.each(zdList.fwyt, function (index, item) {
            $("#djyt").append('<option value="' + item.DM + ':fwyt'+'">' + item.MC + '</option>');
        })
        $.each(zdList.hysylxa, function (index, item) {
            $("#djyt").append('<option value="' + item.DM +':hysylxa'+ '">' + item.MC + '</option>');
        })
        $.each(zdList.hysylxb, function (index, item) {
            $("#djyt").append('<option value="' + item.DM +'hysylxb'+ '">' + item.MC + '</option>');
        })
    }
    $(function () {
        $($('select[disabled="disabled"]')[0]).after('<img src="../../static/lib/bdcui/images/lock.png" alt="">');
        //使用js渲染下拉框
        formSelects.data('select01', 'local', {
            arr: [
                {"name": "精确", "value": '0'},
            ]
        });

        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#submitBtn").click();
            }
        });

        if("nantong" == code){
            // TT系统证号
            $("#yxtcqzhid").hide();
            $("#qjgldmdiv").hide();
        } else {
            // 证书印制号
            $("#zsyzhid").hide();

            $("#lsswsid").hide();
            $("#lsid").hide();
            $("#lhgxrid").hide();
            $("#lhgxrzhid").hide();
            $("#mksfzdq").hide();
        }

        // 下拉选择添加删除按钮
        renderSelectClose(form);

        $('.bdc-frame-close').on('click',function () {
            parent.setSearchData(null, false);
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });


        //监听点击新增权利人
        $('.bdc-add-qlr').on('click',function () {
            var getTpl = qlrTpl.innerHTML;
            laytpl(getTpl).render([], function(html){
                $('.bdc-add-box').append(html);
                if(nameClickTimes != 1){
                    $("select[name='qlrmcmhlx']").val(nameSelect);
                }
                if (zjhClickTimes != 1) {
                    $("select[name='qlrzjhmhlx']").val(zjhSelect);
                }
            });

            form.render('select');
        });

        var btn = document.getElementById("mksfzdq");
        btn.onmousedown = function (e) {
            //现代浏览器阻止默认事件
            if (e && e.preventDefault) {
                e.preventDefault();
            }
            //IE阻止默认事件
            else {
                window.event.returnValue = false;
            }
            return false;
        }
        //监听权利人名称选择
        var nameClickTimes = 1;
        var nameSelect;
        form.on('select(qlrNameFilter)', function (data) {
            nameClickTimes++;
            nameSelect = data.value;
            $("select[name='qlrmcmhlx']").val(nameSelect);
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

        form.on('submit(submitBtn)', function(data) {
            var val = data.field;

            var qlrmc = new Array();
            $("input[name='qlrmc']").each(
                function(){
                    var mc = $(this).val();
                    if(mc && '' != mc){
                        qlrmc.push(mc);
                    }
                }
            );
            if(qlrmc && qlrmc.length > 0){
                val.qlrmc = qlrmc.join();
            }

            var qlrzjh = new Array();
            $("input[name='qlrzjh']").each(
                function(){
                    var zjh = $(this).val();
                    if(zjh && '' != zjh){
                        qlrzjh.push(zjh);
                    }
                }
            );
            if(qlrzjh && qlrzjh.length > 0){
                val.qlrzjh = qlrzjh.join();
            }

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

            //获取父页面中复选框权利类型的值
            if ("nantong" == code) {
                val.qllx = parent.getQllxCheckedVal();
                val.qszt3 = parent.getQsztCheckedVal();
                val.lssws = $("#lssws").val();
                val.lsmc = $("#lsmc").val();
                val.lhgxr = $("#lhgxr").val();
                val.lhgxrzh = $("#lhgxrzh").val();
            }
            var qjgldm = formSelects.value('selectQjgldm', 'valStr');
            val['qjgldm'] = qjgldm;
            parent.setSearchData(val, true);
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });

        //身份证读卡器设置
        window.onReadIdCard = function (qlrlb) {
            try {
                var cardInfo = readIdCard();
                if (cardInfo.ReadCard()) {
                    var name = $.trim(cardInfo.Name);
                    var number = $.trim(cardInfo.ID);

                    if (1 == qlrlb) {
                        $('input[name="qlrmc"]').val(name);
                        $('input[name="qlrzjh"]').val(number);
                    } else {
                        $('input[name="ywrmc"]').val(name);
                        $('input[name="ywrzjh"]').val(number);
                    }
                } else {
                    warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
                }
            } catch (objError) {
                warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
            }
        }

    });


});
// 获取权籍所属筛选配置项
function getQjssSxpz(){
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/getQjssSxpz",
        type: "GET",
        async:false,
        contentType: 'application/json',
        success: function (data) {
            if(data != null){
                $.each(data, function(index,item){
                    var qjgldm = item.split(":");
                    if(qjgldm.length == 2){
                        $("#qjgldm").append('<option value="'+qjgldm[1]+'">'+qjgldm[0]+'</option>');
                    }else{
                        warnMsg("行政区划筛选配置项不正确，无法加载！");
                        return false;
                    }
                })
                // form.render();
            } else {
                warnMsg("没有配置行政区划筛选项！");
            }
        },
        error: function () {
            failMsg("获取页面行政区划筛选项失败！");
        }
    });
};

function mkdqsfz() {
    addModel();
    getReturnData("/rest/v1.0/zszm/mkjk", {}, "GET", function (result) {
        removeModal();
        if (result) {
            if (result.code == "1") {
                //判断当前输入框的权利人名称有几个
                var qlrmcsrk = $('input[name="qlrmc"]');
                if (isNotBlank(qlrmcsrk) && qlrmcsrk.length > 0)
                    if (qlrmcsrk.length === 1) {
                        $('input[name="qlrmc"]').val($.trim(result.data.name));
                        $('input[name="qlrzjh"]').val($.trim(result.data.cardNum));
                    } else {
                        var qlrmcParent = $('input:focus[name="qlrmc"]').parents('.bdc-add-qlr-box');
                        //获取当前点击的输入框
                        if (isNotBlank(qlrmcParent)) {
                            qlrmcParent.find('input[name="qlrmc"]').val($.trim(result.data.name));
                            qlrmcParent.find('input[name="qlrzjh"]').val($.trim(result.data.cardNum));
                        }
                    }
            } else {
                ityzl_SHOW_WARN_LAYER("摩科接口获取权利人信息失败" + result.message)
            }
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    })
}

window.setData1 = function (data) {
    var formSelects
    layui.use(['form', 'jquery', 'element', 'formSelects', 'layer', 'laytpl'], function () {
        formSelects = layui.formSelects;
    });
    getQjssSxpz();
    layui.form.val('searchform', JSON.parse(JSON.stringify(data)));
    var mhlx = {};
    if (code == "nantong") {
        mhlx = {"bdcdyhmhlx":3, "bdcqzhmhlx":3, "qlrmcmhlx":3, "qlrzjhmhlx":3, "ywrmcmhlx":3,
            "ywrzjhmhlx":3, "zlmhlx":3, "ycqzhmhlx":3, "zhlshmhlx":0, "fjhmhlx":3, "slbhmhlx":3,
            "fwbhmhlx":3, "zslshmhlx":3, "ybdcdyhmhlx":3, "yxtcqzhmhlx":3,"zhmhlx":3, "zsyzhmhlx":0};
    }else{
        // 设置默认的字段模糊类型
        mhlx = {"bdcdyhmhlx":3, "bdcqzhmhlx":3, "qlrmcmhlx":0, "qlrzjhmhlx":0, "ywrmcmhlx":0,
            "ywrzjhmhlx":0, "zlmhlx":3, "ycqzhmhlx":3, "zhlshmhlx":3, "fjhmhlx":3, "slbhmhlx":0,
            "fwbhmhlx":0, "zslshmhlx":0, "ybdcdyhmhlx":0, "yxtcqzhmhlx":3};
    }

    layui.form.val('searchform', JSON.parse(JSON.stringify(mhlx)));
    if(data['qjgldm'] != null){
        //重新渲染
        formSelects.render("selectQjgldm");
        $("#qjgldmdiv").show();
        formSelects.value('selectQjgldm',  data['qjgldm'].split(","));
    }else {
        $("#qjgldmdiv").hide();
    }
}