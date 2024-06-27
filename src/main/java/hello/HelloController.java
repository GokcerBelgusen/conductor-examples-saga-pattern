package hello;

import io.orkes.example.saga.pojos.BookingRequest;
import io.orkes.example.saga.service.WorkflowService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
public class HelloController {
    private final HelloWorkflow helloWorkflow;

    @PostMapping(value = "/triggerSayHelloFlow", produces = "application/json")
    public ResponseEntity<Map<String, Object>> triggerSayHelloFlow(@RequestBody HelloRequest helloRequest) {
        log.info("Starting ride booking flow for: {}", helloRequest);
        return ResponseEntity.ok(helloWorkflow.startSayHelloWorkFlow(helloRequest));
    }
}
