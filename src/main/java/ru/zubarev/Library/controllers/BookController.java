package ru.zubarev.Library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zubarev.Library.dao.BookDAO;
import ru.zubarev.Library.dao.PersonDAO;
import ru.zubarev.Library.models.Book;
import ru.zubarev.Library.models.Person;

import java.sql.SQLException;
import java.util.Optional;

@Controller
@RequestMapping("/book")
@Component
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "/book/index";
    }
    //Если у книги есть владелец, то под ключом Owner будет лежать человек-владелец книги
    //Если владелец отсутствует, то под ключом People будет выведен спискок всех людей из таблицы Person
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person")Person person) throws SQLException {
        model.addAttribute("book", bookDAO.show(id));
        Optional<Person> bookOwner=bookDAO.getBookOwner(id);
        if (bookOwner.isPresent())
            model.addAttribute("owner",bookOwner.get());
        else model.addAttribute("people", personDAO.index());
        return "book/show";
    }

    @GetMapping("book/new")
    public String newBook(@ModelAttribute("book") Book book) {

        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/new";

        bookDAO.save(book);
        return "redirect:/book";//метод дописан, лезть не нужно
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) throws SQLException {
        model.addAttribute("book", bookDAO.show(id));
        return "book/edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("book") Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "book/edit";

        bookDAO.update(id, book);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/book";
    }
    //освобождает книгу
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookDAO.release(id);
        return "redirect:/book/"+id;
    }
    //назначает книгу человеку
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id,@ModelAttribute("person")Person selectedPerson){
        bookDAO.assign(id, selectedPerson);
        return "redirect:/book/"+id;
    }
}



