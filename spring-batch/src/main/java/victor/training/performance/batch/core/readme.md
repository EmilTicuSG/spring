## Steps to Optimize inserts
1. Optimize PK generation
2. Activate BATCHing of inserts by Hibernate using #spring.jpa.properties.hibernate.jdbc.batch_size=100
3. Increase chunk size to 500
4. Cache cities (avoid any SELECTS)
5. Challenge: have some cities in DB at start-up
6. Challenge: insert ncities = nrecord/10, random occuring in file
7. Play with Hibernate 2nd level Cache
8. Parallelize items (first disabling country insert - preinsert them)
   1. you'll need: SynchronizedItemStreamReader and .taskExecutor(taskExecutor()) in step

My Measurements:
0: 30/s
1: 45/s
2: 60/s
3: 100/s
4: >600/s

Your measurements:
0: 59/s
cu increment by: 79/s
cu batch mode jdbc: 108/s
cu chunkuri de 100, nu 5: 200/s
fara selecturi inutile dupa city = 1333/s