import javamaven.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TodosTest {

    @Test
    void shouldAddThreeTasksOfDifferentType() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");

        String[] subtasks = { "Молоко", "Яйца", "Хлеб" };
        Epic epic = new Epic(55, subtasks);

        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        Todos todos = new Todos();

        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = { simpleTask, epic, meeting };
        Task[] actual = todos.findAll();
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldSearchFindSimpleTaskByTitle() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(1, "Купить хлеб"));
        todos.add(new SimpleTask(2, "Сходить в магазин"));
        todos.add(new SimpleTask(3, "Позвонить другу"));

        Task[] result = todos.search("хлеб");
        assertEquals(1, result.length);
        assertEquals(1, result[0].getId());
    }

    @Test
    void shouldSearchFindEpicBySubtask() {
        Todos todos = new Todos();
        todos.add(new Epic(10, new String[]{"Молоко", "Сыр"}));
        todos.add(new Epic(20, new String[]{"Хлеб", "Яйца"}));
        todos.add(new Epic(30, new String[]{"Кофе", "Сахар"}));

        Task[] result = todos.search("Хлеб");
        assertEquals(1, result.length);
        assertEquals(20, result[0].getId());
    }

    @Test
    void shouldSearchFindMeetingByTopic() {
        Todos todos = new Todos();
        todos.add(new Meeting(100, "Обсуждение бюджета", "Проект А", "Понедельник"));
        todos.add(new Meeting(200, "Планирование спринта", "Проект Б", "Вторник"));

        Task[] result = todos.search("бюджета");
        assertEquals(1, result.length);
        assertEquals(100, result[0].getId());
    }

    @Test
    void shouldSearchFindMeetingByProject() {
        Todos todos = new Todos();
        todos.add(new Meeting(100, "Обсуждение бюджета", "Проект А", "Понедельник"));
        todos.add(new Meeting(200, "Планирование спринта", "Проект Б", "Вторник"));

        Task[] result = todos.search("Проект Б");
        assertEquals(1, result.length);
        assertEquals(200, result[0].getId());
    }

    @Test
    void shouldSearchFindMultipleTasks() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(1, "Молоко"));
        todos.add(new Epic(2, new String[]{"Молоко"}));
        todos.add(new Meeting(3, "Молоко", "Молоко", "Среда"));

        Task[] result = todos.search("Молоко");
        assertEquals(3, result.length);
    }

    @Test
    void shouldSearchReturnEmptyArrayWhenNoMatches() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(1, "Купить хлеб"));
        todos.add(new SimpleTask(2, "Сходить в магазин"));

        Task[] result = todos.search("кофе");
        assertEquals(0, result.length);
    }

    @Test
    void shouldSearchWorkWithEmptyTodos() {
        Todos todos = new Todos();
        Task[] result = todos.search("любой запрос");
        assertEquals(0, result.length);
    }

    @Test
    void shouldNotModifyOriginalArrays() {
        String[] originalSubtasks = { "Молоко", "Хлеб" };
        Epic epic = new Epic(55, originalSubtasks);

        originalSubtasks[0] = "Сыр";

        String[] epicSubtasks = epic.getSubtasks();
        assertEquals("Молоко", epicSubtasks[0]);
    }
}