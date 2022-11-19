package org.community.itsuda.event.services

import org.bson.types.ObjectId
import org.community.itsuda.event.models.Event
import org.community.itsuda.event.repositories.EventRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Service
class EventService(
    private val repository: EventRepository
) {
    fun getEventById(id: ObjectId):Event = repository.findOneById(id)
    fun getAllEvents():List<Event> = repository.findAll();
    fun saveEvent(@RequestBody body: Event): Event {
            println("body값: ${body}")
            return  repository.save(body)
    }

    fun updateEvent(
        @PathVariable id: String ,
        @RequestBody event: Event
    ): Event {
        val copy = event.copy(id = ObjectId(id))
        return repository.save(copy)
    }

    fun delete(id: String):Any{
        return repository.deleteById(id)
    }
}