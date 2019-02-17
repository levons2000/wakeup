package vtc.room.a101.notificationapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddNotificationDialog extends DialogFragment {

    private static final String TOAST_TEXT = "This name already exists";
    private static final String CUSTOM_ACTION = "android.intent.action.MAIN";

    private final Calendar calendar = Calendar.getInstance();
    private String name, date, time;
    private int day, hour, minute;
    private int image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.add_dialog_style, container, false);
        addNotification(view);
        setType(view);
        setData(view);
        setTime(view);
        return view;
    }

    private void setData(View view) {
        final ImageButton dataButton = view.findViewById(R.id.set_date);
        dataButton.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        final DatePickerDialog dialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                date = i2 + "." + (i1 + 1) + "." + i;
                                day = i2;
                                calendar.set(Calendar.YEAR, i);
                                calendar.set(Calendar.MONTH, i1);
                                calendar.set(Calendar.DAY_OF_MONTH, i2);
                            }
                            },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                        dialog.show();
                    }
                }
        );
    }

    private void setTime(View view) {
        final ImageButton timeButton = view.findViewById(R.id.set_time);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        time = i + ":" + i1;
                        hour = i;
                        minute = i1;
                        calendar.set(Calendar.HOUR_OF_DAY, i);
                        calendar.set(Calendar.MINUTE, i1);
                    }
                },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true);
                dialog.show();
            }
        });
    }

    private void setType(View view) {
        final Spinner spinner = view.findViewById(R.id.set_type);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        image = R.drawable.working;
                        break;
                    case 1:
                        image = R.drawable.training;
                        break;
                    case 2:
                        image = R.drawable.dating;
                        break;
                    case 3:
                        image = R.drawable.other;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setName(View view) {
        final EditText nameText = view.findViewById(R.id.input_name);
        name = nameText.getText().toString();
    }

    private void addNotification(final View itemView) {
        final Button add = itemView.findViewById(R.id.add_notification);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setName(itemView);
                if (checkName(name)) {
                    startAlarm();
                    ((MainActivity) Objects.requireNonNull(getActivity())).addItem(new NotificationModel(
                            name, date, time, image, 1));
                    ((MainActivity) getActivity()).notifyAdapter();
                    dismiss();
                }
            }
        });
    }

    private boolean checkName(String name) {
        final List<NotificationModel> list = ((MainActivity) getActivity()).getList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                Toast.makeText(getActivity(), TOAST_TEXT, Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    @SuppressLint("newAPI")
    private void startAlarm() {
        Intent intent = new Intent(getContext().getApplicationContext(), AlarmService.class);
        intent.putExtra("intarray", new int[]{day, hour, minute});
        intent.setAction(CUSTOM_ACTION);
        getContext().startForegroundService(intent);
    }
}
