import com.boot.teach.web.TeachSystemApplication;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@SpringBootTest(classes = TeachSystemApplication.class)
public class TestWeb {
    @Test
    public void uuidTest(){
        System.out.println(UUID.randomUUID());
    }

    @Test
    public void addPass(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456!Qaz");
        System.out.println(encode);
    }

    @Test
    public void replace(){
        String url = "http://192.168.219.137:9000/bootteach/541349cdabb7452a8cd1ac82039aa95c.jpg;http://192.168.219.137:9000/bootteach/d6be3693970f43a1b17dcbdc435fda29.jpg;http://192.168.219.137:9000/bootteach/5d4e43ed100d4fa1a3d423deb09ce4ed.jpg;";
        String replace = url.replace("http://192.168.219.137:9000/bootteach/5d4e43ed100d4fa1a3d423deb09ce4ed.jpg", ";");
        System.out.println(replace);
    }
    @Test
    void contextLoads() {
        ProcessEngine engines =  ProcessEngines.getDefaultProcessEngine();
        System.out.print(engines);
    }
    @Test
    void createByJava(){
        /**
         * 测试类创建数据库审批流
         */
        ProcessEngine engine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setJdbcUrl("jdbc:mysql://localhost:3306/bootteachsystem?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT%2B8&useSSL=false")
                .setJdbcUsername("root")
                .setJdbcPassword("root")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                .buildProcessEngine();
        System.out.println(engine);
    }
}
