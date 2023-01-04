<html xmlns="http://www.w3.org/1999/xhtml">
<head lang="en">
    <meta http-equiv="content-type" content="text/html;charset=utf-8"></meta>
    <title>不动产登记收费单</title>
    <link rel="stylesheet" href="../../../static/lib/layui/css/layui.css"></link>
    <style>
        .bdc-container{
            padding-top: 20px;
            padding-bottom: 20px;
            width: 620px;
            height: 100%;
            margin: 0 auto;
        }
        /* .layui-table tbody tr:hover,
         .layui-table thead tr,
         .layui-table-total tr,
         .layui-table[lay-even] tr:nth-child(even){
             background-color: #fff;
         }*/
        .layui-table td, .layui-table th{
            height: 20px;
            padding: 9px 5px;
            text-align: center;
        }
        .layui-table td.bdc-text-left{
            text-align: left;
        }
        h1 {
            font-family:SimSun;
            text-align: center;
            font-size: 20px;
            line-height: 60px;
            font-weight: 700;
        }
        .bdc-right-p{
            text-align: right;
            line-height: 30px;
        }
        .bdc-right-p>span{
            min-width: 120px;
            display: inline-block;
            text-align: left;
        }
        .layui-table td{
            border: 1px solid #000000;
        }
        table{
            border-collapse: collapse;
        }
    </style>
</head>
<body>
<div class="bdc-container" style="font-family:SimSun;table-layout:fixed;">
    <h1>不动产登记收费单</h1>
    <p class="bdc-right-p">受理编号：<span>${slbh!}</span></p>
    <table class="layui-table">
        <tr>
            <td>缴费人名称</td>
            <td colspan="2" class="bdc-text-left">${qlrmc!}</td>
            <td width="120px">流程名称</td>
            <td colspan="3" width="200px" class="bdc-text-left">${djxl!}</td>
        </tr>
        <tr>
            <td>代理人名称</td>
            <td colspan="2" class="bdc-text-left">${dlrmc!}</td>
            <td>代理人电话</td>
            <td colspan="3" class="bdc-text-left">${dlrlxdh!}</td>
        </tr>
        <tr>
            <td>坐落</td>
            <td colspan="6" class="bdc-text-left">${zl!}</td>
        </tr>
        <tr>
            <td>不动产单元号</td>
            <td colspan="6" class="bdc-text-left">${bdcdyh!}</td>
        </tr>
        <tr>
            <td>缴费方</td>
            <td>收费项目名称</td>
            <td colspan="2">收费项目标准</td>
            <td>数量</td>
            <td>金额单位</td>
            <td>应缴金额</td>
        </tr>
    <#if recordList?exists>
        <#list recordList as record>
            <tr>
                <td>${record.jff!}</td>
                <td>${record.sfxmmc!}</td>
                <td colspan="2">${record.sfxmbz!}</td>
                <td> ${record.sfxmsl!}</td>
                <td>${record.jedw!}</td>
                <td>${record.ysje!}</td>
            </tr>
        </#list>
    </#if>
        <tr>
            <td>合计</td>
            <td colspan="6" class="bdc-text-left">${hj!}</td>
        </tr>
        <tr>
            <td>备注</td>
            <td colspan="6" class="bdc-text-left">${bz!}</td>
        </tr>
    </table>
    <p class="bdc-right-p">签名：<span>${sfrxm!}</span></p>
    <p class="bdc-right-p">日期：<span>${sfsj!}</span></p>
</div>
</body>
</html>