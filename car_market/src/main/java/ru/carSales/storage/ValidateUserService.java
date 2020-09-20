package ru.carSales.storage;

import ru.carSales.models.UserForSales;
import ru.carSales.storage.encrypt.Encryption;
import ru.carSales.storage.operations.Actions;
import ru.carSales.storage.operations.FindUserAble;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 28.07.2020.
 */
public class ValidateUserService implements Actions<UserForSales>, FindUserAble<UserForSales> {
    private final static DBStorage_user<UserForSales> STORAGE = DBStorage_user.getInstance();
    private Encryption encrypt = Encryption.getInstance();
    private static ValidateUserService ourInstance = new ValidateUserService();

    public static ValidateUserService getInstance() {
        return ourInstance;
    }

    private ValidateUserService() {
    }

    /**
     * Check element if correct, then call DB.add().
     *
     * @param element - element.
     * @return - result.
     */
    @Override
    public UserForSales add(UserForSales element) {
        UserForSales user = element;
        if (user.getPassword() != null && !user.getPassword().equals("") && user.getEmail() != null && !user.getEmail().equals("") && user.getTelephone() != null && !user.getTelephone().equals("")) {
            user.setEmail(this.encrypt.encrypt(element.getEmail()));
            user.setPassword(this.encrypt.encrypt(element.getPassword()));
            STORAGE.add(user);
            return element;
        } else {
            throw new IncorrectDateException("Wrong for add user");
        }
    }

    /**
     * Call STORAGE.getAllElements().
     *
     * @return - List of elements.
     */
    @Override
    public List<UserForSales> getAllElements() {
        return STORAGE.getAllElements();
    }

    /**
     * Call STORAGE.delete(id).
     *
     * @param id - id of an element.
     * @return - result.
     */
    @Override
    public boolean delete(int id) {
        if (id > 0) {
            return STORAGE.delete(id);
        } else {
            throw new IncorrectDateException("Incorrect id for delete user");
        }
    }

    /**
     * Update an element from DB.
     *
     * @param element - item.
     * @return - result.
     */
    @Override
    public boolean update(UserForSales element) {
        if (element.getId() > 0 && element.getTelephone() != null && !element.getTelephone().equals("") && element.getPassword() != null && !element.getPassword().equals("") && element.getEmail() != null && !element.getEmail().equals("")) {
            element.setEmail(this.encrypt.encrypt(element.getEmail()));
            element.setPassword(this.encrypt.encrypt(element.getPassword()));
            STORAGE.update(element);
        } else {
            throw new IncorrectDateException("Wrong element for update");
        }
        return true;
    }

    /**
     * Find element by id.
     *
     * @param id - id of an item.
     * @return- Item
     */
    @Override
    public UserForSales find(int id) {
        UserForSales result;
        if (id == 0) {
            throw new IncorrectDateException("Wrong id for find user");
        } else {
            result = STORAGE.find(id);
        }
        return result;
    }

    /**
     * Close DB.
     */
    @Override
    public void close() {
        STORAGE.close();
    }

    /**
     * Check email and password, if correct than find in DB.
     *
     * @param email    - e-mail.
     * @param password - password.
     * @return - user.
     */
    @Override
    public UserForSales findUser(String email, String password) {
        UserForSales result;
        if (email != null && password != null && !email.equals("") && !password.equals("")) {
            result = STORAGE.findUser(encrypt.encrypt(email), encrypt.encrypt(password));
        }
        else {
            throw new IncorrectDateException("Wrong e-mail or password for find user.");
        }
        return result;
    }
}