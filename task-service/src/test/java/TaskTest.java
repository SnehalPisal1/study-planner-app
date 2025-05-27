import com.studyplanner.taskservice.models.Task;
import com.studyplanner.taskservice.repositories.TaskRepository;
import com.studyplanner.taskservice.services.TaskServicesImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;

import static com.studyplanner.taskservice.models.TaskStatus.IN_PROGRESS;

@ExtendWith(MockitoExtension.class)
public class TaskTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServicesImpl taskServiceImpl;


        @Test
        public void testCreateTask_Success() {
            //Arrange
            Task task = new Task();
            task.setTaskId(2L);
            task.setDescription("Second Description");
            task.setDueDate(LocalDateTime.now().plusDays(2));
            task.setStatus(IN_PROGRESS);
            task.setCreatedBy("testUser");
            task.setCreatedAt(LocalDateTime.now());
            // Arrange
            //   Task task = new Task("Complete project", "Finish by Friday", false);
            Task savedTask = new Task(1L, "Complete project", "Finish by Friday", false, "testuser");
            when(taskService.createTask(any(Task.class))).thenReturn(savedTask);


        }

    @Test
    public void testUpdateTask(){

    }

    @Test
    public void testGetAllTask(){

    }


    @Test
    public void testDeleteTask(){

    }
}
