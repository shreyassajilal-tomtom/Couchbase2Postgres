# Couchbase2Postgres
  Fetches Docs from couchbase according to the view parameters.<br />
  Then sends the documents as POST request to fuse-postgres web service<br />
  
  Currently doc type needs to be set manually and some corresponding changes need to be done
  
  
 ### Multithreading
  Currently multithreading implemented in a primitive way<br />
  Accomplished by changing a ForkJoinPool property<br />
  This lets parallelStream() utilise any number of threads.<br />
  Currenty utilises 1000 threads.
  
 ### Other branches of Repo
  Main - Data migration from couchbase to postgres.<br />
  checkmissing- gets only IDs of a view from couchbase and checks if all are there in postgres database(multithreaded).<br />
  insertmissing- input a txt file with ids of documents, fetches them and inserts into postgres (multithreaded).
  
