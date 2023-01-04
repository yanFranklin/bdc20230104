var form;

//分版本所需全局变量
var edition = 'yc';
//盐城版本，受理页面点保存时不需要将权利人作为领证人保存
var bclzr = false;

//字典列表
var zdList = {
    mjdw: [],
    zjzl: []
};
//初始化获取内容
var sfdydj;

// 获取processInstanceType
var processInstanceType = $.getUrlParam('processInstanceType');

layui.use(['jquery', 'form'], function () {
    form = layui.form;
    var $ = layui.jquery;

    $(function () {
        //监听抵押方式
        form.on('select(dyfs)', function (data) {
            //抵押方式为最高额抵押时，贷款方式为商业贷款
            if (data.value === "2") {
                //定位贷款方式字段
                var $dkfs = $("select[name =dkfs]");
                if ($dkfs.length > 0) {
                    $dkfs.val("商业贷款");
                    form.render("select");
                    resetSelectDisabledCss();
                }
            }
        });

        //监听 权利信息 内 附记 单击
        $('.bdc-tab-box').on('click', '.bdc-pj-popup', function () {
            var $thisTextarea = $(this).siblings('textarea');
            var fjContent = $thisTextarea.val();
            var title = $(this).parents(".layui-form-item").find("label").text();
            layer.open({
                title: isNotBlank(title)? title : '附记',
                type: 1,
                area: ['960px'],
                btn: ['保存'],
                content: $('#fjPopup')
                , yes: function (index, layero) {
                    //提交 的回调
                    $thisTextarea.val($('#fjPopup textarea').val());
                    $('#fjPopup textarea').val('');
                    layer.close(index);
                }
                , btn2: function (index, layero) {
                    //取消 的回调
                    $('#fjPopup textarea').val('');
                }
                , cancel: function () {
                    //右上角关闭回调
                    $('#fjPopup textarea').val('');
                }
                , success: function () {
                    $('#fjPopup textarea').val(fjContent);
                }
            });
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
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积(㎡)', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },
            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {
                field: 'tdsyqssj', title: '土地使用开始期限', minWidth: 150,
                templet: function (d) {
                    if (d.tdsyqssj) {
                        return Format(format(d.tdsyqssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
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
                field: 'ghyt', title: '规划用途', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.ghyt, zdList.fwyt);
                }
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
            {field: 'dzwmj', title: '定着物面积(㎡)', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
            {field: 'dysw', title: '抵押顺位', minWidth: 100},
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
            {field: 'dzwmj', title: '定着物面积(㎡)', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
            {field: 'dzwmj', title: '定着物面积(㎡)', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
            {field: 'dzwmj', title: '定着物面积(㎡)', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
            {field: 'dzwmj', title: '定着物面积(㎡)', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
                {field: 'dzwmj', title: '定着物面积(㎡)', minWidth: 240},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {
                    field: 'qlxz', title: '权利性质', minWidth: 100,
                    templet: function (d) {
                        return changeDmToMc(d.qlxz, zdList.qlxz);
                    }
                },                {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
                {field: 'dzwmj', title: '定着物面积(㎡)', minWidth: 240},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {
                    field: 'qlxz', title: '权利性质', minWidth: 100,
                    templet: function (d) {
                        return changeDmToMc(d.qlxz, zdList.qlxz);
                    }
                },                {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
            {field: 'dzwmj', title: '定着物面积(㎡)', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },
            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
    }
    else {
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积(㎡)', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}

        ];
    }
    return cols;
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



function generateYcslxx(contractNo, index,fwlx,currxmid) {
    if(!isNotBlank(currxmid)){
        currxmid =xmid;
    }
    layer.close(index);
    $.ajax({
        url: getContextPath() + "/ycsl/fcjyxx",
        type: 'GET',
        data: {htbh: contractNo, xmid: currxmid, fwlx: fwlx, lclx : lclx},
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

//加载第三权利人信息
function generateDsQlr(xmid,$sqr,index){
    getReturnData("/slym/qlr/list/dsQlr", {xmid: xmid}, 'GET', function (data) {
        //渲染模板数据
        var json = {
            xmid: xmid,
            bdcDsQlrDOList: data,
            zd: zdList
        };
        var dsQlrxxTpl = dsQlrTpl.innerHTML;

        if(isNotBlank($sqr)){
            //渲染数据
            if($sqr.parents(".layui-tab-item").find(".dsqlr-basic").length >0){
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
    },false);

}

//删除第三权利人
function deleteDsQlr(qlrid, sxh, qlrlb,elem) {
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

//权利人详细
function showDsQlr(qlrid,xmid) {
    var url = getContextPath() + "/view/slym/dsQlr.html?xmid=" + xmid + "&processInsId=" + processInsId + "&formStateId=" + formStateId;
    layer.open({
        type: 2,
        area: ['960px', '360px'],
        fixed: false, //不固定
        title: "新增第三方权利人",
        content: url,
        btnAlign: "c"
    });
    form.render();
    resetSelectDisabledCss();
}

//第三权利人保存
function saveDsQlr() {
    var dsqlrList=[];
    $(".dsqlr-tr").each(function () {
        var $dsqlr =$(this);
        var dsqlrArray = $dsqlr.find(".dsQlr").serializeArray();
        if (dsqlrArray !== null && dsqlrArray.length > 0) {
            var dsqlr ={};
            dsqlrArray.forEach(function (item, index) {
                dsqlr[item.name] = item.value;
            });
            dsqlr.xmid =$dsqlr.find("[name=xmid]").val();
            dsqlr.qlrid =$dsqlr.find("[name=qlrid]").val();
            dsqlrList.push(dsqlr);
        }
    });
    var url = "/slym/qlr/list/updateDsQlr?processInsId=" + processInsId;

    if (dsqlrList != null &&dsqlrList.length >0) {
        getReturnData(url, JSON.stringify(dsqlrList), 'PATCH', function (data) {
        }, function (err) {
            throw err;
        }, false);
    }

}