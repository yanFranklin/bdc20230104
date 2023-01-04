<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>房屋户室查询列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/table.css?33">
    <link rel="stylesheet" href="../lib/bdcui/css/search.css?444">
    <link rel="stylesheet" href="../css/building.css?33">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css"/>
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../lib/js/jquerysession.js?v=01"></script>
    <script src="../js/redirect.js?v=2019-03-07"></script>
    <script src="../js/common.js?v=1.1"></script>
    <script src="../js/fwhs/fwhsgl.js?v=1.1.11"></script>
    <script src="../js/fwhs/hsbg.js?v=1.1.3e"></script>
    <@glo.globalVars />
</head>
<body>
<div class="building-outer-container">
    <div class="bdc-search-box">
        <!--表单模块-->
        <form class="layui-form" lay-filter="form">
            <div class="layui-form-item pf-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">不动产单元号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="bdcdyh" class="layui-input" placeholder="请输入">
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
        <table id="fwhsList" lay-data="{id: 'fwhsList'}" lay-filter="dataTable"></table>
    </div>
    <div class="layui-form-item layui-hide">
        <div id="hshb"></div>
    </div>
</div>
<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm bdc-delete-btn" lay-event="delete">删除</button>
    </div>
</script>
<script type="text/html" id="fwhsListToolBarTmpl">
    <div class="layui-btn-container">
        <span class="layui-btn layui-btn-xs bdc-secondary-btn" lay-event="fwhsHistory">历史关系</span>
    </div>
</script>
<script src="../lib/bdcui/js/table.js"></script>
<script src="../js/fwhs/fwhsList.js?d"></script>
</body>
</html>
