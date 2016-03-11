import java.util

import com.couchbase.client.java.document.JsonDocument
import com.couchbase.client.java.document.json.JsonObject
import com.couchbase.client.java.view._
import com.couchbase.client.java.CouchbaseCluster
import collection.JavaConversions._

// Using JAVA SDK Client 2.2
object Main extends App {
  // Connect to couchbase server, CouchbaseCluster.create without parameter is localhost
  // You can specify nodes you want to connect
  // Cluster cluster = CouchbaseCluster.create("192.168.56.101", "192.168.56.102");
  val cluster = CouchbaseCluster.create

  val bucket = cluster.openBucket("dsp-agent-main", "123456")

  // Create json object
  val user = JsonObject.empty()
    .put("firstname", "Walter")
    .put("lastname", "White")
    .put("job", "chemistry teacher")
    .put("age", 50)

  // Insert document
  val stored = bucket.upsert(JsonDocument.create("walter", user))
  println(s"one document stored:$stored")

  // Retreive document
  val walter = bucket.get("walter")
  println("Found: " + walter.content().get("firstname"))


  // Create a design document named: landmarks
  // and a view named all_view_test in the landmarks design document
  val designDocumentName = "landmarks"
  val designDoc = DesignDocument.create(
    designDocumentName,
    util.Arrays.asList(
      DefaultView.create("all_view_test",
        "function (doc, meta) { emit(meta.id, null); }")
    )
  )

  // Get bucket manager
  val bucketManager = bucket.bucketManager

  // Insert design document into the bucket
  if (bucketManager.getDesignDocument(designDocumentName) == null) {
    bucketManager.insertDesignDocument(designDoc)
  }

  // Query the view in the landmark
  val result = bucket.query(ViewQuery.from("landmarks", "all_view_test"))
  for (row <- result.allRows()) {
    val doc = row.document

    // remove document whose id contains walter
    if (doc.id.contains("walter")) {
      println(s"remove ${doc.id}")
      bucket.remove(doc.id)
    }
  }

  // remove design document which was created before
  println("remove design document")
  bucketManager.removeDesignDocument(designDocumentName)

  cluster.disconnect

}


