package com.droiddevsa.doganator.data;


import fragmentViews.Fragment_DogEntryPage;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DogEntryPagerAdapter extends FragmentPagerAdapter {

    final static String LOG_TAG = DogEntryPagerAdapter.class.getSimpleName();
    List<DogEntry> dogEntries;

    public DogEntryPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }




    public void setDogEntries(List<DogEntry> dogEntries){
        this.dogEntries = dogEntries;
    }
    /*
    * Placing notifyDataChanged() in the live data's onChanged() method may cause some performance problems.
    * When a list of entries are inserted into the room DB. onChanged() is going to be called for each entry inserted.
    * (Its not going to be called once per one insert room query)
    *  If you put notifyDataChanged() in onChanged(), say when onChanged() is called 8 times, then the pager
    *  adapter is also going to be 'updated' 8 times!!.
    *
    *  First check if onChanged() is called 8 times.
    *
    *  //
    * */

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(dogEntries!=null)
            return Fragment_DogEntryPage.newInstance(dogEntries.get(position));
        else
            return null;
    }

    @Override
    public int getCount() {
        if(dogEntries==null)
            return 0;
        else
            return dogEntries.size();

    }
}
