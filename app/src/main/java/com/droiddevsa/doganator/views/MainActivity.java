package com.droiddevsa.doganator.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import fragmentViews.MainActivityUI;
import fragmentViews.OnePaneLayoutFragment;
import fragmentViews.TwoPaneLayoutFragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.droiddevsa.doganator.R;
import com.droiddevsa.doganator.data.DogEntry;

import com.droiddevsa.doganator.data.DogViewModel;


import java.util.List;


public class MainActivity extends AppCompatActivity
{
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    //Ui elements
    SearchView m_searchView;
    Fragment m_userInterface;
    DogViewModel m_viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Search");

        final int DeviceOrientation = getResources().getConfiguration().orientation;

        //View model
        m_viewModel = ViewModelProviders.of(this).get(DogViewModel.class);
        m_viewModel.liveData().observe(this, new Observer<List<DogEntry>>() {
            @Override
            public void onChanged(List<DogEntry> dogEntries) {
                Log.d(LOG_TAG,"onChanged()");
                if(DeviceOrientation == Configuration.ORIENTATION_PORTRAIT)
                    m_userInterface = OnePaneLayoutFragment.newInstance(dogEntries);
                else
                    m_userInterface = TwoPaneLayoutFragment.newInstance(dogEntries);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FragmentHolder,m_userInterface);
                ft.commit();

            }
        });

        Log.d(LOG_TAG,"onCreate Finish...");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(LOG_TAG,"onCreateOptionsMenu()");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        m_searchView= (SearchView) searchItem.getActionView();
        setupSearchView(m_searchView);
        return true;   }


    public void setupSearchView(SearchView searchView){
        searchView.setQueryHint("Search for a dogbreed"); //Search bar hint
        searchView.setMaxWidth(Integer.MAX_VALUE);//Use entire serach bar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Log.d(LOG_TAG,"onQueryTextChange()");
                ((MainActivityUI) m_userInterface).applySearchFilter(query);
                return true; }
        });
    }

}

