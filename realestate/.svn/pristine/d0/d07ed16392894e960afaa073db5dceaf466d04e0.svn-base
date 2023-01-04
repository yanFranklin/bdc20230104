package cn.gtmap.realestate.exchange.core.dto.common.grdacx.response;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-30
 * @description 标准版 个人档案查询响应结构体
 */
public class GrdacxResponseDTO {

    private List<GrdacxResponseQlxx> qlxx;

    public List<GrdacxResponseQlxx> getQlxx() {
        return qlxx;
    }

    public void setQlxx(List<GrdacxResponseQlxx> qlxx) {
        this.qlxx = qlxx;
    }

    public static void main2(String[] args) {
        GrdacxResponseDTO responseDTO = new GrdacxResponseDTO();

        List<GrdacxResponseDyaq> dyxxList = new ArrayList<>();
        GrdacxResponseDyaq dyxx = new GrdacxResponseDyaq();
        dyxx.setQlr(qlrList());
        dyxxList.add(dyxx);

        List<GrdacxResponseCf> cfList = new ArrayList<>();
        GrdacxResponseCf cfxx = new GrdacxResponseCf();
        cfxx.setQlr(qlrList());
        cfList.add(cfxx);

        List<GrdacxResponseYy> yyList = new ArrayList<>();
        GrdacxResponseYy yyxx = new GrdacxResponseYy();
        yyxx.setQlr(qlrList());
        yyList.add(yyxx);

        List<GrdacxResponseQlxx> qlxxList = new ArrayList<>();
        GrdacxResponseQlxx qlxx = new GrdacxResponseQlxx();
        qlxx.setQlr(qlrList());
        qlxx.setDyxx(dyxxList);
        qlxx.setCfxx(cfList);
        qlxx.setYyxx(yyList);
        qlxxList.add(qlxx);
        responseDTO.setQlxx(qlxxList);

        System.out.println(JSONObject.toJSONString(responseDTO, SerializerFeature.WriteMapNullValue));


    }

    private static List<GrdacxResponseQlr> qlrList(){
        List<GrdacxResponseQlr> qlrList = new ArrayList<>();
        GrdacxResponseQlr qlr = new GrdacxResponseQlr();
        qlr.setQlrmc("123");
        qlrList.add(qlr);
        return qlrList;
    }
}
