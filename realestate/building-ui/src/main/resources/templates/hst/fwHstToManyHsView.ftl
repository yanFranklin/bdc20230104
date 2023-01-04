<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <title>户室图</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/common.css">
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js"></script>
    <link rel="stylesheet" href="../css/upload.css?v=1.3.7">
    <style>
        .building-body-height{
            height: 400px;
        }
        .bdc-upload-dragdiv{
            height: 400px;width: auto; margin: 0px 0
        }
    </style>
</head>
<body class="building-body-height">
<div class="bdc-upload-dragdiv">
    <!-- 图片上传开始 -->
    <div class="layui-upload-drag img-drag" id="hst">
    </div>

    <input type="text" class="layui-input layui-hide" name="storageUrl" id="storageUrl"
           value="${storageUrl!}">
</div>
<script type="text/html" id="uploadTpl">
    {{# if(d.srcUrl){ }}
    <img id="img" class="upload-img" src="{{d.srcUrl}}" alt="">
    {{# }else{ }}
    <div class="upload-icon">
        <i class="layui-icon">&#xe654;</i>
        <span>上传户室图</span>
    </div>
    <h4>点击/拖拽单个文件到这里上传</h4>
    <p>支持jpeg、jpg、png格式，大小在10M以下</p>
    <img id="img" class="upload-img" src="" alt="">
    {{# } }}
</script>
<script>
    var fwhsIndexList = [];
    <#if fwHsIndexList?? && (fwHsIndexList?size>0) >
        <#list fwHsIndexList as item >
        fwhsIndexList.push('${item}');
        </#list>
    </#if>
</script>
<script src="../js/fwhst/fwHstToManyHsView.js"></script>
</body>
</html>
