package com.example.administrator.videolistplay;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.SimpleMainThreadMediaPlayerListener;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.RecyclerViewItemPositionGetter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView video_list;
    private MyVideoAdapter adapter;
    private List<VideoInfo> list = new ArrayList<>();

    private VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
        @Override
        public void onPlayerItemChanged(MetaData metaData) {
        }
    });


    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        video_list = (RecyclerView) findViewById(R.id.video_list);
        list = GetVideoData.GetvideoInfos(this);
        adapter = new MyVideoAdapter(list, this);
        video_list.setAdapter(adapter);
        mLayoutManager = new LinearLayoutManager(this);
        video_list.setLayoutManager(mLayoutManager);


    }

    public class MyVideoAdapter extends RecyclerView.Adapter<MyVideoAdapter.VidepViewHolder> {
        private List<VideoInfo> list;
        private Context context;

        public MyVideoAdapter(List<VideoInfo> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public MyVideoAdapter.VidepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View holder = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
            return new VidepViewHolder(holder);
        }

        @Override
        public void onBindViewHolder(final MyVideoAdapter.VidepViewHolder holder, final int position) {
            holder.video_name.setText(list.get(position).getTitle());
            holder.video_play.addMediaPlayerListener(new SimpleMainThreadMediaPlayerListener() {
                @Override
                public void onVideoPreparedMainThread() {
                    holder.video_img.setVisibility(View.INVISIBLE);
                }
                @Override
                public void onVideoStoppedMainThread() {
                    holder.video_img.setVisibility(View.VISIBLE);
                }

                @Override
                public void onVideoCompletionMainThread() {
                    holder.video_img.setVisibility(View.VISIBLE);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mVideoPlayerManager.playNewVideo(null, holder.video_play, list.get(position).getPath());
                }
            });
        }

        @Override
        public int getItemCount() {
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        public class VidepViewHolder extends RecyclerView.ViewHolder {
            public final VideoPlayerView video_play;
            public final TextView video_name, video_tet;
            private final ImageView video_img;

            public VidepViewHolder(View itemView) {
                super(itemView);
                video_name = itemView.findViewById(R.id.video_name);
                video_play = itemView.findViewById(R.id.video_play);
                video_img = itemView.findViewById(R.id.video_img);
                video_tet = itemView.findViewById(R.id.video_tet);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mVideoPlayerManager.stopAnyPlayback();
    }


}

