package com.example.emptytherefrigerator.main.Notification;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emptytherefrigerator.AsyncTasks.MyAsyncTask;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.Notification;
import com.example.emptytherefrigerator.main.RecipeDetailView;

import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>
{
    private ArrayList<Notification> list = new ArrayList<>();
    private LayoutInflater inflater;
    Context context;

    public NotificationAdapter(Context context, ArrayList<Notification> list)
    {
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.notification_item, parent, false);
        return new NotificationAdapter.NotificationViewHolder(itemView, this);
    }
    public void removeItem(int position)
    {
        this.list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount()-position);
    }
    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position)
    {
        context = holder.itemView.getContext();
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder
    {
        ImageView alarmImage;
        TextView content;
        NotificationAdapter adapter;

        public NotificationViewHolder(View view, NotificationAdapter adapter)
        {
            super(view);
            this.adapter=adapter;
            alarmImage = itemView.findViewById(R.id.alarmImage);
            content = itemView.findViewById(R.id.content);
            view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int pos = getAdapterPosition();     //레시피 조회 화면 넘기기
                    if(pos != RecyclerView.NO_POSITION)
                    {
                        context = view.getContext();
                        deleteNotification();
                        Intent intent = new Intent(context, RecipeDetailView.class);     //조회된 레시피 화면으로 넘어간다
                        intent.putExtra("RECIPE",list.get(pos).getRecipe().getRecipeInId());      //다음 화면에 레시피 객체 송신
                        context.startActivity(intent);
                    }
                    removeItem(pos);
                }
            });
        }
        public void onBind(Notification noti)
        {
            if(noti.getType()==1)       //타입에 관한 값이 뭔지 모름
            {
                alarmImage.setImageResource(R.drawable.like_filled1);       //좋아요
                content.setText("누가 " + noti.getRecipe().getTitle()+"을 좋아요 했습니다");
            }
            else
            {
                alarmImage.setImageResource(R.drawable.comment);
                content.setText("누가 " + noti.getRecipe().getTitle()+"에 댓글을 달았습니다");
            }
        }
        public void deleteNotification()
        {
            MyAsyncTask deleteNoti = new MyAsyncTask();
            JSONObject object = new JSONObject();

            try
            {
                object.accumulate("notificationId", list.get(getAdapterPosition()).getNotificationId());
                String result = deleteNoti.execute("deleteNotification", object.toString()).get();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
