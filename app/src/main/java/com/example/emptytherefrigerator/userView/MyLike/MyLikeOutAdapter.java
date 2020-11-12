package com.example.emptytherefrigerator.userView.MyLike;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.AsyncTasks.MyAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.LikeOut;
import com.example.emptytherefrigerator.login.UserInfo;
import org.json.JSONObject;
import java.util.ArrayList;

public class MyLikeOutAdapter extends RecyclerView.Adapter<MyLikeOutAdapter.MyLikeOutViewHolder>
{
    private ArrayList<LikeOut> list= new ArrayList<>();
    private LayoutInflater inflater;
    Context context;

    public MyLikeOutAdapter(Context context, ArrayList<LikeOut> list)
    {
        this.list=list;
        this.context= context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount()       //전체 아이템 갯수 리턴
    {
        return list.size();
    }
    @Override
    public void onBindViewHolder(@NonNull MyLikeOutAdapter.MyLikeOutViewHolder holder, int position) {
        context = holder.itemView.getContext();
        holder.onBind(list.get(position));
    }
    @Override
    public MyLikeOutAdapter.MyLikeOutViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.user_my_like_out_item, parent, false);
        return new MyLikeOutAdapter.MyLikeOutViewHolder(itemView, this);
    }

    class MyLikeOutViewHolder extends RecyclerView.ViewHolder
    {
        MyLikeOutAdapter adapter;

        TextView recipeOutTitle, site_link, likeOutUploadDate;
        ImageButton btnLikeOutDelete;

        public MyLikeOutViewHolder(View view, MyLikeOutAdapter adapter)
        {
            super(view);
            this.adapter=adapter;

            recipeOutTitle = itemView.findViewById(R.id.recipeOutTitle);
            site_link = itemView.findViewById(R.id.site_link);
            btnLikeOutDelete = itemView.findViewById(R.id.btnLikeOutDelete);
            likeOutUploadDate = itemView.findViewById(R.id.likeOutUploadDate);

            btnLikeOutDelete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    deleteLikeOut();
                }
            });
        }
        public void onBind(LikeOut likeOut)
        {
            recipeOutTitle.setText(likeOut.getRecipeOut().getTitle());
            site_link.setText(likeOut.getRecipeOut().getLink());
            likeOutUploadDate.setText(likeOut.getUploadDate());
        }

        public void deleteLikeOut()
        {
            MyAsyncTask deleteLikeOut = new MyAsyncTask();

            try
            {
                int pos = getAdapterPosition();
                JSONObject data = new JSONObject();
                data.accumulate("recipeOutId", list.get(pos).getRecipeOut().getRecipeOutId());
                data.accumulate("userId", UserInfo.getString(context, UserInfo.ID_KEY));
                String result = deleteLikeOut.execute("deleteLikeOut", data.toString()).get();
                if(result.equals("1"))
                {
                    btnLikeOutDelete.setImageResource(R.drawable.like);       //하트 클릭시 빈 하트로 변하게 됨
                    list.remove(pos);                                   //list에서 지움
                    adapter.notifyDataSetChanged();                     //list 업데이트
                }
                else
                {
                    Toast.makeText(itemView.getContext(),"내부 오류로 요청을 수행하지 못했습니다", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
