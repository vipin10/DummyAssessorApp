package assessor.android.com.dummyassessorapp.StudentsAttenandList;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import assessor.android.com.dummyassessorapp.R;

public class StudentsListAct extends AppCompatActivity {
    RecyclerView meet_rc;
    final Context myContext = StudentsListAct.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        meet_rc = (RecyclerView) findViewById(R.id.meet_rc);
        meet_rc.setLayoutManager(new LinearLayoutManager(myContext));
        meet_rc.setAdapter(new MeetAdapter());
    }

    private class MeetAdapter extends RecyclerView.Adapter<MeetAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(myContext);
            View view = inflater.inflate(R.layout.adaptstu, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.maccountName.setText("Student1");
            holder.mcontactperson.setText("9015363586");
            holder.mlocation.setText("Varanasi");
            holder.mstartDate.setText("Batch no. 1");
        }

        @Override
        public int getItemCount() {
            return 8;
           // return accountName.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
            TextView maccountName, mcontactperson, mlocation, mstartDate, mendDate, meetStatus, textMenu;
            AlertDialog dialog;
            double desLat, desLng;
            Geocoder geocoder;

            ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                maccountName = (TextView) itemView.findViewById(R.id.maccountName);
                mcontactperson = (TextView) itemView.findViewById(R.id.mcontactperson);
                mlocation = (TextView) itemView.findViewById(R.id.mlocation);
                mstartDate = (TextView) itemView.findViewById(R.id.mstartDate);
                mendDate = (TextView) itemView.findViewById(R.id.mendDate);
              /*  meetStatus = (TextView) itemView.findViewById(R.id.meetStatus);
                textMenu = (TextView) itemView.findViewById(R.id.textMenu);*/
               // textMenu.setOnClickListener(this);

            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }

            @Override
            public void onClick(View v) {
               Intent ii=new Intent(StudentsListAct.this,StudentAttenAct.class);
               startActivity(ii);
            }

          /*  @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.yesBtn:
                        dialog.cancel();
                        new ProgressDialog(myContext);
                        pd.setCancelable(false);
                        pd.setMessage("Please wait...");
                        pd.show();
                        checkLocation();
                        break;
                    case R.id.noBtn:
                        dialog.cancel();
                        break;
                    case R.id.textMenu:
                        PopupMenu popupMenu = new PopupMenu(myContext, v);
                        popupMenu.getMenu().add(0, R.id.infoItem, 0, "Detail Info");
                        popupMenu.getMenu().add(0, R.id.stopMeet, 0, "Stop Meeting");
                        popupMenu.setOnMenuItemClickListener(this);
                        popupMenu.show();
                        break;
                    default:
                        dialog = new MyAlertDialog(v.getContext());
                        dialog.setCancelable(false);
                        LayoutInflater inflater = LayoutInflater.from(myContext);
                        View view = inflater.inflate(R.layout.meeting_start, null);
                        view.findViewById(R.id.noBtn).setOnClickListener(this);
                        view.findViewById(R.id.yesBtn).setOnClickListener(this);
                        dialog.setView(view);
                        dialog.show();
                }
            }*/


        }


        }
}
