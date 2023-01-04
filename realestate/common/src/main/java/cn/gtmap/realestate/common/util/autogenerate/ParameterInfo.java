package cn.gtmap.realestate.common.util.autogenerate;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2020/8/20 14:09
 * @description 参数信息
 */
public class ParameterInfo {
    /*
     * 参数注解
     */
    private Annotation annotation;

    /*
     * 参数类型
     */
    private String type;

    /*
     * 参数名称
     */
    private String name;

    /*
     * 是否必传 默认必传，true
     */
    private boolean required = true;

    /*
     * 参数说明
     */
    private String description;

    /*
     * 参数来源
     */
    private String source;

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
