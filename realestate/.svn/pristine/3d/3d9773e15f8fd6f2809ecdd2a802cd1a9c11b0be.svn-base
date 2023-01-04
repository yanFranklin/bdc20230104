/**
 * 盐城外部系统调用综合查询版页面JS处理
 */
var reverseList = ['zl'];
var zdList = getMulZdList("fwyt");
var bdclxList = getMulZdList("bdclx");

layui.use(['form', 'jquery', 'element', 'table'], function () {
    var $ = layui.jquery, table = layui.table;

    $(function () {
        table.render({
            elem: '#pageTable',
            title: '证书证明列表',
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xmid', width: 120, title: '项目id', hide: true},
                {field: 'ybdcdyh', width: 200, title: '原不动产单元号', hide: true},
                {field: 'bdcqzh', width: 280, title: '不动产权证号（明）', sort: true},
                {field: 'zl', minWidth: 300, title: '坐落', sort: true,},
                {field: 'qlrmc', width: 250, title: '权利人名称', sort: true,},
                {field: 'qlrzjh', width: 250, title: '权利人证件号', sort: true,},
                {
                    field: 'bdcdyh', width: 280, title: '不动产单元号', sort: true,
                    templet: function (d) {
                        return formatBdcdyh(d.bdcdyh);
                    }
                },
                {
                    field: 'djsj', width: 180, title: '登簿时间', hide: true, sort: true,
                    templet: function (d) {
                        return formatDate(d.djsj);
                    }
                },
                {field: 'djyy', width: 200, title: '登记原因', sort: true,},
                {
                    field: 'ghyt', title: '规划用途', minWidth: 150,
                    templet: function (d) {
                        return convertZdDmToMc('fwyt', d.ghyt, "zdList");
                    }
                },
                {field: 'ywrmc', width: 250, title: '义务人名称', sort: true,},
                {field: 'ywrzjh', width: 250, title: '义务人证件号', sort: true,},
                {field: 'slbh', width: 150, title: '受理编号', sort: true},
                {field: 'zh', width: 150, title: '幢号', sort: false, templet: '#zhTpl'},
                {field: 'fjh', width: 150, title: '房间号', sort: false, templet: '#fjhTpl'},
                {field: 'fwbh', width: 130, title: '房屋编号', sort: true},
                {field: 'jzmj', width: 130, title: '建筑面积', sort: false, templet: '#fwmjTpl'},
                {field: 'tdsyqmj', width: 130, title: '土地权利面积', sort: false, templet: '#tdqlmjTpl'},
                {field: 'zhlsh', width: 150, title: '证号流水号', sort: true},
                {
                    field: 'bdclx', width: 120, title: '不动产类型',
                    templet: function (d) {
                        return convertZdDmToMc('bdclx', d.bdclx, "bdclxList");
                    }
                },
                {
                    field: 'qszt', width: 110, title: '权属状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        return formatQsztWithCx(d.qszt, d.ajzt);
                    }
                },

                {
                    field: 'bdcdyZtDTO', width: 110, title: '限制状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        return formatXzzt(d.bdcdyZtDTO);
                    }
                },
                {title: '查看', fixed: 'right', toolbar: '#barDemo', width: 200}
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            }, done: function (res, curr, count) {
                setHeight();
            }
        });

        // 不动产单元号
        var bdcdyh = $.getUrlParam('bdcdyh');
        // 权属状态
        var qszt = $.getUrlParam('qszt');
        if(isNullOrEmpty(qszt)) {
            qszt = 1;
        }
        // 权利类型
        var qllx = $.getUrlParam('qllx');
        if(isNullOrEmpty(qllx)) {
            qllx = getFwQllx();
        }

        if(!isNullOrEmpty(bdcdyh)) {
            addModel();
            table.reload("pageTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/zszm"
                , where: {"bdcdyh": bdcdyh, "bdcdyhmhlx":0, "qszt3": qszt, "qllx": qllx}
                , page: {
                    curr: 1,  //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                , done: function (res, curr, count) {
                    // 查询附加信息展示
                    getZhcxFjxx(res.data);
                    setTableHeight();
                    reverseTableCell(reverseList);
                    removeModal();
                }
            });
        }

        // 查询附加信息展示
        function getZhcxFjxx(pageData) {
            if (pageData && pageData.length > 0) {
                var xmidArray = new Array();
                $.each(pageData, function (index, item) {
                    xmidArray.push(item.xmid);
                });

                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/zszm/fjxx",
                    type: "POST",
                    data: JSON.stringify(xmidArray),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if (data && data.length > 0) {
                            $.each(data, function (index, item) {
                                var zh = isNullOrEmpty(item.zh) ? "" : item.zh;
                                var fjh = isNullOrEmpty(item.fjh) ? "" : item.fjh;
                                var jzmj = isNullOrEmpty(item.jzmj) ? "" : item.jzmj;
                                var tdsyqmj = isNullOrEmpty(item.tdsyqmj) ? "" : item.tdsyqmj;

                                $("." + item.xmid + "_zh").html('<p class="bdc-table-state-th">' + zh + '</p>');
                                $("." + item.xmid + "_fjh").html('<p class="bdc-table-state-th">' + fjh + '</p>');
                                $("." + item.xmid + "_fwmj").html('<p class="bdc-table-state-th">' + jzmj + '</p>');
                                $("." + item.xmid + "_tdqlmj").html('<p class="bdc-table-state-th">' + tdsyqmj + '</p>');
                            });
                        }
                    }
                });
            }
        }

        function getFwQllx() {
            var fwqllx = "";
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/getQllxSxpz",
                type: "GET",
                async: false,
                contentType: 'application/json',
                success: function (data) {
                    if(data != null){
                        $.each(data, function(index,item){
                            var sxpz = item.split(" ");
                            if((sxpz.length === 2 || sxpz.length === 3) && "房屋" === sxpz[0]){
                                fwqllx = sxpz[1];
                            }
                        })
                    }
                }
            });

            // 默认查询房屋权利
            if(isNullOrEmpty(fwqllx)) {
                fwqllx = "4,6,8,24,25,26,94";
            }
            return fwqllx;
        }

        function setTableHeight() {
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

            if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
            }else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 111);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 111 - 17);
            }
        }

        //监听滚动事件
        var scrollTop = 0, tableTop = 0, tableLeft = 0;
        var $nowBtnMore = '';
        //点击表格中的更多
        $('.bdc-table-box').on('click', '.bdc-more-btn', function (event) {
            tableTop = $(this).offset().top;
            tableLeft = $(this).offset().left;
            event.stopPropagation();
            $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
            var $btnMore = $(this).siblings('.bdc-table-btn-more');
            if ($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight) {
                $btnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 50});
            } else {
                $btnMore.css({top: tableTop - scrollTop - $btnMore.height(), left: tableLeft - 50});
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
            $('.bdc-table-top-more-show').hide();
        });

        //监听工具条
        table.on('tool(pageTable)', function (obj) {
            var data = obj.data;
            if (isNullOrEmpty(data.bdcdyh)) {
                warnMsg(" 没有不动产单元信息，无法查看！");
                return;
            }

            if (obj.event === 'djls') {
                window.open("/realestate-inquiry-ui/view/lsgx/lsgx.html?bdcdyh=" + data.bdcdyh);
                saveDetailLog("ZHCXLSGX", "综合查询-查看登记历史", data);
            } else if (obj.event === 'lpb') {
                if (data.bdclx == 2) {
                    // 只有房屋类型允许查看
                    window.open("/building-ui/lpb/view?code=analysis&bdcdyh=" + data.bdcdyh);
                    saveDetailLog("ZHCXLPB", "综合查询-查看楼盘表", data);
                } else {
                    warnMsg("当前不动产单元无楼盘表！");
                }
            } else if (obj.event === 'djb') {
                window.open("/realestate-register-ui/view/djbxx/bdcDjb.html?param=" + data.bdcdyh);
                saveDetailLog("ZHCXDJB", "综合查询-查看登记簿", data);
            } else if (obj.event === 'dady') {
                openBdcDjDa(data);
                saveDetailLog("ZHCXDADY", "综合查询-档案调用", data);
            } else if (obj.event === 'txdw') {
                window.open('/building-ui/omp/redirect/' + data.bdcdyh + '/' + data.bdclx);
                saveDetailLog("ZHCXTXDW", "综合查询-图形定位", data);
            } else if(obj.event === 'djdcb'){
                window.open('/building-ui/djdcb/initview?bdcdyh=' + data.bdcdyh);
                saveDetailLog("ZHCXDJDCB", "综合查询-地籍调查表", data);
            }else if(obj.event === 'hsswdw'){
                window.open('http://10.9.211.101:6060/bdcdyh/index.html#/home?bdcdyh=' + data.bdcdyh);
                saveDetailLog("ZHCXHSSWDW", "综合查询-户室三维不动产定位", data);
            }else if(obj.event === 'zdswdw'){
                window.open('http://10.9.211.101:6060/zd/index.html#/index?djh=' + data.bdcdyh.substring(0,19));
                saveDetailLog("ZHCXZDSWDW", "综合查询-宗地三维不动产定位", data);
            }
        });
    });
});