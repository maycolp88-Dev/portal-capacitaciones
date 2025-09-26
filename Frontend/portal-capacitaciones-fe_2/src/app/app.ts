import { Component, OnInit } from '@angular/core';
import { Router, RouterModule, NavigationEnd } from '@angular/router';
import { NgIf } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, NgIf, NavbarComponent],
  templateUrl: './app.html'
})
export class AppComponent implements OnInit {
  user: any = null;

  constructor(private router: Router) {
    this.router.events.subscribe(ev => {
      if (ev instanceof NavigationEnd) {
        this.refreshUserFromStorage();
      }
    });
  }

  ngOnInit(): void {
    this.refreshUserFromStorage();
  }

  private refreshUserFromStorage(): void {
    const raw = localStorage.getItem('user');
    if (!raw) {
      this.user = null;
      return;
    }
    try {
      this.user = JSON.parse(raw);
    } catch {
      this.user = null;
    }
  }

  logout(): void {
    localStorage.removeItem('user');
    this.user = null;
    this.router.navigate(['/login']);
  }

  isAdmin(): boolean {
    if (!this.user) return false;
    return !!(this.user.admin || this.user.is_admin || this.user.isAdmin);
  }
}
