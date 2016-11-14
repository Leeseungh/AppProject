package com.example.ankwinam.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by An Kwi nam on 2016-09-08.
 */
public class Local_NaviActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String[] Areagu;
    static final String BASE_URL="https://today-walks-lee-s-h.c9users.io";

    String myJSON;
    private static final String TAG_RESULTS="result";
    ArrayList<Walk_Info> h_info_list;

    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;

    private ListView list;
    WalkListAdapter myadapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_navigation);

        Areagu = new String[]{"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구",
                "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성북구",
                "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"};

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //지역별 스크롤 스피너 매핑 부분
        String[] localspnnier = getResources().getStringArray(R.array.Local_spinner);
        ArrayAdapter<String> local_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, localspnnier);
        Spinner spnnier = (Spinner) findViewById(R.id.Distence_spinner);
        spnnier.setAdapter(local_adapter);

        //지역별 스피너 클릭리스너
        spnnier.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        //한글 parameter 인코딩
                        String gu_parmeter = "";
                        try {
                            gu_parmeter = java.net.URLEncoder.encode(new String(Areagu[position].getBytes("UTF-8")));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        //get ListView
                        getData(BASE_URL+"/list_index.php?Area=" + gu_parmeter);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

//수정해야할 print 함수 부분
    public void print(View v, int position){
        Spinner sp = (Spinner)findViewById(R.id.Distence_spinner);
        String res = "";
        if(sp.getSelectedItemPosition()>0){
            res=(String)sp.getAdapter().getItem(sp.getSelectedItemPosition());
        }
        if(res!=""){
            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

            // Handle the camera action
        if (id == R.id.menu_home) {
            Toast.makeText(getApplicationContext(), "홈", Toast.LENGTH_SHORT).show();
            Intent go_home = new Intent(Local_NaviActivity.this, Choice_NaviActivity.class);
            startActivity(go_home);
            finish();
        } else if (id == R.id.menu_local) {
            Toast.makeText(getApplicationContext(), "지역 별 이동", Toast.LENGTH_SHORT).show();
            Intent go_local = new Intent(Local_NaviActivity.this, Local_NaviActivity.class);
            startActivity(go_local);
            finish();
        } else if (id == R.id.menu_tema) {
            Toast.makeText(getApplicationContext(), "테마 별 이동", Toast.LENGTH_SHORT).show();
            Intent go_tema = new Intent(Local_NaviActivity.this, Tema_NaviActivity.class);
            startActivity(go_tema);
            finish();
        } else if (id == R.id.menu_history) {
            Toast.makeText(getApplicationContext(), "내가 쓴 글", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_stamp) {
            Toast.makeText(getApplicationContext(), "스탬프", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_jjim) {
            Toast.makeText(getApplicationContext(),"찜 한 산책로",Toast.LENGTH_SHORT).show();
            Intent go_jjim = new Intent(Local_NaviActivity.this, JJim_NaviActivity.class);
            startActivity(go_jjim);
            finish();
        } else if (id == R.id.menu_logout) {
            SharedPreferences pref = getSharedPreferences("auto_login",MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putBoolean("auto",false);
            editor.commit();

            Intent go_main = new Intent(Local_NaviActivity.this, MainActivity.class);
            startActivity(go_main);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getList(String[] param){
        class GetList extends AsyncTask<String, Void, Bitmap> {
            String name;
            String area;
            String level;
            String image_url;

            @Override
            protected Bitmap doInBackground(String... params) {

                name = params[0];
                area = params[1];
                level = params[2];

                try {
                    image_url = URLEncoder.encode(name, "UTF-8");
                    URL url = new URL(BASE_URL + "/walks/" + image_url + ".jpg");
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    return bmp;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
            @Override
            protected void onPostExecute(Bitmap bmp){
                h_info_list.add(new Walk_Info(name, area, level, bmp));
            }
        }
        GetList g = new GetList();
        g.execute(param);
    }

//리스트 뷰
    protected void showList(){
        try {
            list = (ListView) findViewById(R.id.listView_area);
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);
            h_info_list = new ArrayList<Walk_Info>();

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString("walk_name");
                String name = "자치구" + c.getString("area");
                String address = "코스레벨" + c.getString("level");
                String[] param = {id, name, address};

                getList(param);

//                String image_url = URLEncoder.encode(id,"UTF-8");
//
//                Log.e("Check",BASE_URL+ "/walks/" + image_url + ".jpg");
//
//                URL url = new URL(BASE_URL+ "/walks/" + image_url + ".jpg");
//                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//
//                h_info_list.add(new Walk_Info(id,name,address, BitmapFactory.decodeResource(getResources(), R.drawable.time)));
//                h_info_list.add(new Walk_Info(id,name,address, getBitmapFromURL(BASE_URL+ "/walks/" + image_url + ".jpg")));
            }


            myadapter = new WalkListAdapter(getApplicationContext(),R.layout.tema_info, h_info_list);
            list.setAdapter(myadapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), TemaResultActivity.class); // 다음넘어갈 화면
                    Bitmap sendBitmap = h_info_list.get(position).image;

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    intent.putExtra("image",byteArray);
                    startActivity(intent);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result){

                myJSON=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}
