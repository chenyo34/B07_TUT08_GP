// Generated by view binder compiler. Do not edit!
package com.example.courseplanner.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.courseplanner.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class StudentTakenCourseDisplayBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button studentTakenCourseDisplayReturn;

  private StudentTakenCourseDisplayBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button studentTakenCourseDisplayReturn) {
    this.rootView = rootView;
    this.studentTakenCourseDisplayReturn = studentTakenCourseDisplayReturn;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static StudentTakenCourseDisplayBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static StudentTakenCourseDisplayBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.student_taken_course_display, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static StudentTakenCourseDisplayBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.studentTakenCourseDisplayReturn;
      Button studentTakenCourseDisplayReturn = ViewBindings.findChildViewById(rootView, id);
      if (studentTakenCourseDisplayReturn == null) {
        break missingId;
      }

      return new StudentTakenCourseDisplayBinding((ConstraintLayout) rootView,
          studentTakenCourseDisplayReturn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
