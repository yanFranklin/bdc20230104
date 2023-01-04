/**
 * Created by ysy on 2020/7/20.
 * 配置页面js
 */
layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
var zddata = [];
var editAble = false;
var zdmc = $.getUrlParam("zdmc");
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table,
        form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;
    var BASE_URL = getContextPath() + "/rest/v1.0/bdczdpz";


    $(function () {
        getZdList();

        function render(){
            var col = [];
            var cols = [];
            var toolbar = ""
            if(editAble){
                toolbar = "#toolbarDemo"
                cols = [
                    {
                        width: '45%', field: 'MC', title: 'MC'
                    },
                    {
                        width: '45%', field: 'DM', title: 'DM'
                    },
                    {width: '10%', sort: false, toolbar: '#barAction', title: '操作', fixed: 'right'}
                ];
            } else {
                if (zddata && zddata.length > 0) {
                    $.each(zddata[0], function (key, value) {
                        cols.push({field: key, title: key})
                    })
                }
                toolbar = "";
            }
            col.push(cols)
            table.render({
                elem: '#zdTable',
                id: 'dzTableId'
                , page: true //开启分页
                , defaultToolbar: ['filter']
                , toolbar: toolbar
                , data: zddata
                , even: true
                , cols: col
                , request: {
                    limitName: 'size' //每页数据量的参数名，默认：limit
                }
                , done: function () {
                    $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');


                    if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                        $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png"  alt="">无数据');
                    } else {
                        $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 131);
                    }
                }
            });
        }
        //获得展示列表
        function getZdList(tablename) {
            addModel();
            getEditAble(tablename);
            $.ajax({
                type: 'GET',
                url: BASE_URL + "/listByName",
                data: {
                    'zdmc': zdmc
                },
                async: false,
                dataType: "json",
                success: function (data) {
                    zddata = data;
                    removeModal();
                    render();
                },
                error: function (err) {
                    removeModal();
                    delAjaxErrorMsg(err);
                }
            });
        }

        //获得展示列表
        function getEditAble(tablename) {
            $.ajax({
                type: 'GET',
                url: BASE_URL + "/editable",
                data: {
                    'zdmc': zdmc
                },
                async: false,
                dataType: "json",
                success: function (data) {
                    if(!isNullOrEmpty(data)){
                        editAble = data;
                    }
                },
                error: function (err) {
                    removeModal();
                    delAjaxErrorMsg(err);
                }
            });
        }

        //刷新
        function refreshZdList() {
            addModel();
            $.ajax({
                type: 'GET',
                url: BASE_URL + "/refresh",
                data: {
                    'tablename': "BDC_ZD_"+zdmc
                },
                async: false,
                dataType: "json",
                success: function (data) {
                },
                error: function (err) {
                }
            });
            $.ajax({
                type: 'GET',
                url: BASE_URL + "/refresh",
                data: {
                    'tablename': "BDC_SL_ZD_"+zdmc
                },
                async: false,
                dataType: "json",
                success: function (data) {
                },
                error: function (err) {
                }
            });
        }

        table.on('tool(zdTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'add') {
                addZdItem(data);
            } else if (obj.event === 'delItem') {
                delZdItem(obj.data);
            } else if (obj.event === 'editItem') {
                editZdItem(obj.data);
            }
        });

        table.on('toolbar(zdTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'add') {
                addZdItem(data);
            }
        });

        /**
         * 新增
         */
        function addZdItem(data) {
            layer.open({
                type: 1,
                title: '新增字典项',
                skin: 'bdc-ckgdxx-layer',
                area: ['430px'],
                content:
                    '<div id="bdc-popup-small ckgdxx-layer">' +
                    '<form class="layui-form" action="">' +
                    '<div class="layui-form-item pf-form-item">' +
                    '<div class="layui-inline" style="width: 100%;">' +
                    ' <label class="layui-form-label bdc-label-newline">字典项代码:</label>' +
                    ' <div class="layui-input-inline">' +
                    '<input type="text" autofocus="true" name="adddm" id="adddm" autocomplete="off" placeholder="请输入" class="layui-input">' +
                    '</div>' +
                    '</div>' +
                    '<div class="layui-inline" style="width: 100%;">' +
                    ' <label class="layui-form-label bdc-label-newline">字典项名称:</label>' +
                    ' <div class="layui-input-inline">' +
                    '<input type="text" autofocus="true" name="addmc" id="addmc" autocomplete="off" placeholder="请输入" class="layui-input">' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</form>' +
                    '</div>',
                btn: ['新增'],
                btnAlign: 'c',
                yes: function (index, layero) {
                    var dm = layero.find("input[name='adddm']").val();
                    var mc = layero.find("input[name='addmc']").val();
                    if (isNullOrEmpty(dm) ||  isNullOrEmpty(mc)) {
                        ityzl_SHOW_WARN_LAYER("输入名称和代码!");
                        return;
                    }
                    addModel();
                    $.ajax({
                        type: 'POST',
                        url: BASE_URL + "/addItem",
                        data: JSON.stringify({
                            'zdmc': zdmc,
                            'mc': mc,
                            'dm': dm,
                        }),
                        async: false,
                        dataType: "json",
                        contentType: 'application/json;charset=utf-8',
                        success: function (data) {
                            refreshZdList();
                            getZdList();
                            removeModal();
                        },
                        error: function (err) {
                            removeModal();
                            delAjaxErrorMsg(err);
                        }
                    });
                    layer.alert("操作成功！");
                    removeModal();
                    layer.close(index);
                },
                cancle: function (index) {
                    layer.close(index);
                },
                done: function (index) {

                }
            });
        }

        /**
         * 修改
         */
        function editZdItem(data) {
            var orgdm = data.DM;
            layer.open({
                type: 1,
                title: '修改字典项',
                skin: 'bdc-ckgdxx-layer',
                area: ['430px'],
                content:
                    '<div id="bdc-popup-small ckgdxx-layer">' +
                    '<form class="layui-form" action="">' +
                    '<div class="layui-form-item pf-form-item">' +
                    '<div class="layui-inline" style="width: 100%;">' +
                    ' <label class="layui-form-label bdc-label-newline">字典项代码:</label>' +
                    ' <div class="layui-input-inline">' +
                    '<input type="text" autofocus="true" name="editdm" id="editdm" autocomplete="off" placeholder="请输入" value="' +
                    data.DM +
                    '" class="layui-input">' +
                    '</div>' +
                    '</div>' +
                    '<div class="layui-inline" style="width: 100%;">' +
                    ' <label class="layui-form-label bdc-label-newline">字典项名称:</label>' +
                    ' <div class="layui-input-inline">' +
                    '<input type="text" autofocus="true" name="editmc" id="editmc" autocomplete="off" placeholder="请输入" value="' +
                    data.MC +
                    '" class="layui-input">' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</form>' +
                    '</div>',
                btn: ['修改'],
                btnAlign: 'c',
                yes: function (index, layero) {
                    var dm = layero.find("input[name='editdm']").val();
                    var mc = layero.find("input[name='editmc']").val();
                    if (isNullOrEmpty(dm) ||  isNullOrEmpty(mc)) {
                        ityzl_SHOW_WARN_LAYER("输入名称和代码!");
                        return;
                    }
                    addModel();
                    $.ajax({
                        type: 'POST',
                        url: BASE_URL + "/editItem",
                        data: JSON.stringify({
                            'zdmc': zdmc,
                            'mc': mc,
                            'dm': dm,
                            'orgdm': orgdm,
                        }),
                        async: false,
                        dataType: "json",
                        contentType: 'application/json;charset=utf-8',
                        success: function (data) {
                            zddata = data;
                            layer.alert("操作成功！");
                            removeModal();
                            refreshZdList();
                            getZdList();
                        },
                        error: function (err) {
                            removeModal();
                            delAjaxErrorMsg(err);
                        }
                    });
                    removeModal();
                    layer.close(index);
                },
                cancle: function (index) {
                    layer.close(index);
                },
                done: function (index) {

                }
            });
        }

        /**
         * 删除
         */
        function delZdItem(data) {
            var orgdm = data.DM;
            var logoutIndex = layer.confirm('是否删除？', {
                title: '提示',
                btn: ['继续', '关闭'] //按钮
            }, function (index) {
                $.ajax({
                    type: 'POST',
                    url: BASE_URL + "/delItem",
                    data: JSON.stringify({
                        'zdmc': zdmc,
                        'orgdm': orgdm,
                    }),
                    async: false,
                    dataType: "json",
                    contentType: 'application/json;charset=utf-8',
                    success: function (data) {
                        zddata = data;
                        layer.alert("操作成功！");
                        refreshZdList();
                        getZdList();
                        removeModal();
                    },
                    error: function (err) {
                        removeModal();
                        delAjaxErrorMsg(err);
                    }
                });
                layer.close(logoutIndex);
            },function () {
                layer.close(pzIndex);
            });
        }
    });

});