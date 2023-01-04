/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/17
 * @description 民政部-婚姻登记信息核验（个人）查询
 */
// 查询参数
var gzlslid= $.getUrlParam("gzlslid");
var qlrlx= '1';
var searchData;
var key ="";
var param;
var jkname= $.getUrlParam("jkname");
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var laytpl = layui.laytpl;
    form.render();

    $(function () {

        // 监听 查询
        $(document).on('click', '.get-cxtj', function () {
            if(isNotBlank(searchData.cxfs) && isNotBlank(searchData.zjh)){
                search();
            }else {
                warnMsg('请输入查询条件');
            }

        });

        initTable();

        function initTable() {
            var getTpl = cxywcsTpl.innerHTML;

            laytpl(getTpl).render({}, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
            $(document).find('input').each(function() {
                $(this).attr('title', $(this).val());
            });
            form.render();
        }

        //查询
        function search() {
            var resultData= [];
            addModel();
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/gx/shijgxCx/sydjzxxcx",
                type: "POST",
                data: JSON.stringify(searchData),
                contentType: 'application/json',
                async:false,
                success: function (data) {
                    if (data.code == 200) {
                        successMsg(data.message);
                        if(data.data && data.data.length>0){
                            //data.data是否有多个元素，是则要循环
                            data.data[0].mc = searchData.mc;
                            data.data[0].zjh = searchData.zjh;
                            data.data[0].cxfs = searchData.cxfs;

                            resultData.push(data.data[0]);
                            generateTable(resultData, false);
                        }
                        dealCxjgxx("success",jkname);
                    } else {
                        warnMsg(data.message);
                    }

                    removeModal();
                },
                error: function (xhr, status, error) {
                    dealCxjgxx("fail",jkname);
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        function generateTable(data, isSearch) {
            var getTpl = tableTpl.innerHTML;

            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
            $(document).find('input').each(function() {
                $(this).attr('title', $(this).val());
            });
            form.render();
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid,"shijsydjzxxcx","民政部-收养登记证信息（国内）查询",key,"市级共享交换平台");
        });
    });



});

function querySydj(clickBtn) {
    let cxfs = $(clickBtn).parents('tr').prev().find('select[name="cxfs"]').val();
    let zjh = $(clickBtn).parents('tr').find('.zjh').val();
    let mc = $(clickBtn).parents('tr').prev().find('.mc').val();

    searchData = {
        "cxfs": Number(cxfs),
        "zjh": zjh,
        "mc": mc
    };
}