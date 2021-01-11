package top.yangbq;

import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GeneratorTest {




    @Test
    public void test1 () {
        List < String > warnings = new ArrayList < String > ( );
        boolean overwrite = true;
        File configFile = new File ( "D:\\idea\\jwt-demo\\src\\main\\resources\\generator.xml" );
        ConfigurationParser cp = new ConfigurationParser ( warnings );

        DefaultShellCallback callback = new DefaultShellCallback ( overwrite );
        MyBatisGenerator myBatisGenerator = null;
        try {
            Configuration config = cp.parseConfiguration ( configFile );
            myBatisGenerator = new MyBatisGenerator ( config , callback , warnings );
            myBatisGenerator.generate ( null );
        } catch (Exception e) {
            e.printStackTrace ( );
        }

    }



}
