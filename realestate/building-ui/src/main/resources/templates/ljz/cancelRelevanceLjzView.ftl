<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>不动产房屋类型</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/popup.css">
    <link rel="stylesheet" href="../css/pop.css">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <link rel="stylesheet" href="../css/building.css?33">
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js?v=1.0"></script>
</head>
<body>
<div id="bdc-popup-small">
    <form class="layui-form" lay-filter="">
        <div class="form-margin-area">
            <div class="layui-form-item pf-form-item">

                <div class="layui-inline">
                    <label class="layui-form-label building-lable-width80">房屋类型</label>
                    <div class="layui-input-inline">
                        <select name="bdcdyfwlx" lay-search="" lay-filter="fwlx" id="bdcdyfwlx">
                            <option value="">请选择</option>
                            <option value="2">独幢</option>
                            <option value="4">户</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="pannel-form-btns building-margin-top">
            <button class="layui-btn bdc-major-btn" lay-submit="" id="updateFwlx" lay-filter="updateFwlx"
                    type="button">修改
            </button>
            <button class="layui-btn  layui-btn-primary" lay-filter="closeBtn" onclick="closeWin();">取消</button>
        </div>
        <input type="text" class="layui-input layui-hide" name="fwDcbIndex" id="fwDcbIndex"
               value="${fwDcbIndex!}">

    </form>
</div>
<script src="../lib/bdcui/js/form.js"></script>
<script src="../js/fwljz/cancelRelevanceLjzView.js"></script>
</body>

</html>
