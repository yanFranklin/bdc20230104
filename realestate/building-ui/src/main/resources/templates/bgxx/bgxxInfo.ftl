<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>变更信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/common.css?v=1034">
    <link rel="stylesheet" href="../lib/bdcui/css/form.css?v=12.1">
    <link rel="stylesheet" href="../lib/bdcui/css/search.css"/>
    <link rel="stylesheet" href="../lib/bdcui/css/form-tab.css?v=1.003"/>
    <link rel="stylesheet" href="../lib/bdcui/css/table.css">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js"></script>
    <script src="../lib/js/jquerysession.js?v=01"></script>
    <@glo.globalVars />
</head>
<body>
<div class="bdc-form-div">
<form class="layui-form setOverflow" lay-filter="form">
    <div class="bdc-content-fix">
        <div class="content-title layui-clear" id="buttons">
            <div class="title-btn-area">
                <button class="layui-btn bdc-major-btn" lay-submit="" id="saveBgxx" lay-filter="saveBgxx"
                        type="button">提交
                </button>
                <button class="layui-btn bdc-secondary-btn lpb-back-btn" type="button">返回</button>
            </div>
        </div>
    </div>
    <div class="form-margin-area">
        <div class="basic-info">
            <!-- 填报字段 -->
            <div class="layui-form-item layui-hide">
                <input type="text" class="layui-input" name="fwHsIndex" id="fwHsIndex"
                       value="${fwHsBgxxDO.getFwHsIndex()!}">
                <input type="text" class="layui-input" name="hsBgIndex" id="hsBgIndex"
                       value="${fwHsBgxxDO.getHsBgIndex()!}">
                <input type="text" class="layui-input" name="bgbh" id="bgbh"
                       value="${fwHsBgxxDO.getBgbh()!}">
                <input type="text" class="layui-input" name="bglx" id="bglx"
                       value="${fwHsBgxxDO.getBglx()!}">
            </div>
            <div class="layui-form-item">
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">开发企业名称</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="kfqymc" id="kfqymc"
                               value="${fwHsBgxxDO.getKfqymc()!}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">坐落</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="zl" id="zl" value="${fwHsBgxxDO.getZl()!}">
                    </div>
                </div>
            </div>
            <div class="layui-form-item change-textarea-margin">
                <label class="layui-form-label change-label-width">变更明细</label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea change-textarea-width" name="bgmx"></textarea>
                </div>
            </div>
            <div class="layui-form-item change-textarea-margin">
                <label class="layui-form-label change-label-width">测绘意见</label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea change-textarea-width" name="chshyj"></textarea>
                </div>
            </div>
            <div class="layui-form-item change-textarea-margin">
                <label class="layui-form-label change-label-width">市场科意见</label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea change-textarea-width" name="sckyj"></textarea>
                </div>
            </div>
            <div class="layui-form-item change-textarea-margin">
                <label class="layui-form-label change-label-width">分管领导意见</label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea change-textarea-width" name="fgldyj"></textarea>
                </div>
            </div>
            <div class="layui-form-item change-textarea-margin">
                <label class="layui-form-label change-label-width">主任审批意见</label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea change-textarea-width" name="zrspyj"></textarea>
                </div>
            </div>
            <div class="layui-form-item change-textarea-margin">
                <label class="layui-form-label change-label-width">备注</label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea change-textarea-width" name="bz"></textarea>
                </div>
            </div>
        </div>
    </div>
</form>
</div>
<script src="../js/bgxx/bgxxInfo.js"></script>
<script src="../js/redirect.js?v=2019-03-05"></script>
<script src="../js/fwhs/fwhsgl.js?v=1.1.11"></script>
<script src="../js/xmbg.js?v=1.1.11"></script>
</body>

</html>
