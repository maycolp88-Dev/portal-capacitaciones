import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface DashboardData {
  totalCourses: number;
  coursesStarted: number;
  coursesCompleted: number;
  badges: number;
  avgProgress: number;
  courses: { id: number; title: string; progress: number; status: string }[];
  badgesList: { id: number; courseId: number; courseTitle: string; awardedAt: string }[];
}

@Injectable({ providedIn: 'root' })
export class DashboardService {
  constructor(private http: HttpClient) {}

  getDashboard(userId: number): Observable<DashboardData> {
    return this.http.get<DashboardData>(`/api/dashboard/${userId}`);
  }
}
