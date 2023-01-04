<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>项目基本信息变更</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/common.css">
    <link rel="stylesheet" href="../lib/bdcui/css/form.css?4">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <link rel="stylesheet" href="../css/building.css?3">
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js?v=1.0"></script>
    <script src="../lib/js/jquerysession.js?v=01"></script>
    <script src="../js/redirect.js?v=2019-03-05"></script>
    <script src="../js/xmbg.js?v=1.1.11"></script>
    <@glo.globalVars />
</head>
<body>
<div class="bdc-form-div building-padding-ifteen">
    <form class="layui-form setOverflow" lay-filter="form">
        <div class="content-title layui-clear">
            <div class="title-btn-area">
                <button class="layui-btn bdc-major-btn" lay-submit="" id="saveInfo" lay-filter="saveInfo"
                        type="button">提交
                </button>
                <button class="layui-btn bdc-secondary-btn lpb-back-btn" type="button">返回</button>
            </div>
        </div>
        <div class="form-margin-area">
            <div class="basic-info">
                <div class="layui-form-item layui-hide">
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label">fwXmxxIndex</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="fwXmxxIndex" id="fwXmxxIndex"
                                   value="${fwXmxxIndex!}">
                            <input type="text" class="layui-input" name="bgbh" id="bgbh"
                                   value="${bgbh!}">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label">隶属宗地</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" readonly name="lszd" id="lszd"
                                   value="${lszd!}">
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt bdc-two-column">
                        <label class="layui-form-label">不动产单元号</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" readonly name="bdcdyh" id="bdcdyh">
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width">不动产状态</label>
                        <div class="layui-input-inline">
                            <select name="bdczt" lay-search="" lay-filter="bdczt">
                                <option value="1">可用</option>
                                <option value="0">不可用</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline bdc-two-column">
                        <label class="layui-form-label">项目名称</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="xmmc">
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label">房屋类型</label>
                        <div class="layui-input-inline">
                            <select name="fwlx" lay-search="" lay-filter="fwlx" class="SZdFwlxDO">
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label">房屋性质</label>
                        <div class="layui-input-inline">
                            <select name="fwxz" lay-search="" lay-filter="fwxz" class="SZdFwxzDO">
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label">交易价格</label>
                        <div class="layui-input-inline">
                            <input type="number" class="layui-input" name="jyjg">
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width">独用土地面积</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="dytdmj"><span>m²</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width">分摊土地面积</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="fttdmj"><span>m²</span>
                        </div>
                    </div>
                    <div class="layui-inline bdc-two-column">
                        <label class="layui-form-label">坐落</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="zl">
                        </div>
                    </div>
                    <div class="layui-inline bdc-two-column">
                        <label class="layui-form-label">产别</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="cb">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline bdc-two-column">
                        <label class="layui-form-label">备注</label>
                        <div class="layui-input-inline">
                            <textarea class="layui-textarea change-textarea-width" name="bz"></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script type="text/html" id="DmMcTpl">
    {{# layui.each(d, function(index, zdItem){ }}
    <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
    {{# }); }}
</script>
<script src="../lib/bdcui/js/form.js"></script>
<script src="../js/xmxx/fwXmxxInfo.js"></script>
</body>

</html>
