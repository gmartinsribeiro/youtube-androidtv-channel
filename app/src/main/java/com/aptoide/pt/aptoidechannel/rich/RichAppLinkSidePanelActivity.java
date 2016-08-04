package com.aptoide.pt.aptoidechannel.rich;

import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.widget.VerticalGridView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.aptoide.pt.aptoidechannel.R;
import com.aptoide.pt.aptoidechannel.TvContractUtils;
import com.aptoide.pt.aptoidechannel.data.Channel;

import java.util.List;

/**
 * Activity that shows a simple side panel UI.
 */
public class RichAppLinkSidePanelActivity extends Activity {
    private VerticalGridView mAppLinkMenuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Channel> Channels = TvContractUtils.getChannels(getContentResolver());
        Channel appLinkChannel = null;

        String displayNumber = getIntent().getStringExtra(RichFeedUtil.EXTRA_DISPLAY_NUMBER);
        if (displayNumber != null) {
            for (Channel channel : Channels) {
                if (displayNumber.equals(channel.getDisplayNumber())) {
                    appLinkChannel = channel;
                    break;
                }
            }
        }

        // Sets the size and position of dialog activity.
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        layoutParams.width = getResources().getDimensionPixelSize(R.dimen.side_panel_width);
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.rich_app_link_side_panel);

        if (appLinkChannel != null && appLinkChannel.getAppLinkColor() != 0) {
            TextView titleView = (TextView) findViewById(R.id.title);
            titleView.setBackgroundColor(appLinkChannel.getAppLinkColor());
        }
        mAppLinkMenuList = (VerticalGridView) findViewById(R.id.list);
        mAppLinkMenuList.setAdapter(new AppLinkMenuAdapter());
    }

    /**
     * Adapter class that provides the app link menu list.
     */
    public class AppLinkMenuAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final int ITEM_COUNT = 2;
        private final int[] ITEM_STRING_RES_IDS = new int[]{R.string.item_1, R.string.item_2};

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = getLayoutInflater().inflate(viewType, mAppLinkMenuList, false);
            return new ViewHolder(view);
        }

        @Override
        public int getItemViewType(int position) {
            return R.layout.rich_app_link_side_panel_item;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            TextView view = (TextView) viewHolder.itemView;
            view.setText(getResources().getString(ITEM_STRING_RES_IDS[position]));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return ITEM_COUNT;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
