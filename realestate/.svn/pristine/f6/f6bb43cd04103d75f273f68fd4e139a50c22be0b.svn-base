/**
 * author: <a href="mailto:huangjian@gtmap.cn>huangjian</a>
 * version 1.0.0  2019/10/04
 * describe: 个人住房信息查询
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var gyfsZd = getMulZdList("gyfs");
var fwytZd = getMulZdList("fwyt");

var reverseList = ['zl'];
layui.use(['jquery', 'element', 'form', 'table', 'laydate', 'formSelects'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var formSelects = layui.formSelects;

    form.render();

    // 当前页数据
    var currentPageData = new Array();
    $(function () {
        // 加载Table
        table.render({
            elem: '#bdcdyTable',
            toolbar: '#toolbarDemo',
            title: '个人住房信息列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'xmid', title: '项目编号', width: 200, align: 'center', hide: true},
                {field: 'bdcqzh', minWidth: 280, title: '不动产权证号'},
                {field: 'qlrmc', title: '权利人', width: 200, align: 'center'},
                {field: 'zjh', title: '权利人证件号', width: 250, align: 'center'},
                {
                    field: 'gyfs', minWidth: 100, title: '共有方式',
                    templet: function (d) {
                        return zdDmToMc('gyfs', d.gyfs, gyfsZd)
                    }
                },
                {field: 'zl', minWidth: 350, title: '坐落'},
                {
                    field: 'ghyt', minWidth: 100, title: '房屋用途',
                    templet: function (d) {
                        return zdDmToMc('fwyt', d.ghyt, fwytZd)
                    }
                },
                {field: 'zh', minWidth: 100, title: '幢号'},
                {field: 'fjh', minWidth: 100, title: '房间号'},
                {
                    field: 'djsj', width: 180, title: '登记时间',
                    templet: function (d) {
                        return formatDate(d.djsj);
                    }
                },
                {
                    field: 'qszt', width: 100, title: '权属状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        return formatQszt(d.qszt);
                    }
                }
                // {title: '查看', fixed: 'right', renderer: 'detailView', toolbar: '#barDemo', width: 100}
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
            },
            done: function () {
                setHeight();
                reverseTableCell(reverseList);
            }
        });

        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            search();
        });
        // 回车查询
        $('.required').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        //查询
        function search() {
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });

            if (0 == count) {
                layer.alert("<div style='text-align: center'>请输入查询内容！</div>", {title: '提示'});
                return false;
            }
            // 获取查询参数
            var zjh = $("#zjh").val();
            var zjzl = $("#zjzl").val();
            if(isNotBlank(zjh)){
                if("0" == zjzl){
                    var msg = checkSfzZjh(zjh);
                    if(isNotBlank(msg)){
                        layer.alert("<div style='text-align: center'>"+msg+"</div>", {title: '提示'});
                        return false;
                    }
                }
            }



            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });

            addModel();
            // 重新请求
            table.reload("bdcdyTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/grzf/listGrzfByPage"
                , where: obj
                , page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();
                }
            });
        }

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

    });

    /**
     * 前台的字典转换，代码转换为名称
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zdname 字典名 bdclx
     * @param zdDm 字典代码 1
     * @param zdListName JS中保存字典数据的变量名 默认为zdList*/
    function zdDmToMc(zdname, zdDm, zdListName) {
        try {
            if (zdDm != null) {
                var zdVals;
                if ("gyfs" == zdname) {
                    zdVals = eval(zdListName.gyfs);
                }
                if ("fwyt" == zdname) {
                    zdVals = eval(zdListName.fwyt);
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



