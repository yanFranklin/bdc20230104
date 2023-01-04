// 领证方式
var lzfs = {};
//分版本所需全局变量
//加载基本信息时，南通版本需要加载领证人
var edition = 'common';

//字典列表
var zdList = {
    mjdw: [],
    zjzl: []
};
//初始化获取内容
var sfdydj;
var $, form, layer, element, table, laytpl, laydate;
//初始化tab获取的内容，权利信息需要使用
var qlxx;
//页面入口
var sjclids = new Set();
//是否虚拟号
var isXndyh = false;

//第一次单击tab获取的到的权利人信息组合
var tabDefaultQlrList = [];

//判断登记原因是否变化
var sfchange = false;
var formIds = "";
var djyyforywr;
var qlrCache;
//增量初始化需要的参数
var jbxxid = "";
var processDefKey = "";
var warnLayer = "";
var tableConfig;
//判断是否首次登记，从而允许楼盘表新增
var sfscdj = false;
//保存结束后提示信息
var saveMsg = '';
//用于判断权利tab页
var className;
var sply;
// 获取processInstanceType
var processInstanceType = $.getUrlParam('processInstanceType');
//获取收件材料信息,渲染到页面
var sjclNumber = 0;
//用于存放所有的收件材料id
var sjclidLists = [];
var processInsId = getQueryString("processInsId");
var lclx = "plzh";
var formStateId = getQueryString("formStateId");
var zsmodel = getQueryString("zsmodel");
var readOnly = getQueryString("readOnly");
var zxlc = getQueryString("zxlc");
var printValue = getQueryString("print");
var taskId = getQueryString("taskId");
var tabIndex = 1;
//档案归属地
var dagsd = '';
var xmfbList={};
var flag = true;
// 切换为文本框textarea的字段name(自动换行需求)
var nameList = ["zjh","qlrmc","dsfqlrmc","lzrmc","lzrzjh"];
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    table = layui.table;
    layer = layui.layer;
    laytpl = layui.laytpl;
    form = layui.form;
    $ = layui.jquery;
    element = layui.element;
    laydate = layui.laydate;
    addModel();
    setTimeout("loadButtonArea('slympl')", 10);
    setTimeout(function () {
        try {
            //获取字典
            getReturnData("/bdczd", '', 'POST', function (data) {
                if (isNotBlank(data)) {
                    zdList = data;
                }
            }, function (err) {
                delAjaxErrorMsg(err);
            }, false);
            //初始化
            generateTabContent();
            removeModal();
        } catch (e) {
            removeModal();
            ERROR_CONFIRM("加载失败", e.message);
            return;
        }
    }, 20);

    $(function () {

        //滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-form-div');
        function defaultStyle() {
            if($cnotentTitle.length == 1){
                $('.bdc-form-div').css('padding-top','68px');
            }
            if($(window).scrollTop() > 68){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','63px');
            }else if($(window).scrollTop() <= 68){
                $cnotentTitle.css('top','10px');
                $navContainer.css('top','68px');
            }
        }

        $(window).resize(function(){
            defaultStyle();
        });
        $(window).on('scroll',function () {
            if($(this).scrollTop() > 68){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','63px');
            }else if($(this).scrollTop() <= 68){
                $cnotentTitle.css('top','10px');
                $navContainer.css('top','68px');
            }
        });
        $('.layui-input').on('focus',function () {
            if($(this).hasClass('layui-form-danger')){
                $(this).removeClass('layui-form-danger');
            }
            if($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')){
                $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
            }
        });
        $('.xm-input').on('click',function () {
            if($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')){
                $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
            }
        });

        //监听面积单位复选框选择，单选
        form.on('checkbox(mjdw)', function () {
            $("[name='mjdw']").prop("checked", "");
            $(this).prop("checked", "checked");
            form.render('checkbox');
        });
        //cz.js按钮操作相关js
        titleShowUi();

        //监听第一次单击tab
        element.on('tab(tabFilter)', function (data) {
            if ($(this).hasClass('bdc-first-click')) {
                $(this).removeClass('bdc-first-click');
                var tabXmid = $(this).data('xmid');
                var tabDjxl = $(this).data('djxl');
                var tabQllx = $(this).data('qllx');
                // 给sfdydj赋值 控制tab展示字段  ccx 2019-10-03
                sfdydj = $(this).data('dydj');
                addModel();
                setTimeout(function () {
                    $.when(loadTabQlr(tabXmid, tabDjxl, '.layui-show'), loadPlQlxx(tabXmid), loadBdcdyh(tabDjxl, tabQllx, tabXmid)).done(function () {
                        generateDsQlr(tabXmid, $('.layui-show').find(".sqr"), tabIndex);
                        tabIndex = tabIndex+1;
                        // 获取当前选择权利信息index
                        var index = $(".qlxx.layui-this").attr("data-index");
                        loadLzrxx(tabXmid, tabDjxl,"lzrxx"+index,index, tabQllx);
                        var a = setInterval(function () {
                            if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                                getStateById(readOnly, formStateId, "slymplzh", "qlxxTab");
                                clearInterval(a);
                            }
                            reloadColorSize(nameList);
                            zjkColoeSize(nameList);
                        }, 50);
                    });
                }, 0);
            }
        });


        //点击提交时不为空的全部标红
        var isSubmit = true;
        var isFirst = true;
        var verifyMsg = "必填项不能为空";
        form.verify({
            required: function (value, item) {
                if (value == '' && !isXndyh) {
                    if (isFirst && flag) {
                        $('.layui-tab-item').removeClass('layui-show');
                        $(item).parents('.layui-tab-item').addClass('layui-show');
                        var liIndex = $(item).parents('.layui-tab-item').index() + 1;
                        $('.layui-tab-title li').removeClass('layui-this');
                        $('.layui-tab-title li:nth-child(' + liIndex + ')').addClass('layui-this');
                        isFirst = false;
                    }
                    $(item).addClass('layui-form-danger');
                    if(sfyzbt) {
                        isSubmit = false;
                    }
                }
            }
            ,qlrmcrequired: function (value, item) {
                //权利人名称必填
                if (value == '') {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    verifyMsg = "权利人名称不能为空";
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
            , lxdh: function (value, item) {
                if (!validatePhone($.trim(value))) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    if (isFirst) {
                        $('.layui-tab-item').removeClass('layui-show');
                        $(item).parents('.layui-tab-item').addClass('layui-show');
                        var liIndex = $(item).parents('.layui-tab-item').index() + 1;
                        $('.layui-tab-title li').removeClass('layui-this');
                        $('.layui-tab-title li:nth-child(' + liIndex + ')').addClass('layui-this');
                        isFirst = false;
                    }
                    verifyMsg = "联系电话格式不正确";
                }
            }
        });

        form.on("submit(saveData)", function (data) {
            addModel();
            AcceptForm.verifyGz(processInsId).done(function (yzResult) {
                removeModal();
                reloadColorSize(nameList);
                zjkColoeSize(nameList);
                // 当验证数据为空时，直接进行保存。
                if (yzResult.length == 0) {
                    doSubmit();
                } else {
                    // 验证存在数据不一致时，进行忽略提示。用户点击忽略时，进行回调保存操作。
                    AcceptForm.showTsxx(yzResult, doSubmit);
                }
            }).fail(function (message) {
                console.info(message);
                showAlertDialog(message);
                removeModal();
            });
        });

        // 表单保存方法
        function doSubmit() {
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                isFirst = true;
                return false;
            } else {
                addModel();
                setTimeout(function () {
                    try {
                        saveSlym();
                    } catch (e) {
                        removeModal();
                        if (e.message) {
                            showAlertDialog(e.message);
                        } else {
                            delAjaxErrorMsg(e);
                        }
                        return;
                    }
                }, 10);
                return false;
            }
        }

        //监听 权利信息 内 附记 单击
        $('.bdc-tab-box').on('click', '.bdc-pj-popup', function () {
            // var $thisTextarea = $(this).siblings('textarea');
            // var fjContent = $thisTextarea.val();
            // var title = $(this).parents(".layui-form-item").find("label").text();
            // layer.open({
            //     title: isNotBlank(title)? title : '附记',
            //     type: 1,
            //     area: ['960px'],
            //     btn: ['保存'],
            //     content: $('#fjPopup')
            //     , yes: function (index, layero) {
            //         //提交 的回调
            //         $thisTextarea.val($('#fjPopup textarea').val());
            //         $('#fjPopup textarea').val('');
            //         layer.close(index);
            //     }
            //     , btn2: function (index, layero) {
            //         //取消 的回调
            //         $('#fjPopup textarea').val('');
            //     }
            //     , cancel: function () {
            //         //右上角关闭回调
            //         $('#fjPopup textarea').val('');
            //     }
            //     , success: function () {
            //         $('#fjPopup textarea').val(fjContent);
            //     }
            // });
        });

        //监听回车事件
        $(document).keydown(function (event) {
            if($('.bdc-spf-layer').length > 0){
                var code = event.keyCode;
                if (code == 13) {
                    $('.layui-layer-btn0').click();
                    return false;
                }
            }

        });
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
    });

    //保存档案归属地
    form.on("submit(saveBtn)",function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            isFirst = true;
            flag = true;
            return false;
        }else{
            dagsd = $("#dagsd option:selected").val();
            saveDagsd(dagsd,processInsId);
            layer.closeAll();
            return false;
        }

    })

    //监听权利tab修改证件类型
    form.on('select(zjzl)', function (data) {
        if (data.value == '1') {
            $(data.elem).parents('td').next().find('input').attr('lay-verify', 'identitynew');
        } else {
            $(data.elem).parents('td').next().find('input').attr('lay-verify', '');
        }
    });

    //监听领证人修改证件类型
    form.on('select(lzrzjhFilter)', function (data) {
        var forms = data.elem.parentNode.parentNode.parentNode;
        var attrVal = $(forms).find("input[name=lzrzjh]").attr("lay-verify");
        if (data.value == '1') {
            if (isNotBlank(attrVal)) {
                if (attrVal.indexOf("identitynew") < 0) {
                    $(forms).find("input[name=lzrzjh]").attr("lay-verify", attrVal + "|identitynew");
                }
            } else {
                $(forms).find("input[name=lzrzjh]").attr("lay-verify", "identitynew");
            }
        } else {
            //移除身份证验证
            if (isNotBlank(attrVal) && attrVal.indexOf("identitynew") > -1) {
                $(forms).find("input[name=lzrzjh]").attr("lay-verify", attrVal.replace("identitynew", ""));
            }
        }
    });

    //监听抵押方式
    form.on('select(dyfs)', function (data) {
        //抵押方式为最高额抵押时，贷款方式为商业贷款
        var bdbzzqse =$(data.elem).parents(".basic-info").find("input[name=bdbzzqse]");
        var zgzqe =$(data.elem).parents(".basic-info").find("input[name=zgzqe]");
        if (data.value === "2") {
            addRequired(zgzqe);
            removeRequired(bdbzzqse);
            //定位贷款方式字段
            var $dkfs = $("select[name =dkfs]");
            if ($dkfs.length > 0) {
                $dkfs.val("商业贷款");
                form.render("select");
                resetSelectDisabledCss();
            }
        }else {
            addRequired(bdbzzqse);
            removeRequired(zgzqe);
        }
    });

    //监听查询契税税票
    $('#queryQssp').click(function (e) {
        queryQssp();
    });

    //监听领证方式，领证方式为ems邮寄时，展示邮寄信息
    form.on('select(lzfs)', function(data){
        var id = data.elem.getAttribute("id");
        var index = id.substr(id.indexOf("lzfs")+4,id.length);
        var xmid =  $(".qlxx.layui-this").find("[name=xmid]").val();
        var djxl =  $(".qlxx.layui-this").find("[name=djxl]").val();
        var qllx = $(".qlxx.layui-this").attr("data-qllx");
        var tabXmidList = [];
        qlxx.forEach(function (v) {
            if (v.bdcXm.xmid == xmid) {
                tabXmidList = v.groupXmidList;
            }
        });
        if (data.value === "1"){
            // 生成邮寄信息
            $("#showYjxx"+index).attr("style", "display:off");
        }else{
            // 隐藏邮寄信息
            $("#showYjxx"+index).attr("style", "display:none");
        }
        var xmfb = {
            lzfs:data.value
        };
        // 更新领证方式
        saveLzfsPlzh(tabXmidList.toString(),processInsId,data.value);
        // 如果选择电子证照，清空领证人信息
        if (data.value === "4") {
            deleteLzrPl(xmid,index,qllx,xmfb);
        } else {
            var qlrList = [];
            var param = {
                xmidList: tabXmidList.toString()
            };
            // 获取sxh最小的权利人，存量数据可能没有sxh=1的数据
            getReturnData("/slym/qlr/groupQlr?gzlslid=" + processInsId + "&djxl=" + djxl + "&qlrlb=1", JSON.stringify(param), "POST", function (data) {
                // 根据sxh排序
                data.sort(function (a, b) {
                    return a.bdcQlrDO.sxh - b.bdcQlrDO.sxh;
                })
                if (data && data.length > 0) {
                    qlrList.push(data[0]);
                }
                // 先删除，后插入领证人
                if (qlrList.length > 0) {
                    delAndSaveLzr(index,xmid,xmfb,qlrList[0].bdcQlrDO);
                }
            }, function (err) {
                console.log(err);
            }, false);

        }
    });

    //重新生成权利其他状况
    $(".bdc-form-div").on('click','.resetqlqtzk', function () {
        addModel();
        var xmid =$(this).data("xmid");
        reGenerateQlqtzk(xmid,"2",$(this));
    });

    /**
     * 重新生成权利其他状况
     */
    function reGenerateQlqtzk(xmid,mode,btn){
        var $nowTab =$(btn).parents(".layui-tab-item");
        var $qlqtzk = $nowTab.find("textarea[name='bfqlqtzk']");
        if($qlqtzk.length >0) {
            $qlqtzk.val("");
            //重新加载模板数据
            queryQlqtzkFjMb(xmid, $qlqtzk, mode,false);
        }
    }

    //重新生成附记
    $(".bdc-form-div").on('click','.resetfj', function () {
        var xmid =$(this).data("xmid");
        reGenerateFj(xmid,"3",$(this));
    });

    /**
     * 重新生成附记
     */
    function reGenerateFj(xmid,mode,btn){
        var $nowTab =$(btn).parents(".layui-tab-item");
        var $fj = $nowTab.find("textarea[name='fj']");
        if($fj.length >0) {
            $fj.val("");
            //重新加载模板数据
            queryQlqtzkFjMb(xmid, $fj, mode,false);
        }
    }
});

//根据qllx，组织表头
function getQlCols(qllx) {
    var cols = [];
    //根据权利类型与数据列对应关系获取对应数据列
    if (qllx === 4 || qllx === 6 || qllx === 8) {
        /**
         * 房地产权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },
            {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {
                field: 'tdsyjssj', title: '土地使用结束期限', minWidth: 150,
                templet: function (d) {
                    if (d.tdsyjssj) {
                        return Format(format(d.tdsyjssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'fwlx', title: '房屋类型', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.fwlx, zdList.fwlx);
                }
            },
            {
                field: 'fwxz', title: '房屋性质', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.fwxz, zdList.fwxz);
                }
            },
            {field: 'zcs', title: '总层数', minWidth: 100},
            {field: 'szc', title: '所在物理层', minWidth: 100},
            {field: 'szmyc', title: '所在名义层', minWidth: 100},
            {
                field: 'fwjg', title: '房屋结构', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.fwjg, zdList.fwjg);
                }
            },
            {
                field: 'fwjgmc', title: '房屋结构名称', minWidth: 150,
            },
            {
                field: 'ghyt', title: '规划用途', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.ghyt, zdList.fwyt);
                }
            },
            {
                field: 'ytmc', title: '用途名称', minWidth: 150,
            },
            {field: 'jyjg', title: '房地产交易价格', minWidth: 180},
            {
                field: 'jgsj', title: '竣工时间', minWidth: 150
            },
            {field: 'jzmj', title: '建筑面积', minWidth: 100},
            {field: 'zyjzmj', title: '专有建筑面积', minWidth: 120},
            {field: 'ftjzmj', title: '分摊建筑面积', minWidth: 120},
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === parseInt(commonDyaq_qllx)) {
        /**
         * 抵押权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'ycqzh', title: '原产权证号', minWidth: 250},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },            {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {
                field: 'zwlxqssj', title: '债务履行起始时间', minWidth: 150,
                templet: function (d) {
                    if (d.zwlxqssj) {
                        return Format(format(d.zwlxqssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'zwlxjssj', title: '债务履行结束时间', minWidth: 150,
                templet: function (d) {
                    if (d.zwlxjssj) {
                        return Format(format(d.zwlxjssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'dyfs', title: '抵押方式', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.dyfs, zdList.dyfs);
                }
            },
            {
                field: 'zgzqe', title: '抵押金额', minWidth: 140,
                templet: function (d) {
                    if(d.dyfs == "1"){
                        return d.bdbzzqse||"";
                    }
                    if (d.dyfs == "2") {
                        return d.zgzqe || '';
                    }
                    return d.zgzqe || d.bdbzzqse||"";
                }
            },
            {field: 'zjjzwdyfw', title: '在建建筑物抵押范围', minWidth: 180},
            {field: 'zjjzwzl', title: '在建建筑物坐落', minWidth: 150},
            {field: 'fwdymj', title: '抵押房产面积', minWidth: 150},
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === 3 || qllx === 7) {
        /**
         * 建设用地使用权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },            {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {
                field: 'syqqssj', title: '使用权起始时间', minWidth: 150,
                templet: function (d) {
                    if (d.syqqssj) {
                        return Format(format(d.syqqssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'syqjssj', title: '使用权结束时间', minWidth: 150,
                templet: function (d) {
                    if (d.syqjssj) {
                        return Format(format(d.syqjssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === 19) {
        /**
         * 地役权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'ycqzh', title: '原产权证号', minWidth: 250},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },            {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}

        ];
    }
    else if (qllx === 97) {
        /**
         * 异议数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'ycqzh', title: '原产权证号', minWidth: 250},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },            {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    }
    else if (qllx === 98) {
        /**
         * 查封数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'ycqzh', title: '原产权证号', minWidth: 250},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },            {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {field: 'cfwh', title: '查封文号', minWidth: 200},
            {field: 'cfjg', title: '查封机关', minWidth: 150},
            {field: 'cfwj', title: '查封文件', minWidth: 150},
            {
                field: 'cflx', title: '查封类型', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.cflx, zdList.cflx);
                }
            },
            {
                field: 'cfqssj', title: '查封起始时间', minWidth: 150,
                templet: function (d) {
                    if (d.cfqssj) {
                        return Format(format(d.cfqssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'cfjssj', title: '查封结束时间', minWidth: 150,
                templet: function (d) {
                    if (d.cfjssj) {
                        return Format(format(d.cfjssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {field: 'cffw', title: '查封范围', minWidth: 200},
            {field: 'jfwh', title: '解封文号', minWidth: 200},
            {field: 'jfjg', title: '解封机关', minWidth: 150},
            {field: 'jfwj', title: '解封文件', minWidth: 150},
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === 96) {
        if (sfdydj) {
            /**
             * 预抵押数据列
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'ycqzh', title: '原产权证号', minWidth: 250},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
                {field: 'zl', title: '坐落', minWidth: 240},
                {field: 'dzwmj', title: '定着物面积', minWidth: 240},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {
                    field: 'qlxz', title: '权利性质', minWidth: 100,
                    templet: function (d) {
                        return changeDmToMc(d.qlxz, zdList.qlxz);
                    }
                },                {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
                {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
                {field: 'zh', title: '幢号', minWidth: 100},
                {
                    field: 'ygdjzl', title: '预告登记种类', minWidth: 200,
                    templet: function (d) {
                        return changeDmToMc(d.ygdjzl, zdList.ygdjzl);
                    }
                },
                {
                    field: 'dyfs', title: '抵押方式', minWidth: 100,
                    templet: function (d) {
                        return changeDmToMc(d.dyfs, zdList.dyfs);
                    }
                },
                {field: 'qdjg', title: '被担保主债权数额', minWidth: 200},
                {
                    field: 'zwlxqssj', title: '债务履行起始时间', minWidth: 150,
                    templet: function (d) {
                        if (d.zwlxqssj) {
                            return Format(format(d.zwlxqssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {
                    field: 'zwlxjssj', title: '债务履行结束时间', minWidth: 150,
                    templet: function (d) {
                        if (d.zwlxjssj) {
                            return Format(format(d.zwlxjssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];
        } else {
            /**
             * 预告数据列
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
                {field: 'zl', title: '坐落', minWidth: 240},
                {field: 'dzwmj', title: '定着物面积', minWidth: 240},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {
                    field: 'qlxz', title: '权利性质', minWidth: 100,
                    templet: function (d) {
                        return changeDmToMc(d.qlxz, zdList.qlxz);
                    }
                },                {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
                {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
                {field: 'zh', title: '幢号', minWidth: 100},
                {
                    field: 'ygdjzl', title: '预告登记种类', minWidth: 200,
                    templet: function (d) {
                        return changeDmToMc(d.ygdjzl, zdList.ygdjzl);
                    }
                },
                {field: 'zcs', title: '总层数', minWidth: 100},
                {field: 'szc', title: '所在物理层', minWidth: 100},
                {field: 'szmyc', title: '所在名义层', minWidth: 100},
                {field: 'jzmj', title: '建筑面积', minWidth: 100},
                {
                    field: 'fwxz', title: '房屋性质', minWidth: 150,
                    templet: function (d) {
                        return changeDmToMc(d.fwxz, zdList.fwxz);
                    }
                },
                {
                    field: 'ghyt', title: '规划用途', minWidth: 150,
                    templet: function (d) {
                        return changeDmToMc(d.ghyt, zdList.fwyt);
                    }
                },
                {
                    field: 'ytmc', title: '用途名称', minWidth: 150,
                },
                {field: 'tdsyqr', title: '土地使用权人', minWidth: 200},
                {field: 'jyje', title: '交易金额', minWidth: 200},
                {
                    field: 'tdsyqssj', title: '土地使用起始时间', minWidth: 150,
                    templet: function (d) {
                        if (d.tdsyqssj) {
                            return Format(format(d.tdsyqssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {
                    field: 'tdsyjssj', title: '土地使用结束时间', minWidth: 150,
                    templet: function (d) {
                        if (d.tdsyjssj) {
                            return Format(format(d.tdsyjssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];
        }
    } else if (qllx === 92) {
        /**
         * 居住权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'ycqzh', title: '原产权证号', minWidth: 250},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },
            {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'jztjhyq', title: '居住条件和要求', minWidth: 140},
            {
                field: 'jzqkssj', title: '居住权起始时间', minWidth: 150,
                templet: function (d) {
                    if (d.jzqkssj) {
                        return Format(format(d.jzqkssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'jzqjssj', title: '居住权结束时间', minWidth: 150,
                templet: function (d) {
                    if (d.jzqjssj) {
                        return Format(format(d.jzqjssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else {
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 130},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },
            {field: 'zdzhmj', title: '宗地宗海面积', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}

        ];
    }
    return cols;
}

//保存后，重新获取权利信息数据
function reloadPlQlxx(xmid, $nowTab) {
    var qlxxShow = {};
    getReturnData("/slym/ql/plzh", {processInsId: processInsId, zxlc: zxlc}, 'GET', function (data) {
        if (isNotBlank(data)) {
            qlxx = data;
            qlxx.forEach(function (v) {
                if (v.bdcXm.xmid == xmid) {
                    qlxxShow = v;
                }
            });
            buildQlxx(qlxxShow, $nowTab);
        }
    }, function (err) {
        console.log(err);
    }, false);
}

/**
 * ywrgroup 义务人分组对象
 * 处理义务人
 */
function dealYwr(ywrgroup) {
    var ywr = "";
    if (ywrgroup != null && ywrgroup.length > 0) {
        for (var i = 0; i < ywrgroup.length; i++) {
            var ywrObj = ywrgroup[i];
            if (ywrObj && isNotBlank(ywrObj.bdcQlrDO.qlrmc)) {
                ywr += ywrObj.bdcQlrDO.qlrmc + " ";
            }
        }

    }
    return ywr;
}



//第一次单击权利tab，获取不动产单元信息表格
function loadBdcdyh(djxl, tabQllx, tabXmid) {
    var tabXmxx = {};
    var tabQszt = "";
    var tabXmidList = [];
    qlxx.forEach(function (v) {
        if (v.bdcXm.xmid == tabXmid) {
            tabXmxx = v.bdcXm;
            tabQszt = tabXmxx.qszt;
            tabXmidList = v.groupXmidList;

        }
    });
    var isSearch = true;
    $(document).keydown(function (event) {
        if (event.keyCode == 13 && isSearch) { //绑定回车
            $(".layui-show #searchBdcdy").click();
        }
    });
    // //判断回车操作
    $('.bdc-tab-box').on('focus', '.layui-laypage-skip .layui-input', function () {
        isSearch = false;
    }).on('blur', '.layui-laypage-skip .layui-input', function () {
        isSearch = true;
    });
    //获取流程对应的规则用途作为选择项
    getReturnData("/slym/xm/queryZdFwytByGzlslid", {gzlslid: processInsId}, "GET", function (data) {
        //清空
        $('.layui-show .bdc-ghyt').empty();
        $('.layui-show .bdc-ghyt').append(new Option("请选择", ""));
        $.each(data, function (index, item) {
            //防止出现对比权籍后，规划用途字典项与登记不一致出现空的情况
            if (item !== null) {
                $('.layui-show .bdc-ghyt').append(new Option(item.mc, item.dm));// 下拉菜单里添加元素
            }
        });
    }, function (error) {
        console.log("用途下拉框获取失败")
    }, false);

    //获取权利信息表头
    var unitTableTitle = getQlCols(tabQllx);
    var url = getContextPath() + '/slym/ql/listQlByPageJson';

    // 查询参数
    var data = {
        sortParameter: "xmid ASC"
    };
    if (zxlc === "true") {
        //注销流程
        data["sfyql"] = true;
    }

    // 根据当前查询参数，获取所有的单元信息，用于数据导出
    data["gzlslid"] = processInsId;
    data["qllx"] = tabQllx;
    data["xmidList"] = tabXmidList;

    //提交表单
    $(".layui-show #searchBdcdy").click(function () {
        var bdcdyArray = $(".layui-show .bdcdyForm").serializeArray();
        bdcdyArray.forEach(function (item) {
            data[item.name] = item.value;
        });
        $.when(tableReload('xmid' + djxl, data, url)).done(function () {
            var a = setInterval(function () {
                if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                    getStateById(readOnly, formStateId, "slympl");
                    clearInterval(a);
                }
            }, 50);
        });
        return false;
    });
    //重置操作
    $(".layui-show #reset").on('click', function () {
        $('.layui-show .bdc-form-search input').val('');
        $('.layui-show .bdc-form-search select').val('');
        form.render('select');
    });

    var bdcdyhLimit = 10;
    tableConfig = {
        id: 'xmid' + djxl,
        url: url,
        method: 'post',
        contentType: 'application/json',
        where: data,
        limit: bdcdyhLimit,
        toolbar: "#toolbarBdcdyh",
        defaultToolbar: ['filter'],
        cols: [unitTableTitle]
        , parseData: function (res) {
            res.content.forEach(function (v) {
                v.yxmid = queryYxmid(v.xmid);
            });
        }
        , done: function () {
            var reverseList = ['zl'];
            reverseTableCell(reverseList, 'xmid');

            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

            if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
            } else {
                $('.bdc-form-div .layui-show .layui-table-body').height($('.bdc-form-div .layui-show .bdc-table-box').height() - 131);
                $('.bdc-form-div .layui-show .layui-table-fixed .layui-table-body').height($('.bdc-form-div .layui-show .bdc-table-box').height() - 131 - 17);

            }
            $('.bdc-table-box').on('mouseenter', 'td', function () {
                if ($(this).text() && ($(this).attr("data-field") === "zl" || $(this).attr("data-field") === "zldz")) {
                    $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>')
                }
                $('.layui-table-grid-down').on('click', function () {
                    setTimeout(function () {
                        $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                    }, 20);
                });
            });
            var a = setInterval(function () {
                if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                    getStateById(readOnly, formStateId, "slympl");
                    clearInterval(a);
                }
            }, 50);
        }
    };

    //加载表格
    loadDataTablbeByUrl('.layui-show #bdcdyTable', tableConfig);


    renderSearchInput();
    //头工具栏事件
    table.on('toolbar(bdcdyTable' + djxl + ')', function (obj) {
        var layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === "plxg") { //批量修改
            var checkStatus = table.checkStatus(obj.config.id);
            var checkData = checkStatus.data;
            var xmids = [];
            if (checkData.length === 0) {
                showConfirmDialog("提示", "未选择数据，默认修改全部数据?", "openPlxg", "'" + xmids + "'", "", "");
            } else {
                for (var i = 0; i < checkData.length; i++) {
                    var xmid = checkData[i].xmid;
                    xmids.push(xmid);
                }
                openPlxg(xmids);
            }

        } else if (layEvent === 'export') {
            exportExcel(obj.config.cols[0]);
        } else if (layEvent === "add") {
            var bdcGzYzQO = {};
            bdcGzYzQO.zhbs = "SLYM_ADDBDCDY";
            var gzyzParamMap = {};
            gzyzParamMap.gzlslid = processInsId;
            bdcGzYzQO.paramMap = gzyzParamMap;
            gzyzCommon(2, bdcGzYzQO, function (data) {
                //新增不动产单元
                var url = getContextPath() + "/view/query/selectBdcdyh.html?gzlslid=" + processInsId + "&zlcsh=true";
                var index = layer.open({
                    type: 2,
                    title: "选择不动产单元",
                    area: ['1300px', '600px'],
                    fixed: false, //不固定
                    maxmin: true, //开启最大化最小化按钮
                    content: url
                });
                layer.full(index);
            });
        } else if (layEvent === "delete") {
            var bdcGzYzQO = {};
            bdcGzYzQO.zhbs = "SLYM_SCBDCDY";
            var gzyzParamMap = {};
            gzyzParamMap.gzlslid = processInsId;
            bdcGzYzQO.paramMap = gzyzParamMap;
            gzyzCommon(2, bdcGzYzQO, function (data) {
                //删除不动产单元
                var checkStatus = table.checkStatus(obj.config.id);
                var checkData = checkStatus.data;
                var xmids = [];
                if (checkData.length === 0) {
                    showAlertDialog("未选择数据")
                } else {
                    for (var i = 0; i < checkData.length; i++) {
                        var xmid = checkData[i].xmid;
                        xmids.push(xmid);
                    }
                    getReturnData("/slxxcsh/deleteBdcdy?xmids=" + xmids+"&gzlslid="+processInsId, {}, 'DELETE', function (data) {
                        var djxl = $(".layui-show").data('djxl');
                        //删除项目后检查收费信息数据
                        getReturnData("/sf/checksfxx?xmids=" + xmids, {
                            gzlslid: processInsId,
                            djxl: djxl
                        }, 'GET', function (data) {

                        }, function (err) {
                            delAjaxErrorMsg(err);
                        });
                        ityzl_SHOW_SUCCESS_LAYER("删除成功");
                        parent.parent.location.reload();
                    }, function (err) {
                        delAjaxErrorMsg(err);
                    }, false);
                }
            });
        } else if (layEvent === "lpb") {
            if ((tabQllx === 4 || tabQllx === 6 || tabQllx === 8)) {
                var tabDjlx = $(".layui-show").data('djlx');
                if (tabDjlx === 100) {
                    //房屋首次允许新增
                    sfscdj = true;
                } else {
                    sfscdj = false;
                }
            } else {
                sfscdj = false;
            }
            //楼盘表
            lpb();

        } else if (layEvent === "pldjls") { //批量登记历史
            var tabQllx = $(".layui-show").data('qllx');
            window.open("/realestate-register-ui/view/lsgx/pldjls.html?gzlslid=" + processInsId + "&qllx=" + tabQllx);
        } else if (layEvent === "gwc") {
            openSlymGwc();
        }


        return false;


    });
    //监听单元格事件
    table.on('tool(bdcdyTable' + djxl + ')', function (obj) {
        var data = {},
            $this = $(obj.tr[0]).find('td[data-field="fj"] .layui-table-cell');
        data.qlid = obj.data.qlid;
        if (obj.event === 'editFj') {
            layer.prompt({
                formType: 2
                , title: '附记'
                , value: obj.data.fj
                , btn: ['保存', '取消']
            }, function (value, index) {
                layer.close(index);
                data.fj = value;
                //中文括号转为英文括号
                if (isNotBlank(data.fj)) {
                    data.fj = data.fj.replace('（', '(');
                    data.fj = data.fj.replace('）', ')');
                }
                //这里一般是发送修改的Ajax请求
                if (isNotBlank(className) && isNotBlank(data.qlid)) {
                    getReturnData("/slym/ql?className=" + className, JSON.stringify(data), 'PATCH', function (data) {
                        ityzl_SHOW_SUCCESS_LAYER("附记保存成功！");
                    }, function (err) {
                        delAjaxErrorMsg(err);
                    }, false);
                } else {
                    ityzl_SHOW_WARN_LAYER("未获取到权利！");
                }
                //同步更新表格和缓存对应的值
                if (isNotBlank(obj.data.fj)) {
                    obj.update({
                        fj: data.fj
                    });
                } else {
                    $this.html('<span>' + data.fj + '</span>');
                }
            });
        }
    });
    //监听滚动事件
    var scrollTop = 0,
        scrollLeft = 0;
    var tableTop = 0, tableLeft = 0;
    var $nowBtnMore = '';
    $(window).on('scroll', function () {
        scrollTop = $(this).scrollTop();
        scrollLeft = $(this).scrollLeft();

        if ($nowBtnMore != '') {
            if (tableTop + 26 + $nowBtnMore.height() < document.body.offsetHeight) {
                $nowBtnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
            } else {
                $nowBtnMore.css({top: tableTop - scrollTop - $nowBtnMore.height(), left: tableLeft - 30});
            }
        }
    });

    //点击表格中的更多
    $('.bdc-table-box').on('click', '.bdc-more-btn', function (event) {
        tableTop = $(this).offset().top;
        tableLeft = $(this).offset().left;
        event.stopPropagation();
        $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
        var $btnMore = $(this).siblings('.bdc-table-btn-more');
        if ($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight) {
            $btnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
        } else {
            $btnMore.css({top: tableTop - scrollTop - $btnMore.height(), left: tableLeft - 30});
        }
        $('.bdc-table-btn-more').hide();
        $btnMore.show();
    });
    //点击更多内的任一内容，隐藏
    $('.bdc-table-btn-more a').on('click', function () {
        $(this).parent().hide();
    });
    //点击页面任一空白位置，消失
    $('body').on('click', function () {
        $('.bdc-table-btn-more').hide();
    });
}

// 导出Excel
// 通过隐藏form提交方式，避免ajax方式不支持下载文件
function exportExcel(cols, allData) {
    var exportCol = {};
    for (var index in cols) {
        if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar && cols[index].field != 'cz') {
            exportCol[cols[index].title] = cols[index].field
        }
    }
    var tempForm = $("<form>");
    tempForm.attr("id", "exportFrom");
    tempForm.attr("style", "display:none");
    tempForm.attr("target", "export");
    tempForm.attr("method", "post");
    tempForm.attr("action", '/realestate-accept-ui/slym/ql/exportBdcdyXxByGzlslid');

    var dataInput = $("<input>");
    dataInput.attr("type", "hidden");
    dataInput.attr("name", "exportCol");
    dataInput.attr("value", encodeURIComponent(JSON.stringify(exportCol)));
    tempForm.append(dataInput);
    var dataInputGzlslid = $("<input>");
    dataInputGzlslid.attr("type", "hidden");
    dataInputGzlslid.attr("name", "gzlslid");
    dataInputGzlslid.attr("value", processInsId);
    tempForm.append(dataInputGzlslid);

    var djxl = $(".layui-this").data("djxl");
    if (isNotBlank(djxl)) {
        var djxlInput = "<input type='hidden' name='djxl' value='" + djxl + "'/>";
        tempForm.append($(djxlInput));
    }

    tempForm.on("submit", function () {
    });
    tempForm.trigger("submit");
    $("body").append(tempForm);
    tempForm.submit();
    $("exportFrom").remove();
    return false;
}

//查看原项目ID
function queryYxmid(xmid) {
    var yxmid = "";
    $.ajax({
        url: getContextPath() + "/slym/ql/queryYxmid",
        type: 'GET',
        data: {xmid: xmid},
        async: false,
        success: function (data) {
            yxmid = data;
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return yxmid;
}

function queryQllx(qllx) {
    if (isNotBlank(zdList.qllx)) {
        for (var i = 0; i < zdList.qllx.length; i++) {
            var qllxZd = zdList.qllx[i];
            if (qllx == qllxZd.DM) {
                qllx = qllxZd.MC;
                break;
            }
        }
    }
    return qllx;
}

function queryZdzhyt(zdzhyt) {
    if (isNotBlank(zdList)) {
        for (var i = 0; i < zdList.tdyt.length; i++) {
            var tdytZd = zdList.tdyt[i];
            if (zdzhyt == tdytZd.DM) {
                zdzhyt = tdytZd.MC;
                break;
            }
        }
    }
    return zdzhyt;
}

function queryDzwyt(dzwyt) {
    if (isNotBlank(zdList.fwyt)) {
        for (var i = 0; i < zdList.fwyt.length; i++) {
            var dzwytZd = zdList.fwyt[i];
            if (dzwyt == dzwytZd.DM) {
                dzwyt = dzwytZd.MC;
                break;
            }
        }
    }
    return dzwyt;
}

function queryGhyt(ghyt) {
    if (isNotBlank(zdList.fwyt)) {
        for (var i = 0; i < zdList.fwyt.length; i++) {
            var ghytZd = zdList.fwyt[i];
            if (ghyt == ghytZd.DM) {
                ghyt = ghytZd.MC;
                break;
            }
        }
    }
    return ghyt;
}



/**
 * 打印不动产单元列表
 * @returns {boolean}
 */
function printBdcdyList() {
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/bdcdy/" + processInsId + "/bdcdyList/xml";
    var modelUrl = bdcdyListModelUrl;
    print(modelUrl, dataUrl, false);

    // 禁止提交后刷新
    return false;
}

//刷新受理页面权利人列表--主要用于权利人新增，删除，修改后刷新页面上所有的权利人列表
function loadQlr() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        if (isNotBlank(tabXmid)) {
            getReturnData("/slym/qlr/checkAndchangeGyfs?lclx=plzh&xmid=" + tabXmid + "&processInsId=" + processInsId, {}, "POST", function () {
                loadTabQlr(tabXmid, tabDjxl, '.layui-tab-item:nth-child(' + i + ')');
            }, function (error) {
                delAjaxErrorMsg(error);
            }, false);
        }

    }
}

//关闭panel
function cancelEdit() {
    layer.closeAll();
}




//---------------------页面加载---------------------------------------
//加载按钮
function loadButtonArea(ymlx) {
    var printDTO = queryPrintQlxx(processInsId, zxlc);
    var json = {
        lclx: printDTO[0].xmlx,
        qlxx: printDTO,
        printBtn: slymPrintBtn,
        fkdy: zhlcfkdy,
        sjdBtn: zhclSjdBtn,
        sqsBtn: zhlcSqsBtn,
        zhajlc: zhajlc
    };
    var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    getStateById(readOnly, formStateId, ymlx);
    disabledAddFa();
    titleShowUi();

    //证书预览
    $("#createZs").click(function () {
        createZs();
    });

    $("#dagsdBtn").click(function () {
        var datas = {
            zd: zdList,
            xmfb: xmfbList
        }
        if (xmfbList == null || isNullOrEmpty(xmfbList.dagsd)){
            if (dagsd != '' && dagsd != null){
                datas.xmfb.dagsd = dagsd;
            }
        }
        if (datas.xmfb.dagsd != dagsd && (dagsd != '' || dagsd != null)){
            datas.xmfb.dagsd = dagsd;
        }
        var gsdtpl = dagsdTpl.innerHTML,view1 = document.getElementById("dagsdDiv");
        laytpl(gsdtpl).render(datas,function (html) {
            view1.innerHTML = html;
        });
        flag = false;
        getStateById(readOnly, formStateId, 'slymplzh');
        form.render();
        layer.open({
            title:"档案归属地",
            type: 1,
            // skin: 'bdc-spf-layer',
            area: ['360px','460px'],
            // btn: ['确定', '取消'],
            content:$("#bdc-dagsd"),
            // yes:function (index, layero) {
            //     dagsd = $("#dagsd option:selected").val();
            //     saveDagsd(dagsd);
            //     layer.closeAll();
            // }
        });
    });

    //同步权籍数据
    $("#synchLpbData").click(function () {
        addModel();
        setTimeout(function () {
            synchLpbDataToLc();
        }, 0);

    });
    //查看权籍附件
    $("#showLpbFj").click(function () {
        showLpbFj("");
    });

    // 查看变更记录
    $("#ckbgjl").click(function(){
        showBgjl();
    });

    //评价器
    $("#evaluate").click(function () {
        commonPj(processInsId,taskId);
    });

    //人证对比
    $("#rzdb").click(function () {
        commonRzdb("", "", processInsId);
    });

    // 获取对比结果
    $("#hqdbjg").click(function(){
        hqdbjg(processInsId);
    });

    //外联证书
    $("#glzs").click(function () {
        glzs();
    });

    // 获取委托材料
    $("#hqwtcl").click(function(){
        hqwtcl(processInsId);
    });

}

function showZhlcSjd(element) {
    $("#third-prinbtn-sjd").show();
    $("#third-prinbtn-sqs").hide();
    setThirdUiWidth($(element), $("#third-prinbtn-sjd"));
}

function showZhlcSqs(element) {
    $("#third-prinbtn-sjd").hide();
    $("#third-prinbtn-sqs").show();
    setThirdUiWidth($(element), $("#third-prinbtn-sqs"));
}

//设置第三级ului标签位置
function setThirdUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    var w = buttonElement.innerWidth();
    var scrollH = $(document).scrollTop();
    var uiWidth = 0;
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
    var top = X;
    if (scrollH > 0) {
        top = top - scrollH;
    }
    // 当右边空间不足时，向左展示
    var RightY = $('body').width() - Y - w;
    if (RightY > uiWidth) {
        uiElement.css({top: top, right: $('body').width() - Y - w - 15 - uiWidth});
    } else {
        uiElement.css({top: top, left: Y - uiWidth});
    }
    uiElement.css({"min-width": uiWidth, "width": uiWidth});
}

function titleShowUi() {
    $(".print-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }

        $("#moreBtn").hide();
        $("#query").hide();
        $("#print").show();
        setUiWidth($(this), $("#print"));
    });

    // 查询点击
    $(".query-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#moreBtn").hide();
        $("#query").show();
        setUiWidth($(this), $("#query"));
    });

    $(".secondary-printbtn-sjd").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#jyxx").hide();
        $("#moreBtn").hide();
        $("#dzzzxx").hide();
        // $("#print").hide();
        $("#third-prinbtn-sqs").hide();
        $("#third-prinbtn-sjd").show();
        setThirdUiWidth($(this), $("#third-prinbtn-sjd"));
    });

    $(".secondary-printbtn-sqs").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#jyxx").hide();
        $("#moreBtn").hide();
        $("#dzzzxx").hide();
        // $("#print").hide();
        $("#third-prinbtn-sjd").hide();
        $("#third-prinbtn-sqs").show();
        setThirdUiWidth($(this), $("#third-prinbtn-sqs"));
    });

    $(".print-btn li").click(function () {
        $("#print").hide();
    });

    $(".query-btn li").click(function () {
        $("#query").hide();
    });


    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && elem.id == 'print' && elem.id == 'query' || elem.id === 'third-prinbtn-sjd' || elem.id === 'third-prinbtn-sqs') {
                return;
            }
            elem = elem.parentNode;
        }
        $(".title-ui").hide();
    });
}

//获取权力信息打印按钮需要使用的参数
function queryPrintQlxx(gzlslid, zxlc) {
    var printDTO = {};
    getReturnData("/slym/ql/print", {gzlslid: gzlslid, zxlc: zxlc}, "GET", function (data) {
        if (data) {
            printDTO = data;
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    }, false)
    return printDTO;
}

function setUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    var w = buttonElement.innerWidth();
    uiElement.css({top: X + 32, right: $('body').width() - Y - w - 15});
    var uiWidth = 0;
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
        var countWidth = (textNumber + 2) * 13;
        if (uiWidth < countWidth) {
            uiWidth = countWidth;
        }
    }
    uiElement.css({"min-width": w, "width": uiWidth + 20});
}

//初始化tab及tab下的内容
function generateTabContent() {
    var xmxx;
    var qllx;
    var xmfb = {};
    var lzfsXmid = {};
    getReturnData("/slym/xm", {processInsId: processInsId}, 'GET', function (data) {
        if (isNotBlank(data)) {
            qllx = data.qllx;
            //面积单位为空时默认为平方米
            if (data.mjdw === null || data.mjdw === '') {
                data.mjdw = '1'
            }

            xmxx = data;
            sply = data.sply;
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    getReturnData("/slym/ql/plzh", {processInsId: processInsId, zxlc: zxlc}, 'GET', function (data) {
        if (isNotBlank(data)) {
            qlxx = data;
            xmfb = data[0].bdcXmFbDO;
            dagsd = data[0].bdcXmFbDO.dagsd;
            xmfbList = data[0].bdcXmFbDO;
        }
    }, function (err) {
        console.log(err);
    }, false);
    //判断是否是虚拟单元号
    checkXndyhPl();

    var json = {
        qllx: qllx,
        qlxx: qlxx
    };
    json['sfchangeqljbxxTab'] = sfchangeqljbxxTab;

    //渲染tab页
    var liTpl = liTableTpl.innerHTML,
        liView = $('#tableUi');
    laytpl(liTpl).render(json, function (html) {
        liView.append(html);
    });
    //渲染tab内容区
    var tpl = tabContentTpL.innerHTML,
        view = document.getElementById("tabContent");
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

    //南通除受理节点，默认加载第一个权利信息
    if (sfchangeqljbxxTab) {
        $.when(loadTabQlr(qlxx[0].bdcXm.xmid, qlxx[0].bdcXm.djxl, '.layui-show'), loadPlQlxx(qlxx[0].bdcXm.xmid), loadBdcdyh(qlxx[0].bdcXm.djxl, qlxx[0].bdcXm.qllx, qlxx[0].bdcXm.xmid)).done(function () {
            var a = setInterval(function () {
                if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                    getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                    clearInterval(a);
                }
            }, 50);
        });
    }

    //日期相关
    renderDate(form, formIds);
    //给下拉框增加title属性
    renderSelect();

    //加载基本信息
    generateJbxx(xmxx, xmfb);
    //加载收件材料
    loadSjcl();
    // 记录表单第一次加载的业务数据至ES中
    addYwxxLog();
}

//判断是否是虚拟单元号
function checkXndyhPl() {
    getReturnData("/slym/xm/checkXndyh", {gzlslid: processInsId}, "GET", function (data) {
        isXndyh = data;
    }, function (error) {
        delAjaxErrorMsg(error);

    });
}

//渲染基本信息
function generateJbxx(bdcxmxx, xmfb) {
    var json = {
        bdcxmxx: bdcxmxx,
        zd: zdList,
        xmfb: xmfb,
        commonJedw: commonJedw
    };
    var tpl = jbxxTpl.innerHTML,
        view = document.getElementById('sljbXx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    renderDate(form, formIds);
    //给下拉框添加删除图标
    renderSelectClose(form);
    getStateById(readOnly, formStateId, 'slympl');
    //监听修改字段
    if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(bdcxmxx.djxl) > -1) {
        formIds = "tabContent";
        renderChange("", form, formIds);
    }
    renderSelect();
    disabledAddFa();
}

function loadSjcl() {
    addModel();
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        removeModal();
        var json = {
            bdcSlSjclDOList: data,
            zd: zdList
        };
        if (data !== null && data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                sjclidLists.push(data[i].sjclid);
            }
        }
        sjclNumber = data.length;
        var tpl = sjclTpl.innerHTML,
            view = document.getElementById('sjclxx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
            form.render('checkbox');
        });
        form.render('select');
        getStateById(readOnly, formStateId, "slympl");
        renderSelect();
        form.render('select');
        disabledAddFa("sjclForm");
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
}

//记录流程第一次加载的业务数据
function addYwxxLog(){
    getReturnData("/rest/v1.0/bgxxdb/es",{gzlslid : processInsId},"GET",function (data) {
    },function (error) {});
}

//第一次单击权利tab，获取申请人信息
function loadTabQlr(xmid, djxl, nowTab) {
    var tabXmid = $(nowTab).attr('data-xmid');
    var tabXmidList = [];
    qlxx.forEach(function (v) {
        if (v.bdcXm.xmid == tabXmid) {
            tabXmidList = v.groupXmidList;
        }
    });
    addModel();
    var param = {
        xmidList:tabXmidList.toString()
    };

    //权利人
    $.ajax({
        url: getContextPath() + "/slym/qlr/groupQlr?gzlslid="+processInsId+"&djxl="+djxl+"&qlrlb=1",
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(param),
        async: false,
        success: function (data) {
            var qlrxx = [];
            if (data && data.length > 0) {
                $.each(data, function(index, value){
                    qlrxx.push(value.bdcQlrDO);
                });
                var tabIndex = $(nowTab).index();
                tabDefaultQlrList[tabIndex] = qlrxx;
                qlrCache = qlrxx;
            }
            removeModal();
            generateQlrxx(qlrxx, "sqrxx", djxl, nowTab);
            //重新加载抵押人查封机关信息
            reloadDyrAndCfjg(xmid, nowTab);
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

    //义务人
    $.ajax({
        url: getContextPath() + "/slym/qlr/groupywr?gzlslid="+processInsId+"&djxl="+djxl,
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(param),
        async: false,
        success: function (data) {
            removeModal();
            generateQlrxx(filterQlrZh(data), "qlrTable", djxl, nowTab);
            //加载权利信息模块部分信息
            reloadQlxxForYwr(data, nowTab);
            form.render();
            getStateById(readOnly, formStateId, 'slympl');
            //合肥联系电话加密显示
            toEncryptClass('dhjm');
            renderSelect();
            disabledAddFa("sqrForm");
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

//1、义务人为企业时：相同名称视为同一企业只保留一个 2、义务人为个人时：按名称+证件号过滤，保留新证件号的
function filterQlrZh(data) {
    if (data.length == 0) {
        return data;
    }
    var filteQlrArray = [];
    //清理相同义务人缓存数据
    var cacheMap = {};
    $.each(data, function (index, val) {
        var bdcQlrDO = val.bdcQlrDO;
        // 人员类别不是义务人时，不进行过滤
        if (bdcQlrDO.qlrlb != "2") {
            filteQlrArray.push(val);
            return true;
        }
        // 对义务人进行数据过滤
        if ("2" == bdcQlrDO.qlrlb) {
            // 义务人类型不为企业和个人时，不过滤
            if (2 != bdcQlrDO.qlrlx && 1 != bdcQlrDO.qlrlx) {
                filteQlrArray.push(val);
                return true;
            }
            // 义务人类型为企业
            if (bdcQlrDO.qlrlx == 2) {
                var isRepeat = bdcQlrDO.qlrmc in cacheMap;
                if (!isRepeat) {
                    cacheMap[bdcQlrDO.qlrmc] = val;
                }
            }
            // 义务人类型为个人
            if (bdcQlrDO.qlrlx == 1 && !compareQlrRepeat(bdcQlrDO, cacheMap)) {
                var key = bdcQlrDO.qlrmc + convertIdCard18To15(bdcQlrDO.zjh);
                cacheMap[key] = val;
            }
        }
    });
    // 将去重的人员数据缓存数据添加到过滤数组中
    $.each(cacheMap, function (key, value) {
        filteQlrArray.push(value);
    });
    return filteQlrArray;
}

// 比较义务人是否重复, 重复返回true，不重复返回false
function compareQlrRepeat(bdcQlrDO, cacheMap) {
    // 将18位转15位进行重复校验,若存在则重复，不存在则不重复。
    if (isNotBlank(bdcQlrDO.zjh) && bdcQlrDO.zjh.length == 18) {
        var mapKey15 = bdcQlrDO.qlrmc + convertIdCard18To15(bdcQlrDO.zjh);
        if (!(mapKey15 in cacheMap)) {
            return false;
        }
    } else {
        var mapKey = bdcQlrDO.qlrmc + bdcQlrDO.zjh;
        if (!(mapKey in cacheMap)) {
            return false;
        }
    }
    return true;
}

//根据传递的数据，组织申请人信息到页面
function generateQlrxx(data, id, djxl, nowTab) {
    var tabXmid = $(".layui-show").data('xmid');
    var qllx =null;
    qlxx.forEach(function (v) {
        if (v.bdcXm.xmid == tabXmid) {
            djyyforywr = v.bdcXm.djyy;
            qllx =v.bdcXm.qllx;
            if (djyyforywr != null && djyyforywr != undefined) {
                djyyforywr = djyyforywr.indexOf('继承') > -1;

            }
        }
    });
    var showJtcyBtn =false;
    if(showJtcyQllxList &&showJtcyQllxList.length>0 &&showJtcyQllxList.indexOf(qllx) >-1){
        showJtcyBtn =true;
    }
    var json = {
        bdcQlrDOList: data,
        zd: zdList,
        qlrLength: 0,
        djxl: djxl,
        djyyforywr: djyyforywr,
        showJtcyBtn:showJtcyBtn
    };
    if (id == 'qlrTable') {
        //无数据隐藏
        if (data && data.length > 0) {
            $(nowTab + " #qlrTable .bdc-table-none").remove();
        }
        $(nowTab).find('.bdc-ywr-tr').remove();
        var tabQlrLength = $(nowTab + ' #' + id).find('tbody tr').length - 1;
        json.qlrLength = tabQlrLength;
        //渲染义务人
        var tpl = ywrTpl.innerHTML,
            view = $(nowTab + ' #' + id).find('tbody');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.append(html);
        });
    } else {
        var tpl = sqrTpl.innerHTML,
            view = $(nowTab + ' #' + id);
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.html(html);
        });
    }


}

/**
 * 处理权利信息中义务人相关信息
 */
function reloadQlxxForYwr(ywrdata, nowTab) {
    //抵押人
    var dyr = $(nowTab + " #dyaq-dyr");
    //供役地权利人
    var gydqlr = $(nowTab + " #dyiq-gydqlr");
    if ((dyr != null &&dyr.length !==0) || (gydqlr != null &&gydqlr.length !==0)) {
        //权利表需要展现义务人的字段，需要拼接所有义务人
        var ywr = dealYwr(ywrdata);
        if (dyr !== undefined && dyr != null) {
            $(dyr).val(ywr);
            dyr.title = ywr;
        }
        if (gydqlr !== undefined && gydqlr != null) {
            $(gydqlr).val(ywr);
            gydqlr.title = ywr;
        }
    }
}

/**
 * 权利人，义务人发生改变后同步修改权利表权利人义务人信息
 * @param xmid
 * @param
 */
function reloadDyrAndCfjg(xmid, nowTab) {
    if (isNotBlank(xmid) && isNotBlank(nowTab)) {
        var xydqlr = $(nowTab + " #dyiq-xydqlr");
        if (xydqlr != null &&xydqlr.length !==0) {
            var bdcxm;
            getReturnData("/slym/xm/xx", {xmid: xmid}, 'GET', function (data) {
                bdcxm = data
            }, function (err) {
                console.log(err);
            }, false);

            //需役地权利人
            $(xydqlr).val(bdcxm.qlr);
            xydqlr.title = bdcxm.qlr;
        }
        var cfjg = document.getElementById('cf-cfjg');
        var jfjg = document.getElementById('cf-jfjg');
        var qllx = $($(".layui-this").find("input[name='qllx']")[0]).val();
        if (qllx === "98") {
            var bdcCfjgQO = {};
            bdcCfjgQO.gzlslid = processInsId;
            if (zxlc === "true") {
                bdcCfjgQO.isjf = true;
            } else {
                bdcCfjgQO.iscf = true;
            }
            var bdccf;

            getReturnData("/slym/ql/updateCfjgOrJfjg?xmid=" + xmid, JSON.stringify(bdcCfjgQO), 'POST', function (data) {
                bdccf = data;
                if (zxlc === "true" && jfjg != null) {
                    $(jfjg).val(bdccf.jfjg);
                    jfjg.title = bdccf.jfjg;
                } else if (cfjg != null) {
                    $(cfjg).val(bdccf.cfjg);
                    cfjg.title = bdccf.cfjg;
                }
            }, function (err) {
                removeModal();
                delAjaxErrorMsg(err);
            });
        }
    }
}


//第一次单击权利tab，获取权利信息数据
function loadPlQlxx(xmid, $nowTab) {
    var qlxxShow = {};
    qlxx.forEach(function (v) {
        if (v.bdcXm.xmid == xmid) {
            qlxxShow = v;
        }
    });
    className = qlxxShow.className;
    if(isNotBlank($nowTab)){
        buildQlxx(qlxxShow, $nowTab);
    }else {
        buildQlxx(qlxxShow, $('.layui-show'));
    }
}

//根据获取到的权利信息数据，渲染内容到页面
function buildQlxx(bdcSlQlxxym, $nowTab) {
    if (isNotBlank(bdcSlQlxxym)) {
        var qllx = bdcSlQlxxym.qllx;
        var djxldjyyList = {};
        //获取登记原因list
        if (isNotBlank(bdcSlQlxxym.bdcXm.djxl)) {
            //在权利信息最上面添加登记原因、是否分别持证、原产权证号
            getReturnData("/slym/xm/queryDjxlDjyy", {djxl: bdcSlQlxxym.bdcXm.djxl}, "GET", function (data) {
                if (isNotBlank(data)) {
                    djxldjyyList = data;
                }
            }, function (error) {
                delAjaxErrorMsg(error);
            }, false);
        }

        //申请分别持证为空默认为是
        if(bdcSlQlxxym &&bdcSlQlxxym.bdcXm &&bdcSlQlxxym.bdcXm.sqfbcz ==null){
            bdcSlQlxxym.bdcXm.sqfbcz =1;
        }
        var qlIndex = $nowTab.index();
        //抵押，预抵押，异议，查封，地役权展现在外面的权利字段
        if (qllx === parseInt(commonDyaq_qllx) || bdcSlQlxxym.dydj || qllx === 97 || qllx === 98 || qllx === 19) {
            var json = {
                bdcSlQlxxym: bdcSlQlxxym,
                zd: zdList,
                dkfs: dkfs,
                djxldjyy: djxldjyyList,
                qlIndex:qlIndex
            };
            var bdcdyfwlx = "";
            if (isNotBlank(bdcSlQlxxym.bdcQlP) && isNotBlank(bdcSlQlxxym.bdcQl.bdcdyfwlx) && bdcSlQlxxym.bdcQl.bdcdyfwlx === 1) {
                bdcdyfwlx = bdcSlQlxxym.bdcQl.bdcdyfwlx;
            }
            var qllxTpl = document.getElementById(bdcSlQlxxym.tableName + bdcdyfwlx);
            if (isNotBlank(qllxTpl)) {
                var tpl = qllxTpl.innerHTML, view = $nowTab.find('#bdcdyqlxx');
                laytpl(tpl).render(json, function (html) {
                    view.html(html);
                });
                renderDate(form, formIds);
                form.render();
                renderSelect();
                //给下拉框添加删除图标
                renderSelectClose(form);
                getStateById(readOnly, formStateId, 'slympl', 'qlxxTab');
                form.render();
                disabledAddFa('qlxxTab');
            }


            //监听修改字段
            if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(bdcSlQlxxym.bdcXm.djxl) > -1) {
                formIds = "tabContent";
                renderChange("", form, formIds);
            }
        } else if (qllx === 96 || qllx === 4 || qllx === 6 || qllx === 8) {
            //展现交易信息字段
            var json = {
                zd: zdList,
                bdcSlQlxxym: bdcSlQlxxym,
                djxldjyy: djxldjyyList,
                qlIndex:qlIndex
            };
            var jyxxTpl = document.getElementById("jyxxTpl");
            if (isNotBlank(jyxxTpl)) {
                var tpl = jyxxTpl.innerHTML,
                    view = $nowTab.find('#bdcdyqlxx');
                laytpl(tpl).render(json, function (html) {
                    view.html(html);
                });
                renderDate(form, formIds);
                renderSelect();
                //给下拉框添加删除图标
                renderSelectClose(form);
                getStateById(readOnly, formStateId, 'slympl', 'qlxxTab');
                form.render();
                disabledAddFa("jyxxTpl");

            }

            //判断页面是否存在交易信息字段，显示即查询
            if ($nowTab.find(".jyxx").length > 0 && isNotBlank(bdcSlQlxxym.bdcXm.xmid)) {
                var formFilter = $nowTab.find('.bdc-qlxx-form').attr('lay-filter');
                getReturnData("/ycsl/jyxx", {xmid: bdcSlQlxxym.bdcXm.xmid}, "GET", function (data) {
                    //表单赋值
                    form.val(formFilter, data);
                    renderDate(form, formIds);
                }, function (error) {
                    delAjaxErrorMsg(error);
                });
            }
        } else {
            //其余产权不展现权利信息详细
            $nowTab.find("#qlxxdiv").remove();
        }
        sfchange = false;
        //合肥判断登记原因是否变化
        form.on('select(djyy)', function (data) {
            console.log($(data.elem).data('djyy')); //得到select原始DOM对象
            console.log(data.value); //得到被选中的值
            console.log(data.othis); //得到美化后的DOM对象
            var ydjyy = $(data.elem).data('djyy');
            var xdjyy = data.value;
            if (ydjyy === xdjyy) {
                sfchange = false;
            } else {
                sfchange = true;
            }
        })
    }
}



//---------------------保存---------------------------------------
//受理页面-点击保存
function saveSlym() {
    $.when(saveXmxx(), saveFbPlzh(), saveSjcl(), saveQlxx(), saveQlr(), saveLzr(), saveJyxx()).done(function () {
        saveDsQlr();
        removeModal();
        if (!isNotBlank(saveMsg)) {
            ityzl_SHOW_SUCCESS_LAYER("保存成功");
            dagsd='';
        } else {
            ityzl_SHOW_WARN_LAYER(saveMsg);
        }
        saveMsg = '';
    })
}


function saveLzr() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        if (isNotBlank(tabXmid)) {
            var lzrArrayPllc = $(".layui-tab-item:nth-child(" + i + ") .lzxx").serializeArray();
            if (lzrArrayPllc.length !== 0) {
                var lzrList = [];
                var lzr = {};
                for (var m = 0; m < lzrArrayPllc.length; m++) {
                    var name = lzrArrayPllc[m].name;
                    lzr[name] = lzrArrayPllc[m].value;
                    if ((m + 1) % 7 === 0) {
                        lzrList.push(lzr);
                        lzr = {};
                    }
                }
                if (isNotBlank(lzrList)) {
                    var url = "/slym/lzr/nt/lzrxx/plzh?gzlslid=" + processInsId + "&djxl=" + tabDjxl;
                    getReturnData(url, JSON.stringify(lzrList), "POST", function (data) {
                    }, function (xhr) {
                        removeModal();
                        delAjaxErrorMsg(xhr)
                    }, false)
                }
            }
        }
    }
    //刷新领证人，防止重复新增
    loadLzr();
}

//保存基本信息中 备注、面积单位   权利信息里面含有class为xmxx的内容
function saveXmxx() {
    var bz = $("#bz").val();
    var qxdm = $("#zsgsd").val();

    var mjdw = '1';
    $("input:checkbox[name='mjdw']:checked").each(function () {
        mjdw = $(this).val();
    });
    // 转移登记一并申请默认为否
    var zydjybsq = 0;
    $("input:radio[name='zydjybsq']:checked").each(function () {
        zydjybsq = $(this).val();
    });

    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        var tabXmidList = [];
        qlxx.forEach(function (v) {
            if (v.bdcXm.xmid == tabXmid) {
                tabXmidList = v.groupXmidList;
            }
        });
        if (!isNotBlank(tabDjxl)) {
            continue;
        }
        var bdcXmData = {};
        var bdcXmArray = $('.layui-tab-item:nth-child(' + i + ') .bdcxm');
        bdcXmArray.serializeArray().forEach(function (item, index) {
            if (item.name.indexOf('sqfbcz') != -1) {
                bdcXmData['sqfbcz'] = item.value;
            } else {
                bdcXmData[item.name] = item.value;
            }
        });
        bdcXmData.bz = bz;
        if (isNotBlank(mjdw)) {
            bdcXmData.mjdw = mjdw;
        }
        if (isNotBlank(qxdm)) {
            bdcXmData.qxdm = qxdm;
        }
        //  批量流程，项目内多幢情况，权利信息增加可编辑坐落字段，同步更新项目表
        var $zl = $('.layui-tab-item:nth-child(' + i + ') #qlxxdiv').find("input[name='zl']");
        if ($zl.length > 0) {
            bdcXmData.zl = $zl.val();
        }
        bdcXmData.zydjybsq = zydjybsq;
        var bdcDjxxUpdateQO = {};
        var whereMap = {};
        whereMap.gzlslid = processInsId;
        whereMap.xmids = tabXmidList;
        bdcDjxxUpdateQO.whereMap = whereMap;
        bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmData);

        getReturnData("/slym/xm/updateBatchBdcXm", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
            if (sfchange) {
                reCreateSjcl();
            }
        }, function (err) {
            throw err;
        }, false);
    }
}

//登记原因改变重新创建收件材料
function reCreateSjcl() {
    getReturnData("/slym/sjcl/recreate", {gzlslid: processInsId}, "GET", function (data) {
        if (data !== null && data !== '' && data !== undefined) {
            loadSjcl();
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr, "重新生成收件材料失败");
    })
}
//保存档案归属地信息
function saveDagsd(data) {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        var tabXmidList = [];
        qlxx.forEach(function (v) {
            if (v.bdcXm.xmid == tabXmid) {
                tabXmidList = v.groupXmidList;
            }
        });
        var bdcXmfbData = {};
        bdcXmfbData.dagsd = data;
        if ($.isEmptyObject(tabXmidList)){
            continue;
        }
        if (!$.isEmptyObject(bdcXmfbData)) {
            var bdcDjxxUpdateQO = {};
            var whereMap = {};
            whereMap.gzlslid = processInsId;
            whereMap.xmids = tabXmidList;
            bdcDjxxUpdateQO.whereMap = whereMap;
            bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmfbData);
            getReturnData("/slym/xm/plxgxmfb", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
                ityzl_SHOW_SUCCESS_LAYER("保存成功")
                console.log("批量保存项目附表信息成功");
            }, function (err) {
                throw err;
            });
        }
    }

}
//保存项目附表
function saveFbPlzh() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        var tabXmidList = [];
        qlxx.forEach(function (v) {
            if (v.bdcXm.xmid == tabXmid) {
                tabXmidList = v.groupXmidList;
            }
        });
        if (!isNotBlank(tabDjxl)) {
            continue;
        }
        var bdcXmfbData = {};
        var bdcXmFbArray = $('.layui-tab-item:nth-child(' + i + ') .xmfb');
        bdcXmFbArray.serializeArray().forEach(function (item, index) {
            bdcXmfbData[item.name] = item.value;
        });
        console.log("===============");
        console.log("bdcXmfbData");
        console.log(bdcXmfbData);
        console.log("tabXmidList");
        console.log(tabXmidList);
        if (!$.isEmptyObject(bdcXmfbData)) {
            var bdcDjxxUpdateQO = {};
            var whereMap = {};
            whereMap.gzlslid = processInsId;
            whereMap.xmids = tabXmidList;
            bdcDjxxUpdateQO.whereMap = whereMap;
            bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmfbData);
            getReturnData("/slym/xm/plxgxmfb", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
                console.log("批量保存项目附表信息成功");
            }, function (err) {
                throw err;
            });
        }
    }
}

//权利信息保存
function saveQlxx() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabClassName = $('.layui-tab-item:nth-child(' + i + ')').data('classname');
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        if (!isNotBlank(tabClassName)) {
            continue;
        }
        var bdcDjxxUpdateQO = {};
        var whereMap = {};
        whereMap.gzlslid = processInsId;
        var tabXmidList = [];
        qlxx.forEach(function (v) {
            if (v.bdcXm.xmid == tabXmid) {
                tabXmidList = v.groupXmidList;
            }
        });
        whereMap.xmids = tabXmidList;
        if (zxlc === "true") {
            whereMap.sfyql = true;
        }
        bdcDjxxUpdateQO.whereMap = whereMap;
        bdcDjxxUpdateQO.className = tabClassName;

        var bdcQlData = {};
        var bdcQlArray = $('.layui-tab-item:nth-child(' + i + ') .qlxx');
        //需要同步项目表与权利表登记原因
        var $djyy = $('.layui-tab-item:nth-child(' + i + ')').find("select[name='djyy']");
        var tabqllx = $('.layui-tab-item:nth-child(' + i + ')').data('qllx');
        if ($djyy.length > 0) {
            var djyy = $djyy.val();
            if (zxlc !== "true" && tabqllx !== 98 && tabqllx !== 97 && tabqllx !== 94) {
                bdcQlData.djyy = djyy;
            } else if (zxlc === "true") {
                if (tabqllx === parseInt(commonDyaq_qllx) || tabqllx === 19) {
                    //注销抵押原因/注销地役原因
                    bdcQlData.zxdyyy = djyy;
                } else if (tabqllx === 97) {
                    //注销异议原因
                    bdcQlData.zxyyyy = djyy;
                }
            }
        }
        if (bdcQlArray.length !== 0 || isNotBlank(bdcQlData)) {
            bdcQlArray.serializeArray().forEach(function (item, index) {
                bdcQlData[item.name] = item.value;
            });
            if (!$.isEmptyObject(bdcQlData)) {
                bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcQlData);
                getReturnData("/slym/ql/updateBatchBdcQl", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {

                }, function (err) {
                    throw err;
                }, false);
            }
        }
    }
}

//权利人保存
function saveQlr() {
    //是否已执行保存权利人
    var saveQlr =false;
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        if (isNotBlank(tabXmid)) {
            var qlrArrayPllc = $(".layui-tab-item:nth-child(" + i + ") .qlr").serializeArray();
            if (qlrArrayPllc.length != 0) {
                saveAllQlr(qlrArrayPllc, tabDjxl, tabXmid, tabDefaultQlrList[i - 1], i);
                saveQlr=true;
            } else {
                //页面获取的权利人数据为空可能是设置了不可编辑，或者是没有数据，验证数据库中是否存在权利人
                var qlmc = $('.layui-tab-title li')[i - 1].innerText;
                var result = checkQlrsl(processInsId, tabXmid);
                if (!result) {
                    throw err = new Error(qlmc + '无权利人，请新增！');
                }
            }
        }
    }
    if(saveQlr) {
        loadQlr();
    }
}

function saveAllQlr(qlrArray, djxl, xmid, defaultQlrArr, index) {
    var qlrList = [];
    var qlr = defaultQlrArr[0];
    var qlrnum = 0;
    var gyfs = "";
    var ywrnum = 0;
    var ywrgyfs = "";
    var qlrmc = "";
    var ywrmc = "";

    var trIndex = 0;

    for (var j = 0; j < qlrArray.length; j++) {
        var name = qlrArray[j].name;
        qlr[name] = qlrArray[j].value;

        // 以xmid为每一组权利人的起始数据，作为分割依据
        if ((j + 1 < qlrArray.length && qlrArray[j + 1].name === 'xmid') || j + 1 == qlrArray.length) {
            if (qlr.qlrlb === "1") {
                qlrnum++;
                gyfs += qlr.gyfs + ",";
                // savePlzhLzr(qlr, djxl);

            }
            if (qlr.qlrlb == '2') {
                ywrnum++;
                ywrgyfs += qlr.gyfs + ",";
                ywrmc += qlr.qlrmc + ",";
            }
            //将证件号中小写字母改为大写
            toUpperClass(qlr, ["zjh", "dlrzjh", "frzjh", "lzrzjh"]);
            qlrList.push(qlr);
            qlr = {};
            trIndex++;
            qlr = defaultQlrArr[trIndex];
        }
    }

    //判断是否存在权利人(当页面的权利人数据设置disabled属性后获取不到值，改为从数据库获取)
    var result = checkQlrsl(processInsId, xmid);
    if (!result) {
        throw err = new Error('无权利人，请新增！');
    }
    //验证权利人共有方式
    if (!checkGyfs(gyfs, qlrnum, xmid)) {
        throw err = new Error('权利人共有方式不正确，请检查！');
    }

    // 处理权利比例数据，去除小数点之前多余的 0
    $.each(qlrList, function(index, value){
        value.qlbl = replaceBeforePointZero(value.qlbl);
    });

    if (!checkAddGybl(qlrList)) {
        throw err = new Error('共有比例不正确，请检查！');
    }


    //验证权利人共有方式
    if (!checkGyfs(ywrgyfs, ywrnum)) {
        alert("义务人共有方式不正确，请检查!");
    }

    var url = "/slym/qlr/list/plzh?processInsId=" + processInsId + "&xmid=" + xmid + "&djxl=" + djxl;

    if (isNotBlank(qlrList)) {
        getReturnData(url, JSON.stringify(qlrList), 'PATCH', function (data) {
        }, function (err) {
            throw err;
        }, false);
    }
}

function savePlzhLzr(bdcQlrData, djxl) {
    var lzrList = [];
    var bdclzr = {};
    if (isNotBlank(bdcQlrData.dlrmc)) {
        bdclzr.qlrid = bdcQlrData.qlrid;
        bdclzr.xmid = bdcQlrData.xmid;
        bdclzr.lzrmc = bdcQlrData.dlrmc;
        bdclzr.lzrzjzl = bdcQlrData.dlrzjzl;
        bdclzr.lzrzjh = bdcQlrData.dlrzjh;
        bdclzr.lzrdh = bdcQlrData.dlrdh;
    } else if (isNotBlank(bdcQlrData.qlrmc)) {
        bdclzr.qlrid = bdcQlrData.qlrid;
        bdclzr.xmid = bdcQlrData.xmid;
        bdclzr.lzrmc = bdcQlrData.qlrmc;
        bdclzr.lzrzjzl = bdcQlrData.zjzl;
        bdclzr.lzrzjh = bdcQlrData.zjh;
        bdclzr.lzrdh = bdcQlrData.dh;
    }
    lzrList.push(bdclzr);
    var url = "/slym/lzr/hf/lzrxx/plzh?gzlslid=" + processInsId + "&qlrid=" + bdcQlrData.qlrid + "&djxl=" + djxl + "&xmid=" + bdcQlrData.xmid;
    getReturnData(url, JSON.stringify(lzrList), 'PATCH', function (data) {

    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    })
}

//交易信息保存
function saveJyxx() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var $nowTab = $('.layui-tab-item:nth-child(' + i + ')');
        var tabDjxl = $nowTab.data('djxl');
        var tabXmid = $nowTab.data('xmid');
        if (isNotBlank(tabXmid)) {
            var tabXmidList = [];
            qlxx.forEach(function (v) {
                if (v.bdcXm.xmid == tabXmid) {
                    tabXmidList = v.groupXmidList;
                }
            });
            var bdcJyData = {};
            var bdcJyArray = $('.layui-tab-item:nth-child(' + i + ') .jyxx');
            if (bdcJyArray.length != 0) {
                bdcJyArray.serializeArray().forEach(function (item, index) {
                    bdcJyData[item.name] = item.value;
                });
                if (!$.isEmptyObject(bdcJyData)) {
                    getReturnData("/ycsl/jyxx/list?processInsId=" + processInsId + "&djxl=" + tabDjxl + "&xmids=" + tabXmidList.join(","), JSON.stringify(bdcJyData), 'PATCH', function (data) {
                        console.log("保存交易信息成功")
                    }, function (err) {
                        throw err;
                    }, false);
                }
                //刷新权利信息模块,防止交易信息重复新增
                reloadPlQlxx(tabXmid, $nowTab);
            }

        }
    }
}

//---------------------按钮操作---------------------------------------
//证书预览
function createZs(xmid, bdcdyh) {
    if (!isNotBlank(xmid)) {
        xmid = "";
    }
    if (!isNotBlank(bdcdyh)) {
        bdcdyh = "";
    }
    var url = "/realestate-register-ui/rest/v1.0/bdcZs?processInsId=" + processInsId + "&xmid=" + xmid + "&bdcdyh=" + bdcdyh + "&zsmodel=" + zsmodel + "&zsyl=true&time=" + new Date().getTime();

    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: "证书预览",
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true
    });
    layer.full(index);
}

//同步权籍数据
function synchLpbDataToLc() {
    getReturnData("/rest/v1.0/slym/synchLpbDataToLc?processInsId=" + processInsId, '', 'PATCH', function (data) {
        for (var i = 2; i <= $('.layui-tab-item').length; i++) {
            var $nowTab = $('.layui-tab-item:nth-child(' + i + ')');
            var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
            reloadPlQlxx(tabXmid, $nowTab);

        }
        ityzl_SHOW_SUCCESS_LAYER("同步数据成功");
        removeModal();


    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
}

//查看权籍附件
function showLpbFj() {
    getReturnData("/rest/v1.0/slym/lpbFjUrlByLc", {processInsId: processInsId}, "GET", function (data) {
        var index = layer.open({
            type: 2,
            title: "权籍附件",
            area: ['1150px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: data
        });
        layer.full(index);
    }, function (error) {
        delAjaxErrorMsg(error);

    });


}

// 打开变更记录页面
function showBgjl(){
    getReturnData("/slym/xm/getlclx", {gzlslid: processInsId}, "GET", function (data) {
        if (data !== null) {
            // 1:普通  2：组合  3：批量  4:批量组合
            if(data == "1"){
                window.open(getContextPath() + "/view/bgdb/bgxxdb.html?processInsId=" + processInsId +"&lclx="+ data);
            }
            if(data == "2" || data == "3" || data == "4" ){
                window.open(getContextPath() + "/view/bgdb/bgxxdbPlzh.html?processInsId=" + processInsId +"&lclx="+ data);
            }
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    });
}

//从受理页面打开购物车
function openSlymGwc() {
    var index = layer.open({
        type: 2,
        title: "购物车",
        area: ['960px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: '/realestate-accept-ui/hefei/slym/slymgwc.html?gzlslid=' + processInsId
    });
}

//删除权利人
function deleteQlr(qlrid, sxh, qlrlb, djxl, xmid) {
    var url = "/slym/qlr/plzh?processInsId=" + processInsId + "&qlrid=" + qlrid + "&sxh=" + sxh + "&djxl=" + djxl;
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
            //先获取删除的权利人的所有数据，根据名称证件号权利人类别djxl
            var qlridList;
            if (qlrlb === "1") {
                qlridList = getDelQlridList(qlrid, djxl);
            }
            getReturnData(url, {}, 'DELETE', function (data) {
                removeModal();
                loadQlr();
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
                deleteLzr(qlrid, djxl, xmid);
                //删除代理人
                deleteDlr(qlridList, djxl)
            }, function (err) {
                delAjaxErrorMsg(err)
            });
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });
}

function getDelQlridList(qlrid, qlrlb) {
    var idList = [];
    getReturnData("/slym/qlr/allXmQlrid", {
        gzlslid: processInsId,
        qlrid: qlrid,
        djxl: $(".layui-this").find('input[name="djxl"]').val()
    }, "GET", function (data) {
        if (data) {
            idList = data;
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
    return idList;
}

function deleteDlr(qlridList, djxl) {
    //单个流程-组合流程直接根据权利人id删除

    //批量根据qlrid 批量删除
    var url = "/slym/dlr/delqlr?gzlslid=" + processInsId + "&djxl=" + djxl;
    getReturnData(url, JSON.stringify(qlridList), "DELETE", function (data) {

    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}


//删除义务人列表
function deleteYwr(qlridlist, sxh, xmid, qlrid, otherXmidList, djxl) {
    var xmidList;
    if (isNotBlank(otherXmidList)) {
        xmidList = otherXmidList + ',' + xmid;
    } else {
        xmidList = xmid;
    }
    var url = "";
    var qlrList = [];
    if (isNotBlank(qlridlist)) {
        qlridlist += ',' + qlrid;
    } else {
        qlridlist = qlrid;
    }
    qlrList = qlridlist.split(",");
    var getXmid = xmidList.split(",");
    var qlrData = {};
    qlrData.xmids = getXmid;
    qlrData.qlrids = qlrList;
    url = "/slym/qlr/deletegroupywr?gzlslid=" + processInsId + "&sxh=" + sxh + "&xmid=" + xmid;

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
            getReturnData(url, JSON.stringify(qlrData), 'DELETE', function (data) {
                removeModal();
                loadQlr();
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
            }, function (err) {
                delAjaxErrorMsg(err)
            });
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });
}

function deleteLzr(qlrid, djxl, xmid) {
    var url = '/slym/lzr/hf/delete?qlrid=' + qlrid + '&gzlslid=' + processInsId + '&djxl=' + djxl;
    getReturnData(url, '', 'DELETE', function (data) {
        console.log('删除领证人成功');
        removeModal();
        // 加载领证人
        var index = $(".qlxx.layui-this").attr("data-index");
        var qllx = $(".qlxx.layui-this").attr("data-qllx");
        loadLzrxx(xmid, djxl, "lzrxx" + index,index,qllx);
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

//删除领证人
function deleteLzrPl(xmid,index,qllx,xmfb) {
    var djxl = $(".layui-this").find('input[name="djxl"]').val();
    var url = '/slym/lzr/yc/lzrdelete/pl?gzlslid='+ processInsId + '&djxl=' + djxl;
    getReturnData(url,'','DELETE',function (data) {
        console.log("批量删除领证人成功");
        loadLzrxx(xmid, djxl, "lzrxx" + index,index,qllx,xmfb);
        removeModal();
    },function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

//删除领证人,再新增
function delAndSaveLzr(index,xmid,xmfb,qlrData) {
    var djxl = $(".layui-this").find('input[name="djxl"]').val();
    var qllx = $(".qlxx.layui-this").attr("data-qllx");
    var dydj = $(".layui-this").find("input[name='dydj']");
    var url = '/slym/lzr/yc/lzrdelete/pl?gzlslid='+ processInsId + '&djxl=' + djxl;
    getReturnData(url,'','DELETE',function (data) {
        console.log("批量删除领证人成功");
        // 插入
        saveHfLzr(qlrData, "insert", "plzh",dydj,index,xmfb,djxl,qllx);
        removeModal();
    },function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

// 查看产权信息
function showCqxx(qlrmc, qlrzjh){
    if(isNullOrEmpty(qlrmc) || isNullOrEmpty(qlrzjh)){
        warnMsg("姓名或证件号信息为空，无法查看产权信息。");
        return;
    }
    var qlrxx = [{
        qlrmc : qlrmc,
        zjh: qlrzjh
    }];
    window.open("/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=qlrfc&qlrxx="+encodeURI(JSON.stringify(qlrxx)));
}


//打开批量修改页面
function openPlxg(xmids) {
    //打开前先清除缓存数据
    sessionStorage.removeItem('plxg_xmids');
    //数据过多,存入缓存
    sessionStorage.setItem('plxg_xmids', xmids);
    var tabXmid = $(".layui-show").data('xmid');
    var tabDjxl = $(".layui-show").data('djxl');
    var tabQllx = $(".layui-show").data('qllx');
    var index = layer.open({
        type: 2,
        title: "批量修改",
        area: ['1300px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: getContextPath() + "/view/slym/plxg.html?processInsId=" + processInsId + "&xmid=" + tabXmid + "&formStateId=" + formStateId + "&zxlc=" + zxlc + "&djxl=" + tabDjxl,
        cancel: function () {
            $.when(loadBdcdyh(tabDjxl, tabQllx, tabXmid)).done(function () {
                reloadPlQlxx(tabXmid, $(".layui-show"));
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                        clearInterval(a);
                    }
                }, 50);
            });
        }
    });
    layer.full(index);
}

//新增权利人展示
function addQlr(qllx, xmid, djxl) {
    var dydj = false;
    var index = $(".layui-this").find("input[name='liIndex']").val();
    var lzfs = $("#lzfs"+ index).val();
    //判断是否是抵押类登记
    getReturnData("/slym/ql/checkDydj", {xmid: xmid}, 'GET', function (data) {
        dydj = data;
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    var url = getContextPath() + "/view/slym/qlr.html?xmid=" + xmid + "&lclx=" + lclx + "&processInsId=" + processInsId + "&formStateId=" + formStateId + "&qllx=" + qllx + "&zxlc=" + zxlc + "&djxl=" + djxl +"&lzfs=" + lzfs;
    if (dydj) {
        url = url + "&dydj=true";
    }
    layer.open({
        type: 2,
        area: ['960px', '575px'],
        fixed: false, //不固定
        title: "新增申请人",
        content: url,
        btnAlign: "c",
        end: function () {
            reloadColorSize(nameList);
        }
    });
    form.render();
    disabledAddFa();
}

//权利人详情展示
function showQlr(qlrid, xmid, qlrlb, djxl) {
    sessionStorage.removeItem('qlridList');
    sessionStorage.removeItem('xmidList');
    var dydj = false;
    //判断是否是抵押类登记
    getReturnData("/slym/ql/checkDydj", {xmid: xmid}, 'GET', function (data) {
        dydj = data;
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    addModel();
    var qllx = $($("input[name='qllx']")[0]).val();
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var url = getContextPath() + "/view/slym/qlr.html?processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=" + lclx + "&xmid=" + xmid + "&formStateId=" + formStateId + "&qllx=" + qllx + "&readOnly=" + readOnly + "&zxlc=" + zxlc + "&djxl=" + djxl;
        //标题
        var title;
        if (dydj) {
            url = url + "&dydj=true";
            if (qlrlb === "1") {
                title = "抵押权人详细信息";
            } else {
                title = "抵押人详细信息";
            }

        } else {
            title = "申请人详细信息";
        }
        layer.open({
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            title: title,
            content: url,
            btnAlign: "c"
        });
        removeModal();
        form.render();
        renderDate(form);
        disabledAddFa();
    })
}

//义务人详情展示
function showYwr(qlrid, xmid, qlrlb, otherqlridList, otherXmidList, djxl) {
    if (otherqlridList != undefined) {
        if (isNotBlank(otherqlridList)) {
            sessionStorage.qlridList = otherqlridList + ',' + qlrid;
        } else {
            sessionStorage.qlridList = qlrid;
        }
        if (isNotBlank(otherXmidList)) {
            sessionStorage.xmidList = otherXmidList + ',' + xmid;
        } else {
            sessionStorage.xmidList = xmid;
        }
    } else {
        sessionStorage.removeItem('qlridList');
        sessionStorage.removeItem('xmidList');
    }
    var dydj = false;
    //判断是否是抵押类登记
    getReturnData("/slym/ql/checkDydj", {xmid: xmid}, 'GET', function (data) {
        dydj = data;
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    addModel();
    var qllx = $($("input[name='qllx']")[0]).val();
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var url = getContextPath() + "/view/slym/qlr.html?processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=" + lclx + "&xmid=" + xmid + "&formStateId=" + formStateId + "&qllx=" + qllx + "&readOnly=" + readOnly + "&zxlc=" + zxlc + "&djxl=" + djxl;
        //标题
        var title;
        if (dydj) {
            url = url + "&dydj=true";
            if (qlrlb === "1") {
                title = "抵押权人详细信息";
            } else {
                title = "抵押人详细信息";
            }

        } else {
            title = "申请人详细信息";
        }
        layer.open({
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            title: title,
            content: url,
            btnAlign: "c"
        });
        removeModal();
        form.render();
        renderDate(form);
        disabledAddFa();
    })
}

//单击表格内 单元信息  不动产单元信息详情页面
function showBdcdy(xmid) {
    var index = layer.open({
        type: 2,
        title: "不动产单元信息",
        area: ['960px', '390px'],
        fixed: false, //不固定
        content: getContextPath() + "/view/slym/bdcdy.html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&zxlc=" + zxlc + "&processInstanceType=" + processInstanceType,
        btnAlign: "c",
        success: function () {
        }
    });
    layer.full(index);
}

//单击表格内 权利信息
function showQl(xmid, qllx, bdcdyfwlx, sfyql) {
    if (xmid !== "" && xmid !== null) {
        qllx = parseInt(qllx);
        bdcdyfwlx = parseInt(bdcdyfwlx);
        var qllxym = getQlxxYm(qllx, bdcdyfwlx);
        var url;
        //展示原权利，不可编辑
        if (sfyql) {
            url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=true" + "&isCrossOri=false";
        } else {
            url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&isCrossOri=false" + "&processInstanceType=" + processInstanceType;
        }
        var index = layer.open({
            type: 2,
            area: ['1150px', '600px'],
            fixed: false, //不固定
            title: "权利信息",
            maxmin: true,
            content: url,
            btnAlign: "c",
            zIndex: 2147483647,
            success: function () {
            },
            cancel: function () {
                //页面关闭刷新单元信息列表和批量权利信息页面
                var $nowTab = $(".layui-show");
                var tabDjxl = $(".layui-show").data('djxl');
                var tabQllx = $(".layui-show").data('qllx');
                var tabXmid = $(".layui-show").data('xmid');
                $.when(loadBdcdyh(tabDjxl, tabQllx, tabXmid), reloadPlQlxx(tabXmid, $nowTab)).done(function () {
                    var a = setInterval(function () {
                        if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                            getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                            clearInterval(a);
                        }
                    }, 50);
                });

            }
        });
        layer.full(index);
    } else {
        ityzl_SHOW_INFO_LAYER("无原权利信息")
    }
}

//单击表格内 更多 原权利信息
//根据当前项目的xmid找到上一手原权利并展示
function openYqlxx(xmid) {
    $.ajax({
        url: getContextPath() + "/slym/ql/yql",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid},
        success: function (data) {
            if (data !== null) {
                var bdcXm = data.bdcXm;
                showQl(bdcXm.xmid, bdcXm.qllx, bdcXm.bdcdyfwlx, true);
            } else {
                ityzl_SHOW_INFO_LAYER("无原权利信息");
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

}

//单击表格内 更多 附属设施
function fwfsss(xmid) {
    $.ajax({
        url: getContextPath() + "/slym/fwfsss/list/xm",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid},
        success: function (data) {
            if (data.length > 0) {
                showFwfsss(xmid);
            } else {
                ityzl_SHOW_INFO_LAYER("无房屋附属设施");
            }


        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

//打开房屋附属设施页面
function showFwfsss(xmid) {
    layer.open({
        type: 2,
        title: "房屋附属设施信息",
        area: ['960px', '600px'],
        fixed: false, //不固定
        content: getContextPath() + "/view/slym/fwfsss.html?xmid=" + xmid,
        btnAlign: "c",
        success: function () {
        }
    });

}

//获取交易信息
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

// 查询契税税票
function queryQssp() {
    var htbh = $('#htbh').val();
    var dzspNo = $('#dzspNo').val();
    var slfType = $('#slfType').val();
    if (!htbh) {
        layer.confirm("请输入合同编号", {title:'提示'}, function(index){
            layer.close(index);
        });
        return;
    }
    if ('' === slfType || !slfType) {
        layer.confirm("请选择是否存量房", {title:'提示'}, function(index) {
            layer.close(index);
        });
        return;
    }
    const wsxx = {gzlslid: processInsId, htbh: htbh, dzsphm: dzspNo, zlfclfbz: slfType};
    $('#htbh').val('');
    $('#dzspNo').val('');
    $('#slfType').val('');
    form.render("select");
    $('#htbh').focus();
    getReturnData("/slym/sjcl/list/swWsxx", JSON.stringify(wsxx),"POST",function (data) {
        if (data && data.length > 0) {
            ityzl_SHOW_SUCCESS_LAYER("查询成功");
        } else {
            ityzl_SHOW_WARN_LAYER("未查询到相关信息！");
        }
    },function (error) {
        delAjaxErrorMsg(error);
    })
}

// 获取契税税票信息
function queryXsspxx() {
    // 小弹出层
    layer.open({
        title: '契税税票查询',
        type: 1,
        area: ['430px', '300px'],
        skin: 'layui-layer-lan',
        content: $('#bdc-popup-qssp')
        , cancel: function () {
            $('#htbh').val('');
            $('#dzspNo').val('');
            $('#slfType').val('');
            form.render("select");
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
    });
}

// 获取互联网缴费状态
function queryHlwJfzt(){
    $.ajax({
        url: getContextPath() + "/sf/hlw/jfzt/" + processInsId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            ityzl_SHOW_SUCCESS_LAYER("查询互联网缴费状态成功");
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

function generateYcslxx(contractNo, index,fwlx,currxmid) {
    if(!isNotBlank(currxmid)){
        currxmid =xmid;
    }
    layer.close(index);
    $.ajax({
        url: getContextPath() + "/ycsl/jyxx/queryHaFcjyxx",
        type: 'GET',
        data: {htbh: contractNo, xmid: currxmid, gzlslid: processInsId, fwlx: fwlx, lclx : lclx},
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data)) {
                //重新组织页面数据
                if(lclx =="zhlc"){
                    refreshQlxx();
                }else if(lclx == "pllc"){ // 批量流程刷新不动产单元信息
                    loadBdcdyh();
                }else{
                    loadBdcdyh($(".layui-show").data('djxl'), $(".layui-show").data('qllx'), $(".layui-show").data('xmid'));
                }
                loadQlr();
                setTimeout(function(){
                    ityzl_SHOW_SUCCESS_LAYER("查询成功");
                    removeModal();
                }, 150);
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

//楼盘表
function lpb() {
    addModel();
    var qjgldm ="";
    if(qlxx &&qlxx.length >0 &&qlxx[0].bdcXmFbDO != null){
        qjgldm =qlxx[0].bdcXmFbDO.qjgldm;
    }
    getReturnData("/slym/lpb", {processInsId: processInsId,qjgldm:qjgldm}, "GET", function (data) {
        removeModal();
        if (data && data.length > 0) {
            var url;
            if (data.length === 1) {
                url = "/building-ui/lpb/view?code=getbdcdyh&fwDcbIndex=" + data[0].fwDcbIndex + "&gzlslid=" + processInsId+"&qjgldm="+qjgldm;
            } else {
                url = "/realestate-accept-ui/view/lpb/lpbList.html?gzlslid=" + processInsId+"&qjgldm="+qjgldm;
            }
            var index = layer.open({
                type: 2,
                title: "楼盘表",
                area: ['1150px', '600px'],
                fixed: false, //不固定
                maxmin: true, //开启最大化最小化按钮
                content: url
            });
            layer.full(index);
        } else {
            showAlertDialog("未找到楼盘表");
        }

    }, function (error) {
        delAjaxErrorMsg(error);

    })
}

//单击楼盘表，勾选弹窗内的复选框，点击 确定 按钮，调用当前方法
function xzbdcdyhCallBack(data) {
    if (!sfscdj) {
        //非首次登记不允许新增
        ityzl_SHOW_WARN_LAYER("当前流程不允许选择楼盘表新增！");
        return false;

    }
    layer.closeAll();
    if (data && data.length > 0) {
        addModel();
        //获取创建所需参数
        getReturnData("/bdcdyh/queryParams", {gzlslid: processInsId}, "GET", function (paramdata) {
            processDefKey = paramdata.processDefKey;
            jbxxid = paramdata.jbxxid;
            //验证并增量初始化
            checkBdcdyh(data, paramdata.processDefKey, paramdata.jbxxid);

        }, function (error) {
            delAjaxErrorMsg(error);
        }, false);
    }
}

//规则验证
function checkBdcdyh(bdcdydata, processDefKey, jbxxid) {
    //组织规则验证参数
    var selectDataList = [];
    for (var i = 0; i < bdcdydata.length; i++) {
        var selectData = bdcdydata[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.processDefKey = processDefKey;
        selectDataList.push(bdcGzYzsjDTO);
    }
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = processDefKey + "_XZBDCDY";
    bdcGzYzQO.paramList = selectDataList;
    $.ajax({
        url: getContextPath() + '/bdcGzyz',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcGzYzQO),
        success: function (data) {
            if (data.length > 0) {
                //展示限制信息
                showXzxx(data);
                //将选择不动产单元data放入，方便后续获取
                sessionStorage.bdcdydata = JSON.stringify(bdcdydata);
            } else {
                //添加不动产单元
                addBdcdyh(bdcdydata, processDefKey, jbxxid);
            }

        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });


}

//添加不动产单元
function addBdcdyh(bdcdydata, processDefKey, jbxxid) {
    var selectDataList = [];
    for (var i = 0; i < bdcdydata.length; i++) {
        var selectData = bdcdydata[i];
        var bdcSlYwxxDTO = {};
        bdcSlYwxxDTO.bdcdyh = selectData.bdcdyh;
        bdcSlYwxxDTO.zl = selectData.zl;
        bdcSlYwxxDTO.qlr = selectData.qlr;
        bdcSlYwxxDTO.qjid = selectData.qjid;
        bdcSlYwxxDTO.dyhcxlx = "1";
        bdcSlYwxxDTO.lx = "HS";
        bdcSlYwxxDTO.sfzlcsh = 1;
        selectDataList.push(bdcSlYwxxDTO);
    }
    var bdcCshSlxmDTO = {};
    bdcCshSlxmDTO.bdcSlYwxxDTOList = selectDataList;
    bdcCshSlxmDTO.gzldyid = processDefKey;
    bdcCshSlxmDTO.jbxxid = jbxxid;
    $.ajax({
        url: getContextPath() + "/addbdcdyh",
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcCshSlxmDTO),
        success: function (data) {
            zlcshSelectedXxSingle(jbxxid, processDefKey);
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

//增量初始化
function zlcshSelectedXxSingle(jbxxid, processDefKey) {
    $.ajax({
        url: getContextPath() + '/slxxcsh/zlcsh',
        type: 'GET',
        dataType: 'json',
        data: {jbxxid: jbxxid, gzldyid: processDefKey,gzlslid:processInsId},
        success: function (data) {
            removeModal();
            var tabXmid = $(".layui-show").data('xmid');
            var tabDjxl = $(".layui-show").data('djxl');
            var tabQllx = $(".layui-show").data('qllx');
            loadBdcdyh(tabDjxl, tabQllx, tabXmid);
            loadTabQlr(tabXmid, tabDjxl, '.layui-show');
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });

}

//展现限制信息
function showXzxx(yzResult) {
    removeModal();
    if (yzResult.length > 0) {
        loadTsxx(yzResult);
        layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
            var $ = layui.jquery,
                layer = layui.layer;
            warnLayer = layer.open({
                type: 1,
                title: '提示信息',
                skin: 'bdc-tips-right',
                shade: [0],
                area: ['600px'],
                offset: 'r', //右下角弹出
                time: 5000000, //2秒后自动关闭
                anim: 2,
                content: $('#tsxx').html(),
                success: function () {
                    $('.layui-layer-ico .layui-layer-close .layui-layer-close1').on('click', function () {
                        layer.close(warnLayer);
                        generate();
                    })
                },
                cancel: function () {
                    layer.close(warnLayer);
                    generate();
                }
            });
        });
    }

}

//验证信息忽略回调函数
function removeTsxxCallBack() {
    if (sessionStorage.bdcdydata != undefined) {
        var bdcdydata = JSON.parse(sessionStorage.bdcdydata);
        addBdcdyh(bdcdydata, processDefKey, jbxxid);
    }
}

/*
 * 加载领证人信息
 */
function loadLzrxx(xmid, djxl,id,index,tabQllx,xmfb) {
    getReturnData("/slym/lzr/nt/lzrxx", {xmid: xmid}, 'GET', function (lzrdata) {
        var json = {
            index: index,
            bdclzrList: lzrdata,
            djxl: djxl,
            zd: zdList
        };
        // 初始化领证方式
        if (xmfb){
            json['lzfs'] = xmfb.lzfs;
        }else{
            qlxx.forEach(function (v) {
                if (v.bdcXm.xmid == xmid) {
                    json['lzfs'] = v.bdcXmFbDO.lzfs;
                }
            });
        }
        //当前领证方式为空时，设置领证方式默认值
        var lzfsXmid = getLzfsXmid(xmid);
        var lzfs0 = lzfsXmid[xmid];
        if (!lzfs0 || lzfs0 == 0) {
            var mrlzfs = getMrlzfs(tabQllx,xmid);
            json['lzfs'] = mrlzfs;
        }

        var tpl = lzrxxTpl.innerHTML, view = document.getElementById(id);
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        getStateById(readOnly, formStateId, 'slympl', "lzrxx");
        resetSelectDisabledCss();
    }, function (err) {
        console.log(err);
    });
}

function getLzfsXmid(xmidLz) {
    var lzfsXmid ={};
    for (var index = 0; index < qlxx.length; index++) {
        var xmfb = qlxx[index].bdcXmFbDO;
        var lzfs = xmfb.lzfs;
        var xmidArray = qlxx[index].groupXmidList;
        for (var index1 = 0; index1 < xmidArray.length; index1++) {
            var xmid = xmidArray[index1];
            lzfsXmid[xmid] = lzfs;
        }
    }
    return lzfsXmid;
}

//当前领证方式为空时，设置领证方式默认值
function getMrlzfs(qllx,xmid) {
    //如果预告权利，判断预告权利登记种类，预告等级种类是抵押权预告时，默认电子证照
    if (qllx == 96) {
        for (var index = 0; index < qlxx.length; index++) {
            var bdcYgQl = qlxx[index].bdcQl;
            if (bdcYgQl && isNotBlank(bdcYgQl.xmid) && bdcYgQl.xmid == xmid) {
                var ygdjzl = bdcYgQl.ygdjzl;
                if (isNotBlank(ygdjzl) && (ygdjzl == 3 || ygdjzl == 4)) {
                    return 4;
                }
            }

        }
    }

    var qllxStr = qllx + "";
    for (var lzfsKey in lzfsQldmMap) {
        var qldmArray = lzfsQldmMap[lzfsKey];
        for (var index = 0; index < qldmArray.length; index++) {
            if (qldmArray[index] == qllx) {
                return lzfsKey;
            }
        }
    }
}

//刷新领证人信息
function loadLzr() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        var qllx = $('.layui-tab-item:nth-child(' + i + ')').data('qllx');
        if (isNotBlank(tabXmid)) {
            loadLzrxx(tabXmid, tabDjxl, "lzrxx" + (i-1), i-1,qllx);
        }
    }
}

//领证信息操作列 删除
function deleteLzxxTr(qlrid, elem, djxl, xmid) {
    if (isNotBlank(qlrid)) {
        showConfirmDialog("提示", "是否确认删除", "deleteLzr", "'" + qlrid + "','" + djxl + "','" + xmid + "'", "", "", true);
    } else {
        var $this = $(elem);
        $this.parents('.bdc-lzxx-tr').remove();
    }
}

//加载第三权利人信息
function generateDsQlr(xmid, $sqr, index) {
    getReturnData("/slym/qlr/list/dsQlr", {xmid: xmid}, 'GET', function (data) {
        //渲染模板数据
        var json = {
            index: index,
            xmid: xmid,
            bdcDsQlrDOList: data,
            zd: zdList
        };
        var dsQlrxxTpl = dsQlrTpl.innerHTML;

        if (isNotBlank($sqr)) {
            //渲染数据
            if ($sqr.parents(".layui-tab-item").find(".dsqlr-basic").length > 0) {
                $sqr.parents(".layui-tab-item").find(".dsqlr-basic").remove();
            }
            laytpl(dsQlrxxTpl).render(json, function (html) {
                var $lzrTpl = $('.layui-show').find('.lzr-basic');
                if ($lzrTpl.length > 0) {
                    $lzrTpl.after(html);
                } else {
                    $sqr.after(html);
                }
            });
        }
    }, function (err) {
        console.log(err);
    });

}

//第三权利人详细
function showDsQlr(qlrid, xmid) {
    var url = getContextPath() + "/view/slym/dsQlr.html?xmid=" + xmid + "&processInsId=" + processInsId + "&formStateId=" + formStateId + "&qlrlb=5";
    layer.open({
        type: 2,
        area: ['960px', '360px'],
        fixed: false, //不固定
        title: "新增第三方权利人",
        content: url,
        btnAlign: "c",
        end: function () {
            reloadColorSize(nameList);
        }
    });
    form.render();
    resetSelectDisabledCss();
}

//第三权利人保存
function saveDsQlr() {
    var dsqlrList = [];
    $(".dsqlr-tr").each(function () {
        var $dsqlr = $(this);
        var dsqlrArray = $dsqlr.find(".dsQlr").serializeArray();
        if (dsqlrArray !== null && dsqlrArray.length > 0) {
            var dsqlr = {};
            dsqlrArray.forEach(function (item, index) {
                dsqlr[item.name] = item.value;
            });
            dsqlr.xmid = $dsqlr.find("[name=xmid]").val();
            dsqlr.qlrid = $dsqlr.find("[name=qlrid]").val();
            dsqlrList.push(dsqlr);
        }
    });
    var url = "/slym/qlr/list/updateDsQlr?processInsId=" + processInsId;

    if (dsqlrList != null && dsqlrList.length > 0) {
        getReturnData(url, JSON.stringify(dsqlrList), 'PATCH', function (data) {
        }, function (err) {
            throw err;
        }, false);
    }

}

//删除第三权利人
function deleteDsQlr(qlrid, sxh, qlrlb, elem) {
    //获取当前权利人对应的项目ID
    var xmid = $($(".layui-this").find("input[name='xmid']")[0]).val();
    var url = "/slym/qlr/deleteDsQlr?xmid=" + xmid + "&qlrid=" + qlrid + "&sxh=" + sxh + "&qlrlb=" + qlrlb + "&processInsId=" + processInsId;

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
            getReturnData(url, {}, 'DELETE', function (data) {
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
                $(elem).parents(".dsqlr-tr").remove();
            }, function (err) {
                delAjaxErrorMsg(err)
            });
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });
}

function reloadDsqlr() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        var index = $('.layui-tab-item:nth-child(' + i + ')').data('index');
        if (isNotBlank(tabXmid)) {
            queryAndLoad(tabXmid, $('.sqr' + index), index);
        }
    }
}


function queryAndLoad(xmid, $sqr, index) {
    getReturnData("/slym/qlr/list/dsQlr", {xmid: xmid}, 'GET', function (data) {
        //渲染模板数据
        var json = {
            index: index,
            xmid: xmid,
            bdcDsQlrDOList: data,
            zd: zdList
        };
        var dsQlrxxTpl = dsQlrTpl.innerHTML;
        if (isNotBlank($sqr)) {
            //渲染数据
            if ($sqr.parents(".layui-tab-item").find(".dsqlr-basic").length > 0) {
                $sqr.parents(".layui-tab-item").find(".dsqlr-basic").remove();
            }
            laytpl(dsQlrxxTpl).render(json, function (html) {
                var $lzrTpl = $('.lzr-basic' + index);
                if ($lzrTpl.length > 0) {
                    $lzrTpl.after(html);
                } else {
                    $sqr.after(html);
                }
                form.render();
            });
        }
        getStateById(readOnly, formStateId, "slympl");

    }, function (err) {
        console.log(err);
    });
}

function showCbfJtcy(qlrid,qlrlb){
    var url = getContextPath() + "/view/slym/cbfjtcy.html?qlrid=" + qlrid + "&formStateId=" + formStateId+"&readOnly="+readOnly+"&processInsId="+processInsId+"&qlrlb="+qlrlb;
    layer.open({
        title: '家庭成员信息',
        type: 2,
        area: ['960px', '400px'],
        content: url
        , cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
        , success: function () {

        }
    });

}

function cancel(){
    layer.closeAll();
}

function loadYjxx(){
    console.log(processInsId);
    // 选中权利页面的xmid，djxl
    var xmid = $(".layui-this").data("xmid");
    var djxl = $(".layui-this").data("djxl");
    var tabXmidList = [];
    qlxx.forEach(function (v) {
        if (v.bdcXm.xmid == xmid) {
            tabXmidList = v.groupXmidList;
        }
    });
    console.log("tabXmidList");
    console.log(tabXmidList.toString());
    console.log(xmid);
    console.log(djxl);
    var slbh = $('#sjbh').val();
    var url = getContextPath() + "/huaian/slym/yjxx.html?processInsId=" + processInsId + "&djxl=" + djxl + "&slbh=" + slbh + "&xmid=" +xmid + "&xmidList=" + tabXmidList.toString() +"&lclx=plzh";
    layer.open({
        type: 2,
        area: ['960px', '300px'],
        fixed: false, //不固定
        title: "邮寄信息",
        content: url,
        btnAlign: "c"
    });
}

//获取交易信息
function queryFcjyEsfHtxx(currxmid) {
    //小弹出层
    var wqHtbhIndex = layer.open({
        title: '获取二手房网签合同信息',
        type: 1,
        skin: 'bdc-spf-layer',
        area: ['430px'],
        btn: ['提交', '取消'],
        content: $('#bdc-popup-esfwqhtxx')
        , yes: function (index, layero) {
            //提交 的回调
            var wqcontractNo = $('#wqcontractNo').val();
            $('#wqcontractNo').val('');
            layer.confirm("确认带入网签合同号为"+wqcontractNo+"的信息吗？", {title:'提示'}, function(index){
                addModel();
                getEsfWqHtxx(wqcontractNo, wqHtbhIndex,currxmid);
                layer.close(index);
            });
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

function getEsfWqHtxx(wqcontractNo, wqHtbhIndex,currxmid){
    if(!isNotBlank(currxmid)){
        currxmid =xmid;
    }
    layer.close(wqHtbhIndex);
    $.ajax({
        url: getContextPath() + "/ycsl/jyxx/queryEsfWqHtxx",
        type: 'GET',
        data: {htbh: wqcontractNo, xmid: currxmid, gzlslid: processInsId},
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data) && isNotBlank(data.msg)) {
                //重新组织页面数据
                if(lclx =="zhlc"){
                    refreshQlxx();
                }else if(lclx == "pllc"){ // 批量流程刷新不动产单元信息
                    loadBdcdyh();
                }else{
                    loadBdcdyh($(".layui-show").data('djxl'), $(".layui-show").data('qllx'), $(".layui-show").data('xmid'));
                }
                loadQlr();
                setTimeout(function(){
                    ityzl_SHOW_SUCCESS_LAYER(data.msg);
                    removeModal();
                }, 150);
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