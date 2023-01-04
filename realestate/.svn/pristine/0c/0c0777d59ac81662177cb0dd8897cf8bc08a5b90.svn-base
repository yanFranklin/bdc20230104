/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 不动产单元
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response'});
var zdList = getMulZdList("sdlx");
var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
var bdcdyhcxLxArr=[{"DM":"TDFW","MC":"定着物","selected":"selected"},{"DM":"TD","MC":"土地"},{"DM":"HY","MC":"海域"},{"DM":"GZW","MC":"构筑物"}];
var sdtzlxList = ['1','2'];
var reverseList = ['bdcdyh','qlr','zl'];
//页面加载时清除缓存数据
sessionStorage.checkedData = '{}';
sessionStorage.allData = '{}';
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form','jquery','laytpl','response'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        formSelects = layui.formSelects,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        response = layui.response,
        form = layui.form;
    var dycxlxCxtj ={
        "TD": {
            "dyhcxlx": 1,
            "dzwtzm": "W"
        },
        "TDFW": {
            "dyhcxlx": 1,
            "dzwtzm": "F"
        },
        "LDLM": {
            "dyhcxlx": 1,
            "dzwtzm": "L"
        },
        "HY": {
            "dyhcxlx": 2,
            "dzwtzm": ""
        },
        "GZW": {
            "dyhcxlx": 3,
            "dzwtzm": ""
        }
    };
    //页面初始化
    generateTableUi();
    generateTableContent();

    $('.bdc-table-box').on('mouseenter','td',function () {
        if($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1){
            $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
        }
        $('.layui-table-grid-down').on('click',function () {
            setTimeout(function () {
                $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
            },20);
        });
    });

    //头工具栏事件
    table.on('toolbar(bdcdyListTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'sd':sd(checkStatus.data);
                break;
        }
    });

    function generateTableUi() {
        layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
            var form = layui.form;
            var laytpl = layui.laytpl;
            var json = {
                sdtzlxList: sdtzlxList
            };
            var tpl = tableUiTpL.innerHTML, view = document.getElementById("tableUi");
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            renderDate(form);
        })
    }

    function generateTableContent() {
        layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate', 'table'], function () {
            var form = layui.form;
            var laytpl = layui.laytpl;
            var element = layui.element;
            var table = layui.table;
            for (var i = 0; i < sdtzlxList.length; i++) {
                var xztzlx = sdtzlxList[i];
                var view = document.getElementById("tableContent");
                var isFirst = false;
                var tpl;
                if (i === 0) {
                    isFirst = true;
                }

                var json = {
                    isFirst: isFirst,
                    zd:zdList
                };
                if (xztzlx === "1") {
                    tpl = dyhTpl.innerHTML;
                }
                if (xztzlx === "2") {
                    tpl = ljzTpl.innerHTML;
                }
                //渲染数据
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = view.innerHTML + html;
                });
            }
            renderDate(form);

            //不动产单元
            initBdcdyhTable();

            //逻辑幢
            initLjzTable();

            //监听第一次单击tab，重构表格尺寸
            var ljzIndex = 0,
                bdcdyhIndex = 0;

            element.on('tab(tabFilter)', function (data) {
                $('.content-title').width($('.layui-tab').width() + 30);
                switch (data.elem.context.value) {
                    case 2:
                        if (ljzIndex === 0) {
                            ljzIndex++;
                            table.resize('ljzList');
                            table.resize('fwDcbIndex');
                        }
                        break;
                    case 1:
                        if (bdcdyhIndex === 0) {
                            bdcdyhIndex++;
                            table.resize('bdcdyListTable')
                            table.resize('qjid');
                        }
                        break;
                }
            });
            //给下拉框添加删除图标
            renderSelectClose(form);
        })
    }
    /**
     * 初始化不动产单元
     */
    function initBdcdyhTable() {
        layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
            var $ = layui.jquery;
            var form = layui.form;
            var table = layui.table;
            // 当前页数据
            var currentPageData = [];
            //获取查询总页数
            var totalPages = 0;
            var isSearch = true;
            //监听回车事件
            $(document).keydown(function (event) {
                var tab = $('.layui-this').val();
                if (event.keyCode == 13 && isSearch) { //绑定回车
                    if (tab === 1) {
                        $("#queryBdcdyh").click();
                    } else if (tab === 2) {
                        $("#queryLjz").click();
                    }
                    return false;
                }
            });
            //判断回车操作
            $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
                isSearch = false;
            }).on('blur', '.layui-laypage-skip .layui-input', function () {
                isSearch = true;
            });

            //加载地区下拉
            renderQjgldm();
            //加载查询类型下拉
            renderCxlx();

            layui.form.render("select");

            //不动产单元号的表头
            var unitTableTitle = [{type: 'checkbox', fixed: 'left'},
                {
                    field: 'bdcdyh', title: '不动产单元号', templet: function (d) {
                        return formatBdcdyh(d.bdcdyh);
                    }},
                {field: 'qlr', title: '权利人'},
                {field: 'zl', title: '坐落'}
            ];

            //提交表单
            form.on("submit(queryBdcdyh)", function (data) {
                addModel();
                //查询时清除缓存数据
                sessionStorage.checkedData = '{}';
                sessionStorage.allData = '{}';
                isSelectAll = false;
                var url = BASE_URL + '/bdcdy/page';
                cxObj = data.field;
                cxObj.zl = deleteWhitespace(cxObj.zl, "edge");
                if (isNotBlank(cxObj.zl)) {
                    cxObj.zl = cxObj.zl.replace(/,/g, '%').replace(/ /g, "%").replace(/，/g, "%");
                }
                var dyhcxlxArr=formSelects.value('selectDyhcxlx');
                if(dyhcxlxArr.length!=0){
                    var dm =dyhcxlxArr[0].DM;
                    var dycxlxObj =dycxlxCxtj[dm];
                    cxObj.dycxlx = '';
                    cxObj.dyhcxlx =dycxlxObj.dyhcxlx;
                    cxObj.dzwtzm =dycxlxObj.dzwtzm;
                }
                tableReload('qjid', cxObj, url);
                return false;
            });

            var tableConfig = {
                id: 'qjid',
                toolbar: "#toolbarBdcdyh",
                defaultToolbar: ['filter'],
                cols: [unitTableTitle],
                // page: true,  //开启分页
                parseData: function (res) {
                    currentPageData = res.content;
                    totalPages = res.totalPages;
                    // 设置选中行
                    var checkedData = JSON.parse(sessionStorage.checkedData);
                    if (res.content && res.content.length > 0 && !$.isEmptyObject(checkedData)) {
                        res.content.forEach(function (v) {
                            $.each(checkedData, function (key, value) {
                                if (key == v.qjid) {
                                    v.LAY_CHECKED = true;
                                }
                            })
                        });
                    }
                },
                done: function (res, curr, count) {
                    reverseTableCell(reverseList, 'qjid');
                    //无数据时显示内容调整
                    if ($('.layui-show .layui-table-body>.layui-table').height() == 0) {
                        $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                    }
                }
            };

            // 监听复选框事件，缓存选中的行数据
            table.on('checkbox(bdcdyListTable)', function (obj) {
                var checkedData = obj.type == 'one' ? [obj.data] : currentPageData;
                var myCheckedData = JSON.parse(sessionStorage.checkedData);
                var myAllData = JSON.parse(sessionStorage.allData);
                $.each(checkedData, function (i, v) {
                    if (obj.checked) {
                        //.增加已选中项
                        myCheckedData[v.qjid] = v;
                    } else {
                        //.删除
                        myCheckedData = deleteCheckedById(myCheckedData,v.qjid);
                    }
                    if (isSelectAll) {
                        v.checked = obj.checked;
                        myAllData[v.qjid] = v;
                    }
                });
                sessionStorage.checkedData = JSON.stringify(myCheckedData);
                sessionStorage.allData = JSON.stringify(myAllData);
                removeModal();

            });

            //加载表格
            loadDataTablbeByUrl('#bdcdyListTable', tableConfig);
            //表格初始化
            table.init('bdcdyListTable', tableConfig);

            //头工具栏事件
            table.on('toolbar(bdcdyListTable)', function (obj) {
                var layEvent = obj.event; //获得 lay-event 对应的值
                if  (layEvent === "sd") { //锁定
                    var checkedData = JSON.parse(sessionStorage.checkedData);
                    if ($.isEmptyObject(checkedData)) {
                        layer.alert("请选择需要编辑记录!");
                    }
                    checkData = [];
                    $.each(checkedData, function (key, value) {
                        checkData.push(value);
                    });
                    if (checkData !== null && checkData.length > 0) {
                        addModel();
                        sd(checkData);
                    } else {
                        layer.alert("请选择需要编辑记录!");
                    }
                }
            });
        })
    }
    /**
     * 初始化逻辑幢
     */
    function initLjzTable() {
        layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
            var $ = layui.jquery;
            var table = layui.table;
            form = layui.form;
            laytpl = layui.laytpl;

            //不动产单元号的表头
            var unitTableTitleLjz = [{type: 'checkbox', fixed: 'left'},
                {field: 'lszd', title: '隶属宗地',align: 'center'},
                {field: 'fwmc', title: '房屋名称',align: 'center'},
                {field: 'zldz', title: '坐落',align: 'center'},
                {
                    field: 'bdcdyh', title: '不动产单元号', templet: function (d) {
                        return formatBdcdyh(d.bdcdyh);
                    }}
            ];

            //提交表单
            form.on("submit(queryLjz)", function (data) {
                //查询时清除缓存数据
                sessionStorage.checkedData = '{}';
                sessionStorage.allData = '{}';
                addModel();
                var url = BASE_URL + '/listLjzByPageJson';
                var cxObj = data.field;
                cxObj.zl = deleteWhitespace(cxObj.zl, "edge");
                //添加模糊类型
                cxtjAddMhlx(cxObj);
                tableReload('fwDcbIndex', cxObj, url);
                return false;
            });

            var tableConfig = {
                id: 'fwDcbIndex',
                toolbar: "#toolbarLjz",
                defaultToolbar: ['filter'],
                cols: [unitTableTitleLjz],
                done: function () {
                    var reverseList = ['zldz'];
                    //无数据时显示内容调整
                    if($('.layui-show .layui-table-body>.layui-table').height() == 0){
                        $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                    }
                }
            };

            //加载表格
            loadDataTablbeByUrl('#ljzList', tableConfig);
            //表格初始化
            table.init('ljzList', tableConfig);
            //头工具栏事件
            table.on('toolbar(ljzList)', function (obj) {
                var layEvent = obj.event; //获得 lay-event 对应的值
                if (layEvent === "sd") {
                    var checkStatus = table.checkStatus(obj.config.id);
                    checkData = checkStatus.data;
                    if (checkData !== null && checkData.length > 0) {
                        addModel();
                        //添加setTimeout,防止遮罩不及时加载
                        setTimeout(function () {

                            checkLjzHs(checkData);

                        },10);

                    } else {
                        layer.alert("请选择需要编辑记录！!");
                    }


                }
            });
        })
    }
    /**
     * 查询逻辑幢下所有户室信息
     * @param checkdData
     * @returns {boolean}
     */
    function checkLjzHs(checkdData) {
        //根据逻辑幢获取所有户室数据
        checkdData =getAllHsByFwdcbIndex();

        var selectDataList = [];
        var bdcdyhList = [];
        for (var i = 0; i < checkdData.length; i++) {
            var selectData = checkdData[i];
            var bdcZssdDO = {};
            if(isNotBlank(selectData.bdcdyh)) {
                bdcZssdDO.bdcdyh = selectData.bdcdyh;
                bdcZssdDO.qjgldm = selectData.qjgldm;
                bdcZssdDO.yt = selectData.ghyt;
                bdcZssdDO.zl = selectData.zl;
                bdcZssdDO.fwDcbIndex = selectData.fwDcbIndex;
                //主要用于楼盘表回调时
                if(!isNotBlank(selectData.fwDcbIndex)) {
                    bdcZssdDO.qjid = selectData.qjid;
                    if (isNotBlank(selectData.qlr)) {
                        bdcZssdDO.qlr = selectData.qlr;
                    }
                    bdcZssdDO.zszl = selectData.zszl;
                    bdcZssdDO.sfsczs = selectData.sfsczs;
                }
                selectDataList.push(bdcZssdDO);
                bdcdyhList.push(selectData.bdcdyh)
            }
        }
        if(selectDataList.length ===0){
            removeModal();
            layer.alert("所选逻辑幢未找到有效的户室不动产单元号，请检查数据");
            return false;
        }
        showDialog(selectDataList, bdcdyhList);
    }
    /**
     * 根据已选数据获取对应所有的户室信息
     * @returns {[]}
     */
    function getAllHsByFwdcbIndex(){
        var fwdcbIndexs = [];
        for (var i = 0; i < checkData.length; i++) {
            fwdcbIndexs.push(checkData[i].fw_dcb_index);
        }
        var qjgldm =checkData[0].qjgldm;
        checkData =[];
        $.ajax({
            url: BASE_URL +"/getAllHsByFwdcbIndex",
            type: 'GET',
            dataType: 'json',
            async: false,
            data: {fwdcbIndexs:fwdcbIndexs.join(","),qjgldm:qjgldm},
            success: function (data) {
                if(data.length >0){
                    checkData =data;
                    for (var i = 0; i < checkData.length; i++) {
                        checkData[i].qjgldm =qjgldm;
                    }
                }

            }, error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }

        });
        return checkData;
    }

    /**
     * 锁定数据是否选择校验
     * @param data
     */
    function sd(data){
        if(!data || data.length == 0){
            layer.alert("请选择需要编辑记录！");
            return;
        }
        var bdcdyhList = [];
        $.each(data, function (index, item) {
            bdcdyhList.push(item.bdcdyh);
        })

        showDialog(data,bdcdyhList);
    }

    /**
     * 锁定弹出框显示
     * @param data
     */
    function showDialog(data, bdcdyhList){
        $("#bdc-popup-small-sd").html("");
        var getTpl = bdcdySdTpl.innerHTML ,view = document.getElementById('bdc-popup-small-sd');
        laytpl(getTpl).render({"sdlx": zdList["sdlx"]}, function(html){
            view.innerHTML = html;
        });
        form.render();
        layer.open({
            title: '锁定原因',
            type: 1,
            area: ['430px'],
            btn: ['提交', '取消'],
            content: $('#bdc-popup-small-sd') ,
            yes: function (index, layero) {   //提交 的回调
                saveSdxx(data,index,bdcdyhList)

            },
            btn2: function (index, layero) {  //取消 的回调
                layer.close(index);
                removeModal();
            },
            cancel: function (index) {   //右上角关闭回调
                layer.close(index);
                removeModal();
            }
        });
    }
    /**
     * 锁定相关校验
     * @param data
     * @param index
     */
    function saveSdxx(data,index,bdcdyhList) {
        if (isNullOrEmpty($('#sdyy').val())){
            layer.alert("请输入锁定原因！");
            return;
        }
        var sdyy = $('#sdyy').val();
        var sdlx = $('#sdlx').val();
        $.each(data, function (index, item) {
            item.sdyy = sdyy;
            item.sdlx = sdlx;
        })
        var sdxx = new Array();
        //查询锁定单元号信息
        if(!isNullOrEmpty(bdcdyhList)){
            sdxx = querySdbdcdyList(bdcdyhList);
        }
        if(sdxx != '' && sdxx.sdzt!=undefined && sdxx.sdzt=='1'){
            layer.confirm("存在不动产单元号" + sdxx.bdcdyh + "已锁定，是否继续锁定？", {
                title: "提示",
                btn: ["确认", "取消"],
                btn1: function (index2) {
                    layer.close(index2);
                    layer.close(index);
                    sdSdxx(index, data);
                },
                btn2: function (index) {
                    layer.close(index);
                    removeModal();
                }
            });
        } else {
            layer.close(index);
            sdSdxx(index, data);
        }
    }
    /**
     * 保存锁定
     * @param index
     * @param data
     */
    function sdSdxx(index,data) {
        $.ajax({
            url: BASE_URL + '/bdcdysd/sd',
            type: "POST",
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                saveSuccessMsg();
                removeModal();

            },
            error: function (e) {
                saveFailMsg();
                removeModal();
            },
            complete:function () {
                layer.close(index);
            }
        });
    }
    /**
     * 批量查询查询锁定的不动产单元
     * @param data
     */
    function querySdbdcdyList(data){
        var result='';
        $.ajax({
            url: BASE_URL + '/bdcdysd/queryBdcdySdList',
            type: "POST",
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            async:false,
            success: function (data) {
                result=data;
            },
            error: function (e) {
                response.fail(e)
            },complete:function () {

            }
        });
        return result;
    }


    /**
     * 初始化地区下拉
     */
    function renderQjgldm(){
        getReturnData("/rest/v1.0/qjgldm/list",{},"GET",function (data){
            if(data &&data.length > 0) {
                $("#dyh-qjgldm").show();
                $("#ljz-qjgldm").show();
                $(".bdc-button-box").addClass("bdc-button-box-four");
                $.each(data, function (index, item) {
                    $('#qjgldm').append('<option value="' + item.dm + '">' + item.mc + '</option>');
                    $('#ljz-qjgldm-select').append('<option value="' + item.dm + '">' + item.mc + '</option>');
                });
            }
            form.render("select");

        },function (error){

        },false);
    }

    /**
     * 查询类型下拉选项
     */
    function renderCxlx(){
        formSelects.config('selectDyhcxlx', {
            keyName: 'MC',            //自定义返回数据中name的key, 默认 name
            keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
        }, true);
        formSelects.data('selectDyhcxlx','local',{arr:bdcdyhcxLxArr});
    }

    function tableReload(tableid, postData, url,isChangeHeight) {
        layui.use(['table'], function () {
            var table = layui.table;
            table.reload(tableid, {
                url: url,
                where: postData,
                // data:[11],
                page: {
                    //重新从第 1 页开始
                    curr: 1,
                    limits: [10, 50, 100, 200, 500]
                },
                request: {
                    limitName: 'size' //每页数据量的参数名，默认：limit
                },
                done: function () {
                    removeModal();
                    $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                    reverseTableCell(reverseList, tableid);
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() === 0) {
                        $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');

                    } else {
                        if(isChangeHeight == undefined){
                            $('.bdc-form-div .layui-show .layui-table-main.layui-table-body').height($('.bdc-form-div .layui-show .bdc-table-box').height() - 131);
                        }
                    }
                    //批量页面实时加载按钮权限
                    var a = setInterval(function () {
                        if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                            var readOnly = getQueryString("readOnly");
                            if (isNotBlank(readOnly)) {
                                getStateById(readOnly, formStateId, "slympl");
                            }
                            clearInterval(a);
                        }
                    }, 50);
                }
            });
            $('.bdc-table-box').on('mouseenter', 'td', function () {
                if ($(this).text() && reverseList.indexOf($(this).attr("data-field")) !== -1) {
                    $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
                }
                $('.layui-table-grid-down').on('click', function () {
                    setTimeout(function () {
                        $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                    }, 20);
                });
            });
        });
    }

    //通过url来进行加载数据
    function loadDataTablbeByUrl(_domId, _tableConfig) {
        layui.use(['table', 'laypage', 'jquery'], function () {
            if (_domId) {
                var table = layui.table;
                var $ = layui.jquery;
                var tableDefaultConfig = {
                    elem: _domId,
                    toolbar: "#toolbarDemo",
                    even: true,
                    cellMinWidth: 80,
                    page: true,  //开启分页
                    limits: [10, 50, 100, 200, 500],
                    data: [],
                    request: {
                        limitName: 'size' //每页数据量的参数名，默认：limit
                    },
                    response: {
                        countName: 'totalElements',  //数据总数的字段名称，默认：count
                        dataName: 'content' //数据列表的字段名称，默认：data
                    }
                };

                if (!_tableConfig.cols || !_tableConfig.cols[0] || _tableConfig.cols[0].length < 1) {
                    _tableConfig.cols = [[]]
                }
                var tableRenderConfig = $.extend({}, tableDefaultConfig, _tableConfig);
                table.render(tableRenderConfig);
            }
        });
    }

    //用于多个tab表格的翻转
    function reverseTableCell(reverseList, tableid) {
        var getTd = $("[lay-id='" + tableid + "'] tbody td");
        for (var i = 0; i < getTd.length; i++) {
            reverseList.forEach(function (v) {
                if ($(getTd[i]).attr('data-field') == v) {
                    var getTdCell = $(getTd[i]).find('.layui-table-cell');
                    getTdCell.css('direction', 'rtl');
                    if (getTdCell.find('span').length == 0) {
                        var tdHtml = reverseString(getTdCell.html());
                        getTdCell.html('<span class="bdc-table-date">' + tdHtml + '</span>');
                    }
                }
            });
        }
    }

    //字段增加模糊类型
    function cxtjAddMhlx(cxObj){
        // 深拷贝一个cxObj备份，用于作为查询参数
        var cxObjBak = JSON.parse(JSON.stringify(cxObj));

        // 获取查询方式：精确查询(1)和模糊查询(4)
        var cxfs = cxObj.cxfs;
        if (isNotBlank(cxfs)) {
            // 查询条件精确或者模糊
            Object.keys(cxObjBak).forEach(function (key) {
                cxObj[key + 'mh'] = cxfs;
            });
        }
    }
    // 不动产单元tab页地区选择
    form.on('select(qjgldm)', function (data) {
        var selected_value = data.value;
        // 逻辑幢地区跟着变化
        $("#ljz-qjgldm-select").val(selected_value);
        layui.form.render("select");
    })
});