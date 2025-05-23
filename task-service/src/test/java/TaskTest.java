import com.studyplanner.taskservice.controllers.TaskController;
import com.studyplanner.taskservice.models.Task;
import com.studyplanner.taskservice.services.TaskServices;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;

import static com.studyplanner.taskservice.models.TaskStatus.IN_PROGRESS;

public class TaskTest {

    @Mock
    private TaskServices taskService;


    //success
    @Test
    public void testCreateTask(){

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
