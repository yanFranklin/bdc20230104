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
    $.each(info, function () {
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
            }
        }
    });
    if(resource.hasSelectBtn){
        resource.checkBoxHide = true;
    }else{
        resource.checkBoxHide = false;
    }

    var json = {
        btns:btnInfos,moreBtns:moreBtnList
    };
    layui.use(['laytpl', 'form'], function () {
        var laytpl = layui.laytpl;
        var toolbar = toolbarTpl.innerHTML
            , toolbarDiv = document.getElementById(resource.toorBarId);
        laytpl(toolbar).render(json, function (html) {
            toolbarDiv.innerHTML = html;
        });
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
    var data = [];
    // eval中有用到 勿删
    $.each(currentRes.checkedList, function (index, v) {
        var infoJson = getDataInfoByIndex(v);
        if(keys.length == 0){
            data.push(infoJson);
        }else{
            var info = {};
            for(var i = 0 ;i<keys.length ; i++){
                if(eval("infoJson."+keys[i])){
                    eval("info."+keys[i]+"=infoJson."+keys[i]);
                }
            }
            if(info){
                data.push(info);
            }
        }
    });
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
    layui.use(['table','form'], function () {
        var table = layui.table;
        var form = layui.form;
        var allCheckBox = currentRes.tabDocument.find(".bdc-td-show input");
        currentRes.checkedList = [];
        for(var i = 0;i < allCheckBox.length;i++){
            var index = $(allCheckBox[i]).data("index");
            currentRes.checkedList.push(index);
            $(allCheckBox[i]).attr("checked","checked");
        }
        form.render("checkbox");
    })
}

function checkBoxInverseSelectFunction(){
    layui.use(['table','form'], function () {
        var table = layui.table;
        var form = layui.form;
        var allCheckBox = currentRes.tabDocument.find(".bdc-td-show input");
        // currentRes.checkedList = [];
        var checkedList =currentRes.checkedList;
        // checkedList.push(currentRes.checkedList);

        // 全选
        checkBoxSelectAllFunction();
        // 取消选中
        // 取消选中
        for(var i = 0 ; i < checkedList.length;i++){
            var tempIndex = checkedList[i];
            currentRes.checkedList = $.grep(currentRes.checkedList, function (value) {
                return value != tempIndex;
            });
            $("input[data-index='"+tempIndex+"']").removeAttr("checked");
        }
        form.render("checkbox");
    })

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
                    var ytStr = '';
                    getYtList.forEach(function (v) {
                        ytStr += v + ',';
                    });
                    ytStr = ytStr.substring(0,ytStr.length-1);
                    var searchList = [ytStr];//与searchClassList对应关系
                    var searchClassList = ['bdc-search-yt'];
                    //查询 的回调
                    updatePageStyle(searchList,searchClassList,getYtList);

                    $('.bdc-yt').val('');
                    $('.bdc-zl').val('');
                },10);
            }
            ,btn2: function(index, layero){
                //取消 的回调
                layer.close(index);
            }
            ,success: function () {
                ytList = [];
                var obj = currentRes.filterList.ghyt;
                if(obj && obj.length > 0){
                        for(var i = 0;i<obj.length;i++){
                            ytList.push({"name":obj[i],"value":obj[i]});
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

                // if($showTd == ''){
                    $showTd = currentRes.tabDocument.find('.bdc-td-show');
                // }
            }
            ,end: function() {
                $(document).off('keydown', this.enterEsc); //解除键盘关闭事件
            }
        });
    })
}




//根据传递的searchList，searchClassList修改页面内的筛选状态---------为了扩展多个查询条件
//getYtList用途数组，只有全等，才被选中
function updatePageStyle(searchList,searchClassList,getYtList) {
    for(var i = 0,len = $showTd.length; i < len; i++){
        var $td = $($showTd[i]);
        var $gwcIcon = $td.find('.bdc-fh>a');
        //重置之前查询的class
        $gwcIcon.parents('.layui-table-cell').attr('class','layui-table-cell');
        if(getYtList.length == 0){
            //如果都为空，重置
            if($gwcIcon.hasClass('one-onselected-hide')){
                $gwcIcon.attr('class','one-onselected one-icon');
            }else if($gwcIcon.hasClass('one-no-select-hide')) {
                $gwcIcon.attr('class','one-no-select one-icon');
            }
        }else if((getYtList.length != 0 && $td.find('p[mark="ghyt"]').html() != '')
            // && ($td.find('p[mark="zl"]').attr('title').indexOf(searchList[1]) > -1
            //     && searchList[1] != '' && $td.find('p[mark="zl"]').attr('title') != '')
        ){
            for(var ytIndex = 0,ytLen = getYtList.length; ytIndex <ytLen; ytIndex++) {
                if(getYtList[ytIndex] === $td.find('p[mark="ghyt"]').html()) {
                    //符合条件，显示购物车
                    if($gwcIcon.hasClass('one-onselected-hide')){
                        $gwcIcon.attr('class','one-onselected one-icon');
                    }else if($gwcIcon.hasClass('one-no-select-hide')) {
                        $gwcIcon.attr('class','one-no-select one-icon').addClass('one-icon');
                    }
                    $gwcIcon.parents('.layui-table-cell').attr('class','layui-table-cell bdc-search-show');
                }else {
                    //不符合条件，背景置灰，购物车隐藏
                    if($gwcIcon.parents('.layui-table-cell>.bdc-td-show').length > 0 && !$gwcIcon.parents('.layui-table-cell').hasClass('bdc-search-show')){
                        if($gwcIcon.hasClass('one-onselected')){
                            $gwcIcon.attr('class','one-onselected-hide');
                        }else if($gwcIcon.hasClass('one-no-select')){
                            $gwcIcon.attr('class','one-no-select-hide');
                        }
                        $gwcIcon.parents('.layui-table-cell').addClass('bdc-td-bg');
                    }
                }
            }
        }else {
            //不符合条件，背景置灰，购物车隐藏
            if($gwcIcon.parents('.layui-table-cell>.bdc-td-show').length > 0 && !$gwcIcon.parents('.layui-table-cell').hasClass('bdc-search-show')){
                if($gwcIcon.hasClass('one-onselected')){
                    $gwcIcon.attr('class','one-onselected-hide');
                }else if($gwcIcon.hasClass('one-no-select')){
                    $gwcIcon.attr('class','one-no-select-hide');
                }
                $gwcIcon.parents('.layui-table-cell').addClass('bdc-td-bg').removeClass('bdc-search-show');
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
        var timestamp1 = (new Date()).valueOf();
        // 获取当前已选中
        var selectedTd = currentRes.tabDocument.find(".layui-table-main tbody tr td a.one-onselected");
        var selectedIndex = [];
        if(currentRes.checkedList.length > 0){
            for(var i = 0;i<currentRes.checkedList.length ;i++){
                selectedIndex.push(currentRes.checkedList[i]);
            }
        }

        var timestamp2 = (new Date()).valueOf();
        console.log('获取当前已选中 '+ (timestamp2-timestamp1));
        timestamp1 = (new Date()).valueOf();

        // 全选按钮
        globalMsg = "nomsg";
        addAllCartWithSelectedClass();

        timestamp2 = (new Date()).valueOf();
        console.log('全选 '+ (timestamp2-timestamp1));
        timestamp1 = (new Date()).valueOf();

        // 检查是否去掉 物理层和单元号上的购物车图标样式
        if(selectedTd.length > 0){
            selectedTd.each(function(){
                cancelSelect(this);
            });
            cancelWlc();
            cancelDyh();
        }

        timestamp2 = (new Date()).valueOf();
        console.log('检查取消图标 '+ (timestamp2-timestamp1));
        timestamp1 = (new Date()).valueOf();

        globalMsg = "反选成功";
        // 移除已选中
        removeCartFuntion(selectedIndex);
        timestamp2 = (new Date()).valueOf();
        console.log('移除已选中 '+ (timestamp2-timestamp1));
    },50);
    globalMsg = "";
}

/**
 * 购物车移除全部 event: cartRemoveAll
 */
function cartRemoveAllFunction(){
    addModel();
    setTimeout(function(){
        everyRemove = [];
        currentRes.tabDocument.find("a.one-icon.one-onselected").each(function(){
            cancelSelect(this);
        });
        currentRes.tabDocument.find("a.hang-icon.hang-onselected").each(function(){
            $(this).addClass("hang-no-select");
            $(this).removeClass("hang-onselected");
        });
        currentRes.tabDocument.find("a.lie-icon.lie-onselected").each(function(){
            $(this).addClass("lie-no-select");
            $(this).removeClass("lie-onselected");
        });
        removeCartFuntion(everyRemove);
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
        if (hsRes.checkedList && hsRes.checkedList.length > 1) {
            var data = [];
            //把带-的字符串转为数组
            hsRes.checkedList.forEach(function (v) {
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
        if (hsRes.checkedList.length == 1) {
            $.hsbg._fwhsCf({fw_hs_index: hsRes.checkedList[0]});
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