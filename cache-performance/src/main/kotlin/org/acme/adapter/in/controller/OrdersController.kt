package org.acme.adapter.`in`.controller

import io.smallrye.mutiny.Uni
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import kotlinx.serialization.Serializable
import org.acme.adapter.out.hibernate.entity.OrdersEntity
import org.acme.adapter.out.hibernate.repository.OrdersRepository

@Serializable
data class OrderRequest(
    val name: String
)

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class OrdersController(
    private val ordersRepository: OrdersRepository
) {
    @GET
    fun getOrders(
        @QueryParam("page") page: Int? = null,
        @QueryParam("perPage") perPage: Int? = null
    ): Uni<List<OrdersEntity>> = ordersRepository.findPaginated(page ?: 0, perPage ?: 10)

    @GET
    @Path("{name}")
    fun getOrderByName(
        @PathParam("name") name: String,
    ): Uni<OrdersEntity?> = ordersRepository.findByName(name)

    @POST
    fun createOrder(
        orderRequest: OrderRequest,
    ) = ordersRepository.createOrder(OrdersEntity.create(orderRequest))
}