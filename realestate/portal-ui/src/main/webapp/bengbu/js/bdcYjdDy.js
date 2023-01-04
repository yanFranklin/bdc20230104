/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 不动产移交打印单
 */
var onePageShowThirty = function (data) {
    //只有一页时表格高度为23,当多页时，最后一页高度为23，其他页高度为29。
    if (data.length % 30 != 0) {
        $(".layui-table div").css("height", "29px");
    } else {
        var magicNumber = data.length / 30;
        if (magicNumber == 1) {
            $(".layui-table div").css("height", "23px");
        } else {
            var lastMagicNumber = magicNumber - 1;
            console.info(lastMagicNumber);
            $(".layui-table").find(".data").each(function (index, value) {
                console.info(index + ":" + index / 30);
                if (index / 30 > lastMagicNumber) {
                    $(value).find("div").css("height", "23px");
                } else {
                    $(value).find("div").css("height", "29px");
                }
            });
        }
    }
}
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form', 'jquery', 'laytpl'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        form = layui.form;

    var BASE_URL = '/realestate-portal-ui/rest/v1.0/task/bdcYjd';
    var user = queryCurrentUser();
    var yjdh = "";
    var data = JSON.parse(sessionStorage.getItem("yjddydata:" + user.id));
    var getTpl = yjdTableTpl.innerHTML
        , view = document.getElementById('tableView');
    laytpl(getTpl).render(data, function (html) {
        view.innerHTML = html;
    })

    // 生成移交单号
    generateYjdh(data);

    //一页显示30条样式控制
    onePageShowThirty(data);

    $('#dy').click(function () {
        $('#dy').css("display", "none");
        updateDyzt(data);
    });
    //关闭处理
    window.onunload = function () {
        window.opener.location.reload()
    };

    /**
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 不动产移交打印单生成移交单号
     */
    function generateYjdh(data) {
        var taskids = [];
        for (var i = 0; i < data.length; i++) {
            taskids[i] = data[i].taskId
        }
        $.ajax({
            url: BASE_URL + '/yjdh',
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data: JSON.stringify(taskids),
            success: function (dh) {
                yjdh = dh;
                $('.bdc-yjd-dh').html(dh);
            },
            error: function (e) {
                response.fail(e)
            }
        });
    }

    function updateDyzt(data) {
        var taskids = [];
        for (var i = 0; i < data.length; i++) {
            taskids.push(data[i].taskId)
        }
        $.ajax({
            url: BASE_URL + '/updateDyztBB',
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data: JSON.stringify(taskids),
            success: function (data) {

            }, error: function (e) {
                response.fail(e)

            }, complete: function () {
                window.print();
            }
        });
    }


});

