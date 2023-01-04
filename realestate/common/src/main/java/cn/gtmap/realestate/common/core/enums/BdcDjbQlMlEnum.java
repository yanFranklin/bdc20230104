package cn.gtmap.realestate.common.core.enums;

import cn.gtmap.realestate.common.util.CommonConstantUtils;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2019/1/17
 * @description 登记簿权利目录枚举类
 */
public class BdcDjbQlMlEnum {

    /**
    * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
    * @param
    * @return
    * @description 登记簿权利展现名称枚举
    */
    public enum BdcDjbQlEnum {
        BDC_DJB_QTCQ("qtcq", "非房屋产权"),
        BDC_DJB_FWCQ("fwcq", "房屋产权"),
        BDC_DJB_CF("cf", "查封登记信息"),
        BDC_DJB_DYA("dya", "抵押权登记信息"),
        BDC_DJB_DYI("dyi", "地役权登记信息"),
        BDC_DJB_YG("yg", "预告登记信息"),
        BDC_DJB_YY("yy", "异议登记信息"),
        BDC_DJB_JYQ("jyq", "经营权登记信息"),
        BDC_DJB_JZQ("jzq", "居住权登记信息");

        private String code;
        private String name;

        BdcDjbQlEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
    * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
    * @param
    * @return
    * @description 登记簿产权对应权利页面url地址
    */
    public enum BdcDjbQlUrlEnum {

        BDC_DJB_CF_URL("98", "bdcDjbCf.html?bdcdyh="),
        BDC_DJB_YG_URL("96", "bdcDjbYg.html?bdcdyh="),
        BDC_DJB_YY_URL("97", "bdcDjbYy.html?bdcdyh="),
        BDC_DJB_DYA_URL(CommonConstantUtils.QLLX_DYAQ_DM.toString(), "bdcDjbDyaq.html?bdcdyh="),
        BDC_DJB_DYI_URL("19", "bdcDjbDyiq.html?bdcdyh="),
        BDC_DJB_JYQ_URL("50", "bdcDjbTdjyq.html?bdcdyh="),
        BDC_DJB_JZQ_URL("92", "bdcDjbJzq.html?bdcdyh="),
        /**产权*/
        BDC_DJB_TDSYQ_URL("1,2", "bdcDjbTdsyq.html?bdcdyh="),
        BDC_DJB_JSYD_URL("3,5,7", "bdcDjbJsydsyqZjdsyq.html?bdcdyh="),
        BDC_DJB_FDCQ_URL("4,6,8", "bdcDjbFdcq.html?bdcdyh="),
        BDC_DJB_FDCQ_DZ_URL("4,6,8", "bdcDjbFdcqDz.html?bdcdyh="),
        BDC_DJB_LQ_URL("11,12,31,33,34,35,36", "bdcDjbLq.html?bdcdyh="),
        BDC_DJB_NYD_URL("9,13,14,23,30", "bdcDjbTdcbjyqNyddqtsyq.html?bdcdyh="),
        BDC_DJB_GZW_URL("16,18,24,25,26", "bdcDjbGjzwsyq.html?bdcdyh="),
        BDC_DJB_HY_URL("15,17", "bdcDjbHysyq.html?bdcdyh="),
        BDC_DJB_QT_URL("20,21,22,99", "bdcDjbQtxgql.html?bdcdyh="),
        BDC_DJB_JZWQFSYQYZGYBF_URL("94", "bdcDjbJzwqfsyqyzgybf.html?bdcdyh=");

        private String code;
        private String name;

        BdcDjbQlUrlEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public enum BdcDjbJbxxUrlEnum{

        BDC_DJB_FM_URL("djbfm", "bdcDjbfm.html?zdzhh="),
        BDC_DJB_ZD_URL("zdxx", "bdcDjbZdjbxx.html?zdzhh="),
        BDC_DJB_ZH_URL("zhxx", "bdcDjbZhjbxx.html?bdcdyh="),
        BDC_DJB_QLML_URL("qlml", "bdcDjbQlml.html?zdzhh="),
        BDC_DJB_BDCDYQLFM_URL("qlfm","bdcDjbQlfm.html?bdcdyh=");

        private String code;
        private String name;

        BdcDjbJbxxUrlEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}
