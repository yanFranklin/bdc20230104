/**
 * 分配盒号
 */
var form, jquery,laydate,element,table,laytpl,$;
var hhList;
layui.use(['form', 'jquery', 'laydate', 'element', 'table','laytpl'], function () {
     $ = layui.jquery;
        form = layui.form;
        laydate = layui.laydate;
        element = layui.element;
        table = layui.table;
        laytpl = layui.laytpl;

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    if (moduleCode) {
        setElementAttrByModuleAuthority(moduleCode);
    }

    table.render({
            elem: '#pageTable',
            toolbar: '#toolbar',
            url: "/realestate-register-ui/rest/v1.0/gdxxfphh/pagelistall",
            title: '分配盒号',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            cellMinWidth: 80,
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {width: 200, sort: false, field: 'sxh', title: '盒号'},// mlh
                {width: 200, sort: false, field: 'ajh', title: '档案号'}, //ajh
                {width: 150, sort: false, field: 'ajjs', title: '案卷件数'},
                {width: 150, sort: false, field: 'ajys', title: '案卷页数'},
                {width: 150, sort: false, field: 'xzmc', title: '乡镇名称'},
                {width: 150, sort: false, field: 'nf', title: '年份'},
                {width: 200, sort: false, field: 'gdsj', title: '归档时间',
                    templet: function (d) {
                        return format(d.gdsj);
                    }
                },
                {width: 260, sort: false, field: 'gdrxm', title: '归档人姓名'},
                {width: 180, sort: false, field: 'slbh', title: '受理编号'},
                {minWidth: 260, sort: false, field: 'qlr', title: '权利人'},
            ]],
            text: {
                none: '未查询到数据'
            },
            data: [],
            autoSort: false,
            page: true,
            parseData: function(res) { //res 即为原始返回的数据
                //console.log(res)
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                }
            },
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        }
    );

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        //先看是否已经有分配的了
        search();
        return false;
    });

    function search() {
        // 获取查询内容
        var obj= {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });

        // 重新请求
        table.reload("pageTable", {
            url: "/realestate-register-ui/rest/v1.0/gdxxfphh/pagelistall"
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



    //头工具栏事件
    table.on('toolbar(pageTable)', function (obj) {
        var data = table.checkStatus('pageTable').data;
        if(obj.event === 'addWgjl'){
            addWgjl();
        } else if(obj.event === 'editWgjl'){
            editWgjl(data);
        }
    });

    /**
     * 新增条目
     */
    function addWgjl() {
        layer.open({
            type: 2,
            title: '分配盒号',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '300px'],
            offset: 'auto',
            content: [ "fphh.html", 'yes'],
            success: function (layero, index) {
            },
            end:function(){
                search();
            }
        });
    }
    /**
     * 编辑
     * @param data
     */
    function editWgjl(data) {
        if(!data || !data[0] || isNullOrEmpty(data[0].gdxxid)) {
            warnMsg("请选择需要编辑的记录");
            return false;
        }
        layer.open({
            type: 2,
            title: '档案信息',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '200px'],
            offset: 'auto',
            content: [ "xghh.html?gdxxid=" + data[0].gdxxid, 'yes'],
            success: function (layero, index) {
            },
            end:function(){
                search();
            }
        });
    }
});

