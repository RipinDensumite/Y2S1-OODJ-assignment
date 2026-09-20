/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.assignment.gui;

import com.mycompany.assignment.classes.AdminStaff;
import com.mycompany.assignment.classes.CheckUpType;
import com.mycompany.assignment.classes.Department;
import com.mycompany.assignment.classes.Doctor;
import com.mycompany.assignment.classes.HospitalAsset;
import com.mycompany.assignment.classes.InsuranceNetwork;
import com.mycompany.assignment.classes.MedicalManager;
import com.mycompany.assignment.classes.MedicalServiceRequest;
import com.mycompany.assignment.classes.Patient;
import com.mycompany.assignment.classes.User;
import com.mycompany.assignment.enums.AssetStatus;
import com.mycompany.assignment.enums.AssetType;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author arifi
 */
public class AdminPage extends javax.swing.JFrame {

    private AdminStaff adminStaff;
    private String editingUserId = null;
    private String editingInsuranceId = null;
    private String editingCheckUpId = null;
    private String editingHospitalAssetId = null;
    private String editingHospitalAssetDepartmentId = null;

    /**
     * Creates new form AdminPage
     *
     * @param adminStaff
     */
    public AdminPage(AdminStaff adminStaff) {
        initComponents();

        this.adminStaff = adminStaff;
        setTitle("Admin Dashboard");

        initialCreateUserDialog();

        lbWelcomeSubtitle.setText("Signed in as " + adminStaff.getFullName() + " | Admin Staff");

        setupTables();
        setupTabChangeListener();

        loadUsers();
        loadInsuranceNetwork();
        loadCheckUpTypes();
        loadHospitalAssets();
        loadDoctorAssignmentData();
    }

    private void setupTabChangeListener() {
        tabAdminDashboard.addChangeListener(e -> {
            java.awt.Component selectedTab = tabAdminDashboard.getSelectedComponent();

            if (selectedTab == UsersPanel) {
                loadUsers();
            } else if (selectedTab == DoctorAssignmentPanel) {
                loadDoctorAssignmentData();
            } else if (selectedTab == HospitalAssetsPanel) {
                loadHospitalAssets();
            } else if (selectedTab == InsuranceNetworkPanel) {
                loadInsuranceNetwork();
            } else if (selectedTab == CheckUpTypesPanel) {
                loadCheckUpTypes();
            } else if (selectedTab == MedicalServiceRequestsPanel) {
                loadMedicalServiceRequests();
            }
        });
    }

    private void setupTables() {
        tbUsers.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"User ID", "Full Name", "Email", "Phone Number", "Role", "Active"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tbInsuranceNetwork.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Insurance ID", "Provider", "Coverage %", "Accepted"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tbDoctorAssignment.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Doctor ID", "Doctor", "Manager ID", "Medical Manager"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tbCheckUpTypes.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Name", "Base Rate (RM)", "Duration (min)", "Description", "Active"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tbHospitalAssets.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Asset ID", "Name", "Type", "Status", "Department"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tbMedicalServiceRequest.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Service ID", "Type", "Doctor", "Consultation", "Reason", "Status", "Asset", "Result", "Fee"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    private void loadInsuranceNetwork() {
        DefaultTableModel model = (DefaultTableModel) tbInsuranceNetwork.getModel();

        model.setRowCount(0);

        ArrayList<InsuranceNetwork> insuranceNetworks = adminStaff.getInsuranceNetworks();

        for (InsuranceNetwork insurance : insuranceNetworks) {
            model.addRow(new Object[]{
                insurance.getInsuranceId(),
                insurance.getProviderName(),
                insurance.getCoverageRate(),
                insurance.isAccepted()
            });
        }
    }

    private void loadUsers() {
        DefaultTableModel model = (DefaultTableModel) tbUsers.getModel();

        model.setRowCount(0);

        ArrayList<User> users = adminStaff.getUsers();

        for (User user : users) {
            model.addRow(new Object[]{
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole(),
                user.isActive()
            });
        }
    }

    private void loadCheckUpTypes() {
        DefaultTableModel model = (DefaultTableModel) tbCheckUpTypes.getModel();
        model.setRowCount(0);
        ArrayList<CheckUpType> checkUpTypes = adminStaff.getCheckUpTypes();

        for (CheckUpType checkUp : checkUpTypes) {
            model.addRow(new Object[]{
                checkUp.getCheckUpTypeId(),
                checkUp.getName(),
                checkUp.getBaseRate(),
                checkUp.getDuration(),
                checkUp.getDescription(),
                checkUp.isActive()
            });
        }
    }

    private void loadHospitalAssets() {
        DefaultTableModel model = (DefaultTableModel) tbHospitalAssets.getModel();
        model.setRowCount(0);
        ArrayList<HospitalAsset> hospitalAssets = adminStaff.getHospitalAssets();

        for (HospitalAsset asset : hospitalAssets) {
            model.addRow(new Object[]{
                asset.getAssetId(),
                asset.getAssetName(),
                asset.getAssetType(),
                asset.getStatus(),
                asset.getDepartmentId() == null
                ? "Not Allocated"
                : asset.getDepartmentId()
            });
        }
    }

    private void loadDoctorAssignmentData() {
        loadDoctorAssignmentLists();
        loadDoctorAssignments();
    }

    private void loadDoctorAssignmentLists() {
        cbDoctorListDoctorAssignment.removeAllItems();
        cbMedicalManagerListDoctorAssignment.removeAllItems();

        ArrayList<Doctor> doctors = adminStaff.getDoctors();
        for (Doctor doctor : doctors) {
            if (doctor.isActive()) {
                cbDoctorListDoctorAssignment.addItem(
                        doctor.getUserId()
                        + " - "
                        + doctor.getFullName()
                );
            }
        }

        ArrayList<MedicalManager> managers = adminStaff.getMedicalManagers();

        for (MedicalManager manager : managers) {
            if (manager.isActive()) {
                cbMedicalManagerListDoctorAssignment.addItem(
                        manager.getUserId()
                        + " - "
                        + manager.getFullName()
                );
            }
        }
    }

    private void loadDoctorAssignments() {
        DefaultTableModel model = (DefaultTableModel) tbDoctorAssignment.getModel();
        model.setRowCount(0);
        ArrayList<String[]> assignments = adminStaff.getDoctorAssignments();

        for (String[] assignment : assignments) {
            String doctorId = assignment[0];
            String managerId = assignment[1];

            User doctor = adminStaff.getUser(doctorId);
            User manager = adminStaff.getUser(managerId);

            String doctorName = doctor == null ? "Unknown" : doctor.getFullName();
            String managerName = manager == null ? "Unknown" : manager.getFullName();

            model.addRow(new Object[]{
                doctorId,
                doctorName,
                managerId,
                managerName
            });
        }
    }

    private void loadMedicalServiceRequests() {

        DefaultTableModel model
                = (DefaultTableModel) tbMedicalServiceRequest.getModel();

        model.setRowCount(0);

        ArrayList<MedicalServiceRequest> requests
                = adminStaff.getMedicalServiceRequests();

        for (MedicalServiceRequest request : requests) {

            User doctor
                    = adminStaff.getUser(
                            request.getDoctorId()
                    );

            String doctorName
                    = doctor == null
                            ? request.getDoctorId()
                            : doctor.getFullName();

            model.addRow(new Object[]{
                request.getRequestId(),
                request.getRequestType(),
                doctorName,
                request.getConsultationId(),
                request.getReason(),
                request.getStatus(),
                request.getAssetId() == null
                ? "Not Assigned"
                : request.getAssetId(),
                request.getResultDetails() == null
                ? "-"
                : request.getResultDetails(),
                request.getServiceFee()
            });
        }
    }

    private void initialCreateUserDialog() {
        editingUserId = null;

        cbRole.removeAllItems();
        cbRole.addItem("ADMIN");
        cbRole.addItem("MEDICAL_MANAGER");
        cbRole.addItem("DOCTOR");
        cbRole.addItem("PATIENT");

        txtFullName.setText("");
        txtEmail.setText("");
        txtPhoneNumber.setText("");
        txtPassword.setText("");

        cbRole.setSelectedIndex(0);
        cbActive.setSelected(true);

        btnCreateUser.setText("Create");
        CreateUserDialog.setTitle("Create User");
    }

    private void initialInsuranceDialog() {
        editingInsuranceId = null;

        txtProvider.setText("");
        txtCoverage.setText("");
        cbAccepted.setSelected(true);

        btnAddInsuranceNetwork.setText("Add");
        CreateInsuranceNetworkDialog.setTitle("Add Insurance Network");
    }

    private void initialCheckUpDialog() {
        editingCheckUpId = null;

        txtNameCheckUp.setText("");
        txtDescriptionCheckUp.setText("");
        txtBaseRateCheckUp.setText("");
        txtDurationCheckUp.setText("");

        cdActiveCheckUp.setSelected(true);

        btnCreateCheckUp.setText("Create");
        CreateCheckUpDialog.setTitle("Create Check-Up Type");
    }

    private void initialHospitalAssetsDialog() {
        editingHospitalAssetId = null;
        editingHospitalAssetDepartmentId = null;

        txtNameHospitalAssets.setText("");

        cbTypeHospitalAssets.removeAllItems();

        for (AssetType type : AssetType.values()) {
            cbTypeHospitalAssets.addItem(type.toString());
        }

        cbStatusHospitalAssets.removeAllItems();

        for (AssetStatus status : AssetStatus.values()) {
            cbStatusHospitalAssets.addItem(status.toString());
        }

        cbTypeHospitalAssets.setSelectedIndex(0);
        cbStatusHospitalAssets.setSelectedIndex(0);

        btnCreateHospitalAssets.setText("Create");
        CreateHospitalAssetsDialog.setTitle(
                "Create Hospital Asset"
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CreateUserDialog = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        txtFullName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPhoneNumber = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        cbRole = new javax.swing.JComboBox<>();
        cbActive = new javax.swing.JCheckBox();
        btnCreateUser = new javax.swing.JButton();
        btnCancelCreateUser = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        CreateInsuranceNetworkDialog = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        btnAddInsuranceNetwork = new javax.swing.JButton();
        btnCancelInsuranceNetwork = new javax.swing.JButton();
        txtProvider = new javax.swing.JTextField();
        txtCoverage = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbAccepted = new javax.swing.JCheckBox();
        CreateCheckUpDialog = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        btnCreateCheckUp = new javax.swing.JButton();
        btnCancelCheckUp = new javax.swing.JButton();
        txtNameCheckUp = new javax.swing.JTextField();
        txtDescriptionCheckUp = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cdActiveCheckUp = new javax.swing.JCheckBox();
        txtBaseRateCheckUp = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtDurationCheckUp = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        CreateHospitalAssetsDialog = new javax.swing.JDialog();
        jPanel11 = new javax.swing.JPanel();
        btnCreateHospitalAssets = new javax.swing.JButton();
        btnCancelHospitalAssets = new javax.swing.JButton();
        txtNameHospitalAssets = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cbTypeHospitalAssets = new javax.swing.JComboBox<>();
        cbStatusHospitalAssets = new javax.swing.JComboBox<>();
        MainPanel = new javax.swing.JPanel();
        tabAdminDashboard = new javax.swing.JTabbedPane();
        UsersPanel = new javax.swing.JPanel();
        btnRefreshUsers = new javax.swing.JButton();
        btnDeleteUser = new javax.swing.JButton();
        btnAddUser = new javax.swing.JButton();
        btnEditUser = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbUsers = new javax.swing.JTable();
        DoctorAssignmentPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbDoctorAssignment = new javax.swing.JTable();
        cbDoctorListDoctorAssignment = new javax.swing.JComboBox<>();
        cbMedicalManagerListDoctorAssignment = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnAssignDoctorDoctorAssignment = new javax.swing.JButton();
        btnUnassignDoctorDoctorAssignment = new javax.swing.JButton();
        HospitalAssetsPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbHospitalAssets = new javax.swing.JTable();
        btnAddAssetHospitalAssets = new javax.swing.JButton();
        btnEditAssetHospitalAssets = new javax.swing.JButton();
        btnAllocateToDepartmentHospitalAssets = new javax.swing.JButton();
        btnRefreshHospitalAssets = new javax.swing.JButton();
        CheckUpTypesPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbCheckUpTypes = new javax.swing.JTable();
        btnAddCheckUp = new javax.swing.JButton();
        btnEditCheckUp = new javax.swing.JButton();
        btnCheckUpRefresh = new javax.swing.JButton();
        InsuranceNetworkPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbInsuranceNetwork = new javax.swing.JTable();
        btnAddInsurance = new javax.swing.JButton();
        btnEditInsurance = new javax.swing.JButton();
        MedicalServiceRequestsPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbMedicalServiceRequest = new javax.swing.JTable();
        btnAssignAssetServiceRequest = new javax.swing.JButton();
        btnRejectServiceRequest = new javax.swing.JButton();
        btnRefreshServiceRequest = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lbWelcomeTitle = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        lbWelcomeSubtitle = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        cbRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbActive.setText("active");

        btnCreateUser.setText("Create");
        btnCreateUser.addActionListener(this::btnCreateUserActionPerformed);

        btnCancelCreateUser.setText("Cancel");
        btnCancelCreateUser.addActionListener(this::btnCancelCreateUserActionPerformed);

        jLabel2.setText("Full Name");

        jLabel3.setText("Email");

        jLabel4.setText("Phone Number");

        jLabel5.setText("Password");

        jLabel6.setText("Role");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtFullName)
                            .addComponent(txtEmail)
                            .addComponent(txtPhoneNumber)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(cbRole, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnCreateUser)
                                .addGap(39, 39, 39)
                                .addComponent(btnCancelCreateUser))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(cbActive)))))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addComponent(cbActive)
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreateUser)
                    .addComponent(btnCancelCreateUser))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout CreateUserDialogLayout = new javax.swing.GroupLayout(CreateUserDialog.getContentPane());
        CreateUserDialog.getContentPane().setLayout(CreateUserDialogLayout);
        CreateUserDialogLayout.setHorizontalGroup(
            CreateUserDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CreateUserDialogLayout.setVerticalGroup(
            CreateUserDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnAddInsuranceNetwork.setText("Add");
        btnAddInsuranceNetwork.addActionListener(this::btnAddInsuranceNetworkActionPerformed);

        btnCancelInsuranceNetwork.setText("Cancel");
        btnCancelInsuranceNetwork.addActionListener(this::btnCancelInsuranceNetworkActionPerformed);

        jLabel7.setText("Provider:");

        jLabel8.setText("Coverage %:");

        cbAccepted.setText("Accepted");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddInsuranceNetwork)
                .addGap(112, 112, 112)
                .addComponent(btnCancelInsuranceNetwork)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(31, 31, 31)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbAccepted)
                    .addComponent(txtCoverage, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(121, 121, 121))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProvider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCoverage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(38, 38, 38)
                .addComponent(cbAccepted)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelInsuranceNetwork)
                    .addComponent(btnAddInsuranceNetwork))
                .addGap(82, 82, 82))
        );

        javax.swing.GroupLayout CreateInsuranceNetworkDialogLayout = new javax.swing.GroupLayout(CreateInsuranceNetworkDialog.getContentPane());
        CreateInsuranceNetworkDialog.getContentPane().setLayout(CreateInsuranceNetworkDialogLayout);
        CreateInsuranceNetworkDialogLayout.setHorizontalGroup(
            CreateInsuranceNetworkDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CreateInsuranceNetworkDialogLayout.setVerticalGroup(
            CreateInsuranceNetworkDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnCreateCheckUp.setText("Create");
        btnCreateCheckUp.addActionListener(this::btnCreateCheckUpActionPerformed);

        btnCancelCheckUp.setText("Cancel");
        btnCancelCheckUp.addActionListener(this::btnCancelCheckUpActionPerformed);

        jLabel9.setText("Name:");

        jLabel10.setText("Description:");

        cdActiveCheckUp.setText("active");

        jLabel11.setText("Base Rate (RM):");

        jLabel12.setText("Duration (min):");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCreateCheckUp)
                .addGap(112, 112, 112)
                .addComponent(btnCancelCheckUp)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(31, 31, 31)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cdActiveCheckUp)
                    .addComponent(txtDescriptionCheckUp, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(txtNameCheckUp, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(txtBaseRateCheckUp)
                    .addComponent(txtDurationCheckUp))
                .addGap(121, 121, 121))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNameCheckUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescriptionCheckUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBaseRateCheckUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDurationCheckUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addComponent(cdActiveCheckUp)
                .addGap(35, 35, 35)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelCheckUp)
                    .addComponent(btnCreateCheckUp))
                .addGap(82, 82, 82))
        );

        javax.swing.GroupLayout CreateCheckUpDialogLayout = new javax.swing.GroupLayout(CreateCheckUpDialog.getContentPane());
        CreateCheckUpDialog.getContentPane().setLayout(CreateCheckUpDialogLayout);
        CreateCheckUpDialogLayout.setHorizontalGroup(
            CreateCheckUpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CreateCheckUpDialogLayout.setVerticalGroup(
            CreateCheckUpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnCreateHospitalAssets.setText("Create");
        btnCreateHospitalAssets.addActionListener(this::btnCreateHospitalAssetsActionPerformed);

        btnCancelHospitalAssets.setText("Cancel");
        btnCancelHospitalAssets.addActionListener(this::btnCancelHospitalAssetsActionPerformed);

        jLabel13.setText("Name:");

        jLabel14.setText("Type:");

        jLabel15.setText("Status:");

        cbTypeHospitalAssets.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbStatusHospitalAssets.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCreateHospitalAssets)
                .addGap(112, 112, 112)
                .addComponent(btnCancelHospitalAssets)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(121, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(31, 31, 31)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNameHospitalAssets, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(cbStatusHospitalAssets, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbTypeHospitalAssets, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(121, 121, 121))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNameHospitalAssets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cbTypeHospitalAssets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cbStatusHospitalAssets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(107, 107, 107)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelHospitalAssets)
                    .addComponent(btnCreateHospitalAssets))
                .addGap(82, 82, 82))
        );

        javax.swing.GroupLayout CreateHospitalAssetsDialogLayout = new javax.swing.GroupLayout(CreateHospitalAssetsDialog.getContentPane());
        CreateHospitalAssetsDialog.getContentPane().setLayout(CreateHospitalAssetsDialogLayout);
        CreateHospitalAssetsDialogLayout.setHorizontalGroup(
            CreateHospitalAssetsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CreateHospitalAssetsDialogLayout.setVerticalGroup(
            CreateHospitalAssetsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("AdminDashboard"); // NOI18N
        setSize(new java.awt.Dimension(0, 0));

        MainPanel.setBackground(new java.awt.Color(255, 255, 255));

        btnRefreshUsers.setText("Refresh");
        btnRefreshUsers.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnRefreshUsers.addActionListener(this::btnRefreshUsersActionPerformed);

        btnDeleteUser.setText("Delete User");
        btnDeleteUser.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnDeleteUser.addActionListener(this::btnDeleteUserActionPerformed);

        btnAddUser.setText("Add User");
        btnAddUser.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnAddUser.addActionListener(this::btnAddUserActionPerformed);

        btnEditUser.setText("Edit User");
        btnEditUser.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnEditUser.addActionListener(this::btnEditUserActionPerformed);

        tbUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbUsers.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbUsers);

        javax.swing.GroupLayout UsersPanelLayout = new javax.swing.GroupLayout(UsersPanel);
        UsersPanel.setLayout(UsersPanelLayout);
        UsersPanelLayout.setHorizontalGroup(
            UsersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1094, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UsersPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnAddUser)
                .addGap(18, 18, 18)
                .addComponent(btnEditUser)
                .addGap(18, 18, 18)
                .addComponent(btnDeleteUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRefreshUsers)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        UsersPanelLayout.setVerticalGroup(
            UsersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UsersPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(UsersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddUser)
                    .addComponent(btnEditUser)
                    .addComponent(btnDeleteUser)
                    .addComponent(btnRefreshUsers))
                .addGap(15, 15, 15))
        );

        tabAdminDashboard.addTab("Users", UsersPanel);

        tbDoctorAssignment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbDoctorAssignment.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tbDoctorAssignment);

        cbDoctorListDoctorAssignment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbMedicalManagerListDoctorAssignment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Doctor");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Medical Manager");

        btnAssignDoctorDoctorAssignment.setText("Assign Doctor To Medical Manager");
        btnAssignDoctorDoctorAssignment.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnAssignDoctorDoctorAssignment.addActionListener(this::btnAssignDoctorDoctorAssignmentActionPerformed);

        btnUnassignDoctorDoctorAssignment.setText("Unassign Doctor");
        btnUnassignDoctorDoctorAssignment.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnUnassignDoctorDoctorAssignment.addActionListener(this::btnUnassignDoctorDoctorAssignmentActionPerformed);

        javax.swing.GroupLayout DoctorAssignmentPanelLayout = new javax.swing.GroupLayout(DoctorAssignmentPanel);
        DoctorAssignmentPanel.setLayout(DoctorAssignmentPanelLayout);
        DoctorAssignmentPanelLayout.setHorizontalGroup(
            DoctorAssignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DoctorAssignmentPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(DoctorAssignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DoctorAssignmentPanelLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(DoctorAssignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUnassignDoctorDoctorAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAssignDoctorDoctorAssignment)))
                    .addGroup(DoctorAssignmentPanelLayout.createSequentialGroup()
                        .addGroup(DoctorAssignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16))
                        .addGroup(DoctorAssignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DoctorAssignmentPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(cbMedicalManagerListDoctorAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DoctorAssignmentPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(cbDoctorListDoctorAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        DoctorAssignmentPanelLayout.setVerticalGroup(
            DoctorAssignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DoctorAssignmentPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(DoctorAssignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbDoctorListDoctorAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(DoctorAssignmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbMedicalManagerListDoctorAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(54, 54, 54)
                .addComponent(btnAssignDoctorDoctorAssignment)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUnassignDoctorDoctorAssignment)
                .addGap(121, 121, 121))
        );

        tabAdminDashboard.addTab("Doctor Assignment", DoctorAssignmentPanel);

        tbHospitalAssets.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbHospitalAssets.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(tbHospitalAssets);

        btnAddAssetHospitalAssets.setText("Add Asset");
        btnAddAssetHospitalAssets.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnAddAssetHospitalAssets.addActionListener(this::btnAddAssetHospitalAssetsActionPerformed);

        btnEditAssetHospitalAssets.setText("Edit Asset");
        btnEditAssetHospitalAssets.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnEditAssetHospitalAssets.addActionListener(this::btnEditAssetHospitalAssetsActionPerformed);

        btnAllocateToDepartmentHospitalAssets.setText("Allocate To Department");
        btnAllocateToDepartmentHospitalAssets.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnAllocateToDepartmentHospitalAssets.addActionListener(this::btnAllocateToDepartmentHospitalAssetsActionPerformed);

        btnRefreshHospitalAssets.setText("Refresh");
        btnRefreshHospitalAssets.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnRefreshHospitalAssets.addActionListener(this::btnRefreshHospitalAssetsActionPerformed);

        javax.swing.GroupLayout HospitalAssetsPanelLayout = new javax.swing.GroupLayout(HospitalAssetsPanel);
        HospitalAssetsPanel.setLayout(HospitalAssetsPanelLayout);
        HospitalAssetsPanelLayout.setHorizontalGroup(
            HospitalAssetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
            .addGroup(HospitalAssetsPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnAddAssetHospitalAssets)
                .addGap(18, 18, 18)
                .addComponent(btnEditAssetHospitalAssets)
                .addGap(18, 18, 18)
                .addComponent(btnAllocateToDepartmentHospitalAssets)
                .addGap(18, 18, 18)
                .addComponent(btnRefreshHospitalAssets)
                .addContainerGap(588, Short.MAX_VALUE))
        );
        HospitalAssetsPanelLayout.setVerticalGroup(
            HospitalAssetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HospitalAssetsPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(HospitalAssetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddAssetHospitalAssets)
                    .addComponent(btnEditAssetHospitalAssets)
                    .addComponent(btnAllocateToDepartmentHospitalAssets)
                    .addComponent(btnRefreshHospitalAssets))
                .addGap(15, 15, 15))
        );

        tabAdminDashboard.addTab("Hospital Assets", HospitalAssetsPanel);

        tbCheckUpTypes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbCheckUpTypes.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tbCheckUpTypes);

        btnAddCheckUp.setText("Add Check-Up");
        btnAddCheckUp.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnAddCheckUp.addActionListener(this::btnAddCheckUpActionPerformed);

        btnEditCheckUp.setText("Edit / Base Rate");
        btnEditCheckUp.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnEditCheckUp.addActionListener(this::btnEditCheckUpActionPerformed);

        btnCheckUpRefresh.setText("Refresh");
        btnCheckUpRefresh.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnCheckUpRefresh.addActionListener(this::btnCheckUpRefreshActionPerformed);

        javax.swing.GroupLayout CheckUpTypesPanelLayout = new javax.swing.GroupLayout(CheckUpTypesPanel);
        CheckUpTypesPanel.setLayout(CheckUpTypesPanelLayout);
        CheckUpTypesPanelLayout.setHorizontalGroup(
            CheckUpTypesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1094, Short.MAX_VALUE)
            .addGroup(CheckUpTypesPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnAddCheckUp)
                .addGap(18, 18, 18)
                .addComponent(btnEditCheckUp)
                .addGap(18, 18, 18)
                .addComponent(btnCheckUpRefresh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CheckUpTypesPanelLayout.setVerticalGroup(
            CheckUpTypesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CheckUpTypesPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(CheckUpTypesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddCheckUp)
                    .addComponent(btnEditCheckUp)
                    .addComponent(btnCheckUpRefresh))
                .addGap(15, 15, 15))
        );

        tabAdminDashboard.addTab("Check Up Types", CheckUpTypesPanel);

        tbInsuranceNetwork.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbInsuranceNetwork.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tbInsuranceNetwork);

        btnAddInsurance.setText("Add Insurance");
        btnAddInsurance.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnAddInsurance.addActionListener(this::btnAddInsuranceActionPerformed);

        btnEditInsurance.setText("Edit Insurance");
        btnEditInsurance.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnEditInsurance.addActionListener(this::btnEditInsuranceActionPerformed);

        javax.swing.GroupLayout InsuranceNetworkPanelLayout = new javax.swing.GroupLayout(InsuranceNetworkPanel);
        InsuranceNetworkPanel.setLayout(InsuranceNetworkPanelLayout);
        InsuranceNetworkPanelLayout.setHorizontalGroup(
            InsuranceNetworkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1094, Short.MAX_VALUE)
            .addGroup(InsuranceNetworkPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnAddInsurance)
                .addGap(18, 18, 18)
                .addComponent(btnEditInsurance)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        InsuranceNetworkPanelLayout.setVerticalGroup(
            InsuranceNetworkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InsuranceNetworkPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(InsuranceNetworkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddInsurance)
                    .addComponent(btnEditInsurance))
                .addGap(15, 15, 15))
        );

        tabAdminDashboard.addTab("Insurance Networks", InsuranceNetworkPanel);

        tbMedicalServiceRequest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(tbMedicalServiceRequest);

        btnAssignAssetServiceRequest.setText("Assign Asset");
        btnAssignAssetServiceRequest.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnAssignAssetServiceRequest.addActionListener(this::btnAssignAssetServiceRequestActionPerformed);

        btnRejectServiceRequest.setText("Reject");
        btnRejectServiceRequest.setMargin(new java.awt.Insets(10, 20, 10, 20));

        btnRefreshServiceRequest.setText("Refresh");
        btnRefreshServiceRequest.setMargin(new java.awt.Insets(10, 20, 10, 20));

        javax.swing.GroupLayout MedicalServiceRequestsPanelLayout = new javax.swing.GroupLayout(MedicalServiceRequestsPanel);
        MedicalServiceRequestsPanel.setLayout(MedicalServiceRequestsPanelLayout);
        MedicalServiceRequestsPanelLayout.setHorizontalGroup(
            MedicalServiceRequestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1094, Short.MAX_VALUE)
            .addGroup(MedicalServiceRequestsPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnAssignAssetServiceRequest)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRejectServiceRequest)
                .addGap(18, 18, 18)
                .addComponent(btnRefreshServiceRequest)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MedicalServiceRequestsPanelLayout.setVerticalGroup(
            MedicalServiceRequestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MedicalServiceRequestsPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(MedicalServiceRequestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAssignAssetServiceRequest)
                    .addComponent(btnRejectServiceRequest)
                    .addComponent(btnRefreshServiceRequest))
                .addGap(15, 15, 15))
        );

        tabAdminDashboard.addTab("Medical Service Requests", MedicalServiceRequestsPanel);

        jPanel1.setBackground(new java.awt.Color(17, 55, 95));

        lbWelcomeTitle.setBackground(new java.awt.Color(255, 255, 255));
        lbWelcomeTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbWelcomeTitle.setForeground(new java.awt.Color(255, 255, 255));
        lbWelcomeTitle.setText("Hospital Management System");

        btnLogout.setText("LOGOUT");
        btnLogout.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        lbWelcomeSubtitle.setBackground(new java.awt.Color(255, 255, 255));
        lbWelcomeSubtitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbWelcomeSubtitle.setForeground(new java.awt.Color(202, 202, 202));
        lbWelcomeSubtitle.setText("Signed in as NAME | Admin Staff");

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logonew.png"))); // NOI18N
        jLabel17.setMaximumSize(new java.awt.Dimension(561, 512));
        jLabel17.setPreferredSize(new java.awt.Dimension(561, 512));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbWelcomeTitle)
                    .addComponent(lbWelcomeSubtitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addGap(25, 25, 25))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btnLogout)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbWelcomeTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbWelcomeSubtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))))
        );

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tabAdminDashboard)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabAdminDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        adminStaff = null;

        CreateUserDialog.dispose();
        CreateInsuranceNetworkDialog.dispose();

        dispose();

        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnRefreshUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshUsersActionPerformed
        loadUsers();
    }//GEN-LAST:event_btnRefreshUsersActionPerformed

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        initialCreateUserDialog();

        CreateUserDialog.pack();
        CreateUserDialog.setLocationRelativeTo(this);
        CreateUserDialog.setVisible(true);
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnCreateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateUserActionPerformed
        String fullName = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String phoneNumber = txtPhoneNumber.getText().trim();
        String password = new String(txtPassword.getPassword());
        String role = cbRole.getSelectedItem().toString();
        boolean active = cbActive.isSelected();

        if (fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(CreateUserDialog, "Please fill in all fields.");
            return;
        }

        User user = null;

//        CREATE
        if (editingUserId == null) {
            switch (role) {
                case "ADMIN":
                    user = new AdminStaff(
                            fullName,
                            email,
                            phoneNumber,
                            password,
                            active
                    );
                    break;

                case "MEDICAL_MANAGER":
                    user = new MedicalManager(
                            fullName,
                            email,
                            phoneNumber,
                            password,
                            active
                    );
                    break;

                case "DOCTOR":
                    user = new Doctor(
                            fullName,
                            email,
                            phoneNumber,
                            password,
                            active
                    );
                    break;

                case "PATIENT":
                    user = new Patient(
                            fullName,
                            email,
                            phoneNumber,
                            password,
                            active
                    );
                    break;
            }

            if (user != null) {
                boolean success = adminStaff.createUser(user);
                if (success) {
                    JOptionPane.showMessageDialog(CreateUserDialog, "User created successfully.");
                    CreateUserDialog.dispose();

                    loadUsers();
                } else {
                    JOptionPane.showMessageDialog(CreateUserDialog, "Failed to create user.");
                }
            }
        } else {
//            EDIT
            switch (role) {
                case "ADMIN":
                    user = new AdminStaff(
                            editingUserId,
                            fullName,
                            email,
                            phoneNumber,
                            password,
                            active
                    );
                    break;

                case "MEDICAL_MANAGER":
                    user = new MedicalManager(
                            editingUserId,
                            fullName,
                            email,
                            phoneNumber,
                            password,
                            active
                    );
                    break;

                case "DOCTOR":
                    user = new Doctor(
                            editingUserId,
                            fullName,
                            email,
                            phoneNumber,
                            password,
                            active
                    );
                    break;

                case "PATIENT":
                    user = new Patient(
                            editingUserId,
                            fullName,
                            email,
                            phoneNumber,
                            password,
                            active
                    );
                    break;
            }

            if (user != null) {
                boolean success = adminStaff.updateUser(user);

                if (success) {
                    JOptionPane.showMessageDialog(CreateUserDialog, "User updated successfully.");

                    CreateUserDialog.dispose();

                    editingUserId = null;

                    loadUsers();
                } else {
                    JOptionPane.showMessageDialog(CreateUserDialog, "Failed to update user.");
                }
            }
        }
    }//GEN-LAST:event_btnCreateUserActionPerformed

    private void btnCancelCreateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelCreateUserActionPerformed
        editingUserId = null;
        CreateUserDialog.dispose();
    }//GEN-LAST:event_btnCancelCreateUserActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
        int selectedRow = tbUsers.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
            return;
        }

        String userId = tbUsers.getValueAt(selectedRow, 0).toString();
        String fullName = tbUsers.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete "
                + fullName
                + " (" + userId + ")?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = adminStaff.deleteUser(userId);

        if (success) {
            JOptionPane.showMessageDialog(this, "User deleted successfully.");
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete user.", "Delete Failed", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void btnEditUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditUserActionPerformed
        int selectedRow = tbUsers.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.");
            return;
        }

        String userId = tbUsers.getValueAt(selectedRow, 0).toString();

        User user = adminStaff.getUser(userId);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "User could not be found.");
            return;
        }

//        Select user want to edit
        editingUserId = userId;

        txtFullName.setText(user.getFullName());
        txtEmail.setText(user.getEmail());
        txtPhoneNumber.setText(user.getPhoneNumber());
        txtPassword.setText(user.getPassword());
        cbRole.setSelectedItem(user.getRole().toString());
        cbActive.setSelected(user.isActive());

        btnCreateUser.setText("Save Changes");
        CreateUserDialog.setTitle("Edit User");

        CreateUserDialog.pack();
        CreateUserDialog.setLocationRelativeTo(this);
        CreateUserDialog.setVisible(true);
    }//GEN-LAST:event_btnEditUserActionPerformed

    private void btnAddInsuranceNetworkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddInsuranceNetworkActionPerformed
        String providerName = txtProvider.getText().trim();
        String coverageText = txtCoverage.getText().trim();
        boolean accepted = cbAccepted.isSelected();

        if (providerName.isEmpty() || coverageText.isEmpty()) {
            JOptionPane.showMessageDialog(CreateInsuranceNetworkDialog, "Please fill in all fields.");
            return;
        }

        double coverageRate;

        try {
            coverageRate = Double.parseDouble(coverageText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(CreateInsuranceNetworkDialog, "Coverage must be a number.");
            return;
        }

        boolean success;

        // CREATE
        if (editingInsuranceId == null) {
            success = adminStaff.createInsuranceNetwork(providerName, coverageRate, accepted);

            if (success) {
                JOptionPane.showMessageDialog(CreateInsuranceNetworkDialog, "Insurance network created successfully.");
            } else {
                JOptionPane.showMessageDialog(CreateInsuranceNetworkDialog, "Failed to create insurance network.");
                return;
            }

        } else {
            // EDIT
            success = adminStaff.updateInsuranceNetwork(editingInsuranceId, providerName, coverageRate, accepted);

            if (success) {
                JOptionPane.showMessageDialog(CreateInsuranceNetworkDialog, "Insurance network updated successfully.");
            } else {
                JOptionPane.showMessageDialog(CreateInsuranceNetworkDialog, "Failed to update insurance network.");
                return;
            }
        }
        CreateInsuranceNetworkDialog.dispose();

        editingInsuranceId = null;

        loadInsuranceNetwork();
    }//GEN-LAST:event_btnAddInsuranceNetworkActionPerformed

    private void btnCancelInsuranceNetworkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelInsuranceNetworkActionPerformed
        // TODO add your handling code here:
        editingInsuranceId = null;
        CreateInsuranceNetworkDialog.dispose();
    }//GEN-LAST:event_btnCancelInsuranceNetworkActionPerformed

    private void btnAddInsuranceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddInsuranceActionPerformed
        // TODO add your handling code here:
        initialInsuranceDialog();

        CreateInsuranceNetworkDialog.pack();
        CreateInsuranceNetworkDialog.setLocationRelativeTo(this);
        CreateInsuranceNetworkDialog.setVisible(true);
    }//GEN-LAST:event_btnAddInsuranceActionPerformed

    private void btnEditInsuranceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditInsuranceActionPerformed
        int selectedRow = tbInsuranceNetwork.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an insurance network to edit.");
            return;
        }

        editingInsuranceId = tbInsuranceNetwork.getValueAt(selectedRow, 0).toString();
        String providerName = tbInsuranceNetwork.getValueAt(selectedRow, 1).toString();
        String coverageRate = tbInsuranceNetwork.getValueAt(selectedRow, 2).toString();
        boolean accepted = Boolean.parseBoolean(tbInsuranceNetwork.getValueAt(selectedRow, 3).toString());

        txtProvider.setText(providerName);
        txtCoverage.setText(coverageRate);
        cbAccepted.setSelected(accepted);

        btnAddInsuranceNetwork.setText("Save Changes");
        CreateInsuranceNetworkDialog.setTitle("Edit Insurance Network");

        CreateInsuranceNetworkDialog.pack();
        CreateInsuranceNetworkDialog.setLocationRelativeTo(this);
        CreateInsuranceNetworkDialog.setVisible(true);
    }//GEN-LAST:event_btnEditInsuranceActionPerformed

    private void btnCheckUpRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckUpRefreshActionPerformed
        loadCheckUpTypes();
    }//GEN-LAST:event_btnCheckUpRefreshActionPerformed

    private void btnCreateCheckUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateCheckUpActionPerformed
        String name = txtNameCheckUp.getText().trim();
        String description = txtDescriptionCheckUp.getText().trim();
        String baseRateText = txtBaseRateCheckUp.getText().trim();
        String durationText = txtDurationCheckUp.getText().trim();
        boolean active = cdActiveCheckUp.isSelected();

        if (name.isEmpty() || description.isEmpty() || baseRateText.isEmpty() || durationText.isEmpty()) {
            JOptionPane.showMessageDialog(CreateCheckUpDialog, "Please fill in all fields.");
            return;
        }

        double baseRate;
        int duration;

        try {
            baseRate = Double.parseDouble(baseRateText);
            duration = Integer.parseInt(durationText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(CreateCheckUpDialog, "Base rate and duration must be a numbers");
            return;
        }

        if (baseRate < 0) {
            JOptionPane.showMessageDialog(CreateCheckUpDialog, "Base rate cannot be negative");
            return;
        }

        if (duration <= 0) {
            JOptionPane.showMessageDialog(CreateCheckUpDialog, "Duration must be greater than 0 minutes");
            return;
        }

        boolean success;

        // CREATE
        if (editingCheckUpId == null) {
            success = adminStaff.createCheckUpType(name, description, baseRate, duration);

            if (success) {
                JOptionPane.showMessageDialog(CreateCheckUpDialog, "Check-up type created successfully.");
            } else {
                JOptionPane.showMessageDialog(CreateCheckUpDialog, "Failed to create check-up type.");
                return;
            }

        } else {
            // EDIT
            success = adminStaff.updateCheckUpType(
                    editingCheckUpId,
                    name,
                    description,
                    baseRate,
                    duration,
                    active
            );

            if (success) {
                JOptionPane.showMessageDialog(CreateCheckUpDialog, "Check-up type updated successfully.");
            } else {
                JOptionPane.showMessageDialog(CreateCheckUpDialog, "Failed to update check-up type.");
                return;
            }
        }

        CreateCheckUpDialog.dispose();
        editingCheckUpId = null;

        loadCheckUpTypes();
    }//GEN-LAST:event_btnCreateCheckUpActionPerformed

    private void btnCancelCheckUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelCheckUpActionPerformed
        editingCheckUpId = null;
        CreateCheckUpDialog.dispose();
    }//GEN-LAST:event_btnCancelCheckUpActionPerformed

    private void btnAddCheckUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCheckUpActionPerformed
        initialCheckUpDialog();

        CreateCheckUpDialog.pack();
        CreateCheckUpDialog.setLocationRelativeTo(this);
        CreateCheckUpDialog.setVisible(true);
    }//GEN-LAST:event_btnAddCheckUpActionPerformed

    private void btnEditCheckUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCheckUpActionPerformed
        int selectedRow = tbCheckUpTypes.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a check-up type to edit.");
            return;
        }

        editingCheckUpId = tbCheckUpTypes.getValueAt(selectedRow, 0).toString();
        String name = tbCheckUpTypes.getValueAt(selectedRow, 1).toString();
        String baseRate = tbCheckUpTypes.getValueAt(selectedRow, 2).toString();
        String duration = tbCheckUpTypes.getValueAt(selectedRow, 3).toString();
        String description = tbCheckUpTypes.getValueAt(selectedRow, 4).toString();
        boolean active = Boolean.parseBoolean(tbCheckUpTypes.getValueAt(selectedRow, 5).toString());

        txtNameCheckUp.setText(name);
        txtDescriptionCheckUp.setText(description);
        txtBaseRateCheckUp.setText(baseRate);
        txtDurationCheckUp.setText(duration);
        cdActiveCheckUp.setSelected(active);

        btnCreateCheckUp.setText("Save Changes");
        CreateCheckUpDialog.setTitle("Edit Check-Up Type");

        CreateCheckUpDialog.pack();
        CreateCheckUpDialog.setLocationRelativeTo(this);
        CreateCheckUpDialog.setVisible(true);
    }//GEN-LAST:event_btnEditCheckUpActionPerformed

    private void btnAddAssetHospitalAssetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAssetHospitalAssetsActionPerformed
        initialHospitalAssetsDialog();

        CreateHospitalAssetsDialog.pack();
        CreateHospitalAssetsDialog.setLocationRelativeTo(this);
        CreateHospitalAssetsDialog.setVisible(true);
    }//GEN-LAST:event_btnAddAssetHospitalAssetsActionPerformed

    private void btnEditAssetHospitalAssetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditAssetHospitalAssetsActionPerformed
        int selectedRow = tbHospitalAssets.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hospital asset to edit.");
            return;
        }

        editingHospitalAssetId = tbHospitalAssets.getValueAt(selectedRow, 0).toString();
        String assetName = tbHospitalAssets.getValueAt(selectedRow, 1).toString();
        String assetType = tbHospitalAssets.getValueAt(selectedRow, 2).toString();
        String status = tbHospitalAssets.getValueAt(selectedRow, 3).toString();
        String department = tbHospitalAssets.getValueAt(selectedRow, 4).toString();

        if (department.equals("Not Allocated")) {
            editingHospitalAssetDepartmentId = null;
        } else {
            editingHospitalAssetDepartmentId = department;
        }

        // Populate enum combo boxes
        cbTypeHospitalAssets.removeAllItems();

        for (AssetType type : AssetType.values()) {
            cbTypeHospitalAssets.addItem(type.toString());
        }

        cbStatusHospitalAssets.removeAllItems();

        for (AssetStatus assetStatus : AssetStatus.values()) {
            cbStatusHospitalAssets.addItem(assetStatus.toString());
        }

        // Fill existing values
        txtNameHospitalAssets.setText(assetName);

        cbTypeHospitalAssets.setSelectedItem(assetType);

        cbStatusHospitalAssets.setSelectedItem(status);

        btnCreateHospitalAssets.setText("Save Changes");

        CreateHospitalAssetsDialog.setTitle("Edit Hospital Asset");

        CreateHospitalAssetsDialog.pack();
        CreateHospitalAssetsDialog.setLocationRelativeTo(this);
        CreateHospitalAssetsDialog.setVisible(true);
    }//GEN-LAST:event_btnEditAssetHospitalAssetsActionPerformed

    private void btnRefreshHospitalAssetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshHospitalAssetsActionPerformed
        loadHospitalAssets();
    }//GEN-LAST:event_btnRefreshHospitalAssetsActionPerformed

    private void btnCreateHospitalAssetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateHospitalAssetsActionPerformed
        String assetName = txtNameHospitalAssets.getText().trim();

        if (assetName.isEmpty()) {
            JOptionPane.showMessageDialog(CreateHospitalAssetsDialog, "Please enter the asset name.");
            return;
        }

        AssetType assetType = AssetType.valueOf(cbTypeHospitalAssets.getSelectedItem().toString());
        AssetStatus status = AssetStatus.valueOf(cbStatusHospitalAssets.getSelectedItem().toString());
        boolean success;

        // CREATE
        if (editingHospitalAssetId == null) {
            success = adminStaff.createHospitalAsset(
                    assetName,
                    assetType,
                    status,
                    null
            );

            if (success) {
                JOptionPane.showMessageDialog(CreateHospitalAssetsDialog, "Hospital asset created successfully.");
            } else {
                JOptionPane.showMessageDialog(CreateHospitalAssetsDialog, "Failed to create hospital asset.");
                return;
            }
        } else {
            // EDIT
            success = adminStaff.updateHospitalAsset(
                    editingHospitalAssetId,
                    assetName,
                    assetType,
                    status,
                    editingHospitalAssetDepartmentId
            );

            if (success) {
                JOptionPane.showMessageDialog(CreateHospitalAssetsDialog, "Hospital asset updated successfully.");
            } else {
                JOptionPane.showMessageDialog(CreateHospitalAssetsDialog, "Failed to update hospital asset.");
                return;
            }
        }

        CreateHospitalAssetsDialog.dispose();

        editingHospitalAssetId = null;
        editingHospitalAssetDepartmentId = null;

        loadHospitalAssets();
    }//GEN-LAST:event_btnCreateHospitalAssetsActionPerformed

    private void btnCancelHospitalAssetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelHospitalAssetsActionPerformed
        editingHospitalAssetId = null;
        editingHospitalAssetDepartmentId = null;

        CreateHospitalAssetsDialog.dispose();
    }//GEN-LAST:event_btnCancelHospitalAssetsActionPerformed

    private void btnAllocateToDepartmentHospitalAssetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllocateToDepartmentHospitalAssetsActionPerformed
        int selectedRow = tbHospitalAssets.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hospital asset.");
            return;
        }

        ArrayList<Department> departments
                = adminStaff.getDepartments();

        if (departments.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No departments are available.");
            return;
        }

        Department selectedDepartment = (Department) JOptionPane.showInputDialog(
                this,
                "Select department:",
                "Allocate Hospital Asset",
                JOptionPane.PLAIN_MESSAGE,
                null,
                departments.toArray(),
                departments.get(0)
        );

        // User pressed Cancel
        if (selectedDepartment == null) {
            return;
        }

        String assetId = tbHospitalAssets.getValueAt(selectedRow, 0).toString();
        String assetName = tbHospitalAssets.getValueAt(selectedRow, 1).toString();
        AssetType assetType = AssetType.valueOf(tbHospitalAssets.getValueAt(selectedRow, 2).toString());
        AssetStatus status = AssetStatus.valueOf(tbHospitalAssets.getValueAt(selectedRow, 3).toString());
        String departmentValue = tbHospitalAssets.getValueAt(selectedRow, 4).toString();
        String currentDepartmentId;

        if (departmentValue.equals("Not Allocated")) {
            currentDepartmentId = null;
        } else {
            currentDepartmentId = departmentValue;
        }

        boolean success = adminStaff.allocateHospitalAsset(
                assetId,
                assetName,
                assetType,
                status,
                currentDepartmentId,
                selectedDepartment
        );

        if (success) {
            JOptionPane.showMessageDialog(
                    this,
                    "Asset allocated to "
                    + selectedDepartment.getDepartmentName()
                    + " successfully."
            );

            loadHospitalAssets();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to allocate hospital asset.");
        }
    }//GEN-LAST:event_btnAllocateToDepartmentHospitalAssetsActionPerformed

    private void btnAssignDoctorDoctorAssignmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignDoctorDoctorAssignmentActionPerformed
        if (cbDoctorListDoctorAssignment.getSelectedItem() == null || cbMedicalManagerListDoctorAssignment.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a doctor and medical manager.");
            return;
        }

        String doctorSelection = cbDoctorListDoctorAssignment.getSelectedItem().toString();
        String managerSelection = cbMedicalManagerListDoctorAssignment.getSelectedItem().toString();

        String doctorId = doctorSelection.split(" - ", 2)[0];
        String managerId = managerSelection.split(" - ", 2)[0];
        boolean success = adminStaff.assignDoctor(doctorId, managerId);

        if (success) {
            JOptionPane.showMessageDialog(this, "Doctor assigned successfully.");
            loadDoctorAssignmentData();
        } else {
            JOptionPane.showMessageDialog(this, "Doctor is already assigned to a medical manager.");
        }
    }//GEN-LAST:event_btnAssignDoctorDoctorAssignmentActionPerformed

    private void btnUnassignDoctorDoctorAssignmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnassignDoctorDoctorAssignmentActionPerformed
        int selectedRow = tbDoctorAssignment.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an assigned doctor.");
            return;
        }

        String doctorId = tbDoctorAssignment.getValueAt(selectedRow, 0).toString();
        String doctorName = tbDoctorAssignment.getValueAt(selectedRow, 1).toString();
        String managerName = tbDoctorAssignment.getValueAt(selectedRow, 3).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Unassign "
                + doctorName
                + " from "
                + managerName
                + "?",
                "Confirm Unassign",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = adminStaff.unassignDoctor(doctorId);

        if (success) {
            JOptionPane.showMessageDialog(this, "Doctor unassigned successfully.");
            loadDoctorAssignmentData();

        } else {
            JOptionPane.showMessageDialog(this, "Failed to unassign doctor.");
        }
    }//GEN-LAST:event_btnUnassignDoctorDoctorAssignmentActionPerformed

    private void btnAssignAssetServiceRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignAssetServiceRequestActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAssignAssetServiceRequestActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CheckUpTypesPanel;
    private javax.swing.JDialog CreateCheckUpDialog;
    private javax.swing.JDialog CreateHospitalAssetsDialog;
    private javax.swing.JDialog CreateInsuranceNetworkDialog;
    private javax.swing.JDialog CreateUserDialog;
    private javax.swing.JPanel DoctorAssignmentPanel;
    private javax.swing.JPanel HospitalAssetsPanel;
    private javax.swing.JPanel InsuranceNetworkPanel;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JPanel MedicalServiceRequestsPanel;
    private javax.swing.JPanel UsersPanel;
    private javax.swing.JButton btnAddAssetHospitalAssets;
    private javax.swing.JButton btnAddCheckUp;
    private javax.swing.JButton btnAddInsurance;
    private javax.swing.JButton btnAddInsuranceNetwork;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnAllocateToDepartmentHospitalAssets;
    private javax.swing.JButton btnAssignAssetServiceRequest;
    private javax.swing.JButton btnAssignDoctorDoctorAssignment;
    private javax.swing.JButton btnCancelCheckUp;
    private javax.swing.JButton btnCancelCreateUser;
    private javax.swing.JButton btnCancelHospitalAssets;
    private javax.swing.JButton btnCancelInsuranceNetwork;
    private javax.swing.JButton btnCheckUpRefresh;
    private javax.swing.JButton btnCreateCheckUp;
    private javax.swing.JButton btnCreateHospitalAssets;
    private javax.swing.JButton btnCreateUser;
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnEditAssetHospitalAssets;
    private javax.swing.JButton btnEditCheckUp;
    private javax.swing.JButton btnEditInsurance;
    private javax.swing.JButton btnEditUser;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnRefreshHospitalAssets;
    private javax.swing.JButton btnRefreshServiceRequest;
    private javax.swing.JButton btnRefreshUsers;
    private javax.swing.JButton btnRejectServiceRequest;
    private javax.swing.JButton btnUnassignDoctorDoctorAssignment;
    private javax.swing.JCheckBox cbAccepted;
    private javax.swing.JCheckBox cbActive;
    private javax.swing.JComboBox<String> cbDoctorListDoctorAssignment;
    private javax.swing.JComboBox<String> cbMedicalManagerListDoctorAssignment;
    private javax.swing.JComboBox<String> cbRole;
    private javax.swing.JComboBox<String> cbStatusHospitalAssets;
    private javax.swing.JComboBox<String> cbTypeHospitalAssets;
    private javax.swing.JCheckBox cdActiveCheckUp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lbWelcomeSubtitle;
    private javax.swing.JLabel lbWelcomeTitle;
    private javax.swing.JTabbedPane tabAdminDashboard;
    private javax.swing.JTable tbCheckUpTypes;
    private javax.swing.JTable tbDoctorAssignment;
    private javax.swing.JTable tbHospitalAssets;
    private javax.swing.JTable tbInsuranceNetwork;
    private javax.swing.JTable tbMedicalServiceRequest;
    private javax.swing.JTable tbUsers;
    private javax.swing.JTextField txtBaseRateCheckUp;
    private javax.swing.JTextField txtCoverage;
    private javax.swing.JTextField txtDescriptionCheckUp;
    private javax.swing.JTextField txtDurationCheckUp;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtNameCheckUp;
    private javax.swing.JTextField txtNameHospitalAssets;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPhoneNumber;
    private javax.swing.JTextField txtProvider;
    // End of variables declaration//GEN-END:variables
}
