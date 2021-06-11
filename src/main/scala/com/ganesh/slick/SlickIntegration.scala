package com.ganesh.slick

import com.ganesh.slick.models._
import slick.jdbc.JdbcBackend.Database
import slick.basic.DatabaseConfig
import slick.dbio.DBIO
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.meta.MTable
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


object SlickIntegration {
  def main(args: Array[String]): Unit = {
    val db = Database.forConfig("mydb")

    val existing = db.run(MTable.getTables)


    val coffees = new Coffee(db)
    val suppliers = new Supplier(coffees)(db)

    Await.result(suppliers.createTable(), 20 seconds)

    existing.map(v => {
      val names = v.map(mt => mt.name.name)
      //this check is just because coffee table has foreign key and createIfNotExist function not creating foreign key. So used create function.
      if(!names.contains(coffees.getTableName)) Await.result(coffees.createTable(),10 seconds) else None
    })

    val listOfCoffeeModels = List(CoffeeModel("test1", 1, 1000, 10, 10000),
                                  CoffeeModel("test2", 2, 1000, 10, 10000),
                                  CoffeeModel("test3", 2, 1000, 10, 10000),
                                  CoffeeModel("test4", 3, 1000, 10, 10000))

    val listOfSupplierModels = List(SupplierModel(1, "supp1", "strt1", "city1", "state1", "zip1"),
      SupplierModel(2, "supp2", "strt1", "city1", "state1", "zip1"),
      SupplierModel(3, "supp3", "strt1", "city1", "state1", "zip1"),
      SupplierModel(4, "supp4", "strt1", "city1", "state1", "zip1"))


// if we want to insert to suppliers table
//    listOfSupplierModels.map(model => Await.result(suppliers.create(model), 2 seconds))

    //if we want to insert to coffees table
//    listOfCoffeeModels.map(model => Await.result(coffees.create(model), 2 seconds))
    //delete from suppliers table
//    Await.result(suppliers.delete("supp4"), 2 seconds)

  }
}
