/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/12
 * @description 民政部-地名信息查询
 */
// 查询参数
var gzlslid= $.getUrlParam("gzlslid");
var searchData=[];
//权利人信息
var qlrDataList =[];
var key ="";
var jkname= $.getUrlParam("jkname");
layui.use(['jquery','form', 'element', 'laydate', 'laytpl'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var element = layui.element;
    var laytpl = layui.laytpl;

    form.render();

    $(function () {

        getSearch();

        form.on('select', function (data) {
            if(isNotBlank(data.value)) {
                for (var i = 0; i < qlrDataList.length; i++) {
                    if (data.value == qlrDataList[i].qlrmc) {
                        $(this).parents('tr').find('.layui-table-edit').val(qlrDataList[i].zjh);
                    }
                }
            }
            form.render('select');
        });

        // 监听 点击核验
        $(document).on('click','#hyxxhy',function () {
            search();
        });

        // 获取查询参数
        function getSearch() {
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/qlr",
                type: "POST",
                data: JSON.stringify({"gzlslid":gzlslid, "qlrlx":1}),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();
                    if(data){

                        var optionList = '';
                        for(i=0; i<data.length; i++){
                            qlrDataList.push({ qlrmc: data[i].qlrmc, zjh: data[i].zjh });
                            optionList += '<option value="'+ data[i].qlrmc + '">' + data[i].qlrmc + '</option>'
                            if (data[i].dlrmc && data[i].dlrzjh){
                                qlrDataList.push({
                                    qlrmc:data[i].dlrmc,
                                    zjh: data[i].dlrzjh
                                });
                                optionList += '<option value="'+ data[i].dlrmc + '">' + data[i].dlrmc + '</option>'
                            }
                        }
                        // 增加权利人姓名选项
                        $('#name_man').append(optionList);
                        $('#name_woman').append(optionList);
                        form.render('select');
                    }

                }, error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        //查询
        function search() {
            var name_man = $('#name_man').val();
            var cert_num_man = $('#cert_num_man').val();
            var name_woman = $('#name_woman').val();
            var cert_num_woman =$('#cert_num_woman').val();

            if(isNullOrEmpty(name_man) || isNullOrEmpty(cert_num_man) || isNullOrEmpty(name_woman) || isNullOrEmpty(cert_num_woman)){
                warnMsg('缺少查询条件');

            }else {

                var obj={
                    "cxywcs":[
                        {
                            "cert_num_man": cert_num_man,
                            "name_man": name_man,
                            "name_woman": name_woman,
                            "cert_num_woman": cert_num_woman
                        }
                    ],
                    "loadpage":false
                };

                addModel();
                // 重新请求
                $.ajax({
                    url:"/realestate-inquiry-ui/rest/v1.0/gx/gjpt/naturalResources/hyxxhysf",
                    type: "POST",
                    data: JSON.stringify(obj),
                    contentType: 'application/json',
                    success: function (data) {
                        dealCxjgxx("success",jkname);
                        var result=obj.cxywcs[0];
                        result.result =data[0].op_type_desc;
                        var resultData =[];
                        resultData.push(result);
                        key =saveJkDataToRedis(resultData);
                        removeModal();
                        // 展示核验结果
                        $('.result').val(data[0].op_type_desc);
                    },
                    error: function (xhr, status, error) {
                        dealCxjgxx("fail",jkname);
                        removeModal();
                        delAjaxErrorMsg(xhr)
                    }
                });
            }
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid,"sjhyxxhysf","民政部-婚姻登记信息核验（双方）",key,"省级数据共享交换平台");
        });

    });

});



