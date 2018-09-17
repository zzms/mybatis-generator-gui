package /packageName/.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * httprequest 工具类
 */
public class RequestUtil {

	/**
	 * 获取当前项目跟目录地址
	 *
	 * @param request
	 * @return
	 */
	public static String getBasePath(HttpServletRequest request) {
		return getServelPath(request) + request.getContextPath() + "/";
	}

	/**
	 * 获取当前web服务器地址（含端口）
	 *
	 * @param request
	 * @return
	 */
	public static String getServelPath(HttpServletRequest request) {
		return getIPPath(request) + ":" + request.getServerPort();
	}

	/**
	 * 获取当前web服务器的地址（不含端口）
	 *
	 * @param request
	 * @return
	 */
	public static String getIPPath(HttpServletRequest request) {
		String ipPath = request.getScheme() + "://" + request.getServerName();
		return ipPath;
	}

	/**
	 * 获取真实访问路径 getPathInfo() & getServletPath()
	 *
	 * @param [参数1] [参数1说明]
	 * @param [参数2] [参数2说明]
	 * @return [返回类型说明]
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String getRelativePath(HttpServletRequest request) {
		if (request.getAttribute("javax.servlet.include.request_uri") != null) {
			String result = (String) request
					.getAttribute("javax.servlet.include.path_info");
			if (result == null) {
				result = (String) request
						.getAttribute("javax.servlet.include.servlet_path");
			} else {
				result = (String) request
						.getAttribute("javax.servlet.include.servlet_path")
						+ result;
			}
			if ((result == null) || (result.equals(""))) {
				result = "/";
			}
			return result;
		}
		String result = request.getPathInfo();
		if (result == null) {
			result = request.getServletPath();
		} else {
			result = request.getServletPath() + result;
		}
		if ((result == null) || (result.equals(""))) {
			result = "/";
		}
		return StringUtil.isEmpty(result) ? "" : result;
	}

	/**
	 * 获取全部请求参数
	 * @param request
	 * @return Map<String,String>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getAllParams(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			//密码不获取
			if("pwd".equals(paramName)){
				continue;
			}
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length >= 1) {
				String value = paramValues[0];
				map.put(paramName, value);
			}
		}
		return map;
	}

	/**
	 * 获取客户端的真实IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = request.getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (StringUtil.isEmpty(ipAddress)
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtil.isEmpty(ipAddress)
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtil.isEmpty(ipAddress)
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if ("127.0.0.1".equals(ipAddress)) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (StringUtil.isNotEmpty(ipAddress) && ipAddress.length() > 15) { // "***.***.***.***".length()
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

}
