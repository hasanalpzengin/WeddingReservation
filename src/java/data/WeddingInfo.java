
package data;

import java.util.HashMap;
import java.util.List;

public class WeddingInfo {
    private int wedding_id;
    private String bride_name, groom_name, date, location;

    public WeddingInfo(List list) {
        this.wedding_id = (int) list.get(0);
        this.bride_name = (String) list.get(1);
        this.groom_name = (String) list.get(2);
        this.date = (String) list.get(3);
        this.location = (String) list.get(4);
    }

    public int getWedding_id() {
        return wedding_id;
    }

    public String getBride_name() {
        return bride_name;
    }

    public String getGroom_name() {
        return groom_name;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public void setWedding_id(int wedding_id) {
        this.wedding_id = wedding_id;
    }

    public void setBride_name(String bride_name) {
        this.bride_name = bride_name;
    }

    public void setGroom_name(String groom_name) {
        this.groom_name = groom_name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    
}
