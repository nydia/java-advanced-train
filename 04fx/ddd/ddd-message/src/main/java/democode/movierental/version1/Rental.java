package democode.movierental.version1;

/**
 * @author nydia
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

    /**
     * 默认租聘积一分，如果是新片且租聘大于1天，在加一分
     *
     * @return
     */
    public int getPoint() {
        return this.movie.getPoint(this.daysRented);
    }

    /**
     * 获取费用
     *
     * @return
     */
    public double getCharge() {
        return this.movie.getCharge(this.daysRented);
    }
}
