package vtc.room.a101.notificationapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private final Context context;
    private final List<NotificationModel> list;

    public NotificationAdapter(Context context, List<NotificationModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.notification_item_style, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        holder.notImage.setImageResource(list.get(position).getImage());
        holder.notName.setText(list.get(position).getName());
        holder.notTime.setText(list.get(position).getTime());
        holder.notDate.setText(list.get(position).getDate());
        if (list.get(position).isTurned() == 1) {
            holder.notSwitch.setChecked(true);
        } else {
            holder.notSwitch.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {
        private ImageView notImage;
        private TextView notName;
        private TextView notTime;
        private TextView notDate;
        private Switch notSwitch;

        NotificationViewHolder(View itemView) {
            super(itemView);
            findViews(itemView);
            setSwitchListener();
        }

        private void findViews(View view) {
            notImage = view.findViewById(R.id.item_image);
            notName = view.findViewById(R.id.item_name);
            notTime = view.findViewById(R.id.item_time);
            notDate = view.findViewById(R.id.item_date);
            notSwitch = view.findViewById(R.id.switch_for_item);
        }

        private void setSwitchListener() {
            notSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        list.get(getAdapterPosition()).setTurned(1);
                    } else {
                        list.get(getAdapterPosition()).setTurned(0);
                    }
                }
            });
        }
    }
}
