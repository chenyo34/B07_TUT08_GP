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

public final class StudentActivityBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button studentActivityAddCourseButton;

  @NonNull
  public final Button studentActivityGenerateTimelineButton;

  @NonNull
  public final Button studentActivityLogOutButton;

  @NonNull
  public final Button studentActivityTakenCourseDisplayButton;

  private StudentActivityBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button studentActivityAddCourseButton,
      @NonNull Button studentActivityGenerateTimelineButton,
      @NonNull Button studentActivityLogOutButton,
      @NonNull Button studentActivityTakenCourseDisplayButton) {
    this.rootView = rootView;
    this.studentActivityAddCourseButton = studentActivityAddCourseButton;
    this.studentActivityGenerateTimelineButton = studentActivityGenerateTimelineButton;
    this.studentActivityLogOutButton = studentActivityLogOutButton;
    this.studentActivityTakenCourseDisplayButton = studentActivityTakenCourseDisplayButton;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static StudentActivityBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static StudentActivityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.student_activity, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static StudentActivityBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.studentActivityAddCourseButton;
      Button studentActivityAddCourseButton = ViewBindings.findChildViewById(rootView, id);
      if (studentActivityAddCourseButton == null) {
        break missingId;
      }

      id = R.id.studentActivityGenerateTimelineButton;
      Button studentActivityGenerateTimelineButton = ViewBindings.findChildViewById(rootView, id);
      if (studentActivityGenerateTimelineButton == null) {
        break missingId;
      }

      id = R.id.studentActivityLogOutButton;
      Button studentActivityLogOutButton = ViewBindings.findChildViewById(rootView, id);
      if (studentActivityLogOutButton == null) {
        break missingId;
      }

      id = R.id.studentActivityTakenCourseDisplayButton;
      Button studentActivityTakenCourseDisplayButton = ViewBindings.findChildViewById(rootView, id);
      if (studentActivityTakenCourseDisplayButton == null) {
        break missingId;
      }

      return new StudentActivityBinding((ConstraintLayout) rootView, studentActivityAddCourseButton,
          studentActivityGenerateTimelineButton, studentActivityLogOutButton,
          studentActivityTakenCourseDisplayButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
