<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>户室基本信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/common.css?v=1034">
    <link rel="stylesheet" href="../lib/bdcui/css/search.css"/>
    <link rel="stylesheet" href="../lib/bdcui/css/form.css?v=1.003"/>
    <link rel="stylesheet" href="../lib/bdcui/css/form-tab.css?v=1.003"/>
    <link rel="stylesheet" href="../lib/bdcui/css/table.css">
    <link rel="stylesheet" href="../css/upload.css?v=1.1.2">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <link rel="stylesheet" href="../css/building.css?v=2019-03-191406">
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js?v=1.0"></script>
</head>
<body>
<div class="bdc-form-div building-notitle">
    <form class="layui-form setOverflow" lay-filter="form">
        <div class="form-margin-area">
            <div class="basic-info">
                <!--表单块的标题-->
                <div class="layui-form-item layui-hide">
                    <input type="text" class="layui-input" name="fwHsIndex" id="fwHsIndex"
                           value="${fwHsIndex!}" readonly>
                    <input type="text" class="layui-input" name="fwDcbIndex" id="fwDcbIndex"
                           value="${fwDcbIndex!}" readonly>
                    <input type="text" class="layui-input" name="last" id="last"
                           value="${last!}">
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline bdc-two-column">
                        <label class="layui-form-label change-label-width bdc-label-newline">不动产单元编号</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="bdcdyh" readonly>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">房间号</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="fjh" readonly>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">室序号</label>
                        <div class="layui-input-inline">
                            <input type="number" class="layui-input" name="sxh" readonly>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">物理层数</label>
                        <div class="layui-input-inline">
                            <input type="number" class="layui-input" name="wlcs" readonly>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">定义层数</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="dycs" readonly>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">单元号</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="dyh" readonly>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">房屋编码</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="fwbm" readonly>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label">层高</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="text" class="layui-input" name="cg" readonly><span>m</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">权利ID</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="qlid" readonly>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width">共有土地面积</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="gytdmj" readonly>
                            <span>m²</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width">实测建筑面积</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="scjzmj" readonly>
                            <span>m²</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width bdc-label-newline">实测套内建筑面积</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="sctnjzmj" readonly><span>m²</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width bdc-label-newline">实测分摊建筑面积</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="scftjzmj" readonly><span>m²</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width bdc-label-newline">实测地下部分建筑面积</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="scdxbfjzmj" readonly><span>m²</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width bdc-label-newline">实测其它建筑面积</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="scqtjzmj" readonly><span>m²</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label">实测分摊系数</label>
                        <div class="layui-input-inline">
                            <input type="number" class="layui-input" name="scftxs" readonly>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width">预测建筑面积</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="ycjzmj" readonly><span>m²</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width bdc-label-newline">预测套内建筑面积</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="yctnjzmj" readonly><span>m²</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width bdc-label-newline">预测分摊建筑面积</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="ycftjzmj" readonly><span>m²</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width bdc-label-newline">预测地下部分建筑面积</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="ycdxbfjzmj" readonly><span>m²</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width bdc-label-newline">预测其他建筑面积</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="ycqtjzmj" readonly><span>m²</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">预测分摊系数</label>
                        <div class="layui-input-inline">
                            <input type="number" class="layui-input" name="ycftxs" readonly>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width">分摊土地面积</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="fttdmj" readonly><span>m²</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width">独用土地面积</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="dytdmj" readonly><span>m²</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">房屋用途</label>
                        <div class="layui-input-inline">
                            <select name="ghyt" lay-search="" lay-filter="ghyt" class="SZdFwytDO" disabled>
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">土地用途</label>
                        <div class="layui-input-inline">
                            <select name="tdyt" lay-search="" lay-filter="tdyt" class="SZdDldmDO" disabled>
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width bdc-label-newline">土地使用权类型</label>
                        <div class="layui-input-inline">
                            <select name="tdsyqlx" lay-search="" lay-filter="tdsyqlx" class="SZdTdsyqlxDO" disabled>
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">起始日期</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" id="qsrq" name="qsrq" disabled>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">终止日期</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="zzrq" id="zzrq" disabled>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">房屋类型</label>
                        <div class="layui-input-inline">
                            <select name="fwlx" lay-search="" lay-filter="fwlx" class="SZdFwlxDO" disabled>
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">房屋性质</label>
                        <div class="layui-input-inline">
                            <select name="fwxz" lay-search="" lay-filter="fwxz" class="SZdFwxzDO" disabled>
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label">交易价格</label>
                        <div class="layui-input-inline bdc-one-icon">
                            <input type="number" class="layui-input" name="jyjg" readonly><span>万元</span>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label change-label-width bdc-label-newline">建成时装修程度</label>
                        <div class="layui-input-inline">
                            <select name="jczxcd" lay-search="" lay-filter="jczxcd" class="SZdJczxcdDO" disabled>
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">房屋户型</label>
                        <div class="layui-input-inline">
                            <select name="fwhx" lay-search="" lay-filter="fwhx" class="SZdFwhxDO" disabled>
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">房屋结构</label>
                        <div class="layui-input-inline">
                            <select name="hxjg" lay-search="" lay-filter="hxjg" class="SZdHxjgDO" disabled>
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">是否附属设施</label>
                        <div class="layui-input-inline">
                            <select name="isfsss" lay-search="" lay-filter="isfsss" class="SZdBoolDO" disabled>
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">产别</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="cb" readonly>
                        </div>
                    </div>
                    <div class="layui-inline bdc-two-column">
                        <label class="layui-form-label">坐落</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="zl" readonly>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">墙体归属 东</label>
                        <div class="layui-input-inline">
                            <select name="d" lay-search="" lay-filter="d" class="SZdQtgsDO" disabled>
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">墙体归属 南</label>
                        <div class="layui-input-inline">
                            <select name="n" lay-search="" lay-filter="n" class="SZdQtgsDO" disabled>
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">墙体归属 西</label>
                        <div class="layui-input-inline">
                            <select name="x" lay-search="" lay-filter="x" class="SZdQtgsDO" disabled>
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label ">墙体归属 北</label>
                        <div class="layui-input-inline">
                            <select name="b" lay-search="" lay-filter="b" class="SZdQtgsDO" disabled>
                                <option value="">请选择</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item change-textarea-margin">
                    <label class="layui-form-label ">备注</label>
                    <div class="layui-input-inline">
                        <textarea class="layui-textarea change-textarea-width" name="bz" readonly></textarea>
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
<script src="../js/fwhs/historyHs.js"></script>
</body>

</html>
