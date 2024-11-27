# UAS_PBO

1. cae
	- membuat class registrasi dan class login 
		-> jangan buat class main(tempat dijalankan)
		-> kalau bisa beda file contoh
			- registrasi.java
			- login.java
		-> data registrasi di simpan di file "data_akun.txt" sehingga ketika login tinggal memasukkan username dan password dari data_akun.txt
		-> password jangan di hash
		-> di github nanti berarti ada 3 file yaitu: 
			- registrasi.java
			- login.java
			- data_akun.txt
2. farjib
 	- membuat class fitur memasukkan barang, class melihat barang, class menghapus, dan class checkout kedalam keranjang
 		-> jangan buat class main(tempat dijalankan)
 		-> barang yang dimasukkan di simpan di file "data_barang.txt"
 		-> buat fitur diatas tanpa fitur login
 		-> kalau bisa beda file contoh:
 			- class memasukkan barang
 			- class melihat barang
 			- class menghapus barang
 		-> di github nanti berarti ada 4 file yaitu:
 			- class memasukkan barang
 			- class meelihat barang
 			- class menghapus barang
 			- data_barang.txt 
3. riyan
 	- buat folder Main.java + buat role admin dan user 
 		-> unduh semua kode program cae(3 file) dan farjib(5 file)
 		-> jalankan kode cae + farjib di Main.java
 		-> admin 
 			- data admin disimpan di "data_admin.txt"
 			- penambahan
 			- penghapusan 
 			- peng-editan barang
 		-> user 
 			- datam user disimpan di "data_user.txt"
 			- memasukkan uang 
 				- masukkan ke dalam file "data_uang.txt"
 			- melihat barang
 				- melihat barang di file "data_barang.txt"
 			- memasukkan barang ke keranjang
 				- masukkan ke dalam file "data_barang_di_keranjang.txt"
 		-> di github nanti berarti ada 12 file 
 			- admin.java
 			- user.java
 			- Main.java
 			- data_uang.txt
 			- data_barang_di_keranjang.txt
 			- file cae ( 3 file ) 
 			- file farjin ( 4 file )
 		-> jika bisa admin dan user disatukan saja filenya. 
4. faris
	- membuat metode pembayaran yang dilakukan oleh user + fitur checkout
 		-> unduh semua kode yang ada di github riyan
		-> ubah file "user.java" dari kode riyan dan menambahkan metode pembayaran
		-> metode pembayaran akan membaca file "data_barang_di_keranjang.txt"
 		-> buat metode pembayaran, dan ketika melakukan transaksi, uang yang berada di file "data_uang.txt" akan berkurang
 		-> jalankan kode tersebut di dalam Main.java
 		-> masukkan semua kode di github
5. muadz 
	- membuat transaksi diterima oleh admin + pengguna dapat melihat history belanja.
		-> unduh seluruh file di github faris
		-> buat file "transaksi.java" 
			- melihat transaksi yang dan dapat menyetujui transaksi serta tidak setuju

6. amirul
   - memperbagus yang sudah dibuat