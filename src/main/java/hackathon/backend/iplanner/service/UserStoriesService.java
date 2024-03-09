package hackathon.backend.iplanner.service;

import hackathon.backend.iplanner.model.PlanningRoom;
import hackathon.backend.iplanner.model.UserEvents;
import hackathon.backend.iplanner.model.UserStory;
import hackathon.backend.iplanner.model.events.EstimateStoryEvent;
import hackathon.backend.iplanner.repository.PlanningRoomRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserStoriesService {

    private final PlanningRoomService planningRoomService;
    private final PlanningRoomRepository planningRoomRepository;

    public UserStoriesService(PlanningRoomService planningRoomService, PlanningRoomRepository planningRoomRepository) {
        this.planningRoomService = planningRoomService;
        this.planningRoomRepository = planningRoomRepository;
    }

    @Cacheable(value = "userStoriesCache", key = "#roomName")
   public List<EstimateStoryEvent> getRoomStories(String roomName){
        return null;
   }

    @CachePut(value = "userStoriesCache", key = "#roomName")
    public List<EstimateStoryEvent> updateRoomStories(String roomName, EstimateStoryEvent estimateStoryEvent) {
        // This method will always be executed, and its result will be put in the cache.
        // It's useful for updating the cache after an event.
        //List<EstimateStoryEvent> existingUserStories = getRoomUserStories(roomName);

        //existingUserStories.add(estimateStoryEvent);

        return null;
    }

    @CacheEvict(value = "userStoriesCache", key = "#roomName")
    public void evictUserStoriesState(String roomName) {
        // This method will remove the entry for the specified key from the cache.
        // It's useful when you need to invalidate or evict the cache.
    }

    // comment
    public List<UserStory> getRoomUserStories(String roomName) {
        PlanningRoom planningRoom = planningRoomRepository.findByRoomName(roomName)
                .orElseThrow(() -> new IllegalStateException("Room not found"));
        UserEvents userEvents = planningRoom.getUserEvents().get(planningRoom.getRoomOwner());
        List<UserStory> estimatedStories = userEvents.getEventList().stream()
                .filter(event -> event instanceof EstimateStoryEvent)
                .map(event -> {
                    EstimateStoryEvent estimateEvent = (EstimateStoryEvent) event;
                    return new UserStory(estimateEvent.getPayload().getTitle(),
                            estimateEvent.getPayload().getDescription(),
                            estimateEvent.getPayload().getPlayerEstimations(),
                            estimateEvent.getPayload().getEstimate());
                })
                .collect(Collectors.toList());
        return estimatedStories;
    }
}
