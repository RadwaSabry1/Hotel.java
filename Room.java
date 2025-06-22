
public class Room {
    private int roomNumber;
    private String category;
    private boolean isAvailable;

    public Room(int roomNumber, String category, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public String toString() {
        return roomNumber + "," + category + "," + isAvailable;
    }

    public static Room fromString(String data) {
        String[] parts = data.split(",");
        return new Room(Integer.parseInt(parts[0]), parts[1], Boolean.parseBoolean(parts[2]));
    }
}
