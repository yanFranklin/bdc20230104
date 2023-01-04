<#--<div class="bdc-form-div building-background-write building-padding-ifteen">-->
<form class="layui-form" lay-filter="fwhsform" id="fwhsForm">
    <div class="form-margin-area">
        <div class="basic-info" id="fwQlrList">
        </div>
        <div class="basic-info">
            <div class="layui-form-item">
                <div class="title-sign">
                    <p>基本信息</p>
                </div>
                <div class="layui-form-item layui-hide">
                    <input type="text" class="layui-input" name="djid" id="djid" />
                    <input type="text" class="layui-input" name="fwDcbIndex" id="fwDcbIndex" />
                    <input type="text" class="layui-input" name="hslx" id="hslx" />
                </div>
                <div class="layui-inline bdc-two-column">
                    <label class="layui-form-label change-label-width bdc-label-newline">不动产单元编号</label>
                    <div class="layui-input-inline change-input-width">
                        <input type="text" class="layui-input" name="bdcdyh" value="${bdcdyh!}" disabled="off">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">幢编号</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" id="ljzh" class="layui-input" name="ljzh">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">单元号</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" id="dyh" class="layui-input" name="dyh">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">房间号</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" class="layui-input" name="fjh">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">物理层</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" id="wlcs" class="layui-input" name="wlcs">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">定义层</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" id="dycs" class="layui-input" name="dycs">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">室序号</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" class="layui-input" name="sxh">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">隶属宗地</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" id="lszd" class="layui-input" name="lszd">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">项目名称</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" id="xmmc" class="layui-input" name="xmmc">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">层高</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" class="layui-input" name="cg">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">土地使用权人</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" id="qlrmc" class="layui-input" name="qlrmc">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">建筑面积</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" id="scjzmj" class="layui-input" name="scjzmj">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">房屋结构</label>
                    <div class="layui-input-inline">
                        <input type="text" name="hxjg" class="layui-input" disabled="off"/>
                        <input type="hidden" disabled="off" class="SZdHxjgDO" zd="true" name="hxjg">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">套内建筑面积</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" id="sctnjzmj" class="layui-input" name="sctnjzmj">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">规划用途</label>
                    <div class="layui-input-inline">
                        <input type="text" name="ghyt" class="layui-input" disabled="off"/>
                        <input type="hidden" disabled="off" id="ghyt" zd="true" class="SZdFwytDO" name="ghyt">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">分摊建筑面积</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" class="layui-input" name="scftjzmj">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">分摊土地面积</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" class="layui-input" name="fttdmj">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">独用土地面积</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" class="layui-input" name="dytdmj">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">终止日期</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" class="layui-input" name="zzrq">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">土地权利性质</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" class="layui-input" name="tdsyqlx">
                        <input type="hidden" disabled="off" zd="true" class="SZdTdsyqlxDO" name="tdsyqlx">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">土地用途</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" class="layui-input" name="tdyt">
                        <input type="hidden" disabled="off" zd="true" class="SZdDldmDO" name="tdyt">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">实测分摊系数</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" class="layui-input" name="scftxs">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">坐落</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="off" class="layui-input" name="zl">
                    </div>
                </div>
            </div>
        </div>
        <div class="basic-info" id="fwdcxx">
            <div class="title-sign">
                <p>调查信息</p>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">调查者</label>
                    <div class="layui-input-inline">
                        <input disabled="off" type="text" class="layui-input" name="dcz" id="dcz">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">调查时间</label>
                    <div class="layui-input-inline">
                        <input disabled="off" type="text" class="layui-input" name="dcsj" id="dcsj">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">产权来源</label>
                    <div class="layui-input-inline">
                        <input disabled="off" type="text" class="layui-input" name="cqly" id="cqly">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">共有情况</label>
                    <div class="layui-input-inline">
                        <input disabled="off" type="text" class="layui-input" name="gyqk">
                    </div>
                </div>
            </div>
            <div class="layui-form-item change-textarea-margin">
                <label class="layui-form-label ">附加说明</label>
                <div class="layui-input-inline">
                    <textarea disabled="off" class="layui-textarea change-textarea-width" name="fjsm"></textarea>
                </div>
            </div>
            <div class="layui-form-item change-textarea-margin">
                <label class="layui-form-label ">调查意见</label>
                <div class="layui-input-inline">
                    <textarea disabled="off" class="layui-textarea change-textarea-width" name="dcyj"></textarea>
                </div>
            </div>
        </div>
    </div>
</form>
<#--</div>-->
<script type="text/html" id="fwQlrTpl">
    <div class="title-sign">
        <p>房屋权利人</p>
    </div>
    <table class="layui-table layui-table-view" border="1" name="">
        <tbody>
        <tr class="gray-tr">
            <td width="30px">序号</td>
            <td min-width="100px">权利人</td>
            <td min-width="70px">证件类型</td>
            <td min-width="100px">证件号</td>
        </tr>
        {{# if(!d.data || d.data.length==0) { }}
        <tr class="bdc-table-none">
            <td colspan="4">
                <div class="layui-none"><img src="../static/lib/bdcui/images/table-none.png" alt="">无数据</div>
            </td>
        </tr>
        {{# } else { }}
        {{# layui.each(d.data, function(index, item){ }}
        <tr>
            <td>{{ index+1 }}</td>
            <td>{{ item.qlr || ''}}</td>
            <td>{{ item.qlrzjlx || ''}}</td>
            <td>{{ item.qlrzjh || ''}}</td>
        </tr>
        {{# }); }}
        {{# } }}
        </tbody>
    </table>
</script>
