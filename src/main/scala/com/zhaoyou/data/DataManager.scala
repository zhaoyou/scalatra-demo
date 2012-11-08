package com.zhaoyou.first.data

import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId

object DataManager {
  def getCollection(dbName: String, collName: String): MongoCollection = {
    val con = MongoConnection("127.0.0.1", 27017)
    con(dbName)(collName)
  }

  def getName(id: String): String = {
    getCollection("test", "user").findOne(MongoDBObject("_id" -> new ObjectId(id))) match {
      case Some(x) => x.asInstanceOf[DBObject].as[String]("name")
      case None => "Null"
    }
  }

  def list(): List[User] = {
    (for (obj <- getCollection("test", "user").find) yield {
      toUser(obj)
    }).toList
  }

  def save(user: User) = {
    getCollection("test", "user").save(MongoDBObject("name" -> user.name,
                                                     "email" -> user.email,
                                                     "phone" -> user.phone))
  }

  def toUser(obj: DBObject): User = {
    new User(
      obj.as[ObjectId]("_id").toString,
      obj.getAsOrElse[String]("name", ""),
      obj.getAsOrElse[String]("email", ""),
      obj.getAsOrElse[String]("phone", "")
    )
  }
}

class User(var id: String,
           var name: String,
           var email: String,
           var phone: String)
