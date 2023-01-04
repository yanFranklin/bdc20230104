/**
 * author: <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
 * version 1.0.0  2021/10/18
 * describe: 质检结果汇总
 */
var laydate, layer, form, laytpl;
var zdList = [];
var djlx = getQueryString("djlx");
var sxfs = getQueryString("sxfs"); // personal 个人  all 所有

layui.use(['form', 'jquery', 'element', 'table', 'laytpl', 'laydate'], function () {
    var $ = layui.jquery,
        laydate = layui.laydate,
        table = layui.table,
        layer = layui.layer,
        laytpl = layui.laytpl;
        form = layui.form;

    zdList = getMulZdList("djlx");

    $(function () {

        // 查询方法
        $('#search').on('click', function () {
            var kssj = $("#kssj").val();
            var jssj = $("#jssj").val();
            if(!isNotBlank(kssj) || !isNotBlank(jssj)){
                warnMsg("请选择开始时间和结束时间后，在进行查询。");
                return;
            }
            //表格头，抽检时间赋值
            var fmtKssj = Format(kssj, "yyyy年M月d日");
            var fmtJssj = Format(jssj, "yyyy年M月d日");
            $("#cjkssj").html(fmtKssj);
            $("#cjjssj").html(fmtJssj);

            var bdcZjQO = {
                sxfs : sxfs,
                djlx: djlx,
                kssj: kssj,
                jssj: jssj
            };
            $.ajax({
                url: getContextPath() + "/rest/changzhou/zjhc/zjjg/hz",
                type: 'POST',
                contentType: "application/json",
                data: JSON.stringify(bdcZjQO),
                success: function (data) {
                    removeModal();
                    $("#zjjghzTable").find(".bdc-table-cotent").remove();
                    var getTpl = zjjghzTpl.innerHTML;
                    laytpl(getTpl).render(data, function(html){
                        $("#zjjghzTable tbody").append(html);
                    });
                    disabledAddFa();
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                }
            });

        });

    });

    // 日期控件
    laydate.render({
        elem: '#kssj',
        //value: new Date((new Date().getTime() - 90 * 24 * 60 * 60 * 1000)),
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#jzsj').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#jssj',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#kssj').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });

});


/**
 * 字典项转换
 * @param name 字典项名称
 * @param zdx 实际值
 * @returns {string} 转换后的名称
 */
function getZdMc(name, zdx) {
    var zdmc = "";
    var zd = zdList[name];
    if(isNotBlank(zd)){
        $.each(zd, function (index, item) {
            if(item.DM == zdx) {
                zdmc = item.MC;
            }
        });
    }
    return zdmc;
}

