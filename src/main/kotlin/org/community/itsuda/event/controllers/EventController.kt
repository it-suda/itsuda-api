package org.community.itsuda.event.controllers

import org.bson.types.ObjectId
import org.community.itsuda.event.models.Event
import org.community.itsuda.event.services.EventService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/events")
class EventController(
    private val eventService: EventService
    ) {

    @GetMapping("/beat")
    fun beat():ResponseEntity<String>{
        return ResponseEntity.ok("beat!!!!");
    }

    @GetMapping("/{id}")
    fun getEvent(@PathVariable("id") id: String): ResponseEntity<Event> {
        val event = eventService.getEventById(ObjectId(id))
        println("event::${event}" )
        return ResponseEntity.ok(event)
    }

    @GetMapping
    fun getEvents():ResponseEntity<List<Event>>{
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    /**
     * Body spec
    {
        "_id": "6242647497071b6b8364725f",
        "title" : "typescript + vue3 뽀개기",
        "category" : {
        "step1" : "DEV",
        "step2" : "WEB"
    },
        "eventType": "ONLINE",
        "cost" : 20000,  // 0이면 무료, 액수는 원단위
        "description" : "", // 상세 정보로 들어가는 description.
        "thumbnail" : "https://ap-north-east.2.xxx.xxx", // s3 리소스 엔드포인트
        "host" : "우아한형제들",
        "startDate": "2022-03-22",
        "endDate" :"2022-04-11",
        "outlink": "https://naver.com"
    }
     */
    @PostMapping("/event")
    fun createEvent(@RequestBody event:Event):ResponseEntity<Event>{
        return ResponseEntity.ok(this.eventService.saveEvent(event))
    }

    @PutMapping("/event/{id}")
    fun updateEvent(@PathVariable("id") id:String, @RequestBody event:Event):ResponseEntity<Event>{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.eventService.updateEvent(id, event))
        }catch (e: Exception) {
            if (e.message.equals("Event does not exist"))
                throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Event Not Found"
                )
            else
                throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Input is not in valid format"
                )

        }
    }

    @DeleteMapping("/event/{id}")
    fun deleteEvent(@PathVariable("id") id:String): ResponseEntity<Any>{
        return ResponseEntity.ok(this.eventService.delete(id))
    }
}