<form class="layui-form setOverflow" lay-filter="zrzTabForm">
    <div class="form-margin-area">
        <div class="basic-info">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        自然幢号
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="zrzh" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        隶属宗地
                    </label>
                    <div class="layui-input-inline change-input-width">
                        <input type="text" name="lszd" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        房屋编号
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="fwbh" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        项目名称
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="xmmc" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        房屋名称
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="fwmc" class="layui-input" />
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        房屋层数
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="fwcs" class="layui-input" />
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        房屋结构
                    </label>
                    <div class="layui-input-inline">
                        <select name="fwjg" lay-search="" lay-filter="fwjg" class="SZdFwjgDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        总套数
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="zts" class="layui-input" />
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        建筑面积
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="jzmj" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        宗地面积
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="zdmj" class="layui-input" />
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        预测建筑面积
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="ycjzmj" class="layui-input" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label ">
                        实测建筑面积
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="scjzmj" class="layui-input" />
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        竣工日期
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" id="jgrq" name="qsrq">
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        建筑物高度
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="jzwgd" class="layui-input" />
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        地上层数
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="dscs" class="layui-input" />
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        地下层数
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="dxcs" class="layui-input" />
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        地下深度
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="dxsd" class="layui-input" />
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label change-label-width bdc-label-newline">
                        建筑物基本用途
                    </label>
                    <div class="layui-input-inline">
                        <select name="jzwjbyt" lay-search="" lay-filter="jzwjbyt" class="SZdDldmDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        所属区域
                    </label>
                    <div class="layui-input-inline">
                        <select name="ssqy" lay-search="" lay-filter="ssqy" class="SDmDwxxDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        分类代码
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" name="fldm" class="layui-input" />
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label ">
                        规划用途
                    </label>
                    <div class="layui-input-inline">
                        <select name="ghyt" lay-search="" lay-filter="ghyt" class="SZdFwytDO">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>