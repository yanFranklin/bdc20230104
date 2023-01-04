layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var reverseList = ['zl'];
var searchData, searchFilterData, needsearch, needFilter;
// 当前页数据
var currentPageData = new Array();
var zdList = {};
var yjddMax = -1;
var form, table, formSelects;
layui.use(['form', 'jquery', 'laydate', 'element', 'table', 'formSelects'], function () {
    var $ = layui.jquery,
        laydate = layui.laydate;
    form = layui.form;
    table = layui.table;
    formSelects = layui.formSelects;

    // 日期控件
    lay('.test-item').each(function(){
        //初始化日期控件
        var startDate = laydate.render({
            elem: '#kssj',
            trigger: 'click',
            /*type: 'datetime',*/
            format: 'yyyy-MM-dd',
            done: function(value,date){
                //选择的结束时间大
                if($('#jssj').val() != '' && !completeDate($('#jssj').val(),value)){
                    $('#jssj').val('');
                    $('.laydate-disabled.layui-this').removeClass('layui-this');
                    layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                }
                endDate.config.min ={
                    year: date.year,
                    month: date.month-1,
                    date: date.date
                }
            }
        });
        var endDate = laydate.render({
            elem: '#jssj',
            trigger: 'click',
            /*type: 'datetime',*/
            format: 'yyyy-MM-dd',
        });

    });

    // 下拉选择添加删除按钮
    renderSelectClose(form);

    $(function () {

        //加载字典项
        getReturnData("/bdczd",{},"POST",function (data) {
            zdList = data;
        },function () {
        },false);

        getReturnData("/rest/v1.0/yjsfxx/yjdd/max",{},"GET",function (data) {
            if(isNotBlank(data)){
                yjddMax = data;
            }
        },function () {
        },false);

        // 加载银行机构
        loadXtjg();
        // 加载区县代码
        laodQxdm();
        // 加载流程名称
        loadProcessDefName();

        // 加载Table
        table.render({
            elem: '#pageTable',
            toolbar: '#toolbarDemo',
            title: '月结收费信息列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                 {type: 'checkbox', fixed: 'left'},
                {field: 'xh', title: '序号', width: 50, type: 'numbers'},
                {field: 'slbh', title: '受理编号', minWidth: 150},
                {field: 'yjdh', title: '月结单号', minWidth: 150},
                {field: 'bdcqzh', title: '不动产权证号', minWidth: 280},
                {field: 'gzldymc', title: '流程名称', minWidth: 180},
                {field: 'ywr', title: '义务人', width: 250},
                {field: 'ywrzjh', title: '义务人证件号', width: 200},
                {field: 'zl', title: '坐落', minWidth: 300},
                {field: 'yt', title: '用途', width: 100, templet: function (d) {
                        return changeDmToMc(d.yt, zdList.fwyt);
                    }},
                {field: 'yt', title: '收费状态', width: 100, templet: function (d) {
                        if(d.sfzt){
                            return changeDmToMc(d.sfzt, zdList.sfzt);
                        }
                        return "未缴费";
                    }},
                {field: 'sfxmmc', title: '缴费项目', width: 250},
                {field: 'dj', title: '单价', width: 250},
                {field: 'sfxmsl', title: '数量', width: 100},
                {field: 'sfxmhj',  title: '合计', width: 100},
                {field: 'jfsewmurl',  title: '缴款识别码', width: 200},
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
                setBtnAuth($("#sfzt").val());
            }
        });

        // 监听表格操作栏按钮
        table.on('toolbar(pageTable)', function (obj) {
            var checkedData = layui.sessionData('checkedData');
            var data = new Array();
            $.each(checkedData, function(key,value){
                data.push(value);
            });
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel(data, obj.config.cols[0]);
                    break;
                case 'smxd':
                    smxd(data, obj.config.cols[0]);
                    break;
                case 'posxd':
                    posxd(data, obj.config.cols[0]);
                    break;
                case 'smbd':
                    smbd(data, obj.config.cols[0]);
                    break;
                case 'queryJfzt':
                    queryJfzt(data, obj.config.cols[0]);
                    break;
                case 'tzzrpt':
                    tzzrpt(data, obj.config.cols[0]);
                    break;
                case 'tsjk':
                    tsjk(data, obj.config.cols[0]);
                    break;
                case 'tksq':
                    tksq(data, obj.config.cols[0]);
                    break;
                case 'tkcx':
                    tkcx(data, obj.config.cols[0]);
                    break;
            }
        });

        // 扫码缴费
        function smxd(checkData, cols){
            if(checkData.length == 0){
                ityzl_SHOW_WARN_LAYER("请先选择一条收费信息，在进行操作。");
                return;
            }
            if(yjddMax !=-1  && yjddMax < checkData.length){
                ityzl_SHOW_WARN_LAYER("当前订单数量（"+checkData.length+"）过多，请控制订单数量。目前最多支持（"+yjddMax+"）。");
                return;
            }
            if(checkedPay(checkData)){
                checkAndAddGx(checkData).done(function (yjdh) {
                    removeModal();
                    if(isNotBlank(yjdh)){
                        console.info("生成新月结单号成功，进行打印月结收费单");
                        printYjsfd(yjdh, "yjsfd");
                    }else{
                        ityzl_SHOW_WARN_LAYER("未获取到月结单号信息");
                    }
                });
            }
        }

        // POS缴费
        function posxd(checkData, cols){
            if(checkData.length == 0){
                ityzl_SHOW_WARN_LAYER("请先选择一条收费信息，在进行操作。");
                return;
            }
            if(yjddMax !=-1  && yjddMax < checkData.length){
                ityzl_SHOW_WARN_LAYER("当前订单数量（"+checkData.length+"）过多，请控制订单数量。目前最多支持（"+yjddMax+"）。");
                return;
            }
            if(checkedPay(checkData)){
                checkAndAddGx(checkData).done(function (yjdh) {
                    if(isNotBlank(yjdh)){
                        addModel();
                        getReturnData("/rest/v1.0/yjsfxx/scdd",{yjdh:yjdh},"GET",function () {
                            removeModal();
                            ityzl_SHOW_SUCCESS_LAYER( "创建成功。");
                            var url="/realestate-accept-ui/view/slym/posjf.html?yjdh="+yjdh;
                            layerCommonOpenFull(url, "POS缴费页面", '1150px', '600px');
                        },function (error) {
                            removeModal();
                            delAjaxErrorMsg(error);
                        })
                    }else{
                        ityzl_SHOW_WARN_LAYER("未获取到月结单号信息");
                    }
                });
            }
        }

        // 校验下单信息，并添加月结收费关系数据
        function checkAndAddGx(checkData) {
            var deferred = $.Deferred();
            addModel();
            checkSfxd(checkData).done(function(){
                removeModal();
                addYjSfxxGx(checkData).done(function(data){
                    deferred.resolve(data[0].yjdh);
                });
            }).fail(function(data){
                removeModal();
                if(isNotBlank(data)){
                    var info ='';
                    var yjdhList = new Array();
                    $.each(data, function(key, value){
                        info +='<div class="title-area">' +
                            '<p class="tsxx-title">月结单号</p>' +
                            '<span>'+ key +'</span>'+
                            '<p>受理编号：'+
                            value.join(",") +
                            '</p></div>'
                        yjdhList.push(key);
                    });
                    var content = '<div class="bdc-right-tips-box" >' +
                        '<div class="bdc-fl-box">'+
                        '<h4 class="bdc-sure-tip">以下为收费信息已生成的月结单号信息，是否作废？</h4></div>'+
                        info +
                        '</div>';
                    layer.open({
                        type: 1,
                        title: '提示信息',
                        anim: -1,
                        skin: 'bdc-tips-right',
                        shade: [0],
                        area: ['390px'],
                        content:content,
                        btn: ['是','否'],
                        btnAlign: 'c',
                        yes: function(index){
                            // 作废操作
                            zfYjdhxx(yjdhList).done(function(){
                                // 作废成功后，进行下单操作
                                addYjSfxxGx(checkData).done(function(data){
                                    deferred.resolve(data[0].yjdh);
                                });
                            }).fail(function(){
                                deferred.reject();
                            });
                            layer.close(index);
                        },
                        btn2: function(){
                            deferred.reject();
                        }
                    });
                }
            });
            return deferred;
        }

        // 校验收费信息是否下单
        function checkSfxd(checkData){
            var deferred = $.Deferred();
            $.ajax({
                url: getContextPath()+"/rest/v1.0/yjsfxx/sfxd",
                type: 'POST',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(checkData),
                dataType: "json",
                success: function (data) {
                    if(isNotBlank(data)){
                        removeModal();
                        deferred.reject(data);
                    }else{
                        deferred.resolve();
                    }
                },
                error: function (xhr, status, error) {
                    removeModal();
                    deferred.reject();
                    delAjaxErrorMsg(xhr);
                }
            });
            return deferred;
        }

        // 作废月结单订单信息
        function zfYjdhxx(yjdhList, layerIndex){
            var deferred = $.Deferred();
            addModel();
            $.ajax({
                url: getContextPath()+"/rest/v1.0/yjsfxx/zfyjdh",
                type: 'POST',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(yjdhList),
                dataType: "json",
                success: function (data) {
                    removeModal();
                    ityzl_SHOW_SUCCESS_LAYER("作废成功");
                    deferred.resolve();
                    layer.close(layerIndex);
                },
                error: function (xhr, status, error) {
                    removeModal();
                    deferred.reject();
                    delAjaxErrorMsg(xhr);
                }
            });
            return deferred;
        }

        // 检查当前收费信息是否缴费
        function checkedPay(checkData){
            var payed = new Array();
            $.each(checkData, function(index, value){
                if(isNotBlank(value.jfsbm)){
                    payed.push(value.slbh);
                }
            });
            if(payed.length == 0){
                return true;
            }
            showAlertDialog("以下受理编号已完成缴费。受理编号：" + payed.join(","));
            return false;
        }

        // 生成月结收费关系数据
        function addYjSfxxGx(checkData){
            var deferred = $.Deferred();
            addModel();
            $.ajax({
                url: getContextPath()+"/rest/v1.0/yjsfxx/gx",
                type: 'POST',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(checkData),
                dataType: "json",
                success: function (data) {
                    removeModal();
                    deferred.resolve(data);
                },
                error: function (xhr, status, error) {
                    removeModal();
                    deferred.reject();
                    delAjaxErrorMsg(xhr);
                }
            });
            return deferred;
        }

        // 导出Excel
        // 通过隐藏form提交方式，避免ajax方式不支持下载文件
        function exportExcel(d, cols){
            if(d.length == 0){
                warnMsg(" 请先选择需要导出的数据后，在进行导出。 ");
                return;
            }
            exportData(d, cols, d);
        }

        function exportData(d, cols, data) {
            addModel();
            // 标题
            var showColsTitle = ["序号","受理编号","不动产权证号","流程名称","交易合同号","义务人","义务人证件号","坐落","月结单号","缴款识别码","单价","数量","合计"];
            // 列内容
            var showColsField = ["xh","slbh","bdcqzh","gzldymc","jyhth","ywr","ywrzjh","zl","yjdh","jfsewmurl","dj","sfxmsl","sfxmhj"];
            // 列宽
            var showColsWidth = [7,20,40,35,20,35,30,45,35,20,20,15,15];

            var kssj = $("#kssj").val();
            var jssj = $("#jssj").val();
            var yhmc = $("#yhmc").val();
            $("#sheetName").val("不动产登记费明细表");
            if(kssj && jssj && yhmc){
                var name = yhmc + "_" + Format(kssj, "yyyy年MM月dd日") + "~" + Format(jssj, "yyyy年MM月dd日")+ "不动产登记费明细";
                $("#fileName").val(name);

            }else{
                $("#fileName").val('不动产登记费明细');
            }

            // 设置Excel基本信息 2020年4月单位非住宅抵押登记
            $("#cellTitle").val(showColsTitle);
            $("#cellWidth").val(showColsWidth);
            $("#cellKey").val(showColsField);


            // 导出增加序号字段
            var exportData = new Array();
            var zjje = 0, zjs = 0;
            var subTitle = "权利人：" + yhmc;
            exportData.push({
                xh: subTitle, slbh: subTitle, bdcqzh: subTitle,gzldymc: subTitle,jyhth:subTitle, ywr:subTitle,
                ywrzjh:subTitle, zl:subTitle, yjdh:subTitle, jfsewmurl:subTitle, dj:subTitle, sfxmsl:subTitle, sfxmhj: subTitle
            })
            for (var i = 0,j = 1 ; i < data.length; i++,j++) {
                exportData[j] = data[i];
                exportData[j].xh = j;
                if(parseFloat(data[i].sfxmhj).toString() != "NaN"){
                    zjje += data[i].sfxmhj;
                }
                zjs = j;
            }
            exportData.push({
                xh:'合计', slbh: '总件数：', bdcqzh: zjs, gzldymc: '',jyhth:'' ,ywr:'', ywrzjh:'', zl:'', yjdh:'', jfsewmurl:'', dj:'', sfxmsl:'总计金额：', sfxmhj: zjje
            });

            removeModal();
            $("#data").val(JSON.stringify(exportData));
            $("#mergeSameCell").val(true);
            $("#mergeColumnCellKeyList").val(JSON.stringify(new Array("xh,slbh,bdcqzh,gzldymc,jyhth,ywr,ywrzjh,zl,yjdh,jfsewmurl,dj,sfxmsl,sfxmhj")));
            $("#addBorder").val(true);
            $("#form").submit();
        }

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(pageTable)', function (obj) {
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;

            $.each(data, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedData', {
                        key: v.sfxxid, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedData', {
                        key: v.sfxxid, remove: true
                    });
                }
            });
        });

        // 监听收费状态选择框
        form.on('select(sfzt)', function(data){
            var selectVal = data.value;
            // 禁用状态解除
            $(".sf-btn").removeAttr("disabled");
            $(".sf-btn").css({'cursor':'pointer'});
            if("2" == selectVal){
                $(".sf-btn").css({'cursor':'no-drop'});
                $(".sf-btn").attr('disabled','off')
            }
        });

        // 查询处理逻辑
        function search() {
            // 每次查询清除下导出缓存数据
            layui.sessionData("checkedData", null);

            // 获取查询参数
            var obj = getSearchData();
            if(!isNotBlank(obj.yhmc) || !isNotBlank(obj.qxdm)){
                warnMsg(" 请添加银行名称和区县代码搜索条件后，再进行搜索。 ");
                return;
            }

            addModel();
            // 重新请求
            table.reload("pageTable", {
                url: getContextPath()+ "/rest/v1.0/yjsfxx/page",
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
                    setBtnAuth($("#sfzt").val());
                }
            });
        }

        // 获取页面中查询参数
        function getSearchData(){
            var searchData = $(".search").serializeArray();
            var useData = {};
            searchData.forEach(function (v) {
                useData[v.name] = v.value;
            });
            // 流程名称
            var processData = formSelects.value('selectedDefName','value');
            var processDefKeys = new Array();
            $.each(processData, function (index, value) {
                processDefKeys.push(value.processDefKey);
            });
            useData['gzldyids'] = processDefKeys.join(",");
            return useData;
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

        //点击高级查询
        $('#seniorSearch').on('click', function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            $(this).parent().toggleClass('bdc-button-box-four');
            $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);
            setHeight();
        });

    });
});

// 加载银行系统机构
function loadXtjg(){
    $.ajax({
        url: getContextPath() + "/rest/v1.0/yjsfxx/queryXtjg",
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            console.info(data);
            if(data){
                data.forEach(function (v) {
                    $("#yhmc").append('<option value="' +  v.jgmc + '">' + v.jgmc + '</option>');
                });
                form.render('select');
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
        }
    });
}
// 加载区县代码
function laodQxdm(){
    $.ajax({
        url: getContextPath() + "/rest/v1.0/yjsfxx/getQxdm",
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            console.info(data);
            if(data){
                data.forEach(function (v) {
                    $("#qxdm").append('<option value="' +  v.XZDM + '">' + v.XZMC + '</option>');
                });
                form.render('select');
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
        }
    });
}
/**
 * 渲染流程名称下拉框
 */
function loadProcessDefName() {
    formSelects.config('selectedDefName', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'processDefKey'            //自定义返回数据中value的key, 默认 value
    }, true);
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/task/process/all",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        async: false,
        success: function (data) {
            if(data){
                var processData = new Array();
                $.each(data, function (index, item) {
                    processData.push({
                        name : item.name,
                        processDefKey : item.processDefKey,
                    });
                });
                formSelects.data('selectedDefName','local',{arr:processData});
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
        }
    });
}
// 扫码补打
function smbd(d, cols){
    showYjdhModel(d,"扫码补打").done(function(data){
        getDdxx({yjdh:data.yjdh},function (ddxx) {
            if(isNotBlank(ddxx) && isNotBlank(ddxx[0].dsfddbh)){
                requestPosSmbd(ddxx[0].dsfddbh);
            }else{
                ityzl_SHOW_WARN_LAYER("未获取到订单信息");
            }
        },function (error) {
            ityzl_SHOW_WARN_LAYER("未获取到订单信息");
        });
    });
}
// 查询缴费状态
function queryJfzt(d, cols){
    showYjdhModel(d,"查询缴费状态").done(function(data){
        addModel();
        $.ajax({
            url: getContextPath() + "/sfss/ddxx/sfzt",
            type: 'get',
            dataType: 'json',
            data: {yjdh: data.yjdh},
            success: function (data) {
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER("获取缴费状态成功");
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });
    });
}

// 通知政融平台
function tzzrpt(d, cols){
    showYjdhModel(d,"通知政融平台").done(function(data){
        $.ajax({
            url: getContextPath() + "/sfss/ddxx/gxDdxxByGzlslid",
            type: 'GET',
            dataType: 'json',
            contentType: "application/json",
            async: false,
            data: {
                yjdh : data.yjdh
            },
            success: function (data) {
                console.info(data);
                if(data && data.resultCode == "1"){
                    ityzl_SHOW_SUCCESS_LAYER("通知政融平台已支付成功。");
                }else{
                    ityzl_SHOW_WARN_LAYER("通知政融平台已支付失败。");
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });
    });
}

function showYjdhModel(d, title){
    var deferred = $.Deferred();
    if(d.length == 0){
        ityzl_SHOW_WARN_LAYER("请先选择一条收费信息，在进行操作。");
        deferred.reject();
    }else{
        getReturnData("/rest/v1.0/yjsfxx/yjdh/"+d[0].sfxxid , {},"GET",function (data) {
            if(isNotBlank(data) && isNotBlank(data[0])){
                var yjdh = data[0];
                layer.open({
                    title: title,
                    type: 1,
                    skin: 'bdc-spf-layer',
                    area: ['430px'],
                    btn: ['提交', '取消'],
                    content: $('#bdc-popup-small'),
                    yes: function (index, layero) {
                        var yjdhxx = $('#yjdh').val();
                        if(!isNotBlank(yjdhxx)){
                            ityzl_SHOW_WARN_LAYER("请输入月结单号");
                            deferred.reject();
                        }else{
                            deferred.resolve({
                                yjdh: yjdhxx
                            });
                        }
                        layer.close(index);
                    },
                    btn2: function (index, layero) {
                        //取消 的回调
                        layer.close(index);
                        deferred.reject();
                    },
                    success: function () {
                        $('#yjdh').val(yjdh);
                        $('#yjdh').focus();
                    }
                });
            }else{
                ityzl_SHOW_WARN_LAYER("未获取到月结单号信息");
                deferred.reject();
            }
        },function (error) {
            delAjaxErrorMsg(error);
            deferred.reject();
        },false);
    }
    return deferred;
}

// 打印月结收费单
function printYjsfd(yjdh, dylx){
    var param = {
        dylx: dylx,
        url : getIP() + "/realestate-accept-ui",
        zxlc: false,
        zdyXmlUrl: "zdy/" + dylx + "/xml/datas?yjdh=" + yjdh,
        gzlslid: "nogzlslid"
    };
    getReturnData("/slPrint/newmode", param,"GET",function (data) {
        console.info(data);
        if(isNotBlank(data)){
            print(data.modelurl, data.dataurl, data.hidemodel);
        }else{
            ityzl_SHOW_WARN_LAYER("未获取到打印参数。");
        }
    },function (xhr) {
        delAjaxErrorMsg(xhr);
    },false);
}

/**
 * 查询订单信息
 */
function getDdxx(param_,callback,failcallback){
    getReturnData("/sfss/ddxx",param_,"GET",function (data) {
        callback(data);
    },function (error) {
        failcallback(error);
    })
}

/**
 * 请求POS控件进行扫码补单
 */
function requestPosSmbd(dsfddbh){
    var pos = {
        // 1、交易指令    2、交易金额    3、缴款书编码    4、缴款书中金额    5、原交易流水号
        transType : "09", transAmount : "", taxNum : dsfddbh, totalAmount: "", oldPostrace: "",
        // 6、原交易系统检索号    7、原交易日期    8、付款码    9、订单号    10、预留备用
        oldHostTrace: "", oldTransDate:"", longQRPay: "", merOrderID: "", reverse: "",
    };
    addModel();
    var request = new Array();
    request.push(pos.transType);
    if(isNotBlank(pos.transAmount)){
        request.push(PrefixInteger(pos.transAmount,12));
    }else{
        request.push(MyAppend(pos.transAmount,12));
    }
    request.push(MyAppend(pos.taxNum,30));
    if(isNotBlank(pos.totalAmount)){
        request.push(PrefixInteger(pos.totalAmount,12));
    }else{
        request.push(MyAppend(pos.totalAmount,12));
    }
    if(isNotBlank(pos.oldPostrace)){
        request.push(PrefixInteger(pos.oldPostrace,6));
    }else{
        request.push(MyAppend(pos.oldPostrace,6));
    }
    request.push(MyAppend(pos.oldHostTrace,12));
    request.push(MyAppend(pos.oldTransDate,4));
    request.push(MyAppend(pos.longQRPay,37));
    request.push(MyAppend(pos.merOrderID,30));
    request.push(MyAppend(pos.reverse,60));
    try{
        var input = request.join("");
        console.log(input);
        var ret = AhCcbTaxMis.misPosTrans(input);
        //var ret = "0100123456789012345          888888888888888888888888888                 1234562009071101                88888888                                        ";
        console.log(ret);
        removeModal();
        if("00" == ret.substring(2,4)){
            ityzl_SHOW_SUCCESS_LAYER("扫码补单成功。");
        }else{
            ityzl_SHOW_WARN_LAYER("扫码补单失败 "+ result.respMessage);
        }
    }catch(err){
        removeModal(); console.info(err);
        delAjaxErrorMsg(err);
    }
}

//将金额补0补足位数  num需要补足的内容；n为补足的位数
function PrefixInteger(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}
//补足对应位数的空白字符串长度
function MyAppend(str, n) {
    var newStr = '';
    if(str.length >= n){
        return str.substring(0, n);
    }
    for (var i = str.length; i <= n; i++) {
        if (i == n) {
            newStr = str+newStr;
        } else {
            newStr += ' ';
        }
    };
    return newStr;
}


// 退款申请
function tksq(d, cols){
    //先查询订单信息
    showYjdhModel(d,"退款申请").done(function(data){
        addModel();
        var yjdh = data.yjdh;
        getReturnData("/sfss/ddxx/ykq/ddxx", {yjdh: yjdh}, "GET", function (data) {
            if(data && data.length >0) {
                var bdcGzYzQO = {
                    zhbs : "SFXX_YJ_TKSQ",
                    paramMap : { yjdh : yjdh}
                };
                gzyzCommon(2, bdcGzYzQO, function (data) {
                    console.log("验证通过");
                    $.ajax({
                        url: getContextPath() + "/sfss/ddxx/tksq",
                        type: 'get',
                        dataType: 'json',
                        data: {yjdh: yjdh},
                        success: function (data) {
                            removeModal();
                            if (data && data[0] != null && isNotBlank(data[0].tkdh)) {
                                ityzl_SHOW_SUCCESS_LAYER("操作成功");
                                table.reload('pageTable');
                            } else {
                                ityzl_SHOW_WARN_LAYER("操作失败");
                            }
                        },
                        error: function (xhr, status, error) {
                            removeModal();
                            delAjaxErrorMsg(xhr)
                        }
                    });
                });
            }else{
                removeModal();
                ityzl_SHOW_WARN_LAYER("未查询到订单信息");
            }
        }, function (error) {
            removeModal();
            delAjaxErrorMsg(error);
        })
    });
}

// 退款查询
function tkcx(d, cols){
    showYjdhModel(d,"退款申请").done(function(data){
        addModel();
        $.ajax({
            url: getContextPath() + "/sfss/ddxx/querySfxxTkqk",
            type: 'get',
            dataType: 'json',
            data: {yjdh: data.yjdh},
            success: function (data) {
                removeModal();
                if (data && isNotBlank(data.sfzt)) {
                    var sfztMc = changeDmToMc(data.sfzt, zdList.sfzt);
                    ityzl_SHOW_SUCCESS_LAYER("查询成功,退款状态为" + sfztMc);
                    table.reload('pageTable');
                } else {
                    ityzl_SHOW_WARN_LAYER("未查询到结果");
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });
    });
}

// 推送缴库
function tsjk(d, cols){
    showYjdhModel(d,"推送缴库").done(function(data){
        addModel();
        $.ajax({
            url: getContextPath() + "/sf/tsjk",
            type: 'get',
            dataType: 'json',
            data: {yjdh: data.yjdh, sfyj:1},
            success: function (data) {
                removeModal();
                console.log(data);
                if (data && data.statusCode === "0000") {
                    ityzl_SHOW_SUCCESS_LAYER("推送成功");
                    table.reload('pageTable');
                } else {
                    ityzl_SHOW_SUCCESS_LAYER("推送失败" + data.msg);
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });
    });
}

function setBtnAuth(selectVal){
    // 禁用状态解除
    $(".sf-btn").removeAttr("disabled");
    $(".sf-btn").css({'cursor':'pointer'});
    if("2" == selectVal){
        $(".sf-btn").css({'cursor':'no-drop'});
        $(".sf-btn").attr('disabled','off')
    }
}

function completeDate(date1,date2){
    var oDate1 = new Date(date1);
    var oDate2 = new Date(date2);
    if(oDate1.getTime() >= oDate2.getTime()){
        return true;
    } else {
        return false;
    }
}