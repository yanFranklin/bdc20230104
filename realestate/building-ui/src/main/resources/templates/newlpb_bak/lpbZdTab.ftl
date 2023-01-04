<form class="layui-form setOverflow" lay-filter="zdTabform">
    <div class="form-margin-area">
        <div class="basic-info">
            <#--<div class="title-sign"><p><a id="jbxx" href="javascript:;">基本信息</a></p></div>-->
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        地籍号
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="djh" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">不动产单元号</label>
                    <div class="layui-input-inline change-input-width">
                        <input type="text" name="bdcdyh" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        面积单位
                    </label>
                    <div class="layui-input-inline">
                        <select name="mjdw" lay-search="" lay-filter="mjdw" class="SZdMjdwDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        使用权类型
                    </label>
                    <div class="layui-input-inline">
                        <select name="syqlx" lay-search="" lay-filter="syqlx" class="SZdTdsyqlxDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        预编地籍号
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="ybdjh" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        权属性质
                    </label>
                    <div class="layui-input-inline">
                        <select name="qsxz" lay-search="" lay-filter="qsxz" class="SZdQsxzDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline ">
                    <label class="layui-form-label ">
                        图幅号
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="sztfh" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label change-label-width bdc-label-newline">
                        国民经济行业代码
                    </label>
                    <div class="layui-input-inline">
                        <select name="gmjjhyfldm" lay-search="" lay-filter="gmjjhyfldm" class="SZdGmjjhydmDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline bdc-two-column">
                    <label class="layui-form-label ">
                        土地坐落
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="tdzl" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label change-label-width bdc-label-newline">
                        权属来源证明材料
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="tdqslyzmcl" class="layui-input" />
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        批准用途
                    </label>
                    <div class="layui-input-inline">
                        <select name="pzyt" lay-search="" lay-filter="pzyt" class="SZdDldmDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        比例尺
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="blc" class="layui-input" />
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        建筑面积
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="jzzmj" class="layui-input" />
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">
                        建筑占地面积
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="zdzmj" class="layui-input" />
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        建筑限高
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="jzxg" class="layui-input" />
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">
                        建筑容积率
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="jzrjl" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        建筑密度
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="jzmd" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        实测面积
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="scmj" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        发证面积
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="fzmj" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        批准面积
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="pzmj" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        土地用途
                    </label>
                    <div class="layui-input-inline">
                        <select name="tdyt" lay-search="" lay-filter="tdyt" class="SZdDldmDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        起始日期
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" id="qsrq" name="qsrq">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        终止日期
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" id="zzrq" name="zzrq">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">
                        权利设定方式
                    </label>
                    <div class="layui-input-inline">
                        <select name="qlsdfs" lay-search="" lay-filter="qlsdfs" class="SZdQlsdfsDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        等级
                    </label>
                    <div class="layui-input-inline">
                        <select name="dj" lay-search="" lay-filter="dj" class="SZdTddjDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        价格
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="qdjg" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        建立日期
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" id="jlrq" name="jlrq">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        宗地四至-北
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="zdszb" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        宗地四至-东
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="zdszd" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        宗地四至-南
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="zdszn" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        宗地四至-西
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="zdszx" class="layui-input" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>