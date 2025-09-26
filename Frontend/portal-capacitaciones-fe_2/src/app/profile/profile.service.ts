import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ModuleProgress {
  id: number;
  title: string;
  description: string;
  orderIndex: number;
  status: string;
  updatedAt?: string;
}

export interface CourseProgress {
  id: number;
  title: string;
  module: string;
  description?: string;
  status: string;
  progress: number;
  modules: ModuleProgress[];
}

export interface Badge {
  id: number;
  courseId: number;
  awardedAt: string;
}

export interface ProfileResponse {
  user: { id: number; username: string; admin: boolean; };
  courses: CourseProgress[];
  badges: Badge[];
}

@Injectable({ providedIn: 'root' })
export class ProfileService {
  constructor(private http: HttpClient) {}

  getProfile(userId: number): Observable<ProfileResponse> {
    return this.http.get<ProfileResponse>(`/api/profile/${userId}`);
  }
}

