package hello;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.example.saga.pojos.BookingRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*

 curl --location 'http://localhost:8081/triggerSayHelloFlow' --header 'Content-Type: application/json' --data '{"name": "Gökçer Belgüsen" }'

 */

@Slf4j
@AllArgsConstructor
@Service
public class HelloWorkflow {

    private final WorkflowClient workflowClient;
    private final Environment environment;
    private final String TASK_DOMAIN_PROPERTY = "conductor.worker.all.domain";

    public Map<String, Object> startSayHelloWorkFlow(HelloRequest helloRequest) {
        UUID uuid = UUID.randomUUID();
        String bookingRequestId = uuid.toString();

        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName("HelloWorld101");
        request.setVersion(6);
        request.setCorrelationId("123");

        String domain = environment.getProperty(TASK_DOMAIN_PROPERTY, String.class, "");

        if (!domain.isEmpty()) {
            Map<String, String> taskToDomain = new HashMap<>();
            taskToDomain.put("*", domain);
            request.setTaskToDomain(taskToDomain);
        }

        Map<String, Object> inputData = new HashMap<>();
        inputData.put("name", helloRequest.getName());
        request.setInput(inputData);

        log.info(request.toString());

        String workflowId = "";
        try {
            workflowId = workflowClient.startWorkflow(request);
            log.info("Workflow id: {}", workflowId);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return Map.of("error", "Booking creation failure", "detail", ex.toString());
        }

        return Map.of("workflowId", workflowId);
    }
}
