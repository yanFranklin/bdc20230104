(function ($) {
    $.hsbg = {
        // 基本信息变更主键
        fwhsBgIndex : "",
        // 变更的逻辑幢主键
        fwhsBgDcbIndex : "",
        // 合并类型
        hbfx: "",
        // 合并原户室列表
        hbYfwhs: [],
        // 合并层号
        hbch: "",
        // 合并选择的主户室
        fwhsHbMainFwHsIndex: "",
        cfYfwhs: "",
        bgbh: "",
        hsbgIndex: "",
        _initFromSession: function () {
            var sessionObject = setSessionParamObject();
            $.hsbg.fwhsBgIndex = sessionObject.fwhsBgIndex;
            $.hsbg.fwhsBgDcbIndex = sessionObject.fwhsBgDcbIndex;
            $.hsbg.hbfx = sessionObject.hbfx;
            $.hsbg.hbYfwhs = sessionObject.hbYfwhs;
            $.hsbg.hbch = sessionObject.hbch;
            $.hsbg.fwhsHbMainFwHsIndex = sessionObject.fwhsHbMainFwHsIndex;
            $.hsbg.cfYfwhs = sessionObject.cfYfwhs;
            $.hsbg.bgbh = sessionObject.bgbh;
            // $.extend($.hsbg, setSessionParamObject());
            // delete $.hsbg["fromurl"];
            // delete $.hsbg["tabname"];
        },
        _submitBgWithBgbh : function(bglx, bgbh) {
            this.bgbh = bgbh;
            if (bglx == "户室变更") {
                this._fwhsBgView();
            }
            if (bglx == "户室灭失") {
                this._fwhsMsMain();
            }
            if (bglx == "户室拆分") {
                this._fwhsCfView();
            }
            if (bglx == "户室合并") {
                this._hshbWithSaveZhs();
            }
        },
        _fwhsMs : function(data) {
            // 先弹变更基本信息的窗
            var bglx = "户室灭失";
            this.hbYfwhs = [];
            this.hbYfwhs.push(data.fw_hs_index);
            var fwhsIndex = data.fw_hs_index;
            var zl = data.zl;
            if (!zl && fwhsIndex) {
                zl = getFwzl(fwhsIndex);
            }
            addModel();
            $.ajax({
                url: "../check/fwhs/bdcdyh",
                dataType: "json",
                traditional: true,
                data: {
                    indexList: this.hbYfwhs
                },
                success: function (data) {
                    removeModal();
                    if (data.success) {
                        // 打开灭失变更基本信息页面
                        toView(getIP()+"/bgxx/infobgxx?zl=" + zl + "&bglx=" + encodeURI(bglx),
                            $.extend($.hsbg,{tabname:"户室灭失"}));
                    } else {
                        layer.msg(data.msg)
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });

        },
        _fwhsMsMain : function () {
            // loading加载
            var loadIndex = layer.load(2, {shade: [0.1, '#fff']});
            $.ajax({
                url: "../fwhsbg/ms",
                dataType: "json",
                traditional: true,
                data: {
                    bgbh: bgbh,
                    yFwHsIndexList: this.hbYfwhs
                },
                success: function (data) {
                    layer.close(loadIndex);
                    if (data && data.success) {
                        refreshAndDelete("灭失成功",true);
                    } else {
                        layer.msg("灭失失败");
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        },
        _fwhsBg : function (data) {
            var bglx = "户室变更";
            var zl = data.zl;
            this.fwhsBgIndex = data.fw_hs_index;
            this.fwhsBgDcbIndex = data.fw_dcb_index;
            // 打开合并页面
            if (!zl && this.fwhsBgIndex) {
                zl = getFwzl(this.fwhsBgIndex);
            }
            var fwIndexList = [];
            fwIndexList.push(this.fwhsBgIndex);
            addModel();
            $.ajax({
                url: "../check/fwhs/bdcdyh",
                dataType: "json",
                traditional: true,
                data: {
                    indexList: fwIndexList
                },
                success: function (data) {
                    removeModal();
                    if (data.success) {
                        toView(getIP()+"/bgxx/infobgxx?zl=" + zl + "&bglx=" + encodeURI(bglx),
                            $.extend($.hsbg,{tabname:"户室基本信息变更"}));
                    } else {
                        layer.msg(data.msg)
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });

        },
        _fwhsBgMain : function (data) {
            var postData = data.field;
            postData.yfwHsIndex = postData.fwHsIndex;
            postData.bgbh = this.bgbh;
            // loading加载
            addModel();
            $.ajax({
                url: "../fwhsbg/hsbg",
                dataType: "json",
                data: postData,
                success: function (data) {
                    removeModal();
                    if (data && data.success) {
                        refreshAndDelete("变更成功",true);
                    } else {
                        layer.msg("变更失败");
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
            return false;
        },
        _fwhsBgView:function() {
            saveSessionParam($.hsbg);
            toView(getIP()+"/fwhsbg/initbg?fwHsIndex=" + this.fwhsBgIndex +
                "&fwDcbIndex=" + this.fwhsBgDcbIndex + "&bgbh=" + this.bgbh,
                $.extend({},$.hsbg,{fromurl:getBackUrl(true)}));
        },
        _fwhsHb : function (data) {
            // 先弹变更基本信息的窗
            this.hbYfwhs = [];
            var selectDataList = [];
            for (var i = 0; i < data.length; i++) {
                this.hbYfwhs.push(data[i].fw_hs_index);
                var bdcGzYzsjDTO = {};
                bdcGzYzsjDTO.fwhsindex = data[i].fw_hs_index;
                selectDataList.push(bdcGzYzsjDTO);
            }
            addModel();
            //采用规则验证
            var bdcGzYzQO = {};
            bdcGzYzQO.zhbs = "HSHB";
            bdcGzYzQO.paramList = selectDataList;
            $.ajax({
                url: "../check/hshb",
                dataType: "json",
                traditional: true,
                type: "GET",
                data: {
                    indexList: this.hbYfwhs
                },
                success: function (data) {
                    removeModal();
                    // 如果存在验证不通过的BDCDYH
                    if (data.length > 0) {
                        // 从已选列表中去掉验证不通过的BDCDYH
                        // 验证不通过的提示
                        yzTip(data);
                    } else {
                        // 打开合并页面
                        layer.open({
                            type: 2,
                            title: "请选择一条数据作为主户室，其他户室信息将合并到该户室",
                            area: ['960px', '300px'],
                            content: "../fwhsbg/inithb?fwHsIndexList=" + encodeURI($.hsbg.hbYfwhs) + ""
                        });
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        },
        _fwhsHbSetMainIndex: function (fwhsIndex, zl, wlcs) {
            this.fwhsHbMainFwHsIndex = fwhsIndex;
            var bglx = "户室合并";
            if (!zl || zl == 'null') {
                zl = "";
            }
            if ($.hsbg.hbfx == '3') {
                $.hsbg.hbch = wlcs;
            }
            // console.log("/bgxx/infobgxx?zl=" + encodeURIComponent(zl) + "&bglx=" + encodeURIComponent(bglx));
            var hsbhindex = layer.open({
                type: 2,
                title: "户室合并",
                content: "../bgxx/infobgxx?zl=" + encodeURIComponent(zl) + "&bglx=" + encodeURIComponent(bglx),
                area: ['960px'],
            })
            $.hsbg.hsbgIndex = hsbhindex;
            layer.full(hsbhindex);
            // toView(getIP() + "/bgxx/infobgxx?zl=" + encodeURIComponent(zl) + "&bglx=" + encodeURIComponent(bglx),
            //     $.extend({}, $.hsbg, {tabname: "户室合并"}));
        },
        _hshbWithSaveZhs : function (zhsList) {
            var config;
            // 读取合并是否需要 选择子户室的配置
            $.ajax({
                url: "../fwhsbg/gethshbconfig",
                traditional: true,
                dataType: "json",
                async: false,
                success: function (data) {
                    config = data;
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this);
                }
            });
            if (config == "1") {
                layer.open({
                    type: 2,
                    title: "户室合并子户室列表",
                    area: ['960px', '300px'],
                    content: "../fwhsbg/inithshbzhs?fwHsIndexList=" + this.hbYfwhs + "&bgbh=" + this.bgbh
                });
            } else {
                this._fwhsHbMain(zhsList);
            }
        },
        _fwhsHbMain : function (zhsList){
            if (this.fwhsHbMainFwHsIndex) {
                var loadIndex = layer.load(2, {shade: [0.1, '#fff']});
                $.ajax({
                    url: "../fwhsbg/hshb",
                    traditional: true,
                    data: {
                        bgbh: this.bgbh,
                        mainIndex: this.fwhsHbMainFwHsIndex,
                        yfwHsIndexList: this.hbYfwhs,
                        hbfx: this.hbfx,
                        ch: this.hbch,
                        zhsList: zhsList
                    },
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        layer.close(loadIndex);
                        ityzl_SHOW_SUCCESS_LAYER("合并成功");
                        setTimeout(function () {
                            layer.close($.hsbg.hsbgIndex);
                            var $iframe;
                            var iframes = $("iframe");
                            var child;
                            if (iframes.length > 0) {
                                for (var j = 0; j < iframes.length; j++) {
                                    if ($(iframes[j]).attr("src").indexOf("/bgxx/infobgxx") > 0) {
                                        $iframe = $(iframes[j]);
                                    }
                                }
                            }
                            if ($iframe) {
                                child = $iframe[0].contentWindow;
                                child.parent.location.reload();
                            }
                        }, 1000)

                    },
                    error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr,this);
                    }
                });
            }
        },
        _fwhsCf : function(data) {
            // 先弹变更基本信息的窗
            var bglx = "户室拆分";
            var zl = data.zl;
            var fwhsIndex = data.fw_hs_index;
            if (!zl && fwhsIndex) {
                zl = getFwzl(fwhsIndex);
            }
            this.cfYfwhs = data.fw_hs_index;
            var fwIndexList = [];
            fwIndexList.push(data.fw_hs_index);
            addModel();
            $.ajax({
                url: "../check/fwhs/bdcdyh",
                dataType: "json",
                traditional: true,
                data: {
                    indexList: fwIndexList
                },
                success: function (data) {
                    removeModal();
                    if (data.success) {
                        // 打开拆分 变更基本信息页面
                        toView(getIP()+"/bgxx/infobgxx?zl=" + zl + "&bglx=" + encodeURI(bglx)+ "&fwHsIndex=" + data.fw_hs_index,
                            $.extend({},$.hsbg,{tabname:"户室拆分"}));
                    } else {
                        layer.msg(data.msg)
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        },
        _fwhsCfView : function() {
            saveSessionParam($.hsbg);
            toView(getIP()+"/fwhsbg/initcf?fwHsIndex=" + this.cfYfwhs + "&bgbh=" + this.bgbh
                ,$.extend({},$.hsbg,{fromurl:getBackUrl()}));
        }
    }

    // 从 session中初始化参数
    $.hsbg._initFromSession();
})(jQuery);

function getFwzl(fwhsIndex) {
    var zl = '';
    $.ajax({
        url: "../fwhs/infofwhs",
        data: {
            fwHsIndex: fwhsIndex
        },
        dataType: "json",
        async: false,
        success: function (data) {
            if (data.zl) {
                zl = data.zl;
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this);
        }
    });
    return zl;
}