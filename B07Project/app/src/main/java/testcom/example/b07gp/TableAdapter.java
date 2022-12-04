package testcom.example.b07gp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    Context context;
    List<Table> tableList;

    public TableAdapter(Context context, List<Table> tableList){
        this.context = context;
        this.tableList = tableList;

    }

    @NonNull
    @Override
    public TableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableAdapter.ViewHolder holder, int position) {
        if (tableList != null && tableList.size() > 0){
            Table table = tableList.get(position);
            holder.table_course.setText(table.courses);
            holder.table_session.setText(table.session);
        }
        else{
            return;
        }
    }

    @Override
    public int getItemCount() {
        return tableList.size();
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
