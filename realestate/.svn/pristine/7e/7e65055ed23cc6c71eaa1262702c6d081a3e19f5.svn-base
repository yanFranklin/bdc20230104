<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>整幢关联</title>
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
    <form class="layui-form" lay-filter="form">
        <div class="form-margin-area">
            <div class="basic-info">
                <div class="layui-form-item pf-form-item">
                    <table class="layui-table" lay-skin="nob">
                        <colgroup>
                            <col width="30%">
                            <col>
                        </colgroup>
                        <tbody>
                        <tr>
                            <input type="text" class="layui-input layui-hide" name="fwDcbIndex" id="fwDcbIndex"
                                   value="${fwDcbIndex!}"/>
                            <input type="text" class="layui-input layui-hide" name="qjgldm" id="qjgldm"
                                   value="${qjgldm!}"/>
                        </tr>
                        <tr>
                            <td class="form-tb-lable building-tb-lable" ">关联条件</td>
                            <td>
                                <div class="building-width-180">
                                    <select name="glgxType" lay-search="" lay-filter="glgxType">
                                        <option value="1">房屋编码</option>
                                        <option value="2">单元号物理层数房间号</option>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tb-lable building-tb-lable">是否重新关联</td>
                            <td>
                                <input type="radio" name="cxgl" value="1" title="是" checked="" class="change-radio-top">
                                <input type="radio" name="cxgl" value="0" title="否" class="change-radio-top">
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="pannel-form-btns">
            <button class="layui-btn bdc-major-btn" lay-submit="" id="saveForm" lay-filter="saveForm">提交</button>
            <button class="layui-btn layui-btn-primary" lay-filter="closeBtn" onclick="closeWin();">取消</button>
        </div>
    </form>
</div>
<script src="../lib/bdcui/js/form.js"></script>
<script src="../js/fwhs/ycscglZz.js?3"></script>
</body>

</html>
