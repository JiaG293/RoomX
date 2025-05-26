// stores/branchStore.ts
import { create } from "zustand";

export type Floor = {
  id: string;
  code: string;
  name: string;
};

export type Building = {
  id: string;
  code: string;
  name: string;
  children: Floor[];
};

export type Branch = {
  id: string;
  code: string;
  name: string;
  layout: string;
  children: Building[];
};

interface BranchState {
  branch: Branch[];
  setBranch: (branch: Branch[]) => void;
}

export const useBranchStore = create<BranchState>((set) => ({
  branch: [],
  setBranch: (branch) => set({ branch }),
}));
