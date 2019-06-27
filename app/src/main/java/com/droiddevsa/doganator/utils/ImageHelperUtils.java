package com.droiddevsa.doganator.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import fragmentViews.Fragment_DogEntryPage;
import com.droiddevsa.doganator.data.DogEntry;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

public class ImageHelperUtils {
    /*Description: This is a image helper class that assists in storing
    * downloaded images to the external storage as well as retreiving
    * them using the Picaso Libarary*/
    private final static String LOG_TAG=ImageHelperUtils.class.getSimpleName();
    private static String DOG_IMAGE_DIR="";
    private static SparseBooleanArray DOGIMAGE_DOWNLOADING =new SparseBooleanArray();
    private static SparseArray<Target> PICASO_IMAGE_TARGET_DOWNLOAD_QUEUE = new SparseArray<>();

    public interface ImageDownloadListener {
        void onDogImageDownload(Bitmap bitmap,String dogbreed);
    }

    public static void downloadDogImage(final ImageDownloadListener listener, final DogEntry dogEntry){
        if(!DOGIMAGE_DOWNLOADING.get(dogEntry.getDogID(),false)){
            Log.i(LOG_TAG,String.format("Downloading Image %s.jpg: not downloaded yet",dogEntry.getDogBreed()));
            DOGIMAGE_DOWNLOADING.append(dogEntry.getDogID(),true);

            //Make picaso targets static (strong reference) to avoid being garbage collected
            Target imageTarget = new Target() {
                DogEntry m_dogEntry = dogEntry;

                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
                {
                    Log.i(LOG_TAG,"onBitMapLoaded() for dog with id: "+ String.valueOf(m_dogEntry.getDogID())+
                            " , dogbreed:"+ m_dogEntry.getDogBreed());
                    listener.onDogImageDownload(bitmap,m_dogEntry.getDogBreed());
                    DOGIMAGE_DOWNLOADING.append(m_dogEntry.getDogID(),false);
                    PICASO_IMAGE_TARGET_DOWNLOAD_QUEUE.remove(m_dogEntry.getDogID());
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.e(LOG_TAG,"onBitMapFailed");
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            PICASO_IMAGE_TARGET_DOWNLOAD_QUEUE.append(dogEntry.getDogID(),imageTarget);
            Picasso.with(MyApplication.getAppContext()).load(dogEntry.getImageUrl()).into(imageTarget);
        }
        else
            Log.e(LOG_TAG,String.format("Image for Dog breed: %s.jpg is still downloading",dogEntry.getDogBreed()));
    }

    public static void initializeImageDirectory(Context context){
        Log.i(LOG_TAG,"initialzImageDirectory()");

        String dogImagesDirectoryPath =context.getFilesDir().getAbsoluteFile()+"/dogImages";
        File dogImageDirectory = new File(dogImagesDirectoryPath);
        if (!dogImageDirectory.exists()) {
            dogImageDirectory.mkdir();
            Log.i(LOG_TAG,"Dog Image directory created: "+DOG_IMAGE_DIR) ;

        }
        DOG_IMAGE_DIR=dogImageDirectory.getAbsolutePath();

    }

    public static void saveBitmapToInternalStorage(Bitmap bitmap,String dogbreed){
        Context context = MyApplication.getAppContext();
        if(context==null){
            Log.e(LOG_TAG,"Fatal error: Activity destroyed on device rotation, upon download complete. Cannot save image" +
                    " because the context was destroyed with the activity");
            return;
        }

        try{
            if(dogImageExists(dogbreed)) {
                Log.i(LOG_TAG,"%s.jpg container exists...terminating saving image process now. Not saving new downloaded image");
                return; }

            File file = new File(DOG_IMAGE_DIR,String.format("%s.jpg",dogbreed));
            boolean createdImageFile=false;
            createdImageFile =file.createNewFile();
            if (!createdImageFile) {
                Log.e(LOG_TAG,String.format("Failed to create image file...%s.jpg",dogbreed));
                return;
            }

            Log.i(LOG_TAG,"Image file created...saving image now");
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 77, ostream);
            ostream.flush();
            ostream.close();

        }catch (Exception e)
        {
            Log.e(LOG_TAG,"Error occured while trying to store image: "+e.toString());
            e.printStackTrace();
        }

    }

    public static String getDogImagePath(String dogbreed){
        return DOG_IMAGE_DIR + String.format("/%s.jpg",dogbreed);
    }
    public static boolean dogImageExists(String dogbreed){
        File file = new File(DOG_IMAGE_DIR,String.format("%s.jpg",dogbreed));

        if(!DOG_IMAGE_DIR.isEmpty()||DOG_IMAGE_DIR.equals(""))
            initializeImageDirectory(MyApplication.getAppContext());

        return file.exists();
    }


}

