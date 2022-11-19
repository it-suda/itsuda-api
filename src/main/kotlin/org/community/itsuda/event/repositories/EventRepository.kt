package org.community.itsuda.event.repositories

import org.bson.types.ObjectId
import org.community.itsuda.event.models.Event
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository:MongoRepository<Event , String> {
    fun findOneById(id: ObjectId): Event;

    fun save(event: Event): Event;

    override fun findAll():List<Event>;

    override fun deleteAll();
}