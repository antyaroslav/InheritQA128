import javamaven.Epic;
import javamaven.Meeting;
import javamaven.SimpleTask;
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
    void shouldEpicMatchByAnySubtask() {
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subtasks);

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
    void shouldMeetingNotMatchByStart() {
        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        assertFalse(meeting.matches("вторник"));
        assertFalse(meeting.matches("обеда"));
    }

    @Test
    void shouldEpicHandleEmptySubtasksArray() {
        Epic epic = new Epic(99, new String[]{});
        assertFalse(epic.matches("любой запрос"));
    }

    @Test
    void shouldEpicHandleNullQuery() {
        Epic epic = new Epic(99, new String[]{"Молоко", "Хлеб"});
        assertTrue(epic.matches(""));
    }
}