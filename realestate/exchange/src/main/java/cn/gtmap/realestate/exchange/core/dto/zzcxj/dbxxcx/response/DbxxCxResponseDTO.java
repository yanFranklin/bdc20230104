package cn.gtmap.realestate.exchange.core.dto.zzcxj.dbxxcx.response;

import cn.gtmap.realestate.exchange.core.dto.zzcxj.BaseResponseDTO;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/5/6.
 * @description 自助查询机房屋权属查询(以权利为主) 返回信息
 */
public class DbxxCxResponseDTO extends BaseResponseDTO {
    private DbxxCxResponseData data;

    public DbxxCxResponseDTO() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.qqsj = sdf.format(new Date());
    }

    public DbxxCxResponseData getData() {
        return data;
    }

    public void setData(DbxxCxResponseData data) {
        this.data = data;
    }
}
