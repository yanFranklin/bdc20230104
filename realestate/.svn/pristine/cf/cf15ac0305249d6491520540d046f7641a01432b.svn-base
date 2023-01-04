layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

//页面入口
var zdList;
var laytpl,form;
// 流程： dj 冻结流程、 jd 解冻流程
var lc =  getQueryString("lc");

layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    laytpl = layui.laytpl;
    if(lc =="dj"){
        $(".content-title p:first-child").html("不动产冻结信息");
        $(".layui-tab-title .li-djjd").html("冻结信息");
    }else{
        $(".content-title p:first-child").html("不动产解冻信息");
        $(".layui-tab-title .li-djjd").html("解冻信息");
    }
    addModel();
    setTimeout("loadButtonArea('slymdj')", 10);
    setTimeout(function () {
        try {
            $.when(loadZdData(),loadFormData(), loadJbxx()).done(function () {
                removeModal();
            })
        } catch (e) {
            removeModal();
            ERROR_CONFIRM("请求失败", e.message);
            return;
        }
    }, 10);

    var isSubmit = true;

    form.on("submit(saveData)", function (data) {
        if (!isSubmit) {
            layer.msg('必填项不能为空', {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {
            addModel();
            setTimeout(function () {
                try {
                    $.when(saveDjxx(),saveXmxx(),saveSjcl()).done(function () {
                        removeModal();
                        ityzl_SHOW_SUCCESS_LAYER("保存成功");
                    })
                } catch (e) {
                    removeModal();
                    ERROR_CONFIRM("保存失败", e.message);
                    return
                }
            }, 10);
            return false;
        }

    });

    //监听冻结原因 单击事件
    $('.layui-tab-content').on('click','.bdc-pj-popup',function(){
        var $thisTextarea = $(this).siblings('textarea');
        var reasonTxt = $thisTextarea.val();
        layer.open({
            title: '原因',
            type: 1,
            area: ['960px'],
            btn: ['保存'],
            content: $('#reasonPopup'),
            yes: function(index, layero){
                //提交 的回调
                $thisTextarea.val($('#reasonPopup textarea').val());
                $('#reasonPopup textarea').val('');
                layer.close(index);
            }
            ,btn2: function(index, layero){
                //取消 的回调
                $('#reasonPopup textarea').val('');
            }
            ,cancel: function(){
                //右上角关闭回调
                $('#reasonPopup textarea').val('');
            }
            ,success: function () {
                $('#reasonPopup textarea').val(reasonTxt);
            }
        });
    });

});

// 加载按钮
function loadBtn() {
    if (qlrsfxxid === null && ywrsfxxid === null) {
        setAllElementDisabled()
    } else {
        getStateById(readOnly, formStateId, "sfxx","","bdcSfxxTable");
    }
}

// 加载字典项数据
function loadZdData() {
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

//加载基本信息模块（入口）
function loadJbxx() {
    getReturnData("/slym/xm", {processInsId: processInsId}, 'GET', function (data) {
        if (isNotBlank(data)) {
            bdcXm = data;
            xmid = data.xmid;
            sply = data.sply;
            //面积单位为空时默认为平方米
            if (data.mjdw === null || data.mjdw === '') {
                data.mjdw = '1'
            }
            //判断是否是虚拟单元号
            isXndyh = checkXndyh(data.bdcdyh);
            generateJbxx(data);
        }
    }, function (err) {
        console.log(err);
    });
}

function loadFormData(){
    var url;
    if(lc == "dj"){
        url = "/slym/sd/getSdxx";
    }else{
        url = "/slym/sd/getJdxx";
    }
    getReturnData(url, {gzlslid: processInsId}, 'GET', function (data) {
        if (isNotBlank(data)) {
            generateDjjdxx(data);
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        generateSjcl(data, "");
    }, function (err) {
        console.log(err);
    });
}

//组织基本信息到页面
function generateJbxx(bdcxmxx) {
    var json = {
        bdcxmxx: bdcxmxx,
        zd: zdList,
    };
    var tpl = jbxxTpl.innerHTML, view = document.getElementById('sljbXx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    renderDate(form);
    getStateById(readOnly, formStateId, 'slymdj');
    form.render('select');
    disabledAddFa();
    loadSjcl();
}

function generateDjjdxx(data){
    // 登记原因
    var djxldjyyList = {};
    var djxl = data.bdcXmDO.djxl;
    if (isNotBlank(djxl)) {
        //查看登记原因
        getReturnData("/slym/xm/queryDjxlDjyy", {djxl: djxl}, 'GET', function (data) {
            if (isNotBlank(data)) {
                djxldjyyList = data;
            }
        }, function (err) {
            console.log(err);
        }, false);
    }
    var bdczssdList = [];
    if(isNotBlank(data.zssdDOList)){
        bdczssdList = data.zssdDOList;
    }else{
        bdczssdList = data.dysdDOList;
    }
    var json = {
        bdcxmxx: data.bdcXmDO,
        bdczssdList: bdczssdList,
        zd: zdList,
        djxldjyy: djxldjyyList,
    };
    // 加载基本信息\
    var tpl, view = document.getElementById('formxx');
    if(lc == "dj"){
        tpl = djxxTpl.innerHTML;
    }else{
        tpl = jdxxTpl.innerHTML;
    }
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

    // 渲染不动单单元列表
    var bdcdyListTpl = bdcdyxxTpl.innerHTML, bdcdyxxView = document.getElementById('bdcdyxx');
    laytpl(bdcdyListTpl).render(json, function (html) {
        bdcdyxxView.innerHTML = html;
    });

    form.render();
    renderDate(form);
    form.render('select');
    disabledAddFa();
    renderSelect();
    // 加载不动产列表数据
    loadBdcdyh();
}

//组织收件材料到页面
function generateSjcl(data, xmid) {
    sjclNumber = data.length;
    sjclidLists = [];
    if(data !== null && data.length >0) {
        for(var i=0;i<data.length;i++) {
            sjclidLists.push(data[i].sjclid);
        }
    }
    sessionStorage.setItem("sjclidLists",sjclidLists);
    var json = {
        xmid: xmid,
        bdcSlSjclDOList: data,
        zd: zdList
    };
    var tpl = sjclTpl.innerHTML, view = document.getElementById("sjcl");
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    getStateById(readOnly, formStateId, 'slymzh', 'sjcl');
    form.render('select');
    disabledAddFa("sjclForm");
}

// 加载房屋信息
function loadBdcdyh() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var table = layui.table;

        var isSearch = true;
        $(document).keydown(function (event) {
            if (event.keyCode == 13 && isSearch) { //绑定回车
                $("#searchBdcdy").click();
            }
        });
        // //判断回车操作
        $('.bdc-tab-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });

        $('body').on('focus', 'textarea', function () {
            isSearch = false;
        }).on('blur', 'textarea', function () {
            isSearch = true;
        });

        //获取权利信息表头
        var unitTableTitle = [
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'fjh', title: '房间号', minWidth: 100},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积(㎡)', minWidth: 240},
            {field: 'dzwyt', title: '定着物用途', minWidth: 120, templet: '#dzwytTpl'},
            {field: 'ycqzh', title: '原产权证号', minWidth: 280},
            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {
                field: 'fwlx', title: '房屋类型', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.fwlx, zdList.fwlx);
                }
            },
            {
                field: 'fwxz', title: '房屋性质', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.fwxz, zdList.fwxz);
                }
            },
            {field: 'zcs', title: '总层数', minWidth: 100},
            {field: 'szc', title: '所在物理层', minWidth: 100},
            {field: 'szmyc', title: '所在名义层', minWidth: 100},
            {
                field: 'fwjg', title: '房屋结构', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.fwjg, zdList.fwjg);
                }
            },
            {field: 'jzmj', title: '建筑面积', minWidth: 100},
            {field: 'zyjzmj', title: '专有建筑面积', minWidth: 120},
            {field: 'ftjzmj', title: '分摊建筑面积', minWidth: 120},
            {field: 'jyjg', title: '房地产交易价格(万元)', minWidth: 180},
            {field: 'jgsj', title: '竣工时间', minWidth: 150},
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'}
        ];
        var url = getContextPath() + '/slym/ql/listQlByPageJson?sfyql=true';

        // 查询参数
        var data = {
            sortParameter: "xmid ASC"
        };
        data["sfyql"] = true;
        // 根据当前查询参数，获取所有的单元信息
        if(lc == "dj"){ //冻结流程使用工作流实例ID，查询原项目的不动产单元信息
            data["gzlslid"] = processInsId;
        }else{ // 解冻流程获取到原来冻结流程的项目id,在获取冻结流程原项目的不动产单元信息
            getReturnData("/slym/sd/getYxmidByGzlslid",{gzlslid:processInsId},"GET",function (yxmids) {
                if(isNotBlank(yxmids)){
                    data["xmidList"] = yxmids.join(",");
                }
            },function (error) {
                ERROR_CONFIRM("获取原项目数据失败", e.message);
                return;
            },false);
        }
        //提交表单
        $("#searchBdcdy").click(function () {
            var bdcdyArray = $(".bdcdyForm").serializeArray();
            bdcdyArray.forEach(function (item) {
                data[item.name] = item.value;
            });
            data["sfyql"] = true;
            tableReload('xmid', data, url);
            return false;
        });
        //重置操作
        $("#reset").on('click', function () {
            $('.bdc-form-search input').val('');
            $('.bdc-form-search select').val('');
            form.render('select');
        });

        // 获取流程对应的规则用途作为选择项
        getReturnData("/slym/xm/queryZdFwytByGzlslid",{gzlslid:processInsId},"GET",function (data) {
            //清空
            $("#ghyt").empty();
            $('#ghyt').append(new Option("请选择", ""));
            $.each(data, function (index, item) {
                //防止出现对比权籍后，规划用途字典项与登记不一致出现空的情况
                if(item !== null) {
                    $('#ghyt').append(new Option(item.mc, item.dm));// 下拉菜单里添加元素
                }
            });
        },function (error) {
            console.log("用途下拉框获取失败")
        },false);


        var tableConfig = {
            id: 'xmid',
            url: url,
            method: 'post',
            contentType: 'application/json',
            where: data,
            toolbar: "#toolbarBdcdyh",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            parseData: function (res) {
            },
            done: function () {
                var reverseList = ['zl'];
                reverseTableCell(reverseList, 'xmid');

                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.bdc-form-div .layui-show .layui-table-body').height($('.bdc-form-div .layui-show .bdc-table-box').height() - 131);
                    $('.bdc-form-div .layui-show .layui-table-fixed .layui-table-body').height($('.bdc-form-div .layui-show .bdc-table-box').height() - 131 - 17);
                }
                $('.bdc-table-box').on('mouseenter', 'td', function () {
                    if ($(this).text() && ($(this).attr("data-field") === "zl" || $(this).attr("data-field") === "zldz")) {
                        $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>')
                    }
                    $('.layui-table-grid-down').on('click', function () {
                        setTimeout(function () {
                            $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                        }, 20);
                    });
                });
            }
        };

        //加载表格
        loadDataTablbeByUrl('#bdcdyTable', tableConfig);

    })

}

function titleShowUi() {
    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id) {
                return;
            }
            elem = elem.parentNode;
        }
    });
}

function queryZdzhyt(zdzhyt) {
    if (isNotBlank(zdList)) {
        for (var i = 0; i < zdList.tdyt.length; i++) {
            var tdytZd = zdList.tdyt[i];
            if (zdzhyt == tdytZd.DM) {
                zdzhyt = tdytZd.MC;
                break;
            }
        }
    }
    return zdzhyt;
}

function queryDzwyt(dzwyt) {
    if (isNotBlank(zdList.fwyt)) {
        for (var i = 0; i < zdList.fwyt.length; i++) {
            var dzwytZd = zdList.fwyt[i];
            if (dzwyt == dzwytZd.DM) {
                dzwyt = dzwytZd.MC;
                break;
            }
        }
    }
    return dzwyt;
}

// 保存冻结信息
function saveDjxx(){
    // 保存冻结人与冻结原因
    var sdxx = {gzlslid : processInsId};
    var url;
    if(lc == "dj" ){
        url = "/slym/sd/updateSdxxByGzlslid";
        sdxx.sdr = $("input[name='sdr']").val();
        sdxx.sdsj = $("input[name='sdsj']").val();
        sdxx.sdyy = $("#sdyy").val();
        sdxx.jssj = $("input[name='jssj']").val();
    }else{
        url = "/slym/sd/updateJdxx";
        sdxx.jsr = $("input[name='jsr']").val();
        sdxx.jsyy = $("#jsyy").val();
        sdxx.jssj = $("input[name='jssj']").val();
    }
    console.info(sdxx);
    getReturnData(url, JSON.stringify(sdxx), 'PATCH', function () {
    }, function (err) {
        throw err;
    }, false);
}

function saveXmxx(){
    var bdcXmData = {
        djyy : $("#djyy").val(),
        slr : $("input[name='slr']").val(),
        slrid : $("input[name='slrid']").val(),
        bz : $("#bz").val(),
    };
    getReturnData("/slym/xm/list?processInsId=" + processInsId, JSON.stringify(bdcXmData), 'PATCH', function () {
    }, function (err) {
        throw err;
    }, false);
}
