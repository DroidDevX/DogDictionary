package fragmentViews;

import com.droiddevsa.doganator.data.DogEntry;

import java.util.List;


public interface MainActivityUI {


    void updateUserInterface(List<DogEntry> dogEntries);
    void showSearchResults();
    void hideSearchResults();
    void applySearchFilter(String searchQuery);
}
