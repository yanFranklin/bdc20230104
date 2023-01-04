//需要翻转的字段
var currreverseList = ['ZL'];
var searchParam = {};
layui.use(['jquery', 'element', 'form', 'table', 'laydate', 'layer'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var layer = layui.layer;
    var laydate = layui.laydate;

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
            elem: '#tdsqTable',
            id: 'tdsqTableId',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            even: true,
            cols: [[
                {field: 'bdcqzh', title: '不动产权证（明）号', minWidth: 200,},
                {field: 'zl', title: '坐落', minWidth: 150},
                {field: 'qlr', title: '权利人', minWidth: 80},
                {field: 'cz', title:'操作', width: 200, fixed: 'right', renderer: 'caozuo', toolbar: '#czBar'}
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                if(isNotBlank(res)){
                    return {
                        code: res.code, //解析接口状态
                        msg: res.msg, //解析提示文本
                        count: res.totalElements, //解析数据长度
                        data: res.content //解析数据列表
                    }
                }else{
                    return {
                        code: 0, //解析接口状态
                        msg: "成功", //解析提示文本
                        count: 0, //解析数据长度
                        data: [] //解析数据列表
                    }
                }
            },
            done: function () {
                setHeight();
                reverseTableCell(currreverseList,"tdsqTableId");
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

        $('.bdc-table-box').on('mouseenter', 'td', function () {
            if ($(this).text() && currreverseList.indexOf($(this).attr("data-field")) !== -1) {
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
            }
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                }, 20);
            });
        });

        //监听删除图标点击
        $('.xzq-sele .layui-icon-close').on('click', function (item) {
            $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
            $(this).hide();
            form.render("select");
            resetSelectDisabledCss();
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

            searchParam = obj;
            addModel();
            // 重新请求
            table.reload("tdsqTableId", {
                url:  "/realestate-etl/td/page"
                , where: obj
                , page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                , done: function (res, curr, count) {
                    reverseTableCell(currreverseList,"tdsqTableId");
                    removeModal();
                    setHeight();
                }
            });
        }

        //监听工具条
        table.on('tool(tdsqTable)', function (obj) {
            var data = obj.data;
            if(obj.event ==="importData") {
                importData(data);
            }
        });

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

        if(!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i)=="9.")) {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click','.layui-icon-close',function () {
                $(this).siblings('.layui-input').val('');
                $(this).siblings().find('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus',function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                $(this).parents('.layui-input-inline').find('.layui-icon-close').removeClass('bdc-hide');
                $(this).siblings('.layui-edge').addClass('bdc-hide');
            }).on('blur',function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                    $this.parents('.layui-input-inline').find('.layui-icon-close').addClass('bdc-hide');
                    $this.siblings('.layui-edge').removeClass('bdc-hide');
                },150)
            });
        }

        //监听滚动事件
        var scrollTop = 0,
            scrollLeft = 0;
        var tableTop = 0, tableLeft = 0;
        var $nowBtnMore = '';
        $(window).on('scroll', function () {
            scrollTop = $(this).scrollTop();
            scrollLeft = $(this).scrollLeft();

            if ($nowBtnMore != '') {
                if (tableTop + 26 + $nowBtnMore.height() < document.body.offsetHeight) {
                    $nowBtnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
                } else {
                    $nowBtnMore.css({top: tableTop - scrollTop - $nowBtnMore.height(), left: tableLeft - 30});
                }
            }
        });
    });

    // 导入土地数据
    function importData(data){
        console.info(data);
        // 根据产权证照去证书表查看是否已经导入过数据
        $.ajax({
            url: getContextPath() + "/td/sfyjdr",
            type: 'GET',
            dataType: 'json',
            data: {bdcqzh: data.bdcqzh},
            success: function (res) {
                removeModal();
                if(res == true){
                    var c = layer.open({
                        // title: '请选择收款单位',
                        type: 1,
                        area: ['430px','180px'],
                        btn: ['确定', '取消'],
                        content: '<div id="bdc-popup-radio">\n' +
                            '    <form class="layui-form" action="">\n' +
                            '        <div class="layui-form-item pf-form-item" style="margin-top: 27px;">\n' +
                            '            <div class="layui-inline">\n' +
                            '                <label style="margin-left: 120px">土地数据已导入，是否重复导入？</label>\n' +
                            '                <div class="layui-input-inline">\n' +
                            '                </div>\n' +
                            '            </div>\n' +
                            '        </div>\n' +
                            '    </form>\n' +
                            '</div>',
                        yes: function (index, layero) {
                            addModel();
                            $.ajax({
                                url: getContextPath() + "/td/import/data",
                                type: 'GET',
                                dataType: 'json',
                                data: {xmid: data.xmid, bdcqzh: data.bdcqzh, qllx: data.qllx},
                                success: function (res) {
                                    removeModal();
                                    successMsg("导入成功");
                                    layer.close(c);
                                    table.reload("tdsqTableId", {
                                        where: searchParam ,
                                        page: {
                                            curr: 1  //重新从第 1 页开始
                                        }
                                    });
                                },
                                error: function (err) {
                                    removeModal();
                                    delAjaxErrorMsg(err);
                                }
                            });
                        }
                        , btn2: function (index, layero) {
                            //取消 的回调
                            layer.close(c)
                        }
                        , cancel: function (index, layero) {
                            //右上角关闭回调
                            layer.close(c)
                        }
                    })
                } else if(res == false){
                    addModel();
                    $.ajax({
                        url: getContextPath() + "/td/import/data",
                        type: 'GET',
                        dataType: 'json',
                        data: {xmid: data.xmid, bdcqzh: data.bdcqzh, qllx: data.qllx},
                        success: function (res) {
                            removeModal();
                            successMsg("导入成功");
                            layer.close(c);
                            table.reload("tdsqTableId", {
                                where: searchParam ,
                                page: {
                                    curr: 1  //重新从第 1 页开始
                                }
                            });
                        },
                        error: function (err) {
                            removeModal();
                            delAjaxErrorMsg(err);
                        }
                    });
                }
            },
            error: function (err) {
                removeModal();
                delAjaxErrorMsg(err);
            }
        });

    }

});


