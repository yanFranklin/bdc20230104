package cn.gtmap.realestate.engine.core.exception;


import java.util.concurrent.ExecutionException;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/04/26
 * @description 子规则验证异常
 */
public class CheckException extends ExecutionException {
    /**
     * 规则ID
     */
    private String gzid;

    /**
     * 数据流ID
     */
    private String sjlid;


    public CheckException(Exception e, String gzid, String sjlid){
        super(e);

        this.gzid = gzid;
        this.sjlid = sjlid;
    }

    public String getGzid() {
        return gzid;
    }

    public String getSjlid() {
        return sjlid;
    }
}
