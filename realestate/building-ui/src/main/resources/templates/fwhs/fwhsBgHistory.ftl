<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>户室历史变更记录</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/common.css">
    <link rel="stylesheet" href="../css/pop.css">
    <link rel="stylesheet" href="../css/building.css?33">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css"/>
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js?v=1.1"></script>
    <script src="../js/fwhsBgHistory.js?v=1.112"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/dist/echarts.min.js"></script>
</head>
<body class="building-background-write">
<input type="text" class="layui-input layui-hide" name="fwHsIndex" id="fwHsIndex"
       value="${fwHsIndex!}">
<div id="relationMap"></div>
<div class="pannel-form-btns building-margin_zero">
    <button class="layui-btn bdc-major-btn" id="revokeBg" type="button">撤销变更</button>
    <button class="layui-btn  layui-btn-primary" lay-filter="closeBtn" onclick="closeWin();">取消</button>
</div>
<script src="../js/fwhs/fwhsBgHistory.js"></script>
</body>