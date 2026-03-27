import javamaven.Epic;
import javamaven.Meeting;
import javamaven.SimpleTask;
import javamaven.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TasksTest {

    @Test
    void shouldSimpleTaskMatchByTitle() {
        SimpleTask task = new SimpleTask(1, "Позвонить родителям");
        assertTrue(task.matches("родителям"));
        assertTrue(task.matches("Позвонить"));
        assertFalse(task.matches("Сходить"));
    }

    @Test
    void shouldSimpleTaskNotMatchCaseSensitive() {
        SimpleTask task = new SimpleTask(1, "Позвонить родителям");
        assertFalse(task.matches("РОДИТЕЛЯМ"));
    }

    @Test
    void shouldSimpleTaskGetAllFieldsAsArray() {
        SimpleTask task = new SimpleTask(1, "Купить хлеб");
        Task[] taskArray = {task};
        assertArrayEquals(new Task[]{task}, new Task[]{task});
    }

    @Test
    void shouldEpicMatchByAnySubtask() {
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subtasks);

        assertArrayEquals(subtasks, epic.getSubtasks());

        assertTrue(epic.matches("Молоко"));
        assertTrue(epic.matches("Яйца"));
        assertTrue(epic.matches("Хлеб"));
        assertFalse(epic.matches("Сыр"));
    }

    @Test
    void shouldEpicGetSubtasksReturnsCopy() {
        String[] originalSubtasks = {"Молоко", "Хлеб", "Яйца"};
        Epic epic = new Epic(55, originalSubtasks);
        assertArrayEquals(originalSubtasks, epic.getSubtasks());
    }

    @Test
    void shouldEpicHandleEmptySubtasksArray() {
        Epic epic = new Epic(99, new String[]{});
        assertArrayEquals(new String[]{}, epic.getSubtasks());
        assertFalse(epic.matches("любой запрос"));
    }

    @Test
    void shouldMeetingMatchByTopic() {
        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        assertTrue(meeting.matches("Выкатка"));
        assertTrue(meeting.matches("версии"));
        assertFalse(meeting.matches("Совещание"));
    }

    @Test
    void shouldMeetingMatchByProject() {
        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        assertTrue(meeting.matches("НетоБанка"));
        assertTrue(meeting.matches("Приложение"));
        assertFalse(meeting.matches("Сбербанк"));
    }

    @Test
    void shouldMeetingGetAllFieldsAsArray() {
        Meeting meeting = new Meeting(
                555,
                "Выкатка",
                "Приложение",
                "Во вторник"
        );
        String[] expectedFields = {"Выкатка", "Приложение", "Во вторник"};
        assertArrayEquals(new String[]{"Выкатка", "Приложение", "Во вторник"}, expectedFields);
    }

    @Test
    void shouldAllTaskTypesCanBeStoredInArray() {
        Task[] allTasks = {
                new SimpleTask(1, "Задача 1"),
                new Epic(2, new String[]{"Подзадача 1", "Подзадача 2"}),
                new Meeting(3, "Тема", "Проект", "Дата")
        };

        assertArrayEquals(allTasks, allTasks);
    }

    @Test
    void shouldMultipleEpicsStoredInArray() {
        Epic epic1 = new Epic(100, new String[]{"A", "B"});
        Epic epic2 = new Epic(200, new String[]{"C", "D"});
        Epic[] epicArray = {epic1, epic2};

        assertArrayEquals(epicArray, new Epic[]{epic1, epic2});
    }
}