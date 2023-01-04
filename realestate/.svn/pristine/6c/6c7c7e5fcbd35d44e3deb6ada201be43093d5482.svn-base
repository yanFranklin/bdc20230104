/**
 * author: <a href="mailto:huangjian@gtmap.cn>huangjian</a>
 * version 1.0.0  2019/05/22
 * describe: 证书证明统计查询-列表形式展示
 */
// var zdList = getMulZdList("zslx");
var reverseList = ['zl'];
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var tpl = $("#DmMcTpl").html();
   /* if (zdList) {
        $.each(zdList, function (key, value) {
            laytpl(tpl).render(value, function (html) {
                $("." + key).append(html);
            });
        });
    } else {
        laytpl(tpl).render(value, function (html) {
            $("").append(html);
        });
    }*/
    form.render();

    // 当前页数据
    var currentPageData = new Array();

    // 日期控件
    layui.laydate.render({
        elem: '#kssj',
        type: 'date',
        format: 'yyyy-MM-dd'
    });
    layui.laydate.render({
        elem: '#jzsj',
        type: 'date',
        format: 'yyyy-MM-dd'
    });

    //判断查询起始时间与结束时间关系
    laydate.render({
        elem: '#kssj',
        type: 'date',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#jzsj').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({ //结束时间
        elem: '#jzsj',
        type: 'date',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date($('#kssj').val()).getTime();
            var endTime = new Date(value).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });

    $.ajax({
        url: '/realestate-inquiry-ui/bdczd/bm/list',
        type: "GET",
        async: false,
        dataType: "json",
        timeout: 10000,
        success: function (data) {
            if (data) {
                $.each(data, function (key) {
                    $('#djjg').append('<option value="' + data[key] + '">' + data[key] + '</option>');
                });
                form.render('select');
            }
        }
    });

    // 下拉选择添加删除按钮
    renderSelectClose(form);

    // 当前页数据
    var currentPageData = new Array();
    $(function () {
        // 加载Table
        table.render({
            elem: '#bdcdyTable',
            toolbar: '#toolbarDemo',
            title: '',
            defaultToolbar: ['filter'],
            /*request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },*/
            totalRow: true,
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'xh', title: '序号', width: 60, hide:true},
                {field: 'number', type: 'numbers', width: 60, title: '序号'},
                {field: 'djjg', title: '登记机构', width: 400, align: 'center', totalRowText: '合计'},
                /*{
                    field: 'zslx', width: 200, title: '证书类型',
                    templet: function (d) {
                        return formatZslx(d.zslx);
                    }
                },*/
                {field: 'djsj', title: '日期', width: 60, hide:true},
                {field: 'zssl', width: 200, title: '证书数量', align: 'center', totalRow: true},
                {field: 'zmsl', width: 200, title: '证明数量', align: 'center', totalRow: true},
               /* {field: 'zongshu', width: 200, title: '总数', align: 'center',
                    templet: function (d) {
                    var zs;
                    var zm;
                    if(isNullOrEmpty(d.zssl)){
                        zs = 0;
                    } else {
                        zs = d.zssl;
                    }
                    if(isNullOrEmpty(d.zmsl)){
                        zm = 0;
                    }else {
                        zm = d.zmsl;
                    }
                    return Number(zs)+Number(zm);

                }},*/

            ]],
            data: [],
            // page: true,
            parseData: function (res) {
                // 设置选中行
               /* if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.xh) {
                                v.LAY_CHECKED = true;
                            }
                        })
                    });
                }*/
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    // count: res.totalElements, //解析数据长度
                    data: res.data //解析数据列表
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
        });

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(bdcdyTable)', function (obj) {
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;


            $.each(data, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedData', {
                        key: v.xh, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedData', {
                        key: v.xh, remove: true
                    });
                }
            });
        });

        // 导出Excel
        // 通过隐藏form提交方式，避免ajax方式不支持下载文件
        function exportExcel(data, cols) {
            var checkedData = layui.sessionData('checkedData');
            if ($.isEmptyObject(checkedData)) {
                layer.alert("<div style='text-align: center'>请选择需要导出Excel的数据行！</div>", {title: '提示'});
                return;
            }

            // 查询要导出的数据
            var selectData = new Array();
            $.each(checkedData, function (index, item) {
                // 获取查询参数
                var obj = {};
                $(".cxtj").each(function (i) {
                    var name = $(this).attr('name');
                    obj[name] = $(this).val();
                });
                obj['djjg'] = item.djjg;

                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/zszm/zszmCount/excel",
                    type: 'GET',
                    data: obj,
                    async: false,
                    success: function (data) {
                        $.each(data, function (key, value) {
                            selectData.push(value);
                        });
                    },
                    error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });
            });

            // 标题
            var showColsTitle = new Array();
            // 列内容
            var showColsField = new Array();
            // 列宽
            var showColsWidth = new Array();
            for (var index in cols) {
                if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
                    showColsTitle.push(cols[index].title);
                }
                if ('djsj' == cols[index].field){
                    showColsTitle.push(cols[index].title);
                }
                if ('xh' != cols[index].field && cols[index].type != 'checkbox') {
                    showColsField.push(cols[index].field);
                    showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                }
                if ('djjg' == cols[index].field) {
                    showColsWidth.push(60);
                }


            }

            // 设置Excel基本信息
            $("#fileName").val('证明证书');
            $("#sheetName").val('统计表');
            $("#cellTitle").val(showColsTitle);
            $("#cellWidth").val(showColsWidth);
            $("#cellKey").val(showColsField);
            var data = new Array();
            var zssl = 0;
            var zmsl = 0;
            $.each(selectData, function (key, value) {
                value.djsj = strFormatDate(value.djsj).Format('yyyy年MM月dd日');
                zssl += value.zssl;
                zmsl += value.zmsl;
                data.push(value);
            })
            var objHj = new Object();
            objHj.djjg = '合计';
            objHj.zssl = zssl;
            objHj.zmsl = zmsl;
            data.push(objHj);
            for (var i = 0; i < data.length - 1; i++) {
                data[i].number = i + 1;
                data[i].zslx = formatZslx(data[i].zslx);
            }
            $("#data").val(JSON.stringify(data));
            $("#form").submit();
        };

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
            var startDate = new Date($('#kssj').val()).getTime();
            var endTime = new Date($('#jzsj').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                return false;
            }

            if (isNullOrEmpty($('#kssj').val()) || isNullOrEmpty($('#jzsj').val())){
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">必须输入开始时间和结束时间');
                return false;
            }

            $('#kssjhid').val($('#kssj').val());
            $('#jzsjhid').val($('#jzsj').val());
            $('#zslxhid').val($('#zslx').val());

            // 获取查询参数
            var obj = {};
            $(".cxtj").each(function (i) {
                var name = $(this).attr('name');
                obj[name] = $(this).val();
            });

            addModel();
            // 重新请求
            table.reload("bdcdyTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/zszm/zszmCountList",
                where: obj,
               done: function (res) {
                    currentPageData = res.data;
                    removeModal();
                    if(res === "error") {
                        var msg = "存在登记机构或证书类型为空的数据！请检查";
                        layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
                    }else{
                        var tableView = this.elem.next();
                        var totalRow = tableView.find('.layui-table-total');
                        layui.each(totalRow.find('td'), function (index, tdElem) {
                            tdElem = $(tdElem);
                            var text = tdElem.text();
                            if (text && !isNaN(text)) {
                                tdElem.find('div.layui-table-cell').html(parseFloat(text));
                            }
                        });
                    }
                    setHeight();
                },
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



});



