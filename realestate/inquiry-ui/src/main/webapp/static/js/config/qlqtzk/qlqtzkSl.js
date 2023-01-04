layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;
    var data={};
    data.djxl='国有建设用地使用权/房屋所有权房屋抵押权首次登记';
    data.qllx='抵押权';
    data.qlqtzksjy=vkbeautify.sql('select t.YCQZH from bdc_xm t where t.xmid=#{xmid}; select t2.mc from BDC_DYAQ t left join BDC_ZD_DYFS t2 on t.dyfs=t2.dm where t.xmid=#{xmid}; select t.bdbzzqse from bdc_dyaq t where t.xmid=#{xmid}; select case when t.dyfs = \'1\' then \'债务履行期限:\'||to_char(t.zwlxqssj,\'yyyy"年"mm"月"dd"日"\')||\'至\'||to_char(t.zwlxjssj,\'yyyy"年"mm"月"dd"日"\') when t.dyfs = \'2\' then \'债权确定期间:\'||to_char(t.zwlxqssj,\'yyyy"年"mm"月"dd"日"\')||\'至\'||to_char(t.zwlxjssj,\'yyyy"年"mm"月"dd"日"\') else \'债务履行期限:\'||to_char(t.zwlxqssj,\'yyyy"年"mm"月"dd"日"\')||\'至\'||to_char(t.zwlxjssj,\'yyyy"年"mm"月"dd"日"\') end zwlxqx from bdc_dyaq t where t.xmid=#{xmid}');
    data.qlqtzkmb='不动产权证书号:#{ybdcqzh} 抵押的方式:#{mc} 担保债权的数额:#{bdbzzqse}万元 #{zwlxqx}';
    data.fjsjy=vkbeautify.sql('select dbr,zl from bdc_xm where xmid = #{xmid};');
    data.fjmb='登簿人：#{dbr} 坐落：#{zl}';
    data.checkValue='xmid="123"';
    form.val('qlqtzkFjForm', JSON.parse(JSON.stringify(data)));
});


