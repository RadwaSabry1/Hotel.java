
import java.util.UUID;

public class Booking {
    String id;
    String guestName;
    int roomNumber;
    String paymentStatus;

    Booking(String guestName, int roomNumber, String paymentStatus) {
        this.id = UUID.randomUUID().toString();
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.paymentStatus = paymentStatus;
    }

    public String toString() {
        return id + "," + guestName + "," + roomNumber + "," + paymentStatus;
    }

    public static Booking fromString(String line) {
        String[] parts = line.split(",");
        Booking b = new Booking(parts[1], Integer.parseInt(parts[2]), parts[3]);
        b.id = parts[0];
        return b;
    }
}
