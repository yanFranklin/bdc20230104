<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <title>项目内多幢信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/common.css">
    <link rel="stylesheet" href="../lib/bdcui/css/form.css?v=1.2.2">
    <link rel="stylesheet" href="../lib/bdcui/css/search.css"/>
    <link rel="stylesheet" href="../lib/bdcui/css/table.css"/>
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css"/>
    <link rel="stylesheet" href="../lib/bdcui/css/form-tab.css?2"/>
    <link rel="stylesheet" href="../css/upload.css?v=1.0.1">
    <link rel="stylesheet" href="../css/building.css?v=1.0.1">
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../lib/js/jquerysession.js?v=01"></script>
    <script src="../js/common.js?v=1.4045"></script>
    <script src="../js/redirect.js?v=1.4045"></script>
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/bdcui/js/table.js"></script>
    <script src="../lib/bdcui/js/form-tab.js"></script>
    <@glo.globalVars />
<style>
    .building-search-box{
        border-bottom: 1px dashed #d0d5da; margin: 15px;
    }
    .building-margin{
        margin-bottom: 12px
    }
</style>
</head>
<body>
<div class="bdc-form-div">
    <form class="layui-form setOverflow" lay-filter="form">
        <div class="bdc-content-fix">
            <div class="content-title layui-clear">
                <div class="title-btn-area">
                    <button class="layui-btn bdc-major-btn" lay-submit="" id="xmxxForm" lay-filter="xmxxForm">提交
                    </button>
                    <button class="layui-btn bdc-secondary-btn lpb-back-btn" type="button">返回</button>
                </div>
            </div>
        </div>
        <div class="layui-tab" lay-filter="tabFilter">
            <ul class="layui-tab-title">
                <li class="layui-this">项目信息</li>
                <li id="dcxxTab">调查信息</li>
                <li id="qlrTab">房产权利人</li>
                <li id="ljzTab">逻辑幢列表</li>
                <li id="pmtTab">平面图</li>
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
                        <a href="javascript:;" id="gjzd" class="bdc-third-btn layui-hide">挂接宗地</a>
                        <a href="javascript:;" id="qxgjzd" class="bdc-third-btn layui-hide">取消挂接宗地</a>
                    </div>
                    <div class="basic-info">
                        <div class="layui-form-item layui-hide">
                            <div class="layui-inline margin-top-ipt">
                                <label class="layui-form-label">fwXmxxIndex</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="fwXmxxIndex" id="fwXmxxIndex"
                                           value="${fwXmxxIndex!}">
                                    <input type="text" class="layui-input" id="storageUrl" name="storageUrl"
                                           value="${storageUrl!}">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline bdc-two-column">
                                <label class="layui-form-label">项目名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" name="xmmc" value="${xmmc!}">
                                </div>
                            </div>
                            <div class="layui-inline bdc-two-column">
                                <label class="layui-form-label">隶属宗地</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" readonly name="lszd" id="lszd"
                                           value="${lszd!}">
                                </div>
                            </div>
                            <div class="layui-inline bdc-two-column">
                                <label class="layui-form-label">不动产单元号</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" readonly name="bdcdyh" id="bdcdyh">
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
                                <label class="layui-form-label change-label-width">不动产状态</label>
                                <div class="layui-input-inline">
                                    <select name="bdczt" lay-search="" lay-filter="bdczt">
                                        <option value="1">可用</option>
                                        <option value="0">不可用</option>
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
                        <div class="layui-form-item change-textarea-margin">
                            <label class="layui-form-label change-label-width bdc-label-newline">备注</label>
                            <div class="layui-input-inline">
                                <textarea class="layui-textarea change-textarea-width" name="bz"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <div class="basic-info" id="dcxxForm">
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
                    <div class="basic-info" id="qlrForm">
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

                        <table class="layui-table building-margin" lay-skin="nob">
                            <colgroup>
                                <col width="10%">
                                <col width="26%">
                                <col width="65%">
                                <col>
                            </colgroup>
                            <tbody>
                            <tr>
                                <td class="form-tb-lable td-text-right">逻辑幢号</td>
                                <td>
                                    <input type="text" id="search-ljzh" class="layui-input" placeholder="请输入逻辑幢号">
                                </td>
                                <td class="td-text-left">
                                    <button class="layui-btn layui-btn-normal bdc-major-btn" id="queryLjz"
                                            type="button">查询
                                    </button>
                                </td>
                            </tr>
                            </tbody>
                        </table>

                    <div class="bdc-table-box">
                        <table id="ljzTable" lay-data="{id: 'ljzTable'}" lay-filter="dataTable"></table>
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
<script type="text/html" id="ljzListToolBarTmpl">
    <div class="layui-btn-container">
        <span class="layui-btn layui-btn-xs bdc-major-btn" lay-event="editLjz">修改</span>
        <span class="layui-btn layui-btn-xs bdc-delete-btn layui-hide" lay-event="deleteLjz">删除</span>
        <span class="layui-btn layui-btn-xs bdc-secondary-btn" lay-event="cancelRelevanceLjz">取消关联</span>
    </div>
</script>
<script type="text/html" id="toolbar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm bdc-table-second-btn" type="button" lay-event="relevanceLjz">关联已有逻辑幢
        </button>
        <button class="layui-btn layui-btn-sm bdc-table-second-btn" type="button" lay-event="addLjz">新增逻辑幢</button>
    </div>
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
<script src="../js/xmxx/xmxxForm.js"></script>
</html>