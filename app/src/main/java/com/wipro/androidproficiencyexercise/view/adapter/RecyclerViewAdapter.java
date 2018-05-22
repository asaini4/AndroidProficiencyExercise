package com.wipro.androidproficiencyexercise.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.wipro.androidproficiencyexercise.R;
import com.wipro.androidproficiencyexercise.application.AppCustomVolleyNetworkRequest;
import com.wipro.androidproficiencyexercise.pojo.Row;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Row> dataAdapters;
    ImageLoader imageLoader;

    public RecyclerViewAdapter(List<Row> getDataAdapter, Context context) {
        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        Row dataAdapterOBJ = dataAdapters.get(position);

        //Set network images
        imageLoader = AppCustomVolleyNetworkRequest.getInstance(context).getImageLoader();

        imageLoader.get(dataAdapterOBJ.getImageHref().toString(),
                ImageLoader.getImageListener(
                        Viewholder.volleyImageView,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        R.mipmap.ic_launcher //Error image if requested image dose not found on server.
                )
        );

        Viewholder.volleyImageView.setImageUrl(dataAdapterOBJ.getImageHref().toString(), imageLoader);

        //Set title
        String title = dataAdapterOBJ.getTitle();
        if(!(title.equalsIgnoreCase("null"))) {
            Viewholder.tv_title.setText(title);
        } else {
            Viewholder.tv_title.setText(R.string.title_not_available);
        }

        //Set description
        String description = dataAdapterOBJ.getDescription();
        if(!(description.equalsIgnoreCase("null"))) {
            Viewholder.tv_description.setText(description);
        } else {
            Viewholder.tv_description.setText(R.string.description_not_available);
        }
    }

    @Override
    public int getItemCount() {
        return dataAdapters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_title;
        public TextView tv_description;
        public NetworkImageView volleyImageView;

        public ViewHolder(View itemView) {

            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);

            volleyImageView = (NetworkImageView) itemView.findViewById(R.id.volleyImageView);
        }
    }
}