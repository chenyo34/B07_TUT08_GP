package testcom.example.b07gp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.function.Consumer;

public class Model {
    private static Model instance;
    private DatabaseReference userRef;
    private FirebaseAuth auth;

    Model() {
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();
    }

    public static Model getInstance() {
        if (instance == null)
            instance = new Model();
        return instance;
    }

    public void authenticate(String email, String password, Consumer<User> callback) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()) {
                    callback.accept(null);
                } else {
                    //log in successful
                    userRef.child(Objects.requireNonNull(auth.getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            callback.accept(user);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    //new change
//    public void register(String name, String email, String password, String type, Consumer<User> callback) {
//        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(!task.isSuccessful()) {
//                    callback.accept(null);
//                } else {
//                    User user = new User(name, email, password, type);
//                    user.UTORid = auth.getCurrentUser().getUid();
//                    callback.accept(user);
//                }
//            }
//        })
//    }
}
