//字典列表
var zdList;
//地址栏获取数据
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var zxlc = getQueryString("zxlc");
var printValue = getQueryString("print");
var ycslfwlx = getQueryString("fwlx");
var xmid = getQueryString("xmid");
var jbxxid;
var $,laytpl,form;
var sjclids = new Set();
//用于存放所有的收件材料id
var sjclidLists = [];
//页面重新加载清空存储的收件材料id session
sessionStorage.removeItem("yxsjclids");
sessionStorage.removeItem("sjclidLists");
//监听转入转出方,家庭成员是否新增
var isAddSqrJtcy =false;
//规则验证入参
var gzyzParamMap ={};
//受理项目
var bdcSlxm={};
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    $ = layui.jquery;
    var element = layui.element;
    laytpl = layui.laytpl;
    var layer = layui.layer;

    //点击提交时不为空的全部标红
    var isSubmit = true;
    var isFirst = true;
    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            if (value == '' || value == null || value == undefined) {//判断是否为空
                if (isXndyh) {//是否为虚拟不动产单元号
                    var bdcdyxx = $("div[name='bdcdyxx']").find($(item));
                    if (bdcdyxx.length == 0 && zxlc !== "true") {//如果需要验证的元素不在不动产单元信息模块，且非注销类流程,则进行校验
                        if (isFirst) {
                            $('.layui-tab-item').removeClass('layui-show');
                            $(item).parents('.layui-tab-item').addClass('layui-show');
                            var liIndex = $(item).parents('.layui-tab-item').index() + 1;
                            $('.layui-tab-title li').removeClass('layui-this');
                            $('.layui-tab-title li:nth-child(' + liIndex + ')').addClass('layui-this');

                            //收起内容中有必填项为空时页面展开
                            var $this = $(item).parents('.layui-form-item');
                            if($this.hasClass('bdc-hide')){
                                $this.removeClass('bdc-hide');
                                $(item).parents('.bdc-basic-info').find('.bdc-show-more').html('收起');
                            }
                            //定位第一个为空的必填项
                            var target = $(item).parents('.bdc-not-null').offset().top;
                            $("body,html").animate({scrollTop:target},0);
                            isFirst = false;
                        }
                        $(item).addClass('layui-form-danger');
                        isSubmit = false;
                    }

                } else {
                    if (isFirst) {
                        $('.layui-tab-item').removeClass('layui-show');
                        $(item).parents('.layui-tab-item').addClass('layui-show');
                        var liIndex = $(item).parents('.layui-tab-item').index() + 1;
                        $('.layui-tab-title li').removeClass('layui-this');
                        $('.layui-tab-title li:nth-child(' + liIndex + ')').addClass('layui-this');

                        //收起内容中有必填项为空时页面展开
                        var $this = $(item).parents('.layui-form-item');
                        if($this.hasClass('bdc-hide')){
                            $this.removeClass('bdc-hide');
                            $(item).parents('.bdc-basic-info').find('.bdc-show-more').html('收起');
                        }
                        //定位第一个为空的必填项
                        var target = $(item).parents('.bdc-not-null').offset().top;
                        $("body,html").animate({scrollTop:target},0);
                        isFirst = false;
                    }
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                }
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
        //要求正整数，允许为空
        , number: [/^[1-9]+[0-9]*]*$/, '请输入非负整数']
        //数字
        , number2: [/^-?\d*$/, '请输入数字']
    });
    //保存
    form.on("submit(saveData)", function (data) {
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
                            $.when(saveSjcl(), saveSlxmxx(),saveYcslXx()).done(function () {
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
        //获取字典列表、加载基本信息、加载收件材料
        setTimeout(function () {
            try {
                $.when(loadButtonArea(),loadZdData(),loadJbxx(),loadSjcl()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e,"加载失败");
                return
            }
        }, 50);

        //监听第一次单击tab，
        var fcjyIndex = 0;
        element.on('tab(tabFilter)', function (data) {
            var tabid = $(".layui-tab-title .layui-this").attr("id");
            $(this).removeAttr("onclick");
            if (tabid === "fcjyxx" || tabid ==="swxx") {
                if (fcjyIndex === 0) {
                    fcjyIndex++;
                    addModel();
                    setTimeout(function () {
                        loadYcslxx();
                    }, 0);
                }
            }
        });

        //房屋信息查询
        $(document).on('click', '#fwxxcx', function (){
            var url =getContextPath()+"/view/query/fwxx.html?xmid="+xmid;
            var index = layer.open({
                type: 2,
                title: "房屋信息查询",
                area: ['1300px', '600px'],
                fixed: false, //不固定
                maxmin: true, //开启最大化最小化按钮
                content: url
            });
            layer.full(index);


        });

        //婚姻接口
        $(document).on('click', '#hyxx', function (){
            addModel();
            var $qlrbasic =$(this).parents(".bdc-basic-info");
            var qlrmc =$qlrbasic.find("input[name='sqrmc']").val();
            var qlrzjh=$qlrbasic.find("input[name='zjh']").val();
            var sqrid=$qlrbasic.find("input[name='sqrid']").val();
            if(!isNotBlank(qlrmc) ||!isNotBlank(qlrzjh)){
                showAlertDialog("申请人名称或者证件号为空");
                removeModal();
                return false;
            }
            getReturnData("/slym/jtcy/hygaxx",{qlrmc:qlrmc,qlrzjh:qlrzjh,beanName:"acceptHyxx"},"GET",function (data) {
                if(isNotBlank(data)){
                    var hyzt =data.hyzt;
                    var bdcSlJtcy =data.jtcy;
                    var bdcSlJtcyList =[];
                    if(isNotBlank(bdcSlJtcy.jtcymc)) {
                        bdcSlJtcy.ysqrgx = "配偶";
                        bdcSlJtcy.sqrid = sqrid;
                        bdcSlJtcyList.push(bdcSlJtcy);
                    }
                    var $tablenone =$qlrbasic.find(".bdc-jt-table tbody .bdc-table-none");
                    if($tablenone.length ===0){
                        //删除原有配偶信息
                        $qlrbasic.find($("tbody tr")).each(function () {
                            var ysqrgx =$(this).find("[name=ysqrgx]").val();
                            if(ysqrgx ==="配偶"){
                                var jtcyid =$(this).find("input[name=jtcyid]").val();
                                getReturnData("/slym/jtcy?jtcyid=" + jtcyid,{},"DELETE",function () {
                                },function (error) {
                                    delAjaxErrorMsg(error);
                                },false);
                            }
                        });
                    }
                    if(isNotBlank(sqrid)) {
                        var bdcSlSqrDTOList = [];
                        var bdcSlSqrDTO = {};
                        //申请人信息
                        var bdcSlSqr = {};
                        bdcSlSqr.sqrid = sqrid;
                        bdcSlSqr.hyzk = hyzt;
                        bdcSlSqrDTO.bdcSlSqrDO =bdcSlSqr;
                        bdcSlSqrDTO.bdcSlJtcyDOList = bdcSlJtcyList;
                        bdcSlSqrDTOList.push(bdcSlSqrDTO);

                        //保存家庭成员配偶信息以及婚姻状况
                        getReturnData("/slym/sqr/sqrxx?gzlslid="+processInsId, JSON.stringify(bdcSlSqrDTOList), "PATCH", function (data) {
                            loadYcslxx();
                            ityzl_SHOW_SUCCESS_LAYER("查询成功");
                            removeModal();
                        }, function (error) {
                            delAjaxErrorMsg(error);
                        });
                    }
                }


            },function (error) {
                delAjaxErrorMsg(error);

            })

        });

        //公安接口
        $(document).on('click', '#gaxx', function (){
            addModel();
            var $qlrbasic =$(this).parents(".bdc-basic-info");
            var qlrmc =$qlrbasic.find("input[name='sqrmc']").val();
            var qlrzjh=$qlrbasic.find("input[name='zjh']").val();
            var sqrid=$qlrbasic.find("input[name='sqrid']").val();
            if(!isNotBlank(qlrmc) ||!isNotBlank(qlrzjh)){
                showAlertDialog("申请人名称或者证件号为空");
                removeModal();
                return false;
            }
            getReturnData("/slym/jtcy/hygaxx",{qlrmc:qlrmc,qlrzjh:qlrzjh,beanName:"acceptJtcyxx"},"GET",function (data) {
                for(var i=0;i<data.length;i++) {
                    var bdcSlJtcy = data[i];
                    bdcSlJtcy.sqrid =sqrid;
                }
                var $tablenone =$qlrbasic.find(".bdc-jt-table tbody .bdc-table-none");
                if($tablenone.length ===0){
                    //删除原有非配偶信息
                    $qlrbasic.find($("tbody tr")).each(function () {
                        var ysqrgx =$(this).find("[name=ysqrgx]").val();
                        if(ysqrgx !=="配偶"){
                            var jtcyid =$(this).find("input[name=jtcyid]").val();
                            getReturnData("/slym/jtcy?jtcyid=" + jtcyid,{},"DELETE",function () {
                            },function (error) {
                                delAjaxErrorMsg(error);
                            },false);
                        }
                    });
                }
                if(data != null && data.length > 0){
                    //保存家庭成员配偶信息
                    getReturnData("/slym/jtcy",JSON.stringify(data),"PATCH",function (data) {
                        loadYcslxx();
                        ityzl_SHOW_SUCCESS_LAYER("查询成功");
                        removeModal();
                    },function (error) {
                        delAjaxErrorMsg(error);
                    })
                }else{
                    loadYcslxx();
                    ityzl_SHOW_SUCCESS_LAYER("查询成功");
                    removeModal();
                }

            },function (error) {
                delAjaxErrorMsg(error);

            })

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
                        if (data && data.responseMsg === "成功") {
                            ityzl_SHOW_SUCCESS_LAYER("推送成功");
                        } else {
                            showAlertDialog("推送失败" + (data && data.responseMsg ? (":" + data && data.responseMsg) : ""));
                        }
                    }, error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });

            });

        });

        //查询税务信息
        $(document).on('click', '#querySwxx', function () {
            addModel();
            var slbh = $("#sjbh").val();
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
                            loadYcslxx();
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
            var submitIndex = layer.open({
                type: 1,
                title: '提示信息',
                skin: 'bdc-small-tips',
                area: ['320px'],
                content: '您确定要作废税务信息吗？',
                btn: ['确定','取消'],
                btnAlign: 'c',
                yes: function(){
                    //确定操作
                    console.log('确定');
                    layer.close(submitIndex);
                    addModel();
                    //调取作废接口
                    var slbh = $("#sjbh").val();
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
                btn2: function(){
                    //取消
                }
            });
        });

        //新增申请人
        $(document).on('click', '.bdc-item-add', function () {
            isAddSqrJtcy =true;
            var id = this.id;
            var $parent = $(this).parents(".basic-info");
            var bdcSlSqr = {};
            var index = $(this).parents('.basic-info').find('.bdc-item-add').length,
                content =$(this).parents('.bdc-basic-info').find('.bdc-form-add').length;
            bdcSlSqr["xmid"] = xmid;
            if (id === "addZrf") {
                bdcSlSqr["sqrlb"] = 1;
            } else {
                bdcSlSqr["sqrlb"] = 2;
            }
            var json = {
                zd: zdList,
                bdcSlSqr: bdcSlSqr
            };
            var getTpl = addSqrTpl.innerHTML;
            laytpl(getTpl).render(json, function (html) {
                $parent.append(html);
                form.render();
                disabledAddFa("ycslym");
            });
            if(index === 1 && content <= 0){
                $(this).parents('.bdc-basic-info').remove();
            }
        });

        //新增家庭成员
        $(document).on('click', '.jtcy-add', function () {
            isAddSqrJtcy =true;
            var json = {
                zd: zdList
            };
            var $parent = $(this).parents('tbody');
            //删除无数据行
            if ($(this).parent().parent()[0].className === "bdc-table-none") {
                $(this).parent().parent().remove();
            }
            var getTpl = addJtcyTpl.innerHTML;
            laytpl(getTpl).render(json, function (html) {
                $parent.append(html);
                form.render();
                disabledAddFa("tabFcjyxx");
            });

        });

        //删除家庭成员
        $('.bdc-form-div').on('click','.jtcy-delete',function(){
            var getJtcyId = $(this).data('jtcyid');
            deleteJtcy(getJtcyId,$(this));
        });

        //维修资金验证
        $(document).on('click', '#wxzjYz', function () {
            wxzjYz();
        });

        //推送一窗创建登记流程
        $(document).on('click', '#tsBdcDjxx', function () {
            tsBdcDjxx();
        });


        //收起、展开
        var $info = $('.layui-tab-content');
        $info.on('click', '.bdc-show-more', function () {
            var $this = $(this);
            if ($this.html() == '收起') {
                $this.html('展开');
            } else {
                $this.html('收起');
            }
            $this.parents('.bdc-title-sign-btn').next('.bdc-form-add').find('.bdc-more-item').toggleClass('bdc-hide');
        });

        //上移
        $info.on('click', '.bdc-item-up', function () {
            var $parent = $(this).parent().parent();
            if ($parent.index() == 1) {
                layer.msg('<img src="../images/info-small.png" alt="">已经是第一个了');
                return false;
            } else {
                var prev = $parent.prev();
                $parent.insertBefore(prev);
            }
        });
        //下移
        $info.on('click', '.bdc-item-down', function () {
            var $parent = $(this).parent().parent();
            var len = $parent.parent().children().length;
            if ($parent.index() == len - 1) {
                layer.msg('<img src="../images/info-small.png" alt="">已经是最后一个了');
                return false;
            } else {
                var next = $parent.next();
                $parent.insertAfter(next);
            }
        });

        //上次交易合同登记时间修改验证
        $('.bdc-form-div').on('focus','#scjydjsj',function () {
            var confimLayer = layer.open({
                type: 1,
                title: '提示',
                skin: 'bdc-small-tips',
                area: ['320px'],
                content: '您确定要修改上次交易合同登记时间？',
                btn: ['确定','取消'],
                btnAlign: 'c',
                yes: function(){
                    //确定操作
                    layer.close(confimLayer);
                    setTimeout(function () {
                        $('.bdc-form-div #scjydjsj').click();
                    },100);
                }
            });
        })

        //监听 满五唯一 选择
        form.on('select(jtmwwyzz)', function(data){
            var $fwtc = $(data.elem).parents('.bdc-form-add').find('select[name=fwtc]');
            if(data.value == 1 || data.value == ''){
                $fwtc.removeAttr('disabled');
                $fwtc.parent().children('img').remove();
                form.render('select');
                resetSelectDisabledCss();
            }else {
                $fwtc.attr('disabled','off');
                $fwtc.find('option').removeAttr('selected');
                $fwtc.find('option[value=9]').attr('selected','selected');
                form.render('select');
                disabledAddFa();
                resetSelectDisabledCss();
            }
        });

        //监听行政区划选择
        form.on('select(xzqh)', function(data){
            jddmDz(data.value);
            form.render('select');
            resetSelectDisabledCss();
        });

        //监听房屋套次修改
        form.on('select(fwtc)', function(data){
            var yfwtc = $(data.elem).parents(".bdc-basic-info").find('.bdc-fwtc').val();
            if(!isNotBlank(data.value) ||parseInt(yfwtc) > parseInt(data.value)){
                $(data.elem).val(yfwtc);
                form.render("select");
                resetSelectDisabledCss();
                ityzl_SHOW_WARN_LAYER("房屋套次不能选择空或者小于原有值");
                return false;

            }

        });

        //监听转入转出方证件种类选择
        form.on('select(zjzl)', function(data){
            addSfzYz(data.value, data.elem);
        });

        //监听家庭成员表格内证件种类选择
        form.on('select(zjzlTd)', function(data){
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

    form.on('checkbox(qxCheckbox)', function (data) {
        $('td input[name=yxTable]').each(function (index, item) {
            item.checked = data.elem.checked;
            var qxData = item.value;
            //如果是选中的则添加，否则全部删除
            if (item.checked) {
                sjclids.add(qxData)
            } else {
                sjclids.delete(qxData);
            }
        });
        form.render('checkbox');
        if(sjclids.size >0) {
            var idList = [];
            sjclids.forEach(function (element, sameElement, set) {
                idList.push(sameElement);
            });
            sessionStorage.setItem("yxsjclids",idList);
        } else {
            sessionStorage.setItem("yxsjclids",[]);
        }
    });
    //单个的
    form.on('checkbox(yxCheckbox)', function (data) {
        var checkedLength = $('td .yx+.layui-form-checked[lay-skin=primary]').length;
        var checkBoxLength = $('td .yx+.layui-form-checkbox[lay-skin=primary]').length;
        var qxCheckBox = $('.gray-tr input[name=qxTable]')[0];
        var sjclid = data.value;
        if (sjclids.has(sjclid)) {
            sjclids.delete(sjclid);
        } else {
            sjclids.add(sjclid);
        }
        //判断是否全选，全选的时候勾选最上面的复选框
        if (checkedLength == checkBoxLength) {
            qxCheckBox.checked = true;
        } else {
            qxCheckBox.checked = false;
        }
        form.render('checkbox');
        if(sjclids.size >0) {
            var idList = [];
            sjclids.forEach(function (element, sameElement, set) {
                idList.push(sameElement);
            });
            sessionStorage.setItem("yxsjclids",idList);
        } else {
            sessionStorage.setItem("yxsjclids",[]);
        }
    });



    //按钮加载
    function loadButtonArea() {
        var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
        //渲染数据
        laytpl(tpl).render({}, function (html) {
            view.innerHTML = html;
        });
        form.render();
        getStateById(readOnly, formStateId, "ycym");
        disabledAddFa();
        titleShowUi();
    }

    //打印，获取交易信息的下拉列表加载
    function titleShowUi() {
        $(".print-btn").click(function (e) {
            var e = e || window.event; //浏览器兼容性
            if (e.stopPropagation) {
                e.stopPropagation();
            } else {
                e.cancelBubble = true;
            }
            $("#jyxx").hide();
            $("#print").show();
            setUiWidth($(this), $("#print"));
        });

        $(".jyxx-btn").click(function (e) {
            var e = e || window.event; //浏览器兼容性
            if (e.stopPropagation) {
                e.stopPropagation();
            } else {
                e.cancelBubble = true;
            }
            $("#print").hide();
            $("#jyxx").show();
            setUiWidth($(this), $("#jyxx"));
        });

        $(".print-btn li").click(function () {
            $("#print").hide();
        });

        $(".jyxx-btn li").click(function () {
            $("#jyxx").hide();
        });

        $(document).click(function (e) {
            var e = e || window.event; //浏览器兼容性
            var elem = e.target || e.srcElement;
            while (elem) { //循环判断至跟节点，防止点击的是div子元素
                if (elem.id && elem.id == 'print') {
                    return;
                }
                elem = elem.parentNode;
            }
            $("#print").hide();
        });
        $(document).click(function (e) {
            var e = e || window.event; //浏览器兼容性
            var elem = e.target || e.srcElement;
            while (elem) { //循环判断至跟节点，防止点击的是div子元素
                if (elem.id && elem.id == 'jyxx') {
                    return;
                }
                elem = elem.parentNode;
            }
            $("#jyxx").hide();
        });
    }

    //动态设置下拉样式
    function setUiWidth(buttonElement, uiElement) {
        var X = buttonElement.offset().top;
        var Y = buttonElement.offset().left;
        uiElement.offset({top: X + 40, left: Y - 0});
        var uiWidth = 90;
        var liArray = uiElement.find("li");
        if (liArray.length > 0) {
            var textNumber = 0;
            for (var i = 0; i < liArray.length; i++) {
                var liEl = liArray[i];
                var liText = $(liEl).text();
                if (textNumber < liText.length) {
                    textNumber = liText.length;
                }
            }
            var countWidth = (textNumber + 3) * 13;
            if (uiWidth < countWidth) {
                uiWidth = countWidth;
            }
        }
        uiElement.width(uiWidth);
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
                //加载房屋信息
                generateFwxx(data.bdcSlFwxxDO,bdcSlxm);
                //加载合同信息
                generateHtxx(data.bdcSlJyxxDO);
                //加载转入方信息
                generateSqrxx(data.bdcSlZrfDTOList, 1);
                //加载转出方信息
                generateSqrxx(data.bdcSlZcfDTOList, 2);
                //加载转入方税务信息
                generateSwxx(data.bdcZrfSwxxList, 1);
                //加载转出方税务信息
                generateSwxx(data.bdcZcfSwxxList, 2);

                form.render();
                renderSelect();
                getStateById(readOnly, formStateId, 'ycym');
                form.render("select");
                disabledAddFa("ycslym");
                renderDate(form);
                removeModal();
                reloadZjhYz();
            }
        });
    };


    window.generateFwxx = function(bdcSlFwxxDO,bdcxmxx) {
            var json = {
                bdcSlFwxx: bdcSlFwxxDO,
                bdcxmxx: bdcxmxx,
                zd: zdList
            };
            var tpl = fwxxTpl.innerHTML, view = document.getElementById('ycslfwxx');
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            if(bdcSlFwxxDO != null &&isNotBlank(bdcSlFwxxDO.xzqh)) {
                jddmDz(bdcSlFwxxDO.xzqh,bdcSlFwxxDO.jddm);
            }

    };


    window.generateHtxx =  function(bdcSlJyxxDO,index) {
        var json = {
            bdcSlJyxx: bdcSlJyxxDO,
            zd: zdList,
            index: index
        };
        var tpl = htxxTpl.innerHTML, view = document.getElementById('ycsljyxx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        // 单价自动计算
        djJs();
    };

    //加载申请人信息
    window.generateSqrxx = function(bdcSlSqrDTOList, sqrlb) {
        //新增默认值
        var bdcSlSqr = {};
        bdcSlSqr["xmid"] = xmid;
        bdcSlSqr["sqrlb"] = sqrlb;
        bdcSlSqr["sxh"] = 1;
        var json = {
            bdcSlSqrDTOList: bdcSlSqrDTOList,
            zd: zdList,
            bdcSlSqr: bdcSlSqr,
            sqrlb:sqrlb
        };
        var tpl, view;
        if (sqrlb === 1) {
            view = document.getElementById('ycslzrfxx');
        } else {
            view = document.getElementById('ycslzcfxx');
        }

        if (bdcSlSqrDTOList != null && bdcSlSqrDTOList.length > 0) {
            tpl = ycslsqrTpl.innerHTML;
        } else {
            tpl = addSqrTpl.innerHTML;
        }
        //渲染申请人数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        //默认收起
        $(view).find('.bdc-show-more').click();


    };

    //加载转入方税务信息
    window.generateSwxx = function(bdcSlSwxxList, sqrlb) {
        var json;
        if (isNotBlank(bdcSlSwxxList) && bdcSlSwxxList.length > 0) {
            json = {
                bdcSlSwxxList: bdcSlSwxxList[0],
                zd: zdList,
                sqrlb: sqrlb
            };
        } else {
            json = {
                bdcSlSwxxList: bdcSlSwxxList,
                zd: zdList,
                sqrlb: sqrlb
            };
        }
            var tpl, view;
            if(bdcSlSwxxList !== null && bdcSlSwxxList.length>0) {
                tpl = swxxTpl.innerHTML;
            } else {
                tpl = nullSwxxTpl.innerHTML;
            }
            if (sqrlb === 1) {
                view = document.getElementById('ycslzrfswxx');
            } else if (sqrlb === 2) {
                view = document.getElementById('ycslzcfswxx');
            }
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });

    }
});

document.onkeydown = function (event) {
    if($('.bdc-spf-layer').length > 0){
        var code = event.keyCode;
        if (code == 13) {
            $('.layui-layer-btn0').click();
            return false;
        }
    }
};

//加载基本信息模块（入口）
function loadJbxx() {
    getReturnData("/slym/xm/queryBdcSlJbxxByGzlslid", {processInsId: processInsId}, 'GET', function (data) {
        if (isNotBlank(data)) {
            getReturnData("/gwc/list/bdcslxm", {jbxxid: data.jbxxid}, 'GET', function (item) {
                if (isNotBlank(item)) {
                    bdcSlxm =item[0];
                    xmid = item[0].xmid;
                    jbxxid=data.jbxxid;
                    gzyzParamMap.bdcdyh =item[0].bdcdyh;
                    //判断是否是虚拟单元号
                    isXndyh = checkXndyh(item[0].bdcdyh);
                    data['xmid'] = xmid;
                    data['bdcdyh'] = item[0].bdcdyh;
                    generateJbxx(data);
                }
            }, function (err) {
                console.log(err);
            },false);
            //面积单位为空时默认为平方米
            if (data.mjdw === null || data.mjdw === '') {
                data.mjdw = '1'
            }

        }
    }, function (err) {
        console.log(err);
    },false);
}
//组织基本信息到页面
function generateJbxx(jbxx) {
    // 登记原因
    var djxldjyyList = {};
    if (isNotBlank(bdcSlxm.djxl)) {
        //查看登记原因
        getReturnData("/slym/xm/queryDjxlDjyy", {djxl: bdcSlxm.djxl}, 'GET', function (data) {
            if (isNotBlank(data)) {
                djxldjyyList = data;
            }
        }, function (err) {
            console.log(err);
        }, false);
    }
    var json = {
        jbxx: jbxx,
        xmxx:bdcSlxm,
        zd: zdList,
        djxldjyy:djxldjyyList
    };
    var tpl = jbxxTpl.innerHTML, view = document.getElementById('sljbXx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    getStateById(readOnly, formStateId, 'ycym');
    form.render('select');
    disabledAddFa();
    renderSelect();
}

//加载收件材料
function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        generateSjcl(data);
    }, function (err) {
        console.log(err);
    });
}
//组织收件材料到页面
function generateSjcl(data) {
    sjclNumber = data.length;
    sjclidLists = [];
    if(data !== null && data.length >0) {
        for(var i=0;i<data.length;i++) {
            sjclidLists.push(data[i].sjclid);
        }
    }
    sessionStorage.setItem("sjclidLists",sjclidLists);
    var json = {
        bdcSlSjclDOList: data,
        zd: zdList
    };
    var tpl = sjclTpl.innerHTML, view = document.getElementById("sjcl");
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    getStateById(readOnly, formStateId, 'ycym');
    form.render('select');
    disabledAddFa("sjclForm");
}

function queryFcjyClfHtxx(fwlx,currxmid) {
    //小弹出层
    layer.open({
        title: '获取房产交易合同信息',
        type: 1,
        skin: 'bdc-spf-layer',
        area: ['430px'],
        btn: ['提交', '取消'],
        content: $('#bdc-popup-small')
        , yes: function (index, layero) {
            //提交 的回调
            addModel();
            var contractNo = $('#contractNo').val();
            $('#contractNo').val('');
            generateYcslxx(contractNo, index,fwlx,currxmid);
        }
        , btn2: function (index, layero) {
            //取消 的回调

        }
        , cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
        , success: function () {
            //清空已有值
            $('#contractNo').val('');
            //自动获取焦点
            $('#contractNo').focus();

        }
    });
}



function generateYcslxx(contractNo, index,fwlx,currxmid) {
    if(!isNotBlank(currxmid)){
        currxmid =xmid;

    }
    if(!isNotBlank(fwlx)){
        fwlx =ycslfwlx;
    }
    layer.close(index);
    $.ajax({
        url: getContextPath() + "/ycsl/fcjyxx",
        type: 'GET',
        data: {htbh: contractNo, xmid: currxmid, fwlx: fwlx},
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data)) {
                //重新组织页面数据
                loadYcslxx();
            } else {
                ityzl_SHOW_INFO_LAYER("未获取到合同信息");
                removeModal();
                layer.close(index);
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            layer.close(index);
            delAjaxErrorMsg(xhr);
        }
    });
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
    var fwxxHtml = document.getElementById('ycslfwxx');
    if (isNotBlank(fwxxHtml)) {
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
                    // ityzl_SHOW_SUCCESS_LAYER("提交成功");
                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }
    }
}

function saveHsxx() {
    var bdcSlHsxx = {};
    // var htxxArray = $(formClass);
    // if (htxxArray !== null && htxxArray.length > 0) {
    //     htxxArray.serializeArray().forEach(function (item, index) {
    //         bdcSlHsxx[item.name] = item.value;
    //     })
    // }


    $.ajax({
        url: getContextPath() + "/slym/sw/hsxx",
        type: 'PATCH',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(bdcSlHsxx),
        success: function (data) {
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

}

//保存核税信息明细
function saveHsxxMx() {
    var bdcSlHsxxMxList = [];
    // var htxxArray = $(formClass);
    // if (htxxArray !== null && htxxArray.length > 0) {
    //     htxxArray.serializeArray().forEach(function (item, index) {
    //         bdcSlHsxx[item.name] = item.value;
    //     })
    // }

    $.ajax({
        url: getContextPath() + "/slym/sw/hsxxmx",
        type: 'PATCH',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(bdcSlHsxxMxList),
        success: function (data) {
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

}

//保存受理申请人信息(申请人以及家庭成员)
function saveSqrDTO() {
    var qlrsxh = 1;
    var ywrsxh = 1;
    var bdcSlSqrDTOList = [];
    var $ycslzrfxx = $('#ycslzrfxx');
    if ($ycslzrfxx.length > 0) {
        //权利人共有方式
        var qlrgyfsgroup ="";
        var bdcSlSqrList = [];
        $(".bdc-form-add").each(function () {
            var bdcSlSqrDTO = {};
            //申请人信息
            var bdcSlSqr = {};
            var bdcSlSqrArray = $(this).find(".layui-inline :input").serializeArray();
            if (bdcSlSqrArray !== null && bdcSlSqrArray.length > 0) {
                bdcSlSqrArray.forEach(function (item, index) {
                    bdcSlSqr[item.name] = item.value;
                });
                if($(this).find('select[name=fwtc]:disabled').length > 0){
                    bdcSlSqr['fwtc'] = $(this).find('select[name=fwtc]').val();
                }
            }
            if (isNotBlank(bdcSlSqr.sqrmc)) {
                if (bdcSlSqr.sqrlb === "1") {
                    bdcSlSqr["sxh"] = qlrsxh;
                    qlrsxh++;
                    qlrgyfsgroup += bdcSlSqr.gyfs + ",";
                } else {
                    bdcSlSqr["sxh"] = ywrsxh;
                    ywrsxh++;
                }
                //将证件号中小写字母改为大写
                toUpperClass(bdcSlSqr,["zjh","dlrzjh","frzjh","lzrzjh"]);

            // 验证转入方申请人身份证是否为18位
            if(bdcSlSqr.sqrlb === "1"){
                var getZjzl = $(this).find('select[name=zjzl]').val(),
                    getZjh = bdcSlSqr.zjh;
                if(getZjzl === "1" && getZjh.length !== 18){
                    throw err = new Error( bdcSlSqr.sqrmc +' '+ '身份证号必须为18位');
                }
            }

                bdcSlSqrDTO.bdcSlSqrDO = bdcSlSqr;
                //家庭成员信息
                var bdcSlJtcyList = [];
                //组织配偶方家庭成员信息
                var poJtcyList =[];
                $(this).find($("[name =jtcyTable] tbody tr")).each(function () {
                    var bdcSlJtcy = {};
                    var bdcSlJtcyArray = $(this).find(":input").serializeArray();
                    if (bdcSlJtcyArray !== null && bdcSlJtcyArray.length > 0) {
                        bdcSlJtcyArray.forEach(function (item, index) {
                            bdcSlJtcy[item.name] = item.value;
                        });

                         // 验证转入方家庭成员身份证号是否为18位
                        if(bdcSlSqr.sqrlb === "1"){
                            var getZjzl = $(this).find('select[name=zjzl]').val(),
                                getZjh = bdcSlJtcy.zjh;
                            if(getZjzl === "1" && getZjh.length !== 18){
                                throw err = new Error(bdcSlJtcy.jtcymc + ' ' + '身份证号必须为18位');
                            }
                        }

                        //验证未成年子女或兄妹是否满18岁
                        if((bdcSlJtcy.ysqrgx == '未成年子女' || bdcSlJtcy.ysqrgx == '未成年兄妹') && (bdcSlJtcy.zjh.length == 15 || bdcSlJtcy.zjh.length == 18)){
                            var getZjh = bdcSlJtcy.zjh;
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
                                var birthDay = "";
                                birthDay = getZjh.substring(6, 12);
                                birthDay = "19" + birthDay;
                                birthDay = birthDay.substring(0, 4);
                                getAge = yearfull - birthDay;//年龄
                            }
                            if(getAge > 18){
                                throw SyntaxError('未成年子女或兄妹>18岁');
                            }
                            console.log(getAge);
                        }
                    }
                    if (isNotBlank(bdcSlJtcy.jtcymc)) {
                        //将证件号中小写字母改为大写
                        toUpperClass(bdcSlJtcy,["zjh"]);
                        bdcSlJtcyList.push(bdcSlJtcy);
                    }
                    //未成年子女
                    if(bdcSlJtcy.ysqrgx ==="未成年子女"){
                        poJtcyList.push(bdcSlJtcy);
                    }
                });
                bdcSlSqrDTO.bdcSlJtcyDOList = bdcSlJtcyList;


                if(bdcSlSqr.gyrsffq ==="1"){
                    //共有人为夫妻,将当前申请人作为另一申请人的配偶
                    var poJtcy = {};
                    poJtcy.ysqrgx = "配偶";
                    poJtcy.jtcymc = bdcSlSqr.sqrmc;
                    poJtcy.zjzl = bdcSlSqr.zjzl;
                    poJtcy.zjh = bdcSlSqr.zjh;
                    poJtcyList.push(poJtcy);

                }
                bdcSlSqrDTO.poJtcyList =poJtcyList;
                bdcSlSqrDTOList.push(bdcSlSqrDTO);
                bdcSlSqrList.push(bdcSlSqr);
                //共有人为夫妻的处理
                dealFqJtcyxx(bdcSlSqrDTOList);
                checkSkjm(bdcSlSqrDTO);
            }

        });
        //验证共有方式
        if (!checkGyfs(qlrgyfsgroup, qlrsxh-1)) {
            throw err = new Error('共有方式不正确，请检查！');

        }
        //验证共有比例
        if (!checkAddGybl(bdcSlSqrList)) {
            throw err = new Error('共有比例不正确，请检查！');
        }
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
function deleteJtcy(jtcyid, elem) {
    var url = getContextPath() + "/slym/jtcy?jtcyid=" + jtcyid;
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

//保存受理项目信息
function saveSlxmxx(){
    var bdcSlXmData ={};
    var slxmArray = $(".bdcxm");
    if (slxmArray !== null && slxmArray.length > 0) {
        slxmArray.serializeArray().forEach(function (item, index) {
            bdcSlXmData[item.name] = item.value;
        });
        if (isNotBlank(bdcSlXmData)) {
            bdcSlXmData.xmid = xmid;
            getReturnData("/gwc/updateBdcSlXm", JSON.stringify(bdcSlXmData), "PATCH", function (data) {

            }, function (error) {
                delAjaxErrorMsg(error);

            });
        }
    }

}
//保存一窗受理信息
function saveYcslXx() {
    checkForYcsl();
     saveHtxx(".htxx");
     saveFwxx(".fwxx");
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
    var $ycslzrfxx = $('#ycslzrfxx');
    if ($ycslzrfxx.length > 0) {
        $(".bdc-form-add").each(function () {
            //申请人信息
            var bdcSlSqr = {};
            var bdcSlSqrArray = $(this).find(".layui-inline :input").serializeArray();
            if (bdcSlSqrArray !== null && bdcSlSqrArray.length > 0) {
                bdcSlSqrArray.forEach(function (item, index) {
                    bdcSlSqr[item.name] = item.value;
                });
            }
            // 只获取权利人中非本地户籍的进行验证
            if (isNotBlank(bdcSlSqr.sqrmc) && bdcSlSqr.sqrlb === "1" && bdcSlSqr.sfbdhj === "0") {
                var qlrParamMap = {};
                var bdcGzYzsjDTO ={};
                qlrParamMap.name = bdcSlSqr.sqrmc;
                qlrParamMap.cardNo = bdcSlSqr.zjh;
                bdcGzYzsjDTO.name = bdcSlSqr.sqrmc;
                bdcGzYzsjDTO.cardNo = bdcSlSqr.zjh;
                bdcGzYzsjDTO.obj =qlrParamMap;
                qlrParamList.push(bdcGzYzsjDTO);
            }
        });
    }
    return qlrParamList;
}

//维修资金验证---点击维修资金验证按钮
function wxzjYz(){
    getReturnData("/ycsl/queryHfwxzjJnzt",{xmid:xmid},"GET",function (data) {
        if(data){
            if(data ==="1"){
                ityzl_SHOW_INFO_LAYER("维修资金已缴");
            }else if(data ==="2"){
                ityzl_SHOW_INFO_LAYER("维修资金免缴");
            }else{
                ityzl_SHOW_INFO_LAYER("维修资金未缴");
            }
        }else{
            ityzl_SHOW_WARN_LAYER("维修资金状态获取失败");
        }
    },function (error) {
        delAjaxErrorMsg(error);

    })


}

//推送一窗创建登记流程
function tsBdcDjxx(){
    addModel();
    var bdcTsDjxxRequestDTO ={};
    bdcTsDjxxRequestDTO.jbxxid =jbxxid;
    bdcTsDjxxRequestDTO.gzlslid =processInsId;
    var bdcYcslPzDTO ={};
    //自动转发
    bdcYcslPzDTO.autoTurn =false;
    bdcYcslPzDTO.gyslbh =true;
    bdcTsDjxxRequestDTO.bdcYcslPzDTO =bdcYcslPzDTO;
    getReturnData("/ycsl/tsBdcDjxx",JSON.stringify(bdcTsDjxxRequestDTO),"POST",function (data) {
        removeModal();
        if(data.lczt ==="1"){
            showAlertDialog("创建失败"+ (isNotBlank(data.msg)? ",失败原因为："+data.msg:""));
        }else if(data.lczt ==="2"){
            //ityzl_SHOW_SUCCESS_LAYER("创建成功,受理编号:"+data.slbh);
            layer.msg('创建成功');
            setTimeout(function () {
                window.open("/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId);
            }, 1000);
        }else if(data.lczt ==="3"){
            //创建成功,转发失败
            layer.msg('创建成功，自动转发失败'+ (isNotBlank(data.msg)? ",失败原因为："+data.msg:""), {
                time: 1000
            }, function () {
                window.open("/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId);
            });
        }else if(data.lczt ==="4"){
            ityzl_SHOW_SUCCESS_LAYER("自动转发成功,受理编号:"+data.slbh);
        }else{
            showAlertDialog("创建失败");
        }
    },function (error) {
        delAjaxErrorMsg(error);
        
    })



}

function checkForYcsl() {
    //1.面积验证
    var htmj = $("#htmj").val();
    var dzwmj = $("#jzmj").val();
    if (htmj !== undefined) {
        if (dzwmj === "") {
            $("#jzmj").addClass('layui-form-danger');
            throw new Error("建筑面积为空");
        } else if (htmj === "") {
            $('.layui-tab-item').removeClass('layui-show');
            $("#htmj").parents('.layui-tab-item').addClass('layui-show');
            $("#htmj").addClass('layui-form-danger');
            throw new Error("合同面积为空");
        } else if (dzwmj !== htmj) {
            throw new Error("请核对建筑面积");
        }
    }
}

//共有人为夫妻的处理
function dealFqJtcyxx(bdcSlSqrDTOList){
    //只有当转入方或者转出方申请人为两个，并且共有人是否夫妻关系为是的时候处理双方家庭成员信息
    if(bdcSlSqrDTOList.length ===2 && bdcSlSqrDTOList[0].bdcSlSqrDO !="{}" &&bdcSlSqrDTOList[0].bdcSlSqrDO.gyrsffq ==="1" &&bdcSlSqrDTOList[1].bdcSlSqrDO !="{}" &&bdcSlSqrDTOList[1].bdcSlSqrDO.gyrsffq ==="1"){
        for(var i = 0; i < bdcSlSqrDTOList.length; i++){
            var jtcyList =bdcSlSqrDTOList[i].bdcSlJtcyDOList;
            //如果共有人为夫妻，则互为配偶,并且双方继承对方的未成年子女信息
            for(var j = 0; j < bdcSlSqrDTOList[1-i].poJtcyList.length; j++){
                var poJtcy =bdcSlSqrDTOList[1-i].poJtcyList[j];
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

//税款减免验证：税款减免为是时，婚姻状况不能为空;税款减免为是且婚姻状况为已婚时，家庭成员信息必须录入配偶信息
function checkSkjm(bdcSlSqrDTO){
    var bdcSlSqrDO = bdcSlSqrDTO.bdcSlSqrDO;
    var bdcSlJtcyDOList = bdcSlSqrDTO.bdcSlJtcyDOList;
    if(isNotBlank(bdcSlSqrDO)) {
        if(bdcSlSqrDO.sfjm === "1"){
            if(bdcSlSqrDO.hyzk === ""){
                throw new Error("婚姻状况不能为空");
            }
            else if(bdcSlSqrDO.hyzk === "已婚") {
                var poList = [];
                if(bdcSlJtcyDOList.length <= 0){
                    throw new Error("未录入配偶信息");
                }else {
                    for(var i=0;i<bdcSlJtcyDOList.length;i++) {
                        var bdcSlJtcy = bdcSlJtcyDOList[i];
                        if(bdcSlJtcy.ysqrgx === "配偶") {
                            poList.push(bdcSlJtcy);
                        }
                    }
                    if(poList.length<=0){
                        throw new Error("未录入配偶信息");
                    }
                }
            }
        }
    }
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
        $('#ycslfwxx').find('#jddm option').remove();
        $('.basic-info').find('#jddm').append("<option value=''>请选择</option>");
        for(var i=0;i<jddmList.length;i++){
            var zdjddm =jddmList[i];
            if(zdjddm.DM.length >=6) {
                var jddmDz = zdjddm.DM.substring(0, 6);
                if (jddmDz === xzqh) {
                    if(zdjddm.DM ===jddm){
                        $('.basic-info').find('#jddm').append("<option value='" + zdjddm.DM + "' title='" + zdjddm.MC + "' selected=\"selected\">" + zdjddm.MC + "</option>");

                    }else {
                        $('.basic-info').find('#jddm').append("<option value='" + zdjddm.DM + "' title='" + zdjddm.MC + "'>" + zdjddm.MC + "</option>");
                    }
                }
            }
        }
    }
}

//单价计算公式：单价 = 交易价格 / 建筑面积
function djJs() {
    var jyje = $('#jyje').val(),
        jzmj = $('#jzmj').val();

    if(isNotBlank(jyje) && isNotBlank(jzmj) && jzmj !== 0){
        var predj = $('#dj').val(),
            dj = ((jyje * 10000)/ jzmj).toFixed(2);
        if(dj !== predj){
            $('#ycsljyxx').find('#dj').val(dj);
        }
    }
}

// 一窗受理申请单打印入口。先判断权利人类型，在进行打印
function printYcslsqd(){
    $.ajax({
        url: getContextPath() + "/slym/sqr/sqrxx",
        type: 'GET',
        dataType: 'json',
        contentType: "application/json",
        data: {
            xmid : xmid,
            sqrlb : "1"
        },
        success: function (data) {
            if(data.length == 0){
                delAjaxErrorMsg("未获取到申请人信息。")
            }else{
                if(data[0].sqrlx == 1) {
                    // 打印个人申请单
                    printYcsl("ycslgrsqd",xmid);
                }else{
                    // 打印单位申请单
                    printYcsl("ycsldwsqd",xmid);
                }
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

}

// 一窗受理收件单打印，批量流程为列表模式，传xmid判断每一个单独的流程
function printYcslsjd(dylx) {
    printYcsl(dylx,xmid);
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
    $('.form-margin-area .bdc-basic-info').each(function () {
        $('[lay-filter="zjzl"]').each(function () {
            addSfzYz($(this).val(),$(this));
        })
        $('[lay-filter="zjzlTd"]').each(function () {
            addSfzYz($(this).val(),$(this));
        })
    });
}

