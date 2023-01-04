/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/30
 * @description 批量项目与费用关联核对
 */
var fplb;
var sfxxList = "";
var BASE_CZ_URL = '/realestate-inquiry-ui/rest/v1.0/changzhou/fph';
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;

    form.render();

    $(function () {
        //表头
        var tableTitle = [
            {field: 'xh', type: 'numbers', width: '3%', title: '序号'},
            {field: 'slbh', title: '受理编号', width: '22%'},
            {field: 'gzldymc', title: '工作流定义名称', width: '25%'},
            {field: 'qlrmc', title: '权利人名称', width: '25%'},
            {field: 'hj', title: '合计', width: '10%'},
            {field: 'cz', title: '操作', width: '15%', templet: '#czTpl'}
        ];

        // 加载Table
        table.render({
            elem: '#xmfyTable',
            toolbar: '#toolbarDemo',
            title: '批量项目与费用关联核对',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
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
            }
        });

        //头工具栏事件
        table.on('toolbar(xmfyTable)', function (obj) {
            if (obj.event == 'detail') {
                $('a[lay-event="collapse"]').click();
                if ($(this).html() == '查看明细') {
                    $(this).html('隐藏明细');
                } else {
                    $(this).html('查看明细');
                }
            } else if (obj.event == 'fph') {
                var data = obj.config.data;
                var bdcFhplDTOS = [];
                $.each(data, function (key, value) {
                    var obj = {};
                    obj["bdcSlSfxxDO"] = value.bdcSlSfxxDO;
                    obj["slbh"] = value.slbh;
                    bdcFhplDTOS.push(obj);
                });
                console.info(bdcFhplDTOS);
                getFph(bdcFhplDTOS);
            } else if (obj.event == 'dyqb') {
                dyqb();
            }
        });

        //监听列表工具条
        table.on('tool(xmfyTable)', function (obj) {
            if (obj.event === 'collapse') {
                var bdcSlSfxmDOS = obj.data.bdcSlSfxmDOS;
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
                        trObjChildren.find('table').attr("id", "sfxmList" + index);
                        table.render({
                            elem: "#sfxmList" + index,
                            data: bdcSlSfxmDOS,
                            width: tablewidth,
                            cols: [[
                                // {field: 'xh', title: '序号', minWidth: 60, type: 'numbers'},
                                {field: 'sfxmmc', title: '项目名称', minWidth: 200},
                                {
                                    field: 'jedw', title: '单位', minWidth: 80, templet: function (d) {
                                        if (d.jedw == '2') {
                                            return "万元";
                                        } else {
                                            return "元";
                                        }
                                    }
                                },
                                {field: 'sfxmbz', title: '单价', minWidth: 200},
                                {field: 'sl', title: '数量', minWidth: 100},
                                {field: 'ysje', title: '收费金额', minWidth: 100},

                            ]]
                        });
                    }
                })
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
            sfxxList = "";
            var qlrlb = $('#qlrlb').val();
            fplb = $('#fplb').val();
            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/changzhou/fph/list?qlrlb=" + qlrlb + "&fplb=" + fplb,
                contentType: "application/json;charset=utf-8",
                type: "GET",
                success: function (data) {
                    removeModal();
                    table.reload("xmfyTable", {
                        data: data
                        , done: function () {
                            setHeight();
                        }
                    });
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

        //获取发票号
        function getFph(bdcFhplDTOS) {
            addModel();
            if (isNullOrEmpty(bdcFhplDTOS) || bdcFhplDTOS.length <= 0) {
                removeModal();
                warnMsg("收费信息不能为空！")
                return false;
            }
            $.ajax({
                url: BASE_CZ_URL + "/pl?fplb=" + fplb,
                type: "POST",
                data: JSON.stringify(bdcFhplDTOS),
                contentType: 'application/json',
                async: false,
                success: function (data) {
                    if (isNullOrEmpty(data)) {
                        warnMsg("获取发票号失败！");
                        sfxxList = "";
                        return;
                    }
                    sfxxList = data;
                    dyqb();
                }, complete: function (XMLHttpRequest, textStatus) {
                    removeModal();
                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }
    });

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
            var tr = '<tr data-index="' + indexChildren + '"><td colspan="' + colspan + '"><div style="height: auto;padding-top:15px;padding-bottom:15px;padding-left:' + lw + 'px;padding-right:' + rw + 'px;background-color: #fff" class="layui-table-cell">' + content + '</div></td></tr>';
            trObjChildren = trObj.after(tr).next().hide(); //隐藏展开行
            var fixTr = '<tr data-index="' + indexChildren + '"></tr>';//固定行
            leftTrChildren = leftTr.after(fixTr).next().hide(); //左固定
            rightTrChildren = rightTr.after(fixTr).next().hide(); //右固定
        }
        //展开|折叠箭头图标
        var dsrBtn = trObj.find('a[lay-event="collapse"]');
        if (dsrBtn.html() == '查看') {
            dsrBtn.html('隐藏');
        } else {
            dsrBtn.html('查看');
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


    //打印
    function dyqb() {
        if (isNullOrEmpty(sfxxList)) {
            warnMsg("请先获取发票号再打印")
            return false;
        }
        var url = "/realestate-inquiry-ui/changzhou/fph/plxmfyGlhd.html?fplb=" + fplb + "&sfxxids=" + sfxxList;
        var index = layer.open({
            type: 2,
            title: "批量项目与费用关联核对",
            area: ['1300px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: url,
            cancel: function () {
            }
        });
        layer.full(index);
    }
});



