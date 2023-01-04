package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.exchange.NationalAccessFeignService;
import cn.gtmap.realestate.common.util.RestRpcUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/11/13
 * @description 查询登簿日志信息
 */
@RestController
@RequestMapping(value = "/dbrz")
public class BdcDbrzController extends BaseController {
	/**
	 * 登簿日志手动上报接口
	 */
	private static final String DBRZJK = "/realestate-exchange/rest/v1.0/access/dbrz/nt?date=#{date}&qxdm=#{qxdm}";

	/**
	 * 登簿日志上报固定IP应用上报（配置值例如 192.168.2.111:8080）
	 */
	@Value("${accessLog.gdyyip:}")
	private String accessLogGdyyip;

	@Autowired
	private RestRpcUtils restRpcUtils;
	@Autowired
	private NationalAccessFeignService nationalAccessFeignService;

	
	@GetMapping(value = "/mx")
	@ResponseStatus(HttpStatus.OK)
	public Object dbrzMx(String id) {
		if (StringUtils.isBlank(id)){
			throw new AppException("缺失参数！");
		}
		return nationalAccessFeignService.dbrzMx(id);
	}

	@GetMapping(value = "/nt")
	@ResponseStatus(HttpStatus.OK)
	public Object accessLogNt(String date, String qxdm) {
		if (StringUtils.isAnyBlank(date, qxdm)) {
			throw new AppException("缺失参数！");
		}

		if (StringUtils.isNotBlank(accessLogGdyyip)) {
			return fixedAppAccessLog(date, qxdm);
		} else {
			return nationalAccessFeignService.accessLogNt(date, qxdm);
		}
	}


	@GetMapping("/dbmxyl")
	@ResponseStatus(HttpStatus.OK)
	public Object dbrzmxyl(String date, String qxdm) {
		if (StringUtils.isAnyBlank(date, qxdm)) {
			throw new AppException("缺失参数！");
		}
		return nationalAccessFeignService.dbrzMxyl(date, qxdm);
	}

	@PostMapping("/dbwjr/plsb")
	@ResponseBody
	public void plsbWjr(@RequestBody List<String> xmidList) {
		LOGGER.warn("未接入登簿台账手动批量上报,更新未接入表处理状态为1xmid集合{}", xmidList);
		nationalAccessFeignService.autoAccessByXmidList(xmidList);
		//更新未接入数据的clzt为1 已处理重新上报
		nationalAccessFeignService.updateWjrZt(xmidList, 1);
	}

	/**
	 * 采用指定IP应用进行登簿日志上报
	 * 原因：登簿日志台账上报和程序自动上报不是同一个ip，省厅统计登簿日志数量时，按照部里制定的规则，不同ip会默认为两个地区上传的登簿日志，数量会累加。
	 * @param date 当前日期
	 * @param qxdm 区县代码
	 */
	private boolean fixedAppAccessLog(String date, String qxdm) {
		Map<String, Object> params = new HashMap<>(2);
		params.put("date", date);
		params.put("qxdm", qxdm);

		try {
			restRpcUtils.getRpcRequest(accessLogGdyyip, DBRZJK, params);
			LOGGER.info("定时登簿日志上报采用固定IP应用模式，调用应用：{}，服务地址：{}", accessLogGdyyip, DBRZJK);
			return true;
		} catch (Exception e) {
			LOGGER.error("定时登簿日志上报采用固定IP应用模式异常，继续采用调用当前应用上报逻辑，固定IP模式调用应用：{}、服务地址：{}", accessLogGdyyip, DBRZJK, e);
			return nationalAccessFeignService.accessLogNt(date, qxdm);
		}
	}
}
