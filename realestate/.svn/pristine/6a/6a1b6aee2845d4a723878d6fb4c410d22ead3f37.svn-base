/**
 * calendar - jQuery EasyUI
 *
 * Copyright (c) 2009-2013 www.jeasyui.com. All rights reserved.
 *
 * Licensed under the GPL or commercial licenses
 * To use it on other terms please contact us: jeasyui@gmail.com
 * http://www.gnu.org/licenses/gpl.txt
 * http://www.jeasyui.com/license_commercial.php
 * 二次开发 ____′↘夏悸
 * http://bbs.btboys.com Easyui中文社区
 */
(function ($) {
	//工作提醒中日历所需
	var holidayList = [];
	var workList = [];
	$.ajax({
		url: getContextPath() + "/rest/v1.0/user/queryWorkDays?startDate=''&endDate=''",
		type: 'get',
		async: false,
		contentType: "application/json;charset=utf-8",
		dataType: "json",
		success: function (data) {
			//console.log(data);
			holidayList = data.holidayList;
			workList = data.workList;
		}
	});

	/*****************************************************************************
	/*****************************************************************************
	日期资料
	 *****************************************************************************/
	var ttime = 0;
	var detail;
	var hideTimer;
	var Today = new Date();
	var tY = Today.getFullYear();
	var tM = Today.getMonth();
	var tD = Today.getDate();
	//alert(tY+"年"+tM+"月"+tD+"日");
	var tInfo = new Array(
			0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
			0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
			0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
			0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
			0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
			0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5b0, 0x14573, 0x052b0, 0x0a9a8, 0x0e950, 0x06aa0,
			0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
			0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b6a0, 0x195a6,
			0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
			0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
			0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
			0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
			0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
			0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
			0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0,
			0x14b63);
	
	var solarMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	var Gan = new Array("甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸");
	var Zhi = new Array("子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥");
	var Animals = new Array("鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪");
	var solarTerm = new Array("小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至");
	var sTermInfo = new Array(0, 21208, 42467, 63836, 85337, 107014, 128867, 150921, 173149, 195551, 218072, 240693, 263343, 285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224, 483532, 504758);
	var nStr1 = new Array('日', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十');
	var nStr2 = new Array('初', '十', '廿', '卅', '□');
	var monthName = new Array("正月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "冬月", "腊月");
	//国历节日 *表示放假日
	var sFtv = new Array(
			"0101*元旦",
			"0214 情人节",
			"0308 妇女节",
			"0312 植树节",
			"0401 愚人节",
			"0501*劳动节",
			"0504 青年节",
			"0801 建军节",
			"0910 教师节",
			"1001*国庆节",
			"1224 平安夜",
			"1225 圣诞节")
		
		//农历节日 *表示放假日
		var lFtv = new Array(
			"0101*春节",
			"0102*初二",
			"0115 元宵节",
			"0505*端午节",
			"0707 七夕",
			"0715 中元节",
			"0815*中秋节",
			"0909 重阳节",
			"1208 腊八",
			"1223 小年",
			"0100*除夕")
		
		//某月的第几个星期几
		var wFtv = new Array(
			"0150 世界麻风日", //一月的最后一个星期日（月倒数第一个星期日）
			"0520 国际母亲节",
			"0530 全国助残日",
			"0630 父亲节",
			"0730 被奴役国家周",
			"0932 国际和平日",
			"0940 国际聋人节 世界儿童日",
			"0950 世界海事日",
			"1011 国际住房日",
			"1013 国际减轻自然灾害日(减灾日)",
			"1144 感恩节")
		
		/*****************************************************************************
		日期计算
		 *****************************************************************************/
		
		//====================================== 返回农历 y年的总天数
	function lYearDays(y) {
		var i,
		sum = 348;
		for (i = 0x8000; i > 0x8; i >>= 1)
			sum += (tInfo[y - 1900] & i) ? 1 : 0;
		return (sum + leapDays(y));
	}
	
	//====================================== 返回农历 y年闰月的天数
	function leapDays(y) {
		if (leapMonth(y))
			return ((tInfo[y - 1900] & 0x10000) ? 30 : 29);
		else
			return (0);
	}
	
	//====================================== 返回农历 y年闰哪个月 1-12 , 没闰返回 0
	function leapMonth(y) {
		return (tInfo[y - 1900] & 0xf);
	}
	
	//====================================== 返回农历 y年m月的总天数
	function monthDays(y, m) {
		return ((tInfo[y - 1900] & (0x10000 >> m)) ? 30 : 29);
	}
	
	//====================================== 算出农历, 传入日期控件, 返回农历日期控件
	//                                       该控件属性有 .year .month .day .isLeap
	function Lunar(objDate) {
		
		var i,
		leap = 0,
		temp = 0;
		var offset = (Date.UTC(objDate.getFullYear(), objDate.getMonth(), objDate.getDate()) - Date.UTC(1900, 0, 31)) / 86400000;
		
		for (i = 1900; i < 2050 && offset > 0; i++) {
			temp = lYearDays(i);
			offset -= temp;
		}
		
		if (offset < 0) {
			offset += temp;
			i--;
		}
		
		this.year = i;
		
		leap = leapMonth(i); //闰哪个月
		this.isLeap = false;
		
		for (i = 1; i < 13 && offset > 0; i++) {
			//闰月
			if (leap > 0 && i == (leap + 1) && this.isLeap == false) {
				--i;
				this.isLeap = true;
				temp = leapDays(this.year);
			} else {
				temp = monthDays(this.year, i);
			}
			
			//解除闰月
			if (this.isLeap == true && i == (leap + 1))
				this.isLeap = false;
			
			offset -= temp;
		}
		
		if (offset == 0 && leap > 0 && i == leap + 1)
			if (this.isLeap) {
				this.isLeap = false;
			} else {
				this.isLeap = true;
				--i;
			}
		
		if (offset < 0) {
			offset += temp;
			--i;
		}
		
		this.month = i;
		this.day = offset + 1;
	}
	
	//==============================返回公历 y年某m+1月的天数
	function solarDays(y, m) {
		if (m == 1)
			return (((y % 4 == 0) && (y % 100 != 0) || (y % 400 == 0)) ? 29 : 28);
		else
			return (solarMonth[m]);
	}
	//============================== 传入 offset 返回干支, 0=甲子
	function cyclical(num) {
		return (Gan[num % 10] + Zhi[num % 12]);
	}
	
	//============================== 阴历属性
	function calElement(sYear, sMonth, sDay, week, lYear, lMonth, lDay, isLeap, cYear, cMonth, cDay) {
		
		this.isToday = false;
		//瓣句
		this.sYear = sYear; //公元年4位数字
		this.sMonth = sMonth; //公元月数字
		this.sDay = sDay; //公元日数字
		this.week = week; //星期, 1个中文
		//农历
		this.lYear = lYear; //公元年4位数字
		this.lMonth = lMonth; //农历月数字
		this.lDay = lDay; //农历日数字
		this.isLeap = isLeap; //是否为农历闰月?
		//八字
		this.cYear = cYear; //年柱, 2个中文
		this.cMonth = cMonth; //月柱, 2个中文
		this.cDay = cDay; //日柱, 2个中文
		
		this.color = '';
		
		this.lunarFestival = ''; //农历节日
		this.solarFestival = ''; //公历节日
		this.solarTerms = ''; //节气
	}
	
	//===== 某年的第n个节气为几日(从0小寒起算)
	function sTerm(y, n) {
		if (y == 2009 && n == 2) {
			sTermInfo[n] = 43467
		}
		var offDate = new Date((31556925974.7 * (y - 1900) + sTermInfo[n] * 60000) + Date.UTC(1900, 0, 6, 2, 5));
		return (offDate.getUTCDate());
	}
	
	//============================== 返回阴历控件 (y年,m+1月)
	/*
	功能说明: 返回整个月的日期资料控件
	
	使用方式: OBJ = new calendar(年,零起算月);
	
	OBJ.length      返回当月最大日
	OBJ.firstWeek   返回当月一日星期
	
	由 OBJ[日期].属性名称 即可取得各项值
	
	OBJ[日期].isToday  返回是否为今日 true 或 false
	
	其他 OBJ[日期] 属性参见 calElement() 中的注解
	 */
	function Calendar(y, m) {
		
		var sDObj,
		lDObj,
		lY,
		lM,
		lD = 1,
		lL,
		lX = 0,
		tmp1,
		tmp2,
		tmp3;
		var cY,
		cM,
		cD; //年柱,月柱,日柱
		var lDPOS = new Array(3);
		var n = 0;
		var firstLM = 0;
		
		sDObj = new Date(y, m, 1, 0, 0, 0, 0); //当月一日日期
		this.length = solarDays(y, m); //公历当月天数
		this.firstWeek = sDObj.getDay(); //公历当月1日星期几
		
		////////年柱 1900年立春后为庚子年(60进制36)
		if (m < 2)
			cY = cyclical(y - 1900 + 36 - 1);
		else
			cY = cyclical(y - 1900 + 36);
		
		var term2 = sTerm(y, 2); //立春日期
		
		////////月柱 1900年1月小寒以前为 丙子月(60进制12)
		var firstNode = sTerm(y, m * 2) //返回当月「节」为几日开始
			cM = cyclical((y - 1900) * 12 + m + 12);
		
		//当月一日与 1900/1/1 相差天数
		//1900/1/1与 1970/1/1 相差25567日, 1900/1/1 日柱为甲戌日(60进制10)
		var dayCyclical = Date.UTC(y, m, 1, 0, 0, 0, 0) / 86400000 + 25567 + 10;
		
		for (var i = 0; i < this.length; i++) {
			
			if (lD > lX) {
				sDObj = new Date(y, m, i + 1); //当月一日日期
				lDObj = new Lunar(sDObj); //农历
				lY = lDObj.year; //农历年
				lM = lDObj.month; //农历月
				lD = lDObj.day; //农历日
				lL = lDObj.isLeap; //农历是否闰月
				lX = lL ? leapDays(lY) : monthDays(lY, lM); //农历当月最后一天
				
				if (n == 0)
					firstLM = lM;
				lDPOS[n++] = i - lD + 1;
			}
			
			//依节气调整二月分的年柱, 以立春为界
			/*
			//PM提出线上2月3日，初一不是庚寅年，应该是辛卯年。
			by yuji
			
			这里firstNode是指农历每月的节气所在的日期，用这个标志判断
			农历每月起始日天干地支是错误的，应当用每月的农历初一所在日确定
			下月的天干地支。农历每月初一都要重新计算一下天干地支。
			 */
			if (m == 1 && ((i + 1) == term2 || lD == 1))
				cY = cyclical(y - 1900 + 36);
			
			//依节气月柱, 以「节」为界
			//if((i+1)==firstNode) cM = cyclical((y-1900)*12+m+13);
			
			/*
			by yuji
			
			这里firstNode是指农历每月的节气所在的日期，用这个标志判断
			农历每月起始日天干地支是错误的，应当用每月的农历初一所在日确定
			下月的天干地支。农历每月初一都要重新计算一下天干地支。
			 */
			if (lD == 1) {
				cM = cyclical((y - 1900) * 12 + m + 13);
				
			}
			
			//日柱
			cD = cyclical(dayCyclical + i);
			
			//sYear,sMonth,sDay,week,
			//lYear,lMonth,lDay,isLeap,
			//cYear,cMonth,cDay
			this[i] = new calElement(y, m + 1, i + 1, nStr1[(i + this.firstWeek) % 7],
					lY, lM, lD++, lL,
					cY, cM, cD);
		}
		
		//节气
		tmp1 = sTerm(y, m * 2) - 1;
		tmp2 = sTerm(y, m * 2 + 1) - 1;
		this[tmp1].solarTerms = solarTerm[m * 2];
		this[tmp2].solarTerms = solarTerm[m * 2 + 1];
		//guohao
		if (y == 2009 && m == 1) {
			if (tD == 3) {
				this[tmp1].solarTerms = ''
					//this[tmp2].solarTerms = ''
			} else if (tD == 4) {
				this[tmp1].solarTerms = '立春'
					//this[tmp2].solarTerms = ''
			}
		}
		if (m == 3)
			this[tmp1].color = 'red'; //清明颜色
		
		//公历节日
		for (i in sFtv)
			if (sFtv[i].match(/^(\d{2})(\d{2})([\s\*])(.+)$/))
				if (Number(RegExp.$1) == (m + 1)) {
					this[Number(RegExp.$2) - 1].solarFestival += RegExp.$4 + ' ';
					if (RegExp.$3 == '*')
						this[Number(RegExp.$2) - 1].color = 'red';
				}
		
		//月周节日
		//for (i in wFtv)
		//	if (wFtv[i].match(/^(\d{2})(\d)(\d)([\s\*])(.+)$/))
		//		if (Number(RegExp.$1) == (m + 1)) {
		//			tmp1 = Number(RegExp.$2);
		//			tmp2 = Number(RegExp.$3);
		//			if (tmp1 < 5)
		//				this[((this.firstWeek > tmp2) ? 7 : 0) + 7 * (tmp1 - 1) + tmp2 - this.firstWeek].solarFestival += RegExp.$5 + ' ';
		//			else {
		//				tmp1 -= 5;
		//				tmp3 = (this.firstWeek + this.length - 1) % 7; //当月最后一天星期?
		//				this[this.length - tmp3 - 7 * tmp1 + tmp2 - (tmp2 > tmp3 ? 7 : 0) - 1].solarFestival += RegExp.$5 + ' ';
		//			}
		//		}
		
		//农历节日
		for (i in lFtv)
			if (lFtv[i].match(/^(\d{2})(.{2})([\s\*])(.+)$/)) {
				tmp1 = Number(RegExp.$1) - firstLM;
				if (tmp1 == -11)
					tmp1 = 1;
				if (tmp1 >= 0 && tmp1 < n) {
					tmp2 = lDPOS[tmp1] + Number(RegExp.$2) - 1;
					if (tmp2 >= 0 && tmp2 < this.length && this[tmp2].isLeap != true) {
						this[tmp2].lunarFestival += RegExp.$4 + ' ';
						if (RegExp.$3 == '*')
							this[tmp2].color = 'red';
					}
				}
			}
		
		//复活节只出现在3或4月
		if (m == 2 || m == 3) {
			var estDay = new easter(y);
			if (m == estDay.m)
				this[estDay.d - 1].solarFestival = this[estDay.d - 1].solarFestival + ' 复活节';
		}
		
		//if (m == 2)
		//	this[20].solarFestival = this[20].solarFestival + unescape('%20%u6D35%u8CE2%u751F%u65E5');

		
		//今日
		if (y == tY && m == tM)
			this[tD - 1].isToday = true;
	}
	
	//======================================= 返回该年的复活节(春分后第一次满月周后的第一主日)
	function easter(y) {
		
		var term2 = sTerm(y, 5); //取得春分日期
		var dayTerm2 = new Date(Date.UTC(y, 2, term2, 0, 0, 0, 0)); //取得春分的公历日期控件(春分一定出现在3月)
		var lDayTerm2 = new Lunar(dayTerm2); //取得取得春分农历
		
		if (lDayTerm2.day < 15) //取得下个月圆的相差天数
			var lMlen = 15 - lDayTerm2.day;
		else
			var lMlen = (lDayTerm2.isLeap ? leapDays(y) : monthDays(y, lDayTerm2.month)) - lDayTerm2.day + 15;
		
		//一天等于 1000*60*60*24 = 86400000 毫秒
		var l15 = new Date(dayTerm2.getTime() + 86400000 * lMlen); //求出第一次月圆为公历几日
		var dayEaster = new Date(l15.getTime() + 86400000 * (7 - l15.getUTCDay())); //求出下个周日
		
		this.m = dayEaster.getUTCMonth();
		this.d = dayEaster.getUTCDate();

	}

	//====================== 中文日期
	function cDay(d, m,dt) {
		var s;
		switch (d) {
		case 1:
			s = monthName[m - 1];
			if(dt){
			s = '初一';
			}
			break;
		case 10:
			s = '初十';
			break;
		case 20:
			s = '二十';
			break;
		case 30:
			s = '三十';
			break;
		default:
			s = nStr2[Math.floor(d / 10)];
			s += nStr1[d % 10];
		}
		return (s);
	}
	
	var detailTpl = '<div style="position: absolute;visibility: hidden;"><div></div></div>';
	var favTpl = '<font color="#000000" style="font-size: 12px;">{fav}</font>';
	function setSize(target) {
		var opts = $.data(target, 'fullCalendar').options;
		var t = $(target);
		if (opts.fit == true) {
			var p = t.parent();
			opts.width = p.width();
		   opts.height = p.height();
		}
		var header = t.find('.calendar-header');
		t._outerWidth(opts.width);
		t._outerHeight(opts.height);
		t.find('.calendar-body')._outerHeight(t.height() - header._outerHeight());
	}
	
	
	
	
	//初始化方法
	
	
	function init(target) {
		$(target).addClass('calendar').wrapInner(
			'<div class="calendar-header">' +
			/*'<a class="calendar-prevmonth"></a>' +
			'<a class="calendar-nextmonth"></a>' +
			'<a class="calendar-prevyear"></a>' +
			'<a class="calendar-nextyear"></a>' +
			'<a class="calendar-title">' +
			'<span>Aprial 2010</span>' +
			'</a>' +*/
			'<a class="calendar-prevyear"><i class="layui-icon layui-icon-prev"></i></a>'+
			'&nbsp;'+
			'<a class="calendar-year"></a>'+
			'&nbsp;'+
			'<a class="calendar-nextyear"><i class="layui-icon layui-icon-next"></i></a>'+
			'&nbsp;'+
			'<a class="calendar-prevmonth"><i class="layui-icon layui-icon-left"></i></a>'+
			'&nbsp;'+
			'<a class="calendar-month"></a>'+
			'&nbsp;'+
			'<a class="calendar-nextmonth"><i class="layui-icon layui-icon-right"></i></a>'+
			'<a class="calendar-title">'+
			'<span>Aprial 2010</span>'+
			'</a>'+
			'</div>' +
			'<div class="calendar-body fullcalendar-body">' +
			'<div class="calendar-menu">' +
			'<div class="calendar-menu-year-inner">' +
			'<span class="calendar-menu-prev"></span>' +
			'<span><input class="calendar-menu-year" type="text"></input></span>' +
			'<span class="calendar-menu-next"></span>' +
			'</div>' +
			'<div class="calendar-menu-month-inner">' +
			'</div>' +
			'</div>' +
			'</div>');
		detail = $('div.fullcalendar-detail');
		if (!detail.length) {
			detail = $('<div class="fullcalendar-detail"/>').appendTo('.easyui-layout');
	   
			detail.hover(function () {
				clearTimeout(hideTimer);
			}, function () {
				$(this).hide();
			});
		}
		
		$(target).find('.calendar-title span').hover(
			function () {
			$(this).addClass('calendar-menu-hover');
		},
			function () {
			$(this).removeClass('calendar-menu-hover');
		}).click(function () {
			var menu = $(target).find('.calendar-menu');
			if (menu.is(':visible')) {
				menu.hide();
			} else {
				showSelectMenus(target);
			}
		});
		
		$('.calendar-prevmonth,.calendar-nextmonth,.calendar-prevyear,.calendar-nextyear', target).hover(
			function () {
			$(this).addClass('calendar-nav-hover');
		},
			function () {
			$(this).removeClass('calendar-nav-hover');
		});
		$(target).find('.calendar-nextmonth').click(function () {
			showMonth(target, 1);
		});
		$(target).find('.calendar-prevmonth').click(function () {
			showMonth(target, -1);
		});
		$(target).find('.calendar-nextyear').click(function () {
			showYear(target, 1);
		});
		$(target).find('.calendar-prevyear').click(function () {
            showYear(target, -1);
		});
		
		$(target).bind('_resize', function () {
			var opts = $.data(target, 'fullCalendar').options;
			if (opts.fit == true) {
				setSize(target);
			}
			return false;
		});
	}
	
	/**
	 * show the calendar corresponding to the current month 点击切换 显示对应于当前的日历月。
	 */
	function showMonth(target, delta) {
		var opts = $.data(target, 'fullCalendar').options;
        opts.month = parseInt(opts.month);
		opts.month += delta;
		if (opts.month > 12) {
			opts.year++;
			opts.month = 1;
		} else if (opts.month < 1) {
			opts.year--;
			opts.month = 12;
		}
		show(target);
		
		var menu = $(target).find('.calendar-menu-month-inner');
		menu.find('td.calendar-selected').removeClass('calendar-selected');
		menu.find('td:eq(' + (opts.month - 1) + ')').addClass('calendar-selected');
		//updateWeekWords();
	}
	
	/**
	 * show the calendar corresponding to the current year. 点击切换 显示相应日历 年
	 */
	function showYear(target, delta) {
		var opts = $.data(target, 'fullCalendar').options;
        opts.year = parseInt(opts.year);
        opts.year += delta;
		show(target);
		
		var menu = $(target).find('.calendar-menu-year');
		menu.val(opts.year);
		//updateWeekWords();
	}
	
	/**
	 * show the select menu that can change year or month, if the menu is not be created then create it.显示可以改变年或月的选择菜单,如果不创建菜单然后创建它。
	 */
	function showSelectMenus(target) {
		var opts = $.data(target, 'fullCalendar').options;
		$(target).find('.calendar-menu').show();
		
		if ($(target).find('.calendar-menu-month-inner').is(':empty')) {
			$(target).find('.calendar-menu-month-inner').empty();
			var t = $('<table cellspacing="5" cellpadding="5" ></table>').appendTo($(target).find('.calendar-menu-month-inner'));
			var idx = 0;
			for (var i = 0; i < 3; i++) {
				var tr = $('<tr></tr>').appendTo(t);
				for (var j = 0; j < 4; j++) {
			 $('<td class="calendar-menu-month"></td>').html(opts.months[idx++]).attr('abbr', idx).appendTo(tr);
				}
			}
			
			$(target).find('.calendar-menu-prev,.calendar-menu-next').hover(
				function () {
				$(this).addClass('calendar-menu-hover');
			},
				function () {
				$(this).removeClass('calendar-menu-hover');
			});
			$(target).find('.calendar-menu-next').click(function () {
				var y = $(target).find('.calendar-menu-year');
				if (!isNaN(y.val())) {
					y.val(parseInt(y.val()) + 1);
				}
			});
			$(target).find('.calendar-menu-prev').click(function () {
				var y = $(target).find('.calendar-menu-year');
				if (!isNaN(y.val())) {
					y.val(parseInt(y.val() - 1));
				
				}
			});
			
			$(target).find('.calendar-menu-year').keypress(function (e) {
				if (e.keyCode == 13) {
					setDate();
				}
			});
			
			$(target).find('.calendar-menu-month').hover(
				function () {
				$(this).addClass('calendar-menu-hover');
			},
				function () {
				$(this).removeClass('calendar-menu-hover');
			}).click(function () {
				var menu = $(target).find('.calendar-menu');
				menu.find('.calendar-selected').removeClass('calendar-selected');
				$(this).addClass('calendar-selected');
				setDate();
			});
		}
		
		function setDate() {
			var menu = $(target).find('.calendar-menu');
			var year = menu.find('.calendar-menu-year').val();
			var month = menu.find('.calendar-selected').attr('abbr');
			if (!isNaN(year)) {
				opts.year = parseInt(year);
				opts.month = parseInt(month);
				show(target);
			}
			menu.hide();
		}
		
		var body = $(target).find('.calendar-body');
		var sele = $(target).find('.calendar-menu');
		var seleYear = sele.find('.calendar-menu-year-inner');
		var seleMonth = sele.find('.calendar-menu-month-inner');
		
		seleYear.find('input').val(opts.year).focus();
		seleMonth.find('td.calendar-selected').removeClass('calendar-selected');
		seleMonth.find('td:eq(' + (opts.month - 1) + ')').addClass('calendar-selected');
		
		sele._outerWidth($('.easyui-layout')._outerWidth());
		sele._outerHeight($('.easyui-layout')._outerHeight());
		seleMonth._outerHeight(sele.height() - seleYear._outerHeight());
	}
	
	/**
	 * get weeks data. 获得周数据
	 */
	function getWeeks(target, year, month) {
		var opts = $.data(target, 'fullCalendar').options;
		var dates = [];
		var lastDay = new Date(year, month, 0).getDate();
		for (var i = 1; i <= lastDay; i++)
			dates.push([year, month, i]);
		
		// group date by week  周数组
		var weeks = [],
		week = [];
		//		var memoDay = 0;
		var memoDay = -1;
		while (dates.length > 0) {
			var date = dates.shift();
			week.push(date);
			var day = new Date(date[0], date[1] - 1, date[2]).getDay();
			if (memoDay == day) {
				day = 0;
			} else if (day == (opts.firstDay == 0 ? 7 : opts.firstDay) - 1) {
				weeks.push(week);
				week = [];
			}
			memoDay = day;
		}
		if (week.length) {
			weeks.push(week);
		}
		
		var firstWeek = weeks[0];
		   // alert(firstWeek);
		if (firstWeek.length < 7) {
			while (firstWeek.length < 7) {
				var firstDate = firstWeek[0];
				var date = new Date(firstDate[0], firstDate[1] - 1, firstDate[2] - 1)
					firstWeek.unshift([date.getFullYear(), date.getMonth() + 1, date.getDate()]);
			}
		} else {
			var firstDate = firstWeek[0];
			var week = [];
			for (var i = 1; i <= 7; i++) {
				var date = new Date(firstDate[0], firstDate[1] - 1, firstDate[2] - i);
				week.unshift([date.getFullYear(), date.getMonth() + 1, date.getDate()]);
			}
			weeks.unshift(week);
		}
		
		var lastWeek = weeks[weeks.length - 1];
		while (lastWeek.length < 7) {
			var lastDate = lastWeek[lastWeek.length - 1];
			var date = new Date(lastDate[0], lastDate[1] - 1, lastDate[2] + 1);
			lastWeek.push([date.getFullYear(), date.getMonth() + 1, date.getDate()]);
		}
		if (weeks.length < 6) {
			var lastDate = lastWeek[lastWeek.length - 1];
			var week = [];
			for (var i = 1; i <= 7; i++) {
				var date = new Date(lastDate[0], lastDate[1] - 1, lastDate[2] + i);
				week.push([date.getFullYear(), date.getMonth() + 1, date.getDate()]);
			}
			weeks.push(week);
		}
		
		return weeks;
	}
	/**
	 * show the calendar day. 显示日历天
	 */
	function show(target) {
		var opts = $.data(target, 'fullCalendar').options;

		if (opts.year > 1874 && opts.year < 1909)
			yDisplay = '光绪' + (((opts.year - 1874) == 1) ? '元' : opts.year - 1874);
		if (opts.year > 1908 && opts.year < 1912)
			yDisplay = '宣统' + (((opts.year - 1908) == 1) ? '元' : opts.year - 1908);
		
		if (opts.year > 1911)
			yDisplay = '建国' + (((opts.year - 1949) == 1) ? '元' : opts.year - 1949);
		
	//$(target).find('.calendar-title span').html(opts.months[opts.month - 1] + ' ' + opts.year + ' ' + yDisplay + '年 农历 ' + cyclical(opts.year - 1900 + 36) + '年 【' + Animals[(opts.year - 4) % 12] + '年】');
	// 这是日历头部
	 $(target).find('.calendar-year').html(opts.year+'年');
     $(target).find('.calendar-month').html(opts.months[opts.month - 1]+'月');
     $(target).find('.calendar-title span').html(yDisplay + '年 农历 ' + cyclical(opts.year - 1900 + 36) + '年 【' + Animals[(opts.year - 4) % 12] + '年】')	 
	


	var body = $(target).find('div.calendar-body');
		body.find('>table').remove();
		
		var t = $('<table cellspacing="0" cellpadding="0" border="0"><thead></thead><tbody></tbody></table>').prependTo(body);
	var tr = $('<tr></tr>').appendTo(t.find('thead'));

        opts.weeks = ['日','一','二','三','四','五','六'];
        for (var i = opts.firstDay; i < opts.weeks.length; i++) {
			tr.append('<th>' + opts.weeks[i] + '</th>');
		}
		for (var i = 0; i < opts.firstDay; i++) {
			tr.append('<th>' + opts.weeks[i] + '</th>');
		}

		var weeks = getWeeks(target, opts.year, opts.month);

		var currentCa = new Calendar(opts.year, opts.month - 1);
		for (var i = 0; i < weeks.length; i++) {
			var week = weeks[i];
			var tr = $('<tr></tr>').appendTo(t.find('tbody'));
			for (var j = 0; j < week.length; j++) {
				var day = week[j];
                var bjMonth = bjNumber(day[1]);
                var bjDay = bjNumber(day[2]);
                var getTdDay = day[0] + '-' + bjMonth + '-' + bjDay;
                var bzHtml = '';
                if(holidayList.indexOf(getTdDay) > -1){
                    bzHtml = '<span class="bdc-special-day bdc-work-msg-blue">休</span>';
                }else if(workList.indexOf(getTdDay) > -1){
                    bzHtml = '<span class="bdc-special-day bdc-work-msg-red">班</span>';
                }
				var dayHtml = '<p class="bdc-calendar-day">' + day[2] + bzHtml + '</p>';
				var info = null;
                if (opts.month == day[1]) {
					info = currentCa[day[2] - 1];
					if (info.color) {
						var color = 'color="' + info.color + '"';
						dayHtml = '<p class="bdc-calendar-day" ' + color + '>' + day[2] + bzHtml + '</p>';
					}
					if (opts.lunarDay) {
						if (opts.solarTerms && info.solarTerms) {
							dayHtml += '<span color="#E0366E">' + info.solarTerms + '</span>';
						}else if(opts.lunarFestival && info.lunarFestival){
                            dayHtml += '<span color="#E0366E">' + info.lunarFestival + '</span>';
                        }else if(opts.solarFestival && info.solarFestival){
                            dayHtml += '<span color="#E0366E">' + info.solarFestival + '</span>';
                        }else {
							dayHtml += '<span size="2" style="font-size:12px">' + cDay(info.lDay, info.lMonth) + '</span>';
						}
					}
				}

				var day = $('<td class="calendar-day fullcalendar-day calendar-other-month"></td>').data('info', info).attr('abbr', day[0] + '-' + day[1] + '-' + day[2]).html(dayHtml).appendTo(tr);
     			if (info && (info.lunarFestival || info.solarFestival)) {
					day.addClass('festival');
				}
				// day.hover(function (e) {
				// 	clearTimeout(hideTimer);
				// 	var inf = $(this).data('info');
                //
				// 	if (inf) {
				// 		var ct = '<font color="#ffffff" style="font-size:12px;">' + inf.sYear
				// 			 + ' 年 ' + inf.sMonth + ' 月 ' + inf.sDay + ' 日<br>星期' + inf.week
				// 			 + '<br><font color="white">农历 ' + monthName[inf.lMonth - 1] + ' 月 ' + cDay(inf.lDay, inf.lMonth,true)
				// 			 + ' 日</font><br><font color="yellow">' + inf.cYear + '年 ' + inf.cMonth + '月 ' + inf.cDay + '日</font></font>';
				// 		detail.html(ct);
                //
				// 		detail.css(calculatePos.call(target, detail, e.currentTarget)).fadeIn();
				// 		if (inf.lunarFestival) {
				// 			detail.append('<div class="lunarFestival">' + inf.lunarFestival + '</div>');
				// 		}
				// 		if (inf.solarFestival) {
				// 			detail.append('<div class="solarFestival">' + inf.solarFestival + '</div>');
				// 		}
				// 	} else {
				// 		detail.hide();
				// 	}
				// }, function () {
				// 	hideTimer = setTimeout(function () {
				// 			$('div.fullcalendar-detail').hide();
				// 		}, 500);
				// });
			}
		}
        function bjNumber(num){
            var resultNum = '';
            if(num > 9){
                resultNum = num;
            }else{
                resultNum = '0' + num;
            }
            return resultNum;
        }
	 t.find('td[abbr^="' + opts.year + '-' + opts.month + '"]').removeClass('calendar-other-month');
	
		var now = new Date();
		
		var today = now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate();
	//	alert(today);
		t.find('td[abbr="' + today + '"]').addClass('calendar-today');
		
		if (opts.current) {
			t.find('.calendar-selected').removeClass('calendar-selected');
			var current = opts.current.getFullYear() + '-' + (opts.current.getMonth() + 1) + '-' + opts.current.getDate();
		//	alert(current);
			t.find('td[abbr="' + current + '"]').addClass('calendar-selected');
		}
		
		// calulate the saturday and sunday index   日历悬浮样式和点击样式
		var saIndex = 6 - opts.firstDay;
		var suIndex = saIndex + 1;
		if (saIndex >= 7)
			saIndex -= 7;
		if (suIndex >= 7)
			suIndex -= 7;
	   t.find('tr').find('td:eq(' + saIndex + ')').addClass('calendar-saturday');
	   t.find('tr').find('td:eq(' + suIndex + ')').addClass('calendar-sunday');

		t.find('td').hover(
			function () {
			$(this).addClass('calendar-hover');
		},
			function () {
			$(this).removeClass('calendar-hover');
		}).click(function () {
            t.find('.calendar-selected').removeClass('calendar-selected');
			$(this).addClass('calendar-selected');
			
		    //获取到当前日期	
         var parts = $(this).attr('abbr').split('-');
     
        //动态添加div给予赋值
        	window.parent.$("#iframe").append('<div class="div_val">'+parts+'</div>');
        //获取div的值 	
           var div_val=window.parent.$("#iframe").find(".div_val").text();
        //替换 所有，逗号   
           var rep= div_val.replace(/\,/g,'-');
        //判断div是否存在     存在就移除掉
           if(window.parent.$("#iframe").find(".div_val").hasClass("div_val")){
           	window.parent.$("#iframe").find(".div_val").remove();
           }
           
        //把值赋给文本框   
           window.parent.$("#iframe").find("#J-xl").val(rep);
           

	       
	      if($(this).onclick=true){
	      	window.parent.$("#iframepage").fadeOut();
	      }
		  
			opts.current = new Date(parts[0], parseInt(parts[1]) - 1, parts[2]);
			 // alert(parts[0]); 输出是年 ;
           
	       opts.onSelect.call(target, opts.current, this);

            if (opts.month != parts[1]) {
                opts.year = parts[0];
				opts.month = parts[1];
				show(target);
			}
		});
		
	
	
		
		opts.onChange.call(target, opts.year, opts.month);
		
	
		
		
		//alert(opts.year+"年"+opts.month+"月"+target);
		
	}
	
	
	
	//easyui-fullCalendar calendar   获取这class 高度宽度
	function calculatePos(target, currentTarget) {
		var w = $(this).width(),
		h = $(this).height();
		var x = getElementLeft(currentTarget) + currentTarget.offsetWidth,
		y = getElementTop(currentTarget);
		
		if (x + $(target).width() > w) {
			x = x - $(target).width() - currentTarget.offsetWidth - 9;
		}
		if (y + $(target).height() > h) {
			y = h - $(target).height() - 28;
		}
		return {
			left : x,
			top : y
		};
	}
	
	function getElementLeft(element) {
		var actualLeft = element.offsetLeft;
		var current = element.offsetParent;
		while (current !== null) {
			actualLeft += current.offsetLeft;
			current = current.offsetParent;
		}
		return actualLeft;
	}
	
	function getElementTop(element) {
		var actualTop = element.offsetTop;
	
		
		var current = element.offsetParent;
	
		while (current !== null) {
			actualTop += current.offsetTop;
			current = current.offsetParent;
		}
		return actualTop;
	}
	
	$.fn.fullCalendar = function (options, param) {
	
		if (typeof options == 'string') {
			return $.fn.fullCalendar.methods[options](this, param);
		}
			
		options = options || {};
		return this.each(function () {
			var state = $.data(this, 'fullCalendar');
		
			if (state) {
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'fullCalendar', {
						options : $.extend({}, $.fn.fullCalendar.defaults, $.fn.calendar.defaults, $.fn.fullCalendar.parseOptions(this), options)
					});
				init(this);
			}
			if (state.options.border == false) {
				$(this).addClass('calendar-noborder');
			}
			setSize(this);
			show(this);
			$(this).find('div.calendar-menu').hide(); // hide the calendar menu
		});
	};
	$.fn.fullCalendar.methods = {
		options : function (jq) {
			return $.data(jq[0], 'fullCalendar').options;
		},
		resize : function (jq) {
			return jq.each(function () {
				setSize(this);
			});
		},
		moveTo : function (jq, date) {
			return jq.each(function () {
				$(this).fullCalendar({
					year : date.getFullYear(),
					month : date.getMonth() + 1,
					current : date
				});
			});
		}
	};
	
	$.fn.fullCalendar.parseOptions = function (target) {
		var t = $(target);
		return $.extend({}, $.parser.parseOptions(target, [
					'width', 'height', {
						firstDay : 'number',
						fit : 'boolean',
						border : 'boolean'
					}
				]));
	};
	
	$.fn.fullCalendar.defaults = {
		width : 180,
		height : 180,
		fit : false,
		border : true,
		firstDay : 0,
		weeks : ['日', '一', '二', '三', '四', '五', '六'],
		months : ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'],
		year : new Date().getFullYear(),//完整的年份
		month : new Date().getMonth() + 1, //当前月份
		current : new Date(),//时间
		solarTerms : true, //显示二十四节气
		lunarFestival: true,
        solarFestival: true,
		lunarDay : true, //显示农历
		onSelect : function (date, target) {},
		onChange : function (year, month) {}
	};
	$.parser.plugins.push('fullCalendar');
})(jQuery);
