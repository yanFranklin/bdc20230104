var param ;
var jyqList ={},bdcdjbDO={};
layui.use(['jquery', 'form', 'table', 'laytpl', 'laydate','element','layer' ], function() {
    $ = layui.jquery;
    var form = layui.form,
        table = layui.table,
        laytpl = layui.laytpl,
        element = layui.element,
        layer = layui.layer,
        laydate = layui.laydate;

    $(function () {
        param = $.getUrlParam('param');
        //宗地代码
        var zddm = $.getUrlParam('zddm');
        if (param) {
            $.ajax({
                url: '/realestate-register-ui/rest/v1.0/djbxx/qlxx/tdcbjyqNyddqtsyq/' + param,
                contentType: "application/json;charset=utf-8",
                type: "GET",
                dataType: "json",
                success: function (data) {
                    /**
                     * data数据为：土地承包经营权.json
                     * 获取data数据中zddm,用zddm请求getDjbFmxx（）；
                     */
                    if (data) {
                        if(data.dkxxByHuKouVOList &&data.dkxxByHuKouVOList.length >0) {
                            // 宗地四至拼接
                            for (var i = 0; i < data.dkxxByHuKouVOList.length; i++) {
                                var zdsz = data.dkxxByHuKouVOList[i].zdszd
                                    + ';' + data.dkxxByHuKouVOList[i].zdszn
                                    + ';' + data.dkxxByHuKouVOList[i].zdszx
                                    + ';' + data.dkxxByHuKouVOList[i].zdszb;

                                data.dkxxByHuKouVOList[i].zdsz = zdsz;
                            }
                            // 重新组织数据：按合同编号分组地块信息
                            data.htdkList = [];
                            for (var i = 0; i < data.dkxxByHuKouVOList.length; i++) {
                                var htdk = {}, isSame = false;
                                htdk.htbh = data.dkxxByHuKouVOList[i].htbh;
                                htdk.cbfs = data.dkxxByHuKouVOList[i].cbfs;
                                htdk.cbjssj = data.dkxxByHuKouVOList[i].cbjssj;
                                htdk.cbqssj = data.dkxxByHuKouVOList[i].cbqssj;
                                htdk.fbfmc = data.dkxxByHuKouVOList[i].fbfmc;
                                htdk.fbfdm = data.dkxxByHuKouVOList[i].fbfdm;
                                htdk.fbffzr = data.dkxxByHuKouVOList[i].fbffzr;
                                htdk.dkxxList = [];
                                htdk.dkxxList.push(data.dkxxByHuKouVOList[i]);
                                if (data.htdkList.length > 0) {
                                    for (var j = 0; j < data.htdkList.length; j++) {
                                        if (isNotBlank(data.htdkList[j].htbh) && data.htdkList[j].htbh == data.dkxxByHuKouVOList[i].htbh) {
                                            isSame = true;
                                            data.htdkList[j].dkxxList.push(data.dkxxByHuKouVOList[i]);
                                        }
                                    }
                                }
                                if (!isSame || !isNotBlank(data.dkxxByHuKouVOList[i].htbh)) {
                                    data.htdkList.push(htdk);
                                }
                            }
                            //按合同编号计算承包合同总面积、地块总数
                            for (var i = 0; i < data.htdkList.length; i++) {
                                data.htdkList[i].qqdkzs = data.htdkList[i].dkxxList.length;
                                data.htdkList[i].cbhtmj = 0;
                                for (var j = 0; j < data.htdkList[i].dkxxList.length; j++) {
                                    if (data.htdkList[i].dkxxList[j].cbhtmj != null) {
                                        data.htdkList[i].cbhtmj += data.htdkList[i].dkxxList[j].cbhtmj;
                                    }
                                }
                            }
                        }
                        jyqList = data;
                        //封面信息
                        getDjbFmxx(zddm);
                    }
                },
                error: function () {
                    layer.alert("获取登记簿信息失败！")
                }
            });
        } else {
            layer.alert("获取登记簿信息失败！")
        }

        //监听第一次单击tab
        element.on('tab(tabFilter)', function (data) {
            if ($(this).hasClass('bdc-first-click')) {
                $(this).removeClass('bdc-first-click');
                if($(this).hasClass('dkxx')){
                    generateDkxxTable(jyqList.htdkList);
                }
            }
        });

        //监听地块信息列表工具条
        table.on('tool(dkxxTable)', function (obj) {
            var dkxxList = obj.data.dkxxList;
            var tablewidth = $('.layui-table-header').width() - 50;
            if (obj.event === 'collapse') {
                var trObj = layui.$(this).parent('tr'); //当前行
                var accordion = true; //开启手风琴，那么在进行折叠操作时，始终只会展现当前展开的表格。
                var content = '<table></table>'; //内容
                //表格行折叠方法
                collapseTable({
                    elem: trObj,
                    accordion: accordion,
                    content: content,
                    success: function (trObjChildren, index) { //成功回调函数
                        //trObjChildren 展开tr层DOM
                        //index 当前层索引
                        trObjChildren.find('table').attr("id", "htdkList" + index);
                        table.render({
                            elem: "#htdkList" + index,
                            data: dkxxList,
                            width:tablewidth,
                            cols: [[
                                    {field: 'xh', title: '序号', minWidth: 60, type: 'numbers'},
                                    {field: 'zddm', title: '地块编码', minWidth: 200},
                                    {field: 'zl', title: '地块名称', minWidth: 200},
                                    {field: 'zdsz', title: '坐落(四至)', minWidth: 300},
                                    {field: 'bdcdyh', title: '不动产单元号', minWidth: 300},
                                    {field: 'cbhtmj', title: '确权(合同)面积', minWidth: 130},
                                    {field: 'scmj', title: '实测面积', minWidth: 130},
                                    {field: 'cbtdyt', title: '承包土地用途', minWidth: 130},
                                    {field: 'dldj', title: '质量等级', minWidth: 100},
                                    {field: 'sfjbnt', title: '是否基本农田', minWidth: 130},
                                    {field: 'dl', title: '地类', minWidth: 100},
                                    {field: 'dbr', title: '登簿人', minWidth: 100},
                                    {field: 'djsj', title: '登记时间', minWidth: 200},
                                    {field: 'fj', title: '附记', minWidth: 200},
                                    {field: 'bz', title: '备注', minWidth: 200}
                                ]]
                        });
                    }
                })
            }
        });

        // 监听点击合同编号展示附图
        $(document).on('click','.bdc-ft-btn',function () {
            var htbh = $(this).text();
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/djbxx/zdtByHtbh",
                type: 'GET',
                data: {htbh: htbh},
                async: false,
                success: function (data) {
                    var img ='<img src="data:image/png;base64,' + data +'" alt=""/>';
                    var index = layer.open({
                        type: 1,
                        title: "附图",
                        area: ['1160px', '600px'],
                        fixed: false, //不固定
                        maxmin: true, //开启最大化最小化按钮
                        content:  $('#layer-ft').html(img)
                    });
                    layer.full(index);
                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        })
    });

    //获取登记簿封面相关信息
    function getDjbFmxx(zddm) {
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/djbxx/" + zddm + "/bdcdjb",
            contentType: "application/json;charset=utf-8",
            type: "GET",
            success: function (data) {
                if(data){
                    bdcdjbDO = data;
                }
                generateDjbxx(bdcdjbDO, jyqList);
            },
            error: function () {
                layer.alert("获取登记簿信息失败！")
            }
        });
    }
    //获取附图信息
    function getFtxx() {
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/djbxx//zdt?qlrzjh=" + bdcdyh,
            contentType: "application/json;charset=utf-8",
            type: "GET",
            success: function (data) {
            },
            error: function () {
                layer.alert("获取登记簿信息失败！")
            }

        });

    }
    //组织登记簿数据到页面
    function generateDjbxx(bdcdjbDO,cbjyq) {
        var json = {
            bdcdjb: bdcdjbDO,
            cbjyq: cbjyq
        };
        var getTpl = djcCbjyqTpl.innerHTML
            , view = document.getElementById('tab-content');
        laytpl(getTpl).render(json, function (html) {
            view.innerHTML = html;
        });
    }

    function generateDkxxTable(data) {
        if(data ===undefined){
            data =[];
        }
        table.render({
            id:'dkxxTable',
            elem: '#dkxxTable',
            data: data, //数据
            page: true,//开启分页
            toolbar: "#toolbarDemo",
            defaultToolbar: ['filter'],
            cols: [[ //表头
                {field: 'xh', title: '序号', minWidth:50, type: 'numbers'},
                {field: 'htbh', title: '合同编号', minWidth: 200, event: 'collapse',
                    templet: function (d) {
                        var htbh='';
                        if(d.htbh != null){
                            htbh = d.htbh;
                        }
                        return '<div style="position: relative;\n' + 'padding: 0 10px 0 20px;">' + htbh + '<i style="left: 0px;" lay-tips="展开" class="layui-icon layui-colla-icon layui-icon-right"></i></div>'
                }},
                {field: 'fbfdm', title: '发包方代码', minWidth: 100},
                {field: 'fbfmc', title: '发包方', minWidth: 100},
                {field: 'fbffzr', title: '发包方负责人', minWidth: 100},
                {field: 'cbfs', title: '承包方式', minWidth: 100},
                {field: 'cbhtmj', title: '承包（合同）总面积',minWidth:100},
                {field: 'qqdkzs', title: '承包地块总数',minWidth:100},
                {field: 'cbqssj', title: '承包起始时间', minWidth: 100,
                    templet: function (d) {
                        if (d.cbqssj) {
                            return Format(d.cbqssj, 'yyyy-MM-dd');
                        } else {
                            return '';
                        }
                }},
                {field: 'cbjssj', title: '承包结束时间',
                    templet: function (d) {
                        if (d.cbjssj) {
                            return Format(d.cbjssj, 'yyyy-MM-dd');
                        } else {
                            return '';
                        }
                }}
            ]]
            ,done: function () {
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
                }
            }

        });
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
        trObj.find('td[lay-event="collapse"] i.layui-colla-icon').toggleClass("layui-icon-right layui-icon-down");
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
