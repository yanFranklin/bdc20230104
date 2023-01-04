/**
 * author: <a href="mailto:huangjian@gtmap.cn>huangjian</a>
 * version 1.0.0  2020/03/16
 * describe: 抵押单元信息查询-蚌埠版
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

// 用户IP地址
var ipaddress;
// var zdList = getMulZdList("bdclx");
var reverseList = ['zl'];
// 分页重置查询参数
var searchParam =[];
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
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');
        // 加载Table
        table.render({
            elem: '#bdcdyTable',
            toolbar: '#toolbarDemo',
            title: '抵押信息列表',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'xmid', title: '项目编号', width: 200, align: 'center', hide: true},
                {field: 'bdcqzh', minWidth: 280, title: '抵押证明号'},
                {field: 'ycqzh', minWidth: 280, title: '原产权证号'},
                {field: 'bdcdyh', minWidth: 280, title: '不动单元号'},

                {field: 'zwlxqssj', minWidth: 180, title: '债务履行起始时间'},
                {field: 'zwlxjssj', minWidth: 180, title: '债务履行结束时间'},
                {field: 'bdbzzqse', minWidth: 180, title: '被担保主债权数额'},

                {field: 'zl', minWidth: 250, title: '坐落'},
                {field: 'qlr', title: '权利人', width: 250, align: 'center'},
                {field: 'qlrzjh', title: '权利人证件号', width: 250, align: 'center'},
                {field: 'ywr', title: '义务人', width: 200, align: 'center'},
                {field: 'ywrzjh', title: '义务人证件号', width: 250, align: 'center'},
                {field: 'djjg', title: '登记机构', width: 150, align: 'center'},
                {field: 'dbr', title: '登簿人', width: 150, align: 'center'},

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
                },

                {title: '查看', fixed: 'right', renderer: 'detailView', toolbar: '#barDemo', width: 100}
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
                // 控制按钮权限
                if(moduleCode){
                    setElementAttrByModuleAuthority(moduleCode);
                }
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
            var key = formSelects.value('select07', 'val');

            if (0 == count) {
                layer.alert("<div style='text-align: center'>请输入必要查询条件！</div>", {title: '提示'});
                return false;
            }

            // 获取查询参数
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });

            searchParam = obj;
            obj.moduleCode=moduleCode;
            addModel();
            // 重新请求
            table.reload("bdcdyTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/dya/bengbu/listDyaByPage"
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
                   // saveDetailLog("DYCX","抵押查询",obj);
                }
            });
        }


        //监听工具条
        table.on('tool(bdcdyTable)', function (obj) {
            var data = obj.data;
            if (isNullOrEmpty(data.bdcdyh)) {
                warnMsg(" 没有不动产单元信息，无法查看！");
                return;
            }

            if (obj.event === 'xq') {
                window.open(htmlConfig.qlxxHtml.dyaqHtml+'?xmid='+data.xmid +'&readOnly=true&isCrossOri=false');
                saveDetailLog("DYCX_CKDJB","抵押查询_查看详情", data)
            }
        });

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

    });

    // 监听表格操作栏按钮
    table.on('toolbar(bdcdyTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'exportExcel':
                if (searchParam.length == 0){
                    // layer.alert("请先查询数据！", {title: '提示'});
                    warnMsg("请选择需要导出Excel的数据行！");
                    return
                }
                if (checkStatus.data.length  > 0) {
                    exportExcel(checkStatus.data, obj.config.cols[0]);
                } else {
                    exportAllExcel(checkStatus.data, obj.config);
                }
                break;
        }
    });

    // 导出Excel
    // 通过隐藏form提交方式，避免ajax方式不支持下载文件
    function exportExcel(d, cols) {
        if ($.isEmptyObject(d)) {
            warnMsg("请选择需要导出Excel的数据行！");
            return;
        }

        // 标题
        var showColsTitle = new Array();
        // 列内容
        var showColsField = new Array();
        // 列宽
        var showColsWidth = new Array();
        for (var index in cols) {
            if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
                showColsTitle.push(cols[index].title);
                showColsField.push(cols[index].field);
                if (isNullOrEmpty(cols[index].width)) {
                    showColsWidth.push(30);
                } else {
                    showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                }
            }
        }

        // 设置Excel基本信息
        $("#fileName").val('抵押查询信息导出Excel表');
        $("#sheetName").val('抵押查询信息导出Excel表');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);

        var data = new Array();
        $.each(d, function (key, value) {
            data.push(value);
        })
        for (var i = 0; i < data.length; i++) {
            data[i].xh = i + 1;
            data[i].bdcdyZtDTO = formatXzzt(data[i].bdcdyZtDTO).replace(/<span\s*[^>]*>(.*?)<\/span>/ig,"$1");
            data[i].qszt = formatQszt(data[i].qszt).replace(/<span\s*[^>]*>(.*?)<\/span>/ig,"$1");
            data[i].djsj = formatDate(data[i].djsj);
        }
        $("#data").val(JSON.stringify(data));
        saveDetailLog("DYCX_EXPORT","抵押查询-导出excel",{"DCNR":data});
        $("#form").submit();
    }

    // 导出所有查询结果Excel
    function exportAllExcel(data, obj) {
        layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要导出Excel的数据行!');
        // layer.confirm("是否导出全部数据？", {
        //     title: "提示",
        //     btn: ["确认", "取消"],
        //     btn1: function (index) {
        //         var cols = obj.cols[0]
        //         var url = obj.url;
        //         var paramData = obj.where;
        //         paramData["type"] = "export";
        //
        //         $.ajax({
        //             url: url,
        //             dataType: "json",
        //             data: paramData,
        //             success: function (data) {
        //                 obj.where.type = "";
        //                 if(data.length > 0){//查询成功
        //                     exportExcel(data,cols);
        //                 }
        //             }
        //         });
        //
        //         layer.close(index);
        //     },
        //     btn2: function (index) {
        //         obj.where.type = "";
        //         return;
        //     }
        // });
    }

    // 保存记录操作信息
    function saveDetailLog(logType, viewTypeName, data){
        var json = JSON.parse(JSON.stringify(data));
        json.logType = logType;
        json.ipaddress = ipaddress;
        json.viewTypeName = viewTypeName;
        saveLogInfo(json);
    }

});



