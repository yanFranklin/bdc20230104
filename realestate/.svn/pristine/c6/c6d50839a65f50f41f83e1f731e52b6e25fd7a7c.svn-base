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
        addModel();

        var zdData;
        // 获取字典
        $.ajax({
            url: '/realestate-register-ui/rest/v1.0/qlxx/zd',
            type: "GET",
            async: false,
            dataType: "json",
            success: function (data) {
                zdData = data;
                console.info(zdData);
            }
        });

        // 获取公共项目信息
        getXmxx();

        // 设置字符过多，省略样式
        var reverseList = ['zl'];

        var taburl = BASE_URL + '/bdcdy/list/qsqc/' + processInsId;

        /**
         * 加载Table数据列表
         */
        table.render({
            elem: '#pageTable',
            id: 'pageTable',
            toolbar: '#toolbarDemo',
            title: '权属清册',
            defaultToolbar: ['filter'],
            url: taburl,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            cellMinWidth: 80,
            even: true,
            limits: commonLimits,// 每页数据量
            cols: [[
                {field: 'xh', title: '序号', width: 50, type: 'numbers', rowspan: 2},
                {field: 'zl', title: '不动产坐落', minWidth: 250, rowspan: 2},
                {field: 'zh', title: '幢号', width: 100, rowspan: 2},
                {field: 'fjh', title: '房号', width: 150, rowspan: 2},
                {
                    field: 'fwjg', title: '结构', width: 150, rowspan: 2, templet: function (d) {
                        return convertZdDmToMc("fwjg", d.fwjg);
                    }
                },
                {field: 'zcs', title: '总层数', width: 100, rowspan: 2},
                {field: 'szmyc', title: '所在层', width: 100, rowspan: 2},
                {field: '', title: '面积（㎡）', width: 200, colspan: 2},
                {field: '', title: '权利性质', colspan: 2},
                {
                    field: '', title: '房屋用途', width: 150, rowspan: 2, templet: function (d) {
                        return convertZdDmToMc("fwyt", d.dzwyt);
                    }
                },
                {field: '', title: '土地用途', colspan: 2},
            ], [
                {field: 'dzwmj', width: 120, title: '房屋',},
                {field: 'zdzhmj', width: 120, title: '土地',},
                {
                    field: 'fwxz', width: 130, title: '房屋', templet: function (d) {
                        return convertZdDmToMc("fwxz", d.fwxz);
                    }
                },
                {
                    field: 'qlxz', width: 130, title: '土地', templet: function (d) {
                        return convertZdDmToMc("qlxz", d.qlxz);
                    }
                },
                {
                    field: 'ytSyqx', title: '使用期限', width: 295, rowspan: 2
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
            var dataUrl = getPrintIp() + "/realestate-register-ui/rest/v1.0/print/bdcdy/" + processInsId + "/" + dylx + "/xml";
            console.info(dataUrl);
            printChoice(dylx,"realestate-register-ui", dataUrl, modelUrl,false,sessionKey);
        }

        /**
         * 渲染页面的公共信息
         * @param data
         */
        function getXmxx() {
            // 获取当前权利的项目信息
            var url = BASE_URL + '/bdcdy/xmxx/qsqc?gzlslid=' + processInsId;
            $.ajax({
                url: url,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if (data) {
                        var xmid = data.xmid;
                        var zl = data.zl;
                        var head = /*isNotBlank(data[0].gzldymc)? data[0].gzldymc + "清册": */"清册";
                        $("#head").html(head);
                        $("#zl").html(zl);
                        $("#bdcqzh").html(data.bdcqzh);

                        $("#tfdw").html("填发单位：" + data.djjg);
                        var fmt = 'yyyy年MM月dd';
                        var djrq = isNotBlank(data.djsj) ? Format(data.djsj, fmt) : "";
                        $("#djrq").html("登记日期：" + djrq);
                        $("#tfrq").html("填发日期：" + Format(format(new Date()), fmt));
                        var width  =  $("#bottom-smxx").width()-400;
                        $(".bottom-tfxx").css("padding-left",width+"px");
                        getQlrxx(xmid);
                    }
                }
            });
        }

        function getQlrxx(xmid){
            var url =  BASE_URL + '/bdcdy/qlrxx';
            $.ajax({
                url: url,
                type: "GET",
                data : {xmid:xmid, qlrlb: 1},
                dataType: "json",
                success: function (data) {
                    if (data) {
                        var gyqr = [];
                        var qlr = "";
                        $.each(data, function(index, val){
                            if(index == 0){
                                qlr = val.qlrmc;
                            }else{
                                gyqr.push(val.qlrmc);
                            }
                        });
                        $("#qlr").html(qlr);
                        $("#gyqr").html(gyqr.join("，"));

                    }
                }
            });
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
