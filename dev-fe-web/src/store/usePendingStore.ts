import { create } from "zustand";

export interface EventType {
  id: string;
  title: string;
  start: string;
  end?: string;
  extendedProps?: {
    status?: string;
    approvalStatus?: string;
    capacity?: number;
    startDate?: string;
    endDate?: string;
    startTime?: string;
    endTime?: string;
    recurrenceType?: string;
    recurrenceInterval?: number;
    daysOfWeek?: string;
    createdAt?: string;
    updatedAt?: string;
  };
  className?: string;
}

interface EventStore {
  events: EventType[];
  setEvents: (newEvents: EventType[]) => void;
}

export const useEventStore = create<EventStore>((set) => ({
  events: [],
  setEvents: (newEvents) => set({ events: newEvents }),
}));
