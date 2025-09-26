import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CoursesService, Course } from '../courses/courses.service';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatGridListModule } from '@angular/material/grid-list';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatDividerModule,
    MatGridListModule
  ],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {
  courses: Course[] = [];
  newCourse: Partial<Course> = { title: '', module: '', description: '' };
  error = '';
  success = '';

  constructor(private svc: CoursesService) {
    this.loadCourses();
  }

  loadCourses() {
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    if (!user?.id) return;

    this.svc.list(user.id).subscribe({
      next: (res) => (this.courses = res),
      error: (err) => {
        console.error('Error cargando cursos:', err);
        this.error = 'Error cargando cursos';
      }
    });
  }

  addCourse() {
    this.success = '';
    this.error = '';

    if (!this.newCourse.title || !this.newCourse.description) {
      this.error = '❌ Título y descripción requeridos';
      return;
    }

    this.svc.addCourse(this.newCourse as Course).subscribe({
      next: () => {
        this.success = '✅ Curso agregado correctamente';
        this.loadCourses();
        this.resetForm();
      },
      error: (err) => {
        console.error(err);
        this.error = '❌ Error agregando curso';
      }
    });
  }

  deleteCourse(id: number) {
    if (!confirm('¿Seguro de eliminar este curso?')) return;

    this.svc.deleteCourse(id).subscribe({
      next: () => {
        this.success = '✅ Curso eliminado';
        this.loadCourses();
      },
      error: (err) => {
        console.error(err);
        this.error = '❌ Error eliminando curso';
      }
    });
  }

  private resetForm() {
    this.newCourse = { title: '', module: '', description: '' };
  }
}
