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

public class  Listadapteruser extends BaseAdapter {

    Context context1;
    List<Username> useid;

    public Listadapteruser(List<Username> listValue1, Context context1)
    {
        this.context1 = context1;
        this.useid = listValue1;
    }

    @Override
    public int getCount()
    {
        return this.useid.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.useid.get(position);
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

            LayoutInflater layoutInflater = (LayoutInflater)this.context1.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.fragment_listuser,null);

            viewItem.Username= (TextView)convertView.findViewById(R.id.listusername);

            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }

        viewItem.Username.setText(useid.get(position).Users_name);

        return convertView;
    }
    class ViewItem
    {
        TextView Username;

    }

}




