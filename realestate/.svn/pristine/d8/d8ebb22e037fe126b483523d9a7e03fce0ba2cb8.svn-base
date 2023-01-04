/**
 * (南通)户籍查询台账接口页面 JS
 */
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        table = layui.table;
    $(function () {
        init();

        function init() {
            table.render({
                elem: '#pageTable',
                toolbar: '#toolbarDemo',
                title: '户籍查询台账',
                defaultToolbar: ['filter', 'print', 'exports'],
                even: true,
                cols: [[
                    {type: 'numbers', width: 50, fixed: 'left', title: '序号'},
                    {field: 'xm',    title: '姓名', width: 400},
                    {field: 'zjhm',  title: '证件号码', width: 400},
                    {field: 'yhzgx', title: '与户主关系', minWidth: 400},
                ]],
                data: [],
                page: true,
                done: function () {
                    setHeight();
                }
            });
        }


        // 点击查询
        $('#search').on('click', function () {
            search();
        });


        function search() {
            var qlrmc = $("#qlrmc").val();
            var zjh = $("#zjh").val();

            if (isNullOrEmpty(zjh)) {
                warnMsg("请输入权利人证件号必填条件！");
                return;
            }

            // 先提交户籍查询申请
            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/nantong/sjpt/hjxxcxsq",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({"zjhList":[{"zjh": zjh}]}),
                success: function (data) {
                    removeModal();

                    if(data && data.head && data.head.code == "0000"){
                        // 申请成功，执行查询结果请求
                        // 省级接口要一分钟以上才能返回，直接一分钟后多次查询
                        successMsg("省厅接口需等待一分钟以上，请耐心等待！")
                        for(var i = 1; i <= 4; i++){
                            setTimeout(function () {
                                getCxjg(zjh);
                            }, i * 70000);
                        }
                    } else {
                        if(data && data.head && data.head.msg){
                            alertMsg(data.head.msg + "，请重试！");
                        } else {
                            failMsg("提交户籍查询申请失败，请重试！");
                        }
                    }
                }, error: function () {
                    init();
                    failMsg("提交户籍查询申请失败，请重试！");
                }, complete: function () {
                    removeModal();
                }
            });
        }

        var issuccess = false;
        function getCxjg(zjh) {
            if(issuccess){
                // 之前查询已经成功，无需再次查询
                return;
            }

            successMsg("正在获取反馈结果，请稍等！");
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/nantong/sjpt/hjxxcxfk",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({"zjhList":[{"zjh": zjh}]}),
                success: function (data) {
                    removeModal();

                    if(data && data.head && data.head.code == "0000"){
                        issuccess = true;

                        table.render({
                            elem: '#pageTable',
                            toolbar: '#toolbarDemo',
                            title: '户籍查询台账',
                            defaultToolbar: ['filter', 'print', 'exports'],
                            even: true,
                            cols: [[
                                {type: 'numbers', width: 50, fixed: 'left', title: '序号'},
                                {field: 'xm',    title: '姓名', width: 400},
                                {field: 'zjhm',  title: '证件号码', width: 400},
                                {field: 'yhzgx', title: '与户主关系', minWidth: 400},
                            ]],
                            data: data.data.cxjg,
                            page: false,
                            done: function () {
                                setHeight();
                            }
                        });
                    } else if(data && data.head && data.head.code == "9004"){
                        alertMsg(data.head.msg);
                        issuccess = true;
                    }  else {
                        warnMsg("未获取到反馈信息，系统正在重试(共三次)！");
                    }
                }, error: function () {
                    init();
                    failMsg("未获取到反馈信息，系统正在重试(共三次)！");
                }, complete: function () {
                    removeModal();
                }
            });
        }
    });
});