package com.example.emptytherefrigerator.userView.MyLike;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.emptytherefrigerator.AsyncTasks.LikeMngAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.LikeIn;
import com.example.emptytherefrigerator.entity.RecipeIn;
import com.example.emptytherefrigerator.login.UserInfo;

import org.json.JSONObject;
import java.util.ArrayList;

public class MyLikeInAdapter extends RecyclerView.Adapter<MyLikeInAdapter.MyLikeInViewHolder> {
    private ArrayList<LikeIn> list = new ArrayList<>();
    private LayoutInflater inflater;
    Context context;

    public MyLikeInAdapter(ArrayList<LikeIn> list)
    {
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull MyLikeInAdapter.MyLikeInViewHolder holder, int position) {
        context = holder.itemView.getContext();
        holder.onBind(list.get(position));
        holder.likeInMainImg.setImageBitmap(RecipeIn.StringToBitmap(list.get(position).getRecipeIn().getRecipeImagePath()));
    }

    @Override
    public int getItemCount()       //전체 아이템 갯수 리턴
    {
        return list.size();
    }

    @Override
    public MyLikeInAdapter.MyLikeInViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.user_my_like_in_item, parent, false);
        return new MyLikeInAdapter.MyLikeInViewHolder(itemView, this);
    }

    class MyLikeInViewHolder extends RecyclerView.ViewHolder
    {
        MyLikeInAdapter adapter;
        ImageView likeInMainImg;
        TextView likeInTitle, likeInRecipeWriter, likeInUploadDate;
        ImageButton likeDelBtn;

        public MyLikeInViewHolder(View view, MyLikeInAdapter adapter)
        {
            super(view);
            this.adapter = adapter;
            likeInMainImg = itemView.findViewById(R.id.likeInMainImg);
            likeInTitle = itemView.findViewById(R.id.likeInTitle);
            likeInRecipeWriter = itemView.findViewById(R.id.likeInRecipeWriter);
            likeInUploadDate = itemView.findViewById(R.id.likeInUploadDate);
            likeDelBtn = itemView.findViewById(R.id.likeDelBtn);
            context = itemView.getContext();
            setListener();
        }

        public void onBind(LikeIn likeIn)
        {
            likeInTitle.setText(likeIn.getRecipeIn().getTitle());
            likeInRecipeWriter.setText(likeIn.getRecipeIn().getUserId());
            likeInUploadDate.setText(likeIn.getRecipeIn().getUploadDate());
        }

        public void setListener() {
            likeDelBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    deleteLikeIn();
                }
            });
        }

        public  void deleteLikeIn()            //좋아요 삭제
        {
            LikeMngAsyncTask deleteLike = new LikeMngAsyncTask();
            try
            {
                int pos = getAdapterPosition();
                JSONObject data = new JSONObject();
                data.accumulate("recipeInId", list.get(pos).getRecipeIn().getRecipeInId());
                data.accumulate("userId", UserInfo.getString(context, UserInfo.ID_KEY));        //shared preference에서 값을 불러움
                String result = deleteLike.execute("deleteLikeIn", data.toString()).get();
                if (result.equals("1"))
                {
                    likeDelBtn.setImageResource(R.drawable.like);       //하트 클릭시 빈 하트로 변하게 됨
                    list.remove(pos);                                   //list에서 지움
                    adapter.notifyDataSetChanged();                     //list 업데이트
                }
                else
                {
                    Toast.makeText(itemView.getContext(),"내부 오류로 요청을 수행하지 못했습니다", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
