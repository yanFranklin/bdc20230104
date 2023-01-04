<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <title>户室合并选择继承子户室</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/common.css?v=1034">
    <link rel="stylesheet" href="../css/upload.css?v=1.1.2">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js?v=1.1"></script>
    <script src="../lib/bdcui/js/table.js"></script>
    <link rel="stylesheet" href="../lib/bdcui/css/table.css">
    <link rel="stylesheet" href="../lib/bdcui/css/form.css">
</head>
<body>
<div>
    <form class="layui-form setOverflow" lay-filter="form">
        <input type="text" class="layui-input layui-hide" name="bgbh" id="bgbh"
               value="${bgbh!}">
        <div class="content-title layui-clear">
            <div class="title-btn-area">
                <button class="layui-btn bdc-major-btn" lay-submit="" id="hsbg" lay-filter="hsbg"
                        type="button">提交
                </button>
            </div>
        </div>
        <table id="hbList" lay-filter="dataTable"></table>
    </form>
</div>
<script>
    var fwhsIndexList = [];
    <#if fwhsIndexList?? && (fwhsIndexList?size>0) >
        <#list fwhsIndexList as item >
            fwhsIndexList.push('${item}');
        </#list>
    </#if>
</script>
<script src="../js/fwhs/hshbZhsList.js"></script>
</body>