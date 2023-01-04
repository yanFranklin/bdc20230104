var reverseList = ['zl'];
var searchData, searchFilterData, needsearch, needFilter;
// 当前页数据
var currentPageData = new Array();
var form, table;
// 获取参数
var gzlslid = $.getUrlParam('gzlslid');
// lhsdqlzt(量化首登权利状态)查询未量化首登的自然幢数据， lhdyqlzt（量化抵押权利状态）
var lhdyqlzt = $.getUrlParam('lhdyqlzt');
var lhsdqlzt = $.getUrlParam('lhsdqlzt');
var zdList = getZdList();
layui.use(['form', 'jquery', 'element', 'table'], function () {
    var $ = layui.jquery;
    form = layui.form;
    table = layui.table;

    // 清空已选缓存
    layui.sessionData("checkedData", null);
    $(function () {
        // 加载Table
        table.render({
            elem: '#zrzListTable',
            toolbar: '#toolbarDemo',
            title: '自然幢信息列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'lszd', title: '地籍号', width: 280},
                {field: 'zrzh', title: '自然幢号', width: 120},
                {field: 'dh', title: '幢号', width: 120},
                {
                    field: 'gcjd', width: 150, title: '工程进度', templet: function (d) {
                        if (zdList && zdList.gcjd && d.gcjd) {
                            var value = '';
                            for (var index in zdList.gcjd) {
                                if (zdList.gcjd[index].DM == d.gcjd) {
                                    value = zdList.gcjd[index].MC;
                                }
                            }
                            return value;
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'ghjzmj', title: '规划建筑面积', width: 200},
                {field: 'scjzmj', title: '实测建筑面积', width: 200},
                {field: 'sfyg', title: '是否预告', width: 120, templet: function (d) {
                        if(d.sfyg > 0){
                            return "是";
                        }else{
                            return "否";
                        }
                    }},
                {field: 'lhsdqlzt', title: '是否首登', width: 120, templet: function (d) {
                        if(d.lhsdqlzt > 0){
                            return "是";
                        }else{
                            return "否";
                        }
                    }
                },
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                // 设置选中行
                if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.fwJsydzrzxxIndex) {
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
                choose();
                setHeight();
                reverseTableCell(reverseList);
            }
        });

        // 监听表格操作栏按钮
        table.on('toolbar(zrzListTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'save':
                    saveZrz();
                    break;
            }
        });

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        // 查询处理逻辑
        function search() {
            layui.sessionData("checkedData", null);

            addModel();

            // 重新请求
            var obj = {
                lszd : $("#lszd").val(),
            }
            if(isNotBlank(lhsdqlzt)){
                //查询未量化首登权利状态的数据
                obj.lhsdqlzt = lhsdqlzt;
            }
            if(isNotBlank(lhdyqlzt)){
                //查询在抵押范围内的楼幢数据
                obj.lhdycs = lhdyqlzt;
            }
            table.reload("zrzListTable", {
                url: getContextPath()+ "/rest/v1.0/jsyd/lhxx/page",
                where: obj,
                page: {
                    curr: 1,  //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                },
                done: function (res, curr, count) {
                    removeModel();
                    if(res.code ==0){
                        currentPageData = res.data;
                        reverseTableCell(reverseList);
                        setHeight();
                    }else{
                        $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + res.msg);
                    }
                }
            });
        }

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(zrzListTable)', function (obj) {
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;

            $.each(data, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedData', {
                        key: v.fwJsydzrzxxIndex, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedData', {
                        key: v.fwJsydzrzxxIndex, remove: true
                    });
                }
            });
            choose();
        });


        //监听滚动事件
        var scrollTop = 0,
            scrollLeft = 0;
        var tableTop = 0, tableLeft = 0;
        var $nowBtnMore = '';
        //点击表格中的更多
        $('.bdc-table-box').on('click','.bdc-more-btn',function (event) {
            tableTop = $(this).offset().top;
            tableLeft = $(this).offset().left;
            event.stopPropagation();
            $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
            var $btnMore = $(this).siblings('.bdc-table-btn-more');
            if($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight){
                $btnMore.css({top: tableTop + 26 -scrollTop,left:tableLeft - 30});
            }else {
                $btnMore.css({top: tableTop -scrollTop - $btnMore.height(),left: tableLeft - 30});
            }
            $('.bdc-table-btn-more').hide();
            $btnMore.show();
        });

        //点击更多内的任一内容，隐藏
        $('.bdc-table-btn-more a').on('click',function () {
            $(this).parent().hide();
        });
        //点击页面任一空白位置，消失
        $('body').on('click',function () {
            $('.bdc-table-btn-more').hide();
            $('.bdc-table-top-more-show').hide();
        });

        // 设置列表高度
        function setHeight(searchHeight) {
            if (isNullOrEmpty(searchHeight)) {
                searchHeight = 131;
            }
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

            if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
            }else {
                $('.layui-table-body').height($('.bdc-table-box').height() - searchHeight);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - searchHeight - 17);
            }
        }
    });
});

function choose(){
    var checkedData = layui.sessionData('checkedData');
    $('.bdc-table-box').find('#choose').html(Object.keys(checkedData).length);
}


function saveZrz(){
    var checkedData = layui.sessionData('checkedData');
    console.info(checkedData);
    if(Object.keys(checkedData).length ===0){
        warnMsg("请先选择自然幢在进行保存");
        return;
    }
    var fwJsydzrzxxIndexArray = new Array();
    var djh = "";
    var dhNotNull = true;
    $.each(checkedData, function(key, value){
        if(!isNotBlank(value.dh)){
            dhNotNull = false;
        }
        djh = value.lszd;
        fwJsydzrzxxIndexArray.push(key);
    });

    if(!dhNotNull){
        warnMsg("选择的楼幢存在幢号为空的数据，请重新选择。");
        return;
    }
    checkLjzZt(fwJsydzrzxxIndexArray, djh).done(function(data){
        if(isNotBlank(data)){
            layer.confirm(data, {
                title: "提示",
                btn: ["是", "否"],
                btn1: function (index) {
                    saveData(fwJsydzrzxxIndexArray);
                    layer.close(index)
                },
                btn2: function (index) {
                    layer.close(index)
                }
            });
        }else{
            saveData(fwJsydzrzxxIndexArray);
        }
    });

}

function saveData(fwJsydzrzxxIndexArray){
    addModel();
    $.ajax({
        url: getContextPath()+"/rest/v1.0/jsyd/lhxx",
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({jsydzrzxxidList : fwJsydzrzxxIndexArray, gzlslid:gzlslid}),
        success: function (data) {
            removeModel();
            successMsg("保存成功");
            // 关闭当前弹出
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            // 刷新父页面
            if(typeof (eval(window.parent.getLhxx)) == "function"){
                window.parent.getLhxx();
            }
        },
        error: function (err) {
            removeModel();
            delAjaxErrorMsg(err);
        }
    });
}

function checkLjzZt(fwJsydzrzxxIndexArray, djh) {
    var deferred = $.Deferred();
    addModel();
    $.ajax({
        url: getContextPath()+"/rest/v1.0/jsyd/lhxx/checkWgxLzj",
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({jsydzrzxxidList : fwJsydzrzxxIndexArray, djh: djh, gzlslid: gzlslid}),
        success: function (data) {
            removeModel();
            deferred.resolve(data);
        },
        error: function (err) {
            removeModel();
            delAjaxErrorMsg(err);
            deferred.reject();
        }
    });
    return deferred;
}