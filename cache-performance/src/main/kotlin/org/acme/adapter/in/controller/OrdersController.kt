package org.acme.adapter.`in`.controller

import io.smallrye.mutiny.Uni
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.acme.adapter.out.hibernate.entity.OrdersEntity
import org.acme.adapter.out.hibernate.repository.OrdersRepository

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class OrdersController(
    private val ordersRepository: OrdersRepository
) {

    @GET
    @Path("/")
    fun getOrders(
        @QueryParam("page") page: Int? = null,
        @QueryParam("perPage") perPage: Int? = null
    ): Uni<List<OrdersEntity>> = ordersRepository.findPaginated(page ?: 0, perPage ?: 10)

    @GET
    @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    fun getOrderByName(
        @PathParam("name") name: String,
    ): Uni<OrdersEntity?> = ordersRepository.findByName(name)

}