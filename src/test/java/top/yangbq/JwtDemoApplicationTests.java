package top.yangbq;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import top.yangbq.mapper.UsersMapper;
import top.yangbq.pojo.Users;

@SpringBootTest
@Slf4j
class JwtDemoApplicationTests {

    @Autowired
    private UsersMapper userMapper;

    @Value ( "${locationTemp}" )
    private String locationTemp;

    @Test
    void contextLoads () {
        Users login = userMapper.login ( new Users ( ) );
    }

    @Test
    public void test1(){
        Users users = new Users ( );
        users.setUserName ( "asda" );
        users.setUserPwd ( "1231" );
        System.out.println (users!=null?true:false );

    }
    @Test
    public void test2(){
        System.out.println ( locationTemp );
    }

}
