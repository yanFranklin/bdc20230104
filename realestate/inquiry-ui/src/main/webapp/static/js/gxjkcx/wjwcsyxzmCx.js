/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/09
 * @description 民政部-婚姻登记信息查询服务接口台账
 */
var gzlslid = getQueryString('gzlslid');
var qlrlx = '1';
layui.use(['jquery','form', 'table', 'laydate', 'laytpl'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;

    if (!isNullOrEmpty(gzlslid)) {
        getSearchParam();
    }

    // 点击查询
    $('#qtqlr').on('click', function () {
        qtqlr();
    });

    form.render();

    $(function () {
        //表格信息
        table.render({
            elem: '#csyxzmxxTable',
            toolbar: '#toolbarDemo',
            title: '公正文书查询服务',
            defaultToolbar: ['filter', 'print', 'exports'],
            even: true,
            cols: [[
                {type: 'numbers', width: 50, fixed: 'left'},
                {field: 'babyName', title: '新生儿姓名', width: 100},
                {field: 'babySex', title: '新生儿性别', minWidth: 180},
                {field: 'birthArea', title: '出生地区', minWidth: 180},
                {field: 'birthTime', title: '出生时间', width: 100},
                {field: 'birthCode', title: '出生医学证明编号', minWidth: 180},
                {field: 'momName', title: '母亲姓名', width: 160, hide:true},
                {field: 'momIdCode', title: '母亲证件编号', width: 180, hide:true},
                {field: 'dadName', title: '父亲姓名', width: 180, hide:true},
                {field: 'dadIdCode', title: '父亲证件编码', width: 180, hide:true},
                {fixed: 'right', title: '操作', toolbar: '#czTpl', width: 80}
            ]],
            data: [],
            page: true,
            done: function () {
                setHeight();
            }
        });

        //监听婚姻登记行工具事件
        table.on('tool(csyxzmxxTable)', function (obj) {
            var data = obj.data;
            form.val('csyxzmxxForm', data);
            //小弹出层
            layer.open({
                title: '出生医学证明',
                type: 1,
                area: ['960px', '400px'],
                // btn: ['关闭'],
                content: $('#popupTwoRows')
                , yes: function (index, layero) {
                }
                , cancel: function () {
                    closeWin();
                }
                , success: function () {

                }
            });

        });
        /**
         * 点击查询
         */
        $('#search').on('click',function () {
            var count = 0;
            $('.required-warn').each(function(){
                if ($(this).val() === '') {
                    count++;
                }
            });
            if (count > 0) {
                warnMsg('请填写完整的查询条件');
                return false;
            }
            search();
        });
        // 回车查询
        $('.required').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        //查询
        function search() {
            // 获取查询参数
            var obj = {};
            obj.birthCode = $('#birthCode').val();
            obj.momName = $('#momName').val();
            obj.momIdCode = $('#momIdCode').val();

            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/bwgxjk/wjwcsyxzm",
                type: "POST",
                data: obj,
                success: function (data) {
                    var content = [];
                    if(data && data.content){
                        content = data.content;
                    }
                    removeModal();
                    table.reload("csyxzmxxTable", {
                        data: content
                        , done: function () {
                            setHeight();
                        }
                    });
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

    });

    /**
     * 关闭弹出页面
     */
    window.closeWin = function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    };

    window.closeParentWindow = function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    };

    // 获取查询参数
    var qlrDataList =[];
    function getSearchParam() {
        if(isNullOrEmpty(gzlslid)){
            return false;
        }
        $.ajax({
            url:"/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/xmxx",
            type: "POST",
            data: JSON.stringify({"gzlslid":gzlslid}),
            contentType: 'application/json',
            success: function (data) {
                removeModal();

                if(data && data.length>0){
                    $("#slbh").val(data[0].slbh);
                    $.ajax({
                        url:"/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/qlr",
                        type: "POST",
                        data: JSON.stringify({"gzlslid":gzlslid,excludeQlrlx:qlrlx}),
                        contentType: 'application/json',
                        success: function (data) {
                            removeModal();

                            if(data && data.length>0){
                                var optionList = '';
                                for (var i = 0; i < data.length; i++) {
                                    qlrDataList.push({qlrmc: data[i].qlrmc, zjh: data[i].zjh});
                                    optionList += '<option value="' + data[i].qlrmc + '">' + data[i].qlrmc + '</option>';
                                }
                                $('#momName').append(optionList);
                                $("#momName").val(data[0].qlrmc);
                                $("#momIdCode").val(data[0].zjh);
                                form.render("select");
                            }

                        },
                        error: function (xhr, status, error) {
                            removeModal();
                            delAjaxErrorMsg(xhr)
                        }
                    });
                }

            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });
    }
    //渲染
    form.on('select(momName)', function (data) {
        if(isNotBlank(data.value)) {
            for (var i = 0; i < qlrDataList.length; i++) {
                if (data.value == qlrDataList[i].qlrmc) {
                    $("#momIdCode").val(qlrDataList[i].zjh);
                }
            }
        }
    });

    function qtqlr() {
        layer.open({
            type: 1,
            title: '查询其他权利人',
            skin: 'bdc-ckgdxx-layer',
            area: ['430px'],
            content:
                '<div id="bdc-popup-small ckgdxx-layer">' +
                '<form class="layui-form" action="">' +
                '<div class="layui-form-item pf-form-item">' +
                '<div class="layui-inline" style="width: 100%;">' +
                ' <label class="layui-form-label">母亲姓名:</label>' +
                ' <div class="layui-input-inline">' +
                '<input type="text" autofocus="true" name="newqlrmc" id="newqlrmc" autocomplete="off" placeholder="请输入" class="layui-input">' +
                '</div>' +
                '</div>' +
                '<div class="layui-inline" style="width: 100%;">' +
                ' <label class="layui-form-label">母亲证件编号:</label>' +
                ' <div class="layui-input-inline">' +
                '<input type="text" autofocus="true" name="newzjh" id="newzjh" autocomplete="off" placeholder="请输入" class="layui-input">' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</form>' +
                '</div>',
            btn: ['查询'],
            btnAlign: 'c',
            yes: function (index, layero) {
                //确定操作
                var newqlrmc = layero.find("input[name='newqlrmc']").val();
                var newzjh = layero.find("input[name='newzjh']").val();

                qlrDataList.push({ qlrmc: newqlrmc, zjh: newzjh });
                var optionList = '<option value="'+ newqlrmc + '">' + newqlrmc + '</option>';


                $('#momName').append(optionList);
                $("#momName").val(newqlrmc);
                $("#momIdCode").val(newzjh);
                form.render("select");
                $('#search').click();

                layer.close(index);
            },
            cancle: function (index) {
                layer.close(index);
            },
            done: function (index) {

            }
        });
    }

});



