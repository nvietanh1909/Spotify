package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.classes.Song;
import com.example.myapplication.classes.Utils;

import java.util.ArrayList;

public class ListAlbumHorizontalAdapter extends RecyclerView.Adapter {
    ArrayList<Song> songArrayList;
    private OnItemClickListener listener;
    Context context;

    public ListAlbumHorizontalAdapter(ArrayList<Song> songArrayList, Context context) {
        this.songArrayList = songArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_layout_album_horizontal, parent, false);
        ListAlbumHorizontalViewHolder listAlbumHorizontalViewHolder = new ListAlbumHorizontalViewHolder(view);
        return listAlbumHorizontalViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Song item = songArrayList.get(position);
        ListAlbumHorizontalViewHolder listAlbumHorizontalViewHolder = (ListAlbumHorizontalViewHolder) holder;
        listAlbumHorizontalViewHolder.textViewName.setText(item.getAlbum());
        //Avatar
        listAlbumHorizontalViewHolder.imageViewAvatar.setImageBitmap(Utils.loadBitMapFormAssets(context, item.getAlbumArt(), "img_album"));

        final Song currentSong = songArrayList.get(position);

        // ...

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(currentSong);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return songArrayList.size();
    }

    class ListAlbumHorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAvatar;
        TextView textViewName;
        public ListAlbumHorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAvatar = itemView.findViewById(R.id.imageViewHorizontalAvatar);
            textViewName = itemView.findViewById(R.id.textViewHorizontalItem);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Song song);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }
}
