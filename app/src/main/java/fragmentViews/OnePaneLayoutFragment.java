package fragmentViews;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droiddevsa.doganator.R;
import com.droiddevsa.doganator.data.DogEntry;
import com.droiddevsa.doganator.data.DogEntryList;
import com.droiddevsa.doganator.data.DogEntryPagerAdapter;
import com.droiddevsa.doganator.data.MasterListItemAdapter;
import com.droiddevsa.doganator.utils.KeyboardUtils;
import com.droiddevsa.doganator.views.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class OnePaneLayoutFragment extends Fragment implements MainActivityUI,MasterListItemAdapter.RecyclerViewClickListener {

    final static String LOG_TAG = "OnePaneLayoutFragment";
    final static String DOGENTRIES_BUNDLE_KEY ="dog_entries";
    private MasterListItemAdapter m_masterListAdapter;
    private RecyclerView m_masterListRecyclerView;
    private ViewPager m_dogViewPager;
    private MainActivity m_activity;
    private SparseIntArray m_dogIndexFinder;

    private List<DogEntry> m_dogEntries;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        m_activity= (MainActivity)context;
    }

    public OnePaneLayoutFragment() {
        // Required empty public constructor
    }

    public static OnePaneLayoutFragment newInstance(List<DogEntry> dogEntries) {
        OnePaneLayoutFragment fragment = new OnePaneLayoutFragment();
        DogEntryList list = new DogEntryList();
        list.addList(dogEntries);
        Bundle args = new Bundle();
        args.putParcelable(DOGENTRIES_BUNDLE_KEY,list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG,"onCreateView()");
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_mainactivity_one_pane, container, false);
        m_masterListRecyclerView = rootView.findViewById(R.id.masterListRecyclerView);
        m_dogViewPager = rootView.findViewById(R.id.dogViewPager);

        if(getArguments()!=null){
            DogEntryList list =getArguments().getParcelable(DOGENTRIES_BUNDLE_KEY);
            if(list!=null) {
                m_dogEntries = list.getListValue();
                updateUserInterface(m_dogEntries);
                setupDogIndexFinder(m_dogEntries);
            }
        }
        return rootView;
    }

    private void setupDogIndexFinder(List<DogEntry> dogEntries){
        //Save each DogEntry's DogID with its' index position in a key value pair
        m_dogIndexFinder = new SparseIntArray();
        for(int indexPosition=0;indexPosition < dogEntries.size();indexPosition++) {
            DogEntry entry = dogEntries.get(indexPosition);
            m_dogIndexFinder.put(entry.getDogID(),indexPosition);
        }
    }


    @Override
    public void updateUserInterface(List<DogEntry> dogEntries) {
        Log.d(LOG_TAG,"updateUserInterface(), dogEntries:size -> "+String.valueOf(dogEntries.size()));
        //Update masterlist
        m_masterListAdapter = new MasterListItemAdapter(this,dogEntries);
        m_masterListRecyclerView.setLayoutManager(new LinearLayoutManager(m_activity));
        m_masterListRecyclerView.setAdapter(m_masterListAdapter);

        //Update viewpager
        DogEntryPagerAdapter viewpagerAdapter = new DogEntryPagerAdapter(getChildFragmentManager());
        ArrayList<DogEntry> shallowCopyEntries = new ArrayList<>();
        shallowCopyEntries.addAll(dogEntries);
        viewpagerAdapter.setDogEntries(shallowCopyEntries);
        m_dogViewPager.setAdapter(viewpagerAdapter);




    }

    @Override
    public void showSearchResults() {
        Log.d(LOG_TAG,"showSearchResults()");
        m_masterListRecyclerView.setVisibility(View.VISIBLE);
        m_dogViewPager.setVisibility(View.GONE);
    }

    @Override
    public void hideSearchResults() {
        Log.d(LOG_TAG,"hideSearchResults()");
        KeyboardUtils.hideKeyboard(m_activity);
        m_masterListRecyclerView.setVisibility(View.GONE);
        m_dogViewPager.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMasterListItemClicked(int dogID) {
        Log.d(LOG_TAG,"onMasterListItemClicked()");
        hideSearchResults();
        int indexPosition = m_dogIndexFinder.get(dogID);
        m_dogViewPager.setCurrentItem(indexPosition);
    }

    @Override
    public void applySearchFilter(String searchQuery) {
        Log.d(LOG_TAG,"applySearchFilter():Query-> "+ searchQuery);
        //Applies filter and calls notifyOnDataChanged()
        if(searchQuery.isEmpty()|| searchQuery.equals(""))
            hideSearchResults();
        else {

            m_masterListAdapter.getFilter().filter(searchQuery);
            showSearchResults();
        }
    }

}
