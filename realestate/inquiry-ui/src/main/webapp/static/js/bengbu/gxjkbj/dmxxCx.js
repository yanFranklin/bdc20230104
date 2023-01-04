/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/12
 * @description 民政部-地名信息查询
 */
var gzlslid= $.getUrlParam("gzlslid");
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;

    form.render();

    $(function () {

        addModel();
        // 加载Table
        table.render({
            elem: '#dmxxTable',
            toolbar: '#toolbarDemo',
            title: '地名信息查询列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'standard_name', title: '地名', width: 150},
                {field: 'place_type', title: '地名类别', width: 150},
                {field: 'roman_alphabet_spelling', title: '罗马字母拼写', width: 150},
                {field: 'place_code', title: '区划代码', width: 200},
                {field: 'place_meaning', title: '地名含义', width: 220},
                {field: 'place_origin', title: '地名来历', width: 220},
                {field: 'place_history', title: '历史沿革', width: 220},
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                setHeight();
                removeModal();
            }
        });

        if (!isNullOrEmpty(gzlslid)) {
            getSearch();
        }

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
            var obj = {},list ={};
            obj.cxywcs=[];
            list.code = $('#code').val();
            list.name = $('#name').val();
            obj.cxywcs.push(list);
            //分页
            obj.loadpage=true;
            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/dmxxcx",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    var content = [];
                    if(data && data.content){
                        content = data.content;
                    }
                    removeModal();
                    table.reload("dmxxTable", {
                        data: content
                        , done: function () {
                            setHeight();
                        }
                    });
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

        // 获取查询参数
        function getSearch() {
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/xm/qxxx/list",
                type: "POST",
                data: JSON.stringify({"gzlslid":gzlslid}),
                contentType: 'application/json',
                success: function (data) {
                    console.log('qxxx:');
                    console.log(data);
                    removeModal();

                    if (data && data.length > 0) {
                        var getData = {
                            "cxywcs": []
                        };
                        for (var i = 0; i < data.length; i++) {
                            getData.cxywcs.push({"code": data[i].code, "name": data[i].name});
                        }
                        var searchData = getData;

                        generateSearchCondition(searchData);

                        // 查询条件不为空进行查询
                        if(!isNullOrEmpty(data)){
                            $('#search').trigger('click');
                        }
                    }


                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        function generateSearchCondition(searchData) {
            $('#code').val(searchData.cxywcs[0].code);
            $('#name').val(searchData.cxywcs[0].name);

            searchData.cxywcs.forEach(function(item, index) {
                $('#code-select').append('<option value="' + item.code + '"' + ' dm=' + item.name + '>' + item.code + '</option>');
            });

            form.render();
        }

        form.on('select(code-select)', function (data) {
            $(this).parents('.layui-input-inline').find("input[name='code']").val(data.value);
            $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});

            var currentValue = data.value;
            var currentDm = $(this).parents('.layui-input-inline').find('select option[value=' + currentValue + ']').attr('dm');
            $(this).parents('.layui-form-item').find('input[name="name"]').val(currentDm);
            form.render();
            resetSelectDisabledCss();
        });

    });

});