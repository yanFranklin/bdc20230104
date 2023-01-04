package cn.gtmap.realestate.exchange.core.dto.zzcxj;

import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response.ZfcxResponseData;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/5/6.
 * @description  查询机返回信息公共部分
 */

public class BaseResponseDTO {
    /**
     * 执行是否成功
     */
    protected boolean success;

    /**
     * 请求时间
     */
    protected String qqsj;


    /**
     * 相应当前页码
     */
    protected int page;


    /**
     * 本次相应实际返回数据条数
     */
    protected int size;


    /**
     * 查询耗时，单位ms
     */
    protected long qtime;


    /**
     * 查询匹配到的总数据量
     */
    protected long records;
    /**
     * 查询匹配到的总页数
     */
    protected long total;

    /**
     * 查询编号
     */
    protected Object cxbh;

    /**
     * 查询编号
     */
    protected Object jlid;
    /**
     * 响应状态码
     */
    protected int statusCode;


    /**
     * 响应具体消息
     */
    protected String message;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getQqsj() {
        return qqsj;
    }

    public void setQqsj(String qqsj) {
        this.qqsj = qqsj;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getQtime() {
        return qtime;
    }

    public void setQtime(long qtime) {
        this.qtime = qtime;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getCxbh() {
        return cxbh;
    }

    public void setCxbh(Object cxbh) {
        this.cxbh = cxbh;
    }

    public Object getJlid() {
        return jlid;
    }

    public void setJlid(Object jlid) {
        this.jlid = jlid;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
