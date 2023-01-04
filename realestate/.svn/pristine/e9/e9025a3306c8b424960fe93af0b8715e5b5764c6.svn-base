/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/09
 * @description 民政部-婚姻登记信息查询服务接口台账
 */
var gzlslid = getQueryString('gzlslid');
layui.use(['jquery','form', 'table', 'laydate', 'laytpl'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;

    if (!isNullOrEmpty(gzlslid)) {
        getSearchParam();
    }

    form.render();

    $(function () {
        //表格信息
        table.render({
            elem: '#gzsxxTable',
            toolbar: '#toolbarDemo',
            title: '公正文书查询服务',
            defaultToolbar: ['filter', 'print', 'exports'],
            even: true,
            cols: [[
                {type: 'numbers', width: 50, fixed: 'left'},
                {field: 'PERSON_NAME_0', title: '当事人姓名', width: 100},
                {field: 'CERTIFICATE_NUM_0', title: '当事人证件号码', minWidth: 180},
                {field: 'GZSBH_1', title: '公证书编号', minWidth: 180},
                {field: 'GZYXM_1', title: '公证员姓名', width: 100},
                {field: 'GZCMC_1', title: '公证处名称', minWidth: 180},
                {field: 'GZSXMC_1', title: '公证业务类别', width: 160, hide:true},
                {field: 'BJRQ_1', title: '办结日期', width: 180, hide:true},
                {fixed: 'right', title: '操作', toolbar: '#czTpl', width: 80}
            ]],
            data: [],
            page: true,
            done: function () {
                setHeight();
            }
        });

        //监听婚姻登记行工具事件
        table.on('tool(gzsxxTable)', function (obj) {
            var data = obj.data;
            form.val('gzsxxForm', data);
            //小弹出层
            layer.open({
                title: '公正文书查询服务信息',
                type: 1,
                area: ['960px', '400px'],
                // btn: ['关闭'],
                content: $('#popupTwoRows')
                , yes: function (index, layero) {
                }
                , cancel: function () {
                    //右上角关闭回调
                    $("#gzsxxForm")[0].reset();
                    closeWin();
                    //return false 开启该代码可禁止点击该按钮关闭
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
            obj.dsrxm = $('#dsrxm').val();
            obj.dsrzjhm = $('#dsrzjhm').val();
            obj.gzsbh = $('#gzsbh').val();

            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/bwgxjk/sfgzws",
                type: "POST",
                data: obj,
                success: function (data) {
                    var content = [];
                    if(data && data.content){
                        content = data.content;
                    }
                    removeModal();
                    table.reload("gzsxxTable", {
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

    function getSearchParam() {
        $.ajax({
            url:"/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/qlr",
            type: "POST",
            async: false,
            data: JSON.stringify({"gzlslid":gzlslid}),
            contentType: 'application/json',
            success: function (data) {
                removeModal();
                if (data && data.length > 0) {
                    queryResult(data);
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }

    function queryResult(searchParam) {
        var obj = {};
        obj.paramDTOS = [];
        obj.loadpage = true;
        for (let i = 0; i < searchParam.length; i++) {
            var param = {};
            param.qlrmc = searchParam[i].qlrmc;
            param.zjh = searchParam[i].zjh;
            obj.paramDTOS.push(param);
        }
        searchData = obj;
        // 展示查询条件，加载表格内容
        console.log('searchData:');
        console.log(searchData);
        generateSearchCondition(searchData);

        addModel();
        // 重新请求
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/civil/marriageQuery",
            type: "POST",
            data: JSON.stringify(obj),
            contentType: 'application/json',
            success: function (data) {
                var content = [];
                if(data && data.content){
                    content = data.content;
                }
                removeModal();
                table.reload("gzsxxTable", {
                    data: content
                    , done: function () {
                        setHeight();
                    }
                });
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr);
            }
        });
    }

    function generateSearchCondition(searchData) {
        $('#name').val(searchData.paramDTOS[0].qlrmc);
        $('#cert_num').val(searchData.paramDTOS[0].zjh);

        searchData.paramDTOS.forEach(function(item, index) {
            $('#name-select').append('<option value="' + item.qlrmc + '"' + ' dm=' + item.zjh + '>' + item.qlrmc + '</option>');
        });

        form.render();
    }

    form.on('select(name-select)', function (data) {
        $(this).parents('.layui-input-inline').find("input[name='name']").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});

        var currentValue = data.value;
        var currentDm = $(this).parents('.layui-input-inline').find('select option[value=' + currentValue + ']').attr('dm');
        $(this).parents('.layui-form-item').find('input[name="cert_num"]').val(currentDm);

        form.render();
        resetSelectDisabledCss();
    });

});



