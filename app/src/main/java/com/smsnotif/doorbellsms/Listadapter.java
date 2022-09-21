package com.smsnotif.doorbellsms;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.doorbellsms.R;

import java.util.List;

public class  Listadapter extends BaseAdapter {

    Context context;
    List<doorbell> dbid;

    public Listadapter(List<doorbell> listValue, Context context)
    {
        this.context = context;
        this.dbid = listValue;
    }

    @Override
    public int getCount()
    {
        return this.dbid.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.dbid.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewItem viewItem;

        if(convertView == null)
        {
            viewItem = new ViewItem();

            LayoutInflater layoutInflater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.fragment_listview,null);

            viewItem.Doorbellname= (TextView)convertView.findViewById(R.id.listdbname);

            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }

        viewItem.Doorbellname.setText(dbid.get(position).doorbellName);

        return convertView;
    }
    class ViewItem
    {
        TextView Doorbellname;

    }

}




