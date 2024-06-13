package victor.training.spring.pachet.pachet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import victor.training.spring.first.Y;

@Slf4j
@Service // numele e "x"
@RequiredArgsConstructor // 😎genereaza un constructor cu toate campurile final
public class X {
//  @Autowired // field injection
//  private Y y;

  private final Y y;
//  public X(Y y) { // constructor injection
//    this.y = y;
//  }

  public int logic() {
    return 1 + y.logic();
  }
}