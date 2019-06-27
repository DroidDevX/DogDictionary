package fragmentViews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.droiddevsa.doganator.R;
import com.droiddevsa.doganator.animation.AnimationUtils;
import com.droiddevsa.doganator.utils.ImageHelperUtils;
import com.droiddevsa.doganator.data.DogEntry;
import com.droiddevsa.doganator.views.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.File;

//Used in View Pager
public class Fragment_DogEntryPage extends Fragment implements ImageHelperUtils.ImageDownloadListener {

    //CONSTANTS
    final private static String LOG_TAG = "Fragment_DogEntryPage";
    final static String DOG_ENTRY_KEY = "DOG_ENTRY";

    //UI MEMBERS
    DetailedView_ViewHolder m_viewHolder;
    Activity m_activity;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        m_activity = (MainActivity)context;
    }

    /**************************************************************************/


    public Fragment_DogEntryPage() {
        // Required empty public constructor
    }

    public static Fragment_DogEntryPage newInstance(DogEntry dogEntry)
    {
        Log.i(LOG_TAG,"newInstance()");
        Fragment_DogEntryPage dogFragment = new Fragment_DogEntryPage();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DOG_ENTRY_KEY,dogEntry);
        dogFragment.setArguments(bundle);

        return  dogFragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(LOG_TAG,"onCreateView()");
        View rootView=inflater.inflate(R.layout.fragment_viewpager_dogentry,container,false);
        m_viewHolder = new DetailedView_ViewHolder(rootView);

        Bundle bundle = getArguments();
        if(bundle!=null) {
            DogEntry dogEntry = bundle.getParcelable(DOG_ENTRY_KEY);
            updateUi(dogEntry);
        }

        return  rootView;
    }

    void updateUi(DogEntry dogEntry){
        Log.i(LOG_TAG,"updateUi()");

        m_viewHolder.dogBreedTextView().setText(dogEntry.getDogBreed());
        m_viewHolder.weightTextView().setText(dogEntry.getWeight());
        m_viewHolder.heightTextView().setText(dogEntry.getHeight());
        m_viewHolder.breedGroupTextView().findViewById(R.id.textView_bredGroup);
        m_viewHolder.bredForTextView().setText(dogEntry.getBredFor());
        m_viewHolder.lifeSpanTextView().setText(dogEntry.getLifeSpan());
        m_viewHolder.temperamentTextView().setText(dogEntry.getTemperament());
        m_viewHolder.dogImageView().setImageResource(R.mipmap.ic_loading);


        if(ImageHelperUtils.dogImageExists(dogEntry.getDogBreed())) {
            Log.d(LOG_TAG,"dog image exists -> loading image into imageView()");
            m_viewHolder.dogImageView().clearAnimation();
            loadImage(dogEntry.getDogBreed());
        }
        else {
            Log.d(LOG_TAG,"dog image does not exist-> downloading image");
            ImageHelperUtils.downloadDogImage(this,dogEntry);
            AnimationUtils.startDownloadingAnimation(m_viewHolder.dogImageView());
        }
    }

    private void loadImage(String dogbreed){
        Log.d(LOG_TAG,"loadImage()");
        File imageFile = new File(ImageHelperUtils.getDogImagePath(dogbreed));
        Picasso.with(getContext()).load(imageFile).resize(getDevicePortraitWidth(),0).into(m_viewHolder.dogImageView());
    }

    @Override
    public void onDogImageDownload(Bitmap bitmap,String dogbreed) {
        Log.i(LOG_TAG,"onDogImageDownload()");
        if(m_viewHolder.dogImageView()!=null)
        {
            AnimationUtils.stopDownloadngAnimation(m_viewHolder.dogImageView());
            ImageHelperUtils.saveBitmapToInternalStorage(bitmap,dogbreed);
            loadImage(dogbreed);
        }

    }

    public int getDevicePortraitWidth(){
        Display display = m_activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }


    private class DetailedView_ViewHolder{
        private  TextView m_DogBreedTextView;
        private TextView m_weightTextView;
        private TextView m_heightTextView;
        private TextView m_breedGroupTextView;
        private TextView m_bredForTextView;
        private TextView m_temperamentTextView;
        private TextView m_lifeSpanTextView;
        private TextView m_swipeTextView;
        private ImageView m_dogImageView;


        public DetailedView_ViewHolder(View rootView){
            m_DogBreedTextView    = rootView.findViewById(R.id.textView_dogbreed);
            m_weightTextView      = rootView.findViewById(R.id.textview_weight);
            m_heightTextView      = rootView.findViewById(R.id.textview_height);
            m_breedGroupTextView  = rootView.findViewById(R.id.textView_bredGroup);
            m_bredForTextView     = rootView.findViewById(R.id.textView_bredFor);
            m_lifeSpanTextView    = rootView.findViewById(R.id.textView_lifespan);
            m_temperamentTextView = rootView.findViewById(R.id.textview_temperament);
            m_swipeTextView       = rootView.findViewById(R.id.swipeTextView);
            m_dogImageView        = rootView.findViewById(R.id.dogImage);
        }

        TextView dogBreedTextView(){return m_DogBreedTextView;}
        TextView weightTextView (){return m_weightTextView;}
        TextView heightTextView(){return m_heightTextView;}
        TextView breedGroupTextView(){return m_breedGroupTextView;}
        TextView bredForTextView(){return m_bredForTextView;}
        TextView temperamentTextView(){return m_temperamentTextView;}
        TextView lifeSpanTextView(){return m_lifeSpanTextView;}
        ImageView dogImageView(){return m_dogImageView;}
    }

}

