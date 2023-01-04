<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <title>逻辑幢信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/common.css?v=1034">
    <link rel="stylesheet" href="../lib/bdcui/css/form.css?v=1.0.13">
    <link rel="stylesheet" href="../lib/bdcui/css/search.css"/>
    <link rel="stylesheet" href="../lib/bdcui/css/form-tab.css?v=1.0033"/>
    <link rel="stylesheet" href="../lib/bdcui/css/table.css">
    <link rel="stylesheet" href="../css/upload.css?v=1.1.2">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <link rel="stylesheet" href="../css/building.css">
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/bdcui/js/form-tab.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../lib/js/jquerysession.js?v=01"></script>
    <script src="../js/common.js?v=1.0045"></script>
    <script src="../js/redirect.js?v=2019-03-05"></script>
    <script src="../lib/bdcui/js/table.js?v=1.0"></script>
    <@glo.globalVars />
</head>
<body>
<div class="bdc-form-div">
    <form class="layui-form setOverflow" lay-filter="form">
        <div class="bdc-content-fix">
            <div class="content-title layui-clear">
                <div class="title-btn-area">
                    <button class="layui-btn bdc-major-btn" lay-submit="" id="ljzForm" lay-filter="ljzForm">提交</button>
                    <button class="layui-btn bdc-secondary-btn lpb-back-btn" type="button">返回</button>
                    <button class="layui-btn bdc-secondary-btn" type="button" id="viewLpbBtn">查看楼盘表</button>
                </div>
            </div>
        </div>
        <div class="layui-tab" lay-filter="tabFilter">
            <ul class="layui-tab-title">
                <li class="layui-this">楼栋信息</li>
                <li class="layui-hide" id="slickDcxx">调查信息</li>
                <li class="layui-hide" id="slickQlr">房产权利人</li>
                <li class="layui-hide" id="slickPmt">平面图</li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <div class="bdc-tab-btn">
                        <#--<button class="layui-btn layui-btn-sm bdc-secondary-btn layui-hide" id="gjzd" type="button">-->
                            <#--挂接宗地-->
                        <#--</button>-->
                        <#--<button class="layui-btn layui-btn-sm bdc-secondary-btn layui-hide" id="qxgjzd" type="button">-->
                            <#--取消挂接宗地-->
                        <#--</button>-->
                        <#--<button class="layui-btn layui-btn-sm bdc-secondary-btn layui-hide" id="gjzrz" type="button">-->
                            <#--挂接自然幢-->
                        <#--</button>-->
                        <#--<button class="layui-btn layui-btn-sm bdc-secondary-btn" id="getLjzh" type="button">获取逻辑幢号-->
                        <#--</button>-->
                        <a href="javascript:;" id="gjzd" class="bdc-third-btn layui-hide">挂接宗地</a>
                        <a href="javascript:;" id="qxgjzd" class="bdc-third-btn layui-hide">取消挂接宗地</a>
                        <a href="javascript:;" id="gjzrz" class="bdc-third-btn layui-hide">挂接自然幢</a>
                        <a href="javascript:;" id="getLjzh" class="bdc-third-btn">获取逻辑幢号</a>
                    </div>
                    <div class="basic-info" id="ldxxFrom">
                        <#--<div class="title-sign"><a id="ldxx" href="javascript:;"></a></div>-->
                        <div class="layui-form-item layui-hide">
                            <input type="text" class="layui-input" id="fwDcbIndex" name="fwDcbIndex"
                                   value="${fwDcbIndex!}">
                            <input type="text" class="layui-input" id="fwXmxxIndex" name="fwXmxxIndex"
                                   value="${fwXmxxIndex!}">
                            <input type="text" class="layui-input" id="storageUrl" name="storageUrl"
                                   value="${storageUrl!}">
                            <input type="text" class="layui-input" id="xmBdcdyh" value="">
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">逻辑幢号</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" id="ljzh" name="ljzh">
                                </div>
                            </div>
                            <div class="layui-inline bdc-two-column">
                                <label class="layui-form-label">隶属宗地</label>
                                <div class="layui-input-inline">
                                    <input type="text" readonly id="lszd" class="layui-input" name="lszd"
                                           value="${djh!}">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">自然幢号</label>
                                <div class="layui-input-inline">
                                    <input type="text" readonly id="zrzh" class="layui-input" name="zrzh">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">房屋名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="fwmc">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width bdc-label-newline">不动产单元房屋类型</label>
                                <div class="layui-input-inline">
                                    <select name="bdcdyfwlx" id="bdcdyfwlx" lay-search="" lay-filter="bdcdyfwlx"
                                            class="SZdBdcdyFwlxDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">不动产状态</label>
                                <div class="layui-input-inline">
                                    <select name="bdczt" readonly lay-search="" lay-filter="bdczt">
                                        <option value="1">可用</option>
                                        <option value="0">不可用</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">幢号</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="dh">
                                </div>
                            </div>
                            <div class="layui-inline bdc-two-column layui-hide" id="bdcdyhFrom">
                                <label class="layui-form-label">不动产单元号</label>
                                <div class="layui-input-inline">
                                    <input type="text" readonly class="layui-input" id="bdcdyh" name="bdcdyh">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">房屋层数</label>
                                <div class="layui-input-inline">
                                    <input type="number" class="layui-input" name="fwcs">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">门牌号</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="mph">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">竣工日期</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" id="jgrq" name="jgrq">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">地上层数</label>
                                <div class="layui-input-inline">
                                    <input type="number" class="layui-input" name="dscs">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">地下层数</label>
                                <div class="layui-input-inline">
                                    <input type="number" class="layui-input" name="dxcs">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">占地面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="zzdmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width">实测建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="scjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width">实测其他面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="scqtmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width">实测地下面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="scdxmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width">预测建筑面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="ycjzmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width">预测其他面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="ycqtmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label change-label-width">预测地下面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="ycdxmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">用地面积</label>
                                <div class="layui-input-inline bdc-one-icon">
                                    <input type="number" class="layui-input" name="zydmj"><span>m²</span>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">房屋结构</label>
                                <div class="layui-input-inline">
                                    <select name="fwjg" lay-search="" lay-filter="fwjg" class="SZdFwjgDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">房屋用途</label>
                                <div class="layui-input-inline">
                                    <select name="fwyt" lay-search="" lay-filter="fwyt" class="SZdFwytDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">建筑物状态</label>
                                <div class="layui-input-inline">
                                    <select name="jzwzt" lay-search="" lay-filter="jzwzt" class="SZdJzwztDO">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">状态日期</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" id="ztrq" name="ztrq">
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
                            <div class="layui-inline">
                                <label class="layui-form-label">总套数</label>
                                <div class="layui-input-inline">
                                    <input type="number" class="layui-input" name="zts">
                                </div>
                            </div>
                            <div class="layui-inline bdc-two-column">
                                <label class="layui-form-label">坐落地址</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="zldz">
                                </div>
                            </div>
                            <div class="layui-inline bdc-two-column">
                                <label class="layui-form-label">产别</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="cb">
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
                <div class="layui-tab-item">
                    <div class="basic-info layui-hide" id="dcxxForm">
                        <#--<div class="title-sign"><a id="dcxx" href="javascript:;"></a></div>-->
                        <div class="layui-form-item layui-hide">
                            <input type="text" class="layui-input" name="fwIndex" id="fwIndex" value="${fwIndex!}">
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">调查者</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="dcz" id="dcz">
                                </div>
                            </div>
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">调查时间</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="dcsj" id="dcsj">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item change-textarea-margin">
                            <label class="layui-form-label">产权来源</label>
                            <div class="layui-input-inline">
                                <textarea class="layui-textarea change-textarea-width" name="cqly"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item change-textarea-margin">
                            <label class="layui-form-label">共有情况</label>
                            <div class="layui-input-inline">
                                <textarea class="layui-textarea change-textarea-width" name="gyqk"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item change-textarea-margin">
                            <label class="layui-form-label">附加说明</label>
                            <div class="layui-input-inline">
                                <textarea class="layui-textarea change-textarea-width" name="fjsm"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item change-textarea-margin">
                            <label class="layui-form-label">调查意见</label>
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
                    <div class="basic-info layui-hide" id="qlrForm">
                        <#--<div class="title-sign"><a id="qlrxx" href="javascript:;"></a></div>-->
                        <div class="form-margin-area">
                            <div class="basic-info">
                                <!--表单块的标题-->
                                <input type="text" class="layui-input layui-hide" name="fwIndex" id="fwIndex"
                                       value="${fwIndex!}">
                                <div class="layui-collapse" lay-filter="test" id="qlrList">
                                </div>
                            </div>
                        </div>
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
<script type="text/html" id="qlrTpl">
    {{# layui.each(d.data, function(index, item){ }}
    {{# var length=d.start+index }}
    <div class="layui-colla-item">
        <div class="layui-colla-title">
            <span id="{{ item.fwFcqlrIndex || ''}}" class="layui-btn layui-btn-sm bdc-delete-btn colla-title-btn"
                  onclick="delQlr(this)">删除
            </span>
            <h3>{{ item.qlr || '权利人'}}</h3>
        </div>
        <div class="layui-colla-content layui-show">
            <div class="layui-form-item layui-hide">
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">权利人id</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].fwFcqlrIndex"
                               value="{{ item.fwFcqlrIndex || ''}}">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">权利人</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].qlr"
                               value="{{ item.qlr || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width bdc-label-newline">权利人证件类型</label>
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
                    <label class="layui-form-label">权利人证件号</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].qlrzjh"
                               value="{{ item.qlrzjh || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">权利人序号</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].qlrbh" value="{{length+1}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">发证机关</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].fzjg"
                               value="{{ item.fzjg || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">性别</label>
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
                    <label class="layui-form-label">电话</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].dh" value="{{ item.dh || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">邮编</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].yb" value="{{ item.yb || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">工作单位</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].gzdw"
                               value="{{ item.gzdw || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">权利人性质</label>
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
                    <label class="layui-form-label">共有方式</label>
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
                    <label class="layui-form-label">权利比例</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].qlbl"
                               value="{{ item.qlbl || ''}}">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">权利面积</label>
                    <div class="layui-input-inline">
                        <input type="number" class="layui-input" name="qlrList[{{length}}].qlmj"
                               value="{{ item.qlmj || ''}}">
                    </div>
                </div>
                <div class="layui-inline bdc-two-column">
                    <label class="layui-form-label">地址</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="qlrList[{{length}}].dz" value="{{ item.dz || ''}}">
                    </div>
                </div>
            </div>
            <div class="layui-form-item change-textarea-margin">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea" name="qlrList[{{length}}].bz"
                              value="{{ item.bz || ''}}"></textarea>
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
<script type="text/html" id="uploadTpl">
    {{# if(d.srcUrl){ }}
    <img id="img" class="upload-img" src="{{d.srcUrl}}" alt="">
    {{# }else{ }}
    <div class="upload-icon">
        <i class="layui-icon">&#xe654;</i>
        <span>上传平面图</span>
    </div>
    <h4>点击/拖拽单个文件到这里上传</h4>
    <p>支持jpeg、jpg、png格式，大小在10M以下</p>
    <img id="img" class="upload-img" src="" alt="">
    <div class="video-icon"></div>
    {{# } }}
</script>
<script src="../js/fwljz/ljzForm.js"></script>
</html>