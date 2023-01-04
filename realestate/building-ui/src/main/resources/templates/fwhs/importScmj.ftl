<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>导入实测面积</title>
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
    <form class="layui-form setOverflow" lay-filter="form">
        <div class="form-margin-area">
            <div class="basic-info">
                <div class="layui-form-item pf-form-item">
                    <table class="layui-table" lay-skin="nob">
                        <colgroup>
                            <col width="180px">
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
                            <td class="form-tb-lable">实测面积EXCEL文件</td>
                            <td>
                                <button type="button" class="layui-btn bdc-secondary-btn" id="fileUpload">选择文件</button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="building-importscmj-button">
            <button type="button" class="layui-btn bdc-major-btn" id="submit">开始上传</button>
            <button class="layui-btn  layui-btn-primary" lay-filter="closeBtn" onclick="closeWin();">取消</button>
        </div>
    </form>
</div>
<script src="../lib/bdcui/js/form.js"></script>
<script src="../js/fwhs/importScmj.js"></script>
</body>

</html>
