<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>不动产房屋类型变更</title>
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
    <script src="../js/common.js?v=1.0045"></script>
    <script src="../lib/js/jquerysession.js?v=01"></script>
    <script src="../js/redirect.js?v=2019-03-05"></script>
    <@glo.globalVars />
</head>
<body>
<div id="bdc-popup-small">
    <form class="layui-form" id="" lay-filter="">
        <div class="layui-form-item pf-form-item">
            <input type="text" name="fwXmxxIndex" id="fwXmxxIndex" value="${fwXmxxIndex!}"  class="layui-input layui-hide"/>
            <input type="text" name="fwDcbIndex" id="fwDcbIndex" value="${fwDcbIndex!}"  class="layui-input layui-hide"/>
            <input type="text" name="lszd" id="lszd" value="${lszd!}"  class="layui-input layui-hide"/>
            <input type="text" name="qjgldm" id="qjgldm" value="${qjgldm!}"  class="layui-input layui-hide"/>


            <div class="layui-inline">
                <label class="layui-form-label bdc-label-newline building-lable-width80">原类型</label>
                <div class="layui-input-inline">
                    <select name="yfwlx" lay-search="" disabled lay-filter="yfwlx" id="yfwlx"></select>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label bdc-label-newline building-lable-width80">变更后类型</label>
                <div class="layui-input-inline">
                    <select name="fwlx" lay-search="" lay-filter="fwlx" id="fwlx">
                        <option value="">请选择</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item change-textarea-margin">
                &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                <input type="checkbox" name="checkbox" id="jlbgjl" lay-skin="primary" title="记录变更历史记录">

            </div>
            <div class="building-bdcdyfwlx-button">
                <button class="layui-btn main-btn" lay-submit="" id="updateFwlx" lay-filter="updateFwlx"
                        type="button">修改
                </button>
                <button class="layui-btn  layui-btn-primary" lay-filter="closeBtn" onclick="closeWin();">取消</button>
            </div>
        </div>
    </form>
</div>


<script type="text/html" id="SZdBdcdyFwlxDOTpl">
    {{# layui.each(d, function(index, zdItem){ }}
    {{# if(zdItem.DM==${yfwlx!}){ }}
    <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
    {{# } }}
    {{# }); }}
</script>
<script type="text/html" id="SZdBdcdyFwlxDOTpl1">
    {{# layui.each(d, function(index, zdItem){ }}
    {{# if(zdItem.DM!=${yfwlx!}&&zdItem.DM!="3"){ }}
    <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
    {{# } }}
    {{# }); }}
</script>
<script src="../lib/bdcui/js/form.js"></script>
<script src="../js/fwlx/bdcdyFwlxBg.js"></script>
</body>
</html>
