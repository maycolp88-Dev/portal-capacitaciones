import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatProgressBarModule } from '@angular/material/progress-bar';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule, MatListModule, MatProgressBarModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  dashboard: any = {};
  loading = true;
  error = '';

  constructor(private http: HttpClient) {
    this.load();
  }

  load() {
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    const uid = user.id || 1;
    this.loading = true;
    this.http.get(`/api/dashboard/${uid}`).subscribe({
      next: (res) => { this.dashboard = res; this.loading = false; },
      error: (err) => { console.error(err); this.error = 'Error cargando dashboard'; this.loading = false; }
    });
  }
}
