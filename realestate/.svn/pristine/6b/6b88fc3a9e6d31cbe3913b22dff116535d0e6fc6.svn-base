/*获取字典数据*/
var table;
var form;
var laydate;
var $, layer, element, laytpl;
var baid = getQueryString("baid");
var isView = getQueryString("isView");
var fwhsindex = getQueryString("fwhsindex");
var bdcdyh = getQueryString("bdcdyh");
var fwdcbindex = "";
var result = "";
// 合同备案信息打印模板地址
var htbaxxModelUrl = getIP() + "/realestate-etl/static/printmodel/htbaxx.fr3";
$(function () {
    layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
        table = layui.table;
        form = layui.form;
        laydate = layui.laydate;
        $ = layui.jquery;
        element = layui.element;
        layer = layui.layer;
        laytpl = layui.laytpl;
        setTimeout(function () {
            try {
                $.when(loadZdData()).done(function () {
                    addModel('加载中');
                    //加载也面前判断是否存在fwhsindex，如果存在是从楼盘表打开，后台逻辑处理备案信息，返回baid
                    if (isNotBlank(bdcdyh)) {
                        getReturnData("/rest/v1.0/htba", {bdcdyh: bdcdyh}, "GET", function (data) {
                            if (data) {
                                baid = data;
                            }
                        }, function (xhr) {
                            delAjaxErrorMsg(xhr);
                        }, false)
                    }
                    loadHtbaData();
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e, "加载失败");
                return;
            }
        }, 10);
        //保存按钮点击事件
        form.on("submit(saveHtbaxx)", function (data) {
            if (isNotBlank(result) && !result.isSubmit) {
                layer.msg(result.verifyMsg, {icon: 5, time: 3000});
                result.isSubmit = true;
            } else {
                addModel();
                //判断是否已备案
                getReturnData("/rest/v1.0/htbaxx/sfyba/"+baid,{},"GET",function (data) {
                    if(!data) {
                        setTimeout(function () {
                            try {
                                $.when(saveHtbaxx(), saveHtbaQlr()).done(function () {
                                    removeModal();
                                    ityzl_SHOW_SUCCESS_LAYER("保存成功");
                                })
                            } catch (e) {
                                removeModal();
                                ERROR_CONFIRM("保存失败", e.message);
                                return
                            }
                        }, 10);
                    } else {
                        removeModal();
                        ityzl_SHOW_WARN_LAYER('已备案,无需再次备案')
                    }
                },function (xhr) {
                    delAjaxErrorMsg(xhr);
                })
            }
            return false;
        });

        // 买受人行增删
        $(document).on('click', '.add-msr', function () {
            var json = {
                zd: zdList
            };
            var getTpl = addMsrTpl.innerHTML;
            laytpl(getTpl).render(json, function (html) {
                $('#htbaxx .layui-table').append(html);
            });
            form.render('select');
        });

        $(document).on('click', '.del-msr', function () {
            addModel()
            $(this).parents('tr').remove();

            if ($('.msr-tr').length == 0) {
                var json = {
                    zd: zdList
                };
                var getTpl = addMsrTpl.innerHTML;
                laytpl(getTpl).render(json, function (html) {
                    $('#htbaxx .layui-table').append(html);
                });
            }
            form.render('select');
            removeModal();
        });

        $(document).on('click','#selectHs',function () {
            selectHs();
        });

        $('#printBtn').on('click', function (data) {
            printHtbaxx('htbaxx');
        });
        $('#downloadPDF').on('click', function (data) {
            downloadPDF('htbaxxpdf');
        });


    });

});

function loadZdData() {
    //获取字典
    getCommonZd(function (data) {
        if (isNotBlank(data)) {
            zdList = data;
        }
    });
}

//加载备案合同信息数据两个模块
//1.合同备案信息2.房屋信息
function generateBahtxx(bahtxx) {
    //1.合同备案信息
    var json = {
        htbaspf: bahtxx.htbaSpfDO,
        htbaqlr: bahtxx.htbaQlrDOList,
        htbafwxx: bahtxx.fwHsDOList,
        htbaljz: bahtxx.fwLjzDO,
        zd: zdList
    };
    console.log(json);
    var tpl = htbaxxTpl.innerHTML, view = document.getElementById('htbaxx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    //2.房屋信息
    var fwtpl = htbafwxxTpl.innerHTML, fwview = document.getElementById('fwxx');
    laytpl(fwtpl).render(json, function (html) {
        fwview.innerHTML = html;
    });
    form.render();
    renderDate(form, 'htbaxx', 'yyyy-MM-dd');
    renderSelect();
    form.on('select(zjzl)', function (data) {
        addSfyz(data.value, data.elem);
    });
    $("[lay-filter=zjzl]").each(function () {
        addSfyz($(this).val(), $(this));
    });
    result = verifyform(form);
    form.render('select');
    loadBtn();
}

//保存合同备案信息和合同备案权利人信息
function saveHtbaxx() {
    var htbaform;
    htbaform = $(".htbaform");
    htbaform.each(function () {
        var htbaspfxx = {};
        var htbaspfArray = $(this).find(".htbaspf").serializeArray();
        if (htbaspfArray !== null && htbaspfArray.length > 0) {
            htbaspfArray.forEach(function (item, index) {
                htbaspfxx[item.name] = item.value;
            })
            //保存即备案
            htbaspfxx.bazt = 1;
        }
        //保存合同备案信息
        $.ajax({
            url: getContextPath() + "/rest/v1.0/htbaxx",
            type: 'PATCH',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(htbaspfxx),
            success: function (data) {
                //保存成功后同步权籍状态
                syncBdcdyBazt(baid);
                //保存成功后，同步一些房管局数据
                syncFgfyxx(baid);
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    });
}

function saveHtbaQlr() {
    var qlrList = [];
    $("#htbaxx").each(function () {
        //权利人信息保存
        $(this).find(".msr-tr").each(function () {
            var qlrArray = $(this).find(".htbaqlr").serializeArray();
            if (qlrArray !== null && qlrArray.length > 0) {
                var htbaqlr = {};
                qlrArray.forEach(function (item, index) {
                    htbaqlr[item.name] = item.value;
                    htbaqlr["baid"] = baid;
                });
                qlrList.push(htbaqlr);
            }
        });
        getReturnData("/rest/v1.0/htbaqlr", JSON.stringify(qlrList), "PATCH", function () {
            //局部重新渲染数据
            loadHtbaData();
        }, function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        })
    });
}

function deleteHtbaQlr(qlrid) {
    layer.confirm('确定删除权利人?', function (index) {
        if (isNotBlank(qlrid)) {
            getReturnData("/rest/v1.0/htbaqlr/" + qlrid, {}, "DELETE", function (data) {
                layer.closeAll();
            }, function (xhr) {
                delAjaxErrorMsg(xhr);
            })
        }
        return false;
    });
}

function loadHtbaData() {
    //获取备案信息数据
    getReturnData("/rest/v1.0/htbaxx/" + baid, {}, "GET", function (data) {
        if (data) {
            if (data.fwLjzDO) {
                fwdcbindex = data.fwLjzDO.fwDcbIndex;
            }
            generateBahtxx(data);
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    });
}

//选择附属设施
function selectHs() {
    addModel();
    var url = getContextPath() + "/view/lpb/hsxx.html?baid=" + baid + "&fwdcbindex=" + fwdcbindex;
    var index = layer.open(
        {
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            title: "备案信息",
            content: url,
            btnAlign: "c",
            end: function (index, layero) {
                loadHtbaData();
                return false;
            }
        }
    )
    layer.full(index);
    removeModal();
}

//权利人身份证读卡器
function sqrReadIdCard(element) {
    var cert = readIdCard();
    if (!cert.ReadCard()) {
        layer.alert("请检查是否安装成功并重新放置");
    } else {
        layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
            var pName = cert.Name;
            var pCardNo = cert.ID;
            var pAddress = cert.Address;
            var forms = element.parentNode.parentNode;
            $(forms).find("input[name='qlrmc']").val(trimStr(pName));
            $(forms).find("select[name='zjzl']").val("1");
            $(forms).find("input[name='zjh']").val($.trim(pCardNo));
            var form = layui.form;
            form.render('select');
        });
    }
}

function addSfyz(zjzl, elem) {
    var zjzlid = $(elem).parent().parent().find("select")[0].id;
    var zjhid = zjzlid.replace("zjzl", "zjh");
    var attrVal = $("#" + zjhid).attr("lay-verify");
    if (zjzl === "1" || zjzl === 1) {
        //增加身份证验证
        if (isNotBlank(attrVal)) {
            if (attrVal.indexOf("identitynew") < 0) {
                $("#" + zjhid).attr("lay-verify", attrVal + "|identitynew");
            }
        } else {
            $("#" + zjhid).attr("lay-verify", "identitynew");
        }
    } else {
        //移除身份证验证
        //增加长度大于五位验证
        if (isNotBlank(attrVal) && attrVal.indexOf("identitynew") > -1) {
            $("#" + zjhid).attr("lay-verify", attrVal.replace("identitynew", ""));
        }
        var newattr = $("#" + zjhid).attr("lay-verify");
        if (isNotBlank(newattr) && newattr.indexOf("zjhlength") < 0) {
            $("#" + zjhid).attr("lay-verify", newattr + "|zjhlength");
        } else if (zjzl !== null && zjzl !== "") {
            $("#" + zjhid).attr("lay-verify", "zjhlength");
        } else if ((zjzl === null || zjzl === "") && isNotBlank(newattr) && newattr.indexOf("zjhlength") > -1) {
            $("#" + zjhid).attr("lay-verify", attrVal.replace("zjhlength", ""));
        }
    }
}

function syncBdcdyBazt(baid) {
    getReturnData("/rest/v1.0/htbaxx/syncbazt/" + baid, {}, "GET", function (data) {
        console.log("同步状态成功")
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

function syncFgfyxx(baid) {
    getReturnData("/rest/v1.0/htbaxx/fgfyxx/" + baid, {}, "GET", function (data) {
        console.log("同步房管数据成功")
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

function loadBtn(){
    if(isView){
        $("#printBtn").show();
        $("#downloadPDF").show();
        $("input,select").prop("disabled",true);
        $(".rowbtn").prop("disabled",true);
    }else{
        $("#selectHs").show();
        $("#saveHtbaxx").show();
    }
}

function printHtbaxx(dylx){
    var dylxArr = [dylx];
    setDypzSession(dylxArr,null);
    var dataUrl = getIP() + "/realestate-etl/rest/v1.0/print/htbaxx/" + dylx + "/" + baid + "/xml";
    printChoice(dylx, 'etl-app', dataUrl, htbaxxModelUrl, false, dylx);
}

function downloadPDF(dylx) {
    var slbh = $("#slbh").val();
    window.location.href = "/realestate-etl/rest/v1.0/print/htbaxx/pdf?baid="+baid+"&dylx="+dylx+"&slbh="+slbh;
}
