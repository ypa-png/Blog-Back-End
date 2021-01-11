package top.yangbq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@MapperScan("top.yangbq.mapper")
public class JwtDemoApplication {

    public static void main ( String[] args ) {
        SpringApplication.run ( JwtDemoApplication.class , args );
    }

}
