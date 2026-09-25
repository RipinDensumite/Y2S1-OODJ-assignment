package com.mycompany.assignment;

import com.mycompany.assignment.classes.AdminStaff;
import com.mycompany.assignment.classes.HospitalAsset;
import com.mycompany.assignment.classes.User;
import com.mycompany.assignment.enums.AssetStatus;
import com.mycompany.assignment.enums.AssetType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class TestGroundCli {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        AdminStaff admin = new AdminStaff(
                "ADMIN001",
                "Main Admin",
                "admin@gmail.com",
                "0123456789",
                "admin123",
                true
        );

        int choice;

        do {

            System.out.println("\n=================================");
            System.out.println("       HMS TEST TERMINAL");
            System.out.println("=================================");

            System.out.println("\n--- USER ---");
            System.out.println("1. Create User");
            System.out.println("2. Get User");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");

            System.out.println("\n--- HOSPITAL ASSET ---");
            System.out.println("5. Create Hospital Asset");
            System.out.println("6. Get Hospital Asset");
            System.out.println("7. Update Hospital Asset");
            System.out.println("8. Delete Hospital Asset");
            System.out.println("9. Get All Hospital Assets");

            System.out.println("\n0. Exit");

            System.out.println("=================================");
            System.out.print("Enter choice: ");

            try {

                choice =
                        Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );

                choice = -1;
                continue;
            }

            switch (choice) {

                case 1:
                    createUser(scanner, admin);
                    break;

                case 2:
                    getUser(scanner, admin);
                    break;

                case 3:
                    updateUser(scanner, admin);
                    break;

                case 4:
                    deleteUser(scanner, admin);
                    break;

                case 5:
                    createHospitalAsset(scanner);
                    break;

                case 6:
                    getHospitalAssetTerminal(scanner);
                    break;

                case 7:
                    updateHospitalAsset(scanner);
                    break;

                case 8:
                    deleteHospitalAsset(scanner);
                    break;

                case 9:
                    getAllHospitalAssets();
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);

        scanner.close();
    }


    // ============================================================
    // USER CRUD
    // ============================================================

    public static void createUser(
            Scanner scanner,
            AdminStaff admin) {

        System.out.println("\n--- CREATE USER ---");

        System.out.print("User ID: ");
        String userId = scanner.nextLine();

        User existingUser =
                admin.getUser(userId);

        if (existingUser != null) {

            System.out.println(
                    "User ID already exists."
            );

            return;
        }

        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print(
                "Active (true/false): "
        );

        boolean active =
                Boolean.parseBoolean(
                        scanner.nextLine()
                );

        AdminStaff newUser =
                new AdminStaff(
                        userId,
                        fullName,
                        email,
                        phoneNumber,
                        password,
                        active
                );

        boolean success =
                admin.createUser(newUser);

        if (success) {

            System.out.println(
                    "User created successfully."
            );

        } else {

            System.out.println(
                    "Failed to create user."
            );
        }
    }


    public static void getUser(
            Scanner scanner,
            AdminStaff admin) {

        System.out.println("\n--- GET USER ---");

        System.out.print("Enter User ID: ");

        String userId =
                scanner.nextLine();

        User user =
                admin.getUser(userId);

        if (user == null) {

            System.out.println(
                    "User not found."
            );

            return;
        }

        displayUser(user);
    }


    public static void updateUser(
            Scanner scanner,
            AdminStaff admin) {

        System.out.println("\n--- UPDATE USER ---");

        System.out.print("Enter User ID: ");

        String userId =
                scanner.nextLine();

        User user =
                admin.getUser(userId);

        if (user == null) {

            System.out.println(
                    "User not found."
            );

            return;
        }

        System.out.println(
                "\nCurrent Information:"
        );

        displayUser(user);

        System.out.println(
                "\nEnter new information:"
        );

        System.out.print("Full Name: ");

        String fullName =
                scanner.nextLine();

        System.out.print("Email: ");

        String email =
                scanner.nextLine();

        System.out.print("Phone Number: ");

        String phoneNumber =
                scanner.nextLine();

        try {

            user.editProfile(
                    fullName,
                    email,
                    phoneNumber
            );

            boolean success =
                    admin.updateUser(user);

            if (success) {

                System.out.println(
                        "User updated successfully."
                );

            } else {

                System.out.println(
                        "Failed to update user."
                );
            }

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Invalid input: "
                    + e.getMessage()
            );
        }
    }


    public static void deleteUser(
            Scanner scanner,
            AdminStaff admin) {

        System.out.println("\n--- DELETE USER ---");

        System.out.print("Enter User ID: ");

        String userId =
                scanner.nextLine();

        User user =
                admin.getUser(userId);

        if (user == null) {

            System.out.println(
                    "User not found."
            );

            return;
        }

        System.out.println("\nUser found:");

        displayUser(user);

        System.out.print(
                "\nDelete this user? (yes/no): "
        );

        String confirmation =
                scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {

            System.out.println(
                    "Delete cancelled."
            );

            return;
        }

        boolean success =
                admin.deleteUser(userId);

        if (success) {

            System.out.println(
                    "User deleted successfully."
            );

        } else {

            System.out.println(
                    "Failed to delete user."
            );
        }
    }


    public static void displayUser(User user) {

        System.out.println(
                "--------------------------"
        );

        System.out.println(
                "User ID      : "
                + user.getUserId()
        );

        System.out.println(
                "Full Name    : "
                + user.getFullName()
        );

        System.out.println(
                "Email        : "
                + user.getEmail()
        );

        System.out.println(
                "Phone Number : "
                + user.getPhoneNumber()
        );

        System.out.println(
                "Role         : "
                + user.getRole()
        );

        System.out.println(
                "Active       : "
                + user.isActive()
        );

        System.out.println(
                "--------------------------"
        );
    }


    // ============================================================
    // HOSPITAL ASSET CRUD
    // ============================================================

    // CREATE
    public static void createHospitalAsset(
            Scanner scanner) {

        System.out.println(
                "\n--- CREATE HOSPITAL ASSET ---"
        );

        System.out.print("Asset ID: ");

        String assetId =
                scanner.nextLine();

        try {

            HospitalAsset existingAsset =
                    findHospitalAsset(assetId);

            if (existingAsset != null) {

                System.out.println(
                        "Asset ID already exists."
                );

                return;
            }

            System.out.print("Asset Name: ");

            String assetName =
                    scanner.nextLine();


            System.out.println(
                    "Available Asset Types:"
            );

            System.out.println(
                    Arrays.toString(
                            AssetType.values()
                    )
            );

            System.out.print("Asset Type: ");

            AssetType assetType =
                    AssetType.valueOf(
                            scanner.nextLine()
                                    .trim()
                                    .toUpperCase()
                    );


            System.out.println(
                    "Available Asset Status:"
            );

            System.out.println(
                    Arrays.toString(
                            AssetStatus.values()
                    )
            );

            System.out.print("Status: ");

            AssetStatus status =
                    AssetStatus.valueOf(
                            scanner.nextLine()
                                    .trim()
                                    .toUpperCase()
                    );


            System.out.print(
                    "Department ID "
                    + "(leave blank if none): "
            );

            String departmentId =
                    scanner.nextLine().trim();

            if (departmentId.isEmpty()) {
                departmentId = null;
            }


            HospitalAsset asset =
                    new HospitalAsset(
                            assetId,
                            assetName,
                            assetType,
                            status,
                            departmentId
                    );


            asset.addHospitalAsset();


            System.out.println(
                    "Hospital asset created successfully."
            );


        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Invalid value: "
                    + e.getMessage()
            );

        } catch (IOException e) {

            System.out.println(
                    "File error: "
                    + e.getMessage()
            );
        }
    }


    // READ ONE
    public static void getHospitalAssetTerminal(
            Scanner scanner) {

        System.out.println(
                "\n--- GET HOSPITAL ASSET ---"
        );

        System.out.print("Enter Asset ID: ");

        String assetId =
                scanner.nextLine();

        try {

            HospitalAsset asset =
                    findHospitalAsset(assetId);

            if (asset == null) {

                System.out.println(
                        "Hospital asset not found."
                );

                return;
            }

            displayHospitalAsset(asset);

        } catch (IOException e) {

            System.out.println(
                    "File error: "
                    + e.getMessage()
            );
        }
    }


    // UPDATE
    public static void updateHospitalAsset(
            Scanner scanner) {

        System.out.println(
                "\n--- UPDATE HOSPITAL ASSET ---"
        );

        System.out.print("Enter Asset ID: ");

        String assetId =
                scanner.nextLine();

        try {

            HospitalAsset asset =
                    findHospitalAsset(assetId);

            if (asset == null) {

                System.out.println(
                        "Hospital asset not found."
                );

                return;
            }


            System.out.println(
                    "\nCurrent Information:"
            );

            displayHospitalAsset(asset);


            System.out.print(
                    "\nNew Asset Name: "
            );

            String newAssetName =
                    scanner.nextLine();


            System.out.println(
                    "Available Status:"
            );

            System.out.println(
                    Arrays.toString(
                            AssetStatus.values()
                    )
            );

            System.out.print(
                    "New Status: "
            );

            AssetStatus newStatus =
                    AssetStatus.valueOf(
                            scanner.nextLine()
                                    .trim()
                                    .toUpperCase()
                    );


            asset.updateDetails(
                    newAssetName,
                    newStatus
            );


            System.out.println(
                    "Hospital asset updated successfully."
            );


        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Invalid value: "
                    + e.getMessage()
            );

        } catch (IOException e) {

            System.out.println(
                    "File error: "
                    + e.getMessage()
            );
        }
    }


    // DELETE
    public static void deleteHospitalAsset(
            Scanner scanner) {

        System.out.println(
                "\n--- DELETE HOSPITAL ASSET ---"
        );

        System.out.print("Enter Asset ID: ");

        String assetId =
                scanner.nextLine();

        try {

            HospitalAsset asset =
                    findHospitalAsset(assetId);

            if (asset == null) {

                System.out.println(
                        "Hospital asset not found."
                );

                return;
            }


            System.out.println(
                    "\nAsset found:"
            );

            displayHospitalAsset(asset);


            System.out.print(
                    "\nDelete this asset? (yes/no): "
            );

            String confirmation =
                    scanner.nextLine();


            if (!confirmation.equalsIgnoreCase("yes")) {

                System.out.println(
                        "Delete cancelled."
                );

                return;
            }


            asset.deleteHospitalAsset();


            System.out.println(
                    "Hospital asset deleted successfully."
            );


        } catch (IOException e) {

            System.out.println(
                    "File error: "
                    + e.getMessage()
            );
        }
    }


    // READ ALL
    public static void getAllHospitalAssets() {

        System.out.println(
                "\n--- ALL HOSPITAL ASSETS ---"
        );

        File file =
                new File("HospitalAsset.txt");

        if (!file.exists()) {

            System.out.println(
                    "No hospital asset file found."
            );

            return;
        }

        try {

            FileReader fr =
                    new FileReader(file);

            BufferedReader br =
                    new BufferedReader(fr);

            String line;

            boolean found = false;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data =
                        line.split(",", -1);

                HospitalAsset asset =
                        new HospitalAsset(
                                data[0],
                                data[1],
                                AssetType.valueOf(data[2]),
                                AssetStatus.valueOf(data[3]),
                                data[4].isEmpty()
                                        ? null
                                        : data[4]
                        );

                displayHospitalAsset(asset);

                found = true;
            }

            br.close();
            fr.close();


            if (!found) {

                System.out.println(
                        "No hospital assets found."
                );
            }


        } catch (IOException e) {

            System.out.println(
                    "File error: "
                    + e.getMessage()
            );
        }
    }


    // ============================================================
    // HOSPITAL ASSET HELPER
    // ============================================================

    public static HospitalAsset findHospitalAsset(
            String assetId) throws IOException {

        File file =
                new File("HospitalAsset.txt");

        if (!file.exists()) {
            return null;
        }

        FileReader fr =
                new FileReader(file);

        BufferedReader br =
                new BufferedReader(fr);

        String line;

        while ((line = br.readLine()) != null) {

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data =
                    line.split(",", -1);

            if (data[0].equals(assetId)) {

                String id =
                        data[0];

                String assetName =
                        data[1];

                AssetType assetType =
                        AssetType.valueOf(
                                data[2]
                        );

                AssetStatus status =
                        AssetStatus.valueOf(
                                data[3]
                        );

                String departmentId =
                        data[4].isEmpty()
                                ? null
                                : data[4];


                HospitalAsset asset =
                        new HospitalAsset(
                                id,
                                assetName,
                                assetType,
                                status,
                                departmentId
                        );


                br.close();
                fr.close();

                return asset;
            }
        }

        br.close();
        fr.close();

        return null;
    }


    public static void displayHospitalAsset(
            HospitalAsset asset) {

        System.out.println(
                "--------------------------------"
        );

        System.out.println(
                "Asset ID      : "
                + asset.getAssetId()
        );

        System.out.println(
                "Asset Name    : "
                + asset.getAssetName()
        );

        System.out.println(
                "Asset Type    : "
                + asset.getAssetType()
        );

        System.out.println(
                "Status        : "
                + asset.getStatus()
        );

        System.out.println(
                "Department ID : "
                + (
                    asset.getDepartmentId() == null
                    ? "Not Allocated"
                    : asset.getDepartmentId()
                )
        );

        System.out.println(
                "--------------------------------"
        );
    }
}