<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <title>户室信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/common.css?v=1034">
    <link rel="stylesheet" href="../lib/bdcui/css/form.css?v=1.0.1">
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
    <script src="../js/redirect.js?v=2019-03-06"></script>
    <script src="../lib/bdcui/js/table.js?v=1.0"></script>
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
            <ul class="layui-tab-title">
                <li class="layui-this" id="hsTab">户室信息</li>
                <li id="dcxxTab">调查信息</li>
                <li id="qlrTab">房产权利人</li>
                <li id="zhsTab">子户室列表</li>
                <li id="hstTab">户室图</li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <div class="bdc-tab-btn">
                        <#--<button class="layui-btn layui-btn-sm bdc-secondary-btn" id="jcyyhs" type="button">-->
                            <#--继承已有户室-->
                        <#--</button>-->
                        <a href="javascript:;" id="jcyyhs" class="bdc-third-btn">继承已有户室</a>
                    </div>
                    <div class="form-margin-area">
                    <div class="basic-info">
                        <!--表单块的标题-->
                        <div class="layui-form-item layui-hide">
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">fwhsIndex</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="fwHsIndex" id="fwHsIndex"
                                           value="${fwHsIndex!}">
                                </div>
                                <label class="layui-form-label">fwDcbIndex</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="fwDcbIndex" id="fwDcbIndex"
                                           value="${fwDcbIndex!}">
                                    <input type="text" class="layui-input" name="storageUrl" id="storageUrl"
                                           value="${storageUrl!}">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline bdc-two-column layui-hide">
                                <label class="layui-form-label bdc-label-newline">不动产单元编号</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="bdcdyh" id="bdcdyh" disabled>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">房间号</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="fjh">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">室序号</label>
                                <div class="layui-input-inline">
                                    <input type="number" class="layui-input" name="sxh">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">物理层数</label>
                                <div class="layui-input-inline">
                                    <input type="number" class="layui-input" name="wlcs">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">定义层数</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="dycs">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">单元号</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="dyh">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">房屋编码</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="fwbm">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">层高</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="text" class="layui-input" name="cg"><span>m<span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">权利ID</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="qlid">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">共有土地面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="gytdmj">
                                    <span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">实测建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="scjzmj">
                                    <span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label bdc-label-newline">实测套内建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="sctnjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label bdc-label-newline">实测分摊建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="scftjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label bdc-label-newline">实测地下部分建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="scdxbfjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label bdc-label-newline">实测其它建筑面积</label>
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
                                <label class="layui-form-label">预测建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="ycjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label bdc-label-newline">预测套内建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="yctnjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label bdc-label-newline">预测分摊建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="ycftjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label bdc-label-newline">预测地下部分建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="ycdxbfjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label bdc-label-newline">预测其他建筑面积</label>
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
                                <label class="layui-form-label">分摊土地面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="fttdmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">独用土地面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="dytdmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">房屋用途</label>
                                <div class="layui-input-inline">
                                    <select name="ghyt" lay-search="" lay-filter="ghyt" class="SZdFwytDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">土地用途</label>
                                <div class="layui-input-inline">
                                    <select name="tdyt" lay-search="" lay-filter="tdyt" class="SZdDldmDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label bdc-label-newline ">土地使用权类型</label>
                                <div class="layui-input-inline">
                                    <select name="tdsyqlx" lay-search="" lay-filter="tdsyqlx" class="SZdTdsyqlxDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">起始日期</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" id="qsrq" name="qsrq">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">终止日期</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="zzrq" id="zzrq">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">房屋类型</label>
                                <div class="layui-input-inline">
                                    <select name="fwlx" lay-search="" lay-filter="fwlx" class="SZdFwlxDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">房屋性质</label>
                                <div class="layui-input-inline">
                                    <select name="fwxz" lay-search="" lay-filter="fwxz" class="SZdFwxzDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">交易价格</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="jyjg"><span>万元</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label bdc-label-newline ">建成时装修程度</label>
                                <div class="layui-input-inline">
                                    <select name="jczxcd" lay-search="" lay-filter="jczxcd" class="SZdJczxcdDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">房屋户型</label>
                                <div class="layui-input-inline">
                                    <select name="fwhx" lay-search="" lay-filter="fwhx" class="SZdFwhxDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">房屋结构</label>
                                <div class="layui-input-inline">
                                    <select name="hxjg" lay-search="" lay-filter="hxjg" class="SZdHxjgDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">是否附属设施</label>
                                <div class="layui-input-inline">
                                    <select name="isfsss" lay-search="" lay-filter="isfsss" class="SZdBoolDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline bdc-two-column">
                                <label class="layui-form-label ">产别</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="cb">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label bdc-label-newline ">参与分摊土地面积计算</label>
                                <div class="layui-input-inline">
                                    <select name="syfttdmjjs" lay-search="" lay-filter="b" class="SZdSyfttdmjjsDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline bdc-two-column">
                                <label class="layui-form-label">坐落</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="zl">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">墙体归属 东</label>
                                <div class="layui-input-inline">
                                    <select name="d" lay-search="" lay-filter="d" class="SZdQtgsDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">墙体归属 南</label>
                                <div class="layui-input-inline">
                                    <select name="n" lay-search="" lay-filter="n" class="SZdQtgsDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">墙体归属 西</label>
                                <div class="layui-input-inline">
                                    <select name="x" lay-search="" lay-filter="x" class="SZdQtgsDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">墙体归属 北</label>
                                <div class="layui-input-inline">
                                    <select name="b" lay-search="" lay-filter="b" class="SZdQtgsDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item change-textarea-margin">
                            <label class="layui-form-label ">备注</label>
                            <div class="layui-input-inline">
                                <textarea class="layui-textarea change-textarea-width" name="bz"></textarea>
                            </div>
                        </div>
                    </div>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <div class="basic-info" id="dcxxForm">
                        <div class="layui-form-item">
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">调查者</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="dcz" id="dcz">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label ">调查时间</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="dcsj" id="dcsj">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item change-textarea-margin">
                            <label class="layui-form-label ">产权来源</label>
                            <div class="layui-input-inline">
                                <textarea class="layui-textarea change-textarea-width" name="cqly"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item change-textarea-margin">
                            <label class="layui-form-label ">共有情况</label>
                            <div class="layui-input-inline">
                                <textarea class="layui-textarea change-textarea-width" name="gyqk"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item change-textarea-margin">
                            <label class="layui-form-label ">附加说明</label>
                            <div class="layui-input-inline">
                                <textarea class="layui-textarea change-textarea-width" name="fjsm"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item change-textarea-margin">
                            <label class="layui-form-label ">调查意见</label>
                            <div class="layui-input-inline">
                                <textarea class="layui-textarea change-textarea-width" name="dcyj"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <div class="bdc-tab-btn">
                        <#--<button class="layui-btn layui-btn-sm bdc-secondary-btn" id="addQlr" type="button">新增权利人-->
                        <#--</button>-->
                        <a href="javascript:;" id="addQlr" class="bdc-third-btn">新增权利人</a>
                    </div>
                    <div class="basic-info" id="qlrForm">
                        <div class="form-margin-area">
                            <div class="basic-info">
                                <!--表单块的标题-->
                                <input type="text" class="layui-input layui-hide" name="fwIndex" id="fwIndex"
                                       value="${fwHsIndex!}">

                                <div class="layui-collapse" lay-filter="test" id="qlrList">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <div class="bdc-tab-btn">
                    </div>
                    <div class="bdc-table-box">
                        <table id="fwzhsList" lay-data="{id: 'fwzhsList'}" lay-filter="dataTable"></table>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <div class="bdc-tab-btn">
                        <#--<button class="layui-btn layui-btn-sm  bdc-major-btn layui-hide" id="downHst" type="button">下载-->
                        <#--</button>-->
                        <#--<button class="layui-btn layui-btn-sm  bdc-delete-btn layui-hide" id="deletHst" type="button">-->
                            <#--删除-->
                        <#--</button>-->
                        <a href="javascript:;" id="downHst" class="bdc-third-btn layui-hide">下载</a>
                        <a href="javascript:;" id="deletHst" class="bdc-third-btn layui-hide">删除</a>
                    </div>
                    <div class="bdc-upload-dragdiv">
                        <div class="layui-upload-drag img-drag" id="hst">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
<script type="text/html" id="uploadTpl">
    {{# if(d.srcUrl){ }}
    <img id="img" class="upload-img" src="{{d.srcUrl}}" alt="">
    {{# }else{ }}
    <div class="upload-icon">
        <i class="layui-icon">&#xe654;</i>
        <span>上传户室图</span>
    </div>
    <h4>点击/拖拽单个文件到这里上传</h4>
    <p>支持jpeg、jpg、png格式，大小在10M以下</p>
    <img id="img" class="upload-img" src="" alt="">
    <div class="video-icon"></div>
    {{# } }}
</script>
<script type="text/html" id="qlrTpl">
    {{# layui.each(d.data, function(index, item){ }}
    {{# var length=d.start+index }}
    <div class="layui-colla-item qlrdiv">
        <div class="layui-colla-title">
            <span id="{{ item.fwFcqlrIndex || ''}}" class="layui-btn layui-btn-sm bdc-delete-btn colla-title-btn"
                  onclick="delQlr(this)">删除
            </span>
            <h3>{{ item.qlr || '权利人'}}</h3>
        </div>
        <div class="layui-colla-content layui-show">
            <div class="layui-form-item layui-hide">
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">权利人id</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].fwFcqlrIndex"
                               value="{{ item.fwFcqlrIndex || ''}}">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">权利人</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].qlr"
                               value="{{ item.qlr || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label bdc-label-newline">权利人证件类型</label>
                    <div class="layui-input-inline">
                        <select name="qlrList[{{length}}].qlrzjlx" lay-search="" lay-filter="zjlx">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdZjllxDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==item.qlrzjlx){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">权利人证件号</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].qlrzjh"
                               value="{{ item.qlrzjh || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">权利人序号</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].qlrbh" value="{{length+1}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">发证机关</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].fzjg"
                               value="{{ item.fzjg || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">性别</label>
                    <div class="layui-input-inline">
                        <select name="qlrList[{{length}}].xb" lay-search="" lay-filter="xb">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdQlrxbDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==item.xb){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">电话</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].dh" value="{{ item.dh || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">邮编</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].yb" value="{{ item.yb || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">工作单位</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].gzdw"
                               value="{{ item.gzdw || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">权利人性质</label>
                    <div class="layui-input-inline">
                        <select name="qlrList[{{length}}].qlrxz" lay-search="" lay-filter="qlrxz">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdQlrxzDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==item.qlrxz){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">共有方式</label>
                    <div class="layui-input-inline">
                        <select name="qlrList[{{length}}].gyfs" lay-search="" lay-filter="gyfs">
                            <option value="">请选择</option>
                            {{# layui.each(d.zdList.SZdGyfsDO, function(index, zdItem){ }}
                            {{# if(zdItem.DM==item.gyfs){ }}
                            <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                            {{# }else{ }}
                            <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                            {{# } }}
                            {{# }); }}
                        </select>
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">权利比例</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].qlbl"
                               value="{{ item.qlbl || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">权利面积</label>
                    <div class="layui-input-inline">
                        <input type="number" class="layui-input" name="qlrList[{{length}}].qlmj"
                               value="{{ item.qlmj || ''}}">
                    </div>
                </div>
                <div class="layui-inline bdc-two-column">
                    <label class="layui-form-label ">地址</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].dz" value="{{ item.dz || ''}}">
                    </div>
                </div>
            </div>
            <div class="layui-form-item change-textarea-margin">
                <label class="layui-form-label ">备注</label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea" name="qlrList[{{length}}].bz">{{item.bz || ''}}</textarea>
                </div>
            </div>
        </div>
    </div>
    {{# }); }}
</script>
<script type="text/html" id="DmMcTpl">
    {{# layui.each(d, function(index, zdItem){ }}
    <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
    {{# }); }}
</script>
<script type="text/html" id="toolbar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm bdc-major-btn" type="button" lay-event="addzhs">添加</button>
        <button class="layui-btn layui-btn-sm bdc-delete-btn" type="button" lay-event="delzhs">删除</button>
    </div>
</script>
<script type="text/html" id="fwzhsListToolBarTmpl">
    <div class="layui-btn-container">
        <span class="layui-btn layui-btn-xs bdc-major-btn" lay-event="updatezhs">更新</span>
    </div>
</script>
<script src="../js/fwhs/fwhsForm.js"></script>
</html>