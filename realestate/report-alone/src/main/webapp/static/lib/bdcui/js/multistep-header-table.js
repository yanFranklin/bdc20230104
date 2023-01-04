/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 动态渲染多表头表格
 */
layui.use(['jquery','table','laytpl','laypage'], function(){
    var $ = layui.$,
        laytpl = layui.laytpl,
        laypage = layui.laypage;

    $(function () {
        //渲染表格
        var tableData = [
            {ywh: '201458545957',bh: '00094582',mc: '物业用房',mj: 95.11,ftmj: '',djsj: '2014年10月21日',dbr: '王**',fj: ''},
            {ywh: '201458545957',bh: '00094582',mc: '物业用房',mj: 95.11,ftmj: '',djsj: '2014年10月21日',dbr: '王**',fj: ''},
            {ywh: '201458545957',bh: '00094582',mc: '物业用房',mj: 95.11,ftmj: '',djsj: '2014年10月21日',dbr: '王**',fj: ''},
            {ywh: '201458545957',bh: '00094582',mc: '物业用房',mj: 95.11,ftmj: '',djsj: '2014年10月21日',dbr: '王**',fj: ''},
            {ywh: '201458545957',bh: '00094582',mc: '物业用房',mj: 95.11,ftmj: '',djsj: '2014年10月21日',dbr: '王**',fj: ''}
        ];
        var getTpl = tableTpl.innerHTML
            ,view = document.getElementById('tableView');
            laytpl(getTpl).render(tableData, function(html){
            view.innerHTML = html;
        });

    });

});