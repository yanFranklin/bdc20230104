<!DOCTYPE html>
<html lang="zh-cn" class="building-background-write">

<head>
    <meta charset="utf-8">
    <title>已有预测户室列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js"></script>
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/search.css?v=1.0.8">
    <link rel="stylesheet" href="../lib/bdcui/css/table.css">
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
                        <col width="10%">
                        <col width="30%">
                        <col width="10%">
                        <col width="30%">
                        <col width="20%">
                    </colgroup>
                    <tbody>
                    <tr>
                        <td class="form-tb-lable">房间号:</td>
                        <td>
                            <input type="text" name="fjh" class="layui-input" autocomplete="off" placeholder="请输入房间号">
                        </td>
                        <td class="form-tb-lable">单元号:</td>
                        <td>
                            <input type="text" name="dyh" class="layui-input" autocomplete="off" placeholder="请输入单元号">
                        </td>
                        <td>
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

            <input type="text" class="layui-input layui-hide" name="fwDcbIndex" id="fwDcbIndex"
                   value="${fwDcbIndex!}">
        </form>
    </div>
    <div class="bdc-table-box">
        <table id="tableList" lay-data="{id: 'tableList'}" lay-filter="dataTable"></table>
    </div>
</div>
<script src="../lib/bdcui/js/table.js"></script>

<script src="../js/fwychs/fwychsListByljz.js"></script>
</body>

</html>
