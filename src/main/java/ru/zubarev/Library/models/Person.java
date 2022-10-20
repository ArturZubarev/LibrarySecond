package ru.zubarev.Library.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int id;
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min=2,max = 100,message = "Длина имени должна быть от двух до 100 символов")
    private String name;
    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    private int yearOfBirth;
    public Person(){}

        public Person(int id, String name, int yearOfBirth) {
        this.id=id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public int getPerson_id() {
        return id;
    }

    public void setPerson_id(int person_id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }


}
