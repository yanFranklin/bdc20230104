var form;
var cxdh;
var rzlx;
var rzmc;
var cxtjList = [];
var cxjgList = [];
var zdList = [];
var globalTooList = [];
var globalToolbarList = [];
var recordColsLength = 0;
var canmhcx;
var hgjArr = [];
var checkeddata;
var searchFlag = false
var uploadInst = null;
var upload = null;
//获取需要翻转的字段集合
var reverseList = [];
var otherJsUseTablereload;
// 是否导入Excel查询标识
var isExcelQuery = false;
// 是否需要查询数据
var loadAllData = false;
//是否打印成功
var dyzt = false;
layui.extend({
    formSelects: '../../static/lib/form-select/formSelects-v4'
});
var formSelects;
//查询条件计数
var cxtjJs = 0;
var tableConfig = {
    elem: '#pageTable'
    , even: true
    , defaultToolbar: ['filter']
    , toolbar: "#toolbarDemo"
    , url: ''
    , data: []
    , page: true
    , cols: [[]]
    , where: {}
    , cellMinWidth: 80
    , limits: [10, 50, 100, 200, 300, 500]
    , request: {
        limitName: 'size' //每页数据量的参数名，默认：limit
        , loadTotal: true
    }, parseData: function (res) {
        if (!res || !res.hasContent) {
            res = $.extend({}, res);
            res.msg = '<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据'
        } else {
            //res 即为原始返回的数据
            globalData = res.content;
            loadXzztFlag = false;
            globalXmid.splice(0, globalXmid.length);
            globalXzzt.splice(0, globalXzzt.length);
            $.each(globalData, function (index, item) {
                if (item.xmid != undefined && $.inArray(item.xmid, globalXmid) == -1) {
                    globalXmid.push(item.xmid);
                }
            });
        }

        // 分页查询，选中之前选中行
        if (res.content && res.content.length > 0) {
            var checkedData = layui.sessionData('allPageCheckedData');
            if (checkedData) {
                res.content.forEach(function (v) {
                    $.each(checkedData, function (key, value) {
                        if (!isNullOrEmpty(v.ID) && key == v.ID) {
                            v.LAY_CHECKED = true;
                        }
                    })
                });
            }
        }

        return res;
    }
    , response: {
        countName: 'totalElements' //数据总数的字段名称，默认：count
        , dataName: 'content' //数据列表的字段名称，默认：data
        , statusName: 'hasContent' //规定数据状态的字段名称，默认：code
        , statusCode: true //规定成功的状态码，默认：0
    }//,
    // done: function (res, curr, count) {
    //     setHeight();
    // }
};

var cxfs;
/** 数据源*/
var dataUrl = '/realestate-inquiry-ui/dtcx/list/result';
/** 查询条件*/
var queryObject = {};
var excel;
/** 默认的分页条数 */
var limit;
// 组织树多选值
var OrgTreeCheck = new Map();
var UserCache = new Map();
layui.use(['form', 'table', 'layer', 'laydate', 'jquery', 'upload','formSelects'], function () {
    var laydate = layui.laydate;
    form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var $ = layui.jquery;
    upload = layui.upload;
    formSelects = layui.formSelects;
    $(function () {
        //设置默认的分页条数（常州未缴费台账要求默认展示500条）
        limit = $.getUrlParam('limit');
        if(isNotBlank(limit)){
            tableConfig['limit'] = limit;
        }
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');
        /**
         * added by cyc
         * 因为筛选框是动态拼接，layui.css等先绑定事件不适用，
         * 需要自行处理x图标的逻辑
         */
        $(".layui-input-inline").find('input').live('focus', function (item) {
            $(this).next('.inputreseticon').show();
        })
        $(".layui-input-inline").find('input').live('blur', function (item) {
            if ($(this).val() == "") {
                $(this).next('.inputreseticon').hide();
            }
        })
        $('.inputreseticon').live('click', function (item) {
            $(this).prev('input').val('');
            $(this).hide();
        })

        //select 的 x 的事件
        form.on('select', function (data) {
            if ($(this).text() == "请选择") {
                $(this).parents('.layui-input-inline').find('.reseticon').hide();
            } else {
                $(this).parents('.layui-input-inline').find('.reseticon').show();
            }
        });
        $('.reseticon').live('click', function (item) {
            $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
            $(this).hide();
            layui.form.render("select");
        })
        $('#reset').live('click', function (item) {
            $('.bdc-percentage-container').find('.layui-form')
                .find('select').find("option:eq(0)")
                .attr("selected", "selected");
            setTimeout($('.bdc-percentage-container').find('.layui-form')
                .find('select').parent().find('input').val(''), 100);
            $('.reseticon').hide();
            $('.inputreseticon').hide();
        })

        cxdh = $.getUrlParam("cxdh");
        var cxmc = decodeURI($.getUrlParam("cxmc"));
        // 窗口标题设置
        if (!isNullOrEmpty(cxmc)) {
            document.title = cxmc;
        }
        if (isNullOrEmpty(cxdh)) {
            layer.alert("缺失查询代号参数，请联系管理员！");
            window.close();
        } else {
            addModel();
            // 获取查询条件和查询结果配置内容
            $.ajax({
                url: "/realestate-inquiry-ui/dtcx/get/" + cxdh,
                type: "get",
                async: false,
                success: function (data) {
                    if (moduleCode) {
                        setElementAttrByModuleAuthorityBeforeRendering(moduleCode, data);
                    }
                    cxtjList = data.cxtjDOList;
                    cxjgList = data.cxjgDOList;
                    canmhcx = data.canmhcx;
                    rzlx = data.rzlx;
                    rzmc = data.rzmc;
                    if (isNullOrEmpty(rzlx)) {
                        rzlx = data.cxdh;
                    }
                    if (isNullOrEmpty(rzmc)) {
                        rzmc = data.cxmc;
                    }
                    /*时间型的条件记录在这里，后面统一渲染*/
                    var dateTjlist = [];
                    var timeTjlist = [];
                    /*记录条件个数，超过6个时放在高级搜索中*/
                    cxtjJs = 0;
                    var hiddenContent =
                        '<input name="cxid" value="' + data.cxid + '"hidden="hidden"/>' +
                        '<input name="cxdh" value="' + data.cxdh + '"hidden="hidden"/>';
                    queryObject["cxid"] = data.cxid;
                    $(".layui-form").html($(".layui-form").html() + hiddenContent);
                    if(cxtjList.length >0) {
                        $.each(cxtjList, function (i, cxtj) {
                            var tjlx = cxtj.tjtype;
                            var cxtjDom;
                            if (cxtj.mrxs == 1 || isNullOrEmpty(cxtj.mrxs)) {
                                cxtjJs = cxtjJs + 1;
                                if (cxtj.tjzdname.replace(/[^\x00-\xff]/g, '**').length <= 13) {
                                    cxtjDom =
                                        '<div class="layui-inline">' +
                                        '<label class="layui-form-label">' + cxtj.tjzdname + '</label>' +
                                        '<div class="layui-input-inline">';
                                } else {
                                    cxtjDom =
                                        '<div class="layui-inline">' +
                                        '<label class="layui-form-label bdc-two-lines">' + cxtj.tjzdname + '</label>' +
                                        '<div class="layui-input-inline">';
                                }
                                if (tjlx === 'text' || tjlx === 'plcx') {
                                    if ('qlr' == cxtj.tjzdid || 'qlrmc' == cxtj.tjzdid) {
                                        if(cxtj.dkqsjzj && (1 === cxtj.dkqsjzj || "1" === cxtj.dkqsjzj)) {
                                            // 读卡追加数据
                                            cxtjDom +=
                                                '<input id="' + cxtj.tjzdid + '" class="layui-input" type="text" name="' + cxtj.tjzdid + '" placeholder="请输入" ondblclick="appendReadIDCardNewQlrAndZjh(this)"/><i class="layui-icon layui-icon-close inputreseticon" style="display:none;"></i>\n';
                                        } else {
                                            cxtjDom +=
                                                '<input id="' + cxtj.tjzdid + '" class="layui-input" type="text" name="' + cxtj.tjzdid + '" placeholder="请输入" ondblclick="ReadIDCardNewQlrAndZjh(this)"/><i class="layui-icon layui-icon-close inputreseticon" style="display:none;"></i>\n';
                                        }
                                    } else if ('jfr' == cxtj.tjzdid || 'jfrmc' == cxtj.tjzdid || 'jfrxm' == cxtj.tjzdid || 'JFRXM' == cxtj.tjzdid) {
                                        cxtjDom +=
                                            '<input id="' + cxtj.tjzdid + '" class="layui-input" type="text" name="' + cxtj.tjzdid + '" placeholder="请输入" ondblclick="ReadIDCardNewJfrAndZjh(this)"/><i class="layui-icon layui-icon-close inputreseticon" style="display:none;"></i>\n';
                                    } else {
                                        cxtjDom +=
                                            '<input id="' + cxtj.tjzdid + '" class="layui-input" type="text" name="' + cxtj.tjzdid + '" placeholder="请输入"/><i class="layui-icon layui-icon-close inputreseticon" style="display:none;"></i>\n';
                                    }
                                } else if (tjlx === 'date') {
                                    dateTjlist.push(cxtj.tjzdid);
                                    cxtjDom +=
                                        '<input id="' + cxtj.tjzdid + '" class="layui-input" type="text" name="' + cxtj.tjzdid + '" placeholder="请选择"/>';
                                } else if (tjlx === 'time') {
                                    timeTjlist.push(cxtj.tjzdid);
                                    cxtjDom +=
                                        '<input id="' + cxtj.tjzdid + '" class="layui-input" type="text" name="' + cxtj.tjzdid + '" placeholder="请选择"/>';
                                } else if (tjlx === 'bmry') {
                                    cxtjDom +=
                                        '<div id="bjryDiv"  class="layui-input-inline xzq-sele">' +
                                        '<select xm-select="' + cxtj.tjzdid + '" xm-select-skin="default" xm-select-show-count="2"xm-select-height="34px" xm-select-search="" xm-select-search-type="dl" id="' + cxtj.tjzdid + '" name="' + cxtj.tjzdid + '" lay-filter="ry-select" class="layui-select" > </select>'
                                } else if (tjlx === 'dropdown') {
                                    cxtjDom +=
                                        '<select id="' + cxtj.tjzdid + '"name="' + cxtj.tjzdid + '" class="layui-select" lay-search="">' +
                                        '<option value="" selected="selected">请选择</option>';
                                    var zdList = getAllMulZdList(cxtj.zdid, cxtj.zdly);
                                    if (zdList && isNotBlank(zdList[cxtj.zdid])) {
                                        $.each(zdList[cxtj.zdid], function (i, zd) {
                                            cxtjDom += '<option value="' + zd.DM + '">' + zd.MC + '</option>';
                                        });
                                    }
                                    cxtjDom += '</select>';
                                    cxtjDom += '<i class="layui-icon layui-icon-close bdc-hide' +
                                        ' reseticon" style="display:none;right: 29px!important;"></i>\n';
                                } else if (tjlx === 'dropdowns') {
                                    cxtjDom +=
                                        '<div id="dxDiv' + cxtj.tjzdid + '" class="layui-input-inline xzq-sele">' +
                                        '<select xm-select="xm-select' + cxtj.tjzdid + '" xm-select-skin="default" xm-select-show-count="2"' +
                                        'xm-select-height="34px" xm-select-search="" xm-select-search-type="dl"' +
                                        'id="' + cxtj.tjzdid + '" name="' + cxtj.tjzdid +'" lay-filter="dx-select-' + cxtj.tjzdid +  '" class="layui-select search" >'+
                                        '</select>';

                                    $.ajax({
                                        url: "/realestate-inquiry-ui/bdczd/mul",
                                        type: "GET",
                                        dataType: "json",
                                        data: {
                                            zdNames: cxtj.zdid
                                        },
                                        success: function (zdList) {
                                            var rymcSelList = [];
                                            var sxm = cxtj.zdid;
                                            var zdArr = zdList[sxm];
                                            if (zdArr) {
                                                $.each(zdArr, function (index, item) {
                                                    // $('#rymc').append('<option value="' + item.DM + '">' + item.DM + '</option>');
                                                    rymcSelList.push({
                                                        name: item.MC,
                                                        value: item.DM
                                                    })
                                                });
                                                var xmSelect = "xm-select" + cxtj.tjzdid;
                                                formSelects.data(xmSelect, 'local', {
                                                    arr: rymcSelList
                                                });
                                            }
                                        }
                                    });




                                }
                                else if (tjlx === 'bm') {
                                    cxtjDom +=
                                        '<div class="layui-input-inline" style="display: flex;">' +
                                        '<input id="' + cxtj.tjzdid + '"name="' + cxtj.tjzdid + '" class="layui-input" placeholder="请选择"  lay-search="" style="width: 220px;">' +
                                        '<input type="hidden" id="djbmdm' + cxtj.tjzdid + '" name="djbmdm" class="search">' +
                                        '<div class="layui-btn layui-btn-sm bdc-table-second-btn org_select_show">选择</div></div>' +
                                        '<div class="org_select_tree" style="width: 400px; height: 200px; left: 60px; top: 40px; display: none;">' +
                                        '<ul id="select_tree" class="ztree"></ul></div>' +
                                        '<script src="../../static/ztree/selectTree.js"></script>';
                                    var selectBjry;
                                    $.each(cxtjList, function (i, cxtj) {
                                        if (cxtj.tjtype === 'bmry') {
                                            selectBjry = cxtj.tjzdid;
                                        }
                                    });

                                    formSelects.config(selectBjry, {keyName: 'alias', keyVal: 'alias'});

                                    loadMultiOrgTreeZdy("select_tree", function (event, treeId, treeNode) {
                                        // 多选选中、取消事件
                                        if (treeNode.checked) {
                                            OrgTreeCheck.set(treeNode.id, {
                                                id: treeNode.id,
                                                code: treeNode.code,
                                                name: treeNode.name
                                            });
                                            getAjaxData('/realestate-inquiry-ui/rest/v1.0/process/users/' + treeNode.id).done(
                                                function (data) {
                                                    //获取人员缓存信息，人员缓存不为空时，将缓存数据和已有数据同步返回给页面。
                                                    UserCache.set(treeNode.id, data);
                                                    formSelects.data(selectBjry, 'local', {
                                                        arr: getUserData()
                                                    });
                                                }
                                            );
                                        } else {
                                            OrgTreeCheck.delete(treeNode.id);
                                            UserCache.delete(treeNode.id);
                                            formSelects.data(selectBjry, 'local', {
                                                arr: getUserData()
                                            });
                                        }

                                        var treeDiv = $("#" + treeId).parents(".layui-inline");
                                        if (OrgTreeCheck.size > 0) {
                                            var checkedOrgName = [], checkedOrgCode = [];
                                            OrgTreeCheck.forEach(function (value, key) {
                                                checkedOrgName.push(value.name);
                                                checkedOrgCode.push(value.code);
                                            });
                                            $(treeDiv).find("input[name=" + cxtj.tjzdid + "]").val(checkedOrgName.join(","));
                                            $(treeDiv).find("input[name=djbmdm]").val(checkedOrgCode.join(","));
                                        } else {
                                            $(treeDiv).find("input[name=" + cxtj.tjzdid + "]").val("");
                                            $(treeDiv).find("input[name=djbmdm]").val("");
                                        }
                                    });
                                } else {
                                    cxtjDom +=
                                        '<input id="' + cxtj.tjzdid + '" class="layui-input" type="text" name="' + cxtj.tjzdid + '" placeholder="请输入"/><i class="layui-icon layui-icon-close inputreseticon" style="display:none;"></i>\n';
                                }
                                if (canmhcx === 1) {
                                    if (cxtj.canmhcx === 1 && tjlx === 'text') {
                                        var mrmhlx = 1;
                                        if (isNotBlank(cxtj.mrmhlx)) {
                                            mrmhlx = cxtj.mrmhlx;
                                        }
                                        cxtjDom += '<div class="bdc-screen-inline">' +
                                            '<select name="' + cxtj.tjzdid + 'mh" id="' + cxtj.tjzdid + 'mh" class="bdc-filter-select">';
                                        if (mrmhlx == 1) {
                                            cxtjDom += '<option value="1" selected = "selected">精确</option>';
                                        } else {
                                            cxtjDom += '<option value="1">精确</option>';
                                        }
                                        if (mrmhlx == 2) {
                                            cxtjDom += '<option value="2" selected = "selected">左模糊</option>';
                                        } else {
                                            cxtjDom += '<option value="2">左模糊</option>';
                                        }
                                        if (mrmhlx == 3) {
                                            cxtjDom += '<option value="3" selected = "selected">右模糊</option>';
                                        } else {
                                            cxtjDom += '<option value="3">右模糊</option>';
                                        }
                                        if (mrmhlx == 4) {
                                            cxtjDom += '<option value="4" selected = "selected">全模糊</option>';
                                        } else {
                                            cxtjDom += '<option value="4">全模糊</option>';
                                        }
                                        cxtjDom += '</select>' +
                                            '</div>';
                                    } else {
                                        var mrmhlx = 1;
                                        if (isNotBlank(cxtj.mrmhlx)) {
                                            mrmhlx = cxtj.mrmhlx;
                                        }
                                        cxtjDom += '<div class="bdc-screen-inline">' +
                                            '<select name="' + cxtj.tjzdid + 'mh" id="' + cxtj.tjzdid + 'mh" class="bdc-filter-select" disabled="off">';
                                        if (mrmhlx == 1) {
                                            cxtjDom += '<option value="1" selected = "selected">精确</option>';
                                        } else {
                                            cxtjDom += '<option value="1">精确</option>';
                                        }
                                        if (mrmhlx == 2) {
                                            cxtjDom += '<option value="2" selected = "selected">左模糊</option>';
                                        } else {
                                            cxtjDom += '<option value="2">左模糊</option>';
                                        }
                                        if (mrmhlx == 3) {
                                            cxtjDom += '<option value="3" selected = "selected">右模糊</option>';
                                        } else {
                                            cxtjDom += '<option value="3">右模糊</option>';
                                        }
                                        if (mrmhlx == 4) {
                                            cxtjDom += '<option value="4" selected = "selected">全模糊</option>';
                                        } else {
                                            cxtjDom += '<option value="4">全模糊</option>';
                                        }
                                        cxtjDom += '</select>' +
                                            '</div>';
                                    }
                                } else {
                                    cxtjDom += '<input type="hidden" id="' + cxtj.tjzdid + 'mh" name="' + cxtj.tjzdid + 'mh" value="1"/>';
                                }
                                cxtjDom +=
                                    ' </div>' +
                                    '</div>';
                            } else {
                                cxtjDom += '<input type="hidden" id="' + cxtj.tjzdid + '" name="' + cxtj.tjzdid + '"/>';
                            }
                            var dom = $(cxtjDom);
                            if (cxtj.tjusage == 'cxtj') {
                                dom.find('input').addClass("cxtj");
                                dom.find('select').addClass("cxtj");
                            }

                            if (canmhcx === 1) {
                                if (cxtjJs <= 5) {
                                    $("#cxtj-area").append(dom);
                                } else {
                                    //增加pf-senior-show,用于查询条件个性化配置判断
                                    $("#gjSearch-area").addClass("pf-senior-show").addClass("bdc-hide").append(dom);
                                }
                            } else {
                                if (cxtjJs <= 6) {
                                    $("#cxtj-area").append(dom);
                                } else {

                                    $("#gjSearch-area").addClass("pf-senior-show").addClass("bdc-hide").append(dom);
                                }
                            }
                            //查询条件为空,不展示查询模块
                            if(cxtjJs ===0){
                                $('.bdc-search-content').hide();
                                $('.bdc-percentage-container').css({"padding-top": "10px"});
                            }else{
                                $('.bdc-search-content').show();
                                var height = $('.bdc-search-content').height() + 10 + 'px';
                                $('.bdc-percentage-container').css('padding-top', height);
                            }

                            $("#" + cxtj.tjzdid).val(getUrlParam(cxtj.tjzdid));
                        });
                    }

                    if (canmhcx === 1) {
                        // 是否显示高级搜索按钮
                        if (cxtjJs > 5) {
                            $("#gjSearch").css({
                                "display": "inline-block"
                            });
                        } else if (cxtjJs < 3) {
                            $('.bdc-percentage-container').addClass('bdc-line-search-container')
                        } else if (cxtjJs === 3) {
                            //查询条件为3个时按钮默认居中
                            $("#btn-area").addClass('bdc-button-box-four');
                        }
                    } else {
                        $("#searchdiv").removeClass("bdc-screen-search-box");
                        // 是否显示高级搜索按钮
                        if (cxtjJs > 6) {
                            $("#gjSearch").css({
                                "display": "inline-block"
                            });
                        } else if (cxtjJs < 4) {
                            $('.bdc-percentage-container').addClass('bdc-line-search-container')
                        } else if (cxtjJs === 4) {
                            //查询条件为4个时按钮默认居中
                            $("#btn-area").addClass('bdc-button-box-four');
                        }
                    }

                    form.render();


                    // 渲染layui时间控件
                    $.each(dateTjlist, function (i, dateDomId) {
                        laydate.render({
                            elem: '#' + dateDomId
                        })
                    });

                    // 渲染layui时间控件
                    $.each(timeTjlist, function (i, dateDomId) {
                        laydate.render({
                            elem: '#' + dateDomId,
                            type: 'datetime',
                            trigger: 'click'
                        })
                    });
                    var cols = [];

                    var zdStr = "";
                    // 遍历查询结果list，数据放在cols中
                    var countWidth = 0;
                    var colXuHao = {title: '序号', width: 60, field: 'ROWNUM_', fixed: 'left'};
                    cols.push(colXuHao);

                    $.each(cxjgList, function (i, cxjg) {
                        if (cxjg.lk !== null && !isNaN(cxjg.lk)) {
                            countWidth += parseInt(cxjg.lk);
                        }
                        var colObject;
                        if (cxjg.jgtype === 'button') {
                            if (isNullOrEmpty(cxjg.url)) {
                                var hgj = '{"title":"' + cxjg.jgzdname + '","name":"' + cxjg.jgzdid.toLowerCase() + '","id":"' + cxjg.jgzdid + '", "width":"' + cxjg.lk + '"}';
                                hgjArr.push(hgj);
                                colObject = {};
                            } else {
                                var url =
                                    '<input  id="' + cxjg.jgzdid + '" name="' + cxjg.jgzdid + '" value="' + cxjg.url + '">';
                                var dom = $(url);
                                dom.find('input').addClass("cxtj");

                                var hgj = '{"title":"' + cxjg.jgzdname + '","name":"openwin","id":"' + cxjg.jgzdid + '", "width":"' + cxjg.lk + '"}';
                                hgjArr.push(hgj);
                                $("#btn-url-area").append(dom);
                                colObject = {};
                            }                        
                        } else if (cxjg.sortable === 1) {
                            colObject = {
                                field: cxjg.jgzdid,
                                title: cxjg.jgzdname,
                                width: cxjg.lk,
                                dcwidth: cxjg.dclk,
                                align: cxjg.dqfs,
                                fixed: cxjg.dqfs,
                                sort: true
                            };
                        } else {
                            colObject = {field: cxjg.jgzdid, title: cxjg.jgzdname, width: cxjg.lk,  dcwidth: cxjg.dclk,fixed: cxjg.dqfs};
                        }

                        if (!isNullOrEmpty(cxjg.fxkpd)) {
                            var fxkpd = eval('(' + cxjg.fxkpd + ')');
                            // 需要通过字典项转换的
                            if (!isNullOrEmpty(cxjg.zdid)) {
                                colObject["zdid"] = cxjg.zdid;
                                colObject["templet"] = function (d) {
                                    var checkflag = false;
                                    var result;
                                    $.each(fxkpd, function (i, fxky) {
                                        if (convertZdDmToMc(cxjg.zdid, d[cxjg.jgzdid.toUpperCase()], 'zdList') == fxky.value) {
                                            checkflag = true;
                                            result = '<span class="' + fxky.class + '">' + convertZdDmToMc(cxjg.zdid, d[cxjg.jgzdid.toUpperCase()], 'zdList') + '</span>';
                                        }
                                    });
                                    if (checkflag === false) {
                                        result = convertZdDmToMc(cxjg.zdid, d[cxjg.jgzdid.toUpperCase()], 'zdList');
                                    }
                                    return result;
                                };
                                zdStr = zdStr + cxjg.zdid + ",";
                            }
                            // 单元号加空格处理
                            else if (cxjg.jgzdid === "bdcdyh" || cxjg.jgzdid === "BDCDYH") {
                                colObject["templet"] = function (d) {
                                    var checkflag = false;
                                    var result;
                                    $.each(fxkpd, function (i, fxky) {
                                        if (d[cxjg.jgzdid.toUpperCase()] == fxky.value) {
                                            checkflag = true;
                                            result = '<span class="' + fxky.class + '">' + formatBdcdyh(d.BDCDYH) + '</span>';
                                        }
                                    });
                                    if (checkflag === false) {
                                        result = formatBdcdyh(d.BDCDYH);
                                    }
                                    return result;
                                };
                            } else {
                                colObject["templet"] = function (d) {
                                    var checkflag = false;
                                    var result;
                                    $.each(fxkpd, function (i, fxky) {
                                        if (d[cxjg.jgzdid.toUpperCase()] == fxky.value) {
                                            checkflag = true;
                                            result = '<span class="' + fxky.class + '">' + d[cxjg.jgzdid.toUpperCase()] + '</span>';
                                        }
                                    });
                                    if (checkflag === false) {
                                        result = d[cxjg.jgzdid.toUpperCase()];
                                    }
                                    return result;
                                }
                            }
                        } else {
                            // 需要通过字典项转换的
                            if (!isNullOrEmpty(cxjg.zdid)) {
                                colObject["zdid"] = cxjg.zdid;
                                colObject["templet"] = function (d) {
                                    return convertDmToMc(cxjg.zdid, d[cxjg.jgzdid.toUpperCase()], 'zdList');
                                };
                                zdStr = zdStr + cxjg.zdid + ",";
                            }
                            // 单元号加空格处理
                            if (cxjg.jgzdid === "bdcdyh" || cxjg.jgzdid === "BDCDYH") {
                                colObject["templet"] = function (d) {
                                    return formatBdcdyh(d.BDCDYH);
                                };
                            }
                        }
                        // 是否隐藏
                        if (cxjg.mrxs == 0) {
                            colObject["hide"] = true;
                        }
                        if (cxjg.jgtype !== "button") {
                            cols.push(colObject);
                        }
                        //省略号在前
                        if (cxjg.slhwz == 1) {
                            reverseList.push(cxjg.jgzdid);
                        }
                    });

                    tableConfig.cols[0] = cols;
                    tableConfig.where = {data: ""};

                    var hascheckbox = false;
                    /*头部工具
                     * name:工具点击事件名
                     * title：工具显示名*/
                    if (data.ymgj) {

                        $(".layui-table-tool").css({
                            'display': "block"
                        });
                        var ymgj = eval('(' + data.ymgj + ')');
                        $.each(ymgj, function (i, gj) {
                            hascheckbox = true;
                            if(gj.name == 'loadAllData'){
                                loadAllData = true;
                                return true;
                            }
                            globalToolbarList.push({
                                layEvent: gj.name,
                                text: gj.title,
                                renderer: gj.name + '()',
                                class: 'bdc-major-btn'
                            });
                        });
                    }

                    if (data.zdyymgj) {

                        $(".layui-table-tool").css({
                            'display': "block"
                        });
                        var zdyymgj = eval('(' + data.zdyymgj + ')');
                        $.each(zdyymgj, function (i, gj) {
                            hascheckbox = true;
                            globalToolbarList.push({
                                layEvent: gj.name,
                                text: gj.title,
                                renderer: gj.name + '()',
                                class: gj.class,
                                model: isNullOrEmpty(gj.model) ? "" : gj.model
                            });
                        });
                    }
                    if (hascheckbox === true) {
                        countWidth += 60;
                        cols.unshift({type: 'checkbox', fixed: 'left'});
                    }

                    /*
                     * 加载自定义js
                     */
                    if (data.js) {
                        var jsList = eval('(' + data.js + ')');
                        $.each(jsList, function (i, js) {
                            jQuery.getScript(js)
                                .done(function () {
                                    if (typeof loadComplete !== 'undefined' && loadComplete instanceof Function) {
                                        load = loadComplete;
                                    }
                                });
                        });
                    }
                    if(data.pzjs){
                        eval(data.pzjs);
                        if (typeof loadComplete !== 'undefined' && loadComplete instanceof Function) {
                            load = loadComplete;
                        }
                    }
                    if (hgjArr.length !== 0) {
                        var width = 20
                        $.each(hgjArr, function (i, hgjdata) {
                            var hgj = eval('(' + hgjdata + ')');
                            var lk = isNaN(hgj.width) ? 80 : parseInt(hgj.width);
                            width += lk;
                        })
                        /*行内工具
                         * name:工具点击事件名
                         * title：工具显示名*/
                        cols.push({
                            title: '操作',
                            fixed: 'right',
                            align: 'center',
                            minWidth: 80,
                            toolbar: '#rowTools',
                            width: width
                        });

                        $.each(hgjArr, function (i, hgjdata) {
                            var hgj = eval('(' + hgjdata + ')');
                            var className;
                            if (i == 0) {
                                className = 'bdc-major-btn';
                            } else {
                                className = 'bdc-secondary-btn';
                            }
                            globalTooList.push({
                                layEvent: hgj.id,
                                class: className,
                                text: hgj.title,
                                renderer: hgj.name
                            });
                        });
                    }
                    tableConfig.done = function (res, curr, count) {
                        setHeight();
                        if (!isNullOrEmpty(data.fxkys)) {
                            changeCheckboxBackground(eval('(' + data.fxkys + ')'));
                        }
                        load(res);
                    };
                    table.render(tableConfig);

                    setElementAttrByModuleAuthorityBeforeRendering(moduleCode, data);

                    tableConfig.url = '/realestate-inquiry-ui/dtcx/list/result';
                    tableConfig.data = undefined;
                    // 行按钮监听事件
                    table.on("tool(dataTable)", function (obj) {
                        console.log(obj);
                        $.each(globalTooList, function (index, item) {
                            if (obj.event == item.layEvent && item.renderer) {
                                eval(item.renderer + "(" + JSON.stringify(obj) + "," + JSON.stringify(obj.data) + "," + JSON.stringify(item.text) + ")");
                            }
                        });

                    });

                    // 工具栏监听事件
                    table.on("toolbar(dataTable)", function (obj) {
                        var checkStatus = table.checkStatus(obj.config.id);
                        checkeddata = checkStatus.data;
                        console.log(obj);
                        $.each(globalToolbarList, function (index, item) {
                            if (obj.event == item.layEvent && item.renderer) {
                                eval(item.renderer);
                            }
                        });
                    });
                    /**
                     * 监听排序事件
                     */
                    table.on('sort(dataTable)', function (obj) {
                        queryObject["cxsql"] = $("#cxsql").val();
                        queryObject["cxdh"] = cxdh;
                        queryObject["sidx"] = obj.field;
                        queryObject["sord"] = obj.type;
                        tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
                        if (recordColsLength < 4) {
                            $(".layui-table-tool-self").hide();
                        }
                    });

                    /**
                     * 监听复选框事件，缓存选中的行数据，用于跨页选择记录处理
                     */
                    table.on('checkbox(dataTable)', function (obj) {
                        // 获取选中或者取消的数据
                        var checkData = obj.type == 'one' ? [obj.data] : currentTablePageData;
                        if (checkData) {
                            $.each(checkData, function (i, v) {
                                if (!isNullOrEmpty(v.ID)) {
                                    // 默认用查询字段ID作为主键，所以相关SQL需要有ID字段
                                    if (obj.checked) {
                                        //.增加已选中项
                                        layui.sessionData('allPageCheckedData', {
                                            key: v.ID, value: v
                                        });
                                    } else {
                                        //.删除
                                        layui.sessionData('allPageCheckedData', {
                                            key: v.ID, remove: true
                                        });
                                    }
                                }
                            });
                        }
                        //添加已选条目数
                        var checkedData = layui.sessionData('allPageCheckedData');
                        var arrcheck = Object.keys(checkedData);
                        var total = arrcheck.length;
                        $('.xuanze').html("已选"+total+"条");
                    });

                    // 获取页面所需的zdlist
                    if (!isNullOrEmpty(zdStr)) {
                        zdStr = zdStr.substring(zdStr.length - 1, zdStr);
                        zdList = getMulZdList(zdStr);
                    }
                    //自定义查询的字段
                    if (cols.length < 5) {
                        $(".layui-table-tool-self").hide();
                    }
                    recordColsLength = cols.length;
                    setTimeout(removeModal(), 100);
                },
                error: function (e) {
                    showErrorInfo(e);
                }
            });
        }
        var changeColor = function changeColor() {
            //字段翻转
            if (reverseList.length > 0) {
                reverseTableCell(reverseList);
            }
            load();

        }
        form.on('submit(search)', function (data) {
            var searchParamFlag = false;
            var searchParamArr = $('.cxtj');

            // 提交前参数校验回调方法，由相关自定义查询JS定义。
            try {
                if (submitCheckParam && typeof (submitCheckParam) == "function") {
                    var errorMsg = submitCheckParam();
                    if(isNotBlank(errorMsg)){
                        layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + errorMsg);
                        return false;
                    }
                }
            } catch (e) {
            }

            for (var i = 0; i < searchParamArr.length; i++) {
                var id = $(searchParamArr[i]).attr("id");

                // 如果有选择时间，开始时间不能大于结束时间
                // 由于无法区分自定义查询条件包含的开始时间结束时间，因此简单根据前后条件都包含“时间”字段判断
                if (cxtjList && cxtjList.length > 0) {
                    for (var index = 0; index < cxtjList.length; index++) {
                        if (id == cxtjList[index].tjzdid) {
                            var tjzdname = cxtjList[index].tjzdname;
                            if (!isNullOrEmpty(tjzdname) && tjzdname.indexOf("时间") > -1) {
                                if (cxtjList[index + 1] && !isNullOrEmpty(cxtjList[index + 1].tjzdname)) {
                                    var tjzdname2 = cxtjList[index + 1].tjzdname;
                                    if (!isNullOrEmpty(tjzdname2) && tjzdname2.indexOf("时间") > -1) {
                                        var startTime = $("#" + id).val();
                                        var endTime = $("#" + cxtjList[index + 1].tjzdid).val();
                                        if (!isNullOrEmpty(startTime) && !isNullOrEmpty(endTime) && new Date(startTime) > new Date(endTime)) {
                                            warnMsg("开始时间不能大于结束时间！");
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                var isMh = id.substring(id.length - 2, id.length);
                //当输入框不是模糊类型的选择框， 并且输入框有值，则说明已输入条件
                if (isMh != "mh" && $(searchParamArr[i]).val() != "") {
                    searchParamFlag = true;
                    break;
                }
            }
            if (!searchParamFlag && rzlx != "zjcx" && rzlx != "clsjcx") {
                layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">请输入至少一个条件');
                return false;
            }


            queryObject = data.field;
            // 保存查询日志
            saveDetailLog(rzlx, rzmc, queryObject);
            queryObject["cxsql"] = $("#cxsql").val();
            queryObject["cxdh"] = cxdh;

            if (queryObject['cfwh']) {
                var cfwhStr = queryObject['cfwh'];
                queryObject['cfwh'] = cfwhStr;
            }

            // 外部调用页面传入的参数，目前只支持单个参数
            var urlParam = $.getUrlParam("param");
            if (!isNullOrEmpty(urlParam)) {
                queryObject["param"] = urlParam;
            }

            // 每次查询清除下选中缓存数据
            layui.sessionData("allPageCheckedData", null);
            // 回调自定义处理方法，由相关自定义查询JS定义
            try {
                if (beforeTableReload && typeof (beforeTableReload) == "function") {
                    beforeTableReload();
                }
            } catch (e) {
            }
            queryObject.moduleCode =moduleCode;

            tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl, changeColor);
            if (recordColsLength < 4) {
                $(".layui-table-tool-self").hide();
            }
            initUploadInst();
            importexcelCx();
            return false;
        });

        if (searchFlag === true) {
            queryObject["cxsql"] = $("#cxsql").val();
            queryObject["cxdh"] = cxdh;
            queryObject.moduleCode =moduleCode;
            tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
        }
        formLoad();
    });

    initUploadInst();
    form.render('select')
    importexcelCx();
    if(loadAllData){
        tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
        importexcelCx();
        removeModal();
    }

    // 根据.bdc-search-content高度自适应调整.bdc-percentage-container的padding-top
    (function(){
        var height = $('.bdc-search-content').height() + 10 + 'px';
        $('.bdc-percentage-container').css('padding-top', height);
        //查询条件为空,不展示查询模块
        if(cxtjJs ===0){
            $('.bdc-search-content').hide();
            $('.bdc-percentage-container').css('padding-top', "10px");
        }
    })();

});


/**
 * 获取url中的参数
 * @param name
 * @returns {*}
 */
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) {
        searchFlag = true;
        queryObject[name] = decodeURIComponent(r[2]);
        return decodeURIComponent(r[2]);
    }
    return null; //返回参数值
}

/**
 * 查看登记簿
 * @date 2019/07/23
 * @author hanyi
 * @return
 */
function showGjSearch() {
    var isActive = $("#gjSearch-area").attr("active");
    if (isActive == 'true' || isActive == true) {
        $("#gjSearch-area").attr("active", "false");
        $("#gjSearch-area").hide();
    } else {
        $("#gjSearch-area").attr("active", "true");
        $("#gjSearch-area").show();
    }
    $('#search-div').toggleClass('bdc-button-box-four');
    $('.bdc-percentage-container').css('padding-top', $('.bdc-search-content').height() + 10);
}

function load(res) {

}

/**
 * 导出查询结果
 * @date 2019/07/23
 * @author hanyi
 * @return
 */
function openwin(obj, data, sjmc) {
    var url = $("#" + obj.event).val();
    $.each(data, function (key, value) {
        url = url.replace("#{" + key + "}", value);
        url = url.replace("#{" + key.toLowerCase() + "}", value);
    });

    // 保存Excel导出日志
    saveDetailLog(rzlx + "_" + obj.event, rzmc + "-" + sjmc, data);
    window.open(url);
}

/**
 * 导出excel文件
 */
function exportExcel() {
    var queryObjectCopy = JSON.parse(JSON.stringify(queryObject));
    delete queryObjectCopy.cxid;
    var dataStringCopy = JSON.stringify(queryObjectCopy);
    if (dataStringCopy === '{}') {
        warnMsg("请先查询数据");
        return;
    }
    var dataString = JSON.stringify(queryObject);
    var checkedDataAll = layui.sessionData('allPageCheckedData');//跨页选中；
    var dataAll = new Array();
    $.each(checkedDataAll, function(key, value){
        dataAll.push(value);
    })

    if (dataAll.length === 0) {
        layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要导出Excel的数据行!');
        // layer.confirm("是否导出全部数据？", {
        //     title: "提示",
        //     btn: ["确认", "取消"],
        //     btn1: function (index) {
        //         var data = JSON.stringify(checkeddata);
        //
        //         var tempForm = $("<form>");
        //         tempForm.attr("id", "tempForm1");
        //         tempForm.attr("style", "display:none");
        //         tempForm.attr("target", "export");
        //         tempForm.attr("method", "post");
        //         tempForm.attr("action", '/realestate-inquiry-ui/dtcx/export/excel');
        //
        //         var dataInput = $("<input>");
        //         dataInput.attr("type", "hidden");
        //         dataInput.attr("name", "dataString");
        //         dataInput.attr("value", dataString);
        //         tempForm.append(dataInput);
        //
        //         var dataInputSelect = $("<input>");
        //         dataInputSelect.attr("type", "hidden");
        //         dataInputSelect.attr("name", "data");
        //         dataInputSelect.attr("value", data);
        //         tempForm.append(dataInputSelect);
        //
        //         // 保存Excel导出日志
        //         saveDetailLog(rzlx + "_EXPORT", rzmc + "-导出Excel",{"DCNR":data});
        //
        //         tempForm.on("submit", function () {
        //         });
        //         tempForm.trigger("submit");
        //         $("body").append(tempForm);
        //         tempForm.submit();
        //         $("tempForm1").remove();
        //
        //         layer.close(index);
        //     },
        //     btn2: function (index) {
        //         return;
        //     }
        // });
    } else {
        var data = JSON.stringify(dataAll);

        var tempForm = $("<form>");
        tempForm.attr("id", "tempForm1");
        tempForm.attr("style", "display:none");
        tempForm.attr("target", "export");
        tempForm.attr("method", "post");
        tempForm.attr("action", '/realestate-inquiry-ui/dtcx/export/excel');

        var dataInput = $("<input>");
        dataInput.attr("type", "hidden");
        dataInput.attr("name", "dataString");
        dataInput.attr("value", dataString);
        tempForm.append(dataInput);

        var dataInputSelect = $("<input>");
        dataInputSelect.attr("type", "hidden");
        dataInputSelect.attr("name", "data");
        dataInputSelect.attr("value", data);
        tempForm.append(dataInputSelect);

        // 保存Excel导出日志
        saveDetailLog(rzlx + "_EXPORT", rzmc + "-导出Excel", {"DCNR": data});

        tempForm.on("submit", function () {
        });
        tempForm.trigger("submit");
        $("body").append(tempForm);
        tempForm.submit();
        $("tempForm1").remove();
    }
}

/**
 * 全部导出excel文件 添加确认弹窗
 */
function exportExcelAll() {
    layer.confirm("是否导出全部数据？",{
        title: "提示",
        btn: ["确认","取消"],
        btn1: function (index) {
            reallyExportExcelAll();
            layer.close(index);
        },
        btn2: function (index) {
            return;
        }
    });
}
/**
 * 全部导出excel文件
 */
function reallyExportExcelAll() {
    var dataString = JSON.stringify(queryObject);

    if (dataString === '{}') {
        warnMsg("请先查询数据");
        return;
    }

    if (!queryObject || isNullOrEmpty(queryObject.cxdh)) {
        warnMsg("请先查询数据");
        return;
    }


    var tempForm = $("<form>");
    tempForm.attr("id", "tempForm1");
    tempForm.attr("style", "display:none");
    tempForm.attr("target", "export");
    tempForm.attr("method", "post");
    tempForm.attr("action", '/realestate-inquiry-ui/dtcx/export/excel');

    var dataInput = $("<input>");
    dataInput.attr("type", "hidden");
    dataInput.attr("name", "dataString");
    dataInput.attr("value", dataString);
    tempForm.append(dataInput);

    var dataInputSelect = $("<input>");
    dataInputSelect.attr("type", "hidden");
    dataInputSelect.attr("name", "data");
    dataInputSelect.attr("value", "");
    tempForm.append(dataInputSelect);

    // 保存Excel导出日志
    saveDetailLog(rzlx + "_EXPORT", rzmc + "-导出Excel", {"DCNR": "全部数据"});

    tempForm.on("submit", function () {
    });
    tempForm.trigger("submit");
    $("body").append(tempForm);
    tempForm.submit();
    $("tempForm1").remove();
}

window.initUploadInst = function () {

    uploadInst = upload.render({
        elem: '.import' //绑定元素
        , accept: 'file'
        , url: '/realestate-inquiry-ui/rest/v1.0/jgpz/import/excel' //上传接口
        , done: function (res) {
            if (res == null || res.length == 0) {
                ityzl_SHOW_INFO_LAYER("导入失败,请检查数据格式");
            } else {
                layui.table.reload('pageTable', {page: {curr: 1}});
                initUploadInst();
                ityzl_SHOW_SUCCESS_LAYER("导入成功")
            }
            removeModal();
            // $("#search").click();
        }
        , error: function (e) {
            removeModal();
            layer.alert('导入数据解析失败，请检查模板是否正确！', {title: '提示'});
        }
    });
}

function importexcel() {
    // addModel();
    uploadInst.upload();
    return false;
}

// 导入Excel保留查询条件
var cxtjblVal = {};

/**
 * 自定义查询导入Excel查询
 */
window.importexcelCx = function () {
    var cxtjCompare = [];
    if (cxtjList && cxtjList.length > 0) {
        for (var index = 0; index < cxtjList.length; index++) {
            if (cxtjList[index].mrxs == 1) {
                cxtjCompare[index] = cxtjList[index].tjzdid;
            }
        }
    }
    upload.render({
        elem: '#excelCx' //绑定元素
        , url: '/realestate-inquiry-ui/rest/v1.0/drcx/excelCx'
        , accept: 'file'
        , data: {cxtjCompare: cxtjCompare}
        , exts: 'xls|xlsx'
        , before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
            cxtjblVal = {};
            for (var index = 0; index < cxtjList.length; index++) {
                var cxtjId = cxtjList[index].tjzdid;
                var cxtjVal = $("#" + cxtjId).val();
                if(1 === cxtjList[index].drcxbl && !isNullOrEmpty(cxtjVal)) {
                    // 保留字段不动
                    cxtjblVal[cxtjId] = cxtjVal;
                    cxtjblVal[cxtjId + "_MHZD"] = $("#" + cxtjId + "mh" +"[name=" + cxtjId + "mh" + "]").val();
                } else {
                    // 非保留字段清空
                    $("#" + cxtjId).val("");
                    form.render("select");
                }
            }
            addModel();
        }
        , done: function (res) {
            /**
             * 导入查询保留字段，如果设置“是”，在Excel导入查询时候，对应字段在页面输入的查询条件会和Excel里的查询条件一起查询，
             * 如果查询字段冲突，则以Excel中的查询条件值为准
             */
            if(!$.isEmptyObject(cxtjblVal)) {
                $.each(cxtjblVal, function (key, value) {
                    // 导入的Excel中查询条件是否为空
                    var isExcelCxtjNull = isNullOrEmpty(res[key]) || res[key].indexOf("&&") === 0;
                    // 非模糊类型字段、并且Excel中未设值的保留字段复用页面查询条件
                    if(key.indexOf("_MHZD") === -1 && !isNullOrEmpty(key) && !isNullOrEmpty(value) && isExcelCxtjNull) {
                        var cxtjNum = res[key].split("|");
                        if(cxtjNum && cxtjNum.length > 0) {
                            res[key] = value;
                            for(var i = 1; i <cxtjNum.length; i++) {
                                res[key] = res[key] + "|" + value;
                            }
                            res[key + "mh"] = cxtjblVal[key + "_MHZD"];
                        }
                    }
                });
            }

            isExcelQuery = true;
            layer.closeAll('loading');
            if (res == null || res.length == 0) {
                ityzl_SHOW_INFO_LAYER("导入失败,请检查数据格式");
            } else if (res.srzExistFlag == 1) {
                ityzl_SHOW_INFO_LAYER("Excel表格输入为空，无法查询数据");
            } else {
                res.cxid = cxtjList[0].cxid;
                tableReload('pageTable', {data: JSON.stringify(res)}, dataUrl);
                queryObject=res;
                queryObject["cxdh"] = cxdh;
                importexcelCx();
                ityzl_SHOW_SUCCESS_LAYER("导入查询成功");
            }
            removeModal();
        }
        ,
        error: function () {
            removeModal();
            layer.alert("查询失败")
        }
    })
}

/**
 * 自定义查询导出Excel模板
 * @param obj
 * @param data
 */
function dccx(obj, data) {
    var excelCxtjList = [];
    $.ajax({
        url: "/realestate-inquiry-ui/dtcx/get/" + cxdh,
        type: "get",
        async: false,
        success: function (data) {
            excelCxtjList = data.cxtjDOList;
        }
    })
    var excelList = [];
    var excelBody = new Array();
    if (excelCxtjList && excelCxtjList.length > 0) {
        for (var index = 0; index < excelCxtjList.length; index++) {
            if (excelCxtjList[index].mrxs == 1) {
                excelBody.push({
                    tjzdname: excelCxtjList[index].tjzdname,
                    canmhcx: excelCxtjList[index].canmhcx,
                    tjzdid: excelCxtjList[index].tjzdid,
                    mrmhlx: excelCxtjList[index].mrmhlx
                })
            }
        }
    }
    var cxVal = "";
    var comVal = "";
    if (zdList) {
        for (var tempValue in zdList) {
            var temp = "";
            for (var index = 0; index < zdList[tempValue].length; index++) {
                temp += zdList[tempValue][index].MC + ',';
                comVal += zdList[tempValue][index].MC + ":" + zdList[tempValue][index].DM + "&&";
            }
            cxVal += tempValue + ":" + temp.substring(0, temp.length - 1);
            cxVal += "&&";
        }
    }
    comVal += "cxid:" + excelCxtjList[0].cxid;
    excelList[0] = {"excelBody": excelBody};
    excelList[1] = {"excelVal": cxVal.substring(0, cxVal.length - 2)};
    excelList[2] = {"comVal": comVal};
    // 设置Excel基本信息
    $("#data").val(JSON.stringify(excelList));
    $("#form").submit();
}

/**
 * 自定义查询导入Excel查询
 * （逻辑绑定在上传控件commonCx.js-importexcelCx，这里避免报错声明空函数）
 * @param obj
 * @param data
 */
function excelCx(obj, data) {
}

/**
 * 自定义查询下载指定模板文件
 * （模板存放路径：printPth/excelmodel/name.xls，下载的模板名称由自定义查询配置按钮的model指定）
 * @param obj
 * @param data
 */
function xzzdmb(obj, data) {
    var modelName = $("#xzzdmb").attr("model");
    if(isNullOrEmpty(modelName)) {
        warnMsg("未定义下载文件名称！");
        return;
    }

    $("#fileName").val(modelName);
    $("#downloadFileForm").submit();
}

/**
 * 保存Excel批量查询日志
 * @param data 查询参数、文件路径等信息
 * @param pageData 当前页查询结果数据
 */
function saveExcelQueryLog(data, pageData) {
    if (!isExcelQuery) {
        return;
    }

    var param = {};
    param.cxtj = JSON.stringify(data);
    param.excel = data.excelFilePath;
    param.cxjg = JSON.stringify(pageData);
    $.ajax({
        url: "/realestate-inquiry-ui/log/xtcx",
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(param)
    });

    isExcelQuery = false;
}

/**
 * 自定义查询打印（这里预留两个打印方法，页面可配置两个不同打印按钮）
 */
function print1(obj, data) {
    var dylx = $("#print1").attr("model");
    printData(dylx);
}
function print2(obj, data) {
    var dylx = $("#print2").attr("model");
    printData(dylx);
}

/**
 * 执行自定义查询打印逻辑
 * @param dylx 打印类型
 * @param czlButton 操作栏打印按钮
 */
function printData(dylx,czlButton) {
    if(isNullOrEmpty(dylx)) {
        warnMsg("未定义打印数据类型！");
        return dyzt;
    }
    var data  = JSON.stringify(checkeddata);
    //操作栏打印按钮，无需判断选中
    if (isNullOrEmpty(czlButton)) {
        if(!checkeddata || 0 === checkeddata.length) {
            warnMsg("请选择需要打印的数据！");
            return dyzt;
        }
    }
    if (!isNullOrEmpty(czlButton)) {
        data = JSON.stringify(czlButton);
    }
    addModel();
    // 缓存要打印的参数信息
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/print/zdycx/param",
        type: "POST",
        data: data,
        contentType: 'application/json',
        dataType: 'text',
        success: function (redisKey) {
            if (!isNullOrEmpty(redisKey)) {
                // 执行打印
                var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/zdycx/" + dylx + "/" + redisKey + "/xml";
                // 模板默认打印类型命名的fr3，pdf打印模板由对应打印数据源配置
                var mburl = getIP() + "/realestate-inquiry-ui/static/printModel/" + dylx + ".fr3";

                printChoice(dylx, "realestate-inquiry-ui", dataUrl, mburl, false, dylx);
                dylx = true;
            } else {
                failMsg("打印出错，请重试！");
            }
        },
        error: function () {
            failMsg("打印出错，请重试！");
            dylx = false;
        },
        complete: function () {
            removeModal();
        }
    });
    return dylx;
}


function getAjaxData(url){
    var deferred =$.Deferred();
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (data) {
            if(data){
                deferred.resolve(data);
            }else{
                deferred.resolve([]);
            }
        },error:function(e){
            console.info(e);
            deferred.reject(e);
        }
    });
    return deferred;
}

function getUserData(){
    var data = new Array();
    if(UserCache.size !=0 ){
        UserCache.forEach(function(key){
            data.push.apply(data, key);
        });
    }
    var list = uniqueArray(data);
    console.info(list);
    return list;
}

function uniqueArray(data){
    if (data.length == 0) {
        return [];
    }
    var temp = {};
    var newArray = [];
    for (var i = 0; i < data.length; i++) {
        if (!temp[data[i].id]) {
            newArray.push(data[i]);
            temp[data[i].id] = true;
        }
    }
    return newArray;
}

/**
 *  创建附件文件夹
 */
function uploadFj(id,cxdh,fjwjjmc){
        $.ajax({
            url: '/realestate-inquiry-ui/dtcx/createFjgl?id=' + id + '&cxdh=' + cxdh + '&fjwjjmc=' + fjwjjmc,
            type: 'GET',
            dataType: "text",
            success: function (sid) {
                if (isNullOrEmpty(sid)) {
                    layer.msg("文件夹生成失败");
                }
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
}

/**
 * 查看附件
 * @param obj
 * @param data
 */
function fj(obj,data){
    var id = data.FJID;
    // 获取自定义附件文件夹名称，默认为"附件文件夹"
    var fjwjjmc = data.FJWJJMC;
    if (isNullOrEmpty(fjwjjmc)){
        fjwjjmc = Base64.encode("附件文件夹");
    } else {
        fjwjjmc = Base64.encode(data.FJWJJMC);
    }
    var cxdh = $.getUrlParam("cxdh")
    var bdcSlWjscDTO = queryBdcSlWjscDTO();
    var host = document.location.host
    bdcSlWjscDTO.spaceId = cxdh+id;
    bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + host + "/storage";
    var url = "/realestate-inquiry-ui/yancheng/zq/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));

    $.ajax({
        url: '/realestate-inquiry-ui/dtcx/queryFjgl?id=' + id + '&cxdh=' + cxdh + '&fjwjjmc=' + fjwjjmc,
        type: 'GET',
        dataType: "text",
        success: function (DownUrlid) {
            if (isNullOrEmpty(DownUrlid)) {
                uploadFj(id,cxdh,fjwjjmc);
                openSjcl(url, "附件上传");
            } else {
                openSjcl(url, "附件上传");
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

/**
 * 查询收件材料所需参数（打开附件上传使用）
 */
function queryBdcSlWjscDTO() {
    var bdcSlWjscDTO = {};
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/dwgz/getFileCs',
        type: 'GET',
        async: false,
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            if (isNotBlank(data)) {
                bdcSlWjscDTO = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return bdcSlWjscDTO;
}

/**
 * 打开附件上传
 */
function openSjcl(url, title) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true
    });
    layer.full(index);
}

/**
 * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
 * @param zdname 字典名 bdclx
 * @param zdDm 字典代码 1
 * @param zdListName JS中保存字典数据的变量名 默认为zdList
 * @return
 * @description 前台的字典转换，代码转换为名称
 */
function convertDmToMc(zdname, zdDm, zdListName) {
    if (zdDm !== '' && zdDm !== 'undefined' && zdDm !== null && zdDm !== undefined && zdDm !== 'null'){
        zdDm = zdDm+"";
        var mc = "";
        var zdDmList = zdDm.split(',');
        if (!isNullOrEmpty(zdDmList)) {
            for (var i = 0; i < zdDmList.length; i++) {
                zdDm = zdDmList[i];
                if (!isNullOrEmpty(mc)){
                    mc = mc + "," + convertZdDmToMc(zdname, zdDm, zdListName);
                } else {
                    mc = convertZdDmToMc(zdname, zdDm, zdListName);
                }
            }
        }
        return mc;
    }
    return '';
}