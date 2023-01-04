<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>逻辑幢建筑面积计算</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/common.css">
    <link rel="stylesheet" href="../lib/bdcui/css/form.css">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <link rel="stylesheet" href="../lib/bdcui/css/form-tab.css?v=1.003"/>
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js"></script>
</head>
<body>
<div class="bdc-form-div">
    <form class="layui-form setOverflow" lay-filter="form">
        <div class="bdc-content-fix">
        <div class="content-title layui-clear">
            <p>逻辑幢建筑面积计算</p>
            <div class="title-btn-area">
                <button class="layui-btn bdc-secondary-btn" id="chooseZd" type="button">选择宗地</button>
                <button class="layui-btn bdc-secondary-btn" id="chooseLjz" type="button">选择逻辑幢</button>
                <button class="layui-btn bdc-major-btn" lay-submit="" lay-filter="calculated"
                        id="calculated" type="button">计算</button>
            </div>
        </div>
        </div>
        <div class="form-margin-area building-table-area">
            <div class="layui-form-item layui-hide">
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width">fwDcbIndex</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="fwDcbIndex" id="fwDcbIndex"
                               value="">
                    </div>
                </div>
            </div>
            <div class="basic-info">
                <div class="layui-form-item">
                    <div class="layui-inline margin-top-ipt bdc-two-column">
                        <label class="layui-form-label">宗地号</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="zdh" id="zdh" readonly>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline margin-top-ipt bdc-two-column">
                        <label class="layui-form-label">逻辑幢</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" id="fwmc" readonly>
                        </div>
                    </div>
                </div>

                <div class="layui-form-item layui-hide">
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label">面积类别</label>
                        <div class="layui-input-inline">
                            <input type="radio" class="layui-input" name="mjlb" value="sc" title="实测" checked/>
                            <input type="radio" class="layui-input" name="mjlb" value="yc" title="预测"/>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline margin-top-ipt bdc-two-column">
                        <label class="layui-form-label">计算规则</label>
                        <div class="layui-input-inline">
                            <input type="checkbox" class="layui-input" name="zhs" value="zhs" title="计算子户室建筑面积"/>
                            <input type="checkbox" class="layui-input" name="dxs" value="dxs" title="计算地下室"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script src="../js/calculate/ljzJzmj.js"></script>
</body>

</html>
