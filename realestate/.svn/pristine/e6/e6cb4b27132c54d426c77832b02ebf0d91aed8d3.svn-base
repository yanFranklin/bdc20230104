var isOneWebSource = $.getUrlParam('isOneWebSource');
layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'laytpl'], function() {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laytpl = layui.laytpl;
});

/**
 *
 * @param obj
 * @param data
 * @describe 手动补偿的方法
 */
function sdbc(obj, data){
    // 根据服务名称发送对应的服务
    if(!isNullOrEmpty(data) && '生成证书' == data.FWMC){
        var c = layer.open({
            title: '请填写请求参数',
            type: 1,
            area: ['430px','180px'],
            btn: ['确定', '取消'],
            content: '<div id="bdc-popup-radio">\n' +
                '    <form class="layui-form" action="">\n' +
                '        <div class="layui-form-item pf-form-item">\n' +
                '            <div class="layui-inline" style="width:80%;margin-left: 35px;margin-top: 35px;">\n' +
                '                <label class="layui-form-label" style="width:100px">工作流实例id：</label>\n' +
                '                <div class="layui-input-inline">\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </div>\n' +
                '    </form>\n' +
                '</div>',
            success: function () {
                var form = layui.form;
                var text = "";
                text += "<input type=\"text\" id=\"gzlslid\" name=\"gzlslid\" autocomplete=\"off\" placeholder=\"请输入\" class=\"layui-input\">";
                $('#bdc-popup-radio').find(".layui-input-inline").html(text);
                form.render();
            },
            yes: function (index, layero) {
                var gzlslid = $("input[name='gzlslid']").val();
                if (!isNotBlank(gzlslid)) {
                    warnMsg(" 请填写请求参数！");
                    return false;
                }
                addModel();
                getReturnData("/rest/v1.0/sdbcfw/sczs?gzlslid=" + gzlslid, {}, "GET", function (data) {
                    removeModal();
                    successMsg(data);
                    layer.close(c)
                }, function (xhr) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                });
            }
            , btn2: function (index, layero) {
                //取消 的回调
                layer.close(c)
            }
            , cancel: function (index, layero) {
                //右上角关闭回调
                layer.close(c)
            }
        })
    }else if(!isNullOrEmpty(data) && '生成证号' == data.FWMC){
        var c = layer.open({
            title: '请填写请求参数',
            type: 1,
            area: ['430px','180px'],
            btn: ['确定', '取消'],
            content: '<div id="bdc-popup-radio">\n' +
                '    <form class="layui-form" action="">\n' +
                '        <div class="layui-form-item pf-form-item">\n' +
                '            <div class="layui-inline" style="width:80%;margin-left: 35px;margin-top: 35px;">\n' +
                '                <label class="layui-form-label" style="width:100px">工作流实例id：</label>\n' +
                '                <div class="layui-input-inline">\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </div>\n' +
                '    </form>\n' +
                '</div>',
            success: function () {
                var form = layui.form;
                var text = "";
                text += "<input type=\"text\" id=\"gzlslid\" name=\"gzlslid\" autocomplete=\"off\" placeholder=\"请输入\" class=\"layui-input\">";
                $('#bdc-popup-radio').find(".layui-input-inline").html(text);
                form.render();
            },
            yes: function (index, layero) {
                var gzlslid = $("input[name='gzlslid']").val();
                if (!isNotBlank(gzlslid)) {
                    warnMsg(" 请填写请求参数！");
                    return false;
                }
                addModel();
                getReturnData("/rest/v1.0/sdbcfw/sczh?gzlslid=" + gzlslid, {}, "GET", function (data) {
                    removeModal();
                    successMsg(data);
                    layer.close(c)
                }, function (xhr) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                });
            }
            , btn2: function (index, layero) {
                //取消 的回调
                layer.close(c)
            }
            , cancel: function (index, layero) {
                //右上角关闭回调
                layer.close(c)
            }
        })
    }else if(!isNullOrEmpty(data) && '生成电子证照（项目id）' == data.FWMC){
        var c = layer.open({
            title: '请填写请求参数',
            type: 1,
            area: ['430px','180px'],
            btn: ['确定', '取消'],
            content: '<div id="bdc-popup-radio">\n' +
                '    <form class="layui-form" action="">\n' +
                '        <div class="layui-form-item pf-form-item">\n' +
                '            <div class="layui-inline" style="width:80%;margin-left: 35px;margin-top: 35px;">\n' +
                '                <label class="layui-form-label" style="width:80px">项目id：</label>\n' +
                '                <div class="layui-input-inline">\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </div>\n' +
                '    </form>\n' +
                '</div>',
            success: function () {
                var form = layui.form;
                var text = "";
                text += "<input type=\"text\" id=\"xmid\" name=\"xmid\" autocomplete=\"off\" placeholder=\"请输入\" class=\"layui-input\">";
                $('#bdc-popup-radio').find(".layui-input-inline").html(text);
                form.render();
            },
            yes: function (index, layero) {
                var xmid = $("input[name='xmid']").val();
                var xmids = xmid.split(',')
                alert(xmids);
                if (xmids.length == 0) {
                    warnMsg(" 请填写请求参数！");
                    return false;
                }
                addModel();
                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/dzzzcz/createDzzz",
                    type: "POST",
                    dataType: 'json',
                    contentType: "application/json;charset=UTF-8",
                    data: JSON.stringify(xmids),
                    success: function (data) {
                        if (data == null || data == undefined || data.length == 0 || data == 0) {
                            layer.alert("创建电子证照失败！", {title: '提示'});
                            return;
                        } else if (1 === data) {
                            successMsg("创建成功！");
                            // 刷新表格数据
                            search();
                        } else if (2 === data) {
                            warnMsg("创建电子证照部分失败！请检查！");
                        } else if (3 === data) {
                            failMsg("电子证照创建全部失败！")
                        }
                        removeModal();
                        layer.close(c)
                    },
                    error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr);
                        // failMsg("系统验证发生异常，请重试或联系管理员！");
                        removeModal();
                    }
                })
            }
            , btn2: function (index, layero) {
                //取消 的回调
                layer.close(c)
            }
            , cancel: function (index, layero) {
                //右上角关闭回调
                layer.close(c)
            }
        })

    }else if(!isNullOrEmpty(data) && '生成电子签章' == data.FWMC){
        var c = layer.open({
            title: '请填写请求参数',
            type: 1,
            area: ['430px','180px'],
            btn: ['确定', '取消'],
            content: '<div id="bdc-popup-radio">\n' +
                '    <form class="layui-form" action="">\n' +
                '        <div class="layui-form-item pf-form-item">\n' +
                '            <div class="layui-inline" style="width:80%;margin-left: 35px;margin-top: 35px;">\n' +
                '                <label class="layui-form-label" style="width:100px">工作流实例id：</label>\n' +
                '                <div class="layui-input-inline">\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </div>\n' +
                '    </form>\n' +
                '</div>',
            success: function () {
                var form = layui.form;
                var text = "";
                text += "<input type=\"text\" id=\"gzlslid\" name=\"gzlslid\" autocomplete=\"off\" placeholder=\"请输入\" class=\"layui-input\">";
                $('#bdc-popup-radio').find(".layui-input-inline").html(text);
                form.render();
            },
            yes: function (index, layero) {
                var gzlslid = $("input[name='gzlslid']").val();
                if (!isNotBlank(gzlslid)) {
                    warnMsg(" 请填写请求参数！");
                    return false;
                }
                alert(gzlslid);
                addModel();
                getReturnData("/rest/v1.0/sdbcfw/scdzqz?gzlslid=" + gzlslid, {}, "GET", function (data) {
                    removeModal();
                    successMsg(data);
                    layer.close(c)
                }, function (xhr) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                });
            }
            , btn2: function (index, layero) {
                //取消 的回调
                layer.close(c)
            }
            , cancel: function (index, layero) {
                //右上角关闭回调
                layer.close(c)
            }
        })
    }else if(!isNullOrEmpty(data) && '改变权属状态，保存登簿' == data.FWMC){
        var c = layer.open({
            title: '请填写请求参数',
            type: 1,
            area: ['430px','180px'],
            btn: ['确定', '取消'],
            content: '<div id="bdc-popup-radio">\n' +
                '    <form class="layui-form" action="">\n' +
                '        <div class="layui-form-item pf-form-item">\n' +
                '            <div class="layui-inline" style="width:80%;margin-left: 35px;margin-top: 35px;">\n' +
                '                <label class="layui-form-label" style="width:100px">工作流实例id：</label>\n' +
                '                <div class="layui-input-inline">\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </div>\n' +
                '    </form>\n' +
                '</div>',
            success: function () {
                var form = layui.form;
                var text = "";
                text += "<input type=\"text\" id=\"gzlslid\" name=\"gzlslid\" autocomplete=\"off\" placeholder=\"请输入\" class=\"layui-input\">";
                $('#bdc-popup-radio').find(".layui-input-inline").html(text);
                form.render();
            },
            yes: function (index, layero) {
                var gzlslid = $("input[name='gzlslid']").val();
                if (!isNotBlank(gzlslid)) {
                    warnMsg(" 请填写请求参数！");
                    return false;
                }
                alert(gzlslid);
                addModel();
                getReturnData("/rest/v1.0/sdbcfw/gxqszt?gzlslid=" + gzlslid, {}, "GET", function (data) {
                    removeModal();
                    successMsg(data);
                    layer.close(c)
                }, function (xhr) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                });
            }
            , btn2: function (index, layero) {
                //取消 的回调
                layer.close(c)
            }
            , cancel: function (index, layero) {
                //右上角关闭回调
                layer.close(c)
            }
        })
    }else if(!isNullOrEmpty(data) && '推送单个证书电子签章' == data.FWMC){
        var c = layer.open({
            title: '请填写请求参数',
            type: 1,
            area: ['430px','180px'],
            btn: ['确定', '取消'],
            content: '<div id="bdc-popup-radio">\n' +
                '    <form class="layui-form" action="">\n' +
                '        <div class="layui-form-item pf-form-item">\n' +
                '            <div class="layui-inline" style="width:80%;margin-left: 35px;margin-top: 35px;">\n' +
                '                <label class="layui-form-label" style="width:100px">证书id：</label>\n' +
                '                <div class="layui-input-inline">\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </div>\n' +
                '    </form>\n' +
                '</div>',
            success: function () {
                var form = layui.form;
                var text = "";
                text += "<input type=\"text\" id=\"zsid\" name=\"zsid\" autocomplete=\"off\" placeholder=\"请输入\" class=\"layui-input\">";
                $('#bdc-popup-radio').find(".layui-input-inline").html(text);
                form.render();
            },
            yes: function (index, layero) {
                var zsid = $("input[name='zsid']").val();
                if (!isNotBlank(zsid)) {
                    warnMsg(" 请填写请求参数！");
                    return false;
                }
                alert(zsid);
                addModel();
                getReturnData("/rest/v1.0/sdbcfw/tsBdcZsDzqz?zsid=" + zsid, {}, "GET", function (data) {
                    removeModal();
                    successMsg(data);
                    layer.close(c)
                }, function (xhr) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                });
            }
            , btn2: function (index, layero) {
                //取消 的回调
                layer.close(c)
            }
            , cancel: function (index, layero) {
                //右上角关闭回调
                layer.close(c)
            }
        })
    }else if(!isNullOrEmpty(data) && '生成电子证照(工作流id)' == data.FWMC){
        var c = layer.open({
            title: '请填写请求参数',
            type: 1,
            area: ['430px','180px'],
            btn: ['确定', '取消'],
            content: '<div id="bdc-popup-radio">\n' +
                '    <form class="layui-form" action="">\n' +
                '        <div class="layui-form-item pf-form-item">\n' +
                '            <div class="layui-inline" style="width:80%;margin-left: 35px;margin-top: 35px;">\n' +
                '                <label class="layui-form-label" style="width:100px">工作流实例id：</label>\n' +
                '                <div class="layui-input-inline">\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </div>\n' +
                '    </form>\n' +
                '</div>',
            success: function () {
                var form = layui.form;
                var text = "";
                text += "<input type=\"text\" id=\"gzlslid\" name=\"gzlslid\" autocomplete=\"off\" placeholder=\"请输入\" class=\"layui-input\">";
                $('#bdc-popup-radio').find(".layui-input-inline").html(text);
                form.render();
            },
            yes: function (index, layero) {
                var gzlslid = $("input[name='gzlslid']").val();
                if (!isNotBlank(gzlslid)) {
                    warnMsg(" 请填写请求参数！");
                    return false;
                }
                alert(gzlslid);
                addModel();
                getReturnData("/rest/v1.0/sdbcfw/scdzzz?gzlslid=" + gzlslid, {}, "GET", function (data) {
                    removeModal();
                    successMsg(data);
                    layer.close(c)
                }, function (xhr) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                });
            }
            , btn2: function (index, layero) {
                //取消 的回调
                layer.close(c)
            }
            , cancel: function (index, layero) {
                //右上角关闭回调
                layer.close(c)
            }
        })
    }
}




