package com.beak.scrolltrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beak.scrolltrack.scroll.BottomTrackListener;
import com.beak.scrolltrack.scroll.TopDecoration;
import com.beak.scrolltrack.scroll.TopTrackListener;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMainRecyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainRecyclerView = (RecyclerView)findViewById(R.id.main_recycler_view);
        mMainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMainRecyclerView.addItemDecoration(new TopDecoration(findViewById(R.id.main_top_view)));
        mMainRecyclerView.addOnScrollListener(new TopTrackListener(findViewById(R.id.main_top_view)));
        mMainRecyclerView.addOnScrollListener(new BottomTrackListener(findViewById(R.id.main_bottom_view)));

        mMainRecyclerView.setAdapter(new SimpleAdapter());
    }


    private class SimpleAdapter extends RecyclerView.Adapter<SimpleHolder> {

        @Override
        public SimpleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_rv_item, null));
        }

        @Override
        public void onBindViewHolder(SimpleHolder holder, int position) {
            holder.mTv.setText(position + "");
        }

        @Override
        public int getItemCount() {
            return 200;
        }
    }

    private class SimpleHolder extends RecyclerView.ViewHolder {

        public TextView mTv = null;

        public SimpleHolder(View itemView) {
            super(itemView);
            mTv = (TextView)itemView.findViewById(R.id.item_tv);
        }
    }
}
