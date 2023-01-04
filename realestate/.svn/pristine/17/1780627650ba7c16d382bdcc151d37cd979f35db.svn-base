/** 用于存储个性化table配置*/
var tableArray = new Array();


function saveCols(data, tableid, url) {
    $.ajax({
        type: 'POST',
        url: '/realestate-register-ui/rest/v1.0/gxhpz/save/table?url=' + encodeURI(url) + '&tableid=' + tableid,
        data: JSON.stringify(data),
        contentType: 'application/json'
    });
}

function getGxhpz(tableid) {
    var url;
    if (window.location.pathname != "/realestate-register-ui/view/dtcx/commonCx.html") {
        url = window.location.pathname;
    } else {
        url = window.location.pathname + window.location.search;
    }
    $.ajax({
        type: 'GET',
        url: '/realestate-register-ui/rest/v1.0/gxhpz/query/table?url=' + encodeURI(url) + '&tableid=' + tableid,
        success: function (data) {
            if (data) {
                var dataObj = JSON.parse(data);
                var table;
                $.each(tableArray, function (index, item) {
                    if (item.config.id == tableid) {
                        table = item;
                    }
                })

                // 特殊处理 用于防止table内容本身被修改
                if (table.config.cols[0].length != dataObj.length) {
                    return false;
                }

                for (var i = 0; i < table.config.cols[0].length; i++) {
                    if (table.config.cols[0][i].field != dataObj[i].field) {
                        return false;
                    }
                }
                for (var i = 0; i < table.config.cols[0].length; i++) {
                    table.config.cols[0][i].hide = dataObj[i].hide;
                }

                layui.each(dataObj, function (t, i) {
                    if (i.type == "normal") {
                        var d = i.hide;
                        table.elem.find('*[data-field="' + i.field + '"]')[!d ? "removeClass" : "addClass"]("layui-hide"), table.setParentCol(d, ""), table.resize()
                    }
                })
            }
        }
    });
}


function linkTable(t) {
    var flag = true;
    for (var i = 0;i< tableArray.length;i++){
        if (tableArray[i].config.id == t.config.id){
            flag = false;
        }
    }

    if(flag == true){
        tableArray.push(t);
    }
    getGxhpz(t.config.id);
}


$(function () {

    formLoad();
})

/**
 * 根据json数据，隐藏指定查询条件
 * @param data  接口获取json
 * @param isTab 是否是tab下面的查询条件
 */
function hideSearchByData(data, formid) {
    var $searchBox;
    if (isNotBlank(formid)) {
        $searchBox = $("#" + formid);
    } else {
        return false;
    }
    var forms = $('.bdc-search-box').find('form');

    if ($('#' + formid).length == 0) {
        for (var i = 0; i < forms.length; i++) {
            var id = 'form' + i;
            if (id === formid) {
                $searchBox = $(forms[i]);
            }
        }
    } else {
        $searchBox = $('#' + formid);
    }
    data.forEach(function (v) {
        if (!v.isShow) {
            var $label = $searchBox.find('.layui-form-label');
            for (var i = 0, len = $label.length; i < len; i++) {
                if ($label[i].innerHTML == v.name) {
                    var $seniors = $searchBox.find('.pf-senior-show');
                    var $senior;
                    if ($seniors.children().length > 0) {
                        for (var j = 0;j<$seniors.length;j++){
                            if ($($seniors[j]).children().length > 0){
                                $senior = $($seniors[j]);
                                break;
                            }
                        }
                        if ($senior) {
                            var $seniorFirstChild = $senior.find('.layui-inline:first-child');
                            if ($seniorFirstChild.length > 0) {
                                $seniors.first().before('<div class="layui-inline">' + $seniorFirstChild.html() + '</div>');
                                $seniorFirstChild.remove();
                            }
                        }
                        if ($senior.find('.layui-inline').length == 0) {
                            if ($senior) {
                                $senior.hide();
                            }
                            var buttons = $searchBox.find('button');
                            buttons.each(function (index,button) {
                                if (button.innerHTML.replace(/\s+/g, "") === '高级查询'){
                                    $(button).hide();
                                }
                            })
                        }
                    }
                    if($seniors.children().length == 0){
                        var buttons = $searchBox.find('button');
                        buttons.each(function (index,button) {
                            if (button.innerHTML.replace(/\s+/g, "") === '高级查询'){
                                $(button).hide();
                            }
                        })
                        var as = $searchBox.find('a');
                        as.each(function (index,button) {
                            if (button.innerHTML.replace(/\s+/g, "") === '高级查询'){
                                $(button).hide();
                            }
                        })
                    }

                    $($label[i]).parents('.layui-inline').hide();
                }
            }
            // 对处于特殊位置的控件需要进行再次判断
            var $label = $searchBox.find('.layui-form-label');
            for (var i = 0, len = $label.length; i < len; i++) {
                if ($label[i].innerHTML == v.name) {
                    $($label[i]).parents('.layui-inline').hide();
                }
            }
            //隐藏后如果查询条件个数正好一行，按钮居中
            var count = 0;
            $searchBox.find('.layui-form-label').each(function (index, label) {
                if (!$(label).is(':hidden')){
                    count++;
                }
            })
            if ($searchBox.find('.bdc-screen-inline').length != 0){
                if (count == 3) {
                    $searchBox.find('.bdc-button-box').addClass('bdc-button-box-four');
                }
            } else{
                if (count == 4) {
                    $searchBox.find('.bdc-button-box').addClass('bdc-button-box-four');
                }
            }
        }
    })
}

function formLoad() {
    if ($('.bdc-search-box').length > 0) {
        var $searchBox = $('.bdc-search-box');

        $searchBox.each(function (i) {
            $($searchBox[i]).append('<a href="javascript:;" class="bdc-set-search" style="position: absolute;top: 0;right: 0;">' +
                '<img src="../../static/lib/bdcui/images/setSearch.png" alt="" style="width: 28px;">' +
                '</a>');
        });
    }
    var forms = $('.bdc-search-box').find('form');

    var url;
    if (window.location.pathname != "/realestate-register-ui/view/dtcx/commonCx.html") {
        url = window.location.pathname;
    } else {
        url = window.location.pathname + window.location.search;
    }

    for (var i = 0; i < forms.length; i++) {
        var searchData;
        var formid;
        if(!$(forms[i]).attr('id')){
            formid = 'form' + i;
        } else {
            formid = $(forms[i]).attr('id');
        }

        $.ajax({
            type: 'GET',
            url: '/realestate-register-ui/rest/v1.0/gxhpz/query/form?url=' + encodeURI(url) + '&formid=' + formid,
            async: false,
            success: function (data) {
                if (data) {
                    searchData = JSON.parse(data);
                }
            }
        });
        if (isNotBlank(searchData)) {
            hideSearchByData(searchData, formid);
        }
    }

    $(".bdc-set-search").on('click', function () {
        var formid;
        var form = $(this).parent().find('form');
        var forms = $('.bdc-search-box').find('form');
        for (var i = 0; i < forms.length; i++) {
            if (form[0] === forms[i]){
                if (form.attr('id')){
                    formid = form.attr('id');
                } else {
                    formid = 'form' + i;
                }
            }
        }

        var url;
        var searchData = new Array();
        if (window.location.pathname != "/realestate-register-ui/view/dtcx/commonCx.html") {
            url = window.location.pathname;
        } else {
            url = window.location.pathname + window.location.search;
        }
        $.ajax({
            type: 'GET',
            url: '/realestate-register-ui/rest/v1.0/gxhpz/query/form?url=' + encodeURI(url) + '&formid=' + formid,
            async: false,
            success: function (data) {
                if (data) {
                    searchData = JSON.parse(data);
                }
            }
        });
        var $label = form.find('.layui-form-label');
        var sameFlag = true;
        if (isNotBlank(searchData) && searchData.length == $label.length) {
            for (var i = 0; i < searchData.length; i++) {
                if (searchData[i].name != $label[i].innerHTML) {
                    sameFlag = false;
                }
            }
        } else {
            sameFlag = false;
        }

        if (sameFlag == false) {
            var saveData = new Array();
            for (var i = 0; i < $label.length; i++) {
                var saveObj = {"name": $label[i].innerHTML, "isShow": $label.css('display') != 'none'};
                saveData.push(saveObj);
            }
            $.ajax({
                type: 'POST',
                url: '/realestate-register-ui/rest/v1.0/gxhpz/save/form?url=' + encodeURI(url) + '&formid=' + formid,
                data: JSON.stringify(saveData),
                contentType: 'application/json',
                async: false
            });
        }

        var index = layer.open({
            type: 2,
            title: "编辑",
            area: ['600px', '240px'],
            fixed: false, //不固定bdcFzjl
            maxmin: true, //开启最大化最小化按钮
            content: "/realestate-register-ui/view/gxhpz/searchPz.html?formid=" + formid + "&url=" + encodeURI(url),
            btn: ['保存', '取消'],
            yes: function (index, layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
                iframeWin.saveAction();//调用子页面的方法，得到子页面返回的ids
            }
            , btn2: function (index, layero) {
                layer.close(index);

            }
            , cancel: function (index) {
                layer.close(index);
            }
        });

        return false;
    });
}

//空字符串不包括"(空格)",只特指""
function isNotBlank(object) {
    if (typeof object === "object" && !(object instanceof Array)) {
        var hasProp = false;
        for (var prop in object) {
            hasProp = true;
            break;
        }
        if (hasProp) {
            hasProp = [hasProp];
        } else {
            return false;
        }
        return hasProp;
    }
    return typeof object != "undefined" && object != "";
}