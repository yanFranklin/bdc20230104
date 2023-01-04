var qlhb = getQueryString("qlhb");
var xmid = getQueryString("xmid");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var zxlc = getQueryString("zxlc");
var type = getQueryString("type");
$(function () {
    addModel();
    setTimeout(function () {
        try {
            $.when(loadData()).done(function () {
                removeModal();
            })
        } catch (e) {
            removeModal();
            delAjaxErrorMsg(e,"加载失败");
            return
        }
    }, 10);
});
var zdList;
//是否虚拟号
var isXndyh =false;
var qllx,djxl;

//获取页面数据
function loadData() {
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
                loadBdcdyh()
            }
        }
    });
}


function loadBdcdyh() {
    $.ajax({
        url: getContextPath() + "/slym/xm/xx",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid},
        success: function (data) {
            if (isNotBlank(data)) {
                getReturnData("/slym/xm/fb",{xmid:xmid},"GET",function (result) {
                    //判断是否是虚拟单元号
                    isXndyh =checkXndyh(data.bdcdyh);
                    generateBdcdy(data,result);
                    qllx =data.qllx;
                    djxl =data.djxl;
                },function (xhr) {
                    delAjaxErrorMsg(xhr);
                })
            }
        }
    });
}


function generateBdcdy(bdcXm,bdcXmfb) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var xmid = bdcXm.xmid;
        var bdcdyh = bdcXm.bdcdyh;
        var sfzf = getSfzfByXmid(xmid);
        var yhfs = getYhfs(bdcdyh);
        var hydb = getHydb(bdcdyh);
        var json = {
            bdcXm: bdcXm,
            xmfb: bdcXmfb,
            zd: zdList,
            sfzf:sfzf,
            hyllc:hyllc,
            yhfs:yhfs,
            hydb:hydb
        };
        var tpl = bdcdyTpl.innerHTML, view = document.getElementById('bdc-popup-large');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        renderSelect();
        getStateById(readOnly, formStateId, 'bdcdy');
        //所有业务宗地用途,权利性质为空时可编辑
        changeZdState(form);
        form.render("select");
        disabledAddFa();
        renderDate(form);
        //点击提交时不为空的全部标红
        var isSubmit = true;
        form.verify({
            required: function (value, item) {
                if (value == '' &&!isXndyh) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                }
            }
        });

        //提交表单
        form.on("submit(updateBdcdy)", function (data) {
            if (!isSubmit) {
                layer.msg('必填项不能为空', {icon: 5, time: 3000});
                isSubmit = true;
                return false;
            } else {
                updateBdcdy(data.field);
                if($("#sfzf") && $("#sfzf").length >0){
                    var sfzf = $("#sfzf").val();
                    saveSfzfByXmid(sfzf,data.field.xmid);
                }

                return false;
            }
        });

        //权利信息与单元信息组合大页面所需监听事件
        if($('select[name=dzwyt]').length > 0 && $.isFunction(window.parent.getSelectData)){
            form.on("select(dzwytFilter)",function(data){
                window.parent.getSelectData('dzwytFilter',data);
            });
        }
        if($('select[name=qlxz]').length > 0 && $.isFunction(window.parent.getSelectData)){
            form.on("select(qlxzFilter)", function (data) {
                window.parent.getSelectData('qlxzFilter', data);
            });
        }
        if ($('input[name=dzwmj]').length > 0 && $.isFunction(window.parent.getSelectData)) {
            $('input[name=dzwmj]').on('input propertychange', function () {
                var changeData = $(this).val();
                window.parent.getSelectData('dzwmj', changeData);
            });
        }
        if ($('input[name=jyhth]').length > 0 && $.isFunction(window.parent.getSelectData)) {
            $('input[name=jyhth]').on('input propertychange', function () {
                var changeData = $(this).val();
                window.parent.getSelectData('jyhthdy', changeData);
            });
        }
        //监听用海方式修改
        form.on('select(yhfs)', function (data) {
            var yhfs = $("#yhfs").val() ||'';
            $.ajax({
                url: getContextPath() + "/rest/v1.0/slym/updateYhfs?bdcdyh=" + bdcdyh + "&yhfs=" + yhfs,
                type: 'POST',
                async: false,
                contentType: "application/json",
                success: function (data) {
                    ityzl_SHOW_SUCCESS_LAYER("修改成功");
                }, error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        });
        //监听海域等级修改
        form.on('select(hydb)', function (data) {
            var hydb = $("#hydb").val() ||'';
            $.ajax({
                url: getContextPath() + "/rest/v1.0/slym/updateDb?bdcdyh=" + bdcdyh + "&hydb=" + hydb,
                type: 'POST',
                async: false,
                contentType: "application/json",
                success: function (data) {
                    ityzl_SHOW_SUCCESS_LAYER("修改成功");
                }, error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        });
    })
}

function updateBdcdy(bdcdyData) {
    var url = getContextPath() + "/slym/xm";
    if(type=="xxbl"){
        url = getContextPath() + "/slym/xm/saveXxblxm";
    }
    $.ajax({
        url: url,
        type: 'PATCH',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(bdcdyData),
        success: function (data) {
            if (data > 0) {
                //更新冗余字段,不考虑注销类流程
                if(zxlc !=="true") {
                    updateRyzd(bdcdyData.xmid);
                }

                if(qlhb == null){
                    // 保存受理页面的收费信息
                    if (parent && 'function' === typeof (parent.saveSf)) {
                        parent.saveSf();
                    }
                    ityzl_SHOW_SUCCESS_LAYER("单元信息保存成功");

                    if (parent && 'function' === typeof (parent.getAllBdcdy)) {
                        //修改完成后，外面表格刷新一次。
                        //处理头部的面积等信息
                        if (parent && 'function' === typeof (parent.dealHjxx)) {
                            parent.dealHjxx();
                        }
                    }else{
                        //处理头部的面积等信息
                        if (parent && 'function' === typeof (parent.dealHjxx)) {
                            parent.dealHjxx(qllx,djxl);
                        }
                    }

                    var config = parent.tableConfig;
                    var pageNum = parent.$('.layui-laypage-default').find('span:eq(0)').find('input').val();
                    // 分页情况下，非第一页，重载后需要翻到当前页
                    if(pageNum && parseInt(pageNum)>1){
                        config['page'] = {'curr':parseInt(pageNum)}
                    }
                    parent.loadDataTablbeByUrl('#bdcdyTable', config);

                    if(window.parent.$('.layui-laypage-btn')[0] != undefined){
                        window.parent.$('.layui-laypage-btn')[0].click();
                    }
                }else {
                    parent.saveHtxxAfter(qllx,djxl);
                }
            }
        }
    });

    //保存项目附表信息
    var bdcXmfbData = {};
    var bdcXmfbArray = $('.xmfb');
    if (bdcXmfbArray !== null && bdcXmfbArray.length > 0) {
        bdcXmfbArray.serializeArray().forEach(function (item, index) {
            bdcXmfbData[item.name] = item.value;
        });
        getReturnData("/slym/xm/fb", JSON.stringify(bdcXmfbData), 'PATCH', function (data) {
            console.log("保存项目附表成功")
        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);
    }
}

//更新冗余字段
function updateRyzd(xmid) {
    //更新权利表相关字段
    var qlData = {};
    //坐落同步
    qlData.zl = $("#zl").val();
    //面积同步
    qlData.jzmj = $("#dzwmj").val();
    //用途同步
    qlData.ghyt =$("#dzwyt").val();
    getReturnData("/slym/ql/updateQlRyzd?xmid=" + xmid, JSON.stringify(qlData), 'PATCH', function (data) {

    }, function (err) {
        throw err;
    }, false);

}

function cancelEdit() {
    window.parent.cancelEdit();
}

function cancle() {
    window.parent.cancelEdit();
}

//宗地用途、权利性质这两个字段为空，则允许编辑。
function changeZdState(form){
    var $zdyt = $('.bdc-zdzhyt');
    var $qlxz = $('.bdc-qlxz');
    $zdyt.each(function(i){
        if($($zdyt[i]).val() == ''){
            $($zdyt[i]).removeAttr('disabled');
            $($zdyt[i]).parent().removeClass('bdc-date-disabled');
            $($zdyt[i]).siblings('img').remove();
        }
        form.render();
    });
    $qlxz.each(function(i){
        if($($qlxz[i]).val() == ''){
            $($qlxz[i]).removeAttr('disabled');
            $($qlxz[i]).parent().removeClass('bdc-date-disabled');
            $($qlxz[i]).siblings('img').remove();
        }
        form.render();
    });
}



function getYhfs(bdcdyh){
    var yhfs = "";
    if(hyllc){
        getReturnData("/rest/v1.0/slym/queryYhfs?bdcdyh="+bdcdyh,"","GET",function (result) {
            if(result){
                yhfs =result;
            }
        },function (error) {
            delAjaxErrorMsg(error);
        },false);
    }
    return yhfs;
}
function getHydb(bdcdyh){
    var hydb = "";
    if(hyllc){
        getReturnData("/rest/v1.0/slym/queryDb?bdcdyh="+bdcdyh,"","GET",function (result) {
            if(result){
                hydb =result;
            }
        },function (error) {
            delAjaxErrorMsg(error);
        },false);
    }
    return hydb;
}

