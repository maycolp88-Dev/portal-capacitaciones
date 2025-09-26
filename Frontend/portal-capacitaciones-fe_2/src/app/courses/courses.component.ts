import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { CoursesService, Course } from './courses.service';

// Angular Material
import { MatExpansionModule } from '@angular/material/expansion';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatDividerModule } from '@angular/material/divider';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar'; // ✅ import snackbar

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatExpansionModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatProgressBarModule,
    MatDividerModule,
    MatSnackBarModule // ✅ agregado
  ],
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css']
})
export class CoursesComponent {
  courses: Course[] = [];
  error = '';
  user: any = null;
  loading = true;

  constructor(
    private coursesService: CoursesService,
    private router: Router,
    private snackBar: MatSnackBar // ✅ inyectar snackbar
  ) {
    this.refreshUser();
    this.loadCourses();
  }

  private refreshUser() {
    this.user = JSON.parse(localStorage.getItem('user') || '{}');
    if (!this.user?.id) {
      this.error = 'Usuario no autenticado';
    }
  }

  loadCourses() {
    if (!this.user?.id) return;

    this.loading = true;
    this.coursesService.list(this.user.id).subscribe({
      next: (res) => {
        // Asignar courseId a cada módulo manualmente
        this.courses = res.map(course => ({
          ...course,
          modules: course.modules?.map(m => ({ ...m, courseId: course.id })) || []
        }));
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando cursos:', err);
        this.error = 'Error cargando cursos';
        this.loading = false;
      }
    });
  }


  startCourse(courseId: number) {
    if (!this.user?.id) return;
    this.coursesService.startCourse(courseId, this.user.id).subscribe({
      next: () => {
        this.snackBar.open('✅ Curso iniciado correctamente', 'Cerrar', {
          duration: 3000,
          horizontalPosition: 'right',
          verticalPosition: 'top',
          panelClass: ['snackbar-success'] // ✅ estilo custom
        });
        this.loadCourses();
      },
      error: () =>
        this.snackBar.open('❌ Error al iniciar el curso', 'Cerrar', {
          duration: 3000,
          horizontalPosition: 'right',
          verticalPosition: 'top',
          panelClass: ['snackbar-error']
        })
    });
  }

  completeCourse(courseId: number) {
    if (!this.user?.id) return;
    this.coursesService.completeCourse(courseId, this.user.id).subscribe({
      next: () => this.loadCourses(),
      error: () =>
        this.snackBar.open('❌ Error al completar el curso', 'Cerrar', {
          duration: 3000,
          horizontalPosition: 'right',
          verticalPosition: 'top',
          panelClass: ['snackbar-error']
        })
    });
  }

  goToModule(moduleId: number) {
    this.router.navigate(['/module', moduleId]);
  }

  completeModule(courseId: number, moduleId: number) {
    if (!this.user?.id) return;
    this.coursesService.completeModule(courseId, moduleId, this.user.id).subscribe({
      next: () => {
        this.snackBar.open(`✅ Módulo ${moduleId} completado`, 'Cerrar', {
          duration: 3000,
          horizontalPosition: 'right',
          verticalPosition: 'top',
          panelClass: ['snackbar-success']
        });
        this.loadCourses();
      },
      error: () =>
        this.snackBar.open('❌ Error al completar el módulo', 'Cerrar', {
          duration: 3000,
          horizontalPosition: 'right',
          verticalPosition: 'top',
          panelClass: ['snackbar-error']
        })
    });
  }
}
