package victor.training.spring.bean;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Component
@ToString
@RequiredArgsConstructor
public class CuValueuri {

    @Value("${welcome.help.app-id}")
    private final String p1;
    @Value("${welcome.help.file}")
    private final String p2;
    private final X x;

    @PostConstruct
    public void method() {
        System.out.println("injected :" + this);
    }

}

@Service
class X {

}
