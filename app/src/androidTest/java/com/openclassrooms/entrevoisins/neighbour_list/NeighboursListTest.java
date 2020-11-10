
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.ViewPagerActions.scrollRight;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;
    private final List<Neighbour> mNeighboursList = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
    private final NeighbourApiService mApiService = DI.getNeighbourApiService();
    private final List<Neighbour> mFavoriteNeighboursList = mApiService.getFavorites();

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * When we click on an item, open the profile activity
     */
    @Test
    public void myNeighboursList_clickAction_shouldOpenProfileNeighbourActivity(){
        // When perform a click on a element
        onView(withId(R.id.list_neighbours)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Then : open the profile activity
        onView(withId(R.id.profile_neighbour)).check(matches(isDisplayed()));
    }

    /**
     * We ensure that the name displayed in the profile activity correspond to the clicked item
     */
    @Test
    public void checkIf_nameTextView_isCorrectlyFilled(){
        // Given : we retrieve the element information
        Neighbour neighbour = mNeighboursList.get(0);
        // We perform a click on that element, then open the profile activity
        onView(withId(R.id.list_neighbours)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Then : the displayed name correspond to the retrieved information
        onView(withId(R.id.name_text_view)).check(matches(withText(neighbour.getName())));
    }

    /**
     * When we delete a favorite, the item is no more shown
     */
    @Test
    public void myFavoritesNeighboursList_deleteAction_shouldRemoveItem(){
        // Given : we add 2 element to the favorite list
        mApiService.addToFavorite(mNeighboursList.get(0));
        mApiService.addToFavorite(mNeighboursList.get(1));
        onView(withId(R.id.list_favorite_neighbours)).check(withItemCount(2));
        // We move to the favorite tab
        onView(withId(R.id.container)).perform(scrollRight());
        // When perform a click on a delete icon
        onView(withId(R.id.list_favorite_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 1
        onView(withId(R.id.list_favorite_neighbours)).check(withItemCount(2-1));
    }

    /**
     * We ensure that our favorite recyclerview only displays favorites
     */
    @Test
    public void checkIf_myFavoritesNeighboursList_onlyContainsFavorites() {
        // Given : we add 1 element to the favorite list
        mFavoriteNeighboursList.clear();
        // When profile activity is opened
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        // When we perform a click on the favorite FAB
        onView(withId(R.id.favorite_button)).perform(click());
        // We return to the previous activity
        pressBack();
        // Then : the number of favorite element in our list is 1
        onView(withId(R.id.list_favorite_neighbours)).check(withItemCount(1));
    }
}