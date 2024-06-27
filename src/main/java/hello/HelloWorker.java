package hello;

import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;
import io.orkes.example.saga.pojos.BookingRequest;
import io.orkes.example.saga.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Component
@ComponentScan(basePackages = {"hello"})
public class HelloWorker {

    @WorkerTask(value = "hello_world_task", threadCount = 3, pollingInterval = 300)
    public TaskResult sayHello(HelloRequest helloRequest) {
        String name = HelloService.sayHello(helloRequest.getName());

       log.info("Requested " + helloRequest.toString() + " response will be " + name);

        TaskResult result = new TaskResult();
        Map<String, Object> output = new HashMap<>();

        output.put("response", name);

        result.setOutputData(output);
        result.setStatus(TaskResult.Status.COMPLETED);

        return result;
    }
}
