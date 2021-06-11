package com.ganesh.slick

import com.ganesh.slick.models.Models.Coffees
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.duration._
import slick.migration.api.{PostgresDialect, SqlMigration, TableMigration}

import scala.concurrent.Await

object SlickMigration {
  def main(args: Array[String]): Unit = {
    val db = Database.forConfig("mydb")

    val coffees = TableQuery[Coffees]
    implicit val dialect = new PostgresDialect

    val init =
      TableMigration(coffees)
      .renameColumn(_.total, "totalPrice")
//      .addColumns(_.newCol)   //to add new column add 'newCol' field to coffee model and then uncomment this

    val seed = SqlMigration("""insert into coffees (cof_name, sup_id, price, sales, "totalPrice", testmigration) values ('lavista', 105,  1000, 100, 100000, 'test')""")
    val migration = seed

    val result = db.run(migration())

    Await.result(result, 10 seconds)

  }
}
