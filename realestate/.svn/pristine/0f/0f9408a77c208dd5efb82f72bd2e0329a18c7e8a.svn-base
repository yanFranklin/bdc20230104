<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>宗地查询列表</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../lib/js/jquerysession.js?v=02"></script>
    <script src="../js/common.js?v=1.0045"></script>
    <script src="../js/mask.js"></script>
    <script src="../js/redirect.js?v=2019-03-06 15:33"></script>
    <link rel="stylesheet" href="../lib/bdcui/css/table.css">
    <link rel="stylesheet" href="../lib/bdcui/css/search.css?3">
    <link rel="stylesheet" href="../css/building.css?33">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css"/>
    <@glo.globalVars />
</head>
<body>
<div class="building-outer-container">
    <!--表单模块-->
    <div class="bdc-search-box">
        <form class="layui-form" lay-filter="form">
            <div class="layui-form-item pf-form-item">
                <div class="layui-inline layui-hide">
                    <label class="layui-form-label">地籍号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="djh" class="layui-input" placeholder="请输入">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">不动产单元号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="bdcdyh" class="layui-input" placeholder="请输入">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">坐落</label>
                    <div class="layui-input-inline">
                        <input type="text" name="tdzl" class="layui-input" placeholder="请输入">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">权利人</label>
                    <div class="layui-input-inline">
                        <input type="text" name="qlr" class="layui-input" placeholder="请输入">
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
        <table id="zdList" lay-data="{id: 'zdList'}" lay-filter="dataTable"></table>
    </div>
</div>
<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm bdc-table-second-btn" lay-event="addLjz">新增逻辑幢</button>
        <button class="layui-btn layui-btn-sm bdc-table-second-btn" lay-event="addXmxx">新增项目信息</button>
    </div>
</script>
<script type="text/html" id="zdListToolBarTmpl">
    <div class="layui-btn-container">
        <span class="layui-btn layui-btn-xs bdc-table-second-btn" lay-event="viewLpb">楼盘表</span>
        <span class="layui-btn layui-btn-xs bdc-table-second-btn" lay-event="xmxxList">项目信息</span>
    </div>
</script>
<script src="../lib/bdcui/js/table.js?v=2.0"></script>
<script src="../js/zd/zdList.js?3"></script>
</body>

</html>
