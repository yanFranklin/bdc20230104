/*获取字典数据*/

var table;
var form;
var laydate;
var moduleCode = getQueryString("moduleCode");
//数据行编辑按钮权限
var rowEdit;
//数据行删除按钮权限
var rowDel;
/*table数据加载*/
$(function () {
    if(moduleCode){
        setElementAttrByModuleAuthority(moduleCode);
    }
    layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
        table = layui.table;
        form = layui.form;
        laydate = layui.laydate;
        //不动产单元号的表头
        var unitTableTitle = [
            {type: 'checkbox', fixed: 'left'},
            {field: 'BAID', title: '备案id', hide: true},
            {field: 'SLBH', title: '受理编号', width: "15%"},
            {field: 'HTBH', title: '合同编号', width: "15%"},
            {field: 'MSR', title: '买受人', width: "20%"},
            {
                field: 'BASJ', title: '备案日期', width: "17.6%",
                templet: function (d) {
                    return format(d.BASJ);
                }
            },
            {field: 'ZL', title: '坐落', width: "20%"},
            {field: 'CZ', title: '操作', width: "10%", templet: '#htbaczTpl', fixed: "right"}
        ];
        var url = getContextPath() + '/rest/v1.0/htbaxx';
        var htbaxxQO = form.val("searchform");
        var tableConfig = {
            id: 'baid',
            url: "",
            where: {htbaxxQO: JSON.stringify(htbaxxQO)},
            defaultToolbar: ['filter'],
            page: true,
            toolbar: '#toolbarDemo',
            cols: [unitTableTitle],
            parseData: function (res) {
                res.content.forEach(function (v) {
                    v.edit = rowEdit;
                    v.del = rowDel;
                });
            },
            done: function (res, curr, count) {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() === 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 131);
                }
                var reverseList = ['ZL'];
                reverseTableCell(reverseList, "baid");

            }
        };

        //加载表格
        loadDataTablbeByUrl('#baxxTable', tableConfig);

        //监听行工具事件，表格操作栏内部按钮
        table.on('tool(baxxTable)', function (obj) {
            var baxx = obj.data;
            switch (obj.event) {
                case 'edit':
                    editBaxx(baxx.BAID);
                    break;
                case 'view':
                    viewBaxx(baxx.BAID);
                    break;
                case 'del':
                    var baidList = [];
                    baidList.push(baxx.BAID);
                    layer.confirm('确定删除备案信息?', function (index) {
                        deleteBaxx(baidList.join());
                        return false;
                    })
                    break;
            }
        });

        // 监听表格头部按钮
        table.on('toolbar(baxxTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'delete':
                    if (checkStatus.data.length > 0) {
                        var baidList = [];
                        checkStatus.data.forEach(function (element, sameElement, set) {
                            baidList.push(element.BAID)
                        });
                        layer.confirm('确定删除备案信息?', function (index) {
                            deleteBaxx(baidList.join());
                            return false;
                        })
                    } else {
                        ityzl_SHOW_INFO_LAYER("请先勾选数据再操作");
                    }
                    break;
                case 'selectlpb':
                    var index = layer.open(
                        {
                            type: 2,
                            area: ['960px', '575px'],
                            fixed: false, //不固定
                            title: "选择楼盘表",
                            content: '/realestate-etl/view/lpb/selectLpb.html',
                            end: function () {
                                $('#queryBaxx').click();
                                return false;
                            }
                            // btnAlign: "c"
                        }
                    )
                    layer.full(index);
                    break;
            }
        });

        //提交表单
        form.on("submit(queryBaxx)", function (data) {
            tableReloadNew('baid', data.field, url);
            return false;
        });

        //回车操作
        $(document).keydown(function (event) {
            if (event.keyCode === 13) {
                $('#queryBaxx').click();
            }
        });
    });

});

function deleteBaxx(baids) {
    addModel("删除中");
    getReturnData("/rest/v1.0/htbaxx?baids=" + baids, {}, "DELETE", function (data) {
        if (data) {
            ityzl_SHOW_SUCCESS_LAYER("删除成功");
            $('#queryBaxx').click();
            //同步权籍的备案状态,data返回的是bdcdyh集合
            getReturnData("/rest/v1.0/htbaxx/syncbazt/bdcdyh",JSON.stringify(data),"POST",function (data) {
                console.log("删除同步权籍状态结束");
            },function (xhr) {
                delAjaxErrorMsg(xhr);
            })
        }
        removeModal();
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

function editBaxx(baid) {
    addModel();
    var url = getContextPath() + "/view/htba/baxx.html?baid=" + baid;
    var index = layer.open(
        {
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            title: "备案信息",
            content: url,
            btnAlign: "c",
            end: function (index, layero) {
                $('#queryBaxx').click();
                return false;
            }
        }
    )
    layer.full(index);
    removeModal();
}

function viewBaxx(baid) {
    addModel();
    var url = getContextPath() + "/view/htba/baxx.html?baid=" + baid+"&isView=1";
    var index = layer.open(
        {
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            title: "备案信息",
            content: url,
            btnAlign: "c",
            end: function (index, layero) {
                $('#queryBaxx').click();
                return false;
            }
        }
    )
    layer.full(index);
    removeModal();
}


/**
 *根据模块管理中的元素配置设置权限
 * @param moduleCode 资源名
 */
function setElementAttrByModuleAuthority(moduleCode) {
    $.ajax({
        url:  getContextPath() + '/rest/v1.0/htbaxx/moduleAuthority/' + moduleCode,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data) {
                for (var i in data) {
                    id = i;
                    attr = data[i];
                    if( id=="rowEdit" ){ rowEdit = data[i]}
                    if( id=="rowDel" ){ rowDel = data[i]}
                    var element = document.getElementById(id);
                    if (element !== null) {
                        //1.隐藏2.只读3.必填
                        if (attr === "hidden") {
                            if($("."+i)) {
                                $("."+i).hide();
                            }
                        }
                    }
                }
            }
        }, error: function (xhr, status, error) {
            failMsg("页面权限请求设置失败，请联系管理员！");
        }
    });
}
