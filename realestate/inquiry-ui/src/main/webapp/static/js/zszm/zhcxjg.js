/**
 * 综合查询结果日志展示JS
 */
var zdList = getMulZdList("fwyt");
layui.use(['table','laytpl','layer'],function () {
    var table = layui.table,
        $ = layui.jquery;

    $(function () {
        // 日志记录ID
        var id = $.getUrlParam('id');
        // 本地综合查询日志区分
        var xtcx = $.getUrlParam('xtcx');
        var data = [];
        if(xtcx){
            $.ajax({
                url: "/realestate-inquiry-ui/log/xtcxdata/" + id + "/cxjg",
                type: 'GET',
                async: false,
                dataType: "text",
                success: function (res) {
                    if(res){
                        data = JSON.parse(res);
                    }
                }
            });
        }else{
            $.ajax({
                url: "/realestate-inquiry-ui/log/data/" + id + "/cxjg",
                type: 'GET',
                async: false,
                dataType: "text",
                success: function (res) {
                    if(res){
                        data = JSON.parse(res);
                    }
                }
            });
        }

        var reverseList = ['zl'];
        table.render({
            id: 'pageTable',
            elem: '#pageTable',
            title: '查询结果列表',
            defaultToolbar: ['filter'],
            even: true,
            cols: [[
                {field: 'xmid', width: 120, title: '项目id', hide: true},
                {field: 'ytdzh', width: 200, title: '原土地证号', hide: true},
                {field: 'ybdcdyh', width: 200, title: '原不动产单元号', hide: true},
                {field: 'bdcqzh', width: 280, title: '不动产权证号（明）', sort: false},
                {field: 'zl', minWidth: 300, title: '坐落', sort: false,},
                {field: 'qlrmc', width: 250, title: '权利人名称', sort: false,},
                {field: 'qlrzjh', width: 250, title: '权利人证件号', sort: false,},
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
                {field: 'djyy', width: 200, title: '登记原因', sort: false,},
                {field: 'ghyt', title: '规划用途', minWidth: 150,
                    templet: function (d) {
                        return zdDmToMc('fwyt', d.ghyt, zdList);
                    }
                },
                {field: 'ywrmc', width: 250, title: '义务人名称', sort: false,},
                {field: 'ywrzjh', width: 250, title: '义务人证件号', sort: false,},
                {field: 'slbh', width: 150, title: '受理编号', sort: false},
                {field: 'fwbh', width: 130, title: '房屋编号', sort: false},
                {field: 'zhlsh', width: 150, title: '证号流水号', sort: false},
                {field: 'qszt', width: 100, title: '权属状态', fixed: 'right', sort: false,
                    templet: function (d) {
                        return formatQszt(d.qszt);
                    }
                },
                {field: 'bdcdyZtDTO', width: 100, title: '限制状态', fixed: 'right', sort: false,
                    templet: function (d) {
                        return formatXzzt(d.bdcdyZtDTO);
                    }
                },
                {field: 'ajzt', width: 100, title: '办理状态', fixed: 'right', sort: false,hide: true,
                    templet: function (d) {
                        return formatAjzt(d.ajzt);
                    }
                }
            ]],
            data: data,
            page: false,
            done: function () {
                reverseTableCell(reverseList);
            }
        });

        /**
         * 前台的字典转换，代码转换为名称
         */
        function zdDmToMc(zdname, zdDm, zdListName) {
            try {
                if (zdDm) {
                    /* if (!zdListName) {
                         zdListName = "zdList"
                     }*/
                    var zdVals;
                    if ("fwyt" == zdname) {
                        zdVals = eval(zdListName.fwyt);
                    }
                    if ("bdclx" == zdname) {
                        zdVals = eval(zdListName.bdclx);
                    }
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
});

window.setData = function (data) {
    layui.form.val('searchform', JSON.parse(data));
}