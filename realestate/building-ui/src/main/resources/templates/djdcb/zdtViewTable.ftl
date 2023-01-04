<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="../static/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="../static/lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../static/lib/bdcui/css/form.css"/>
    <link rel="stylesheet" href="../static/lib/bdcui/css/search.css"/>
    <link rel="stylesheet" href="../static/lib/bdcui/css/table.css"/>
    <link rel="stylesheet" href="../static/lib/bdcui/css/form-tab.css"/>
    <link rel="stylesheet" href="../static/lib/bdcui/css/mask.css">
    <link rel="stylesheet" href="../css/building.css">
    <link rel="stylesheet" href="../static/lib/form-select/formSelects-v4.css">
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../js/djdcb.js"></script>
    <script src="../js/mask.js"></script>
    <script src="../lib/bdcui/js/table.js?v=2.1"></script>
    <script src="../js/common.js?v=2.1"></script>
</head>
<body>
<div class="bdc-table-box" id="tuxknrList"></div>
<div class="bdc-upload-dragdiv" style="position: relative;left: 50%;top: 50%;transform: translate(-50%,-50%);width:100%;height:calc(100% - 40px);overflow:scroll;" id="zdtdiv">
    <div class="layui-upload-drag img-drag" id="zdtImg" style="padding: 0;border: none;width:100%;height:100%;">
    </div>
</div>
<div class="layui-form-item layui-hide">
    <input type="text" class="layui-input" name="bdcdyh" id="bdcdyh" value="${bdcdyh!}">
    <input type="text" class="layui-input" name="qjgldm" id="qjgldm" value="${qjgldm!}">
    <input type="text" class="layui-input" name="dzwtzm" id="dzwtzm" value="${dzwtzm!}">
    <input type="text" class="layui-input" name="cbzd" id="cbzd" value="${cbzd!}" />
    <input type="text" class="layui-input" name="jyq" id="jyq" value="${jyq!}" />
</div>
</body>
<script type="text/html" id="uploadTpl">
    {{# if(d.base64){ }}
    <img id="img" class="upload-img" style="max-width: 100%;overflow: scroll;" src="{{d.base64}}" alt="">
    {{# }else{ }}
    <div class="upload-icon">
        <i class="layui-icon">&#xe654;</i>
        <span>无宗地图</span>
    </div>
    <img id="img" class="upload-img" style="max-width: 900px" src="" alt="">
    <div class="video-icon"></div>
    {{# } }}
</script>
<script type="text/html" id="tuxknrListTpl">
    <table class="layui-table tuxknr-table-view" border="1" name="">
        <tbody>
        <tr class="gray-tr">
            <td width="40px">序号</td>
            <td width="700px">附件名称</td>
            <td width="100px">操作</td>
        </tr>
        {{# if(!d.data || d.data.length==0) { }}
        <tr class="bdc-table-none">
            <td colspan="3">
                <div class="layui-none">
                    <img src="../static/lib/bdcui/images/table-none.png" alt="">无数据</div>
            </td>
        </tr>
        {{# } else { }}
        {{# layui.each(d.data, function(index, item){ }}
        <tr>
            <td>{{ index+1 }}</td>
            <td>{{ item.tcm || ''}}</td>
            <td><span class="layui-btn layui-btn-xs bdc-secondary-btn" onclick="downTux('{{item.url||\'\'}}')">下载</span></td>
        </tr>
        {{# }); }}
        {{# } }}
        </tbody>
    </table>
</script>
<script src="../js/djdcb/zdtViewTable.js"></script>
<script src="../lib/bdcui/js/form.js"></script>
<script src="../js/form.js"></script>
</html>