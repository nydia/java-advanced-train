package democode.movierental.version0;

/**
 * @author nydia
 * Created on 2021/4/12 12:16 下午
 */
public class Movie {
    public static final int CHILDRENS = 2;
    public static final int REGULAR = 0;
    public static final int NEW_RELEASE = 1;
    private String title;
    private Integer priceCode;

    public Movie(String title, Integer priceCode) {
        this.title = title;
        this.priceCode = priceCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(Integer priceCode) {
        this.priceCode = priceCode;
    }
}
