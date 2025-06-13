import com.studyplanner.taskservice.models.Task;
import com.studyplanner.taskservice.repositories.TaskRepository;
import com.studyplanner.taskservice.services.TaskServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.studyplanner.taskservice.models.TaskStatus.DONE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.studyplanner.taskservice.models.TaskStatus.IN_PROGRESS;

@ExtendWith(MockitoExtension.class)
public class TaskTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServicesImpl taskServiceImpl;

    private Task task;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setTaskId(1L);
        task.setTaskName("Java");
        task.setDescription("Java version - 8");
        task.setDueDate(LocalDateTime.now().plusDays(2));
        task.setStatus(IN_PROGRESS);
        task.setCreatedBy("testUser");
        task.setCreatedAt(LocalDateTime.now());
    }

     @Test
     public void testCreateTask_Success() {
        //Arrange response
         Task inputTask = new Task();
         inputTask.setTaskName("Java");
         inputTask.setDescription("Java version - 8");
         inputTask.setDueDate(LocalDateTime.now().plusDays(2));
         inputTask.setStatus(IN_PROGRESS);
         inputTask.setCreatedBy("testUser");
         inputTask.setCreatedAt(LocalDateTime.now());

         when(taskRepository.save(any(Task.class))).thenReturn(task);

         // Act
         Task acutalResponse = taskServiceImpl.createTask(inputTask);

         // Assert
         assertNotNull(acutalResponse);
         assertEquals(task.getTaskId(), acutalResponse.getTaskId());
         assertEquals(task.getTaskName(), acutalResponse.getTaskName());
         assertEquals(task.getCreatedBy(), acutalResponse.getCreatedBy());
         assertEquals(task.getStatus(), acutalResponse.getStatus());
        }

    @Test
    public void testUpdateTask(){

        // arrange
        // task already arranged in setUp method
        when(taskRepository.findById(task.getTaskId())).thenReturn(Optional.ofNullable(task));

        task.setDescription("Java programming language version - 8");
        task.setStatus(DONE);

        when(taskRepository.save(task)).thenReturn(task);

        //act
        Task acutalResponse=taskServiceImpl.updateTask(task);

    }

    @Test
    public void testGetAllTask(){

        List<Task> expectedList= new ArrayList<>();

        expectedList.add(task);

        Task newTask = new Task();
        newTask.setTaskId(2L);
        newTask.setTaskName("Spring boot");
        newTask.setDescription("spring boot framework - 3.X");
        newTask.setDueDate(LocalDateTime.now().plusDays(5));
        newTask.setStatus(IN_PROGRESS);
        newTask.setCreatedBy("testUser");
        newTask.setCreatedAt(LocalDateTime.now());
        expectedList.add(newTask);

        when(taskRepository.findByCreatedBy("testUser")).thenReturn(expectedList);

        List<Task> acutalList=taskServiceImpl.getAllTasks("testUser");
        // Assert
        assertNotNull(acutalList);
        assertEquals(2,acutalList.size());
        assertEquals(expectedList.size(),acutalList.size());
    }


    @Test
    public void testDeleteTask(){

        List<Task> expectedList= new ArrayList<>();

        //Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        //Act
        taskServiceImpl.deleteTask(task.getTaskId());

        //Assert
        verify(taskRepository).deleteById(task.getTaskId()); // Verify delete was called with the right task
        verify(taskRepository).findById(1L); // Verify existence check
    }
}
