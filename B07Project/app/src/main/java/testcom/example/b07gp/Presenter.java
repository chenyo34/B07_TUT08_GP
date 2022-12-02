package testcom.example.b07gp;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Toast;

public class Presenter {
    private Model model;
    private MainActivity view;

    public Presenter(Model model, MainActivity view) {
        this.model = model;
        this.view = view;
    }

    public void login(String email, String password) {
        //call model's authenticate to check if I am Student or Admin
        model.authenticate(email, password, (User user) -> {
            if (user == null) {
                view.failedToLogin();
            }
            //get the callback and then assess it if it is student of Admin
            else if(user instanceof Student) {
                view.ToStudentPages(user.UTORid);
            }

            else if(user instanceof Admin) {
                view.ToAdminPages(user.UTORid);
            }
        });
    }
}
