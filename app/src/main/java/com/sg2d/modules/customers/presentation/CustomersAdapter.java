package com.sg2d.modules.customers.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sg2d.modules.R;
import com.sg2d.modules.customers.entities.CustomerEntry;

import java.util.ArrayList;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    CustomerSelectListener customerSelectListener;
    ArrayList<CustomerEntry> filteredCustomerList;
    ArrayList<CustomerEntry> customerList;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filteredCustomerList = customerList;
                } else {
                    ArrayList<CustomerEntry> filteredList = new ArrayList<>();
                    for (CustomerEntry row : customerList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getEmail().toLowerCase().contains(charString.toLowerCase())
                                || row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filteredCustomerList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredCustomerList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredCustomerList = (ArrayList<CustomerEntry>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout userItem;
        ImageView profilePhoto;
        TextView userName;
        TextView userRole;

        public MyViewHolder(View view) {
            super(view);
            userItem = view.findViewById(R.id.user_item);
            profilePhoto = view.findViewById(R.id.profile_photo);
            userName = view.findViewById(R.id.user_name);
            userRole = view.findViewById(R.id.user_role);
        }
    }


    public CustomersAdapter(Context mContext, CustomerSelectListener customerSelectListener, ArrayList<CustomerEntry> filteredCustomerList) {
        this.mContext = mContext;
        this.customerSelectListener = customerSelectListener;
        this.filteredCustomerList = filteredCustomerList;
        this.customerList = filteredCustomerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_users, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.userName.setText(filteredCustomerList.get(position).getName());
        holder.userRole.setText(filteredCustomerList.get(position).getEmail());
        if (filteredCustomerList.get(position).getLevel() == 0) {
            holder.profilePhoto.setImageResource(R.drawable.ic_support_user);
        }
        holder.userItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerSelectListener.onCustomerSelect(filteredCustomerList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredCustomerList.size();
    }

    public interface CustomerSelectListener {
        void onCustomerSelect(int userId);
    }
}