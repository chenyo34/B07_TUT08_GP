package testcom.example.b07gp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    Context context;
    List<Course> courseList;

    public CourseAdapter (Context context, List<Course> courseList){
        this.context = context;
        this.courseList = courseList;

    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        if (courseList != null && courseList.size() > 0){
            Course course = courseList.get(position);
            holder.table_course.setText(course.getCourseCode());
            holder.table_session.setText(course.getOfferingSessions().toString());
        }
        else{
            return;
        }
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView table_course, table_session;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            table_course = itemView.findViewById(R.id.table_course);
            table_session = itemView.findViewById(R.id.table_session);
        }
    }
}
