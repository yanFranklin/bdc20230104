/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/10
 * @description 最高法-司法判决信息结果反馈接口
 */
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;

    form.render();

    // 当前页数据
    var currentPageData = new Array();
    $(function () {
        //表头
        var tableTitle =[
            // {title:'按钮', toolbar: '#barDemo', width:60, fixed: 'left'},
            {field: 'xh', type: 'numbers', width: 60, title: '序号'},
            // {field: 'ah', title: '案号', width: '25%', event: 'collapse',
            //     templet: function (d) {
            //         var ah='';
            //         if(d.ah != null){
            //             ah = d.ah;
            //         }
            //         return '<div style="position: relative;\n' + 'padding: 0 10px 0 20px;">' + ah + '<i style="left: 0px;" lay-tips="展开" class="layui-icon layui-colla-icon layui-icon-right"></i></div>'
            //     }},
            {field: 'ah', title: '案号', width: '25%'},
            {field: 'gshah', title: '格式化案号', width: '25%'},
            {field: 'stm', title: '实体码', width: '30%'},
            {field: 'cz', title: '操作', minWidth: 80, templet: '#czTpl', fixed: 'right'}
        ];

        // 加载Table
        table.render({
            elem: '#sfpjxxjgTable',
            toolbar: '#toolbarDemo',
            title: '最高法-司法判决信息结果反馈',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [tableTitle],
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
            }
        });

        //监听列表工具条
        table.on('tool(sfpjxxjgTable)', function (obj) {
            if (obj.event === 'collapse') {
                var ajDsrDTOList = obj.data.ajDsrDTOList;
                var tablewidth = $('.layui-table-header').width() - 50;
                var trObj = layui.$(this).parents('tr'); //当前行
                var accordion = false; //开启手风琴，那么在进行折叠操作时，始终只会展现当前展开的表格。
                var content = '<table></table>'; //内容
                //表格行折叠方法
                collapseTable({
                    elem: trObj,
                    accordion: accordion,
                    content: content,
                    success: function (trObjChildren, index) { //成功回调函数
                        //trObjChildren 展开tr层DOM
                        //index 当前层索引
                        trObjChildren.find('table').attr("id", "ajDsrList" + index);
                        table.render({
                            elem: "#ajDsrList" + index,
                            data: ajDsrDTOList,
                            width:tablewidth,
                            cols: [[
                                {field: 'xh', title: '序号', minWidth: 60, type: 'numbers'},
                                {field: 'mc', title: '当事人姓名/企业名称', minWidth: 200},
                                {field: 'zjhm', title: '证件号码/组织机构代码', minWidth: 200},

                            ]]
                        });
                    }
                })
            }
            if (obj.event === 'wsnr') {
                var wsnr = obj.data.wsDTO.nr;
                var index = layer.open({
                    type: 1,
                    title: "文书内容",
                    area: ['1300px', '600px'],
                    fixed: false, //不固定
                    maxmin: true, //开启最大化最小化按钮
                    content: wsnr,
                    cancel: function () {
                        //刷新页面
                        // loadYcymList();
                    }
                });
                layer.full(index);
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
            obj.cxqqdhList=[];
            obj.slbh = $('#slbh').val();
            if(isNotBlank($('#cxqqdh').val())){
                obj.cxqqdhList.push($('#cxqqdh').val());
            }
            obj.loadpage =true;

            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/supremecourt/decisionResponse",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();
                    var content='';
                    if(data){
                        content = data.content[0].dataDTO.ajDTOList;
                    }
                    table.reload("sfpjxxjgTable", {
                        data:content
                        , done: function () {
                            setHeight();
                        }
                    });
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                }
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
            if (zdDm != null) {
                var zdVals;
                if ("gyfs" == zdname) {
                    zdVals = eval(zdListName.gyfs);
                }
                if ("fwyt" == zdname) {
                    zdVals = eval(zdListName.fwyt);
                }
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

    // 嵌套表格方法
    function collapseTable(options) {
        var trObj = options.elem;
        if (!trObj) return;
        var accordion = options.accordion,
            success = options.success,
            content = options.content || '';
        var tableView = trObj.parents('.layui-table-view'); //当前表格视图
        var id = tableView.attr('lay-id'); //当前表格标识
        var index = trObj.data('index'); //当前行索引
        var leftTr = tableView.find('.layui-table-fixed.layui-table-fixed-l tr[data-index="' + index + '"]'); //左侧当前固定行
        var rightTr = tableView.find('.layui-table-fixed.layui-table-fixed-r tr[data-index="' + index + '"]'); //右侧当前固定行
        var colspan = trObj.find('td').length; //获取合并长度
        var trObjChildren = trObj.next(); //展开行Dom
        var indexChildren = id + '-' + index + '-children'; //展开行索引
        var leftTrChildren = tableView.find('.layui-table-fixed.layui-table-fixed-l tr[data-index="' + indexChildren + '"]'); //左侧展开固定行
        var rightTrChildren = tableView.find('.layui-table-fixed.layui-table-fixed-r tr[data-index="' + indexChildren + '"]'); //右侧展开固定行
        var lw = leftTr.width() + 25; //左宽
        var rw = rightTr.width() + 25; //右宽
        //不存在就创建展开行
        if (trObjChildren.data('index') != indexChildren) {
            //装载HTML元素
            var tr = '<tr data-index="' + indexChildren + '"><td colspan="' + colspan + '"><div style="height: auto;padding-top:15px;padding-bottom:15px;padding-left:' + lw + 'px;padding-right:' + rw + 'px" class="layui-table-cell">' + content + '</div></td></tr>';
            trObjChildren = trObj.after(tr).next().hide(); //隐藏展开行
            var fixTr = '<tr data-index="' + indexChildren + '"></tr>';//固定行
            leftTrChildren = leftTr.after(fixTr).next().hide(); //左固定
            rightTrChildren = rightTr.after(fixTr).next().hide(); //右固定
        }
        //展开|折叠箭头图标
        var dsrBtn = trObj.find('a[lay-event="collapse"]');
        if(dsrBtn.html() == '查看当事人'){
            dsrBtn.html('隐藏当事人');
        }else {
            dsrBtn.html('查看当事人');
        }

        //显示|隐藏展开行
        trObjChildren.toggle();
        //开启手风琴折叠和折叠箭头
        if (accordion) {
            trObj.siblings().find('td[lay-event="collapse"] i.layui-colla-icon').removeClass("layui-icon-down").addClass("layui-icon-right");
            trObjChildren.siblings('[data-index$="-children"]').hide(); //展开
            rightTrChildren.siblings('[data-index$="-children"]').hide(); //左固定
            leftTrChildren.siblings('[data-index$="-children"]').hide(); //右固定
        }
        success(trObjChildren, indexChildren); //回调函数
        heightChildren = trObjChildren.height(); //展开高度固定
        rightTrChildren.height(heightChildren + 115).toggle(); //左固定
        leftTrChildren.height(heightChildren + 115).toggle(); //右固定
    }

});



