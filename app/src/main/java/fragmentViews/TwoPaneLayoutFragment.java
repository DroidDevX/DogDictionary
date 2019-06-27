package fragmentViews;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.droiddevsa.doganator.R;
import com.droiddevsa.doganator.animation.AnimationUtils;
import com.droiddevsa.doganator.data.DogEntry;
import com.droiddevsa.doganator.data.DogEntryList;
import com.droiddevsa.doganator.data.MasterListItemAdapter;
import com.droiddevsa.doganator.utils.ImageHelperUtils;
import com.droiddevsa.doganator.utils.MyApplication;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;



public class TwoPaneLayoutFragment extends Fragment implements
        MainActivityUI,
        MasterListItemAdapter.RecyclerViewClickListener,
        ImageHelperUtils.ImageDownloadListener
        {
    final static String LOG_TAG = TwoPaneLayoutFragment.class.getSimpleName();
    /*UI*/
    DetailedView_ViewHolder m_viewHolder;
    MasterListItemAdapter m_masterListAdapter ;

    /*DATA*/
    List<DogEntry> m_dogEntries;
    private SparseIntArray m_dogIndexFinder;
    final public static String DOGENTRIES_BUNDLE_KEY="dog_entries_key";

    public TwoPaneLayoutFragment() {
        // Required empty public constructor
    }

    public static TwoPaneLayoutFragment newInstance(List<DogEntry> dogEntries) {
        TwoPaneLayoutFragment fragment = new TwoPaneLayoutFragment();
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
        LinearLayout rootView =(LinearLayout)inflater.inflate(R.layout.fragment_mainactivity_two_pane, container, false);

        m_viewHolder = new DetailedView_ViewHolder(rootView);

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
    public void onMasterListItemClicked(int dogID) {
        Log.d(LOG_TAG,"onMasterListItemClicked(), DogID: "+ String.valueOf(dogID));
        int dogIndexPosition = m_dogIndexFinder.get(dogID);
        DogEntry selectedDog = m_dogEntries.get(dogIndexPosition);
        updateDetailedView(selectedDog);
    }

    @Override
    public void updateUserInterface(List<DogEntry> dogEntries) {
        Log.d(LOG_TAG,"updateUserInterface(), dogEntries -> size ="+ String.valueOf(dogEntries.size()));
        m_masterListAdapter = new MasterListItemAdapter(this,dogEntries);
        m_viewHolder.masterListRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
        m_viewHolder.masterListRecyclerView().setAdapter(m_masterListAdapter);
        DogEntry firstDog = dogEntries.get(0);
        updateDetailedView(firstDog);
    }

    private void updateDetailedView(DogEntry dogEntry){

        m_viewHolder.dogBreedTextView().setText(dogEntry.getDogBreed());
        m_viewHolder.weightTextView().setText(dogEntry.getWeight());
        m_viewHolder.heightTextView().setText(dogEntry.getHeight());
        m_viewHolder.breedGroupTextView().setText(dogEntry.getBreedGroup());
        m_viewHolder.bredForTextView().setText(dogEntry.getBredFor());
        m_viewHolder.temperamentTextView().setText(dogEntry.getTemperament());
        m_viewHolder.lifeSpanTextView().setText(dogEntry.getLifeSpan());


        updateDogImageImageView(dogEntry);
    }

    private void updateDogImageImageView(DogEntry dogEntry){
        if(ImageHelperUtils.dogImageExists(dogEntry.getDogBreed()))
           loadImageFromStorage(dogEntry.getDogBreed());
        else
          downloadDogImage(dogEntry);

    }

    private void loadImageFromStorage(String dogbreed){
        final File imageFile = new File(ImageHelperUtils.getDogImagePath(dogbreed));
        m_viewHolder.detailedView().post(new Runnable() {
            @Override
            public void run() {
                m_viewHolder.dogImageView().clearAnimation();
                int imageWidth = (int) (0.5 * m_viewHolder.detailedView().getWidth());
                Picasso.with(getContext()).load(imageFile).resize(imageWidth, 0).into(m_viewHolder.dogImageView());
            }
        });
    }

    private void downloadDogImage(DogEntry dogEntry){
        m_viewHolder.dogImageView().setImageResource(R.mipmap.ic_loading);
        AnimationUtils.startDownloadingAnimation(m_viewHolder.m_dogImageView);
        ImageHelperUtils.downloadDogImage(this,dogEntry);
    }

    @Override
    public void onDogImageDownload(Bitmap bitmap, String dogbreed) {
        ImageHelperUtils.saveBitmapToInternalStorage(bitmap,dogbreed);
        loadImageFromStorage(dogbreed);
    }

    @Override
    public void showSearchResults() {
        Log.d(LOG_TAG,"showSearchResults()");
        //Nothing
    }

    @Override
    public void hideSearchResults() {
        Log.d(LOG_TAG,"hideSearchResults()");
        applySearchFilter("");
    }

    @Override
    public void applySearchFilter(String searchQuery) {
        Log.d(LOG_TAG,"applySearchFilter()");
        m_masterListAdapter.getFilter().filter(searchQuery);
    }

    private class DetailedView_ViewHolder{
       private LinearLayout m_rootView;
        private  TextView m_DogBreedTextView;
        private TextView m_weightTextView;
        private TextView m_heightTextView;
        private TextView m_breedGroupTextView;
        private TextView m_bredForTextView;
        private TextView m_temperamentTextView;
        private TextView m_lifeSpanTextView;
        private RecyclerView m_masterListRecycelerView;
        private ImageView m_dogImageView;
        private LinearLayout m_detailedView;


        public DetailedView_ViewHolder(LinearLayout rootView){
            this.m_rootView = rootView;
            m_DogBreedTextView = m_rootView.findViewById(R.id.textView_dogbreed);
            m_weightTextView   = m_rootView.findViewById(R.id.textview_weight);
            m_heightTextView   = m_rootView.findViewById(R.id.textview_height);
            m_breedGroupTextView = m_rootView.findViewById(R.id.textView_bredGroup);
            m_bredForTextView  = m_rootView.findViewById(R.id.textView_bredFor);
            m_temperamentTextView = m_rootView.findViewById(R.id.textview_temperament);
            m_lifeSpanTextView = m_rootView.findViewById(R.id.textView_lifespan);
            m_masterListRecycelerView = m_rootView.findViewById(R.id.masterListRecyclerView);
            m_dogImageView = rootView.findViewById(R.id.dogImageView);
            m_detailedView = m_rootView.findViewById(R.id.detailedView);
        }

        TextView dogBreedTextView(){return m_DogBreedTextView;}
        TextView weightTextView (){return m_weightTextView;}
        TextView heightTextView(){return m_heightTextView;}
        TextView breedGroupTextView(){return m_breedGroupTextView;}
        TextView bredForTextView(){return m_bredForTextView;}
        TextView temperamentTextView(){return m_temperamentTextView;}
        TextView lifeSpanTextView(){return m_lifeSpanTextView;}
        RecyclerView masterListRecyclerView(){return m_masterListRecycelerView;}
        ImageView dogImageView(){return m_dogImageView;}
        LinearLayout detailedView(){return m_detailedView;}
    }
}
