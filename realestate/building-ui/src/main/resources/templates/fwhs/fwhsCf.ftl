<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>户室拆分</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/search.css?v=1.0.1">
    <link rel="stylesheet" href="../lib/bdcui/css/common.css?2">
    <link rel="stylesheet" href="../lib/bdcui/css/form.css">
    <link rel="stylesheet" href="../css/zdtree.css?v=1.0.7">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js"></script>
    <script src="../lib/js/jquerysession.js?v=01"></script>
    <script src="../js/redirect.js?v=2019-03-05"></script>
    <script src="../js/fwhs/fwhsgl.js?v=1.1.11"></script>
    <script src="../js/fwhs/hsbg.js?v=1.1.11"></script>
    <link rel="stylesheet" href="../css/building.css?2333">
    <@glo.globalVars />
</head>
<body>
<div class="content-main bdc-form-div building-background-regrey building-cf-padding">
<div class="building-background-write">
    <div>
        <form class="layui-form setOverflow" lay-filter="form">
            <input type="text" class="layui-input layui-hide" name="fwHsIndex" id="fwHsIndex"
                   value="${fwHsIndex!}" />
            <input type="text" class="layui-input layui-hide" name="bgbh" id="bgbh"
                   value="${bgbh!}" />
            <div class="layui-form-item pf-form-item">
                <table class="layui-table" lay-skin="nob">
                    <colgroup>
                        <col width="10%">
                        <col width="20%">
                        <col width="10%">
                        <col width="20%">
                        <col>
                    </colgroup>
                    <tbody>
                    <tr>
                        <td class="form-tb-lable">
                            拆分方向:
                        </td>
                        <td>
                            <input type="radio" name="cffx" value="横向" title="横向" checked="">
                            <input type="radio" name="cffx" value="纵向" title="纵向">
                        </td>
                        <td class="form-tb-lable">
                            拆分户室数量:
                        </td>
                        <td>
                            <input type="number" name="cfhsnum" lay-verify="required" autocomplete="off"
                                   class="layui-input">
                        </td>
                        <td>
                            <button class="layui-btn bdc-secondary-btn" lay-submit
                                    lay-filter="cfbtn" id="cfbtn"
                                    type="button">拆分
                            </button>
                            <button class="layui-btn bdc-major-btn" lay-submit=""
                                    id="sureCfhs" lay-filter="sureCfhs"
                                    type="button">提交
                            </button>
                            <button class="layui-btn bdc-secondary-btn lpb-back-btn" type="button">返回</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </form>
        <div id="fjhList" class="building-align-center">

        </div>
    </div>
    <hr class="layui-bg-blue">
    <form class="layui-form setOverflow" lay-filter="form">
        <button class="layui-btn layui-hide" lay-submit lay-filter="saveFwhss" id="saveFwhss"
                type="button">提交多个房屋户室
        </button>
        <div class="layui-tab layui-tab-brief" lay-filter="tab">
            <ul class="layui-tab-title layui-hide" id="fjhLi">

            </ul>
            <div class="layui-tab-content" id="fjhTab">
            </div>
        </div>
    </form>
</div>
</div>
<script type="text/html" id="cfHsFjhTpl">
    {{# layui.each(d, function(index, item){ }}
    <a class="layui-btn layui-btn-primary" href="javascript:;" onclick="reloadNowFwHsInfo({{item.fjh}},{{index}})">房间号:{{item.fjh}}</a>
    {{# }); }}
</script>
<script type="text/html" id="fjhLiTpl">
    <li lay-id='{{d.fjh}}' id='{{d.fjh}}'>{{d.fjh}}</li>
</script>
<script type="text/html" id="fjhTabTpl">
    <div class="layui-tab-item" id="tab{{d.fjh}}">
        <div class="basic-info">
            <!--表单块的标题-->
            <div class="layui-form-item layui-hide">
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width">fwDcbIndex</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="fwHsList[{{d.length}}].fwDcbIndex" id="fwDcbIndex"
                               value="{{ d.fwhsInfo.fwDcbIndex || ''}}">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">房间号</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="fwHsList[{{d.length}}].fjh" value="{{d.fjh}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">室序号</label>
                    <div class="layui-input-inline">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].sxh"
                               value="{{ d.fwhsInfo.sxh || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">物理层数</label>
                    <div class="layui-input-inline">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].wlcs"
                               value="{{ d.fwhsInfo.wlcs || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">定义层数</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="fwHsList[{{d.length}}].dycs"
                               value="{{ d.fwhsInfo.dycs || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">单元号</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="fwHsList[{{d.length}}].dyh"
                               value="{{ d.fwhsInfo.dyh || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">产别</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="fwHsList[{{d.length}}].cb"
                               value="{{ d.fwhsInfo.cb || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">层高</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="text" class="layui-input" name="fwHsList[{{d.length}}].cg"
                               value="{{ d.fwhsInfo.cg || ''}}"><span>m</span>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">权利ID</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="fwHsList[{{d.length}}].qlid"
                               value="{{ d.fwhsInfo.qlid || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width">共有土地面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].gytdmj"
                               value="{{ d.fwhsInfo.gytdmj || ''}}"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width">实测建筑面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].scjzmj"
                               value="{{ d.fwhsInfo.scjzmj || ''}}"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width bdc-label-newline">实测套内建筑面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].sctnjzmj"
                               value="{{ d.fwhsInfo.sctnjzmj || ''}}"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width bdc-label-newline">实测分摊建筑面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].scftjzmj"
                               value="{{ d.fwhsInfo.scftjzmj || ''}}"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width bdc-label-newline">实测地下部分建筑面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].scdxbfjzmj"
                               value="{{ d.fwhsInfo.scdxbfjzmj || ''}}"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width bdc-label-newline">实测其它建筑面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].scqtjzmj"
                               value="{{ d.fwhsInfo.scqtjzmj || ''}}"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">实测分摊系数</label>
                    <div class="layui-input-inline">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].scftxs"
                               value="{{ d.fwhsInfo.scftxs || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width">预测建筑面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].ycjzmj"
                               value="{{ d.fwhsInfo.ycjzmj || ''}}"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width bdc-label-newline">预测套内建筑面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].yctnjzmj"
                               value="{{ d.fwhsInfo.yctnjzmj || ''}}"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width bdc-label-newline">预测分摊建筑面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].ycftjzmj"
                               value="{{ d.fwhsInfo.ycftjzmj || ''}}"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width bdc-label-newline">预测地下部分建筑面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].ycdxbfjzmj"
                               value="{{ d.fwhsInfo.ycdxbfjzmj || ''}}"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width bdc-label-newline">预测其他建筑面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].ycqtjzmj"
                               value="{{ d.fwhsInfo.ycqtjzmj || ''}}"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">预测分摊系数</label>
                    <div class="layui-input-inline">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].ycftxs"
                               value="{{ d.fwhsInfo.ycftxs || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width">分摊土地面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].fttdmj"
                               value="{{ d.fwhsInfo.fttdmj || ''}}"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width">独用土地面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].dytdmj"
                               value="{{ d.fwhsInfo.dytdmj || ''}}"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">房屋用途</label>
                    <div class="layui-input-inline">
                        <select name="fwHsList[{{d.length}}].ghyt" lay-search="" lay-filter="ghyt" class="SZdFwytDO">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdFwytDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==d.fwhsInfo.ghyt){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">土地用途</label>
                    <div class="layui-input-inline">
                        <select name="fwHsList[{{d.length}}].tdyt" lay-search="" lay-filter="tdyt" class="SZdDldmDO">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdDldmDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==d.fwhsInfo.tdyt){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width bdc-label-newline">土地使用权类型</label>
                    <div class="layui-input-inline">
                        <select name="fwHsList[{{d.length}}].tdsyqlx" lay-search="" lay-filter="tdsyqlx"
                                class="SZdTdsyqlxDO">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdTdsyqlxDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==d.fwhsInfo.tdsyqlx){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">起始日期</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" id="qsrq{{d.length}}" name="fwHsList[{{d.length}}].qsrq"
                               value="{{ d.fwhsInfo.qsrq || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">终止日期</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="fwHsList[{{d.length}}].zzrq" id="zzrq{{d.length}}"
                               class="zzrq"
                               value="{{ d.fwhsInfo.zzrq || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">房屋类型</label>
                    <div class="layui-input-inline">
                        <select name="fwHsList[{{d.length}}].fwlx" lay-search="" lay-filter="fwlx" class="SZdFwlxDO">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdFwlxDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==d.fwhsInfo.fwlx){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">房屋性质</label>
                    <div class="layui-input-inline">
                        <select name="fwHsList[{{d.length}}].fwxz" lay-search="" lay-filter="fwxz" class="SZdFwxzDO">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdFwxzDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==d.fwhsInfo.fwxz){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width">交易价格</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].jyjg"
                               value="{{ d.fwhsInfo.jyjg || ''}}"><span>万元</span>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width bdc-label-newline">建成时装修程度</label>
                    <div class="layui-input-inline">
                        <select name="fwHsList[{{d.length}}].jczxcd" lay-search="" lay-filter="jczxcd"
                                class="SZdJczxcdDO">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdJczxcdDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==d.fwhsInfo.jczxcd){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">房屋户型</label>
                    <div class="layui-input-inline">
                        <select name="fwHsList[{{d.length}}].fwhx" lay-search="" lay-filter="fwhx" class="SZdFwhxDO">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdFwhxDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==d.fwhsInfo.fwhx){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">房屋结构</label>
                    <div class="layui-input-inline">
                        <select name="fwHsList[{{d.length}}].hxjg" lay-search="" lay-filter="hxjg" class="SZdHxjgDO">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdHxjgDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==d.fwhsInfo.hxjg){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">是否附属设施</label>
                    <div class="layui-input-inline">
                        <select name="fwHsList[{{d.length}}].isfsss" lay-search="" lay-filter="isfsss"
                                class="SZdBoolDO">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdBoolDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==d.fwhsInfo.isfsss){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">墙体归属 东</label>
                    <div class="layui-input-inline">
                        <select name="fwHsList[{{d.length}}].d" lay-search="" lay-filter="d" class="SZdQtgsDO">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdQtgsDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==d.fwhsInfo.d){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">墙体归属 南</label>
                    <div class="layui-input-inline">
                        <select name="fwHsList[{{d.length}}].n" lay-search="" lay-filter="n" class="SZdQtgsDO">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdQtgsDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==d.fwhsInfo.n){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">墙体归属 西</label>
                    <div class="layui-input-inline">
                        <select name="fwHsList[{{d.length}}].x" lay-search="" lay-filter="x" class="SZdQtgsDO">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdQtgsDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==d.fwhsInfo.x){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">墙体归属 北</label>
                    <div class="layui-input-inline">
                        <select name="fwHsList[{{d.length}}].b" lay-search="" lay-filter="b" class="SZdQtgsDO">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdQtgsDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==d.fwhsInfo.b){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">合并方向</label>
                    <div class="layui-input-inline">
                        <select name="fwHsList[{{d.length}}].hbfx" lay-search="" lay-filter="hbfx" class="SZdHbfxDO">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdHbfxDO, function(index, zdItem){ }}
                            {{# if( $(".layui-form-radioed").prev().val()=="横向" && zdItem.DM=="2"){ }}
                            {{# console.info("-----横向2") }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else if($(".layui-form-radioed").prev().val()=="纵向" && zdItem.DM=="3"){ }}
                            {{# console.info("-----纵向3") }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">合并户数</label>
                    <div class="layui-input-inline">
                        <input type="number" class="layui-input" name="fwHsList[{{d.length}}].hbhs"
                               value="{{ d.fwhsInfo.hbhs || ''}}">
                    </div>
                </div>
                <div class="layui-inline bdc-two-column">
                    <label class="layui-form-label">坐落</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input"name="fwHsList[{{d.length}}].zl"
                               value="{{ d.fwhsInfo.zl || ''}}">
                    </div>
                </div>
                <div class="layui-inline bdc-two-column">
                    <label class="layui-form-label">产别</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input"name="fwHsList[{{d.length}}].cb"
                               value="{{ d.fwhsInfo.cb || ''}}">
                    </div>
                </div>
            </div>
            <div class="layui-form-item change-textarea-margin">
                <label class="layui-form-label change-label-width">备注</label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea change-textarea-width" name="fwHsList[{{d.length}}].bz"
                              value="{{ d.fwhsInfo.bz || ''}}"></textarea>
                </div>
            </div>
        </div>
    </div>
</script>
<script src="../js/fwhs/fwhsCf.js"></script>
</body>
</html>
