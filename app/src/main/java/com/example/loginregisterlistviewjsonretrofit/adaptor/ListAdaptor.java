package com.example.loginregisterlistviewjsonretrofit.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loginregisterlistviewjsonretrofit.viewModel.ModelListView;
import com.example.loginregisterlistviewjsonretrofit.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ListAdaptor extends BaseAdapter {

    private Context context;
    private ArrayList<ModelListView> dataModelArrayList;

    public ListAdaptor(Context context, ArrayList<ModelListView> dataModelArrayList) {

        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return dataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //contain the necessary coding lines to fill the information.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view, null, true);

            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.tvemail = (TextView) convertView.findViewById(R.id.email);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        // populating the row of the listview
        Picasso.get().load(dataModelArrayList.get(position).getImgURL()).into(holder.iv);
        holder.tvname.setText("Name: "+dataModelArrayList.get(position).getName());
        holder.tvemail.setText("Email: "+dataModelArrayList.get(position).getEmail());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname,tvemail;
        protected ImageView iv;
    }

}
