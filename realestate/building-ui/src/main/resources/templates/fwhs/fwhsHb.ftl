<!DOCTYPE html>
<html lang="zh-cn" class="building-background-write">

<head>
    <meta charset="utf-8">
    <title>户室合并</title>
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
</head>
<body>

<table id="hbList" lay-filter="dataTable"></table>
<script src="../js/fwhs/fwhsHb.js"></script>
<script>
    var fwhsIndexList = [];
    <#if fwhsIndexList?? && (fwhsIndexList?size>0) >
    <#list fwhsIndexList as item >
        fwhsIndexList.push('${item}');
    </#list>
    </#if>
</script>
</body>