package democode.movierental.version1;

/**
 * @author zhengyin
 * Created on 2021/4/15 4:17 下午
 */
public class Main {
    public static void main(String[] args) {
        Movie movie1 = new Movie("儿童片", Movie.CHILDRENS);
        Movie movie2 = new Movie("普通片", Movie.REGULAR);
        Movie movie3 = new Movie("新片", Movie.NEW_RELEASE);
        Customer customer = new Customer("张三");
        customer.addRental(new Rental(movie1, 1));
        customer.addRental(new Rental(movie2, 3));
        customer.addRental(new Rental(movie3, 5));
        System.out.println(customer.statement());
    }
}