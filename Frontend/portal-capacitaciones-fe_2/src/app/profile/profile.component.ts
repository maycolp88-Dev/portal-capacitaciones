import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ProfileService } from './profile.service';

// Angular Material
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatProgressBarModule,
    MatIconModule,
    MatDividerModule
  ],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  profile: any = null;
  loading = true;
  error = '';

  constructor(private svc: ProfileService) {
    this.loadProfile();
  }

  loadProfile() {
    const raw = localStorage.getItem('user');
    if (!raw) {
      this.error = 'No hay usuario logueado';
      this.loading = false;
      return;
    }
    const user = JSON.parse(raw);
    if (!user?.id) {
      this.error = 'No hay usuario válido en sesión';
      this.loading = false;
      return;
    }

    this.svc.getProfile(user.id).subscribe({
      next: (res) => {
        this.profile = res;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando perfil', err);
        this.error = 'No se pudo cargar perfil';
        this.loading = false;
      }
    });
  }

  // 👉 cantidad de cursos inscritos
  get enrolledCount(): number {
    return this.profile?.progress?.length || 0;
  }

  // 👉 cantidad de cursos completados
  get completedCount(): number {
    if (!this.profile?.progress) return 0;
    return this.profile.progress.filter((c: any) => c.status === 'completado').length;
  }

  // 👉 cantidad de insignias
  get badgesCount(): number {
    return this.profile?.badges?.length || 0;
  }

  getBadgeIcon(courseId: number): string {
  switch (courseId) {
    case 1: return 'badges/fullstack.png';
    case 2: return 'badges/apis.png';
    case 3: return 'badges/cloud.png';
    case 4: return 'badges/data.png';
    default: return 'badges/default.png';
    }
  }
}
