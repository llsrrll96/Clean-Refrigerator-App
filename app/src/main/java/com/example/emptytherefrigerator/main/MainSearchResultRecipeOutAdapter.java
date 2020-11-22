package com.example.emptytherefrigerator.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.AsyncTasks.MyAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeIn;
import com.example.emptytherefrigerator.entity.RecipeOut;
import com.example.emptytherefrigerator.login.UserInfo;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainSearchResultRecipeOutAdapter extends RecyclerView.Adapter<MainSearchResultRecipeOutAdapter.ViewHolder>
{

    private final ArrayList<RecipeOut> recipeResults;
    private Context context;

    public MainSearchResultRecipeOutAdapter(ArrayList<RecipeOut> recipeResults) {
        this.recipeResults = recipeResults;
    }

    @NonNull
    @Override
    public MainSearchResultRecipeOutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.main_search_result_recipeout_item,parent,false);
        return new MainSearchResultRecipeOutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainSearchResultRecipeOutAdapter.ViewHolder holder, int position) {
        context = holder.itemView.getContext();

        holder.recipeImage.setImageBitmap(StringToBitmap(recipeResults.get(position).getRecipeImageByte()[0]));
        holder.title.setText(recipeResults.get(position).getTitle());   //viewHolder 객체
        holder.link.setText(recipeResults.get(position).getLink());
    }

    @Override
    public int getItemCount() {
        return recipeResults.size();
    }

    public Bitmap StringToBitmap(String encodedString)                      //스트링 이미지 데이터 -> 비트맵으로
    {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    //ViewHolder 클래스
    class ViewHolder extends RecyclerView.ViewHolder{
        Intent intent;
        public ImageView recipeImage;
        public TextView title;
        public TextView link;
        private Context holdercontext;

        public ViewHolder(@NonNull View view) {
            super(view);
            recipeImage = itemView.findViewById(R.id.recipeout_Image);
            title = itemView.findViewById(R.id.recipeout_title);
            link = itemView.findViewById(R.id.recipeout_link);

            view.setOnClickListener(new View.OnClickListener() { // 리싸이클러 아이템 클릭시
                @Override
                public void onClick(View v) {           //클릭시
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        holdercontext = v.getContext();

/*                        intent = new Intent(Intent.ACTION_VIEW);
                        Uri uri = Uri.parse(recipeResults.get(pos).getLink());
                        intent.setData(uri);
                        holdercontext.startActivity(intent);*/

                        //해당 링크로 이동
                        //intent = new Intent(holdercontext, RecipeDetailView.class);     //조회된 레시피 화면으로 넘어간다
                        //intent.putExtra("RECIPE",recipeResults.get(pos).getRecipeOutId());  //해당 링크로 이동

                        intent = new Intent(Intent.ACTION_VIEW);
                        intent = new Intent(holdercontext, MainSearchRecipeOutWebView.class);       //외부 검색
                        intent.putExtra("TITLE", recipeResults.get(pos).getTitle());
                        intent.putExtra("LINK", recipeResults.get(pos).getLink());
                        intent.putExtra("recipeOutId",recipeResults.get(pos).getRecipeOutId());
                        holdercontext.startActivity(intent);
                    }
                }
            });
        }


    }

}
