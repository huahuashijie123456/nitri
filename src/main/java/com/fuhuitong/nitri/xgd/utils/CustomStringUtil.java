package com.fuhuitong.nitri.xgd.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类-字符串处理
 *
 * @author xx
 * @version 2.0
 * @since 2014年1月28日
 */
@Slf4j
public class CustomStringUtil
{
	/**
	 * 字符串空处理，去除首尾空格 如果str为null，返回"",否则返回str
	 *
	 * @param str
	 * @return
	 */
	public static String isNull(String str)
	{
		if (str == null)
		{
			return "";
		}
		return str.trim();
	}

	/**
	 * 将对象转为字符串
	 *
	 * @param o
	 * @return
	 */
	public static String isNull(Object o)
	{
		if (o == null)
		{
			return "";
		}
		String str = "";
		if (o instanceof String)
		{
			str = (String) o;
		}
		else
		{
			str = o.toString();
		}
		return str.trim();
	}

	/**
	 * 检验是否为空或空字符串
	 *
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str)
	{
		return isNull(str).equals("");
	}

	public static boolean isBlank(Object o)
	{
		return isNull(o).equals("");
	}

	/**
	 * 检验是否非空字符串
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str)
	{
		return !isNull(str).equals("");
	}

	/**
	 * 检验手机号
	 *
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone)
	{
		phone = isNull(phone);
		Pattern regex = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher matcher = regex.matcher(phone);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	/**
	 * 校验是否全中文，返回true 表示是 反之为否
	 *
	 * @param realname
	 * @return
	 */
	public static boolean isChinese(String realname)
	{
		realname = isNull(realname);
		Pattern regex = Pattern.compile("[\\u4e00-\\u9fa5]{2,25}");
		Matcher matcher = regex.matcher(realname);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	/**
	 * 校验邮箱格式
	 *
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email)
	{
		email = isNull(email);
		Pattern regex = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher matcher = regex.matcher(email);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	/**
	 * 校验身份证号码
	 */
	public static boolean isCard(String cardId)
	{
		cardId = isNull(cardId);
		// 身份证正则表达式(15位)
		Pattern isIDCard1 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
		// 身份证正则表达式(18位)
		Pattern isIDCard2 = Pattern
				.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
		Matcher matcher1 = isIDCard1.matcher(cardId);
		Matcher matcher2 = isIDCard2.matcher(cardId);
		boolean isMatched = matcher1.matches() || matcher2.matches();
		return isMatched;
	}

	/**
	 * 判断字符串是否为整数
	 *
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str)
	{
		if (isBlank(str))
		{
			return false;
		}
		Pattern regex = Pattern.compile("\\d*");
		Matcher matcher = regex.matcher(str);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	/**
	 * 判断字符串是否为数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str)
	{
		if (isBlank(str))
		{
			return false;
		}
		Pattern regex = Pattern.compile("(-)?\\d*(.\\d*)?");
		Matcher matcher = regex.matcher(str);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	/**
	 * 首字母大写
	 *
	 * @param s
	 * @return
	 */
	public static String firstCharUpperCase(String s)
	{
		StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
		sb.append(s.substring(1, s.length()));
		return sb.toString();
	}

	/**
	 * 隐藏头几位
	 *
	 * @param str
	 * @param len
	 * @return
	 */
	public static String hideFirstChar(String str, int len)
	{
		if (str == null)
			return null;
		char[] chars = str.toCharArray();
		if (str.length() <= len)
		{
			for (int i = 0; i < chars.length; i++)
			{
				chars[i] = '*';
			}
		}
		else
		{
			for (int i = 0; i < 1; i++)
			{
				chars[i] = '*';
			}
		}
		str = new String(chars);
		return str;
	}

	/**
	 * 隐藏末几位
	 *
	 * @param str
	 * @param len
	 * @return
	 */
	public static String hideLastChar(String str, int len)
	{
		if (str == null)
			return null;
		char[] chars = str.toCharArray();
		if (str.length() <= len)
		{
			return hideLastChar(str, str.length() - 1);
		}
		else
		{
			for (int i = chars.length - 1; i > chars.length - len - 1; i--)
			{
				chars[i] = '*';
			}
		}
		str = new String(chars);
		return str;
	}

	/**
	 * 指定起始位置字符串隐藏
	 *
	 * @param str
	 * @param index1
	 * @param index2
	 * @return
	 */
	public static String hideStr(String str, int index1, int index2)
	{
		if (str == null)
			return null;
		String str1 = str.substring(index1, index2);
		String str2 = str.substring(index2);
		String str3 = "";
		if (index1 > 0)
		{
			str1 = str.substring(0, index1);
			str2 = str.substring(index1, index2);
			str3 = str.substring(index2);
		}
		char[] chars = str2.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			chars[i] = '*';
		}
		str2 = new String(chars);
		String str4 = str1 + str2 + str3;
		return str4;
	}

	/**
	 * Object数组拼接为字符串
	 *
	 * @param args
	 * @return
	 */
	public static String contact(Object[] args)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < args.length; i++)
		{
			sb.append(args[i]);
			if (i < args.length - 1)
			{
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * Long数组拼接为字符串
	 *
	 * @param args
	 * @return
	 */
	public static String contact(long[] args)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < args.length; i++)
		{
			sb.append(args[i]);
			if (i < args.length - 1)
			{
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 数字数组拼接为字符串
	 *
	 * @param arr
	 * @return
	 */
	public static String array2Str(int[] arr)
	{
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < arr.length; i++)
		{
			s.append(arr[i]);
			if (i < arr.length - 1)
			{
				s.append(",");
			}
		}
		return s.toString();
	}

	/**
	 * 字符串数组转换为数字数组
	 *
	 * @param strarr
	 * @return
	 */
	public static int[] strarr2intarr(String[] strarr)
	{
		int[] result = new int[strarr.length];
		for (int i = 0; i < strarr.length; i++)
		{
			result[i] = Integer.parseInt(strarr[i]);
		}
		return result;
	}

	/**
	 * 大写字母转成“_”+小写 驼峰命名转换为下划线命名
	 *
	 * @param str
	 * @return
	 */
	public static String toUnderline(String str)
	{
		char[] charArr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		sb.append(charArr[0]);
		for (int i = 1; i < charArr.length; i++)
		{
			if (charArr[i] >= 'A' && charArr[i] <= 'Z')
			{
				sb.append('_').append(charArr[i]);
			}
			else
			{
				sb.append(charArr[i]);
			}
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * 下划线改成驼峰样子
	 *
	 * @param str
	 * @return
	 */
	public static String clearUnderline(String str)
	{
		char[] charArr = isNull(str).toLowerCase().toCharArray();
		StringBuffer sb = new StringBuffer();
		sb.append(charArr[0]);
		boolean isClear = false;
		for (int i = 1; i < charArr.length; i++)
		{
			if (charArr[i] == '_')
			{
				isClear = true;
				continue;
			}
			if (isClear && (charArr[i] >= 'a' && charArr[i] <= 'z'))
			{
				char c = (char) (charArr[i] - 32);
				sb.append(c);
				isClear = false;
			}
			else
			{
				sb.append(charArr[i]);
			}

		}
		return sb.toString();
	}

	/**
	 * String to int
	 *
	 * @param str
	 * @return
	 */
	public static int toInt(String str)
	{
		if (isBlank(str))
			return 0;
		int ret = 0;
		try
		{
			ret = Integer.parseInt(str);
		}
		catch (NumberFormatException e)
		{
			ret = 0;
		}
		return ret;
	}

	public static byte toByte(String str)
	{
		if (isBlank(str))
			return 0;
		byte ret = 0;
		try
		{
			ret = Byte.parseByte(str);
		}
		catch (NumberFormatException e)
		{
			ret = 0;
		}
		return ret;
	}

	/**
	 * String to Long
	 *
	 * @param str
	 * @return
	 */
	public static long toLong(String str)
	{
		if (isBlank(str))
			return 0L;
		long ret = 0;
		try
		{
			ret = Long.parseLong(str);
		}
		catch (NumberFormatException e)
		{
			ret = 0;
		}
		return ret;
	}

	/**
	 * String[] to long[]
	 *
	 * @param str
	 * @return
	 */
	public static long[] toLongs(String[] str)
	{
		if (str == null || str.length < 1)
			return new long[]{0L};
		long[] ret = new long[str.length];
		ret = (long[]) ConvertUtils.convert(str, long.class);
		return ret;
	}

	/**
	 * String[] to double[]
	 *
	 * @param str
	 * @return
	 */
	public static double[] toDoubles(String[] str)
	{

		if (str == null || str.length < 1)
			return new double[]{0L};
		double[] ret = new double[str.length];
		for (int i = 0; i < str.length; i++)
		{
			ret[i] = toDouble(str[i]);
		}
		return ret;
	}

	/**
	 * String to Double
	 *
	 * @param str
	 * @return
	 */
	public static double toDouble(String str)
	{
		if (isBlank(str))
			return 0;
		try
		{
			return BigDecimalUtil.round(str);
		}
		catch (Exception e)
		{
			return 0;
		}
	}

	/**
	 * 根据身份证Id获取性别
	 *
	 * @param cardId
	 * @return
	 */
	public static String getSex(String cardId)
	{
		int sexNum = 0;
		//15位的最后一位代表性别，18位的第17位代表性别，奇数为男，偶数为女
		if (cardId.length() == 15)
		{
			sexNum = cardId.charAt(cardId.length() - 1);
		}
		else
		{
			sexNum = cardId.charAt(cardId.length() - 2);
		}

		if (sexNum % 2 == 1)
		{
			return "M";
		}
		else
		{
			return "F";
		}
	}

	/**
	 * Wap网关Via头信息中特有的描述信息
	 */
	private static final String[] MOBILE_GETWAY_HEADERS = new String[]{
			"ZXWAP",//中兴提供的wap网关的via信息，例如：Via=ZXWAP GateWayZTE Technologies，
			"chinamobile.com",//中国移动的诺基亚wap网关，例如：Via=WTP/1.1 GDSZ-PB-GW003-WAP07.gd.chinamobile.com (Nokia WAP Gateway 4.1 CD1/ECD13_D/4.1.04)
			"monternet.com",//移动梦网的网关，例如：Via=WTP/1.1 BJBJ-PS-WAP1-GW08.bj1.monternet.com. (Nokia WAP Gateway 4.1 CD1/ECD13_E/4.1.05)
			"infoX",//华为提供的wap网关，例如：Via=HTTP/1.1 GDGZ-PS-GW011-WAP2 (infoX-WISG Huawei Technologies)，或Via=infoX WAP Gateway V300R001 Huawei Technologies
			"XMS 724Solutions HTG",//国外电信运营商的wap网关，不知道是哪一家
			"wap.lizongbo.com",//自己测试时模拟的头信息
			"Bytemobile",//貌似是一个给移动互联网提供解决方案提高网络运行效率的，例如：Via=1.1 Bytemobile OSN WebProxy/5.1
	};

	/**
	 * 电脑上的IE或Firefox浏览器等的User-Agent关键词
	 */
	private static String[] PC_HEADERS = new String[]{
			"Windows 98",
			"Windows ME",
			"Windows 2000",
			"Windows XP",
			"Windows NT",
			"Ubuntu"
	};

	/**
	 * 手机浏览器的User-Agent里的关键词
	 */
	private static String[] MOBILE_USERaGENTS = new String[]{
			"Nokia",//诺基亚，有山寨机也写这个的，总还算是手机，Mozilla/5.0 (Nokia5800 XpressMusic)UC AppleWebkit(like Gecko) Safari/530
			"SAMSUNG",//三星手机 SAMSUNG-GT-B7722/1.0+SHP/VPP/R5+Dolfin/1.5+Nextreaming+SMM-MMS/1.2.0+profile/MIDP-2.1+configuration/CLDC-1.1
			"MIDP-2",//j2me2.0，Mozilla/5.0 (SymbianOS/9.3; U; Series60/3.2 NokiaE75-1 /110.48.125 Profile/MIDP-2.1 Configuration/CLDC-1.1 ) AppleWebKit/413 (KHTML like Gecko) Safari/413
			"CLDC1.1",//M600/MIDP2.0/CLDC1.1/Screen-240X320
			"SymbianOS",//塞班系统的，
			"MAUI",//MTK山寨机默认ua
			"UNTRUSTED/1.0",//疑似山寨机的ua，基本可以确定还是手机
			"Windows CE",//Windows CE，Mozilla/4.0 (compatible; MSIE 6.0; Windows CE; IEMobile 7.11)
			"iPhone",//iPhone是否也转wap？不管它，先区分出来再说。Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_1 like Mac OS X; zh-cn) AppleWebKit/532.9 (KHTML like Gecko) Mobile/8B117
			"iPad",//iPad的ua，Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X; zh-cn) AppleWebKit/531.21.10 (KHTML like Gecko) Version/4.0.4 Mobile/7B367 Safari/531.21.10
			"Android",//Android是否也转wap？Mozilla/5.0 (Linux; U; Android 2.1-update1; zh-cn; XT800 Build/TITA_M2_16.22.7) AppleWebKit/530.17 (KHTML like Gecko) Version/4.0 Mobile Safari/530.17
			"BlackBerry",//BlackBerry8310/2.7.0.106-4.5.0.182
			"UCWEB",//ucweb是否只给wap页面？ Nokia5800 XpressMusic/UCWEB7.5.0.66/50/999
			"ucweb",//小写的ucweb貌似是uc的代理服务器Mozilla/6.0 (compatible; MSIE 6.0;) Opera ucweb-squid
			"BREW",//很奇怪的ua，例如：REW-Applet/0x20068888 (BREW/3.1.5.20; DeviceId: 40105; Lang: zhcn) ucweb-squid
			"J2ME",//很奇怪的ua，只有J2ME四个字母
			"YULONG",//宇龙手机，YULONG-CoolpadN68/10.14 IPANEL/2.0 CTC/1.0
			"YuLong",//还是宇龙
			"COOLPAD",//宇龙酷派YL-COOLPADS100/08.10.S100 POLARIS/2.9 CTC/1.0
			"TIANYU",//天语手机TIANYU-KTOUCH/V209/MIDP2.0/CLDC1.1/Screen-240X320
			"TY-",//天语，TY-F6229/701116_6215_V0230 JUPITOR/2.2 CTC/1.0
			"K-Touch",//还是天语K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223 Release/30.07.2008 Browser/WAP2.0
			"Haier",//海尔手机，Haier-HG-M217_CMCC/3.0 Release/12.1.2007 Browser/WAP2.0
			"DOPOD",//多普达手机
			"Lenovo",// 联想手机，Lenovo-P650WG/S100 LMP/LML Release/2010.02.22 Profile/MIDP2.0 Configuration/CLDC1.1
			"LENOVO",// 联想手机，比如：LENOVO-P780/176A
			"HUAQIN",//华勤手机
			"AIGO-",//爱国者居然也出过手机，AIGO-800C/2.04 TMSS-BROWSER/1.0.0 CTC/1.0
			"CTC/1.0",//含义不明
			"CTC/2.0",//含义不明
			"CMCC",//移动定制手机，K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223 Release/30.07.2008 Browser/WAP2.0
			"DAXIAN",//大显手机DAXIAN X180 UP.Browser/6.2.3.2(GUI) MMP/2.0
			"MOT-",//摩托罗拉，MOT-MOTOROKRE6/1.0 LinuxOS/2.4.20 Release/8.4.2006 Browser/Opera8.00 Profile/MIDP2.0 Configuration/CLDC1.1 Software/R533_G_11.10.54R
			"SonyEricsson",// 索爱手机，SonyEricssonP990i/R100 Mozilla/4.0 (compatible; MSIE 6.0; Symbian OS; 405) Opera 8.65 [zh-CN]
			"GIONEE",//金立手机
			"HTC",//HTC手机
			"ZTE",//中兴手机，ZTE-A211/P109A2V1.0.0/WAP2.0 Profile
			"HUAWEI",//华为手机，
			"webOS",//palm手机，Mozilla/5.0 (webOS/1.4.5; U; zh-CN) AppleWebKit/532.2 (KHTML like Gecko) Version/1.0 Safari/532.2 Pre/1.0
			"GoBrowser",//3g GoBrowser.User-Agent=Nokia5230/GoBrowser/2.0.290 Safari
			"IEMobile",//Windows CE手机自带浏览器，
			"WAP2.0"//支持wap 2.0的
	};

	/**
	 * 根据当前请求的特征，判断该请求是否来自手机终端，主要检测特殊的头信息，以及user-Agent这个header
	 *
	 * @param request http请求
	 * @return 如果命中手机特征规则，则返回对应的特征字符串
	 */
	public static boolean isMobileDevice(HttpServletRequest request)
	{
		boolean isMobileDevice = false;
		boolean pcFlag = false;
		boolean mobileFlag = false;
		String via = request.getHeader("Via");
		String userAgent = request.getHeader("user-agent");
		for (int i = 0; via != null && !via.trim().equals("") && i < MOBILE_GETWAY_HEADERS.length; i++)
		{
			if (via.contains(MOBILE_GETWAY_HEADERS[i]))
			{
				mobileFlag = true;
				break;
			}
		}
		for (int i = 0; !mobileFlag && userAgent != null && !userAgent.trim().equals("") && i < MOBILE_USERaGENTS.length; i++)
		{
			if (userAgent.contains(MOBILE_USERaGENTS[i]))
			{
				mobileFlag = true;
				break;
			}
		}
		for (int i = 0; userAgent != null && !userAgent.trim().equals("") && i < PC_HEADERS.length; i++)
		{
			if (userAgent.contains(PC_HEADERS[i]))
			{
				pcFlag = true;
				break;
			}
		}
		if (mobileFlag == true && pcFlag == false)
		{
			isMobileDevice = true;
		}
		return isMobileDevice;//false:pc  true:手机

	}

	/**
	 * 将字符串数值转为BigDecimal类型，去掉其中的非数值。
	 *
	 * @param str to BigDecimal
	 * @return BigDecimal类型数字
	 */
	public static BigDecimal extractNumber(String str)
	{
		if (isBlank(str))
			return new BigDecimal("0.00");
		//去掉字符串中的非数值
		String regEx = "\\d+";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		if (m.find())
		{
			String findNum = m.group();
			if (isBlank(findNum))
				return new BigDecimal("0.00");
			return new BigDecimal(findNum);
		}
		else
			return new BigDecimal("0.00");
	}

	/**
	 * 去掉字符串中的空行
	 *
	 * @param input 字符串
	 * @return 结果
	 */
	public static String deleteBlankLine(String input)
	{
		String regex = "((\r\n)|\n)[\\s\t ]*(\\1)+";
		String str = input.replaceAll(regex, "$1");
		return str.replaceAll(regex, "$1");
	}

	/**
	 * 判断可变参数字符串不为空
	 *
	 * @param strs 字符串
	 * @return 结果
	 */
	public static boolean isNotBlankBatch(String... strs)
	{
		for (String str : strs)
		{
			if (str == null || "".equals(str))
				return false;
		}
		return true;
	}

	/**
	 * 截取字符串指定指定字节数的内容，汉字不能截取部分
	 *
	 * @param pContent    待截取字符串
	 * @param pLen        指定长度
	 * @param charSetName 字符串编码
	 * @return 截取结果
	 * @throws UnsupportedEncodingException 编码类型不支持
	 */
	public static String subStringByBytes(String pContent, int pLen, String charSetName)
			throws UnsupportedEncodingException
	{
		log.info(">>> start subStringByBytes(),pContent:{},pLen:{},charSetName:{}", pContent, pLen, charSetName);
		if (pContent != null)
		{
			try
			{
				byte[] bytes = pContent.getBytes(charSetName);
				if (bytes.length > pLen)
				{
					int tempLen = new String(bytes, 0, pLen, charSetName).length();
					//根据tempLen长度截取原字符串
					pContent = pContent.substring(0, tempLen);
					bytes = pContent.getBytes(charSetName);
					//如果第totalLen、第totalLen+1个字节正好是一个汉字，String的substring方法会返回一个完整的汉字，
					// 导致长度为totalLen+1（超过totalLen），所以再次对pContent的长度进行字节判断与处理
					if (bytes.length > pLen)
					{
						pContent = pContent.substring(0, tempLen - 1);
					}
					// 最后三个字符用省略号替代
					pContent = pContent.substring(0, pContent.length() - 3) + "...";
				}
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
				log.info("subStringByBytes()不支持的字符串编码,e:", e);
			}
		}
		log.info(">>> end subStringByBytes(),pContent:{}", pContent);
		return pContent;
	}

	/**
	 * 按GB18030截取字符串指定指定字节数的内容，汉字不能截取部分
	 *
	 * @param pContent 待截取字符串
	 * @param pLen     指定长度
	 * @return 截取结果
	 */
	public static String subStringByBytesGB18030(String pContent, int pLen)
	{
		String subString = "";
		try
		{
			subString = subStringByBytes(pContent, pLen, "GB18030");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			log.info("subStringByBytes()不支持的字符串编码,e:", e);
		}
		return subString;
	}

	/**
	 * 将整数转为固定长度字符串
	 *
	 * @param num 整数
	 * @return 固定长度字符串
	 */
	public static String intToFixedString(Integer num)
	{
		final DecimalFormat df = new DecimalFormat("000");
		return df.format(num);
	}

	/**
	 * url编码
	 *
	 * @param str 绝对路径
	 * @return url编码后路径
	 */
	public static String getURLEncoderString(String str)
	{
		String result = "";
		if (null == str)
		{
			return "";
		}
		try
		{
			result = URLEncoder.encode(str, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * url解码
	 *
	 * @param str 编码url路径
	 * @return 解码后的url路径
	 */
	public static String getURLDecoderString(String str)
	{
		String result = "";
		if (null == str)
		{
			return "";
		}
		try
		{
			result = URLDecoder.decode(str, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 判断对象是否为空
	 *
	 * @param obj
	 * @return
	 */
	public static Boolean isNotEmptyBean(Object obj)
	{
		Boolean flag = false;
		try
		{
			if (null != obj)
			{
				//得到类对象
				Class<?> c = (Class<?>) obj.getClass();
				//得到属性集合
				Field[] fs = c.getDeclaredFields();
				//得到方法体集合
				Method[] methods = c.getDeclaredMethods();
				//遍历属性
				for (Field f : fs)
				{
					//设置属性是可以访问的(私有的也可以)
					f.setAccessible(true);
					String fieldGetName = parGetName(f.getName());
					//判断属性是否存在get方法
					if (!checkGetMet(methods, fieldGetName))
					{
						continue;
					}
					//得到此属性的值
					Object val = f.get(obj);
					//只要有1个属性不为空,那么就不是所有的属性值都为空
					if (!isBlank(val))
					{
						flag = true;
						break;
					}
				}
			}
		}
		catch (Exception e)
		{
			log.error("判断对象是否为空出错：" + e.getMessage());
		}
		return flag;
	}

	/**
	 * 拼接某属性的 get方法
	 *
	 * @param fieldName
	 * @return String
	 */
	public static String parGetName(String fieldName)
	{
		if (null == fieldName || "".equals(fieldName))
		{
			return null;
		}
		int startIndex = 0;
		if (fieldName.charAt(0) == '_')
			startIndex = 1;
		return "get"
				+ fieldName.substring(startIndex, startIndex + 1).toUpperCase()
				+ fieldName.substring(startIndex + 1);
	}

	/**
	 * 判断是否存在某属性的 get方法
	 *
	 * @param methods
	 * @param fieldGetMet
	 * @return boolean
	 */
	public static Boolean checkGetMet(Method[] methods, String fieldGetMet)
	{
		for (Method met : methods)
		{
			if (fieldGetMet.equals(met.getName()))
			{
				return true;
			}
		}
		return false;
	}
}
