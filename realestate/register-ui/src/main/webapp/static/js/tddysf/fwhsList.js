/**
 * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @description 不动产单元
 */
var cxObj ={};
layui.use(['table', 'laytpl', 'layer', 'form','jquery'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        layer = layui.layer;

    var gzlslid = $.getUrlParam('gzlslid');

    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });

    var reverseList = ['zl'];

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
        elem: '#fwhsListTable',
        id: 'fwhsListTable',
        toolbar: '#toolbarDemo',
        title: '房屋信息',
        defaultToolbar: [],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        data:[],
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {
                field: 'bdcdyh', title: '不动产单元号', width: 300,templet: function (d) {
                    return formatBdcdyh(d.bdcdyh);
                }},
            {field: 'zl', title: '坐落', minWidth: 200},
            {field: 'tddysfsf', title: '土地抵押是否释放', minWidth: 150}
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

            reverseTableCell(reverseList);


        }
    });

    $('.bdc-table-box').on('mouseenter','td',function () {
        if($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1){
            $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
        }
        $('.layui-table-grid-down').on('click',function () {
            setTimeout(function () {
                $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
            },20);
        });
    });

    //主页面头工具栏事件
    table.on('toolbar(fwhsListTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'gldyh':
                glFwdyh(checkStatus.data);
                break;
        }
    });

    //关联房屋单元号
    function glFwdyh(checkData){
        if (checkData.length === 0) {
            showAlertDialog("未选择数据");
        } else {
            var bdcdyhArr=[];
            for (var i = 0; i < checkData.length; i++) {
                var bdcdyh = checkData[i].bdcdyh;
                bdcdyhArr.push(bdcdyh);
            }
            var bdcTddysfDyh = {};
            bdcTddysfDyh.scfs=1;
            bdcTddysfDyh.gzlslid = gzlslid;
            bdcTddysfDyh.qjgldm =cxObj.qjgldm;
            bdcTddysfDyh.bdcdyhList = bdcdyhArr;
            getReturnData("/rest/v1.0/tddysf/glfwdyh", JSON.stringify(bdcTddysfDyh), "POST", function () {
                successMsg("关联成功");
                parent.layer.closeAll();
                parent.$("#search").click();
            }, function (error) {
                delAjaxErrorMsg(error);

            });
        }
    }



    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        // 获取查询内容

        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            cxObj[name] = value;
        });
        // 重新请求
        table.reload("fwhsListTable", {
            url: "/realestate-register-ui/rest/v1.0/tddysf/fwdyh/page",
            where: cxObj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });


});

//设置查询条件
function setSearchData(searchData){
    for (var key in searchData) {
        if (isNotBlank(searchData[key])) {
            cxObj[key] = searchData[key];
        }
    }
}