public class Person implements Comparable<Person>{
    private String name;
    private int age;

    public Person(int age, String name) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person o) {
        return age - o.age;
    }
}
