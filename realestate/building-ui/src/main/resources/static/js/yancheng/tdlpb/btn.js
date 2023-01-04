function initYchsBtns(){
    initBtns(ychsRes);
}

function initHsBtns(){
    initBtns(hsRes);
}

function initBtns(resource){
    var info = resourceData.info;
    var btnInfos = [];
    var moreBtnInfo = [];
    var moreBtnList = {};
    /*$.each(info, function () {
        if (this.type == 'BUTTON') {
            if(resource.checkHasTab(this.tabType)){
                if(this.btnMore){
                    if(!eval("moreBtnList." + this.btnMore)) {
                        eval("moreBtnList." + this.btnMore + "=[]");
                    }
                    eval("moreBtnList." + this.btnMore).push(this);
                }else{
                    btnInfos.push(this);
                }
                if(this.value == 'select'){
                    resource.hasSelectBtn = true;
                }
                //存在发证状态按钮
                if(this.value ==="generateZsBtn"){
                    resource.hasFzjlBtn =true;
                }
                //判断是否展示添加按钮
                if(this.value === "addGwcBtn") {
                    var showBtns = showBtnDyids.split(",");
                    var gzldyid = window.parent.getQueryString("processDefKey");
                    if(showBtns.indexOf(gzldyid) > -1) {
                        var a= this.btnClass;
                        var newclass = this.btnClass.replace('bdc-hide','');
                        this.btnClass = newclass;
                    }
                }
            }
        }
    });*/
    /*if (resource.hasSelectBtn || ($("#code").val() == 'accept' && !resource.hasFzjlBtn)) {
        resource.checkBoxHide = true;
    } else if ($("#code").val() === 'getbdcdyh' && version === "yancheng") {
        resource.checkBoxHide = true;
    } else {
        resource.checkBoxHide = false;
    }
*/
    var json = {
        btns: btnInfos, moreBtns: moreBtnList, hssl: chscount
    };

    layui.use(['laytpl', 'form'], function () {
        var laytpl = layui.laytpl;
        var form = layui.form;
        var toolbar = toolbarTpl.innerHTML
            , toolbarDiv = document.getElementById(resource.toorBarId);
        laytpl(toolbar).render(json, function (html) {
            toolbarDiv.innerHTML = html;
        });
        form.render('select');
    });
}


/**
 * 调用外围系统的回调方法
 * @param callbackParam  逗号隔开，第一个参数为 回调函数方法名，之后的参数都是属性
 */
function btnCallBack(callbackParam){
    var callbackFun = callbackParam;
    var keys = [];
    if(callbackParam.indexOf(",")>-1){
        var params = callbackParam.split(",");
        callbackFun = params[0];
        keys = params.slice(1,params.length);
    }
    var cells = [];
    for(var i = 0 ; i < currentRes.data.allHsCells.length;i++){
        var temp = currentRes.data.allHsCells[i];
        if(currentRes.checkboxcheckedList.indexOf(temp.info.fwHsIndex.value) >= 0){
            cells.push(temp);
        }

    }
    var dataArr = getToCartDatas(currentRes,cells,true,false);
    var data = [];
    $.each(dataArr, function (index, v) {
        if(keys.length == 0){
            data.push(v);
        }else{
            var info = {};
            for(var i = 0 ;i < keys.length ; i++){
                if(eval("v."+keys[i])){
                    eval("info."+keys[i]+"=v."+keys[i]);
                }
            }
            if(info){
                data.push(info);
            }
        }
    });
    //console.info(data);
    if(eval("parent."+callbackFun)){
        eval("parent."+callbackFun+"(data)");
    }
}

/**
 * 更多按钮事件
 * @param event
 */
function moreClick(event){
    eval(event + "Function()");
}

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @description 预设按钮功能定义
 */

/**
 * 返回按钮 event:back
 */
function backFunction(){
    backToView(sessionParamObjet.fromurl);
}

/**
 * 导入按钮 event：import
 */
function importFunction(){
    var fwDcbIndex = $("#fwDcbIndex").val();
    importLpb(fwDcbIndex);
}

/**
 * 导出按钮 event：export
 */
function exportFunction(){
    var fwDcbIndex = $("#fwDcbIndex").val();
    exportLpb(fwDcbIndex);
}

/**
 * 构建楼盘表 event：build
 */
function buildFunction(){
    var fwDcbIndex = $("#fwDcbIndex").val();
    var bdcdyfwlx = $("#bdcdyfwlx").val();
    buildLpb({fw_dcb_index : fwDcbIndex,bdcdyfwlx:bdcdyfwlx});
}


/**
 * 预测转实测 event：yczsc
 */
function yczscFunction(){
    var fwDcbIndex = $("#fwDcbIndex").val();
    yczsc(fwDcbIndex);
}

/**
 * 预测实测关联 event：ycscgl
 */
function ycscglFunction(){
    var fwDcbIndex = $("#fwDcbIndex").val();
    ycscgl(fwDcbIndex);
}

/**
 * 刷新坐落 event：zlsx
 */
function zlsxFunction(){
    var fwDcbIndex = $("#fwDcbIndex").val();
    zlsx(fwDcbIndex);
}

/**
 * 计算逻辑幢建筑面积  event：jsLjzJzmj
 */
function jsLjzJzmjFunction(){
    var fwDcbIndex = $("#fwDcbIndex").val();
    jsLjzJzmj(fwDcbIndex);
}

/**
 * 计算逻辑幢分摊土地面积 event：jsLjzFttdmj
 */
function jsLjzFttdmjFunction(){
    var fwDcbIndex = $("#fwDcbIndex").val();
    jsLjzFttdmj(fwDcbIndex);
}

/**
 * 选择按钮 event：select
 */
function selectFunction() {
    currentRes.tabDocument.find(".cell-checkbox").toggleClass('bdc-hide');
    if (currentRes.checkBoxHide) {
        currentRes.checkBoxHide = false;
    } else {
        currentRes.checkBoxHide = true;
    }
    currentRes.checkedList = [];
    layui.use(['table'], function () {
        var table = layui.table;
        table.reload(currentRes.tableResizeId);
    });

    // 选中状态
    for(var key in currentRes.checkedShowColumn){
        var thisKey = eval("currentRes.checkedShowColumn."+key)
        if(thisKey.key){
            // 增加选中状态
            currentRes.tabDocument.find(".bdc-export-tools")
                .find("input[data-key='"+key+"']")
                .next("div[class='layui-unselect layui-form-checkbox']")
                .addClass("layui-form-checked");
            // 单元格中显示选中效果
            showColumn(thisKey,true,currentRes);
        }else{
            showColumn(thisKey,false,currentRes);
        }
    }
}

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param
 * @return
 * @description  显示 checkBox时的 全选
 */
function checkBoxSelectAllFunction(){
    getToCheckDatas(currentRes,currentRes.data.allHsCells,true,true);
    resScrollData(currentRes);
}

function checkBoxInverseSelectFunction(){
    var checkedList = [];
    checkedList = checkedList.concat(currentRes.checkboxcheckedList);
    // 全选
    getToCheckDatas(currentRes,currentRes.data.allHsCells,true,true);
    // 取消选中
    for(var i = 0 ; i < checkedList.length;i++){
        removeCheckedBox(checkedList[i]);
    }
    resScrollData(currentRes);
}

/**
 * 购物车全选按钮
 */
function cartSelectAllFunction(){
    addModel();
    setTimeout(function(){
        addAllCartWithSelectedClass();
    },50);
}

/**
 * 清除筛选按钮
 */
function clearFilterFunction(){
    var $oneOnSelectHide = currentRes.tabDocument.find('.one-onselected-hide');
    for(var i = 0,len = $oneOnSelectHide.length; i < len; i++) {
        var $temp =  $($oneOnSelectHide[i]);
        $temp.addClass('one-onselected').addClass('one-icon').removeClass('one-onselected-hide');
    }
    var $oneNoSelectHide = currentRes.tabDocument.find('.one-no-select-hide');
    for(var i = 0,len = $oneNoSelectHide.length; i < len; i++) {
        var $temp =  $($oneNoSelectHide[i]);
        $temp.addClass('one-no-select').addClass('one-icon').removeClass('one-no-select-hide');
    }
}



var ytList = [];
var jyztList = [];
function requestGhyt(){
    $.ajax({
        url: "../zd/mul",
        dataType: "json",
        data: {
            zdDoNames: "SZdFwytDO"
        },
        async: false,
        success: function (data) {
            var fwytZdList = data.SZdFwytDO;
            if(fwytZdList){
                for(var i = 0 ;i < fwytZdList.length;i++){
                    ytList.push({"name":fwytZdList[i].MC,"value":fwytZdList[i].DM});
                }
            }
        }
    });
}

/**
 * 筛选按钮
 */
var spliceIndex;//超出24个英文字符的下标
var $showTd = '';
function filterFunction(){
    layui.use(['jquery', 'laytpl', 'element', 'table','layer','formSelects'], function () {
        var layer = layui.layer,
            formSelects = layui.formSelects;
        layer.open({
            title: '筛选条件',
            type: 1,
            area: ['430px','375px'],
            btn: ['查询', '取消'],
            content: $('#bdc-popup-small')
            ,yes: function(index, layero){
                addModel();
                layer.close(index);
                setTimeout(function () {
                    var getYtList = formSelects.value('selectYt','name');
                    var getJyztList = formSelects.value('selectJyzt', 'name');
                    var ytStr = '';
                    var jyztStr = '';
                    getYtList.forEach(function (v) {
                        ytStr += v + ',';
                    });
                    getJyztList.forEach(function (v) {
                        jyztStr += v + ',';
                    });
                    ytStr = ytStr.substring(0,ytStr.length-1);
                    jyztStr = jyztStr.substring(0, jyztStr.length - 1);
                    var searchList = [ytStr, jyztStr];//与searchClassList对应关系
                    var searchClassList = ['bdc-search-yt', 'bdc-search-jyzt'];
                    //查询 的回调
                    updatePageStyle(searchList, searchClassList, getYtList, getJyztList);

                    $('.bdc-yt').val('');
                    $('.bdc-jyzt').val('');
                    $('.bdc-zl').val('');
                },10);
            }
            ,btn2: function(index, layero){
                //取消 的回调
                layer.close(index);
            }
            ,success: function () {
                ytList = [];
                jyztList = [];
                var obj = currentRes.filterList.ghyt;
                if (obj && obj.length > 0) {
                    for (var i = 0; i < obj.length; i++) {
                        ytList.push({"name": obj[i], "value": obj[i]});
                    }
                }
                var jyztObj = currentRes.filterList.jyzt;
                if (jyztObj && jyztObj.length > 0) {
                    for (var i = 0; i < jyztObj.length; i++) {
                        jyztList.push({"name": jyztObj[i], "value": jyztObj[i]});
                    }
                }
                var $searchYt = $('.bdc-search-yt');
                if($searchYt.length > 0){
                    var getYtStr = $searchYt.attr('title');
                    var searchYtList = getYtStr.split(',');
                    for(var i = 0,len = ytList.length; i < len; i++){
                        for(var j = 0, ytLen = searchYtList.length; j < ytLen; j++){
                            if(ytList[i].name ==  searchYtList[j]){
                                ytList[i]['selected'] = "selected";
                            }
                        }
                    }
                }
                formSelects.data('selectYt', 'local', {
                    arr: ytList
                });
                var $searchJyzt = $('.bdc-search-jyzt');
                if ($searchJyzt.length > 0) {
                    var getJyztStr = $searchJyzt.attr('title');
                    var searchJyztList = getJyztStr.split(',');
                    for (var i = 0, len = jyztList.length; i < len; i++) {
                        for (var j = 0, jyztLen = searchJyztList.length; j < jyztLen; j++) {
                            if (jyztList[i].name == searchJyztList[j]) {
                                jyztList[i]['selected'] = "selected";
                            }
                        }
                    }
                }
                formSelects.data('selectJyzt', 'local', {
                    arr: jyztList
                });

                // var $searchZl = $('.bdc-search-zl');
                // if($searchZl.length > 0){
                //     $('.bdc-zl').val($searchZl.attr('title'));
                // }

                // 输入框删除图标
                if(!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i)=="9.")) {
                    //监听input点击
                    $('.layui-form-item .layui-input-inline').on('click','.layui-icon-close',function () {
                        $(this).siblings('.layui-input').val('');
                        $(this).siblings().find('.layui-input').val('');
                    });

                    $('.layui-form-item .layui-input-inline .layui-input').on('focus',function () {
                        $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                        $(this).parents('.layui-input-inline').find('.layui-icon-close').removeClass('bdc-hide');
                        $(this).siblings('.layui-edge').addClass('bdc-hide');
                    }).on('blur',function () {
                        var $this = $(this);
                        setTimeout(function () {
                            $this.siblings('.layui-icon-close').addClass('bdc-hide');
                            $this.parents('.layui-input-inline').find('.layui-icon-close').addClass('bdc-hide');
                            $this.siblings('.layui-edge').removeClass('bdc-hide');
                        },150)
                    });
                }
                //监听回车
                this.enterEsc = function(event) {
                    if (event.keyCode === 13) {
                        $('.layui-layer-btn0').click();
                        return false;
                    }
                };
                $(document).on('keydown', this.enterEsc); //监听键盘事件，关闭层
            }
            ,end: function() {
                $(document).off('keydown', this.enterEsc); //解除键盘关闭事件
            }
        });
    })
}

//不符合条件，背景置灰，购物车隐藏
function hideCart($gwcIcon, tabclass) {
    if ($gwcIcon.parents('.layui-table-cell>.bdc-td-show').length > 0 && !$gwcIcon.parents('.layui-table-cell').hasClass(tabclass)) {
        if ($gwcIcon.hasClass('one-onselected')) {
            $gwcIcon.attr('class', 'one-onselected-hide');
        } else if ($gwcIcon.hasClass('one-no-select')) {
            $gwcIcon.attr('class', 'one-no-select-hide');
        }
        $gwcIcon.parents('.layui-table-cell').addClass('bdc-td-bg').removeClass('bdc-search-show').removeClass('tabclass');
    }
}
//根据传递的searchList，searchClassList修改页面内的筛选状态---------为了扩展多个查询条件
//getYtList用途数组，只有全等，才被选中
function updatePageStyle(searchList, searchClassList, ytList, jyztList) {
    $showTd = currentRes.tabDocument.find('.bdc-td-show');
    for(var i = 0,len = $showTd.length; i < len; i++){
        var $td = $($showTd[i]);
        var $gwcIcon = $td.find('.bdc-fh>a[cart="1"]');
        //重置之前查询的class
        $gwcIcon.parents('.layui-table-cell').attr('class','layui-table-cell');
        if ((!ytList || ytList.length == 0) && (!jyztList || jyztList.length == 0)) {
            //如果都为空，重置
            if($gwcIcon.hasClass('one-onselected-hide')){
                $gwcIcon.attr('class','one-onselected one-icon');
            }else if($gwcIcon.hasClass('one-no-select-hide')) {
                $gwcIcon.attr('class','one-no-select one-icon');
            }
        } else {
            var valid = true;
            if (ytList.length != 0 && $td.find('p[mark="ghyt"]').html() != '') {
                for (var ytIndex = 0, ytLen = ytList.length; ytIndex < ytLen; ytIndex++) {
                    if (ytList[ytIndex] === $td.find('p[mark="ghyt"]').html()) {
                        //符合条件，显示购物车
                        if ($gwcIcon.hasClass('one-onselected-hide')) {
                            $gwcIcon.attr('class', 'one-onselected one-icon');
                        } else if ($gwcIcon.hasClass('one-no-select-hide')) {
                            $gwcIcon.attr('class', 'one-no-select one-icon').addClass('one-icon');
                        }
                        $gwcIcon.parents('.layui-table-cell').attr('class', 'layui-table-cell bdc-search-show bdc-search-valid-ghyt');
                        valid = true;
                    } else {
                        valid = false
                        hideCart($gwcIcon, 'bdc-search-valid-ghyt');
                    }
                }
            } else if (ytList.length != 0) {
                valid = false
                hideCart($gwcIcon, 'bdc-search-valid-ghyt');
            }
            if (jyztList.length != 0 && $td.find('.bdc-jy-state').attr("title") != '' && valid) {
                for (var jyztIndex = 0, jyztLen = jyztList.length; jyztIndex < jyztLen; jyztIndex++) {
                    if (jyztList[jyztIndex] === $td.find('.bdc-jy-state').attr("title")) {
                        //符合条件，显示购物车
                        if ($gwcIcon.hasClass('one-onselected-hide')) {
                            $gwcIcon.attr('class', 'one-onselected one-icon');
                        } else if ($gwcIcon.hasClass('one-no-select-hide')) {
                            $gwcIcon.attr('class', 'one-no-select one-icon').addClass('one-icon');
                        }
                        $gwcIcon.parents('.layui-table-cell').attr('class', 'layui-table-cell bdc-search-show bdc-search-valid-jyzt');
                    } else {
                        hideCart($gwcIcon, 'bdc-search-valid-jyzt');
                    }
                }
            } else if (jyztList.length != 0) {
                hideCart($gwcIcon, 'bdc-search-valid-jyzt');
            }
        }
    }
    for(var i = 0; i < searchList.length; i++){
        if(searchList[i] == ''){
            currentRes.tabDocument.find('.' + searchClassList[i]).remove();
        }else {
            spliceIndex = 0;
            operateStrLen(searchList[i]);

            var endText;
            if(spliceIndex == 0){
                endText = searchList[i];
            }else {
                endText = '...' + searchList[i].substring(spliceIndex,searchList[i].length);
            }

            var $srarchContent = currentRes.tabDocument.find('.bdc-search-content');
            if($srarchContent.length == 0){
                currentRes.tabDocument.find('.bdc-search').after('<a href="javascript:;" class="layui-btn layui-btn-sm bdc-table-second-btn bdc-search-content">' +
                    '<span class="'+ searchClassList[i] +'" title="'+ searchList[i] +'">'+
                    endText
                    +'<i class="layui-icon layui-icon-close"></i>' +
                    '</span>' +
                    '</a>');
            }else {
                if(currentRes.tabDocument.find('.bdc-search-content .'+searchClassList[i]).length > 0){
                    currentRes.tabDocument.find('.bdc-search-content .'+searchClassList[i]).html(endText +'<i class="layui-icon layui-icon-close"></i>').attr('title',searchList[i]);
                }else {
                    $srarchContent.append('<span class="'+ searchClassList[i] +'" title="'+ searchList[i] +'">'+
                        endText
                        +'<i class="layui-icon layui-icon-close"></i>' +
                        '</span>');
                }
            }
        }
    }
    if($srarchContent){
        if(currentRes.tabDocument.find('.bdc-search-content span').length == 0){
            $srarchContent.remove();
        }
    }
    removeModal();
}
//计算输入字符的中英文长度
function operateStrLen(text) {
    var rx = /[a-z\d]/i, rxcn = /[\u4e00-\u9fa5]/, num = 0, chr;
    for (var i = text.length-1; i >= 0; i--) {
        chr = text.charAt(i);
        if(rx.test(chr)){
            num++;

        } else if(rxcn.test(chr)){
            num += 2;
        }else {
            //特殊字符
            num += 2;
        }
        if(num == 20){
            spliceIndex = i;
        }
    }
}

/**
 * 购物车反选功能
 */
function cartInverseSelectFunction(){
    addModel();
    setTimeout(function(){
        // 全选按钮
        globalMsg = "nomsg";
        var removeIndex = [];
        removeIndex = removeIndex.concat(currentRes.checkedList);
        if (removeIndex.length > 0) {
            addAllCartWithSelectedClass();
            globalMsg = "反选成功";
            // 移除已选中
            removeCartFuntion(removeIndex);
            resScrollData(currentRes);
        } else {
            globalMsg = "未选中任一户室，无法反选";
            removeModal();
            cartActionMsg(globalMsg);
        }
    },50);
    globalMsg = "";
}

/**
 * 购物车移除全部 event: cartRemoveAll
 */
function cartRemoveAllFunction(){
    addModel();
    setTimeout(function(){
        removeCartFuntion(currentRes.checkedList);
        resScrollData(currentRes);
    },50);
}


/**
 * 重置按钮 event: reset
 */
function resetFunction() {
    layui.use(['jquery', 'laytpl', 'element', 'table'], function () {
        var table = layui.table;
        currentRes.checkedList = [];
        table.reload(currentRes.tableResizeId);
    })
}

/**
 * 新增操作  event: add
 */
function addFunction() {
    layui.use(['jquery', 'laytpl', 'element', 'table'], function () {
        var fwDcbIndex = $("#fwDcbIndex").val();
        currentRes.add(fwDcbIndex);
    })
}

/**
 * 合并操作  event: hshb
 */
function hshbFunction() {
    layui.use(['jquery', 'laytpl', 'element', 'table'], function () {
        if (currentRes.checkboxcheckedList && currentRes.checkboxcheckedList.length > 1) {
            var data = [];
            //把带-的字符串转为数组
            currentRes.checkboxcheckedList.forEach(function (v) {
                data.push({fw_hs_index: v});
            });
            var checkFlag = hbCheckFun(data);
            if (checkFlag) {
                $.hsbg._fwhsHb(data);
            } else {
                layer.alert("请选择连续，同一单元数据进行合并操作");
            }
        } else {
            layer.alert("请选择多条数据进行合并操作");
        }
    })
}

/**
 * 拆分操作  event: hscf
 */
function hscfFunction() {
    layui.use(['jquery', 'laytpl', 'element', 'table'], function () {
        if (currentRes.checkboxcheckedList.length === 1) {
            $.hsbg._fwhsCf({fw_hs_index: currentRes.checkboxcheckedList[0]});
        } else {
            layer.alert("请选择一条数据进行拆分操作");
        }
    })
}

/**
 * 删除操作  event: delete
 */
function deleteFunction() {
    layui.use(['jquery', 'laytpl', 'element', 'table'], function () {
        if (currentRes.checkedList && currentRes.checkedList.length > 0) {
            var data = [];
            //把带-的字符串转为数组
            currentRes.checkedList.forEach(function (v) {
                data.push({fw_hs_index: v});
            });
            layer.confirm('确定删除？', function (index) {
                currentRes.delete(data);
                return false;
            });
        } else {
            layer.alert("请选择至少一条数据进行删除操作");
        }
    })
}

/**
 * 更新操作  event: update
 */
function updateFunction() {
    layui.use(['jquery', 'laytpl', 'element', 'table'], function () {
        if (currentRes.checkedList.length == 1) {
            var fwDcbIndex = $("#fwDcbIndex").val();
            var updateData = {
                fw_hs_index: currentRes.checkedList[0],
                fw_dcb_index: fwDcbIndex
            };
            currentRes.update(updateData);
        } else {
            layer.alert("请选择一条数据进行更新操作");
        }
    })
}

/**
 * 户室灭失 event：hsms
 */
function hsmsFunction() {
    layui.use(['jquery', 'laytpl', 'element', 'table'], function () {
        var data = [];
        $.each(hsRes.checkedList, function (index, v) {
            data.push({fw_hs_index: v});
        })
        if (data.length == 1) {
            $.hsbg._fwhsMs(data[0]);
        } else {
            layer.alert("请选择一条数据进行灭失操作");
        }
    })
}

/**
 * 户室变更 event：hsbg
 */
function hsbgFunction() {
    layui.use(['jquery', 'laytpl', 'element', 'table'], function () {
        var data = [];
        $.each(hsRes.checkedList, function (index, v) {
            data.push({fw_hs_index: v});
        })
        if (data.length == 1) {
            var bgData = {
                fw_hs_index: data[0].fw_hs_index,
                fw_dcb_index: fwDcbIndex
            }
            $.hsbg._fwhsBg(bgData);
        } else {
            layer.alert("请选择一条数据进行变更操作");
        }
    })
}

/**
 * 批量更新属性 event：plgxsx
 */
function plgxsxFunction() {
    var fwDcbIndex = $("#fwDcbIndex").val();
    layui.use(['jquery', 'laytpl', 'element', 'table'], function () {
        if (hsRes.checkedList && hsRes.checkedList.length > 0) {
            var data = [];
            $.each(hsRes.checkedList, function (index, v) {
                data.push({fw_hs_index: v});
            });
            plgxsxFun(data);
        } else {
            var index = layer.confirm('确定批量更新整幢？', {
                title: '提示',
                btn: ['确定', '取消'] //按钮
            }, function () {
                layer.close(index);
                plgxsxFun([],fwDcbIndex);
            });
        }
    })
}

/**
 * 批量更新面积 event：plgxmj
 */
function plgxmjFunction() {
    var fwDcbIndex = $("#fwDcbIndex").val();
    layui.use(['jquery', 'laytpl', 'element', 'table'], function () {
        if (hsRes.checkedList && hsRes.checkedList.length > 0) {
            var data = [];
            $.each(hsRes.checkedList, function (index, v) {
                data.push({fw_hs_index: v});
            });
            plgxmjFun(data);
        } else {
            var index = layer.confirm('确定批量更新整幢？', {
                title: '提示',
                btn: ['确定', '取消'] //按钮
            }, function () {
                layer.close(index);
                plgxmjFun([],fwDcbIndex);
            });
        }
    })
}

/**
 * 户室变更历史关系 event:bghistory
 */
function bghistoryFunction(){
    layui.use(['jquery', 'laytpl', 'element', 'table'], function () {
        var data = [];
        $.each(hsRes.checkedList, function (index, v) {
            var bdcdyh = $("input[data-index='"+v+"']").attr("data-bdcdyh");
            data.push({fw_hs_index: v, bdcdyh: bdcdyh});
        });
        if (data.length == 1) {
            fwHsHistory(data[0]);
        } else {
            layer.alert("请选择一条数据");
        }
    })
}

/**
 * 批量上传户室图 event：pLUploadHst
 */
function pLUploadHstFunction(){
    layui.use(['jquery', 'laytpl', 'element', 'table'], function () {
        if (hsRes.checkedList && hsRes.checkedList.length > 0) {
            var data = [];
            $.each(hsRes.checkedList, function (index, v) {
                data.push({fw_hs_index: v});
            });
            hstToManyHsView(data);
        } else {
            layer.alert("请选择至少一条数据进行更新操作");
        }
    })
}

//选择户室数量按钮点击事件
function selectHssl(hssl) {
    layui.use(['jquery', 'laytpl', 'element', 'table', 'layer', 'formSelects'], function () {
        var layer = layui.layer, form = layui.form;
        var index = layer.open({
            type: 1,
            title: '请选择每行展示户室数量',
            skin: 'bdc-ckgdxx-layer',
            area: ['430px', '300px'],
            content:
                '<div id="bdc-popup-small ckgdxx-layer">' +
                '<form class="layui-form" action="">' +
                '<div class="layui-form-item pf-form-item">' +
                '<div class="layui-inline" style="width: 100%;">' +
                ' <label class="layui-form-label">户室数量:</label>' +
                ' <div class="layui-input-inline select-height">' +
                '<select class="hssl" lay-filter="hssl" name="hssl" id="hssl"><option value="6">6</option><option value="7">7</option>' +
                '<option value="8">8</option><option value="9">9</option>' +
                '<option value="10">10</option><option value="15">15</option>' +
                '<option value="20">20</option><option value="25">25</option>' +
                '<option value="30">30</option><option value="35">35</option>' +
                '</select>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</form>' +
                '</div>',
            // btn: ['确定'],
            // btnAlign: 'c',
            // yes: function (index, layero) {
            //     layer.close(index);
            // },
            cancle: function (index) {
                layer.close(index);
            },
            done: function (index) {
                $('.hssl').val(hssl);
                form.render();
            },
            success: function () {
                $('.hssl').val(hssl);
                form.on('select(hssl)', function (data) {
                    chscount = data.value;
                    // currentRes.loadTableData(chscount);
                    // initBtns(currentRes);
                    getReturnData("/tdlpb/hssl", {hssl: data.value}, "GET", function (data) {
                        window.location.reload();
                        // currentRes.loadTableData();
                        // initBtns(currentRes);
                        layer.close(index);
                    }, function (xhr) {
                        delAjaxErrorMsg(xhr)
                    }, false)

                });
                form.render();
            }
        });
    })
}


function showSwhst(bdcdyh) {
    if (!isNotBlank(bdcdyh)) {
        ityzl_SHOW_WARN_LAYER("单元号异常无法打开三维户室图")
    } else {
        window.open(swhstUrl + bdcdyh);
    }
}