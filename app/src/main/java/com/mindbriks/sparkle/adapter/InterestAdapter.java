package com.mindbriks.sparkle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.model.InterestCategory;

import java.util.List;

public class InterestAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<InterestCategory> interestCategories;

    public InterestAdapter(Context context, List<InterestCategory> interestCategories) {
        this.context = context;
        this.interestCategories = interestCategories;
    }

    @Override
    public int getGroupCount() {
        return interestCategories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return interestCategories.get(groupPosition).getInterests().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return interestCategories.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return interestCategories.get(groupPosition).getInterests().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_interest_category, null);
        }

        TextView tvCategoryName = convertView.findViewById(R.id.group_name);
        tvCategoryName.setText(interestCategories.get(groupPosition).getCategoryName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_interest, null);
        }

        TextView tvInterestName = convertView.findViewById(R.id.child_name);
        tvInterestName.setText(interestCategories.get(groupPosition).getInterests().get(childPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
