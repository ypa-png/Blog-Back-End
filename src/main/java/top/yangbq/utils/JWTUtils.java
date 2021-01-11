package top.yangbq.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    public static JWTUtils getInstance(){
        return new JWTUtils ();
    }

    private static String SIGN="!ASDSDJAKSD";//签名秘钥
    private static Integer EXPIRE_DATE=7;//过期时间（天）
    /*
    生成token ,header.payLodaer.sing
    * */
    public static String getToken( Map <String,String> map ){
        String token=null;
        Calendar instance = Calendar.getInstance ( );
        instance.add ( Calendar.DATE,EXPIRE_DATE );
        JWTCreator.Builder builder = JWT.create ( );
        map.forEach ( (k,v)->{
            builder.withClaim ( k, v );
        } );
        try {
            token = builder.withExpiresAt ( instance.getTime ( ) ).sign ( Algorithm.HMAC256 ( SIGN ) );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace ( );
        }
        return token;
    }

    /*
    * 验证token
    * */
    public static DecodedJWT verify(String token){
        DecodedJWT decodedJWT=null;
        try {
            decodedJWT = JWT.require ( Algorithm.HMAC256 ( SIGN ) ).build ( ).verify ( token );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace ( );
        }
        return decodedJWT;
    }

    /*获取验证信息*/
    public static DecodedJWT getTokenInfo(String token){
        DecodedJWT verify=null;
        try {
            verify = JWT.require ( Algorithm.HMAC256 ( SIGN ) ).build ( ).verify ( token );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace ( );
        }
        return verify;
    }

    public static void main ( String[] args ) {
        HashMap < String, String > map = new HashMap <> ( );
        map.put ( "username" ,"zhangsan" );
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjE2MDQ5MzI0MzI4ODYiLCJleHAiOjE2MDU1ODAyODQsInVzZXJuYW1lIjoieWFuZ2JxNDA0In0.aJVxJzyOnHQYh1nkLDmJpqOPrp60WDcIH-nrWc7ctoM";
        System.out.println ( verify ( token ).getClaims (  ).get ( "username" ).asString ()  );
        System.out.println ( verify ( token ).getClaims (  ).get ( "id" ).asString ()  );
    }
}
