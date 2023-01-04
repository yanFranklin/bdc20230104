/**
    南通房产证明（其他证明）列表JS
 */
var reverseList = ['zl'];
// 获取房屋用途字段
var zdList = getMulZdList("fwyt");
// 打印模板名称
var zmmb = getIP() + "/realestate-inquiry-ui/static/printModel/";
// 配置项
var zhcxpz;

layui.use(['table','laytpl','layer','form'],function () {
    var table = layui.table,
        $ = layui.jquery,
        form = layui.form;

    $(function () {
        // 打印的证明类型
        var type = $.getUrlParam('type');

        // 获取配置项
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zhcx/pz",
            type: "GET",
            dataType: 'json',
            async: false,
            success: function (res) {
                if(res){
                    zhcxpz = res;
                }else{
                    failMsg("发现页面未配置正确，请联系管理员解决！");
                }
            },
            error: function () {
                failMsg("发现页面未配置正确，请联系管理员解决！");
            }
        });

        // 获取当前登录用户
        var userName = "";
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zhcx/nantong/fczm/userinfo",
            type: "GET",
            dataType: "text",
            success: function (data) {
                userName = data;
            }
        });

        // 查询参数
        var cxxxCookie = $.cookie('cxxx');
        if(cxxxCookie){
            var cxxx = JSON.parse(cxxxCookie);
            form.val('searchform', cxxx);
        }

        // 数据内容
        var fczmListData = layui.data('fczmListData');
        var data = JSON.parse(fczmListData.data);
        if(!data){
            warnMsg("未指定打印证明数据！");
        }

        table.render({
            elem: '#fczmTable',
            id: 'fczmTable',
            toolbar: '#toolbarDemo',
            title: '房产证明数据列表',
            defaultToolbar: ['filter'],
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'xmid',     width: 120, title: '项目id', hide: true},
                {field: 'ytdzh',    width: 200, title: '原土地证号', hide: true},
                {field: 'ybdcdyh',  width: 200, title: '原不动产单元号', hide: true},
                {field: 'bdcqzh',   width: 280, title: '不动产权证号（明）', sort: false},
                {field: 'zl',    minWidth: 300, title: '坐落', sort: false},
                {field: 'qlrmc',    width: 250, title: '权利人名称', sort: false},
                {field: 'qlrzjh',   width: 250, title: '权利人证件号', sort: false},
                {field: 'bdcdyh', width: 280, title: '不动产单元号', sort: false,
                    templet: function (d) {
                        return formatBdcdyh(d.bdcdyh);
                    }
                },
                {field: 'djsj', width: 180, title: '登簿时间', hide: true, sort: false,
                    templet: function (d) {
                        return formatDate(d.djsj);
                    }
                },
                {field: 'djyy', width: 200, title: '登记原因', sort: false},
                {field: 'ghyt', title: '规划用途', minWidth: 150,
                    templet: function (d) {
                        return zdDmToMc('fwyt', d.ghyt, zdList);
                    }
                },
                {field: 'ywrmc', width: 250, title: '义务人名称', sort: false},
                {field: 'ywrzjh', width: 250, title: '义务人证件号', sort: false},
                {field: 'slbh', width: 150, title: '受理编号', sort: false},
                {field: 'fwbh', width: 250, title: '房屋编号', sort: false},
                {field: 'zhlsh', width: 150, title: '证号流水号', sort: false}
            ]],
            data: data,
            page: true,
            done: function () {
                setHeight();
                reverseTableCell(reverseList);
            }
        });

        //头工具栏事件
        table.on('toolbar(fczmTable)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'edit':
                    edit(checkStatus.data, obj.config.cols[0]);
                    break;
                case 'print':
                    printzm(checkStatus.data, obj.config.cols[0]);
                    break;
            }
        });

        function edit(data, cols) {
            if(!data || data.length != 1){
                warnMsg(" 请选择一条需要编辑的的数据行！");
                return;
            }

            var zmxx = data[0];
            zmxx.cxsqr = $("#qlrmc").val();
            zmxx.qlrmc = $("#qlrmc").val();
            zmxx.sfzh = $("#qlrzjh").val();
            zmxx.qlrzjh = $("#qlrzjh").val();
            $.cookie('fczm', JSON.stringify(zmxx));

            window.open("fczm.html?pageType=2&type=" + type);
        }

        function printzm() {
            var fczmListData = layui.data('fczmListData');
            var data = JSON.parse(fczmListData.data);
            if(!data){
                warnMsg("无缓存数据，无法打印！");
                return;
            }


            var printData = new Array();
            var zmbh = new Array();
            $.each(data, function(index,item) {
                var printItem = {};
                printItem.bh = item.bh;
                printItem.cxsqr = item.cxsqr;
                printItem.sfzh = item.sfzh;
                printItem.cxsd = item.cxsd;
                printItem.zmnr = item.zmnr;
                printItem.zmsj = item.zmsj;
                printItem.jbr = userName;
                printData.push(printItem);

                zmbh.push(item.bh);
            });

            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/print/nantong/fcda/pl",
                type: "POST",
                data: JSON.stringify(printData),
                contentType: 'application/json',
                dataType: "text",
                success: function (key) {
                    if (key) {
                        // 获取证明模板名称
                        var zmmbmc = getZmmbmc(type);

                        var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/nt/fczm/pl/" + key + "/xml";
                        // 请求打印
                        print(zmmbmc, dataUrl, false);
                        // 保存打印日志
                        savePrintInfo(zmmbmc, dataUrl, {'zmbh': zmbh.join("、"), "printType": getTpyeName(type),"zl": cxxx.zl,
                            "qlr": cxxx.qlrmc});
                    } else {
                        failMsg("打印失败，请重试！");
                    }
                },
                error: function () {
                    failMsg("打印失败，请重试！");
                }
            });
        }

        //监听滚动事件
        var scrollTop = 0,
            scrollLeft = 0;
        $(window).on('scroll',function () {
            scrollTop = $(this).scrollTop();
            scrollLeft = $(this).scrollLeft();
        });
    });

    // 获取打印证明类型名称
    function getTpyeName(type){
        switch (type) {
            case "bwsqrsyzm":   return "不为申请人所有证明";
            case "ycszm":       return "已出售证明";
            case "wbdcdjzlzm":  return "无不动产登记资料证明";
            case "dkzm":        return "贷款证明";
            case "zlbgzmyzl":   return "座落变更证明（原座落）";
            case "zlbgzmxzl":   return "座落变更证明（现座落）";
            case "qtzm":        return "其他证明";
        }
    }

    // 获取证明模板名称
    function getZmmbmc(type) {
        switch (type) {
            case "bwsqrsyzm":   return zmmb + zhcxpz.zhcxzmmbBwsqrsyzm;
            case "ycszm":       return zmmb + zhcxpz.zhcxzmmbYcszm;
            case "wbdcdjzlzm":  return zmmb + zhcxpz.zhcxzmmbWbdcdjzlzm;
            case "dkzm":        return zmmb + zhcxpz.zhcxzmmbDkzm;
            case "zlbgzmyzl":   return zmmb + zhcxpz.zhcxzmmbZlbgzmyzl;
            case "zlbgzmxzl":   return zmmb + zhcxpz.zhcxzmmbZlbgzmxzl;
            case "qtzm":        return zmmb + zhcxpz.zhcxzmmbQtzm;
        }
    }

    /**
     * 前台的字典转换，代码转换为名称
     */
    function zdDmToMc(zdname, zdDm, zdListName) {
        try {
            if (zdDm) {
                var zdVals = eval(zdListName.fwyt);
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
            return '';
        } catch (e) {
            return "";
        }
    }
});