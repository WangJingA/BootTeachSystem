import com.boot.teach.web.TeachSystemApplication;
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
}
