package hello;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.constant.Constable;

@Slf4j
@AllArgsConstructor
@Service
public class HelloService {
    public static String sayHello(String name) {
        return "Hello " + name;
    }
}
