package com.ganesh.slick.models

import slick.lifted.Tag
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Models {


  // Definition of the COFFEES table
  class Coffees(tag: Tag) extends Table[CoffeeModel](tag, "coffee_new") {
    val suppliers = TableQuery[Suppliers]
    def name = column[String]("cof_name", O.PrimaryKey)
    def supID = column[Int]("sup_id")
    def price = column[Double]("price")
    def sales = column[Int]("sales")
    def total = column[Int]("total")

    def * = (name, supID, price, sales, total) <> (CoffeeModel.tupled, CoffeeModel.unapply)
    // A reified foreign key relation that can be navigated to create a join
    def supplier = foreignKey("sup_fk", supID, suppliers)(_.id,  onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

  }

  class Suppliers(tag: Tag) extends Table[SupplierModel](tag, "suppliers_new") {
    def id = column[Int]("sup_id", O.PrimaryKey) // This is the primary key column
    def name = column[String]("sup_name")
    def street = column[String]("street")
    def city = column[String]("city")
    def state = column[String]("state")
    def zip = column[String]("zip")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, name, street, city, state, zip) <> (SupplierModel.tupled, SupplierModel.unapply)
  }

}
