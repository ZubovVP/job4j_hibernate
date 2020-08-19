package ru.job4j.todoList.servlets;

import org.junit.Ignore;
import org.powermock.api.mockito.PowerMockito;
import ru.job4j.todoList.models.Item;
import ru.job4j.todoList.storage.ValidateService;
import ru.job4j.todoList.storage.operations.Actions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 26.04.2020.
 */
//@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "com.sun.org.apache.xalan.*"})
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(ValidateService.class)
public class ItemCreateServletTest {
    private HttpServletResponse resp;
    private HttpServletRequest req;

    @Ignore
    public void addElementAndCheckElement() throws IOException, ServletException {
        Actions<Item> validate = new ValidateService();
        PowerMockito.mockStatic(ValidateService.class);
        when(new ValidateService()).thenReturn((ValidateService) validate);
        this.req = mock(HttpServletRequest.class);
        this.resp = mock(HttpServletResponse.class);
        when(this.req.getParameter("desc")).thenReturn("DescriptionTest");
        new ItemCreateServlet().doPost(this.req, this.resp);
        Iterator<Item> itr = validate.getAllElements().iterator();
        assertThat(itr.next().getDescription(), is("DescriptionTest"));
    }
}