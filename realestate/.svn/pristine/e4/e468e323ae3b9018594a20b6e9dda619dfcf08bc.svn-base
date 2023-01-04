package cn.gtmap.realestate.certificate.core.model.storage;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StorageDto implements Serializable {
    private String id;
    private String clientId;
    private String spaceId;
    private int enabled;
    private int type;
    private String name;
    private String owner;
    private String path;
    private String downUrl;
    private int weight;
    private String currentKey;
    private String updatedBy;
    private long currentVersion;
    private long fileSize;
    private String fileType;
    private String storeType;
    private StorageDto parent;
    private List<StorageDto> children;
    private ShareDto share;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createAt;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date updateAt;

    public StorageDto() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setCurrentKey(String currentKey) {
        this.currentKey = currentKey;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setCurrentVersion(long currentVersion) {
        this.currentVersion = currentVersion;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public void setParent(StorageDto parent) {
        this.parent = parent;
    }

    public void setChildren(List<StorageDto> children) {
        this.children = children;
    }

    public void setShare(ShareDto share) {
        this.share = share;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getId() {
        return this.id;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getSpaceId() {
        return this.spaceId;
    }

    public int getEnabled() {
        return this.enabled;
    }

    public int getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getPath() {
        return this.path;
    }

    public String getDownUrl() {
        return this.downUrl;
    }

    public int getWeight() {
        return this.weight;
    }

    public String getCurrentKey() {
        return this.currentKey;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public long getCurrentVersion() {
        return this.currentVersion;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public String getFileType() {
        return this.fileType;
    }

    public String getStoreType() {
        return this.storeType;
    }

    public StorageDto getParent() {
        return this.parent;
    }

    public List<StorageDto> getChildren() {
        return this.children;
    }

    public ShareDto getShare() {
        return this.share;
    }

    public Date getCreateAt() {
        return this.createAt;
    }

    public Date getUpdateAt() {
        return this.updateAt;
    }
}
