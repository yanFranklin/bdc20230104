//字典列表
var zdList;
//地址栏获取数据
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var zxlc = getQueryString("zxlc");
var xmid = getQueryString("xmid");
var oldScjydjsj = "";
var slbh;
var sply;
var $,laytpl,form;

//监听转入转出方,家庭成员是否新增
var isAddSqrJtcy =false;
//规则验证入参
var gzyzParamMap ={};
var jtcyList = [];
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    $ = layui.jquery;
    var element = layui.element;
    laytpl = layui.laytpl;
    var layer = layui.layer;

    // 监听单选
    form.on('radio(zstdcrj)', function (data) {
        var $zspmTab=$($('#ycsltdsyjxx').find(".bdc-zspm")[0]);
       if(data.value =="1"){
           $zspmTab.find('select').find("option:eq(1)").attr("selected","selected");
           var zspmVal =$zspmTab.find("[name='zspm']").val();
           if(isNotBlank(zspmVal)) {
               //截取前5位
               $zspmTab.find("[name='zsxm']").val(zspmVal.substring(0, 5));
           }
           form.render("select");
           resetSelectDisabledCss();
       }else if(data.value =="0"){
           $('#ycsltdsyjxx').find('select').find("option:eq(0)").attr("selected","selected");
           form.render("select");
           resetSelectDisabledCss();
       }
    });

    //监听征收品目
    form.on('select(zspm)',function (data){
        var zspmVal =data.value;
        if(isNotBlank(zspmVal)) {
            //截取前5位
            $(this).parents(".bdc-zspm").find("[name='zsxm']").val(zspmVal.substring(0, 5));
            form.render("select");
            resetSelectDisabledCss();
        }
    });

    //点击提交时不为空的全部标红
    var isSubmit = true;
    var isFirst = true;
    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            if (value == '' || value == null || value == undefined) {//判断是否为空
                $(item).addClass('layui-form-danger');
                isSubmit = false;
            }
        }
        , identitynew: function (value, item) {
            var msg = checkSfzZjh(value);
            if (isNotBlank(msg)) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = msg;
            }
        }
        ,number:function (value, item) {
            //为非负整数,允许为空
            if (isNotBlank(value) && !/^[1-9]+[0-9]*]*$/.test(value)) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "请输入非负整数";
            }
        }
        //数字
        , number2: [/^-?\d*$/, '请输入数字']
    });
    //保存
    form.on("submit(saveData)", function (data) {
        //验证保存数据
        var checkMsg =checkSaveData();
        if(isNotBlank(checkMsg)){
            layer.alert(checkMsg);
            return false;
        }
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            isFirst = true;
            return false;
        } else {
            //调用规则验证
            var bdcGzYzQO ={};
            bdcGzYzQO.zhbs = "saveYcslData";
            gzyzParamMap.xmid =xmid;
            bdcGzYzQO.paramMap = gzyzParamMap;
            addModel();
            gzyzCommon(2,bdcGzYzQO,function (data) {
                // 对同一规则需要多次验证的规则进行验证
                var gzYzQO = {};
                gzYzQO.zhbs = "saveYcslData_loop";
                gzYzQO.paramList = getGzYzParamList();

                doGzyz("/bdcGzyz",gzYzQO,function (data) {
                    console.log("验证通过");
                    setTimeout(function () {
                        try {
                            $.when(saveYcslXx()).done(function () {
                                // 本次的上次交易登记时间和 页面初始的时间不一致则要记录下来
                                if(oldScjydjsj!="" && oldScjydjsj != $("#scjydjsj").val()){
                                    insertScjydjsjLog();
                                }
                                removeModal();
                                ityzl_SHOW_SUCCESS_LAYER("保存成功");
                            })
                        } catch (e) {
                            removeModal();
                            if (e.message) {
                                showAlertDialog(e.message);
                            } else {
                                delAjaxErrorMsg(e);

                            }
                            return
                        }
                     }, 10);
                 });
            });


            return false;

        }

    });

    $(function () {
        addModel();
        //获取字典列表、加载基本信息
        setTimeout(function () {
            try {
                $.when(loadButtonArea(),loadZdData(),loadJbxx(),loadYcslxx()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e,"加载失败");
                return
            }
        }, 50);

        //房屋信息查询
        $(document).on('click', '#fwxxcx', function (){
            var url =getContextPath()+"/view/query/fwxx.html?xmid="+xmid;
            layerCommonOpenFull(url,"房屋信息查询",'1300px','600px');

        });

        //家庭成员查询
        $(document).on('click', '.getJtcyxx', function () {
            addModel();
            var $qlrbasic =$(this).parents("tr");
            var qlrmc =$qlrbasic.find("input[name='mc']").val();
            var qlrzjh=$qlrbasic.find("input[name='zjh']").val();
            var sqrid=$qlrbasic.find("input[name='id']").val();
            if(!isNotBlank(qlrmc) ||!isNotBlank(qlrzjh)){
                showAlertDialog("申请人名称或者证件号为空");
                removeModal();
                return false;
            }

            getReturnData("/slym/jtcy/hefei/getJtcyxx",{sply:sply,qlrmc:qlrmc,qlrzjh:qlrzjh,sqrid:sqrid,slbh:slbh,xmid:xmid},"GET",function (data) {
                if(sply == 5 || sply == 11){
                    jtcyList = data;
                    console.log(jtcyList);
                    openJtcyYm();
                }else{
                    loadYcslxx();
                    ityzl_SHOW_SUCCESS_LAYER("查询成功");
                }
                removeModal();
            },function (error) {
                delAjaxErrorMsg(error);
            })
        });

        //还原合并页面
        $(document).on('click', '.jtcyGroupHy', function (){
            addModel();
            var $qlrbasic =$(this).parents("tr");
            var sqrlb =$qlrbasic.find("input[name='sqrlb']").val();
            $.ajax({
                url: getContextPath() + "/slym/sqr/getSqrxxGroupByJtWithSqrlb",
                type: 'GET',
                dataType: 'json',
                async: false,
                data: {xmid: xmid,sqrlb:sqrlb,sfhb:false},
                success: function (data) {
                    removeModal();
                    console.log(data);
                    generateSqrJtcyxx(data,parseInt(sqrlb));
                    //修改按钮隐藏
                   if(parseInt(sqrlb) ===1) {
                       $("#ycslzrfxx .jtcyGroupHy").addClass("bdc-hide");
                   }else if(parseInt(sqrlb) ===2) {
                       $("#ycslzcfxx .jtcyGroupHy").addClass("bdc-hide");
                   }

                    renderForm();
                    form.render('select');
                    disabledAddFa();

                }
                , error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
            });


        });

        //推送税务信息
        $(document).on('click', '.tsSwxx', function () {
            var id =$(this)[0].id;
            var beanName =id;
            if(id ==="tsClfSwxx"){
                beanName="tsSwxxClf";
            }else if(id ==="tsSpfSwxx"){
                beanName="tsSwxxSpf";
            }else if(id === "tsClfSwxx_dk"){
                beanName="tsSwxxClf_dk";
            }else if(id === "tsSpfSwxx_dk"){
                beanName="tsSwxxSpf_dk";
            }
            addModel();
            //调用规则验证
            var bdcGzYzQO ={};
            bdcGzYzQO.zhbs = "tsSwxx";
            gzyzParamMap.xmid =xmid;
            bdcGzYzQO.paramMap = gzyzParamMap;
            gzyzCommon(2,bdcGzYzQO,function (data) {
                console.log("验证通过");
                $.ajax({
                    url: getContextPath() + "/slym/sw/tsSwxx",
                    type: 'GET',
                    dataType: 'json',
                    data: {gzlslid: processInsId, beanName: beanName},
                    success: function (data) {
                        removeModal();
                        if (data && data.responseMsg.indexOf("成功") > -1) {
                            ityzl_SHOW_SUCCESS_LAYER("推送成功");
                        } else {
                            showAlertDialog("推送失败" + (data && data.responseMsg ? (":" + data && data.responseMsg) : ""));
                        }
                        loadTsJsSwsj();
                    }, error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });

            });

        });

        //查询税务信息
        $(document).on('click', '#querySwxx', function () {
            addModel();
            $.ajax({
                url: getContextPath() + "/slym/sw/getSwxx",
                type: 'GET',
                dataType: 'json',
                data: {xmid: xmid, slbh: slbh},
                success: function (data) {
                    removeModal();
                    if (data && data.responseMsg === "成功") {
                        if (data.jbrxx != null) {
                            showAlertDialog("正在办理中，经办人：" + data.jbrxx.jbrxm + "，经办人电话：" + data.jbrxx.jbrdh);
                        } else {
                            ityzl_SHOW_SUCCESS_LAYER("查询成功");
                        }
                    } else {
                        showAlertDialog(data ? data.responseMsg : "查询失败");
                    }
                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });

        });

        //取消作废税务信息
        $(document).on('click', '#qxzfSwxx', function () {
            //调用规则验证
            var bdcGzYzQO ={};
            bdcGzYzQO.zhbs = "qxzfSwxx";
            var gzyzParamMap ={};
            gzyzParamMap.xmid =xmid;
            gzyzParamMap.gzlslid =processInsId;
            bdcGzYzQO.paramMap = gzyzParamMap;
            gzyzCommon(2,bdcGzYzQO,function (data) {
                console.log("验证通过");
                var submitIndex = layer.open({
                    type: 1,
                    title: '提示信息',
                    skin: 'bdc-small-tips',
                    area: ['320px'],
                    content: '您确定要作废税务信息吗？',
                    btn: ['确定', '取消'],
                    btnAlign: 'c',
                    yes: function () {
                        //确定操作
                        console.log('确定');
                        layer.close(submitIndex);
                        addModel();
                        //调取作废接口

                        $.ajax({
                            url: getContextPath() + "/slym/sw/qxzfSwxx",
                            type: 'GET',
                            dataType: 'json',
                            data: {xmid: xmid, slbh: slbh},
                            success: function (data) {
                                removeModal();
                                if (data && data.responseCode === "200") {
                                    loadYcslxx();
                                    ityzl_SHOW_SUCCESS_LAYER("取消作废税务信息成功");
                                } else {
                                    showAlertDialog("操作失败" + (data && data.responseMsg ? (":" + data.responseMsg) : ""));
                                }
                            }, error: function (xhr, status, error) {
                                removeModal();
                                delAjaxErrorMsg(xhr)
                            }
                        });
                    },
                    btn2: function () {
                        //取消
                    }
                });
            });
        });

        //查询税务信息
        $(document).on('click', '#querySwxxDk', function () {
            addModel();
            $.ajax({
                url: getContextPath() + "/slym/sw/getSwxx",
                type: 'GET',
                dataType: 'json',
                data: {xmid: xmid, slbh: slbh,beanName: 'swCxxx_dk'},
                success: function (data) {
                    removeModal();
                    if (data && data.responseCode === "200") {
                        if (data.jbrxx != null) {
                            showAlertDialog("正在办理中，经办人：" + data.jbrxx.jbrxm + "，经办人电话：" + data.jbrxx.jbrdh);
                        } else {
                            ityzl_SHOW_SUCCESS_LAYER("查询成功");
                        }
                        loadTsJsSwsj();
                    } else {
                        showAlertDialog(data ? data.responseMsg : "查询失败");
                    }
                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });

        });

        //取消作废税务信息
        $(document).on('click', '#qxzfSwxxDk', function () {
            //调用规则验证
            var bdcGzYzQO ={};
            bdcGzYzQO.zhbs = "qxzfSwxx";
            var gzyzParamMap ={};
            gzyzParamMap.xmid =xmid;
            gzyzParamMap.gzlslid =processInsId;
            bdcGzYzQO.paramMap = gzyzParamMap;
            gzyzCommon(2,bdcGzYzQO,function (data) {
                console.log("验证通过");
                var submitIndex = layer.open({
                    type: 1,
                    title: '提示信息',
                    skin: 'bdc-small-tips',
                    area: ['320px'],
                    content: '您确定要作废税务信息吗？',
                    btn: ['确定', '取消'],
                    btnAlign: 'c',
                    yes: function () {
                        //确定操作
                        console.log('确定');
                        layer.close(submitIndex);
                        addModel();
                        //调取作废接口

                        $.ajax({
                            url: getContextPath() + "/slym/sw/qxzfSwxx",
                            type: 'GET',
                            dataType: 'json',
                            data: {xmid: xmid, slbh: slbh, system:'dk'},
                            success: function (data) {
                                removeModal();
                                if (data && data.responseCode === "200") {
                                    loadYcslxx();
                                    ityzl_SHOW_SUCCESS_LAYER("取消作废税务信息成功");
                                } else {
                                    showAlertDialog("操作失败" + (data && data.responseMsg ? (":" + data.responseMsg) : ""));
                                }
                            }, error: function (xhr, status, error) {
                                removeModal();
                                delAjaxErrorMsg(xhr)
                            }
                        });
                    },
                    btn2: function () {
                        //取消
                    }
                });
            });
        });
        //材料上传按钮点击事件
        $(document).on('click', '#fjsc', function () {
            addModel("推送中");
            getReturnData("/slym/sw/tsfjcl",{gzlslid:processInsId},"GET",function (data) {
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER("推送成功");
            },function (xhr) {
                removeModal();
                ityzl_SHOW_WARN_LAYER("推送失败");
                delAjaxErrorMsg(xhr);
            })
        });
        //新增家庭成员
        $(document).on('click', '.jtcy-add', function () {
            isAddSqrJtcy =true;
            var sqrDTO={};
            var id = this.id;
            var $parent =$(this).parents('tr'),
                view,rowlen;
            var sqrlb =$parent.find("input[name=sqrlb]").val();
            sqrDTO["sqrlb"] = sqrlb;
            var json = {
                zd: zdList,
                sqrDTO:sqrDTO
            };

            // 根据按钮标识判断按钮位置

            if($parent.hasClass('sqrgroup-first')){
                var nextbtn = $parent.next().find('.layui-btn');
                if($parent.next().length == 0 || nextbtn.parents('tr').hasClass('sqrgroup-first')){
                    view = $parent;
                }else {
                    view =$parent.next();
                }
                // 更改需合并字段的列数
                rowlen = $parent.find('[name="fwtc"]').parents('td').attr('rowspan');
                $parent.find('[name="fwtc"]').parents('td').attr('rowspan',parseInt(rowlen)+1);
                $parent.find('[name="sbfwtc"]').parents('td').attr('rowspan',parseInt(rowlen)+1);

            }else {
                view = $parent;
                // 更改需合并字段的列数
                rowlen = $parent.prev().find('[name="fwtc"]').parents('td').attr('rowspan');
                $parent.prev().find('[name="fwtc"]').parents('td').attr('rowspan',parseInt(rowlen)+1);
                $parent.prev().find('[name="sbfwtc"]').parents('td').attr('rowspan',parseInt(rowlen)+1);
            }

            var getTpl = addJtcyTpl.innerHTML;
            laytpl(getTpl).render(json, function (html) {
                view.after(html);
                form.render();
                renderForm();
                disabledAddFa();
            });
            form.render();
        });

        //通知省平台
        $(document).on('click', '#tzspt', function () {
            addModel("通知中···")
            getReturnData("/ycsl/tzspt", {gzlslid: processInsId}, "GET", function (data) {
                removeModal();
                if (data.code === 200) {
                    ityzl_SHOW_SUCCESS_LAYER("通知成功");
                } else {
                    ityzl_SHOW_WARN_LAYER("通知失败");
                }
            }, function (xhr) {
                removeModal();
                ityzl_SHOW_WARN_LAYER("通知失败");
                delAjaxErrorMsg(xhr);
            })
        });

        //上次交易合同登记时间修改验证
        $('.bdc-form-div').on('focus', '#scjydjsj', function () {
            var confimLayer = layer.open({
                type: 1,
                title: '提示',
                skin: 'bdc-small-tips',
                area: ['320px'],
                content: '您确定要修改上次交易合同登记时间？',
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function(){
                    //确定操作
                    // 记录老的时间
                    // 只记录一次，当有值的时候说明已经保存过一次，此时不再记录保存后的，只记录原始的
                    if(oldScjydjsj == ""){
                        oldScjydjsj = $("#scjydjsj").val();
                    }
                    layer.close(confimLayer);
                    setTimeout(function () {
                        $('.bdc-form-div #scjydjsj').click();
                    },100);
                }
            });
        });

        //监听 满五唯一 选择
        form.on('select(jtmwwyzz)', function(data){
            var $fwtc = $(data.elem).parents('tr').find('select[name=fwtc]');
            var ysqrgx = $(data.elem).parents('tr').find('select[name=ysqrgx]').val();
            if(ysqrgx == "本人"){
                if(data.value == 1 || data.value == ''){
                    // 是满五唯一，则更新房屋套次的值，并后台更新申请人信息
                    $fwtc.removeAttr('disabled');
                    $fwtc.parent().children('img').remove();
                    $fwtc.find('option[value=]').attr('selected','selected');
                    $(data.elem).parents("tr").find('.bdc-fwtc').val(''); //重置原房屋套次的值
                    form.render('select');
                    modifySqrxx({
                        sqrid : $(data.elem).parents('tr').find('input[name=id]').val(),
                        jtmwwyzz : data.value,
                        fwtc: $fwtc.val()
                    });
                    resetSelectDisabledCss();
                }else {
                    $fwtc.attr('disabled','off');
                    $fwtc.find('option').removeAttr('selected');
                    $fwtc.find('option[value=9]').attr('selected','selected');
                    form.render('select');
                    disabledAddFa();
                    resetSelectDisabledCss();
                }
            }
        });

        // 用于更新申请人家庭满五唯一住宅、房屋套次信息
        function modifySqrxx(sqr){
            console.info(sqr);
            $.ajax({
                url: getContextPath() + "/slym/sqr",
                type: 'PATCH',
                dataType: 'json',
                async: false,
                contentType: "application/json",
                data: JSON.stringify(sqr),
                success: function (data) {},
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        //监听行政区划选择
        form.on('select(xzqh)', function(data){
            jddmDz(data.value);
            form.render('select');
            resetSelectDisabledCss();
        });

        //监听房屋套次修改
        form.on('select(fwtc)', function(data){
            var yfwtc = $(data.elem).parents("tr").find('.bdc-fwtc').val();
            if(!isNotBlank(data.value) ||parseInt(yfwtc) > parseInt(data.value)){
                $(data.elem).val(yfwtc);
                form.render("select");
                resetSelectDisabledCss();
                ityzl_SHOW_WARN_LAYER("房屋套次不能选择空或者小于原有值");
                return false;

            }

        });

        //监听证件种类选择
        form.on('select(zjzl)', function(data){
            addSfzYz(data.value, data.elem);
        });


        //监听网签状态
        form.on('select(htzt)', function(data){
            var htzt =data.value;
            var jyxxid =$("input[name='jyxxid']").val();
            if(htzt ==="2"){
                //非网签
                getReturnData("/ycsl/generateHtbh?jyxxid="+jyxxid,{},"POST",function (data) {
                    $("#htbh").val(data);
                    $("#htbh").attr('disabled','off');
                    $("#htbah").val(data);
                    $("#htbah").attr('disabled','off');

                },function (error) {
                    delAjaxErrorMsg(error);

                })

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
    });

    //验证保存数据
    function checkSaveData(){
        var msg ="";
        //土地出让金校验
        var $tdcrjArray = $(".tdcrj");
        var tdcrjArrayData=[];
        var tdcrjData={};
        if ($tdcrjArray.length > 0) {
            var zstdcrj = $("input[name='zstdcrj']:checked").val();
            var zstdqs = $("input[name='zstdqs']:checked").val();
            $tdcrjArray.serializeArray().forEach(function (item, index) {
                tdcrjData[item.name] = item.value;
                if ((index + 1 < $tdcrjArray.length && $tdcrjArray[index + 1].name === 'tdcrjid') || index + 1 == $tdcrjArray.length) {
                    if (isNotBlank(tdcrjData.zspm)) {
                        tdcrjData.xmid = xmid;
                        tdcrjData.zstdcrj = zstdcrj;
                        tdcrjData.zstdqs = zstdqs;
                        tdcrjArrayData.push(tdcrjData);
                    }
                    tdcrjData = {};
                }
            });
        }
        if(tdcrjArrayData.length >0) {
            for (var i = 0; i < tdcrjArrayData.length; i++) {
                //zsxm为30148,zsbl为0或空时,激活征收金额填写框，且征收金额必填且大于0
                if (tdcrjArrayData[i].zsxm === "30148") {
                    if((isNullOrEmpty(tdcrjArrayData[i].zsbl) ||tdcrjArrayData[i].zsbl == 0)) {
                        if (tdcrjArrayData[i].zsje <= 0) {
                            if (isNotBlank(msg)) {
                                msg += "<br>";
                            }
                            msg += "土地出让金征收项目为国有土地使用权出让收入,征收比例为0或空时,征收金额必须大于0";
                        }
                    }else{
                        if (tdcrjArrayData[i].zsje != 0) {
                            if (isNotBlank(msg)) {
                                msg += "<br>";
                            }
                            msg += "土地出让金征收项目为国有土地使用权出让收入,征收比例不为空时,征收金额必须为0";
                        }
                    }
                }
                //多明细费款时，30146及30147的征收金额必须不为0或空。
                if ((tdcrjArrayData[i].zsxm === "30146" || tdcrjArrayData[i].zsxm === "30147") && tdcrjArrayData.length > 1) {
                    if (tdcrjArrayData[i].zsje <= 0) {
                        if(isNotBlank(msg)){
                            msg +="<br>";
                        }
                        msg += "多明细费款时,土地出让金征收项目为国有土地收益基金收入或农业土地开发资金收入时,征收金额必须大于0";
                    }
                }
            }
        }
        return msg;
    }


    //按钮加载
    function loadButtonArea() {
        var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
        //渲染数据
        laytpl(tpl).render({}, function (html) {
            view.innerHTML = html;
        });
        form.render();
        renderForm();
        //getStateById(readOnly, formStateId, "ycym");
        disabledAddFa();
    }

    function loadZdData() {
        $.ajax({
            url: getContextPath() + "/bdczd",
            type: 'POST',
            dataType: 'json',
            async: false,
            success: function (data) {
                if (isNotBlank(data)) {
                    zdList = data;
                }
            }
        });
    }

    window.loadYcslxx = function (){
        $.ajax({
            url: getContextPath() + "/ycsl",
            type: 'GET',
            dataType: 'json',
            async: false,
            data: {gzlslid: processInsId, xmid: xmid},
            success: function (data) {
                console.log(data);
                //加载合同房屋信息信息
                generateHtFwxx(data.bdcSlJyxxDO,data.bdcSlFwxxDO);
                //加载交易差额征收信息
                loadJyCezsxx(data.bdcSlJyxxDO.gscezs, data.bdcSlJyxxCezsDO);
                //加载拆迁安置信息
                generateCqazxx(data.bdcSlJyxxDO);
                //加载土地收益金信息
                generateTdsyjxx(data.bdcSlTdcrjDOList);
                //加载推送接受税务信息
                loadTsJsSwsj();
                loadZrzcfxx();

                form.render();
                renderSelect();
                renderForm();
                form.render("select");
                disabledAddFa();
                renderDate(form);
                removeModal();
                reloadZjhYz();
            }
        });
    };

    window.loadJyCezsxx = function(gscezs, bdcSlJyxxCezsDo){
        var json = {
            gscezs: gscezs,
            zd: zdList,
            bdcSlJyxxCezs : bdcSlJyxxCezsDo
        };
        var tpl = cezsTpl.innerHTML, view = document.getElementById('ycslcezs');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
    }

    window.loadTsJsSwsj = function(){
        getReturnData("/ycsl/tsjssj",{gzlslid:processInsId},"GET",function (data) {
            if(data) {
                var json = {
                    swtssj: data.swtssj,
                    swjssj: data.swjssj,
                    qyjyzsbz: data.qyjyzsbz
                };
                var tpl = jstssjTpl.innerHTML, view = document.getElementById('ycslswtsjssj');
                //渲染数据
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = html;
                });
                form.render();
            }
        },function (xhr) {
            removeModal();
            delAjaxErrorMsg(xhr);
        })
    }

    window.loadZrzcfxx = function (){
        $.ajax({
            url: getContextPath() + "/slym/sqr/getSqrxxGroupByJt",
            type: 'GET',
            dataType: 'json',
            async: false,
            data: {xmid: xmid},
            success: function (data) {
                console.log(data);
                generateSqrJtcyxx(data.bdcQlrGroupDTOLists,1);
                generateSqrJtcyxx(data.bdcYwrGroupDTOLists,2);
            }
        });
    };


    window.generateHtFwxx =  function(bdcSlJyxxDO,bdcSlFwxx) {
        var json = {
            bdcSlJyxx: bdcSlJyxxDO,
            zd: zdList,
            bdcSlFwxx: bdcSlFwxx
        };
        var tpl = htxxTpl.innerHTML, view = document.getElementById('ycsljyxx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        //行政区划与街道代码联动
        if(bdcSlFwxx != null &&isNotBlank(bdcSlFwxx.xzqh)) {
            jddmDz(bdcSlFwxx.xzqh,bdcSlFwxx.jddm);
        }
        // 渲染完数据后 需要查询上次交易登记时间是否改变 改变则需要高亮
        window.setTimeout(queryScjydjsjLog,300);
    };


    window.generateCqazxx =  function(bdcSlJyxxDO) {
        var json = {
            bdcSlJyxx: bdcSlJyxxDO,
            zd: zdList
        };
        var tpl = cqazTpl.innerHTML, view = document.getElementById('ycslcqaz');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });

    };

    window.generateTdsyjxx =  function(bdcSlTdcrjDOList) {
        if(bdcSlTdcrjDOList ==null ||bdcSlTdcrjDOList.length ===0){
            var bdcSlTdcrj={};
            var bdcSlTdcrjDOList =[];
            bdcSlTdcrjDOList.push(bdcSlTdcrj);
        }
        var json = {
            bdcSlTdcrj: bdcSlTdcrjDOList[0],
            zd: zdList
        };
        var tpl = tdsyjxxTpl.innerHTML, view = document.getElementById('ycsltdsyjxx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });

    };


    //加载申请人信息
    window.generateSqrJtcyxx = function(bdcSqrGroupDTOLists,sqrlb) {
        var json = {
            bdcSqrGroupDTOLists: bdcSqrGroupDTOLists,
            zd: zdList,
            sqrlb:sqrlb,
            sply:sply
        };
        var view;
        if(sqrlb ===1) {
            view = document.getElementById('ycslzrfxx');
        }else{
            view = document.getElementById('ycslzcfxx');
        }
        if(view != null) {
            var tpl = sqrxxTpl.innerHTML;
            //渲染转入方数据
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
        }
    };

    //个税差额征收，单选框事件绑定
    form.on('radio(gscezs)', function(data){
        if(data.value == "1"){
            $("#bdcSlJyxxCezs").show();
        }else{
            $("#bdcSlJyxxCezs").hide();
        }
    });
});

//加载基本信息模块（入口）
function loadJbxx() {
    getReturnData("/slym/xm/listYcslXm", {gzlslid: processInsId}, 'GET', function (data) {
        if(data &&data.length >0) {
            //判断页面入参是否存在xmid
            if (!isNotBlank(xmid)) {
                xmid = data[0].xmid;
            }
            slbh = data[0].slbh;
            sply = data[0].sply;
            gzyzParamMap.bdcdyh = data[0].bdcdyh;
        }else{
            layer.alert("当前未生成涉税数据，请确认！");
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    },false);
}

function saveHtxx(formClass) {
    var htxxData = {};
    var htxxHtml = document.getElementById('ycsljyxx');
    if (isNotBlank(htxxHtml)) {
        var htxxArray = $(formClass);
        if (htxxArray !== null && htxxArray.length > 0) {
            htxxArray.serializeArray().forEach(function (item, index) {
                htxxData[item.name] = item.value;
            });
            htxxData.xmid = xmid;
            if($("input[name='gscezs']").length > 0){
                htxxData.gscezs = $("input[name='gscezs']:checked").val()
            }
            $.ajax({
                url: getContextPath() + "/ycsl/htxx",
                type: 'PATCH',
                dataType: 'json',
                async: false,
                contentType: "application/json",
                data: JSON.stringify(htxxData),
                success: function (data) {
                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }
    }
}

function saveFwxx(formClass) {
    var fwxxData = {};
    var fwxxArray = $(formClass);
    if (fwxxArray !== null && fwxxArray.length > 0) {
        fwxxArray.serializeArray().forEach(function (item, index) {
            fwxxData[item.name] = item.value;
        });
        fwxxData.xmid = xmid;
        $.ajax({
            url: getContextPath() + "/ycsl/fwxx",
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(fwxxData),
            success: function (data) {
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }

}

function saveJyxxCezs(formClass){
    var $gscezs = $("input[name='gscezs']");
    if($gscezs.length > 0 && $gscezs.val() == "1"){
        var cezsData = {};
        var $cezsArray = $(formClass);
        if ($cezsArray.length > 0) {
            $cezsArray.serializeArray().forEach(function (item, index) {
                cezsData[item.name] = item.value;
            });
            cezsData.xmid = xmid;
            $.ajax({
                url: getContextPath() + "/ycsl/jyxxcezs",
                type: 'PATCH',
                dataType: 'json',
                async: false,
                contentType: "application/json",
                data: JSON.stringify(cezsData),
                success: function (data) {
                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }
    }
}

function saveTdcrjxx(formClass){
    var tdcrjArrayData =[];
    var tdcrjData = {};
    var $tdcrjArray = $(formClass);
    if ($tdcrjArray.length > 0) {
        $tdcrjArray.serializeArray().forEach(function (item, index) {
            tdcrjData[item.name] = item.value;
        });
        tdcrjData.xmid = xmid;
        tdcrjData.zstdcrj = $("input[name='zstdcrj']:checked").val();
        tdcrjData.zstdqs = $("input[name='zstdqs']:checked").val();
        tdcrjArrayData.push(tdcrjData);
        $.ajax({
            url: getContextPath() + "/rest/v1.0/tdcrj",
            type: 'POST',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(tdcrjArrayData),
            success: function (data) {
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }
}

//保存受理申请人信息(申请人以及家庭成员)
function saveSqrDTO() {
    var bdcSlSqrDTOList = [];
    var $ycslzrfxx = $('#ycslzrfxx');
    if ($ycslzrfxx.length > 0) {
        //一个家庭申请人列表
        var bdcSlSqrListForJt = [];
        //组织配偶方家庭成员信息
        var poJtcyList =[];
        //家庭总行数
        var jtrowlen = 0;
        var trIndex=0;
        //家庭套次
        var fwtc = "";
        var sbfwtc="";
        //循环表格行
        $(".bdc-jt-table[name =sqrGroupDTOTable] tbody tr").each(function () {
            var $tr =$(this);
            var bdcSlSqrDTO = {};
            if($tr.hasClass('sqrgroup-first')){
                //房屋套次
                fwtc =$tr.find('[name="fwtc"]').val();
                sbfwtc =$tr.find('[name="sbfwtc"]').val();
                jtrowlen = parseInt($tr.find('[name="fwtc"]').parents('td').attr('rowspan'));
                //生成新的一组数据
                bdcSlSqrListForJt =[];
                trIndex=0;
            }
            trIndex++;
            var bdcSlSqrGroupDTOArray = $tr.find(":input").serializeArray();
            if (bdcSlSqrGroupDTOArray !== null && bdcSlSqrGroupDTOArray.length > 0) {
                var bdcSlSqrGroupDTO ={};
                bdcSlSqrGroupDTOArray.forEach(function (item, index) {
                    bdcSlSqrGroupDTO[item.name] = item.value;
                });
                bdcSlSqrGroupDTO.fwtc =fwtc;
                bdcSlSqrGroupDTO.sbfwtc =sbfwtc;

                //本人时权利人信息不可编辑时前面获取不到值
                var ysqrgx = $tr.find('select[name=ysqrgx]').val();
                if(isNotBlank(ysqrgx)){
                    bdcSlSqrGroupDTO.ysqrgx =ysqrgx;
                }
                var mc = $tr.find('input[name=mc]').val();
                if(isNotBlank(mc)){
                    bdcSlSqrGroupDTO.mc =mc;
                }
                var zjzl = $tr.find('select[name=zjzl]').val();
                if(isNotBlank(zjzl)){
                    bdcSlSqrGroupDTO.zjzl =zjzl;
                }
                var zjh = $tr.find('input[name=zjh]').val();
                if(isNotBlank(zjh)){
                    bdcSlSqrGroupDTO.zjh =zjh;
                }
                if(isNotBlank(bdcSlSqrGroupDTO.mc)){

                    if(bdcSlSqrGroupDTO.ysqrgx ==="本人"){
                        //申请人信息
                        bdcSlSqrGroupDTO.sqrmc =bdcSlSqrGroupDTO.mc;
                        bdcSlSqrGroupDTO.sqrid =bdcSlSqrGroupDTO.id;
                        bdcSlSqrDTO.bdcSlSqrDO =bdcSlSqrGroupDTO;
                        bdcSlSqrListForJt.push(bdcSlSqrDTO);
                    }else{
                        //家庭成员信息,同一组的申请人共有其家庭成员信息
                        for(var i=0;i<bdcSlSqrListForJt.length;i++){
                            var yjtcyList =bdcSlSqrListForJt[i].bdcSlJtcyDOList;
                            if(yjtcyList == null){
                                yjtcyList =[];
                            }
                            bdcSlSqrGroupDTO.jtcymc =bdcSlSqrGroupDTO.mc;
                            //多个申请人共有家庭成员时不赋值jtcyid
                            if(bdcSlSqrListForJt.length ===1) {
                                bdcSlSqrGroupDTO.jtcyid =bdcSlSqrGroupDTO.id;
                            }
                            yjtcyList.push(bdcSlSqrGroupDTO);
                            bdcSlSqrListForJt[i].bdcSlJtcyDOList =yjtcyList;
                        }

                    }

                    //将证件号中小写字母改为大写
                    toUpperClass(bdcSlSqrGroupDTO,["zjh"]);

                    //获取证件种类和证件号
                    var getZjzl = bdcSlSqrGroupDTO.zjzl,
                        getZjh = bdcSlSqrGroupDTO.zjh;
                    // 验证转入方申请人及家庭成员身份证是否为18位
                    if(bdcSlSqrGroupDTO.sqrlb === "1"){
                        if(getZjzl === "1" && getZjh.length !== 18){
                            throw err = new Error( bdcSlSqrGroupDTO.mc +' '+ '身份证号必须为18位');
                        }
                    }

                    //验证未成年子女或兄妹是否满18岁
                    if((bdcSlSqrGroupDTO.ysqrgx == '未成年子女' || bdcSlSqrGroupDTO.ysqrgx == '未成年兄妹') && getZjzl === "1" && (bdcSlSqrGroupDTO.zjh.length == 15 || bdcSlSqrGroupDTO.zjh.length == 18)) {
                        var date = new Date();
                        var yearfull = date.getFullYear();
                        var getAge;
                        if (getZjh.length === 18)//18位身份证号处理
                        {
                            var birthyear = getZjh.substring(6, 10);
                            getAge = yearfull - birthyear;
                        }
                        if (getZjh.length === 15)//15位身份证号处理
                        {
                            var birthDay = getZjh.substring(6, 12);
                            birthDay = "19" + birthDay;
                            birthDay = birthDay.substring(0, 4);
                            getAge = yearfull - birthDay;//年龄
                        }
                        if(getAge > 18){
                            throw SyntaxError('未成年子女或兄妹>18岁');
                        }
                        console.log(getAge);
                    }

                    //组织配偶方家庭成员
                    if(bdcSlSqrGroupDTO.gyrsffq ==="1"){
                        //共有人为夫妻,将当前申请人作为另一申请人的配偶
                        var poJtcy = {};
                        poJtcy.ysqrgx = "配偶";
                        poJtcy.jtcymc = bdcSlSqrGroupDTO.mc;
                        poJtcy.zjzl = bdcSlSqrGroupDTO.zjzl;
                        poJtcy.zjh = bdcSlSqrGroupDTO.zjh;
                        var poJtcyList =[];
                        poJtcyList.push(poJtcy);
                        bdcSlSqrDTO.poJtcyList =poJtcyList;
                    }else if(bdcSlSqrGroupDTO.ysqrgx ==="未成年子女"){
                        // 未成年子女
                        for(var i=0;i<bdcSlSqrListForJt.length;i++){
                            var poJtcyList =bdcSlSqrListForJt[i].poJtcyList;
                            if(poJtcyList != null){
                                //未成年子女
                                poJtcyList.push(bdcSlSqrGroupDTO);
                                bdcSlSqrListForJt[i].poJtcyList =poJtcyList;
                            }
                        }
                    }



                }

                if(trIndex ===jtrowlen){
                    //当为一组中最后一个时,将家庭列表数据插入
                    for(var i=0;i<bdcSlSqrListForJt.length;i++){
                        bdcSlSqrDTOList.push(bdcSlSqrListForJt[i]);
                    }
                }
            }
        });
        //共有人为夫妻的处理
        dealFqJtcyzz(bdcSlSqrDTOList);
        // 税款减免验证
        checkSkjm(bdcSlSqrDTOList);



    }
    if (bdcSlSqrDTOList.length > 0) {
        $.ajax({
            url: getContextPath() + "/slym/sqr/sqrxx?gzlslid="+processInsId,
            type: 'PATCH',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bdcSlSqrDTOList),
            success: function (data) {
                // 如果新增申请人或新增家庭成员需要自动进行房屋套次查询
                if(isAddSqrJtcy){
                    //获取房屋数据
                    getReturnData("/ycsl/fwcx",{xmid:xmid},"GET",function (data) {
                        ityzl_SHOW_SUCCESS_LAYER("更新房屋套次信息成功");
                        console.log("更新房屋套次信息");
                        isAddSqrJtcy =false;
                    },function (error) {
                        delAjaxErrorMsg(error);

                    },false);

                }
                loadYcslxx();

            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }

}

//删除申请人及家庭成员
function deleteSqrDTO(sqrid, elem) {

    var url = getContextPath() + "/slym/sqr/sqrxx?sqrid=" + sqrid+"&gzlslid="+processInsId;
    var index = $(elem).parents('.basic-info').find('.bdc-item-delete').length;

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
            if (isNotBlank(sqrid)) {
                $.ajax({
                    url: url,
                    type: 'DELETE',
                    success: function (data) {
                        removeModal();
                        ityzl_SHOW_SUCCESS_LAYER("删除成功");
                        if(index === 1){
                            $(elem).parents(".bdc-basic-info").find('.bdc-form-add').remove();
                        }else{
                            $(elem).parents(".bdc-basic-info").remove();
                        }
                    }, error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });
            } else {
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
                if(index === 1){
                    $(elem).parents(".bdc-basic-info").find('.bdc-form-add').remove();
                }else{
                    $(elem).parents(".bdc-basic-info").remove();
                }
            }


            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });

}

//删除家庭成员
function deleteJtcy(jtcyid, elem,sqridList) {
    var url;
    if (isNotBlank(sqridList) &&sqridList.length >1) {
        url = getContextPath() + "/slym/jtcy/deleteBatchBdcSlJtcy?jtcyid=" + jtcyid+"&sqridList="+sqridList;

    }else{
        url = getContextPath() + "/slym/jtcy?jtcyid=" + jtcyid;
    }

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
                        reloadDelJtcy(elem);

                    }, error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });
            } else {
                reloadDelJtcy(elem);
            }


            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });
}

//重新加载家庭成员
function reloadDelJtcy(elem) {

    // 向上遍历定位该组家庭第一个，获取家庭长度
    var $thisbtn,rowlen;
    var $parent;

    $thisbtn = $(elem);
    while(!$thisbtn.parents('tr').hasClass('sqrgroup-first')){
        $parent = $thisbtn.parents('tr').prev();
        $thisbtn = $parent.find('.layui-btn');
    }
    rowlen = $parent.find('[name="fwtc"]').parents('td').attr('rowspan');
    $parent.find('[name="fwtc"]').parents('td').attr('rowspan',parseInt(rowlen)-1);
    $parent.find('[name="sbfwtc"]').parents('td').attr('rowspan',parseInt(rowlen)-1);

    removeModal();
    ityzl_SHOW_SUCCESS_LAYER("删除成功");
    var $parent = elem.parent().parent().parent();
    elem.parent().parent().remove();
    var trlength = $parent.find("tr").length;
    if (trlength === 0) {
        //增加无数据行
        $parent.append('<tr class="bdc-table-none"><td colspan="4">' +
            ' <div class="layui-none"><img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据</div>' +
            '</td><td align="center">' +
            ' <button type="button" value="readOnly" class="layui-btn layui-btn-xs bdc-major-btn jtcy-add" >增加</button>' +
            '</td></tr>');


    }

}


//保存一窗受理信息
function saveYcslXx() {
    saveHtxx(".htxx");
    saveFwxx(".fwxx");
    saveJyxxCezs(".cezsxx");
    saveTdcrjxx(".tdcrj");
    saveSqrDTO();

}
// 组织规则验证参数
function getGzYzParamList() {
    var xgYzQlrparamList = getXgYzQlrParam();
    return connectParam(xgYzQlrparamList);
}


// 拼接数组 此处暂时只考虑了一种规则参数的情况 后续如果调用此方法 增加其他规则的参数时 这个方法需要重新设计 将各规则的参数 放到同一组对象中
function connectParam() {
    var paramList = [];
    if (arguments.length > 0) {
        for (var i=0;i<arguments.length;i++) {
            var list = arguments[i];
            if (Array.isArray(list)) {
                for (var a =0;a <list.length;a++) {
                    paramList.push(list[a]);
                }
            }
        }
    }
    return paramList;
}
// 获取限购验证规则验证的权利人信息
function getXgYzQlrParam() {
    var qlrParamList = [];
    var bdcdyh = "";
    if (isNotBlank(xmid)) {
        //获取单元号信息
        getReturnData("/slym/xm/xx", {xmid: xmid}, "GET", function (data) {
            if (data) {
                bdcdyh = data.bdcdyh;
            }
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        }, false)
    }
    $(".bdc-jt-table[name =sqrGroupDTOTable] tbody tr").each(function () {
        var $tr = $(this);

        var bdcSlSqrGroupDTOArray = $tr.find(":input").serializeArray();
        if (bdcSlSqrGroupDTOArray !== null && bdcSlSqrGroupDTOArray.length > 0) {
            var bdcSlSqrGroupDTO = {};
            bdcSlSqrGroupDTOArray.forEach(function (item, index) {
                bdcSlSqrGroupDTO[item.name] = item.value;
            });
            //本人时权利人信息不可编辑时前面获取不到值
            var ysqrgx = $tr.find('select[name=ysqrgx]').val();
            if(isNotBlank(ysqrgx)){
                bdcSlSqrGroupDTO.ysqrgx =ysqrgx;
            }
            var mc = $tr.find('input[name=mc]').val();
            if(isNotBlank(mc)){
                bdcSlSqrGroupDTO.mc = mc;
            }
            var zjzl = $tr.find('select[name=zjzl]').val();
            if (isNotBlank(zjzl)) {
                bdcSlSqrGroupDTO.zjzl = zjzl;
            }
            var zjh = $tr.find('input[name=zjh]').val();
            if (isNotBlank(zjh)) {
                bdcSlSqrGroupDTO.zjh = zjh;
            }
            var sfbdhj = $tr.find('select[name=sfbdhj]').val();
            if (isNotBlank(sfbdhj)) {
                bdcSlSqrGroupDTO.sfbdhj = sfbdhj;
            }
            if (isNotBlank(bdcSlSqrGroupDTO.mc)) {

                if (bdcSlSqrGroupDTO.ysqrgx === "本人") {
                    // 只获取权利人中非本地户籍的进行验证
                    if (isNotBlank(bdcSlSqrGroupDTO.mc) && bdcSlSqrGroupDTO.sqrlb === "1" && bdcSlSqrGroupDTO.sfbdhj === "0") {
                        var qlrParamMap = {};
                        var bdcGzYzsjDTO = {};
                        qlrParamMap.name = bdcSlSqrGroupDTO.mc;
                        qlrParamMap.cardNo = bdcSlSqrGroupDTO.zjh;
                        qlrParamMap.bdcdyh = bdcdyh;
                        bdcGzYzsjDTO.name = bdcSlSqrGroupDTO.mc;
                        bdcGzYzsjDTO.cardNo = bdcSlSqrGroupDTO.zjh;
                        bdcGzYzsjDTO.bdcdyh = bdcdyh;
                        bdcGzYzsjDTO.obj = qlrParamMap;
                        qlrParamList.push(bdcGzYzsjDTO);
                    }


                }

            }

        }
    });
    return qlrParamList;
}

//共有人为夫妻的处理
// 转入转出方分组处理验证后再组合
function dealFqJtcyzz(bdcSlSqrDTOList) {

    var bdcSlZrfDTOList = [],
        bdcSlZcfDTOList = [];
    for(var i=0;i<bdcSlSqrDTOList.length;i++){
        if(bdcSlSqrDTOList[i].bdcSlSqrDO.sqrlb == "1"){
            bdcSlZrfDTOList.push(bdcSlSqrDTOList[i]);
        }else {
            bdcSlZcfDTOList.push(bdcSlSqrDTOList[i]);
        }
    }
    dealFqJtcyxx(bdcSlZrfDTOList);
    dealFqJtcyxx(bdcSlZcfDTOList);
    bdcSlSqrDTOList = [];
    bdcSlSqrDTOList.push(bdcSlZrfDTOList);
    bdcSlSqrDTOList.push(bdcSlZcfDTOList);
}
function dealFqJtcyxx(list){

    //只有当转入方或者转出方申请人为两个，并且共有人是否夫妻关系为是的时候处理双方家庭成员信息
    if(list.length ===2 && list[0].bdcSlSqrDO !="{}" &&list[0].bdcSlSqrDO.gyrsffq ==="1" &&list[1].bdcSlSqrDO !="{}" &&list[1].bdcSlSqrDO.gyrsffq ==="1"){
        for(var i = 0; i < list.length; i++){
            var jtcyList =list[i].bdcSlJtcyDOList;
            if(jtcyList == null){
                jtcyList =[];
                list[i].bdcSlJtcyDOList =jtcyList;

            }
            //如果共有人为夫妻，则互为配偶,并且双方继承对方的未成年子女信息
            for(var j = 0; j < list[1-i].poJtcyList.length; j++){
                var poJtcy =list[1-i].poJtcyList[j];
                //判断是否存在当前家庭成员
                var isExistJtcy =false;
                for(var k = 0; k < jtcyList.length; k++){
                    if(poJtcy.jtcymc ===jtcyList[k].jtcymc){
                        isExistJtcy=true;
                        break;
                    }
                }
                //不存在
                if(!isExistJtcy){
                    var jtcy =JSON.parse(JSON.stringify(poJtcy));
                    //清空家庭成员ID
                    jtcy.jtcyid ="";
                    jtcyList.push(jtcy);
                }
            }
        }


    }

}

//税款减免验证：税款减免为是时，婚姻状况不能为空
function checkSkjm(bdcSlSqrDTOList){
    for(var i=0;i<bdcSlSqrDTOList.length;i++){
        var bdcSlSqrDO = bdcSlSqrDTOList[i].bdcSlSqrDO;
        var bdcSlJtcyDOList = bdcSlSqrDTOList[i].bdcSlJtcyDOList;
        if(isNotBlank(bdcSlSqrDO)) {
            if(bdcSlSqrDO.sfjm === "1"){
                if(bdcSlSqrDO.hyzk === ""){
                    throw new Error("婚姻状况不能为空");
                } else if(bdcSlSqrDO.hyzk === "已婚") {
                    var poList = [];
                    if(!bdcSlJtcyDOList ||bdcSlJtcyDOList.length <= 0){
                        //页面获取不到查询数据库
                        checkPoxx(bdcSlSqrDO);
                    }else {
                        for(var j=0;j<bdcSlJtcyDOList.length;j++) {
                            var bdcSlJtcy = bdcSlJtcyDOList[j];
                            if(bdcSlJtcy.ysqrgx === "配偶") {
                                poList.push(bdcSlJtcy);
                            }
                        }
                        if(poList.length<=0){
                            //页面获取不到查询数据库
                            checkPoxx(bdcSlSqrDO);
                        }
                    }
                }
            }
        }
    }

}

//查询数据库判断是否存在配偶
function checkPoxx(bdcSlSqrDO) {
    getReturnData("/slym/jtcy/listBdcSlJtcy",{sqrid:bdcSlSqrDO.sqrid,ysqrgx:"配偶"},"GET",function (data) {
        if(!data ||data.length ===0){
            throw new Error("未录入配偶信息");
        }
        
    },function (error) {
        delAjaxErrorMsg(error);
        
    },false)
    
}

//监听增值税金额等字段修改：增值税金额=含增值税金额-不含增值税金额
function changeZzsJe(elem) {
    var hzzsje =$("#hzzsje").val();
    var bhzzsje =$("#bhzzsje").val();
    if(isNotBlank(hzzsje) &&isNotBlank(bhzzsje)){
        $("#zzsje").val(calculateFloat(hzzsje -bhzzsje));
    }

}

/**
 * 处理小数计算问题，四舍五入保留两位小数
 * 需要引用math.js
 * @param equation  计算公式
 */
function calculateFloat(equation) {
    var result = math.format(math.evaluate(equation), 14);
    return formatFloat(parseFloat(result));
}

//处理小数问题
function formatFloat(f) {
    if((f + '').indexOf('.') != -1){
        var d, carryD, i,
            ds = f.toString().split('.'),
            len = ds[1].length,
            b0 = '', k = 0;

        if (len > 2) {
            while(k < 2 && ds[1].substring(0, ++k) == '0') {
                b0 += '0';
            }
            d = Number(ds[1].substring(0, 2));
            // 判断保留位数后一位是否需要进1
            carryD = Math.round(Number('0.' + ds[1].substring(2, len)));
            len = (d + carryD).toString().length;
            if (len > 2) {
                return Number(ds[0]) + 1;
            } else if (len == 2) {
                return Number(ds[0] + '.' + (d + carryD));
            }
            return Number(ds[0] + '.' + b0 + (d + carryD));
        }
    }
    return f;
}

// 行政区划与街道代码对照
function jddmDz(xzqh,jddm){
    var jddmList=zdList.jddm;
    // 获取行政区划对应街道代码
    if(isNotBlank(jddmList)){
        $('#ycsljyxx').find('#jddm option').remove();
        var $jddm = $('#ycsljyxx').find('#jddm');
        $jddm.append("<option value=''>请选择</option>");
        for(var i=0;i<jddmList.length;i++){
            var zdjddm =jddmList[i];
            if(zdjddm.DM.length >=6) {
                var jddmDz = zdjddm.DM.substring(0, 6);
                if (jddmDz === xzqh) {
                    if(zdjddm.DM ===jddm){
                        $jddm.append("<option value='" + zdjddm.DM + "' title='" + zdjddm.MC + "' selected=\"selected\">" + zdjddm.MC + "</option>");

                    }else {
                        $jddm.append("<option value='" + zdjddm.DM + "' title='" + zdjddm.MC + "'>" + zdjddm.MC + "</option>");
                    }
                }
            }
        }
    }
}

// 身份证证件号验证
function addSfzYz(zjzl, elem){
    var zjzlid = $(elem).parent().parent().find("select")[0].id;
    var zjhid = zjzlid.replace("zjzl", "zjh");
    var $zjh = $(elem).parent().parent().next().find("#" + zjhid);
    var zjhVerify =  $zjh.attr("lay-verify");
    if(zjzl == '1'){
        if (isNotBlank(zjhVerify)) {
            if (zjhVerify.indexOf("identitynew") < 0) {
                $zjh.attr("lay-verify", zjhVerify + "|identitynew");
            }
        } else {
            $zjh.attr("lay-verify", "identitynew");
        }
    }else {
        //移除证件号验证
        if (isNotBlank(zjhVerify) && zjhVerify.indexOf("identitynew") > -1) {
            $zjh.attr("lay-verify", zjhVerify.replace("identitynew", ""));
        }
    }
}

// 身份证验证初始化
function reloadZjhYz() {
    // 转入转出方身份证验证
    $('.form-margin-area .basic-info').each(function () {
        $('[lay-filter="zjzl"]').each(function () {
            addSfzYz($(this).val(),$(this));
        });

    });
}

//根据审批来源控制按钮隐藏
function setSplyBtnAttr(){
    //互联网
    if(sply ===5 || sply === 11){
        //隐藏删除按钮
        $(".jtcy-delete").addClass("bdc-hide");
        //税款减免，本地户籍，婚姻状况,满五唯一，是否直系亲属不可编辑
        $("select[name=sfjm]").attr('disabled', 'off');
        $("select[name=sfbdhj]").attr('disabled', 'off');
        $("select[name=hyzk]").attr('disabled', 'off');
        $("select[name=jtmwwyzz]").attr('disabled', 'off');
        $("select[name=sfzxqs]").attr('disabled', 'off');
        $("select[name=xgmnsr]").attr('disabled', 'off');
        // 家庭成员查询按钮不可编辑
        //$('button[name="getJtcyxx"]').css({'pointer-events':'none'});
    }

}

//对表单的一系列渲染操作
function renderForm(){
    //表单权限控制
    setSplyBtnAttr();
    getStateById(readOnly, formStateId, 'ycym');

}

/**
 * 记录上次交易登记时间
 */
function insertScjydjsjLog(){
    var sjjydjsj = new Object();
    sjjydjsj.scjydjsj = oldScjydjsj;
    getReturnData("/rest/v1.0/slym/addXgLog?gzlslid=" + processInsId, JSON.stringify(sjjydjsj), "POST", function () {
    }, function (error) {
        delAjaxErrorMsg(error);
    })
}

function queryScjydjsjLog(){
    getReturnData("/rest/v1.0/slym/queryXgLog", {gzlslid: processInsId}, "GET", function (data) {
        if (data && data.value) {
            $('#scjydjsj').parents('.layui-input-inline').addClass('bdc-change-input');
            $('#scjydjsj').css({"background-color": "#eaf4fe"});
            renderChangeTips("上次登记时间");
        }
    }, function (error) {

    })
}

//打开家庭成员页面
function openJtcyYm() {
    var url = getContextPath() + "/view/ycsl/jtcy.html";
    layer.open({
        title: '家庭成员信息',
        type: 2,
        area: ['960px'],
        fixed: false, //不固定
        content: url
        ,cancel: function(){
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
        ,success: function () {

        }
    });
}