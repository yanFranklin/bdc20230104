<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>导入楼盘表</title>
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
                            <td class="form-tb-lable">是否覆盖已有户室数据</td>
                            <td>
                                <input type="radio" name="fgyyhs" value="false" title="不覆盖" checked="">
                                <input type="radio" name="fgyyhs" value="true" title="覆盖">
                            </td>
                        </tr>
                        <tr>
                            <td class="form-tb-lable">楼盘表EXCEL文件</td>
                            <td>
                                <button type="button" class="layui-btn bdc-secondary-btn" id="fileUpload">选择文件</button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="building-importlpb-button">
            <button type="button" class="layui-btn bdc-major-btn" id="submit">开始上传</button>
            <button type="button" class="layui-btn bdc-secondary-btn" id="downtemp">模板下载</button>
        </div>
    </form>
</div>
<script src="../lib/bdcui/js/form.js"></script>
<script src="../js/fwhs/importYcLpb.js"></script>
</body>

</html>
