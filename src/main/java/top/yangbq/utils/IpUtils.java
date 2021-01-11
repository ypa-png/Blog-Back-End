package top.yangbq.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class IpUtils {
    /**
     * 获取访问者IP
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     * @param request
     * @return
     */
    public static String getIpAddr( HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty (ip) && !"unknown".equalsIgnoreCase(ip)) {
            if(ip.contains("../")||ip.contains("..\\")){
                return "";
            }
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty (ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                ip= ip.substring(0, index);
            }
            if(ip.contains("../")||ip.contains("..\\")){
                return "";
            }
            return ip;
        } else {
            ip=request.getRemoteAddr();
            if(ip.contains("../")||ip.contains("..\\")){
                return "";
            }
            if(ip.equals("0:0:0:0:0:0:0:1")){
                ip="127.0.0.1";
            }
            return ip;
        }

    }
}
