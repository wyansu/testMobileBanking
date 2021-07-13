# testMobileBanking
Tools ini digunakan untuk melakukan automation test di aplikasi mobile(Android). Test akan dilakukan dengan membaca semua parameter dari file excel. Dan hasil dari test adalah file output (Excel) dengan status test dan printscreen jika diperlukan. Test ini didasarkan pada prinsip test DDT (Data Driven Test) dan bukan merupakan BDT yang umunya dipakai untuk test UI.

## Steps
1. Setup Appium (Windows)
2. Setup test code (Eclipse dan IntelliJ)
3. Run test
4. Create test script

## Setup Appium
Merefer ke dokumentasi dari appium, kita bisa menginstall appium dengan dua cara yaitu, dengan menggunakan NPM dan menggunakan aplikasi desktop. Untuk test ini kita akan menggunakan desktop application. Aplikasi dapat didownload dari di link [Appium Desktop](https://github.com/appium/appium-desktop/releases). Download file zip dan extract di working folder kita.

Setup berikutnya yang diperlukan adalah men-configure appium desktop. Konfigurasi yang diperlukan hanya forlder Android sdk. Android sdk digunakan oleh appiun untuk melakukan automation ke android devices maupun simulator android.

Run Appium

![Appium main image!](https://i.postimg.cc/D0hBXDWG/appium-main.png)

Clik edit configuration

![appium config](https://i.postimg.cc/0QkC5ShG/sdk-setting.png)

Input folder android sdk di field ANDROID_HOME. Untuk JAVA_HOME dideteksi oleh aplikasi secara auto, jika tidak sesuai dengan java yang kita gunakan, bisa diubah sesuai java yang ingin dipakai. Selanjutnya click save dan restart button.

## Setup Test Code
Test code kita menggunakan JUnit sebagai framework test dan karena kita menggunakan java kita akan menggunakan java-client dari appium. Project test code dibangun menggunakan maven. Test code bisa diimport di Eclipse maupun IntelliJ dengan create project dari existing source code. Dan pastikan semua IDE yang dipakai mengenali project sebagai maven project dengan konfigurasi file POM.

Semua dependencies yang diperlukan oleh source code ini akan dimanage oleh maven.

## Run Test
Test ini menggunakan framework JUnit dan kedua IDE yaitu Eclipse dan IntelliJ mempunyai UI untuk execute JUnit (termasuk UI report). Walaupun kedua IDE tersebut mempunyai UI untuk report hasil test, kita akan membuat report sendiri untuk hasil test di file excel.

File excel config ini saya set namingnya sama dengan nama class yang dijalankan pada saat test, sebagai conton jika class yang dijalankan adalah LoginTest maka file config akan diambil di LoginTest.xls di folder resources. Sedangkan untuk hasil test saya put di c/temp/YYY_result.xls dengan YYY adalah namaclass. Parameter ini bisa diubah di method initliaze parameter.

![ubah param](https://i.postimg.cc/pybw6k1Y/code-foledr.png)

Untuk menjalankan test bisa disesuaikan dengan IDE masing-masing untuk menjalankan test JUnit.

## Create Test Script
Untuk membuat test script, step-step yang diperlukan :

1. buat class dengan extend salah satu class
2. buat file config di excel dengan row pertama adalah header yang sekaligus merupakan nama parameter. Paramter ini dibuat dengan map, dengan nama header sebagai key. Misalnya kita mempunyai kolom dengan header "Username" makan di dalam code kita bisa mengakses value ini dengan `testParam.get("Username")`
3. test yang sama akan dijalankan sesuai jumlah row di file excel (semua row kecuali header atau row 1)
4. method untuk test adalah method test JUnit biasa. Untuk menggunakan test ini saya tidak menggunakan assertion tetapi hanya catch exception di setiap step test. Dengan cara ini saya tidak perlu tahu detail test dan memungkinkan menggunakan kombinasi data sebagai penentu test.
5. gunakan LoginTest sebagai sample untuk membuat test script.

