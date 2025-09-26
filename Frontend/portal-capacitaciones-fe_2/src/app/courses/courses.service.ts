import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Module {
  id: number;
  title: string;
  description: string;
  orderIndex: number;
  courseId?: number;
  status?: string;
  content?: string;
}

export interface Course {
  id: number;
  title: string;
  description: string;
  module: string;
  modules?: Module[];
  progress?: number;
}

export interface ModuleDetail {
  id: number;
  title: string;
  description: string;
  orderIndex: number;
  content: string;
  course: number;
}

@Injectable({ providedIn: 'root' })
export class CoursesService {
  constructor(private http: HttpClient) {}

  list(userId: number): Observable<Course[]> {
  return this.http.get<Course[]>(`/api/courses?userId=${userId}`);
  }

  startCourse(courseId: number, userId: number): Observable<any> {
    return this.http.post(`/api/courses/${courseId}/start`, { userId });
  }

  completeCourse(courseId: number, userId: number): Observable<any> {
    return this.http.post(`/api/courses/${courseId}/complete`, { userId });
  }

  addCourse(course: Course): Observable<Course> {
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    return this.http.post<Course>(`/api/admin/courses?userId=${user.id}`, course);
  }

  deleteCourse(id: number): Observable<void> {
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    return this.http.delete<void>(`/api/admin/courses/${id}?userId=${user.id}`);
  }

  getModule(moduleId: number): Observable<Module> {
  return this.http.get<Module>(`/api/modules/${moduleId}`);
  }

  completeModule(courseId: number, moduleId: number, userId: number): Observable<any> {
  return this.http.post(`/api/courses/${courseId}/modules/${moduleId}/complete`, { userId });
  }

  getModuleDetail(moduleId: number): Observable<ModuleDetail> {
    return this.http.get<ModuleDetail>(`/api/modules/${moduleId}`);
  }

  addModule(courseId: number, module: Partial<Module>): Observable<Module> {
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    return this.http.post<Module>(
      `/api/admin/courses/${courseId}/modules?userId=${user.id}`,
      module
    );
  }

  deleteModule(courseId: number, moduleId: number): Observable<void> {
  const user = JSON.parse(localStorage.getItem('user') || '{}');
  return this.http.delete<void>(`/api/admin/courses/${courseId}/modules/${moduleId}?userId=${user.id}`);
  }

}

