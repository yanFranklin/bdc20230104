package cn.gtmap.realestate.portal.ui.core.dto;

import cn.gtmap.gtc.sso.domain.dto.ModuleDto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Auther: <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @Date: 1.0， 2019/3/4 16:14
 * @Description: 模块管理 DTO
 */
public class BdcModuleDTO {
    /**
     * 主键
     */
    private String id;

    /**
     * 是否禁用， ztree 显示使用
     */
    private int enabled = 1;

    /**
     * 模块编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 类型编码
     */
    private String type;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * url
     */
    private String url;

    /**
     * 请求方法类型
     */
    private String method;

    /**
     * 应用id
     */
    private String clientId;

    /**
     * 排序编号
     */
    private int sequenceNumber;

    /**
     * 上一级模块主键
     */
    private String parentId;

    /**
     * 上一级模块名称
     */
    private String parentName;

    /**
     * 是否拥有子集， 根据ztree 定义
     */
    private boolean isParent;

    /**
     * 设置节点的 checkbox / radio 是否禁用， 根据ztree 定义
     */
    private boolean chkDisabled;

    /**
     * 节点的 checkBox / radio 的 勾选状态， 根据ztree 定义
     */
    private boolean checked;
    /**
     * 子节点
     */
    private List<BdcModuleDTO> childTree;

    public BdcModuleDTO(ModuleDto moduleDto) {
        id = moduleDto.getId();
        enabled = moduleDto.getEnabled();
        code = moduleDto.getCode();
        name = moduleDto.getName();
        description = moduleDto.getDescription();
        type = moduleDto.getType();
        typeName = moduleDto.getTypeName();
        url = moduleDto.getUrl();
        method = moduleDto.getMethod();
        clientId = moduleDto.getClientId();
        sequenceNumber = moduleDto.getSequenceNumber();
        parentId = moduleDto.getParentId();
        parentName = moduleDto.getParentName();
        isParent = moduleDto.getIsParent();
        chkDisabled = moduleDto.getChkDisabled();
        checked = moduleDto.getChecked();
        childTree = new ArrayList<>();
    }

    public List<BdcModuleDTO> getChildTree() {
        return childTree;
    }

    public void setChildTree(List<BdcModuleDTO> childTree) {
        this.childTree = childTree;
    }

    @Override
    public String toString() {
        return "BdcModuleDTO{" +
                "id='" + id + '\'' +
                ", enabled=" + enabled +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", typeName='" + typeName + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", clientId='" + clientId + '\'' +
                ", sequenceNumber=" + sequenceNumber +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", isParent=" + isParent +
                ", chkDisabled=" + chkDisabled +
                ", checked=" + checked +
                ", childTree=" + childTree +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public boolean isChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
