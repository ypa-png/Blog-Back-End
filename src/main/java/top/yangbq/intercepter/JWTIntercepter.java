package top.yangbq.intercepter;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.yangbq.utils.JWTUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class JWTIntercepter  implements HandlerInterceptor {

    @Override
    public boolean preHandle ( HttpServletRequest request , HttpServletResponse response , Object handler ) throws Exception {
        /*获取请求头中的令牌*/
        String token = null;

        //OPTIONS请求直接放行
        if(RequestMethod.OPTIONS.name().equals(request.getMethod())) {
            return true;
        }
        Map < String, Object > map = new HashMap <> ();

        token = request.getHeader ( "Token" );



        String domain = request.getScheme()
                + "://" + request.getServerName()
                + ":" + request.getServerPort()
                + request.getContextPath()
                + "/"
                +request.getRequestURI ();

        String queryurl=request.getQueryString();
        if(null!=queryurl){
            domain+="?"+queryurl;
        }

        log.info ( "请求URL: [{}]",domain );
        log.info ( "请求方式：[{}]",request.getMethod() );
        log.info ( "token：[{}]",request.getHeader ( "Token" ) );



        try{
            JWTUtils.verify ( token );
            return true;//放行请求
        }catch (SignatureVerificationException e){//签名异常
            e.printStackTrace ();
            map.put ( "msg" , "无效签名" );
        }catch (TokenExpiredException e){//过期异常
            e.printStackTrace ();
            map.put ( "msg" , "token已过期" );
        }catch (AlgorithmMismatchException e){//算法不匹配异常
            e.printStackTrace ();
            map.put ( "msg" , "token算法不匹配" );
        }catch (NullPointerException e){
            e.printStackTrace ();
            map.put ( "msg","token为空" );
        }catch (Exception e){
            e.printStackTrace ();
            map.put ( "msg" , "token无效" );
        }
        map.put ( "tokenState" ,false );//设置状态

        //将map转为json
        String s = new ObjectMapper ( ).writeValueAsString ( map );
        response.setContentType ( "application/json;charset=UTF-8" );
        response.getWriter ().print ( s );

        return false;
    }
}
