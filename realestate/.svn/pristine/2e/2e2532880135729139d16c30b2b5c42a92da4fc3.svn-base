<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>预测实测关联</title>
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
    <link rel="stylesheet" href="../css/ycscgl.css?v=1">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <link rel="stylesheet" href="../css/building.css?33">
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/bdcui/js/table.js"></script>
    <script src="../lib/js/jquerysession.js?v=01"></script>
    <script src="../js/redirect.js?v=2019-03-05"></script>
    <@glo.globalVars />
</head>
<body>
<div class="bdc-table-box">
    <div class="layui-fluid">
        <div class="layui-row">
            <div class="layui-col-md5 building-col-44">
                <div class="bdc-search-box">
                    <form class="layui-form" lay-filter="ycform">
                        <div class="layui-inline">
                            <label class="layui-form-label">预测房间号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="fjh" autocomplete="off" placeholder="请输入房间号"
                                       class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <a href="javascript:;" class="layui-btn layui-btn-normal bdc-major-btn" lay-submit=""
                               lay-filter="ycsearch" id="ycsearch">查询</a>
                            <button type="reset" class="layui-btn layui-btn-normal bdc-secondary-btn" id="ycreset">重置
                            </button>
                        </div>
                    </form>
                </div>
                <!--表格开始-->
                <div class="bdc-table-box">
                    <table id="fwychsTable" lay-data="{id: 'fwychsTable'}" lay-filter="fwychsTable"></table>
                </div>
            </div>
            <div class="layui-col-md2 building-col-12">
                <div class="demoTable">
                    <button class="layui-btn layui-btn-primary lpb-back-btn button-width-115" data-type="zzgl" id="zzgl"
                            type="button">返   回<i class="layui-icon"></i></button>
                    <button class="layui-btn layui-btn-primary button-width-115"  data-type="zzgl" id="zzgl"
                            type="button">整幢关联<i class="layui-icon"></i></button>
                    <button class="layui-btn layui-btn-primary button-width-115"  data-type="qxzzgl" id="qxzzgl"
                            type="button">取消整幢关联<i class="layui-icon"></i></button>
                    <button class="layui-btn layui-btn-primary" data-type="ycglsc" id="ycglsc"
                            type="button">关&emsp;&emsp;联<i class="layui-icon"></i></button>
                    <button class="layui-btn layui-btn-primary" data-type="qxgl" id="qxgl"
                            type="button"><i class="layui-icon"></i>取消关联
                    </button>
                    <button class="layui-btn layui-btn-primary" data-type="ckglschs" id="ckglschs"
                            type="button">查看关联<i class="layui-icon"></i></button>
                    <button class="layui-btn layui-btn-primary" data-type="ckglychs" id="ckglychs"
                            type="button"><i class="layui-icon"></i>查看关联
                    </button>
                </div>
            </div>
            <div class="layui-col-md5 building-col-44">
                <div class="bdc-search-box">
                    <form class="layui-form" lay-filter="scform">
                        <div class="layui-inline">
                            <label class="layui-form-label">实测房间号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="fjh" autocomplete="off" placeholder="请输入房间号"
                                       class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <a href="javascript:;" class="layui-btn layui-btn-normal bdc-major-btn" lay-submit=""
                               lay-filter="scsearch" id="scsearch">查询</a>
                            <button type="reset" class="layui-btn layui-btn-normal bdc-secondary-btn" id="screset">重置
                            </button>
                        </div>
                    </form>
                </div>
                <div class="bdc-table-box">
                    <table id="fwhsTable" lay-data="{id: 'fwhsTable'}" lay-filter="fwhsTable"></table>
                </div>
            </div>
        </div>
    </div>
    <input type="text" class="layui-input layui-hide" name="fwDcbIndex" id="fwDcbIndex" value="${fwDcbIndex!}">
    <input type="text" class="layui-input layui-hide" name="qjgldm" id="qjgldm" value="${qjgldm!}">
</div>
<script src="../js/fwhs/ycscgl.js"></script>
</body>
</html>
