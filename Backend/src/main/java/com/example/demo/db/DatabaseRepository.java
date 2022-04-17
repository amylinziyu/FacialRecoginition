package com.example.demo.db;

public interface DatabaseRepository {

    int savePerson(Person person);

    int saveImage(Image image);

    Person getPerson(int userId);

    Image getImage(int userId);

}
