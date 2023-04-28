package com.example.mypet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<PetProfile> mData;
    private LayoutInflater inflater;
    private Context context;
    final ListAdapter.OnItemClickListener listener;
    public interface  OnItemClickListener {
        void onItemClick(PetProfile pet);

    }

    public ListAdapter(List<PetProfile> itemPet, Context context, ListAdapter.OnItemClickListener listener){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemPet;
        this.listener = listener;
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card,null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.binData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name,idPet   ;
        public ViewHolder(View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.ivPetProfile);
            name = itemView.findViewById(R.id.ivPetName);
            idPet = itemView.findViewById(R.id.tvIdPet);

        }
        void binData(final PetProfile pet){
            Picasso.get().load(pet.getUriPet()).into(profile);
            name.setText(pet.getName());
            String id = String.valueOf(pet.getIdPet());
            idPet.setText(id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(pet);
                }
            });
        }
    }
}
