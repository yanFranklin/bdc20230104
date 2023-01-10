/**
 * Created by Ypp on 2020/2/19.
 * 打印配置js
 */
layui.use(['jquery', 'laytpl', 'layer', 'form', 'element', 'upload'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var layer = layui.layer;
    var element = layui.element;
    var laytpl = layui.laytpl;
    var upload = layui.upload;
    var copyDypz = $.getUrlParam('copy');
    var fuZhi = false;
    // 全局变量，打印配置信息
    var dypzxx = {};

    $(function () {
        // 编辑类型（区分当前是新增操作（false或为空）还是修改操作（true））
        var edit = $.getUrlParam("edit");
        var dylx = $.getUrlParam("dylx");
        var isFirstLoad = true;
        // 修改操作下，获取数据信息
        if (!isNullOrEmpty(edit) && "true" == edit) {
            queryDyPzxx(dylx);
        } else {
            renderForm();
        }

        // tab切换事件
        element.on('tab(tabFilter)', function (data) {
            // 基本信息
            if (3 == data.index) {
                if (isFirstLoad) {
                    // 校验信息加载
                    var csJson = getJyCsJson();
                    $("#jsonCs").val(JSON.stringify(csJson, null, 4));
                    isFirstLoad = false;
                }
            }
        });


        $('.bdc-dyyl').on('mouseenter', function () {
            $('.bdc-dyyl-second').show();
        });
        $('.bdc-tab-btn').on('mouseleave', function () {
            $('.bdc-dyyl-second').hide();
        });
        $('.bdc-table-btn-more a').on('click', function () {
            $(this).parent().hide();
        });

        //双击textarea
        $('.bdc-form-div').on('dblclick', '.bdc-large-textarea', function () {
            var $this = $(this);
            var getText = $this.val();
            layer.open({
                type: 1,
                title: '信息',
                area: ['100%', '100%'],
                skin: 'bdc-large-textarea',
                content: '<textarea class="layui-textarea bdc-layer-text">' + getText + '</textarea>',
                cancel: function () {
                    $this.val($('.bdc-layer-text').val());
                }
            });
        });

        //单击复制按钮
        $('.bdc-copy-btn').on('click', function () {
            $(this).parent().prev()[0].select();
            document.execCommand('copy');
        });

        // 获取打印字段示例
        $('.dyzd-sl-btn').on('click', function () {
            var resultXml = formatXml(dyzdMb);
            $("#dyzd").val(resultXml);
        });

        // 数据校验
        $('.sjjy-btn').on('click', function () {
            jypzxx();
        });
        // fr3打印
        $('.fr3-btn').on('click', function () {
            fr3Print();
        });
        // pdf打印
        $('.pdf-btn').on('click', function () {
            pdfPrint();
        });

        //保存事件
        $("#submit-btn").click(function () {
            // 封装打印配置信息
            var dypzxx = generateDypzxx();
            // 保存前，先校验
            var jyjg = jypzxx(dypzxx);

            if (jyjg) {
                // 保存或更新打印配置信息
                saveOrupdateDypzxx(dypzxx);
            } else {
                alertMsg("校验不通过，详细请看校验结果！");
                return false;
            }
        });
        //绑定原始文件域,fr3模板上传
        upload.render({
            elem: '#fr3Upload'
            , url: BASE_URL + "/pzxx/file" //改成您自己的上传接口
            , accept: 'file' //普通文件
            , done: function (res) {
                //获取当前触发上传的元素，一般用于 elem 绑定 class 的情况，注意：此乃 layui 2.1.0 新增
                var item = this.item;
                console.log(res);
                if (res && res.downUrl) {
                    successMsg('上传成功');
                    $("#fr3path").val(res.downUrl);
                } else {
                    errorsMsg("上传失败！");
                }
            }
        });
        //pdf模板上传
        upload.render({
            elem: '#pdfUpload'
            , url: BASE_URL + "/pzxx/file" //改成您自己的上传接口
            , accept: 'file' //普通文件
            , done: function (res) {
                console.log(res);
                if (res && res.downUrl) {
                    successMsg('上传成功');
                    $("#pdfpath").val(res.downUrl);
                } else {
                    errorsMsg("上传失败！");
                }
            }
        });

        //监听 子表 打印数据来源 选择
        form.on('radio(sjlyFilter)', function (data) {
            var $parent = $(this).parents('.layui-form-item');
            //如果选择的是服务
            if (data.value == 2) {
                if ($parent.find('.bdc-fw-content').length == 0) {
                    var fwData = {"fwfs": "", "qqyy": ""};
                    var fwfs = $(data.elem).data('fwfs');
                    var qqyy = $(data.elem).data('qqyy');
                    if (fwfs != "undefined") {
                        fwData.fwfs = fwfs;
                    }
                    if (qqyy != "undefined") {
                        fwData.qqyy = qqyy;
                    }
                    var getFwTpl = fwTpl.innerHTML;
                    laytpl(getFwTpl).render(fwData, function (html) {
                        $parent.append(html);
                        form.render('select');
                    });
                    var getSjk = $parent.find('.bdc-sjk:checked').val();
                    $(data.elem).parent().find('.bdc-sql-js').data('sjk', getSjk);
                    $parent.find('.bdc-sql-content').remove();
                }
            } else {
                if ($parent.find('.bdc-sql-content').length == 0) {
                    var sjkData = {"sjk": ""};
                    var sjk = $(data.elem).data('sjk');
                    if (sjk != "undefined") {
                        sjkData.sjk = sjk;
                    }
                    var getFwTpl = sqlTpl.innerHTML;
                    laytpl(getFwTpl).render(sjkData, function (html) {
                        $parent.append(html);
                        form.render('radio');
                    });

                    var getFwfs = $parent.find('.bdc-fw-content').find('.bdc-ffws').val();
                    var getQqyy = $parent.find('.bdc-fw-content').find('.bdc-qqyy').val();
                    $(data.elem).parent().find('.bdc-sjly-js').data('fwfs', getFwfs);
                    $(data.elem).parent().find('.bdc-sjly-js').data('qqyy', getQqyy);
                    $parent.find('.bdc-fw-content').remove();
                }
            }
        });
    });

    /**
     * 提交失败提示
     * @param data
     */

    window.fail = function () {
        layer.alert("<div style='text-align: center'>未查询到打印配置数据，请重试!</div>", {title: '提示'});
    };

    /**************************************js调用方法*******************************************/
    /**
     * 组装页面的打印配置信息
     */
    function generateDypzxx() {
        //基本信息
        console.log(form.val('jbxxFrom'));
        // 主表配置
        console.log(form.val('zbpzForm'));
        //子表配置
        var $zbForm = $(".bdc-zb-form");
        var zbFormList = {bdcDysjZbPzDOList: []};
        for (var i = 0, len = $zbForm.length; i < len; i++) {
            var getForm = $($zbForm[i]).serializeArray();
            var ejList = {};
            var dyzbsjy;
            var cs;
            getForm.forEach(function (item) {
                ejList[item.name] = item.value;
                if (item.name == "dyzbsjy") {
                    dyzbsjy = item.value;
                }
                if (item.name == "cs") {
                    cs = item.value;
                }
            });
            if (dyzbsjy && cs) {
                zbFormList.bdcDysjZbPzDOList.push(ejList);
            }
        }
        console.log(zbFormList);

        // 组装打印配置页面信息
        // 获取主表基本信息
        copy(dypzxx, form.val("jbxxFrom"));

        // 获取主表打印数据信息
        copy(dypzxx, form.val("zbpzForm"));

        // 获取子表打印配置信息
        dypzxx["bdcDysjZbPzDOList"] = zbFormList.bdcDysjZbPzDOList;

        //SQL数据加密
        //1.1 主表sql加密
        if(!isNullOrEmpty(dypzxx.dysjy)){
            dypzxx.dysjy = Base64.encode(dypzxx.dysjy);
        }
        //1.2 子表sql数据加密
        dypzxx.bdcDysjZbPzDOList.forEach(function (value,index){
            if(!isNullOrEmpty(value.dyzbsjy)){
                value.dyzbsjy = Base64.encode(value.dyzbsjy);
            }
        })

        return dypzxx;
    }

    /**
     * 获取校验参数Json
     */
    function getJyCsJson() {
        var csJson = {};

        var csArr = [];
        // 主表参数
        csArr.push(form.val("zbpzForm").cs);

        // 子表信息
        var $zbForm = $(".bdc-zb-form");
        for (var i = 0, len = $zbForm.length; i < len; i++) {
            var getForm = $($zbForm[i]).serializeArray();
            getForm.forEach(function (item) {
                if ('cs' == item.name) {
                    csArr.push(item.value);
                }
            });
        }
        if (csArr && csArr.length > 0) {
            for (var i = 0, len = csArr.length; i < len; i++) {
                if (csArr[i]) {
                    var csSingleArr = csArr[i].split(',');
                    for (var j = 0, lenj = csSingleArr.length; j < lenj; j++) {
                        if (csSingleArr[j]) {
                            // 默认给每个参数设置值为0
                            csJson[csSingleArr[j]] = "0";
                        }
                    }
                }
            }
        }

        var jsonArray = [];
        jsonArray.push(csJson);
        return jsonArray;
    }

    /**
     * 保存或更新打印配置信息
     */
    function saveOrupdateDypzxx(dypzxx) {
        if (fuZhi) {
            dypzxx.fuZhi = true;
            // 校验打印类型是否重复
            var repeat = false;
            $.ajax({
                type: "GET",
                url: BASE_URL + "/pzxx/checkDylx?dylx=" + dypzxx.dylx,
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data == 1) {
                        layer.alert("打印类型重复，请重新填写！", {title: '提示'});
                        repeat = true
                    }
                }, error: function (e) {
                    console.info(e);
                    repeat = true
                }
            });
            if (repeat) {
                return;
            }
        }
        $.ajax({
            url: BASE_URL + "/pzxx",
            type: "POST",
            contentType: 'application/json',
            dataType: "json",
            data: JSON.stringify(dypzxx),
            sync: false,
            success: function (data) {
                //对获取数据进行解密
                //主表SQL数据解密
                data.dysjy = Base64.decode(data.dysjy);
                //子表SQL数据解密
                data.bdcDysjZbPzDOList.forEach(function (bdcDysjZb,index){
                    if(!isNullOrEmpty(bdcDysjZb.dyzbsjy)){
                        bdcDysjZb.dyzbsjy = Base64.decode(bdcDysjZb.dyzbsjy);
                    }
                })
                if (data && data.id) {
                    // 将返回的id主键值赋值给页面的隐藏域
                    $("#id").val(data.id);
                    saveSuccessMsg();
                    fuZhi = false;
                } else {
                    alertMsg("未成功保存，请检查！");
                }
            }
            ,
            error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
    }

    /**
     * 查询打印配置信息
     */
    function queryDyPzxx(dylx) {
        $.ajax({
            url: BASE_URL + "/pzxx/" + dylx,
            type: "GET",
            contentType: 'application/json',
            dataType: "json",
            //async: false,
            success: function (data) {
                if (data) {
                    // console.log(JSON.stringify(data));
                    if (!data.bdcDysjZbPzDOList || data.bdcDysjZbPzDOList.length <= 0) {
                        data.bdcDysjZbPzDOList = [{"ytmc": "用途说明/名称"}];
                    }else{
                        //子表SQL数据解密
                        data.bdcDysjZbPzDOList.forEach(function (bdcDysjZb,index){
                            if(!isNullOrEmpty(bdcDysjZb.dyzbsjy)){
                                bdcDysjZb.dyzbsjy = Base64.decode(bdcDysjZb.dyzbsjy);
                            }
                        })

                    }
                    //主表SQL数据解密
                    data.dysjy = Base64.decode(data.dysjy);
                    bdcDysjPzDTO = data;

                    renderForm();
                }
            },
            error: function (e) {
                //response.fail(e, '');
                delAjaxErrorMsg(e);
            }
        });
    }

    /**
     * 渲染模板
     */
    function renderForm() {
        if (copyDypz) {
            bdcDysjPzDTO.dylx = "";
            bdcDysjPzDTO.ytmc = "";
            copyDypz = false;
            fuZhi = true;
        }
        if (bdcDysjPzDTO.dyzd) {
            bdcDysjPzDTO.dyzd = formatXml(bdcDysjPzDTO.dyzd);
        }
        // 加载主页面基本信息
        form.val('jbxxFrom', bdcDysjPzDTO);
        // 加载主表信息
        renderZub(laytpl, form, bdcDysjPzDTO);
        // 加载子表信息
        creatGzSjl(laytpl, form, bdcDysjPzDTO);
    }

    /**
     *  获取校验结果
     */
    function jypzxx(dypzxx) {
        if (!dypzxx) {
            dypzxx = generateDypzxx();
        }
        var jyjg = false;
        // 封装校验信息
        var dypzVO = {};
        if (isNullOrEmpty($("#jsonCs").val())) {
            dypzVO["dycsMapList"] = getJyCsJson();
        } else {
            dypzVO["dycsMapList"] = JSON.parse($("#jsonCs").val().trim());
        }
        dypzVO["bdcDysjZbPzDOList"] = dypzxx.bdcDysjZbPzDOList;
        //delete  dypzxx.bdcDysjZbPzDOList;
        dypzVO["bdcDysjPzDO"] = dypzxx;
        $.ajax({
            url: BASE_URL + "/pzxx/jgjy",
            type: "POST",
            contentType: 'application/json',
            data: JSON.stringify(dypzVO),
            dataType: "json",
            async: false,
            success: function (data) {
                if (data && data.code == true) {
                    successMsg("校验通过");
                    $("#jyjg").val(formatXml(data.result));
                    jyjg = true;
                } else {
                    $("#jyjg").val(JSON.stringify(data));
                }
            },
            error: function (e) {
                errorsMsg("校验异常！详情请见校验结果。");
                $("#jyjg").val(e.responseText);
            }, complete: function () {
                // 关闭loading
            }
        });
        return jyjg;
    }

    // fr3 打印
    function fr3Print() {
        // 预览前先校验
        if (jypzxx()) {
            if (isNullOrEmpty(dypzxx.fr3path)) {
                warnMsg("请设置fr3模板名称！");
                return;
            }
            // fr3控件不支持本地xml字符串打印。所以先保存到后台redis，再通过url获取
            var xml = $("#jyjg").val();
            var redisKey = sendXmlToRedis(xml);
            var dataUrl = getIP() + getContextPath() + "/rest/v1.0/dysjpz/print/xml/" + redisKey;
            print(dypzxx.fr3path, dataUrl, false)
        } else {
            errorsMsg("校验不通过！");
            return false;
        }
    }

    // pdf打印预览
    function pdfPrint() {
        var params = JSON.parse($("#jsonCs").val());
        var dylx = $("#dylx").val();
        var pdfpath = $("#pdfpath").val();

        if (isNullOrEmpty(pdfpath)) {
            warnMsg("请设置PDF模板名称！");
            return;
        }

        pdfpath = appendIpPort(pdfpath);

        var data = {"dylx": dylx, "params": params, "pdfpath": pdfpath};
        var redisKey = sendXmlToRedis(JSON.stringify(data));
        var pdfUrl =  getContextPath() + '/rest/v1.0/dysjpz/pdf/' + redisKey;
        window.open( getContextPath() + '/static/lib/pdf/web/viewer.html?file=' + encodeURIComponent(pdfUrl), 'PDF');
    }
});

// 生成打印子表detailId 给使用者复制粘贴用
function generateDetailId() {
    var dyzbid = $("#dyzbid").val();
    var dyzbsx = $("#dyzbsx").val();
    $("#dyzbdtid").val(dyzbid);
    if (!isNullOrEmpty(dyzbsx)) {
        $("#dyzbdtid").val(dyzbid+dyzbsx);
    }

}
