package paras.developers.com.tourmentor;

/**
 * Created by Sunny Parihar on 20-03-2018.
 */

public class ListClass {
   String place;
   String rating;
   String fare;
public ListClass(){

}

    public ListClass(String place, String rating, String fare) {
        this.place = place;
        this.rating = rating;
        this.fare = fare;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }
}
