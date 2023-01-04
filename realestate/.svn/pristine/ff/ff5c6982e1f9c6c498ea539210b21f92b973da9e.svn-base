var tablexnbdcdyhs= [];
layui.use(['jquery', 'table', 'element', 'form', 'laytpl', 'laydate', 'layer','response'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        laydate = layui.laydate,
        response = layui.response;
    $(function () {

        //监听台账查询 input 点击
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

        // 渲染表格
        var waitUrl = getContextPath() + "/rest/v1.0/task/clxnbdcdy/list";
        var waitTableId = '#waitTable';
        var waitCurrentId = 'dbTable';
        var waitToolbar = '#toolbarDemo';
        renderWaitTable(waitUrl, waitTableId, waitCurrentId, waitToolbar);


        // 待办任务头工具栏事件
        table.on('toolbar(waitTableFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            // 过滤了右侧的筛选事件
            if (obj.event != "LAYTABLE_COLS") {
                var selectedNum = checkStatus.data.length;
                if (obj.event === 'autoMatch' && selectedNum == 0) {
                    warnMsg("请选择数据进行操作！");
                    return false;
                }else if(obj.event === 'manualMatch' && selectedNum!=1){
                    warnMsg("请选择一条数据进行操作！");
                    return false;
                }else if(obj.event === 'cancelMatch' && selectedNum == 0){
                    warnMsg("请选择数据进行操作！");
                    return false;
                }
            }
            if (obj.event === 'autoMatch') {
                // 判断是否可以匹配
                checkMatch(checkStatus);
            }else if(obj.event === 'manualMatch'){
                var checkResult=true;
                var checkData=checkStatus.data[0];
                var bdcdyh = checkData.bdcdyh;
                if(checkXn(bdcdyh)=="否"){
                    warnMsg("非虚拟单元号,不能匹配！");
                    checkResult=false;
                }
                if(checkResult){
                    var index = layer.open({
                        title: '手动匹配单元页面',
                        type: 2,
                        area: ['960px', '350px'],
                        content: ['./sdPpBdcdyh.html?xnbdcdyh=' + bdcdyh]
                    });
                    layer.full(index);
                }
            }else if(obj.event === 'cancelMatch'){
                cancelMatch(checkStatus);
            }
        });

        //查询
        $('#dbSearch').on('click', function () {
            addModel();
            var obj={};
            // 获取查询内容
            $(".dbSearch").each(function () {
                var value = $(this).val();
                var name = $(this).attr('name');
                obj[name] = value;
            });
            if(obj["xndyh"]=="1"){
                obj["xndyh"]=true;
            }else{
                obj["xndyh"]=false;
            }
            // 重新请求
            table.reload("dbTable", {
                where: obj,
                page: {
                    curr: 1  //重新从第 1 页开始
                },
                done: function(){
                    tableDone();
                }
            });
        });

        /**
         * 复制反转的 坐落
         */
        $('.bdc-table-box').on('mouseenter', 'td', function () {
            if ($(this).text() && $(this).attr("data-field") == "zl") {
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>')
            }
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    if (!isNullOrEmpty($('.layui-table-tips-main .bdc-table-date').html())) {
                        $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                    }
                }, 20);
            });
        });

    });

    function cancelMatch(checkStatus) {
        var checkResult = true;
        var bdcdyhs = [];
        for(var j=0;j<checkStatus.data.length;j++){
            var bdcdyh = checkStatus.data[j].bdcdyh;
            if(checkXn(bdcdyh)=="是"){
                warnMsg("数据中存在虚拟单元号,不能取消匹配！");
                checkResult=false;
                break;
            }
            if(bdcdyhs.indexOf(bdcdyh)==-1){
                bdcdyhs.push(bdcdyh+";"+checkStatus.data[j].xmid);
            }
        }
        if (!checkResult) {
            return false;
        }
        match(bdcdyhs,true);
    }

    function checkMatch(checkStatus) {
        var checkResult = true;
        var xnbdcdyhs = [];
        for(var j=0;checkStatus.data.length;j++){
            var bdcdyh = checkStatus.data[j].bdcdyh;
            if(checkXn(bdcdyh)=="否"){
                warnMsg("数据中存在非虚拟单元号,不能匹配！");
                checkResult=false;
                break;
            }
            var isPp = $("."+ bdcdyh).eq(0).text();
            if (!isNullOrEmpty(isPp) && isPp === '可匹配') {
                if(xnbdcdyhs.indexOf(bdcdyh)==-1){
                    xnbdcdyhs.push(bdcdyh);
                }
            } else if (isPp === '加载中') {
                warnMsg("数据加载中！");
                checkResult=false;
                break;
            } else {
                warnMsg("此数据不可以匹配！");
                checkResult=false;
                break;
            }
        }
        if (!checkResult) {
            return false;
        }
        match(xnbdcdyhs,false);
    }

    /**
     * 自动匹配虚拟不动产单元号
     * @param gzlslid
     */
    function match(xnbdcdyhs,qxpp) {
        if(qxpp){
            addModel("取消匹配中");
        }else{
            addModel("自动匹配中");
        }
        $.ajax({
            url: getContextPath() + "/rest/v1.0/task/matchClData",
            type: 'GET',
            dataType: 'json',
            traditional: true,
            data: {xnbdcdyhs: xnbdcdyhs,qxpp:qxpp},
            success: function () {
                removeModal();
                if(qxpp){
                    layer.msg("取消匹配成功！");
                }else {
                    layer.msg("匹配成功！");
                }
                // 模拟查询点击事件刷新页面
                $('#dbSearch').click();
            }, error: function (e) {
                removeModal();
                response.fail(e, '');
            }
        });
    }

    /**
     * 表格加载完成后渲染
     */
    function tableDone() {
        removeModal();
        tablexnbdcdyhs.forEach(function (xnbdcdyh) {
            if(checkXn(xnbdcdyh)=="是"){
                yzQj(xnbdcdyh);
            }else{
                $("."+ xnbdcdyh).html('<p class="bdc-table-state-th">不可匹配</p>')
            }
        });
        $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

        $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
        if ($('.layui-table-body>.layui-table').height() == 0) {
            $('.layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
        }
        var reverseList = ['zl'];
        reverseTableCell(reverseList);
    }

    //渲染表格
    function renderWaitTable(url, tableId, currentId, toolbar) {
        addModel();
        var obj={};
        obj["xndyh"]=true;
        table.render({
            elem: tableId,
            id: currentId,
            url: url,
            toolbar: toolbar,
            title: '虚拟单元号匹配台账',
            method: 'post',
            even: true,
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: false,
                loadTotalBtn: false
            },
            where:obj,
            limits: [10,30,50,70,90,110,130,150],
            defaultToolbar: ['filter'],
            cols: [[{type: 'checkbox', fixed: 'left'},
                {field: 'slbh', title: '受理编号', width: "10%"},
                {field: 'bdcdyh', title: '不动产单元号', width: "22%", templet: '#bdcdyhTpl'},
                {field: 'bdcqzh', title: '不动产权证（明）号', width: "22%"},
                {field: 'qllx', title: '权利类型', width: "22%", hide: 'true'},
                {field: 'xmid', title: '项目ID', width: "10%", hide: 'true'},
                {field: 'zl', title: '坐落', width: "16%"},
                {field: 'qlrmc', title: '权利人', width: "11.4%"},
                {minWidth: 100, title: '是否虚拟', templet: '#isXn'},
                {minWidth: 75, title: '可匹配', templet: '#isPpTpl'},
                {field: 'ycqzh', title: '原产权证号', width: "22%"},
                {field: 'yxtcqzh', title: 'TT系统证号', width: "22%"}
            ]],
            parseData: function (res) { //res 即为原始返回的数据
                tablexnbdcdyhs = [];
                res.content.forEach(function (v) {
                    tablexnbdcdyhs.push(v.bdcdyh);
                });
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            page: true,
            done: function () {
                tableDone();
            }
        });
    }
});

// 调用权籍服务，判断是否可以匹配
function yzQj(xnbdcdyh) {
    $.ajax({
        url: getContextPath() + "/rest/v1.0/task/sflz?xnbdcdyh=" + xnbdcdyh,
        type: 'GET',
        dataType: 'json',
        async: true,
        success: function (data) {
            if (data !== true) {
                $("."+ xnbdcdyh).html('<p class="bdc-table-state-th">不可匹配</p>');
            } else {
                $("."+ xnbdcdyh).html('<p class="bdc-table-state-jh">可匹配</p>');
            }
        }
    });
}


function renderTable() {
    alert("+++1");
    $('#dbSearch').click();
}
