/**
 * author: <a href="mailto:chenyucheng@gtmap.cn>gaolining</a>
 * version 1.0.0  2021/02/22
 * describe: 不动产登记中心权属状态修正js
 */
var $, form, layer, element, table, laytpl, laydate;
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var taskId = getQueryString("taskId");
var zdList = {a:[]};
var sjclNumber = 0;
var sjclids = new Set();
//用于存放所有的收件材料id
var sjclidLists = [];
//页面重新加载清空存储的收件材料id session
sessionStorage.removeItem("yxsjclids");
sessionStorage.removeItem("sjclidLists");
var xzxxid = getQueryString("xzxxid");

layui.use(['laydate', 'form', 'jquery', 'laytpl', 'layer', 'table'], function () {
    $ = layui.jquery,
        laydate = layui.laydate,
        form = layui.form,
        layer = layui.layer,
        table = layui.table,
        laytpl = layui.laytpl;
    $(function () {
        addModel();
        getZdList();
        loadXzxx();
        loadSjcl();
        // 页面验证
        var isSubmit = true;
        var verifyMsg = "";
        form.verify({
            required: function (value, item) {
                if (value == '' || value == null || value == undefined) {//判断是否为空
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    verifyMsg = "必填项不能为空";
                }
            }
        });

        // 保存
        form.on('submit(saveData)', function (data) {
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                isFirst = true;
                verifyMsg = "";
                removeModal();
                return false;
            } else {
                addModel();
                setTimeout(function () {
                    try{
                        $.when(saveJbxx(), saveSjcl()).done(function () {
                            //异步该变权属状态（现在受理节点不能更新权属状态）
                            // changeQszt();
                            ityzl_SHOW_SUCCESS_LAYER("保存成功");
                            loadXzxx();
                            removeModal();
                            form.render();
                        })
                    }catch (e) {
                        removeModal();
                        ityzl_SHOW_WARN_LAYER(e.message);
                    }
                } , 50);
            }
        });

        //监听复选框选择
        //全选

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


        // 根据工作流实例id获取需求流转信息实体
        function loadXzxx() {
            addModel();
            $.ajax({
                url: getContextPath() + '/slym/xm/queryBdcSlJbxxByGzlslid',
                async: false,
                data: {processInsId: processInsId},
                dataType: 'json',
                type: 'GET',
                success: function (data) {
                    if (isNotBlank(data)) {
                        if(data.slsj && data.slsj.length > 11){
                            var slsj = data.slsj.substring(0,10);
                            data.slsj = slsj;
                        }
                        // 渲染模板
                        var tpl = xzxxTpl.innerHTML
                            ,view = document.getElementById('xzxxView');
                        laytpl(tpl).render(data, function(html){
                            view.innerHTML = html;
                        });

                        // 渲染日期
                        laydate.render({
                            elem: '#slsj',
                            trigger: 'click',
                            format: 'yyyy-MM-dd'
                        });
                        renderDate(form, formStateId);
                        form.render();
                        getStateById(readOnly, formStateId, 'xzxx');
                        removeModal();
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }


    });
});

//查询收件材料所需参数（打开附件上传使用）
function queryBdcSlWjscDTOXZ(clmc,sfhqqx,bxzlcGzlslid) {
    var bdcSlWjscDTO = {};
    $.ajax({
        url: '/realestate-accept-ui/slym/sjcl/bdcSlWjscDTO',
        type: 'GET',
        async: false,
        data: {processInsId: bxzlcGzlslid,clmc: clmc,sfhqqx: sfhqqx},
        success: function (data) {
            if (isNotBlank(data)) {
                bdcSlWjscDTO = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return bdcSlWjscDTO;
}

//打开附件上传页面
function openSjclXzlc(url,sfsx, title,bxzlcGzlslid) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true,
        end: function () {
        }
    });
    layer.full(index);
}

function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        generateSjcl(data, "");
    }, function (err) {
        console.log(err);
    });
}

//组织收件材料到页面
function generateSjcl(data, xmid) {
    sjclNumber = data.length;
    sjclidLists = [];
    if(data !== null && data.length >0) {
        for(var i=0;i<data.length;i++) {
            sjclidLists.push(data[i].sjclid);
        }
    }
    sessionStorage.setItem("sjclidLists",sjclidLists);
    var json = {
        xmid: xmid,
        bdcSlSjclDOList: data,
        zd:zdList
    };
    var tpl = sjclTpl.innerHTML, view = document.getElementById("sjcl");
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    //renderForm();
    getStateById(readOnly, formStateId, 'xzxx', 'sjcl');
    form.render('select');
    disabledAddFa("sjclForm");
    removeModal();
}

//保存基本信息
function saveJbxx() {
    var jbxxArray = $(".jbxx").serializeArray();
    var jbxx = {};
    if (jbxxArray !== null && jbxxArray.length > 0) {
        jbxxArray.forEach(function (item, index) {
            jbxx[item.name] = item.value;
        });
    }
    if(Object.keys(jbxx).length >1 &&isNotBlank(jbxx.jbxxid)) {
        getReturnData("/slym/xm/jbxx", JSON.stringify(jbxx), 'PATCH', function (data) {
        }, function (err) {
            throw err;
        }, false);
    }
}

function changeQszt() {
    getReturnData("/slym/xm/yxmqszt",{gzlslid: processInsId},"GET",function (data) {
        console.log("原项目权属状态更新成功");
    },function (xhr) {

    })
}
