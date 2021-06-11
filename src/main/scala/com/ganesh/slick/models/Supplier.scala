package com.ganesh.slick.models

import Models.Suppliers
import slick.lifted.Tag
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class SupplierModel(id: Int, name: String, street: String, city: String, state: String, zip: String)

class Supplier(coffees: Coffee)(protected val db: Database) {


  private[models] val suppliers = TableQuery[Suppliers]

  def _findByName(name: String): Query[Suppliers, SupplierModel, List] =
    suppliers.filter(_.name.trim === name.trim).to[List]


  def findById(id: Int): Future[Seq[SupplierModel]] =
    db.run(suppliers.filter(_.id === id).result)

  def findByName(name: String): Future[Seq[SupplierModel]] =
    db.run(suppliers.filter(_.name === name).result)

  def all: Future[List[SupplierModel]] =
    db.run(suppliers.to[List].result)

  def create(supplierModel: SupplierModel): Future[String] = {
    db.run(suppliers returning suppliers.map(_.name) += supplierModel)
  }

  def createTable() ={
    db.run(suppliers.schema.createIfNotExists)
  }

  def delete(name: String): Future[Int] = {
    val query = _findByName(name)

    val interaction = for {
      suppliers        <- {
        query.delete
      }
    } yield suppliers


    db.run(interaction)
  }

}
