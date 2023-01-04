<!DOCTYPE html>
<html lang="zh-cn" class="building-background-write">

<head>
    <meta charset="utf-8">
    <title>项目信息合并</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js?v=1.1"></script>
    <link rel="stylesheet" href="../lib/bdcui/css/table.css">
    <link rel="stylesheet" href="../lib/bdcui/css/search.css">
    <script src="../lib/bdcui/js/table.js"></script>
    <link rel="stylesheet" href="../css/building.css?33">
</head>
<body>

<table id="hbList" lay-filter="dataTable"></table>

<script>
    var fwXmxxIndexList = [];
    <#if fwXmxxIndexList?? && (fwXmxxIndexList?size>0) >
        <#list fwXmxxIndexList as item >
            fwXmxxIndexList.push('${item}');
        </#list>
    </#if>
</script>
<script src="../js/xmxx/xmxxHb.js?3235"></script>
</body>