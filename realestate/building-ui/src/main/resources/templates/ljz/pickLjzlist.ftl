<!DOCTYPE html>
<html lang="zh-cn" class="building-background-write">

<head>
    <meta charset="utf-8">
    <title>逻辑幢查询列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js?v=1.1"></script>
    <link rel="stylesheet" href="../lib/bdcui/css/table.css">
    <link rel="stylesheet" href="../lib/bdcui/css/search.css">
    <link rel="stylesheet" href="../css/building.css?33">
</head>
<body>
<div class="bdc-outer-container">
    <div class="bdc-search-box">
        <!--表单模块-->
        <form class="layui-form">
            <div id="queryDiv" class="layui-form-item pf-form-item layui-hide">
                <div class="layui-inline layui-hide">
                    <label class="layui-form-label building-lable-width80">隶属宗地</label>
                    <div class="layui-input-inline">
                        <input type="text" name="lszd" id="lszd" value="${lszd!}" placeholder="请输入隶属宗地"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline layui-hide">
                    <label class="layui-form-label building-lable-width80">自然幢号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="zrzh" id="zrzh" value="${zrzh!}" placeholder="请输入自然幢号"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline layui-hide">
                    <label class="layui-form-label building-lable-width80">坐落</label>
                    <div class="layui-input-inline">
                        <input type="text" name="zldz" id="zldz" placeholder="请输入坐落" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline layui-hide">
                    <label class="layui-form-label building-lable-width80">逻辑幢号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="ljzh" id="ljzh" placeholder="请输入逻辑幢号" class="layui-input">
                    </div>
                </div>
                <div id="btnDiv" class="layui-inline">
                    <button class="layui-btn layui-btn-normal bdc-major-btn" lay-submit="" id="query" lay-filter="query"
                            type="button">
                        查询
                    </button>
                    <button type="reset" class="layui-btn layui-btn-normal bdc-secondary-btn" id="reset">重置</button>
                </div>
            </div>
            <input type="text" class="layui-input layui-hide" name="fwDcbIndex" id="fwDcbIndex"
                   value="${fwDcbIndex!}">
            <input type="text" class="layui-input layui-hide" name="showQuery" id="showQuery"
                   value="${showQuery!}">
            <input type="text" class="layui-input layui-hide" name="qjgldm" id="qjgldm"
                   value="${qjgldm!}">
        </form>
    </div>
    <div class="bdc-table-box">
        <table id="ljzList" lay-data="{id: 'ljzList'}" lay-filter="dataTable"></table>
    </div>

</div>
<script src="../lib/bdcui/js/table.js"></script>
<script src="../js/fwljz/pickLjzlist.js"></script>
</body>

</html>
