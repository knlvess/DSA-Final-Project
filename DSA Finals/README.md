## Clinic Management System

Sistem managemen clinic berbasi Java ini memungkinkan pengguna untuk:

- Mengelola data pasien dan dokter
- Mengatur jadwal konsultasi/kunjungan dokter
- Menampilkan informasi dengan antarmuka menu berbasis teks

Anggota Kelompok:
* Daffa Arkan Taqiya [L0124047]
* Erika Nur Amalia [L0124049]
* Azzaral Aswad Asshiddiqy [L0124090]
* Devan Huda Fadhilah [L0124095]
------

## Struktur Direktori Program
- `Main.java`
    - `MainPage.java`
        - `DoctorLogin.java`
        - `PatientRecordManagement.java`
        - `AppointmentManagement.java`
        - `AppointmentMap.java`
- `PatientRecords.csv`
- `DoctorRecords.csv`
- `AppointmentRecords.csv`
- `Schedule.csv`
------

## Cara Penggunaan Program

### Memulai Program

1.  **Persiapan**:
    * Pastikan Anda telah menginstal Java Development Kit (JDK) 8.
    * Buka terminal atau `command prompt`, dan pindah ke direktori `Clinic/`.

2.  **Kompilasi Semua File**:
    Kompilasi semua file Java yang ada di direktori tersebut:
    ```bash
    javac *.java
    ```

3.  **Jalankan Program**:
    Setelah kompilasi berhasil, jalankan program utama:
    ```bash
    java Main
    ```

### Alur Program dan Opsi Menu

Setelah program berjalan, Anda akan disambut dengan halaman login.

**1. Halaman Login**

Halaman ini adalah titik utama program. Anda akan diminta untuk memilih ingin login sebagai:
```
=======================================================
               AMBALABU CLINIC (LOGIN PAGE)
=======================================================
Login as:
[1] Patient
[2] Doctor
[3] Admin
-------------------------------------------------------
Please select an option:
```
* **[1] Patient**: Memungkinkan Anda untuk masuk sebagai pasien untuk mengelola janji temu pribadi.
* **[2] Doctor**: Memungkinkan Anda untuk masuk sebagai dokter untuk mengelola janji temu pasien.
* **[3] Admin**: Memungkinkan Anda untuk masuk sebagai administrator untuk mengelola data pasien dan melihat informasi dokter.

**2. Login Sebagai Pasien ([1] Patient)**

Jika Anda memilih opsi `1` (Patient) pada halaman login, Anda akan diminta untuk memasukkan ID Pasien dan Nomor Telepon.
```
=======================================================
               PATIENT LOGIN
=======================================================
Patient ID         : [Masukkan Patient ID Anda]
Phone Number       : [Masukkan Nomor Telepon Anda]
```
Jika ID Pasien dan Nomor Telepon cocok dengan data yang ada, Anda akan berhasil login dan diarahkan ke **Menu Utama Pasien**.

**2.1. Menu Utama Pasien**

Setelah berhasil login sebagai pasien, Anda akan melihat opsi-opsi berikut:
```
=======================================================
               AMBALABU CLINIC
=======================================================
[1] Schedule Appointment
[2] Display Upcoming Appointments
[3] Login Menu
[0] Exit
-------------------------------------------------------
Please select an option:
```
* **[1] Schedule Appointment**:
    * Memungkinkan Anda membuat janji temu baru.
    * Anda akan diminta untuk memilih poliklinik (`[1] General Clinic` atau `[2] Dental Clinic`).
    * Kemudian, Anda akan melihat daftar dokter yang tersedia untuk poliklinik yang Anda pilih
      >![1e0d65f3-9e90-44fd-b934-7be243cbfde2](https://github.com/user-attachments/assets/e78287ba-7e77-4337-a50f-a63cb40bd072)
    * Anda harus memilih dokter berdasarkan ID.
    * Selanjutnya, Anda akan melihat jadwal yang tersedia untuk dokter tersebut. Slot yang bertanda "Unavailable" tidak dapat dipilih.
      >![244f8b0c-cfbb-41b6-966a-7442d4c1ca77](https://github.com/user-attachments/assets/980fc207-85c5-426b-965c-0930972e4f81)
    * Setelah memilih jadwal, Anda akan melihat ringkasan janji temu dan diminta untuk mengkonfirmasi atau membatalkan.
      >![5a48ed22-e5fa-4c98-af66-c7c7475966c0](https://github.com/user-attachments/assets/3225c062-9d8d-4c94-8350-faffb04d78be)
    * Jika dikonfirmasi, janji temu akan berhasil dijadwalkan, dan data akan disimpan ke `AppointmentRecords.csv`. Slot jadwal yang dipilih akan ditandai sebagai tidak tersedia di `Schedule.csv`.

* **[2] Display Upcoming Appointments**:
    * Menampilkan daftar janji temu yang akan datang khusus untuk ID pasien.
      >![4bfca3f1-b3a3-4b04-b622-66a9551f7d63](https://github.com/user-attachments/assets/044ee685-152e-47a7-b74d-1212414a74ca)
    * Informasi yang ditampilkan meliputi ID Janji Temu, ID Dokter, dan Waktu.

* **[3] Login Menu**: Mengarahkan Anda kembali ke halaman login utama.

* **[0] Exit**: Menutup program.

**3. Login Sebagai Dokter ([2] Doctor)**

Jika Anda memilih opsi `2` (Doctor) pada halaman login, Anda akan diminta untuk memasukkan ID Dokter.
```
=======================================================
               DOCTOR LOGIN
=======================================================
Doctor ID          : [Masukkan Doctor ID Anda]
```
Jika ID Dokter ditemukan, Anda akan melihat detail dokter tersebut dan diminta untuk login atau membatalkan. Jika Anda memilih login, waktu login Anda akan dicatat, dan Anda akan diarahkan ke **Menu Utama Dokter**.

**3.1. Menu Utama Dokter**

Setelah berhasil login sebagai dokter, Anda akan melihat opsi-opsi berikut:
```
=======================================================
               AMBALABU CLINIC
=======================================================
[1] Process Appointment
[2] Display Upcoming Appointments
[3] Login Menu
[4] Logout
[0] Exit
-------------------------------------------------------
Please select an option:
```
* **[1] Process Appointment**:
    * Memungkinkan dokter untuk memproses janji temu berikutnya dalam antrean mereka.
    * Jika tidak ada janji temu yang tersedia, pesan "No appointments to process" akan ditampilkan.
      >![7f8fe2ae-e3d8-4c13-8a2a-d912f219631d](https://github.com/user-attachments/assets/e25bf80f-46bd-4863-b0a1-c345b9b68936)
    * Anda akan diminta untuk memasukkan Diagnosis dan Treatment untuk janji temu yang sedang diproses.
      >![c33757f7-720f-44ff-a966-fbd62b117c1c](https://github.com/user-attachments/assets/57967ece-e9f1-45b7-bc2a-6113b79445df)
    * Setelah diproses, janji temu akan dipindahkan ke `CompletedAppointments.csv`.
    * Slot jadwal yang telah diproses akan kembali tersedia di `Schedule.csv`.

* **[2] Display Upcoming Appointments**:
    * Menampilkan daftar janji temu yang akan datang khusus untuk ID dokter.
      >![ede5679b-d714-4db3-9645-4e97177f834b](https://github.com/user-attachments/assets/89e05807-4791-40df-8601-03d9e28fef73)
    * Informasi yang ditampilkan meliputi ID Janji Temu, ID Pasien, dan Waktu.

* **[3] Login Menu**: Mengarahkan Anda kembali ke halaman login utama.

* **[4] Logout**:
    * Memungkinkan dokter untuk logout dari sistem.
    * Waktu logout dokter akan dicatat di `DoctorRecords.csv`, dan `loginTime` akan disetel ke `0`.
    * Anda akan diarahkan kembali ke halaman login.

* **[0] Exit**: Menutup program.

**4. Login Sebagai Admin ([3] Admin)**

Jika Anda memilih opsi `3` (Admin) pada halaman login, Anda akan langsung diarahkan ke **Menu Utama Admin**.

**4.1. Menu Utama Admin**

Setelah berhasil login sebagai admin, Anda akan melihat opsi-opsi berikut:
```
=======================================================
               AMBALABU CLINIC
=======================================================
[1] Add New Patient
[2] Remove Patient by ID
[3] Search Patient by Name
[4] Display All Patients
[5] View Last Logged-in Doctor
[6] Search Patient by ID
[7] Display All Patients (BST - SORTED BY ID)
[8] Login Menu
[0] Exit
-------------------------------------------------------
Please select an option:
```
* **[1] Add New Patient**:
    * Memungkinkan admin untuk menambahkan data pasien baru ke sistem.
    * Anda akan diminta untuk memasukkan Nama Lengkap, Usia, Alamat, dan Nomor Telepon pasien.
      >![4a8753d8-f971-4bce-8725-ff53fe54fc14](https://github.com/user-attachments/assets/f2d396cb-7841-438a-a04f-021084e38e52)
    * Pasien baru akan diberikan ID unik.
    * Data pasien akan ditambahkan ke `PatientRecords.csv` dan juga di-insert ke Binary Search Tree (BST) internal.

* **[2] Remove Patient by ID**:
    * Memungkinkan admin untuk menghapus data pasien dari sistem berdasarkan ID pasien.
      >![2afc04ca-09f1-4a9f-8210-693f0ba580b7](https://github.com/user-attachments/assets/3dc8ffec-ad56-447a-851f-11a66b124a21)
    * Setelah penghapusan, data di `PatientRecords.csv` akan diperbarui, dan pasien juga akan dihapus dari BST.

* **[3] Search Patient by Name**:
    * Memungkinkan admin untuk mencari pasien berdasarkan nama.
      >![2104dfe6-7580-4db7-a7f3-7a52ec09cce3](https://github.com/user-attachments/assets/54427b7a-7974-4518-8b7e-29f91ac5de4e)
    * Jika ditemukan, detail pasien akan ditampilkan.
      >![ae03316c-44da-40c5-ba64-ed76e793a0a6](https://github.com/user-attachments/assets/adf95402-c36a-4ec1-a6e8-229eb9da1cb8)

* **[4] Display All Patients**:
    * Menampilkan daftar lengkap semua pasien yang terdaftar dalam sistem.
      >![8cc6aba3-6aaa-4e1c-aef7-b79221e9b5c4](https://github.com/user-attachments/assets/3c2282ad-5eb0-4d62-acfc-a03a4b5662df)
    * Data pasien diambil dari linked list internal (`PatientRecordManagement`).

* **[5] View Last Logged-in Doctor**:
    * Menampilkan daftar dokter yang saat ini login ke sistem, bersama dengan ID, Nama, dan Spesialisasi mereka.
      >![a5b829c3-24dc-4609-a6b5-ba63fad05ab7](https://github.com/user-attachments/assets/a4f13b9f-029c-45fb-9911-f0dafb36f68c)

* **[6] Search Patient by ID**:
    * Memungkinkan admin untuk mencari pasien berdasarkan ID pasien.
      >![48986c54-9380-4791-87b5-8d8a59c93f75](https://github.com/user-attachments/assets/f2d1cb35-85b8-45b9-be7d-7d8f2c7e94df)
    * Pencarian ini menggunakan implementasi linked list (`PatientRecordManagement`).

* **[7] Display All Patients (BST - Sorted by ID)**:
    * Menampilkan daftar lengkap semua pasien yang terdaftar, diurutkan berdasarkan ID.
      >![e9a8440a-bce0-42f2-8fb0-b0b711328a86](https://github.com/user-attachments/assets/57f87b18-824c-4110-9f4a-d42d25bf5102)
    * Tampilan ini menggunakan data dari Binary Search Tree (BST) internal. BST memungkinkan pencarian yang lebih cepat.

* **[8] Login Menu**: Mengarahkan Anda kembali ke halaman login utama.

* **[0] Exit**: Menutup program.
------

## Fitur Utama:
1. **Login Dokter**
     - Dokter dapat login menggunakan username dan password yang telah ditentukan.
     - Mendukung proses login untuk dokter berdasarkan username dan password.
     - Menggunakan struktur data linked list untuk menyimpan data login dokter.

2. **Manajemen Data Pasien**
     - Menambahkan dan menampilkan daftar pasien.
     - Menggunakan linked list buatan sendiri.
     - Menyimpan data pasien ke file PatientRecords.csv.

3. **Penjadwalan Janji Temu**
     - Menjadwalkan janji temu antara pasien dan dokter.
     - Menggunakan struktur data Queue untuk mengelola antrean.
     - Menyimpan data dalam AppointmentRecords.csv.

4. **Pemetaan Janji Temu**
     - Menyediakan tampilan visual janji temu berdasarkan ID pasien.
     - Mengelola dan mencari janji temu dari data yang tersedia.

5. **Navigasi Menu**
     - Menu berbasis teks sebagai antarmuka utama pengguna.
     - Menghubungkan semua fitur di atas melalui Main.java.

6. **Pencarian Pasien dengan Binary Search Tree (BST)**
    - Menyimpan dan mengelola data pasien dalam struktur pohon biner.
    - Memungkinkan pencarian pasien berdasarkan ID dengan efisiensi tinggi.
    - Diimplementasikan dalam file BST.java.
