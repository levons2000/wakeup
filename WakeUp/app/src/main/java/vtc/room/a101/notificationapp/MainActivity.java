package vtc.room.a101.notificationapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    private static final String DIALOG_TAG = "tag";
    private final List<NotificationModel> list = new ArrayList<>();
    private NotificationAdapter adapter;
    private DBHelper db;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDataBase();

        setFabListener();
        setRecyclerView();
        loadContent();
    }

    @Override
    protected void onPause() {
        super.onPause();
        db.removeAllItems();
        for (int i = 0; i < list.size(); i++) {
            db.insertItem(list.get(i));
        }
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recyc);
        adapter = new NotificationAdapter(MainActivity.this, list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        swipeListener(recyclerView);
    }

    private void setFabListener() {
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNotificationDialog dialog = new AddNotificationDialog();
                dialog.show(getSupportFragmentManager(), DIALOG_TAG);
            }
        });
    }

    public void addItem(NotificationModel model) {
        list.add(model);
    }

    private void swipeListener(final RecyclerView recyclerView) {
        final ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                db.removeItem(list.remove(viewHolder.getAdapterPosition()).getName());
                notifyAdapter();
            }
        };
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    private void getDataBase() {
        db = DBHelper.getInstance(MainActivity.this);
        for (int i = 0; i < db.getItemsCount(); i++) {
            list.add(db.getItem(i));
        }
    }

    public List<NotificationModel> getList() {
        return list;
    }

    public void loadContent() {
        fab.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView backgroundImage = findViewById(R.id.start_background);
                ImageView loadingImage = findViewById(R.id.loading_image);
                GifImageView mainBackground = findViewById(R.id.main_menu_background);
                TextView loadingLogo = findViewById(R.id.loading_logo);
                TextView loadingText = findViewById(R.id.loading_text);
                backgroundImage.setVisibility(View.GONE);
                loadingImage.setVisibility(View.GONE);
                loadingLogo.setVisibility(View.GONE);
                loadingText.setVisibility(View.GONE);
                mainBackground.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }
}
