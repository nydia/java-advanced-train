package democode.movierental.version0;

/**
 * @author zhengyin
 * Created on 2021/4/12 12:26 下午
 */
public class Rental {
    /**
     * 租的电影
     */
    private Movie movie;
    /**
     * 已租天数
     */
    private int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public void setDaysRented(int daysRented) {
        this.daysRented = daysRented;
    }
}
