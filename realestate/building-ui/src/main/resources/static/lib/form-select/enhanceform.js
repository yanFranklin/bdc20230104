/**
 * auth captainY 2019年9月12日14:48:31
 * layui表单增加插件
 * 
 */
layui.define(['jquery', 'form'],
    function(exports) {
        var $ = layui.jquery,
            form = layui.form,
            formObj,
            hint = layui.hint();
        var EnhanceForm = function(options) {
            this.options = options;
            formObj = $(options.elem);
        };
        /**
         * 设置select选中值
         * @param {String} name 对象名称，指“name”
         * @param {String} val 内里的值
         * @param {Boolean} isOnSelect 是否触发选中事件
         * @returns {} 
         */
        EnhanceForm.prototype.setSelectVal = function(name, val, isOnSelect) {
            if (name === undefined) {
                throw "name no undefined";
            }
            formObj.find('select[name="' + name + '"]').val(val);
            form.render('select');
            if (typeof (isOnSelect) === "boolean") {
                if (isOnSelect) {
                    formObj.find("dd[lay-value='" + val + "']").trigger("click");
                }
            }
            return this;
        };
        /**
         * 设置radio选中
         * @param {String} name 对象名称，指“name”
         * @param {String} val 对象值
         * @returns {} 
         */
        EnhanceForm.prototype.setRadioVal = function(name, val) {
            if (name === undefined) {
                throw "name no undefined";
            }
            formObj.find('input[type="radio"][name="' + name + '"][value="' + val + '"]').prop("checked", true);
            form.render('radio');
            return this;
        };
        /**
         * 设置checkbox选中
         * @param {String} name 对象名称，指“name”
         * @returns {} 
         */
        EnhanceForm.prototype.setCheckboxVal = function(name) {
            if (name === undefined) {
                throw "name no undefined";
            }
            formObj.find('input[type="checkbox"][name="' + name + '"]').prop("checked", true);
            form.render('checkbox');
            return this;
        }

        /**
         * 接口方法输出
         */
        exports('enhanceform',
            function(options) {
            	var elem = $(options.elem);
            	if (!elem[0]) {
                    return hint.error('layui.enhanceform 没有找到' + options.elem + '元素');
                }
                var enhance = new EnhanceForm(options = options || {});
                return enhance;
            });
    });