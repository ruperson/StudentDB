package ru.ifmo.rain.vanyan.student;

import info.kgeorgiy.java.advanced.student.Group;
import info.kgeorgiy.java.advanced.student.Student;
import info.kgeorgiy.java.advanced.student.StudentGroupQuery;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB implements StudentGroupQuery {
    private List<String> getSomeStrings(final List<Student> students, Function<Student, String> mapFunc) {
        return students.stream()
                .map(mapFunc)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFirstNames(final List<Student> students) {
        return getSomeStrings(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(final List<Student> students) {
        return getSomeStrings(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(final List<Student> students) {
        return getSomeStrings(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(final List<Student> students) {
        return getSomeStrings(students, student -> student.getFirstName() + " " + student.getLastName());
    }

    private List<Student> sortStudentsBy(Collection<Student> students, Comparator<Student> comparator) {
        return students.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private static final Comparator<Student> COMPARATOR_BY_ID = Comparator.comparing(Student::getId);

    private static final Comparator<Student> COMPARATOR_BY_NAME =
            Comparator.comparing(Student::getLastName)
                    .thenComparing(Student::getFirstName)
                    .thenComparing(Student::getId);

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return sortStudentsBy(students, COMPARATOR_BY_ID);
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return sortStudentsBy(students, COMPARATOR_BY_NAME);
    }


    private List<Student> findStudentBy(Collection<Student> students, Function<Student, String> mapFunc, String name) {
        return students.stream()
                .filter(student -> mapFunc.apply(student).equals(name))
                .sorted(COMPARATOR_BY_NAME)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return findStudentBy(students, Student::getFirstName, name);
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return findStudentBy(students, Student::getLastName, name);
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return findStudentBy(students, Student::getGroup, group);
    }

    private Stream<Map.Entry<String, List<Student>>> getGroupEntryStream(Collection<Student> students) {
        return students.stream()
                .collect(Collectors.groupingBy(Student::getGroup)) //map<группа, лист студентов>
                .entrySet().stream();
    }

    //теперь и сами группы посорчены и внутри группы студенты по компаратору
    private List<Group> getGroupsSortedBy(Collection<Student> students, Comparator<Student> comparator) {
        return getGroupEntryStream(students)
                .map(mapEntry -> new Group(mapEntry.getKey(), sortStudentsBy(mapEntry.getValue(), comparator)))
                .sorted(Comparator.comparing(Group::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Group> getGroupsByName(Collection<Student> students) {
        return getGroupsSortedBy(students, COMPARATOR_BY_NAME);
    }

    @Override
    public List<Group> getGroupsById(Collection<Student> students) {
        return getGroupsSortedBy(students, COMPARATOR_BY_ID);
    }

    private String getLargestGroupBy(Collection<Student> students, Comparator<Group> comparator) {
        return getGroupEntryStream(students)
                .map(entry -> new Group(entry.getKey(), entry.getValue()))
                .max(comparator)
                .map(Group::getName).orElse("");
    }

    @Override
    public String getLargestGroup(Collection<Student> students) {
        return getLargestGroupBy(students, Comparator.<Group, Integer>comparing(group -> group.getStudents().size())
                .thenComparing(Comparator.comparing(Group::getName).reversed()));
    }


    @Override
    public String getLargestGroupFirstName(Collection<Student> students) {
        return getLargestGroupBy(students, Comparator.<Group, Integer>comparing(group -> getDistinctFirstNames(group.getStudents()).size())
                .thenComparing(Comparator.comparing(Group::getName).reversed()));
    }


    @Override
    public Map<String, String> findStudentNamesByGroup(final Collection<Student> students, final String group) {
        return students.stream()
                .filter(student -> student.getGroup().equals(group))
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName, BinaryOperator.minBy(String::compareTo)));
    }


    @Override
    public Set<String> getDistinctFirstNames(final List<Student> students) {
        return students.stream()
                .map(Student::getFirstName)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public String getMinStudentFirstName(final List<Student> students) {
        return students.stream()
                .min(COMPARATOR_BY_ID)
                .map(Student::getFirstName)
                .orElse("");
    }
}