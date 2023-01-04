<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>分摊土地面积计算</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/common.css">
    <link rel="stylesheet" href="../lib/bdcui/css/form.css">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <link rel="stylesheet" href="../lib/bdcui/css/form-tab.css?v=1.003"/>
    <link rel="stylesheet" href="../css/building.css?33">
    <link rel="stylesheet" href="../css/pop.css">
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js"></script>
</head>
<body>
<div id="bdc-popup-large">
    <form class="layui-form setOverflow" lay-filter="form">
        <div class="form-margin-area building-lpb-fttdmj">
            <div class="layui-form-item layui-hide">
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width">fwDcbIndex</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="fwDcbIndex" id="fwDcbIndex"
                               value="${fwDcbIndex!}">
                        <input type="text" class="layui-input" name="zdh" id="zdh"
                               value="${djh!}">
                        <input type="text" class="layui-input" name="qjgldm" id="qjgldm"
                               value="${qjgldm!}">
                    </div>
                </div>
            </div>
            <div class="basic-info">
                <div class="layui-form-item">
                    <div class="layui-inline margin-top-ipt bdc-two-column">
                        <label class="layui-form-label">计算规则</label>
                        <div class="layui-input-inline">
                            <input type="checkbox" class="layui-input" name="zhs" value="zhs" title="计算子户室建筑面积"/>
                            <input type="checkbox" class="layui-input" name="dxs" value="dxs" title="计算地下室"/>
                        </div>
                    </div>
                </div>

                <label class="layui-form-label">计算公式</label>
                <div class="layui-input-inline">
                    <table border="0" class="building-fttdmj-table">
                        <tr>
                            <td rowspan="3"><input type="radio" class="layui-input" name="jsgsxh" value="2"
                                                   lay-filter="jsgsxh" checked/></td>
                            <td rowspan="4" width="15">(</td>
                            <td align="center" colspan="4">户室建筑面积</td>
                            <td rowspan="4" width="15" align="right">)</td>
                            <td rowspan="4">*</td>
                            <td rowspan="4" width="10">(</td>
                            <td rowspan="2">
                                <input type="radio" class="layui-input" name="mjlb" value="sc" lay-filter="mjlb"
                                       title="宗地内楼幢实测面积" checked/>
                            </td>
                            <td rowspan="4">)</td>

                        </tr>
                        <tr>
                            <td class="building-fttdmj-td" rowspan="3">宗地内楼幢总建筑面积</td>
                            <td class="building-fttdmj-td" rowspan="3">(</td>
                            <td class="building-fttdmj-td"><input type="radio" class="layui-input" name="zdnlzzjzmj"
                                                                  value="ljz"
                                                                  lay-filter="zdnlzzjzmj" title="逻辑幢(SCJZMJ)" checked/>
                            </td>
                            <td class="building-fttdmj-td" rowspan="3">)</td>
                        </tr>
                        <tr>
                            <td><input type="radio" class="layui-input" name="zdnlzzjzmj" value="zrz"
                                       lay-filter="zdnlzzjzmj" title="自然幢(ZDMJ*FWCS)"/></td>
                            <td rowspan="2">
                                <input type="radio" class="layui-input" name="mjlb" value="fz" lay-filter="mjlb"
                                       title="宗地内楼幢发证面积"/>
                            </td>
                        </tr>
                    </table>
                </div>


                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-inline">
                        <table border="0" class="building-fttdmj-table">
                            <tr>
                                <td rowspan="2"><input type="radio" class="layui-input" name="jsgsxh" value="3"
                                                       lay-filter="jsgsxh"/></td>
                                <td rowspan="2" width="20">(</td>
                                <td align="center">户室建筑面积</td>
                                <td rowspan="2" width="20" align="right">)</td>
                                <td rowspan="2">*</td>
                                <td rowspan="2">自然幢占地面积</td>
                            </tr>
                            <tr>
                                <td class="building-fttdmj-td">自然幢内楼幢总建筑面积</td>
                            </tr>
                        </table>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-inline">
                        <table border="0" class="building-fttdmj-table">
                            <tr>
                                <td><input type="radio" class="layui-input" name="jsgsxh" value="1"
                                           lay-filter="jsgsxh"/></td>
                                <td align="center">户室建筑面积</td>
                                <td>*</td>
                                <td><input type="text" class="layui-input" name="ftxs" placeholder="分摊系数"/></td>
                            </tr>
                        </table>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-inline">
                        <table border="0" class="building-fttdmj-table">
                            <tr>
                                <td rowspan="2"><input type="radio" class="layui-input" name="jsgsxh" value="4"
                                                       lay-filter="jsgsxh"/></td>
                                <td rowspan="2" width="20">(</td>
                                <td align="center">户室建筑面积</td>
                                <td rowspan="2" width="20" align="right">)</td>
                                </td>
                            </tr>
                            <tr>
                                <td class="building-fttdmj-td"><input type="text" class="layui-input" name="lcs"
                                                                      placeholder="楼层数"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="pannel-form-btns">
            <button class="layui-btn bdc-major-btn" lay-submit="" lay-filter="calculated"
                    id="calculated" type="button">计算
            </button>
            <button class="layui-btn  layui-btn-primary" lay-filter="closeBtn" onclick="closeWin();">取消</button>
        </div>
    </form>
</div>
<script src="../js/calculate/fttdmj.js"></script>
</body>
</html>
