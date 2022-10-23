package ru.zubarev.Library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.zubarev.Library.models.Book;
import ru.zubarev.Library.models.Person;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) throws SQLException {
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book VALUES(name,author,year_of_publication)", book.getName(),
                book.getAuthor(), book.getYearOfPublication());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE book SET name=?, author=?, year_of_publication=? WHERE id=?",
                updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYearOfPublication(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book where id=?", id);
    }

    //Делаем join таблиц Person и Book, получаем человека, которому принадлежит книга с указанным id
    public Optional<Person> getBookOwner(int id) {
        //Выбираем все колонки таблицы Person из объединенной таблицы
        return jdbcTemplate.query("SELECT person.* FROM book JOIN " +
                                "person on book.person_id=person_id WHERE book.id=?"
                        ,
                        new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny();
    }

    //Вызов этого метода делает книгу ничейной при ее возврате в библиотеку
    public void release(int id) {
        jdbcTemplate.update("UPDATE book set person_id=null where id=?",id);
    }
    //Вызов этого метода обеспечивает назначение книги конкртетному человеку,вызывается, когда человек
    //берет книгу из библиотеки
    public void assign(int id,Person selectedPerson){
        jdbcTemplate.update("UPDATE book set person_id=? where id=?",selectedPerson.getId(), id);
    }

}
