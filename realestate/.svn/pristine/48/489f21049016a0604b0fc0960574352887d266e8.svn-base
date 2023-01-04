/**
 * 首次证明单JS代码
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
$(function () {
    layui.use(['form', 'jquery', 'laytpl', 'layer', 'response', 'laytpl'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var layer = layui.layer;
        var laytpl = layui.laytpl;

        // 申请书ID
        var sqsid = $.getUrlParam("sqsid");
        //默认权利人展示个数
        var qlrRowspan = 2;

        var zdData = getMulZdList('zjzl,cxsqcl,cxnr');

        // 渲染证件种类下拉框
        var zjlxData = zdData.zjzl;
        // 初始化权利人表格
        if (isNullOrEmpty(sqsid)) {
            var qlrList = [];
            renderQlrxx(qlrList);
        }
        // 初始化申请材料/查询内容复选框信息
        renderCheckbox();


        // 点击新增权利人
        var $contentMain = $('.content-main');
        $contentMain.on('click', '.bdc-add-qlr', function () {
            qlrRowspan++;
            $('.bdc-qlr-title').attr('rowspan', qlrRowspan);
            var getQlrTpl = qlrTpl.innerHTML;
            laytpl(getQlrTpl).render(zjlxData, function (html) {
                $('.bdc-add-tr').before(html);
                form.render('select');
            });
        });
        // 点击删除权利人
        $contentMain.on('click', '.bdc-delete-qlr', function () {
            qlrRowspan--;
            $('.bdc-qlr-title').attr('rowspan', qlrRowspan);
            $(this).parents('tr').remove();
        });


        //滚动时头部固定
        var $contentTitle = $('.bdc-content-fix:visible');
        console.log($contentTitle);
        if ($contentTitle.length != 0) {
            //console.log('aaa');
            defaultStyle();
        }

        function defaultStyle() {
            if ($(window).scrollTop() > 10) {
                $contentTitle.css('top', '0');
            } else if ($(window).scrollTop() <= 10) {
                $contentTitle.css('top', '10px');
            }
        }

        $(window).resize(function () {
            if ($contentTitle.length != 0) {
                defaultStyle();
            }
        });
        $(window).on('scroll', function () {
            if ($contentTitle.length != 0) {
                if ($(this).scrollTop() > 10) {
                    $contentTitle.css('top', '0');
                } else if ($(this).scrollTop() <= 10) {
                    $contentTitle.css('top', '10px');
                }
            }
        });


        // 展示申请书信息
        renderSqsData();

        /**
         * 正式生成证书时，后台查询证书信息展示
         */
        function renderSqsData() {
            if (isNullOrEmpty(sqsid)) {
                return;
            }
            // loading加载
            addModel();
            //获取数据
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/cxsqs/" + sqsid,
                contentType: "application/json;charset=utf-8",
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if (data) {
                        data.cxrq = Format(data.cxrq, 'yyyy年MM月dd日');
                        // 为基本信息赋值（必须先赋值再渲染复选框）
                        form.val('form', data);

                        // 渲染查询类型
                        if (data && data.cxlx) {
                            $.each($("[name='cxlx']"), function (index, item) {
                                item.checked = false;
                                if (item.value == data.cxlx) {
                                    item.checked = true;
                                }
                            });
                        }
                        // 渲染查询申请材料
                        if (data && data.sqcl) {
                            var sqclArr = data.sqcl.split(zf_yw_dh);
                            $.each($("[name='sqcl']"), function (index, item) {
                                item.checked = false;
                                if (sqclArr.indexOf(item.value) > -1) {
                                    item.checked = true;
                                }
                            });
                        }
                        // 设置查询内容
                        if (data && data.cxnr) {
                            var cxnrArr = data.cxnr.split(zf_yw_dh);
                            $.each($("[name='cxnr']"), function (index, item) {
                                item.checked = false;
                                if (cxnrArr.indexOf(item.value) > -1) {
                                    item.checked = true;
                                }
                            });
                        }
                        form.render();
                        // 渲染权利人信息
                        if (data.bdcCxqlrDOList) {
                            renderQlrxx(data.bdcCxqlrDOList);
                        }
                    }
                    // 关闭加载
                    removeModal();
                }, error: function () {
                    // 关闭加载
                    removeModal();
                }
            });
            // 禁止提交后刷新
            return false;
        }

        //监听提交
        form.on('submit(submitBtn)', function (data) {
            // loading加载
            addModel();
            var param = data.field;
            delete param.cxrq;

            var isSubmit = true;
            var verifyMsg = "";
            // 验证申请人电话格式
            if (!validatePhone(param.cxsqrdh)) {
                $("input[name='cxsqrdh']").addClass('layui-form-danger');
                verifyMsg = "联系电话格式不正确!";
                isSubmit = false;
            }


            // 权利人信息
            var qlrmcArr = [];
            var qlrzjhArr = [];
            var cxQlrList = [];
            $.each($(".qlrxx").find("input[name='qlrmc']"), function (index, $qlrmc) {
                if ($qlrmc.value && !isNullOrEmpty($qlrmc.value)) {
                    var qlrmc = $qlrmc.value;
                    var zjzl = $(".qlrxx").find("select[name='qlrzjzl']")[index].value;
                    var zjh = $(".qlrxx").find("input[name='qlrzjh']")[index].value;
                    // 身份证件格式验证
                    if (zjzl == '1' && !isNullOrEmpty(zjh)) {
                        var yzxx = verifyIdNumber(zjh);
                        if (!isEmptyObject(yzxx) && !yzxx.isSubmit) {
                            isSubmit = yzxx.isSubmit;
                            if (verifyMsg.indexOf(yzxx.verifyMsg) == -1) {
                                verifyMsg = verifyMsg + yzxx.verifyMsg;
                            }
                            $($(".qlrxx input[name='qlrzjh']")[index]).addClass('layui-form-danger');
                        }
                    }

                    qlrmcArr.push(qlrmc);
                    qlrzjhArr.push(zjh);

                    var cxQlr = {};
                    cxQlr["qlrmc"] = qlrmc;
                    cxQlr["zjzl"] = zjzl;
                    cxQlr["zjh"] = zjh;
                    cxQlrList.push(cxQlr);
                }
            });
            if (!isSubmit) {
                warnMsg(verifyMsg);
                // 关闭加载
                removeModal();
                return false;
            }

            param.qlrmc = qlrmcArr.join(zf_yw_dh);
            param.bdcCxqlrDOList = cxQlrList;
            param.qlrzjh = qlrzjhArr.join(zf_yw_dh);

            // 申请材料信息
            var sqclArr = [];
            $.each($("input[name='sqcl']"), function (index, $sqcl) {
                if ($sqcl.checked) {
                    sqclArr.push($sqcl.value);
                }
            });
            param.sqcl = sqclArr.join(zf_yw_dh);

            // 查询内容信息
            var cxnrArr = [];
            $.each($("input[name='cxnr']"), function (index, $cxnr) {
                if ($cxnr.checked) {
                    cxnrArr.push($cxnr.value);
                }
            });
            param.cxnr = cxnrArr.join(zf_yw_dh);

            // 设置session，用于主页面查询
            sessionStorage.cxsqsQlrmc = qlrmcArr.join(zf_yw_dh);
            sessionStorage.cxsqsQlrzjh = qlrzjhArr.join(zf_yw_dh);
            sessionStorage.cxsqsCxmd = param.cxmd;
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/cxsqs",
                type: "POST",
                data: JSON.stringify(param),
                contentType: 'application/json',
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data) {
                        parent.sqsid = data.sqsid;
                        $("#cxsldjbh").val(data.cxsldjbh);
                        $("#sqsid").val(data.sqsid);
                        $("#cxrq").val(Format(data.cxrq, 'yyyy年MM月dd日'));
                        saveSuccessMsg();
                    } else {
                        warnMsg("提交失败，请重试！");
                    }
                }
            });
            // 关闭加载
            removeModal();
            // 禁止提交后刷新
            return false;
        });

        /**
         * 渲染权利人信息
         * @param qlrList
         */
        function renderQlrxx(qlrList) {
            qlrRowspan = qlrList.length;
            qlrList.forEach(function (v, i) {
                v['zjlxList'] = zjlxData;
            });
            var getDefaultQlrTpl = defaultQlrTpl.innerHTML;
            laytpl(getDefaultQlrTpl).render(qlrList, function (html) {
                $('.bdc-qlr-content').html('');
                $('.bdc-qlr-thead').after(html);
                form.render('select');
            });
            if (qlrList.length == 0) {
                qlrRowspan = 2;
                zjlxData.forEach(function (v, i) {
                    $('.bdc-zjzl').append('<option value="' + v.DM + '">' + v.MC + '</option>');
                });
                form.render('select');
            }
        }

        /**
         * 渲染多选模板
         */
        function renderCheckbox() {
            // 申请材料
            var sqclData = zdData.cxsqcl;
            var getSqclTpl = sqclTpl.innerHTML;
            laytpl(getSqclTpl).render(sqclData, function (html) {
                $('.sqcl-checkbox').html(html);
                // form.render();
            });

            // 查询内容
            var cxnrData = zdData.cxnr;
            var getCxnrTpl = cxnrTpl.innerHTML;
            laytpl(getCxnrTpl).render(cxnrData, function (html) {
                $('.cxnr-checkbox').html(html);
            });

            form.render();
        }
    });
});

/**
 * 身份证读卡器设置
 * @param element
 */
function cxqlrReadIdCard(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            var pName = objTest.Name;
            var pCardNo = objTest.ID;

            var $qlrxx = $(element).parents("tr");

            $($qlrxx.find("input[name='qlrmc']")[0]).val(trimStr(pName));
            $($qlrxx.find("input[name='qlrzjh']")[0]).val(trimStr(pCardNo));
            // 设置下拉框为 身份证
            $($qlrxx.find("select[name='qlrzjzl'] option[value='1']")[0]).attr("selected", true);
            $($qlrxx.find(".layui-select-title input")[0]).val("身份证");
            $($qlrxx.find("dd[lay-value='1']")[0]).addClass("layui-this");

        } else {
            warnMsg("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}