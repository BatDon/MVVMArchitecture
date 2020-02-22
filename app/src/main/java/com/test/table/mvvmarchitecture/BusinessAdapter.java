package com.test.table.mvvmarchitecture;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class BusinessAdapter extends ListAdapter<Business, BusinessAdapter.BusinessHolder> {
    private OnItemClickListener listener;

    public BusinessAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Business> DIFF_CALLBACK = new DiffUtil.ItemCallback<Business>() {
        @Override
        public boolean areItemsTheSame(Business oldItem, Business newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Business oldItem, Business newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public BusinessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.business_item, parent, false);
        return new BusinessHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessHolder holder, int position) {
        Business currentBusiness = getItem(position);
        holder.textViewTitle.setText(currentBusiness.getTitle());
        holder.textViewDescription.setText(currentBusiness.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentBusiness.getPriority()));
    }

    public Business getBusinessAt(int position) {
        return getItem(position);
    }

    class BusinessHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public BusinessHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Business business);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
