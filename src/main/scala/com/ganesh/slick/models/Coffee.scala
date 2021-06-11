package com.ganesh.slick.models

import Models.Coffees
import slick.lifted.Tag
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class CoffeeModel(name: String, supId:Int, price: Double, sales: Int, total: Int)

class Coffee(protected val db: Database) {

  private[models] val coffees = TableQuery[Coffees]

  def getTableName = coffees.baseTableRow.tableName

  def _findByName(name: String): Query[Coffees, CoffeeModel, List] =
    coffees.filter(_.name === name).to[List]

  def _findBySupId(id: Int): Query[Coffees, CoffeeModel, List] =
    coffees.filter(_.supID === id).to[List]

  def findBySuppId(id: Int): Future[Seq[CoffeeModel]] =
    db.run(coffees.filter(_.supID === id).result)

  def findByName(name: String): Future[Seq[CoffeeModel]] =
    db.run(coffees.filter(_.name === name).result)

  def all: Future[List[CoffeeModel]] =
    db.run(coffees.to[List].result)

  def create(coffeeModel: CoffeeModel): Future[String] = {
    db.run(coffees returning coffees.map(_.name) += coffeeModel)
  }

  def createTable() ={
    db.run(coffees.schema.create)
  }

  def delete(name: String): Future[Int] = {
    val query = _findByName(name)

    val interaction = for {
      coffeesDeleted <- query.delete
    } yield coffeesDeleted

    db.run(interaction.transactionally)
  }

  def _deleteAllWhichHasSupplierId(id: Int): DBIO[Int] = {
    println("id is deletall "+id)
    coffees.filter(_.supID === id).delete
  }
}
