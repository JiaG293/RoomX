package com.roomx.domain.service;

import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.model.entity.ConflictInfo;
import com.roomx.shared.dto.booking.base.BookingRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ConflictBookingResolutionDomainService {
    public List<BookingRequest> suggestApprovalOrder(List<BookingRequest> requests) {
        List<BookingRequestEvent> events = new ArrayList<>();
        //CreateEvent
        for(BookingRequest request : requests){
            LocalDateTime startTime = LocalDateTime.of(request.getStartDate(),request.getStartTime());
            LocalDateTime endTime = LocalDateTime.of(request.getEndDate(),request.getEndTime());
            events.add(new BookingRequestEvent(startTime, "start", request.getId()));
            events.add(new BookingRequestEvent(endTime, "end", request.getId()));
        }

        //SortEvent
        events.sort(Comparator.comparing(BookingRequestEvent::getTimestamp));

        Set<BookingRequest> activeRequests = new HashSet<>();
        List<ConflictInfo> conflictInfos = new ArrayList<>();
        Map<BookingRequest, ConflictInfo> conflictInfoMap = new HashMap<>();

        for (BookingRequest request : requests) {
            conflictInfoMap.put(request, ConflictInfo.builder().request(request).build());
        }

        //Run sweepLine
        for (BookingRequestEvent event : events) {
            BookingRequest request = requests.stream().filter(r -> r.getId() == event.getBookingRequestId()).findFirst().orElse(null);
            if (event.getType().equals("start")) {
                for (BookingRequest activeRequest : activeRequests) {
                    if (isConflict(request, activeRequest)) {
                        conflictInfoMap.get(request).getConflictingRequests().add(activeRequest);
                        conflictInfoMap.get(activeRequest).getConflictingRequests().add(request);
                    }
                }
                activeRequests.add(request);
            } else if (event.getType().equals("end")) {
                activeRequests.remove(request);
            }
        }

        // Suggestion approve order
        return suggestApprovalOrderAlgorithm(new ArrayList<>(conflictInfoMap.values()));
    }

    // Suggest the approval order
    private List<BookingRequest> suggestApprovalOrderAlgorithm(List<ConflictInfo> conflictInfos) {
        List<BookingRequest> approvalOrder = new ArrayList<>();
        Set<BookingRequest> remainingRequests = new HashSet<>();
        for (ConflictInfo conflictInfo : conflictInfos) {
            remainingRequests.add(conflictInfo.getRequest());
        }

        while (!remainingRequests.isEmpty()) {
            // Find the request with the highest priority
            BookingRequest bestRequest = remainingRequests.stream()
                    .max(Comparator.comparing(BookingRequest::getPriority))
                    .orElse(null);

            if (bestRequest == null) break; // No more requests

            // Add to the approval list
            approvalOrder.add(bestRequest);

            // Remove conflicting requests
            remainingRequests.remove(bestRequest);
            for (ConflictInfo conflictInfo : conflictInfos) {
                if (conflictInfo.getRequest() == bestRequest) {
                    remainingRequests.remove(bestRequest);
                    for(BookingRequest c : conflictInfo.getConflictingRequests()){
                        remainingRequests.remove(c);
                    }
                    break;
                }
            }
        }

        return approvalOrder;
    }

    //Check the time of the booking
    private boolean isConflict(BookingRequest requestA, BookingRequest requestB) {
        return !requestA.getEndTime().isBefore(requestB.getStartTime()) && !requestA.getStartTime().isAfter(requestB.getEndTime());
    }

}
