package cn.gtmap.realestate.exchange.core.dto.wwsq.dj.request;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYjxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.DsfSlxxDTO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.component.ExchangeFactory;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.InitSjrxx;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description //国土建设用地首次登记dto
 * @Date 2022/5/17 11:10
 **/
public class GyjsydscdjDto {

    /**
     * 实际dto
     */
    private List<GyjsydscdjDataDto> data;

    public static class GyjsydscdjDataDto{
        /**
         * 办件编号（对应工改系统业务流水号）
         */
        private String ywh;

        /**
         * 申请登记类型
         */
        private String sqdjlx;

        /**
         * 单位代码（合肥市/庐江县），用来存储bdc_xm.qjgldm
         */
        private String dwdm;


        /**
         * 不动产单元号
         */
        private String bdcdyh;
        /**
         * 权利人名称
         */
        private String qlrmc;
        /**
         * 权利人证件号
         */
        private String qlrzjh;
        /**
         * 权利人证件种类
         */
        private Integer qlrzjzl;
        /**
         * 代理人证件种类
         */
        private Integer dlrzjzl;
        /**
         * 代理人证件号
         */
        private String dlrzjh;
        
        /**
         * 审批事项编号
         */
        private String spsxbh;
        /**
         * 审批单位名称
         */
        private String spdwmc;
        /**
         * 审批单位代码
         */
        private String spdwdm;
      
        /**
         * 预约部门编码（对应合肥及庐江县办证大厅）,同互联网逻辑一致
         */
        private String yybmbm;
        /**
         * 物流信息节点（设计同互联网+，包含领证方式,收件人信息）
         */
        private List<InitSjrxx> wlxx;


        public String getYwh() {
            return ywh;
        }

        public void setYwh(String ywh) {
            this.ywh = ywh;
        }

        public String getSqdjlx() {
            return sqdjlx;
        }

        public void setSqdjlx(String sqdjlx) {
            this.sqdjlx = sqdjlx;
        }

        public String getDwdm() {
            return dwdm;
        }

        public void setDwdm(String dwdm) {
            this.dwdm = dwdm;
        }

        public String getBdcdyh() {
            return bdcdyh;
        }

        public void setBdcdyh(String bdcdyh) {
            this.bdcdyh = bdcdyh;
        }

        public String getQlrmc() {
            return qlrmc;
        }

        public void setQlrmc(String qlrmc) {
            this.qlrmc = qlrmc;
        }

        public String getQlrzjh() {
            return qlrzjh;
        }

        public void setQlrzjh(String qlrzjh) {
            this.qlrzjh = qlrzjh;
        }

        public Integer getQlrzjzl() {
            return qlrzjzl;
        }

        public void setQlrzjzl(Integer qlrzjzl) {
            this.qlrzjzl = qlrzjzl;
        }

        public Integer getDlrzjzl() {
            return dlrzjzl;
        }

        public void setDlrzjzl(Integer dlrzjzl) {
            this.dlrzjzl = dlrzjzl;
        }

        public String getDlrzjh() {
            return dlrzjh;
        }

        public void setDlrzjh(String dlrzjh) {
            this.dlrzjh = dlrzjh;
        }

        public String getSpsxbh() {
            return spsxbh;
        }

        public void setSpsxbh(String spsxbh) {
            this.spsxbh = spsxbh;
        }

        public String getSpdwmc() {
            return spdwmc;
        }

        public void setSpdwmc(String spdwmc) {
            this.spdwmc = spdwmc;
        }

        public String getSpdwdm() {
            return spdwdm;
        }

        public void setSpdwdm(String spdwdm) {
            this.spdwdm = spdwdm;
        }

        public String getYybmbm() {
            return yybmbm;
        }

        public void setYybmbm(String yybmbm) {
            this.yybmbm = yybmbm;
        }

        public List<InitSjrxx> getWlxx() {
            return wlxx;
        }

        public void setWlxx(List<InitSjrxx> wlxx) {
            this.wlxx = wlxx;
        }


        @Override
        public String toString() {
            return "GyjsydscdjDataDto{" +
                    "ywh='" + ywh + '\'' +
                    ", sqdjlx='" + sqdjlx + '\'' +
                    ", dwdm='" + dwdm + '\'' +
                    ", bdcdyh='" + bdcdyh + '\'' +
                    ", qlrmc='" + qlrmc + '\'' +
                    ", qlrzjh='" + qlrzjh + '\'' +
                    ", qlrzjzl=" + qlrzjzl +
                    ", dlrzjzl=" + dlrzjzl +
                    ", dlrzjh='" + dlrzjh + '\'' +
                    ", spsxbh='" + spsxbh + '\'' +
                    ", spdwmc='" + spdwmc + '\'' +
                    ", spdwdm='" + spdwdm + '\'' +
                    ", yybmbm='" + yybmbm + '\'' +
                    ", wlxx=" + wlxx +
                    '}';
        }

        /**
         * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
         * @Description //构建项目类模型集合
         * @Date 2022/5/17 10:07
         **/
        public static List<BdcSlXmDTO> buildBdcSlXmDTOList(GyjsydscdjDto gyjsydscdjDto) {
            return gyjsydscdjDto.getData().stream().map(item -> {
                BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
                // 构建  不动产受理项目
                bdcSlXmDTO.setBdcSlXmDO(buildBdcSlXmDO(item));
                // 构建  第三方受理项目属性实体
                bdcSlXmDTO.setDsfSlxxDTO(buildDsfSlxxDTO(item, item.getDwdm()));
                // 构建  第三权利人信息
                bdcSlXmDTO.setBdcSlSqrDOList(Lists.newArrayList(buildBdcDsQlrDO(item)));
                return bdcSlXmDTO;
            }).collect(Collectors.toList());
        }

        /**
         * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
         * @Description //构建  第三权利人信息
         * @Date 2022/5/17 10:09
         **/
        private static BdcSlSqrDO buildBdcDsQlrDO(GyjsydscdjDataDto item) {
            BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
            bdcSlSqrDO.setSqrmc(item.getQlrmc());
            bdcSlSqrDO.setZjh(item.getQlrzjh());
            bdcSlSqrDO.setZjzl(item.getQlrzjzl());
            bdcSlSqrDO.setDlrzjh(item.getDlrzjh());
            bdcSlSqrDO.setDlrzjzl(item.getDlrzjzl());
            return bdcSlSqrDO;
        }
        /**
         * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
         * @Description //构建  第三方受理项目属性实体
         * @Date 2022/5/17 10:09
         **/
        private static DsfSlxxDTO buildDsfSlxxDTO(GyjsydscdjDataDto item, String qxdm) {
            DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
            dsfSlxxDTO.setQxdm(
                    Optional.ofNullable(ExchangeFactory.getDsfZdToBdcDm().conver("DSF_ZD_QXDM", "wwsq", item.getYybmbm()))
                            .map(Object::toString).orElse("")
            );
            dsfSlxxDTO.setSpsxbh(item.getSpsxbh());
            dsfSlxxDTO.setSpdwdm(item.getSpdwdm());
            dsfSlxxDTO.setSpdwmc(item.getSpdwmc());
            return dsfSlxxDTO;
        }
        /**
         * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
         * @Description //构建  不动产受理项目
         * @Date 2022/5/17 10:08
         **/
        private static BdcSlXmDO buildBdcSlXmDO(GyjsydscdjDataDto item) {
            BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
            bdcSlXmDO.setBdcdyh(item.getBdcdyh());
            bdcSlXmDO.setQlr(item.getQlrmc());
            bdcSlXmDO.setSpxtywh(item.getYwh());
            return bdcSlXmDO;
        }
        /**
         * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
         * @Description //创建不动产受理邮寄信息DO
         * @Date 2022/5/17 11:05
         **/
        public static BdcSlYjxxDO buildBdcSlYjxxDO(GyjsydscdjDto gyjsydscdjDto) {
            List<InitSjrxx> wlxx = gyjsydscdjDto.getData().stream().findFirst().get().wlxx;
            BdcSlYjxxDO bdcSlYjxxDO = new BdcSlYjxxDO();
            if (CollectionUtils.isNotEmpty(wlxx)) {
                InitSjrxx initSjrxx = wlxx.get(0);
                bdcSlYjxxDO.setSjrmc(initSjrxx.getSjrmc());
                bdcSlYjxxDO.setSjrlxdh(initSjrxx.getSjrlxdh());
                bdcSlYjxxDO.setSjrszc(initSjrxx.getSjrszshi());
                bdcSlYjxxDO.setSjrszp(initSjrxx.getSjrszsheng());
                bdcSlYjxxDO.setSjrszx(initSjrxx.getSjrszx());
                bdcSlYjxxDO.setSjrxxdz(initSjrxx.getSjrxxdz());
            }
            return bdcSlYjxxDO;
        }
    }
    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //构造受理信息实体
     * @Date 2022/5/17 10:05
     **/
    public BdcSlxxDTO buildBdcSlxxDTO() {
        BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
        // 设置不动产受理基本信息
        bdcSlxxDTO.setBdcSlJbxx(buildBdcSlJbxxDO());
        // 设置项目类模型集合
        bdcSlxxDTO.setBdcSlXmList(GyjsydscdjDataDto.buildBdcSlXmDTOList(this));
        // 不动产受理邮寄信息
        bdcSlxxDTO.setBdcSlYjxxDO(GyjsydscdjDataDto.buildBdcSlYjxxDO(this));
        return bdcSlxxDTO;
    }
    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //不动产受理基本信息
     * @Date 2022/5/17 10:07
     **/
    public BdcSlJbxxDO buildBdcSlJbxxDO() {
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        bdcSlJbxxDO.setJbxxid(UUIDGenerator.generate());
        bdcSlJbxxDO.setGzldyid(Optional.ofNullable(this.data.get(0)).map(GyjsydscdjDataDto::getSqdjlx).orElse(""));
        return bdcSlJbxxDO;
    }


    public List<GyjsydscdjDataDto> getData() {
        return data;
    }

    public void setData(List<GyjsydscdjDataDto> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GyjsydscdjDto{" +
                "data=" + data +
                '}';
    }
}
