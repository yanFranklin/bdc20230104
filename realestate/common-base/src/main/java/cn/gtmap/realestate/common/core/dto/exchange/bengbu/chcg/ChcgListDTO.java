package cn.gtmap.realestate.common.core.dto.exchange.bengbu.chcg;

import io.swagger.annotations.ApiModel;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/10/5
 * @description 委托测绘事项关联的成果集合
 */
@ApiModel(value = "ChcgListDTO", description = "委托测绘事项关联的成果集合")
public class ChcgListDTO {


    /**
     * attachname : paper1.jpg
     * filesize : 1.48 MB
     * attachpath : hebeUpload/bbdchy/sgxk/e3fff9c950504b9481ce46e7e0ef57b9/9fe02a9a87a546bab6e508f375452ef3/b0bf431382635d76f63bc74cc35a9137.jpg
     * chcgRowid : 9fe02a9a87a546bab6e508f375452ef3
     * cgflname : 拿地即办证
     */

    private String attachname;
    private String filesize;
    private String attachpath;
    private String chcgRowid;
    private String cgflname;

    public String getAttachname() {
        return attachname;
    }

    public void setAttachname(String attachname) {
        this.attachname = attachname;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getAttachpath() {
        return attachpath;
    }

    public void setAttachpath(String attachpath) {
        this.attachpath = attachpath;
    }

    public String getChcgRowid() {
        return chcgRowid;
    }

    public void setChcgRowid(String chcgRowid) {
        this.chcgRowid = chcgRowid;
    }

    public String getCgflname() {
        return cgflname;
    }

    public void setCgflname(String cgflname) {
        this.cgflname = cgflname;
    }

    @Override
    public String toString() {
        return "ChcgListDTO{" +
                "attachname='" + attachname + '\'' +
                ", filesize='" + filesize + '\'' +
                ", attachpath='" + attachpath + '\'' +
                ", chcgRowid='" + chcgRowid + '\'' +
                ", cgflname='" + cgflname + '\'' +
                '}';
    }
}
