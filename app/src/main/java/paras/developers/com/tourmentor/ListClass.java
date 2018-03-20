package paras.developers.com.tourmentor;

/**
 * Created by Sunny Parihar on 20-03-2018.
 */

public class ListClass {
   String name;
    String Rating;
    String Ticket;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }

    public ListClass(String name, String rating, String ticket) {

        this.name = name;
        Rating = rating;
        Ticket = ticket;
    }
public ListClass(){

}
}
