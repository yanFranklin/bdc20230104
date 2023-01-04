/**
 * Created by sly on 2017/10/11.
 */
    //设置命名空间
var CodeSTD = window.CodeSTD || {};

window.CodeSTD = CodeSTD;

/**
 * 创建Form表单
 * @param config Object
 *  <p>url:form的Action，提交的后台地址</p>
 *  <p>method:使用POST还是GET提交表单</p>
 *  <p>params:参数 K-V</p>
 * @return Form
 */
CodeSTD.form = function (config) {
    config = config || {};

    var url = config.url,
        method = config.method || 'GET',
        params = config.params || {};

    var form = document.createElement('form');
    form.action = url;
    form.method = method;
    form.target = "_blank";

    for (var param in params) {
        var value = params[param],
            input = document.createElement('input');

        input.type = 'hidden';
        input.name = param;
        input.value = value;

        form.appendChild(input);
    }

    return form;
}

