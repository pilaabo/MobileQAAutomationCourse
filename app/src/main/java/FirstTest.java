import lib.CoreTestCase;
import ui.MainPageObject;
import ui.SearchPageObject;

public class FirstTest extends CoreTestCase {
    private MainPageObject mainPageObject;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainPageObject = new MainPageObject(driver);
    }

    public void testSearch() {
        mainPageObject.skipOnboarding();
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}
