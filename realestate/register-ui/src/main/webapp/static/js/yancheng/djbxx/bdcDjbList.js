/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 初始化表格，工具栏事件
 */
//获取页面版本
var version = getQueryString("version");

layui.use(['form', 'table', 'jquery'], function () {
    var form = layui.form;
    var table = layui.table;

    $(function () {
        //绑定回车键
        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#search").click();
            }
        });
        // 获取参数
        var processInsId = $.getUrlParam('processInsId');
        // 表单ID
        var formStateId = $.getUrlParam('formStateId');
        // 获取表单权限参数readOnly
        var readOnly = $.getUrlParam('readOnly');

        /**
         * 获取字典
         */
        var zdData;
        $.ajax({
            url: '/realestate-register-ui/rest/v1.0/qlxx/zd',
            type: "GET",
            async: false,
            dataType: "json",
            timeout: 10000,
            success: function (data) {
                zdData = data;
            }
        });

        //点击高级查询
        $('#seniorSearch').on('click', function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            $(this).parent().toggleClass('bdc-button-box-four');
            $('.bdc-percentage-container').css('padding-top', $('.bdc-search-content').height() + 10);

            $('.layui-table-body').height($('.bdc-table-box').height() - 129);
            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 129 - 17);
        });

        if (!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.")) {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
                $(this).siblings('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
            }).on('blur', function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                }, 150)
            });
        }

        /**
         * 加载Table数据列表
         */
        table.render({
            elem: '#djbTable',
            id: 'djbTable',
            toolbar: '#toolbarDemo',
            title: '登记簿台账',
            defaultToolbar: [],
            url: '/realestate-register-ui/rest/v1.0/bdcdy/xmOrYxm/' + processInsId,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            // cellMinWidth: 80,
            even: true,
            cols: [[
                {type: 'numbers', fixed: 'left'},
                {field: 'xmid', title: 'xmid', minWidth: 100, hide: true},
                {field: 'ytdzh', title: '原土地证号', minWidth: 100, hide: true},
                {field: 'ybdcdyh', title: '原不动产单元号', minWidth: 100, hide: true},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
                {field: 'zl', title: '坐落', minWidth: 250},
                {
                    field: 'qllx', title: '权利类型', minWidth: 150,
                    templet: function (d) {
                        if (zdData && zdData.qllx) {
                            for (var index in zdData.qllx) {
                                if (zdData.qllx[index].DM == d.qllx) {
                                    return zdData.qllx[index].MC;
                                }
                            }
                        } else {
                            return d.qllx;
                        }
                    }, hide: true
                },
                {field: 'bdcqzh', title: '不动产权证号', minWidth: 250, hide: true},
                {field: 'bdclx', title: '不动产类型', hide: true},
                {field: 'gzlslid', title: 'gzlslid', hide: true},
                {field: 'qlrzjh', title: '权利人证件号', hide: true},
                {field:'cz', title: '操作', width: 450, templet: '#barDemo', fixed: 'right'}
            ]],
            text: {
                none: '未查询到数据'
            },
            autoSort: false,
            page: true,
            limits: commonLimits,
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                var searchHeight = 131;
                setTableHeight(searchHeight);

                // 设置字符过多，省略样式
                var reverseList = ['zl'];
                reverseTableCell(reverseList);


                // 获取表单控制权限
                if (readOnly || !isNullOrEmpty(formStateId)) {
                    getStateById(readOnly, formStateId, "bdcDjbList");
                }
                //蚌埠【33151】 需求特殊配置
                if (version && version === 'bengbu'){
                    $('.ompredirect').hide();
                    $('.fcda').hide();
                }
            }
        });

        $('.bdc-table-box').on('mouseenter', 'td', function () {
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                }, 20);
            });
        });

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
                    // $nowBtnMore.css({top: tableTop + 26 -scrollTop,right: 20});
                    $nowBtnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
                } else {
                    // $nowBtnMore.css({top: tableTop -scrollTop - $nowBtnMore.height(),right: 20});
                    $nowBtnMore.css({top: tableTop - scrollTop - $nowBtnMore.height(), left: tableLeft - 30});
                }
            }
        });
        //点击表格中的更多
        $('.bdc-table-box').on('click', '.bdc-more-btn', function (event) {
            tableTop = $(this).offset().top;
            tableLeft = $(this).offset().left;
            event.stopPropagation();
            $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
            var $btnMore = $(this).siblings('.bdc-table-btn-more');
            if ($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight) {
                // $btnMore.css({top: $(this).offset().top + 26 -scrollTop,right: 20});
                $btnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
            } else {
                // $btnMore.css({top: $(this).offset().top -scrollTop - $btnMore.height(),right: 20});
                $btnMore.css({top: tableTop - scrollTop - $btnMore.height(), left: tableLeft - 30});
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
        });

        // 监听按钮事件
        form.on("submit(search)", function (data) {
            tableReload("djbTable", data.field);
            return false;
        });


        //监听行工具事件
        table.on('tool(dataTable)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case 'djb':
                    window.open('bdcDjb.html?param=' + data.bdcdyh + '&qllx=' + data.qllx);
                    break;
                case 'djdcb':
                    window.open('/building-ui/djdcb/initview?bdcdyh=' + data.bdcdyh);
                    break;
                case 'lpb':
                    /**
                     * added by cyc at 2019-08-13
                     * 2:土地和房屋
                     * 4:土地和在建建筑物
                     * 10:海域和房屋
                     * 只有带有房屋的才能打开楼盘表
                     */
                    if (data.bdclx == '2' || data.bdclx == '4' || data.bdclx == '10') {
                        window.open('/building-ui/lpb/view?code=bdcres&bdcdyh=' + data.bdcdyh);
                    } else {
                        layer.alert('该不动产类型没有楼盘表，无法查看！', {title: '提示'});
                    }
                    break;
                case 'ompRedirect':
                    window.open('/building-ui/omp/redirect/' + data.bdcdyh + '/' + data.bdclx);
                    break;
                case 'lsgx':
                    window.open('/realestate-register-ui/view/lsgx/bdcdyDjLsgx.html?bdcdyh=' + data.bdcdyh + '&hideDjbBtn=true');
                    break;
                case 'fcda':
                    var url = '/realestate-register-ui/view/daxx/scan.html?type=' + 1 + '&bdcqzh=';
                    if (!isNullOrEmpty(data.ycqzh)) {
                        var str = data.ycqzh;
                        var strs = new Array();
                        strs = str.split(","); //字符分割
                        url += encodeURI(strs[0]);
                    }
                    window.open(url);
                    break;
                case 'dady':
                    dady(data);
                    break;
                case 'bdcda':
                    openBdcda(data.gzlslid);
                    break;
                case 'ahdjb':
                    var zddm ="";
                    if (!isNullOrEmpty(data.bdcdyh) &&data.bdcdyh.length ===28) {
                        zddm =data.bdcdyh.substring(0, 19);
                    }
                    window.open('/realestate-register-ui/view/djbxx/bdcAhdjb.html?param=' + data.qlrzjh +"&zddm="+zddm);
                    break;
            }
        });
    });

    /**
     * 查看登记簿档案信息
     * @param gzlslid
     */
    function openBdcda(gzlslid) {
        $.ajax({
            url: '/realestate-register-ui/rest/v1.0/url/common/bdcda?xmid=&gzlslid=' + gzlslid,
            type: "GET",
            success: function (data) {
                window.open(data);
            }, error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
    }

});
