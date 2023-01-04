package cn.gtmap.realestate.engine.core.dto;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzBdsTsxxDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzTsxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.engine.util.Constants;
import cn.gtmap.realestate.engine.util.DataUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/6/27
 * @description  规则验证表达式转换为Drools表达式信息
 */
public class BdcGzBdsTsxxDTO extends BdcGzBdsTsxxDO{
    /**
     * 转换后的Drools表达式
     */
    private String drool;

    /**
     * 规则验证KieBase对象
     */
    private KieBase kbase;


    public BdcGzBdsTsxxDTO(Builder builder) {
        this.setGzid(builder.bdcGzBdsTsxxDO.getGzid());
        this.setBdsid(builder.bdcGzBdsTsxxDO.getBdsid());
        this.setTsxx(builder.bdcGzBdsTsxxDO.getTsxx());
        this.setGzbds(builder.bdcGzBdsTsxxDO.getGzbds());

        this.drool = builder.drool;
        this.kbase = builder.kbase;
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 表达式实体建造者
     */
    public static class Builder {
        private BdcGzBdsTsxxDO bdcGzBdsTsxxDO;

        private String drool;

        private KieBase kbase;

        public Builder(BdcGzBdsTsxxDO bdcGzBdsTsxxDO){
            this.bdcGzBdsTsxxDO = bdcGzBdsTsxxDO;
        }

        public Builder drool(String drool){
            this.drool = drool;
            return this;
        }

        public Builder kbase(KieBase kbase){
            this.kbase = kbase;
            return this;
        }

        public BdcGzBdsTsxxDTO build(){
            return new BdcGzBdsTsxxDTO(this);
        }
    }

    // -----------------------------------------------------
    // 将针对规则验证表达式验证相关的操作封装到表达式实体自身内部
    // -----------------------------------------------------

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param queryResultList 验证表达式所在子规则数据流查询结果
     * @return {List} 验证后的提示信息
     * @description 执行验证表达式
     */
    public List<BdcGzZgzTsxxDTO> execute(List<BdcGzZgzTsxxDTO> queryResultList) throws CloneNotSupportedException {
        if(null == this.kbase){
            return Collections.emptyList();
        }

        // 执行验证
        this.batchVerify(queryResultList);

        // 处理返回信息
        return this.resolveReturnTipInfo(queryResultList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param queryResultList 验证表达式所在子规则数据流查询结果
     * @description 批量验证
     */
    private void batchVerify(List<BdcGzZgzTsxxDTO> queryResultList) {
        KieSession ksession = null;

        try {
            ksession = this.kbase.newKieSession();

            // 批量验证：一个验证表达式、多个验证参数执行的数据流结果
            for (BdcGzZgzTsxxDTO result : queryResultList) {
                if (MapUtils.isNotEmpty(result.getSjljg())) {
                    ksession.insert(result.getSjljg());
                }
            }

            ksession.fireAllRules();
        } catch (Exception e){
            throw new AppException(ErrorCode.RULE_CHECK_ERROR, "规则表达式" + super.getBdsid() + "执行发生错误");
        } finally {
            if(null != ksession){
                ksession.dispose();
            }
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param queryResultList 验证表达式所在子规则数据流查询结果
     * @description 处理返回提示信息
     */
    private List<BdcGzZgzTsxxDTO> resolveReturnTipInfo(List<BdcGzZgzTsxxDTO> queryResultList) throws CloneNotSupportedException {
        List<BdcGzZgzTsxxDTO> resultList = new ArrayList<>(queryResultList.size());

        for(BdcGzZgzTsxxDTO item : queryResultList){
            Object val = MapUtils.getObject(item.getSjljg(), Constants.RULE_RESULT);

            // 校验通过无需返回
            if(null == val || StringUtils.isBlank(String.valueOf(val))){
                continue;
            }

            // 处理提示信息
            List<String> tsxx = new ArrayList<>(1);
            tsxx.add(DataUtil.resolveTipInfo(String.valueOf(val), item.getSjljg(), item.getParam()));

            // 复制返回对象
            BdcGzZgzTsxxDTO result = (BdcGzZgzTsxxDTO) item.clone();
            result.setTsxx(tsxx);
            resultList.add(result);
        }

        return resultList;
    }
}
