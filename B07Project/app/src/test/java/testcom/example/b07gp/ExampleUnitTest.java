package testcom.example.b07gp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.function.Consumer;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href=" ">Testing documentation</a >
 */

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    MainActivity view;

    @Mock
    Model model;

    @Captor
    private ArgumentCaptor<Consumer<User>> captor;


    @Test
    public void testStudentLogin() {
        String mail = "chen@stu.com";
        String password = "123123";

        User student = new Student();
        student.type = "student";

        Presenter presenter = new Presenter(model, view);

        presenter.login(mail, password);

        //verify model.authenticate() has run
        verify(model).authenticate(eq(mail), eq(password), captor.capture());
        Consumer<User> callback = captor.getValue();
        callback.accept(student);

        verify(view, times(1)).ToStudentPages(any());
    }

    @Test
    public void testStudentLoginFailed(){
        String mail = "chen@stu.com";
        String password = "123123";

        User student = null;

        Presenter presenter = new Presenter(model, view);

        presenter.login(mail, password);
        //verify model.authenticate() has run
        verify(model).authenticate(eq(mail), eq(password), captor.capture());
        Consumer<User> callback = captor.getValue();
        callback.accept(student);

        //verify if view.toAdmin has run
        verify(view, times(0)).ToStudentPages(any());

        verify(view, times(1)).failedToLogin();
    }

    @Test
    public void testAdminLogin() {
        String mail = "chen@adm.com";
        String password = "123123";

        User admin = new Admin();
        admin.type = "Admin";

        Presenter presenter = new Presenter(model, view);

        presenter.login(mail, password);

        //verify model.authenticate() has run
        verify(model).authenticate(eq(mail), eq(password), captor.capture());
        Consumer<User> callback = captor.getValue();
        callback.accept(admin);

        verify(view, times(1)).ToAdminPages(any());
    }

    @Test
    public void testAdminLoginFailed(){
        String mail = "chen@adm.com";
        String password = "123123";

        User admin = null;

        Presenter presenter = new Presenter(model, view);

        presenter.login(mail, password);
        //verify model.authenticate() has run
        verify(model).authenticate(eq(mail), eq(password), captor.capture());
        Consumer<User> callback = captor.getValue();
        callback.accept(admin);

        //verify if view.toAdmin has run
        verify(view, times(0)).ToAdminPages(any());

        verify(view, times(1)).failedToLogin();
    }
}