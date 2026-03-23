import javamaven.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TodosTest {

    @Test
    void shouldAddThreeTasksOfDifferentType() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
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
        Task[] expected = {simpleTask, epic, meeting};
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
        SimpleTask task1 = new SimpleTask(1, "Молоко");
        Epic task2 = new Epic(2, new String[]{"Молоко"});
        Meeting task3 = new Meeting(3, "Тема", "Молоко", "Среда");
        todos.add(task1);
        todos.add(task2);
        todos.add(task3);
        Task[] expected = {task1, task2, task3};
        Task[] actual = todos.search("Молоко");
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldSearchReturnEmptyArrayWhenNoMatches() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(1, "Купить хлеб"));
        todos.add(new SimpleTask(2, "Сходить в магазин"));
        Task[] result = todos.search("кофе");
        assertEquals(0, result.length);
        assertArrayEquals(new Task[]{}, result);
    }

    @Test
    void shouldSearchWorkWithEmptyTodos() {
        Todos todos = new Todos();
        Task[] result = todos.search("любой запрос");
        assertEquals(0, result.length);
        assertArrayEquals(new Task[]{}, result);
    }

    @Test
    void shouldNotModifyOriginalArrays() {
        String[] originalSubtasks = {"Молоко", "Хлеб"};
        Epic epic = new Epic(55, originalSubtasks);
        originalSubtasks[0] = "Сыр";
        String[] epicSubtasks = epic.getSubtasks();
        assertEquals("Молоко", epicSubtasks[0]);
        assertEquals("Хлеб", epicSubtasks[1]);
        assertArrayEquals(new String[]{"Молоко", "Хлеб"}, epicSubtasks);
    }
}