enum Course {
    JAVA, PYTHON, CPP
}
class Student {
    String name;
    Course course;

    Student(String name, Course course) {
        this.name = name;
        this.course = course;
    }
}
List<Student> students = List.of(
        new Student("Alex", Course.JAVA),
        new Student("Maria", Course.PYTHON),
        new Student("Ivan", Course.JAVA),
        new Student("Anna", Course.CPP)
);
Map<Course, List<Student>> groups = students.stream()
        .collect(Collectors.groupingBy(
                s -> s.course,
                () -> new EnumMap<>(Course.class),
                Collectors.toList()
        ));

void main() {

}
