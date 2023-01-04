/**
 * Created by ypp on 2020/3/10.
 * 身份证信息查询js
 */
var layer;
var gzlslid= $.getUrlParam("gzlslid");
layui.use(['element','layer','laytpl', 'form'], function(){
  var element = layui.element,
      layer = layui.layer,
      laytpl = layui.laytpl,
      $ = layui.jquery,
      form = layui.form;

  $(function(){

      addModel();
      getSearch();

      //身份证信息查询
      $('#sfzSearch').on('click',function(){
          var getSfzh = $('#sfzh').val();

          if(getSfzh != ''){
              // 身份证号验证
              var msg = checkSfzZjh(getSfzh);
              if(isNotBlank(msg)){
                  layer.msg(msg, {icon: 5, time: 3000});
              }else {
                  $.ajax({
                      url: "/realestate-inquiry-ui/rest/v1.0/queryByExchange/bengbu/queryIdinfo?zjh=" + getSfzh,
                      type: "GET",
                      contentType: 'application/json',
                      dataType: "json",
                      success: function (data) {
                          if(isNotBlank(data)){
                              data.CSRQ = getCsrq(data.GRZJHM);
                              var getSfzTpl = sfzTpl.innerHTML;
                              laytpl(getSfzTpl).render(data, function(html){
                                  $('.bdc-sf-info').html(html);
                              });
                          }else {
                              // 查询数据为空时抛提示
                              alertMsg('未查询到数据');
                          }

                      },
                      error: function (e) {
                          delAjaxErrorMsg(e);
                      }
                  });
              }
          }else {
              warnMsg('请输入身份证号进行查询');
          }
      });

      //营业执照信息查询
      $('#xydmSearch').on('click',function(){
          var getXydm = $('#xydm').val();
          if(getXydm != ''){
             $.ajax({
                  url: "/realestate-inquiry-ui/rest/v1.0/queryByExchange/bengbu/queryYyzzinfo?zjh=" + getXydm,
                  type: "GET",
                  contentType: 'application/json',
                  dataType: "json",
                  success: function (data) {
                      if(isNotBlank(data)){
                          var getXydmTpl = yyzzTpl.innerHTML;
                          laytpl(getXydmTpl).render(data, function(html){
                              $('.bdc-xydm-info').html(html);
                          });
                      }else {
                          // 查询数据为空时抛提示
                          alertMsg('未查询到数据');
                      }

                  },
                  error: function (e) {
                      delAjaxErrorMsg(e);
                  }
              });
          }else {
              warnMsg('请输入社会统一信用代码进行查询');
          }
      });
  });

  // 根据身份证号截取出生日期
  function getCsrq(zjh) {
     var year,month,day,csrq='';
     if(isNotBlank(zjh)){
         if(zjh.length === 18){   //18位身份证号处理
             year = zjh.substring(6, 10);
             month = zjh.substring(10, 12);
             day = zjh.substring(12, 14);
             csrq = year +'年'+ month + '月' + day + '日';
         }
         if(zjh.length === 15){   //15位身份证号处理
             year = "19"+ zjh.substring(6, 8);
             month = zjh.substring(8, 10);
             day = zjh.substring(10, 12);
             csrq = year +'年'+ month + '月' + day + '日';
         }

     }
     return csrq;
  }

    // 获取查询参数
    function getSearch() {
        $.ajax({
            url:"/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/qlr",
            type: "POST",
            data: JSON.stringify({"gzlslid":gzlslid}),
            contentType: 'application/json',
            success: function (data) {
                console.log('qlr:');
                console.log(data);
                removeModal();

                if (data && data.length > 0) {
                    var getData = {
                        "cxywcs": []
                    };
                    for (var i = 0; i < data.length; i++) {
                        getData.cxywcs.push({"zjh": data[i].zjh});
                    }

                    var searchData = getData;

                    // 展示查询条件，加载表格内容
                    console.log('searchData:');
                    console.log(searchData);
                    // generateTable(searchData, false);
                    generateSearchCondition(searchData);

                    // 查询条件不为空进行查询
                    if(searchData &&searchData.cxywcs &&searchData.cxywcs.length >0){
                        $('#sfzSearch').trigger('click');
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
        $('#sfzh').val(searchData.cxywcs[0].zjh);
        $('#xydm').val(searchData.cxywcs[0].zjh);

        searchData.cxywcs.forEach(function(item, index) {
            $('#sfzh-select').append('<option value="' + item.zjh + '">' + item.zjh + '</option>');
            $('#xydm-select').append('<option value="' + item.zjh + '">' + item.zjh + '</option>');
        });

        form.render();
    }

    form.on('select(sfzh-select)', function (data) {
        $(this).parents('.layui-input-inline').find("input[name='sfzh']").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});
        form.render();
        resetSelectDisabledCss();
    });

    form.on('select(xydm-select)', function (data) {
        $(this).parents('.layui-input-inline').find("input[name='xydm']").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});
        form.render();
        resetSelectDisabledCss();
    });

});
