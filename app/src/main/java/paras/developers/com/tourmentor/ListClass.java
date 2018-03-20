package paras.developers.com.tourmentor;

/**
 * Created by Sunny Parihar on 20-03-2018.
 */

public class ListClass {
    int rating;
    String place;
    int fare;

    public ListClass(int rating, String place, int fare) {
        this.rating = rating;
        this.place = place;
        this.fare = fare;
    }
public ListClass(){

}
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }
}
