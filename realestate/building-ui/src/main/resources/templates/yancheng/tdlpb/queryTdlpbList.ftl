<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>土地楼盘表查询列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <link rel="stylesheet" href="../css/building.css?333">
    <link rel="stylesheet" href="../lib/bdcui/css/table.css?23">
    <link rel="stylesheet" href="../lib/bdcui/css/search.css?24">
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../lib/js/jquerysession.js?v=02"></script>
    <script src="../js/redirect.js?v=2019-03-05 20:40"></script>
    <script src="../js/common.js?v=1.1"></script>
    <script src="../js/gxhpz.js"></script>
    <@glo.globalVars />
</head>
<body>
<div class="building-outer-container">
    <div class="bdc-search-box">
        <!--表单模块-->
        <form class="layui-form" lay-filter="form">
            <div class="layui-form-item pf-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">地籍号</label>
                    <div class="layui-input-inline">
                        <input type="hidden" id="moduleCode" value="${moduleCode!}">
                        <input type="text" name="djh" class="layui-input" placeholder="请输入">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">宗地权利人</label>
                    <div class="layui-input-inline">
                        <input type="text" name="zdqlr" class="layui-input" placeholder="请输入">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">坐落</label>
                    <div class="layui-input-inline">
                        <input type="text" name="zl" class="layui-input" placeholder="请输入">
                    </div>
                </div>
                <div class="layui-inline bdc-button-box">
                    <button class="layui-btn layui-btn-normal bdc-major-btn" lay-submit="" id="query" lay-filter="query"
                            type="button">
                        查询
                    </button>
                    <button type="reset" class="layui-btn layui-btn-normal bdc-secondary-btn" id="reset">重置</button>
                </div>
            </div>
        </form>
    </div>
    <div class="bdc-table-box">
        <table id="tdlpbList" lay-data="{id: 'tdlpbList'}" lay-filter="dataTable"></table>
    </div>
</div>
<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
    </div>
</script>

<script type="text/html" id="tdlpbListToolBarTmpl">
    <div class="layui-btn-container">
        <span class="layui-btn layui-btn-xs  bdc-secondary-btn lpb" lay-event="toLpbView" id="lpb">楼盘表</span>
    </div>
</script>
<script src="../lib/bdcui/js/table.js?v111"></script>
<script src="../js/yancheng/tdlpb/queryTdlpbList.js"></script>
<script src="../js/common.js"></script>
</body>
</html>
