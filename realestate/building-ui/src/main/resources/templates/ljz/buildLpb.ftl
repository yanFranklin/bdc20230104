<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>构建楼盘表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/popup.css">
    <link rel="stylesheet" href="../css/pop.css">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js?v=1.0"></script>
</head>
<body>
<div id="bdc-popup-large">
    <form class="layui-form" lay-filter="form">
        <div class="layui-form-item pf-form-item">
            <div class="layui-form-item layui-hide">
                <input type="text" class="layui-input" name="fwDcbIndex" value="${fwDcbIndex!}"/>
                <input type="text" class="layui-input" name="bdcdyfwlx" value="${bdcdyfwlx!}"/>
                <input type="text" class="layui-input" name="qjgldm" value="${qjgldm!}"/>
            </div>
            <div class="basic-info" id="dtgjDiv">
                <div class="layui-form-item layui-show">
                    <div class="layui-inline">
                        <label class="layui-form-label bdc-label-newline">构建方式</label>
                        <div class="layui-input-inline">
                            <select name="gjfs" id="gjfs" lay-search="" lay-filter="gjfs">
                                <option value="0">请选择</option>
                                <option value="1">无单元按户构建</option>
                                <option value="2">按单元户数平均构建</option>
                                <option value="3">按单元户数动态构建</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label bdc-label-newline">层数</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="cs" />
                        </div>
                    </div>
                </div>
                <div class="layui-form-item layui-show wdygj">
                    <div class="layui-inline">
                        <label class="layui-form-label bdc-label-newline">每层户数</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="mchs" />
                        </div>
                    </div>
                </div>
                <div class="layui-form-item layui-show pjgj">
                    <div class="layui-inline">
                        <label class="layui-form-label bdc-label-newline">单元数</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="dys" />
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label bdc-label-newline">每层户数</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="dymshs" />
                        </div>
                    </div>
                </div>
                <div class="layui-form-item layui-show dtgj">
                    <input type="text" class="layui-input layui-hide hsdtgj" name="hsdtgj[0]" />
                    <div class="layui-inline">
                        <label class="layui-form-label bdc-label-newline">单元号</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input hsdtgj-dyh" name="dtgj[0].dyh" />
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label bdc-label-newline">户数</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input hsdtgj-hs" name="dtgj[0].hs" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="pannel-form-btns">
            <button class="layui-btn bdc-major-btn" lay-submit=""  lay-filter="buildBtn"
                    id="buildBtn" type="button">提交</button>
            <button class="layui-btn bdc-secondary-btn dtgj" id="dtgjAdd" type="button">增加单元</button>
        </div>
    </form>
</div>
<script type="text/html" id="dtgjTpl">
    {{# layui.each(d.data, function(index, item){ }}
    {{# var length=d.start+index }}
    <div class="layui-form-item layui-show dtgj">
        <input type="text" class="layui-input layui-hide hsdtgj" name="hsdtgj[{{length}}]" />
        <div class="layui-inline">
            <label class="layui-form-label change-label-width">单元号</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input hsdtgj-dyh" name="dtgj[{{length}}].dyh" />
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label change-label-width">户数</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input hsdtgj-hs" name="dtgj[{{length}}].hs" />
            </div>
        </div>
    </div>
    {{# }); }}
</script>
<script src="../js/fwljz/buildLpb.js"></script>
</body>
</html>
