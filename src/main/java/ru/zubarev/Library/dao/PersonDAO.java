package ru.zubarev.Library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.zubarev.Library.models.Book;
import ru.zubarev.Library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, year_of_birth) values (?,?)", person.getName(),
                person.getYearOfBirth());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person set name=?,year_of_birth=? where id=?",
                updatedPerson.getName(), updatedPerson.getYearOfBirth(), id);

    }
    public void delete(int id){
        jdbcTemplate.update("DELETE from person where id=?",id);
    }

    //Для валидации уникальности ФИО
    public Optional<Person> getPersonByName(String name) {
        return jdbcTemplate.query("SELECT * FROM person where id=?",
                new Object[]{name}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public List<Book> getBooksByPersonID(int id){
        return jdbcTemplate.query("SELECT* from book WHERE person_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}