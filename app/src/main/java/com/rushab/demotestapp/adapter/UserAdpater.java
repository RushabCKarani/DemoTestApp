package com.rushab.demotestapp.adapter;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rushab.demotestapp.R;
import com.rushab.demotestapp.model.Users;

import java.util.ArrayList;
import java.util.List;

public class UserAdpater extends RecyclerView.Adapter<UserAdpater.UserAdpaterViewHolder>
        implements Filterable {

    private static final String TAG = "UserAdpater";
    private Context mCtx;
    private List<Users> list;
    private List<Users> filteredlist;

    public UserAdpater(Context mCtx, List<Users> list) {
        this.mCtx = mCtx;
        this.list = list;
        this.filteredlist = list;
    }

    @Override
    public UserAdpaterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.row_home, parent, false);
        return new UserAdpaterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final UserAdpaterViewHolder holder, final int position) {
        final Users user = filteredlist.get(position);

        holder.tvName.setText(user.getFirstName() + " " + user.getLastName());
        holder.tvEmail.setText(user.getEmail());

        Glide.with(mCtx)
                .load(user.getAvatar())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.ivAvatar);
    }


    @Override
    public int getItemCount() {
        return filteredlist.size();
    }

    class UserAdpaterViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvEmail;
        CardView cvMain;
        ImageView ivAvatar;

        public UserAdpaterViewHolder(View itemView) {
            super(itemView);

            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvName = itemView.findViewById(R.id.tvName);
            cvMain = itemView.findViewById(R.id.cvMain);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredlist = list;
                } else {
                    Log.d(TAG, "performFiltering: " + charString);
                    List<Users> filteredList1 = new ArrayList<>();
                    for (Users row : list) {
                        if (row.getFirstName().toLowerCase().contains(charString.toLowerCase())
                                || row.getLastName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList1.add(row);
                            Log.d(TAG, "filteredList1 added");
                        }
                    }

                    filteredlist = filteredList1;
                    Log.d(TAG, "performFiltering size: " + filteredlist.size());
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredlist;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredlist = (ArrayList<Users>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
