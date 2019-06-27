//package lab.cloud.config.task;
//
//import org.springframework.stereotype.Component;
//
//import com.cmf.paas.task.api.annotation.TaskHander;
//import com.cmf.paas.task.api.executor.TaskExecutor;
//import com.cmf.paas.task.api.store.domain.Task;
//
//@Component
//@TaskHander(taskType = "TestTask")
//public class TestTask implements TaskExecutor {
//
//    @Override
//    public Object execute(Task task) throws Throwable {
//        System.out.println("task done");
//        return null;
//    }
//}