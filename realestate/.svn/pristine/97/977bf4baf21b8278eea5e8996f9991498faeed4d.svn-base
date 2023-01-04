/**
 * author: <a href="mailto:huangjian@gtmap.cn>huangjian</a>
 * version 1.0.0  2020/03/16
 * describe: 抵押单元信息查询-标准版
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

// 用户IP地址
var ipaddress;
var zdList = getMulZdList("dyfs");
var reverseList = ['zl'];
// 分页重置查询参数
var searchParam =[];
// var dcts;
layui.use(['jquery', 'element', 'form', 'table', 'laydate', 'formSelects'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var formSelects = layui.formSelects;

    form.render();

    // 当前页数据
    var currentPageData = new Array();
    $(function () {
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');
        // 加载Table
        table.render({
            elem: '#bdcdyTable',
            toolbar: '#toolbarDemo',
            even: true,
            loadTotal: true,
            title: '抵押信息列表',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'xmid', title: '项目编号', width: 200, align: 'center', hide: true},
                {field: 'bdcqzh', minWidth: 280, title: '抵押证明号'},
                {field: 'ycqzh', minWidth: 280, title: '产权证号', templet: function(d){
                        return queryCqBdcqzh(d);
                }},
                {field: 'qszt', width: 120, title: '权属状态', sort: true, templet: function (d) {
                        return formatQszt(d.qszt);
                    }
                },
                {field: 'qlr', title: '权利人', width: 250, align: 'center'},
                {field: 'qlrzjh', title: '权利人证件号', width: 250, align: 'center', hide: true},
                {field: 'ywr', title: '义务人', width: 200, align: 'center'},
                {field: 'ywrzjh', title: '义务人证件号', width: 250, align: 'center', hide: true},
                {field: 'bdcdyh', minWidth: 280, title: '不动单元号'},
                {field: 'zl', minWidth: 250, title: '坐落'},
                {field: 'dyfs', title: '抵押方式', width: 150, align: 'center',
                    templet: function (d) {
                        if(isNotBlank(d.dyfs)){
                            return convertZdDmToMc('dyfs', d.dyfs, "zdList");
                        }else{
                            return "";
                        }
                    }},
                {field: 'zwlxqssj', title: '抵押起始时间', width: 150, align: 'center'},
                {field: 'zwlxjssj', title: '抵押结束时间', width: 150, align: 'center'},
                {field: 'bdbzzqse', title: '被担保主债权数额', width: 150, align: 'center'},
                {field: 'dbr', title: '登簿人', width: 150, align: 'center'},
                {field: 'djsj', title: '登记时间', width: 180, align: 'center', templet: function (d) {
                        return formatDate(d.djsj);}
                },
                {title: '查看', fixed: 'right', renderer: 'detailView', toolbar: '#barDemo', width: 100}
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                // 设置选中行
                if(res.content && res.content.length > 0){
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function(key, value){
                            if(key == v.xmid){
                                v.LAY_CHECKED = true;
                            }
                        })
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
                setHeight();
                reverseTableCell(reverseList);
                // 控制按钮权限
                if(moduleCode){
                    setElementAttrByModuleAuthority(moduleCode);
                }
            }
        });
        ydslcxtj();
        // 获取配置项
        // $.ajax({
        //     url: "/realestate-inquiry-ui/rest/v1.0/dya/pz",
        //     type: "GET",
        //     dataType: 'json',
        //     async: false,
        //     success: function (res) {
        //         if (res) {
        //             dcts = res;
        //         } else {
        //             failMsg("发现页面未配置正确，请联系管理员解决！");
        //         }
        //     },
        //     error: function () {
        //         failMsg("发现页面未配置正确，请联系管理员解决！");
        //     }
        // });

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
            // 每次查询清除下导出缓存数据
            layui.sessionData("checkedData", null);

            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });
            var key = formSelects.value('select07', 'val');

            if (0 == count) {
                layer.alert("<div style='text-align: center'>请输入必要查询条件！</div>", {title: '提示'});
                return false;
            }

            // 获取查询参数
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });

            searchParam = obj;
            obj.moduleCode=moduleCode;
            if(isNotBlank(obj.ygzmh) && isNotBlank(obj.bdcqzh)){
                ityzl_SHOW_INFO_LAYER("不支持预告证明号与抵押证明号同时查询。");
                return false;
            }
            addModel();
            // 重新请求
            table.reload("bdcdyTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/dya/standard/listDyaByPage",
                where: obj,
                loadTotal: true,
                even: true,
                page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                },
                done: function (res, curr, count) {
                    currentPageData = res.data;
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();
                    //添加已选条目数
                    var checkedData = layui.sessionData('checkedData');
                    var arrcheck = Object.keys(checkedData);
                    var total = arrcheck.length;
                    $('#choosecount').html("已选"+total+"条");
                }
            });
        }

        //监听工具条
        table.on('tool(bdcdyTable)', function (obj) {
            var data = obj.data;
            if (isNullOrEmpty(data.bdcdyh)) {
                warnMsg(" 没有不动产单元信息，无法查看！");
                return;
            }

            if (obj.event === 'xq') {
                window.open(htmlConfig.qlxxHtml.dyaqHtml+'?xmid='+data.xmid +'&readOnly=true&isCrossOri=false');
                saveDetailLog("DYCX_CKDJB","抵押查询_查看详情", data)
            }
        });

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

        //点击高级查询--4的倍数
        $('#seniorSearch').on('click', function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            $(this).parent().toggleClass('bdc-button-box-four');

            if ($(this).html() == '高级查询') {
                $(this).html('收起');
            } else {
                $(this).html('高级查询');
            }

            $('.bdc-percentage-container').css('padding-top', $('.bdc-search-content').height() + 10);
            $('.layui-table-body').height($('.bdc-table-box').height() - 129);
            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 129 - 17);

            $('.pf-senior-show-normal').toggleClass('bdc-hide');
            $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);

            if($(this).html() == '高级查询'){
                $(this).html('收起');
            }else {
                $(this).html('高级查询');
            }

            if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
            }else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        });

        //url携带参数bdcdyh时，自动查询
        var bdcdyParam = $.getUrlParam("bdcdyh");
        if (bdcdyParam) {
            $("input[name='bdcdyh']").val(bdcdyParam);
            search();
        }
    });

    // 监听表格操作栏按钮
    table.on('toolbar(bdcdyTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'exportExcel':
                if (searchParam.length == 0){
                    // layer.alert("请先查询数据！", {title: '提示'});
                    warnMsg("请选择需要导出Excel的数据行！");
                    return
                }
                if (checkStatus.data.length  > 0) {
                    exportExcel(checkStatus.data, obj.config.cols[0],"checked");
                } else {
                    exportAllExcel(checkStatus.data, obj.config);
                }
                break;
            case 'exportAllExcel':
                layer.confirm("是否导出全部数据？",{
                    title: "提示",
                    btn: ["确认","取消"],
                    btn1: function (index) {
                        exportAll(checkStatus.data, obj.config);
                        layer.close(index);
                    },
                    btn2: function (index) {
                        obj.config.where.type = "";
                        return;
                    }
                })
                break;
        }
    });

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(bdcdyTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;
        $.each(data, function (i, v) {
            if (obj.checked) {
                //.增加已选中项
                layui.sessionData('checkedData', {
                    key: v.xmid, value: v
                });
            } else {
                //.删除
                layui.sessionData('checkedData', {
                    key: v.xmid, remove: true
                });
            }
        });
        //添加已选条目数
        var checkedData = layui.sessionData('checkedData');
        var arrcheck = Object.keys(checkedData);
        var total = arrcheck.length;
        $('#choosecount').html("已选"+total+"条");
    });

    // 导出Excel
    // 通过隐藏form提交方式，避免ajax方式不支持下载文件
    function exportExcel(d, cols, type) {
        var checkedData = layui.sessionData('checkedData');
        if ($.isEmptyObject(checkedData) && type == "checked") {
            warnMsg("请选择需要导出Excel的数据行！");
            return;
        }

        // 标题
        var showColsTitle = new Array();
        // 列内容
        var showColsField = new Array();
        // 列宽
        var showColsWidth = new Array();
        for (var index in cols) {
            if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
                showColsTitle.push(cols[index].title);
                showColsField.push(cols[index].field);
                if (isNullOrEmpty(cols[index].width)) {
                    showColsWidth.push(30);
                } else {
                    showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                }
            }
        }

        // 设置Excel基本信息
        $("#fileName").val('抵押查询信息导出Excel表');
        $("#sheetName").val('抵押查询信息导出Excel表');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);

        var data = new Array();
        if (type == "checked") {
            $.each(checkedData, function (key, value) {
                data.push(value);
            })
        } else {
            data = d;
        }

        for (var i = 0; i < data.length; i++) {
            data[i].xh = i + 1;
            data[i].bdcdyZtDTO = formatXzzt(data[i].bdcdyZtDTO).replace(/<span\s*[^>]*>(.*?)<\/span>/ig,"$1");
            data[i].qszt = formatQszt(data[i].qszt).replace(/<span\s*[^>]*>(.*?)<\/span>/ig,"$1");
            data[i].djsj = formatDate(data[i].djsj);
            data[i].dyfs = formatDyfs(data[i].dyfs);
        }
        $("#data").val(JSON.stringify(data));
        saveDetailLog("DYCX_EXPORT","抵押查询-导出excel",{"DCNR":data});
        $("#form").submit();
    }

    function exportAll(data, obj) {
        var cols = obj.cols[0]
        var size = obj.page.count;
        var url = obj.url+"?page=0&&size="+size;
        // if (dcts > 0) {
        //     size = dcts;
        //     url = obj.url+"?page=0&&size="+size;
        //
        // }
        var paramData = obj.where;
        paramData["type"] = "exportAll";
        $.ajax({
            url: url,
            dataType: "json",
            data: paramData,
            success: function (data) {
                if(data.code == 0){//查询成功
                    exportExcel(data.content,cols, "all");
                }
            }
        });
    }

    // 导出所有查询结果Excel
    function exportAllExcel(data, obj) {
        layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要导出Excel的数据行!');
        // layer.confirm("是否导出全部数据？", {
        //     title: "提示",
        //     btn: ["确认", "取消"],
        //     btn1: function (index) {
        //         var cols = obj.cols[0]
        //         var url = obj.url;
        //         var paramData = obj.where;
        //         paramData["type"] = "export";
        //
        //         $.ajax({
        //             url: url,
        //             dataType: "json",
        //             data: paramData,
        //             success: function (data) {
        //                 obj.where.type = "";
        //                 if(data.length > 0){//查询成功
        //                     exportExcel(data,cols);
        //                 }
        //             }
        //         });
        //
        //         layer.close(index);
        //     },
        //     btn2: function (index) {
        //         obj.where.type = "";
        //         return;
        //     }
        // });
    }

    // 保存记录操作信息
    function saveDetailLog(logType, viewTypeName, data){
        var json = JSON.parse(JSON.stringify(data));
        json.logType = logType;
        json.ipaddress = ipaddress;
        json.viewTypeName = viewTypeName;
        saveLogInfo(json);
    }

    //获取产权的不动产证号信息
    function queryCqBdcqzh(d){
        var queryParam  = {
            xmid : d.xmid,
            qllx: d.qllx,
            bdcdyh: d.bdcdyh
        };
        var cqbdcqzh="";
        $.ajax({
            url: getContextPath() + "/rest/v1.0/dya/cqbdcqzh",
            type: 'GET',
            data : queryParam,
            dataType: 'text',
            async: false,
            success: function (data) {
                if(isNotBlank(data)){
                    cqbdcqzh = data
                }
            },
            error: function (xhr, status, error) {
            }
        });
        return cqbdcqzh;
    }

    //异地受理角色展示部分查询条件
    function ydslcxtj(){
        var queryParam  = {
            cxym : "dyacx",
        };
        $.ajax({
            url: getContextPath() + "/rest/v1.0/dya/ydslcxtj",
            type: 'GET',
            data : queryParam,
            dataType: 'text',
            async: false,
            success: function (data) {
                if(isNotBlank(data)){
                    // 需要展示的查询条件
                    var showCxtj = data.split(",");
                    // 获取所有查询条件并且遍历
                    $(".cxtj").each(function(){
                        if(!(showCxtj.indexOf($(this).attr("id")) > -1)){
                            $(this).hide();
                        }
                    });
                    if(showCxtj.length < 6){
                        $("#seniorSearchNormal").hide();
                    }
                    // 查询条件小于等于2时需要将下面的表格往上移动
                    if(showCxtj.length <= 2){
                        $(".bdc-percentage-container").css("padding-top","61px");
                    }
                }
            },
            error: function (xhr, status, error) {
            }
        });
    }

});