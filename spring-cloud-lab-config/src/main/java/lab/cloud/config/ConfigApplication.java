package lab.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.cmf.paas.task.AsyncTaskPoolManager;
//import com.cmf.paas.task.api.store.domain.Task;

@EnableConfigServer
@SpringBootApplication
@RestController
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

//    @GetMapping(value = "/task")
//    public String testTask() {
//
//        Task task = new Task();
//        task.setTaskType("TestTask");
//        task.setTaskId("TestTask");
//
//        AsyncTaskPoolManager.getAsyncTaskPool().submit(task);
//
//        return "success";
//    }
}