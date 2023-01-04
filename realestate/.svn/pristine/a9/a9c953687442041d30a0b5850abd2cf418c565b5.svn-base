var dylxArry=["dwsfqc"]
var sessionKey = "dwsfqc";
setDypzSession(dylxArry,sessionKey)
layui.use(['form', 'table', 'jquery'], function () {
    var form = layui.form;
    var table = layui.table;
    var $ = layui.jquery;

    $(function () {
        //绑定回车键
        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#search").click();
            }
        });
        // 获取参数
        var processInsId = $.getUrlParam('processInsId');

        // 遮罩
        // addModel();

        // 设置字符过多，省略样式
        var reverseList = ['zl'];

        /**
         * 加载Table数据列表
         */
        var taburl = '/realestate-inquiry-ui/history/xm' + '?gzlslid=' + '4141678';
        table.render({
            elem: '#pageTable',
            id: 'pageTable',
            toolbar: '#toolbarDemo',
            title: '权属清册',
            defaultToolbar: ['filter'],
            // url: taburl,
            data: [{
                xh: 1,
                zl: '南京市'
            }],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            cellMinWidth: 80,
            even: true,
            limits: commonLimits,// 每页数据量
            cols: [[
                {field: 'xh', title: '序号', width: 50, type: 'numbers', rowspan: 2},
                {field: 'zl', title: '不动产坐落', minWidth: 250, rowspan: 2},
                {field: 'zh', title: '幢号', rowspan: 2},
                {field: 'fjh', title: '房号', rowspan: 2},
                {field: 'fwjg', title: '结构', rowspan: 2, templet: function (d) {
                        return convertZdDmToMc("fwjg", d.fwjg);
                    }},
                {field: 'zcs', title: '总层数', rowspan: 2},
                {field: 'szc', title: '所在层', rowspan: 2},
                {field: '', title: '面积（㎡）', colspan: 2},
                {field: '', title: '用途', colspan: 2},
                {field: '', title: '权利性质', colspan: 2},
                {field: '', title: '土地使用期限', minWidth:300, colspan: 2},
                {field: '', title: '土地使用期限2', minWidth:300, colspan: 2},
                {field: '', title: '土地使用期限3', minWidth:300, colspan: 2},
            ],[
                { field: 'dzwmj', title: '房屋',},
                { field: 'zdzhmj', title: '土地',},
                { field: 'dzwyt', title: '房屋', templet: function (d) {
                        return convertZdDmToMc("fwyt", d.dzwyt);
                    }},
                {
                    field: 'zdzhyt', title: '土地', templet: function (d) {
                        return convertZdDmToMc("tdyt", d.zdzhyt) + '\xa0' + convertZdDmToMc("tdyt", d.zdzhyt2) + '\xa0' + convertZdDmToMc("tdyt", d.zdzhyt3);
                    }
                },

                {
                    field: 'fwxz', title: '房屋', templet: function (d) {
                        return convertZdDmToMc("fwxz", d.fwxz);
                    }
                },
                {
                    field: 'qlxz', title: '土地', templet: function (d) {
                        return convertZdDmToMc("qlxz", d.qlxz);
                    }
                },
                {
                    field: 'tdsyqssj', title: '起始时间', minWidth: 150,
                    templet: function (d) {
                        if (d.tdsyqssj) {
                            return Format(format(d.tdsyqssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {
                    field: 'tdsyjssj', title: '结束时间', minWidth: 150,
                    templet: function (d) {
                        if (d.tdsyjssj) {
                            return Format(format(d.tdsyjssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {
                    field: 'tdsyqssj2', title: '起始时间', minWidth: 150,
                    templet: function (d) {
                        if (d.tdsyqssj2) {
                            return Format(format(d.tdsyqssj2), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {
                    field: 'tdsyjssj2', title: '结束时间', minWidth: 150, templet: function (d) {
                        if (d.tdsyjssj2) {
                            return Format(format(d.tdsyjssj2), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {
                    field: 'tdsyqssj3', title: '起始时间', minWidth: 150,
                    templet: function (d) {
                        if (d.tdsyqssj3) {
                            return Format(format(d.tdsyqssj3), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {
                    field: 'tdsyjssj3', title: '结束时间', minWidth: 150,
                    templet: function (d) {
                        if (d.tdsyjssj3) {
                            return Format(format(d.tdsyjssj3), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
            ]],
            text: {
                none: '未查询到数据'
            },
            autoSort: false,
            page: true,
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                //setTableHeight(131);
                setTableHeight();
                reverseTableCell(reverseList);
                // 移除遮罩
                removeModel();

                // 监听行点击获取xmid，生成树状图
                table.on('row(pageTable)', function(obj){
                    var xmid = obj.data.xmid;
                    parent.generateLsgx(xmid);
                });
            }
        });

        $('.bdc-table-box').on('mouseenter', 'td', function () {
            if ($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1) {
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
            }
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                }, 20);
            });
        });

        //主页面头工具栏事件
        table.on('toolbar(pageTable)', function (obj) {
            switch (obj.event) {
                case 'print':
                    printQsqc();
                    break;
            }
        });

        /**
         * 打印
         */
        function printQsqc() {
            var dylx = "dwsfqc";
            var modelUrl = qcMpdelUrl;
            var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/bdcdy/" + processInsId + "/" + dylx + "/xml";
            console.info(dataUrl);
            printChoice(dylx,"realestate-register-ui", dataUrl, modelUrl,false,sessionKey);
        }

        //前台的字典转换，代码转换为名称
        function convertZdDmToMc(zdname, zdDm) {
            if (zdDm && zdData) {
                var zdVals = zdData[zdname];
                if (zdVals) {
                    for (var i = 0; i < zdVals.length; i++) {
                        var zdVal = zdVals[i];
                        if (zdDm == zdVal.DM) {
                            return zdVal.MC;
                        }
                    }
                }
                return zdDm;
            }
            return "";
        }


    });
    window.onresize=function(){
        var width  =  $("#bottom-smxx").width()-400;
        $(".bottom-tfxx").css("padding-left",width+"px");
    }


});
