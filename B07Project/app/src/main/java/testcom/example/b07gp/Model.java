package testcom.example.b07gp;

import android.os.Build;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
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

//    DatabaseReference curRef = userRef.child(Objects.requireNonNull(auth.getUid()));
//
////                    System.out.println("Student1");
//                    curRef.addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            User curUser = new User();
////                            System.out.println(snapshot.child("type").getValue());
////                            System.out.println("Student2");
//
//
//            if (Objects.equals(snapshot.child("type").getValue(), "student")) {
//                System.out.println("i AM IN ");
//                curUser = snapshot.getValue(Student.class);
////                                System.out.println(curUser.toString());
//            } else {
//                curUser = snapshot.getValue(Admin.class);
//            }

    public void authenticate(String email, String password, Consumer<User> callback) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    System.out.println("cannot find");
                    if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.N))
                        callback.accept(null);
                } else {
                    userRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            if (user.type.equals("admin")) {
                                User admin = snapshot.getValue(Admin.class);
                                if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.N))
                                    callback.accept(admin);
                            } else {
                                User student = snapshot.getValue(Student.class);
                                if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.N))
                                    callback.accept(student);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    public void getCourse(String courseCode, Consumer<User> callback) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //create a hashmap
                HashMap<String, Course> allCourses = new HashMap<>();
                for(DataSnapshot cns : snapshot.getChildren()) {
                    Course c = cns.getValue(Course.class);
                    allCourses.put(c.CourseCode, c);
                }

                //create the final result
                List<Course> result = new ArrayList<Course>();

                //create the queue
                Queue<String> q = new LinkedList<>();

                //add that course
                q.offer(courseCode);

                while (!q.isEmpty()) {
                    //dequeue that course
                    String cur = q.poll();
                    //get the course from hashmap
                    Course curCourse = allCourses.get(courseCode);
                    result.add((allCourses.get(cur)));
                    //for loop the pre of this course
                    for (String code : curCourse.Precourses) {
                        //add it into the queue
                        q.offer(code);
                    }
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    callback.accept((User) result);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
