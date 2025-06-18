public class BST {
    static class Node {
        Patient patient;
        Node left;
        Node right;

        Node(Patient patient) {
            this.patient = patient;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public BST() {
        this.root = null;
    }

    public void insertPatient(Patient patient){
        root = insertRecursive(root, patient);
    }

    private Node insertRecursive(Node current, Patient patient){
        if(current == null){
            return new Node(patient);
        }

        if(patient.id < current.patient.id){
            current.left = insertRecursive(current.left, patient);
        } 
        else if(patient.id > current.patient.id){
            current.right = insertRecursive(current.right, patient);
        }
        return current;
    }

    public Patient searchPatient(int id){
        Node result = searchRecursive(root, id);
        return (result != null) ? result.patient : null;
    }

    private Node searchRecursive(Node current, int id){
        if(current == null || current.patient.id == id){
            return current;
        }
        if(id < current.patient.id){
            return searchRecursive(current.left, id);
        }
        return searchRecursive(current.right, id);
    }

    public void removePatient(int id){
        root = removeRecursive(root, id);
    }

    private Node removeRecursive(Node current, int id){
        if(current == null){
            return null;
        }

        if(id == current.patient.id){
            if(current.left == null && current.right == null){
                return null;
            }
            if(current.right == null){
                return current.left;
            }
            if(current.left == null){
                return current.right;
            }

            Patient smallestValue = findSmallestValue(current.right);
            current.patient = smallestValue;
            current.right = removeRecursive(current.right, smallestValue.id);
            return current;
        }

        if(id < current.patient.id){
            current.left = removeRecursive(current.left, id);
            return current;
        }
        current.right = removeRecursive(current.right, id);
        return current;
    }

    private Patient findSmallestValue(Node root){
        return root.left == null ? root.patient : findSmallestValue(root.left);
    }

    public void inOrderDisplay() {
        if (root == null) {
            System.out.println("No patients found in BST.");
            return;
        }
        System.out.println("Patients (sorted by ID):");
        MainPage.singleLine();
        inOrderTraversal(root);
    }

    private void inOrderTraversal(Node node) {
        if (node != null) {
            inOrderTraversal(node.left);
            
            // Display current patient
            MainPage.printTab("ID");
            System.out.println(node.patient.id);
            MainPage.printTab("Full Name");
            System.out.println(node.patient.name);
            MainPage.printTab("Age");
            System.out.println(node.patient.age);
            MainPage.printTab("Address");
            System.out.println(node.patient.address);
            MainPage.printTab("Phone Number");
            System.out.println(node.patient.phone);
            MainPage.singleLine();
            
            inOrderTraversal(node.right);
        }
    }

    // Check if BST is empty
    public boolean isEmpty() {
        return root == null;
    }

    // Get total number of patients in BST
    public int size() {
        return getSizeRecursive(root);
    }

    private int getSizeRecursive(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + getSizeRecursive(node.left) + getSizeRecursive(node.right);
    }

    // Find patient with minimum ID
    public Patient findMinPatient() {
        if (root == null) {
            return null;
        }
        Node current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.patient;
    }

    // Find patient with maximum ID
    public Patient findMaxPatient() {
        if (root == null) {
            return null;
        }
        Node current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.patient;
    }

    // Load all patients from linked list to BST
    public void loadFromLinkedList(PatientRecordManagement prm) {
        PatientRecordManagement.Node current = prm.getHead();
        while (current != null) {
            insertPatient(current.patient);
            current = current.next;
        }
    }
}