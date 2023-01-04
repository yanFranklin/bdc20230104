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



    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });

    var reverseList = ['zl'];

    //加载地区下拉
    renderQjgldm();

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
        elem: '#fwhslwListTable',
        id: 'fwhslwListTable',
        toolbar: '#toolbarDemo',
        title: '房屋信息',
        defaultToolbar: [],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        data: [],
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {
                field: 'bdcdyh', title: '不动产单元号', width: 300,templet: function (d) {
                    return formatBdcdyh(d.bdcdyh);
                }},
            {field: 'zl', title: '坐落', minWidth: 300},
            {field: 'tddysfsf', title: '土地抵押是否释放', minWidth: 150},
            {field: 'fjh', title: '房间号', minWidth: 50},
            {field: 'jzmj', title: '建筑面积', minWidth: 50,templet: function (d) {
                    if(d.scjzmj >=0){
                        return d.scjzmj;
                    }else if(d.ycjzmj >=0){
                        return d.ycjzmj;
                    }else{
                        return '';
                    }
                }},
            {field: 'tnjzmj', title: '套内建筑面积', minWidth: 50,templet: function (d) {
                    if(d.scjzmj >=0){
                        if(d.sctnjzmj >=0) {
                            return d.sctnjzmj;
                        }else{
                            return '';
                        }
                    }else if(d.yctnjzmj >=0){
                        return d.yctnjzmj;
                    }else{
                        return '';
                    }
                }},
            {field: 'ftjzmj', title: '分摊建筑面积', minWidth: 50,templet: function (d) {
                    if(d.scjzmj >=0){
                        if(d.scftjzmj >=0) {
                            return d.scftjzmj;
                        }else{
                            return '';
                        }
                    }else if(d.ycftjzmj >=0){
                        return d.ycftjzmj;
                    }else{
                        return '';
                    }
                }}
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        limits: commonLimits,
        parseData: function (res) {
            var qjgldm=$("#qjgldm").val();
            if(isNotBlank(qjgldm)) {
                res.content.forEach(function (v) {
                    v.qjgldm = qjgldm;
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
    table.on('toolbar(fwhslwListTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'lwdyh':
                lwFwdyh(checkStatus.data);
                break;
        }
    });

    //例外房屋单元号
    function lwFwdyh(checkData){
        if (checkData.length === 0) {
            showAlertDialog("未选择数据");
        } else {
            var htmlStr = '<div id="exception-reason" class="bdc-layer-textarea">\n' +
                '        <form class="layui-form" action="">\n' +
                '            <div class="layui-form-item pf-form-item">\n' +
                '                <div class="layui-inline">\n' +
                '                    <label class="layui-form-label"><span class="required-span"><sub>*</sub></span>例外原因</label>\n' +
                '                    <div class="layui-input-inline bdc-end-time-box">\n' +
                '                        <textarea name="lwyy" id="lwyy" placeholder="请输入内容" class="layui-textarea"></textarea>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </form>\n' +
                '    </div>';
            layer.open({
                title: '例外原因',
                type: 1,
                area: ['430px'],
                btn: ['确定', '取消'],
                content: htmlStr,
                yes: function(index, layero) {
                    var lwyy = $("#lwyy").val();
                    if (isNullOrEmpty(lwyy)) {
                        layer.msg('请输入例外原因!');
                        return false;
                    }
                    var bdcdyhArr=[];
                    for (var i = 0; i < checkData.length; i++) {
                        var bdcdyh = checkData[i].bdcdyh;
                        bdcdyhArr.push(bdcdyh);
                    }
                    var bdcTddysfDyh = {};
                    bdcTddysfDyh.scfs=2;
                    bdcTddysfDyh.qjgldm =checkData[0].qjgldm;
                    bdcTddysfDyh.lwyy=lwyy;
                    bdcTddysfDyh.bdcdyhList = bdcdyhArr;
                    getReturnData("/rest/v1.0/tddysf/glfwdyh", JSON.stringify(bdcTddysfDyh), "POST", function () {
                        successMsg("操作成功");
                        $("#search").click();
                        layer.close(index);
                    }, function (error) {
                        delAjaxErrorMsg(error);

                    });
                },
                btn2: function(index, layero){
                    layer.close(index);
                }
            });

        }
    }

    //地区下拉选项
    function renderQjgldm(){
        getReturnData("/rest/v1.0/tddysf/qjgldm/list",{},"GET",function (data){
            if(data &&data.length >0) {
                $("#search-qjgldm").removeClass("bdc-hide");
                $.each(data, function (index, item) {
                    $('#qjgldm').append('<option value="' + item.dm + '">' + item.mc + '</option>');
                });
            }
            form.render("select");

        },function (error){

        },false);
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
        table.reload("fwhslwListTable", {
            url: "/realestate-register-ui/rest/v1.0/tddysf/fwdyh/page",
            where: cxObj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });


});

