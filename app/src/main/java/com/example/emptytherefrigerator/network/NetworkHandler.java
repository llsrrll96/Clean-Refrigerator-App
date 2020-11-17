package com.example.emptytherefrigerator.network;

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

public class NetworkHandler
{
    private static final String url="http://192.168.25.43:3000/";        //서버의 주소
    private static HttpURLConnection conn;              //커넥션을 맺는 객체

    public static final NetworkHandler networkHandler =  new NetworkHandler();      //앱 내에 하나만 있어야 하므로 싱글톤 패턴 적용

    public static NetworkHandler getInstance()  {return networkHandler;}
    //-------------------------------------------------------------------------------------------------------
    public void connect(String reqUrl)         //서버와 연결
    {
        try
        {
            URL connUrl = new URL(url+reqUrl);
            this.conn=(HttpURLConnection)connUrl.openConnection();

            conn.setRequestMethod("POST");//POST방식으로 보냄
            conn.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
            conn.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
            conn.setRequestProperty("Accept", "application/json");  //받을 데이터도 json 형식으로 받음

            conn.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
            conn.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
            conn.connect();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------------------------------------
    public static void disconnect()            //연결 끊기
    {
        if(conn!=null)
        {
            conn.disconnect();
        }
    }
    //-------------------------------------------------------------------------------------------------------
    public static String communication(String jsonObject)      //요청 및 응답
    {
        BufferedReader reader = null;                   //요청을 주고받을때마다 열고 닫을것인가 vs 앱 시작과 종료 시 한번만 열고 닫을것인가
        BufferedWriter writer = null;                   //후자로 했을 때 버퍼가 꼬이게 되지는 않을까 싶어서 일단은 전자로 함
        OutputStream outStream =null;
        InputStream inputStream = null;
        StringBuffer resultString = new StringBuffer();
        try
        {

            outStream = conn.getOutputStream();
            //버퍼를 생성하고 넣음
            writer = new BufferedWriter(new OutputStreamWriter(outStream));

            if(jsonObject!=null)        //보낼 데이터가 없는 경우가 있을때만
            {
                writer.write(jsonObject);
                writer.flush();
                writer.close();//버퍼를 닫음
            }
            inputStream = conn.getInputStream();      //서버로부터 데이터를 받음
            reader = new BufferedReader(new InputStreamReader(inputStream));
            resultString = new StringBuffer();

            String line = "";
            while((line = reader.readLine()) != null)
            {
                resultString.append(line);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally         //stream을 닫음
        {
            try
            {
                reader.close();
                writer.close();
                outStream.close();
                inputStream.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return resultString.toString();//서버로 부터 받은 값을 return
        }
    }
}
