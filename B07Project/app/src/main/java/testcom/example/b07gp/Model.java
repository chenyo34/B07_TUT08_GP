package testcom.example.b07gp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
//import androidx.core.util.Consumer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;

import javax.security.auth.callback.Callback;

public class Model {
    private static Model instance;
    private DatabaseReference userRef;
    private DatabaseReference coursesRef;
    private FirebaseAuth auth;

    Model() {
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();
        coursesRef = FirebaseDatabase.getInstance().getReference("CurrentProvidedCourses");

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
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    callback.accept(student);
                                }
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

    public void getStudent(String userID, Consumer<Student> callback) {
        System.out.println("I am inside the get student " + userID);
        userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("Ready to get the student");
                Student s = snapshot.getValue(Student.class);
                System.out.println(s.toString());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    callback.accept(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void saveStudent(Student student, String userID, Consumer<Boolean> callback) {
        userRef.child(userID).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    callback.accept(task.isSuccessful());
                }
            }
        });
    }

    public void getCourses(Consumer<HashMap<String, Course>> callback) {
        coursesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //create a hashmap
                HashMap<String, Course> allCourses = new HashMap<>();
                for (DataSnapshot cns : snapshot.getChildren()) {
                    Course c = cns.getValue(Course.class);
                    allCourses.put(c.getCourseCode(), c);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    callback.accept(allCourses);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

//    public void getCourses(Consumer<Map<String, Course>> callback) {
//        coursesRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //create a hashmap
//                HashMap<String, Course> allCourses = new HashMap<>();
//                for (DataSnapshot cns : snapshot.getChildren()) {
//                    Course c = cns.getValue(Course.class);
//                    allCourses.put(c.getCourseCode(), c);
//                }
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    callback.accept(allCourses);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });
//    }

    public List<Course> getCoursePath(Map<String, Course> allCourses, String courseCode) {

        System.out.println("get in the coursePath with " + courseCode);
        if (!allCourses.containsKey(courseCode)) {
            return null;
        }

        // create the final result
        List<Course> result = new ArrayList<Course>();

//        //create the queue
//        Queue<String> q = new LinkedList<>();
//
//        //add that course
//        q.offer(courseCode);

//        while (!q.isEmpty()) {
//            System.out.println("I am on the while loop");
//            //dequeue that course
//            String cur = q.poll();
//            //get the course from hashmap
//            Course curCourse = allCourses.get(cur);
//            System.out.println(curCourse);
//            result.add(curCourse);
//            //for loop the pre of this course
//            for (String next : curCourse.Precourses) {
//                //add it into the queue
//                q.offer(next);
//            }
//            System.out.println(q);
//        }
        Course cur = allCourses.get(courseCode);
        result.add(cur);

        for (String next: cur.Precourses) {
            if (!Objects.equals(next, "") && next != null) {
                result.addAll(getCoursePath(allCourses, next));
            }

        }
        System.out.println(result);
        return result;
    }


    public void getPrecourse(String coursecode, Consumer<ArrayList<String>> callback) {

//        System.out.println("inside the getPrecourses Functions");
//        HashSet<String> PreCLst = new HashSet<>();
//        ArrayList<String> req = new ArrayList<>();
//
//        PreCLst.add(coursecode);
//
//        getCourseByCode(coursecode, (Course cur) -> {
//            req.addAll(cur.Precourses);
//        });
//
//
//
//        coursesRef.child(coursecode).
    }
    public void getCourseByCode(String coursecode, Consumer<Course> callback) {

        System.out.println("inside the getCourseByCode");
        coursesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot cns : snapshot.getChildren()) {
                    System.out.println(cns.getKey());
                    System.out.println(coursecode);
                    if (Objects.equals(cns.getKey(), coursecode)) {
                        System.out.println(cns.toString());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            callback.accept(cns.getValue(Course.class));
                        }
                    } else {
//                        System.out.println("Problem Detected");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void DeletecourseByCode(String code, Context view, Consumer<String> callback) {
        System.out.println("deleting");

        AlertDialog.Builder builder = new AlertDialog.Builder(view);

        builder.setCancelable(true);
        builder.setTitle("The course will be removed permanently.");
        builder.setMessage(Html.fromHtml("<font color=#00000>"+code+"</font>"));

        builder.setPositiveButton("Removed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                coursesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(code)) {
                            coursesRef.child(code).removeValue();
                            getCourses((HashMap<String, Course> allCourses) -> {
                                for (Map.Entry<String, Course> c : allCourses.entrySet()) {
                                    if (c.getValue().Precourses.contains(code)) {
                                        coursesRef.child(c.getValue().getCourseCode()).setValue(c.getValue().removePre(code));
                                    }
                                }
                            });

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                callback.accept(code);
                            }
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                callback.accept(null);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            };
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(view,
                        "The remove has been cancelled.",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.DKGRAY);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.DKGRAY);
    }

    public void DeleteCourseUpdate(String code, Consumer<String> callback) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user_snapshot : snapshot.getChildren()) {
                    if (user_snapshot.hasChild("takenCourses")) {
                        String id = user_snapshot.getKey().toString();
                        getStudent(id, (Student student) -> {
                            if (student.TakenCourses.contains(code)) {
                                student.removeTaken(code);
                                saveStudent(student, id, (Boolean success) -> {
                                });
                            }
                        });
                    }

                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    callback.accept(code);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
