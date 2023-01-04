package cn.gtmap.realestate.inquiry.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2019/11/6
 * @description 财务登记费用户信息
 */
@Component
@ConfigurationProperties(prefix = "finance")
public class FinanceConfig {
	
	/**
	 * 财务登记费用户配置
	 */
	private Map<String, Map<String,String>> djfMap = new HashMap<>();
	
	/**
	 * 银行名称列表
	 */
	private List<String> yhmcList = new ArrayList<>();
	
	public Map<String, Map<String, String>> getDjfMap() {
		return djfMap;
	}
	
	public void setDjfMap(Map<String, Map<String, String>> djfMap) {
		this.djfMap = djfMap;
	}
	
	public List<String> getYhmcList() {
		return yhmcList;
	}
	
	public void setYhmcList(List<String> yhmcList) {
		this.yhmcList = yhmcList;
	}
}
