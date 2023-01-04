//页面入口
var gzlslid = getQueryString("gzlslid");
var $;
layui.use(['jquery','table'], function () {
    $ = layui.jquery;
   var table = layui.table;
   $(function () {
       addModel();
       $.ajax({
           type: "get",
           url: getContextPath() + "/slym/qlr/sfhy",
           dataType:"json",
           data: {gzlslid: gzlslid},
           success: function (data) {
               console.log(data);
               // 加载Table
               removeModal();
               if(data){
                   $('.bdc-table-none').remove();
                   var trList = '';
                   for (i=0;i < data.data.length;i++){
                       trList += '<tr><td class="set-center">'+data.data[i].CheckSourceInfos.xm + '</td>>'+
                           '<td class="set-center">'+data.data[i].CheckCodeDesc + '</td>></tr>';
                   }
                   $('tbody').append(trList);
               }

           }, error: function (xhr, status, error) {
               removeModal();
               delAjaxErrorMsg(xhr);
           }
       });

   });
});





