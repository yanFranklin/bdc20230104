package cn.gtmap.realestate.etl.core.dto;

public class DaxxFile {


    /**
     * XOffice : false
     * alias :
     * content :
     * converttype :
     * extension : jpg
     * fileSize : 239717
     * fullText :
     * id : 0bef93a3a68d46b58ca2dbee88a6134c
     * image : true
     * kgb : 0.71
     * linktype :
     * name : 0001.jpg
     * ownerId : 2002-3-2178
     * ownerModelName : Wsda
     * path : E:\tomcat_cluster\连云港附件\data\archive\2017DASZH\文书原文\2002\002\0387-2002-002-0003\0001.jpg
     * receiveTime :
     * status :
     * updateTime : 2017-11-27
     */

    private boolean XOffice;
    private String alias;
    private String content;
    private String converttype;
    private String extension;
    private int fileSize;
    private String fullText;
    private String id;
    private boolean image;
    private double kgb;
    private String linktype;
    private String name;
    private String ownerId;
    private String ownerModelName;
    private String path;
    private String receiveTime;
    private String status;
    private String updateTime;

    public boolean isXOffice() {
        return XOffice;
    }

    public void setXOffice(boolean XOffice) {
        this.XOffice = XOffice;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConverttype() {
        return converttype;
    }

    public void setConverttype(String converttype) {
        this.converttype = converttype;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public double getKgb() {
        return kgb;
    }

    public void setKgb(double kgb) {
        this.kgb = kgb;
    }

    public String getLinktype() {
        return linktype;
    }

    public void setLinktype(String linktype) {
        this.linktype = linktype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerModelName() {
        return ownerModelName;
    }

    public void setOwnerModelName(String ownerModelName) {
        this.ownerModelName = ownerModelName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "DaxxFile{" +
                "XOffice=" + XOffice +
                ", alias='" + alias + '\'' +
                ", content='" + content + '\'' +
                ", converttype='" + converttype + '\'' +
                ", extension='" + extension + '\'' +
                ", fileSize=" + fileSize +
                ", fullText='" + fullText + '\'' +
                ", id='" + id + '\'' +
                ", image=" + image +
                ", kgb=" + kgb +
                ", linktype='" + linktype + '\'' +
                ", name='" + name + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", ownerModelName='" + ownerModelName + '\'' +
                ", path='" + path + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                ", status='" + status + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
