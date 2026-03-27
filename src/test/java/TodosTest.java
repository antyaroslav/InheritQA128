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
        assertArrayEquals(new Task[]{}, new Task[0]);
        assertEquals(1, result.length);
    }

    @Test
    void shouldSearchFindEpicBySubtask() {
        Todos todos = new Todos();
        todos.add(new Epic(10, new String[]{"Молоко", "Сыр"}));
        todos.add(new Epic(20, new String[]{"Хлеб", "Яйца"}));
        todos.add(new Epic(30, new String[]{"Кофе", "Сахар"}));
        Task[] result = todos.search("Хлеб");
        assertArrayEquals(new Task[]{new Epic(20, new String[]{"Хлеб", "Яйца"})}, result);
    }

    @Test
    void shouldSearchFindMeetingByTopic() {
        Todos todos = new Todos();
        todos.add(new Meeting(100, "Обсуждение бюджета", "Проект А", "Понедельник"));
        todos.add(new Meeting(200, "Планирование спринта", "Проект Б", "Вторник"));
        Task[] result = todos.search("бюджета");
        assertArrayEquals(new Task[]{new Meeting(100, "Обсуждение бюджета", "Проект А", "Понедельник")}, result);
    }

    @Test
    void shouldSearchFindMeetingByProject() {
        Todos todos = new Todos();
        todos.add(new Meeting(100, "Обсуждение бюджета", "Проект А", "Понедельник"));
        todos.add(new Meeting(200, "Планирование спринта", "Проект Б", "Вторник"));
        Task[] result = todos.search("Проект Б");
        assertArrayEquals(new Task[]{new Meeting(200, "Планирование спринта", "Проект Б", "Вторник")}, result);
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
        assertArrayEquals(new Task[]{}, result);
    }

    @Test
    void shouldSearchWorkWithEmptyTodos() {
        Todos todos = new Todos();
        Task[] result = todos.search("любой запрос");
        assertArrayEquals(new Task[]{}, result);
    }

    @Test
    void shouldReturnCopiesOfArrays() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(1, "Задача"));
        Task[] found = todos.findAll();
        assertArrayEquals(todos.findAll(), found);
    }

    @Test
    void shouldNotModifyOriginalArrays() {
        String[] originalSubtasks = {"Молоко", "Хлеб"};
        Epic epic = new Epic(55, originalSubtasks);
        String[] expectedCopy = {"Молоко", "Хлеб"};
        originalSubtasks[0] = "Сыр";
        String[] epicSubtasks = epic.getSubtasks();
        assertArrayEquals(expectedCopy, epicSubtasks);
    }

    @Test
    void shouldReturnAllTasksAsArray() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(1, "Задача 1"));
        todos.add(new SimpleTask(2, "Задача 2"));
        todos.add(new SimpleTask(3, "Задача 3"));

        Task[] expected = {
                new SimpleTask(1, "Задача 1"),
                new SimpleTask(2, "Задача 2"),
                new SimpleTask(3, "Задача 3")
        };

        assertArrayEquals(expected, todos.findAll());
    }

    @Test
    void shouldSearchReturnEmptyArrayInEmptyList() {
        Todos todos = new Todos();
        assertArrayEquals(new Task[]{}, todos.search("тест"));
    }

    @Test
    void shouldAddMultipleSimpleTasksAndVerifyArray() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(1, "A"));
        todos.add(new SimpleTask(2, "B"));
        todos.add(new SimpleTask(3, "C"));

        Task[] all = todos.findAll();
        assertArrayEquals(
                new Task[]{new SimpleTask(1, "A"), new SimpleTask(2, "B"), new SimpleTask(3, "C")},
                all
        );
    }

    @Test
    void shouldSearchMixedTypesWithArrayComparison() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(1, "Текст"));
        todos.add(new Meeting(2, "Поиск", "Текст", "Дата"));

        Task[] results = todos.search("Текст");
        assertArrayEquals(
                new Task[]{new SimpleTask(1, "Текст"), new Meeting(2, "Поиск", "Текст", "Дата")},
                results
        );
    }
}