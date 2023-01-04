package cn.gtmap.realestate.common.core.vo.inquiryui;

import io.swagger.annotations.ApiModelProperty;
/**
 * @program: realestate
 * @description: 日志下载功能连接的主机详细信息
 * @author: <a href="mailto:hongqin@gtmap.cn">gaolining</a>
 * @create: 2022-09-02 16:26
 **/
public class BdcRzxzDO {
    /**
     * 主机
     */
    @ApiModelProperty(value = "主机ip")
    private String host;
    /**
     * 端口
     */
    @ApiModelProperty(value = "端口")
    private String port;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 应用所在目录
     */
    @ApiModelProperty(value = "应用所在目录")
    private String path;
    /**
     * 日志存放临时目录
     */
    @ApiModelProperty(value = "日志存放临时目录")
    private String applyname;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    @Override
    public String toString() {
        return "BdcRzxzDO{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", username=" + username +
                ", password='" + password + '\'' +
                ", path=" + path +
                ", applyname='" + applyname + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getApplyname() {
        return applyname;
    }

    public void setApplyname(String applyname) {
        this.applyname = applyname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
