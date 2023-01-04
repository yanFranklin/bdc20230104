/**
 * 权利信息验证js
 */

/**
 * 验证土地结束期限,验证规则1：权利性质为划拨，土地使用结束期限应为空
 */
function checkJssjGz1(qlxz, jssj) {
    if (qlxz === "101" && isNotBlank(jssj)) {
        throw error = new Error("权利性质为划拨，不可填写土地使用结束时间");
    }
}

/**
 * 验证土地结束期限,验证规则2：权利性质为出让，土地使用结束期限不能为空
 */
function checkJssjGz2(qlxz, jssj, sfyz) {
    if (qlxz === "102" && jssj === "" && !sfyz) {
        throw error = new Error("权利性质为出让，请填写土地使用结束时间");
    }
}

/**
 * 验证土地结束期限,验证规则3：南通地区--权利性质为划拨，土地使用起止时间都必须为空
 */
function checkJssjGz3(qlxz, jssj, qssj) {
    if (qlxz === "101" && (isNotBlank(jssj) || isNotBlank(qssj))) {
        throw error = new Error("权利性质为划拨，不予填写土地期限");
    }
}

/**
 * 验证土地结束期限,验证规则4：南通地区--权利性质为租赁，土地使用结束期限不能为空
 */
function checkJssjGz4(qlxz, jssj) {
    if (qlxz === "104" && !isNotBlank(jssj)) {
        throw error = new Error("权利性质为租赁，请填写土地使用结束时间");
    }
}