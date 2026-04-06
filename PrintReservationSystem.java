import java.util.*;

class Request {
    String file, date, type, status = "NOT APPROVED YET", reason = "";

    Request(String f, String d, String t) {
        file = f;
        date = d;
        type = t;
    }
}

class Client {
    String username, password;
    ArrayList<Request> list = new ArrayList<>();

    Client(String u, String p) {
        username = u;
        password = p;
    }
}

public class PrintReservationSystem {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Client> clients = new ArrayList<>();

    static final String ADMIN_USER = "admin";
    static final String ADMIN_PASS = "testpass";

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n=== WELCOME TO FOUR ANGEL'S COPY CENTER ===");
            System.out.println("[1] Admin");
            System.out.println("[2] Client");
            System.out.println("[3] Exit");
            System.out.print(">> ");

            String c = sc.nextLine();

            if (c.equals("1")) adminEntry();
            else if (c.equals("2")) clientEntry();
            else if (c.equals("3")) System.exit(0);
            else System.out.println("INVALID CHOICE");
        }
    }

    // ================= ADMIN =================
    static void adminEntry() {

    System.out.println("\n[1] Sign in");
    System.out.println("[2] Back");
    System.out.print(">> ");

    String c = sc.nextLine();

    if (c.equals("1")) {

        System.out.print("Username: ");
        String u = sc.nextLine();

        System.out.print("Password: ");
        String p = sc.nextLine();

        if (u.equals(ADMIN_USER) && p.equals(ADMIN_PASS)) {
            adminMenu(); // after sign out, goes back to MAIN
        } else {
            System.out.println("Invalid admin credentials.");
        }

    } else if (c.equals("2")) {
        return;
    } else {
        System.out.println("INVALID CHOICE");
    }
}

    static void adminMenu() {
        while (true) {
            System.out.println("\n=== ADMIN ===");
            System.out.println("[1] View all Request");
            System.out.println("[2] Manage Request");
            System.out.println("[3] Sign out");
            System.out.print(">> ");

            String c = sc.nextLine();

            if (c.equals("1")) {

                boolean found = false;
                for (Client cl : clients) {
                    if (!cl.list.isEmpty()) {
                        found = true;
                        System.out.println("\nClient: " + cl.username);

                        for (int i = 0; i < cl.list.size(); i++) {
                            Request r = cl.list.get(i);
                            System.out.println("\nFile: " + r.file);
                            System.out.println("Date: " + r.date);
                            System.out.println("Type: " + r.type);
                            System.out.println("Status: " + r.status);

                            if (r.status.equals("REJECTED"))
                                System.out.println("Reason: " + r.reason);
                        }
                    }
                }

                if (!found) System.out.println("No requests.");

            } else if (c.equals("2")) {
                manage();
            } else if (c.equals("3")) {
                return;
            } else {
                System.out.println("INVALID CHOICE");
            }
        }
    }

    static void manage() {

        ArrayList<Request> all = new ArrayList<>();
        ArrayList<Client> owners = new ArrayList<>();

        for (Client c : clients) {
            for (Request r : c.list) {
                all.add(r);
                owners.add(c);
            }
        }

        if (all.isEmpty()) {
            System.out.println("No requests.");
            return;
        }

        for (int i = 0; i < all.size(); i++) {

            Request r = all.get(i);
            Client c = owners.get(i);

            System.out.println("\nClient: " + c.username);
            System.out.println("File: " + r.file);
            System.out.println("Date: " + r.date);
            System.out.println("Type: " + r.type);
            System.out.println("Status: " + r.status);

            System.out.print("[1] Approved [2] Reject [3] Delete [4] Back >> ");
            String ch = sc.nextLine();

            if (ch.equals("1")) {
                r.status = "APPROVED";

            } else if (ch.equals("2")) {
                r.status = "REJECTED";
                System.out.print("INPUT REASON: ");
                r.reason = sc.nextLine();

            } else if (ch.equals("3")) {

                System.out.print("Are you sure you want to delete request? (Y/N): ");
                String conf = sc.nextLine();

                if (conf.equalsIgnoreCase("Y")) {

                    System.out.println("Pick a queue:");

                    for (int j = 0; j < all.size(); j++) {
                        System.out.println("[" + (j + 1) + "] " + all.get(j).file);
                    }

                    try {
                        int pick = Integer.parseInt(sc.nextLine()) - 1;

                        if (pick >= 0 && pick < all.size()) {
                            owners.get(pick).list.remove(all.get(pick));
                            System.out.println("Successfully Deleted Request");
                        } else {
                            System.out.println("INVALID CHOICE");
                        }

                    } catch (Exception e) {
                        System.out.println("INVALID CHOICE");
                    }
                }

            } else if (ch.equals("4")) {
                return;
            } else {
                System.out.println("INVALID CHOICE");
            }
        }
    }

    // ================= CLIENT =================
    static void clientEntry() {

    System.out.println("\n[1] Sign in");
    System.out.println("[2] Sign up");
    System.out.println("[3] Back");
    System.out.print(">> ");

    String c = sc.nextLine();

    if (c.equals("1")) login();
    else if (c.equals("2")) signup();
    else if (c.equals("3")) return;
    else System.out.println("INVALID CHOICE");
}

    static void login() {
        System.out.print("Enter Username: ");
        String u = sc.nextLine();

        for (Client c : clients) {
            if (c.username.equals(u)) {

                System.out.print("Enter password: ");
                String p = sc.nextLine();

                if (c.password.equals(p)) {
                    clientMenu(c);
                } else {
                    System.out.println("Password Incorrect.Try Again");
                }
                return;
            }
        }

        System.out.println("Username does not exist");
    }

    static void signup() {
        System.out.print("Enter Username: ");
        String u = sc.nextLine();

        for (Client c : clients) {
            if (c.username.equals(u)) {
                System.out.println("Username already exists.");
                return;
            }
        }

        System.out.print("Enter password: ");
        String p = sc.nextLine();

        System.out.print("Confirm password: ");
        String cp = sc.nextLine();

        if (!p.equals(cp)) {
            System.out.println("INVALID CHOICE");
            return;
        }

        clients.add(new Client(u, p));
        System.out.println("Successfully Saved Account! Yipieee!");
    }

    static void clientMenu(Client c) {
        while (true) {
            System.out.println("\nWelcome " + c.username + "!");
            System.out.println("[1] Add Request");
            System.out.println("[2] View Request");
            System.out.println("[3] Delete Request");
            System.out.println("[4] Sign out");
            System.out.print(">> ");

            String ch = sc.nextLine();

            if (ch.equals("1")) {

                System.out.print("Enter file name: ");
                String f = sc.nextLine();

                System.out.print("Enter Date (MM/DD/YYYY): ");
                String d = sc.nextLine();

                System.out.print("Print type [1] Colored [2] B/W: ");
                String t = sc.nextLine();

                if (!t.equals("1") && !t.equals("2")) {
                    System.out.println("INVALID CHOICE");
                    continue;
                }

                String type = t.equals("1") ? "COLORED" : "BLACK & WHITE";

                c.list.add(new Request(f, d, type));
                System.out.println("Request List Successfully Added to list");

            } else if (ch.equals("2")) {

                if (c.list.isEmpty()) {
                    System.out.println("No requests.");
                } else {
                    for (int i = 0; i < c.list.size(); i++) {
                        Request r = c.list.get(i);

                        System.out.println("\nFile: " + r.file);
                        System.out.println("Date: " + r.date);
                        System.out.println("Type: " + r.type);
                        System.out.println("Status: " + r.status);

                        if (r.status.equals("REJECTED"))
                            System.out.println("Reason: " + r.reason);
                    }
                }

            } else if (ch.equals("3")) {

                if (c.list.isEmpty()) {
                    System.out.println("No requests.");
                    continue;
                }

                for (int i = 0; i < c.list.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + c.list.get(i).file);
                }

                System.out.print("Enter request number to delete: ");

                try {
                    int i = Integer.parseInt(sc.nextLine()) - 1;

                    if (i >= 0 && i < c.list.size()) {

                        System.out.print("Are you sure you want to delete request? (Y/N): ");
                        String conf = sc.nextLine();

                        if (conf.equalsIgnoreCase("Y")) {
                            c.list.remove(i);
                            System.out.println("Successfully Deleted Request");
                        }

                    } else {
                        System.out.println("INVALID CHOICE");
                    }

                } catch (Exception e) {
                    System.out.println("INVALID CHOICE");
                }

            } else if (ch.equals("4")) {
                return;
            } else {
                System.out.println("INVALID CHOICE");
            }
        }
    }
}