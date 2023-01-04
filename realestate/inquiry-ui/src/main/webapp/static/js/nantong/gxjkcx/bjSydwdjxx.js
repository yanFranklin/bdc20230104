/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/10
 * @description 中编办-事业单位登记信息（含机关、群团信息）查询接口台账
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
        //党群机关表头
        var dqjgTableTitle =[
            {field: 'xh', type: 'numbers', width: 60, title: '序号'},
            {field: 'onemc', title: '第一名称', width: 200},
            {field: 'tyshxydm', title: '统一社会信用代码', width: 200},
            {field: 'jgdz', title: '机构地址', width: 200},
            {field: 'bfrq', title: '颁发日期', width: 180},
            {field: 'jgxz', title: '机构性质', width: 200},
            {field: 'ztzw', title: '状态', width: 200
                ,templet:function (d) {
                    switch (d.zt) {
                        case "0":
                            return "正常";
                            break;
                        case "1":
                            return "已撤销";
                            break;
                    }
                }
            },

            {field: 'fmjg', title: '赋码机关', minWidth: 200, hide:true},
            {field: 'fzr', title: '负责人', minWidth: 200, hide:true},
            {field: 'qlbs', title: '权力标识', width: 200, hide:true},
            {field: 'qtmc', title: '其他名称', width: 200, hide:true},
            {field: 'sjrq', title: '数据日期', width: 200, hide:true},
            {field: 'twomc', title: '第二名称', width: 200, hide:true},
            {field: 'threemc', title: '第三名称', width: 200, hide:true},
            {field: 'cz', title: '操作', minWidth: 80, templet: '#czTpl', fixed: 'right'}


        ];
        //事业单位报表头
        var sydwTableTitle =[
            {field: 'xh', type: 'numbers', width: 60, title: '序号'},
            {field: 'onemc', title: '第一名称', width: 200},
            {field: 'tyshxydm', title: '统一社会信用代码', width: 200},
            {field: 'zs', title: '住所', width: 200},
            {field: 'jbdw', title: '举办单位', minWidth: 200},
            {field: 'ywlx', title: '业务类型', width: 200},
            {field: 'ztzw', title: '状态', width: 200
                ,templet:function (d) {
                    switch (d.zt) {
                        case "0":
                            return "正常";
                            break;
                        case "1":
                            return "证书废止";
                            break;
                        case "2":
                            return "已吊销";
                            break;
                        case "3":
                            return "冻结";
                            break;
                        case "9":
                            return "冻结";
                            break;
                    }
                }
            },

            {field: 'djgljg', title: '登记管理机关', minWidth: 200, hide:true},
            {field: 'fzr', title: '法定代表人', minWidth: 200, hide:true},
            {field: 'jfly', title: '经费来源', minWidth: 200, hide:true},
            {field: 'kbzj', title: '开办资金', minWidth: 200, hide:true},
            {field: 'qlbs', title: '权力标识', width: 200, hide:true},
            {field: 'qtmc', title: '其他名称', width: 200, hide:true},
            {field: 'sjrq', title: '数据日期', width: 200, hide:true},
            {field: 'twomc', title: '第二名称', width: 200, hide:true},
            {field: 'threemc', title: '第三名称', width: 200, hide:true},
            {field: 'yxqx_s', title: '有效期(始)', width: 200, hide:true},
            {field: 'yxqx_z', title: '有效期(至)', width: 200, hide:true},
            {field: 'zzhywfw', title: '宗旨和业务范围', width: 200, hide:true},
            {field: 'cz', title: '操作', minWidth: 80, templet: '#czTpl', fixed: 'right'}
        ];

        // 加载Table
        table.render({
            elem: '#sydwdjxxTable',
            toolbar: '#toolbarDemo',
            title: '事业单位登记信息（含机关、群团信息）查询列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [dqjgTableTitle],
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

        //监听社会信用代码行工具事件
        table.on('tool(sydwdjxxTable)', function (obj) {
            var data = obj.data;
            var popupContent = $('.popup-dqjg'),ztmc='';

            // 状态代码转换名称
            if(data.qlbs =='事业单位'){
                popupContent = $('.popup-sydw');
                $('#popupTwoRows textarea[name="zzhywfw"]').html(data.zzhywfw);

                switch (data.zt) {
                    case "0":
                        ztmc = "正常";
                        break;
                    case "1":
                        ztmc = "证书废止";
                        break;
                    case "2":
                        ztmc = "已吊销";
                        break;
                    case "3":
                        ztmc = "冻结";
                        break;
                    case "9":
                        ztmc = "冻结";
                        break;
                }
            }else {
                switch (data.zt) {
                    case "0":
                        ztmc = "正常";
                        break;
                    case "1":
                        ztmc = "已撤销";
                        break;
                }
            }
            form.val('djxxForm', data);
            popupContent.find('input[name="zt"]').val(ztmc);

            //小弹出层
            layer.open({
                title: '中编办机关群团事业单位统一社会信用代码信息',
                type: 1,
                area: ['960px', '500px'],
                // btn: ['关闭'],
                content: popupContent
                , yes: function (index, layero) {
                }
                , cancel: function () {
                    //右上角关闭回调
                    $("#djxxForm")[0].reset();
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
            list.tyshxydm = $('#tyshxydm').val();
            list.name = $('#name').val();
            obj.paramDTOList.push(list);
            obj.loadpage =true;

            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/scopsr/organQuery",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();
                    var content = [],tableTitle = dqjgTableTitle;
                    if (data && data.content){
                        content = data.content;
                        qlbs = data.content[0].qlbs;
                        // qlbs为事业单位时更改表头，默认表头为党群机关
                        if(qlbs =='事业单位'){
                            tableTitle = sydwTableTitle;
                        }
                    }
                    // 重新加载Table
                    table.render({
                        elem: '#sydwdjxxTable',
                        toolbar: '#toolbarDemo',
                        title: '事业单位登记信息（含机关、群团信息）查询列表',
                        defaultToolbar: ['filter'],
                        request: {
                            limitName: 'size' //每页数据量的参数名，默认：limit
                        },
                        even: true,
                        cols: [tableTitle],
                        data: content,
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

    });
});



