<!DOCTYPE html>
<html lang="zh-cn"  class="building-background-write">

<head>
    <meta charset="utf-8">
    <title>自然幢查询列表</title>
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
            <div class="layui-form-item pf-form-item">
                <table class="layui-table" lay-skin="nob">
                    <colgroup>
                        <col width="12%">
                        <col width="26%">
                        <col width="12%">
                        <col width="26%">
                        <col>
                    </colgroup>
                    <tbody>
                    <tr>
                        <td class="form-tb-lable td-text-right">自然幢号:</td>
                        <td class="td-text-left">
                            <input type="text" name="zrzh" class="layui-input" placeholder="请输入自然幢号">
                        </td>
                        <td class="form-tb-lable td-text-right">坐落地址:</td>
                        <td class="td-text-left">
                            <input type="text" name="zldz" class="layui-input" placeholder="请输入坐落地址">
                        </td>
                        <td class="td-text-left">
                            <button class="layui-btn layui-btn-normal bdc-major-btn" lay-submit="" id="query"
                                    lay-filter="query"
                                    type="button">
                                查询
                            </button>
                            <button type="reset" class="layui-btn layui-btn-normal bdc-secondary-btn" id="reset">重置</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <input type="text" class="layui-input layui-hide" name="lszd" id="lszd"
                   value="${lszd!}">
        </form>
    </div>
    <div class="bdc-table-box">
        <table id="zrzList" lay-data="{id: 'zdList'}" lay-filter="dataTable"></table>
    </div>
</div>
<script src="../lib/bdcui/js/table.js"></script>
<script src="../js/zrz/pickZrzList.js"></script>
<script src="../js/lib/viewerjs/dist/viewer.js"></script>
</body>

</html>
