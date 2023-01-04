<div class="form-margin-area">
    <form class="layui-form" lay-filter="zdxxform" id="jyqForm">
        <div class="basic-info">
            <div class="layui-form-item">
                <div class="title-sign">
                    <p>基本信息</p>
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
                        地块实测面积
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="dkscmj" class="layui-input" disabled="off"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label change-label-width bdc-label-newline">
                        地块实测面积（亩）
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="dkscmjm" class="layui-input" disabled="off"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        合同面积
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="dkhtmj" class="layui-input" disabled="off"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label bdc-label-newline">
                        合同面积（亩）
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="dkhtmjm" class="layui-input" disabled="off"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label bdc-label-newline">
                        经营权起始日期
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="jyqqsrq" class="layui-input" disabled="off"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label bdc-label-newline">
                        经营权<br>终止日期
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="jyqzzrq" class="layui-input" disabled="off"/>
                    </div>
                </div>
                <div class="layui-inline bdc-two-column">
                    <label class="layui-form-label change-label-width">土地坐落</label>
                    <div class="layui-input-inline change-input-width">
                        <input type="text" name="zl" class="layui-input" disabled="off"/>
                    </div>
                </div>
            </div>
            <div class="layui-form-item change-textarea-margin">
                <label class="layui-form-label change-label-width">宗地四至</label>
                <div class="layui-input-inline">
                    <textarea class="layui-textarea change-textarea-width" name="zdsz" disabled="off"></textarea>
                </div>
            </div>

        </div>
    </form>
    <form class="layui-form" lay-filter="lcfxxForm" id="lcfxxForm">
        <div class="basic-info" id="lcfxx">
            <div class="layui-form-item">
                <div class="title-sign">
                    <p>流出方</p>
                </div>
                <div class="layui-inline bdc-two-column">
                    <label class="layui-form-label">
                        流出方名称
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="lcfmc" disabled="off" class="layui-input"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        负责人姓名
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="lcffzrmc" class="layui-input" disabled="off"/>
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
                        <input type="text" name="lcffzrdz" class="layui-input" disabled="off"/>
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
    <div class="basic-info" id="jyqqlrList">
    </div>


</div>
<!--经营权权利人-->
<script type="text/html" id="jyqqlrTpl">
    <div class="title-sign">
        <p>权利人</p>
    </div>
    <table class="layui-table djdcb-table-view" border="1" name="">
        <tbody>
        <tr class="gray-tr">
            <td width="30px">序号</td>
            <td min-width="100px">土地权利人</td>
            <td min-width="70px">证件类型</td>
            <td min-width="100px">证件号</td>
            <td min-width="150px">通讯地址</td>
            <td min-width="100px">法定代表人</td>
            <td min-width="70px">法定代表人证件类型</td>
            <td min-width="100px">法定代表人证件号</td>
            <td>电话</td>
        </tr>
        {{# if(!d.data || d.data.length==0) { }}
        <tr class="bdc-table-none">
            <td colspan="9">
                <div class="layui-none">
                    <img src="../static/lib/bdcui/images/table-none.png" alt="">无数据</div>
            </td>
        </tr>
        {{# } else { }}
        {{# layui.each(d.data, function(index, item){ }}
        <tr>
            <td>{{ index+1 }}</td>
            <td>{{ item.qlrmc || ''}}</td>
            <td>{{ item.qlrzjlx || ''}}</td>
            <td>{{ item.qlrzjh || ''}}</td>
            <td>{{ item.txdz || ''}}</td>
            <td>{{ item.frdbxm || ''}}</td>
            <td>{{ item.frdbzjlx || ''}}</td>
            <td>{{ item.frdbzjh || ''}}</td>
            <td>{{ item.frdbdhhm || ''}}</td>
        </tr>
        {{# }); }}
        {{# } }}
        </tbody>
    </table>
</script>


