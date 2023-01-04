/**
 * author: <a href="mailto:huangjian@gtmap.cn>huangjian</a>
 * version 1.0.0  2019/05/22
 * describe: 查封单元信息查询
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
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
            title: '查封信息列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'xmid', title: '项目编号', width: 200, align: 'center', hide: true},
                {field: 'zl', minWidth: 250, title: '坐落'},
                {field: 'bdcqzh', minWidth: 280, title: '不动产权证号'},
                {field: 'cfwh', minWidth: 250, title: '查封文号'},
                {field: 'qlr', title: '权利人', width: 200, align: 'center'},
                {field: 'qlrzjh', title: '权利人证件号', width: 250, align: 'center'},
                {field: 'ywr', title: '义务人', width: 200, align: 'center'},
                {field: 'ywrzjh', title: '义务人证件号', width: 250, align: 'center'},
                {
                    field: 'cfsj', width: 180, title: '查封时间',
                    templet: function (d) {
                        return formatDate(d.cfsj);
                    }
                },
                {
                    field: 'djsj', width: 180, title: '登记时间',
                    templet: function (d) {
                        return formatDate(d.djsj);
                    }
                },
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


        // 监听表格操作栏按钮
        table.on('toolbar(bdcdyTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel(checkStatus.data, obj.config.cols[0]);
                    break;
            }
            ;
        });

        //监听工具条
        table.on('tool(bdcdyTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'djb') {
                detailView(data);
            }
        });


        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            search();
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
            var key = formSelects.value('select07', 'val');

            if (0 == count) {
                layer.alert("<div style='text-align: center'>请输入查询内容！</div>", {title: '提示'});
                return false;
            }
            if (0 == key.length) {
                layer.alert("<div style='text-align: center'>请选择查询范围！</div>", {title: '提示'});
                return false;
            }

            // 获取查询参数
            var obj = {};
            var keys = "";
            key.forEach(function (value, i) {
                keys = keys + value + ","
            });
            keys = keys.substr(0, keys.length - 1)
            $(".cxtj").each(function (i) {
                var name = $(this).attr('name');
                obj[name] = $(this).val();
                obj['keys'] = keys;
                obj['index'] = "bdc-cf";

            });

            addModel();
            // 重新请求
            table.reload("bdcdyTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/es/cf"
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

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(bdcdyTable)', function (obj) {
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;


            $.each(data, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedData', {
                        key: v.xmid, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedData', {
                        key: v.xmid, remove: true
                    });
                }
            });
        });

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
            if (zdDm) {
                /* if (!zdListName) {
                     zdListName = "zdList"
                 }*/
                var zdVals = eval(zdListName.bdclx);
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


    function detailView(data) {
        window.open('/realestate-register-ui/view/djbxx/bdcDjb.html?param=' + data.bdcdyh);

    }

    // 登记簿查看
    window.viewBdcDjb = function (zsid, zslx) {
        if (isNullOrEmpty(zsid) || isNullOrEmpty(zslx)) {
            return;
        }

        if ("1" == zslx) {
            window.open("/realestate-register-ui/view/zsxx/bdcZs.html?zsid=" + zsid + "&readOnly=false");
        } else if ("2" == zslx) {
            window.open("/realestate-register-ui/view/zsxx/bdcZm.html?zsid=" + zsid + "&readOnly=false");
        }
    }

});



