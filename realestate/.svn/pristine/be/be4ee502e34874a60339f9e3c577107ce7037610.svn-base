/**
 * Created by Administrator on 2018/12/21.
 */
layui.use(['jquery', 'laytpl','element','table','form','tree'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;
    var layer = layui.layer;
    var reverseList = ['bdcdyh', 'zl', 'bdcqzh'];

    $(function () {


        // 手风琴点击效果
        $(document).on('click', '.link', function () {
            var $this = $(this);
            $this.parents('.accordion').find('.submenu').not($this.next()).slideUp().parent().removeClass('open');
            $this.next().slideToggle();
            $this.parent().toggleClass('open');
        });


        $(document).on('click', '.submenu a', function () {
            $('.submenu a').removeClass('active');
            $(this).addClass('active');
        });


        var cxsqdh = localStorage.getItem("cxsqdh");
        var xmid = localStorage.getItem("xmid");
        $.ajax({
            type: "POST",
            url:"../cxqq/cxjg/loadTree",
            dataType: "json",
            data: {cxsqdh: cxsqdh,xmid:xmid},
            success: function (zNodes) {
                // 组装数据
                var nodeArray = arrayToTree(zNodes);
                var html = "";
                nodeArray.forEach(function(item) {
                    $(".layui-aside-top span").html("查询结果");
                    var sedChildren = item.children;
                    html += "<p class='bdc-xlh'>"+ item.name +"</p>"
                    if (sedChildren.length !=0) {
                        sedChildren.forEach(function (sedItem) {
                            html += "                                <li><div class=\"link\">\n" +
                                "                                    <i class=\"fa fa-caret-right\"></i>\n" +
                                "                                    <img src=\"../static/layui/images/file.png\" alt=\"图标\" class=\"docu-icon\">\n" +
                                "                                    "+sedItem.name+"" +
                                "                                </div>";
                            html += " <ul class=\"submenu\">";
                            var thdChildren = sedItem.children;
                            if (thdChildren.length !=0) {
                                thdChildren.forEach(function (thdItem) {
                                    html += "                                    <li>\n" +
                                        "                                        <a href=\"javascript:;\" onclick='loadTable(\""+thdItem.id+"\",\""+thdItem.pId+"\")' data-id='"+thdItem.id+"' data-pId='"+thdItem.pId+"'>\n" +
                                        "                                            <img src=\"../static/layui/images/log.png\" alt=\"图标\" class=\"docu-icon\">"+thdItem.name+"" +
                                        "                                        </a>\n" +
                                        "                                    </li>";
                                });
                            }
                            html += "</ul></li>";
                        });
                    }

                });
                $("#accordion").append(html);

                window.loadTable = function (id,pId) {
                    var treeNodeId = id;
                    var param = {
                        qllx : treeNodeId,
                        xmid : pId
                    }
                    if (treeNodeId == "cfdj") {
                        loadDataTablbeByParam("#resultTable", cfdjTableConfig,param);
                    }
                    if (treeNodeId == "dyaq") {
                        loadDataTablbeByParam("#resultTable", dyaqTableConfig,param);
                    }
                    if (treeNodeId == "fdcq") {
                        loadDataTablbeByParam("#resultTable", fdcqTableConfig,param);
                    }
                    if (treeNodeId == "gjzwsyq") {
                        loadDataTablbeByParam("#resultTable", gjzwsyqTableConfig,param);
                    }
                    if (treeNodeId == "hysyq") {
                        loadDataTablbeByParam("#resultTable", hysyqTableConfig,param);
                    }
                    if (treeNodeId == "ysydsyq") {
                        loadDataTablbeByParam("#resultTable", ysydsyqTableConfig,param);
                    }
                    if (treeNodeId == "lq") {
                        loadDataTablbeByParam("#resultTable", lqTableConfig,param);
                    }
                    if (treeNodeId == "nydsyq") {
                        loadDataTablbeByParam("#resultTable", nydsyqTableConfig,param);
                    }
                    if (treeNodeId == "tdsyq") {
                        loadDataTablbeByParam("#resultTable", tdsyqTableConfig,param);
                    }
                    if (treeNodeId == "ygdj") {
                        loadDataTablbeByParam("#resultTable", ygdjTableConfig,param);
                    }
                    if (treeNodeId == "yydj") {
                        loadDataTablbeByParam("#resultTable", yydjTableConfig,param);
                    }
                }


            },
            error: function (data) {

            }

        });
        function arrayToTree(arr){
            var top = [], sub = [], tempObj = {};
            arr.forEach(function(item){
                if(!item.pId){ // 顶级分类
                    top.push(item);
                    item.spread = true;
                }else{
                    sub.push(item) // 其他分类
                }
                item.children = []; // 默然添加children属性
                tempObj[item.id] = item // 用当前分类的id做key，存储在tempObj中
            })

            sub.forEach(function (item) {
                // 取父级
                var parent = tempObj[item.pId] || {'children': []}
                // 把当前分类加入到父级的children中
                parent.children.push(item)
            })

            return top;
        }


        var cfdjTableConfig = {
            toolbar: "#toolbarSqxx"
            , url: '../cxqq/qlxx/getByPage',
            cols:[[
                {field: 'BDCDYH', title: '不动产单元号'},
                {field: 'ZL', title: '坐落'},
                {field: 'CFJG', title: '查封机关'},
                {field: 'CFWH', title: '查封文号'}
            ]],
            done: function () {
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body').height('56px');
                    $('.layui-table-fixed .layui-table-body').height('56px');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }

            }
        };
        var dyaqTableConfig = {
            toolbar: "#toolbarSqxx"
            , url: '../cxqq/qlxx/getByPage'
            ,cols:[[
                {field: 'BDCDYH', title: '不动产单元号'},
                {field: 'ZL', title: '坐落'},
                {field: 'BDBZZQSE', title: '被担保主债权数额(万元)'},
                {field: 'BDCDJZMH', title: '不动产权证号'}
            ]],
            done: function () {
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body').height('56px');
                    $('.layui-table-fixed .layui-table-body').height('56px');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        };
        var fdcqTableConfig = {
            toolbar: "#toolbarSqxx"
            , url: '../cxqq/qlxx/getByPage'
            ,cols:[[
                {field: 'BDCDYH', title: '不动产单元号'},
                {field: 'FDZL', title: '房地坐落'},
                {field: 'JZMJ', title: '使用权面积'},
                {field: 'BDCQZH', title: '不动产权证号'}
            ]],
            done: function () {
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body').height('56px');
                    $('.layui-table-fixed .layui-table-body').height('56px');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        };
        var gjzwsyqTableConfig = {
            toolbar: "#toolbarSqxx"
            , url: '../cxqq/qlxx/getByPage'
            ,cols:[[
                {field: 'BDCDYH', title: '不动产单元号'},
                {field: 'ZL', title: '坐落'},
                {field: 'GJZWMJ', title: '构（建）筑物面积'},
                {field: 'BDCQZH', title: '不动产权证号'}
            ]],
            done: function () {
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body').height('56px');
                    $('.layui-table-fixed .layui-table-body').height('56px');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        };
        var hysyqTableConfig = {
            toolbar: "#toolbarSqxx"
            , url: '../cxqq/qlxx/getByPage'
            ,cols:[[
                {field: 'BDCDYH', title: '不动产单元号'},
                {field: 'XMMC', title: '项目名称'},
                {field: 'HDMC', title: '海岛名称'},
                {field: 'BDCQZH', title: '不动产权证号'}
            ]],
            done: function () {
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body').height('56px');
                    $('.layui-table-fixed .layui-table-body').height('56px');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        };
        var ysydsyqTableConfig = {
            toolbar: "#toolbarSqxx"
            , url: '../cxqq/qlxx/getByPage'
            ,cols:[[
                {field: 'BDCDYH', title: '不动产单元号'},
                {field: 'ZL', title: '坐落'},
                {field: 'SYQMJ', title: '使用权面积'},
                {field: 'BDCQZH', title: '不动产权证号'}
            ]],
            done: function () {
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body').height('56px');
                    $('.layui-table-fixed .layui-table-body').height('56px');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        };
        var lqTableConfig = {
            toolbar: "#toolbarSqxx"
            , url: '../cxqq/qlxx/getByPage'
            ,cols:[[
                {field: 'BDCDYH', title: '不动产单元号'},
                {field: 'ZL', title: '坐落'},
                {field: 'SYQMJ', title: '使用权（承包）面积(平方米)'},
                {field: 'BDCQZH', title: '不动产权证号'}
             ]],
            done: function () {
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body').height('56px');
                    $('.layui-table-fixed .layui-table-body').height('56px');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        };
        var nydsyqTableConfig = {
            toolbar: "#toolbarSqxx"
            , url: '../cxqq/qlxx/getByPage'
            ,cols:[[
                {field: 'BDCDYH', title: '不动产单元号'},
                {field: 'ZL', title: '坐落'},
                {field: 'CBMJ', title: '承包（使用权）面积(平方米)'},
                {field: 'BDCQZH', title: '不动产权证号'}
            ]],
            done: function () {
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body').height('56px');
                    $('.layui-table-fixed .layui-table-body').height('56px');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        };
        var tdsyqTableConfig = {
            toolbar: "#toolbarSqxx"
            , url: '../cxqq/qlxx/getByPage'
            ,cols:[[
                {field: 'BDCDYH', title: '不动产单元号'},
                {field: 'ZL', title: '坐落'},
                {field: 'ZDMJ', title: '宗地面积(平方米)'},
                {field: 'BDCQZH', title: '不动产权证号'}
             ]],
            done: function () {
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body').height('56px');
                    $('.layui-table-fixed .layui-table-body').height('56px');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        };
        var ygdjTableConfig = {
            toolbar: "#toolbarSqxx"
            , url: '../cxqq/qlxx/getByPage'
            ,cols:[[
                {field: 'BDCDYH', title: '不动产单元号'},
                {field: 'ZL', title: '坐落'},
                {field: 'JZMJ', title: '建筑面积'},
                {field: 'BDCDJZMH', title: '不动产权证号'}
            ]],
            done: function () {
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body').height('56px');
                    $('.layui-table-fixed .layui-table-body').height('56px');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        };
        var yydjTableConfig = {
            toolbar: "#toolbarSqxx"
            , url: '../cxqq/qlxx/getByPage'
            ,cols:[[
                {field: 'BDCDYH', title: '不动产单元号'},
                {field: 'ZL', title: '坐落'},
                {field: 'YYSX', title: '异议事项'},
                {field: 'BDCDJZMH', title: '不动产权证号'}
            ]],
            done: function () {
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body').height('56px');
                    $('.layui-table-fixed .layui-table-body').height('56px');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        };

        function loadDataTablbeByParam(_domId, _tableConfig,_param) {
            layui.use(['table', 'laypage', 'jquery'], function () {
                if (_domId) {
                    var table = layui.table;
                    var $ = layui.jquery;
                    var tableDefaultConfig = {
                        toolbar: true,
                        defaultToolbar: ['filter'],
                        even: true,
                        elem: _domId
                        , cellMinWidth: 80
                        , page: true //开启分页
                        , limit: 10
                        , parseData: function (res) { //res 即为原始返回的数据
                            res.hasContent=true
                            return res;
                        }
                        , request: {
                            limitName: 'size' //每页数据量的参数名，默认：limit
                        }
                        , response: {
                            countName: 'totalElements' //数据总数的字段名称，默认：count
                            , dataName: 'content' //数据列表的字段名称，默认：data
                            , statusName: 'hasContent' //规定数据状态的字段名称，默认：code
                            , statusCode: true //规定成功的状态码，默认：0
                        }
                        ,where : _param
                    };
                    if (!_tableConfig.cols || !_tableConfig.cols[0] || _tableConfig.cols[0].length < 1) {
                        _tableConfig.cols = [[]]
                    }
                    var tableRenderConfig = $.extend({}, tableDefaultConfig, _tableConfig);
                    table.render(tableRenderConfig);
                }
            });
        }



    });
});