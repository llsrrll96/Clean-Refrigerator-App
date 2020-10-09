package com.example.emptytherefrigerator.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.main.MainPageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginView extends AppCompatActivity {
    private String serverURL = "http://192.168.0.6:3000/";
    private Button btnLogin;
    private Button btnSignUp;
    private Intent intent;
    private EditText editTextID;
    private EditText editTextPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initializeView();
        setListener();
    }

    public void initializeView()
    {
        btnLogin = (Button)findViewById(R.id.buttonLogin);
        btnSignUp = (Button)findViewById(R.id.buttonCreate);
        editTextID = findViewById(R.id.editTextID);
        editTextPW = findViewById(R.id.editTextPW);
    }

    public void setListener()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), MainPageView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                loginAsyncTask login = new loginAsyncTask();
                login.execute(serverURL+"login");
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), LoginSignUpView.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    // 로그인을 처리하는 새로운 thread 생성
    private class loginAsyncTask extends AsyncTask<String, String, String>
    {
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String...urls)
        {
            URL urlCon=null;
            HttpURLConnection con = null;
            BufferedReader reader = null;

            try {
                JSONObject loginTest = new JSONObject();
                loginTest.accumulate("id", editTextID.getText().toString());
                loginTest.accumulate("pw", editTextPW.getText().toString());
                String jsonStrng = loginTest.toString();

                URL url = new URL(urls[0]);
                //연결을 함
                con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");//POST방식으로 보냄
                con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음

                con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                con.connect();

                OutputStream outStream = con.getOutputStream();
                //버퍼를 생성하고 넣음
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                writer.write(loginTest.toString());
                writer.flush();
                writer.close();//버퍼를 받아줌

                //서버로 부터 데이터를 받음
                InputStream stream = con.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }
                return buffer.toString();//서버로 부터 받은 값을 return
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally {
                if(con != null){
                    con.disconnect();
                }
                try {
                    if(reader != null){
                        reader.close();//버퍼를 닫음
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "fail";
        }

        @Override
        protected void onPostExecute(String result)         //doinbackground의 결과값 사용
        {
            super.onPostExecute(result);
           // System.out.println(result);       //-> ok
            if(result.equals("ok"))        //로그인 성공
            {
                Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainPageView.class);      //현재 화면의 제어를 넘길 클래스 지정
                startActivity(intent);      //다음 화면으로 넘어감
            }
            else if(result.equals("wrong") || result.equals("error"))      //로그인 실패
            {
                Toast.makeText(getApplicationContext(),"로그인 실패", Toast.LENGTH_SHORT).show();
            }

        }
    }

}