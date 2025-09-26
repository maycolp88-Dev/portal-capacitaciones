import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ModuleDetail } from '../courses/courses.service';

// Angular Material
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatIconModule } from '@angular/material/icon';

import { CoursesService } from '../courses/courses.service';

@Component({
  selector: 'app-module',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatButtonModule,
    MatDividerModule,
    MatProgressBarModule,
    MatIconModule
  ],
  templateUrl: './module-detail.component.html',
  styleUrls: ['./module-detail.component.css']
})
export class ModuleComponent implements OnInit {
  module: ModuleDetail | null = null;
  loading = true;
  error = '';
  courseId!: number;
  moduleId!: number;

  constructor(
    private route: ActivatedRoute,
    private svc: CoursesService
  ) {}

  ngOnInit(): void {
    this.moduleId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadModule();
  }

  loadModule() {
    this.svc.getModuleDetail(this.moduleId).subscribe({
      next: (res) => {
        this.module = res;
        this.loading = false;
      },
      error: () => {
        this.error = '❌ Error cargando módulo';
        this.loading = false;
      }
    });
  }

  completeModule() {
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    if (!user?.id || !this.module?.id) return;

    this.svc.completeModule(
  (this.module as any).courseId || (this.module as any).course,
  this.module.id,
  user.id
).subscribe({
  next: () => alert(`✅ Módulo completado: ${this.module?.title}`),
  error: () => alert('❌ Error al completar módulo')
    });
  }
}


