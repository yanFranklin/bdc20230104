<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>房屋属性修改</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/form.css">
    <link rel="stylesheet" href="../lib/bdcui/css/table.css">
    <link rel="stylesheet" href="../css/pop.css">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js?v=1.0"></script>
    <link rel="stylesheet" href="../css/building.css?33">
</head>
<body>
<div class="bdc-outer-container building-outer-container">
    <form class="layui-form setOverflow" lay-filter="form">
        <div class="form-margin-area">
            <div class="basic-info building-padding-zero">
                <table class="layui-table">
                    <colgroup>
                        <col width="32%">
                        <col width="29%">
                        <col width="39%">
                    </colgroup>
                    <thead>
                    <td colspan="3">
                        <input type="checkbox" id="replace" lay-filter="replace" lay-skin="primary" title="替换属性">
                    </td>
                    </thead>
                    <thead>
                    <td class="form-tb-lable" align="center">
                        <b>替换属性</b>
                    </td>
                    <td class="form-tb-lable" align="center">
                        <b>替换值</b>
                    </td>
                    <td class="form-tb-lable" align="center">
                        <b>目标值</b>
                    </td>
                    </thead>

                    <tbody>
                    <tr>
                        <td class="form-tb-lable">
                            <select id="replaceColumn" lay-search="">
                                <option value="">请选择属性</option>
                                <option value="zl">坐落</option>
                                <option value="ysdm">要素代码</option>
                                <option value="qlid">权利ID</option>
                                <option value="gyqk">共有情况</option>
                                <option value="fjsm">附加说明</option>
                                <option value="fjh">房间号</option>
                                <option value="fcdah">房产档案号</option>
                                <option value="dyh">单元号</option>
                                <option value="dycs">定义层数</option>
                                <option value="dcz">调查者</option>
                                <option value="dcyj">调查意见</option>
                                <option value="cqly">产权来源</option>
                                <option value="cb">产别</option>
                                <option value="bz">备注</option>
                            </select>
                        </td>
                        <td class="form-tb-lable">
                            <input type="text" id="replaceThz" class="layui-input" placeholder="请输入替换值">
                        </td>
                        <td>
                            <input type="text" id="replaceMbz" class="layui-input" placeholder="请输入目标值">
                        </td>
                    </tr>
                    </tbody>
                </table>

                <table class="layui-table" style="margin-top: 15px">
                    <colgroup>
                        <col width="3%">
                        <col width="27%">
                        <col width="30%">
                        <col width="40%">
                    </colgroup>
                    <thead>
                    <td colspan="4">
                        <input type="checkbox" lay-filter="updateNullOnly" lay-skin="primary" title="仅刷新空值">
                    </td>
                    </thead>
                    <thead>
                    <td class="form-tb-lable">
                        <input type="checkbox" lay-skin="primary" lay-filter="allChoose" />
                    </td>
                    <td class="form-tb-lable">
                        <center><b>枚举名称</b></center>
                    </td>
                    <td class="form-tb-lable">
                        <center><b>枚举字段</b></center>
                    </td>
                    <td class="form-tb-lable">
                        <center><b>枚举值</b></center>
                    </td>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <input type="checkbox" lay-filter="checkbox" columId="ghyt" class="checkbox" lay-skin="primary">
                        </td>
                        <td class="form-tb-lable">房屋用途</td>
                        <td class="form-tb-lable">GHYT</td>
                        <td>
                            <select id="ghyt" lay-filter="d" class="SZdFwytDO">
                                <option value="">请选择</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" lay-filter="checkbox" columId="fwhx" class="checkbox" lay-skin="primary">
                        </td>
                        <td class="form-tb-lable">房屋户型</td>
                        <td class="form-tb-lable">FWHX</td>
                        <td>
                            <select id="fwhx" lay-search="" lay-filter="d" class="SZdFwhxDO">
                                <option value="">请选择</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" lay-filter="checkbox" columId="tdyt" class="checkbox" lay-skin="primary">
                        </td>
                        <td class="form-tb-lable">土地用途</td>
                        <td class="form-tb-lable">TDYT</td>
                        <td>
                            <select id="tdyt" lay-search="" lay-filter="d" class="SZdDldmDO">
                                <option value="">请选择</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" lay-filter="checkbox" columId="hxjg" class="checkbox" lay-skin="primary">
                        </td>
                        <td class="form-tb-lable">户型结构</td>
                        <td class="form-tb-lable">HXJG</td>
                        <td>
                            <select id="hxjg" lay-search="" lay-filter="d" class="SZdHxjgDO">
                                <option value="">请选择</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" lay-filter="checkbox" columId="fwlx" class="checkbox" lay-skin="primary">
                        </td>
                        <td class="form-tb-lable">房屋类型</td>
                        <td class="form-tb-lable">FWLX</td>
                        <td>
                            <select id="fwlx" lay-search="" lay-filter="d" class="SZdFwlxDO">
                                <option value="">请选择</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" lay-filter="checkbox" columId="syfttdmjjs" class="checkbox" lay-skin="primary">
                        </td>
                        <td class="form-tb-lable">参与分摊计算</td>
                        <td class="form-tb-lable">SYFTTDMJJS</td>
                        <td>
                            <select id="syfttdmjjs" lay-search="" lay-filter="d" class="SZdSyfttdmjjsDO">
                                <option value="">请选择</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" lay-filter="checkbox" columId="jczxcd" class="checkbox" lay-skin="primary">
                        </td>
                        <td class="form-tb-lable">建成时装修程度</td>
                        <td class="form-tb-lable">JCZXCD</td>
                        <td>
                            <select id="jczxcd" lay-search="" lay-filter="d" class="SZdJczxcdDO">
                                <option value="">请选择</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" lay-filter="checkbox" columId="fwxz" class="checkbox" lay-skin="primary">
                        </td>
                        <td class="form-tb-lable">房屋性质</td>
                        <td class="form-tb-lable">FWXZ</td>
                        <td>
                            <select id="fwxz" lay-search="" lay-filter="d" class="SZdFwxzDO">
                                <option value="">请选择</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" lay-filter="checkbox" columId="tdsyqlx" class="checkbox" lay-skin="primary">
                        </td>
                        <td class="form-tb-lable">土地使用权类型</td>
                        <td class="form-tb-lable">TDSYQLX</td>
                        <td>
                            <select id="tdsyqlx" lay-search="" lay-filter="d" class="SZdTdsyqlxDO">
                                <option value="">请选择</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" lay-filter="checkbox" columId="isfsss" class="checkbox" lay-skin="primary">
                        </td>
                        <td class="form-tb-lable">是否附属设施</td>
                        <td class="form-tb-lable">ISFSSS</td>
                        <td>
                            <select id="isfsss" lay-search="" lay-filter="d" class="SZdBoolDO">
                                <option value="">请选择</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" lay-filter="checkbox" columId="dyh" class="checkbox" lay-skin="primary">
                        </td>
                        <td class="form-tb-lable">单元号</td>
                        <td class="form-tb-lable">DYH</td>
                        <td>
                            <input type="text" id="dyh" class="layui-input" placeholder="请输入单元号">
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="pannel-form-btns">
                <button class="layui-btn bdc-major-btn" lay-submit="" id="sxSubmit" lay-filter="sxSubmit"
                        type="button">提交
                </button>
                <button class="layui-btn  layui-btn-primary" lay-filter="closeBtn" onclick="closeWin();">取消</button>
            </div>
    </form>
</div>
<script src="../lib/bdcui/js/form.js"></script>
<script type="text/html" id="DmMcTpl">
    {{# layui.each(d, function(index, zdItem){ }}
    <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
    {{# }); }}
</script>
<script>
    var fwhsIndexList = [];
    <#if fwHsIndexList?? && (fwHsIndexList?size>0) >
        <#list fwHsIndexList as item >
            fwhsIndexList.push('${item}');
        </#list>
    </#if>
    var fwDcbIndex = '${fwDcbIndex!}';
</script>
<script src="../js/fwhs/fwhsPlgxSx.js"></script>
</body>

</html>
