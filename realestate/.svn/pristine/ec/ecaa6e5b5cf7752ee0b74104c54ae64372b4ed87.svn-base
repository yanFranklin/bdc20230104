/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2020/08/11
 * describe: 海门多个物合并一个不动产单元配置台账页面处理JS
 */
var reverseList = ['zl'];

layui.use(['table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;

    // 获取字典信息
    getZdList();

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#bdcdyTable',
        toolbar: '#toolbar',
        title: '多个物合并一个不动产单元数据查询展示台账',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {minWidth:120, sort:false, field:'slbh',  title:'受理编号' },
            {minWidth: 280,sort:false, field: 'qlcfbs',title: '权利拆分标识', hide: true},
            {minWidth:250, sort:false, field:'bdcqzh',title:'不动产权证号'},
            {minWidth:120, sort:false, field:'qlr',   title:'权利人'},
            {minWidth:200, sort:false, field:'qlrzjh',title:'权利人证件号'},
            {minWidth:250, sort:false, field:'zl',    title:'坐落'},
            {minWidth:100, sort:false, field:'zh',    title:'幢号'},
            {minWidth:100, sort:false, field:'zcs',   title:'总层数'},
            {minWidth:100, sort:false, field:'szc',   title:'所在层'},
            {minWidth:100, sort:false, field:'fjh',   title:'房间号'},
            {minWidth:120, sort:false, field:'ghyt',  title:'规划用途',
                templet: function (d) {
                    if (!isNullOrEmpty(d.ghyt) && !isEmptyObject(zdList) && !isEmptyObject(zdList.fwyt)) {
                        $.each(zdList.fwyt, function (index, item) {
                            if (d.ghyt == item.DM) {
                                return item.MC;
                            }
                        });
                    }
                    return "";
                }
            },
            {
                minWidth: 180,sort:false, field: 'djsj',title: '登记时间',
                templet: function (d) {
                    return format(d.djsj);
                }
            },
            {
                width: 280, sort:false, field: 'bdcdyh',title: '不动产单元号',
                templet: function (d) {
                    return formatBdcdyh(d.bdcdyh);
                }
            },
            {
                field: 'sfyhb', width: 120, title: '合并状态', fixed: 'right', sort: false,
                templet: function (d) {
                    if(d && d.qlcfbs && d.qlcfbs == "BDCDYHBJL") {
                        return '<span class="bdc-yy" style="color:#ef7106;">新记录</span>';
                    } else {
                        if(d && d.sfyhb && (d.sfyhb == true || d.sfyhb == "true")) {
                            return '<span class="bdc_change_red bdc-cf" style="color:#EE0000;">已合并</span>';
                        } else {
                            return '<span class="" style="color:#32b032;">未合并</span>';
                        }
                    }
                }
            },
            {
                field: 'qszt', width: 100, title: '权属状态', fixed: 'right', sort: false,
                templet: function (d) {
                    return formatQsztWithCx(d.qszt, 0);
                }
            },
            {
                field: 'bdcdyZtDTO', width: 120, title: '限制状态', fixed: 'right', sort: false,
                templet: function (d) {
                    return formatXzzt(d.bdcdyZtDTO);
                }
            },
            {title: '查看', fixed: 'right', toolbar: '#barDemo', width: 100}
        ]],
        data: [],
        page: true,
        parseData: function (res) {
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
        }
    });

    //监听工具条
    table.on('tool(bdcdyTable)', function (obj) {
        var data = obj.data;
        if (isNullOrEmpty(data.qlcfbs)) {
            warnMsg("未查询到权利拆分标识，请先补充数据！");
            return;
        }

        if (obj.event === 'tbsjl') {
            hbBdcdy(data.qlcfbs);
        }
    });

    /**
     * 弹出相同权利拆分标识的房地产权记录，进行合并操作
     * @param qlcfbs 权利拆分标识
     */
    function hbBdcdy(qlcfbs) {
        layer.open({
            type: 2,
            title: '合并不动产单元',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['100%', '100%'],
            offset: 'auto',
            content: [ "hbbdcdyCz.html?qlcfbs=" + qlcfbs, 'yes'],
            success: function (layero, index) {
            },
            end:function(){
                table.reload('bdcdyTable');
            }
        });
    }

    /**
     * 点击查询
     */
    $('#search').on('click',function () {
        search();
    });

    /**
     * 回车查询
     */
    $('.search').on('keydown', function (event) {
        if (event.keyCode == 13) {
            search();
        }
    });

    function search() {
        // 获取查询内容
        var obj = {};
        var count = 0;
        $(".search").each(function(i){
            var value= $(this).val();
            if(!isNullOrEmpty(value)){
                count += 1;
            }
            var name= $(this).attr('name');
            obj[name]=value;
        });

        if(0 == count){
            warnMsg("请输入查询条件！");
            return false;
        }

        addModel();
        // 重新请求
        table.reload("bdcdyTable", {
            where: obj
            ,url: "/realestate-register-ui/rest/v1.0/bdcdy/hb/bdcdy"
            , page: {
                curr: 1  //重新从第 1 页开始
            }
            , done: function (res, curr, count) {
                removeModel();
                setHeight();
                reverseTableCell(reverseList);
            }
        });
    }

    /**
     * 重置
     */
    $('#reset').on('click',function () {
    });
});