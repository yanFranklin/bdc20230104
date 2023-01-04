<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>逻辑幢查询列表</title>
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
    <script src="../js/fwhs/fwhsgl.js?v=1.1"></script>
    <script src="../js/fwljz/ljzgl.js?v=1.1"></script>
    <script src="../js/gxhpz.js"></script>
    <@glo.globalVars />
</head>
<body>
<div class="building-outer-container">
    <div class="bdc-search-box">
        <!--表单模块-->
        <form class="layui-form" lay-filter="form">
            <div class="layui-form-item pf-form-item">
                <div class="layui-inline qjgldm" style="display: none">
                    <label class="layui-form-label">地区</label>
                    <div class="layui-input-inline">
                        <select name="qjgldm" id="qjgldm" lay-filter="qjgldm">
                        </select>
                        <i class="layui-icon layui-icon-close bdc-hide"></i>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">地籍号</label>
                    <div class="layui-input-inline">
                        <input type="hidden" id="moduleCode" value="${moduleCode!}">
                        <input type="text" name="lszd" class="layui-input" placeholder="请输入">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">房屋名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="fwmc" class="layui-input" placeholder="请输入">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">自然幢号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="zrzh" class="layui-input" placeholder="请输入">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">逻辑幢号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="ljzh" class="layui-input" placeholder="请输入">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">坐落</label>
                    <div class="layui-input-inline">
                        <input type="text" name="zldz" class="layui-input" placeholder="请输入">
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
        <table id="ljzList" lay-data="{id: 'ljzList'}" lay-filter="dataTable"></table>
    </div>
</div>
<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm bdc-delete-btn" lay-event="delEvent" id="delEvent">删除</button>
        <#--<button class="layui-btn layui-btn-sm bdc-major-btn" lay-event="addHs">新增户室</button>-->
        <#--<button class="layui-btn layui-btn-sm bdc-table-second-btn" lay-event="addYchs" id="addYchs">新增预测户室</button>-->
        <button class="layui-btn layui-btn-sm bdc-table-second-btn" lay-event="importLpb" id="importLpb">导入实测楼盘表
        </button>
        <button class="layui-btn layui-btn-sm bdc-table-second-btn" lay-event="importYcLpb" id="importYcLpb">导入预测楼盘表
        </button>
        <button class="layui-btn layui-btn-sm bdc-table-second-btn" lay-event="exportLpb" id="exportLpb">导出楼盘表</button>
        <button class="layui-btn layui-btn-sm bdc-table-second-btn" lay-event="importScjzmj" id="importScjzmj">导入实测面积
        </button>
    </div>
</script>
<script>
    var sfyc = ${sfyc?c};
</script>
<script type="text/html" id="ljzListToolBarTmpl">
    <div class="layui-btn-container">
        <span class="layui-btn layui-btn-xs  bdc-secondary-btn lpb" lay-event="toLpbView" id="lpb">楼盘表</span>
        <a class="layui-btn layui-btn-xs bdc-secondary-btn bdc-more-btn">
            更多<i class="layui-icon layui-icon-down"></i>
        </a>
        <div class="bdc-table-btn-more">
            <a lay-event="hbfh" class="hbfh">户室合并</a>
            <a lay-event="buildLpb" class="buildLpb">构建楼盘表</a>
            <a lay-event="fwlxBg" class="fwlxBg">房屋类型变更</a>
            <a lay-event="ycToSc" class="ycToSc">预测转实测</a>
            <a lay-event="ycScGl" class="ycScGl">预测实测关联</a>
            <a lay-event="zlsx" class="zlsx">刷新户室坐落</a>
            <a lay-event="jsjzmj" class="jsjzmj">计算建筑面积</a>
            <a lay-event="jsfttdmj" class="jsfttdmj">计算分摊土地面积</a>
            <a lay-event="txdw" class="txdw">图形定位</a>
            <a lay-event="djdcb" class="djdcb">地籍调查表</a>
            <a lay-event="tsycljz" class="tsycljz">推送预测逻辑幢</a>
        </div>
    </div>
</script>
<script src="../lib/bdcui/js/table.js?v1"></script>
<script src="../js/fwljz/queryLjzList.js"></script>
<script src="../js/common.js"></script>
</body>
</html>
