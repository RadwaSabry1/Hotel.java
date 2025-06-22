
import java.io.*;
import java.util.*;

public class HotelSystem {
    static List<Room> rooms = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {
        loadRooms();
        loadBookings();

        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Hotel Reservation Menu ===");
            System.out.println("1. Search Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Bookings");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> searchRooms(input);
                case 2 -> bookRoom(input);
                case 3 -> cancelBooking(input);
                case 4 -> viewBookings();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice");
            }

        } while (choice != 0);

        saveRooms();
        saveBookings();
    }

    static void searchRooms(Scanner input) {
        System.out.print("Enter room category (Standard/Deluxe/Suite): ");
        String category = input.nextLine();
        boolean found = false;

        for (Room r : rooms) {
            if (r.getCategory().equalsIgnoreCase(category) && r.isAvailable()) {
                System.out.println("Room available: " + r.getRoomNumber());
                found = true;
            }
        }

        if (!found) System.out.println("No rooms found in that category.");
    }

    static void bookRoom(Scanner input) {
        System.out.print("Enter your name: ");
        String name = input.nextLine();
        System.out.print("Enter room number to book: ");
        int roomNum = input.nextInt();
        input.nextLine();

        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNum && r.isAvailable()) {
                r.setAvailable(false);
                Booking booking = new Booking(name, roomNum, "Paid");
                bookings.add(booking);
                System.out.println("✅ Room booked! Booking ID: " + booking.id);
                return;
            }
        }
        System.out.println("❌ Room not available or does not exist.");
    }

    static void cancelBooking(Scanner input) {
        System.out.print("Enter booking ID: ");
        String id = input.nextLine();

        Booking toRemove = null;
        for (Booking b : bookings) {
            if (b.id.equals(id)) {
                toRemove = b;
                break;
            }
        }

        if (toRemove != null) {
            bookings.remove(toRemove);
            for (Room r : rooms) {
                if (r.getRoomNumber() == toRemove.roomNumber) {
                    r.setAvailable(true);
                }
            }
            System.out.println("❎ Booking cancelled.");
        } else {
            System.out.println("⚠️ Booking ID not found.");
        }
    }

    static void viewBookings() {
        for (Booking b : bookings) {
            System.out.println("ID: " + b.id + ", Name: " + b.guestName + ", Room: " + b.roomNumber + ", Paid: " + b.paymentStatus);
        }
    }

    static void loadRooms() {
        try (BufferedReader br = new BufferedReader(new FileReader("rooms.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                rooms.add(Room.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("No rooms file found. Creating default rooms...");
            rooms.add(new Room(101, "Standard", true));
            rooms.add(new Room(102, "Deluxe", true));
            rooms.add(new Room(103, "Suite", true));
        }
    }

    static void loadBookings() {
        try (BufferedReader br = new BufferedReader(new FileReader("bookings.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                bookings.add(Booking.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("No bookings file found. Starting fresh.");
        }
    }

    static void saveRooms() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rooms.txt"))) {
            for (Room r : rooms) {
                bw.write(r.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving rooms.");
        }
    }

    static void saveBookings() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("bookings.txt"))) {
            for (Booking b : bookings) {
                bw.write(b.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings.");
        }
    }
}
