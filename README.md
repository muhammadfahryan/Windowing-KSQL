# Use Case For Testing Windowing
mengirim message, dengan windowing 30 detik.

```
jam 10:10:10
value : 10
key : "key2"

jam 10:10:20
value : 12
key : "key2"

nah di detik 10:10:40
data yang di terima adalah latest 
value : 12
key : "key2"
```

Hasilnya jika kita ambil stream nya dengan query :

```
SELECT ROWKEY AS rowkey,
       LATEST_BY_OFFSET(value) AS latest_value
FROM input_test_window2
WINDOW TUMBLING (SIZE 30 SECONDS)
GROUP BY ROWKEY
EMIT CHANGES;
```


Hasilnya seperti berikut :
![Hasil Dari Windowing](https://github.com/muhammadfahryan/Windowing-KSQL/assets/87516915/51fb97bc-8110-4311-88c5-51e67297e271)


Tumbling Window:
    Ini kayak lo bikin jendela waktu yang gak overlap. Setiap jendela berdiri sendiri.
     Berarti semua message dalam jendela 10:10:00-10:10:30 dihitung, dan jendela baru mulai dari 10:10:30-11:00:00.

Hopping Window:
    Ini kayak tumbling window, tapi ada overlapnya. Misalnya, setiap jendela juga 30 detik tapi mulai tiap 10 detik. 
	Jadi bisa ada lebih dari satu jendela yang cover waktu yang sama.
    Misal jendela pertama 10:10:00-10:10:30, jendela kedua 10:10:10-10:10:40, dan seterusnya. 
	Tapi ini gak cocok kalau cuma mau hasil dari satu jendela tanpa overlap.

Session Window:
    Ini jendelanya dinamis, tergantung aktivitas. Jendela baru kebuka kalau ada message baru dan akan ditutup kalau ada periode idle (gak ada message) 
	selama durasi tertentu.
    Ini lebih cocok buat use case yang unpredictable, di mana kamu gak tau kapan message akan datang. 
	Tapi kalau kamu udah tau fix durasinya 30 detik, ini gak relevan.

