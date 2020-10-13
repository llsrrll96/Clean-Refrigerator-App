package com.example.emptytherefrigerator.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emptytherefrigerator.R;

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

public class LoginSignUpView extends AppCompatActivity {
    private String serverURL = "http://192.168.25.8:3000/";
    private TextView btnOk;
    private TextView btnCancel;
    private Intent intent;

    private TextView id;
    private TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign_up);

        initializeView();
        setListener();
    }

    public void initializeView()
    {
        btnOk = (TextView)findViewById(R.id.ok);
        btnCancel = (TextView)findViewById(R.id.cancel);
        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
    }
    public void setListener()
    {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), LoginView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                SignUpAsyncTask signUpTask = new SignUpAsyncTask();
                signUpTask.execute(serverURL+"signUp");
                //회원가입 완료
                //v.getContext().startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                intent = new Intent(v.getContext(), Login.class);
//                v.getContext().startActivity(intent);

            }
        });
    }
    private class SignUpAsyncTask extends AsyncTask<String, String, String>
    {
        protected String doInBackground(String...urls)
        {
            URL urlCon=null;
            HttpURLConnection con = null;
            BufferedReader reader = null;

            try {
                JSONObject loginTest = new JSONObject();
                loginTest.accumulate("id", id.getText().toString());
                loginTest.accumulate("pw", password.getText().toString());
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
            if(result.equals("ok"))        //성공
            {
                Toast.makeText(getApplicationContext(),"회원가입 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginView.class);      //현재 화면의 제어를 넘길 클래스 지정
                startActivity(intent);      //다음 화면으로 넘어감
            }
            else if(result.equals("error") | result.equals("중복"))      //실패
            {
                Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
            }

        }
    }
}