package com.kabir.huiping.javatest;

class Person {
    private int age;
    private String name;
    private String vocation;

    public Person(int age, String name, String vocation) {
        setAge(age);
        this.name = name;
        this.vocation = vocation;
    }

    private static void test() {
        Person kabir = new Person(30, "Kabir", "Engineer");
        kabir.setAge(18);
        System.out.println(kabir);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age > 140 || age < 0)
            throw new ArrayIndexOutOfBoundsException();
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", vocation='" + vocation + '\'' +
                '}';
    }
}
