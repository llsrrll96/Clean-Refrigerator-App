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
import com.example.emptytherefrigerator.AsyncTasks.RecipeMngAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.LikeIn;
import com.example.emptytherefrigerator.entity.RecipeIn;
import org.json.JSONObject;
import java.util.ArrayList;

public class MyLikeInAdapter extends RecyclerView.Adapter<MyLikeInAdapter.MyLikeInViewHolder> {
    private ArrayList<LikeIn> list = new ArrayList<>();
    private LayoutInflater inflater;
    Context context;

    public MyLikeInAdapter(Context context, ArrayList<LikeIn> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull MyLikeInAdapter.MyLikeInViewHolder holder, int position) {
        context = holder.itemView.getContext();
        holder.onBind(list.get(position));
        holder.likeInMainImg.setImageBitmap(RecipeIn.StringToBitmap(list.get(position).getRecipeImageByte()[0]));
    }

    @Override
    public int getItemCount()       //전체 아이템 갯수 리턴
    {
        return list.size();
    }

    @Override
    public MyLikeInAdapter.MyLikeInViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.user_my_comment_item, parent, false);
        return new MyLikeInAdapter.MyLikeInViewHolder(itemView, this);
    }

    class MyLikeInViewHolder extends RecyclerView.ViewHolder {
        MyLikeInAdapter adapter;
        ImageView likeInMainImg;
        TextView likeInTitle, likeInRecipeWriter, likeInUploadDate;
        ImageButton likeDelBtn;
        Context context;

        public MyLikeInViewHolder(View view, MyLikeInAdapter adapter) {
            super(view);
            this.adapter = adapter;
            likeInMainImg = view.findViewById(R.id.likeInMainImg);
            likeInTitle = view.findViewById(R.id.likeInTitle);
            likeInRecipeWriter = view.findViewById(R.id.likeInRecipeWriter);
            likeInUploadDate = view.findViewById(R.id.likeInUploadDate);
            likeDelBtn = view.findViewById(R.id.likeDelBtn);
            context = view.getContext();
            setListener();
        }

        public void onBind(LikeIn likeIn) {
            likeInTitle.setText(likeIn.getRecipe().getTitle());
            likeInRecipeWriter.setText(likeIn.getWriterId());
            likeInUploadDate.setText(likeIn.getUploadDate());
        }

        public void setListener() {
            likeDelBtn.setOnClickListener(new View.OnClickListener() {
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
            try {
                int pos = getAdapterPosition();
                JSONObject data = new JSONObject();
                data.accumulate("likeInId", list.get(pos).getLikeInId());
                data.accumulate("userId", list.get(pos).getUserId());
                String result = deleteLike.execute("deleteLikeIn", data.toString()).get();
                if (result.equals("1"))
                {
                    likeDelBtn.setImageResource(R.drawable.like);       //하트 클릭시 빈 하트로 변하게 됨
                    list.remove(pos);                                   //list에서 지움
                    adapter.notifyDataSetChanged();                     //list 업데이트
                }
                else
                {
                    Toast.makeText(context,"내부 오류로 요청을 수행하지 못했습니다", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
