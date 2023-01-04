/**
 * author: <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
 * version 1.0.0  2021/10/18
 * describe: 质检结果汇总
 */
var laydate, layer, form, laytpl;
var zjdbh = getQueryString("zjdbh");
var zdList = [];
layui.use(['form', 'jquery', 'element', 'table', 'laytpl', 'laydate'], function () {
    var $ = layui.jquery,
        laydate = layui.laydate,
        table = layui.table,
        layer = layui.layer,
        laytpl = layui.laytpl;
        form = layui.form;

    $(function () {
        zdList = getMulZdList("djlx");
        loadZjdxx(zjdbh);
        /**
         * 加载质检单信息（发起请求）
         * @param zjdbh
         */
        function loadZjdxx(zjdbh) {
            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/changzhou/zjhc/plzj/info",
                type: 'POST',
                data: {
                    zjdbh: zjdbh,
                },
                dataType: 'json',
                success: function (data) {
                    generateXmxx(data);
                    generateZjxx(data);
                    removeModel();
                }
            });
        }

        /**
         * 生成基本信息
         * @param bdcxmxx
         */
        function generateXmxx(bdczjxx) {
            var tpl = zjdTpl.innerHTML, view = document.getElementById('zjdxx');
            laytpl(tpl).render(bdczjxx.bdcZjdDO, function (html) {
                view.innerHTML = html;
            });
            form.render();
        }


        /**
         * 渲染权利人信息
         * @param data 从后台获取的数据
         * @param xmid 项目 ID
         */
        function generateZjxx(data) {
            var tpl = zjTpl.innerHTML, view = document.getElementById('zjxx');
            laytpl(tpl).render(data.bdcZjDOList, function (html) {
                view.innerHTML = html;
            });
            disabledAddFa();
        }

    });



});


function openZjDad(data){
    console.info(data);
    var url = getContextPath()+"/changzhou/zj/zjqk.html?zjid=" + data ;
    layer.open({
        type: 2,
        area: ['960px', '575px'],
        fixed: false, //不固定
        content: url,
        btnAlign: "c"
    });
}

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