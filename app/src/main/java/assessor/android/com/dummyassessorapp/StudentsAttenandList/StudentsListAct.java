package assessor.android.com.dummyassessorapp.StudentsAttenandList;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import assessor.android.com.dummyassessorapp.AsssessorAttendance.BatchSelection;
import assessor.android.com.dummyassessorapp.GlobalAccess.MyNetwork;
import assessor.android.com.dummyassessorapp.LocalDB.DbAutoSave;
import assessor.android.com.dummyassessorapp.R;

public class StudentsListAct extends AppCompatActivity {
    RecyclerView meet_rc;
    final Context myContext = StudentsListAct.this;
    String batchidd;
    List<String> Studentname = new ArrayList<>();
    List<String> mob = new ArrayList<>();
    List<String> Dob = new ArrayList<>();
    List<String> Email = new ArrayList<>();
    ProgressDialog pdd;
    SharedPreferences sp;
    Cursor cursor;
    final String mypreference = "mypref1";
    DbAutoSave dbAutoSave;
    String attenstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        Intent il=getIntent();
        batchidd=il.getStringExtra("batchid");
        sp=getSharedPreferences(mypreference,Context.MODE_PRIVATE);
        Toast.makeText(getApplicationContext(),"batchidd"+batchidd,Toast.LENGTH_LONG).show();
        dbAutoSave=new DbAutoSave(getApplicationContext());
        cursor=dbAutoSave.getAllData();
        if (cursor!=null){
            cursor.moveToFirst();
            attenstatus=cursor.getColumnName(3);
            if (attenstatus.equals("0")){

            }else if(attenstatus.equals("1")){

            }else
            {

            }
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        meet_rc = (RecyclerView) findViewById(R.id.meet_rc);
        meet_rc.setLayoutManager(new LinearLayoutManager(myContext));
        meet_rc.setAdapter(new MeetAdapter());
        StudentsList();
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
            if (Studentname.get(position)!=null) {
                holder.maccountName.setText(Studentname.get(position));
                holder.mcontactperson.setText(Dob.get(position));
                holder.mlocation.setText(mob.get(position));
                holder.mstartDate.setText(Email.get(position));
            }else{
                Toast.makeText(getApplicationContext(),"Empty",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public int getItemCount() {
            return Studentname.size();
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
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }

            @Override
            public void onClick(View v) {

                String ab=maccountName.getText().toString();
                String mobilee=mlocation.getText().toString();
                Toast.makeText(myContext, "abbbb"+ab, Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("StuName", ab);
                editor.putString("phone", mobilee);
                editor.apply();
               Intent ii=new Intent(StudentsListAct.this,StudentAttenAct.class);
               startActivity(ii);
            }

        }

        }



    private void StudentsList() {
        pdd = new ProgressDialog(StudentsListAct.this);
        pdd.setMessage("Loading...");
        pdd.show();
        String serverURL = "https://www.skillassessment.org/ssc/android_connect/batch_students.php";


        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    //Toast.makeText(getApplicationContext(),"Details are"+response,Toast.LENGTH_LONG).show();
                    String status= jobj.getString("status");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("students");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            Studentname.add(c.getString("student_id"));
                            mob.add(c.getString("mobile"));
                            Email.add(c.getString("email"));
                            Dob.add(c.getString("dob"));
                            //Toast.makeText(getApplicationContext(),"data is"+Studentname+mob+Email+Dob,Toast.LENGTH_LONG).show();


                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                meet_rc = (RecyclerView) findViewById(R.id.meet_rc);
                meet_rc.setLayoutManager(new LinearLayoutManager(myContext));
                meet_rc.setAdapter(new MeetAdapter());

                if (pdd.isShowing()) {
                    pdd.dismiss();
                }
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error: Please try again Later", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();

                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");
                map.put("batch_id", batchidd);

                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("This is not the right time to exit. The Assessment process has been started.")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        moveTaskToBack(true);
                        //finish();

                        //close();


                    }
                })
                .setNegativeButton("Stay", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }
}
