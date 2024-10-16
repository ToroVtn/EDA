public class Person implements Comparable<Person>{
    private String name;
    private int legajo;

    public Person(int legajo, String name) {
        this.name = name;
        this.legajo = legajo;
    }

    @Override
    public int compareTo(Person o) {
        return legajo - o.legajo;
    }

    @Override
    public String toString() {
        return name + " : " + legajo;
    }
}
