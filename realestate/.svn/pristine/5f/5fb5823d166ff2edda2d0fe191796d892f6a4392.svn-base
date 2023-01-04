//字典列表
var zdList;
//地址栏获取数据
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var zxlc = getQueryString("zxlc");
var xmid = getQueryString("xmid");

var $,laytpl,form;
var bdcXmDo =[];
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
            var gzyzParamMap ={};
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
                                //保存完后重新加载页面
                                loadYcslxx();
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

    //按钮加载
    function loadButtonArea() {
        var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
        //渲染数据
        laytpl(tpl).render({}, function (html) {
            view.innerHTML = html;
        });
        form.render();
        renderForm();
        disabledAddFa();
    }
    // 字典表加载
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

    //加载基本信息模块（入口）
    function loadJbxx() {
        getReturnData("/slym/xm", {processInsId: processInsId}, 'GET', function (data) {
            bdcSlXmList = [];
            if(data) {
                bdcXmDo = data;
                //判断页面入参是否存在xmid
                if (!isNotBlank(xmid)) {
                    xmid = data.xmid;
                }
            }else{
                layer.alert("当前未生成涉税数据，请确认！");
            }
        }, function (err) {
            delAjaxErrorMsg(err);
        },false);
    }

    // 加载一窗信息
    window.loadYcslxx = function (){
        getReturnData("/ycsl/bengbu", {xmid:xmid}, "GET", function (data) {
            loadQlrYwrxx(1);
            loadQlrYwrxx(2);
            generateSlxx();
            generateFwxx(data.bdcSlFwxxDO !== null ? data.bdcSlFwxxDO : {},data.bdcSlHsxxDO !== null ? data.bdcSlHsxxDO : {});
            generateJyxx(data.bdcSlFwxxDO !== null ? data.bdcSlFwxxDO : {},data.bdcSlJyxxDO !== null ? data.bdcSlJyxxDO :{},data.bdcSlHsxxDO !== null ? data.bdcSlHsxxDO : {});
            form.render();
            renderSelect();
            renderForm();
            form.render("select");
            disabledAddFa();
            renderDate(form);
            removeModal();
            // reloadZjhYz();

        }, function (error) {
        }, false);
    };

 });

//加载权利人义务人信息
function loadQlrYwrxx(qlrlb) {
    getReturnData("/slym/sqr/sqrxx", {xmid:xmid, sqrlb:qlrlb}, "GET", function (data) {
        generateQlYwrxx(data,qlrlb);
    }, function (error) {
    }, false);
}

function generateQlYwrxx(bdcQlrDOList,sqrlb) {
    var json = {
        zd: zdList,
        bdcQlrDOList: bdcQlrDOList,
        sqrlb:sqrlb
    };
    if(sqrlb ===1) {
        view = document.getElementById('qlrxx');
    }else{
        view = document.getElementById('ywrxx');
    }
    if(view != null) {
        var tpl = qlrxxTpl.innerHTML;
        //渲染转入方数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
    }
}

// 加载受理信息
function generateSlxx() {
    var json = {
        zd: zdList,
        bdcXmDo: bdcXmDo
    };
    var tpl = slxxTpl.innerHTML, view = document.getElementById('slxx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
}

// 加载房产信息
function generateFwxx(bdcSlFwxxDO,bdcSlHsxxDO) {
    var json = {
        zd: zdList,
        bdcSlFwxx: bdcSlFwxxDO,
        bdcXm:bdcXmDo,
        bdcslHsxx: bdcSlHsxxDO
    };
    var tpl = fcxxTpl.innerHTML, view = document.getElementById('fcxx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

    if( !isNotBlank(bdcSlFwxxDO.fwxxid)){
        layer.alert("当前未生成涉税房屋信息数据，请确认！");
    }
}

// 加载交易信息
function generateJyxx(bdcSlFwxxDO,bdcSlJyxxDO,bdcSlHsxxDO) {
    var json = {
        zd: zdList,
        bdcSlFwxx: bdcSlFwxxDO,
        bdcSlJyxx: bdcSlJyxxDO,
        bdcSlHsxx: bdcSlHsxxDO
    };
    var tpl = jyxxTpl.innerHTML, view = document.getElementById('jyxx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
}

//保存一窗受理信息
function saveYcslXx() {
    saveHtxx(".jyxx");
    saveFwxx(".fwxx");
    saveHsxx(".hsxx");
}

// 保存交易信息
function saveHtxx(formClass) {
    var htxxData = {};
    var htxxHtml = document.getElementById('jyxx');
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
// 保存房屋信息
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
                // ityzl_SHOW_SUCCESS_LAYER("提交成功");
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }

}
//保存核税信息
function saveHsxx(formClass) {
    var hsxxData = {};
    var hsxxArray = $(formClass);
    if (hsxxArray !== null && hsxxArray.length > 0) {
        hsxxArray.serializeArray().forEach(function (item, index) {
            hsxxData[item.name] = item.value;
        });
        hsxxData.xmid = xmid;
        hsxxData.sqrlb = '1';
        $.ajax({
            url: getContextPath() + "/ycsl/hsxx",
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(hsxxData),
            success: function (data) {
                // ityzl_SHOW_SUCCESS_LAYER("提交成功");
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }
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
    $(".bdc-jt-table[name =sqrGroupDTOTable] tbody tr").each(function () {
        var $tr =$(this);

        var bdcSlSqrGroupDTOArray = $tr.find(":input").serializeArray();
        if (bdcSlSqrGroupDTOArray !== null && bdcSlSqrGroupDTOArray.length > 0) {
            var bdcSlSqrGroupDTO ={};
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
                    // 只获取权利人中非本地户籍的进行验证
                    if (isNotBlank(bdcSlSqrGroupDTO.mc) && bdcSlSqrGroupDTO.sqrlb === "1" && bdcSlSqrGroupDTO.sfbdhj === "0") {
                        var qlrParamMap = {};
                        var bdcGzYzsjDTO ={};
                        qlrParamMap.name = bdcSlSqrGroupDTO.mc;
                        qlrParamMap.cardNo = bdcSlSqrGroupDTO.zjh;
                        bdcGzYzsjDTO.name = bdcSlSqrGroupDTO.mc;
                        bdcGzYzsjDTO.cardNo = bdcSlSqrGroupDTO.zjh;
                        bdcGzYzsjDTO.obj =qlrParamMap;
                        qlrParamList.push(bdcGzYzsjDTO);
                    }


                }

            }

        }
    });
    return qlrParamList;
}

//对表单的一系列渲染操作
function renderForm(){
    //表单权限控制
    getStateById(readOnly, formStateId, 'ycym');

}