/**
 * author: <a href="mailto:huangjian@gtmap.cn>huangjian</a>
 * version 1.0.0  2019/05/22
 * describe: 已登记不动产单元查询
 */
var zdList = getMulZdList("bdclx");
var reverseList = ['zl'];
// 用户IP地址
var ipaddress;
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var dataUrl = '/realestate-inquiry-ui/rest/v1.0/bdcdyxx/listbypage';
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var tpl = $("#DmMcTpl").html();
    if (zdList) {
        $.each(zdList, function (key, value) {
            laytpl(tpl).render(value, function (html) {
                $("." + key).append(html);
            });
        });
    } else {
        laytpl(tpl).render(value, function (html) {
            $("").append(html);
        });
    }
    form.render();

    //判断查询起始时间与结束时间关系
    laydate.render({
        elem: '#djqsrq',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#djjsrq').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({ //结束时间
        elem: '#djjsrq',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date($('#djqsrq').val()).getTime();
            var endTime = new Date(value).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });

    // 下拉选择添加删除按钮
    renderSelectClose(form);

    // 当前页数据
    var currentPageData = new Array();
    $(function () {

        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');
        // 加载Table
        table.render({
            elem: '#bdcdyTable',
            toolbar: '#toolbarDemo',
            title: '已登记不动产单元列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {
                    field: 'bdclx', width: 150, title: '不动产类型',
                    templet: function (d) {
                        return zdDmToMc('bdclx', d.bdclx, zdList);
                    }
                },
                {
                    field: 'bdcdyh', width: 280, title: '不动产单元号',
                    templet: function (d) {
                        return formatBdcdyh(d.bdcdyh);
                    }
                },
                {field: 'qlrmc', title: '权利人', width: 300, align: 'center'},
                // {field: 'djyy', title: '登记原因', width: 150, align: 'center'},
                {field: 'zl', minWidth: 250, title: '坐落'},
                {
                    field: 'qszt', width: 100, title: '权属状态', fixed: 'right',
                    templet: function (d) {
                        return formatQszt(d.qszt);
                    }
                },
                {
                    field: 'bdcdyZtDTO', width: 100, title: '限制状态', fixed: 'right',
                    templet: function (d) {
                        return formatXzzt(d.bdcdyZtDTO);
                    }
                },
                {title: '查看', fixed: 'right', renderer: 'detailView', toolbar: '#barDemo', width: 100}
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                // 设置选中行
                if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.xmid) {
                                v.LAY_CHECKED = true;
                            }
                        })
                    });
                }
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
                if(moduleCode){
                    setElementAttrByModuleAuthority(moduleCode);
                }
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

        // 导出Excel
        // 通过隐藏form提交方式，避免ajax方式不支持下载文件
        function exportExcel(data, cols) {
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                layer.alert("<div style='text-align: center'>请选择需要导出Excel的数据行！</div>", {title: '提示'});
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

                    if ('bdcdyZtDTO' == cols[index].field) {
                        showColsField.push('xzzt');
                    } else {
                        showColsField.push(cols[index].field);
                    }

                    if ('zl' == cols[index].field) {
                        showColsWidth.push(60);
                    } else {
                        showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                    }
                }
            }

            // 设置Excel基本信息
            $("#fileName").val('已登记不动产单元查询');
            $("#sheetName").val('统计表');
            $("#cellTitle").val(showColsTitle);
            $("#cellWidth").val(showColsWidth);
            $("#cellKey").val(showColsField);
            var data = new Array();
            $.each(checkedData, function (key, value) {
                data.push(value);
            })
            for (var i = 0; i < data.length; i++) {
                data[i].xh = i + 1;
                data[i].bdclx = zdDmToMc('bdclx', data[i].bdclx, zdList);
                data[i].qszt = formatQsztOfData(data[i].qszt);
                data[i].xzzt = formatXzztOfData(data[i].bdcdyZtDTO);
            }
            $("#data").val(JSON.stringify(data));
            saveDetailLog("BDCDYCX_EXPORT", "不动产单元查询-导出excel",{"DCNR":data})
            $("#form").submit();
        }

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
            // 每次查询清除下导出缓存数据
            layui.sessionData("checkedData", null);
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });
            if (0 == count) {
                layer.alert("<div style='text-align: center'>请输入必要查询条件！</div>", {title: '提示'});
                return false;
            }
            var startDate = new Date($('#djqsrq').val()).getTime();
            var endTime = new Date($('#djjsrq').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                return false;
            }

            // 获取查询参数
            var obj = {};
            $(".cxtj").each(function (i) {
                var name = $(this).attr('name');
                obj[name] = $(this).val();
            });

            addModel();
            // 重新请求
            table.reload("bdcdyTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/bdcdyxx/listbypage"
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
                    if(moduleCode){
                        setElementAttrByModuleAuthority(moduleCode);
                    }
                   // saveDetailLog("BDCDYCX","不动产单元查询",obj);
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
        saveDetailLog("BDCDYCX_CKDJB", "不动产单元查询-查看登记簿",data);
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


    // 保存记录操作信息
    function saveDetailLog(logType, viewTypeName, data){
        var json = JSON.parse(JSON.stringify(data));
        json.logType = logType;
        json.ipaddress = ipaddress;
        json.viewTypeName = viewTypeName;
        saveLogInfo(json);
    }
});



