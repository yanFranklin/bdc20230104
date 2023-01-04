<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>子户室查询列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js?v=1.1.1"></script>
    <link rel="stylesheet" href="../lib/bdcui/css/table.css">
    <link rel="stylesheet" href="../lib/bdcui/css/search.css">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/bdcui/js/table.js"></script>
</head>
<body>
<div class="bdc-table-box">

    <input type="text" class="layui-input layui-hide" name="fwHsIndex" id="fwHsIndex"
           value="${fwHsIndex!}">
    <!--数据表格-->
    <table id="fwzhsList" lay-data="{id: 'fwzhsList'}" lay-filter="dataTable"></table>
    <!--数据表格结束-->
</div>
<script type="text/html" id="toolbar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm bdc-major-btn" type="button" lay-event="addzhs">添加</button>
        <button class="layui-btn layui-btn-sm bdc-delete-btn" type="button" lay-event="delzhs">删除</button>
    </div>
</script>
<script type="text/html" id="fwzhsListToolBarTmpl">
    <div class="layui-btn-container">
        <span class="layui-btn layui-btn-xs bdc-major-btn" lay-event="updatezhs">更新</span>
    </div>
</script>
<script src="../js/fwZhs/fwzhsList.js"></script>
</body>

</html>
