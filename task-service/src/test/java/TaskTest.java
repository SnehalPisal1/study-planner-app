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
            //Arrange input
        Task task = new Task();
        task.setTaskName("Java");
        task.setDescription("Java version - 8");
        task.setDueDate(LocalDateTime.now().plusDays(2));
        task.setStatus(IN_PROGRESS);
        task.setCreatedBy("testUser");
        task.setCreatedAt(LocalDateTime.now());

            //Arrange response
            Task savedTask = new Task();
            savedTask.setTaskId(1L);
            savedTask.setTaskName("Java");
            savedTask.setDescription("Java version - 8");
            savedTask.setDueDate(LocalDateTime.now().plusDays(2));
            savedTask.setStatus(IN_PROGRESS);
            savedTask.setCreatedBy("testUser");
            savedTask.setCreatedAt(LocalDateTime.now());

            when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

            // Act
            Task response = taskServiceImpl.createTask(task);


            // Assert
            assertNotNull(response);
            assertEquals(savedTask.getTaskId(), response.getTaskId());
            assertEquals(savedTask.getTaskName(), response.getTaskName());
            assertEquals(savedTask.getCreatedBy(), response.getCreatedBy());
            assertEquals(savedTask.getStatus(), response.getStatus());
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
