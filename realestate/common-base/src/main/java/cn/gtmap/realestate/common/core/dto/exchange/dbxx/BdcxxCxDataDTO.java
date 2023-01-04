package cn.gtmap.realestate.common.core.dto.exchange.dbxx;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wangyinghao
 */
public class BdcxxCxDataDTO {
    String isSuccessful;

    String message;

    String bdcdyh;

    BdcxxCxData data;

    public String getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(String isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public BdcxxCxData getData() {
        return data;
    }

    public void setData(BdcxxCxData data) {
        this.data = data;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class BdcxxCxData {
        List<BdxxCxTdsyqDTO> tdsyqxx;
        List<BdxxCxJsydsyqDTO> jsydsyqxx;
        List<BdxxCxFdcqDTO> fdcqxx;
        List<BdxxCxCfDTO> cfdjxx;
        List<BdxxCxDyaqDTO> dyaqxx;
        List<BdxxCxHysyqDTO> hysyqxx;
        List<BdxxCxGjzwsyqDTO> gjzwsyqxx;
        List<BdxxCxLqDTO> lqxx;
        List<BdxxCxYgDTO> ygdjxx;
        List<BdxxCxTdcbnydsyqDTO> nydsyqxx;
        List<BdxxCxYyDTO> yydjxx;

        public List<BdxxCxTdsyqDTO> getTdsyqxx() {
            return tdsyqxx;
        }

        public void setTdsyqxx(List<BdxxCxTdsyqDTO> tdsyqxx) {
            this.tdsyqxx = tdsyqxx;
        }

        public List<BdxxCxJsydsyqDTO> getJsydsyqxx() {
            return jsydsyqxx;
        }

        public void setJsydsyqxx(List<BdxxCxJsydsyqDTO> jsydsyqxx) {
            this.jsydsyqxx = jsydsyqxx;
        }

        public List<BdxxCxFdcqDTO> getFdcqxx() {
            return fdcqxx;
        }

        public void setFdcqxx(List<BdxxCxFdcqDTO> fdcqxx) {
            this.fdcqxx = fdcqxx;
        }

        public List<BdxxCxCfDTO> getCfdjxx() {
            return cfdjxx;
        }

        public void setCfdjxx(List<BdxxCxCfDTO> cfdjxx) {
            this.cfdjxx = cfdjxx;
        }

        public List<BdxxCxDyaqDTO> getDyaqxx() {
            return dyaqxx;
        }

        public void setDyaqxx(List<BdxxCxDyaqDTO> dyaqxx) {
            this.dyaqxx = dyaqxx;
        }

        public List<BdxxCxHysyqDTO> getHysyqxx() {
            return hysyqxx;
        }

        public void setHysyqxx(List<BdxxCxHysyqDTO> hysyqxx) {
            this.hysyqxx = hysyqxx;
        }

        public List<BdxxCxGjzwsyqDTO> getGjzwsyqxx() {
            return gjzwsyqxx;
        }

        public void setGjzwsyqxx(List<BdxxCxGjzwsyqDTO> gjzwsyqxx) {
            this.gjzwsyqxx = gjzwsyqxx;
        }

        public List<BdxxCxLqDTO> getLqxx() {
            return lqxx;
        }

        public void setLqxx(List<BdxxCxLqDTO> lqxx) {
            this.lqxx = lqxx;
        }

        public List<BdxxCxYgDTO> getYgdjxx() {
            return ygdjxx;
        }

        public void setYgdjxx(List<BdxxCxYgDTO> ygdjxx) {
            this.ygdjxx = ygdjxx;
        }

        public List<BdxxCxTdcbnydsyqDTO> getNydsyqxx() {
            return nydsyqxx;
        }

        public void setNydsyqxx(List<BdxxCxTdcbnydsyqDTO> nydsyqxx) {
            this.nydsyqxx = nydsyqxx;
        }

        public List<BdxxCxYyDTO> getYydjxx() {
            return yydjxx;
        }

        public void setYydjxx(List<BdxxCxYyDTO> yydjxx) {
            this.yydjxx = yydjxx;
        }
    }
}
