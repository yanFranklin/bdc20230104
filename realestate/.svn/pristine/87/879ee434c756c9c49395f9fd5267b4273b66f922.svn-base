(function ($) {
    $.xmbg = {
        hbYxmxx: [],
        fwXmxxIndex: "",
        lszd: "",
        xmxxHbMainFwXmxxIndex: "",
        bgbh: "",
        _initFromSession: function () {
            var sessionObject = setSessionParamObject();
            $.xmbg.hbYxmxx = sessionObject.hbYxmxx;
            $.xmbg.fwXmxxIndex = sessionObject.fwXmxxIndex;
            $.xmbg.lszd = sessionObject.lszd;
            $.xmbg.xmxxHbMainFwXmxxIndex = sessionObject.xmxxHbMainFwXmxxIndex;
            $.xmbg.bgbh = sessionObject.bgbh;
        },
        _submitBgWithBgbh: function (bglx, bgbh) {
            this.bgbh = bgbh;
            var bglx = bglx;
            if (bglx == "变更") {
                this._xmjbxxBgView();
            }
            if (bglx == "灭失") {
                this._xmxxMs();
            }
            if (bglx == "合并") {
                this._xmxxHbMain()
            }
        },
        _xmxxHb: function (data) {
            var djh = [];
            // 先弹变更基本信息的窗
            this.hbYxmxx = [];
            for (var i = 0; i < data.length; i++) {
                this.hbYxmxx.push(data[i].fwXmxxIndex);
                djh.push(data[i].lszd)
            }
            if (djh) {
                var msg;
                for (var i = 0; i < djh.length; i++) {
                    if (djh[0]!= djh[i]) {
                        msg = "同宗地下项目才可以合并"
                    }
                }
                if (msg) {
                    layer.msg(msg);
                } else {
                    addModel();
                    $.ajax({
                        url: "../check/xmxx/bdcdyh",
                        dataType: "json",
                        traditional: true,
                        data: {
                            indexList: this.hbYxmxx
                        },
                        success: function (data) {
                            removeModal();
                            if (data.success) {
                                // 打开合并页面
                                layer.open({
                                    type: 2,
                                    title: "项目信息合并",
                                    area: ['960px', '300px'],
                                    content: "../xmxxbg/inithb?fwXmxxIndexList=" + encodeURI($.xmbg.hbYxmxx) + ""
                                });
                            } else {
                                layer.msg(data.msg)
                            }
                        },
                        error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr,this)
                        }
                    });
                }
            }
        },
        _xmxxHbMain: function () {
            if (this.xmxxHbMainFwXmxxIndex) {
                addModel();
                $.ajax({
                    url: "../xmxxbg/xmxxhb",
                    traditional: true,
                    data: {
                        bgbh: this.bgbh,
                        mainIndex: this.xmxxHbMainFwXmxxIndex,
                        yXmxxIndexList: this.hbYxmxx
                    },
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        removeModal();
                        // layer.msg(data.msg);
                        refreshAndDelete(data.msg,true);
                    },
                    error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr,this);
                    }
                });
            }
        },
        _xmxxHbSetMainIndex: function (fwXmxxIndex, zl) {
            this.xmxxHbMainFwXmxxIndex = fwXmxxIndex;
            var bglx = "合并";
            if (!zl || zl == 'null') {
                zl = '';
            }
            toView(getIP() + "/bgxx/infobgxx?zl=" + zl + "&bglx=" + encodeURI(bglx),
                $.extend({}, $.xmbg, {tabname: "项目合并"}));
        },
        _xmjbxxBgView: function () {
            var fwIndexList = [];
            fwIndexList.push(this.fwXmxxIndex);
            $.ajax({
                url: "../check/xmxx/bdcdyh",
                dataType: "json",
                traditional: true,
                data: {
                    indexList: fwIndexList
                },
                success: function (data) {
                    if (data.success) {
                        // 打开变更页面
                        saveSessionParam($.xmbg);
                        toView(getIP() + "/xmxxbg/jbxxbgview?fwXmxxIndex=" + $.xmbg.fwXmxxIndex + "&bgbh=" + $.xmbg.bgbh + "&lszd=" + $.xmbg.lszd,
                            $.extend({}, $.xmbg, {fromurl: getBackUrl(true)}));
                    } else {
                        layer.msg(data.msg);
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });

        },
        _xmxxJbxxBg: function (postData) {
            postData.bgbh = this.bgbh;
            var fwIndexList = [];
            fwIndexList.push(postData.fwXmxxIndex);
            addModel();
            $.ajax({
                url: "../xmxxbg/xmjbxxbg",
                dataType: "json",
                data: postData,
                success: function (data) {
                    removeModal();
                    if (data && data.success) {
                        refreshAndDelete(data.msg,true);
                    } else {
                        layer.alert("变更失败");
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
            return false;
        },
        _xmxxBg: function (data) {
            var bglx = "变更";
            var zl = '';
            if (data.zl) {
                zl = data.zl;
            }
            this.fwXmxxIndex = data.fwXmxxIndex;
            this.lszd = data.lszd;
            // 打开合并页面
            var fwIndexList = [];
            fwIndexList.push(data.fwXmxxIndex);
            addModel();
            $.ajax({
                url: "../check/xmxx/bdcdyh",
                dataType: "json",
                traditional: true,
                data: {
                    indexList: fwIndexList
                },
                success: function (data) {
                    removeModal();
                    if (data.success) {
                        // 打开基本信息变更
                        toView(getIP() + "/bgxx/infobgxx?zl=" + zl + "&bglx=" + encodeURI(bglx),
                            $.extend($.xmbg, {tabname: "项目变更"}));
                    } else {
                        layer.msg(data.msg)
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        },
        _xmxxMs: function () {
            addModel();
            $.ajax({
                url: "../xmxxbg/ms",
                dataType: "json",
                data: {
                    yFwXmxxIndex: this.fwXmxxIndex,
                    bgbh: this.bgbh
                },
                success: function (data) {
                    removeModal();
                    if (data && data.success) {
                        refreshAndDelete(data.msg,true);
                    } else {
                        layer.alert(data.msg)
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        },
        _xmxxMsView: function (data) {
            var bglx = "灭失";
            var zl = '';
            if (data.zl) {
                zl = data.zl;
            }
            this.fwXmxxIndex = data.fwXmxxIndex;
            var fwIndexList = [];
            fwIndexList.push(data.fwXmxxIndex);
            addModel();
            $.ajax({
                url: "../check/xmxx/bdcdyh",
                dataType: "json",
                traditional: true,
                data: {
                    indexList: fwIndexList
                },
                success: function (data) {
                    removeModal();
                    if (data.success) {
                        // 打开基本信息变更
                        toView(getIP() + "/bgxx/infobgxx?zl=" + zl + "&bglx=" + encodeURI(bglx),
                            $.extend($.xmbg, {tabname: "项目灭失"}));
                    } else {
                        layer.msg(data.msg)
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        }
    }
    $.xmbg._initFromSession();
})(jQuery);