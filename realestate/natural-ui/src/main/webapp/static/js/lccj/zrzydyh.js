// layui.config({
//     base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
// });
//需要翻转的字段
var currreverseList = [];
var processDefKey = getQueryString("processDefKey");
var processDefMc = getQueryString("processDefMc");

layui.use(['jquery', 'element', 'form', 'table', 'laydate', 'layer'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var layer = layui.layer;
    var laydate = layui.laydate;

    form.render();


    $(function () {

        var isSearch = true;
        //监听回车事件
        $(document).keydown(function (event) {
            if (event.keyCode == 13 && isSearch) { //绑定回车
                $(".bdc-search-box #search").click();
            }
        });
        //判断回车操作
        $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });


        // 加载Table
        table.render({
            elem: '#zrzydyhTable',
            id: 'zrzydyhTableId',
            toolbar: '#toolbarDemo',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'zrzydjdyh', title: '自然资源登记单元号', minWidth: 80, templet: '#bdcdyhTpl' },
                {field: 'zl', title: '坐落', minWidth: 80},
                {field: 'ysdm', title: '要素代码', minWidth: 250},
                {field: 'mc', title: '登记单元名称', minWidth: 250},
                {field: 'djdyzmj', title: '登记单元总面积', minWidth: 250},
                // {field: 'cz', title: '操作', width: 200, fixed: 'right', renderer: 'caozuo', toolbar: '#czBar'}
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                // 设置选中行
                if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.zrzydjdyh) {
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
                //无数据时显示内容调整
                if ($('.layui-show .layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
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

            addModel();
            // 重新请求
            table.reload("zrzydyhTableId", {
                url: "/realestate-natural-ui/rest/v1.0/dyh/list"
                , where: obj
                , page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                , done: function (res, curr, count) {
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

        // 监听表格操作栏按钮
        table.on('toolbar(zrzydyhTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                //创建
                case 'createXm':
                    checkData = checkStatus.data;
                    if (checkData !== null && checkData.length > 0) {
                        if (checkData.length > 1){
                            layer.alert("只能选择一条数据!");
                            break;
                        }
                        addModel();
                        checkZrzyData(checkData);
                        //
                    } else {
                        layer.alert("未选择数据!");
                    }
                    break;
            }
        });
    });

    // 设置列表高度
    function setHeight(searchHeight) {
        if (isNullOrEmpty(searchHeight)) {
            searchHeight = 131;
        }
        $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

        if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
            $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
        } else {
            $('.layui-table-body').height($('.bdc-table-box').height() - searchHeight);
            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - searchHeight - 17);
        }
    }


    //规则校验
    function checkZrzyData(checkData) {
        var selectDataList = [];
        for (var i = 0; i < checkData.length; i++) {
            var selectData = checkData[i];
            var bdcGzYzDTO = {};
            bdcGzYzDTO.zrzydjdyh = selectData.zrzydjdyh;
            selectDataList.push(bdcGzYzDTO);
        }
        var bdcGzYzQO = {};
        bdcGzYzQO.zhbs = processDefKey + "_ZRZY";
        bdcGzYzQO.paramList = selectDataList;
        gzyzCommon(1, bdcGzYzQO, function () {
            //如果规则校验没有问题则执行创建逻辑
            setTimeout(function () {
                try {
                    $.when(cshXm(checkData)).done(function () {
                        removeModal();

                        setTimeout(function() {
                            showAlertDialog("创建成功");
                        }, 1000);

                        // 关闭本页面
                        window.close();
                    })
                } catch (e) {
                    removeModal();
                    if (e.message) {
                        showAlertDialog(e.message);
                    } else {
                        delAjaxErrorMsg(e);
                    }
                }
            }, 10);
        });
    }

    //项目初始化
    function cshXm(checkData) {
        var zrzyCshDTO = {};
        zrzyCshDTO.gzldyid = processDefKey;
        zrzyCshDTO.gzldymc = processDefMc;
        var zrzyCshYwxxDTOList = [];
        for (var i = 0; i < checkData.length; i++) {
            var selectData = checkData[i];
            var zrzyCshYwxxDTO = {};
            zrzyCshYwxxDTO.zrzydjdyh = selectData.zrzydjdyh;
            zrzyCshYwxxDTOList.push(zrzyCshYwxxDTO);
        }
        if (zrzyCshYwxxDTOList.length > 0) {
            zrzyCshDTO.zrzyCshYwxxDTOList = zrzyCshYwxxDTOList;
            $.ajax({
                url: "/realestate-natural-ui/rest/v1.0/ywxx/csh",
                type: "POST",
                async: false,
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(zrzyCshDTO),
                success: function (data) {
                    debugger;
                    lcCsh(data);
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);
                }
            });
        }
    }


    /*
* 正常流程创建
* */
    function lcCsh(taskId) {
        removeModal();
        var portalUrl = "/realestate-portal-ui/view/zrzy-new-page.html?taskId=" + taskId;
        window.open(portalUrl);
    }

    //监听单元号点击
    $('.bdc-table-box').on('click', '.bdc-bdcdyh-click', function () {
        addModel();
        var data = [];
        data.push($(this).data('json'));
        checkZrzyData(data);
    });


});


