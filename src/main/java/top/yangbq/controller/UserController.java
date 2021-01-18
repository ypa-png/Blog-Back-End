package top.yangbq.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Delete;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.yangbq.pojo.Articles;
import top.yangbq.pojo.Users;
import top.yangbq.pojo.UsersExample;
import top.yangbq.service.UserService;
import top.yangbq.utils.AliOssTemplate;
import top.yangbq.utils.IdWork;
import top.yangbq.utils.JWTUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//    @PostMapping("/user/test")
//    public Map<String,Object> test( HttpServletRequest request ){
//        Map < String, Object > map = new HashMap <>();
//        String header = request.getHeader ("token" );
//        System.out.println ( header );
//        DecodedJWT tokenInfo = JWTUtils.getTokenInfo ( header );
//        log.info ( "请求的用户username:[{}]",tokenInfo.getClaim ( "username" ).asString ( ) );
//        log.info ( "请求的用户id:[{}]",tokenInfo.getClaim ( "id" ).asString () );
//        /*处理自己的业务逻辑*/
//        map.put ( "state" , true );
//        map.put ( "msg" , "请求成功" );
//        return map;
//
//    }


/**
 * @ClassName: UserController
 * @Description
 * @Author yangbq
 * @Date 2020/11/16
 * @Time 22:15
 */
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Value ( "${locationTemp}" )
    private String locationTemp;

    /**
     * @MethodName: login
     * @Description 用户登录，登陆成功生成token并返回
     * @Author yangbq
     * @MethodReturnType java.util.Map<java.lang.String , java.lang.Object>
     * @ParameterNames [user]
     * @Date 2020/11/16
     * @Time 22:13
     */
    @PostMapping ( "/user/login" )
    public Map < String, Object > login ( @RequestBody Users user ) {
//        log.info ( "用户名：[{}]",user.getUserName ());
//        log.info ( "密码：[{}]",user.getUserPwd ());
        System.out.println ( "用户名：" + user.getUserName ( ) );
        System.out.println ( "密码：" + user.getUserPwd ( ) );

        Map < String, Object > map = new HashMap <> ( );
        try {
            Users login = userService.login ( user );
            System.out.println ( "user: " + login );
//            log.info ( "登录查询用户：[{}] ",login);
            HashMap < String, String > payLoad = new HashMap <> ( );
            payLoad.put ( "id" , String.valueOf ( login.getUserId ( ) ) );
            payLoad.put ( "username" , String.valueOf ( login.getUserName ( ) ) );
            //生成jwt令牌
            String token = JWTUtils.getToken ( payLoad );
            map.put ( "id" , login.getUserId ( ) );
            map.put ( "username" , login.getUserName ( ) );
            map.put ( "msg" , "登陆成功" );
            map.put ( "state" , true );
            map.put ( "token" , token );//响应token
        } catch (Exception e) {
            map.put ( "msg" , e.getMessage ( ) );
            map.put ( "state" , false );
        }
        return map;
    }

    /**
     * @MethodName: userRegist
     * @Description 用户注册
     * @Author yangbq
     * @MethodReturnType java.util.Map<java.lang.String , java.lang.Object>
     * @ParameterNames [user]
     * @Date 2020/11/16
     * @Time 22:13
     */
    @PostMapping ( "/user/regist" )
    public Map < String, Object > userRegist ( @RequestBody Users user ) {
//        log.info ( "用户名：[{}]",user.getUserName ());
//        log.info ( "密码：[{}]",user.getUserPwd ());
        Map < String, Object > map = new HashMap <> ( );
        try {
            userService.insertSelective ( user );
            map.put ( "msg" , "认证成功" );
            map.put ( "state" , true );
        } catch (Exception e) {
            map.put ( "msg" , e.getMessage ( ) );
            map.put ( "state" , false );
        }
        return map;
    }

    /**
     * @MethodName: validateUsername
     * @Description 校验用户名是否可用
     * @Author yangbq
     * @MethodReturnType java.util.Map<java.lang.String , java.lang.Object>
     * @ParameterNames [username]
     * @Date 2020/11/16
     * @Time 22:14
     */
    @GetMapping ( value = "/user/validate/{username}" )
    public Map < String, Object > validateUsername ( @PathVariable ( "username" ) String username ) {
        Map < String, Object > map = new HashMap <> ( );
//        log.info ( "username: [{}]", username );
        try {
            Users users = userService.validateUsername ( username );
            map.put ( "msg" , "用户名可用" );
            map.put ( "state" , true );
        } catch (MyBatisSystemException e) {
            map.put ( "msg" , "数据库错误" );
            map.put ( "state" , false );
        } catch (Exception e) {
            map.put ( "msg" , e.getMessage ( ) );
            map.put ( "state" , false );
        }
        return map;
    }

    /**
     * @MethodName: test2
     * @Description 测试接口
     * @Author yangbq
     * @MethodReturnType java.util.Map<java.lang.String , java.lang.Object>
     * @ParameterNames [headers]
     * @Date 2020/11/16
     * @Time 22:14
     */
    @PostMapping ( "/user/test2" )
    public Map < String, Object > test2 ( @RequestHeader HttpHeaders headers ) {
        Map < String, Object > map = new HashMap <> ( );
        String header = headers.get ( "token" ).get ( 0 );
        DecodedJWT tokenInfo = JWTUtils.getTokenInfo ( header );
        map.put ( "state" , true );
        map.put ( "msg" , "请求成功" );
        return map;
    }

    @GetMapping ( "/user" )
    public Map < String, Object > getUserList () {
        Map < String, Object > map = new HashMap <> ( );
        UsersExample usersExample = new UsersExample ( );
        usersExample.createCriteria ( ).andUserNameNotEqualTo ( "root" );
        try {
            List < Users > users = userService.selectByExample ( usersExample );
            map.put ( "data" , users );
            map.put ( "state" , true );
        } catch (Exception e) {
            map.put ( "msg" , "查询异常" );
            map.put ( "state" , false );
        }
        return map;
    }

    @PostMapping ( "/user/pageList" )
    public Map pageList ( @RequestParam ( value = "currentPage", defaultValue = "1" ) Integer currentPage ,
                          @RequestParam ( value = "pageSize", defaultValue = "4  " ) Integer pageSize ,
                          @RequestBody Users searchEntity ) {
        Map map = new HashMap ( );
        PageInfo pageInfo = null;
        try {
            pageInfo = userService.pageList ( currentPage , pageSize , searchEntity );
            map.put ( "pageInfo" , pageInfo );
            map.put ( "state" , true );

        } catch (Exception e) {
            e.printStackTrace ( );
            map.put ( "msg" , e.getMessage ( ) );
            map.put ( "state" , false );
        }

        return map;
    }

    public String updateHeadPic ( MultipartFile file , HttpServletRequest request , Users user ) throws IOException {
        System.out.println ( user );
        String rootPath = locationTemp;
        String OriginalFilename = file.getOriginalFilename ( );
        System.out.println ( OriginalFilename );
        String ext = OriginalFilename.substring ( OriginalFilename.lastIndexOf ( "." ) );
        File headPicPath = new File ( rootPath , "user" + File.separator + "headPic" );
        if (!headPicPath.exists ( )) {
            headPicPath.mkdirs ( );
        }
        String newFileName = null;
        if (user.getUserHeadpic ( ) != null && !"".equals ( user.getUserHeadpic ( ) ) && !"null".equals ( user.getUserHeadpic ( ) )) {
            String oldFilename = user.getUserHeadpic ( ).substring ( user.getUserHeadpic ( ).lastIndexOf ( File.separator ) + 1 );
            File file1 = new File ( headPicPath , oldFilename );
            System.out.println ( "删除的图片名" + file1 );
            file1.delete ( );
            newFileName = "head_" + user.getUserId ( ) + System.currentTimeMillis ( ) + ext;
            file.transferTo ( new File ( headPicPath , newFileName ) );
        } else {
            newFileName = "head_" + IdWork.getId ( ) + System.currentTimeMillis ( ) + ext;
            file.transferTo ( new File ( headPicPath , newFileName ) );
        }
        return newFileName;
    }



    @PutMapping ( "user/update" )
    public Map < String, Object > updateUser ( String user , MultipartFile file , HttpServletRequest request ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper ( );
        Users users = objectMapper.readValue ( user , Users.class );

        Map < String, Object > map = new HashMap <> ( );
        String fileName = null;

        try {
//            fileName = updateHeadPic ( file , request , users );
            fileName = new AliOssTemplate ().upload ( file,"userHeadPic" );
            users.setUserHeadpic ( fileName );
            userService.updateByPrimaryKeySelective ( users );
            map.put ( "msg" , "修改成功" );
            map.put ( "userHeadPic" , fileName );
            map.put ( "state" , true );
        } catch (NullPointerException e) {
            userService.updateByPrimaryKeySelective ( users );
            map.put ( "msg" , "修改成功" );
            map.put ( "state" , true );
        } catch (Exception e) {
            e.printStackTrace ( );
            map.put ( "msg" , "修改失败" );
            map.put ( "state" , false );
        }

        return map;
    }

    @GetMapping ( value = "/user/{userId}" )
    public Map < String, Object > getUserInfo ( @PathVariable ( "userId" ) String userId ) {
        Map < String, Object > map = new HashMap <> ( );
        try {
            Users users = userService.selectByPrimaryKey ( userId );
            map.put ( "user" , users );
            map.put ( "state" , true );
        } catch (Exception e) {
            map.put ( "state" , false );
        }
        return map;
    }


    @DeleteMapping ( "/user/delete" )
    public Map delete ( String[] ids ) {
        Map map = new HashMap ( );

        try {
            userService.deleteByIds ( ids );
            map.put ( "msg" , "删除成功" );
            map.put ( "state" , true );
        } catch (Exception e) {
            e.printStackTrace ( );
            map.put ( "msg" , e.getMessage ( ) );
            map.put ( "state" , false );
        }

        return map;
    }

//    @GetMapping ( "/user/{userId}" )
//    public Map < String, Object > getUserInfoById ( @PathVariable ( "userId" ) String userId ) {
//        Map < String, Object > map = new HashMap <> ( );
//
//        Users users = userService.selectByPrimaryKey ( userId );
//
//
//        return map;
//
//    }
}
