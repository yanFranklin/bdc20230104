package cn.gtmap.realestate.exchange.core.qo;


import java.util.List;

/**
 * @program: realestate
 * @description: 常州教育局产权查询请求参数
 * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @create: 2022-09-01
 **/
public class JyjCqcxQO {

    List<JyjCqcxDataQO>  data;

    public List<JyjCqcxDataQO> getData() {
        return data;
    }

    public void setData(List<JyjCqcxDataQO> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JyjCqcxQO{" +
                "data=" + data +
                '}';
    }
}
