import { create } from "zustand";

type Schedule = {
  id: string;
  title: string;
  start: string;
  end: string;
  className: string;
};

type ScheduleState = {
  events: Schedule[];
  setEvents: (events: Schedule[]) => void;
};

export const useScheduleStore = create<ScheduleState>((set) => ({
  events: [],
  setEvents: (newEvents) =>
    set((state) => {
      const ids = new Set(newEvents.map((e) => e.id));
      const merged = [
        ...newEvents,
        ...state.events.filter((e) => !ids.has(e.id)),
      ];
      return { events: merged };
    }),
}));
