package org.acme.adapter.`in`.controller

import io.smallrye.mutiny.Uni
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.acme.adapter.`in`.controller.dto.OrderRequest
import org.acme.adapter.out.hibernate.entity.OrdersEntity
import org.acme.adapter.out.hibernate.repository.OrdersRepository
import jakarta.ws.rs.core.Response;
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Path("/orders/etag")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class OrdersWithETagController(
    private val ordersRepository: OrdersRepository
) {
    private var eTag: String = genETag()
    private val json = Json { ignoreUnknownKeys = true }

    @GET
    fun getOrders(
        @QueryParam("page") page: Int? = null, @QueryParam("perPage") perPage: Int? = null,
        @HeaderParam("If-None-Match") eTag: String? = null,
    ): Uni<Response> {
        if (eTag == this.eTag) {
            return Uni.createFrom().item(Response.notModified().build())
        }
        return ordersRepository.findPaginated(page ?: 0, perPage ?: 10)
            .onItem().transform { orders ->
                val newETag = genETag(*orders.toTypedArray())
                val ordersString = json.encodeToString(orders)
                Response.ok(ordersString).header("tag", this.eTag).build()
            }
    }

    @POST
    fun createOrder(
        orderRequest: OrderRequest,
    ): Uni<Response> {
        return ordersRepository.createOrder(OrdersEntity.create(orderRequest))
            .onItem().transform { order ->
                eTag = genETag(order)
                Response.ok(order).header("eTag", eTag).build()
            }
    }

    private fun genETag(vararg order: OrdersEntity): String {
        return order.joinToString { "${it.id}#" }
    }
}