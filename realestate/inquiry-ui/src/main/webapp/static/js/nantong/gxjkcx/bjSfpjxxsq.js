/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/10
 * @description 最高法-司法判决信息查询申请接口台账
 */
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;

    form.render();

    // 当前页数据
    var currentPageData = new Array();
    $(function () {
        // 加载Table
        table.render({
            elem: '#sfpjxxTable',
            toolbar: '#toolbarDemo',
            title: '司法判决信息查询申请列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'cxqqdh', title: '查询请求单号'},
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
            }
        });

        /**
         * 点击查询
         */
        $('#search').on('click', function () {
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
            obj.paramDTOList=[];
            obj.slbh = $('#slbh').val();
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                list[name] = value;
            });
            obj.paramDTOList.push(list);
            obj.loadpage =true;

            addModel();
            // 重新请求
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/supremecourt/decisionApply",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();
                    var content='';
                    if(data && data.content){
                        content = data.content;
                    }
                    table.reload("sfpjxxTable", {
                        data:content
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

    });

    /**
     * 前台的字典转换，代码转换为名称
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zdname 字典名 bdclx
     * @param zdDm 字典代码 1
     * @param zdListName JS中保存字典数据的变量名 默认为zdList*/
    function zdDmToMc(zdname, zdDm, zdListName) {
        try {
            if (zdDm != null) {
                var zdVals;
                if ("gyfs" == zdname) {
                    zdVals = eval(zdListName.gyfs);
                }
                if ("fwyt" == zdname) {
                    zdVals = eval(zdListName.fwyt);
                }
                if (zdVals) {
                    for (var i = 0; i < zdVals.length; i++) {
                        var zdVal = zdVals[i];
                        if (zdDm == zdVal.DM) {
                            return zdVal.MC;
                        }
                    }
                }
                return zdDm;
            }
            return '';
        } catch (e) {
            return "";
        }
    }


});



