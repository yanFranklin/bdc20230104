package cn.gtmap.realestate.certificate.core.model.storage;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ShareDto implements Serializable {
    private String id;
    private String name;
    private int enabled;
    private String owner;
    private String password;
    private String url;
    private String scope;
    private String scopeDesc;
    private String authority;
    private int count = 0;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createAt;
    private String createTime;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date expiryDate;
    private boolean authorized;
    private String flag;
    private int expired;
    private List<StorageDto> storages;

    public ShareDto() {
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getEnabled() {
        return this.enabled;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUrl() {
        return this.url;
    }

    public String getScope() {
        return this.scope;
    }

    public String getScopeDesc() {
        return this.scopeDesc;
    }

    public String getAuthority() {
        return this.authority;
    }

    public int getCount() {
        return this.count;
    }

    public Date getCreateAt() {
        return this.createAt;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public Date getExpiryDate() {
        return this.expiryDate;
    }

    public boolean isAuthorized() {
        return this.authorized;
    }

    public String getFlag() {
        return this.flag;
    }

    public int getExpired() {
        return this.expired;
    }

    public List<StorageDto> getStorages() {
        return this.storages;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setScopeDesc(String scopeDesc) {
        this.scopeDesc = scopeDesc;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setExpired(int expired) {
        this.expired = expired;
    }

    public void setStorages(List<StorageDto> storages) {
        this.storages = storages;
    }
}
