var form;
//分版本所需全局变量
//加载基本信息时，南通版本需要加载领证人
//海门版本
var edition = 'hm';

//字典列表
var zdList = {
    mjdw: [],
    zjzl: []
};
//初始化获取内容
var sfdydj;
var $,layer;
//非受理节点权利信息优先展示
var isSljd = false,isQtjd = false;
var taskId = getQueryString("taskId");
layui.use(['jquery', 'element', 'form','layer'], function () {
    var element = layui.element;
    form = layui.form;
    $ = layui.jquery;
    layer = layui.layer;
    $(function () {
        //监听领证人修改证件类型
        form.on('select(lzrzjhFilter)', function (data) {
            var attrVal = $("#lzrzjh").attr("lay-verify");
            if (data.value == '1') {
                if (isNotBlank(attrVal)) {
                    if (attrVal.indexOf("identitynew") < 0) {
                        $("#lzrzjh").attr("lay-verify", attrVal + "|identitynew");
                    }
                } else {
                    $("#lzrzjh").attr("lay-verify", "identitynew");
                }
            } else {
                //移除身份证验证
                if (isNotBlank(attrVal) && attrVal.indexOf("identitynew") > -1) {
                    $("#lzrzjh").attr("lay-verify", attrVal.replace("identitynew", ""));
                }
            }
        });

        //重新生成权利其他状况
        $(".bdc-form-div").on('click','.resetqlqtzk', function () {
            var xmid =$(this).data("xmid");
            reGenerateQlqtzk(xmid,"2",$(this));
        });

        //重新生成附记
        $(".bdc-form-div").on('click','.resetfj', function () {
            var xmid =$(this).data("xmid");
            reGenerateFj(xmid,"3",$(this));
        });

        //监听 权利信息 内 附记 单击
        $('.layui-tab-content').on('click','.bdc-qlqtzk-popup',function(){
            var $nowTab =$(this).parents(".layui-tab-item");
            $nowTab.find("#qlqtzksdtx").removeAttr('style');
        });

        //监听 权利信息 内 附记 单击
        $('.layui-tab-content').on('click','.bdc-pj-popup',function(){
            var $nowTab =$(this).parents(".layui-tab-item");
            $nowTab.find("#fjsdtx").removeAttr('style');
        });
    });
});

/**
 * 判断流程是否生成一本证,生成多本证隐藏附记和权利其他状况(初始化方法)
 */
function initHideQlqtzkFjByZssl($nowTab,$qlqtzk,$fj){
    var tabDjxl = $nowTab.data('djxl');
    getReturnData("/slym/ql/checkPlZhOne",{gzlslid:processInsId,djxl:tabDjxl},"GET",function (data) {
        if(!data){
            //生成多本证移除附记和权利其他状况
            if($qlqtzk.length >0) {
                $qlqtzk.parent().parent().remove();
            }
            if($fj.length >0) {
                $fj.parent().parent().remove();
            }
        }
    },function (error) {
        delAjaxErrorMsg(error);

    });
}

/**
 * 重新生成权利其他状况
 */
function reGenerateQlqtzk(xmid,mode,btn){
    var $nowTab =$(btn).parents(".layui-tab-item");
    var $qlqtzk = $nowTab.find("textarea[name='bfqlqtzk']");
    if($qlqtzk.length >0) {

        $qlqtzk.val("");
        //清空原有数据并保存受理页面
        try{
            $.when(saveSlym()).done(function () {
            //重新加载模板数据
            queryQlqtzkFjMb(xmid, $qlqtzk, mode,true);
            })
        }catch (e) {
            removeModal();
            if (e.message) {
                showAlertDialog(e.message);
            } else {
                delAjaxErrorMsg(e);

            }
            return;
        }

    }
}

/**
 * 重新生成附记
 */
function reGenerateFj(xmid,mode,btn){
    var $nowTab =$(btn).parents(".layui-tab-item");
    var $fj = $nowTab.find("textarea[name='fj']");
    if($fj.length >0) {
        $fj.val("");
        //清空原有数据并保存受理页面
        $.when(saveSlym()).done(function () {
            //重新加载模板数据
            queryQlqtzkFjMb(xmid, $fj, mode,true);
        });
    }
}


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
            {field: 'sfzf', title: '是否主房', minWidth: 100 ,templet:function (d) {
                    if(d.sfzf == 1){
                        return '是';
                    }else {
                        return '<span class="bdc-change-blue">否</span>';

                    }
                }
            },
            {field: 'zdsylx', title: '宗地使用类型', minWidth: 120 ,templet:function (d) {
                    if(d.zdsylx === 1){
                        return '独用';
                    }else if(d.zdsylx === 2) {
                        return '共有';
                    } else {
                        return '';
                    }
                }},
            {field: 'ycqzh', title: '原产权证号', minWidth: 270, hide: true},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'zl', title: '坐落', minWidth: 240},
            {
                field: 'ghyt', title: '规划用途', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.dzwyt, zdList.fwyt);
                }
            },
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
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
            {field: 'jyjg', title: '房地产交易价格(万元)', minWidth: 180},
            {
                field: 'jgsj', title: '竣工时间', minWidth: 150
            },
            {field: 'jzmj', title: '建筑面积', minWidth: 100},
            {field: 'zyjzmj', title: '专有建筑面积', minWidth: 120},
            {field: 'ftjzmj', title: '分摊建筑面积', minWidth: 120},
            {field: 'fj', title: '附记', minWidth: 250},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === parseInt(commonDyaq_qllx)) {
        if (zxlc === "true") {
            /**
             * 抵押权注销，原产权证号显示为 抵押证明号
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'sfzf', title: '是否主房', minWidth: 100 ,templet:function (d) {
                        if(d.sfzf == 1){
                            return '是';
                        }else {
                            return '<span class="bdc-change-blue">否</span>';

                        }
                    }
                },
                {field: 'zdsylx', title: '宗地使用类型', minWidth: 120 ,templet:function (d) {
                        if(d.zdsylx === 1){
                            return '独用';
                        }else if(d.zdsylx === 2) {
                            return '共有';
                        } else {
                            return '';
                        }
                    }},
                {field: 'ycqzh', title: '原产权证号', minWidth: 270, hide: true},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'zl', title: '坐落', minWidth: 240},
                {
                    field: 'ghyt', title: '规划用途', minWidth: 150,
                    templet: function (d) {
                        return changeDmToMc(d.dzwyt, zdList.fwyt);
                    }
                },
                {field: 'ycqzh', title: '抵押证明号', minWidth: 250},
                {field: 'zwr', title: '抵押人', minWidth: 100},
                {
                    field: 'dyfs', title: '抵押方式', minWidth: 100,
                    templet: function (d) {
                        return changeDmToMc(d.dyfs, zdList.dyfs);
                    }
                },
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
                {field: 'tddymj', title: '抵押土地面积', minWidth: 150},
                {field: 'fwdymj', title: '抵押房产面积', minWidth: 150},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
                {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
                {field: 'zh', title: '幢号', minWidth: 100},
                {field: 'zjjzwdyfw', title: '在建建筑物抵押范围', minWidth: 180},
                {field: 'zjjzwzl', title: '在建建筑物坐落', minWidth: 150},
                {field: 'fj', title: '附记', minWidth: 250},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];
        } else {
            /**
             * 抵押权数据列
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'sfzf', title: '是否主房', minWidth: 100 ,templet:function (d) {
                        if(d.sfzf == 1){
                            return '是';
                        }else {
                            return '<span class="bdc-change-blue">否</span>';
                        }
                    }
                },
                {field: 'zdsylx', title: '宗地使用类型', minWidth: 120 ,templet:function (d) {
                        if(d.zdsylx === 1){
                            return '独用';
                        }else if(d.zdsylx === 2) {
                            return '共有';
                        } else {
                            return '';
                        }
                    }},
                {field: 'ycqzh', title: '原产权证号', minWidth: 250},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'zl', title: '坐落', minWidth: 240},
                {
                    field: 'ghyt', title: '规划用途', minWidth: 150,
                    templet: function (d) {
                        return changeDmToMc(d.dzwyt, zdList.fwyt);
                    }
                },
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
                {field: 'zjjzwdyfw', title: '在建建筑物抵押范围', minWidth: 180},
                {field: 'zjjzwzl', title: '在建建筑物坐落', minWidth: 150},
                {field: 'fwdymj', title: '抵押房产面积', minWidth: 150},
                {field: 'fj', title: '附记', minWidth: 250},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];
        }
    } else if (qllx === 3 || qllx === 7) {
        /**
         * 建设用地使用权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'sfzf', title: '是否主房', minWidth: 100 ,templet:function (d) {
                    if(d.sfzf == 1){
                        return '是';
                    }else {
                        return '<span class="bdc-change-blue">否</span>';

                    }
                }
            },
            {field: 'zdsylx', title: '宗地使用类型', minWidth: 120 ,templet:function (d) {
                    if(d.zdsylx === 1){
                        return '独用';
                    }else if(d.zdsylx === 2) {
                        return '共有';
                    } else {
                        return '';
                    }
                }},
            {field: 'ycqzh', title: '原产权证号', minWidth: 270, hide: true},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
            {field: 'fj', title: '附记', minWidth: 250},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === 19) {
        /**
         * 地役权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'sfzf', title: '是否主房', minWidth: 100 ,templet:function (d) {
                    if(d.sfzf == 1){
                        return '是';
                    }else {
                        return '<span class="bdc-change-blue">否</span>';

                    }
                }
            },
            {field: 'zdsylx', title: '宗地使用类型', minWidth: 120 ,templet:function (d) {
                    if(d.zdsylx === 1){
                        return '独用';
                    }else if(d.zdsylx === 2) {
                        return '共有';
                    } else {
                        return '';
                    }
                }},
            {field: 'ycqzh', title: '原产权证号', minWidth: 250},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
            {field: 'sfzf', title: '是否主房', minWidth: 100 ,templet:function (d) {
                    if(d.sfzf == 1){
                        return '是';
                    }else {
                        return '<span class="bdc-change-blue">否</span>';

                    }
                }
            },
            {field: 'zdsylx', title: '宗地使用类型', minWidth: 120 ,templet:function (d) {
                    if(d.zdsylx === 1){
                        return '独用';
                    }else if(d.zdsylx === 2) {
                        return '共有';
                    } else {
                        return '';
                    }
                }},
            {field: 'ycqzh', title: '原产权证号', minWidth: 250},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    }
    else if (qllx === 98) {
        if (zxlc === "true") {
            /**
             * 解封数据列， 原产权证号显示为查封文号
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'sfzf', title: '是否主房', minWidth: 100 ,templet:function (d) {
                        if(d.sfzf == 1){
                            return '是';
                        }else {
                            return '<span class="bdc-change-blue">否</span>';

                        }
                    }
                },
                {field: 'zdsylx', title: '宗地使用类型', minWidth: 120 ,templet:function (d) {
                        if(d.zdsylx === 1){
                            return '独用';
                        }else if(d.zdsylx === 2) {
                            return '共有';
                        } else {
                            return '';
                        }
                    }},
                {field: 'ycqzh', title: '原产权证号', minWidth: 270, hide: true},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'zl', title: '坐落', minWidth: 240},
                {field: 'ycqzh', title: '原产权证号', minWidth: 200},
                {field: 'cfwh', title: '查封文号', minWidth: 200},
                {field: 'cfjg', title: '查封机关', minWidth: 150},
                {
                    field: 'cflx', title: '查封类型', minWidth: 100,
                    templet: function (d) {
                        return changeDmToMc(d.cflx, zdList.cflx);
                    }
                },
                {field: 'cfwj', title: '查封文件', minWidth: 150},
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
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
                {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
                {field: 'zh', title: '幢号', minWidth: 100},
                {field: 'jfwh', title: '解封文号', minWidth: 200},
                {field: 'jfjg', title: '解封机关', minWidth: 150},
                {field: 'jfwj', title: '解封文件', minWidth: 150},
                {field: 'fj', title: '附记', minWidth: 250},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];
        } else {
            /**
             * 查封数据列
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'sfzf', title: '是否主房', minWidth: 100 ,templet:function (d) {
                        if(d.sfzf == 1){
                            return '是';
                        }else {
                            return '<span class="bdc-change-blue">否</span>';
                        }
                    }
                },
                {field: 'zdsylx', title: '宗地使用类型', minWidth: 120 ,templet:function (d) {
                        if(d.zdsylx === 1){
                            return '独用';
                        }else if(d.zdsylx === 2) {
                            return '共有';
                        } else {
                            return '';
                        }
                    }},
                {field: 'ycqzh', title: '原产权证号', minWidth: 250},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'zl', title: '坐落', minWidth: 240},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
                {field: 'fj', title: '附记', minWidth: 250},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];
        }
    } else if (qllx === 96) {
        if (sfdydj) {
            /**
             * 预抵押数据列
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'sfzf', title: '是否主房', minWidth: 100 ,templet:function (d) {
                        if(d.sfzf == 1){
                            return '是';
                        }else {
                            return '<span class="bdc-change-blue">否</span>';

                        }
                    }
                },
                {field: 'zdsylx', title: '宗地使用类型', minWidth: 120 ,templet:function (d) {
                        if(d.zdsylx === 1){
                            return '独用';
                        }else if(d.zdsylx === 2) {
                            return '共有';
                        } else {
                            return '';
                        }
                    }},
                {field: 'ycqzh', title: '原产权证号', minWidth: 250},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'zl', title: '坐落', minWidth: 240},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
                {field: 'qdjg', title: '被担保主债权数额(万元)', minWidth: 200},
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
                {field: 'fj', title: '附记', minWidth: 250},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];
        } else {
            /**
             * 预告数据列
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'sfzf', title: '是否主房', minWidth: 100 ,templet:function (d) {
                        if(d.sfzf == 1){
                            return '是';
                        }else {
                            return '<span class="bdc-change-blue">否</span>';

                        }
                    }
                },
                {field: 'zdsylx', title: '宗地使用类型', minWidth: 120 ,templet:function (d) {
                        if(d.zdsylx === 1){
                            return '独用';
                        }else if(d.zdsylx === 2) {
                            return '共有';
                        } else {
                            return '';
                        }
                    }},
                {field: 'ycqzh', title: '原产权证号', minWidth: 270, hide: true},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'zl', title: '坐落', minWidth: 240},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
                {field: 'jyje', title: '交易金额(万元)', minWidth: 200},
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
                {field: 'fj', title: '附记', minWidth: 250},
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
            {field: 'sfzf', title: '是否主房', minWidth: 100 ,templet:function (d) {
                    if(d.sfzf == 1){
                        return '是';
                    }else {
                        return '<span class="bdc-change-blue">否</span>';

                    }
                }
            },
            {field: 'zdsylx', title: '宗地使用类型', minWidth: 120 ,templet:function (d) {
                    if(d.zdsylx === 1){
                        return '独用';
                    }else if(d.zdsylx === 2) {
                        return '共有';
                    } else {
                        return '';
                    }
                }},
            {field: 'ycqzh', title: '原产权证号', minWidth: 270, hide: true},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}

        ];
    }
    return cols;
}

//处理合计信息
function dealHjxx(tabQllx,djxl) {
    if (tabQllx === 4 || tabQllx === 6 || tabQllx === 8) {
        $(".layui-show .bdc-table-zj").removeClass("bdc-hide");
        var height = $('.bdc-table-zj').height();
        $(".layui-show .bdc-line-search-container").css("padding-top", height+62);



        getReturnData("/slym/ql/calculatedQlMj", {gzlslid: processInsId,zxlc:zxlc,djxl:djxl}, "GET", function (data) {
            if (data) {
                //总建筑面积
                if(isNotBlank(data.jzmj)) {
                    $(".layui-show #sumjzmj").text(data.jzmj + "m²");
                }else{
                    $(".layui-show #sumjzmj").text("0m²");
                }
                //总土地权利面积
                if(isNotBlank(data.tdqlmj)) {
                    $(".layui-show #sumtdqlmj").text(data.tdqlmj + "m²");
                }else{
                    $(".layui-show #sumtdqlmj").text("0m²");

                }
            }


        }, function (error) {
            delAjaxErrorMsg(error);
        })
    }
}

//计算使用期限（单位：月）
function countSyqxByMonth(){
    var $layuishow = $('.layui-show');
    var ydyqssj = $layuishow.find("#yg-zwlxqssj").val();
    var dyaqssj = $layuishow.find("#dyaq-zwlxqssj").val();
    var syqx = parseInt($layuishow.find(".zwlxqx").val());
    if (syqx > 0) {
        //计算结束时间后的日期 日 要减一天
        if (isNotBlank(ydyqssj)) {
            var qssj = new Date(ydyqssj);
            qssj.setMonth(qssj.getMonth() + syqx);
            var time = qssj.getTime() - 1000 * 60 * 60 * 24;
            console.log(Format(formatChinaTime(new Date(time)), "yyyy-MM-dd"));
            $layuishow.find("#yg-zwlxqssj").val(Format(formatChinaTime(new Date(time)), "yyyy-MM-dd"));
        } else if (isNotBlank(dyaqssj)) {
            var qssj = new Date(dyaqssj);
            qssj.setMonth(qssj.getMonth() + syqx);
            var time = qssj.getTime() - 1000 * 60 * 60 * 24;
            console.log(Format(formatChinaTime(qssj), "yyyy-MM-dd"));
            $layuishow.find("#dyaq-zwlxjssj").val(Format(formatChinaTime(new Date(time)), "yyyy-MM-dd"));
        }
    }
}

function pj(pjdata) {
    var url = pjdata.url +"?ywbh=" + pjdata.bdcXmDO.slbh + "&jdmc=受理" + "&blry=" + pjdata.bdcXmDO.slr + "&sqrxm=" + pjdata.qlrmc + "&sqrlxfs=" + pjdata.qlrlxfs;
    console.log(encodeURI(url));
    window.open(encodeURI(url),"评价页面");
}

/**
 * 处理交易信息中登记业务表相关信息
 * var tabXmid = $('.layui-tab-item:nth-child(' + 2 + ')').data('xmid');
 */
function dealDjxx(data,xmid){
    var bdcSlJyxx = {
        htbh : data.htbh,
        jyje : data.jyje,
        htdjsj : format(new Date(data.sqsj)),
        xmid : $('.layui-tab-item:nth-child(' + 2 + ')').data('xmid')
    };
    var paremJson = {
        bdcSlJyxx :  bdcSlJyxx,
        fw : data.fw
    }
    getReturnData("/ycsl/jyxx/dealDjxx?processInsId="+processInsId,JSON.stringify(paremJson),"POST",function () {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("导入成功。");
    },function (error) {
        removeModal();
        console.info(error);
        ityzl_SHOW_WARN_LAYER("导入失败。");
    })
}

function querySpfJyxx(test) {
    showAlertDialog("目前商品房接口未开放");
}

// 查看地籍调查表
function djdcb(processInsId) {
    // 判断是否是多个不动单元
    getReturnData("/slym/xm/dcblx", {
        processInsId: processInsId
    }, 'GET', function (data) {
        // 根据返回值判断是海籍调查表还是地籍调查表
        if (data) {
            var tzm = data.tzm;
            var bdcdyh = data.bdcdyh;
            var xmid = data.xmid;
            if ('H' == tzm) {
                // 海籍调查表
                if (bdcdyh) {
                    layerOpen("海籍调查表", "/realestate-register-ui/view/hyxx/hjdcb.html?xmid=" + xmid);
                } else {
                    layerOpen("海籍调查列表", "/realestate-accept-ui/view/query/dcbList.html?processInsId=" + processInsId + '&tzm=' + tzm);
                }
            } else {
                // 地籍调查表
                if (bdcdyh) {
                    layerOpen("地籍调查表", "/building-ui/djdcb/initview?bdcdyh=" + bdcdyh);
                } else {
                    layerOpen("地籍调查列表", "/realestate-accept-ui/view/query/dcbList.html?processInsId=" + processInsId + '&tzm=' + tzm);
                }
            }
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

function saveSf() {
    getReturnData("/sf/sfxm/save/nantong", {gzlslid: processInsId}, "GET", function (data) {
        console.log("保存收费信息成功");
    }, function (err) {
        throw err;
    })
}


function setLzrqToPortal() {
    getReturnData("/slym/xm/cnlzrq", {gzlslid: processInsId}, "GET", function (data) {
        console.log("回写平台领证日期结束")
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })

}

//表格内详细信息
function showDetails(xmid, qllx, bdcdyfwlx, sfyql){
    var index = layer.open({
        type: 2,
        area: ['1150px', '600px'],
        fixed: false, //不固定
        title: "详细信息",
        maxmin: true,
        content: getContextPath() + '/view/slym/qldyxx.html?xmid=' + xmid + "&qllx=" + qllx+ "&bdcdyfwlx=" + bdcdyfwlx+ "&sfyql=" + sfyql+ "&formStateId=" + formStateId+ "&readOnly=" + readOnly+ "&zxlc=" + zxlc,
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
            $.when(loadBdcdyh(tabDjxl, tabQllx,tabXmid), reloadPlQlxx(tabXmid, $nowTab)).done(function () {
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

//根据taskId获取去当前节点名称
function getJdNameByTaskId(){
    getReturnData("/rest/v1.0/slym/jdmc", {taskId: taskId}, 'GET', function (data) {
        if (isNotBlank(data)) {
            if(data.taskName == '受理'){
                isSljd = true;
            }else {
                isQtjd = true;
            }
        }
    }, function (err) {
        console.log(err);
    }, false);
}

//加载按钮
function loadButtonArea(ymlx) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {};
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

        // 地籍调查表/海籍调查表
        $("#djdcb").click(function () {
            djdcb(processInsId);
        });
        //档案调用
        $("#dady").click(function () {
            dady(processInsId);
        });

        // 打印电子发票
        $("#printDzfp").click(function(){
            printDzfp();
        });

        //审核不通过
        $("#shbtg").click(function () {
            shbtg();
        });
        //获取扣款信息
        $("#getKkxx").click(function () {
            console.log("获取扣款信息");
            getKkxx();
        });
        // 按钮宽度
        $(".title-btn-area .layui-btn").css("padding","0 10px");
        if($(".title-btn-area").height()>38){
            $(".bdc-tab-box").css("padding-top","76px")
        }

    });
}
//获取扣款信息
function getKkxx(){
    getReturnData("/slym/sw/wsxx", {gzlslid: processInsId, htbh: getHtbhParam()}, "GET", function (data) {
        ityzl_SHOW_SUCCESS_LAYER("获取成功");
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

//获取合同编号
function getHtbhParam() {
    var htbh = "";
    getReturnData("/ycsl/getJyxxByGzlslid", {gzlslid: processInsId}, "GET", function (data) {
        if (isNotBlank(data)) {
            htbh = data.htbh;
        }
    }, function (error) {
        console.info(error);
    }, false);
    return htbh;
}
