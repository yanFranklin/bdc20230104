package cn.gtmap.realestate.common.util.autogenerate;

import cn.gtmap.realestate.common.util.autogenerate.ParameterInfo;

import java.util.List;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2020/8/20 15:13
 * @description 接口信息
 */
public class InterfaceInfo {
    /*
     * 作者
     */
    private String author;

    /*
     * 接口说明
     */
    private String InterfaceDesc;

    /*
     * UIController方法的注解
     */
    private List<Annotation> uiMethodAnno;

    /*
     * 方法名称
     */
    private String methodName;

    /*
     * 方法的参数
     */
    private List<ParameterInfo> methodParam;

    /*
     * 返回值的类型
     */
    private String returnType;

    /*
     * 返回值说明
     */
    private String returnDesc;

    /*
     * RestService方法注解
     */
    private List<Annotation> restMethodAnno;

    /*
     * uiControllerPath本地路径
     */
    private String uiControllerPath;

    /*
     * restServicePath本地路径
     */
    private String restServicePath;

    private String restControllerPath;

    private String servicePath;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getInterfaceDesc() {
        return InterfaceDesc;
    }

    public void setInterfaceDesc(String interfaceDesc) {
        InterfaceDesc = interfaceDesc;
    }

    public List<Annotation> getUiMethodAnno() {
        return uiMethodAnno;
    }

    public void setUiMethodAnno(List<Annotation> uiMethodAnno) {
        this.uiMethodAnno = uiMethodAnno;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<Annotation> getRestMethodAnno() {
        return restMethodAnno;
    }

    public void setRestMethodAnno(List<Annotation> restMethodAnno) {
        this.restMethodAnno = restMethodAnno;
    }

    public String getUiControllerPath() {
        return uiControllerPath;
    }

    public void setUiControllerPath(String uiControllerPath) {
        this.uiControllerPath = uiControllerPath;
    }

    public String getRestServicePath() {
        return restServicePath;
    }

    public void setRestServicePath(String restServicePath) {
        this.restServicePath = restServicePath;
    }

    public String getRestControllerPath() {
        return restControllerPath;
    }

    public void setRestControllerPath(String restControllerPath) {
        this.restControllerPath = restControllerPath;
    }

    public String getServicePath() {
        return servicePath;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }

    public List<ParameterInfo> getMethodParam() {
        return methodParam;
    }

    public void setMethodParam(List<ParameterInfo> methodParam) {
        this.methodParam = methodParam;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }
}
