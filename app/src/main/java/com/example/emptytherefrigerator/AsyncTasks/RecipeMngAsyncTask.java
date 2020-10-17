package com.example.emptytherefrigerator.AsyncTasks;

import android.os.AsyncTask;

import com.example.emptytherefrigerator.network.NetworkHandler;

//doinbackground 변수 타입, onProgress 타입, onPostExcute 타입
public class RecipeMngAsyncTask extends AsyncTask<String, Integer, String>
{
    int values = 0;

    @Override
    protected String doInBackground(String... strings) {
        String functionType = strings[0];
        String data = strings[1];
        switch (functionType)
        {
            case "createRecipe" : break;
            case "updateRecipe" : break;
            case "deleteRecipe" : break;
            case "readRecipe" : break;
        }
        
        NetworkHandler.getInstance().connect(functionType);      // 서버와 연결
        String result = NetworkHandler.communication(data);
        NetworkHandler.disconnect();

        return result;
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
