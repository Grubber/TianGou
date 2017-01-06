package com.github.grubber.tiangou.ui.top;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.grubber.tiangou.R;
import com.github.grubber.tiangou.data.api.ApiDefaultConfig;
import com.github.grubber.tiangou.data.api.model.Top;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by grubber on 2017/1/6.
 */
public class TopAdapter extends BaseAdapter {
    private List<Top> _result = new ArrayList<>();
    private LayoutInflater _layoutInflater;
    private Picasso _picasso;

    public TopAdapter(Context context, Picasso picasso) {
        _layoutInflater = LayoutInflater.from(context);
        _picasso = picasso;
    }

    public void setResult(List<Top> result) {
        _result.addAll(result);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return _result.size();
    }

    @Override
    public Object getItem(int position) {
        return _result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = _layoutInflater.inflate(R.layout.item_common, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        _picasso.load(ApiDefaultConfig.IMG_URL + _result.get(position).getImg())
                .placeholder(R.drawable.list_icon_no_image)
                .error(R.drawable.list_icon_error_image).into(viewHolder.icon);

        viewHolder.title.setText(_result.get(position).getTitle());
        viewHolder.tag.setText(_result.get(position).getKeywords());
        viewHolder.time.setText(DateFormat.getDateTimeInstance().format(_result.get(position)
                .getTime()));
        viewHolder.browser.setText("浏览：" + _result.get(position).getCount());

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.list_item_icon)
        ImageView icon;
        @Bind(R.id.list_item_title)
        TextView title;
        @Bind(R.id.list_item_tag)
        TextView tag;
        @Bind(R.id.list_item_time)
        TextView time;
        @Bind(R.id.list_item_browser)
        TextView browser;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
