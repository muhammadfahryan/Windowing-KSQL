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

