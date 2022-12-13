# Couchbase2Postgres
  Fetches List of Doc IDs and then pulls them by ID from couchbase according to the view parameters.
  Then sends the documents as POST request to fuse-postgres web service
  
  Currently doc type needs to be set manually and some corresponding changes need to be done
  
  
 ### Multithreading
  Currently multithreading implemented in a primitive way
  Accomplished by changing a ForkJoinPool property
  This lets parallelStream() utilise any number of threads.
  Currenty utilises 1000 threads.
  
 ### Other branches of Repo
  Main - Data migration from couchbase to postgres.
  checkmissing- gets only IDs of a view from couchbase and checks if all are there in postgres database(multithreaded).
  insertmissing- input a txt file with ids of documents, fetches them and inserts into postgres (multithreaded).
  
