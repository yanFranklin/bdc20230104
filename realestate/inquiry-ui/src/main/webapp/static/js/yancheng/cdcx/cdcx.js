var reverseList = ['zl'];
var searchData, searchFilterData, needsearch, needFilter;
// 当前页数据
var currentPageData = new Array();
var form, table;
var qlrzjh = getQueryString("qlrzjh");
var bdcdyh = getQueryString("bdcdyh");

var qlrmc = decodeURI(getQueryString("qlrmc"));
var zl = decodeURI(getQueryString("zl"));
var bdcqzh = decodeURI(getQueryString("bdcqzh"));
var dylx = getQueryString("dylx");

layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        laydate = layui.laydate;
    form = layui.form;
    table = layui.table;


    $(function () {

        // 加载Table
        table.render({
            elem: '#pageTable',
            toolbar: '#toolbarDemo',
            title: '查档信息列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                 {type: 'checkbox', fixed: 'left'},
                {field: 'xh', title: '序号', width: 50, type: 'numbers'},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
                {field: 'zl', title: '坐落', minWidth: 300},
                {field: 'qlrmc', title: '权利人名称', width: 250},
                {field: 'qlrzjh', title: '权利人证件号', width: 200},
                {field: 'bdcqzh', title: '不动产权证号', minWidth: 280},
                {
                    field: 'qszt', width: 110, title: '权属状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        return formatQsztWithCx(d.qszt, d.ajzt);
                    }
                },
                {
                    field: 'bdcdyZtDTO', width: 100, title: '限制状态', fixed: 'right', sort: true,
                    templet: function (d) {
                        return formatXzzt(d.bdcdyZtDTO);
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
                            if (key == v.sfxxid) {
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
            }
        });

        // 监听表格操作栏按钮
        table.on('toolbar(pageTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'print':
                    printCdjg(checkStatus.data, obj.config.cols[0]);
                    break;
            }
        });


        search();

        // 查询处理逻辑
        function search() {
            var obj = new Object();
            if(isNotBlank(bdcqzh) && 'null'!=bdcqzh){
                obj.bdcqzh = bdcqzh;
                obj.bdcqzhmhlx = 3;
            }
            if(isNotBlank(qlrmc) && 'null'!=qlrmc){
                obj.qlrmc = qlrmc;
                obj.qlrmcmhlx = 0;
            }
            if (isNotBlank(qlrzjh)) {
                obj.qlrzjh = qlrzjh;
                obj.qlrzjhmhlx = 0;
            }
            if (isNotBlank(zl) && 'null' != zl) {
                obj.zl = zl;
                obj.zlmhlx = 3;
            }
            if (isNotBlank(bdcdyh) && 'null' !== bdcdyh) {
                obj.bdcdyh = bdcdyh;
                obj.bdcdyhmhlx = 0;
            }
            obj.qszt3 = 1;
            addModel();
            // 重新请求
            table.reload("pageTable", {
                url: getContextPath() + "/rest/v1.0/zszm",
                where: obj,
                page: {
                    curr: 1,  //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                },
                done: function (res, curr, count) {
                    removeModal();
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

function printCdjg(data, cols){
    if(!isNotBlank(dylx)){
        warnMsg(" 未获取到打印类型！");
        return;
    }
    if ($.isEmptyObject(data)) {
        warnMsg(" 请选择需要打印的记录！");
        return;
    }

    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zhcx/bzb/cdxxcxjg",
        type: "POST",
        data: JSON.stringify(data),
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            if (data && data.redisKey) {
                var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/zhcx/bzb/cdxxcx/" + data.redisKey + "/"+ dylx +"/xml";
                var modelUrl = getIP() + "/realestate-inquiry-ui/static/printModel/"+ dylx +".fr3"
                print(modelUrl, dataUrl, false);

                $.each(data.param, function(i,val){
                    savePrintInfo(modelUrl, dataUrl, {'zmbh': val.cxh, "printType": getPrintType(dylx)});
                });
            } else {
                failMsg("打印查档结果失败，请重试！");
            }
        },
        error: function () {
            removeModal();
            failMsg("获取查档结果信息失败，请重试！");
        },
        complete: function () {
            removeModal();
        }
    });
}

// 获取打印类型中文名称
function getPrintType(dylx){
    var printType = "";
    switch(dylx){
        case "qlrcdjg":
            printType = "权利人查档结果";
            break;
        case "lhgxcdjg":
            printType = "利害关系查档结果";
            break;
        case "zlhgxcdjg":
            printType = "准利害关系查档结果";
            break;
    }
    return printType;
}


/**
 * 保存打印信息
 * @param modelUrl 模板路径
 * @param dataSourceUrl 模板数据源
 * @param privateAttrMap 私有信息
 */
function savePrintInfo(modelUrl,dataSourceUrl,privateAttrMap){
    // 为了保存数据的准确性，保存的时候就即时查询出打印的xm了，保存至大云
    var xmlStr = getXmlStr(dataSourceUrl);
    $.ajax({
        url: '/realestate-inquiry-ui/log/savePrintInfo',
        dataType: "json",
        type:"POST",
        data: {'modelUrl':modelUrl,'dataUrl':dataSourceUrl,'xmlStr':xmlStr,'privateAttrMap':privateAttrMap},
        success: function (data) {
        }
    });
}

/**
 * 请求保存的xml
 * @param dataSourceUrl
 * @returns {string}
 */
function getXmlStr(dataSourceUrl){
    var str = "";
    $.ajax({
        url: dataSourceUrl,
        async:false,
        success: function (data) {
            str = data;
        }
    });
    return str;
}
