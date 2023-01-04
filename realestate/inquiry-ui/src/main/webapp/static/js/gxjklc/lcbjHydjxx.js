/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2021/01/29
 * @description 民政部-婚姻登记信息查询服务
 */
// 查询参数
var gzlslid= $.getUrlParam("gzlslid");
var formStateId = $.getUrlParam("formStateId");
var jkname= $.getUrlParam("jkname");
var alldata= $.getUrlParam("alldata");
var qlrlx= '1';
var searchData=[];
var key ="";
layui.use(['jquery','form', 'table', 'laydate', 'laytpl'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var laytpl = layui.laytpl;

    form.render();
    $(function () {

        //是否展示所有的
        if (isNullOrEmpty(alldata)){
            alldata = false;
        }

        getSearch();

        getStateById(false, formStateId, 'lcbjHydjxx');

        $('#search').on('click', function () {
            // 获取查询条件
            var name = $(this).parents('.layui-form-item').find('#name').val();
            var certNum = $(this).parents('.layui-form-item').find('#certNum').val();

            // 判空
            if (!isNotBlank(name) || !isNotBlank(certNum)) {
                warnMsg('请输入必要查询条件');
                return false;
            }

            // 组织查询条件
            var getData ={
                "slbh":"",
                "paramDTOList":[],
                "loadpage":false
            };
            getData.paramDTOList.push({"name": name, "certNum": certNum});
            searchData = getData;

            // 展示查询条件，加载表格内容
            generateTable(searchData,false);

            // 查询条件不为空进行查询
            if(searchData &&searchData.paramDTOList &&searchData.paramDTOList.length >0){
                search();
            }
        });


        // 获取查询参数
        function getSearch() {
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/qlr",
                type: "POST",
                data: JSON.stringify({"gzlslid":gzlslid, "qlrlx":qlrlx}),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();

                    if(data && data.length>0){
                        var getData ={
                            "slbh":"",
                            "paramDTOList":[],
                            "loadpage":false
                        };
                        for(var i=0; i<data.length;i++){
                            getData.paramDTOList.push({"name":data[i].qlrmc, "certNum":data[i].zjh});
                            if (data[i].dlrmc && data[i].dlrzjh){
                                getData.paramDTOList.push({
                                    "certNum":data[i].dlrzjh,
                                    "name": data[i].dlrmc
                                });
                            }
                        }
                        searchData = getData;

                        // 展示查询条件，加载表格内容
                        generateTable(searchData,false);

                    }
                    // 查询条件不为空进行查询
                    if(searchData &&searchData.paramDTOList &&searchData.paramDTOList.length >0){
                        search();
                    }
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }


        //查询
        function search() {
            addModel();
            // 重新请求
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/civil/marriageQuery",
                type: "POST",
                data: JSON.stringify(searchData),
                contentType: 'application/json',
                success: function (data) {
                    dealCxjgxx("success",jkname);
                    // key =saveJkDataToRedis(data);
                    removeModal();

                    // 结果数据不为空，重新加载表格
                    var resultData =[];
                    var certNumManArr =[];
                    if(data && data.length>0){
                        // 将需要展示的查询条件加入结果数据
                        for(var j=0; j < searchData.paramDTOList.length; j++){
                            //是否获取到婚姻信息
                            var hyxx =false;
                            for(var i=0; i<data.length; i++){
                                if((data[i].certNumMan ==  searchData.paramDTOList[j].certNum &&
                                    data[i].nameMan == searchData.paramDTOList[j].name) ||
                                    (data[i].certNumWoman ==  searchData.paramDTOList[j].certNum &&
                                    data[i].nameWoman == searchData.paramDTOList[j].name)
                                ){
                                    hyxx =true;
                                    if(data[i].paramDTOList ==null){
                                        data[i].paramDTOList =[];
                                    }
                                    //可能会出现二婚的情况,如果配置了全部展示则将所有出现的数据都展示
                                    if(certNumManArr.indexOf(data[i].certNumMan) <0
                                        ||  (alldata == true || alldata == "true")
                                    ){
                                        //夫妻双方均为权利人，结果只展示一条
                                        certNumManArr.push(data[i].certNumMan);
                                        resultData.push(data[i]);
                                    }
                                    data[i].paramDTOList.push(searchData.paramDTOList[j]);
                                }
                            }
                            if (!hyxx) {
                                //没有查询到婚姻信息
                                var dataObj = {};
                                var paramList = [];
                                var param = searchData.paramDTOList[j];
                                paramList.push(param);
                                dataObj.paramDTOList = paramList;
                                resultData.push(dataObj);
                            }
                        }
                        //夫妻双方之只需要一条记录即可
                        key = saveJkDataToRedis(resultData);
                        generateTable(resultData, true);
                    }
                },
                error: function (xhr, status, error) {
                    dealCxjgxx("fail",jkname);
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }


        function generateTable(data,isSearch) {
            $('#lcsbj').removeClass('layui-hide');
            if(isSearch){
                var getTpl = tableTpl.innerHTML;
            }else {
                var getTpl = cxywcsTpl.innerHTML;
            }

            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid,"bjhydjxx","民政部-婚姻登记信息查询",key,"自然资源部");
        });

    });

});



