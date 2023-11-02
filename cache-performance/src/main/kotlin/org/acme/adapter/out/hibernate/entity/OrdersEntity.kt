package org.acme.adapter.out.hibernate.entity

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.ZonedDateTime
import java.util.*

@Entity
@Table(name = "orders")
@Serializable
class OrdersEntity : PanacheEntityBase {
    @Id
    @GeneratedValue
    @Column(name = "uuid", updatable = false)
    @Serializable(with = UUIDSerializer::class)
    lateinit var id: UUID

    lateinit var name: String

    @Column(name = "created_at", updatable = false)
    @Serializable(with = ZonedDateTimeSerializer::class)
    lateinit var createdAt: ZonedDateTime

    @Column(name = "updated_at")
    @Serializable(with = ZonedDateTimeSerializer::class)
    var updatedAt: ZonedDateTime? = null
}

class UUIDSerializer : KSerializer<UUID> {
    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }
}

class ZonedDateTimeSerializer : KSerializer<ZonedDateTime> {
    override val descriptor = PrimitiveSerialDescriptor("ZonedDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ZonedDateTime) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): ZonedDateTime {
        return ZonedDateTime.parse(decoder.decodeString())
    }
}