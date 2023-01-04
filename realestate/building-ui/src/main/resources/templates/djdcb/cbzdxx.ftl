<div class="form-margin-area">
    <form class="layui-form" lay-filter="cbzdForm" id="cbzdForm">
        <div class="basic-info">
            <div class="layui-form-item">
                <div class="title-sign">
                    <p>基本信息</p>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        地籍号
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="cbzdDjh" name="djh" disabled="off" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-inline bdc-two-column">
                    <label class="layui-form-label">
                        不动产单元号
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="bdcdyh" class="layui-input" disabled="off"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        承包土地用途
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="tdyt" class="layui-input" disabled="off"/>
                        <input type="hidden" name="tdyt" zd="true" class="SZdCbtdytDO"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label change-label-width">
                        是否基本农田
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="sfjbnt" class="layui-input" disabled="off"/>
                        <input type="hidden" name="sfjbnt" zd="true" class="SZdBoolDO"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        地力等级
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="dldj" class="layui-input" disabled="off"/>
                        <input type="hidden" name="dldj" zd="true" class="SZdCbdldjDO"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        水域滩涂类型
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="syttlx" class="layui-input" disabled="off"/>
                        <input type="hidden" name="syttlx" zd="true" class="SZdCbsyttlxDO"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        养殖业方式
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="yzyfs" class="layui-input" disabled="off"/>
                        <input type="hidden" name="yzyfs" zd="true" class="SZdCbyzfsDO"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        适宜载畜量
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="syzxl" class="layui-input" disabled="off"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        草层高度(cm)
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="ccgd" class="layui-input" disabled="off"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        草地覆盖率(%)
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="cdfgd" class="layui-input" disabled="off"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        建群
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="cdjq" class="layui-input" disabled="off"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        优势种
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="cdysz" class="layui-input" disabled="off"/>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <form class="layui-form" lay-filter="fbfxxForm" id="fbfxxForm">
        <div class="basic-info" id="fbfxx">
            <div class="layui-form-item">
                <div class="title-sign">
                    <p>发包方</p>
                </div>
                <div class="layui-inline bdc-two-column">
                    <label class="layui-form-label">
                        发包方名称
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="fbfmc" disabled="off" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        负责人姓名
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="fbffzrxm" class="layui-input" disabled="off"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        联系电话
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="lxdh" class="layui-input" disabled="off"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        负责人地址
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="fbffzrdz" class="layui-input" disabled="off"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label change-label-width bdc-label-newline">
                        负责人证件种类
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="fzrzjlx" class="layui-input" disabled="off"/>
                        <input type="hidden" name="fzrzjlx" zd="true" class="SZdZjllxDO"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        证件号
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="fzrzjhm" class="layui-input" disabled="off"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        邮政编码
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="yzbm" class="layui-input" disabled="off"/>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <form class="layui-form" lay-filter="cbfxxForm" id="cbfxxForm">
        <div class="basic-info" id="cbfxx-jtcy">
        </div>

    </form>
    <form class="layui-form" lay-filter="cbzddcxxForm" id="cbzddcxxForm">
        <div class="basic-info" id="cbzddcxx">
            <div class="title-sign">
                <p>调查信息</p>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">调查员</label>
                    <div class="layui-input-inline">
                        <input disabled="off" type="text" class="layui-input" name="dcy">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">调查日期</label>
                    <div class="layui-input-inline">
                        <input disabled="off" type="text" class="layui-input" name="dcrq">
                    </div>
                </div>
            </div>
            <div class="layui-form-item change-textarea-margin">
                <label class="layui-form-label ">调查记事</label>
                <div class="layui-input-inline">
                    <textarea disabled="off" class="layui-textarea change-textarea-width" name="dcjs"></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label">审核人</label>
                    <div class="layui-input-inline">
                        <input disabled="off" type="text" class="layui-input" name="shr">
                    </div>
                </div>
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label ">审核日期</label>
                    <div class="layui-input-inline">
                        <input disabled="off" type="text" class="layui-input" name="shrq">
                    </div>
                </div>
            </div>
            <div class="layui-form-item change-textarea-margin">
                <label class="layui-form-label ">审核意见</label>
                <div class="layui-input-inline">
                    <textarea disabled="off" class="layui-textarea change-textarea-width" name="shyj"></textarea>
                </div>
            </div>
        </div>
    </form>
</div>
<!--承包方与家庭成员信息-->
<script type="text/html" id="cbfJtcyTpl">
    {{# layui.each(d.data, function(index, item){ }}
    <div class="basic-info" id="cbfxx">
        <div class="title-sign">
            <p>承包方</p>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline bdc-two-column">
                <label class="layui-form-label">
                    承包方(代表)
                </label>
                <div class="layui-input-inline">
                    <input type="text" name="cbfmc" disabled="off" class="layui-input"
                           value="{{ item.cbzdCbfDO.cbfmc || ''}}"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">
                    取得承包方式
                </label>
                <div class="layui-input-inline">
                    <input type="text" name="qdcbfs" class="layui-input" disabled="off"
                           value="{{ item.cbzdCbfDO.qdcbfs || ''}}"/>
                    <input type="hidden" name="qdcbfs" zd="true" class="SZdCbjyqqdfsDO"
                           value="{{ item.cbzdCbfDO.qdcbfs || ''}}"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">
                    承包合同编号
                </label>
                <div class="layui-input-inline">
                    <input type="text" name="cbhtbh" class="layui-input" disabled="off"
                           value="{{ item.cbzdCbfDO.cbhtbh || ''}}"/>
                </div>
            </div>
            <div class="layui-inline bdc-two-column">
                <label class="layui-form-label">
                    经营权证编号
                </label>
                <div class="layui-input-inline">
                    <input type="text" name="jyqzbh" class="layui-input" disabled="off"
                           value="{{ item.cbzdCbfDO.jyqzbh || ''}}"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <table class="layui-table djdcb-table-view" border="1" name="">
                <tbody>
                <tr class="gray-tr">
                    <td min-width="100px">家庭成员姓名</td>
                    <td min-width="70px">与户主关系</td>
                    <td min-width="100px">身份证号</td>
                    <td min-width="150px">成员备注</td>
                </tr>
                {{# if(!item.nydJtcyDOList || item.nydJtcyDOList.length==0) { }}
                <tr class="bdc-table-none">
                    <td colspan="9">
                        <div class="layui-none">
                            <img src="../static/lib/bdcui/images/table-none.png" alt="">无数据
                        </div>
                    </td>
                </tr>
                {{# } else { }}
                {{# layui.each(item.nydJtcyDOList, function(index, jtcy){ }}
                <tr>
                    <td>{{ jtcy.xm || ''}}</td>
                    <td>{{ jtcy.gx || ''}}</td>
                    <td>{{ jtcy.sfzhm || ''}}</td>
                    <td>{{ jtcy.cybzsm || ''}}</td>
                </tr>
                {{# }); }}
                {{# } }}
                </tbody>
            </table>
        </div>
    </div>
    {{# }); }}
</script>


