### Задание. Студенты

1.  Разработайте класс <tt>StudentDB</tt>, осуществляющий поиск по базе данных студентов.
    *   Класс <tt>StudentDB</tt> должен реализовывать интерфейс <tt>StudentQuery</tt> (простая версия) или <tt>StudentGroupQuery</tt> (сложная версия).
    *   Каждый метод должен состоять из ровно одного оператора. При этом длинные операторы надо разбивать на несколько строк.
2.  При выполнении задания следует обратить внимание на:
    *   Применение лямбда-выражений и потоков.
    *   Избавление от повторяющегося кода.
    
Тестирование

 * простой вариант:
    ```info.kgeorgiy.java.advanced.student StudentQuery <полное имя класса>```
 * сложный вариант:
    ```info.kgeorgiy.java.advanced.student StudentGroupQuery <полное имя класса>```

Исходный код

 * простой вариант:
    [интерфейс](modules/info.kgeorgiy.java.advanced.student/info/kgeorgiy/java/advanced/student/StudentQuery.java),
    [тесты](modules/info.kgeorgiy.java.advanced.student/info/kgeorgiy/java/advanced/student/FullStudentQueryTest.java)
 * сложный вариант:
    [интерфейс](modules/info.kgeorgiy.java.advanced.student/info/kgeorgiy/java/advanced/student/StudentGroupQuery.java),
    [тесты](modules/info.kgeorgiy.java.advanced.student/info/kgeorgiy/java/advanced/student/FullStudentGroupQueryTest.java)


