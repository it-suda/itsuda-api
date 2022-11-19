package org.community.itsuda.event.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Event(
    @Id
    @JsonSerialize(using = ToStringSerializer::class)
    val id: ObjectId = ObjectId.get(),
    val title: String,
    val category : Any?, // category 쪼개기
    val eventType: String,
    val eventPlace: String?,
    val cost : Number?,
    val description : String?,
    val thumbnail : String?, // aws s3 올리는 link
    val host: String?,
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = LocalDateTime.now(),
    val outLink : String?
)
