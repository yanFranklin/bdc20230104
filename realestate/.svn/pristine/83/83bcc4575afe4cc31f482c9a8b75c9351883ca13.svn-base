<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>户室信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/common.css?v=1034">
    <link rel="stylesheet" href="../lib/bdcui/css/search.css"/>
    <link rel="stylesheet" href="../lib/bdcui/css/form.css?v=1.003"/>
    <link rel="stylesheet" href="../lib/bdcui/css/form-tab.css?v=1.003"/>
    <link rel="stylesheet" href="../lib/bdcui/css/table.css">
    <link rel="stylesheet" href="../css/upload.css?v=1.1.2">
    <link rel="stylesheet" href="../css/building.css?v=1.1.2">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/bdcui/js/form-tab.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../lib/js/jquerysession.js?v=01"></script>
    <script src="../js/common.js?v=1.0045"></script>
    <script src="../js/redirect.js?v=2019-03-27"></script>
    <script src="../lib/bdcui/js/table.js?v=1.0"></script>
    <script src="../js/fwhs/hsbg.js?v=1.1.2562"></script>
    <@glo.globalVars />
</head>
<body>
<div class="bdc-form-div">
    <form class="layui-form setOverflow" lay-filter="form">
        <div class="bdc-content-fix">
            <div class="content-title layui-clear">
                <div class="title-btn-area">
                    <button class="layui-btn bdc-major-btn" lay-submit="" id="saveForm" lay-filter="saveForm">提交
                    </button>
                    <button class="layui-btn bdc-secondary-btn lpb-back-btn" type="button">返回</button>
                </div>
            </div>
        </div>
        <div class="layui-tab" lay-filter="tabFilter">
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <div class="basic-info">
                        <div class="layui-form-item layui-hide">
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width">fwhsIndex</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="fwHsIndex" id="fwHsIndex"
                                           value="${fwHsIndex!}">
                                </div>
                            </div>
                            <label class="layui-form-label change-label-width">fwDcbIndex</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" name="fwDcbIndex" id="fwDcbIndex"
                                       value="${fwDcbIndex!}">
                                <input type="text" class="layui-input" name="bgbh" id="bgbh"
                                       value="${bgbh!}">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <!--表单块的标题-->
                            <div class="layui-inline bdc-two-column layui-hide">
                                <label class="layui-form-label change-label-width bdc-label-newline">不动产单元编号</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="bdcdyh" id="bdcdyh" disabled>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">房间号</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="fjh">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">室序号</label>
                                <div class="layui-input-inline">
                                    <input type="number" class="layui-input" name="sxh">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">物理层数</label>
                                <div class="layui-input-inline">
                                    <input type="number" class="layui-input" name="wlcs">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">定义层数</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="dycs">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">单元号</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="dyh">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">房屋编码</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="fwbm">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">层高</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="text" class="layui-input" name="cg"><span>m</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">权利ID</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="qlid">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width">共有土地面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="gytdmj">
                                    <span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width">实测建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="scjzmj">
                                    <span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width bdc-label-newline">实测套内建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="sctnjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width bdc-label-newline">实测分摊建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="scftjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width bdc-label-newline">实测地下部分建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="scdxbfjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width bdc-label-newline">实测其它建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="scqtjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">实测分摊系数</label>
                                <div class="layui-input-inline">
                                    <input type="number" class="layui-input" name="scftxs">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width">预测建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="ycjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width bdc-label-newline">预测套内建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="yctnjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width bdc-label-newline">预测分摊建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="ycftjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width bdc-label-newline">预测地下部分建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="ycdxbfjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width bdc-label-newline">预测其他建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="ycqtjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">预测分摊系数</label>
                                <div class="layui-input-inline">
                                    <input type="number" class="layui-input" name="ycftxs">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width">分摊土地面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="fttdmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width">独用土地面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="dytdmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">房屋用途</label>
                                <div class="layui-input-inline">
                                    <select name="ghyt" lay-search="" lay-filter="ghyt" class="SZdFwytDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">土地用途</label>
                                <div class="layui-input-inline">
                                    <select name="tdyt" lay-search="" lay-filter="tdyt" class="SZdDldmDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width bdc-label-newline">土地使用权类型</label>
                                <div class="layui-input-inline">
                                    <select name="tdsyqlx" lay-search="" lay-filter="tdsyqlx" class="SZdTdsyqlxDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">起始日期</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" id="qsrq" name="qsrq">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">终止日期</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="zzrq" id="zzrq">
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
                                <label class="layui-form-label change-label-width">交易价格</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="jyjg"><span>万元</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width bdc-label-newline">建成时装修程度</label>
                                <div class="layui-input-inline">
                                    <select name="jczxcd" lay-search="" lay-filter="jczxcd" class="SZdJczxcdDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">房屋户型</label>
                                <div class="layui-input-inline">
                                    <select name="fwhx" lay-search="" lay-filter="fwhx" class="SZdFwhxDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">房屋结构</label>
                                <div class="layui-input-inline">
                                    <select name="hxjg" lay-search="" lay-filter="hxjg" class="SZdHxjgDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width bdc-label-newline">是否附属设施</label>
                                <div class="layui-input-inline">
                                    <select name="isfsss" lay-search="" lay-filter="isfsss" class="SZdBoolDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">产别</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="cb">
                                </div>
                            </div>
                            <div class="layui-inline bdc-two-column">
                                <label class="layui-form-label">坐落</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="zl">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">墙体归属 东</label>
                                <div class="layui-input-inline">
                                    <select name="d" lay-search="" lay-filter="d" class="SZdQtgsDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">墙体归属 南</label>
                                <div class="layui-input-inline">
                                    <select name="n" lay-search="" lay-filter="n" class="SZdQtgsDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">墙体归属 西</label>
                                <div class="layui-input-inline">
                                    <select name="x" lay-search="" lay-filter="x" class="SZdQtgsDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">墙体归属 北</label>
                                <div class="layui-input-inline">
                                    <select name="b" lay-search="" lay-filter="b" class="SZdQtgsDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item change-textarea-margin">
                            <label class="layui-form-label">备注</label>
                            <div class="layui-input-inline">
                                <textarea class="layui-textarea change-textarea-width" name="bz"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script type="text/html" id="DmMcTpl">
    {{# layui.each(d, function(index, zdItem){ }}  ·
    <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
    {{# }); }}
</script>
<script src="../lib/bdcui/js/form.js"></script>
<script src="../js/fwhs/fwhsbg.js?3"></script>
</body>

</html>
