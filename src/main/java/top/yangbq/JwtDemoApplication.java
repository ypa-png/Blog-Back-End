package top.yangbq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;

@SpringBootApplication
@EnableScheduling
@MapperScan("top.yangbq.mapper")
public class JwtDemoApplication {

    public static void main ( String[] args ) {
        SpringApplication.run ( JwtDemoApplication.class , args );
    }

}
