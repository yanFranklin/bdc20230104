package cn.gtmap.realestate.certificate.core.dto;

import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.DzqzPositionDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.DzqzSealDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 证照签章
 * <p>
 * 2.0 版本，读取配置 cz.zzqz 配置内容
 * 相关配置信息
 * </p>
 */
@Component
@ConfigurationProperties(prefix = "cz.zzqz")
public class ZzqzConfig {
    private DzqzPositionDTO position;
    private DzqzSealDTO seal;
    private DzqzZzpzDTO zz;
    private String foldName = "电子证照";

    public String getFoldName() {
        return foldName;
    }

    public void setFoldName(String foldName) {
        this.foldName = foldName;
    }

    public DzqzPositionDTO getPosition() {
        return position;
    }

    public void setPosition(DzqzPositionDTO position) {
        this.position = position;
    }

    public DzqzSealDTO getSeal() {
        return seal;
    }

    public void setSeal(DzqzSealDTO seal) {
        this.seal = seal;
    }

    public DzqzZzpzDTO getZz() {
        return zz;
    }

    public void setZz(DzqzZzpzDTO zz) {
        this.zz = zz;
    }

    @Override
    public String toString() {
        return "CzZzqzConfig{" +
                "position=" + position +
                ", seal=" + seal +
                ", zz=" + zz +
                ", foldName='" + foldName + '\'' +
                '}';
    }
}
