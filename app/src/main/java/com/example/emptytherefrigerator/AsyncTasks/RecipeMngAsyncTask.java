package com.example.emptytherefrigerator.AsyncTasks;

import android.os.AsyncTask;

//doinbackground 변수 타입, onProgress 타입, onPostExcute 타입
public class RecipeMngAsyncTask extends AsyncTask<String, Integer, String>
{
    int values = 0;

    @Override
    protected String doInBackground(String... urls) {
//        switch(urls[0])
//        {
//
//        }
        return "";
    }

    //백그라운드 스레드에서 작업 처리 중에 프로그레스바 진행 상태 업데이트 같은  UI작업이 필요한 경우
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);

    }
}
