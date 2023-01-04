package cn.gtmap.realestate.building.config.ftp;

import cn.gtmap.realestate.common.core.service.InterfaceCode;

/**
 * @author wyh
 * @version 1.0
 * @date 2022/2/18 16:38
 */
public interface FtpConfig  extends InterfaceCode {

    public String getIp();

    public String getPort();

    public String getUsername();

    public String getPassword();

    public String getPath();

    public String getPathF();
}
