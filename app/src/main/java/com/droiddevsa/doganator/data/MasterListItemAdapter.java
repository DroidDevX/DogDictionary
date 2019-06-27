package com.droiddevsa.doganator.data;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.droiddevsa.doganator.R;
import com.droiddevsa.doganator.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MasterListItemAdapter extends RecyclerView.Adapter<MasterListItemAdapter.TextViewHolder> implements Filterable
{
    private final static String LOG_TAG = MasterListItemAdapter.class.getSimpleName();
    private String m_dogSelected ="";

    private List<DogEntry> m_dogEntriesFiltered;
    private List<DogEntry> m_dogEntriesFull;
    private RecyclerViewClickListener m_clickListener;

    public interface RecyclerViewClickListener{
        void onMasterListItemClicked(int dogID);
    }

    class TextViewHolder extends RecyclerView.ViewHolder{
        int itemIndex=-1;
        TextViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }
        void setItemIndex(int itemIndex){
            this.itemIndex=itemIndex;
        }
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        Log.d(LOG_TAG,"onCreateViewHolder()");
        Context context = recyclerView.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View textView = inflater.inflate(R.layout.recyclerview_masterlist_item,recyclerView,false);

        //Setup the onclick listener for each item
        final MasterListItemAdapter adapter = this;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dogItem = (TextView)v;
                int dogID=  (int)dogItem.getTag();
                m_dogSelected = dogItem.getText().toString();
                m_clickListener.onMasterListItemClicked(dogID);
                adapter.notifyDataSetChanged();
            }
        });
        return new TextViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TextViewHolder holder, int itemIndex) {
       String dogBreed = m_dogEntriesFiltered.get(itemIndex).getDogBreed();
        holder.setItemIndex(itemIndex);
       ((TextView)holder.itemView).setText(dogBreed);

        //Store the dog ID as a tag, use in the textview's onclick listener
        (holder.itemView).setTag(m_dogEntriesFiltered.get(itemIndex).getDogID());

        //Highlight selected itemview if selected, else set itemview to default color
        if(dogBreed.equals(m_dogSelected)){
            holder.itemView.setBackgroundResource(R.color.colorPrimary);
            ((TextView) holder.itemView).setTextColor(Color.WHITE);
        }
        else
        {
            holder.itemView.setBackgroundResource(R.color.colorWhite);
            ((TextView)holder.itemView).setTextColor(ContextCompat.getColor(MyApplication.getAppContext(),R.color.primary_text));
        }



    }

    @Override
    public int getItemCount() {
        return m_dogEntriesFiltered.size();
    }

    public MasterListItemAdapter(RecyclerViewClickListener clickListener , List<DogEntry> dogEntries){
        this.m_dogEntriesFiltered = dogEntries;
        this.m_dogEntriesFull = new ArrayList<>(dogEntries);
        this.m_clickListener = clickListener;


        if(m_dogEntriesFiltered==null|| m_dogEntriesFiltered.isEmpty())
            m_dogSelected="";
        else
            m_dogSelected= this.m_dogEntriesFiltered.get(0).getDogBreed();

    }

    @Override
    public Filter getFilter() { return m_Filter; }

   Filter  m_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence searchViewQuery) {
           //Done on worker thread
            List<DogEntry> filteredResults= new ArrayList<>();
            if(searchViewQuery==null || searchViewQuery.length()==0) {
                Log.i(LOG_TAG,"Query size is null");
                filteredResults.addAll(m_dogEntriesFull);
            }else
            {
                String filterPattern = searchViewQuery.toString().toLowerCase().trim();
                for(DogEntry entry: m_dogEntriesFull)
                    if(entry.getDogBreed().toLowerCase().contains(filterPattern))
                        filteredResults.add(entry);
            }

            FilterResults results = new FilterResults();
            results.values = filteredResults;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //If you want to display filtered list. This method runs on the ui thread
            if(results.values==null)
                return;

            List<DogEntry> resultList = (List<DogEntry>)  results.values;
            Log.i(LOG_TAG,"result list: "+ resultList);
            m_dogEntriesFiltered.clear();
            m_dogEntriesFiltered.addAll((resultList));
            notifyDataSetChanged();

        }
    };


}
