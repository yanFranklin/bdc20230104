/**
 * 督察质检监管-质检台账
 */

var zdList = getZdList();
var zjlx = zdList.zjlx;
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element,
        table = layui.table;

    var kssj = laydate.render({
        elem: '#zjwjscsjq',
        type: 'datetime',
        trigger: 'click',
        done: function (value, date) {
            //选择的结束时间大
            if ($('#zjwjscsjz').val() != '' && !completeDate($('#zjwjscsjz').val(), value)) {
                $('#zjwjscsjz').val('');
                $('.laydate-disabled.layui-this').removeClass('layui-this');
            }
            jssj.config.min = {
                year: date.year,
                month: date.month - 1,
                date: date.date,
                hours: date.hours,
                minutes: date.minutes,
                seconds: date.seconds
            }
        }
    });
    var jssj = laydate.render({
        elem: '#zjwjscsjz',
        type: 'datetime',
        trigger: 'click'
    });


    //比较起止时间
    function completeDate(date1, date2) {
        var oDate1 = new Date(date1);
        var oDate2 = new Date(date2);
        if (oDate1.getTime() > oDate2.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    if (moduleCode) {
        setElementAttrByModuleAuthority(moduleCode);
    }

    if(zdList) {
        // 质检类型查询条件下拉框
        if (zjlx) {
            $.each(zjlx, function (index, item) {
                $('#zjlx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
        }
        form.render('select');
    }

    table.render({
        elem: '#pageTable',
        toolbar: '#toolbar',
        url: "/realestate-supervise/rest/v1.0/dczjjg/zjxx",
        title: '质检台账',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {width: 100, sort: false, field: 'zjryid', title: '质检人员ID', hide: true},
            {width: 150, sort: false, field: 'zjry', title: '质检人员',
                templet: function (d) {
                    return bjRedColor(d.sfassczjwj,d.zjry);
                }
            },
            {width: 180, sort: false, field: 'zjbmid', title: '质检部门ID', hide: true},
            {width: 200, sort: false, field: 'zjbm', title: '质检部门',
                templet: function (d) {
                    return bjRedColor(d.sfassczjwj,d.zjbm);
                }
            },
            {width: 200, sort: false, field: 'zjlx', title: '质检类型',
                templet: function (d) {
                    if (zjlx) {
                        for (var index in zjlx) {
                            if (zjlx[index].DM == d.zjlx) {
                                return bjRedColor(d.sfassczjwj,zjlx[index].MC);
                            }
                        }
                    } else {
                        return d.zjlx;
                    }
                }
            },
            {width: 200, sort: false, field: 'zjsj', title: '质检时间',
                templet: function (d) {
                    return bjRedColor(d.sfassczjwj,formatDate(d.zjsj));
                }
            },
            {width: 300, sort: false, field: 'zjqkbz', title: '质检情况备注',
                templet: function (d) {
                    return bjRedColor(d.sfassczjwj,d.zjqkbz);
                }
            },
            {width: 250, sort: false, field: 'zjwjmc', title: '质检文件名称', hide: true},
            {width: 150, sort: false, field: 'zjwjid', title: '质检文件ID', hide: true},
            {width: 250, sort: false, field: 'zjwjlj', title: '质检文件路径', hide: true},
            {width: 200, sort: false, field: 'zjwjscsj', title: '质检文件上传时间', hide: true,
                templet: function (d) {
                    return formatDate(d.zjwjscsj);
                }
            },
            {width: 150, sort: false, field: 'sfwc', title: '是否完成',
                templet: function (d) {
                    if(d.sfwc === 1) {
                        return bjRedColor(d.sfassczjwj,"是");
                    } else {
                        return bjRedColor(d.sfassczjwj,"否");
                    }
                }
            },
            {width: 250, sort: false, field: 'scyj', title: '审查意见',
                templet: function (d) {
                    return bjRedColor(d.sfassczjwj,d.scyj);
                }
            },
            {width: 150, sort: false, field: 'scryid', title: '审查人员ID', hide: true},
            {width: 150, sort: false, field: 'scry', title: '审查人员',
                templet: function (d) {
                    return bjRedColor(d.sfassczjwj,d.scry);
                }
            },
            {width: 150, sort: false, field: 'scbmid', title: '审查部门ID', hide: true},
            {width: 180, sort: false, field: 'scbm', title: '审查部门',
                templet: function (d) {
                    return bjRedColor(d.sfassczjwj,d.scbm);
                }
            },
            {width: 200, sort: false, field: 'scsj', title: '审查时间',
                templet: function (d) {
                    return bjRedColor(d.sfassczjwj,d.scsj);
                }
            },
            {width: 120, sort: false, templet: '#barAction', title: '操作', fixed: 'right'}
        ]],
        text: {
            none: '未查询到数据'
        },
        data: [],
        autoSort: false,
        page: true,
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function (data) {
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');


            if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
            }else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
            var dataArr = data.data;
            if(data !== null && dataArr.length >0) {
                for(var i=0;i<dataArr.length;i++) {
                    if (dataArr[i].sfassczjwj == "false"){
                        warnMsg("请及时上传质检报告！")
                        return;
                    }
                }
            }
        }
    });

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        search();
        return false;
    });

    function search() {
        addModel();
        // 获取查询内容
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });

        // 重新请求
        table.reload("pageTable", {
            url: "/realestate-supervise/rest/v1.0/dczjjg/zjxx"
            ,where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            },
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
                removeModal();
            }
        });
        return false;
    }

    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            search();
        }
    });

    //监听工具条
    table.on('tool(pageTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'downloadZjbg') {
            downloadZjbg(data);
        }
    });

    //头工具栏事件
    table.on('toolbar(pageTable)', function (obj) {
        var data = table.checkStatus('pageTable').data;
        if(obj.event === 'addZjxx'){
            addZjxx();
        } else if(obj.event === 'editZjxx'){
            editZjxx(data);
        } else if(obj.event === 'deleteZjxx'){
            deleteZjxx(data);
        }
    });

    /**
     * 新增质检信息
     */
    function addZjxx() {
        layer.open({
            type: 2,
            title: '质检信息',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '400px'],
            offset: 'auto',
            content: [ "zjxx.html", 'yes'],
            success: function (layero, index) {
            },
            end:function(){
                search();
            }
        });
    }

    /**
     * 编辑质检信息
     * @param data
     */
    function editZjxx(data) {
        if(!data || !data[0] || isNullOrEmpty(data[0].id)) {
            warnMsg("请选择需要编辑的记录");
            return false;
        }

        layer.open({
            type: 2,
            title: '质检信息',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '400px'],
            offset: 'auto',
            content: [ "zjxx.html?id=" + data[0].id, 'yes'],
            success: function (layero, index) {
            },
            end:function(){
                search();
            }
        });
    }

    /**
     * 删除质检信息
     * @param data
     */
    function deleteZjxx(data) {
        if(!data || 0 === data.length) {
            warnMsg("请选择需要删除的记录");
            return false;
        }

        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '是否确认删除？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                //确定操作
                addModel();
                $.ajax({
                    url: "/realestate-supervise/rest/v1.0/dczjjg/zjxx",
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: "application/json;charset=UTF-8",
                    dataType: "text",
                    success: function (data) {
                        if(data && data > 0) {
                            successMsg("删除成功！");
                            search();
                        } else {
                            failMsg("删除失败！");
                        }
                    },
                    complete: function () {
                        removeModal();
                    }
                });
                layer.close(deleteIndex);
            },
            btn2: function () {
                //取消
                layer.close(deleteIndex);
            }
        });
    }

    /**
     * 查看质检报告
     * @param data
     * @returns {boolean}
     */
    function downloadZjbg(data) {
        layer.open({
            type: 2,
            title: '附件信息',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '500px'],
            offset: 'auto',
            content: [ "../file/files.html?page=edit&ywid=" + data.id, 'yes'],
            success: function (layero, index) {
            },
            end:function(){
            }
        });
    }
});
