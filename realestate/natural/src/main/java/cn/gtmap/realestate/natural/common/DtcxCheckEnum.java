package cn.gtmap.realestate.natural.common;

/**
 * version 1.0
 *
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @description 查询模式
 * @date 2019/3/19
 */
public enum DtcxCheckEnum {

    SUCCESS("success", "成功"),
    SQL_ERROR("sql.error", "SQL语句存在错误"),
    CXTJ_REPEAT("cxtj.repeat", "条件列表中字段id存在重复"),
    CXJG_REPEAT("cxjg.repeat", "结果列表中字段id存在重复"),
    CXTJ_ERROR("cxtj.error", "字段对应方式存在问题"),
    CXTJ_NOT_INPUT_ERROR("cxtj.not.input.error", "存在未填写的条件字段id"),
    CXJG_NOT_INPUT_ERROR("cxjg.not.input.error", "存在未填写的结果字段id"),
    CXTJ_NOT_EXISTS_ERROR("cxtj.not.exists.error", "存在不存在于SQL中的查询条件"),
    CXJG_NOT_EXISTS_ERROR("cxjg.not.exists.error", "存在不在SQL结果列中的字段id！");

    private String dm;

    private String mc;

    DtcxCheckEnum(String dm, String mc) {
        this.dm = dm;
        this.mc = mc;
    }

    public String getDm() {
        return dm;
    }

    public String getMc() {
        return mc;
    }
}
