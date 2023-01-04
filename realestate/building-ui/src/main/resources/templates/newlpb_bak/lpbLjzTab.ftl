<form class="layui-form setOverflow" lay-filter="ljzTabForm">
    <div class="form-margin-area">
        <div class="basic-info">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">逻辑幢号</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" id="ljzh" name="ljzh">
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label change-label-width bdc-label-newline">不动产单元房屋类型</label>
                    <div class="layui-input-inline">
                        <select name="bdcdyfwlx" id="bdcdyfwlx" lay-search="" lay-filter="bdcdyfwlx"
                                class="SZdBdcdyFwlxDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label">隶属宗地</label>
                    <div class="layui-input-inline">
                        <input type="text" id="lszd" class="layui-input" name="lszd" value="${lszd!}">
                    </div>
                </div>
                <div class="layui-inline " id="bdcdyhFrom">
                    <label class="layui-form-label">不动产单元号</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="bdcdyh"/>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">自然幢号</label>
                    <div class="layui-input-inline">
                        <input type="text" id="zrzh" class="layui-input" name="zrzh">
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">房屋名称</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="fwmc">
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">权利状态</label>
                    <div class="layui-input-inline">
                        <select name="bdczt" lay-search="" lay-filter="bdczt">
                            <option value="">请选择</option>
                            <option value="0">不可用</option>
                            <option value="1">可用</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">幢号</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="dh">
                    </div>
                </div>
                <div class="layui-inline bdc-two-column">
                    <label class="layui-form-label ">坐落地址</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="zldz">
                    </div>
                </div>

                <div class="layui-inline ">
                    <label class="layui-form-label ">房屋层数</label>
                    <div class="layui-input-inline">
                        <input type="number" class="layui-input" name="fwcs">
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">总套数</label>
                    <div class="layui-input-inline">
                        <input type="number" class="layui-input" name="zts">
                    </div>
                </div>

                <div class="layui-inline ">
                    <label class="layui-form-label ">门牌号</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="mph">
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">竣工日期</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" id="jgrq" name="jgrq">
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">地上层数</label>
                    <div class="layui-input-inline">
                        <input type="number" class="layui-input" name="dscs">
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">地下层数</label>
                    <div class="layui-input-inline">
                        <input type="number" class="layui-input" name="dxcs">
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label change-label-width">预测建筑面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="ycjzmj"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label change-label-width">预测地下面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="ycdxmj"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label change-label-width">预测其他面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="ycqtmj"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label change-label-width">实测建筑面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="scjzmj"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label change-label-width">实测地下面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="scdxmj"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label change-label-width">实测其他面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="scqtmj"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label">占地面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="number" class="layui-input" name="zzdmj"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label">用地面积</label>
                    <div class="layui-input-inline bdc-one-icon">
                        <input type="text" class="layui-input" name="zydmj"><span>m²</span>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">房屋结构</label>
                    <div class="layui-input-inline">
                        <select name="fwjg" lay-search="" lay-filter="fwjg" class="SZdFwjgDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">房屋用途</label>
                    <div class="layui-input-inline">
                        <select name="fwyt" lay-search="" lay-filter="fwyt" class="SZdFwytDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">建筑物状态</label>
                    <div class="layui-input-inline">
                        <select name="jzwzt" lay-search="" lay-filter="jzwzt" class="SZdJzwztDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">状态日期</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" id="ztrq" name="ztrq">
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">墙体归属 东</label>
                    <div class="layui-input-inline">
                        <select name="d" lay-search="" lay-filter="d" class="SZdQtgsDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">墙体归属 南</label>
                    <div class="layui-input-inline">
                        <select name="n" lay-search="" lay-filter="n" class="SZdQtgsDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">墙体归属 西</label>
                    <div class="layui-input-inline">
                        <select name="x" lay-search="" lay-filter="x" class="SZdQtgsDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">墙体归属 北</label>
                    <div class="layui-input-inline">
                        <select name="b" lay-search="" lay-filter="b" class="SZdQtgsDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline bdc-two-column">
                    <label class="layui-form-label">产别</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="cb">
                    </div>
                </div>
            </div>
            <div class="layui-form-item" >
                <div class="layui-inline bdc-two-column">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-inline">
                        <textarea class="layui-textarea change-textarea-width" name="bz"></textarea>
                    </div>
                </div>
            </div>

        </div>
    </div>
</form>