package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ListAlbumHorizontalAdapter;
import com.example.myapplication.classes.Song;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListAlbumHorizontalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListAlbumHorizontalFragment extends Fragment {
    ArrayList<Song> songArrayList;
    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListAlbumHorizontalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListAlbumHorizontalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListAlbumHorizontalFragment newInstance(String param1, String param2) {
        ListAlbumHorizontalFragment fragment = new ListAlbumHorizontalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        initSampleData();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_album_horizontal, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewHorizontal);
        ListAlbumHorizontalAdapter listAlbumHorizontalFragment = new ListAlbumHorizontalAdapter(songArrayList, getContext());
        recyclerView.setAdapter(listAlbumHorizontalFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        listAlbumHorizontalFragment.setOnItemClickListener(new ListAlbumHorizontalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Song song) {
                // Xử lý sự kiện click ở đây
                 Intent intent = new Intent(getContext(), DetailSongActivity.class);
                 intent.putExtra("song_name", song.getAlbum());
                 startActivity(intent);
            }
        });
        return view;
    }
    void initSampleData(){
        songArrayList = new ArrayList<Song>();
        songArrayList.add(new Song("", "Obito", "Đánh đổi", 300, "Gen Z", "", "obito.jpg"));
        songArrayList.add(new Song("", "Wren Evans", "Loi Choi", 300, "Gen Z", "", "wrenevans.jpg"));
        songArrayList.add(new Song("", "Oplus", "OZONE", 300, "Gen Z", "", "oplus.jpg"));
        songArrayList.add(new Song("", "Tlinh", "Ái", 300, "Gen Z", "", "tlinh.jpg"));
        songArrayList.add(new Song("", "MCK", "99%", 300, "Gen Z", "", "mck.jpg"));
    }
}