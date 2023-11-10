package org.acme.adapter.out.hibernate.repository

import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.acme.adapter.out.hibernate.entity.OrdersEntity

@ApplicationScoped
class OrdersRepository : PanacheRepositoryBase<OrdersEntity, Long> {
    fun findByName(name: String): Uni<OrdersEntity?> {
        return find("name", name).firstResult()
    }

    fun findPaginated(page: Int, perPage: Int): Uni<List<OrdersEntity>> {
        return findAll().page(page, perPage).list()
    }

    @WithTransaction
    fun createOrder(order: OrdersEntity): Uni<OrdersEntity> {
        return persistAndFlush(order)
            .onFailure {
                println("Error creating order: ${it.message}")
                throw it
            }.recoverWithItem { _ ->
                println("Order created successfully")
                null
            }
    }
}